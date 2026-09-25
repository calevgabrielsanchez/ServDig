/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart-nez Cham-nica
 *  @Proyecto: delta
 *  @Archivo: MaquinariaEquipo.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 13:41:50
 */
public class MaquinariaEquipo extends ItemClasificacion {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4877443426994865687L;
	private Long id;
	private String desCapacidadPotencia;
	private String desNombre;
	private String desUso;
	private BigDecimal numUnidades;
	private TipoMaquinariaEquipo tipo;
	
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
	 * @return the descripcion
	 */
	public String getDesCapacidadPotencia() {
		return desCapacidadPotencia;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDesCapacidadPotencia(String descripcion) {
		this.desCapacidadPotencia = descripcion;
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
	 * @return the tipo
	 */
	public TipoMaquinariaEquipo getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoMaquinariaEquipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MaquinariaEquipo [id=");
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
