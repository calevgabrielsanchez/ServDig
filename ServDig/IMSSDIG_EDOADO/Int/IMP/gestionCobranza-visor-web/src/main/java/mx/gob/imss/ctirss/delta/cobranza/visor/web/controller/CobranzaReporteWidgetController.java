package mx.gob.imss.ctirss.delta.cobranza.visor.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/widget/cobranza")
public class CobranzaReporteWidgetController extends AbstractController {

	

	@RequestMapping(method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request) {

		return "widgetReporteCobranzaInit";
	}

	@RequestMapping(value = "/resumen")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model) {
		
		
		
		return "widgetReporteCobranza";
	}
	
	
}
