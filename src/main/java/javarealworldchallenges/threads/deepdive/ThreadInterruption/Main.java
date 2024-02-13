package javarealworldchallenges.threads.deepdive.ThreadInterruption;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)  throws InterruptedException{

        Thread t = new Thread(() -> {
            TransferQueue<String> queue = new LinkedTransferQueue<>();

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    logger.info(()-> "For 3 seconds the thread " + Thread.currentThread().getName() + " will try to poll element from queue...");
                    queue.poll(3000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    logger.severe(() -> "InterruptedException! The thread " + Thread.currentThread().getName() + " was interrupted");
                    Thread.currentThread().interrupt();
                }
            }

            logger.info(() -> "The execution was stopped");
        });



        t.start();
        Thread.sleep(1500);
        t.interrupt();
    }

}
