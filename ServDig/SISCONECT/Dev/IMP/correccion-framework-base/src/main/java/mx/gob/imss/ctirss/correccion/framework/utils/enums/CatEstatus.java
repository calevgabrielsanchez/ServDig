/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.utils.enums;

/**
 * @author Oscar Beltran
 * Enumeracion que representa al catalogo  <code>CGC_CATSTATUS</code>
 * 
 */
public enum CatEstatus {
	
	ERROR_FOLIO_ALTERADO(0L), 
	REGISTRO_DATOS_GENERALES(1L),
	SOLICITUD_RECHAZADA(3L),
	CORRECCION_RECHAZADA(6L),
	PROSESO_DE_PRESENTACION_DE_LA_SOLICITUD_DE_CORRECCION(3L),
	PROCESO_DE_REQUERIMIENTO_DE_DOCUMENTACION(11L),
	DERIVADO_SUBDELEGACION(21L),
	FOLIO_CANCELADO(22L),
	EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION(28L),
	EN_PROCESO_ATENCION_OFICIO_PROMOCION(29L),
	ADHERIDO_AL_PROGRAMA_CORRECCION_INVITACION(30L),
	ADHERIDO_AL_PROGRAMA_CORRECCION_ESPONTANEA(31L),
	PROMOCION_REGULARIZADA(32L),
	OFICIO_INIVITACION_ATENDIDO(33L),
	CORRECCION_DERIVADA_DICTAMEN(25L),
	CORRECCION_DERIVADA_FISCALIZACION(24L),
	CORRECCION_DERIVADA_OTRA_SUBDELEGACION(21L),
	PROMOCION_CONCLUIDA_COTIZO_RAZONABLEMENTE(36L),
	
	//estatus seguimiento de la correccion
	
	//Autodeterminacian de correccian sin resultados y en proceso de aplicacian de cadula de razonabilidad.
	AUTODETERMINACION_CORRECCION_SIN_RESULTADOS(7L),
	//Autodeterminacian de correccian pagada y en proceso de aplicacian de cadula de razonabilidad.
	AUTODETERMINACION_CORRECCION_PAGADA(8L),
	//Autodeterminacian de correccian en proceso de pago y en proceso de aplicacian de cadula de razonabilidad.
	AUTODETERMINACION_CORRECCION_PROCESO_DE_PAGO(9L),	
	//Proceso de Revision.
	PROCESO_DE_REVISION(10L),
	//Correccian concluida con parametro razonable
	CORRECCION_CONCLUIDA_PARAMETRO_RAZONABLE(12L),
	//Oficio de resultados
	NOTIFICACION_OFICIO_RESULTADOS(14L),
	//Diferencias determinadas en la revisian pagadas.
	DIFERENCIAS_DETERMINADAS_REVISION_PAGADAS(16L),
	//Diferencias determinadas en la revisian, en proceso de pago.
	DIFERENCIAS_DETERMINADAS_REVISION_PROCESO_PAGO(17L),
	//Diferencias determinadas en la revisian aclaradas.
	DIFERENCIAS_DETERMINADAS_REVISION_ACLARADAS(18L)
	;
	
	
	
	/**
	 * Id de la entidad <code>cgc_catstatus</code>.
	 */
	private Long id;

	/**
	 * @param id 	 	
	 */
	private CatEstatus(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
