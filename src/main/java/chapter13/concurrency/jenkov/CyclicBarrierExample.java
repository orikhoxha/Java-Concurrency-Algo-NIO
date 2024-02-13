package chapter13.concurrency.jenkov;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {
        Runnable barrier1Action = () -> System.out.println("BarrierAction 1 executed");
        Runnable barrier2Action = () -> System.out.println("BarrierAction 2 executed");

        CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
        CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

        CyclicBarrierRunnable cyclicBarrierRunnable1 = new CyclicBarrierRunnable(barrier1, barrier2);
        CyclicBarrierRunnable cyclicBarrierRunnable2 = new CyclicBarrierRunnable(barrier1, barrier2);

        new Thread(cyclicBarrierRunnable1).start();
        new Thread(cyclicBarrierRunnable2).start();
    }


    static class CyclicBarrierRunnable implements Runnable {
        CyclicBarrier barrier1;
        CyclicBarrier barrier2;

        public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2) {
            this.barrier1 = barrier1;
            this.barrier2 = barrier2;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
                barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
                barrier2.await();

                System.out.println(Thread.currentThread().getName() + " dune!");

            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
    }
}
