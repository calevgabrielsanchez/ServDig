package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class Constantes {
	
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat FORMATO_FECHA_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final String FECHA_PIVOTE_FIRMA = "2016-01-01";


	//constantes para los tipos de usuario
//    public static final int SISTEMA_SARE = 0;
//    public static final int SISTEMA_AVCE = 1;
//    public static final int ROL_JEFE_DE_OFICINA = 0;
//    public static final int ROL_VENTANILLA = 1;
//    public static final int ROL_NORMATIVO = 2;
//    public static final int ROL_JEFE_DE_OFICINA_DELEGACIONAL = 3;
//    public static final int ROL_JEFE_DE_OFICINA_SUBDELEGACIONAL = 4;
//    public static final int ROL_VENTANILLA_SUBDELEGACIONAL = 5;
//    public static final int ROL_VENTANILLA_DELEGACIONAL = 6;
//    public static final int ROL_NORMATIVO_DELEGACIONAL = 7;
//    public static final int ROL_NORMATIVO_SUBDELEGACIONAL = 8;
//    public static final int ROL_NORMATIVO_NIVEL_CENTRAL = 9;	
    
    public static final String TEXTO_ESTATUS_PENDIENTE_DE_ANALISIS = "POR ASIGNAR";
	
	public static final String REPORTE_NOMBRE = "reporteCe";
	public static final String BITACORA_NOMBRE = "bitacoraCe";
	public static final String CONCENTRADO_NOMBRE_INSCRIPCION = "Concentrado-MAC-ImssDig-Inscripcion";
	public static final String CONCENTRADO_NOMBRE_MODIFICACIONES = "Concentrado-MAC-ImssDig-Modificacion";
	public static final String REPORTE_EXTENSION = "xls";

	public static final String CLEM_GENERAR = "Generar CLEM";
	public static final String CLEM_MODIFICAR = "Modificar CLEM";
	
	public static final int CLEM_LONGITUD_MOTIVOS = 4000;
	public static final int CLEM_LONGITUD_DESC_LUGARFECHA = 80;
	public static final int CLEM_LONGITUD_TITULAR_SUP = 50;
	public static final int CLEM_LONGITUD_PUESTO = 100;
	public static final int COMENTARIO_LONGITUD_DESC = 2500;
	public static final int CLASIFICACION_LONGITUD_ACTIVIDAD_DETECTADA = 200;
	public static final int CLASIFICACION_LONGITUD_ACTIVIDAD_DETECTADA_MAC = 40;
	
	public static final String PATH_IMAGENES = "/resources/imagenes/";
	public static final String BITACORA_IMAGEN_HEADER = "RepoheaderBitacora.png";
	public static final String REPORTE_IMAGEN_HEADER = "Repoheader.png";

	public static final int CLAVE_DEL_VERACRUZ_SUR = 32;
	public static final int CLAVE_SUBDEL_COATZACOALCOS = 45;
	public static final String INCISO_AB_VER_COAT = "ab";
	public static final String DES_INCISO_AB_VER_COAT = "a) , b)";
	//Hora de Inicio del proceso batch
	public static final String HORA_INICIAL_PROCESO_BATCH = "18";
	public static final String MINUTO_INICIAL_PROCESO_BATCH = "45";
	public static final String DURACION_PROCESO_BATCH = "5";
	public static final int IND_PROCESO_ENVIADO_SINDO = 2;
	public static final int IND_PROCESO_NO_ENVIADO_SINDO = 1;
	public static final int IND_PROCESO_NO_MODIFICACION = 0;
	
	/* POSFIJO DELEGACIONAL/SUBDELEGACIONAL*/
	public static final String CLEM_POSFIJO_DELEGACIONAL = "DM";
    public static final String CLEM_POSFIJO_SUBDELEGACIONAL = "SM";
    
	//Constantes para mostrar documentos de analisis
	public static final int TIPO_DOCUMENTO_CLEM = 1;
	public static final int TIPO_DOCUMENTO_AVISO = 57;
	public static final int TIPO_DOCUMENTO_TIP = 55;
	public static final int TIPO_DOCUMENTO_ARP = 56;
	
	public static final int CODIGO_ERROR_LONGITUD = 1;
	
	public static final String CLEM_INSCRIP_DEL = "1";
	public static final String CLEM_INSCRIP_SUBDEL = "2";
	public static final String CLEM_MOD_DEL = "3";
	public static final String CLEM_MOD_SUBDEL = "4";
	
}
