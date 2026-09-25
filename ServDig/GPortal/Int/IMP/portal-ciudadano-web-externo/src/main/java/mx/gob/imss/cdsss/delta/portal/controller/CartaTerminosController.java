package mx.gob.imss.cdsss.delta.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping(value = "/cartaTerminos/")
public class CartaTerminosController extends AbstractController{

	@RequestMapping(value = "/mostrarCartaTerminos/{tipoTramite}", method = {RequestMethod.GET, RequestMethod.POST })
	public String mostrarCartaTerminos(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable Long tipoTramite) {		
		request.setAttribute("tipoTramite", tipoTramite);
		
		return "viewCartaTerminos";
	}
	
}
