package mx.gob.imss.cit.cda.web.app.reportes.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesReportesPage;
//import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
import mx.gob.imss.cit.cda.web.app.reportes.model.TramitesReportes;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReadTramitesReportesController
		extends AbstractReadController<RequestTramitesReportesPage, Page<TramitesReportes>> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(GenerarReportesController.class);

	@Autowired
	@Qualifier(BeansConstants.READ_TRAMITES_REPORTES_HELPER)
	ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> service;

	@Override
	public ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> getHelper() {
		return service;
	}

	//READ_TRAMITES_ASIGNADOS_AUTORIZADOR
	@RequestMapping(RequestMappingConstants.READ_TRAMITES_REPORTES)
	@ResponseBody
	@Override
	public ResponseEntity<Page<TramitesReportes>> load(@RequestBody RequestTramitesReportesPage input, HttpServletRequest request){
		System.err.println("######## LOAD");
	    return super.load(input, request);
	}

}
