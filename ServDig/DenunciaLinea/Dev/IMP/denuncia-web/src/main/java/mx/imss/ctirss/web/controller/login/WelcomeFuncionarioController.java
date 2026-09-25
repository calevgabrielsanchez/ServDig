/**
 * WelcomeController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Saúl Rosales Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 14/09/2012
 */
@Controller
public class WelcomeFuncionarioController extends AbstractController {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/btnFuncionarioSalirHome")
	public String home() {
		return "homeFuncionario";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcomeFuncionario")
	public String getCreateForm(Model model) {
		model.addAttribute(new DlcUsuario());
		return "accesoFuncionario";
	}

}
