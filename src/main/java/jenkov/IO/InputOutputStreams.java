package jenkov.IO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class InputOutputStreams {




    public static void main(String[] args) throws Exception{
        InputStream is = new FileInputStream("E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\input-file.txt");


        byte[] bytes = new byte[is.available()];

        while (is.read(bytes) != -1) {
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
            System.out.println("Remaining bytes to read: " + is.available());
        }

    }
}
