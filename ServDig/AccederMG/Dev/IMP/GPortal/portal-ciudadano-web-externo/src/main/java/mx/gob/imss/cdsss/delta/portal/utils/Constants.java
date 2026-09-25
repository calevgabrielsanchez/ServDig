package mx.gob.imss.cdsss.delta.portal.utils;


public class Constants {
	// Variables en Sesion
	public static final String KEY_CABEZA_GRUPO = "keyCabezaGrupoFamiliarSession";
	public static final String KEY_ASIGNACION_NSS = "keyAsignacionNSSSession";
	public static final String KEY_PATRONES_ASEGURADO = "keyPatronesAseguradoSession";
	public static final String KEY_IDS_MODALIDADES = "keyIdsModalidadesPatrones";
	public static final String KEY_MODALIDADES_ACTIVAS = "keyModalidadesActivas";
	public static final String KEY_PATRON_IMSS = "keyAseguradoPatronImss";
	public static final String KEY_DATOS_ASEGURADO = "keyDatosAseguradoSession";
	public static final String KEY_CIUDADANO_SESSION = "ciudadano";
	public static final String KEY_SOLICITUD = "keySolicitudSession";
	public static final String KEY_FOLIO_SOLICITUD = "keyFolioSolSession";
	public static final String KEY_DATOS_DOM_UMF = "keyDatosUmfDom";
	public static final String KEY_CORREO_CIUDADANO = "keyCorreoCiudadano";
	public static final String KEY_TRAMITE = "keytramite";
	public static final String KEY_DOCTOS_REENVIADOS = "keyDoctosReenviados";
	public static final String KEY_DOCUMENTO_TRAMITE = "keyDocumentoTramite";
	public static final String KEY_ACUSE_TRAMITE = "keyAcuseSession";
	public static final String KEY_LONGITUD_UMF = "keyLongitudUmf";
	public static final String KEY_LATITUD_UMF = "keyLatitudUmf";
	public static final String KEY_FORM_CON_NSS = "keyFormConNSS";
	public static final String KEY_TIPO_TRAMITE = "keyCveTipoTramite";
	public static final String KEY_HOMOCLAVE = "keyHomoclaveTramite";
	public static final String KEY_CAPTCHA_SESSION = "captcha";
	
	//varialbes de session para actualizar solicitudNssCorreo y curpCiudadano
	public static final String KEY_ACTUALIZA_PORTAL_CIUDADANO_CORREO = "keyActualizaPortalCorreo";
	public static final String KEY_ACTUALIZA_ASIGNACION_CORREO = "keyActualizaAsignacionCorreo";
	public static final String KEY_TRAMITE_CAMBIO_CURP = "keyTramiteCambioCurp";
	
	// View
	public static final String VIEW_SELECCION_DOMICILIO_UMF = "seleccionUmf";
	public static final String KEY_VIEW_CLINICA = "seleccionUmf";
	public static final String KEY_VIEW_FINALIZAR_SOLICITUD = "detalleSolicitud";
	public static final String KEY_VIEW_GENERAL_DHABIENTES = "derechohabientes";
	public static final String KEY_VIEW_TERMINOS_ACTUALIZA_CURP = "terminosActualizaCURP";

	//mensajes de error
	public static String ERROR_CONSULTA_NSS = "Ocurri&oacute; un error al consultar el NSS. ";
	public static String ERROR_CONSULTA_INTEGRANTE_ASEGURADO = "Ocurri&oacute; un error al consultar al integrante cabeza de grupo familiar. ";
	public static String ERROR_SIN_NSS = "Para realizar el tr&aacute;mite de registro es necesario que la persona cuente con un NSS asociado.";
	public static String ERROR_ESTUDIANTE = "El NSS proporcionado corresponde al de un estudiante, para realizar el tr&aacute;mite es necesario" +
	"acudir a ventanilla.";
	public static String ERROR_PATRONES = "Ocurri&oacute; un error al consultar a los patrones del asegurado/pensionado. ";
	public static String ERROR_VIGENCIA = "Para poder realizar su registro como derechohabiente usted debe " +
	" contar una relaci&oacute;n laboral o ser un pensionado.";
	public static String ERROR_YA_REGISTRADO = "Usted ya se encuentra registrado como derechohabiente.";
	public static String ERROR_REGISTRO_ASEGURADO_NECESARIO = "Para el registro de beneficiarios es requerido" +
	" que usted ya cuente con un registro como derechohabiente.";
	public static String ERROR_DOMICILIO_UMF_NECESARIO = "Usted no cuenta con domicilio y/o UMF y esta informaci&oacute;n es requerida para el  registro de beneficiarios, "+ 
	"seleccione la opci&oacute;n de cambio de cl&iacute;nica o acuda a su UMF m&aacute;s cercana.";
	public static String ERROR_CONSULTA_CABEZA = "Ocurri&oacute; un error al consultar la cabeza de grupo familiar.";

}
