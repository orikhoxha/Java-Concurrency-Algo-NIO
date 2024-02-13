package chapter13.concurrency;

import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable{

    private Thread thread;
    private BlockingQueue<Runnable> taskQueue;
    private boolean isStopped;

    public PoolThreadRunnable(BlockingQueue queue) {
        this.taskQueue = queue;
    }


    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                System.out.println("Thrread " + thread.getName() + " running");
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (Exception e) {
                System.out.println("Thread " + thread.getName() + " interrupted");
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
