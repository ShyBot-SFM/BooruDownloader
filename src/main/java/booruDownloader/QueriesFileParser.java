package booruDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueriesFileParser {

	private static final String QUERIES_FILE = "BooruQueries.txt";
	private static final String COMMENT_CHAR = "#";
	private static final String BOORU_CHAR = ">";
	private static final String COMMON_QUERIES = "Common";

	private static final String IGNORE_CACHE_ARG = "--f";
	private static final String CHACE_ONLY_ARG = "--c";

	public static List<BooruQueries> parseConfigFile() {
		List<BooruQueries> boorusQueries = new ArrayList<BooruQueries>();

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(QUERIES_FILE));
			br = new BufferedReader(fr);

			BooruQueries currentBooru = null;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (line.startsWith(COMMENT_CHAR) || line.isEmpty()) {
					continue;
				}

				if (line.startsWith(BOORU_CHAR)) {
					BooruQueries booru = parseBooruLine(line);
					if (!boorusQueries.contains(booru)) {
						boorusQueries.add(booru);
					}
					currentBooru = booru;
				} else {
					Query query = parseQueryLine(line);
					if (currentBooru != null) {
						currentBooru.addQuery(query);
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error: \"" + QUERIES_FILE + "\" file not found. Creating file...");
			try {
				new File(QUERIES_FILE).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}

				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int commonQueriesIndex = boorusQueries.indexOf(new BooruQueries(COMMON_QUERIES));
		if (commonQueriesIndex != -1) {
			List<Query> commonQueries = boorusQueries.remove(commonQueriesIndex).getBooruQueries();
			for (BooruQueries booruQueries : boorusQueries) {
				for (Query query : commonQueries) {
					booruQueries.addQuery(query);
				}
			}
		}

		return boorusQueries;
	}

	private static BooruQueries parseBooruLine(String line) {
		String booruURL;
		try {
			booruURL = line.split(BOORU_CHAR)[1];
		} catch (IndexOutOfBoundsException e) {
			booruURL = COMMON_QUERIES;
		}

		return new BooruQueries(booruURL);
	}

	private static Query parseQueryLine(String line) {
		StringBuilder query = new StringBuilder();
		boolean ignoreCache = false;
		boolean cacheOnly = false;

		String[] queryFields = line.split(" ");
		for (String queryField : queryFields) {
			if (queryField.equals(IGNORE_CACHE_ARG)) {
				ignoreCache = true;
			} else if (queryField.equals(CHACE_ONLY_ARG)) {
				cacheOnly = true;
			} else {
				if (query.length() == 0) {
					query.append(queryField);
				} else {
					query.append(" " + queryField);
				}
			}
		}

		for (int i = 1; i < queryFields.length; ++i) {
			switch (queryFields[i]) {
			case IGNORE_CACHE_ARG:
				ignoreCache = true;
				break;
			default:
				break;
			}
		}

		return new Query(query.toString(), ignoreCache, cacheOnly);
	}

}
