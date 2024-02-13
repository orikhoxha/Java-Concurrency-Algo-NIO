package jenkov.IO.Pipes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeExample {

    public static void main(String[] args) throws  Exception {
        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);

        Thread t1 = new Thread(() -> {
            try {
                output.write("Hello world, pipe!".getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                int data = input.read();

                while (data != -1) {
                    System.out.print((char) data);
                    data = input.read();
                }

            } catch (IOException ex) {

            }
        });

        t2.start();
        t1.start();
    }
}
