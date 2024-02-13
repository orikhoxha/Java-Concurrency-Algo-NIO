package javarealworldchallenges.threads.Crawler.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlNormalizerTest {

    @Test
    public void testUrlNormalizer() {
        String normalizedUrl = URLNormalizer.normalize("java/interface.html", "http://tutorials.jenkov.com");
        assertEquals("http://tutorials.jenkov.com/java/interface.html", normalizedUrl);

        normalizedUrl = URLNormalizer.normalize("java/interfaces.html", "http://tutorials.jenkov.com/texts/index.html");
        assertEquals("http://tutorials.jenkov.com/texts/java/interfaces.html", normalizedUrl);

        normalizedUrl = URLNormalizer.normalize("../java/interfaces.html", "http://tutorials.jenkov.com/html4/index.html");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = URLNormalizer.normalize("/flex-tetris/more/../../index.html", "http://tutorials.jenkov.com/html4/level1/level2/index.html");
        assertEquals("http://tutorials.jenkov.com/index.html", normalizedUrl);

    }
}
