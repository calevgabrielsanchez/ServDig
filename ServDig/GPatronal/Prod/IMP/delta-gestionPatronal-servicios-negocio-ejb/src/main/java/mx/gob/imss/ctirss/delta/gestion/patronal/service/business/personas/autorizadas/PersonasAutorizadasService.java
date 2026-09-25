package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.personas.autorizadas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception.ParametrosInvalidosException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.autorizadas.PersonasAutorizadasEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaPersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;

/**
 * Servicios de persona autorizada
 * @author Mario Teran Blando
 *
 */
@Stateless(name="personasAutorizadasService", mappedName = "personasAutorizadasService")
public class PersonasAutorizadasService extends AbstractServiceBusiness implements
		PersonasAutorizadasServiceRemote {

	@EJB PersonasAutorizadasEntityLocal personasAutorizadasEntityLocal;
	@EJB(name="solicitudBusiness", mappedName = "solicitudBusiness") 
	SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name="personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness") 
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusinessRemote;
	@EJB 
	ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	@EJB
	private SolicitudServiceBusinessRemote solicitudService;
	
	/**
	 * Metodo para obtener las personas autorizadas de una persona ya sea fisica o moral
	 * @param persona - Objeto persona que debe incluir el rfc y el tipo de persona ya sea fisica o moral
	 */
	@Override
	public List<PersonaAutorizada> getPersonasAutorizadasByPersona(
			Persona persona) {
		
		List<PersonaAutorizada> personasAutorizadas = null;
		//Se consultan las personas autorizadas
		try {
			personasAutorizadas = personasAutorizadasEntityLocal.getPersonasAutorizadasByPersonaMF(persona);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return personasAutorizadas;
	}

	
	/**
	 * Metodo para obtener a los patrones que tienen a la persona con idPersona como persona autorizada
	 * @param idPersona el id de la persona que esta como persona autorizada
	 */
	@Override
	public List<SujetoObligado> getSujetosObligadosPorPersonaAutorizada(Long idPersona) {
		List<SujetoObligado> listaPatrones = null;
		
		listaPatrones = personasAutorizadasEntityLocal.getSujetosObligadosByIdPersonaAutorizada(idPersona);
		
		return listaPatrones;
	}


	/**
	 * Metodo para obtener los patrones ya representados de acuerdo a un rfc y una persona fisica
	 * @param empresa - Tipo Persona debe incluir el rfc y el tipo de persona a buscar
	 * @param autorizada - Tipo Fisica debe incluir el cveIdPersoaFisica
	 * @return List<SujetoObligado> - Lista de los sujetos obligados que ya tienen como persona autorizada a la personada
	 */
	@Override
	public List<SujetoObligado> getSujetosObligadosYaRepresentadosPorPersona(
			Persona empresa, Fisica autorizada) {
		List<PersonaAutorizada> personasAutorizadas = null;
		List<SujetoObligado> patrones = null;
		
		//Se buscan las ralciones que existan de acuerdo a la empresa y a la persona
		try {
			personasAutorizadas = personasAutorizadasEntityLocal.getPersonaAutorizadasPorPersonaMFYAutorizada(empresa, autorizada);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//En caso de que la lista no sea nula o vacia agregamos al sujeto obligado a la lista
		if(personasAutorizadas != null && !personasAutorizadas.isEmpty()) {
			patrones = new ArrayList<SujetoObligado>();
			for(PersonaAutorizada persona: personasAutorizadas) {
				patrones.add(persona.getSujetoObligado());
			}
		}
		//retornamos a los patrones encontrados
		return patrones;
	}

	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud - Tipo Solicitud a solicitud a finalizar, debe de incluir los datos de la misma y sus tramites
	 * @throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException
	 */
	@Override
	public void finalizarSolicitudRegistroPersonaAutorizada(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException{
		
		solicitud.setFechaConclusion(Calendar.getInstance().getTime());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());	

		//Actualizamos los estados de la solicitud
		solicitudBusinessRemote.actualizarEstados(solicitud);
		//Actualizamos los tramites
		for(Tramite tramite: solicitud.getTramites()) {
			tramite.getEstadoTramite().setIdEstadoTramitePersona(
					EstadoTramiteEnum.CERRADO.getCodigo());
			tramite.setFechaConclusion(Calendar.getInstance().getTime());
		}

		//Obtenemos el tramite para guardar la relacion de persona autorizada
		TramitePersonaAutorizada tPA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);

		//Obtenemos la persona a autorizar
		Fisica personaAutorizada = tPA.getPersonaAutorizada();

		//Verificamos que la persona este registrada
		if(personaAutorizada.getIdPersona() == null) {
			personaAutorizada = personaFisicaServiceBusinessRemote.registrar(personaAutorizada);
		}

		//A cada patron que encontremos los relacionaremos con la persona
		for(SujetoObligado so: tPA.getSujetosObligados()) {
			PersonaAutorizada personaA = new PersonaAutorizada();
			personaA.setSujetoObligado(so);
			personaA.setFisica(personaAutorizada);

			personasAutorizadasEntityLocal.savePersonaAutorizada(personaA);
		}
		
	}
	
	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud - Tipo Solicitud a solicitud a finalizar, debe de incluir los datos de la misma y sus tramites
	 * @throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException
	 */
	@Override
	public void finalizarSolicitudBajaPersonaAutorizada(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException{
		
		solicitud.setFechaConclusion(Calendar.getInstance().getTime());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());	

		//Actualizamos los estados de la solicitud
		solicitudBusinessRemote.actualizarEstados(solicitud);
		//Actualizamos los tramites
		for(Tramite tramite: solicitud.getTramites()) {
			tramite.getEstadoTramite().setIdEstadoTramitePersona(
					EstadoTramiteEnum.CERRADO.getCodigo());
			tramite.setFechaConclusion(Calendar.getInstance().getTime());
		}

		//Obtenemos el tramite para guardar la relacion de persona autorizada
		TramiteBajaPersonaAutorizada tPA = (TramiteBajaPersonaAutorizada) solicitud.getTramites().get(0);

		for(PersonaAutorizada personaA: tPA.getPersonasAutorizadas()) {
			personaA.setFechaBaja(new Date());
			try {
				personasAutorizadasEntityLocal.updatePersonaAutorizada(personaA);
			} catch (ParametrosInvalidosException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void afectarTramiteAltaPersonaAutorizada(Tramite tramite, Long idSolicitud)
			throws GestionPatronalBusinessException {
		//Obtenemos el tramite para guardar la relacion de persona autorizada
		TramitePersonaAutorizada tPA = (TramitePersonaAutorizada) tramite;

		//Obtenemos la persona a autorizar
		Fisica personaAutorizada = tPA.getPersonaAutorizada();

		//Verificamos que la persona este registrada
		if(personaAutorizada.getIdPersona() == null) {
			try {
				//se conservan medios de contacto proporcionados
				TelefonoFijo tf=personaAutorizada.getTelefonoFijo();
				TelefonoMovil tm =personaAutorizada.getTelefonoMovil();
				CorreoElectronico correo = personaAutorizada.getCorreoElectronico();
				//validamos nuevamente q la persona no este dada de alta, 
				//esto porq quiza otro proceso pudo darla de antes mientras 
				//se creo el tramite de alta y se concluyo
				personaAutorizada=buscarPersonaFisica(personaAutorizada);
				
				personaAutorizada.setTelefonoFijo(tf);
				personaAutorizada.setTelefonoMovil(tm);
				personaAutorizada.setCorreoElectronico(correo);
				
				if(personaAutorizada.getIdPersona()==null){
					personaAutorizada = personaFisicaServiceBusinessRemote.registrar(personaAutorizada);
					solicitudService.agregarTramiteASolicitud(
						idSolicitud, creaTramitePersonaFisica(personaAutorizada));
				}else if(personaAutorizada.getCveFisica()==null){
					personaAutorizada = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaAutorizada);
				}else if(personaAutorizada.getCveFisica()==-1l){
					personaAutorizada.setCveFisica(null);
					personaAutorizada = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaAutorizada);
				}
				tPA.setPersonaAutorizada(personaAutorizada);
			} catch (RegistroPersonaFisicaException e) {
				e.printStackTrace();
				throw new GestionPatronalBusinessException("Se presentó una falla al registrar a la persona autorizada como persona física");
			}
		}else if(personaAutorizada.getCveFisica()==null){
			//en este punto la persona del tramite no tenia cve fisica
			//se busca en base para corroborar q sigue sin registro en dit_persona_fisica al concluir el tramite
			
			personaAutorizada=buscarPersonaFisica(personaAutorizada);
			if(personaAutorizada.getCveFisica()==null)
				personaAutorizada = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaAutorizada);
		}else if(personaAutorizada.getCveFisica()==-1l){
			personaAutorizada.setCveFisica(null);
			personaAutorizada = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaAutorizada);
		}

		//A cada patron que encontremos los relacionaremos con la persona
		for(SujetoObligado so: tPA.getSujetosObligados()) {
			PersonaAutorizada personaA = new PersonaAutorizada();
			personaA.setSujetoObligado(so);
			personaA.setFisica(personaAutorizada);

			personasAutorizadasEntityLocal.savePersonaAutorizada(personaA);
		}

		
	}

	@Override
	public void afectarTramiteBajaPersonaAutorizada(Tramite tramite)
			throws GestionPatronalBusinessException {
		//Obtenemos el tramite para guardar la relacion de persona autorizada
		TramiteBajaPersonaAutorizada tPA = (TramiteBajaPersonaAutorizada) tramite;

		for(PersonaAutorizada personaA: tPA.getPersonasAutorizadas()) {
			personaA.setFechaBaja(new Date());
			try {
				personasAutorizadasEntityLocal.updatePersonaAutorizada(personaA);
			} catch (ParametrosInvalidosException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public void agregarPersonasAutorizadas(List<PersonaAutorizada> personasAutorizadas)throws GestionPatronalBusinessException{
		for(PersonaAutorizada personaAutorizada: personasAutorizadas ){
			agregarPersonaAutorizada(personaAutorizada);
		}
	}
	
	public void agregarPersonaAutorizada(PersonaAutorizada personaAutorizada)throws GestionPatronalBusinessException{
		Fisica personaAsociar=personaAutorizada.getFisica();
		//Verificamos que la persona este registrada
		if(personaAsociar.getIdPersona() == null) {
			try {
				//se conservan medios de contacto proporcionados
				TelefonoFijo tf=personaAsociar.getTelefonoFijo();
				TelefonoMovil tm =personaAsociar.getTelefonoMovil();
				CorreoElectronico correo = personaAsociar.getCorreoElectronico();
				//validamos nuevamente q la persona no este dada de alta, 
				//esto porq quiza otro proceso pudo darla de antes mientras 
				//se creo el tramite de alta y se concluyo
				personaAsociar=buscarPersonaFisica(personaAsociar);
				
				personaAsociar.setTelefonoFijo(tf);
				personaAsociar.setTelefonoMovil(tm);
				personaAsociar.setCorreoElectronico(correo);
				
				if(personaAsociar.getIdPersona()==null)
					personaAsociar = personaFisicaServiceBusinessRemote.registrar(personaAsociar);
				personaAutorizada.setFisica(personaAsociar);

			} catch (RegistroPersonaFisicaException e) {
				e.printStackTrace();
				throw new GestionPatronalBusinessException("Se presentó una falla al registrar a la persona autorizada como persona física");
			}
		}
		personaAutorizada.setFechaAlta(Calendar.getInstance().getTime());
		//A cada patron que encontremos los relacionaremos con la persona
		personasAutorizadasEntityLocal.savePersonaAutorizada(personaAutorizada);
				

	}
	
	private Fisica buscarPersonaFisica(Fisica persona)throws GestionPatronalBusinessException{
		try {
			persona = consultaPersonaFisicaServiceBusinessRemote.getPersonaByCurpImssEntidadesExternas(persona);
			return persona;
		} catch (ClienteWebserviceRenapoCurpException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ClienteWebserviceSatRfcException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ErrorComparacionDatosSATException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (DiferenciasRENAPOContraSAT e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (PersonaSinCalificacionesException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}

	}
	
	private TramiteFisica creaTramitePersonaFisica(Fisica personaAutorizada){
		TramiteFisica tpf =  new TramiteFisica();
		tpf.setFisica(personaAutorizada);
		tpf.setEstadoTramite(new EstadoTramite());
		tpf.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tpf.setTipoTramite(new TipoTramite());
		tpf.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
		return tpf;
	}
}
