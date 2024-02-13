package javarealworldchallenges.threads.Crawler.util;

public class SameWebsiteOnlyFilter implements IUrlFilter{

    String domainUrl;

    public SameWebsiteOnlyFilter(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    @Override
    public boolean include(String url) {
        return url.startsWith(this.domainUrl);
    }
}
