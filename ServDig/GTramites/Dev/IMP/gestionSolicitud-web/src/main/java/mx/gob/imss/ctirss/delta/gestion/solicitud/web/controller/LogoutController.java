/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionSolicitud
 *  @Archivo:LogoutController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.solicitud.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
public class LogoutController extends AbstractController {

	
	
	@RequestMapping( value="/logout" , method=RequestMethod.GET)
	public String logout ( Model model, HttpSession session){
		session.invalidate();
		model.addAttribute("usuario",new Usuario());
		return "gestionSolicitud";
	}
	
}
