package javarealworldchallenges.threads.deepdive.ForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class SumRecursiveTask  extends RecursiveTask<Integer> {

    private static final Logger logger = Logger.getLogger(SumRecursiveTask.class.getName());
    private static final int THRESHOLD = 10;

    //private static AtomicInteger conter = new AtomicInteger(0);

    private final List<Integer> worklist;

    public SumRecursiveTask(List<Integer> worklist) {
        this.worklist = worklist;
    }

    @Override
    protected Integer compute() {
        if (worklist.size() <= 10) {
            return partialSum(worklist);
        }


        int size = worklist.size();

        List<Integer> left = worklist.subList(0, (size + 1) / 2);
        List<Integer> right = worklist.subList((size + 1) / 2, size);

        SumRecursiveTask leftTask = new SumRecursiveTask(left);
        leftTask.fork();

        SumRecursiveTask rightTask = new SumRecursiveTask(right);

        Integer secondTaskResult = rightTask.compute();
        Integer firstTaskResult = leftTask.join();

        return firstTaskResult + secondTaskResult;
       // logger.info(() -> "Count split:" + conter.incrementAndGet());
        /*return ForkJoinTask.invokeAll(createSubtasks())
                .stream()
                .mapToInt(ForkJoinTask::join)
                .sum();*/
    }


    private List<SumRecursiveTask> createSubtasks() {
        List<SumRecursiveTask> subtasks = new ArrayList<>();
        int size = worklist.size();
        List<Integer> worklistleft = worklist.subList(0, (size + 1) / 2);
        List<Integer> worklistRight = worklist.subList((size + 1 ) / 2, size);
        subtasks.add(new SumRecursiveTask(worklistleft));
        subtasks.add(new SumRecursiveTask(worklistRight));
        return subtasks;
    }

    private Integer partialSum(List<Integer> list) {
        int sum = worklist.stream()
                .mapToInt(e -> e)
                .sum();

        logger.info(() -> "Partial sum: " + worklist +  " = " + sum + "\t Thread: " + Thread.currentThread().getName());
        return sum;
    }
}
