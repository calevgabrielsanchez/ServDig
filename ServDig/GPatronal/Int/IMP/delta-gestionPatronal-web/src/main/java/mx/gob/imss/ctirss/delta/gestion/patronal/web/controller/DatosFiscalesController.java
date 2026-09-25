/**
 * delta-gestionPatronal-web26/04/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller26/04/2012
 * DatosFiscalesController.java
 * 26/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value="{cveIdPatronSujetoObligado}/datosFiscales")
public class DatosFiscalesController extends AbstractController {
	
//	@Autowired
//	RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote; 
	
	@RequestMapping(method=RequestMethod.GET)
	public String  inicio(@PathVariable String cveIdPatronSujetoObligado, Model model, HttpSession session){
		
//		PatronSujetoObligado patronSujetoObligado = new PatronSujetoObligado();
//		
//		patronSujetoObligado.setCveIdPatronSujetoObligado(Long.parseLong(cveIdPatronSujetoObligado));
//		
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
//    	this.log.debug("Usuario: " + usuario);
//		
//		session.setAttribute("usuario", usuario);
//		
//		model.addAttribute(patronSujetoObligado);
//		
		
		return "datos.fiscales";
	}

}
