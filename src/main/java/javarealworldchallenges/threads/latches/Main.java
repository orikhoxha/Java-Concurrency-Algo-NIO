package javarealworldchallenges.threads.latches;

public class Main {

    public static void main(String[] args) {
        Thread server = new Thread(new ServerInstance());
        server.start();
    }
}
