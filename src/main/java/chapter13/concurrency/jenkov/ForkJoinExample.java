package chapter13.concurrency.jenkov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        /*MyRecursiveAction myRecursiveAction = new MyRecursiveAction(65);
        forkJoinPool.invoke(myRecursiveAction);*/
        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);
        long result = forkJoinPool.invoke(myRecursiveTask);
        System.out.println("Result: " + result);

    }
}


class MyRecursiveAction extends RecursiveAction {

    private long workLoad;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (this.workLoad > 16) {
            System.out.println("Splitting workload: " + this.workLoad);
            List<MyRecursiveAction> subtasks = new ArrayList<>();

            subtasks.addAll(createSubtasks());
            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }
        } else {
            System.out.println("Doing workload myself" + this.workLoad);
        }
    }
    private List<MyRecursiveAction> createSubtasks () {
        List<MyRecursiveAction> subtasks = new ArrayList<>();
        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);
        return subtasks;
    }
}


class MyRecursiveTask extends RecursiveTask<Long> {

    private long workLoad;

    public MyRecursiveTask(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected Long compute() {
        if (this.workLoad > 16) {
            System.out.println("Splitting workload: " + this.workLoad);

            List<MyRecursiveTask> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            long result = 0;
            for (MyRecursiveTask subtask : subtasks) {
                subtask.fork();
            }

            for (MyRecursiveTask subtask : subtasks) {
                result += subtask.join();
            }
            return result;
        } else {
            System.out.println(Thread.currentThread().getName() + " doing workload: " + this.workLoad);
            return workLoad * 3;
        }
    }

    private List<MyRecursiveTask> createSubtasks() {
        List<MyRecursiveTask> subtasks = new ArrayList<>();
        MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2); // 64 64  32 32  32 32  16  16 16 16 16 16 16
        MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        return subtasks;
    }
}
