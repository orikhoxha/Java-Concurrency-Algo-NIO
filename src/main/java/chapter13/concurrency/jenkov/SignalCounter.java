package chapter13.concurrency.jenkov;

public class SignalCounter {

    private int signals = 0;
    private int threadsWaiting = 0;

    public void doNotify() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " calling notify()");
            if (this.threadsWaiting == 0) {
                this.signals++;
            }
            this.notify();
            System.out.println(Thread.currentThread().getName() + " exiting notify()");
        }
    }

    public void doWait() throws InterruptedException{
        synchronized (this) {
            if (this.signals > 0) {
                System.out.println(Thread.currentThread().getName() + " " + this.signals + " signals was raised - decrementing signals and return");
                this.signals--;
                return;
            }
            System.out.println(Thread.currentThread().getName() + " calling wait()");
            this.threadsWaiting++;
            this.wait();
            this.threadsWaiting--;
            System.out.println(Thread.currentThread().getName() + " exited wait()");
        }
    }
}
