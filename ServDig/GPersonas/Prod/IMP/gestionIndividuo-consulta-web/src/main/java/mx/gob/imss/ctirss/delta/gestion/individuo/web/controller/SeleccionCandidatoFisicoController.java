/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value = "/servicios/internos/persona/fisica/paso2")
public class SeleccionCandidatoFisicoController extends AbstractController {
	private static final String KEY_LIST_CANDIDATOS = "KEY_LIST_CANDIDATOS";
	
	
	@Autowired
	ComplementarCalificacionPersonaFisicaServiceBusinessRemote complementarCalificacionPersonaFisicaServiceBusiness;
	

	
	@RequestMapping( method=RequestMethod.GET)
	public String iniciar(Model model,  HttpSession session){
		model.addAttribute("fisica", new Fisica());

		List<Candidato> candidatos = (List<Candidato>) session
				.getAttribute(KEY_LIST_CANDIDATOS);
		
		
		model.addAttribute("candidatos" , candidatos);
		
		return "consulta.restcandidatos";
	}
	
	
}
