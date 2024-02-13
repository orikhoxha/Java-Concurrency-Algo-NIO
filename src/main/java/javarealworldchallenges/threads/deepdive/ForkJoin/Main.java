package javarealworldchallenges.threads.deepdive.ForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        int noOfProcessors = Runtime.getRuntime().availableProcessors();
        logger.info(() -> "Available processors: " + noOfProcessors);

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        int initialPoolSize = forkJoinPool.getPoolSize();
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        logger.info(() -> "Common Pool paralelism :" + commonPoolParallelism);
        logger.info(() -> "Common Pool size before:" + initialPoolSize);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add( i);
        }

        SumRecursiveTask sumRecursiveTask = new SumRecursiveTask(list);
        Integer sumAll = forkJoinPool.invoke(sumRecursiveTask);
        logger.info(() -> "Final sum: " + sumAll);

        int afterPoolSize = forkJoinPool.getPoolSize();
        logger.info(() -> "Common pool size after : " + afterPoolSize);

    }
}
