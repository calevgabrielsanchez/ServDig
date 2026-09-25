package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/portlet")
public class PortletsRepresentanteLegalController extends AbstractController {

	@Autowired
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/representantes/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initRepresentantesLegalesPortlet(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "portletRepresentantesInit";
	}

	/**
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/representantes/resumen/{idPersona}/{idTipoPersona}")
	public String obtenerRepresentantesLegales(@PathVariable Long idPersona,
			@PathVariable Long idTipoPersona, HttpServletRequest request) {

		TipoPersonaEnum tipoPersona = null;

		if (TipoPersonaEnum.FISICA.getId() == idTipoPersona.longValue()) {
			tipoPersona = TipoPersonaEnum.FISICA;
		} else {
			tipoPersona = TipoPersonaEnum.MORAL;
		}

		List<RepresentanteLegal> representantes = this.representanteLegalServiceBusiness
				.obtenerRepresentantesLegalesPorPersona(idPersona, tipoPersona);

		request.setAttribute("representantes", representantes);

		return "portletRepresentantesContenido";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/representante/obtener/representdos/{idPersona}", method = RequestMethod.GET)
	public String initRepresentadosPortlet(Model model,
			HttpServletRequest request, @PathVariable Long idPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		model.addAttribute("persona", persona);

		return "portletRepresentadosInit";
	}

	/**
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/representante/obtener/representdos/resumen/{idPersona}")
	public String obtenerRepresentadosLegales(@PathVariable Long idPersona,
			HttpServletRequest request, Model model) {

		List<Persona> representados = this.representanteLegalServiceBusiness
				.obtenerPersonasRepresentadasPorRepresentanteLegal(idPersona);

		request.setAttribute("representados", representados);
		model.addAttribute("personaMoral", new Moral());

		return "portletRepresentadosContenido";
	}

}