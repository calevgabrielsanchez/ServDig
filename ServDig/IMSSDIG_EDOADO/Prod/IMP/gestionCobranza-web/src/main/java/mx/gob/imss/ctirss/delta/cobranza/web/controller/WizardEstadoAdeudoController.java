package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "wizard/estadoAdeudo")
public class WizardEstadoAdeudoController extends AbstractController {
	
	@RequestMapping(value = "/{nrp}")
	public String mostrarGraficas(@PathVariable String nrp, Model model) {
		
		model.addAttribute("nrp", nrp);
		
		return "wizardGraficas";
		
	}

}
