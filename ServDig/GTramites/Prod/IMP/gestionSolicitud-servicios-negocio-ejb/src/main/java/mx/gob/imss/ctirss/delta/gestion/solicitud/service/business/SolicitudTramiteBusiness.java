package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.InfoComplementariaTramiteException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.TramiteEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;

@Stateless(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
public class SolicitudTramiteBusiness extends AbstractServiceBusiness implements
		SolicitudTramiteBusinessRemote {

	@EJB
	TramiteEntityLocal tramiteEntity;

	@Override
	public List<Tramite> getTramitesAbierto(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado) throws Exception {
		return tramiteEntity.getTramitesAbierto(idPersonas,
				idPersonaAseguradoPensionado,null);
	}
	
	@Override
	public List<Tramite> getTramitesAbierto(List<Long> idPersonas,
			Long idPersonaAseguradoPensionado, List<Long> idModulos) throws Exception {
		return tramiteEntity.getTramitesAbierto(idPersonas,
				idPersonaAseguradoPensionado,idModulos);
	}

	
	@Override
	public void buscarYCancelarSolicitudesAbiertasPorPersonasYAsegurado(
			List<Long> idsPersonas, Long idPersonaAsegurado, String observaciones) {
		tramiteEntity.buscarYCancelarTramitesPorIdsPersonasYAsegurado(idsPersonas, idPersonaAsegurado, observaciones);
	}


	@Override
	public List<Solicitud> getSolicitudesPersona(List<Long> idPersona,
			List<Long> tipoTramite, List<Long> estadoTramite,
			Long indResultado, Long razonResultado, Long idPersonaInt,
			Boolean enLista, Integer maxResult, Boolean ordenDescendente)
			throws Exception {
		return tramiteEntity.getTramitePersona(idPersona, tipoTramite,
				estadoTramite, indResultado, razonResultado, idPersonaInt,
				enLista, maxResult, ordenDescendente);
	}

	@Override
	public List<Tramite> findTramitesXPersona(Long idPersona,
			List<Long> estadoTramite) throws Exception {
		return tramiteEntity.findTramitesXPersona(idPersona, estadoTramite);
	}

	@Override
	public Tramite getUltimoTramitePersonaXTipoTramite(Long idPersona,
			List<Long> tiposTramite) throws Exception {
		return tramiteEntity.getUltimoTramitePersonaXTipoTramite(idPersona,
				tiposTramite);
	}

	@Override
	public Solicitud getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(
			Long idPersona, List<Long> tiposTramite, Long estado)
			throws Exception {

		return tramiteEntity
				.getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(
						idPersona, tiposTramite, estado);
	}

	@Override
	public TramiteInfo obtenerInfoComplementariaTramite(long idTipoTramite,
			long idOrigen) throws InfoComplementariaTramiteException {
		
		return this.tramiteEntity.obtenerInfoComplementariaTramite(idTipoTramite, idOrigen);
		
	}
	
	
	@Override
	public List<Tramite> obtenerTramitesCerrados( List<Long> origenSolicitud, Integer tipoSolicitud, Long idPersona, Date fechaConclusion, Long tipoTramite  ) throws Exception {
		return this.tramiteEntity.obtenerTramitesCerrados(origenSolicitud, tipoSolicitud, idPersona, fechaConclusion, tipoTramite );
	}

	@Override
	public Solicitud getUltimaSolicitudPatronPorTipoEstado(
			Long idPatronSujetoObligado, List<Long> tiposTramite, Long estado)
			throws Exception {
		return tramiteEntity.getUltimaSolicitudPatronPorTipoEstado(idPatronSujetoObligado, tiposTramite, estado);
	}

	@Override
	public List<Long> encontrarSolicitudesCDAPorEstadoYCurp(List<Long> idsEstadoTramite, String curp) {

		return this.tramiteEntity.encontrarSolicitudesCDAPorEstadoYCurp(idsEstadoTramite, curp);
	}
	
}
