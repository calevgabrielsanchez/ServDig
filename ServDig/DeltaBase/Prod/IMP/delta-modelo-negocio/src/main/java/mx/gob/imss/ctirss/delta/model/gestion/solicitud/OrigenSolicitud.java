package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class OrigenSolicitud extends AbstractModel {

	private static final long serialVersionUID = -4516658781588680627L;

	private Long idOrigenSolicitud;

	private String descripcion;

	public Long getIdTipoSolicitud() {
		return idOrigenSolicitud;
	}
	public void setIdTipoSolicitud(Long idTipoSolicitud) {
		this.idOrigenSolicitud = idTipoSolicitud;
	}
	
	//Se agregan get y set de idOrigenSolicitud
	public Long getIdOrigenSolicitud() {
		return idOrigenSolicitud;
	}

	public void setIdOrigenSolicitud(Long idOrigenSolicitud) {
		this.idOrigenSolicitud = idOrigenSolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
