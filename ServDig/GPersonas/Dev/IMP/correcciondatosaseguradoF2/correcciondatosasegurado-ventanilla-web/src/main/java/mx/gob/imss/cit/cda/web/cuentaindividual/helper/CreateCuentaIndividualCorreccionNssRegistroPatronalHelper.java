package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.MotivosAclaracionErroneosException;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.CREATE_CUENTA_INDIVIDUAL_CORRECCION_NSS_REGISTRO_PATRONAL_HELPER)
public class CreateCuentaIndividualCorreccionNssRegistroPatronalHelper implements CreateHelper<CuentaIndividualRegistroPatronal, BaseModel> {
    
    @Autowired
    private CuentaIndividualRemote cuentaIndividualBussines;
      
    @Override
    public CreatedEvent<BaseModel> requestEvent(CreateEvent<CuentaIndividualRegistroPatronal> requestCreateEvent) {        
      try {
        Long total = cuentaIndividualBussines.registrarCorreccionNssRegistroPatronal(requestCreateEvent.getData());
        BaseModel model = new BaseModel();
        model.setMessage( String.valueOf(total) );
        return new CreatedEvent<BaseModel>(requestCreateEvent.getKey(), model );
      } catch (MotivosAclaracionErroneosException ex) {
        BaseModel model = new BaseModel();
        model.setStatus("ERROR");
        model.setMessage(ex.getMessage());
        return new CreatedEvent<BaseModel>(requestCreateEvent.getKey(), model );
      }
    }


    public CuentaIndividualRemote getCuentaIndividualBussines() {
        return cuentaIndividualBussines;
    }
    
}