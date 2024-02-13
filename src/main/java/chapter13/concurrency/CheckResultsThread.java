package chapter13.concurrency;

public class CheckResultsThread {


    static int counter = 0;

    public static void main(String[] args) {
        final var mainThread = Thread.currentThread();
        checkResults(mainThread);
        while (counter < 1_000_000) {
            System.out.println("Not reached yet");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
                System.out.println("Reached: " + counter);
            }
        }

    }
    static void checkResults(Thread mainThread) {
        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) counter++;
            mainThread.interrupt();
        }).start();
    }
}
