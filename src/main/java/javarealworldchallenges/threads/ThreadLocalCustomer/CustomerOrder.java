package javarealworldchallenges.threads.ThreadLocalCustomer;

import java.util.Random;
import java.util.logging.Logger;

public class CustomerOrder implements Runnable {

    private static final Logger logger = Logger.getLogger(CustomerOrder.class.getName());
    private static final Random rnd = new Random();

    private static final ThreadLocal<Order> customerOrder = new ThreadLocal<>();

    private final int customerId;

    public CustomerOrder(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public void run() {
        logger.info(() -> "Given customer id: " + customerId
                + " | " + customerOrder.get()
                + " | " + Thread.currentThread().getName());

        customerOrder.set(new Order(customerId));

        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.severe(() -> "Exception: " + ex);
        }

        logger.info(() -> "Given customer id: " + customerId
                + " | " + customerOrder.get()
                + " | " + Thread.currentThread().getName());

        customerOrder.remove();

    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        CustomerOrder co1 = new CustomerOrder(1);
        CustomerOrder co2 = new CustomerOrder(2);
        CustomerOrder co3 = new CustomerOrder(3);

        new Thread(co1).start();
        new Thread(co2).start();
        new Thread(co3).start();
    }
}
