package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/portlet/personaAutorizada/")
public class PortletPersonaAutorizadaController extends AbstractController {
	
	@Autowired PersonasAutorizadasServiceRemote personasAutorizadasServiceRemote;
	
	private static final String VIEW_PERSONAS_AUTORIZADAS_INIT = "portletPersonasAutorizadasInit";
	private static final String VIEW_PERSONAS_AUTORIZADAS_CONT = "portletPersonasAutorizadasContenido";
	
	@RequestMapping("/init/{idPersona}/{idTipoPersona}/{rfc}")
	public String initPersonasAutorizadas(Model model, @PathVariable Long idPersona, @PathVariable Long idTipoPersona,@PathVariable String rfc,
			HttpServletRequest request,HttpSession session) {
		Persona persona = new Persona();
		inicializarPersona(persona, idPersona, idTipoPersona, rfc);
		List<PersonaAutorizada> listaPersonasAut = personasAutorizadasServiceRemote.getPersonasAutorizadasByPersona(persona);

		if (listaPersonasAut != null) {
			request.setAttribute("sizeListaPersonasAut", listaPersonasAut.size());
		} else {
			request.setAttribute("sizeListaPersonasAut", 0);
		}

		persona.setIdPersona(idPersona);
		model.addAttribute("persona",persona);
		return VIEW_PERSONAS_AUTORIZADAS_INIT;
	}
	
	@RequestMapping("/resumen/{idPersona}/{idTipoPersona}/{rfc}")
	public String muestraPersonasAutorizadasAsociadas(Model model, @PathVariable Long idPersona,@PathVariable Long idTipoPersona,@PathVariable String rfc,
			HttpServletRequest request, HttpSession session) {
		Persona persona = new Persona();
		inicializarPersona(persona, idPersona, idTipoPersona, rfc);
		request.setAttribute("persona",persona);
		request.setAttribute("personasAutorizadas", personasAutorizadasServiceRemote.getPersonasAutorizadasByPersona(persona));
		return VIEW_PERSONAS_AUTORIZADAS_CONT;
	}

	private void inicializarPersona(Persona persona, Long idPersona, Long idTipoPersona, String rfc){
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		persona.setRfc(rfc);		
		if(idTipoPersona.equals(TipoPersonaEnum.MORAL.getId())) {
			persona.setIdPersona(null);
		}
	}
}

