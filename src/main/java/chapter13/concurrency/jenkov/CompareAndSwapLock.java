package chapter13.concurrency.jenkov;

import java.util.concurrent.atomic.AtomicBoolean;

public class CompareAndSwapLock implements MyLock {

    private AtomicBoolean locked = new AtomicBoolean(false);

    public void unlock() {
        this.locked.set(false);
    }

    public void lock() {
        while (!this.locked.compareAndSet(false, true)) {
            // busy wait till condition succeeds.
        }
    }
}
