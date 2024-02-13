package javarealworldchallenges.threads.Semaphores;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BarberShop {

    private static final Logger logger = Logger.getLogger(BarbershopCustomer.class.getName());
    private static final Random rnd = new Random();
    private Semaphore seats;

    public BarberShop(int seatsCount) {
        this.seats = new Semaphore(seatsCount, true);
    }

    public boolean acquireSeat(int customerId) {
        logger.info(() -> "Customer #" + customerId + " is trying to get a seat");

        try {
            boolean acquired = seats.tryAcquire(5 * 1000, TimeUnit.MILLISECONDS);

            if (!acquired) {
                 logger.info(() -> "Customer #" + customerId + " has left the barbershop");
                 return false;
            }

            logger.info(() -> "Customer #" + customerId + " got a seat");
            return true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public void releaseSeat(int customerId) {
        logger.info(() -> "Customer #" + customerId + " has released a seat");
        seats.release();
    }

}
