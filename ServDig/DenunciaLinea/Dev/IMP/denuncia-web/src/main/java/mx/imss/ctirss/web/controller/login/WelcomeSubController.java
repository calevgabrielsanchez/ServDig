/**
 * WelcomeController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login;

import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.login.model.SegUsuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
public class WelcomeSubController extends AbstractController {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/btnSalirHome2")
	public String home() {
		return "home";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome2")
	public String getCreateForm(Model model) {
		model.addAttribute(new DlcUsuario() );
		//return "acceso";
	    return "inicioRegistro";
	}
	
	

}
