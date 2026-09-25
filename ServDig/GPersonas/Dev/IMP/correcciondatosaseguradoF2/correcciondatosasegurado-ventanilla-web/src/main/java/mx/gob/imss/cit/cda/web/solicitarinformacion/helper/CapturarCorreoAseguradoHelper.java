package mx.gob.imss.cit.cda.web.solicitarinformacion.helper;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.solicitarinformacion.utils.CapturarCorreoAseguradoUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.CAPTURAR_CORREO_ASEGURADO_HELPER)
public class CapturarCorreoAseguradoHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {

    private final Logger logger = LoggerFactory.getLogger(CapturarCorreoAseguradoHelper.class);

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    private CapturarCorreoAseguradoUtils capturarCorreoAseguradoUtils;

    @SuppressWarnings("unchecked")
    @Override
    public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
        try {
            logger.debug("CDA Capturar Correo Electronico Asegurado ",requestUpdateEvent.toString());

            Solicitud solicitud = getSolicitudBusiness().consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
            getSolicitudBusiness().actualizarTramites(getCapturarCorreoAseguradoUtils().modificarCorreoAsegurado(solicitud, requestUpdateEvent));

            return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
        } catch (Exception e) {
            logger.error("---------------------Error al capturar correo asegurado---------------------{}",e);
        }

        return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
    }

    public SolicitudBusinessRemote getSolicitudBusiness() {
        return solicitudBusiness;
    }

    public CapturarCorreoAseguradoUtils getCapturarCorreoAseguradoUtils() {
        return capturarCorreoAseguradoUtils;
    }

}
