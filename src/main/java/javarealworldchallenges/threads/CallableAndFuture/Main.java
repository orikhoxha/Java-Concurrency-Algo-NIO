package javarealworldchallenges.threads.CallableAndFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(AssemblyLine.class.getName());


    public static void main(String[] args) throws InterruptedException{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        //AssemblyLine.startAssemblyLine();

        new Thread(Main::startFuture).start();
        Thread.sleep(10 * 1000);
        //AssemblyLine.stopAssemblyLine();
    }


    static void startFuture() {
        logger.info("Starting task...");
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newSingleThreadExecutor();


        Future<String> future = executorService.submit(() -> {
            Thread.sleep(3000);
            return "Task completed";
        });

        while (!future.isDone()) {
            System.out.println("Task is in progress ...");
            try {
                Thread.sleep(100);

                long elapsedTime = (System.currentTimeMillis() - startTime);
                if (elapsedTime > 1000) {
                    future.cancel(true);
                }
            } catch (InterruptedException ex) {
                logger.info(() -> "Task interrupted: " + ex.getCause());
            }
        }

        System.out.println("Task was cancelled: " + future.isCancelled() + "\nTask is done: " + future.isDone());
    }
}
