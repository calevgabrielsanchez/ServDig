package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RazonResultado extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1571604089913585749L;
	private Long idRazonResultado;
	private String descripcion;

	public Long getIdRazonResultado() {
		return idRazonResultado;
	}

	public void setIdRazonResultado(Long idRazonResultado) {
		this.idRazonResultado = idRazonResultado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
