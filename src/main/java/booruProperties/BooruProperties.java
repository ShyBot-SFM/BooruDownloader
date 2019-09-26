package booruProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class BooruProperties {

	public static BooruProperties newProperties(String booruURL) {
		if (booruURL.equals(Rule34xxxProperties.RULE_34_XXX_URL)) {
			return new Rule34xxxProperties();
		}

		if (booruURL.equals(Rule34PahealProperties.RULE_34_PAHEAL_URL)) {
			return new Rule34PahealProperties();
		}

		if (booruURL.equals(E621Properties.E621_URL)) {
			return new E621Properties();
		}

		return new StandardBooruProperties(booruURL);
	}

	public final String getBooruName() {
		String[] splittedURL = getBooruMainPageURL().split("/");
		return splittedURL[splittedURL.length - 1];
	}

	public final String getDownloadDirectory() {
		return getBooruName().replace('.', '_') + "/";
	}

	public final String getDownloadDirectory(String search) {
		return getDownloadDirectory() + removeInvalidCharacters(search) + "/";
	}

	private static String removeInvalidCharacters(String search) {
		if (isLinuxComputer()) {
			return search;
		}

		return removeInvalidWindowsCharacters(search);
	}

	private static boolean isLinuxComputer() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0);
	}

	private final static char[] WINDOWS_INVALID_CHARACTERS = { '<', '>', ':', '"', '/', '|', '?', '*' };

	private static String removeInvalidWindowsCharacters(String search) {
		String fixedSearch = search;
		for (char windowsInvalidCharacter : WINDOWS_INVALID_CHARACTERS) {
			fixedSearch = fixedSearch.replace(windowsInvalidCharacter, '_');
		}

		return fixedSearch;
	}

	public final int getIndexOfFirstImageInPage(int page) {
		return (page - 1) * getBooruImagesPerPage();
	}

	public String getImageFileName(String imageURL) {
		String[] splittedURL = imageURL.split("/");
		String fileName = splittedURL[splittedURL.length - 1];

		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	public abstract String getBooruMainPageURL();

	public abstract String getBooruPageURL(String translatedSearch, int page);

	public abstract int getBooruImagesPerPage();

	public abstract String translateSearch(String search);

	public abstract String getImageIdentifier();

}
