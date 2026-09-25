package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.global.model.TramiteTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

@Stateless(mappedName = "solicitudServiciosExpuestos")
public class SolicitudServiciosExpuestos extends AbstractServiceBusiness
		implements SolicitudServiciosExpuestosRemote {

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusiness;
	@EJB
	private SolicitudEntityLocal solicitudEntity;
	@EJB
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;

	@Override
	public SolicitudTO crearSolicitud(SolicitudTO solicitudTO)
			throws SolicitudNoValidaException {
		EstadoSolicitud estadoSolicitud;
		if (solicitudTO.getEstadoSolicitud() == null) {
			estadoSolicitud = new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getValor());
		} else {
			estadoSolicitud = solicitudTO.getEstadoSolicitud();
		}

		TipoSolicitud tipoSolicitud = solicitudTO.getTipoSolicitud();
		OrigenSolicitud origenSolicitud = solicitudTO.getOrigenSolicitud();

		Usuario solicitante;
		if (StringUtils.isNotBlank(solicitudTO.getUsuarioResponsable())) {
			solicitante = new Usuario();
			solicitante.setUsuario(solicitudTO.getUsuarioResponsable());
		} else {
			solicitante = null;
		}

		Solicitud solicitud = solicitudBusiness.crearSolicitudInicial(
				estadoSolicitud, tipoSolicitud, origenSolicitud, solicitante);
		Date fechaActual = new Date();
		solicitud.setFechaPresentacion(fechaActual);

		if (StringUtils.isNotBlank(solicitudTO.getObservacion())) {
			solicitud.setObservacion(solicitudTO.getObservacion());
		}
		solicitud.setFirmadaDigitalmente(solicitudTO.isFirmadaDigitalmente());

		if (CollectionUtils.isEmpty(solicitudTO.getTramites())
				&& !ArrayUtils.isEmpty(solicitudTO.getTramitesAux())) {
			solicitudTO.setTramites(Arrays.asList(solicitudTO.getTramitesAux()));
		}

		if (!CollectionUtils.isEmpty(solicitudTO.getTramites())) {
			for (TramiteTO tramiteTO : solicitudTO.getTramites()) {
				Tramite tramite = new Tramite();

				TipoTramite tipoTramite = new TipoTramite();
				BeanUtils.copyProperties(tramiteTO.getTipoTramite(), tipoTramite);

				EstadoTramite estadoTramite;
				if (tramiteTO.getEstadoTramite() == null) {
					estadoTramite = new EstadoTramite();
					estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getValor());
				} else {
					estadoTramite = tramiteTO.getEstadoTramite();
				}

				if (solicitudTO.getPersona() != null
						&& (solicitudTO.getPersona().getIdPersona() != null
						|| StringUtils.isNotBlank(solicitudTO.getPersona().getNss()))) {
					Fisica persona = new Fisica();
					persona.setIdPersona(solicitudTO.getPersona().getIdPersona());
					persona.setNss(solicitudTO.getPersona().getNss());
					tramite.setPersona(persona);
				}

				tramite.setTramiteId(tramiteTO.getTramiteId());
				log.error("Detalle del tr�mite a guardar en servicios expuestos: "
						+ tramiteTO.getDetalleTramiteXml());
				tramite.setDetalleTramiteXml(tramiteTO.getDetalleTramiteXml());
				tramite.setErrorFormGeneral(tramiteTO.getErrorFormGeneral());

				solicitudBusiness.asociarTramiteSolicitud(solicitud, tramite,
						estadoTramite, tipoTramite);
			}
		}

		if (solicitudTO.getSolicitudId() != null) {
			solicitud.setSolicitudId(solicitudTO.getSolicitudId());
			try {
				List<Tramite> tramites = solicitud.getTramites();
				solicitud.setTramites(null);
				// Al actualizar la solicitud dejamos que recarge los tramites guardados
				solicitud = solicitudBusiness.actualizarEstados(solicitud);
				// y le agregamos los tramites que traia para firmar
				solicitud.setTramites(tramites);
				solicitudBusiness.actualizarTramites(solicitud);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			solicitud = solicitudBusiness.crear(solicitud);
		}

		solicitudTO.setNoFolioSolicitud(solicitud.getNoFolioSolicitud());
		solicitudTO.setSolicitudId(solicitud.getSolicitudId());
		solicitudTO.setEstadoSolicitud(solicitud.getEstadoSolicitud());

		if (solicitudTO.isFirmadaDigitalmente()) {
			this.log.debug("La solicitud " + solicitud.getNoFolioSolicitud()
					+ " trae firma digital, se va a guardar.");

			FirmaElectronica firmaElectronica = solicitudTO.getFirmaElectronica();

			if (StringUtils.isBlank(firmaElectronica.getUrlAcuseFirma())) {
				firmaElectronica.setUrlAcuseFirma("");
			}

			if (firmaElectronica.getIniciaVigenciaCertificado() == null) {
				firmaElectronica.setIniciaVigenciaCertificado(new Date());
				firmaElectronica.setFinVigenciaCertificado(new Date());
			}

			try {
				this.firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (solicitudTO.getTramitesAux() != null && solicitudTO.getTramitesAux().length > 0) {
			int contador = 0;
			for (Tramite tramiteP : solicitud.getTramites()) {
				solicitudTO.getTramitesAux()[contador].setTramiteId(tramiteP.getTramiteId());
				contador++;
			}
		}

		// Debido a que el OSB no puede procesar java.util.List se nullea
		solicitudTO.setTramites(null);

		return solicitudTO;
	}
	
	@Override
	public void cancelarSolicitudPorFolio(String folio) {
		
		this.log.debug("Se va a cancelar la solicitud con folio " + folio);
		
		this.solicitudEntity.cancelarSolicitudPorFolio(folio);
		
		this.log.debug("La solicitud con folio " + folio + " fue cancelada exitosamente");
	}

	@Override
	public void actualizarEstadoMensajeError(String folio,
			Integer idEstadoParaAsignar, Integer idEstadoTramiteAsignar,
			String observacion) {
		
		this.log.debug("Se va a poner como [estadoSolicitud="
				+ idEstadoParaAsignar + "] la solicitud con folio " + folio
				+ " debido a " + observacion);
		
		this.solicitudEntity.actualizarEstadoMensajeError(folio,
				idEstadoParaAsignar, idEstadoTramiteAsignar, observacion);
		
		this.log.debug("La solicitud con folio " + folio
				+ " fue puesta exitosamente como [estadoSolicitud="
				+ idEstadoParaAsignar + "] debido a " + observacion);
		
	}

	@Override
	public void concluirSolicitudPorFolio(String folio) {
		this.log.debug("Se va a CONCLUIR la solicitud con folio " + folio);
		solicitudEntity.concluirSolicitudPorFolio(folio);
		this.log.debug("La solicitud con folio " + folio + " fue CONCLUIDA exitosamente");
	}

	@Override
	public void cancelarTramitePorId(Long idTramite) {
		try {
			solicitudEntity.cancelarTramitePorId(idTramite);
		} catch (TramiteNoEncontradoException e) {
			log.error("No fue posible cancelar el tramite con id " + idTramite);
		}
	}

    @Override
    public void asociarSolicitudSubDelegacion(Long idSolicitud, Long IdSubDelegacion) {
        solicitudEntity.asociarSolicitudSubdelegacion(idSolicitud, IdSubDelegacion);
    }

	@Override
	public CitaSolicitud guardaCitaSolicitud(CitaSolicitud cita) throws SolicitudException {
		 return solicitudEntity.guardaCitaSolicitud(cita);
	}

	@Override
	public CitaSolicitud actualizaCitaSolicitud(CitaSolicitud cita) throws SolicitudException {
		return  solicitudEntity.actualizaCitaSolicitud(cita);
		
	}

	@Override
	public CitaSolicitud calculaFechaCita(CitaSolicitud cita) throws SolicitudException {
		return  solicitudEntity.calculaFechaCita(cita);
		
	}

	@Override
	public boolean validaFechaCita(CitaSolicitud cita) throws SolicitudException {
		return solicitudEntity.validaFechaCita(cita);
	}
    
	/**COnsulta la cita ya sea foir folio, id o id solicitud
	 * 
	 * @param cita
	 * @return
	 * @throws SolicitudException
	 */
	@Override
	public CitaSolicitud consultaCItaSOlicitud(CitaSolicitud cita) throws SolicitudException{
		return solicitudEntity.consultaCItaSOlicitud(cita);
	}
        
}
