package mx.gob.imss.ctirss.cliente.clasificacion.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {

	private static ResourceBundle resourceBoundle = null;

	public static ResourceBundle getResourceBundle() {
		if (resourceBoundle == null) {
			resourceBoundle = ResourceBundle.getBundle("config");
		}
		return resourceBoundle;
	}

}