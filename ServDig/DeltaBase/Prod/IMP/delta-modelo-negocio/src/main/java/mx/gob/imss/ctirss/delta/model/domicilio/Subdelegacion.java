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
 *  @Archivo: SubDelegacion.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha: 10:53:33
 */
public class Subdelegacion extends AbstractModel {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 3066876937452596470L;
	
	
	private Long id;
	private String clave;
	private String descripcion;
	private Delegacion delegacion;
	
	
	/**
	 * 
	 */
	public Subdelegacion() {
		// TODO Auto-generated constructor stub
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


	/**
	 * @return the delegacion
	 */
	public Delegacion getDelegacion() {
		return delegacion;
	}


	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}
	
	
	
	
}
