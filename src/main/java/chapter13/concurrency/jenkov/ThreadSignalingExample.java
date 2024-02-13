package chapter13.concurrency.jenkov;

public class ThreadSignalingExample {

    public static void main(String[] args) {
        SignalCarrier signalCarrier = new SignalCarrier();

        Thread waiter = new Thread(() -> {
            try {
                signalCarrier.doWait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        Thread notifier = new Thread(() -> {
            try {
                signalCarrier.doNotify();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        notifier.start();
        waiter.start();

    }
}
