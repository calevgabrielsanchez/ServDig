/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.autorizador.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesAsignadosPage;
import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author antonio
 */
@Controller
public class ReadTramitesAsignadosAutorizadorController
		extends AbstractReadController<RequestTramitesAsignadosPage, Page<TramitesAsignados>> {

	@Autowired
	@Qualifier(BeansConstants.READ_TRAMITES_ASIGNADOS_HELPER)
	ReadHelper<RequestTramitesAsignadosPage, Page<TramitesAsignados>> service;

	@Override
	public ReadHelper<RequestTramitesAsignadosPage, Page<TramitesAsignados>> getHelper() {
		return service;
	}

	@RequestMapping(RequestMappingConstants.READ_TRAMITES_ASIGNADOS_AUTORIZADOR)
	@ResponseBody
	@Override
	public ResponseEntity<Page<TramitesAsignados>> load(@RequestBody RequestTramitesAsignadosPage input, HttpServletRequest request){
	    return super.load(input, request);
	}

}
