package chapter13.concurrency.jenkov;


public class Producer implements Runnable {

    BlockingQueue blockingQueue;

    public Producer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            long timeMillis = System.currentTimeMillis();
            try {
                this.blockingQueue.enqueue("" + timeMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sleep(1000);
        }
    }

    private void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
