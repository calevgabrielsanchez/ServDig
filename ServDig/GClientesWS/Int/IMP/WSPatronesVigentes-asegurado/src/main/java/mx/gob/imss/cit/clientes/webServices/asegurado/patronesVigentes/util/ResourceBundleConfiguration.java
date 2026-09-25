package mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundle = null;

	  public static ResourceBundle getResourceServicioExterno() {
	    if (resourceBoundle == null) {
	      resourceBoundle = ResourceBundle.getBundle("config-externo");
	    }
	    return resourceBoundle;
	  }
	  
	  
	
}
