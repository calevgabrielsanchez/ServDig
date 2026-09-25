package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CreateCuentaIndividualCorreccionNssRegistroPatronalController extends AbstractCreateController<CuentaIndividualRegistroPatronal, BaseModel> {

    @Autowired
    @Qualifier(BeansConstants.CREATE_CUENTA_INDIVIDUAL_CORRECCION_NSS_REGISTRO_PATRONAL_HELPER)
    private CreateHelper<CuentaIndividualRegistroPatronal, BaseModel> service;

    @Override
    public CreateHelper<CuentaIndividualRegistroPatronal, BaseModel> getHelper() {
        return service;
    }
    
    @RequestMapping(value = RequestMappingConstants.CREATE_CUENTA_INDIVIDUAL_CORRECCION_NSS_REGISTRO_PATRONAL_URL)
    @ResponseBody
    @Override
    public ResponseEntity<BaseModel> load(@RequestBody CuentaIndividualRegistroPatronal input) {
        return super.load(input);
    }
      
}