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
 * @Archivo: EstadoCivil.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.individuo
 * @Fecha: 13:06:07
 */
public class EstadoCivil extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4769338214556479392L;
	private Integer idEstadoCivil;
	private String descripcion;

	/**
	 * @return the idEstadoCivil
	 */
	public Integer getIdEstadoCivil() {
		return idEstadoCivil;
	}

	/**
	 * @param idEstadoCivil
	 *            the idEstadoCivil to set
	 */
	public void setIdEstadoCivil(Integer idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
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
