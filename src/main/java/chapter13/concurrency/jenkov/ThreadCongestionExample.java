package chapter13.concurrency.jenkov;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCongestionExample {


    public static void main(String[] args) {
        int objectsToProduce = 1024 * 1024;

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(objectsToProduce);
        ConsumerRunnable[] consumerRunnables = new ConsumerRunnable[3];

        synchronized (ThreadCongestionExample.class) {
            for (int i = 0; i < consumerRunnables.length; i++) {
                consumerRunnables[i] = new ConsumerRunnable(blockingQueue);
                Thread t = new Thread(consumerRunnables[i]);
                t.start();
            }
        }

        Thread producingThread = new Thread(() -> {
            for (int i = 0; i < objectsToProduce; i++) {
                try {
                    blockingQueue.put("" + i);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("All objects produced!");
            System.out.println(" => produced " + objectsToProduce);

            synchronized (ThreadCongestionExample.class) {
                for (ConsumerRunnable consumerRunnable : consumerRunnables) {
                    consumerRunnable.stop();
                }
            }
        });

        producingThread.start();
    }
}
