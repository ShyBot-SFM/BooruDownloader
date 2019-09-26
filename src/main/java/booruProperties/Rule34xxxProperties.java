package booruProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Rule34xxxProperties extends BooruProperties {

	public static final String RULE_34_XXX_URL = "https://rule34.xxx/";

	private static final String RULE_34_XXX_HEADER = RULE_34_XXX_URL
			+ "index.php?page=dapi&s=post&q=index&limit=100&tags=";
	private static final String RULE_34_XXX_PAGE_SUFFIX = "&pid=";

	@Override
	public String getBooruMainPageURL() {
		return RULE_34_XXX_URL;
	}

	@Override
	public String getBooruPageURL(String translatedSearch, int page) {
		return RULE_34_XXX_HEADER + translatedSearch + RULE_34_XXX_PAGE_SUFFIX + (page - 1);
	}

	@Override
	public int getBooruImagesPerPage() {
		throw new UnsupportedOperationException("Rule34xxx does not use a IMAGES_PER_PAGE value");
	}

	@Override
	public String translateSearch(String search) {
		String translatedURL = null;
		try {
			translatedURL = URLEncoder.encode(search, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return translatedURL;
	}

	@Override
	public String getImageIdentifier() {
		throw new UnsupportedOperationException("Rule34xxx does not use a IMAGE_IDENTIFIER value");
	}

}
