package mx.gob.imss.ctirss.sso.admonusuarios.ttds.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundle = null;

	  public static ResourceBundle getResourceBundle() {
	    if (resourceBoundle == null) {
	      resourceBoundle = ResourceBundle.getBundle("properties.configTTD");
	    }
	    return resourceBoundle;
	  }

}
