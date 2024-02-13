package javarealworldchallenges.threads.Semaphores;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        BarberShop bs = new BarberShop(3);

        for (int i = 0; i <= 10; i++) {
            BarbershopCustomer bc = new BarbershopCustomer(bs, i);
            new Thread(bc).start();
        }
    }
}
