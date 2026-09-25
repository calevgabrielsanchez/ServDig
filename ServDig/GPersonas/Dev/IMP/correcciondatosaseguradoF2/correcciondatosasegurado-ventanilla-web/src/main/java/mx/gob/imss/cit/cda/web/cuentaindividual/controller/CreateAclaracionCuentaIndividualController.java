package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CreateAclaracionCuentaIndividualController extends AbstractCreateController<CuentaIndividual, CuentaIndividual> {

    @Autowired
    @Qualifier(BeansConstants.CREATE_ACLARACION_CUENTA_INDIVIDUAL_HELPER)
    private CreateHelper<CuentaIndividual, CuentaIndividual> service;

    @Override
    public CreateHelper<CuentaIndividual, CuentaIndividual> getHelper() {
        return service;
    }
    
    @RequestMapping(value = RequestMappingConstants.CREATE_ACLARACION_CUENTA_INDIVIDUAL)
    @ResponseBody
    @Override
    public ResponseEntity<CuentaIndividual> load(@RequestBody CuentaIndividual input) {
        return super.load(input);
    }
      
}