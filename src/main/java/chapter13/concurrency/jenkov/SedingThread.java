package chapter13.concurrency.jenkov;

public class SedingThread extends Thread{

    Semaphore semaphore;

    public SedingThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(1000);
                System.out.println("Sending signal");
                this.semaphore.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}



