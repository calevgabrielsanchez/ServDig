package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MateriaPrimaTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4962808887192133503L;
	private Long id;
	private String descripcion;
	private Long idRegistroPatronal;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
}
