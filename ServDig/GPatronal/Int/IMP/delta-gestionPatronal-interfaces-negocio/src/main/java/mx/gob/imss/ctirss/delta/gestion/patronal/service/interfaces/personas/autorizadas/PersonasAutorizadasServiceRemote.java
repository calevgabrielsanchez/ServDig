package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface PersonasAutorizadasServiceRemote {

	List<PersonaAutorizada> getPersonasAutorizadasByPersona(Persona persona);
	List<SujetoObligado> getSujetosObligadosPorPersonaAutorizada(Long idPersona);
	List<SujetoObligado> getSujetosObligadosYaRepresentadosPorPersona(Persona empresa, Fisica autorizada);
	void finalizarSolicitudRegistroPersonaAutorizada(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException;
	void finalizarSolicitudBajaPersonaAutorizada(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException;
	
	/**
	 * Actualiza la base de datos con la información de personas autorizadas contenidas en el trámite
	 * @param tramite
	 * @throws GestionPatronalBusinessException
	 */
	void afectarTramiteAltaPersonaAutorizada(Tramite tramite, Long idSolicitud)throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la base de datos con la baja acorde de personas autorizadas contenidas en el trámite
	 * @param tramite
	 * @throws GestionPatronalBusinessException
	 */
	void afectarTramiteBajaPersonaAutorizada(Tramite tramite)throws GestionPatronalBusinessException;

	/**
	 * Agrega la lista de personas autorizadas proporcionada
	 * 
	 * @param personasAutorizadas
	 * @throws GestionPatronalBusinessException
	 */
	void agregarPersonasAutorizadas(List<PersonaAutorizada> personasAutorizadas)throws GestionPatronalBusinessException;
	
	/**
	 * Agrega una personas autorizadas proporcionada, si la persona fisica no lleva id se agrega su registro como persona fisica
	 * 
	 * @param personasAutorizadas
	 * @throws GestionPatronalBusinessException
	 */
	void agregarPersonaAutorizada(PersonaAutorizada personaAutorizada)throws GestionPatronalBusinessException;
}
