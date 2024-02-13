package javarealworldchallenges.threads.Crawler.st;

import javarealworldchallenges.threads.Crawler.util.SameWebsiteOnlyFilter;

public class CrawlerMain {

    public static void main(String[] args) {

        Crawler crawler = new Crawler();
        String url = "https://www.vogella.com/";

        crawler.setUrlFilter(new SameWebsiteOnlyFilter(url));
        crawler.setPageProcessor(null);

        crawler.addUrl(url);

        crawler.crawl();
    }
}
