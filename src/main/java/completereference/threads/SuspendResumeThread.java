package completereference.threads;

public class SuspendResumeThread {


    public static void main(String[] args) {
        NewThread ob1 = new NewThread("One");
        NewThread ob2 = new NewThread("Two");

        ob1.t.start();
        ob2.t.start();

        try {
            Thread.sleep(1000);
            ob1.mySuspend();
            System.out.println("Suspending thread One");
            Thread.sleep(1000);
            ob1.myResume();
            System.out.println("Resuming thread One");
            ob2.mySuspend();
            System.out.println("Suspending thread Two");
            Thread.sleep(1000);
            ob2.myResume();
            System.out.println("Resuming thread Two");
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        try {
            System.out.println("Waiting for threads to finish");
            ob1.t.join();
            ob2.t.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread exiting.");
    }
    static class NewThread implements Runnable {
        String name;
        Thread t;
        boolean suspendFlag;

        public NewThread(String name) {
            this.name = name;
            t = new Thread(this, name);
            System.out.println("New thread: " + t);
            suspendFlag = false;
        }

        @Override
        public void run() {
            try {
                for (int i = 15; i > 0; i--) {
                    System.out.println(name + ": " + i);
                    Thread.sleep(200);
                    synchronized (this) {
                        while (suspendFlag) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(name + " interrupted");
            }
            System.out.println(name + " exiting.");
        }

        synchronized void mySuspend() {
            suspendFlag = true;
        }

        synchronized void myResume() {
            suspendFlag = false;
            notify();
        }
    }
}




