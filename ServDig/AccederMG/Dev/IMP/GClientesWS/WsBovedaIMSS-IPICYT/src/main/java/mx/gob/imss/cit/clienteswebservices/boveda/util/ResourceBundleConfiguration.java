package mx.gob.imss.cit.clienteswebservices.boveda.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundle = null;

	  public static ResourceBundle getResourceServicioExterno() {
	    if (resourceBoundle == null) {
	      resourceBoundle = ResourceBundle.getBundle("boveda-ipicyt-config-externo");
	    }
	    return resourceBoundle;
	  }
	  
	  
	
}
