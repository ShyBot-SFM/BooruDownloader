package booruProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StandardBooruProperties extends BooruProperties {

	private static final int IMAGES_PER_PAGE = 20;
	private static final String IMAGE_IDENTIFIER = "page=post&s=view&id=";
	private static final String BOORU_PAGE_SUFFIX = "index.php?page=post&s=list&tags=";
	private static final String BOORU_IMAGE_ID_SUFFIX = "&pid=";

	private final String BOORU_URL;
	private final String BOORU_HEADER;

	public StandardBooruProperties(String booruMainPageURL) {
		BOORU_URL = booruMainPageURL;
		BOORU_HEADER = BOORU_URL + BOORU_PAGE_SUFFIX;
	}

	@Override
	public String getImageFileName(String imageURL) {
		String[] splittedURL = imageURL.split("/");
		String fileName = splittedURL[splittedURL.length - 1];

		int questionMarkPosition = fileName.indexOf('?');
		if (questionMarkPosition != -1) {
			fileName = fileName.substring(0, questionMarkPosition);
		}

		return fileName;
	}

	@Override
	public String getBooruMainPageURL() {
		return BOORU_URL;
	}

	@Override
	public String getBooruPageURL(String translatedSearch, int page) {
		return BOORU_HEADER + translatedSearch + BOORU_IMAGE_ID_SUFFIX + getIndexOfFirstImageInPage(page);
	}

	@Override
	public int getBooruImagesPerPage() {
		return IMAGES_PER_PAGE;
	}

	@Override
	public String translateSearch(String search) {
		String translatedSearch = null;
		try {
			translatedSearch = URLEncoder.encode(search, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return translatedSearch;
	}

	@Override
	public String getImageIdentifier() {
		return IMAGE_IDENTIFIER;
	}

}
