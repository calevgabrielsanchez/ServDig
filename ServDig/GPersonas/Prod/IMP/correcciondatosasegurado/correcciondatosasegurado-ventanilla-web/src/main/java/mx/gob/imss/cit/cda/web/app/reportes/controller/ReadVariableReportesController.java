package mx.gob.imss.cit.cda.web.app.reportes.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.reportes.model.VariablesReportes;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesReportesPage;
//import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
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

@Controller
public class ReadVariableReportesController
		extends AbstractReadController<RequestTramitesReportesPage, Page<VariablesReportes>> 
{

	@Autowired
	@Qualifier(BeansConstants.VARIABLE_ELEGIDA_HELPER)
	ReadHelper<RequestTramitesReportesPage, Page<VariablesReportes>> service;

	@Override
	public ReadHelper<RequestTramitesReportesPage, Page<VariablesReportes>> getHelper() {
		return service;
	}

	//READ_TRAMITES_ASIGNADOS_AUTORIZADOR
	@RequestMapping(RequestMappingConstants.READ_VARIABLE_ELEGIDA)
	@ResponseBody
	@Override
	public ResponseEntity<Page<VariablesReportes>> load(@RequestBody RequestTramitesReportesPage input, HttpServletRequest request){
		System.err.println("######## LOAD");
	    return super.load(input, request);
	}

}
