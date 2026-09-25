package mx.gob.imss.dsdir.altapff.batchaltas.domain;

import java.util.Date;

public class Solicitud {

	private Long cveIdSolicitud;
	private Date fechaRegistroActualizado;

	public Long getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(Long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public Date getFechaRegistroActualizado() {
		return fechaRegistroActualizado;
	}

	public void setFechaRegistroActualizado(Date fechaRegistroActualizado) {
		this.fechaRegistroActualizado = fechaRegistroActualizado;
	}
	
	
	
}
