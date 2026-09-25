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
 *  @Archivo: TipoCombustible.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 15:47:59
 */
public class TipoCombustible extends AbstractModel {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2560123696657484468L;
	private Long clave;
	private String desTipoCombustible;
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
	 * @return the desTipoCombustible
	 */
	public String getDesTipoCombustible() {
		return desTipoCombustible;
	}
	/**
	 * @param desTipoCombustible the desTipoCombustible to set
	 */
	public void setDesTipoCombustible(String desTipoCombustible) {
		this.desTipoCombustible = desTipoCombustible;
	}
	
	
	
	
}
