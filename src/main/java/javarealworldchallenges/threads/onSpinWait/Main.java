package javarealworldchallenges.threads.onSpinWait;

public class Main {

    public static void main(String[] args) throws Exception{
        StartService service = new StartService();
        Thread t = new Thread(service);

        t.start();

        Thread.sleep(5000);
        service.setServiceAvailable(true);
    }
}
