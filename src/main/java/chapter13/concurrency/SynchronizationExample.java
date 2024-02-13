package chapter13.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizationExample {

    private int count = 0;

    private synchronized void incrementAndReport() {
        System.out.print(++count + " ");
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        try {
            SynchronizationExample ex = new SynchronizationExample();
            for (int i = 0; i < 10; i++) {
                service.submit(ex::incrementAndReport);

            }
        } finally {
            service.shutdown();
        }
    }
}
