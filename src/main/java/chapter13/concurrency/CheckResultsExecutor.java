package chapter13.concurrency;

import java.util.concurrent.*;

public class CheckResultsExecutor {

    private static int counter = 0;
    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            Future<?> result = service.submit(() -> {
                for (int i = 0; i < 1_000_000; i++) counter++;
            });
            result.get(10, TimeUnit.SECONDS);
            System.out.println("Reached!");
        } catch (TimeoutException ex) {
            System.out.println("Not reached in time!");

        } finally {
            service.shutdown();
        }

        System.out.println("program:");
    }
}
