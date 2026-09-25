/**
 * 
 */
package mx.gob.imss.ctirss.delta.portal.model;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author vanderluk
 *
 */
public class Solicitud extends AbstractModel {

	/**
	 * The Date
	 */
	
	private Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
