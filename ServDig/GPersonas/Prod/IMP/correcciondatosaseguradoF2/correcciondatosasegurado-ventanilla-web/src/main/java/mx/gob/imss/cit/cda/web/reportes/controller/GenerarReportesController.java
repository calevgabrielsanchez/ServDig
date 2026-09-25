package mx.gob.imss.cit.cda.web.reportes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.imss.cit.cda.web.app.autorizador.utils.SeguimientoSolicitudEditor;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.reportes.constans.ReporteConstantes;
import mx.gob.imss.cit.cda.web.reportes.helper.GenerarReporteHelper;
import mx.gob.imss.cit.cda.web.reportes.vo.ReporteDTO;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

/**
 *
 */
@Controller
@Scope("request")
public class GenerarReportesController extends AbstractController {

    @Autowired
    private GenerarReporteHelper generarReporteHelper;

    @Autowired
    protected HttpSession httpSession;
    
    private static final Logger log = LoggerFactory.getLogger(GenerarReportesController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logInitBinder(binder);
        binder.registerCustomEditor(SeguimientoSolicitud.class,
                new SeguimientoSolicitudEditor());
    }

    private void logInitBinder(WebDataBinder binder) {
        log.debug("Creando DataBinder---" + binder);
    }

    @RequestMapping(RequestMappingConstants.READ_GENERA_PDF + "/{variable}")
    public String load(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String variable) {

        ReporteDTO reporte = getGenerarReporteHelper().obtenerReporteVariablesPDF(variable);
        try {
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + "ReporteCDA" + ".pdf");
            response.setContentLength((int) reporte.getContenido().length);
            response.setContentType("application/pdf");
            response.getOutputStream().write(reporte.getContenido(), 0,
                    reporte.getContenido().length);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error("Error GenerarReportesController load {}",e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(RequestMappingConstants.READ_GENERA_XLS + "/{variable}")
    public String loadXls(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String variable) {
        
        List<TramitesReportes> listaReporte = (List<TramitesReportes>) httpSession.getAttribute(ReporteConstantes.LIST_TRAMITE_REPORTE);        
        
        
        ReporteDTO reporte = getGenerarReporteHelper().obtenerReporteVariablesXLS(listaReporte);
        try {
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + "ReporteCDA" + ".xls");
            response.setContentLength((int) reporte.getContenido().length);
            response.setContentType("application/xls");
            response.getOutputStream().write(reporte.getContenido(), 0,
                    reporte.getContenido().length);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error("[{}]", e);
        }
        return null;
    }

    public GenerarReporteHelper getGenerarReporteHelper() {
        return generarReporteHelper;
    }

}