package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Clase que servira para transportar los datos de la persona y los indicadores hacia el controller 
 * @author 191807 141212
 *
 */
public class ICADatosConsulta extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8333735203732134255L;
	
	private Fisica personaFisica;
	private Moral personaMoral;
	private Boolean indicadorConsultaRENAPO;
	private Boolean indicadorConsultaSAT;
	private Boolean indicadorMostrarPantalla;
	private Boolean isUsuarioExterno;
	
	/**
	 * @return the personaFisica
	 */
	public Fisica getPersonaFisica() {
		return personaFisica;
	}
	
	/**
	 * @param personaFisica the personaFisica to set
	 */
	public void setPersonaFisica(Fisica personaFisica) {
		this.personaFisica = personaFisica;
	}
	
	/**
	 * @return the personaMoral
	 */
	public Moral getPersonaMoral() {
		return personaMoral;
	}

	/**
	 * @param personaMoral the personaMoral to set
	 */
	public void setPersonaMoral(Moral personaMoral) {
		this.personaMoral = personaMoral;
	}

	/**
	 * @return the indicadorConsultaRENAPO
	 */
	public Boolean getIndicadorConsultaRENAPO() {
		return indicadorConsultaRENAPO;
	}

	/**
	 * @param indicadorConsultaRENAPO the indicadorConsultaRENAPO to set
	 */
	public void setIndicadorConsultaRENAPO(Boolean indicadorConsultaRENAPO) {
		this.indicadorConsultaRENAPO = indicadorConsultaRENAPO;
	}

	/**
	 * @return the indicadorConsultaSAT
	 */
	public Boolean getIndicadorConsultaSAT() {
		return indicadorConsultaSAT;
	}

	/**
	 * @param indicadorConsultaSAT the indicadorConsultaSAT to set
	 */
	public void setIndicadorConsultaSAT(Boolean indicadorConsultaSAT) {
		this.indicadorConsultaSAT = indicadorConsultaSAT;
	}

	/**
	 * @return the indicadorMostrarPantalla
	 */
	public Boolean getIndicadorMostrarPantalla() {
		return indicadorMostrarPantalla;
	}

	/**
	 * @param indicadorMostrarPantalla the indicadorMostrarPantalla to set
	 */
	public void setIndicadorMostrarPantalla(Boolean indicadorMostrarPantalla) {
		this.indicadorMostrarPantalla = indicadorMostrarPantalla;
	}

	/**
	 * @return the isUsuarioExterno
	 */
	public Boolean getIsUsuarioExterno() {
		return isUsuarioExterno;
	}

	/**
	 * @param isUsuarioExterno the isUsuarioExterno to set
	 */
	public void setIsUsuarioExterno(Boolean isUsuarioExterno) {
		this.isUsuarioExterno = isUsuarioExterno;
	}		
}
