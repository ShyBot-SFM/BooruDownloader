package booruParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import booruProperties.BooruProperties;

class E621JSONStructure {
	String file_url;
}

public class E621Parser extends BooruParser {

	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

	public E621Parser(BooruProperties booruProperties) {
		super(booruProperties);
	}

	@Override
	protected Set<String> retrieveImagesURLsInPage(String translatedSearch, int page) {
		Set<String> pageImagesURLs = new HashSet<>();

		String JSON_URL = booruProperties.getBooruPageURL(translatedSearch, page);
		try {
			URL url = new URL(JSON_URL);
			HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
			httpsConnection.setRequestProperty("User-Agent", USER_AGENT);

			InputStream inputStream = httpsConnection.getInputStream();
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			Gson gson = new Gson();
			E621JSONStructure[] pagePosts = gson.fromJson(reader, E621JSONStructure[].class);

			for (E621JSONStructure pagePost : pagePosts) {
				pageImagesURLs.add(pagePost.file_url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageImagesURLs;
	}

}
