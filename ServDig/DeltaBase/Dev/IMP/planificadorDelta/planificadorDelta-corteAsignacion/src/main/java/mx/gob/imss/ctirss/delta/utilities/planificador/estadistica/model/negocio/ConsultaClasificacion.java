package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.Date;

public class ConsultaClasificacion {
	private Long cveIdTramite;
	private Integer cveCiz;
	private String folioSolicitud;
	private String descripcionTramite;
	private Long cveIdPatronSujetoObligado;
	private String regPatron;
	private String numModalidad;
	private String digVer;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	private Date fecPresentacionTram;
	private Date fecRegistroAltaTram;
	private Date fecRegistroActualizadoTram;
	private Date fecEfectoTram;

	public ConsultaClasificacion() {
	}

	public ConsultaClasificacion(Long cveIdTramite, Integer cveCiz,
			String folioSolicitud, String descripcionTramite,
			Long cveIdPatronSujetoObligado, String regPatron,
			String numModalidad, String digVer, Date fecRegistroAlta,
			Date fecRegistroBaja, Date fecRegistroActualizado,
			Date fecPresentacionTram, Date fecRegistroAltaTram,
			Date fecRegistroActualizadoTram, Date fecEfectoTram) {
		this.cveIdTramite = cveIdTramite;
		this.cveCiz = cveCiz;
		this.folioSolicitud = folioSolicitud;
		this.descripcionTramite = descripcionTramite;
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
		this.regPatron = regPatron;
		this.numModalidad = numModalidad;
		this.digVer = digVer;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.fecPresentacionTram = fecPresentacionTram;
		this.fecRegistroAltaTram = fecRegistroAltaTram;
		this.fecRegistroActualizadoTram = fecRegistroActualizadoTram;
		this.fecEfectoTram = fecEfectoTram;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
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

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
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

	public Date getFecPresentacionTram() {
		return fecPresentacionTram;
	}

	public void setFecPresentacionTram(Date fecPresentacionTram) {
		this.fecPresentacionTram = fecPresentacionTram;
	}

	public Date getFecRegistroAltaTram() {
		return fecRegistroAltaTram;
	}

	public void setFecRegistroAltaTram(Date fecRegistroAltaTram) {
		this.fecRegistroAltaTram = fecRegistroAltaTram;
	}

	public Date getFecRegistroActualizadoTram() {
		return fecRegistroActualizadoTram;
	}

	public void setFecRegistroActualizadoTram(Date fecRegistroActualizadoTram) {
		this.fecRegistroActualizadoTram = fecRegistroActualizadoTram;
	}

	public Date getFecEfectoTram() {
		return fecEfectoTram;
	}

	public void setFecEfectoTram(Date fecEfectoTram) {
		this.fecEfectoTram = fecEfectoTram;
	}
}
