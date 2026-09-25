package mx.gob.imss.ctirss.delta.gestion.patronal.web.beans;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;

public class MedioContactoForm extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TelefonoFijo telefonoFijo;
	private TelefonoMovil telefonoMovil;
	private CorreoElectronico correoElectronico;
	private Facebook facebook;
	private Twitter twitter;
	private TipoMedioContacto tipoMedioContacto;
	
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
	public Facebook getFacebook() {
		return facebook;
	}
	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}
	public Twitter getTwitter() {
		return twitter;
	}
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}
	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}
	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

}
