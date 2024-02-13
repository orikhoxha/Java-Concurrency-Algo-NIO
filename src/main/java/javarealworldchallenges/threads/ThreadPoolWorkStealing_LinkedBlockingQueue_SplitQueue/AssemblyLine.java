package javarealworldchallenges.threads.ThreadPoolWorkStealing_LinkedBlockingQueue_SplitQueue;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class AssemblyLine {

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int MAX_PROD_BULBS = 15_000_000;
    private static final int CHUNK_BULBS = 1_000_000;

    private static final Random rnd = new Random();
    private static final Queue<BlockingQueue<String>> chunks = new LinkedBlockingQueue<>();

    private static long startTime;
    private static ExecutorService consumerService;

    private static Map<String, Integer> threads = new HashMap<>();

    private static Map<String, String> threadsQueues = new HashMap<>();

    private static class Consumer implements Runnable {
        private static final AtomicInteger countBulbs = new AtomicInteger();
        private final BlockingQueue<String> bulbs;

        public Consumer(BlockingQueue<String> bulbs) {
            this.bulbs = bulbs;
        }


        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            threads.put(threadName, threads.getOrDefault(threadName, 0) + 1);
            threadsQueues.merge(threadName, bulbs.hashCode() + " ", String::concat);

            while (!bulbs.isEmpty()) {
                String bulb = bulbs.poll();
                if (bulb != null) {

                }
                countBulbs.incrementAndGet();
            }

            if (countBulbs.get() == MAX_PROD_BULBS) {
                logger.info(() -> "The consumers team packed all bulbs in "
                        + (System.currentTimeMillis() - startTime) + " ms");
                logger.info("Note: It is possible to see the above message multiple times...");
                threads.forEach((k, v) -> {
                    System.out.println("Thread: " + k + " processed tasks count:" + v);
                });

                threadsQueues.forEach((k, v) -> {
                    System.out.println("Thread: " + k + " processed queues:" + v);
                });
                System.exit(0);
            }

        }
    }

    public static void startAssemblyLine() {
        simulatingProducers();
        startConsumers();
    }


    // 15 consumers -> each handles 1_000_000 tasks
    private static void startConsumers() {
        logger.info(() -> "We have a consumers team of "
                + PROCESSORS + " members ...");

        logger.info(() -> "After split in chunks there are "
                + chunks.size() + " chunks of " + CHUNK_BULBS + " bulbs ...");


        //0.7 ms 4 threads
       // consumerService = Executors.newWorkStealingPool();

        // 0.6 ms 15 threads
        // consumerService = Executors.newCachedThreadPool();


        // 0.7 ms 4 threads
        // consumerService = Executors.newWorkStealingPool(PROCESSORS);

        // 0.7 ms 4 threads
        consumerService = Executors.newFixedThreadPool(PROCESSORS);



        startTime = System.currentTimeMillis();
        while (!chunks.isEmpty()) {
            Consumer consumer = new Consumer(chunks.poll());
            consumerService.execute(consumer);
        }

        consumerService.shutdown();
        try {
            consumerService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.severe(() -> "Exception: " + ex);
        }

    }


    // 15 blocking queues with 1_000_000 tasks
    private static Queue<BlockingQueue<String>> simulatingProducers() {
        logger.info("Simulating the job of the producers overnight ...");
        logger.info(() -> "The producers checked " + MAX_PROD_BULBS + " bulbs ...");


        int counter = 0;
        while (counter < MAX_PROD_BULBS) {
            BlockingQueue chunk = new LinkedBlockingQueue<>(CHUNK_BULBS);
            for (int i = 0; i < CHUNK_BULBS; i++) {
                chunk.offer("bulb-" + rnd.nextInt(1000));
            }

            chunks.offer(chunk);
            counter += CHUNK_BULBS;
         }
        return chunks;
    }
}
