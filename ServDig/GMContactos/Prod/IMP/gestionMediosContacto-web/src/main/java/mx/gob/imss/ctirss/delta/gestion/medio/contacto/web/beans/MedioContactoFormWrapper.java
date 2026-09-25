/**
 * gestionMediosContacto-web15/05/2012
 * mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans15/05/2012
 * MedioContactoFormWrapper.java
 * 15/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;

/**
 * @author Lucio Duran Silva 
 * Instituto Mexicano del Seguro Social
 */
public class MedioContactoFormWrapper extends AbstractModel {

	private static final long serialVersionUID = -3224092725072317898L;

	private TelefonoFijo telefonoFijo;
	private TelefonoMovil telefonoMovil;
	private CorreoElectronico correoElectronico;
	private Facebook facebook;
	private Twitter twitter;
	
	/*
	 * Atributo para usuarlo dentro de la administración de medios de contacto
	 * para poder elegir qué tipo de medio se desea dar de alta
	 */
	private TipoMedioContacto tipoMedioContacto;
	
	private Long idPersona;
	
	/**
	 * @return the telefonoFijo
	 */
	public TelefonoFijo getTelefonoFijo() {
		return telefonoFijo;
	}

	/**
	 * @param telefonoFijo
	 *            the telefonoFijo to set
	 */
	public void setTelefonoFijo(TelefonoFijo telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * @return the telefonoMovil
	 */
	public TelefonoMovil getTelefonoMovil() {
		return telefonoMovil;
	}

	/**
	 * @param telefonoMovil
	 *            the telefonoMovil to set
	 */
	public void setTelefonoMovil(TelefonoMovil telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	/**
	 * @return the correoElectronico
	 */
	public CorreoElectronico getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico
	 *            the correoElectronico to set
	 */
	public void setCorreoElectronico(CorreoElectronico correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MedioContactoFormWrapper [telefonoFijo=" + telefonoFijo
				+ ", telefonoMovil=" + telefonoMovil + ", correoElectronico="
				+ correoElectronico + "]";
	}

	/**
	 * @return the facebook
	 */
	public Facebook getFacebook() {
		return facebook;
	}

	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	/**
	 * @return the twitter
	 */
	public Twitter getTwitter() {
		return twitter;
	}

	/**
	 * @param twitter the twitter to set
	 */
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	/**
	 * @return the tipoMedioContacto
	 */
	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}

	/**
	 * @param tipoMedioContacto the tipoMedioContacto to set
	 */
	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona
	 *            the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

}
