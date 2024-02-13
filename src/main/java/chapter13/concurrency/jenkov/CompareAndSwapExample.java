package chapter13.concurrency.jenkov;

public class CompareAndSwapExample {

    public static void main(String[] args) {
        Counter counter = new SimpleCounter(new CompareAndSwapLock());

        Counter optimisticCounter = new OptimisticLockCounter();

        CountRunnable runnable1 = new CountRunnable(counter, 1_000_000);
        CountRunnable runnable2 = new CountRunnable(counter, 1_000_000);

        CountRunnable runnable3 = new CountRunnable(optimisticCounter, 1_000_000);
        CountRunnable runnable4 = new CountRunnable(optimisticCounter, 1_000_000);

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        Thread t3 = new Thread(runnable3);
        Thread t4 = new Thread(runnable4);

        t1.start();
        t2.start();

        t3.start();
        t4.start();
    }
}
