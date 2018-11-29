package xyz.pietryga.crawler.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PageComposite implements Page {

    private final String address;
    private final List<Page> subpages = new LinkedList<>();

    public PageComposite(String address) {
	this.address = address;
    }

    @Override
    public String getAddress() {
	return address;
    }

    public <T extends Page> List<T> getSubpages() {
	return (List<T>) subpages;
    }

    public void addSubpages(List<Page> pages) {
	this.subpages.addAll(pages);
    }

    @Override
    public void addSubpage(Page page) {
	this.subpages.add(page);
    }

    @Override
    public String toString() {
	if (subpages.size() > 0) {
	    return "{\"page\":\"" + address + "\", \"subpages\":" + subpages.toString() + "}";
	}
	return "{\"page\":\"" + address + "\"}";
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
	final PageComposite other = (PageComposite) obj;
	if (!Objects.equals(this.address, other.address)) {
	    return false;
	}
	return true;
    }

}
