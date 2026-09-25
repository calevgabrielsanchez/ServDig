package mx.gob.imss.cit.cda.web.app.common.helper;

import java.util.Arrays;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Documento;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_DOCUMENTO_HELPER)
public class ReadDocumentoHelper implements ReadHelper<Documento, byte[]> {

    @Autowired
    private BovedaRemote bovedaBusiness;

    private static final String TIPO_ID_USR = "IDPERSONA";

    private final Logger log = LoggerFactory
            .getLogger(ReadDocumentoHelper.class);

    private static final String EXTENCIONES_VALIDAS[] = { "gif", "tif", "jpg",
            "png", "pdf" };

    private static final String NULL = "null";

    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<byte[]> requestEvent(
            RequestReadEvent<Documento> requestReadEvent) {
        log.debug("init obtener documento");
        byte[] documento = null;
        try {
            Solicitud sol = new Solicitud();
            sol.setNoFolioSolicitud(requestReadEvent.getData().getFolio());
            sol.setSolicitudId(Long.parseLong(requestReadEvent.getData()
                    .getIdPersona()));

            String idDocumento = requestReadEvent.getData().getIdDocBoveda()
                    .equalsIgnoreCase(NULL) ? null : requestReadEvent.getData()
                    .getIdDocBoveda();

            log.info("---CDA--- IdDocumento es nulo {} valor {}",
                    StringUtils.isBlank(idDocumento), idDocumento);
            try {
                documento = bovedaBusiness.recuperarDocumento(sol, idDocumento,
                        requestReadEvent.getData().getNombreArchivo());
            } catch (BovedaCDAException bce) {
                log.error("---CDA--- Error en documentos {}",
                        bce.getSituacion(), bce);
                return ReadEvent.error(requestReadEvent.getKey(),
                        bce.getSituacion(), bce.getMensajeError());
            }
            return new ReadEvent<byte[]>(requestReadEvent.getKey(), documento);
        } catch (Exception e) {
            log.error("Error: {}", e);
            return ReadEvent
                    .error(requestReadEvent.getKey(),
                            e.getMessage(),
                            MensajesBovedaCDAEnum
                                    .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                            .getCodigo()));
        }
    }

}
