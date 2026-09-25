package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PersonalTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3505415911385343297L;
	private Long clave;
	private BigDecimal numTrabajadores;
	private String oficioOcupacion;
	private Long idRegistroPatronal;

	public Long getClave() {
		return clave;
	}
	public void setClave(Long clave) {
		this.clave = clave;
	}
	public BigDecimal getNumTrabajadores() {
		return numTrabajadores;
	}
	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}
	public String getOficioOcupacion() {
		return oficioOcupacion;
	}
	public void setOficioOcupacion(String oficioOcupacion) {
		this.oficioOcupacion = oficioOcupacion;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
	
}
