package mx.gob.imss.cit.cda.web.correccion.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component(BeansConstants.CORRECION_DATOS_HELPER)
public class CorreccionDatosHelper implements
ReadHelper<CorreccionDatos, CorreccionDatos> {
    
    private final Logger LOGGER = LoggerFactory
            .getLogger(CorreccionDatosHelper.class);
    
    @Autowired
    private CorreccionDatosRemote correccionDatosBusiness;

    @Override
    public ReadEvent<CorreccionDatos> requestEvent(
            RequestReadEvent<CorreccionDatos> requestReadEvent) {
        
        try {
            correccionDatosBusiness.guardarXmlCorreccionDatos(requestReadEvent.getData());
        } catch (SolicitudNoEncontradaException e) {
            LOGGER.error("SOLICITUD NO ENCONTRADA", e);
        } catch (TramiteNoEncontradoException e) {
            LOGGER.error("TRAMITE NO ENCONTRADO", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("TRAMITE NO ENCONTRADO", e);
        }
        return new ReadEvent<CorreccionDatos>(requestReadEvent.getKey(), requestReadEvent.getData());
    }

}
