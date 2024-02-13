package javarealworldchallenges.threads.Crawler.mt;

import org.jsoup.nodes.Document;

public interface IPageProcessor {

    void process(Document document);
}
