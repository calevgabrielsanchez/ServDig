package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para cancelacion
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 *
 */
public class SegCancelacionGenericoTabVO extends ControlTabs {
	
	private static final long serialVersionUID = 2L;	
	/**
	 * Atributo de la referencia de cancelacion
	 */	
	private String referenciaCancelacion;
	/**
	 * Atributo de la fecha de cancelacion
	 */	
	private String fechaCancelacion;
	/**
	 * Representa la clave del funcionario que autoriza la cancelacion
	 */		
	private String cveFuncionarioAutoriza;
	/**
	 * Representa la clave del motivo de cancelacion
	 */			
	private String cveMotivoCancelacion;
	/**
	 * Devuelve la referencia de cancelacion
	 * 
	 * @return the referenciaCancelacion
	 */
	public String getReferenciaCancelacion() {
		return referenciaCancelacion;
	}
	/**
	 * Asigna la referencia de cancelacion
	 * 
	 * @param referenciaCancelacion
	 */
	public void setReferenciaCancelacion(String referenciaCancelacion) {
		this.referenciaCancelacion = referenciaCancelacion;
	}
	/**
	 * Devuelve la fecha de cancelacion
	 * 
	 * @return fechaCancelacion
	 */
	public String getFechaCancelacion() {
		return fechaCancelacion;
	}
	/**
	 * Asigna la fecha de cancelacion
	 * 
	 * @param fechaCancelacion
	 */
	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	/**
	 * Devuelve la clave del funcionario que autoriza
	 * 
	 * @return cveFuncionarioAutoriza
	 */
	public String getCveFuncionarioAutoriza() {
		return cveFuncionarioAutoriza;
	}
	/**
	 * Asigna la clave del funcionario que autoriza
	 * 
	 * @param cveFuncionarioAutoriza
	 */
	public void setCveFuncionarioAutoriza(String cveFuncionarioAutoriza) {
		this.cveFuncionarioAutoriza = cveFuncionarioAutoriza;
	}
	/**
	 * Devuelve la clave del motivo de cancelacion
	 * 
	 * @return cveMotivoCancelacion
	 */
	public String getCveMotivoCancelacion() {
		return cveMotivoCancelacion;
	}
	/**
	 * Asigna la clave del motivo de cancelacion
	 * 
	 * @param cveMotivoCancelacion
	 */
	public void setCveMotivoCancelacion(String cveMotivoCancelacion) {
		this.cveMotivoCancelacion = cveMotivoCancelacion;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SegCancelacionGenericoTabVO [referenciaCancelacion="
				+ referenciaCancelacion + ", fechaCancelacion="
				+ fechaCancelacion + ", cveFuncionarioAutoriza="
				+ cveFuncionarioAutoriza + ", cveMotivoCancelacion="
				+ cveMotivoCancelacion + "]";
	}	

}
