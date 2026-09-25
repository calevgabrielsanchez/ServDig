package mx.gob.imss.ctirss.delta.gestion.motorCalculo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/tramite")
public class WizardController extends AbstractController {

	@RequestMapping(value = "/iniciar/{idDummy}", method = RequestMethod.GET)
	public String iniciarTramiteDummy(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idDummy) {

		model.addAttribute("idDummy", idDummy);

		return "tramiteDummyInicio";
	}

	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudTramiteDummy(final HttpSession session,
			HttpServletRequest request, final Model model) {

		// En este punto se debe mandar a crear la solicitud

		return "tramiteDummyContenido";
	}

	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarSolicitudTramiteDummy(
			@PathVariable Long idSolicitud, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		// En este punto se manda a encolar la solicitud

		return result;
	}

	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		return result;
	}
}
