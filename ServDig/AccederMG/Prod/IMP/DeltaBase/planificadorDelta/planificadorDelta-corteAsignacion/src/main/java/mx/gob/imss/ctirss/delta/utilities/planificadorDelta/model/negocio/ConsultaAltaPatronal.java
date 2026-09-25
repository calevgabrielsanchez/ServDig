package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.util.Date;

public class ConsultaAltaPatronal {
	private Integer cveCiz;
	private String folioSolicitud;
	private Long cveIdPatronSujetoObligado;
	private String regPatron;
	private String numModalidad;
	private String digVer;
	private Date fecSolicitud;
	private Date fecConclusionSol;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;

	public ConsultaAltaPatronal() {
	}

	public ConsultaAltaPatronal(Integer cveCiz, String folioSolicitud,
			Long cveIdPatronSujetoObligado, String regPatron,
			String numModalidad, String digVer, Date fecSolicitud,
			Date fecConclusionSol, Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado) {
		this.cveCiz = cveCiz;
		this.folioSolicitud = folioSolicitud;
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
		this.regPatron = regPatron;
		this.numModalidad = numModalidad;
		this.digVer = digVer;
		this.fecSolicitud = fecSolicitud;
		this.fecConclusionSol = fecConclusionSol;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
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

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public String getRegPatron() {
		return regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getNumModalidad() {
		return numModalidad;
	}

	public void setNumModalidad(String numModalidad) {
		this.numModalidad = numModalidad;
	}

	public String getDigVer() {
		return digVer;
	}

	public void setDigVer(String digVer) {
		this.digVer = digVer;
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

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
}
