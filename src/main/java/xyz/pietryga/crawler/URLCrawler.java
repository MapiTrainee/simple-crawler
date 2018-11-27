package xyz.pietryga.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.pietryga.crawler.util.CrawlerUtil;

public class URLCrawler {

    private URL rootURL;

    private final static Logger logger = Logger.getLogger(URLCrawler.class.getName());

    public URLCrawler(String fullAddress) {
	try {
	    this.rootURL = new URL(fullAddress);
	} catch (MalformedURLException ex) {
	    logger.log(Level.SEVERE, null, ex);
	    printUsageAndStop();
	}
	System.setProperty("http.agent", "Chrome");
    }

    public static void printUsageAndStop() {
	System.err.println("Usage: java URLCrawler http://yourwebsite.com");
	System.exit(1);
    }

    public Iterable<URL> findAndVisitLocalURLs() {
	Queue<URL> urlsToCheck = new LinkedList<>(CrawlerUtil.getURLsFromCurrentURL(rootURL));
	Set<URL> visitedURLs = new LinkedHashSet<>();
	visitedURLs.add(rootURL);

	while (!urlsToCheck.isEmpty()) {
	    URL currentURL = urlsToCheck.poll();
	    if (visitedURLs.add(currentURL)) {
		List<URL> foundedURLs = CrawlerUtil.getURLsFromCurrentURL(currentURL);
		if (!foundedURLs.isEmpty()) {
		    urlsToCheck.addAll(foundedURLs);
		}
	    }
	}
	return visitedURLs;
    }

}
