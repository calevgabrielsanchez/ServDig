package mx.gob.imss.ctirss.sso.admonusuarios.siap.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundle = null;

	  public static ResourceBundle getResourceBundle() {
	    if (resourceBoundle == null) {
	      resourceBoundle = ResourceBundle.getBundle("properties.config");
	    }
	    return resourceBoundle;
	  }

}
