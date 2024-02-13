package javarealworldchallenges.threads.Crawler.st;

import javarealworldchallenges.threads.Crawler.util.IUrlFilter;
import javarealworldchallenges.threads.Crawler.util.URLNormalizer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crawler {

    protected IUrlFilter urlFilter;

    protected IPageProcessor pageProcessor;

    protected List<String> urlsToCrawl = new ArrayList<>();
    protected Set<String> crawledUrls = new HashSet<>();

    public Crawler() {}

    public void setUrlFilter(IUrlFilter urlFilter) {
        this.urlFilter = urlFilter;
    }

    public void setPageProcessor(IPageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    public void addUrl(String url) {
        this.urlsToCrawl.add(url);
    }

    public void crawl() {

        long startTime = System.currentTimeMillis();

        while (urlsToCrawl.size() > 0) {
            String nextUrL = this.urlsToCrawl.remove(0);

            if (!shouldCrawlUrl(nextUrL)) continue;

            this.crawledUrls.add(nextUrL);

            try {
                System.out.println(nextUrL);
                CrawlJob crawlJob = new CrawlJob(nextUrL);

                // pageProcessor impl
                crawlJob.addPageProcessor((url, doc) -> {
                    Elements elements = doc.select("a[href]");

                    for (Element element : elements) {
                        String linkUrl = element.attr("href");
                        String normalizedUrl = URLNormalizer.normalize(linkUrl, url);
                        addUrl(normalizedUrl);
                    }
                });

                crawlJob.crawl();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("URL's crawled:" + this.crawledUrls.size() + " in " + totalTime + " ms (avg: " + totalTime / this.crawledUrls.size());

     }

    private boolean shouldCrawlUrl(String nextUrl) {
        if (this.urlFilter != null && !this.urlFilter.include(nextUrl)) {
            return false;
        }

        if (this.crawledUrls.contains(nextUrl)) return false;
        if (nextUrl.startsWith("javascript:"))  { return false; }
        if (nextUrl.contains("mailto:"))        { return false; }
        if (nextUrl.startsWith("#"))            { return false; }
        if (nextUrl.endsWith(".swf"))           { return false; }
        if (nextUrl.endsWith(".pdf"))           { return false; }
        if (nextUrl.endsWith(".png"))           { return false; }
        if (nextUrl.endsWith(".gif"))           { return false; }
        if (nextUrl.endsWith(".jpg"))           { return false; }
        if (nextUrl.endsWith(".jpeg"))          { return false; }

        return true;
    }
}
