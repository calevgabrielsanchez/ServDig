package mx.gob.imss.ctirss.delta.gestion.solicitud.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GraficasConfig {

	private static PropertiesConfiguration configuration = null;

	static {
		
		String property = null;
		Properties properties = new Properties();
		InputStream is = null;

		try {
			is = new ClassPathResource("graficas_config.properties")
					.getInputStream();
			
			properties.load(is);
			is.close();

			property = properties.getProperty("url_archivo");

			configuration = new PropertiesConfiguration();
            configuration.setFileName(property);
			configuration.setDelimiterParsingDisabled(true);
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            configuration.load();
			
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
	
	public static synchronized String getProperty (final String key) {
		return configuration.getString(key);
	}

}
