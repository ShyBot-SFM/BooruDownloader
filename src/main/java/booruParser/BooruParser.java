package booruParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import booruProperties.BooruProperties;
import booruProperties.E621Properties;
import booruProperties.Rule34PahealProperties;
import booruProperties.Rule34xxxProperties;

public abstract class BooruParser {

	protected BooruProperties booruProperties;

	public BooruParser(BooruProperties booruProperties) {
		this.booruProperties = booruProperties;
	}

	public static BooruParser initializeParser(BooruProperties booruProperties) {
		if (booruProperties instanceof Rule34xxxProperties) {
			return new Rule34xxxParser(booruProperties);
		}

		if (booruProperties instanceof Rule34PahealProperties) {
			return new Rule34PahealParser(booruProperties);
		}

		if (booruProperties instanceof E621Properties) {
			return new E621Parser(booruProperties);
		}

		return new StandardBooruParser(booruProperties);
	}

	public final Set<String> retrieveImagesURLs(String translatedSearch, Set<String> cachedURLs) {
		Set<String> imagesURLs = new HashSet<String>();

		int page = 1;
		boolean imagesFound = true;
		while (imagesFound) {
			Set<String> imagesInPage = retrieveImagesURLsInPage(translatedSearch, page++);
			imagesInPage.removeAll(cachedURLs);
			imagesURLs.addAll(imagesInPage);
			imagesFound = !imagesInPage.isEmpty();
		}

		return imagesURLs;
	}

	public final String getHTML(String translatedSearch, int page) throws IOException {
		return getPageDocument(translatedSearch, page).html();
	}

	protected final Document getPageDocument(String translatedSearch, int page) throws IOException {
		String pageURL = booruProperties.getBooruPageURL(translatedSearch, page);
		return Jsoup.connect(pageURL).get();
	}

	protected abstract Set<String> retrieveImagesURLsInPage(String translatedSearch, int page);

}
