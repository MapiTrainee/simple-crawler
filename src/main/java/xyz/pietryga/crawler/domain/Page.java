package xyz.pietryga.crawler.domain;

public interface Page {

    String getAddress();

    void addSubpage(Page page);
}
