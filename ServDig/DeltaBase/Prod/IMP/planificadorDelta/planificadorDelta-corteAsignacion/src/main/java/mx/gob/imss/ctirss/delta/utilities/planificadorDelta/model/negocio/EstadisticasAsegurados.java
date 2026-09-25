package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.util.Date;

public class EstadisticasAsegurados {
	private String nss;
	private String folioSolicitud;
	private Date fechaSolicitud;
	private Date fechaConclusion;

	

	public EstadisticasAsegurados(String nss, String folioSolicitud,
			Date fechaSolicitud, Date fechaConclusion) {
		this.nss = nss;
		this.folioSolicitud = folioSolicitud;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaConclusion = fechaConclusion;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
}
