/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: SubEstadoValidado.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.individuo
 * @Fecha: 12:54:25
 */
public class Calificacion extends AbstractModel {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1088954754426472303L;
	private Long idCalificacion;
	private String descripcion;

	public Long getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Long idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
