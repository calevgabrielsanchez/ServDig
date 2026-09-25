package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller para el wizard de Asignación de NSS para estudiantes dentro del
 * Portal Patronal
 * 
 * @author Marco Sánchez
 * 
 */

@Controller
@RequestMapping(value = "/wizard/tramite/asignacion/nss/estudiantes")
public class WizardAsignacionNSSEstudiantesController extends AbstractController {
	
	protected static final String NRP_KEY = "nrpPatronNSS";
	
	@RequestMapping(value = "/{nrp}", method = RequestMethod.GET)
	public String initActualizarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request,
			@PathVariable String nrp) {
		
		model.addAttribute("nrp", nrp);
		
		return "wizardAsignacionNSSEstudiantesInit";
	}

	@RequestMapping(value = "/iniciar/tramite", method = RequestMethod.POST)
	public String crearSolicitudActualizacionDatos(final HttpSession session,
			HttpServletRequest request) {
		
		String nrp = request.getParameter(NRP_KEY);
		
		this.log.debug("NRP Asignacion NSS estudiantes -> " + nrp);
		
		request.setAttribute(NRP_KEY, nrp);
		
		return "redirect:/asignacion/portal/inicio/" + nrp;
	}
}
