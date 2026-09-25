package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;

public class EquipoTransporteTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6359068931550656338L;
	private Long id;
	private String desCapacidadPotencia;
	private String desNombre;
	private String desUso;
	private BigDecimal numUnidades;
	private TipoCombustible tipoCombustible;
	private Long idRegistroPatronal;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesCapacidadPotencia() {
		return desCapacidadPotencia;
	}
	public void setDesCapacidadPotencia(String desCapacidadPotencia) {
		this.desCapacidadPotencia = desCapacidadPotencia;
	}
	public String getDesNombre() {
		return desNombre;
	}
	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}
	public String getDesUso() {
		return desUso;
	}
	public void setDesUso(String desUso) {
		this.desUso = desUso;
	}
	public BigDecimal getNumUnidades() {
		return numUnidades;
	}
	public void setNumUnidades(BigDecimal numUnidades) {
		this.numUnidades = numUnidades;
	}
	public TipoCombustible getTipoCombustible() {
		return tipoCombustible;
	}
	public void setTipoCombustible(TipoCombustible tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
	
}
