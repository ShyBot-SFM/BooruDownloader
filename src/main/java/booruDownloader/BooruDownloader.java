package booruDownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HttpsURLConnection;

import booruParser.BooruParser;
import booruProperties.BooruProperties;

public abstract class BooruDownloader {

	private static final int THREADS = 4;
	private static final String USER_AGENT_PROPERTY = "User-Agent";
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

	private static Query booruQuery;
	private static BooruProperties booruProperties;
	private static BooruParser booruParser;
	private static BooruCache booruCache;

	private static Semaphore downloadCount = new Semaphore(0);
	private static ExecutorService downloadExecutor = Executors.newFixedThreadPool(THREADS);
	private static Queue<String> failedDownloadURLs = new ConcurrentLinkedQueue<String>();

	public static void startDownload(Query query, BooruAttributes booruAttributes) {
		resetDownloader(query, booruAttributes);

		Set<String> imagesURLs = fetchURLs(query);
		if (imagesURLs.isEmpty()) {
			System.out.println("No (new) images found");
			return;
		}

		startDownload(imagesURLs);
		awaitForDownloadTermination(imagesURLs.size());
	}

	private static void resetDownloader(Query query, BooruAttributes booruAttributes) {
		failedDownloadURLs.clear();
		booruQuery = query;
		booruProperties = booruAttributes.PROPERTIES;
		booruParser = booruAttributes.PARSER;
		booruCache = booruAttributes.CACHE;
	}

	private static Set<String> fetchURLs(Query query) {
		System.out.println("Fetching URLs...");

		String translatedQuery = booruProperties.translateSearch(query.getQuery());
		Set<String> cachedURLs = (query.getIgnoreCache()) ? new HashSet<String>() : booruCache.retrieveURLsCached();
		return booruParser.retrieveImagesURLs(translatedQuery, cachedURLs);
	}

	private static void startDownload(Set<String> imagesURLs) {
		new Thread(() -> {
			createDownloadDirectory();
			for (String imageURL : imagesURLs) {
				downloadExecutor.submit(() -> download(imageURL));
			}
		}).start();
	}

	private static boolean downloadDirectoryExists() {
		File downloadDirectory = new File(booruProperties.getDownloadDirectory(booruQuery.getQuery()));
		return downloadDirectory.exists();
	}

	private static void createDownloadDirectory() {
		if (downloadDirectoryExists()) {
			return;
		}

		File downloadDirectory = new File(booruProperties.getDownloadDirectory(booruQuery.getQuery()));
		downloadDirectory.mkdirs();
	}

	private static void awaitForDownloadTermination(int downloadSize) {
		int imagesDownloaded = 0;
		do {
			System.out.format("\rDownloading... %d/%d", imagesDownloaded++, downloadSize);
			downloadCount.acquireUninterruptibly();
		} while (imagesDownloaded < downloadSize);

		System.out.format("\rFinished all %d downloads for search \"%s\"\n", downloadSize, booruQuery.getQuery());
	}

	private static void download(String url) {
		FileOutputStream outputStream = null;
		InputStream inputStream = null;

		try {
			String fileName = booruProperties.getDownloadDirectory(booruQuery.getQuery())
					+ booruProperties.getImageFileName(url);

			HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestProperty(USER_AGENT_PROPERTY, USER_AGENT);

			outputStream = new FileOutputStream(fileName);
			inputStream = connection.getInputStream();

			ReadableByteChannel rbc = Channels.newChannel(inputStream);
			outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (Exception e) {
			failedDownloadURLs.offer(url);
		} finally {
			booruCache.cacheURL(url);
			downloadCount.release();

			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void printFailedDownloads() {
		if (failedDownloadURLs.isEmpty()) {
			return;
		}

		System.err.println("The program failed to download some images:");
		for (String failedDownloadURL : failedDownloadURLs) {
			System.err.format("> %s\n", failedDownloadURL);
		}
	}

	public static void onProgramShutdown() {
		downloadExecutor.shutdown();
	}

}
