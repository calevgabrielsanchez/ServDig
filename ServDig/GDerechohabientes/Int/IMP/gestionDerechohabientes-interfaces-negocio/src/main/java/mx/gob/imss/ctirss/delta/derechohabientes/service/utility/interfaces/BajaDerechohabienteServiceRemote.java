package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)	
 * @date 17/04/2012
 */
@Remote
public interface BajaDerechohabienteServiceRemote {

	/**
	 * Metodo para obtener a loa integrantes del grupo familiar candidatos a baja administrativa
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaAdministrativa(Long idAsignacionNss, Usuario usuario) throws DerechohabientesBusinessException;
	/**
	 * metodo para obtener a los candidatos a baja  por defuncion
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaDefuncion(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	/**
	 * metodo para obtener a los candidatos a baja  por termino de dependencia o convivencia
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaConvivencia(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	/**
	 * metodo para obtener a los candidatos a baja  por termino de concubinato
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaConcubinato(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * metodo para obtener a los candidatos a baja  por divorcio
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaDivorcio(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * metodo para obtener a los candidatos a baja  por unión civil
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarBajaUnionCivil(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * Se crea metodo generico para guardar la baja de derechohabiente
	 * @param idDerechohabiente
	 * @param usuario
	 * @param aseguradoPensionado
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Map<String, Object> saveSolicitudBajaDerechohabiente(Long idDerechohabiente, TipoBajaDerechohabienteEnum tipoBaja, AsignacionNSS aseguradoPensionado, OrigenSolicitudEnum origen,Usuario usuario) throws DerechohabientesBusinessException;
	Solicitud inicioAutorizacionBaja(Long idTramite, AsignacionNSS nss) throws DerechohabientesBusinessException;
	Solicitud rechazarSolicitudBaja(Long idSolicitud, Long idPersona, Long idTramite, Long idRechazo, String observaciones, Fisica personaUsuario) throws DerechohabientesBusinessException;
	
	Solicitud finalizarSolicitudBaja(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException;
	Solicitud finalizarSolicitudBaja(Solicitud solicitud, AsignacionNSS asignacionNSS) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException;
	
	Solicitud finalizarSolicitudBajaNormativa(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, Exception;
	
	/**
	 * 
	 * 
	 * @param entrada
	 * @return
	 */
	List<GrupoFamiliar> quitarBajasDefuncion(List<GrupoFamiliar> entrada);
	List<GrupoFamiliar> quitarBajasNoAdministrativas(List<GrupoFamiliar> entrada);
	List<GrupoFamiliar> quitarEstados(List<GrupoFamiliar> entrada,EstadoDerechohabienteEnum estado);
	List<BajaDerechohabienteDto> getBajaDerechohabiente(Long idAsignasionNss, List<Long> idPersonas, List<Long> tiposBaja, Boolean activa);
	List<GrupoFamiliar> quitarIntegrantesConTramiteBaja(List<GrupoFamiliar> grupoFamiliar, List<Long> tiposBaja);
	void actualizarInsertarBaja(BajaDerechohabienteDto bajaDto) throws IllegalArgumentException, TransactionRequiredException;

	
	/**
	 * Busca bajas adminstrativas activas en DitBajaDerechohabiente; en caso de encontrar alguna regresa true
	 * 
	 * @param idAsignasionNss
	 * @param idPersona
	 * @return boolean
	 */
	boolean tieneBajaAdministrativaActiva(Long idAsignasionNss, Long idPersona);
}