package javarealworldchallenges.threads.CallableAndFuture.InvokeMultipleCallableDoNotWaitAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public final class AssemblyLine {

    private AssemblyLine() {
        throw new AssertionError("There is a single assembly line!");
    }

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int MAX_PROD_BULBS = 100;

    private static final Random rnd = new Random();
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private static ExecutorService consumerService;

    private static final Consumer consumer = new Consumer();

    private static class Consumer implements Callable<String> {

        AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public String call() throws Exception {
            String bulb = queue.poll();
            Thread.sleep(1000);

            if (bulb != null) {
                logger.info(() -> "Packed: " + bulb + " by consumer: "
                        + Thread.currentThread().getName() + " Order: " + atomicInteger.getAndIncrement());

                return bulb;
            }
            return "";
        }
    }

    public static void startAssemblyLine() throws ExecutionException, InterruptedException {
        simulatingProducers();
        startConsumers();
    }

    private static void startConsumers() throws ExecutionException, InterruptedException {
        logger.info(() -> "We have a consumers team of "
                + PROCESSORS + " members ...");

        consumerService = Executors.newWorkStealingPool();
        // consumerService = Executors.newSingleThreadExecutor();
        // consumerService = Executors.newWorkStealingPool(PROCESSORS);
        // consumerService = Executors.newFixedThreadPool(PROCESSORS);

        int queueSize = queue.size();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < queueSize; i++) {
            Future<String> f = consumerService.submit(consumer);
            futures.add(f);
        }
 // 308 910 139 82
        logger.info(() -> "Array size" + futures.size());

        try {
            for (Future<String> future: futures) {
                String bulb = future.get();
                logger.info(() -> "Future done: " + bulb);
            }

            futures.forEach(f -> {
                try {
                    System.out.print(f.get() + ", ");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.severe(() -> "Exception: " + ex);
        } catch (ExecutionException ex) {
            logger.severe(() -> "Exception: " + ex.getCause());
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
