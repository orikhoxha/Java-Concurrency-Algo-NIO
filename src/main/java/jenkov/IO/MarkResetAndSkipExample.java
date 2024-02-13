package jenkov.IO;

import java.io.ByteArrayInputStream;

public class MarkResetAndSkipExample {


    public static void main(String[] args) {
        byte[] bytes = "abcdefg".getBytes();

        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        int data = stream.read();
        System.out.print(data + " "); // a
        data = stream.read();
        System.out.print(data + " "); // b
        data = stream.read();
        System.out.print(data + " "); // c

        stream.mark(1024); // before d

        data = stream.read();
        System.out.print(data + " "); // d
        data = stream.read();
        System.out.print(data + " "); // e
        stream.reset();
        data = stream.read();
        System.out.print(data + " "); // d



    }
}
