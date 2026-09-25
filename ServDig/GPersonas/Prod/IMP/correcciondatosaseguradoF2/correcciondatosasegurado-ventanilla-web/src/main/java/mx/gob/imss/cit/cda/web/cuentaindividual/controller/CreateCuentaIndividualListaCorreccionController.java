package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CreateCuentaIndividualListaCorreccionController extends AbstractCreateController<PageCuentaIndividualPeriodo, BaseModel> {

    @Autowired
    @Qualifier(BeansConstants.CREATE_CUENTA_INDIVIDUAL_LISTA_CORRECCION_HELPER)
    private CreateHelper<PageCuentaIndividualPeriodo, BaseModel> service;

    @Override
    public CreateHelper<PageCuentaIndividualPeriodo, BaseModel> getHelper() {
        return service;
    }
    
    @RequestMapping(value = RequestMappingConstants.CREATE_CUENTA_INDIVIDUAL_LISTA_CORRECCION_URL)
    @ResponseBody
    @Override
    public ResponseEntity<BaseModel> load(@RequestBody PageCuentaIndividualPeriodo input) {
        return super.load(input);
    }
      
}