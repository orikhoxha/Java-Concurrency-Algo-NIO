package chapter13.concurrency;

import java.util.concurrent.*;

public class ScheduledExecutorServiceExample {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable task1 = () -> System.out.println("Hello zoo");
        Callable<String> task2 = () -> "Monkey";
        ScheduledFuture<?> r1 = service.schedule(task1, 10, TimeUnit.SECONDS);
        ScheduledFuture<?> r2 = service.schedule(task2, 30, TimeUnit.SECONDS);

        System.out.println(r2.get());

        service.shutdown();

    }
}
