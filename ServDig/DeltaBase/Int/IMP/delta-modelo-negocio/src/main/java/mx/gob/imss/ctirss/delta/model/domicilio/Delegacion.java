/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: Delegacion.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha: 10:53:44
 */
public class Delegacion extends AbstractModel {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1174830324624713622L;
	private Long id;
	private String clave;
	private String descripcion;
	
	//derechohabientes
	protected Integer ciz;


	/**
	 * Gets the value of the ciz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getCiz() {
		return ciz;
	}

	/**
	 * Sets the value of the ciz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCiz(Integer value) {
		this.ciz = value;
	}

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
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
