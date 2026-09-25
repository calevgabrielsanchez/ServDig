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
 *  @Archivo: Facultad.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 13:29:39
 */
public class Facultad extends AbstractModel {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 28809550301825345L;
	private Long clave;
	private String descripcion;
	private TipoFacultad tipoFacultad;
	
	
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
	/**
	 * @return the tipoFacultad
	 */
	public TipoFacultad getTipoFacultad() {
		return tipoFacultad;
	}
	/**
	 * @param tipoFacultad the tipoFacultad to set
	 */
	public void setTipoFacultad(TipoFacultad tipoFacultad) {
		this.tipoFacultad = tipoFacultad;
	}
	
	
	
	
}
