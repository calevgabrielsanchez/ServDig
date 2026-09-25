/**
 * SegAutAvisoDictSaticTabVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para el tab 
 * Autorizar Aviso de Dictamen
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 *
 */
public class SegAutAvisoDictGenericoTabVO extends ControlTabs implements Serializable{	
	private static final long serialVersionUID = 3L;
	
	/**
	 * Representa la fecha de autorizacion del aviso de dictamen
	 */	
	private String fecAutAvisoDictamen;
	/**
	 * Representa el numero de aviso de dictamen
	 */		
	private String numAvisoDictamen;
	/**
	 * Representa la fecha de inicio del periodo de dictamen
	 */	
	private String fecIniDictamen;
	/**
	 * Representa la fecha de fin del periodo de dictamen
	 */	
	private String fecFinDictamen;
	
	/**
	 * Devuelve la fecha de autorizacion del aviso de dictamen
	 * 
	 * @return  fecAutAvisoDictamen
	 */
	public String getFecAutAvisoDictamen() {
		return fecAutAvisoDictamen;
	}
	/**
	 * Asigna la fecha de autorizacion del aviso de dictamen
	 * 
	 * @param fecAutAvisoDictamen 
	 */
	public void setFecAutAvisoDictamen(String fecAutAvisoDictamen) {
		this.fecAutAvisoDictamen = fecAutAvisoDictamen;
	}
	/**
	 * Devuelve el numero del aviso de dictamen
	 * 
	 * @return numAvisoDictamen
	 */
	public String getNumAvisoDictamen() {
		return numAvisoDictamen;
	}
	/**
	 * Asigna el numero del aviso de dictamen
	 * 
	 * @param numAvisoDictamen
	 */
	public void setNumAvisoDictamen(String numAvisoDictamen) {
		this.numAvisoDictamen = numAvisoDictamen;
	}
	/**
	 * Devuelve la fecha de inicio del periodo de dictamen
	 * 
	 * @return fecIniDictamen
	 */
	public String getFecIniDictamen() {
		return fecIniDictamen;
	}
	/**
	 * Asigna la fecha de inicio del periodo de dictamen
	 * 
	 * @param fecIniDictamen
	 */
	public void setFecIniDictamen(String fecIniDictamen) {
		this.fecIniDictamen = fecIniDictamen;
	}
	/**
	 * Devuelve la fecha de fin del periodo de dictamen
	 * 
	 * @return fecFinDictamen
	 */
	public String getFecFinDictamen() {
		return fecFinDictamen;
	}
	/**
	 * Asigna la fecha de fin del periodo de dictamen
	 * 
	 * @param fecFinDictamen
	 */
	public void setFecFinDictamen(String fecFinDictamen) {
		this.fecFinDictamen = fecFinDictamen;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SegAutAvisoDictGenericoTabVO [fecAutAvisoDictamen="
				+ fecAutAvisoDictamen + ", numAvisoDictamen="
				+ numAvisoDictamen + ", fecIniDictamen=" + fecIniDictamen
				+ ", fecFinDictamen=" + fecFinDictamen + "]";
	}
}
