package jenkov.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\input-file.txt", "rw");
        file.seek(2);

        int data = file.read();

        while (data != -1) {
            System.out.print((char)data);
            data = file.read();
        }

    }
}
