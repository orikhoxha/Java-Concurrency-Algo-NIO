package jenkov.example.nio.server;


import java.io.IOException;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Socket {

    public long socketId;

    public SocketChannel socketChannel;
    public IMessageReader messageReader;
    public MessageWriter messageWriter;

    public boolean endOfStreamReached;

    public Socket(){}

    public Socket(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {

        try {
            int bytesRead = this.socketChannel.read(byteBuffer);
            int totalBytesRead = bytesRead;

            while (bytesRead > 0)  {
                bytesRead = this.socketChannel.read(byteBuffer);
                totalBytesRead += bytesRead;
            }
            if (bytesRead == -1) {
                this.endOfStreamReached = true;
            }
            return totalBytesRead;
        } catch (IOException ex) {
            ex.printStackTrace(); // CONNECTION IS CLOSING BECAUSE SELECTOR IS GIVING 1 AFTER CLIENT CLOSE CONNECTION
            this.endOfStreamReached = true;
            return -1;
        }
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        int bytesWritten = this.socketChannel.write(byteBuffer);
        int totalBytesWritten = bytesWritten;

        while (bytesWritten > 0 && byteBuffer.hasRemaining()) {
            bytesWritten += this.socketChannel.write(byteBuffer);
            totalBytesWritten += bytesWritten;
        }
        return totalBytesWritten;
    }

}
