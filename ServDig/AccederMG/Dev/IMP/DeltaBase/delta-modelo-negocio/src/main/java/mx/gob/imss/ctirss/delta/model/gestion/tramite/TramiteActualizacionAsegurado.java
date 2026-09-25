package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@XmlRootElement
public class TramiteActualizacionAsegurado extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8605289646843990474L;
	private Long idPersona;
	private Long idAsignacionNSS;
	private Fisica fisicaAnterior;
	private Fisica fisicaNueva;
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}

	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Fisica getFisicaAnterior() {
		return fisicaAnterior;
	}
	
	public void setFisicaAnterior(Fisica fisicaAnterior) {
		this.fisicaAnterior = fisicaAnterior;
	}
	
	public Fisica getFisicaNueva() {
		return fisicaNueva;
	}
	
	public void setFisicaNueva(Fisica fisicaNueva) {
		this.fisicaNueva = fisicaNueva;
	}
	
	
}
