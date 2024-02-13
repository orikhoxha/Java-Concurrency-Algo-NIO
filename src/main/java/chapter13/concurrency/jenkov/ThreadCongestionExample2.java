package chapter13.concurrency.jenkov;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCongestionExample2 {


    public static void main(String[] args) {
        int objectsToProduce = 1024 * 1024;

        BlockingQueue<String>[] blockingQueues = new BlockingQueue[3];

        for (int i = 0; i < blockingQueues.length; i++) {
            blockingQueues[i] = new ArrayBlockingQueue<>(objectsToProduce);
        }

        ConsumerRunnable[] consumerRunnables = new ConsumerRunnable[3];

        synchronized (ThreadCongestionExample2.class) {
            for (int i = 0; i < consumerRunnables.length; i++) {
                consumerRunnables[i] = new ConsumerRunnable(blockingQueues[i]);
                Thread t = new Thread(consumerRunnables[i]);
                t.start();
            }
        }

        Thread producingThread = new Thread(() -> {
            for (int i = 0; i < objectsToProduce; i++) {
                try {
                    blockingQueues[i % blockingQueues.length].put("" + i);
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
