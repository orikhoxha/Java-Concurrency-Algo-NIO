package jenkov.IO;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;

public class BufferedInputStreamExample {

    public static void main(String[] args) throws Exception{
        BufferedInputStream bs = new BufferedInputStream(new FileInputStream("E:\\java-17-certificate\\src\\main\\java\\jenkov\\IO\\NIO\\input-file.txt"));
            bs.close();



        URL url = new URL("http://jenkov.com");
        url.openConnection();

    }
}
