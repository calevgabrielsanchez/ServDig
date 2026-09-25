/**
 * ClaseCatalogoController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.ctirss.login.model.SegMenu;
import mx.imss.ctirss.login.model.SegUsuario;
import mx.imss.ctirss.menu.service.interfaces.MenuService;

import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.TipoCertificado;
import mx.imss.ctirss.session.UserSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
public class IniciarPatronController extends AbsractSeguridadController {

	@Autowired
	private MenuService<SegMenu> menuServiceBean;

	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(IniciarPatronController.class);

	@RequestMapping(value = "iniciarPatron")
	public String iniciarPatron(@RequestParam String patIDSEnpie,
			@RequestParam String senucePAT, @RequestParam String itipoCE,
			HttpServletResponse response, HttpServletRequest request) {
		logger.debug("patIDSEnpie :: " + patIDSEnpie);
		logger.debug("senucePAT :: " + senucePAT);
		logger.debug("itipoCE :: " + itipoCE);
		

		
			return new WelcomeController().home();
		
		
		
		
	}

	

}
