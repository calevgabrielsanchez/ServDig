package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ProductoTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5891810505848625478L;
	private String descripcion;
	private Long id;
	private Long idRegistroPatronal;

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
}
