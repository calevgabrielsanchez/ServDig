package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoTerminoGeneral extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long clave;
	
	private String descripcion;

	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
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
