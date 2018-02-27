package xyz.pietryga.crawler.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Page implements Comparable<Page> {

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

    public boolean addLinks(Queue<String> files, String protocolAndHost) {
	for (String file : files) {
	    links.add(new Page(protocolAndHost + file));
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
    public int compareTo(Page page
    ) {
	if (page != null) {
	    return (address.equals(page.getAddress())) ? 0 : 1;
	}
	throw new NullPointerException("Page can not be null!");
    }

    @Override
    public boolean equals(Object o
    ) {
	return (o instanceof Page) && address.equals(((Page) o).getAddress());
    }

    @Override
    public int hashCode() {
	return address.hashCode();
    }

}
