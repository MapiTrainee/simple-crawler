package xyz.pietryga.crawler.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.junit.Assert.*;

public class IOUtilTest {

    @Test
    public void shouldReadInputStream() {
	String expectedString = "One Two Three";
	InputStream sampleInputStream = new ByteArrayInputStream(expectedString.getBytes(StandardCharsets.UTF_8));
	String actualString = IOUtil.readFromInputStream(sampleInputStream);
	assertThat(actualString, is(expectedString));
    }

}
