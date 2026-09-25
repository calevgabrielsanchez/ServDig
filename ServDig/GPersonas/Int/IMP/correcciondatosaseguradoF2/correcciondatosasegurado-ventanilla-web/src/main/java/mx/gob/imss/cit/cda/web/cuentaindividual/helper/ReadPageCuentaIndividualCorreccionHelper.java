package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.PageRequest;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import org.springframework.beans.factory.annotation.Autowired;

@Component(BeansConstants.READ_PAGE_CUENTA_INDIVIDUAL_CORRECCION_HELPER)
public class ReadPageCuentaIndividualCorreccionHelper implements
        ReadHelper<PageRequest<CuentaIndividualRegistroPatronal>, PageCuentaIndividualPeriodo> {
  
  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;
    

  @SuppressWarnings("unchecked")
  @Override
  public final ReadEvent<PageCuentaIndividualPeriodo> requestEvent(
    final RequestReadEvent<PageRequest<CuentaIndividualRegistroPatronal>> requestReadEvent) {

    PageCuentaIndividualPeriodo page;
    
    page = cuentaIndividualBussines.obtenerPageCuentaIndividualCorreccion(
            requestReadEvent.getData().getModel(), requestReadEvent.getData().getPage() );
    
    return new ReadEvent<PageCuentaIndividualPeriodo>(requestReadEvent.getKey(),
      page);
  }
}
