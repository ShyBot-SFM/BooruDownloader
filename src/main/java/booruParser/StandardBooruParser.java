package booruParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import booruProperties.BooruProperties;

public class StandardBooruParser extends BooruParser {

	public StandardBooruParser(BooruProperties booruProperties) {
		super(booruProperties);
	}

	@Override
	protected Set<String> retrieveImagesURLsInPage(String translatedSearch, int page) {
		Set<String> imagesURLs = new HashSet<String>();
		Set<String> imagesPagesURLs = retrieveImagesPagesURLs(translatedSearch, page);

		for (String imagePageURL : imagesPagesURLs) {
			try {
				Document imagePageDocument = Jsoup.connect(imagePageURL).get();

				if (imagePageDocument.select("video").isEmpty()) {
					retrieveImage(imagePageDocument, imagesURLs);
				} else {
					retrieveVideo(imagePageDocument, imagesURLs);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return imagesURLs;
	}

	private Set<String> retrieveImagesPagesURLs(String translatedSearch, int page) {
		Set<String> imagesPagesURLs = new HashSet<String>();
		Document pageDocument = null;
		try {
			pageDocument = getPageDocument(translatedSearch, page);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements pageElements = pageDocument.select("a");
		for (Element pageElement : pageElements) {
			String elementURL = pageElement.absUrl("href");
			if (elementURL.contains(booruProperties.getImageIdentifier())) {
				imagesPagesURLs.add(elementURL);
			}
		}

		return imagesPagesURLs;
	}

	private void retrieveImage(Document pageDoc, Set<String> outputImagesURLs) {
		Elements images = pageDoc.select("img");
		for (Element image : images) {
			String imageURL = image.absUrl("src");
			if (imageURL.contains("img.")) {
				outputImagesURLs.add(imageURL);
			}
		}
	}

	private void retrieveVideo(Document pageDoc, Set<String> outputImagesURLs) {
		Elements videos = pageDoc.select("video");
		for (Element video : videos) {
			for (Element videoFormat : video.children()) {
				outputImagesURLs.add(videoFormat.absUrl("src"));
			}
		}
	}

}
