/**
 * gestionDomicilios-web26/02/2012
 * mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller26/02/2012
 * DomicilioController.java
 * 26/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */



@Controller
@RequestMapping(value="/domicilio/registro")
public class RegistroDomicilioController extends AbstractController {
	/**
	 * Metodo para desplegar la pantalla de captura de un domicilio.
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String  inicio(Model model){
		model.addAttribute("domicilio" , new Domicilio());
		return "domicilio.captura";
	}

}
