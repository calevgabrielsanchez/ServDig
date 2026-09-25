package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import org.springframework.beans.factory.annotation.Autowired;

@Component(BeansConstants.READ_CUENTA_INDIVIDUAL_NSS_HELPER)
public class ReadCuentaIndividualNssHelper implements ReadHelper<Long, CuentaIndividual> {
  
  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;

  @SuppressWarnings("unchecked")
  @Override
  public final ReadEvent<CuentaIndividual> requestEvent(
    final RequestReadEvent<Long> requestReadEvent) {

    CuentaIndividual cuentaIndividual;    
    cuentaIndividual = cuentaIndividualBussines.obtenerCuentaIndividual( requestReadEvent.getData() );
    
    return new ReadEvent<CuentaIndividual>(requestReadEvent.getKey(), cuentaIndividual);
  }
}
