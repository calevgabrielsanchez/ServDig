package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class DatosHistoriaLaboral implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1822678251085627894L;
	private String nombrePatron;
	private String entidadFederativa;
	private String numeroRegistroPatronal;
	private String domicilioEmpresa;
	private String actividadEmpresa;
	private String rfc;
	private Long  patronVigente;
	//private boolean isPatronVigente;
	
	

	
	
	
	
	public Long getPatronVigente() {
		return patronVigente;
	}

	public void setPatronVigente(Long patronVigente) {
		this.patronVigente = patronVigente;
	}

	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
	
	public String getNombrePatron() {
		return nombrePatron;
	}
	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}
	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}
	public String getDomicilioEmpresa() {
		return domicilioEmpresa;
	}
	public void setDomicilioEmpresa(String domicilioEmpresa) {
		this.domicilioEmpresa = domicilioEmpresa;
	}
	public String getActividadEmpresa() {
		return actividadEmpresa;
	}
	public void setActividadEmpresa(String actividadEmpresa) {
		this.actividadEmpresa = actividadEmpresa;
	}
	
	
	

}
