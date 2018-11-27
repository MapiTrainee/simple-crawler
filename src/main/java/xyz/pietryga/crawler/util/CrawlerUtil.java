package xyz.pietryga.crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xyz.pietryga.crawler.domain.Page;

public class CrawlerUtil {

    private static final Logger logger = Logger.getLogger(CrawlerUtil.class.getName());

    private CrawlerUtil() {
    }

    public static String getProtocolAndHostFromLink(String link) {
	String regex = "^(http:\\/\\/(www\\.)?[^\\s\\/]+\\.[a-z]{2,6})";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(link);
	return matcher.find() ? matcher.group(1) : null;
    }

    public static List<String> parseBodyToFiles(String body) {
	List<String> files = new LinkedList<>();
	String regexLinks = "<a[^<|>]+href=[\"|']+([^<|>|\\\"|']+)[^<|>]+>";
	String regexFiles = "^(http|www).*$";
	Pattern pattern = Pattern.compile(regexLinks);
	Matcher matcher = pattern.matcher(body);
	while (matcher.find()) {
	    String link = matcher.group(1);
	    if (!link.matches(regexFiles)) {
		files.add(link);
	    }
	}
	return files;
    }

    public static boolean isStringURLCorrect(String path) {
	String regex = "^http:\\/\\/(www\\.)?[^\\s]+\\.[^\\s]+$";
	return path.matches(regex);
    }

    public static String readFromInputStream(InputStream inputStream) {
	StringBuilder sb = new StringBuilder();
	try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	    String line;
	    while ((line = br.readLine()) != null) {
		sb.append(line);
	    }
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, null, ex);
	}
	return sb.toString();
    }

    public static String getAddress(String file, String core) {
	if ("/".equals(file)) {
	    return core;
	} else if (file.startsWith("/")) {
	    return core + file;
	} else {
	    return core + "/" + file;
	}
    }

}
