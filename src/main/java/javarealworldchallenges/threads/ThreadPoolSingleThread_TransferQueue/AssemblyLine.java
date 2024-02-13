package javarealworldchallenges.threads.ThreadPoolSingleThread_TransferQueue;

import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

public final class AssemblyLine {

    private AssemblyLine() {
        throw new AssertionError("There is a single assembly line!");
    }

    private static final int MAX_PROD_TIME_MS = 5 * 1000;
    private static final int MAX_CONS_TIME_MS = 7 * 1000;

    private static final int TIMEOUT_MS = MAX_CONS_TIME_MS + 1000;

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());

    private static final Random rnd = new Random();
    private static final TransferQueue<String> queue = new LinkedTransferQueue<>();

    private static final Producer producer = new Producer();
    private static final Consumer consumer = new Consumer();


    private static volatile boolean runningProducer;
    private static volatile boolean runningConsumer;

    private static ExecutorService producerService;
    private static ExecutorService consumerService;

    private static class Producer implements Runnable {

        @Override
        public void run() {
            while (runningProducer) {
                try {
                    String bulb = "bulb-" + rnd.nextInt(1000);
                    Thread.sleep(rnd.nextInt(MAX_PROD_TIME_MS));
                    boolean transfered = queue.tryTransfer(bulb, TIMEOUT_MS, TimeUnit.MILLISECONDS);

                    if (transfered) {
                        logger.info(() -> "Checked: " + bulb);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    logger.severe(() -> "Exception: " + ex);
                    break;
                }
            }
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            while (runningConsumer) {
                try {
                    String bulb = queue.poll(MAX_PROD_TIME_MS, TimeUnit.MILLISECONDS);

                    if (bulb != null) {
                        Thread.sleep(rnd.nextInt(MAX_CONS_TIME_MS));
                        logger.info(() -> "Checked: " + bulb);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    logger.severe(() -> "Exception: " + ex);
                    break;
                }
            }
        }
    }

    public static void startAssemblyLine() {
        if (runningProducer || runningConsumer) {
            logger.info("Assembly line is already running ...");
            return;
        }

        logger.info("\n\nStarting assembly line ...");
        logger.info(() -> "Remaining bulbs from previous run: \n" + queue + "\n\n");

        runningProducer = true;
        producerService = Executors.newSingleThreadExecutor();
        producerService.execute(producer);

        runningConsumer = true;
        consumerService = Executors.newSingleThreadExecutor();
        consumerService.execute(consumer);
    }

    public static void stopAssemblyLine() {
        logger.info("Stopping assembly line ...");

        boolean isProducerDown = shutdownProducer();
        boolean isConsumerDown = shutdownConsumer();

        if (!isProducerDown || !isConsumerDown) {
            logger.severe("Something abnormal happened during shutdown");
            System.exit(0);
        }

        logger.info("Assembling line was successfully stopped!");
    }

    private static boolean shutdownProducer() {
        runningProducer = false;
        return shutdownExecutor(producerService);
    }

    private static boolean shutdownConsumer() {
        runningProducer = false;
        return shutdownExecutor(consumerService);
    }

    private static boolean shutdownExecutor(ExecutorService executor) {
        executor.shutdown(); // stop accepting new task or non submitted ones.
        try {
            if (!executor.awaitTermination(TIMEOUT_MS * 2, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow(); // shutdown immediately
                return executor.awaitTermination(TIMEOUT_MS * 2, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            logger.severe(() -> "Exception: " + ex);
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        AssemblyLine.startAssemblyLine();
        Thread.sleep(10 * 1000);
        AssemblyLine.stopAssemblyLine();

        Thread.sleep(2000);

        System.out.println("Starting assembly line again ...");

        AssemblyLine.startAssemblyLine();
        Thread.sleep(10 * 1000);
        AssemblyLine.stopAssemblyLine();

    }

}
