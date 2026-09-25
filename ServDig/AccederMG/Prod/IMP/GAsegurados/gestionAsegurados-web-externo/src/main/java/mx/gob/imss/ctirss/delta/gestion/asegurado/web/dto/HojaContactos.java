/**
 * Contiene los diferentes objetos especificos de los medios de contacto, este
 * objeto es para la transportacion entre la capa de la vista (front) y no
 * con otra capa.
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;

/**
 * @author Lucio Duran Silva
 *
 */
public class HojaContactos extends AbstractModel {
	
	
	/**
	 * Telefono fijo
	 */
	private TelefonoFijo telefonoFijo;
	
	
	/**
	 * Telefono movil
	 */
	private TelefonoMovil telefonoMovil;
	
	/**
	 * Correo electronico.
	 */
	private CorreoElectronico correoElectronico;

	public TelefonoFijo getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(TelefonoFijo telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public TelefonoMovil getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(TelefonoMovil telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public CorreoElectronico getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(CorreoElectronico correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	
	
	

}
