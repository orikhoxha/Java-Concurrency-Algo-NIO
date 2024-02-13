package completereference.threads;

public class ThreadGroupExample {

    public static void main(String[] args) {
        ThreadGroup ga = new ThreadGroup("Group A");
        ThreadGroup gb = new ThreadGroup("Group B");

        NewThreadd ob1 = new NewThreadd("One", ga);
        NewThreadd ob2 = new NewThreadd("Two", ga);
        NewThreadd ob3 = new NewThreadd("Three", gb);
        NewThreadd ob4 = new NewThreadd("Four", gb);

        ob1.start();
        ob2.start();
        ob3.start();
        ob4.start();

        System.out.println("Here is output from list");
        ga.list();
        gb.list();
        System.out.println();

        System.out.println("Suspending Group A");
        Thread[] tga = new Thread[ga.activeCount()];
        ga.enumerate(tga);
        for (int i = 0 ; i < tga.length; i++ ) {
            ((NewThreadd) tga[i]).mySuspend();
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {

        }

        System.out.println("Resuming Group A");
        for (int i = 0; i < tga.length; i++) {
            ((NewThreadd) tga[i]).myResume();
        }

        try {
            ob1.join();
            ob2.join();
            ob3.join();
            ob4.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class NewThreadd extends Thread {

    boolean suspendFlag;

    NewThreadd(String threadName, ThreadGroup tgOb) {
        super(tgOb, threadName);
        System.out.println("New thread: " + this);
    }
    @Override
    public void run() {
        try {
           for (int i = 5; i > 0; i--) {
               System.out.println(getName() + ": " + i);
               Thread.sleep(1000);
               synchronized (this) {
                   while (suspendFlag) wait();
               }
           }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    synchronized void  mySuspend() {
        suspendFlag = true;
    }

    synchronized void myResume() {
        suspendFlag = false;
        notify();
    }
}
