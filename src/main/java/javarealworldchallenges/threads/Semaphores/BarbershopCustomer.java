package javarealworldchallenges.threads.Semaphores;

import java.util.Random;
import java.util.logging.Logger;

public class BarbershopCustomer implements Runnable {

    private static final Logger logger = Logger.getLogger(BarbershopCustomer.class.getName());
    private static final Random rnd = new Random();

    private final BarberShop barbershop;
    private final int customerId;

    public BarbershopCustomer(BarberShop barberShop,int customerId) {
        this.barbershop = barberShop;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        boolean acquired = barbershop.acquireSeat(this.customerId);

        if (acquired) {
            try {
                Thread.sleep(rnd.nextInt(10 * 1000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                barbershop.releaseSeat(customerId);
            }
        } else {
            Thread.currentThread().interrupt();
        }
    }
}
