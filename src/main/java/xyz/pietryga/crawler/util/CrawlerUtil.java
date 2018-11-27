package xyz.pietryga.crawler.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xyz.pietryga.crawler.domain.Page;

public class CrawlerUtil {

    private static final Logger logger = Logger.getLogger(CrawlerUtil.class.getName());

    private CrawlerUtil() {
    }

    public static void writeURLsToFile(Iterable<URL> urls, String filename) {
	IOUtil.writeToFile(urls, filename);
    }

    public static void writePageToFile(Page page, String filename) {
	IOUtil.writeToFile(page, filename);
    }

    public static List<URL> getURLsFromCurrentURL(URL currentURL) {
	List<URL> urls = new ArrayList<>();
	try {
	    URLConnection connection = currentURL.openConnection();
	    String body = IOUtil.readFromInputStream(connection.getInputStream());
	    List<String> localAdresses = CrawlerUtil.getLocalAddressesFromXmlDocument(body);
	    urls = CrawlerUtil.createURLs(localAdresses, currentURL);
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return urls;
    }

    private static List<URL> createURLs(List<String> localAddresses, URL currentURL) {
	List<URL> urls = new ArrayList<>();
	for (String localAddress : localAddresses) {
	    try {
		urls.add(new URL(currentURL.getProtocol(), currentURL.getHost(), localAddress));
	    } catch (MalformedURLException ex) {
		logger.log(Level.SEVERE, null, ex);
	    }
	}
	return urls;
    }

    private static boolean isLocalAddress(String address) {
	String regexLocalLink = "^(http|www|https).*$";
	return !address.matches(regexLocalLink);
    }

    public static List<String> getLocalAddressesFromXmlDocument(String body) {
	List<String> files = new LinkedList<>();
	String regexLinks = "<a[^<|>]+href=[\"|']+([^<|>|\\\"|']+)[^<|>]+>";
	Pattern patternLinks = Pattern.compile(regexLinks);
	Matcher matcherLinksInBody = patternLinks.matcher(body);
	while (matcherLinksInBody.find()) {
	    String address = matcherLinksInBody.group(1);
	    if (isLocalAddress(address)) {
		files.add(address);
	    }
	}
	return files;
    }

    public static void printUsageAndStop() {
	System.err.println("Usage: java URLCrawler http://yourwebsite.com");
	System.exit(1);
    }
}
