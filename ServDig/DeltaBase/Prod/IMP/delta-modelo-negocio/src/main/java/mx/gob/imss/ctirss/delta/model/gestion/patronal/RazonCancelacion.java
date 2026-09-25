package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RazonCancelacion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4739885111533630929L;
	private Long idRazonCancelacion;
	private String descripcion;

	public Long getIdRazonCancelacion() {
		return idRazonCancelacion;
	}

	public void setIdRazonCancelacion(Long idRazonCancelacion) {
		this.idRazonCancelacion = idRazonCancelacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
