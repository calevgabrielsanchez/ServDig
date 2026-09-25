package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

public class Personal extends ItemClasificacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4365082079571893832L;
	
	private Long clave;
	private BigDecimal numTrabajadores;
	private String oficioOcupacion;

	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}
	
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}
	
	/**
	 * @return the numTrabajadores
	 */
	public BigDecimal getNumTrabajadores() {
		return numTrabajadores;
	}
	
	/**
	 * @param numTrabajadores the numTrabajadores to set
	 */
	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}
	
	/**
	 * @return the oficioOcupacion
	 */
	public String getOficioOcupacion() {
		return oficioOcupacion;
	}
	
	/**
	 * @param oficioOcupacion the oficioOcupacion to set
	 */
	public void setOficioOcupacion(String oficioOcupacion) {
		this.oficioOcupacion = oficioOcupacion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Personal [clave=");
		builder.append(clave);
		builder.append(", numTrabajadores=");
		builder.append(numTrabajadores);
		builder.append(", oficioOcupacion=");
		builder.append(oficioOcupacion);
		builder.append(", "+super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
