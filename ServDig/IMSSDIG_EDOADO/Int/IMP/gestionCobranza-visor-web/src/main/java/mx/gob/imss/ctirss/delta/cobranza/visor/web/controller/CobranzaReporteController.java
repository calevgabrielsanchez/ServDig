package mx.gob.imss.ctirss.delta.cobranza.visor.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/portlet/cobranza")
public class CobranzaReporteController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request) {

		return "portletReporteCobranzaInit";
	}

	@RequestMapping(value = "/resumen")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model) {
		
		FiltroSolicitud filtroSolicitud = new FiltroSolicitud();
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if (usuario.getCveIdSubdelegacion() != null) {
			filtroSolicitud.setIdSubdelegacion(usuario.getCveIdSubdelegacion());
		}
		
		model.addAttribute("filtroSolicitud",filtroSolicitud);
		model.addAttribute("solicitud", new Solicitud());
		
		return "portletReporteCobranza";
	}
	
	
}
