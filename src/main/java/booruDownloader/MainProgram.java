package booruDownloader;

import java.util.List;

public class MainProgram {

	private static BooruManager booruManager;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			booruManager.onFinishedInterpretingQuery();
			BooruDownloader.onProgramShutdown();
			BooruDownloader.printFailedDownloads();
		}));
		downloadBoorus();
	}

	public static void downloadBoorus() {
		List<BooruQueries> boorusQueries = QueriesFileParser.parseConfigFile();
		for (BooruQueries booruQueries : boorusQueries) {
			String booru = booruQueries.getBooruURL();
			List<Query> queries = booruQueries.getBooruQueries();

			if (queries.isEmpty()) {
				continue;
			}

			System.out.println("Starting " + booru + " downloads");
			booruManager = new BooruManager(booru);
			for (Query query : queries) {
				System.out.println(query.getQuery().toLowerCase());
				booruManager.interpretQuery(query);
				booruManager.onFinishedInterpretingQuery();
				System.out.println();
			}
			System.out.println();
		}

		System.out.println("All downloads finished");
		BooruDownloader.onProgramShutdown();
	}

}
