package booruParser;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import booruProperties.BooruProperties;

public class Rule34xxxParser extends BooruParser {

	public Rule34xxxParser(BooruProperties booruProperties) {
		super(booruProperties);
	}

	@Override
	protected Set<String> retrieveImagesURLsInPage(String translatedSearch, int page) {
		Set<String> pageImagesURLs = new HashSet<String>();

		try {
			Document pageDocument = getPageDocument(translatedSearch, page);
			Element postsArray = pageDocument.select("posts").first();
			for (Element post : postsArray.children()) {
				String imageURL = post.absUrl("file_url");
				pageImagesURLs.add(imageURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageImagesURLs;
	}

}
