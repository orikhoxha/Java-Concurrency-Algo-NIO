package chapter13.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {


    public static void printHello(Lock lock) {
        try {
            lock.lock();

            Thread.sleep(2000);
            System.out.println("hello");
        } catch (InterruptedException ex) {

        } finally{
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Thread(() -> printHello(lock)).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {

        }

        if (lock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                System.out.println("Lock obtained, entering protected code");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock, doing something else");
        }

    }
}
