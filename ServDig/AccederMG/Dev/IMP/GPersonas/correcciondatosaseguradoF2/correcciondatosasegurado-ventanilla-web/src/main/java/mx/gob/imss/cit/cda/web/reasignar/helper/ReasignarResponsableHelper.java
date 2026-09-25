package mx.gob.imss.cit.cda.web.reasignar.helper;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.ReasignarSolicitudRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.ReasignacionSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.reasignar.utils.ReasignarResponsableUtil;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.REASIGNAR_RESPONSABLE_HELPER)
public class ReasignarResponsableHelper implements
        UpdateHelper<ReasignacionSolicitud, ReasignacionSolicitud> {

    private final Logger log = LoggerFactory.getLogger(ReasignarResponsableHelper.class);

    @Autowired
    @Qualifier("reasignarSolicitudBusiness")
    private ReasignarSolicitudRemote reasignarSolicitudBusiness;

    @Autowired
    private ReasignarResponsableUtil reasignarResponsableUtil;

    @Autowired
    private CorreosUtils correosUtils;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Override
    public UpdatedEvent<ReasignacionSolicitud> requestEvent(
            UpdateEvent<ReasignacionSolicitud> requestUpdateEvent) {
        log.debug(
                "---CDA--- Recibiendo datos para reasignar Tarea  [idtarea{},idSolicitud{},idTramite{}]",
                new Object[] { requestUpdateEvent.getData().getIdTarea(),
                        requestUpdateEvent.getData().getIdSolicitud(),
                        requestUpdateEvent.getData().getIdTarea() });

        try {
            Solicitud solicitud = solicitudBusiness
                    .consultarPorFolioSolicitud(requestUpdateEvent.getData()
                            .getFolio());
            getReasignarSolicitudBusiness().reasignarTareaAutorizador(
                    reasignarResponsableUtil.crearSolicitudReasignar(solicitud,
                            requestUpdateEvent),
                    reasignarResponsableUtil
                            .armarTareasTramites(requestUpdateEvent),
                    requestUpdateEvent.getData().getDetalle(),
                    requestUpdateEvent.getData().getCurp());
            envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils
                    .crearCorreoElectronicoDTOReasignacion(requestUpdateEvent
                            .getData()),
                    EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
            return new UpdatedEvent<ReasignacionSolicitud>(
                    requestUpdateEvent.getKey(), requestUpdateEvent.getData());
        } catch (BPMException e) {
            log.error("---CDA--- Hubo un problema al reasignar Tarea {}", e);
        } catch (SolicitudNoEncontradaException e) {
            log.error("---CDA--- Hubo un problema al reasignar Tarea {}", e);
        } catch (TramiteNoEncontradoException e) {
            log.error("---CDA--- Hubo un problema al reasignar Tarea {}", e);
        } catch (Exception e) {
            log.error(
                    "---CDA--- No se pudo enviar el correo de la solicitud {}",
                    requestUpdateEvent.getData().getIdSolicitud(), e);
        }

        return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
    }

    public ReasignarSolicitudRemote getReasignarSolicitudBusiness() {
        return reasignarSolicitudBusiness;
    }
}
