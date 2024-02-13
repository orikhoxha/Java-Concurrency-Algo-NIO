package chapter13.concurrency.jenkov;

public class SignalCarrier {

    private boolean signalRaised = false;
    private boolean isThreadWaiting = false;

    public void doWait() throws InterruptedException {
        synchronized (this) {
            if (signalRaised) {
                System.out.println(Thread.currentThread().getName() + " signal was already raised");
                this.signalRaised = false;
                return;
            }
            System.out.println(Thread.currentThread().getName() + " calling wait()");
            this.isThreadWaiting = true;
            this.wait();
            this.isThreadWaiting = false;
            System.out.println(Thread.currentThread().getName() + " exited wait()");
        }
    }

    public void doNotify() throws InterruptedException {
        synchronized (this) {
            if (!isThreadWaiting) {
                System.out.println(Thread.currentThread().getName() + " exited notifty()");
                this.signalRaised = true;
            }
            this.notify();
            System.out.println(Thread.currentThread().getName() + " exited notifty()");
        }
    }
}
