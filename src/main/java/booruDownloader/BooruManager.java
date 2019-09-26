package booruDownloader;

import java.io.IOException;
import java.util.Set;

import booruParser.BooruParser;
import booruProperties.BooruProperties;

public class BooruManager {

	private BooruProperties booruProperties;
	private BooruParser booruParser;
	private BooruCache booruCache;

	public BooruManager(String booruURL) {
		this.booruProperties = BooruProperties.newProperties(booruURL);
		this.booruParser = BooruParser.initializeParser(this.booruProperties);
		this.booruCache = new BooruCache(this.booruProperties);

		try {
			this.booruCache.loadCache();
		} catch (IOException e) {
			System.err.format(
					"No cache found for booru %s. A cache file will be created once this booru's downloads are completed\n",
					this.booruProperties.getBooruMainPageURL());
		}
	}

	public void interpretQuery(Query query) {
		if (query.getCacheOnly()) {
			cacheQuery(query);
		} else {
			BooruAttributes booruAttributes = new BooruAttributes(booruProperties, booruParser, booruCache);
			BooruDownloader.startDownload(query, booruAttributes);
			BooruDownloader.printFailedDownloads();
		}
	}

	private void cacheQuery(Query query) {
		Set<String> cachedURLs = booruCache.retrieveURLsCached();
		String translatedQuery = booruProperties.translateSearch(query.getQuery());
		Set<String> imagesURLs = booruParser.retrieveImagesURLs(translatedQuery, cachedURLs);

		for (String imageURL : imagesURLs) {
			booruCache.cacheURL(imageURL);
		}
	}

	public void onFinishedInterpretingQuery() {
		booruCache.storeCache();
	}

}
