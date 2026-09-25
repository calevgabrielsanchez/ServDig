package mx.gob.imss.cit.cda.web.app.autorizador.utils;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;

public class SeguimientoSolicitudEditor extends PropertyEditorSupport {
	
	private final Logger log = LoggerFactory.getLogger(SeguimientoSolicitudEditor.class);
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
		ObjectMapper mapper = new ObjectMapper();

        SeguimientoSolicitud value = null;
        
        try {
			value = mapper.readValue(text, SeguimientoSolicitud.class);
		} catch (JsonParseException e) {
			log.debug("---CDA--- ocurrio un error al parsear json {}",e);
		} catch (JsonMappingException e) {
			log.debug("---CDA--- Error {}",e);
		} catch (IOException e) {
			log.debug("---CDA--- Error {}",e);
		}
        

        setValue(value);
    }

}
