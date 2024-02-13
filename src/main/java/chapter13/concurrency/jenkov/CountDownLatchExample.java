package chapter13.concurrency.jenkov;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {



    public static void main(String[] args) throws InterruptedException{

        CountDownLatch latch = new CountDownLatch(3);

        Runnable waiter = () -> {
            try {
                latch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("Waiter Released");
        };

        Runnable decrementer = () -> {
            try {
                Thread.sleep(1000);
                latch.countDown();

                Thread.sleep(1000);
                latch.countDown();

                Thread.sleep(1000);
                latch.countDown();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        new Thread(decrementer).start();
        new Thread(waiter).start();

        Thread.sleep(4000);

    }

}

