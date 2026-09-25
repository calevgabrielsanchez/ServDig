package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.InfoComplementariaTramiteException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;

@Local
public interface TramiteEntityLocal {

	List<Tramite> getTramitesAbierto(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado, List<Long> idModulos) throws Exception;

	void buscarYCancelarTramitesPorIdsPersonasYAsegurado(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado,String observaciones);
	List<Solicitud> getTramitePersona(List<Long> idPersonas,
			List<Long> tipoTramites, List<Long> estadoTramites,
			Long indResultado, Long razonResultado, Long idPersonaInt,
			Boolean tramiteEnLista, Integer maxResult, Boolean ordenDescendente)
			throws Exception;

	List<Tramite> findTramitesXPersona(Long idPersona, List<Long> estadoTramite)
			throws Exception;

	Tramite getUltimoTramitePersonaXTipoTramite(Long idPersona,
			List<Long> tiposTramite) throws Exception;

	Solicitud getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(
			Long idPersona, List<Long> tiposTramite, Long estado)
			throws Exception;

			
			
	TramiteInfo obtenerInfoComplementariaTramite(long idTipoTramite,
			long idOrigen) throws InfoComplementariaTramiteException;
	
	List<Tramite> obtenerTramitesCerrados( List<Long> origenSolicitud, Integer tipoSolicitud, Long idPersona, Date fechaConclusion, Long tipoTramite  ) throws Exception;

	Solicitud getUltimaSolicitudPatronPorTipoEstado(
			Long idPatronSujetoObligado, List<Long> tiposTramite, Long estado)
			throws Exception;

	List<Long> encontrarSolicitudesCDAPorEstadoYCurp(List<Long> idsEstadoTramite, String curp);
}
