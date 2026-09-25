package mx.gob.imss.ctirss.correccion.session;

/**
 * Clase de constantes para alojar objetos en la sesian.
 * 
 * @author vaguirre
 * 
 */
public abstract class ConstantesSession {

	public static final String USR_SESSION = "USR_SESSION";
	public static final String DOMICILIO_GEOGRAFICO  = "DOMICILIO_GEOGRAFICO";
	public static final String DOMICILIOS_GEOGRAFICOS_MODEL  = "objDG";
	public static final String DOCUMENTO_PDF = "documento";
	public static final String NOMBRE_ARCHIVO_PDF = "nombreArchivo";
	public static final String TIPO_DESCARGA_PDF = "tipoDescarga";
	public static final String DESCARGA_PDF = "descarga";
	public static final String MUESTRA_PDF = "muestra";

	public static final String PREFIX_ATTR_CADENA = "SSO_";
	public static final String PREFIX_ATTR_PERFIL = "IMSS_PERFILES";
	public static final String PREFIX_DATO_PERFIL = "cn=";
	
	public enum ATTR_OPENAM{
		USUARIO("SSO_UID"),
		NOMBRE("SSO_CN"),
		AP_MATERNO("SSO_GIVENNAME"),
		AP_PATERNO("SSO_SN"),
		ID_DEL("SSO_DELEGACION"),
		ID_SUBDEL("SSO_SUBDELEGACION"),
		PERFIL ("SSO_PERFIL");
		private String atributo;
		
		private ATTR_OPENAM(String opcion){
			this.setAtributo(opcion);
		}

		public String getAtributo() {
			return atributo;
		}

		public void setAtributo(String atributo) {
			this.atributo = atributo;
		}

		
	}
	
}
