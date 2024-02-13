package jenkov.IO.NIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BasicChannelExcample {

    public static void main(String[] args) throws Exception{
        RandomAccessFile aFile = new RandomAccessFile("E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\NIO\\input-file.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);

        while (bytesRead != -1) {
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }

        aFile.close();

    }
}
