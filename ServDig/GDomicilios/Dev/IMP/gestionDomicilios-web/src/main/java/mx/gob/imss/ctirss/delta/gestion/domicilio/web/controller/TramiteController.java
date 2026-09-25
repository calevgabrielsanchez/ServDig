/**
 * gestionDomicilios-web12/03/2012
 * mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller12/03/2012
 * TramiteController.java
 * 12/03/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value="/tramite")
public class TramiteController extends AbstractController {
	
	
	@RequestMapping(value="/inicio", method=RequestMethod.GET)
	public String iniciar(Model model){
//		model.addAttribute("solicitud" , new Solicitud());
		return "tramite.inicio";
	}

	
	
	@RequestMapping(value="/capturar", method=RequestMethod.GET)
	public String capturar(Model model){
		
		
		return "tramite.capturar";
	}
	
	
	
}
