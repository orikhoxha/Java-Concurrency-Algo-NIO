package chapter13.concurrency.jenkov;

public class Semaphore {

    private boolean signal = false;

    public synchronized void take() {
        this.signal = true;
        notify();
    }

    public synchronized  void release() throws InterruptedException {
        while (!this.signal) wait();
        this.signal = false;
    }
}

class TestSemaphore {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();

        SedingThread sedingThread = new SedingThread(semaphore);
        RecevingThread recevingThread = new RecevingThread(semaphore);

        sedingThread.start();
        recevingThread.start();
    }
}
