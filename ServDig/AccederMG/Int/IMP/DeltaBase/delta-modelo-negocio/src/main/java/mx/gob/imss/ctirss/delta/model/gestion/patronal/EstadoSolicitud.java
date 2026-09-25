package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoSolicitud extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3589713277651913484L;
	private Long idEstadoSolicitud;
	private String descripcion;

	public Long getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(Long idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
