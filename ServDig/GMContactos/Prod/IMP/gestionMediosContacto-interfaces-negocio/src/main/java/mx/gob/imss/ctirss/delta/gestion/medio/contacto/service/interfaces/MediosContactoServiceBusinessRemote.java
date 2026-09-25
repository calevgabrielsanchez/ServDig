package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.ParametroContactoRequeridoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PersonaContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;

@Remote
public interface MediosContactoServiceBusinessRemote {

	/**
	 * Servicio para el registro de medios de contacto Este servicio recibe una
	 * lista de diferentes tipos de medios de contacto ( Cualquier clase que
	 * extienda de Medio de Contacto ) para registrarlos.
	 * 
	 * @param mediosDeContacto
	 *            Lista de medios de contacto a registrar
	 * @return El servicio regresa los medios de contacto son su clave generada.
	 * @throws RegistrarMedioContactoException
	 *             En caso de existir algun error al guardar los medios de
	 *             contacto.
	 */
	List<MedioContacto> registrarMedioDeContacto(List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException;
	
	
	
	/**
	 * Servicio que consulta los medios de contacto de una persona ( Persona ) 
	 * 
	 * @param persona Persona con el Id de la persona
	 * @return Lista de medios de contacto de la persona
	 * @throws PersonaSinMedioDeContactoException Si la persona no tiene 
	 * 			medios de contaco asignados.
	 */
	List<MedioContacto> consultarMedioDeContactoPersona(
			Persona persona) throws PersonaSinMedioDeContactoException;
	
	/**
	 * Servicio para el registro/actualización de medios de contacto Este servicio recibe una
	 * lista de diferentes tipos de medios de contacto ( Cualquier clase que
	 * extienda de Medio de Contacto ) para registrarlos.
	 * 
	 * @param mediosDeContacto
	 *            Lista de medios de contacto a registrar
	 * @return El servicio regresa los medios de contacto son su clave generada.
	 * @throws RegistrarMedioContactoException
	 *             En caso de existir algun error al guardar los medios de
	 *             contacto.
	 */
	List<MedioContacto> actualizarMedioDeContacto(List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException;
	
	/**
	 * Este servicio recupera la lista de medios de contactos asociados a los distintos propietarios
	 * de medios de contacto (REPRESENTANTE LEGAL, PERSONA, SOCIO, CENTRO DE TRABAJO, DERECHOHABIENTE.)
	 * con base en su identificador.
	 * 
	 * En caso de proporcionar un idTramite el servicio consulta la información del trámite y devuelve 
	 * los medios de contacto previamente almacenados en la descripción detallada del trámite en base
	 * al tipo e identificador del propietario.
	 * 
	 * @param idPropiertarioContacto
	 * 			Identificador del propietario del medio de contacto, puede ser un: 
	 * 			REPRESENTANTE LEGAL, PERSONA, SOCIO, CENTRO DE TRABAJO, DERECHOHABIENTE.
	 * @param idTramite
	 * 			Opcional: Identificador del trámite del cuál se obtendrá la información de
	 * 			medios de contacto.
	 * @param propietario 
	 * 			Enumerado que indica a que tipo de individuo pertenece el contacto:
	 * 			REPRESENTANTE LEGAL, PERSONA, SOCIO, CENTRO DE TRABAJO, DERECHOHABIENTE.
	 * @return List<MedioContacto>
	 * @throws PersonaSinMedioDeContactoException
	 */
	List<MedioContacto> consultarMediosContactoPorTipoPropietario(Long idPropiertarioContacto, 
			Long idSolicitud, PropietarioMedioContactoEnum propietario, 
//			TipoPersona tipoPersona, 
			Long idPatronSujetoObligado ) 
					throws PersonaSinMedioDeContactoException, ParametroContactoRequeridoException, SolicitudNoEncontradaException;
	
	/**
	 * Obtiene los tipos de contacto activos configurados en la base de datos
	 * @return
	 */
	List<TipoContacto> consultarTipoContacto();
	
	/**
	 * Servicio que consulta los medios de contacto fiscales de una persona ( Persona ) 
	 * 
	 * @param persona Persona con el Id de la persona
	 * @return Lista de medios de contacto fiscales de la persona
	 * @throws PersonaSinMedioDeContactoException Si la persona no tiene 
	 * 			medios de contaco asignados.
	 */
	List<MedioContacto> consultarMediosFiscalesPersona(Persona persona)
			throws PersonaSinMedioDeContactoException;
	
	/**
	 * Método que guarda un medio de contacto y lo asocia a la persona
	 * 
	 * @param medioContacto
	 * @param cvePersona
	 * @return MedioContacto
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto registrarAsociarMedioContactoPersona(
			MedioContacto medioContacto, Long cvePersona)
			throws RegistrarMedioContactoException;

	/**
	 * Método que guarda un medio de contacto y lo asocia a la persona
	 * moral
	 * 
	 * @param medioContacto
	 * @param cvePersonaMoral
	 * @return MedioContacto
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto registrarAsociarMedioContactoPersonaMoral(
			MedioContacto medioContacto, Long cvePersonaMoral)
			throws RegistrarMedioContactoException;
	
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
	 * Método que guarda un medio de contacto fiscal y lo asocia a la persona
	 * física
	 * 
	 * @param medioContacto
	 * @param cvePersonaFisica
	 * @return MedioContacto
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto registrarAsociarMedioContactoFiscalPersonaFisica(
			MedioContacto medioContacto, Long cvePersonaFisica)
			throws RegistrarMedioContactoException;

	/**
	 * Método que guarda un medio de contacto fiscal y lo asocia a la persona
	 * moral
	 * 
	 * @param medioContacto
	 * @param cvePersonaMoral
	 * @return MedioContacto
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto registrarAsociarMedioContactoFiscalPersonaMoral(
			MedioContacto medioContacto, Long cvePersonaMoral)
			throws RegistrarMedioContactoException;

	/**
	 * Método que actualiza un medio de contacto
	 * 
	 * @param medioContacto
	 * @return
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto actualizarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException;

	/**
	 * Método que borra el medio de contacto y la relación con la persona
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaMoral
	 * @throws RegistrarMedioContactoException
	 */
	void eliminarMedioDeContacto(Long cveMedioContacto)
			throws RegistrarMedioContactoException;

	/**
	 * Serivicio para obtener los medios asociados a una solicitud de
	 * modificación de datos que está en proceso
	 * 
	 * @param idSolicitud
	 * @param isFiscal
	 * @return
	 * @throws SolicitudNoEncontradaException
	 */
	List<MedioContacto> retomarTramiteAdmonMedios(Long idSolicitud,
			boolean isFiscal) throws SolicitudNoEncontradaException;
	
	/**
	 * Retorna los medios de contacto asociados a un centro de trabajo/registro patronal
	 * @param idPatronSujetoObligado
	 * @return List<MedioContacto>
	 */
	List<MedioContacto> consultarMedioContactoDeRegistroPatronal(Long idPatronSujetoObligado)throws PersonaSinMedioDeContactoException;
	
	/**
	 * Consultan medios de centro de trabajo
	 * @param centro
	 * @return List<MedioContacto>
	 */
	List<MedioContacto> consultarMedioContactoDeCentroTrabajo(CentroTrabajo centro);
	
	mx.gob.imss.digital.modelo.persona.Persona obtenerListaDeMediosContacto(mx.gob.imss.digital.modelo.persona.Persona persona);	
	
	boolean validarExistenciaCorreoElectronicoPersona(Persona persona);

	void asociarCorreoMedioContactoPersona(Long idPersona, String correo);
	
}
