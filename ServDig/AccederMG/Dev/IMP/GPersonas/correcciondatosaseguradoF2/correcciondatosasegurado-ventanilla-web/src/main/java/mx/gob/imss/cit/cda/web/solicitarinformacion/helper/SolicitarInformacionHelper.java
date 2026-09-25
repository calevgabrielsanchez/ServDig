package mx.gob.imss.cit.cda.web.solicitarinformacion.helper;

import java.util.Map;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.SolicitarInformacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.solicitarinformacion.utils.SolicitarInformacionUtils;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.SOLICITAR_INFORMACION_HELPER)
public class SolicitarInformacionHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {
    
    private final Logger log = LoggerFactory.getLogger(SolicitarInformacionHelper.class);

    @Autowired
    @Qualifier("solicitarInformacionBusiness")
    private SolicitarInformacionRemote solicitarInformacionBusiness;

    @Autowired
    private SolicitarInformacionUtils solicitarInformacionUtils;

    @Autowired
    private CorreosUtils correosUtils;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @SuppressWarnings("unchecked")
    @Override
    public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
        try {
            log.debug("CDA SolicitarInformacion idPersona [{}] ", requestUpdateEvent.getUserProfile().getIdPersona());

            Solicitud solicitud = solicitudBusiness.consultarPorFolioSolicitud(requestUpdateEvent.getData().getFolio());

            Map<String, String> tramitesTareas = getSolicitarInformacionUtils().armarTareasTramites(requestUpdateEvent.getData().getTareasTramites());

            log.debug("Usuario {}", requestUpdateEvent.getUserProfile().getUsuario());
            
            String observacion = requestUpdateEvent.getData().getDetalle() + ","+ requestUpdateEvent.getData().getResumen();
            
            getSolicitarInformacionBusiness().avanzarSolicitarInformacion(getSolicitarInformacionUtils().crearSolicitarInformacion(solicitud,requestUpdateEvent), 
                                                                        tramitesTareas,
                                                                        observacion,
                                                                        requestUpdateEvent.getUserProfile().getUsuario(),
                                                                        requestUpdateEvent.getData().getIsAutorizador());
            
            if (requestUpdateEvent.getData().getInformacionRENAPO().getCorreoElectronico() != null) {
                envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTO(requestUpdateEvent.getData(),TipoNotificacionEnum.SOLICITARINFO, null),EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
            }
            
            return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
            
        } catch (Exception e) {
            log.error("---------------------Error al solicitar informacion---------------------{}",e);
        }

        return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
    }
    
    public SolicitarInformacionRemote getSolicitarInformacionBusiness() {
        return solicitarInformacionBusiness;
    }

    public SolicitarInformacionUtils getSolicitarInformacionUtils() {
        return solicitarInformacionUtils;
    }

}
