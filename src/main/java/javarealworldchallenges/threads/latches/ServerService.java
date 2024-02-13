package javarealworldchallenges.threads.latches;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class ServerService implements Runnable {

    private static final Logger logger = Logger.getLogger(ServerService.class.getName());
    private final CountDownLatch latch;
    private final Random random = new Random();
    private final String serviceName;

    public ServerService(CountDownLatch latch, String serviceName) {
        this.latch = latch;
        this.serviceName = serviceName;
    }
    @Override
    public void run() {
        int startingIn = random.nextInt(10) * 100;

        try {
            logger.info(() -> "Starting service " + serviceName + " ...");
            Thread.sleep(startingIn);

            logger.info(() -> "Service '" + serviceName
                    + "' has successfully started in "
                    + startingIn / 1000 + " seconds");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            latch.countDown();
            logger.info(() -> "Service " + serviceName + " running ....");
        }
    }
}
