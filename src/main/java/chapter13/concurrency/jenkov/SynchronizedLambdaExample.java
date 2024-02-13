package chapter13.concurrency.jenkov;

import java.util.function.Consumer;

public class SynchronizedLambdaExample {

    public static void main(String[] args) {
        Consumer<String> func = (String param) -> {
            synchronized (SynchronizedLambdaExample.class) {
                System.out.println(Thread.currentThread().getName() + " step 1: " + param);

                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " step 2: " + param);
            }
        };

        Thread t1 = new Thread(() -> func.accept("Parameter 1"));
        Thread t2 = new Thread(() -> func.accept("Parameter 2"));

        t1.start();
        t2.start();
    }
}
