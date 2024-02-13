package completereference.threads;

public class Synchronization {


    public static void main(String[] args) {
        CallMe target =  new CallMe();
        Caller c1 = new Caller(target, "Hello");
        Caller c2 = new Caller(target, "Synchronized");
        Caller c3 = new Caller(target, "World");
        CallerTwo c4 = new CallerTwo(target, "CallerTwo");

        c1.t.start();
        c2.t.start();
        c3.t.start();
        c4.t.start();


        try {
            c1.t.join();
            c2.t.join();
            c3.t.join();
            c4.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


class CallMe {
     void call(String msg) {
        System.out.print("[" + msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("InterrupteD");
        }
        System.out.print("]");
    }

    void callTwo(String msg) {
         System.out.println(msg);
    }
}

class Caller implements Runnable {
    String msg;
    final CallMe target;
    Thread t;

    public Caller(CallMe targ, String s) {
        target = targ;
        msg = s;
        t = new Thread(this);
    }

    @Override
    public void run() {
        synchronized (target) {
            try {
                target.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            target.call(msg);
        }
    }
}

class CallerTwo implements Runnable {
    String msg;
    final CallMe target;
    Thread t;

    public CallerTwo(CallMe targ, String s) {
        target = targ;
        msg = s;
        t = new Thread(this);
    }

    @Override
    public void run() {
        synchronized (target) {
            target.callTwo(msg);
            target.notifyAll();
        }
    }
}
