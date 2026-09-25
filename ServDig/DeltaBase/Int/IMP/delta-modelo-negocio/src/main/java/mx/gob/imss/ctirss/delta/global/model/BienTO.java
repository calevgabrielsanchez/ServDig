package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class BienTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3423066928286123859L;
	private Long id;
	private String desBienes;
	private BigDecimal numCantidad;
	private Long idRegistroPatronal;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesBienes() {
		return desBienes;
	}
	public void setDesBienes(String desBienes) {
		this.desBienes = desBienes;
	}
	public BigDecimal getNumCantidad() {
		return numCantidad;
	}
	public void setNumCantidad(BigDecimal numCantidad) {
		this.numCantidad = numCantidad;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
}
