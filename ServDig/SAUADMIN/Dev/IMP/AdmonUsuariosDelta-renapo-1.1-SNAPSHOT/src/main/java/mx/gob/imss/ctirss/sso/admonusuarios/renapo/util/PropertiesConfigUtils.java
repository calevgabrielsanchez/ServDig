package mx.gob.imss.ctirss.sso.admonusuarios.renapo.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Utileria para otener los parametros de configuracion.
 * 
 * @author lyj.diaz
 *
 */
public final class PropertiesConfigUtils {

	private static final Logger LOG = Logger.getLogger(PropertiesConfigUtils.class);

	private static final ResourceBundle resourceConfigAPP = ResourceBundle
			.getBundle(SauAdminConstants.CONFIG_APP);

	private static final ResourceBundle resourceBundleException = ResourceBundle
			.getBundle(SauAdminConstants.EXCEPTION_FILE);

	/**
	 * Default Constructor.
	 */
	private PropertiesConfigUtils() {
		super();
	}

	public static String getPropertyService(String key, Object... params) {
		return getString(resourceConfigAPP.getString(key), params);
	}

	public static String getPropertyException(String key, Object... params) {

		return getString(resourceBundleException.getString(key), params);
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
			LOG.error("Exception", e);
		}

		return mensaje;
	}

}
