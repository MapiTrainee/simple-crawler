package xyz.pietryga.crawler.util;

import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class CrawlerUtilTest {

    @Test
    public void shouldReturnLocalAddressesFromXmlDocument() {
	List<String> expectedLocalAddresses = new LinkedList<>();
	expectedLocalAddresses.add("index.html");

	String document = "<!DOCTYPE html>\n"
		+ "<html>\n"
		+ "<head>\n"
		+ "	<title>Test</title>\n"
		+ "</head>\n"
		+ "<body>\n"
		+ "	<a href='index.html'>Home</a>\n"
		+ "	<a href='http://google.com'>Google</a>\n"
		+ "</body>\n"
		+ "</html>";

	CrawlerUtil.createLocalAddressesFromXmlDocument(document);
	List<String> actualLocalAddresses = CrawlerUtil.getLocalAddresses();

	assertThat(actualLocalAddresses, is(expectedLocalAddresses));
    }

}
