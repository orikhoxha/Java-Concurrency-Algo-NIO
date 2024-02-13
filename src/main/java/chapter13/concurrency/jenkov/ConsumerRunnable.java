package chapter13.concurrency.jenkov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerRunnable implements Runnable {

    private BlockingQueue<String> blockingQueue;

    private AtomicBoolean keepRunning = new AtomicBoolean(true);

    public ConsumerRunnable(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void stop() {
        System.out.println("Stopped thread");
        this.keepRunning.set(false);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " consumer started.");

        long objectsConsumed = 0;

        while(this.keepRunning.get()) {

            takeObjectFromQueue();
            objectsConsumed++;
        }

        System.out.println(Thread.currentThread().getName() + " finishing up");

        while (this.blockingQueue.size() > 0) {
            takeObjectFromQueue();
            objectsConsumed++;
        }

        System.out.println(Thread.currentThread().getName() + " finished: " + objectsConsumed);
    }

    private void takeObjectFromQueue() {
        try {
            String obj = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
            if (obj != null) {
                // do smth
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
