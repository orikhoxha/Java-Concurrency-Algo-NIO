package javarealworldchallenges.threads.onSpinWait;

public class StartService implements Runnable {

    private volatile boolean serviceAvailable;

    @Override
    public void run() {
        System.out.println("Wait for service to be available");
        while (!serviceAvailable) Thread.onSpinWait();

        serviceRun();
    }

    private void serviceRun() {
        System.out.println("Service is running");
    }

    public void setServiceAvailable(boolean serviceAvailable) {
        this.serviceAvailable = serviceAvailable;
    }
}
