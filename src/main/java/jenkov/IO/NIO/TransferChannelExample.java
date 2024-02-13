package jenkov.IO.NIO;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransferChannelExample {

    public static void main(String[] args) throws Exception {

        String fromChannelURL = "E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\NIO\\input-file.txt";
        String toChannelURL   = "E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\NIO\\input-file2.txt";


        RandomAccessFile fromFile = new RandomAccessFile(fromChannelURL, "rw");
        FileChannel fromFileChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(toChannelURL, "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = toChannel.size();
        System.out.println(position);
        long count = fromFileChannel.size();

        toChannel.transferFrom(fromFileChannel, position, count);

        fromFile.close();
        toFile.close();
    }
}
