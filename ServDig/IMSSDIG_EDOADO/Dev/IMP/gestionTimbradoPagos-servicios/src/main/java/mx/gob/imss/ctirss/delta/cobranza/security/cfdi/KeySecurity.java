package mx.gob.imss.ctirss.delta.cobranza.security.cfdi;

import java.util.ResourceBundle;

public class KeySecurity {

	protected final static ResourceBundle resourceBoundle = ResourceBundle.getBundle("properties.configuracion");
	
	protected final static String PATH_FILE_KEY_DIGITAL = resourceBoundle.getString("ruta.llave.digital");
	protected final static String PASSWORD_KEY_DIGITAL = resourceBoundle.getString("password.llave.digital");    
	protected final static String PATH_FILE_CERT_DIGITAL = resourceBoundle.getString("ruta.certificado.digital");
	protected final static String PATH_FILE_XML = resourceBoundle.getString("ruta.archivo.xml.generado");
    
}
