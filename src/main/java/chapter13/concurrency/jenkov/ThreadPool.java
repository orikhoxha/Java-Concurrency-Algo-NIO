package chapter13.concurrency.jenkov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {

    private BlockingQueue taskQueue;
    private List<PoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped;

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            PoolThreadRunnable poolThreadRunnable = new PoolThreadRunnable(taskQueue);
            runnables.add(poolThreadRunnable);
        }
        runnables.forEach(r -> new Thread(r).start());
    }

    public synchronized void execute(Runnable task) throws Exception {
        if (this.isStopped) throw new IllegalStateException("ThreadPool is stopped");
        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (PoolThreadRunnable thread : runnables) {
            thread.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (this.taskQueue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}



