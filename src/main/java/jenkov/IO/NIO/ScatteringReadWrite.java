package jenkov.IO.NIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatteringReadWrite {

    public static void main(String[] args) throws Exception{
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArr = {header, body};

        RandomAccessFile aFile = new RandomAccessFile("E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\NIO\\input-file.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        inChannel.read(bufferArr);

        inChannel.write(bufferArr);

        aFile.close();
    }
}
