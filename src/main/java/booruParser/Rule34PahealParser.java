package booruParser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import booruProperties.BooruProperties;

public class Rule34PahealParser extends BooruParser {

	public Rule34PahealParser(BooruProperties booruProperties) {
		super(booruProperties);
	}

	@Override
	protected Set<String> retrieveImagesURLsInPage(String translatedSearch, int page) {
		Set<String> pageImagesURLs = new HashSet<String>();
		Document pageDocument = null;
		try {
			pageDocument = getPageDocument(translatedSearch, page);
		} catch (Exception e) {
			return Collections.emptySet();
		}

		Elements pageElements = pageDocument.select("a");
		for (Element pageElement : pageElements) {
			String elementURL = pageElement.absUrl("href");
			if (elementURL.contains(booruProperties.getImageIdentifier())) {
				pageImagesURLs.add(elementURL);
			}
		}

		return pageImagesURLs;
	}

}
