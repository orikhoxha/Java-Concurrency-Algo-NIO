package javarealworldchallenges.threads.Crawler.util;

public class URLNormalizer {

    public static String normalize(String targetUrl, String baseUrl) {
        if (targetUrl.startsWith("https://"))                  return targetUrl;
        if (targetUrl.startsWith("http://"))                   return targetUrl;
        if (targetUrl.toLowerCase().startsWith("javascript:")) return targetUrl;

        //System.out.println("*** Normalizing " + targetUrl + " using base url " + baseUrl);

        StringBuilder builder = new StringBuilder();

        if (!baseUrl.startsWith("http://")) {
            builder.append("http://");
        }

        if (targetUrl.startsWith("/")) {

            int endOfDomain = baseUrl.indexOf("/", 7);

            if (endOfDomain == -1) {
                builder.append(baseUrl, 0, endOfDomain);
            } else {
                builder.append(baseUrl.substring(0, endOfDomain));
            }
            builder.append(targetUrl);

        } else {
            int lastDirSeparatorIndex = baseUrl.lastIndexOf("/");
            if (lastDirSeparatorIndex > 6) {
                builder.append(baseUrl, 0, lastDirSeparatorIndex);
            } else {
                builder.append(baseUrl);
            }
            builder.append("/");
            builder.append(targetUrl);
        }

        int fragmentIndex = builder.indexOf("#");

        if (fragmentIndex > -1) {
            // deletes all occurences of # from string
            builder.delete(fragmentIndex, builder.length());
        }


        int indexOfDirUp = builder.indexOf("../");

        while (indexOfDirUp > -1) {
            int indexOfLastDirBeforeDirUp = builder.lastIndexOf("/",  indexOfDirUp - 2);
            if (indexOfLastDirBeforeDirUp > -1) {
                builder.delete(indexOfLastDirBeforeDirUp + 1, indexOfDirUp + 3);
            }
            indexOfDirUp = builder.indexOf("../");
        }
        return builder.toString();
    }
}
