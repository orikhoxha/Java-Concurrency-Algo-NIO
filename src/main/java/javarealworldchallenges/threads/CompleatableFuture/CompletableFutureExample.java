package javarealworldchallenges.threads.CompleatableFuture;

import javarealworldchallenges.threads.deepdive.ForkJoin.SumRecursiveTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class CompletableFutureExample {

    private static final Logger LOGGER = Logger.getLogger(SumRecursiveTask.class.getName());

    public static void main(String[] args) throws Exception{
        //printOrder(); print in async returns void
        //fetchOrderSummary(); return string in async
        //fetchOrderSummaryExecutor();
        //fetchInvoiceTotalSign();
        //fetchAndPrintOrder();
       // fetchOrderTotalException();
        //fetchInvoiceTotalSignException();
        //testThenCombine();
        //downloadAllInvoices();
        customerRaffleWin();
    }

    static void printOrder() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cfPrintOrder = CompletableFuture.runAsync(() -> {
            LOGGER.info(() -> "Order is prinnted by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        cfPrintOrder.get();
        LOGGER.info("Customer order was printed");
    }

    static void fetchOrderSummary() throws Exception{
        CompletableFuture<String> cfOrderSummary = CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Fetch order summary by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return "Order Summary #93443";
        });
        String summary = cfOrderSummary.get();
        LOGGER.info(() -> "Order summary: " + summary);
    }

    static void fetchOrderSummaryExecutor() throws Exception{
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<String> cfOrderSummary = CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Fetch order summary by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Order Summary #93443";
        }, executor);

        String summary = cfOrderSummary.get();
        LOGGER.info(() -> "Order summary: " + summary);
        executor.shutdown();
    }

    static void fetchInvoiceTotalSign() throws Exception{
        CompletableFuture<String> cfFetchInvoice = CompletableFuture.supplyAsync(() -> {
           LOGGER.info(() -> "Fetch invoice by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Invoice #3344";
        });

        CompletableFuture<String> ofTotalSign = cfFetchInvoice
                .thenApply(o -> o + "Total: $145")
                .thenApply(o -> o + " Signed");

        String result = ofTotalSign.get();
        LOGGER.info(() -> "Invoice: " + result);
    }

    static void fetchAndPrintOrder() throws Exception{
        CompletableFuture<String> cfFetchOrder = CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Fetch order by: " + Thread.currentThread().getName());
            return "Order #1024";
        });

        CompletableFuture<Void> cfPrintOrder = cfFetchOrder.thenAccept(o -> {
            LOGGER.info(() -> "Printing order " + o + " by: " + Thread.currentThread().getName());
        });

        cfPrintOrder.get();
        LOGGER.info("Order was fetched and printed");
    }

    static void deliverOrderNotifyCustomer() throws Exception {
        CompletableFuture<Void> cfDeliverOrder = CompletableFuture.runAsync(() -> {
            LOGGER.info(() -> "Order was delivered by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            LOGGER.info(() -> "Dear customer, your order has beed delivered today by: " + Thread.currentThread().getName());
        });

        cfDeliverOrder.get();
        LOGGER.info(() -> "Order was delivered and customer was notified");
    }

    static void fetchOrderTotalException() throws Exception{
        CompletableFuture<Integer> ofTotalOrder = CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Compute total: " + Thread.currentThread().getName());
            int surrogate = new Random().nextInt(1000);
            if (surrogate < 500) {
                throw new IllegalStateException("Invoice service is not responding");
            }
            return 1000;
        }).exceptionally(ex -> {
            LOGGER.severe(() -> "Exception: " + ex + " Thread: " + Thread.currentThread().getName());
            return 0;
        });

        int result =ofTotalOrder.get();
        LOGGER.info(() -> "Total: " + result);
    }

    static void fetchInvoiceTotalSignException() throws Exception{
        CompletableFuture<String> offFetchInvoice = CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Fetch invoice by: " + Thread.currentThread().getName());
            int surrogate = new Random().nextInt(1000);
            if (surrogate < 500) {
                throw new IllegalStateException("Invoice service is not responding");
            }
            return "Invoice #3344";
        }).thenApply(o -> {
            LOGGER.info(() -> "Compute total by: " + Thread.currentThread().getName());
            int surrogate = new Random().nextInt(1000);
            if (surrogate < 500) {
                throw new IllegalStateException("Total service is not responding");
            }
            return o + "Total: $145";
        }).thenApply(o -> {
            LOGGER.info(() -> "Sign invoice by: "
                    + Thread.currentThread().getName());
            int surrogate = new Random().nextInt(1000);
            if (surrogate < 500) {
                throw new IllegalStateException(
                        "Signing service is not responding");
            }
            return o + " Signed";
        }).exceptionallyAsync(ex -> {
            LOGGER.severe(() -> "Exception: " + ex
                    + " Thread: " + Thread.currentThread().getName());
            return "[No-Invoice-Exception]";
        });

        String result = offFetchInvoice.get();
        LOGGER.info(() -> "Result: " + result);
    }

    static void exceptioalFetchPrinterIp() {
        CompletableFuture<String> cfServicePrinterIp = CompletableFuture.supplyAsync(() -> {
            int surrogate = new Random().nextInt(1000);
            if (surrogate < 500) {
                throw new IllegalStateException("Printing service is not responding");
            }
            return "192.168.1.0";
        });

        CompletableFuture<String> cfBackupPrinterIp = CompletableFuture.supplyAsync(() -> {
            return "192.192.192.192";
        });

        CompletableFuture<Void> printInvoice = cfServicePrinterIp.exceptionallyCompose(th -> {
            LOGGER.severe(() -> "Exception: " + th + " Thread: " + Thread.currentThread().getName());
            return cfBackupPrinterIp;
        }).thenAccept((ip) -> LOGGER.info(() -> "Printing at: " + ip));
    }


    static CompletableFuture<Integer> taxes() {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            int result = new Random().nextInt(100);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete(result);
        });
        return completableFuture;
    }

    static CompletableFuture<String> fetchOrder(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Order of " + customerId);
    }

    static CompletableFuture<Integer> computeTotal(String order) {
        return CompletableFuture.supplyAsync(() -> order.length() + new Random().nextInt(100));
    }

    static void printOrder2() throws Exception{
        CompletableFuture<Integer> cfTotal = fetchOrder("!23").thenCompose(o -> computeTotal(o));
        int total = cfTotal.get();
    }

    static CompletableFuture<Integer> computeTotal2(String order) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("Thread: " + Thread.currentThread().getName());
            return order.length() + new Random().nextInt(1000);
        });
    }

    private static CompletableFuture<String> packProducts(String order) {
        return CompletableFuture.supplyAsync(() ->  {
            LOGGER.info("Thread: " + Thread.currentThread().getName());
            return " Order: " + order + " | Product 1, Product 2, Product 3, ... ";
        });
    }

    static void testThenCombine() throws Exception{
        CompletableFuture<String> cfParcel = computeTotal2("#332")
                .thenCombine(packProducts("#332"), (total, products) -> "Parcel-[" + products + " Invoice: $" + total + "]");

        String parcel = cfParcel.get();
        LOGGER.info(() -> "Delivering: " + parcel);
    }

    static CompletableFuture<String> downloadInvoices(String invoice) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info(() -> "Thread: " + Thread.currentThread().getName() + " Downloading invoice: " + invoice);
            return "Downloaded invoice: " + invoice;
        });
    }

    static void downloadAllInvoices() throws Exception{
        List<String> invoices = Arrays.asList("#2334", "#122", "#55", "#222","#444");
        CompletableFuture<String> [] cfInvoices = invoices.stream()
                .map(CompletableFutureExample::downloadInvoices)
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> cfDownloaded = CompletableFuture.allOf(cfInvoices);
        List<String> result = cfDownloaded.thenApply(o -> {
            List<String> downloaded = new ArrayList<>();
            for (CompletableFuture<String> cfInvoice : cfInvoices) {
                downloaded.add(cfInvoice.join());
            }
            return downloaded;
        }).get();

        System.out.println("List: " + result);
    }

    static CompletableFuture<String> raffle(String customerId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return customerId;
        });
    }

    static void customerRaffleWin() throws Exception{
        List<String> customers = Arrays.asList(
                "#1", "#4", "#2", "#7", "#6", "#5"
        );

        CompletableFuture<String> [] cfCustomers = customers.stream()
                .map(c -> raffle(c))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Object> cfWinner = CompletableFuture.anyOf(cfCustomers);
        Object winner = cfWinner.get();
        LOGGER.info("Winner: " + winner);
    }
}
