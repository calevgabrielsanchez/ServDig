/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart-nez Cham-nica
 *  @Proyecto: delta
 *  @Archivo: TipoFacultad.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 13:38:34
 */
public class TipoFacultad extends AbstractModel {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -8503240546202400672L;
	private Long clave;
	private String descripcion;
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
