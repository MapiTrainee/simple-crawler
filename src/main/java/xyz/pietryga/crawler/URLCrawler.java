package xyz.pietryga.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLCrawler {

    private URL url;

    private final static Logger logger = Logger.getLogger(URLCrawler.class.getName());

    public URLCrawler(String fullAddress) {
	try {
	    this.url = new URL(fullAddress);
	} catch (MalformedURLException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	System.setProperty("http.agent", "Chrome");
    }

    public static void printUsageAndStop() {
	System.err.println("Usage: java URLCrawler http://yourwebsite.com");
	System.exit(1);
    }

    public List<URL> findURLs() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
