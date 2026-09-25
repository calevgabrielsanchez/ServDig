/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionDerechohabientes
 *  @Archivo:LoginController.java
 *  @Paquete:y.web.controller
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.web.validator.LoginValidator;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/login")
public class LoginController extends AbstractController {
	
	
	@RequestMapping(method=RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }
	
	
	
	@RequestMapping(value="/entrar",method=RequestMethod.POST)
	public String entrar(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session) {
        
		//TODO: Aplicacion del validator del objeto Usuario
		//TODO: Manejo de los errores y mensajes para la presentacion.
		
		new LoginValidator().validate(usuario, result);
		if(result.hasErrors()){
			return "gestionDerechohabientes";
		}
		session.setAttribute("usuario", usuario);
		this.setFechaSistema(session);
		
		
        return "home";
    }
	

}
