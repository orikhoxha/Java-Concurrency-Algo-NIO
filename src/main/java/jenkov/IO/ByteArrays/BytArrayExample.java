package jenkov.IO.ByteArrays;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BytArrayExample {

    public static void main(String[] args) throws Exception{

        String abc = "orik";
        byte[] bytes = abc.getBytes();

        InputStream inputStream = new ByteArrayInputStream(bytes);

        int data = inputStream.read();
        while (data != -1) {
            System.out.println((char) data);
            data = inputStream.read();
        }
    }
}
