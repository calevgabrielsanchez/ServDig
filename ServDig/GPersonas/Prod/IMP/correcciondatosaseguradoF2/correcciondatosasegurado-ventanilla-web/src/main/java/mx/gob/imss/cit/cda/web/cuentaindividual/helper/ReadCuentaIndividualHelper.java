package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Component(BeansConstants.READ_CUENTA_INDIVIDUAL_HELPER)
public class ReadCuentaIndividualHelper  implements
        ReadHelper<Solicitud, CuentaIndividual> {
  
  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;
  
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    

  @SuppressWarnings("unchecked")
  @Override
  public final ReadEvent<CuentaIndividual> requestEvent(
    final RequestReadEvent<Solicitud> requestReadEvent) {

    CuentaIndividual cuentaIndividual;
    try{
      cuentaIndividual = cuentaIndividualBussines.findByFolio( requestReadEvent.getData().getFolio() );
    }catch (CuentaIndividualNoDisponibleException ex) {
       LOGGER.error("Ocurrio un error al consultar la cuenta individual: {}", ex);
      cuentaIndividual = new CuentaIndividual();
      cuentaIndividual.setStatus("ERROR");
      cuentaIndividual.setMessage(ex.getMessage());
    }
    
    return new ReadEvent<CuentaIndividual>(requestReadEvent.getKey(),
      cuentaIndividual);
  }
}
