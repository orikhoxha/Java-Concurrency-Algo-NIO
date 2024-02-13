package javarealworldchallenges.threads.CallableAndFuture.InvokeMultipleCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class AssemblyLine {

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int MAX_PROD_BULBS = 100;

    private static final Random rnd = new Random();
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private static ExecutorService consumerService;
    private static final Consumer consumer = new Consumer();

    private static class Consumer implements Callable<String> {

        @Override
        public String call() throws Exception {
            String bulb = queue.poll();
            Thread.sleep(100);

            if (bulb != null) {
                logger.info(() -> "Packed: " + bulb + " by consumer:" + Thread.currentThread().getName() + " hashcode: " + hashCode());
                return bulb;
            }
            return "";
        }
    }

    public static void startAssemblyLine() {
        simulatingProducers();
        startConsumers();
    }

    private static void startConsumers() {
        logger.info(() -> "We have a consumer team of " + PROCESSORS + " members...");

        //consumerService = Executors.newWorkStealingPool();
        //consumerService = Executors.newCachedThreadPool();
        //consumerService = Executors.newWorkStealingPool(PROCESSORS);
        consumerService = Executors.newFixedThreadPool(PROCESSORS);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < queue.size(); i++) {
            tasks.add(consumer);
        }

        logger.info("Submit all tasks and wait for all of them to complete ...");

        long time = System.currentTimeMillis();

        try {
            //List<Future<String>> futures = consumerService.invokeAll(tasks);

            String bulb = consumerService.invokeAny(tasks);
            /*for (Future<String> future : futures) {
                String bulb = future.get();
                logger.info(() -> "Future done: " + bulb);
            }*/

            logger.info(() -> "Future done: " + bulb);
            System.out.println("Time: " + (System.currentTimeMillis() - time));
            consumerService.shutdown();
            consumerService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);

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
