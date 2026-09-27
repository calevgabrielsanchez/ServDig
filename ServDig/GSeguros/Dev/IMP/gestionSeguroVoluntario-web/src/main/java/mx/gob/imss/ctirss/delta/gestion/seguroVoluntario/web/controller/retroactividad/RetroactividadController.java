package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.retroactividad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.BajaExpresaController;

@Controller
@RequestMapping(value = "/retroactividad")
public class RetroactividadController extends AbstractController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BajaExpresaController.class);
	
	private static final String OPCION_RETROACTIVIDAD = "seleccionRetroactividad";
	private static final String WIZARD_ALTA = "wizardContinuacionVoluntariaAltaInit";
	private static final String PERIODOS_RETRO = "mostrarPeriodosRetro";

	@RequestMapping(value = "/permisoRetroactividad", method = RequestMethod.GET)
	public String permisoRetroactividad(@RequestParam("nss") String nss) {
		System.out.println("permiso Retroactividad");
		return "seleccionRetroactividad";
		
	}
	
	
	@RequestMapping(value = "/periodos/{opcion}", method = RequestMethod.GET)
	public String mostrarPeriodos(@PathVariable String opcion) {
		System.out.println("Mostrar periodos");		
		if(Integer.valueOf(opcion) == 1) {
			return PERIODOS_RETRO;
		}else {
			return WIZARD_ALTA;
		}		
	}
	
	@RequestMapping(value = "/confirmarRetroactividad/{nssCifrado}", method = RequestMethod.GET)
	public String confirmarDatosRetroact(@PathVariable String nssCifrado) {
		
	System.out.println("Mostrar periodos");
		
		return "confirmarDatosRetroactividad";
		
	}	

}
