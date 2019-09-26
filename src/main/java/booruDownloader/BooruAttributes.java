package booruDownloader;

import booruParser.BooruParser;
import booruProperties.BooruProperties;

public class BooruAttributes {

	public final BooruProperties PROPERTIES;
	public final BooruParser PARSER;
	public final BooruCache CACHE;

	public BooruAttributes(BooruProperties booruProperties, BooruParser booruParser, BooruCache booruCache) {
		this.PROPERTIES = booruProperties;
		this.PARSER = booruParser;
		this.CACHE = booruCache;
	}

}
