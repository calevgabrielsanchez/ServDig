/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author erik
 *
 */
@Component(BeansConstants.COMPLETE_CUENTA_INDIVIDUAL_HELPER)
public class CompleteCuentaIndividualHelper implements
        UpdateHelper<CuentaIndividual, CuentaIndividual> {

  @Autowired
  private CuentaIndividualRemote cuentaIndividualBussines;

  private static final Logger logger = LoggerFactory.getLogger(CompleteCuentaIndividualHelper.class);

  public CuentaIndividualRemote getCuentaIndividualBussines() {
    return cuentaIndividualBussines;
  }

  @Override
  public UpdatedEvent<CuentaIndividual> requestEvent(UpdateEvent<CuentaIndividual> requestUpdateEvent) {

    logger.debug("Se finaliza la CI ");
    CuentaIndividual cuentaIndividual = requestUpdateEvent.getData();
    String message = getCuentaIndividualBussines().complete(requestUpdateEvent.getData().getFolioSolicitud(), requestUpdateEvent.getUserProfile().getUsuario());
    cuentaIndividual.setMessage(message);
    return new UpdatedEvent<CuentaIndividual>(requestUpdateEvent.getKey(), cuentaIndividual);
  }

}
