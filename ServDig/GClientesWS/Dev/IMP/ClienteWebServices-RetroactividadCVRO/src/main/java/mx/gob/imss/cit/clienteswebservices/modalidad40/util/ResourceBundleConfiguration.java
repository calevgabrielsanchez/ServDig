package mx.gob.imss.cit.clienteswebservices.modalidad40.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundle = null;

	  public static ResourceBundle getResourceServicioExterno() {
	    if (resourceBoundle == null) {
	      resourceBoundle = ResourceBundle.getBundle("modalidad-40-config-externo");
	    }
	    return resourceBoundle;
	  }
	  
	  
	
}
