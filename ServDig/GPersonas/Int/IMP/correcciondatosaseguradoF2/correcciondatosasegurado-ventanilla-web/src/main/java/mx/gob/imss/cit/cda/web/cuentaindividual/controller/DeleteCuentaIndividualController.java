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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DeleteCuentaIndividualController extends
		AbstractUpdateController<Long, Long> {

	@Autowired
	@Qualifier(BeansConstants.BORRAR_CUENTA_INDIVIDUAL_HELPER)
	UpdateHelper<Long, Long> service;

	@Override
	public UpdateHelper<Long, Long> getHelper() {
		return this.service;
	}

	@RequestMapping(RequestMappingConstants.DELETE_CUENTA_INDIVIDUAL_CERTIFICADOR)
	@ResponseBody
	@Override
	public ResponseEntity<Long> update(@RequestBody Long input,
			HttpServletRequest request) {
		return super.update(input, request);
	}

}
