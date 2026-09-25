package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.util.Date;

public class ConsultaCambioClinica {
	private Integer cveCiz;
	private String folioSolicitud;
	private String desTipoSolicitud;
	private Long cveIdPersonaAsig;
	private String numNss;
	private Long cveIdAsignacionNss;
	private String curp;
	private Date fecSolicitud;
	private Date fecConclusionSol;

	public ConsultaCambioClinica() {
	}

	public ConsultaCambioClinica(Integer cveCiz, String folioSolicitud,
			String desTipoSolicitud, Long cveIdPersonaAsig, String numNss,
			Long cveIdAsignacionNss, String curp, Date fecSolicitud,
			Date fecConclusionSol) {
		this.cveCiz = cveCiz;
		this.folioSolicitud = folioSolicitud;
		this.desTipoSolicitud = desTipoSolicitud;
		this.cveIdPersonaAsig = cveIdPersonaAsig;
		this.numNss = numNss;
		this.cveIdAsignacionNss = cveIdAsignacionNss;
		this.curp = curp;
		this.fecSolicitud = fecSolicitud;
		this.fecConclusionSol = fecConclusionSol;
	}

	public Integer getCveCiz() {
		return cveCiz;
	}

	public void setCveCiz(Integer cveCiz) {
		this.cveCiz = cveCiz;
	}

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	public String getDesTipoSolicitud() {
		return desTipoSolicitud;
	}

	public void setDesTipoSolicitud(String desTipoSolicitud) {
		this.desTipoSolicitud = desTipoSolicitud;
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
}
