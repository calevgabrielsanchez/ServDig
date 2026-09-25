/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portal
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.cobranza.visor.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.cobranza.visor.web.controller;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value="/home")
public class HomeController extends AbstractController {

	
	@RequestMapping(method=RequestMethod.GET)
    public String login(Model model, HttpSession session , HttpServletRequest request) {
		
		//UsuarioSSO usuarioSSO  = this.procesarUsuarioSSO(request);
		
        model.addAttribute("usuario", new Usuario());
        return "home";
    }
	
	
}
