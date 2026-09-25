package mx.gob.imss.ctirss.delta.gestion.individuo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReader {

	private final transient String path;
	private final transient ClassLoader classLoader;

	public InputStreamReader(final String path) {
		this.path = path;
		this.classLoader = getDefaultClassLoader();
	}

	public InputStream getInputStream() throws IOException {
		final InputStream inStream = this.classLoader.getResourceAsStream(this.path);
		if (inStream == null) {
			throw new FileNotFoundException(path + " cannot be opened because it does not exist");
		}
		return inStream;
	}

	private ClassLoader getDefaultClassLoader() {
		ClassLoader classLoader = null; // NOPMD
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) { // NOPMD
			// Cannot access thread context ClassLoader - falling back to system
			// class loader...
		}
		if (classLoader == null) {
			// No thread context class loader -> use class loader of this class.
			// Original:
			// classLoader = InputStreamReader.class.getClassLoader();
			classLoader = Thread.currentThread().getContextClassLoader();
		}
		return classLoader;
	}
}
