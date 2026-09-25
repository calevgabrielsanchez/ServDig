package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import java.util.List;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import org.springframework.beans.factory.annotation.Autowired;

@Component(BeansConstants.READ_LISTA_CUENTA_INDIVIDUAL_REGISTRO_PATRONAL_HELPER)
public class ReadListaCuentaIndividualRegistroPatronalHelper implements
        ReadHelper<Long, List<CuentaIndividualRegistroPatronal>> {
  
  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;
    

  @SuppressWarnings("unchecked")
  @Override
  public final ReadEvent<List<CuentaIndividualRegistroPatronal>> requestEvent(
    final RequestReadEvent<Long> requestReadEvent) {

    List<CuentaIndividualRegistroPatronal> lista;
    
    lista = cuentaIndividualBussines.obtenerListaCuentaIndividualRegistroPatronal( requestReadEvent.getData() );
    
    return new ReadEvent<List<CuentaIndividualRegistroPatronal>>(requestReadEvent.getKey(), lista);
  }
}
