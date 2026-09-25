/**
 * Controller para los diferentes Widgets a desarrollar en el proyecto DELTA.
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 * 
 */
@Controller
@RequestMapping(value = "/widget/asignacion/nss")
public class WidgetAsignacionNSSController extends AbstractController {

	@Autowired
	PersonaBusinessRemote personaBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String initAsignacionNSSPersonaWidget(Model model,
			HttpSession session, HttpServletRequest request) {

		return "widgetNSSInit";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/detalle/{idPersona}", method = RequestMethod.GET)
	public String detalleAsignacionNSSPersonaWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona) {

		String view = null;
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);

		// Primero se busca si la persona ya tiene NSS a través de su ID
		try {
			String nss = this.personaBusiness.obtenerNssPersona(fisica
					.getIdPersona());
			
			// La persona ya tiene NSS
			this.log.debug("La persona " + fisica.getIdPersona()
					+ " ya cuenta con NSS [" + nss + "]");

			model.addAttribute("NSS_RECUPERADO", nss);
			
			view = "widgetNSSRecuperadoContenido";
			 
		} catch (PersonaConVariosNSSException e) {
			// La persona cuenta con más de un NSS
			this.log.debug("La persona [idPersona:" + idPersona
					+ "] cuenta con más de un NSS asociado directamente");
		} catch (PersonaSinNSSException e) {
			this.log.debug(e.getMessage());
		}

		if (StringUtils.isBlank(view)) {
			view = "widgetNSSContenido";
		}
		
		return view;
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/estudiantes", method = RequestMethod.GET)
	public String initAsignacionNSSPatronWidget(Model model,
			HttpSession session, HttpServletRequest request) {

		return "widgetNSSEstudiantesInit";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/estudiantes/detalle", method = RequestMethod.GET)
	public String detalleAsignacionNSSPatronWidget(Model model,
			HttpSession session, HttpServletRequest request) {

		return "widgetNSSEstudiantesContenido";
	}

}
