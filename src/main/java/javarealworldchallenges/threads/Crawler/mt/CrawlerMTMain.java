package javarealworldchallenges.threads.Crawler.mt;

import javarealworldchallenges.threads.Crawler.util.SameWebsiteOnlyFilter;

public class CrawlerMTMain {

    public static void main(String[] args) {
        String url = "https://vogella.com/";
        CrawlerMT crawler = new CrawlerMT(new SameWebsiteOnlyFilter(url));
        crawler.addUrl(url);
        crawler.crawl();
    }
}
