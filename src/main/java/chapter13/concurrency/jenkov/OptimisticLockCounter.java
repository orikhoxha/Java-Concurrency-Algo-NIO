package chapter13.concurrency.jenkov;

import java.util.concurrent.atomic.AtomicLong;

public class OptimisticLockCounter implements Counter{

    private AtomicLong count = new AtomicLong();

    public void inc() {
        boolean incSuccesfull = false;

        while (!incSuccesfull) {
            long value = this.count.get();
            long newValue = value + 1;

            incSuccesfull = this.count.compareAndSet(value, newValue);
        }
    }

    @Override
    public long getCount() {
        return this.count.get();
    }
}
