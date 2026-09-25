/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: Constantes.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.util
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class Constantes {
	
	public static final DateFormat FORMATO_FECHA_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat FORMATO_FECHA_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat FORMATO_FECHA_YYYY = new SimpleDateFormat("yyyy");
	
    public static final String TEXTO_ESTATUS_PENDIENTE_DE_ANALISIS = "POR ASIGNAR";
	public static final String CLEM_GENERAR = "Generar CLEM";
	public static final String CLEM_MODIFICAR = "Modificar CLEM";
	
	public static final String TEXTO_ENCABEZADO_NRP = "NRP";
	public static final String TEXTO_ENCABEZADO_NRP_PADRE = "NRP PADRE";
	public static final String TEXTO_ENCABEZADO_RAZON_SOCIAL = "NOMBRE O RAZON SOCIAL";
	public static final String TEXTO_ENCABEZADO_ID_DELEGACION = "ID";
	public static final String TEXTO_ENCABEZADO_DELEGACION = "DELEGACION";
	public static final String TEXTO_ENCABEZADO_ID_SUBDELEGACION = "ID";
	public static final String TEXTO_ENCABEZADO_SUBDELEGACION = "SUBDELEGACION";
	public static final String TEXTO_ENCABEZADO_MUNICIPIO = "MUNICIPIO";
	public static final String TEXTO_ENCABEZADO_TIPO_MODIFICACION = "TIPO DE MODIFICACION";
	public static final String TEXTO_ENCABEZADO_FECHA_REGISTRO = "FECHA PRESENTACION";
	public static final String TEXTO_ENCABEZADO_TIPO_PERSONA = "TIPO DE PERSONA";
	public static final String TEXTO_ENCABEZADO_TIPO_REGISTRO = "TIPO DE REGISTRO";
	public static final String TEXTO_ENCABEZADO_TIPO_MOVIMIENTO = "TIPO DE MOVIMIENTO";
	public static final String TEXTO_ENCABEZADO_TIPO_TRAMITE = "TIPO DE TRAMITE";
	public static final String TEXTO_ENCABEZADO_ESTATUS = "ESTATUS";
	
	public static final String TEXTO_FECHA_REVISION = "FECHA DE REVISION";
	public static final String TEXTO_FECHA_AUTORIZACION = "FECHA DE AUTORIZACION";
	public static final String TEXTO_CLASE_DECLARADA = "CLASE DECLARADA";
	public static final String TEXTO_FRACCION_DECLARADA = "FRACCION DECLARADA";
	public static final String TEXTO_PRIMA_DECLARADA = "PRIMA DECLARADA";
	public static final String TEXTO_CLASE_RECTIFICADA = "CLASE RECTIFICADA";
	public static final String TEXTO_FRACCION_RECTIFICADA = "FRACCION RECTIFICADA";
	public static final String TEXTO_PRIMA_RECTIFICADA = "PRIMA RECTIFICADA";
	public static final String TEXTO_FOLIO_RESOLUCION = "FOLIO DE RESOLUCION";
	public static final String TEXTO_DES_COMENTARIO = "COMENTARIO";
	public static final String TEXTO_PRIMA_RESULTANTE = "PRIMA RESULTANTE";
	public static final String TEXTO_ENCABEZADO_CIZ = "CIZ";
	public static final String TEXTO_ENCABEZADO_MODIFICADA = "AUTORIZACION MODIFICADA";
	public static final String TEXTO_ENCABEZADO_MOD_CLEM = "MODIFICACIONES CLEM";
	
	public static final String PATH_IMAGENES = "imagenes/";
	public static final String REPORTE_IMAGEN_HEADER = "Repoheader.png";
	public static final String REPORTE_IMAGEN_HEADER_INSCRIPCION = "RepoheaderInscripcion.png";
	public static final String REPORTE_IMAGEN_HEADER_MODIFICACION = "RepoheaderModificacion.png";
	public static final String BITACORA_IMAGEN_HEADER = "RepoheaderBitacora.png";
	public static final String BITACORA_IMAGEN_HEADER_INSCRIPCION = "RepoheaderBitacoraInscripcion.png";
	public static final String BITACORA_IMAGEN_HEADER_MODIFICACION = "RepoheaderBitacoraModificacion.png";
	public static final String PATH_REPORTES = "reportes/";
	public static final String PDF_DELEGACIONAL_MODIFICACION = "Clem04Delegacional.jasper";
	public static final String PDF_SUBDELEGACIONAL_MODIFICACION = "Clem04Subdelegacional.jasper";
	public static final String PDF_DELEGACIONAL_INICIAL = "Clem04DelegacionalInscripcion.jasper";
	public static final String PDF_SUBDELEGACIONAL_INICIAL = "Clem04SubdelegacionalInscripcion.jasper";
	public static final String PDF_HOJA_ANALISIS = "HojaAnalisis.jrxml";
	
	//Agregado JJGV 26/01/2012
	public static final String TEXTO_BITACORA_USUARIO = "USUARIO RESPONSABLE";
	public static final String TEXTO_BITACORA_ACCION = "ACCION";
	public static final String TEXTO_BITACORA_COMENTARIO = "COMENTARIO";
	public static final String TEXTO_BITACORA_FECHA = "FECHA ACTIVIDAD";
	public static final String TEXTO_BITACORA_NRP = "NUM. DE REGISTRO PATRONAL";

	//constantes para los tipos de CLEM
	public static final int CLEM_DELEGACIONAL = 1;
	public static final int CLEM_SUBDELEGACIONAL = 2;
    public static final String CLEM_POSFIJO_DELEGACIONAL = "D";
    public static final String CLEM_POSFIJO_SUBDELEGACIONAL = "S";
    public static final String CLEM_POSFIJO_SECUENCIA_FOLIO = "FOLIO_CLEM_";
    public static final String CLEM_DESC_SECUENCIA_FOLIO = "SECUENCIA ANUAL DEL FOLIO DEL CLEM";
    public static final String CLEM_POSFIJO_SECUENCIA_FOLIO_INSCRIPCION = "F_CLEM_INS_";
    public static final String CLEM_DESC_SECUENCIA_FOLIO_INSCRIPCION = "SECUENCIA ANUAL DEL FOLIO DEL CLEM INS";
    public static final int CLAVE_DEL_VERACRUZ_SUR = 32;
	public static final int CLAVE_SUBDEL_COATZACOALCOS = 122;//Anteriormenete tenía un 45
	public static final String INCISO_AB_VER_COAT = "ab";
	public static final String DES_INCISO_AB_VER_COAT = "a) , b)";
	public static final int TOTAL_CARACTERES_FOLIO = 7;
	public static final int TOTAL_CARACTERES_FOLIO_INSCRIPCION = 4;

	//banderas de datos activos
	public static final BigInteger IND_ACTIVO= BigInteger.valueOf(1l);
	public static final BigInteger IND_NO_ACTIVO= BigInteger.valueOf(0l);
	
	//Hora de Inicio del proceso batch
	public static final String HORA_INICIAL_PROCESO_BATCH = "18";
	public static final String MINUTO_INICIAL_PROCESO_BATCH = "55";
	public static final String DURACION_PROCESO_BATCH = "15";
	
	//Constantes para mostrar documentos de analisis
	public static final int TIPO_DOCUMENTO_CLEM = 1;
	public static final String QUERY_EXISTE_DOCUMENTO_ANALISIS_CLEM = "select dc.cveIdClem, dc.acuse from DitDatosClem dc, DitAnalisisCe ace, DitSolicitud s where s.cveIdSolicitud = :idSolicitud and ace.cveIdSolicitud = s.cveIdSolicitud and dc.ditAnalisisCe.cveIdAnalisis = ace.cveIdAnalisis and dc.fecRegistroBaja is null";
	public static final String QUERY_REF_DOCUMENTO_ANALISIS_CLEM = "select dc.refDocumento from DitDatosClem dc where dc.cveIdClem = :id";

	public static final int LONGITUD_COMENTARIOS = 2500;
	public static final int LIMITE_MESES_REPORTE = 3;
	public static final int LIMITE_MESES_CONCENTRADO = 12;
	public static final String FECHA_CAMBIO_FLUJO = "27-06-2015";
	
	public static final String FECHA_PIVOTE_FIRMA = "01-01-2017";
	public static final String CLEM_INSCRIP_DEL = "1";
	public static final String CLEM_INSCRIP_SUBDEL = "2";
	public static final String CLEM_MOD_DEL = "3";
	public static final String CLEM_MOD_SUBDEL = "4";
}
