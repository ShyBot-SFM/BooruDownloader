package booruDownloader;

import java.util.Set;

import booruProperties.BooruProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;

public class BooruCache {

	private Set<String> urlsCache;
	private BooruProperties booruProperties;

	public BooruCache(BooruProperties booruProperties) {
		this.urlsCache = Collections.synchronizedSet(new HashSet<String>());
		this.booruProperties = booruProperties;
	}

	public void loadCache() throws IOException {
		File cacheFile = new File(booruProperties.getDownloadDirectory() + "cache");
		if (!cacheFile.exists()) {
			throw new IOException("Error: Cache not found");
		}

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(cacheFile);
			ois = new ObjectInputStream(fis);
			urlsCache = Collections.synchronizedSet((Set<String>) ois.readObject());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
			try {
				fis.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void storeCache() {
		String booruPath = booruProperties.getDownloadDirectory();
		File booruDirectory = new File(booruPath);
		if (!booruDirectory.exists()) {
			booruDirectory.mkdirs();
		}

		File cacheFile = new File(booruPath + "cache");
		if (!cacheFile.exists()) {
			try {
				cacheFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(cacheFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(urlsCache);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Set<String> retrieveURLsCached() {
		return new HashSet<String>(urlsCache);
	}

	public void cacheURL(String url) {
		urlsCache.add(url);
	}

}
