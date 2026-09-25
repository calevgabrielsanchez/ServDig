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
 *  @Archivo: TipoMaquinariaEquipo.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 15:26:49
 */
public class TipoMaquinariaEquipo extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7588989525424374794L;
	private Long id;
	private String descripcion;
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
