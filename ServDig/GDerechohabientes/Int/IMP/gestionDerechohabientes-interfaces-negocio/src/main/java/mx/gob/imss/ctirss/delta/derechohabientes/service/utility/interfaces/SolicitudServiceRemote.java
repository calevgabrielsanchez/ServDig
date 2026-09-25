package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;


import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;


/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */

@Remote
public interface SolicitudServiceRemote {
	
	public Boolean tramitePosible(Long idPersona, Long tipoTramite, AsignacionNSS nss);
	public Boolean tramitesAdemasDeRegistro(Long idPersona, AsignacionNSS nss);
	public Tramite tramiteAbierto(Long idPersona, AsignacionNSS nss) throws Exception;
	public Solicitud detalleSolicitud(Long idSolicitud) throws DerechohabientesBusinessException;
	public Tramite getTramiteSolicitud(Long idSolicitud, Long idPersona, Long idTipoTramite) throws DerechohabientesBusinessException;
	public void cancelarSolicitud(Long idSolicitud, Fisica personaUsuario) throws DerechohabientesBusinessException;
	public DatosSalidaPaginador<SolicitudNssDto> listSolicitudesPendAut(SolicitudesPendientesAutorizacionDto solicitudesPendientesAutorizacionDto) throws DerechohabientesBusinessException, Exception;
	
	public TramiteRegistroDerechohabiente detalleRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public TramiteCorreccionDerechohabiente detalleCorreccionDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public TramiteCircunscripcionForanea detalleCircunscripcionForanea(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public TramiteCircunscripcionForanea detalleSuspencionCircunscripcion(Long idTramiteSuspencion) throws DerechohabientesBusinessException, Exception;
	public TramiteProrroga detalleProrroga(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public List<Tramite> findTramites(Long idSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud getSolicitud(String folioSolicitud) throws DerechohabientesBusinessException, Exception;
	public Tramite rechazarSolicitud(Long idSolicitud, Long idPersona, Long idTramite, Long idRechazo, String observaciones, Fisica usuario) throws DerechohabientesBusinessException;
	public Tramite getTramiteAutorizar(Long idTramite) throws DerechohabientesBusinessException, Exception;
	/**
	 * Recupera el nss del un asegurado o cabeza de grupo familiar a travez de una busqueda por folio y usuario firmado.
	 * @param folioSolicitud
	 * @param idPersona
	 * @return AsignacionNss - Numero de seguro social
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	public AsignacionNSS getSolicitudFolio(String folioSolicitud) throws DerechohabientesBusinessException, Exception;
	public AsignacionNSS getAsignacionByIdSolicitud(Long idSolicitud) throws DerechohabientesBusinessException, Exception;
	Solicitud rechazarSolicitud(Long idSolicitud, Long idRazonRechazo, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException;
	AsignacionNSS getAsignacionNssPorNss(String nss) throws DerechohabientesBusinessException, Exception;
	public TramiteProrroga recuperaProrrogaXML(Long idTramite) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Metodo encargado de consultar las solicitudes de derechohabientes basado en el grupo familiar y otros filtros
	 * Es requerido que todos los atributos del bean sean llenos excpetuando el cveIdAsignacion
	 * @param solicitudDto
	 * @return
	 * @throws Exception
	 */
	public DatosSalidaPaginador<Solicitud> solicitudesDerechohabientes(SolicitudDto solicitudDto) throws Exception;
	
	void marcarAtendidaSolictud(Long idSolicitud, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException;
	
	public long obtenerIdAsignacionNssPorNss(String nss) throws Exception;
}
