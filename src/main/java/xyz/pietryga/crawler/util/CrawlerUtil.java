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

public class CrawlerUtil {

    private static final Logger logger = Logger.getLogger(CrawlerUtil.class.getName());

    private CrawlerUtil() {
    }

    public static void writeURLsToFile(Iterable<URL> urls, String filename) {
	IOUtil.writeToFile(urls, filename);
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

    public static List<String> getFilesFromLink(String link) {
	try {
	    URL destURL = new URL(link);
	    URLConnection connection = destURL.openConnection();
	    String body = IOUtil.readFromInputStream(connection.getInputStream());
	    List<String> files = CrawlerUtil.getLocalAddressesFromXmlDocument(body);
	    return files;
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return null;
    }

    public static String getProtocolAndHostFromLink(String link) {
	String regex = "^(http:\\/\\/(www\\.)?[^\\s\\/]+\\.[a-z]{2,6})";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(link);
	return matcher.find() ? matcher.group(1) : null;
    }

    public static boolean isStringURLCorrect(String path) {
	String regex = "^http:\\/\\/(www\\.)?[^\\s]+\\.[^\\s]+$";
	return path.matches(regex);
    }

    public static String getAddress(String file, String core) {
	if ("/".equals(file)) {
	    return core;
	} else if (file.startsWith("/")) {
	    return core + file;
	} else {
	    return core + "/" + file;
	}
    }

}
