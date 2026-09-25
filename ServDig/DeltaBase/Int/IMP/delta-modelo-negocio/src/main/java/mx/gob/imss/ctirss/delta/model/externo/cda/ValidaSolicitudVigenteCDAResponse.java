package mx.gob.imss.ctirss.delta.model.externo.cda;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class ValidaSolicitudVigenteCDAResponse extends AbstractResponseExterno implements Serializable {

	private static final long serialVersionUID = -6886781487407744581L;

	private Integer idEstadoSolicitud;
	private String descripcionSolicitud;
	private String numeroSolicitud;
	private String fechaActualizacion;

	public Integer getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}

	public String getDescripcionSolicitud() {
		return descripcionSolicitud;
	}

	public void setDescripcionSolicitud(String descripcionSolicitud) {
		this.descripcionSolicitud = descripcionSolicitud;
	}

	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
