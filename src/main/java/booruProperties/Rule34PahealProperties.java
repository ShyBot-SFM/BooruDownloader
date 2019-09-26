package booruProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Rule34PahealProperties extends BooruProperties {

	public static final String RULE_34_PAHEAL_URL = "http://rule34.paheal.net/";

	private static final String RULE_34_PAHEAL_HEADER = RULE_34_PAHEAL_URL + "post/list/";
	private static final String RULE_34_PAHEAL_IMAGE_IDENTIFIER = "_images";

	@Override
	public String getBooruMainPageURL() {
		return RULE_34_PAHEAL_URL;
	}

	@Override
	public String getBooruPageURL(String translatedSearch, int page) {
		return RULE_34_PAHEAL_HEADER + translatedSearch + "/" + page;
	}

	@Override
	public int getBooruImagesPerPage() {
		throw new UnsupportedOperationException("Rule34Paheal does not use a IMAGES_PER_PAGE value");
	}

	@Override
	public String translateSearch(String search) {
		String translatedSearch = null;
		try {
			translatedSearch = URLEncoder.encode(search, "UTF-8");
			translatedSearch = translatedSearch.replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return translatedSearch;
	}

	@Override
	public String getImageIdentifier() {
		return RULE_34_PAHEAL_IMAGE_IDENTIFIER;
	}

}
