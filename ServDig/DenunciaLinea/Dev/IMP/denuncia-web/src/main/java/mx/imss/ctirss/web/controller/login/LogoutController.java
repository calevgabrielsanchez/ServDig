/**
 * ClaseCatalogoController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.login.model.SegUsuario;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
public class LogoutController extends AbstractController {

	private static final String SALIR_APLICACION = "salirAplicacion";
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(LogoutController.class);

	@RequestMapping(value = "/logout")
	public String logout(@RequestParam(required=false) String tgt,
			HttpServletResponse response, HttpServletRequest request, Model model) {
		logger.debug("tgt :: " + tgt);
		model.addAttribute("segUsuario", new SegUsuario());
		//Invalidando la session
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		
		if(session != null){
			session.invalidate();
		}
		
		if (tgt != null && tgt.equals(Boolean.toString(false))) {
			return "salida";
		}else if(tgt != null && tgt.equals(SALIR_APLICACION)){
			return "salidaAplicacion";
		}
//		FIXME VAP -  salida para patrones
		return "salida";
	}



	
}
