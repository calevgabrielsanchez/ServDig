/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.distss.escritorio.virtual.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud("13842952440332842");
		
		model.addAttribute("solicitud", solicitud);		
			
		return "home";
	}
}
