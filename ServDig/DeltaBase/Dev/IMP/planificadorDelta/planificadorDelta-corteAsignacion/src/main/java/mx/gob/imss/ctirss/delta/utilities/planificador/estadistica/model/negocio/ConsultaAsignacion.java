package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.Date;

public class ConsultaAsignacion {
	private String folioSolicitud;
	private Long cveIdPersonaAsig;
	private String numNss;
	private Long cveIdAsignacionNss;
	private String curp;
	private Date fecSolicitud;
	private Date fecConclusionSol;
	private Date fecRegistroAltaAsig;
	private Date fecRegistroBajaAsig;
	private Date fecRegistroActualizadoAsig;

	public ConsultaAsignacion() {
	}

	public ConsultaAsignacion(String folioSolicitud, Long cveIdPersonaAsig,
			String numNss, Long cveIdAsignacionNss, String curp,
			Date fecSolicitud, Date fecConclusionSol, Date fecRegistroAltaAsig,
			Date fecRegistroBajaAsig, Date fecRegistroActualizadoAsig) {
		this.folioSolicitud = folioSolicitud;
		this.cveIdPersonaAsig = cveIdPersonaAsig;
		this.numNss = numNss;
		this.cveIdAsignacionNss = cveIdAsignacionNss;
		this.curp = curp;
		this.fecSolicitud = fecSolicitud;
		this.fecConclusionSol = fecConclusionSol;
		this.fecRegistroAltaAsig = fecRegistroAltaAsig;
		this.fecRegistroBajaAsig = fecRegistroBajaAsig;
		this.fecRegistroActualizadoAsig = fecRegistroActualizadoAsig;
	}

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	public Long getCveIdPersonaAsig() {
		return cveIdPersonaAsig;
	}

	public void setCveIdPersonaAsig(Long cveIdPersonaAsig) {
		this.cveIdPersonaAsig = cveIdPersonaAsig;
	}

	public String getNumNss() {
		return numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public Long getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}

	public void setCveIdAsignacionNss(Long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFecSolicitud() {
		return fecSolicitud;
	}

	public void setFecSolicitud(Date fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
	}

	public Date getFecConclusionSol() {
		return fecConclusionSol;
	}

	public void setFecConclusionSol(Date fecConclusionSol) {
		this.fecConclusionSol = fecConclusionSol;
	}

	public Date getFecRegistroAltaAsig() {
		return fecRegistroAltaAsig;
	}

	public void setFecRegistroAltaAsig(Date fecRegistroAltaAsig) {
		this.fecRegistroAltaAsig = fecRegistroAltaAsig;
	}

	public Date getFecRegistroBajaAsig() {
		return fecRegistroBajaAsig;
	}

	public void setFecRegistroBajaAsig(Date fecRegistroBajaAsig) {
		this.fecRegistroBajaAsig = fecRegistroBajaAsig;
	}

	public Date getFecRegistroActualizadoAsig() {
		return fecRegistroActualizadoAsig;
	}

	public void setFecRegistroActualizadoAsig(Date fecRegistroActualizadoAsig) {
		this.fecRegistroActualizadoAsig = fecRegistroActualizadoAsig;
	}
}
