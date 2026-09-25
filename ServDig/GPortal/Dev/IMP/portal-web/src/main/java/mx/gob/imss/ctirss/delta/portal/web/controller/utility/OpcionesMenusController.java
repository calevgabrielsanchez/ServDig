package mx.gob.imss.ctirss.delta.portal.web.controller.utility;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.TipoContenedorEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.portal.web.utils.PropertiesOpciones;

@Controller
@RequestMapping("/utility/menu/")
public class OpcionesMenusController extends AbstractController{

	@Autowired
	private PropertiesOpciones properties;
	
	@RequestMapping(value = "/opciones/{idTipoContenedor}/{idContenedor}", method = RequestMethod.POST)
	public String getOpcionesMenu(Model model,HttpServletRequest request, @PathVariable Integer idTipoContenedor,@PathVariable Integer idContenedor) {
		
		Map<String, Boolean> opciones = properties.getOpciones();
		model.addAttribute("idTipoContenedor", idTipoContenedor);
		model.addAttribute("idContenedor", idContenedor);
		model.addAttribute("opciones",opciones);
		if(idTipoContenedor.intValue() == TipoContenedorEnum.PORTLET.getId() 
				&& idContenedor.intValue() ==  TipoContenedorEnum.PORTLET.getId() ) {
			if(request.getSession().getAttribute("sesionTipoPM") != null) {
				request.setAttribute("reqTipoPM", TipoPersona.TIPO_PERSONA_MORAL);
				request.getSession().removeAttribute("sesionTipoPM");
			}
		}
		
		return "opcionesMenu";
	}
	
	@RequestMapping(value = "/opciones/portlet/grupo/{idParentesco}/{idEstado}", method = RequestMethod.POST)
	public String getOpcionesPortletGrupoFamiliar(Model model,HttpServletRequest request,@PathVariable Long idParentesco,@PathVariable Long idEstado) {
		
		Map<String, Boolean> opciones = properties.getOpciones();
		model.addAttribute("idParentesco", idParentesco);
		model.addAttribute("idEstado", idEstado);
		model.addAttribute("opciones",opciones);
		
		return "opcionesMenuGrupoFamiliar";
	}
	
	@RequestMapping(value = "/opciones/widgetNavegacion/tramite/{idTipoContenedor}/{portalContext}/{idParentesco}/{idEstado}/{patronPlataforma}/{patronListaBlanca}")
	public String getOpcionesWidgetTramites(Model model,HttpServletRequest request,@PathVariable Long idTipoContenedor,@PathVariable 
			Long portalContext,@PathVariable Long idParentesco,@PathVariable Long idEstado, @PathVariable String patronPlataforma, @PathVariable String patronListaBlanca ) {
		
		log.debug("patron plataforma " + patronPlataforma);
		log.debug("patron patronListaBlanca " + patronListaBlanca);
		Long idPatronPlataforma = 0L;
		Long idPatronListaBlanca = 0L;
		if(!StringUtils.isEmpty(patronPlataforma) && patronPlataforma.equalsIgnoreCase("true"))
			idPatronPlataforma=1L;
		if(!StringUtils.isEmpty(patronListaBlanca) && patronListaBlanca.equalsIgnoreCase("true"))
			idPatronListaBlanca=1L;
		
		log.debug("ei idPatronPlataforma es " + idPatronPlataforma);
		log.debug("el idPatronListaBlanca es " + idPatronListaBlanca);

		Map<String, Boolean> opciones = properties.getOpciones();
		model.addAttribute("idPatronPlataforma",idPatronPlataforma);
		model.addAttribute("idPatronListaBlanca",idPatronListaBlanca);
		model.addAttribute("idTipoContenedor", idTipoContenedor);
		model.addAttribute("idContenedor", portalContext);
		model.addAttribute("idParentesco", idParentesco);
		model.addAttribute("idEstado", idEstado);
		model.addAttribute("opciones",opciones);
		
		
		
		return "opcionesWidgetTramite";
	}
	
	@RequestMapping(value = "/opciones/adscripcion/{idParentesco}/{idEstado}", method = RequestMethod.POST)
	public String getOpcionesMenu(Model model,HttpServletRequest request,@PathVariable Long idParentesco,@PathVariable Long idEstado) {
		
		Map<String, Boolean> opciones = properties.getOpciones();
		model.addAttribute("idParentesco", idParentesco);
		model.addAttribute("idEstado", idEstado);
		model.addAttribute("opciones",opciones);
		
		return "opcionesMenuAdscripcion";
	}
	
	@RequestMapping(value = "/submenu/patrones/{registroPatronal}/{indClase}/{modalidad}/{idTipoRegPatron}/{estadoPatron}/{rfc}", method = RequestMethod.POST)
	public String getOpcionesSubmenuPatrones(Model model,HttpServletRequest request, @PathVariable String registroPatronal,
			@PathVariable String indClase,@PathVariable String modalidad, @PathVariable Long idTipoRegPatron, @PathVariable Long estadoPatron, @PathVariable String rfc) {
		
		Map<String, Boolean> opciones = properties.getOpciones();
		model.addAttribute("registroPatronal", registroPatronal);
		model.addAttribute("indClase", indClase);
		model.addAttribute("modalidad",modalidad);
		model.addAttribute("opciones",opciones);
		model.addAttribute("idTipoRegPatron", idTipoRegPatron);
		model.addAttribute("estadoPatron", estadoPatron);
		model.addAttribute("rfc", rfc);
		
		
		return "opcionesSubmenuPatron";
	}
	
}
