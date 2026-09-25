/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.nss;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class TipoSerie extends AbstractModel {
	
	/**
	 * Clave del tipo de serie
	 */
	private Integer idTipoSerie;
	
	/**
	 * Descripcion del tipo de serie
	 */
	private String descripcion;

	/**
	 * @return the idTipoSerie
	 */
	public Integer getIdTipoSerie() {
		return idTipoSerie;
	}

	/**
	 * @param idTipoSerie the idTipoSerie to set
	 */
	public void setIdTipoSerie(Integer idTipoSerie) {
		this.idTipoSerie = idTipoSerie;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
