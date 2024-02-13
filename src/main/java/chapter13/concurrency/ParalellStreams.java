package chapter13.concurrency;

import java.util.List;

public class ParalellStreams {

    static int doWork(int input) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return input;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List.of(1,2,3,4,5)
                .parallelStream()
                .map(ParalellStreams::doWork)
                .forEach(s -> System.out.print(s + " "));

        var takenTime = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Time: " + takenTime + " seconds");
    }
}
