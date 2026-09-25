/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.distss.portal.vigencia.grupo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	/*
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		
		model.addAttribute("usuario", new Usuario());
		
		model.addAttribute("idDummy", 1);
		
		return "home";
	}
	*/
	@RequestMapping(method = RequestMethod.GET, params="goto")
    public ModelAndView redirect(Model model, @RequestParam("goto") String redirectparam) {
		log.debug("entre a validar la session y replicarla ****** redirect [" + redirectparam 
				+ "]");
        //Si viene de otra aplicacion, redirigir a j_spring_security_check de la otra aplicacion
        if (loginExiste() 
                && !redirectparam.contains("/gestionVigenciaGpoFamiliar-web-externo/")
                && redirectparam.contains("j_spring_security_check")) {
            log.debug("***************************************");
            log.debug("Redirigiendo a : --> {}", redirectparam);
            log.debug("***************************************");
            return new ModelAndView (new RedirectView(redirectparam));
        }
        
        return new ModelAndView("home", "usuario", new Usuario());
    }

    private boolean loginExiste() {
        boolean found = null != SecurityContextHolder.getContext().getAuthentication();
        return found;
    }
    
    
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		
		log.info("llega la llamada JCSSS "  );
		return "";
	
	}


}
