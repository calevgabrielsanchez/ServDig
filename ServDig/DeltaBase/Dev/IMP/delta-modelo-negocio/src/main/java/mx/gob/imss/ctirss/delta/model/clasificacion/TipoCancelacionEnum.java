/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: MensajesCancelacionEnum.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha: 10/01/2013
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

public enum TipoCancelacionEnum {

	CANCELADO_POR_NUEVO_TRAMITE_DE_GCE(19, "ANALISIS IMPROCEDENTE POR EXISTENCIA DE UNA NUEVA SOLICITUD"),
	CANCELADO_POR_BAJA_PATRONAL(20, "ANALISIS IMPROCEDENTE POR BAJA PATRONAL");

	private final int clave;
	private final String descripcion;

	private TipoCancelacionEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getClave() {
		return clave;
	}
}