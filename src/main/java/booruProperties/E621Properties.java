package booruProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class E621Properties extends BooruProperties {

	public static final String E621_URL = "https://e621.net/";

	private static final String E621_HEADER = E621_URL + "post/index.json?tags=";
	private static final String E621_PAGE_SUFFIX = "&page=";

	@Override
	public String getBooruMainPageURL() {
		return E621_URL;
	}

	@Override
	public String getBooruPageURL(String translatedSearch, int page) {
		return E621_HEADER + translatedSearch + E621_PAGE_SUFFIX + page;
	}

	@Override
	public int getBooruImagesPerPage() {
		throw new UnsupportedOperationException("E621 does not use a IMAGES_PER_PAGE");
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
		throw new UnsupportedOperationException("E621 does not use a IMAGE_IDENTIFIER");
	}

}
