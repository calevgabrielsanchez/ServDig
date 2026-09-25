package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping (value = "/portlet")
public class PortletsPatronesController extends AbstractController {

	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private PersonasAutorizadasServiceRemote personasAutorizadasServiceRemote;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/patrones/autorizados/persona/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initPatronesAutorizadosPersona(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "portletPatronesAutorizadosInit";
	}

	/**
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/patrones/autorizados/persona/resumen/{idPersona}/{idTipoPersona}")
	public String obtenerPatronesAutorizadosPersona(@PathVariable Long idPersona, 
			HttpServletRequest request, @PathVariable Long idTipoPersona) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		
		persona.setTipoPersona(tipoPersona);
		
		List<SujetoObligado> patronesAsociados = null;
		
		try {
			patronesAsociados = personasAutorizadasServiceRemote.getSujetosObligadosPorPersonaAutorizada(idPersona);
		} catch (Exception e) {
			this.log.error(e);
		}

		request.setAttribute("patronesAsociados", patronesAsociados);
		request.setAttribute("persona", persona);
		

		return "portletPatronesAutorizadosContenido";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/patrones/asociados/persona/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initPatronesAsociadosPersona(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "portletPatronesAsociadosInit";
	}

	/**
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/patrones/asociados/persona/resumen/{idPersona}/{idTipoPersona}")
	public String obtenerPatronesAsociadosPersona(@PathVariable Long idPersona, 
			HttpServletRequest request, @PathVariable Long idTipoPersona) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		
		persona.setTipoPersona(tipoPersona);
		
		List<SujetoObligado> patronesAsociados = null;
		
		try {
			//patronesAsociados = this.sujetoObligadoServiceBusiness.listarRegistrosPatronalesPorPersonaDatosBasicosPatron(persona);
			if(idTipoPersona.longValue() == TipoPersona.TIPO_PERSONA_FISICA.longValue())
				persona = this.sujetoObligadoServiceBusiness.obtenerPersonaPorIdentificador(idPersona);
			else if(idTipoPersona.longValue() == TipoPersona.TIPO_PERSONA_MORAL.longValue())
				persona = this.sujetoObligadoServiceBusiness.obtenerPersonaMoralPorIdentificador(idPersona);
			
			patronesAsociados = this.sujetoObligadoServiceBusiness.getListaPatronesPorPersona(persona);
		} catch (GestionPatronalBusinessException e) {
			this.log.error(e);
		}

		request.setAttribute("patronesAsociados", patronesAsociados);
		request.setAttribute("persona", persona);
		

		return "portletPatronesAsociadosContenido";
	}

	/**
	 * @param model
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/patrones/clasificacion/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initPatronesClasificacion(Model model,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("sujetoObligado", patron);

		return "portletPatronesClasificacionInit";
	}

	/**
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/patrones/clasificacion/detalle/{numeroRegistroPatronal}")
	public String obtenerPatronesClasificacion(HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal, Model model) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		model.addAttribute("sujetoObligado", patron);

		return "portletPatronesClasificacionContenido";
	}
	

}
