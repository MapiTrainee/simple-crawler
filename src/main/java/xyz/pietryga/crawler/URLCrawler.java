package xyz.pietryga.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.pietryga.crawler.domain.Page;
import xyz.pietryga.crawler.util.CrawlerUtil;

public class URLCrawler {

    private final static Logger logger = Logger.getLogger(URLCrawler.class.getName());

    private static List<String> getFilesFromLink(String link) {
	try {
	    URL destURL = new URL(link);
	    URLConnection connection = destURL.openConnection();
	    String body = CrawlerUtil.readFromInputStream(connection.getInputStream());
	    List<String> files = CrawlerUtil.parseBodyToFiles(body);
	    return files;
	} catch (MalformedURLException ex) {
	    logger.log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return null;
    }

    public static void main(String[] args) throws URISyntaxException {
	if (args.length < 1 || !CrawlerUtil.isStringURLCorrect(args[0])) {
	    System.err.println("Usage: java URLCrawler http://yourwebsite.com");
	    System.exit(1);
	}

	// CLASSICAL VERSION
	String link = args[0];
	String core = CrawlerUtil.getProtocolAndHostFromLink(link);
	link = (link.endsWith("/")) ? link.substring(0, link.length() - 1) : link;

	Queue<String> files = (Queue<String>) getFilesFromLink(link);
	Set<String> visitedLinks = new LinkedHashSet<>();
	visitedLinks.add(link);

	while (files.peek() != null) {
	    String file = files.poll();
	    if (visitedLinks.add(CrawlerUtil.getAddress(file, core))) {
		Queue<String> newFiles = (Queue<String>) getFilesFromLink(CrawlerUtil.getAddress(file, core));
		if (newFiles != null) {
		    files.addAll(newFiles);
		}
	    }
	}

	// OBJECT ORIENTED VERSION
	Page homePage = new Page(link);
	files = (Queue<String>) getFilesFromLink(link);
	homePage.addLinks(files, core);
	Queue<Page> pages = new LinkedList<>(homePage.getLinks());
	Set<Page> visitedPages = new HashSet<>();
	visitedPages.add(homePage);

	while (pages.peek() != null) {
	    Page page = pages.poll();
	    if (visitedPages.add(page)) {
		Queue<String> newFiles = (Queue<String>) getFilesFromLink(page.getAddress());
		if (newFiles != null) {
		    page.addLinks(newFiles, core);
		    pages.addAll(page.getLinks());
		}
	    }
	}

	logger.info(core);
	logger.info(visitedLinks.toString());
	logger.info("" + visitedLinks.size());
	logger.info(homePage.toString());
    }

}
