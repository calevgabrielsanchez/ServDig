/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author vanderluk
 *
 */
@Controller
@RequestMapping("/portlet/edoadeudo")
public class CobranzaPortletController extends AbstractController {

	@Autowired
	CobranzaServiceRemote cobranzaServiceRemote;
	
	@RequestMapping("/{nrp}")
	public String initSolicitudessPortlet(    Model model,
			HttpServletRequest request, @PathVariable String nrp) {
		
		request.setAttribute("nrp", nrp);
		
		return "portletGraficasInit";
	}
	
	
	@RequestMapping(value = "/graficas/{nrp}")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model, @PathVariable String nrp) {
		
		request.setAttribute("nrp", nrp);
		
		return "portletGraficasContenido";
	}
	
	
	
	
}
