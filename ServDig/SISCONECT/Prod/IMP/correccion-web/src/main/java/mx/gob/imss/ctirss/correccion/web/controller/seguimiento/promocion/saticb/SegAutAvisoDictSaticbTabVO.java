/**
 * SegAutAvisoDictSaticTabVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para Autorizar aviso de 
 * dictamen
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class SegAutAvisoDictSaticbTabVO extends ControlTabs{
	
	
	private String fecAutAvisoDictamen;
	private String numAviso;
	private String funcionarioRegistraAutDict;
	private String ejericioDictaminarSaticb;
	/**
	 * Retorna el valor fecAutAvisoDictamen
	 * @return  fecAutAvisoDictamen
	 */
	public String getFecAutAvisoDictamen() {
		return fecAutAvisoDictamen;
	}
	/**
	 * Asigna el valor del fecAutAvisoDictamen al atributo fecAutAvisoDictamen
	 * @param fecAutAvisoDictamen 
	 */
	public void setFecAutAvisoDictamen(String fecAutAvisoDictamen) {
		this.fecAutAvisoDictamen = fecAutAvisoDictamen;
	}
	/**
	 * Retorna el valor numAviso
	 * @return  numAviso
	 */
	public String getNumAviso() {
		return numAviso;
	}
	/**
	 * Asigna el valor del numAviso al atributo numAviso
	 * @param numAviso 
	 */
	public void setNumAviso(String numAviso) {
		this.numAviso = numAviso;
	}
	/**
	 * Retorna el valor funcionarioRegistraAutDict
	 * @return  funcionarioRegistraAutDict
	 */
	public String getFuncionarioRegistraAutDict() {
		return funcionarioRegistraAutDict;
	}
	/**
	 * Asigna el valor del funcionarioRegistraAutDict al atributo funcionarioRegistraAutDict
	 * @param funcionarioRegistraAutDict 
	 */
	public void setFuncionarioRegistraAutDict(String funcionarioRegistraAutDict) {
		this.funcionarioRegistraAutDict = funcionarioRegistraAutDict;
	}
	/**
	 * Retorna el valor ejericioDictaminarSaticb
	 * @return  ejericioDictaminarSaticb
	 */
	public String getEjericioDictaminarSaticb() {
		return ejericioDictaminarSaticb;
	}
	/**
	 * Asigna el valor del ejericioDictaminarSaticb al atributo ejericioDictaminarSaticb
	 * @param ejericioDictaminarSaticb 
	 */
	public void setEjericioDictaminarSaticb(String ejericioDictaminarSaticb) {
		this.ejericioDictaminarSaticb = ejericioDictaminarSaticb;
	}
	
	

}
