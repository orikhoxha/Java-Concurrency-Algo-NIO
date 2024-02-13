package chapter13.concurrency.jenkov;

public class MySignal {

    protected boolean hasDataToProcess = false;

    public synchronized boolean hasDataToProcess() {
        return this.hasDataToProcess;
    }

    public synchronized void setHasDataToProcess(boolean hasDataToProcess) {
        this.hasDataToProcess = hasDataToProcess;
    }
}


class MonitorObject{}

class MyWaitNotify {
    MonitorObject monitorObject = new MonitorObject();
    boolean wasSignaled = false;

    public void doWait() {
        synchronized (monitorObject) {
            if (!wasSignaled) {
                try {
                    monitorObject.wait();
                } catch (InterruptedException e) {
                }
            }
            wasSignaled = false;
        }
    }

    public void doNotify() {
        synchronized (monitorObject) {
            wasSignaled = true;
            monitorObject.notify();
        }
    }
}
