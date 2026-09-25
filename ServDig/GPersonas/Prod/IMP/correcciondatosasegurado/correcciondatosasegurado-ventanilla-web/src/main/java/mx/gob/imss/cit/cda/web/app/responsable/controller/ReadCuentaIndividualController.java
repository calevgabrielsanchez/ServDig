/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.CuentaIndividual;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestCuentaIndividualPage;
import mx.gob.imss.cit.cda.web.support.model.Page;

@Controller
public class ReadCuentaIndividualController
		extends AbstractReadController<RequestCuentaIndividualPage, Page<CuentaIndividual>> {

	@Autowired
	@Qualifier(BeansConstants.READ_CUENTA_INDIVIDUAL_HELPER)
	ReadHelper<RequestCuentaIndividualPage, Page<CuentaIndividual>> service;

	@Override
	public ReadHelper<RequestCuentaIndividualPage, Page<CuentaIndividual>> getHelper() {
		return service;
	}

	@RequestMapping(value = RequestMappingConstants.READ_CUENTA_INDIVIDUAL_CERTIFICADOR)
	public ResponseEntity<Page<CuentaIndividual>> load(@RequestBody RequestCuentaIndividualPage input,
			HttpServletRequest request) {
		return super.load(input, request);
	}

}
