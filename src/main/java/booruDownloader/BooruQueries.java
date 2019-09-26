package booruDownloader;

import java.util.ArrayList;
import java.util.List;

public class BooruQueries {

	private final String BOORU_URL;
	private final List<Query> QUERIES;

	public BooruQueries(String booruURL) {
		this.BOORU_URL = booruURL;
		this.QUERIES = new ArrayList<Query>();
	}

	public void addQuery(Query query) {
		if (QUERIES.contains(query)) {
			return;
		}

		QUERIES.add(query);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BOORU_URL == null) ? 0 : BOORU_URL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooruQueries other = (BooruQueries) obj;
		if (BOORU_URL == null) {
			if (other.BOORU_URL != null)
				return false;
		} else if (!BOORU_URL.equals(other.BOORU_URL))
			return false;
		return true;
	}

	public String getBooruURL() {
		return BOORU_URL;
	}

	public List<Query> getBooruQueries() {
		return new ArrayList<Query>(QUERIES);
	}

}
