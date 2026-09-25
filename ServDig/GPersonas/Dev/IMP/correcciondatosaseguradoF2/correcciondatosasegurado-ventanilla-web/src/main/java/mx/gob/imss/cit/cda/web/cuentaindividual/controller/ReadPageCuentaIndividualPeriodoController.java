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
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageRequestCuentaIndividualRegistroPatronal;

@Controller
public class ReadPageCuentaIndividualPeriodoController extends AbstractReadController<PageRequestCuentaIndividualRegistroPatronal, PageCuentaIndividualPeriodo> {

    @Autowired
    @Qualifier(BeansConstants.READ_PAGE_CUENTA_INDIVIDUAL_PERIODO_HELPER)
    private ReadHelper<PageRequestCuentaIndividualRegistroPatronal, PageCuentaIndividualPeriodo> service;

    @Override
    public ReadHelper<PageRequestCuentaIndividualRegistroPatronal, PageCuentaIndividualPeriodo> getHelper() {
        return service;
    }

    @RequestMapping(value = RequestMappingConstants.READ_PAGE_CUENTA_INDIVIDUAL_PERIODO_URL)
    @ResponseBody
    @Override
    public ResponseEntity<PageCuentaIndividualPeriodo> load(@RequestBody PageRequestCuentaIndividualRegistroPatronal input, HttpServletRequest request) {
        return super.load(input, request);
    }

}
