package mx.gob.imss.ctirss.delta.model.asegurado;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MovtoAsegAbierto extends AbstractModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idMovimientoAsegurado;
	private String desOcupacion;
	private String salarioBase;
	private String salarioArt33;
	private String salarioReal;
	/**
	 * @return the idMovimientoAsegurado
	 */
	public Long getIdMovimientoAsegurado() {
		return idMovimientoAsegurado;
	}
	/**
	 * @param idMovimientoAsegurado the idMovimientoAsegurado to set
	 */
	public void setIdMovimientoAsegurado(Long idMovimientoAsegurado) {
		this.idMovimientoAsegurado = idMovimientoAsegurado;
	}
	/**
	 * @return the desOcupacion
	 */
	public String getDesOcupacion() {
		return desOcupacion;
	}
	/**
	 * @param desOcupacion the desOcupacion to set
	 */
	public void setDesOcupacion(String desOcupacion) {
		this.desOcupacion = desOcupacion;
	}
	/**
	 * @return the salarioBase
	 */
	public String getSalarioBase() {
		return salarioBase;
	}
	/**
	 * @param salarioBase the salarioBase to set
	 */
	public void setSalarioBase(String salarioBase) {
		this.salarioBase = salarioBase;
	}
	/**
	 * @return the salarioArt33
	 */
	public String getSalarioArt33() {
		return salarioArt33;
	}
	/**
	 * @param salarioArt33 the salarioArt33 to set
	 */
	public void setSalarioArt33(String salarioArt33) {
		this.salarioArt33 = salarioArt33;
	}
	/**
	 * @return the salarioReal
	 */
	public String getSalarioReal() {
		return salarioReal;
	}
	/**
	 * @param salarioReal the salarioReal to set
	 */
	public void setSalarioReal(String salarioReal) {
		this.salarioReal = salarioReal;
	}
	
	

}
