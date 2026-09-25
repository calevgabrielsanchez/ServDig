package mx.gob.imss.ctirss.delta.cobranza.security.timbrado;

import java.util.ResourceBundle;

import mx.gob.imss.ctirss.delta.cobranza.service.business.GenerarTimbradoCFDIServiceBusiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceBoundleConfigurationCSD {
	
	  private static final Logger LOG;
	  
	  private static ResourceBundle resourceBoundle = null;
	  private static String archivoLlaveDigital = null;
	  private static String archivoCertificadoDigital = null;
	  private static String passwordKey = null;
		
	  
	   static {
		LOG = LoggerFactory.getLogger(GenerarTimbradoCFDIServiceBusiness.class);
	   }

	   
	   /**
	    * @param 
	    * @param 
	    * @return ConfigurationContext
	    * @throws 
	    */        
	    public static ResourceBundle getResourceBundle(){
	        if(resourceBoundle == null){
	            resourceBoundle = ResourceBundle.getBundle("properties.configuracion");
	        }        
	        return resourceBoundle;
	    }
	   
	   /**
	    * @param 
	    * @param 
	    * @return ConfigurationContext
	    * @throws 
	    */
	    public static String getArchivoLlaveDigital(ResourceBundle resourceBoundle) {
	        
	        if(archivoLlaveDigital == null){
	            archivoLlaveDigital = resourceBoundle.getString("ruta.llave.digital");            
	        }
	        
	        return archivoLlaveDigital;
	    }
	    
	   /**
	    * @param 
	    * @param 
	    * @return ConfigurationContext
	    * @throws 
	    */
	    public static String getArchivoCertificadoDigital(ResourceBundle resourceBoundle){
	        
	        if(archivoCertificadoDigital == null){
	             archivoCertificadoDigital = resourceBoundle.getString("ruta.certificado.digital");             
	        }        
	        return archivoCertificadoDigital;
	    }
	    
	    /**
	    * @param 
	    * @param 
	    * @return ConfigurationContext
	    * @throws 
	    */
	    public static String getpasswordLlaveDigital(ResourceBundle resourceBoundle){
	        if(passwordKey == null){
	            passwordKey = resourceBoundle.getString("password.llave.digital");
	        }        
	        return passwordKey;
	    }

}
