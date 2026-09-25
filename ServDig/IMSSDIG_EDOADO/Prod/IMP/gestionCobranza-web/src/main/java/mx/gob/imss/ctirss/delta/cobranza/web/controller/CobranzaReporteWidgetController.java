package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.PatronCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/widget/cobranza")
public class CobranzaReporteWidgetController extends AbstractController {

	@Autowired
	private CobranzaServiceRemote cobranzaServiceRemote;
	@Autowired 
	private PatronCobranzaServiceRemote patronCobranzaServiceRemote;

	@RequestMapping(value="/{nrp}", method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request, @PathVariable String nrp) {

		request.setAttribute("nrp", nrp);
		
		return "widgetReporteCobranzaInit";
	}

	@RequestMapping(value = "/resumen/{nrp}")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model, @PathVariable String nrp) {
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		System.out.println("rp: " + rp);
		System.out.println("modalidad: " + modalidad);
		
		Patron patron = new Patron();
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
		}catch(Exception e) {
			e.printStackTrace();
			patron = null;
		}
		
		request.setAttribute("nrp", nrp);
		if(patron == null) {
			request.setAttribute("error", "No se econtraron datos");
		}else{
			
			Map<String,Object> totalesIMSS = cobranzaServiceRemote.getTotalesCreditosImss(patron);
			Map<String,Object> totalesIMSSRCV = cobranzaServiceRemote.getTotalesCreditosRCVImss(patron);
			
			request.setAttribute("totalesImss", totalesIMSS);
			request.setAttribute("totalesRcv", totalesIMSSRCV);
		}
		
		return "widgetReporteCobranza";
	}
	
	
}
