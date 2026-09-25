/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
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

/**
 * 
 * @author erik
 *
 */
@Controller
public class UpdateCuentaIndividualController extends AbstractUpdateController<CuentaIndividual, CuentaIndividual> {

    @Autowired
    @Qualifier(BeansConstants.MODIFICAR_CUENTA_INDIVIDUAL_HELPER)
    UpdateHelper<CuentaIndividual, CuentaIndividual> service;

    @Override
    public UpdateHelper<CuentaIndividual, CuentaIndividual> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.SAVE_CUENTA_INDIVIDUAL_CERTIFICADOR)
    @ResponseBody
    @Override
    public ResponseEntity<CuentaIndividual> update(@RequestBody CuentaIndividual input, HttpServletRequest request) {
        return super.update(input, request);
    }

}
