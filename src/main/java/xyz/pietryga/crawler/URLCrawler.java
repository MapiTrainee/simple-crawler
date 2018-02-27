package xyz.pietryga.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	
	String link = args[0];
	String core = CrawlerUtil.getProtocolAndHostFromLink(link) + "/";

	Queue<String> files = (Queue<String>) getFilesFromLink(link);
	Set<String> visitedFiles = new LinkedHashSet<>();

	while (files.peek() != null) {
	    String file = files.poll();
	    if (visitedFiles.add(file)) {
		Queue<String> newFiles = (Queue<String>) getFilesFromLink(core + file);
		if (newFiles != null) {
		    files.addAll(newFiles);
		}
	    }
	}

	logger.info(core);
	logger.info(visitedFiles.toString());
	logger.info("" + visitedFiles.size());
    }

}
