package jenkov.example.nio.server.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

public class NioClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 9999);

        Runnable r = () -> {
            try (SocketChannel crunchifyClient = SocketChannel.open(crunchifyAddr)) {
                log("Connecting to Server on port 1111...");
                ArrayList<String> companyDetails = new ArrayList<String>();

                companyDetails.add("HTTP/1.1 200 OK\r\n" + // \n is not calculated in byte length
                        "Content-Length: 34\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body>Facebook</body></html>"); // 98

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

                companyDetails.add("HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 33\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body>Twitter</body></html>"); // 97

                for (String companyName : companyDetails) {
                    byte[] message = companyName.getBytes();
                    ByteBuffer buffer = ByteBuffer.wrap(message);
                    crunchifyClient.write(buffer);
                    log("sending: " + companyName);
                    buffer.clear();
                    // wait for 2 seconds before sending next message
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // close(): Closes this channel.
                // If the channel has already been closed then this method returns immediately.
                // Otherwise it marks the channel as closed and then invokes the implCloseChannel method in order to complete the close operation.
                crunchifyClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();


    }

    private static void log(String str) {

        System.out.println(str);
    }

    /*private InetAddress hostAddress;
    private int port;

    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024 * 1024);

    private List pendingChanges = new LinkedList();

    private Map pendingData = new HashMap();

    private Map rspHandler = Collections.synchronizedMap(new HashMap<>());

    public NioClient(InetAddress hostAddress, int port) throws IOException {
         this.hostAddress = hostAddress;
         this.port = port;
         this.selector = this.initSelector();
    }

    private SocketChannel initiateConnection() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false); // It's set on the server side as well

        socketChannel.connect(new InetSocketAddress(this.hostAddress, this.port));

        synchronized (this.pendingChanges) {
            this.pendingChanges.add(new ChangeRequest());
        }


    }

    private Selector initSelector() throws IOException {
        return SelectorProvider.provider().openSelector();
    }


    class ResponseHandler {
        private byte[] rsp;

        public synchronized boolean handleResponse(byte[] rsp) {
            this.rsp = rsp;
            this.notify();
            return true;
        }

        public synchronized void waitForResponse() {
            while (this.rsp == null) {

            }
        }
    }

    @Override
    public void run() {

    }*/
}


