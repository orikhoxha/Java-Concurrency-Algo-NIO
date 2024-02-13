package completereference.threads;

public class NewThread  implements Runnable {

    String name;
    Thread t;

    NewThread(String tName) {
        name = tName;
        t = new Thread(this, name);
        System.out.println("New thread: " + t);
    }

    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            System.out.println(name + " Interrupted");
        }
        System.out.println(name + " exiting.");
    }
}


class MultiThreadDemo {
    public static void main(String[] args) {
        NewThread t1 = new NewThread("One");
        NewThread t2 = new NewThread("Two");
        NewThread t3 = new NewThread("Three");

        t1.t.setPriority(10);

        t1.t.start();
        t2.t.start();
        t3.t.start();


        System.out.println("Thread one is alive:  " + t1.t.isAlive());
        System.out.println("Thread two is alive:  " + t2.t.isAlive());
        System.out.println("Thread three is alive:  " + t3.t.isAlive());


        try {
            t1.t.join();
            t2.t.join();
            t3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Completed t3");
        }

        System.out.println("Thread one is alive:  " + t1.t.isAlive());
        System.out.println("Thread two is alive:  " + t2.t.isAlive());
        System.out.println("Thread three is alive:  " + t3.t.isAlive());

       /* try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            System.out.println("Main thread interrupted");
        }*/

        System.out.println("Main thread exiting.");
    }
}
