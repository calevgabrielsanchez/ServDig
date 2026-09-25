/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.model;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author vanderluk
 *
 */
public class Solicitud extends AbstractModel {

	/**
	 * The Date
	 */
	@DateTimeFormat(style = "S-")
	private Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
