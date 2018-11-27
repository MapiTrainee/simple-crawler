package xyz.pietryga.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.pietryga.crawler.domain.Page;
import xyz.pietryga.crawler.domain.PageComposer;
import xyz.pietryga.crawler.util.CrawlerUtil;

public class PageCrawler {

    private final URLCrawler crawler;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public PageCrawler(String fullAddress) {
	this.crawler = new URLCrawler(fullAddress);
    }

    public Page getPageWithVisitedLocalSubpages() {
	PageComposer rootPage = new PageComposer(crawler.getRootURL().toString());
	List<URL> urls = CrawlerUtil.getURLsFromCurrentURL(crawler.getRootURL());
	addSubpagesToPageFromURLs(rootPage, urls);

	Queue<Page> pagesToCheck = new LinkedList<>(rootPage.getSubpages());
	Set<Page> visitedPages = new HashSet<>();
	visitedPages.add(rootPage);

	while (!pagesToCheck.isEmpty()) {
	    Page currentPage = pagesToCheck.poll();
	    if (visitedPages.add(currentPage)) {
		List<URL> currentURLs = CrawlerUtil.getURLsFromCurrentURL(getURLFromPage(currentPage));
		if (!currentURLs.isEmpty()) {
		    addSubpagesToPageFromURLs(currentPage, currentURLs);
		    pagesToCheck.addAll(((PageComposer) currentPage).getSubpages());
		}
	    }
	}
	return rootPage;
    }

    private void addSubpagesToPageFromURLs(Page page, List<URL> urls) {
	for (URL url : urls) {
	    page.addSubpage(new PageComposer(url.toString()));
	}
    }

    private URL getURLFromPage(Page page) {
	URL url = null;
	try {
	    url = new URL(page.getAddress());
	} catch (MalformedURLException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return url;
    }

}
