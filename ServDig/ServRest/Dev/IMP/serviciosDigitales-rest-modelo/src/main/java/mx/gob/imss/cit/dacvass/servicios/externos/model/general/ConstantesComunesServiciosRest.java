package mx.gob.imss.cit.dacvass.servicios.externos.model.general;

import java.io.Serializable;


public class ConstantesComunesServiciosRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387513498150315113L;
	
	public static final boolean VALIDA_BAJA_CONSULTA_ASEGURADO = true;
	public static final boolean NO_VALIDA_BAJA_CONSULTA_ASEGURADO = false;
	
	public static final String  REGION_CACHE_QUERY_CATALOGOS_GENERALES ="query.catalogos.generales";
	public static final String  REGION_CACHE_QUERY_DOMICILIO ="query.domicilio.entity";
	public static final String  REGION_CACHE_QUERY_DEL_SUBDEL="query.delegacion.subdelegacion.entity";
	public static final String  REGION_CACHE_QUERY_UMF="query.umf.entity";
	public static final String  REGION_CACHE_QUERY_CLASIFICACION="query.clasificacion.entity";
	public static final String  REGION_CACHE_QUERY_CAT_COMUNES="query.catalogos.catComun";
	public static final String  CARACTER_REEMPLAZO_SERCICIO_SIAP = "#####";
	
}
