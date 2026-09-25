package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/wizard/comunes")
public class WizardComunesController {
	@RequestMapping(value="terminosCondicionesCuestionario")
	public String terminosCondicionesCuestionario() {
		return "wizardComunesTerminosCondicionesCuestionario";
	}
}
