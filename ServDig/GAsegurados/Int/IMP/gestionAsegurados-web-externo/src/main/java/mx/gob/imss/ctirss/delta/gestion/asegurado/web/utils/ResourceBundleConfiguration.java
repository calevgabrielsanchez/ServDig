package mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundleWeb = null;
	
	  public static ResourceBundle getResourceProperties() {
	    if (resourceBoundleWeb == null) {
	    	resourceBoundleWeb = ResourceBundle.getBundle("config-aplication");
	    }
	    return resourceBoundleWeb;
	  }
	  
	
}
