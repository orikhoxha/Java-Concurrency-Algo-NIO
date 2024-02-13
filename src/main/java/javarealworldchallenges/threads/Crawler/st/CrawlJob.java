package javarealworldchallenges.threads.Crawler.st;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CrawlJob {

    protected String urlToCrawl;

    protected IPageProcessor pageProcessor;

    public CrawlJob(String urlToCrawl) {
        this.urlToCrawl = urlToCrawl;
    }

    public void addPageProcessor(IPageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    public void crawl() throws IOException{
        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection;

        try {
            urlConnection = url.openConnection();

            try (InputStream input = urlConnection.getInputStream()) {

                Document doc = Jsoup.parse(input, "UTF-8", "");
                pageProcessor.process(this.urlToCrawl, doc);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
