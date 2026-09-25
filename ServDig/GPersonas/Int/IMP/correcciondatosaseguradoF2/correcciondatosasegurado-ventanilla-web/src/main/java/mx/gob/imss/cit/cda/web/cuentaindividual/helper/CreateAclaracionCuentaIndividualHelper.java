package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import java.util.logging.Level;
import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.CREATE_ACLARACION_CUENTA_INDIVIDUAL_HELPER)
public class CreateAclaracionCuentaIndividualHelper implements CreateHelper<CuentaIndividual, CuentaIndividual> {
	
	private final Logger log = LoggerFactory.getLogger(CreateAclaracionCuentaIndividualHelper.class);
    
    @Autowired
    private CuentaIndividualRemote cuentaIndividualBussines;
      
    @Override
    public CreatedEvent<CuentaIndividual> requestEvent(CreateEvent<CuentaIndividual> requestCreateEvent) {
    	
    	log.debug("Iniciando cuenta individual");

		
        CuentaIndividual cuentaIndividual;
    try {
      cuentaIndividual =
              getCuentaIndividualBussines().findByFolio( requestCreateEvent.getData().getFolioSolicitud() );
      return new CreatedEvent<CuentaIndividual>(requestCreateEvent.getKey(), cuentaIndividual);
    } catch (CuentaIndividualNoDisponibleException ex) {
      log.error("Ocurrio un error al consultar la cuenta individual: {}", ex);
      cuentaIndividual = new CuentaIndividual();
      cuentaIndividual.setStatus("ERROR");
      cuentaIndividual.setMessage(ex.getMessage());
      return new CreatedEvent<CuentaIndividual>(requestCreateEvent.getKey(), cuentaIndividual);
      
    }
        
        
    }


    public CuentaIndividualRemote getCuentaIndividualBussines() {
        return cuentaIndividualBussines;
    }
    
}