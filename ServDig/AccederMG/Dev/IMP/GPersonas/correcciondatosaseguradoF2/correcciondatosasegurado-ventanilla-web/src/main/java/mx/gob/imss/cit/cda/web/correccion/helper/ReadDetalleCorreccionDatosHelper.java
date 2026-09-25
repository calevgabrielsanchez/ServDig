package mx.gob.imss.cit.cda.web.correccion.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_DETALLE_DATOS_HELPER)
public class ReadDetalleCorreccionDatosHelper implements
        ReadHelper<CorreccionDatos, ResumenCorrecion> {
	
	  private final Logger LOGGER = LoggerFactory
	            .getLogger(ReadDetalleCorreccionDatosHelper.class);
    @Override
    public ReadEvent<ResumenCorrecion> requestEvent(
            RequestReadEvent<CorreccionDatos> requestReadEvent) {
    
    	CorreccionDatos correccionDatos=new CorreccionDatos();
    	correccionDatos=requestReadEvent.getData();
    	ResumenCorrecion resultado = new ResumenCorrecion();
    	 try {  
    		 
    		 LOGGER.info("Entra ReadDetalleCorreccionDatosHelper");
    	resultado=correccionDatosBusiness.armarDetalleCoreccion(correccionDatos);
    	 LOGGER.info("Regresa del Remote");
      
    	return new ReadEvent<ResumenCorrecion>(requestReadEvent.getKey(),
    			resultado);

        } catch (Exception e) {
        	LOGGER.error("ReadDetalleCorreccionDatosHelper_ERROR",e.getMessage());
            return ReadEvent.notFound(requestReadEvent.getKey());
        }

    }

    @Autowired
    private CorreccionDatosRemote correccionDatosBusiness;

}
