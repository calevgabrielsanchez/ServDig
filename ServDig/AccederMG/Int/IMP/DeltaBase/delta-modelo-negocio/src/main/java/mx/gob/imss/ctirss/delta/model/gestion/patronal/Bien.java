package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

public class Bien extends ItemClasificacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351903898102276248L;
	
	private Long id;
	private String desBienes;
	private BigDecimal numCantidad;
	
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bien [cveIdBienes=");
		builder.append(id);
		builder.append(", desBienes=");
		builder.append(desBienes);
		builder.append(", numCantidad=");
		builder.append(numCantidad);
		builder.append(", "+super.toString());
		builder.append("]");
		return builder.toString();
	}

}
