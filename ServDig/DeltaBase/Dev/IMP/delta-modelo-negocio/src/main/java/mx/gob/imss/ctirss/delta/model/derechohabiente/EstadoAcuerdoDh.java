package mx.gob.imss.ctirss.delta.model.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoAcuerdoDh extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String descripcion;
	
	public EstadoAcuerdoDh() {
		super();
		this.id= null;
		this.descripcion = null;
	}
	
	public EstadoAcuerdoDh(Long id) {
		super();
		this.id = id;
	}

	public EstadoAcuerdoDh(Long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

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
	
}