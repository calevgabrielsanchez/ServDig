/**
 * WelcomeController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
public class WelcomeController extends AbstractController {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/btnSalirHome")
	public String home(HttpServletRequest request) {
		return determinaURL(request, "home", "homePatron");
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome")
	public String getCreateForm(Model model) {
		model.addAttribute(new SegUsuario());
		return "acceso";
	}
	
	
}
