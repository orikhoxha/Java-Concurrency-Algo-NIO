package oliverloeffler;

import java.time.Duration;
import java.util.*;

public class Scheduler {

    private final Clock clock;
    private final SortedSet<Task> tasks;
    private long counter;

    Scheduler() {
        this(new SystemClock());
    }

    Scheduler(Clock clock) {
        this.clock = clock;
        this.tasks = new TreeSet<>(Comparator.comparing((Task t) -> t.time).thenComparing(t -> t.id));
    }

    synchronized void execute(Runnable task) {
        schedule(task, Duration.ZERO);
    }


    synchronized ScheduledTask schedule(Runnable task, Duration duration) {
        Task t = new Task(task, duration, clock.nanoTime() + duration.toNanos(), counter++);
        tasks.add(t);
        return t;
    }

    synchronized List<Runnable> expired() {
        long time = clock.nanoTime();
        List<Runnable> result = new ArrayList<>();
        Iterator<Task> it = tasks.iterator();
        Task item;
        while (it.hasNext() && (item = it.next()).time <= time) {
            result.add(item.task);
            it.remove();
        }
        return result;
    }

    private synchronized void cancel(Task task) {
        tasks.remove(task);
    }

    private synchronized ScheduledTask reschedule(Task task) {
        tasks.remove(task);
        return schedule(task.task, task.duration);
    }

    class Task implements ScheduledTask {

        final Runnable task;
        final Duration duration;
        final long time;
        final long id;

        Task(Runnable task, Duration duration, long time, long id) {
            this.task = task;
            this.duration = duration;
            this.id = id;
            this.time = time;
        }
        @Override
        public void cancel() {
            Scheduler.this.cancel(this);
        }

        @Override
        public ScheduledTask reschedule() {
            return Scheduler.this.reschedule(this);
        }
    }
}
