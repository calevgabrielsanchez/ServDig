package mx.gob.imss.ctirss.delta.portal.web.controller.utility;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.portal.web.utils.PropertiesOpciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/opciones/widgetNavegacion/tramite/{idTipoContenedor}/{portalContext}/{idParentesco}/{idEstado}", method = RequestMethod.POST)
	public String getOpcionesWidgetTramites(Model model,HttpServletRequest request,@PathVariable Long idTipoContenedor,@PathVariable Long portalContext,@PathVariable Long idParentesco,@PathVariable Long idEstado) {
		
		Map<String, Boolean> opciones = properties.getOpciones();
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
