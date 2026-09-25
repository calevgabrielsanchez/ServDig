package mx.gob.imss.ctirss.gestionpersonas.servicios.dictamen;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PersonaContacto;

@Remote
public interface DictamenServiceBusinessRemote {

	/**
	 * Servicio que crea una persona nueva a partir de las entidades externas
	 * RENAPO y/o SAT. <br>
	 * Recibe una instancia de persona fisica o moral. <br>
	 * Para persona fisica requiera CURP y/o RFC. <br>
	 * Para persona moral requiere RFC. <br>
	 * Para ambos casos califica la persona de acuerdo a las entidades
	 * consultadas <br>
	 * Puede crear la solicitud de registro de persona si lo requiere.
	 * 
	 * @param persona
	 * @param crearSolicitud
	 * @return Persona
	 * @throws RegistroPersonaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws RegistroPersonaFisicaException
	 * @throws SolicitudNoValidaException
	 * @throws PersonaNoEncontradaException
	 * @throws DomicilioNoValidoException
	 */
	Persona registrarPersonaConEntidadesExternas(Persona persona,
			boolean crearSolicitud) throws RegistroPersonaException,
			ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException, RegistroPersonaFisicaException,
			SolicitudNoValidaException, PersonaNoEncontradaException,
			DomicilioNoValidoException;

	/**
	 * Método que guarda un medio de contacto y lo asocia a la persona fisica
	 * 
	 * @param medioContacto
	 * @param cvePersona
	 * @return PersonaContacto
	 * @throws RegistrarMedioContactoException
	 */
	PersonaContacto registrarMedioContactoPersonafContacto(
			MedioContacto medioContacto, Long cvePersona)
			throws RegistrarMedioContactoException;

	/**
	 * Método que guarda un medio de contacto y lo asocia a la persona moral
	 * moral
	 * 
	 * @param medioContacto
	 * @param cvePersonaMoral
	 * @return PersonaContacto
	 * @throws RegistrarMedioContactoException
	 */
	PersonaContacto registrarMedioContactoPersonamContacto(
			MedioContacto medioContacto, Long cvePersonaMoral)
			throws RegistrarMedioContactoException;

	/**
	 * Servicio que crea un domicilio fiscal ( domicilio SAT ).
	 * 
	 * @param domicilioFiscal
	 * @return DomicilioFiscal
	 * @throws DomicilioNoValidoException
	 */
	DomicilioFiscal registrarDomicilioFiscal(DomicilioFiscal domicilioFiscal)
			throws DomicilioNoValidoException;

	/**
	 * Servicio que crea la relacion entre persona fisica y domicilio fiscal
	 * 
	 * @param cveDomicilioFiscal
	 * @param cveFisica
	 * @throws AsociarDomicilioException
	 */
	long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
			Long cveFisica) throws AsociarDomicilioException;

	/**
	 * Servicio que crea la relacion entre persona moral y domicilio fiscal
	 * 
	 * @param cveDomicilioFiscal
	 * @param cveMoral
	 * @throws AsociarDomicilioException
	 */
	long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
			Long cveMoral) throws AsociarDomicilioException;

	
	Object actualizaFechaBaja(Object entidad);
}