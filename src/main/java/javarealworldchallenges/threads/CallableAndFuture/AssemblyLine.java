package javarealworldchallenges.threads.CallableAndFuture;

import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class AssemblyLine {

    private static final int MAX_PROD_TIME_MS = 5 * 1000;
    private static final int MAX_CONS_TIME_MS = 3 * 1000;
    private static final int TIMEOUT_MS = MAX_PROD_TIME_MS + MAX_CONS_TIME_MS;

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());
    private static final Random rnd = new Random();

    private static volatile boolean runningProducer;
    private static volatile boolean runningConsumer;

    private static ExecutorService producerService;
    private static ExecutorService consumerService;

    private static class Producer implements Callable<String> {

        private final String bulb;

        private Producer(String bulb) {
            this.bulb = bulb;
        }

        @Override
        public String call() throws Exception {
            if (runningProducer) {
                Thread.sleep(rnd.nextInt(MAX_PROD_TIME_MS));

                if (rnd.nextInt(100) < 5) {
                    throw new DefectBulbException("Defect: " + bulb);
                } else {
                    logger.info(() -> "Checked: " + bulb);
                }
                return bulb;
            }
            return "";
        }
    }

    private static class Consumer implements Runnable {
        private final String bulb;

        private Consumer(String bulb) {
            this.bulb = bulb;
        }

        @Override
        public void run() {
            if (runningConsumer) {
                 try {
                     Thread.sleep(rnd.nextInt(MAX_CONS_TIME_MS));
                     logger.info(() -> "Packed: " + bulb);
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
            }
        }
    }

    public static void startAssemblyLine() {
        if (runningProducer || runningConsumer) {
            logger.info("Assembly line is already running...");
            return;
        }

        logger.info("\n\nStarting assembly line ...");

        runningProducer = true;
        producerService = Executors.newSingleThreadExecutor();

        runningConsumer = true;
        consumerService = Executors.newSingleThreadExecutor();

        new Thread(AssemblyLine::automaticSystem).start();
    }

    private static void automaticSystem() {
        while (runningProducer && runningConsumer) {
            String bulb = "bulb-" + rnd.nextInt(1000);
            Producer producer = new Producer(bulb);

            long startTime = System.currentTimeMillis();
            Future<String> bulbFuture = producerService.submit(producer);

            try {
                String checkedBulb = bulbFuture.get(MAX_PROD_TIME_MS + 1000, TimeUnit.MILLISECONDS);
                Consumer consumer = new Consumer(checkedBulb);

                if (runningConsumer) {
                    consumerService.execute(consumer);
                }
            } catch (ExecutionException ex) {
                logger.severe(() -> "Exception: " + ex.getCause());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                logger.severe(() -> "Exception: " + ex);
            } catch (TimeoutException ex) {
                logger.severe("The producer doesn't respect the checking time");
            }
        }
    }

    public static void stopAssemblyLine() {
        logger.info("Stopping assembly line");

        boolean isProducerDown = shutdownProducer();
        boolean isConsumerDown = shutdownConsumer();

        if (!isProducerDown || !isConsumerDown) {
            logger.severe("Something abnormal happened during shutting down the assembly line!");
            System.exit(0);
        }

        logger.info("Assembly line was succesfully stopped");
    }

    private static boolean shutdownProducer() {
        runningProducer = false;
        return shutdownExecutor(producerService);
    }

    private static boolean shutdownConsumer() {
        runningConsumer = false;
        return shutdownExecutor(consumerService);
    }

    private static boolean shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
                return executor.awaitTermination(TIMEOUT_MS, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            logger.severe(() -> "Exception: " + ex);
        }
        return false;

    }
}
