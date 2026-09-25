package mx.gob.imss.ctirss.gestionpersonas.servicios.dictamen;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PersonaContacto;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

@Stateless(name="dictamenServiceBusiness" ,mappedName="dictamenServiceBusiness" )
public class DictamenServiceBusiness extends AbstractServiceBusiness implements DictamenServiceBusinessRemote {
	
	@EJB
	private PersonaBusinessRemote personaBusinessRemote;
	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

	@Override
	public Persona registrarPersonaConEntidadesExternas(Persona persona,
			boolean crearSolicitud) throws RegistroPersonaException,
			ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException, RegistroPersonaFisicaException,
			SolicitudNoValidaException, PersonaNoEncontradaException,
			DomicilioNoValidoException {
		persona = personaBusinessRemote.registrarPersonaConEntidadesExternas(persona, crearSolicitud);
		return persona;
	}

	@Override
	public PersonaContacto registrarMedioContactoPersonafContacto(
			MedioContacto medioContacto, Long cvePersona)
			throws RegistrarMedioContactoException {
		
		PersonaContacto personaContacto = mediosContactoServiceBusinessRemote.registrarMedioContactoPersonafContacto(medioContacto, cvePersona);
		
		return personaContacto;
	}

	@Override
	public PersonaContacto registrarMedioContactoPersonamContacto(
			MedioContacto medioContacto, Long cvePersonaMoral)
			throws RegistrarMedioContactoException {
		
		PersonaContacto personaContacto = mediosContactoServiceBusinessRemote.registrarMedioContactoPersonamContacto(medioContacto, cvePersonaMoral);
		
		return personaContacto;
	}

	@Override
	public DomicilioFiscal registrarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException {
		
		domicilioFiscal = domicilioServiceBusinessRemote.registrarDomicilioFiscal(domicilioFiscal);
		this.log.debug("al salir del mentodo la clave es desde el servicio expuesto [" +domicilioFiscal.getClave()+"]" );
		
		return domicilioFiscal;
	}

	@Override
	public long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
			Long cveFisica) throws AsociarDomicilioException {
		
		long cveIdPfdomFiscal = domicilioServiceBusinessRemote.asociarDomicilioFiscalPersonaFisica(cveDomicilioFiscal, cveFisica);
		
		return cveIdPfdomFiscal;
		
	}

	@Override
	public long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
			Long cveMoral) throws AsociarDomicilioException {
		
		long cveIdPmdomFiscal = domicilioServiceBusinessRemote.asociarDomicilioFiscalPersonaMoral(cveDomicilioFiscal, cveMoral);
		
		return cveIdPmdomFiscal;
		
	}

	@Override
	public Object actualizaFechaBaja(Object entidad) {
		// TODO Auto-generated method stub
		return personaBusinessRemote.actualizaFechaBajaEntidad(entidad);		
	}

}