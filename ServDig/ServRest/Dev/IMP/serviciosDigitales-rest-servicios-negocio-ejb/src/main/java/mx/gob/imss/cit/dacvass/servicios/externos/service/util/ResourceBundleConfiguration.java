package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.ResourceBundle;

public class ResourceBundleConfiguration {
	
	private static ResourceBundle resourceBoundleServiciosDigitales = null;
	
	  public static ResourceBundle getResourceServiciosDigitales() {
	    if (resourceBoundleServiciosDigitales == null) {
	    	resourceBoundleServiciosDigitales = ResourceBundle.getBundle("config-servicios-digitales");
	    }
	    return resourceBoundleServiciosDigitales;
	  }
	  
	
}
