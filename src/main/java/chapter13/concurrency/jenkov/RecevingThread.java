package chapter13.concurrency.jenkov;

public class RecevingThread extends Thread{

    Semaphore semaphore;

    public RecevingThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.release();
                System.out.println("Receiving signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
