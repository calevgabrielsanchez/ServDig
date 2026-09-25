package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/portlet/solicitudes")
public class PortletSolicitudesController extends AbstractController {

	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "portletSolicitudesInit";
	}

	/**
	 * @param model
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/registroPatronal/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initSolicitudesRegistroPatronalPortlet(Model model,
			HttpServletRequest request, @PathVariable String numeroRegistroPatronal) {

		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("sujetoObligado", patron);

		return "portletSolicitudesRegPatInit";
	}

	/**
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resumen/{idPersona}/{idTipoPersona}")
	public String obtenerSolicitudes(@PathVariable Long idPersona,
			@PathVariable Long idTipoPersona, HttpServletRequest request) {
		
		List<Solicitud> solicitudes = null;

		solicitudes = this.solicitudBusiness.obtenerInfoBasicaSolicitudesPorPersonaPortal(idPersona, idTipoPersona, TipoSolicitudEnum.TODAS, null);

		request.setAttribute("solicitudes", solicitudes);

		return "portletSolicitudesContenido";
	}

	@RequestMapping(value = "/registroPatronal/resumen/{numeroRegistroPatronal}")
	public String obtenerSolicitudesRegistroPatronal(@PathVariable String numeroRegistroPatronal, HttpServletRequest request) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.consultarPorNumeroRegistroPatronal(numeroRegistroPatronal);
		List<Solicitud> solicitudes = solicitudBusiness
				.obtenerSolicitudesPorRegistroPatronal(patron.getCveIdSujetoObligado());
		request.setAttribute("solicitudes", solicitudes);

		return "portletSolicitudesRegPatContenido";
	}
}
