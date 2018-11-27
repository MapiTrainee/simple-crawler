package xyz.pietryga.crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtil {

    private static final Logger logger = Logger.getLogger(IOUtil.class.getName());

    public static <T> void writeToFile(T t, String filename) {
	File file = new File(filename);
	try (final PrintWriter pw = new PrintWriter(file)) {
	    pw.write(t.toString());
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

    public static String readFromInputStream(InputStream inputStream) {
	StringBuilder sb = new StringBuilder();
	try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	    String line;
	    while ((line = br.readLine()) != null) {
		sb.append(line);
	    }
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return sb.toString();
    }

}
