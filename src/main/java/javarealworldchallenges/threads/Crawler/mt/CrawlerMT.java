package javarealworldchallenges.threads.Crawler.mt;

import javarealworldchallenges.threads.Crawler.st.Crawler;
import javarealworldchallenges.threads.Crawler.util.IUrlFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class CrawlerMT {

    private IUrlFilter urlFilter;
    protected Set<String> crawledUrls = new HashSet<>();
    private ExecutorService crawlService;
    protected LinkedBlockingQueue<String> linksQueue = new LinkedBlockingQueue<>();
    protected CyclicBarrier barrier = new CyclicBarrier(1);

    public CrawlerMT(IUrlFilter filter) {
        this.urlFilter = filter;
    }

    public void addUrl(String url) {
        linksQueue.add(url);
    }

    public void crawl() {
        long startTime = System.currentTimeMillis();

        crawlService = Executors.newCachedThreadPool();

        int count = 0;

        while (!linksQueue.isEmpty()) {
            String nextUrl = null;

            try {
                nextUrl = linksQueue.take();

            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }

            if (nextUrl == null) {
                System.out.println("queue is null here");

            }

            if (!shouldCrawlUrl(nextUrl)) continue;

            this.crawledUrls.add(nextUrl);

            try {
                System.out.println(nextUrl);
                CrawlerJobMT crawlJob = new CrawlerJobMT(this, nextUrl);
                crawlService.submit(crawlJob);

                synchronized (this) {
                    count++;
                }

                if (linksQueue.isEmpty()) {
                    barrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e){
                System.out.println("Error crawling: URL: " + nextUrl);
            }
        }

        crawlService.shutdown();

        try {
            crawlService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long timeRunMs = endTime - startTime;
        System.out.println("URL Crawled in " + timeRunMs);
    }

    private boolean shouldCrawlUrl(String nextUrl) {
        if(this.urlFilter != null && !this.urlFilter.include(nextUrl)){
            return false;
        }
        if(this.crawledUrls.contains(nextUrl)) { return false; }
        if(nextUrl.startsWith("javascript:"))  { return false; }
        if(nextUrl.startsWith("#"))            { return false; }
        if(nextUrl.endsWith(".swf"))           { return false; }
        if(nextUrl.endsWith(".pdf"))           { return false; }
        if(nextUrl.endsWith(".png"))           { return false; }
        if(nextUrl.endsWith(".gif"))           { return false; }
        if(nextUrl.endsWith(".jpg"))           { return false; }
        if(nextUrl.endsWith(".jpeg"))          { return false; }

        return true;
    }




}
