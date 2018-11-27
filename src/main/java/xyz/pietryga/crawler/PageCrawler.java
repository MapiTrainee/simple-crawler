package xyz.pietryga.crawler;

import java.util.logging.Logger;
import xyz.pietryga.crawler.domain.Page;

public class PageCrawler {

    private URLCrawler crawler;

    private final static Logger logger = Logger.getLogger(PageCrawler.class.getName());

    public PageCrawler(String fullAddress) {
	this.crawler = new URLCrawler(fullAddress);
    }

    public Page getPageWithVisitedLocalPages() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
