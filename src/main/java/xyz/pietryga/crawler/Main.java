package xyz.pietryga.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.pietryga.crawler.domain.Page;
import xyz.pietryga.crawler.util.CrawlerUtil;
import xyz.pietryga.crawler.util.IOUtil;

public class Main {

    public static void main(String[] args) {
	if (args.length < 1) {
	    URLCrawler.printUsageAndStop();
	}
	String address = args[0];
	URLCrawler urlc = new URLCrawler(address);
	
	// CLASSICAL VERSION
	String link = args[0];
	String core = CrawlerUtil.getProtocolAndHostFromLink(link);
	link = (link.endsWith("/")) ? link.substring(0, link.length() - 1) : link;
	Queue<String> files = (Queue<String>) IOUtil.getFilesFromLink(link);
	Set<String> visitedLinks = new LinkedHashSet<>();
	visitedLinks.add(link);
	while (files.peek() != null) {
	    String file = files.poll();
	    if (visitedLinks.add(CrawlerUtil.getAddress(file, core))) {
		Queue<String> newFiles = (Queue<String>) IOUtil.getFilesFromLink(CrawlerUtil.getAddress(file, core));
		if (newFiles != null) {
		    files.addAll(newFiles);
		}
	    }
	}
	// OBJECT ORIENTED VERSION
	Page homePage = new Page(link);
	files = (Queue<String>) IOUtil.getFilesFromLink(link);
	homePage.addLinks(files, core);
	Queue<Page> pages = new LinkedList<>(homePage.getLinks());
	Set<Page> visitedPages = new HashSet<>();
	visitedPages.add(homePage);
	while (pages.peek() != null) {
	    Page page = pages.poll();
	    if (visitedPages.add(page)) {
		Queue<String> newFiles = (Queue<String>) IOUtil.getFilesFromLink(page.getAddress());
		if (newFiles != null) {
		    page.addLinks(newFiles, core);
		    pages.addAll(page.getLinks());
		}
	    }
	}
	System.out.println("Visited links: " + (visitedLinks.size()));
	IOUtil.writeToFile(visitedLinks);
	IOUtil.writeToFile(homePage);
	System.out.println("Links have been written to links.txt and links.json");
    }
}
