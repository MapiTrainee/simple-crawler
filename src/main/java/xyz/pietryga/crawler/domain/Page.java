package xyz.pietryga.crawler.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import xyz.pietryga.crawler.util.CrawlerUtil;

public class Page {

    private final String address;
    private final List<Page> links = new LinkedList<>();

    public Page(String address) {
	this.address = address;
    }

    public String getAddress() {
	return address;
    }

    public List<Page> getLinks() {
	return links;
    }

    public boolean addLinks(List<Page> links) {
	return links.addAll(links);
    }

    public boolean addLinks(Queue<String> files, String core) {
	for (String file : files) {
	    links.add(new Page(CrawlerUtil.getAddress(file, core)));
	}
	return true;
    }

    @Override
    public String toString() {
	if (links.size() > 0) {
	    return "{\"address\":\"" + address + "\", \"links\":" + links.toString() + "}";
	}
	return "{\"address\":\"" + address + "\"}";
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 61 * hash + Objects.hashCode(this.address);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Page other = (Page) obj;
	if (!Objects.equals(this.address, other.address)) {
	    return false;
	}
	return true;
    }

}
