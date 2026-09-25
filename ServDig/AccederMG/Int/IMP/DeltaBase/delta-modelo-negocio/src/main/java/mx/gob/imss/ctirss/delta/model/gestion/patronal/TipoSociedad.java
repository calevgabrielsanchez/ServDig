/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: TipoSociedad.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 * @Fecha: 17:00:28
 */
public class TipoSociedad extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7171283971464137577L;
	private Long idTipoSociedad;
	private String descripcion;
	private String descripcionAbreviada;

	/**
	 * @return the idTipoSociedad
	 */
	public Long getIdTipoSociedad() {
		return idTipoSociedad;
	}

	/**
	 * @param idTipoSociedad
	 *            the idTipoSociedad to set
	 */
	public void setIdTipoSociedad(Long idTipoSociedad) {
		this.idTipoSociedad = idTipoSociedad;
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

	/**
	 * @return the descripcionAbreviada
	 */
	public String getDescripcionAbreviada() {
		return descripcionAbreviada;
	}

	/**
	 * @param descripcionAbreviada the descripcionAbreviada to set
	 */
	public void setDescripcionAbreviada(String descripcionAbreviada) {
		this.descripcionAbreviada = descripcionAbreviada;
	}

}
