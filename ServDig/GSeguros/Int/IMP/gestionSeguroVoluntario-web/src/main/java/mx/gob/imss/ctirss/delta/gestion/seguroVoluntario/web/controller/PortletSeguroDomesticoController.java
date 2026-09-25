package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ws.client.core.WebServiceTemplate;

@Controller
@SessionAttributes("ivroSolicitante")
@RequestMapping(value="/portlet/seguroDomestico")
public class PortletSeguroDomesticoController extends WebServiceCallerController {

	@ModelAttribute("ivroSolicitante")
	public Fisica getSolicitante() {
		return new Fisica();
	}

	@Autowired
	@Qualifier("webServiceConsultaSeguroDomestico")
	private WebServiceTemplate webServiceConsultaSeguroDomestico;

	@RequestMapping(value="/{idPersona}", method = RequestMethod.GET)
	public String init(Model model, HttpSession session,
			@PathVariable Long idPersona,
			@ModelAttribute("ivroSolicitante") Fisica solicitante) {
		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);
		model.addAttribute("ivroSolicitante", persona);
		return "portletSeguroDomesticoInit";
	}

	@RequestMapping(value="/content/{idPersona}", method = RequestMethod.GET)
	public String content(Model model, @PathVariable Long idPersona,
			@ModelAttribute("ivroSolicitante") Fisica solicitante) {
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		SegurosIvro seguros = new SegurosIvro();
		try {
			seguros = callWebService(webServiceConsultaSeguroDomestico, persona, SegurosIvro.class, new Class[] {Persona.class});
		} catch(Exception e) {
//			seguros.getSeguroIvro()[0].get
			log.error("Error al intentar leer la lista de seguros de la persona.", e);
		}
		model.addAttribute("segurosDomesticos", seguros);
		return "portletSeguroDomesticoContent";
	}
}
