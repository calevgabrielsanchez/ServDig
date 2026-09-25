package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.InfoComplementariaTramiteException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;

@Remote
public interface SolicitudTramiteBusinessRemote {

	List<Tramite> getTramitesAbierto(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado) throws Exception;
	
	List<Tramite> getTramitesAbierto(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado, List<Long> idModulos)throws Exception;
	
	void buscarYCancelarSolicitudesAbiertasPorPersonasYAsegurado(List<Long> idsPersonas, Long idPersonaAsegurado, String observaciones);

	List<Solicitud> getSolicitudesPersona(List<Long> idPersona,
			List<Long> tipoTramite, List<Long> estadoTramite,
			Long indResultado, Long razonResultado, Long idPersonaInt,
			Boolean enLista, Integer maxResult, Boolean ordenDescendente)
			throws Exception;

	Solicitud getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(
			Long idPersona, List<Long> tiposTramite, Long estado)
			throws Exception;

	List<Tramite> findTramitesXPersona(Long idPersona, List<Long> estadoTramite)
			throws Exception;

	Tramite getUltimoTramitePersonaXTipoTramite(Long idPersona,
			List<Long> tiposTramite) throws Exception;

	TramiteInfo obtenerInfoComplementariaTramite(long idTipoTramite,
			long idOrigen) throws InfoComplementariaTramiteException;
	
	
	/**
	 * Obtiene todos los tr&aacute;mites cerrados cuya fecha de conclusi&oacute;n sea mayor o igual a la indicada 
	 *  
	 * @param origenSolicitud Long
	 * @param tipoSolicitud Long
	 * @param idPersona Long
	 * @param fechaConclusion Date
	 * @param tipoTramite Long  
	 * @return List<Tramite> o null en casi de no se encontrar tr&aacute;mites 
	 * @throws Exception
	 */
	List<Tramite> obtenerTramitesCerrados( List<Long> origenSolicitud, Integer tipoSolicitud, Long idPersona, Date fechaConclusion, Long tipoTramite  ) throws Exception;
	
	Solicitud getUltimaSolicitudPatronPorTipoEstado(
			Long idPatronSujetoObligado, List<Long> tiposTramite, Long estado)
			throws Exception;

	List<Long> encontrarSolicitudesCDAPorEstadoYCurp(List<Long> idsEstadoTramite, String curp);
}
