package javarealworldchallenges.threads.CompleatableFuture;

import javarealworldchallenges.threads.deepdive.ForkJoin.SumRecursiveTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategorizationService {


    private static final Logger LOGGER = Logger.getLogger(CategorizationService.class.getName());

    public static Category categorizeTransaction(Transaction transaction) {
        delay();
        return new Category("Category_" + transaction.getId());
    }

    static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Category {
        private final String category;

        public Category(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "category='" + category + '\'' +
                    '}';
        }
    }

    static class Transaction {
        private String id;
        private String description;

        public Transaction(String id, String description) {
            this.id = id;
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    public static void main(String[] args) {
        //demoNoThreads();
        demoCompletableFuture();
    }

   // 3051 ms - single thread, 1089 parallel
    static void demoNoThreads() {
        long start = System.currentTimeMillis();
        var categories = Stream.of(new Transaction("1", "description 1"),
                new Transaction("2", "description 2"),
                new Transaction("3", "description 3"))
                .parallel()
                .map(CategorizationService::categorizeTransaction)
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        LOGGER.info(() -> "The operation took " + (end - start));
        LOGGER.info(() -> "Categories are: " + categories);
    }

    static void demoCompletableFuture() {
        Executor executor = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        var futureCategories = Stream.of(new Transaction("1", "description 1"),
                new Transaction("2", "description 2"),
                new Transaction("3", "description 3"))
                .map(transaction -> CompletableFuture.supplyAsync(() ->
                    CategorizationService.categorizeTransaction(transaction), executor))
                .collect(Collectors.toList());

        var categories = futureCategories.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);
    }

}
