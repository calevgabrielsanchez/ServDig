package mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility;


public abstract class BeneficiosConstants {

	//Constantes generales
	public static final String FORMAT_DATE_GUINMEDIO_dd_MM_yyyy = "dd-MM-yyyy";
	public static final String FORMAT_DATE_DIAGONAL_dd_MM_yyyy = "dd/MM/yyyy";
	public static final String FORMAT_DATE_FORMATO_LARGO_1 = "dd 'de' MMMM yyyy, HH:mm:ss";
	public static final String FORMAT_DATE_FORMATO_LARGO_2 = "MMMM d 'de' yyyy',' HH:mm:ss";
	public static final String LOCALE_es = "es";
	public static final String LOCALE_MX = "MX";
	public static final String CONSTANTE_SI = "SI";	
	public static final String CONSTANTE_NO = "NO";
	public static final int IMAGEN_QR_ANCHO = 160;
	public static final int IMAGEN_QR_ALTO = 150;
	
	//Reportes
	public static final String REPORTES_EXTENCION_PDF = ".pdf";
	public static final String REPORTES_NOMBRE_BENEFICIO_RISS = "ReporteRiss";
	public static final String REPORTES_BENEFICIO_RISS_JASPER = "reportes/BeneficioRISSGOBMX.jasper";
	public static final String REPORTES_IMAGENES_DIR = "IMAGENES_DIR";
	public static final String REPORTES_SUBREPORT_DIR = "SUBREPORT_DIR";
	public static final String REPORTES_RISS_CLASS_PATH = "reportes/";
	//Parametros reporte	
	public static final String REPORTES_PARAM_NOMBRE_COMPLETO = "nombreCompleto";
	public static final String REPORTES_PARAM_NSS = "nss";
	public static final String REPORTES_PARAM_RFC = "rfc";
	public static final String REPORTES_PARAM_CURP = "curp";	
	public static final String REPORTES_PARAM_NRPS = "nrps";
	public static final String REPORTES_PARAM_MOSTRAR_NRPS = "mostrarNRPs";
	public static final String REPORTES_PARAM_FOLIO_SOLICITUD = "folioSolicitud";
	public static final String REPORTES_PARAM_FECHA_REPORTE = "fechaReporte";
	public static final String REPORTES_PARAM_MOSTRAR_FIRMA_AUTOGRAFA = "mostrarFirmaAutografa";
	public static final String REPORTES_PARAM_CADENA_ORIGINAL = "cadenaOriginal";
	public static final String REPORTES_PARAM_SECUENCIA_NOTARIAL = "secuenciaNotaria";
	public static final String REPORTES_PARAM_SELLO_DIGITAL = "selloDigital";
	public static final String REPORTES_PARAM_NUMERO_SERIE = "numeroSerie";
	public static final String REPORTES_PARAM_IMAGEN_QR = "imagenQR";
	
	/**
	 * Archivo propeties 
	 */
	public static final String ARCHIVO_PROPERTIES = "urlConfig.properties";
	/**
	 * Identificador para la URL de IMSS Digital (urlConfig.properties)
	 */
	public static final String URL_IMSS_DIGITAL = "URL_IMSS_DIGITAL";
	/**
	 * Identificador para el URI del WS para validar si el RFC es RIF ante el SAT.
	 */
	public static final String URI_WS_VALIDA_RIF_SAT = "uri.wsOsb.validaSatDerechoRif";
	    
}
