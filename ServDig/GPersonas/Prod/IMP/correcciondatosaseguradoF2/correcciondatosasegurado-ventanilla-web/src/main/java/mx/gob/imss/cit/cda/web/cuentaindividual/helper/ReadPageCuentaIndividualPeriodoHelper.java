package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageRequestCuentaIndividualRegistroPatronal;
import org.springframework.beans.factory.annotation.Autowired;

@Component(BeansConstants.READ_PAGE_CUENTA_INDIVIDUAL_PERIODO_HELPER)
public class ReadPageCuentaIndividualPeriodoHelper implements
        ReadHelper<PageRequestCuentaIndividualRegistroPatronal, PageCuentaIndividualPeriodo> {
  
  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;
    

  @SuppressWarnings("unchecked")
  @Override
  public final ReadEvent<PageCuentaIndividualPeriodo> requestEvent(
    final RequestReadEvent<PageRequestCuentaIndividualRegistroPatronal> requestReadEvent) {

    PageCuentaIndividualPeriodo page;
    
    page = cuentaIndividualBussines.obtenerPageCuentaIndividualPeriodo(
            requestReadEvent.getData().getModel(), requestReadEvent.getData().getPage() );
    
    return new ReadEvent<PageCuentaIndividualPeriodo>(requestReadEvent.getKey(), page);
  }
}
