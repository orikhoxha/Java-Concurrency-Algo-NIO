package javarealworldchallenges.threads.ThreadPoolWorkStealing_LinkedBlockingQueue;


import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssemblyLine {

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int MAX_PROD_BULBS = 15_000_000;

    private static final Random rnd = new Random();
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();


    private static final Consumer consumer = new Consumer();

    private static long startTime;
    private static ExecutorService consumerService;

    private static Map<String, Integer> threads = new HashMap<>();

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            threads.put(threadName, threads.getOrDefault(threadName, 0) + 1);
            String bulb = queue.poll();
            if (bulb != null) {
                // process the bulb here
                // logger.info(() -> "Packed: " + bulb + " by consumer: "
                //      + Thread.currentThread().getName());
            }

            if (queue.isEmpty()) {
                logger.info(() -> "The consumers team packed all bulbs in "
                        + (System.currentTimeMillis() - startTime) + " ms");
                logger.info("Note: It is possible to see the above message multiple times...");
                logger.info("Nr threads: " + threads.size());

                threads.forEach((k, v) -> {
                    System.out.println("Thread: " + k + " processed tasks count:" + v);
                });

                System.exit(0);
            }
        }

    }

    public static void startAssemblyLine() {
        simulatingProducers();
        startConsumers();
    }

    private static void startConsumers() {
        logger.info(() -> "We have a consumers team of "
                + PROCESSORS + " members ...");


        // 2 seconds
        consumerService = Executors.newWorkStealingPool();

        // 46 seconds
        // consumerService = Executors.newCachedThreadPool();

        // 2 seconds
        //consumerService = Executors.newWorkStealingPool(PROCESSORS);

        // 5 seconds
        //consumerService = Executors.newFixedThreadPool(PROCESSORS);
        int queueSize = queue.size();

        startTime = System.currentTimeMillis();
        for (int i = 0; i < queueSize; i++) {
            consumerService.execute(consumer);
        }

        consumerService.shutdown();

        try {
            consumerService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(AssemblyLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void simulatingProducers() {
        logger.info("Simulating the job of the producers overnight ...");
        logger.info(() -> "The producers checked " + MAX_PROD_BULBS + " bulbs ...");

        for (int i = 0; i < MAX_PROD_BULBS; i++) {
            queue.offer("bulb-" + rnd.nextInt(1000));
        }
    }

}
