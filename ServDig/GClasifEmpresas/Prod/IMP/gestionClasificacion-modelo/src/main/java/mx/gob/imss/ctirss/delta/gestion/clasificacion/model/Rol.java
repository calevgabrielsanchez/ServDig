/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Joaquin Guevara
 *
 */
public class Rol extends AbstractModel {
	
	private Integer idRol;
	private String cveRol;
	
	
	public Integer getIdRol() {
		return idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	public String getCveRol() {
		return cveRol;
	}
	public void setCveRol(String cveRol) {
		this.cveRol = cveRol;
	}
	
	

}
