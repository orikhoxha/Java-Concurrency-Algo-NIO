package javarealworldchallenges.threads.deepdive.ForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class FibonacciRecursiveAction extends RecursiveTask<Integer> {

    private static final Logger logger = Logger.getLogger(FibonacciRecursiveAction.class.getName());

    private static final long THRESHOLD = 5;
    private volatile int nr;

    public FibonacciRecursiveAction(int nr) {
        this.nr = nr;
    }

    @Override
    protected Integer compute() {
        final int n = nr;
        if (n <= THRESHOLD) {
            nr = fibonnaci(n);
            return nr;
        } else {

            /*FibonacciRecursiveAction fibonacciMinusOne = new FibonacciRecursiveAction(n - 1);
            fibonacciMinusOne.fork();

            FibonacciRecursiveAction fibonacciMinusTwo = new FibonacciRecursiveAction(n - 2);

            int leftResult = fibonacciMinusOne.join();
            int rightResult = fibonacciMinusTwo.compute();
            nr = leftResult + rightResult;*/

            nr = ForkJoinTask.invokeAll(createSubtasks(n))
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
            return nr;
        }
    }

    private List<FibonacciRecursiveAction> createSubtasks(int n) {
        List<FibonacciRecursiveAction> subtasks = new ArrayList<>();

        FibonacciRecursiveAction fibonacciMinusOne = new FibonacciRecursiveAction(n - 1);
        FibonacciRecursiveAction fibonacciMinusTwo = new FibonacciRecursiveAction(n - 2);

        subtasks.add(fibonacciMinusOne);
        subtasks.add(fibonacciMinusTwo);
        return subtasks;
    }

    private int fibonnaci(int n) {
        //logger.info(() -> "Number: " + n  + " Thread: " + Thread.currentThread().getName());

        if (n <= 1) {
            return n;
        }

        return fibonnaci(n - 1) + fibonnaci(n - 2);
    }

    public static void main(String[] args) {

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        int noOfProcessors = Runtime.getRuntime().availableProcessors();

        logger.info(() -> "Available processors: " + noOfProcessors);

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        int initialPoolSize = forkJoinPool.getPoolSize();
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        logger.info(() -> "Common Pool parallelism :" + commonPoolParallelism);
        logger.info(() -> "Common Pool size before:" + initialPoolSize);

        long startTime = System.currentTimeMillis();
        FibonacciRecursiveAction fibonacciRecursiveAction = new FibonacciRecursiveAction(12);
        forkJoinPool.invoke(fibonacciRecursiveAction);
        long endTime = System.currentTimeMillis();
        logger.info(() -> "Time taken: " + (double)(endTime - startTime) / 1000);
        logger.info(() -> "Fibonacci: " + fibonacciRecursiveAction.nr);

        int afterPoolSize = forkJoinPool.getPoolSize();
        logger.info(() -> "Common Pool size after  :" + afterPoolSize);
    }

}
