package javarealworldchallenges.threads.Crawler.mt;

import javarealworldchallenges.threads.Crawler.util.URLNormalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BrokenBarrierException;

public class CrawlerJobMT implements Runnable {


    protected CrawlerMT crawler;
    protected String urlToCrawl;

    public CrawlerJobMT(CrawlerMT crawler, String urlToCrawl) {
        this.crawler = crawler;
        this.urlToCrawl = urlToCrawl;
    }



    @Override
    public void run() {
        try {
            crawl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crawl() throws IOException {
        URL url = new URL(this.urlToCrawl);
        URLConnection urlConnection = null;

        try {
            urlConnection = url.openConnection();

            try (InputStream input = urlConnection.getInputStream()) {
                Document doc = Jsoup.parse(input, "UTF-8", "");
                Elements elements = doc.select("a");
                String baseUrl = url.toExternalForm();

                for (Element element : elements) {
                    String linkUrl = element.attr("href");
                    String normalizedUrl = URLNormalizer.normalize(linkUrl, baseUrl);
                    crawler.linksQueue.put(normalizedUrl);
                    System.out.println(" - " + normalizedUrl);
                }

                if (crawler.barrier.getNumberWaiting() == 1) {
                    crawler.barrier.await();
                }
            } catch (IOException | InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }  catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
