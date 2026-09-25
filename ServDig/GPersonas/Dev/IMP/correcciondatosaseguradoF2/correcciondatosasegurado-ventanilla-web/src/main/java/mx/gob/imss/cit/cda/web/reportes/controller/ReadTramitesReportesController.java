package mx.gob.imss.cit.cda.web.reportes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import mx.gob.imss.cit.cda.web.reportes.constans.ReporteConstantes;
import mx.gob.imss.cit.cda.web.reportes.vo.RequestTramitesReportesPage;
import mx.gob.imss.cit.cda.web.reportes.vo.TramitesReportes;
import mx.gob.imss.cit.cda.web.support.model.Page;

@Controller
public class ReadTramitesReportesController extends
        AbstractReadController<RequestTramitesReportesPage, Page<TramitesReportes>> {

    private final Logger LOGGER = LoggerFactory
            .getLogger(GenerarReportesController.class);

    @Autowired
    @Qualifier(BeansConstants.READ_TRAMITES_REPORTES_HELPER)
    private ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> service;

    @Autowired
    protected HttpSession httpSession;

    @Override
    public ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> getHelper() {
        return service;
    }

    // READ_TRAMITES_ASIGNADOS_AUTORIZADOR
    @RequestMapping(RequestMappingConstants.READ_TRAMITES_REPORTES)
    @ResponseBody
    @Override
    public ResponseEntity<Page<TramitesReportes>> load(
            @RequestBody RequestTramitesReportesPage input,
            HttpServletRequest request) {

        LOGGER.error("######## LOAD");
        httpSession.setAttribute(ReporteConstantes.SES_DATA_REPORTE_GRID,
                input.getFilter());
        return super.load(input, request);
    }

}