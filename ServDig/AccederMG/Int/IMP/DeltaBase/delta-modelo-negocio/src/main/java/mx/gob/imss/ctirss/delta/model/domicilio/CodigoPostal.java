/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:CodigoPostal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha:02/04/2012
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class CodigoPostal extends AbstractModel {
	
	
	private String codigoPostal;

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CodigoPostal [codigoPostal=" + codigoPostal + "]";
	}
	
	
	
	

}
