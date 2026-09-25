package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;

/**
 * Clase que servira para transportar el resultado del servicio ICA
 * @author 191807 141212
 *
 */
public class ICADatosRespuesta extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5394235730311694747L;
	
	private Fisica personaFisicaIMSS;
	private Moral personaMoralIMSS;
	private Fisica personaFisicaEE;
	private Moral personaMoralEE;
	private Map<String, String> traza;
	private Map<String, CambioComparacionEnum> cambios;
	
	private boolean indicadorConsultaRENAPO;
	private boolean indicadorConsultaSAT;
	
	
	/**
	 * @return the personaFisicaIMSS
	 */
	public Fisica getPersonaFisicaIMSS() {
		return personaFisicaIMSS;
	}
	
	/**
	 * @param personaFisicaIMSS the personaFisicaIMSS to set
	 */
	public void setPersonaFisicaIMSS(Fisica personaFisicaIMSS) {
		this.personaFisicaIMSS = personaFisicaIMSS;
	}
	
	/**
	 * @return the personaMoralIMSS
	 */
	public Moral getPersonaMoralIMSS() {
		return personaMoralIMSS;
	}

	/**
	 * @param personaMoralIMSS the personaMoralIMSS to set
	 */
	public void setPersonaMoralIMSS(Moral personaMoralIMSS) {
		this.personaMoralIMSS = personaMoralIMSS;
	}			
	
	/**
	 * @return the personaFisicaEE
	 */
	public Fisica getPersonaFisicaEE() {
		return personaFisicaEE;
	}
	
	/**
	 * @param personaFisicaEE the personaFisicaEE to set
	 */
	public void setPersonaFisicaEE(Fisica personaFisicaEE) {
		this.personaFisicaEE = personaFisicaEE;
	}
	
	/**
	 * @return the personaMoralEE
	 */
	public Moral getPersonaMoralEE() {
		return personaMoralEE;
	}

	/**
	 * @param personaMoralEE the personaMoralEE to set
	 */
	public void setPersonaMoralEE(Moral personaMoralEE) {
		this.personaMoralEE = personaMoralEE;
	}

	/**
	 * @return the traza
	 */
	public Map<String, String> getTraza() {
		return traza;
	}

	/**
	 * @param traza the traza to set
	 */
	public void setTraza(Map<String, String> traza) {
		this.traza = traza;
	}

	/**
	 * @return the cambios
	 */
	public Map<String, CambioComparacionEnum> getCambios() {
		return cambios;
	}

	/**
	 * @param cambios the cambios to set
	 */
	public void setCambios(Map<String, CambioComparacionEnum> cambios) {
		this.cambios = cambios;
	}

	/**
	 * @return the indicadorConsultaRENAPO
	 */
	public boolean getIndicadorConsultaRENAPO() {
		return indicadorConsultaRENAPO;
	}

	/**
	 * @param indicadorConsultaRENAPO the indicadorConsultaRENAPO to set
	 */
	public void setIndicadorConsultaRENAPO(boolean indicadorConsultaRENAPO) {
		this.indicadorConsultaRENAPO = indicadorConsultaRENAPO;
	}

	/**
	 * @return the indicadorConsultaSAT
	 */
	public boolean getIndicadorConsultaSAT() {
		return indicadorConsultaSAT;
	}

	/**
	 * @param indicadorConsultaSAT the indicadorConsultaSAT to set
	 */
	public void setIndicadorConsultaSAT(boolean indicadorConsultaSAT) {
		this.indicadorConsultaSAT = indicadorConsultaSAT;
	}
}
