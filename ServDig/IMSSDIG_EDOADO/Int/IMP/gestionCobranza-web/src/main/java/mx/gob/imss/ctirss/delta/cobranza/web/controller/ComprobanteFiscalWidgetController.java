package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/widget/comprobanteFiscal")
public class ComprobanteFiscalWidgetController {

	@RequestMapping(value = "/{nrp}/{rfc}", method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request, @PathVariable String nrp,
			@PathVariable String rfc) {
		request.setAttribute("nrp", nrp);
		request.setAttribute("rfc", rfc);

		return "widgetComprobanteFiscalInit";
	}

	@RequestMapping(value = "/resumen/{nrp}/{rfc}")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model, @PathVariable String nrp,
			@PathVariable String rfc) {
		request.setAttribute("nrp", nrp);
		request.setAttribute("rfc", rfc);

		return "widgetComprobanteFiscalContenido";
	}

}
