/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;

@Controller
public class ReadCuentaIndividualNssController extends AbstractReadController<Long, CuentaIndividual> {

    @Autowired
    @Qualifier(BeansConstants.READ_CUENTA_INDIVIDUAL_NSS_HELPER)
    private ReadHelper<Long, CuentaIndividual> service;

    @Override
    public ReadHelper<Long, CuentaIndividual> getHelper() {
        return service;
    }

    @RequestMapping(value = RequestMappingConstants.READ_CUENTA_INDIVIDUAL_NSS_URL)
    @ResponseBody
    @Override
    public ResponseEntity<CuentaIndividual> load(@RequestBody Long input, HttpServletRequest request) {
        return super.load(input, request);
    }

}
