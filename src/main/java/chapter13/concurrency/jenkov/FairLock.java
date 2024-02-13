package chapter13.concurrency.jenkov;

import java.util.ArrayList;
import java.util.List;

public class FairLock {

    private boolean isLocked;
    private Thread lockingThread;
    private List<QueueObject> waitingThreads = new ArrayList<>();


    public void lock() throws InterruptedException {
        QueueObject queueObject = new QueueObject();  // t1, t2, t3
        boolean isLockedForThisThread = true;
        synchronized (this) {
            waitingThreads.add(queueObject);  // t1, t2, t3
        }

        while (isLockedForThisThread) {
            synchronized (this) { // t2, t3, t1
                isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
                if (!isLockedForThisThread) {
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread(); // t1
                    return;
                }
            }
            try {
                queueObject.doWait();
            } catch (InterruptedException ex) {
                synchronized (this) { waitingThreads.remove(queueObject);}
                throw ex;
            }
        }
    }

    public synchronized void unlock() {
        if (this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException("Calling thread has not locked this thread");
        }
        isLocked = false;
        lockingThread = null;
        if (waitingThreads.size() > 0) {
            waitingThreads.get(0).doNotify();
        }
    }
}

class QueueObject {
    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        while (!isNotified) {
            this.wait();
        }
        this.isNotified = false;
    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}



class SharedObj {

    int counter = 0;

    public void addCount() {

        counter++;
    }



}

class MyRunnablee implements Runnable {

    SharedObj sharedObj;
    FairLock fairLock;

    MyRunnablee(SharedObj sharedObj, FairLock fairLock) {
        this.fairLock = fairLock;
        this.sharedObj = sharedObj;
    }

    @Override
    public void run() {

        try {
            fairLock.lock();
            sharedObj.addCount();
            fairLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TestFairLock {
    public static void main(String[] args) {

        SharedObj obj = new SharedObj();
        FairLock fairLock = new FairLock();

        MyRunnablee myRunnablee = new MyRunnablee(obj, fairLock);


        Thread t1 = new Thread(myRunnablee);
        Thread t2= new Thread(myRunnablee);

        Thread t3 = new Thread(myRunnablee);
        Thread t4 = new Thread(myRunnablee);

        Thread t5 = new Thread(myRunnablee);
        Thread t6 = new Thread(myRunnablee);


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();


        try {
            Thread.sleep(10000);
            System.out.println(obj.counter);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
