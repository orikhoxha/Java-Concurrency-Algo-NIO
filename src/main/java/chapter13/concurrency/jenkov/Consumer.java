package chapter13.concurrency.jenkov;

public class Consumer implements Runnable {

    BlockingQueue blockingQueue;

    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String timeMillis = (String) blockingQueue.dequeue();
                System.out.println(timeMillis + "consumed");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
