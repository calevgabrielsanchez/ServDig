/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: EstatusNoCancelacionEnum.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha: 10/01/2013
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

public enum EstatusNoCancelacionEnum {

	RATIFICADO_AUTORIZADO(5, "RATIFICADO AUTORIZADO"), 
	RECTIFICADO_AUTORIZADO(6, "RECTIFICADO AUTORIZADO"),
	CANCELADO_POR_NUEVO_TRAMITE_DE_GCE(19, "CANCELADO POR NUEVO TRAMITE DE CLASIFICACION DE EMPRESAS"),
	CANCELADO_POR_BAJA_PATRONAL(20, "CANCELADO POR BAJA PATRONAL");

	private final int clave;
	private final String descripcion;

	private EstatusNoCancelacionEnum(int clave, String descripcion) {
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