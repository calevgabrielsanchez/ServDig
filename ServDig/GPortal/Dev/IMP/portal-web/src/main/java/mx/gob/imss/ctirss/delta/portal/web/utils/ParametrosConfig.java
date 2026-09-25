package mx.gob.imss.ctirss.delta.portal.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.core.io.ClassPathResource;

public class ParametrosConfig {

	private static PropertiesConfiguration configuration = null;

	static {
		
		String property = null;
		Properties properties = new Properties();
		InputStream is = null;

		try {
			is = new ClassPathResource("params.properties")
					.getInputStream();
			
			properties.load(is);
			is.close();

			property = properties.getProperty("url_archivo");

			configuration = new PropertiesConfiguration(property);
			configuration.setDelimiterParsingDisabled(true);
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	public static synchronized String getParametro (final String paramKey) {
		return configuration.getString(paramKey);
	}

}
