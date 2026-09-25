package mx.gob.imss.cit.cda.web.reportes.controller;

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
import mx.gob.imss.cit.cda.web.reportes.vo.DetalleReporteCDA;
import mx.gob.imss.cit.cda.web.reportes.vo.RequestTramitesReportesPage;
import mx.gob.imss.cit.cda.web.support.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ReadVariableReportesController extends
        AbstractReadController<RequestTramitesReportesPage, Page<DetalleReporteCDA>> {

    private final Logger logger = LoggerFactory.getLogger(ReadVariableReportesController.class);
    
    @Autowired
    @Qualifier(BeansConstants.VARIABLE_ELEGIDA_HELPER)
    private ReadHelper<RequestTramitesReportesPage, Page<DetalleReporteCDA>> service;

    @Override
    public ReadHelper<RequestTramitesReportesPage, Page<DetalleReporteCDA>> getHelper() {
        return service;
    }

    // READ_TRAMITES_ASIGNADOS_AUTORIZADOR
    @RequestMapping(RequestMappingConstants.READ_VARIABLE_ELEGIDA)
    @ResponseBody
    @Override
    public ResponseEntity<Page<DetalleReporteCDA>> load(
            @RequestBody RequestTramitesReportesPage input,
            HttpServletRequest request) {
        
        logger.debug("######## LOAD");
        String variable = input.getFilter().getVariable();
        input.getFilter().setVariable(variable);
        return super.load(input, request);
    }

}