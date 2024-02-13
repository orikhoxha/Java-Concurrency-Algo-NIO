package jenkov.example.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketProcessor implements Runnable {

    private Queue<Socket> inboundSocketQueue;

    private MessageBuffer readMessageBuffer;
    private MessageBuffer writeMessageBuffer;

    public AtomicInteger integerWrite = new AtomicInteger(0);
    public AtomicInteger integerRead = new AtomicInteger(0);

    private IMessageReaderFactory messageReaderFactory;

    private Queue<Message> outboundMessageQueue = new LinkedList<>();

    private Map<Long, Socket> socketMap = new HashMap<>();

    private ByteBuffer readByteBuffer = ByteBuffer.allocate(1024 * 1024); // 1MB
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024 * 1024); // 1MB

    private Selector readSelector;
    private Selector writeSelector;

    private IMessageProcessor messageProcessor;
    private WriteProxy writeProxy;

    private long nextSocketId = 16 * 1024;

    private Set<Socket> emptyToNonEmptySockets = new HashSet<>(); // non empty sockets
    private Set<Socket> nonEmptyToEmptySockets = new HashSet<>(); // empty sockets


    public SocketProcessor(Queue<Socket> inboundSocketQueue, MessageBuffer readMessageBuffer, MessageBuffer writeMessageBuffer,
                            IMessageReaderFactory messageReaderFactory, IMessageProcessor messageProcessor) throws IOException {
        this.inboundSocketQueue = inboundSocketQueue;
        this.readMessageBuffer = readMessageBuffer;
        this.writeMessageBuffer = writeMessageBuffer;
        this.writeProxy = new WriteProxy(writeMessageBuffer, this.outboundMessageQueue);

        this.messageReaderFactory = messageReaderFactory;
        this.messageProcessor = messageProcessor;
        this.readSelector = Selector.open();
        this.writeSelector = Selector.open();
    }

    @Override
    public void run()  {

        while (true) {
            try {
                executeCycle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void executeCycle() throws IOException {
        takeNewSockets();
        readFromSockets();
        writeToSockets();
    }

    public void takeNewSockets() throws IOException {
        Socket newSocket = this.inboundSocketQueue.poll();

        while (newSocket != null) {
            newSocket.socketId = this.nextSocketId++;
            newSocket.socketChannel.configureBlocking(false); // set socket to non-blocking mode

            newSocket.messageReader = this.messageReaderFactory.createMessageReader();
            newSocket.messageReader.init(this.readMessageBuffer);

            newSocket.messageWriter = new MessageWriter();

            this.socketMap.put(newSocket.socketId, newSocket);

            SelectionKey key = newSocket.socketChannel.register(this.readSelector, SelectionKey.OP_READ, newSocket);
            key.attach(newSocket);

            newSocket = this.inboundSocketQueue.poll();
        }
    }

    public void readFromSockets() throws IOException {
        int readReady = this.readSelector.selectNow(); // Get the ready to write sockets

        if (readReady > 0) {
            Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys(); // Iterate sockets
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                readFromSocket(key);
                keyIterator.remove();
            }
            selectedKeys.clear();
        }
    }

    private void readFromSocket(SelectionKey key) throws IOException {
        Socket socket = (Socket) key.attachment();
        socket.messageReader.read(socket, readByteBuffer);

        List<Message> fullMessages = socket.messageReader.getCompleteMessages();
        System.out.println("Read" + integerWrite.incrementAndGet());
        if (fullMessages.size() > 0) {
            for (Message message: fullMessages) {
                message.socketId = socket.socketId;
                this.messageProcessor.process(message, this.writeProxy);
            }
            fullMessages.clear();
        }

        // Close socket
        if (socket.endOfStreamReached) {
            System.out.println("Socket closed: " + socket.socketId);
            this.socketMap.remove(socket.socketId);
            key.attach(null);
            key.cancel();
            key.channel().close();
        }
    }

    private void writeToSockets() throws IOException{

        // Take all new messages
        takeNewOutboundMessages();

        // Cancel all sockets which have no more data to write
        cancelEmptySockets();

        // Register all sockets that have data and which are not yet registered
        registerNonEmptySockets();

        int writeReady = this.writeSelector.selectNow();

        if (writeReady > 0) {
            Set<SelectionKey> selectionKeys = this.writeSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                Socket socket = (Socket) key.attachment();

                socket.messageWriter.write(socket, this.writeByteBuffer);
                System.out.println("Write" + integerWrite.incrementAndGet());
                if (socket.messageWriter.isEmpty()) {
                    nonEmptyToEmptySockets.add(socket);
                }
                keyIterator.remove();
            }
            selectionKeys.clear();
        }
    }

    private void registerNonEmptySockets() throws ClosedChannelException {
        for (Socket socket : emptyToNonEmptySockets) {
            SelectionKey key = socket.socketChannel.register(this.writeSelector, SelectionKey.OP_WRITE);
            key.attach(socket);
        }
        emptyToNonEmptySockets.clear();
    }

    private void cancelEmptySockets() {
        for (Socket socket : nonEmptyToEmptySockets) {
            SelectionKey key = socket.socketChannel.keyFor(this.writeSelector);
            key.cancel();
        }
        nonEmptyToEmptySockets.clear();
    }

    private void takeNewOutboundMessages() {
        Message outMessage = this.outboundMessageQueue.poll();
        while (outMessage != null) {
            Socket socket = this.socketMap.get(outMessage.socketId);
            if (socket != null) {
                MessageWriter messageWriter = socket.messageWriter;
                if (messageWriter.isEmpty()) {
                    messageWriter.enqueue(outMessage);
                    nonEmptyToEmptySockets.remove(socket); // remove from empty sockets
                    emptyToNonEmptySockets.add(socket); // add to non empty sockets
                } else {
                    messageWriter.enqueue(outMessage);
                }
            }

            outMessage = this.outboundMessageQueue.poll();
        }
    }
}
