package mx.gob.imss.cit.cda.web.app.reportes.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;

import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import mx.gob.imss.cit.cda.web.app.autorizador.utils.SeguimientoSolicitudEditor;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
//import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.cit.cda.web.app.reportes.helper.GenerarReporteHelper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import mx.gob.imss.cit.cda.web.app.reportes.dto.ReporteDTO;
/**
 *
 */
@Controller
@Scope("request")
public class GenerarReportesController  extends AbstractController{

	@Autowired 
	GenerarReporteHelper generarReporteHelper;
	
	
	
	private final Logger log = LoggerFactory.getLogger(GenerarReportesController.class);
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logInitBinder(binder);
	    binder.registerCustomEditor(SeguimientoSolicitud.class, new SeguimientoSolicitudEditor());
	}
	
	private void logInitBinder(WebDataBinder binder){
		log.debug("Creando DataBinder---" + binder);
	}
	
		
	@RequestMapping(RequestMappingConstants.READ_GENERA_PDF+"/{variable}")		
	public String load(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,@PathVariable String variable) {
		
		
		ReporteDTO reporte=generarReporteHelper.obtenerReporteVariablesPDF(variable);
		   try {
		 response.addHeader("Content-Disposition", "attachment; filename=" + "prueba"+".pdf");
	        response.setContentLength((int) reporte.getContenido().length );
	        response.setContentType("application/pdf");
	        response.getOutputStream().write( reporte.getContenido(),0,reporte.getContenido().length );
	        response.getOutputStream().flush();
            response.getOutputStream().close();
		   } catch (Exception e) {
//	            log.error(e);
	        }
		 return null;
	}
	
	@RequestMapping(RequestMappingConstants.READ_GENERA_XLS+"/{variable}")		
	public String loadXls(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,@PathVariable String variable) {
		
		
		ReporteDTO reporte=generarReporteHelper.obtenerReporteVariablesXLS(null);
		   try {
		 response.addHeader("Content-Disposition", "attachment; filename=" + "prueba"+".xls");
	        response.setContentLength((int) reporte.getContenido().length );
	        response.setContentType("application/xls");
	        response.getOutputStream().write( reporte.getContenido(),0,reporte.getContenido().length );
	        response.getOutputStream().flush();
            response.getOutputStream().close();
		   } catch (Exception e) {
			   log.error("[{}]",e);
	       }
		 return null;
	}

}
