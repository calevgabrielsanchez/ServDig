package mx.gob.imss.cit.cda.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/logout")
public class LogoutCDAController extends AbstractController {

	@Value("${url.openam.logout}")
	private String LOGOUT_URL;
	
	@RequestMapping("/logoutFuncionario")
	public Object logoutVentanilla(HttpSession session, HttpServletRequest request){
		session.removeAttribute(SessionConstants.USER_PROFILE);
		session.invalidate();
		log.debug("--CDA-- SESION VENTANILLA TERMINADA");
		return "redirect:" + request.getContextPath() + LOGOUT_URL;
	}
	
}
