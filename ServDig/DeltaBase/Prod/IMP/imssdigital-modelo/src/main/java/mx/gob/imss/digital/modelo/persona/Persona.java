/**
 * 
 */
package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.derechohabiente.UnidadMedicoFamiliar;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.interfaces.MensajeError;
import mx.gob.imss.digital.modelo.medio.contacto.CorreoElectronico;
import mx.gob.imss.digital.modelo.medio.contacto.Facebook;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.medio.contacto.TelefonoFijo;
import mx.gob.imss.digital.modelo.medio.contacto.TelefonoMovil;
import mx.gob.imss.digital.modelo.medio.contacto.Twitter;
import mx.gob.imss.digital.modelo.patron.RegistrosPatronales;


/**
 * MOdelo que representa a las personas 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "persona", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "persona", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Persona implements Serializable, MensajeError{

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * identificador de la persona
     */
    protected Long idPersona;
    /**
     * tipo de persona
     */
    protected TipoPersona tipoPersona;
    /**
     * domicilio particular de la persona
     */
    protected Domicilio domicilioParticular;
    /**
     * Domicilio fiscal de la persona
     */
    protected Domicilio domicilioFiscal;
    /**
     * Lista de medios de contacto para una persona
     */
    protected MedioContacto[] mediosContacto;
    /**
     * telefono fijo
     */
    protected TelefonoFijo telefonoFijo;
    /**
     * telefono movil
     */
    protected TelefonoMovil telefonoMovil;
    /**
     * correo electronico
     */
    protected CorreoElectronico correoElectronico;
    /**
     * cuenta de facebook
     */
    protected Facebook facebook;
    /**
     * cuenta de twitter
     */
    protected Twitter twitter;
    /**
     * RFC de la persona
     */
    protected String rfc;
    /**
     * Rfc Original
     */
    protected String rfcOriginal;
    /**
     * Rfc solicitado
     */
    protected String rfcSolicitado;
    /**
     * Rfc vigente
     */
    protected String rfcVigente;
    /**
     * Indicador RIF
     */
    protected Boolean indRIF;
    /**
     * Fiel
     */
    protected Fiel fiel;
    
    /**
     * Registros patronales
     */
    private RegistrosPatronales registrosPatronales;
    
    private UnidadMedicoFamiliar umfAsociado;
    
    /**
     * Mensaje de error
     */
    protected String errorFormGeneral;
    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }
    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }
    /**
     * @return the tipoPersona
     */
    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }
    /**
     * @param tipoPersona the tipoPersona to set
     */
    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
    /**
     * @return the domicilioParticular
     */
    public Domicilio getDomicilioParticular() {
        return domicilioParticular;
    }
    /**
     * @param domicilioParticular the domicilioParticular to set
     */
    public void setDomicilioParticular(Domicilio domicilioParticular) {
        this.domicilioParticular = domicilioParticular;
    }
    /**
     * @return the domicilioFiscal
     */
    public Domicilio getDomicilioFiscal() {
        return domicilioFiscal;
    }
    /**
     * @param domicilioFiscal the domicilioFiscal to set
     */
    public void setDomicilioFiscal(Domicilio domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }
    /**
     * @return the mediosContacto
     */
    public MedioContacto[] getMediosContacto() {
        return mediosContacto;
    }
    /**
     * @param mediosContacto the mediosContacto to set
     */
    public void setMediosContacto(MedioContacto[] mediosContacto) {
        this.mediosContacto = mediosContacto != null ? mediosContacto.clone() : null;
    }
    /**
     * @return the telefonoFijo
     */
    public TelefonoFijo getTelefonoFijo() {
        return telefonoFijo;
    }
    /**
     * @param telefonoFijo the telefonoFijo to set
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
     * @param telefonoMovil the telefonoMovil to set
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
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(CorreoElectronico correoElectronico) {
        this.correoElectronico = correoElectronico;
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
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }
    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    /**
     * @return the rfcOriginal
     */
    public String getRfcOriginal() {
        return rfcOriginal;
    }
    /**
     * @param rfcOriginal the rfcOriginal to set
     */
    public void setRfcOriginal(String rfcOriginal) {
        this.rfcOriginal = rfcOriginal;
    }
    /**
     * @return the rfcSolicitado
     */
    public String getRfcSolicitado() {
        return rfcSolicitado;
    }
    /**
     * @param rfcSolicitado the rfcSolicitado to set
     */
    public void setRfcSolicitado(String rfcSolicitado) {
        this.rfcSolicitado = rfcSolicitado;
    }
    /**
     * @return the rfcVigente
     */
    public String getRfcVigente() {
        return rfcVigente;
    }
    /**
     * @param rfcVigente the rfcVigente to set
     */
    public void setRfcVigente(String rfcVigente) {
        this.rfcVigente = rfcVigente;
    }
    /**
     * @return the indRIF
     */
    public Boolean getIndRIF() {
        return indRIF;
    }
    /**
     * @param indRIF the indRIF to set
     */
    public void setIndRIF(Boolean indRIF) {
        this.indRIF = indRIF;
    }
    /**
     * @return the fiel
     */
    public Fiel getFiel() {
        return fiel;
    }
    /**
     * @param fiel the fiel to set
     */
    public void setFiel(Fiel fiel) {
        this.fiel = fiel;
    }
    /**
     * @return the errorFormGeneral
     */
    public String getErrorFormGeneral() {
        return errorFormGeneral;
    }
    /**
     * @param errorFormGeneral the errorFormGeneral to set
     */
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }
    
    /**
     * 
     * @return RegistrosPatronales[]
     */
	public RegistrosPatronales getRegistrosPatronales() {
		return registrosPatronales;
	}
	
	/**
	 * 
	 * @param registrosPatronales
	 */
	public void setRegistrosPatronales(RegistrosPatronales registrosPatronales) {
		this.registrosPatronales = registrosPatronales;
	}

	public UnidadMedicoFamiliar getUmfAsociado() {
		return umfAsociado;
	}

	public void setUmfAsociado(UnidadMedicoFamiliar umfAsociado) {
		this.umfAsociado = umfAsociado;
	}

}
