package chapter13.concurrency.jenkov;

public class ThreadLocalBasicExample {


    public static void main(String[] args) {
        ThreadLocal<String> threadLocal =  new ThreadLocal<>();

        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread 1");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String value = threadLocal.get();
            System.out.println(value);
        });

        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread 2");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String value = threadLocal.get();
            System.out.println(value);
        });

        t1.start();
        t2.start();
    }
}
