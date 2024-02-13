package jenkov.example.nio.server.client;

import jenkov.example.nio.server.Message;
import jenkov.example.nio.server.Socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NioClient2 implements Runnable {

    private InetAddress hostAddress;
    private int port;

    private Socket socket;

    private final Selector selector;

    private ByteBuffer readByteBuffer = ByteBuffer.allocate(1024 * 1024);

    AtomicInteger writeCount = new AtomicInteger(0);

    AtomicInteger readCount = new AtomicInteger(0);
    private volatile boolean connectionOpen;

    private Queue<byte[]> outboundMessageQueue = new ArrayBlockingQueue<>(1024);

    public NioClient2(InetAddress hostAddress, int port) throws IOException {
        this.hostAddress = hostAddress;
        this.port = port;
        this.selector = Selector.open();
        this.socket = this.initSocketConnection();
    }

    private Socket initSocketConnection() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(this.hostAddress, this.port));
        SelectionKey selectionKey = socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        Socket s = new Socket(socketChannel);
        selectionKey.attach(s);
        connectionOpen = true;
        return s;
    }

    // Write only when there is something to write
    public void writeData(byte[] data) throws IOException {
        outboundMessageQueue.add(data);

    }

    @Override
    public void run() {
        while (connectionOpen) {
            try {
                executeCycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void executeCycle() throws IOException {

        int ready = this.selector.selectNow();
        if (ready > 0) {
            Iterator<SelectionKey> selectedKeysIterator = this.selector.selectedKeys().iterator();

            while (selectedKeysIterator.hasNext()) {
                SelectionKey key = selectedKeysIterator.next();

                if (!key.isValid()) {
                    continue;
                }

                SocketChannel socketChannel = this.socket.socketChannel;

                if (key.isConnectable()) {
                    key.interestOps(SelectionKey.OP_WRITE);
                    System.out.println("Connectable");
                    while (socketChannel.isConnectionPending()) {
                        this.finishConnect(key);
                    }

                } else if (key.isReadable()) {
                    this.readFromSocket(key);
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()){
                    this.writeToSocket(key);
                    key.interestOps(SelectionKey.OP_READ);
                }

                selectedKeysIterator.remove();
            }
        }

    }

    private void finishConnect(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        try {
            socketChannel.finishConnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeToSocket(SelectionKey key) throws IOException {
        Socket socket = (Socket) key.attachment();
        byte[] data = this.outboundMessageQueue.poll();

        if (data != null) {
            socket.write(ByteBuffer.wrap(data));
        }
    }

    private void readFromSocket(SelectionKey key) throws IOException {
        Socket socket = (Socket) key.attachment();

        int bytesRead = socket.read(this.readByteBuffer);
        System.out.println("readFromSocket count:" + readCount.incrementAndGet());
        this.readByteBuffer.flip();

        if (this.readByteBuffer.remaining() == 0) {
            this.readByteBuffer.clear();
            return;
        }

        if(this.socket.endOfStreamReached) {
            terminateConnection(key);

        }

        byte[] bytesReadResponse = this.readByteBuffer.array();

        byte currByte;
        int pos = 0;
        do {
            currByte = bytesReadResponse[pos++];
            System.out.print((char)currByte);
        } while (currByte != 0 && currByte != -1);

        this.readByteBuffer.clear(); // Clear buffer before reading
    }


    // Set interest in writing only when there's data to write
    private void checkOutBoundMessage() {
        if (this.outboundMessageQueue != null && this.outboundMessageQueue.size() > 0) {
            SelectionKey key = socket.socketChannel.keyFor(this.selector);
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }


    private void terminateConnection(SelectionKey key) {
        try {



            key.attach(null);
            key.cancel();
            key.channel().close();
            this.connectionOpen = false; // Stop spinning thread.
            System.out.println("Closed connection");
        } catch (IOException ex) {
            System.out.println("Closed connection failed");
        }
    }

    public void terminateConnection() {
        try {
            this.socket.socketChannel.close();
            this.connectionOpen = false;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't close the connection");
        }
    }


    public static void main(String[] args) throws Exception{

        ArrayList<String> companyDetails = new ArrayList<String>();

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>"); // 98

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                "Content-Length: 34\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>");

        companyDetails.add("HTTP/1.1 200 OK\r\n" +
                "Content-Length: 4148\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>" +
                "<html><body>Facebook</body></html>"
        );


        InetAddress serverAddr = InetAddress.getByName("localhost");
        int port = 9999;
        NioClient2 nioClient2 = new NioClient2(serverAddr, port);

        NioClient2 nioClientNew = new NioClient2(serverAddr, port);

        Thread t = new Thread(nioClient2);
        t.start();

        for (int i = 0; i < companyDetails.size(); i++) {
            nioClient2.writeData(companyDetails.get(i).getBytes(StandardCharsets.UTF_8));
            Thread.sleep(1000);
        }

    }
}
