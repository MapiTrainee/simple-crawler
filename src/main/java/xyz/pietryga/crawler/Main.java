package xyz.pietryga.crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import xyz.pietryga.crawler.domain.Page;
import xyz.pietryga.crawler.util.CrawlerUtil;
import xyz.pietryga.crawler.util.IOUtil;

public class Main {

    public static void main(String[] args) {
	if (args.length < 1) {
	    CrawlerUtil.printUsageAndStop();
	}
	String startAddress = args[0];
	
	URLCrawler crawler = new URLCrawler(startAddress);
	Iterable<URL> urls = crawler.findAndVisitLocalURLs();
	CrawlerUtil.writeURLsToFile(urls, "links.txt");
	for (URL url : urls) {
	    System.out.println(url);
	}
	
	PageCrawler pageCrawler = new PageCrawler(startAddress);
	Page rootPage = pageCrawler.getPageWithVisitedLocalPages();
	CrawlerUtil.writePageToFile(rootPage, "links.json");
	

	// CLASSICAL VERSION
	String link = args[0];
	String core = CrawlerUtil.getProtocolAndHostFromLink(link);
	link = (link.endsWith("/")) ? link.substring(0, link.length() - 1) : link;
	Queue<String> files = (Queue<String>) CrawlerUtil.getFilesFromLink(link);
	Set<String> visitedLinks = new LinkedHashSet<>();
	visitedLinks.add(link);
	while (files.peek() != null) {
	    String file = files.poll();
	    if (visitedLinks.add(CrawlerUtil.getAddress(file, core))) {
		Queue<String> newFiles = (Queue<String>) CrawlerUtil.getFilesFromLink(CrawlerUtil.getAddress(file, core));
		if (newFiles != null) {
		    files.addAll(newFiles);
		}
	    }
	}
	// OBJECT ORIENTED VERSION
	Page homePage = new Page(link);
	files = (Queue<String>) CrawlerUtil.getFilesFromLink(link);
	homePage.addLinks(files, core);
	Queue<Page> pages = new LinkedList<>(homePage.getLinks());
	Set<Page> visitedPages = new HashSet<>();
	visitedPages.add(homePage);
	while (pages.peek() != null) {
	    Page page = pages.poll();
	    if (visitedPages.add(page)) {
		Queue<String> newFiles = (Queue<String>) CrawlerUtil.getFilesFromLink(page.getAddress());
		if (newFiles != null) {
		    page.addLinks(newFiles, core);
		    pages.addAll(page.getLinks());
		}
	    }
	}
	System.out.println("Visited links: " + (visitedLinks.size()));
	IOUtil.writeToFile(visitedLinks, "links.txt");
	IOUtil.writeToFile(homePage, "link.js");
	System.out.println("Links have been written to links.txt and links.json");
    }
}
