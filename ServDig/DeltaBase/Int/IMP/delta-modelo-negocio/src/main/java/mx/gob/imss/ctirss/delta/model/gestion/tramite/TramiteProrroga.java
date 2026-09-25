package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoProrroga;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@XmlRootElement
public class TramiteProrroga extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5479048525325620904L;
	private Long idAsignacionNSS;
	private Fisica fisica;
	private Caracter caracter;
	private Date fechaInicioProrroga;
	private Date fechaFinProrroga;
	private Long cveIdTramite;
	private Tramite tramite;
	private EstadoProrroga estadoProrroga;
	private GrupoFamiliar grupoFamiliar;
	private String observaciones;
	private Long idTipoProrroga; 
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	public Fisica getFisica() {
		return fisica;
	}
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}
	public Caracter getCaracter() {
		return caracter;
	}
	public void setCaracter(Caracter caracter) {
		this.caracter = caracter;
	}
	public Date getFechaInicioProrroga() {
		return fechaInicioProrroga;
	}
	public void setFechaInicioProrroga(Date fechaInicioProrroga) {
		this.fechaInicioProrroga = fechaInicioProrroga;
	}
	public Date getFechaFinProrroga() {
		return fechaFinProrroga;
	}
	public void setFechaFinProrroga(Date fechaFinProrroga) {
		this.fechaFinProrroga = fechaFinProrroga;
	}
	public Long getCveIdTramite() {
		return cveIdTramite;
	}
	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}
	public EstadoProrroga getEstadoProrroga() {
		return estadoProrroga;
	}
	public void setEstadoProrroga(EstadoProrroga estadoProrroga) {
		this.estadoProrroga = estadoProrroga;
	}
	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}
	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Long getIdTipoProrroga() {
		return idTipoProrroga;
	}
	public void setIdTipoProrroga(Long idTipoProrroga) {
		this.idTipoProrroga = idTipoProrroga;
	}  
}