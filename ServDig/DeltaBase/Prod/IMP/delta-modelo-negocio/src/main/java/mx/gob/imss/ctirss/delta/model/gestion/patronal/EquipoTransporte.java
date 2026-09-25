package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

public class EquipoTransporte extends ItemClasificacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1331201072822726347L;
	private Long id;
	private String desCapacidadPotencia;
	private String desNombre;
	private String desUso;
	private BigDecimal numUnidades;
	private TipoCombustible tipoCombustible;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the desCapacidadPotencia
	 */
	public String getDesCapacidadPotencia() {
		return desCapacidadPotencia;
	}
	/**
	 * @param desCapacidadPotencia the desCapacidadPotencia to set
	 */
	public void setDesCapacidadPotencia(String desCapacidadPotencia) {
		this.desCapacidadPotencia = desCapacidadPotencia;
	}
	/**
	 * @return the desNombre
	 */
	public String getDesNombre() {
		return desNombre;
	}
	/**
	 * @param desNombre the desNombre to set
	 */
	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}
	/**
	 * @return the desUso
	 */
	public String getDesUso() {
		return desUso;
	}
	/**
	 * @param desUso the desUso to set
	 */
	public void setDesUso(String desUso) {
		this.desUso = desUso;
	}
	/**
	 * @return the numUnidades
	 */
	public BigDecimal getNumUnidades() {
		return numUnidades;
	}
	/**
	 * @param numUnidades the numUnidades to set
	 */
	public void setNumUnidades(BigDecimal numUnidades) {
		this.numUnidades = numUnidades;
	}
	/**
	 * @return the tipoCombustible
	 */
	public TipoCombustible getTipoCombustible() {
		return tipoCombustible;
	}
	/**
	 * @param tipoCombustible the tipoCombustible to set
	 */
	public void setTipoCombustible(TipoCombustible tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EquipoTansporte [id=");
		builder.append(id);
		builder.append(", desNombre=");
		builder.append(desNombre);
		builder.append(", desUso=");
		builder.append(desUso);
		builder.append(", desCapacidadPotencia=");
		builder.append(desCapacidadPotencia);
		builder.append(", numUnidades=");
		builder.append(numUnidades);
		builder.append(", "+super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
