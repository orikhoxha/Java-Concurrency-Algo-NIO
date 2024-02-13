package oliverloeffler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.List;

public class EventLoop {

    private final Options options;
    private final Logger logger;
    private final Handler handler;

    private final Scheduler scheduler;
    private final ByteBuffer readBuffer;
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    private long connectionCounter;

    private volatile boolean stop;

    public EventLoop(Options options, Handler handler) throws IOException{
        this(options, new DebugLogger(), handler);
    }

    public EventLoop(Options options, Logger logger, Handler handler) throws IOException {
        this.options = options;
        this.logger = logger;
        this.handler = handler;

        scheduler = new Scheduler();
        readBuffer = ByteBuffer.allocateDirect(options.readBufferSize());
        selector = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(options.host(), options.port()), options.acceptLength());
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private class Connection {
        static final String HTTP_1_0 = "HTTP/1.0";
        static final String HTTP_1_1 = "HTTP/1.1";

        final SocketChannel socketChannel;
        final SelectionKey selectionKey;
        final ByteTokenizer byteTokenizer;
        final String id;
        RequestParser requestParser;
        ByteBuffer writeBuffer;
        ScheduledTask socketTimeoutTask;
        boolean httpOneDotZero;
        boolean keepAlive;

        private Connection(SocketChannel socketChannel, SelectionKey selectionKey) {
            this.socketChannel = socketChannel;
            this.selectionKey = selectionKey;
            //byteTokenizer = new ByteTokenizer();
            byteTokenizer = new ByteTokenizer();

            id = Long.toString(connectionCounter++);
            requestParser = new RequestParser(byteTokenizer);
            socketTimeoutTask = scheduler.schedule(this::onSocketTimeout, options.socketTimeout());
        }

        private void onSocketTimeout() {
            if (logger.enabled()) {
                logger.log(new LogEntry("event", "socket_timeout"),
                           new LogEntry("id", id));

            }
            failSafeClose();
        }

        private void onReadable() {
            try {
                doOnReadable();
            } catch (IOException | RuntimeException e) {
                if (logger.enabled()) {
                    logger.log(e, new LogEntry("event", "read_error"),
                                  new LogEntry("id", id));
                }
                failSafeClose();
            }

        }

        private void doOnReadable() throws IOException {
            readBuffer.clear();
            int numBytes = socketChannel.read(readBuffer);
            if (numBytes < 0) {
                if (logger.enabled()) {
                    logger.log(new LogEntry("event", "read_close"),
                               new LogEntry("id", id));
                }
                failSafeClose();
                return;
            }

            socketTimeoutTask = socketTimeoutTask.reschedule();
            readBuffer.flip();
            byteTokenizer.add(readBuffer);
            if (logger.enabled()) {
                logger.log(
                        new LogEntry("event", "read_bytes"),
                        new LogEntry("id", id),
                        new LogEntry("read_bytes", Integer.toString(byteTokenizer.remaining()))
                );
            }

            if (requestParser.parse()) {
                if (logger.enabled()) {
                    logger.log(
                            new LogEntry("event", "read_request"),
                            new LogEntry("id", id),
                            new LogEntry("request_bytes", Integer.toString(byteTokenizer.remaining()))
                    );
                }
                onParseRequest();
            }
        }

        private void onParseRequest() throws ClosedChannelException {
            socketChannel.register(selector, 0, this);
            Request request = requestParser.request();
            httpOneDotZero = request.version().equals(HTTP_1_0);
            keepAlive = request.hasHeader("Connection", "Keep-Alive");
            handler.handle(request, this::onResponse);
            byteTokenizer.compact();
            requestParser = new RequestParser(byteTokenizer);
        }

        private void onResponse(Response response) {
            scheduler.execute(() -> {
                try {
                    prepareToWriteResponse(response);
                } catch (IOException e) {
                    if (logger.enabled()) {
                        logger.log(e,
                                new LogEntry("event", "response_ready_error"),
                                new LogEntry("id", id));
                    }
                    failSafeClose();
                }
            });
        }

        private void prepareToWriteResponse(Response response) throws ClosedChannelException {
            String version = httpOneDotZero ? HTTP_1_0 : HTTP_1_1;
            List<Header> headers = new ArrayList<>();
            if (httpOneDotZero && keepAlive) {
                headers.add(new Header("Connection", "Keep-Alive"));
            }

            if (response.body().length > 0 &&  !response.hasHeader("Content-Length")) {
                headers.add(new Header("Content-Length", Integer.toString(response.body().length)));
            }

            writeBuffer = ByteBuffer.wrap(response.serialize(version, headers));
            socketChannel.register(selector, SelectionKey.OP_WRITE, this);
            if (logger.enabled()) {
                logger.log(new LogEntry("event", "response_ready"),
                           new LogEntry("id", id),
                           new LogEntry("num_bytes", Integer.toString(writeBuffer.remaining())));

            }
        }

        private void onWritable() throws IOException{
            int numBytes = socketChannel.write(writeBuffer);
            if (!writeBuffer.hasRemaining()) {
                if (logger.enabled()) {
                    logger.log(
                            new LogEntry("event", "write_response"),
                            new LogEntry("id", id),
                            new LogEntry("num_bytes", Integer.toString(numBytes)));
                }
                if (httpOneDotZero && !keepAlive) { // non-persistent connection, close now
                    if (logger.enabled()) {
                        logger.log(
                                new LogEntry("event", "close_after_response"),
                                new LogEntry("id", id));
                    }
                    failSafeClose();
                } else {
                    if (requestParser.parse()) {
                        if (logger.enabled()) {
                            logger.log(
                                    new LogEntry("event", "pipeline_request"),
                                    new LogEntry("id", id),
                                    new LogEntry("request_bytes", Integer.toString(byteTokenizer.remaining())));
                        }
                        onParseRequest();
                    } else {
                        writeBuffer = null;
                        socketChannel.register(selector, SelectionKey.OP_READ, this);
                    }
                }
            }
        }

        private void failSafeClose() {
            try {
                socketTimeoutTask.cancel();
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException e) {
                // supress error
            }
        }
    }
}
