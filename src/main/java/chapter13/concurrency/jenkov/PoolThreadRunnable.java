package chapter13.concurrency.jenkov;

class PoolThreadRunnable implements Runnable {

    private Thread thread;
    private java.util.concurrent.BlockingQueue taskQueue;
    private boolean isStopped;

    public PoolThreadRunnable(java.util.concurrent.BlockingQueue queue) {
        taskQueue = queue;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.take();
            } catch (InterruptedException ex) {
                System.out.println(this.thread.getName() + " interrupted!");
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }
}
