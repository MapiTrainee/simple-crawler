package xyz.pietryga.crawler.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.hamcrest.core.Is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class CrawlerUtilTest {

    @Test
    public void testReadFromInputStream_sampleInputStream_expectedString() {
	String expectedString = "One Two Three";
	InputStream sampleInputStream = new ByteArrayInputStream(expectedString.getBytes(StandardCharsets.UTF_8));
	String actualString = IOUtil.readFromInputStream(sampleInputStream);
	assertThat(actualString, Is.is(expectedString));
    }

    @Test
    public void testParseBodyToFiles_sampleBody_expectedFiles() {
	List<String> expectedFiles = new LinkedList<>();
	expectedFiles.add("index.html");

	String sampleBody = "<!DOCTYPE html>\n"
		+ "<html>\n"
		+ "<head>\n"
		+ "	<title>Test</title>\n"
		+ "</head>\n"
		+ "<body>\n"
		+ "	<a href='index.html'>Home</a>\n"
		+ "	<a href='http://google.com'>Google</a>\n"
		+ "</body>\n"
		+ "</html>";

	CrawlerUtil.createLocalAddressesFromXmlDocument(sampleBody);
	List<String> actualFiles = CrawlerUtil.getLocalAddresses();

	assertThat(actualFiles, Is.is(expectedFiles));
    }
}
