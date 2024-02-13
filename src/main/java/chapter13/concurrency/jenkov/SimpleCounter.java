package chapter13.concurrency.jenkov;

public class SimpleCounter implements Counter{

    private MyLock lock;

    public SimpleCounter(MyLock lock) {
        this.lock = lock;
    }

    private long count = 0;

    @Override
    public void inc() {
        this.lock.lock();
        count++;
        this.lock.unlock();
    }

    @Override
    public long getCount() {
        return this.count;
    }
}
