package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {

	private static ResourceBundle resourceBoundle = null;

	public static ResourceBundle getResourceBundle() {
		if (resourceBoundle == null) {
			resourceBoundle = ResourceBundle.getBundle("config-reporte");
		}
		return resourceBoundle;
	}

}
