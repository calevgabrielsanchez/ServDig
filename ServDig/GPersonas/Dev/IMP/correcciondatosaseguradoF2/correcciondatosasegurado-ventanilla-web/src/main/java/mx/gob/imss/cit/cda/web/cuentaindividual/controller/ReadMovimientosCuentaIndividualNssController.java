/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReadMovimientosCuentaIndividualNssController extends AbstractReadController<String, Long> {

    @Autowired
    @Qualifier(BeansConstants.READ_MOVIMIENTOS_CUENTA_INDIVIDUAL_NSS_HELPER)
    private ReadHelper<String, Long> service;

    @Override
    public ReadHelper<String, Long> getHelper() {
        return service;
    }

    @RequestMapping(value = RequestMappingConstants.READ_MOVIMIENTOS_CUENTA_INDIVIDUAL_NSS_URL)
    @ResponseBody
    @Override
    public ResponseEntity<Long> load(@RequestBody String input, HttpServletRequest request) {
        return super.load(input, request);
    }

}
