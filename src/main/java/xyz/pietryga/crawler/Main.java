package xyz.pietryga.crawler;

import java.net.URL;
import java.util.Collection;
import xyz.pietryga.crawler.domain.Page;
import xyz.pietryga.crawler.util.CrawlerUtil;

public class Main {

    public static void main(String[] args) {
	if (args.length < 1) {
	    CrawlerUtil.printUsageAndStop();
	}
	String startAddress = args[0];

	// URL CRAWLER USING (CLASSICAL VERSION)
	URLCrawler crawler = new URLCrawler(startAddress);
	Collection<URL> urls = crawler.findAndVisitLocalURLs();
	CrawlerUtil.writeURLsToFile(urls, "links.txt");

	// PAGE CRAWLER USING (OBJECT ORIENTED VERSION)
	PageCrawler pageCrawler = new PageCrawler(startAddress);
	Page rootPage = pageCrawler.getPageWithVisitedLocalSubpages();
	CrawlerUtil.writePageToFile(rootPage, "links.json");

	System.out.println("Visited links: " + urls.size());
	System.out.println("Links have been written to links.txt and links.json");
    }
}
