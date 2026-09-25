package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Local
public interface SolicitudDaoLocal {

	/**
	 * Regresa una solicitud buscada por el numero de folio indicado
	 * @param folio
	 * @return solicitud que concuerda con el folio indicado
	 * @throws Exception 
	 */
	public Solicitud getSolicitudByFolio(String folio) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Regresa una solicitud que concuerde con el id indicado
	 * @param idSolicitud
	 * @return Solicitud encontrada
	 */
	public Solicitud getSolicitudById(Long idSolicitud) throws Exception ;
	
	/**
	 * Regresa el numero de solicitudes para la fechaUMF y turno
	 * @param solicitud
	 * @return int numero de solicitudes
	 * @throws Exception 
	 */
	public Long countSolicitudesFechaTurnoUmf(CitaSolicitud cita) throws Exception;

	/**
	 * Lista las solicitudes con estatus pendiente autorisacion correspondientes al nss o folio 
	 * y que correspondan a la umf del solicitante= al umf de la subdelegacion donde se consultan con paginacion
	 * @param solicitudesPendientesAutorizacionDto
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List<DitSolicitud> solicitudesPendientesDeAutorizacion(SolicitudesPendientesAutorizacionDto solicitudesPendientesAutorizacionDto) throws Exception;
	/**
	 * Lista de solicitudes pendientes de autorizar sin paginacion
	 * @param solicitudesPendientesAutorizacionDto
	 * @return
	 * @throws Exception
	 */
	public List<DitSolicitud> solicitudesPendientesAut(Long idPersona) throws Exception;
	
	/**
	 * Metodo que devuelve un listado de solitifes en base al  objeto de filros que se envia, es obligado que todos los atributos
	 * lleven valor a excepcion del cveIdAsignacion
	 * @param solicitudesDto
	 * @return
	 * @throws Exception
	 */
	List<DitSolicitud> solicitudesDerechohabientes(SolicitudDto solicitudesDto) throws Exception;
	
	/**
	 * Valida si existen tramites rechazados de un derechohabiente
	 * @param idPersona
	 * @param idTipoTramite
	 * @return true o false
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	public List<PersonaInteresadaSolicitud> tramitesRechazados(long idPersona) throws DerechohabientesBusinessException, Exception;
	
	
	Solicitud saveSolicitud(Solicitud solicitud) throws DerechohabientesBusinessException, Exception;
	Solicitud updateSolicitudSaveTramite(Solicitud solicitud) throws DerechohabientesBusinessException, Exception;
	Solicitud updateSolicitudSaveTramite(Solicitud solicitud,List<GrupoFamiliar> integrantes) throws DerechohabientesBusinessException, Exception;
	void savePersonaInteresadaSolicitud(PersonaInteresadaSolicitud miPersonaInteresada) throws DerechohabientesBusinessException, Exception;
	void updateSolicitud(Solicitud solicitud) throws DerechohabientesBusinessException, Exception;
	void updateSolicitudTramites(Solicitud solicitud, Fisica personaUsuario) throws DerechohabientesBusinessException, Exception;

	public Tramite getTramite(Long idTramite, Long idPersona) throws DerechohabientesBusinessException, Exception;
	public Tramite getTramite(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public List<Tramite> findTramites(Long idSolicitud) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * actualisa las solicitudes hechas en los asentamientos que cambian de umf
	 * @param asentamiento
	 * @param medicoEnTurno
	 * @param fechaCita
	 * @return Long
	 * @throws Exception 
	 */
	
	Long updateCitaSolicitudesPorCambioMasivoClinica(Asentamiento asentamiento,
			MedicoEnTurno medicoEnTurno, Date fechaCita) throws Exception;
	
	/**
     * Metodo para insertar movimientos de una solicitud, como cambios de estado o cancelaciones
     *  
     * @param tramite
     * @throws Exception
     */
    void insertBitacoraSegTramite(Tramite tramite) throws Exception;
    
    /**
     * Recupera una lista de solicitudes de una persona (usuario)
     * 
     * @param idPersona
     * @return
     * @throws Exception
     */
    List<DitSolicitud> consultaSolAtendidasByUsuario(SolicitudesAtendidasDto solicitudesAtendidasDto) throws Exception;
    Tramite saveTramite(Tramite tramite) throws Exception;
    List<Tramite> getTramitesBySolicitud(Long idSolicitud) throws Exception;
    
    /**
     * Metodo para consultar si un NSS a realizado tramites de derechohabientes en IMSS digital
     * @param strNSS
     * @return int con valor 0 si no tiene tramites y 1 si tiene uno o mas tramites el NSS
     */
    public int consultaTramitesDerechohabientePorNSS (String strNSS);
    
}
