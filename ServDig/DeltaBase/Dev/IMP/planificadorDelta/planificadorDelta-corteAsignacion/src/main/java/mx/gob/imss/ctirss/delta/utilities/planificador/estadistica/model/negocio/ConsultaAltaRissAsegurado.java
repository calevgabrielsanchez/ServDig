package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.Date;

public class ConsultaAltaRissAsegurado {
	private String folioSolicitud;
	private String rfc;
	private String curp;
	private Date fecSolicitud;
	private Date fecConclusionSol;
	private Date fecRegistroAltaBen;
	private Date fecRegistroBajaBen;
	private Date fecRegistroActualizadoBen;
	private String desTipoBeneficio;
	private String desEstadoBeneficio;
	private Date fecInicioVigencia;
	private Date fecFinVigencia;

	public ConsultaAltaRissAsegurado() {
	}

	public ConsultaAltaRissAsegurado(String folioSolicitud, String rfc,
			String curp, Date fecSolicitud, Date fecConclusionSol,
			Date fecRegistroAltaBen, Date fecRegistroBajaBen,
			Date fecRegistroActualizadoBen, String desTipoBeneficio,
			String desEstadoBeneficio, Date fecInicioVigencia,
			Date fecFinVigencia) {
		this.folioSolicitud = folioSolicitud;
		this.rfc = rfc;
		this.curp = curp;
		this.fecSolicitud = fecSolicitud;
		this.fecConclusionSol = fecConclusionSol;
		this.fecRegistroAltaBen = fecRegistroAltaBen;
		this.fecRegistroBajaBen = fecRegistroBajaBen;
		this.fecRegistroActualizadoBen = fecRegistroActualizadoBen;
		this.desTipoBeneficio = desTipoBeneficio;
		this.desEstadoBeneficio = desEstadoBeneficio;
		this.fecInicioVigencia = fecInicioVigencia;
		this.fecFinVigencia = fecFinVigencia;
	}

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
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

	public Date getFecRegistroAltaBen() {
		return fecRegistroAltaBen;
	}

	public void setFecRegistroAltaBen(Date fecRegistroAltaBen) {
		this.fecRegistroAltaBen = fecRegistroAltaBen;
	}

	public Date getFecRegistroBajaBen() {
		return fecRegistroBajaBen;
	}

	public void setFecRegistroBajaBen(Date fecRegistroBajaBen) {
		this.fecRegistroBajaBen = fecRegistroBajaBen;
	}

	public Date getFecRegistroActualizadoBen() {
		return fecRegistroActualizadoBen;
	}

	public void setFecRegistroActualizadoBen(Date fecRegistroActualizadoBen) {
		this.fecRegistroActualizadoBen = fecRegistroActualizadoBen;
	}

	public String getDesTipoBeneficio() {
		return desTipoBeneficio;
	}

	public void setDesTipoBeneficio(String desTipoBeneficio) {
		this.desTipoBeneficio = desTipoBeneficio;
	}

	public String getDesEstadoBeneficio() {
		return desEstadoBeneficio;
	}

	public void setDesEstadoBeneficio(String desEstadoBeneficio) {
		this.desEstadoBeneficio = desEstadoBeneficio;
	}

	public Date getFecInicioVigencia() {
		return fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public Date getFecFinVigencia() {
		return fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}
}
