package booruDownloader;

public class Query {

	private final String QUERY;
	private final boolean IGNORE_CACHE;
	private final boolean CACHE_ONLY;

	public Query(String query, boolean ignoreCache, boolean cacheOnly) {
		this.QUERY = query;
		this.IGNORE_CACHE = ignoreCache;
		this.CACHE_ONLY = cacheOnly;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((QUERY == null) ? 0 : QUERY.hashCode());
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
		Query other = (Query) obj;
		if (QUERY == null) {
			if (other.QUERY != null)
				return false;
		} else if (!QUERY.equals(other.QUERY))
			return false;
		return true;
	}

	public String getQuery() {
		return QUERY;
	}

	public boolean getIgnoreCache() {
		return IGNORE_CACHE;
	}

	public boolean getCacheOnly() {
		return CACHE_ONLY;
	}

}
