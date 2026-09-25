package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: Persona.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.individuo
 * @Fecha: 12:52:01
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Persona extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 764588089755238538L;
	private Long idPersona;
	private Long idPersonaFisica;
	private TipoPersona tipoPersona;
	private List<PersonaEstado> personaEstados = new ArrayList<PersonaEstado>();
	private List<PersonaCalificacion> personaCalificaciones = new ArrayList<PersonaCalificacion>();
	
	private List<Domicilio> domicilios;
	
	private List<MedioContacto> mediosContacto;
	private TelefonoFijo telefonoFijo;
	private TelefonoMovil telefonoMovil;
	private CorreoElectronico correoElectronico;
	private Facebook facebook;
	private Twitter twitter;
	
	private List<DocumentoProbatorio> documentosProbatorios;
    
	private Nacimiento actaNacimiento;
	
	private String rfc;

	private List<Identificador> identificadores;

	protected List<PersonaDomicilio> personaDomicilio;
	protected List<RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
	protected List<Socio> socios = new ArrayList<Socio>();
	
	/**
	 * Domicilio Fiscal del SAT
	 */
	private DomicilioFiscal domicilioFiscal;
	
	private List<MedioContacto> mediosContactoFiscales;
	private TelefonoFijo telefonoFijoFiscal;
	private TelefonoMovil telefonoMovilFiscal;
	private CorreoElectronico correoElectronicoFiscal;

	private String rfcOriginal;
	private String rfcSolicitado;
	private String rfcVigente;
	private Boolean indRIF;
	
	private Fiel fiel;
	
	public Persona(){
		this.mediosContacto=  new ArrayList<MedioContacto>();
		this.documentosProbatorios = new ArrayList<DocumentoProbatorio>();
		this.mediosContactoFiscales = new ArrayList<MedioContacto>();
	}
	
	
	/**
	 * @return the personaDomicilio
	 */
	public List<PersonaDomicilio> getPersonaDomicilio() {
		return personaDomicilio;
	}


	/**
	 * @param personaDomicilio the personaDomicilio to set
	 */
	public void setPersonaDomicilio(List<PersonaDomicilio> personaDomicilio) {
		this.personaDomicilio = personaDomicilio;
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

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<PersonaEstado> getPersonaEstados() {
	    if(personaEstados == null) {
	        personaEstados = new ArrayList<PersonaEstado>();
	    }
		return personaEstados;
	}

	public void setPersonaEstados(List<PersonaEstado> personaEstados) {
		this.personaEstados = personaEstados;
	}

	public List<PersonaCalificacion> getPersonaCalificaciones() {
		return personaCalificaciones;
	}

	public void setPersonaCalificaciones(
			List<PersonaCalificacion> personaCalificaciones) {
		this.personaCalificaciones = personaCalificaciones;
	}

	public List<Domicilio> getDomicilios() {
	    if(domicilios == null) {
	        domicilios = new ArrayList<Domicilio>();
	    }
		return domicilios;
	}

	public void setDomicilios(List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}



	/**
	 * @return the mediosContacto
	 */
	public List<MedioContacto> getMediosContacto() {
		return mediosContacto;
	}

	/**
	 * @param mediosContacto the mediosContacto to set
	 */
	public void setMediosContacto(List<MedioContacto> mediosContacto) {
		this.mediosContacto = mediosContacto;
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
		if(this.telefonoFijo!=null)
			this.mediosContacto.remove(this.telefonoFijo);
		this.telefonoFijo = telefonoFijo;
		if(this.telefonoFijo!=null)
			this.mediosContacto.add(this.telefonoFijo);
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
		if(this.telefonoMovil!=null)
			this.mediosContacto.remove(this.telefonoMovil);
		this.telefonoMovil = telefonoMovil;
		if(this.telefonoMovil!=null)
			this.mediosContacto.add(this.telefonoMovil);
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
		if(this.correoElectronico!=null)
			this.mediosContacto.remove(this.correoElectronico);
		this.correoElectronico = correoElectronico;
		if(this.correoElectronico!=null)
			this.mediosContacto.add(this.correoElectronico);
	}

	public List<DocumentoProbatorio> getDocumentosProbatorios() {
		return documentosProbatorios;
	}

	public void setDocumentosProbatorios(
			List<DocumentoProbatorio> documentosProbatorios) {
		this.documentosProbatorios = documentosProbatorios;
	}

	public Nacimiento getActaNacimiento() {
		return actaNacimiento;
	}

	public void setActaNacimiento(Nacimiento actaNacimiento) {
		if(actaNacimiento !=null){
			ListIterator<DocumentoProbatorio> it = this.documentosProbatorios.listIterator();
			if(it != null){
				while(it.hasNext()){
					if(it.next() instanceof Nacimiento){
						it.remove();
					}
				}
			}
		}
			
		this.actaNacimiento = actaNacimiento;
		if(this.actaNacimiento!=null)
			this.documentosProbatorios.add(this.actaNacimiento);
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

	public List<RepresentanteLegal> getRepresentantesLegales() {
		return representantesLegales;
	}


	public void setRepresentantesLegales(
			List<RepresentanteLegal> representantesLegales) {
		this.representantesLegales = representantesLegales;
	}
	
	public List<Socio> getSocios() {
		return socios;
	}


	public void setSocios(List<Socio> socio) {
		this.socios = socio;
	}


	public Facebook getFacebook() {
		return facebook;
	}
	
	public void setFacebook(Facebook facebook) {
		if(this.facebook!=null)
			this.mediosContacto.remove(this.facebook);
		this.facebook = facebook;
		if(this.facebook!=null)
			this.mediosContacto.add(this.facebook);
	}

	public Twitter getTwitter() {
		return twitter;
	}

	
	public void setTwitter(Twitter twitter) {
		if(this.twitter!=null)
			this.mediosContacto.remove(this.twitter);
		this.twitter = twitter;
		if(this.twitter!=null)
			this.mediosContacto.add(this.twitter);
	}


	/**
	 * @return the domicilioFiscal
	 */
	public DomicilioFiscal getDomicilioFiscal() {
		return domicilioFiscal;
	}


	/**
	 * @param domicilioFiscal the domicilioFiscal to set
	 */
	public void setDomicilioFiscal(DomicilioFiscal domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}


	/**
	 * @return the identificadores
	 */
	public List<Identificador> getIdentificadores() {
	    if(identificadores == null) {
	        identificadores = new ArrayList<Identificador>();
	    }
		return identificadores;
	}


	/**
	 * @param identificadores the identificadores to set
	 */
	public void setIdentificadores(List<Identificador> identificadores) {
		this.identificadores = identificadores;
	}

	public List<MedioContacto> getMediosContactoFiscales() {
		return mediosContactoFiscales;
	}


	public void setMediosContactoFiscales(
			List<MedioContacto> mediosContactoFiscales) {
		this.mediosContactoFiscales = mediosContactoFiscales;
	}


	/**
	 * @return the telefonoFijoFiscal
	 */
	public TelefonoFijo getTelefonoFijoFiscal() {
		return telefonoFijoFiscal;
	}


	/**
	 * @param telefonoFijoFiscal the telefonoFijoFiscal to set
	 */
	public void setTelefonoFijoFiscal(TelefonoFijo telefonoFijoFiscal) {			
		if (telefonoFijoFiscal != null){
			ListIterator<MedioContacto> it = this.mediosContactoFiscales.listIterator();
			
			MedioContacto medio = null;
			while(it.hasNext()){
				medio = it.next();
				
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
					it.remove();
				}
			}
		}
		
		this.telefonoFijoFiscal = telefonoFijoFiscal;
		
		if (this.telefonoFijoFiscal != null){
			this.mediosContactoFiscales.add(this.telefonoFijoFiscal);
		}
	}


	/**
	 * @return the telefonoMovilFiscal
	 */
	public TelefonoMovil getTelefonoMovilFiscal() {
		return telefonoMovilFiscal;
	}


	/**
	 * @param telefonoMovilFiscal the telefonoMovilFiscal to set
	 */
	public void setTelefonoMovilFiscal(TelefonoMovil telefonoMovilFiscal) {
		if (telefonoMovilFiscal != null){
			ListIterator<MedioContacto> it = this.mediosContactoFiscales.listIterator();
			
			MedioContacto medio = null;
			while(it.hasNext()){
				medio = it.next();
				
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
					it.remove();
				}
			}
		}
		
		this.telefonoMovilFiscal = telefonoMovilFiscal;
		
		if (this.telefonoMovilFiscal != null){
			this.mediosContactoFiscales.add(this.telefonoMovilFiscal);
		}
	}


	/**
	 * @return the correoElectronicoFiscal
	 */
	public CorreoElectronico getCorreoElectronicoFiscal() {
		return correoElectronicoFiscal;
	}


	/**
	 * @param correoElectronicoFiscal the correoElectronicoFiscal to set
	 */
	public void setCorreoElectronicoFiscal(CorreoElectronico correoElectronicoFiscal) {
		if (correoElectronicoFiscal != null){
			ListIterator<MedioContacto> it = this.mediosContactoFiscales.listIterator();
			
			MedioContacto medio = null;
			while(it.hasNext()){
				medio = it.next();
				
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
					it.remove();
				}
			}
		}
		
		this.correoElectronicoFiscal = correoElectronicoFiscal;
		
		if (this.correoElectronicoFiscal != null){
			this.mediosContactoFiscales.add(this.correoElectronicoFiscal);
		}
	}
	
	/**
	 * Método para settear el telefono fijo sin 
	 * agregarlo a la coleccion de medios de contacto
	 * 
	 * @param telefonoFijo
	 */
	public void setTelefonoFijoAux(TelefonoFijo telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * Método para settear el telefono movil sin 
	 * agregarlo a la coleccion de medios de contacto
	 * 
	 * @param telefonoMovil the telefonoMovil to setS
	 */
	public void setTelefonoMovilAux(TelefonoMovil telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	/**
	 * Método para settear el correo electrónico sin 
	 * agregarlo a la coleccion de medios de contacto
	 * 
	 * 
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronicoAux(CorreoElectronico correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	/**
	 * Método para settear la cuenta de facebook sin 
	 * agregarla a la coleccion de medios de contacto
	 * 
	 * 
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setFacebookAux(Facebook facebook) {
		this.facebook = facebook;
	}

	/**
	 * Método para settear la cuenta de twitter sin 
	 * agregarla a la coleccion de medios de contacto
	 * 
	 * 
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setTwitterAux(Twitter twitter) {
		this.twitter = twitter;
	}
	
	/**
	 * Método para settear el telefono fijo fiscal sin 
	 * agregarlo a la coleccion de medios de contacto fiscales
	 * 
	 * @param telefonoFijo
	 */
	public void setTelefonoFijoFiscalAux(TelefonoFijo telefonoFijoFiscal) {
		this.telefonoFijoFiscal = telefonoFijoFiscal;
	}

	/**
	 * Método para settear el telefono movil fiscal sin 
	 * agregarlo a la coleccion de medios de contacto fiscales
	 * 
	 * @param telefonoMovil the telefonoMovil to set
	 */
	public void setTelefonoMovilFiscalAux(TelefonoMovil telefonoMovilFiscal) {
		this.telefonoMovilFiscal = telefonoMovilFiscal;
	}

	/**
	 * Método para settear el correo electrónico fiscal sin 
	 * agregarlo a la coleccion de medios de contacto fiscales
	 * 
	 * 
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronicoFiscalAux(CorreoElectronico correoElectronicoFiscal) {
		this.correoElectronicoFiscal = correoElectronicoFiscal;
	}

	/**
	 * @return the rfcOriginal
	 */
	public String getRfcOriginal() {
		return rfcOriginal;
	}

	/**
	 * @param rfcOriginal
	 *            the rfcOriginal to set
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
	 * @param rfcSolicitado
	 *            the rfcSolicitado to set
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
	 * @param rfcVigente
	 *            the rfcVigente to set
	 */
	public void setRfcVigente(String rfcVigente) {
		this.rfcVigente = rfcVigente;
	}
	
	/**
	 * Settea el Acta de Nacimiento sin agregarla a la lista de documentos
	 * probatorios
	 * 
	 * @param actaNacimiento
	 */
	public void setActaNacimientoAux(Nacimiento actaNacimiento) {
		this.actaNacimiento = actaNacimiento;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getIndRIF() {
		return indRIF;
	}

	/**
	 * 
	 * @param indRIF
	 */
	public void setIndRIF(Boolean indRIF) {
		this.indRIF = indRIF;
	}


	public Fiel getFiel() {
		return fiel;
	}


	public void setFiel(Fiel fiel) {
		this.fiel = fiel;
	}
	
	public Long getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(Long idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
}
