package javarealworldchallenges.threads.ThreadLocal;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        ThreadSafeStringBuilder threadSafe = new ThreadSafeStringBuilder();

        for (int i = 0; i < 3; i++) {
            new Thread(threadSafe, "thread-" + i).start();
        }
    }
}
