package mx.gob.imss.dacvass.scheduler.cron.service.utility;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesConfigUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfigUtils.class);
	
	private static final ResourceBundle resourceBundleConfig = ResourceBundle.getBundle("properties.config");
	
	public static String getPropertyConfig(String key, Object... params) {
		return getString(resourceBundleConfig.getString(key), params);
	}
	
	private static String getString(String msg, Object... params) {
		String mensaje = null;
		try {
			if (params == null || params.length == 0) {
				mensaje = msg;
			} else {
				mensaje = MessageFormat.format(msg, params);
			}
		} catch (MissingResourceException e) {
			mensaje = "";
			LOGGER.info("Error en el format " + e);
		}
		return mensaje;
	}

}
