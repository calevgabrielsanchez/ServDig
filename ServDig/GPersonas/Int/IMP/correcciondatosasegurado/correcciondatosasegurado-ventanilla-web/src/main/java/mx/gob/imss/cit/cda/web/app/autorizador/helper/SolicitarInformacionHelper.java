package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.cit.cda.web.utils.SolicitudUpdateUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Component(BeansConstants.SOLICITAR_INFORMACION_HELPER)
public class SolicitarInformacionHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {

	@Autowired
	@Qualifier("responsableTareaBusiness")
	private ResponsableTareaRemote responsableTareaBusiness;

	@Autowired
	private SolicitudUpdateUtils solicitudUpdateUtils;
	
	@Autowired
	private CorreosUtils correosUtils;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;

	private final Logger log = LoggerFactory.getLogger(SolicitarInformacionHelper.class);
	
	public final static int infoAutorizador = 2;

	@SuppressWarnings("unchecked")
	@Override
	public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
		try {
			log.debug("CDA SolicitarInformacion idPersona [{}] idTarea [{}]",
					requestUpdateEvent.getUserProfile().getIdPersona(), requestUpdateEvent.getData().getIdTarea());
			
			Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
			
			
			log.debug("Usuario {}",requestUpdateEvent.getUserProfile().getUsuario());
			responsableTareaBusiness.avanzarSolitudInformacion(
					solicitudUpdateUtils.solicitarInformacion(solicitud,requestUpdateEvent),
					requestUpdateEvent.getData().getIdTarea(), requestUpdateEvent.getData().getDetalle()+","+requestUpdateEvent.getData().getResumen(),requestUpdateEvent.getUserProfile().getUsuario(), infoAutorizador);
			if (requestUpdateEvent.getData().getInformacionRENAPO().getCorreoElectronico()!=null){
				envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTO(requestUpdateEvent.getData(),TipoNotificacionEnum.SOLICITARINFO, null),EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
			}
			return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
		} catch (Exception e) {
			log.error("---------------------Error al solicitar informacion---------------------{}", e);
		}

		return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
	}

}
