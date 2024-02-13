package chapter13.concurrency.jenkov;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(3);

        Producer producer = new Producer(blockingQueue);

        Consumer consumer = new Consumer(blockingQueue);
        Consumer consumer1 = new Consumer(blockingQueue);

        Thread producerThread = new Thread(producer);
        Thread consumerThreaddd = new Thread(consumer);
        Thread consumer1Thread = new Thread(consumer1);

        producerThread.start();
        consumerThreaddd.start();
        consumer1Thread.start();

    }
}
