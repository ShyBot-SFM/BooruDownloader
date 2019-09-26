package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import booruDownloader.BooruManager;
import booruDownloader.Query;
import booruParser.BooruParser;
import booruParser.Rule34PahealParser;
import booruParser.StandardBooruParser;
import booruProperties.BooruProperties;
import booruProperties.E621Properties;
import booruProperties.Rule34PahealProperties;
import booruProperties.StandardBooruProperties;

public class BooruDownloaderTests {

	@Test
	public void TestRetrieveHTML() throws IOException {
		String rule34xxxMainPage = "https://rule34.xxx/";
		String search = "Duraludon".toLowerCase();

		BooruProperties booruProperties = new StandardBooruProperties(rule34xxxMainPage);
		StandardBooruParser booruParser = new StandardBooruParser(booruProperties);
		String html = booruParser.getHTML(search, 1);

		System.out.println(booruProperties.getBooruPageURL(search, 1));
		System.out.println(html);

		assertTrue("HTML not found", !html.equals(""));
	}

	@Test
	public void TestRetrieveHTMLComplexSearch() throws IOException {
		String rule34xxxMainPage = "https://rule34.xxx/";
		String search = "warframe sound".toLowerCase();

		BooruProperties booruProperties = new StandardBooruProperties(rule34xxxMainPage);
		StandardBooruParser booruParser = new StandardBooruParser(booruProperties);
		String html = booruParser.getHTML(search, 1);

		System.out.println(booruProperties.getBooruPageURL(search, 1));
		System.out.println(html);

		assertTrue("HTML not found", !html.equals(""));
	}

	@Test
	public void TestRetrieveImagesUrls() {
		String rule34xxxMainPage = "https://rule34.xxx/";
		String search = "Duraludon";

		BooruProperties booruProperties = new StandardBooruProperties(rule34xxxMainPage);
		StandardBooruParser booruParser = new StandardBooruParser(booruProperties);
		Set<String> imagesURLs = booruParser.retrieveImagesURLs(search, new HashSet<>());

		for (String imageURL : imagesURLs) {
			System.out.println(imageURL);
		}
		System.out.println(imagesURLs.size());

		assertTrue("No images found", !imagesURLs.isEmpty());
	}

	@Test
	public void TestImageDownload() {
		String rule34xxxMainPage = "https://rule34.xxx/";
		String search = "Duraludon";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(rule34xxxMainPage);
		booruDownloader.interpretQuery(query);
	}

	@Test
	public void Rule34PahealTest() {
		String search = "Warframe Khora Animated";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(Rule34PahealProperties.RULE_34_PAHEAL_URL);
		booruDownloader.interpretQuery(query);
	}

	@Test
	public void Rule34PahealTestMultiplePages() {
		String search = "yveltal";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(Rule34PahealProperties.RULE_34_PAHEAL_URL);
		booruDownloader.interpretQuery(query);
	}

	@Test
	public void Rule34PahealURLFetchTest() {
		String search = "adriandustred";
		BooruProperties properties = new Rule34PahealProperties();
		BooruParser parser = new Rule34PahealParser(properties);

		parser.retrieveImagesURLs(properties.translateSearch(search), new HashSet<>());
	}

	@Test
	public void E621DownloadTest() {
		String search = "warframe ivara_(warframe) adriandustred";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(E621Properties.E621_URL);
		booruDownloader.interpretQuery(query);
	}

	@Test
	public void E621DownloadTestMultiplePages() {
		String search = "kindred_(lol) soraka";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(E621Properties.E621_URL);
		booruDownloader.interpretQuery(query);
	}

	@Test
	public void CacheTest() {
		String search = "kindred_(lol) soraka";
		Query query = new Query(search, false, false);

		BooruManager booruDownloader = new BooruManager(E621Properties.E621_URL);
		booruDownloader.interpretQuery(query);
	}

}
