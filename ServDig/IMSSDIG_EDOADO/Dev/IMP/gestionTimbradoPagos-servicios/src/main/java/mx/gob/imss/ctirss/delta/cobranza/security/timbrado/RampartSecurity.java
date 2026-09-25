package mx.gob.imss.ctirss.delta.cobranza.security.timbrado;

import java.util.ResourceBundle;

public class RampartSecurity {	
	protected final static ResourceBundle resourceBoundle = ResourceBundle.getBundle("properties.rampart");
	protected final static String PATH_FILE_KEYSTORE = resourceBoundle.getString("ruta.almacen.key.rampart");
	protected final static String USER_KEYSTORE = resourceBoundle.getString("user.keystore.rampart");
	protected final static String PASSWORD_KEYSTORE = resourceBoundle.getString("password.keystore.rampart");
	protected final static String TYPE_KEYSTORE = resourceBoundle.getString("type.keystore.rampart");
	protected final static String IMSS_RFC = resourceBoundle.getString("rfc.imss.timbrado");
	protected final static String ENGAGE_RAMPART = resourceBoundle.getString("engage.security.rampart");
	protected final static String PATH_ENGAGE_RAMPART = resourceBoundle.getString("modulos.security.rampart");	
}
