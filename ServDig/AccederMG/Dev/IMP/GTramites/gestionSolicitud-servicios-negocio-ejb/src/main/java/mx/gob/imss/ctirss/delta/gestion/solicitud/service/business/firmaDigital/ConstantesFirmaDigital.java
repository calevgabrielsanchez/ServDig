package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

public class ConstantesFirmaDigital {

	// CERTIFICADO FIEL
	public static final String ENDPOINT_WS_FIRMA_FIEL = "http://172.26.18.128:10010/WSFirmaDigital/services/WSFirmaDigital";
	// public static final String ENDPOINT_WS_FIRMA_FIEL = "http://172.24.116.23:10000/WSFirmaDigital/services/WSFirmaDigital";
	// nuevo public static final String ENDPOINT_WS_FIRMA_FIEL ="http://11.254.14.177:14100/WSFirmaDigital/services/WSFirmaDigital";

	public static final String RFC_DIFERENTE_PATRON = "EL RFC DEL CERTIFICADO NO ES IGUAL AL RFC DEL PATRON O AL RFC DEL REPRESENTANTE LEGAL";
	public static final String CERTIFICADO_VALIDO = "VALIDO";
	public static final String CERTIFICADO_SAT = "SAT";
	public static final String CERTIFICADO_INVALIDO = "EL CERTIFICADO SAT INGRESADO ES INVALIDO";
	public static final String CERTIFICADO_CADUCADO = "EXPIRADO";
	public static final String SERVICIO_FIEL_NO_DISPONIBLE = "EL SERVICIO DE VALIDACION DE CERTIFICADO DIGITAL NO ESTA DISPONIBLE EN ESTE MOMENTO";

	public static final int CODIGO_RESPUESTA_VALIDO = 1;
	public static final int NO_CERTIFICADO_SAT = 2;
	
	
	// CERTIFICADO IMSS
	// public static final String ENDPOINT_WS_FIRMA_IMSS = "http://11.254.14.41:10400/seguriws/PKI.jws";
	public static final String ENDPOINT_WS_FIRMA_IMSS = "http://11.254.171.61:11302/seguriws/PKI.jws";
}
