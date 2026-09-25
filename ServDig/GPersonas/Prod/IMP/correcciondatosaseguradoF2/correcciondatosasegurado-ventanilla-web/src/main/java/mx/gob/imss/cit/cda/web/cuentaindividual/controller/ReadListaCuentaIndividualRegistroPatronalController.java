/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadListController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;

@Controller
public class ReadListaCuentaIndividualRegistroPatronalController extends AbstractReadListController<Long, List<CuentaIndividualRegistroPatronal>> {

    @Autowired
    @Qualifier(BeansConstants.READ_LISTA_CUENTA_INDIVIDUAL_REGISTRO_PATRONAL_HELPER)
    private ReadHelper<Long, List<CuentaIndividualRegistroPatronal>> service;

    @Override
    public ReadHelper<Long, List<CuentaIndividualRegistroPatronal>> getHelper() {
        return service;
    }

    @RequestMapping(value = RequestMappingConstants.READ_LISTA_CUENTA_INDIVIDUAL_REGISTRO_PATRONAL_URL)
    @ResponseBody
    @Override
    public ResponseEntity<List<CuentaIndividualRegistroPatronal>> load(@RequestBody Long input, HttpServletRequest request) {
        return super.load(input, request);
    }

}
