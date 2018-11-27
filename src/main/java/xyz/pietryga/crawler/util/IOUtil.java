package xyz.pietryga.crawler.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.pietryga.crawler.domain.Page;

public class IOUtil {

    private static final Logger logger = Logger.getLogger(IOUtil.class.getName());

    public static List<String> getFilesFromLink(String link) {
	try {
	    URL destURL = new URL(link);
	    URLConnection connection = destURL.openConnection();
	    String body = CrawlerUtil.readFromInputStream(connection.getInputStream());
	    List<String> files = CrawlerUtil.parseBodyToFiles(body);
	    return files;
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return null;
    }

    public static void writeToFile(Page homePage) {
	File file = new File("links.json");
	try (final FileWriter fw = new FileWriter(file)) {
	    fw.write(homePage.toString());
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
    }

    public static <T> void writeToFile(Iterable<T> ts, String filename) {
	File file = new File(filename);
	try (final PrintWriter pw = new PrintWriter(file)) {
	    for (T t : ts) {
		pw.println(t.toString());
	    }
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
    }

}
