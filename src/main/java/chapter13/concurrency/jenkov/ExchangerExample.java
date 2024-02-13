package chapter13.concurrency.jenkov;

import java.util.concurrent.Exchanger;

public class ExchangerExample {


    public static void main(String[] args) throws InterruptedException{
        Exchanger exchanger = new Exchanger();

        ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "A");
        ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "B");

        new Thread(exchangerRunnable1).start();
        new Thread(exchangerRunnable2).start();

        Thread.sleep(4000);
    }
    static class ExchangerRunnable implements Runnable {

        Exchanger exchanger;
        Object object;

        public ExchangerRunnable(Exchanger exchanger, Object obj) {
            this.exchanger = exchanger;
            this.object = obj;
        }

        @Override
        public void run() {
            try {
                Object previous = this.object;

                this.object = this.exchanger.exchange(this.object);
                System.out.println(
                        Thread.currentThread().getName() +
                                " exchanged " + previous + " for " + this.object
                );
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
