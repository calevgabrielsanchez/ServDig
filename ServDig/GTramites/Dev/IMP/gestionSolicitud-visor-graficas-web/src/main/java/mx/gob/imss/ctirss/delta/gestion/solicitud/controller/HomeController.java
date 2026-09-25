package mx.gob.imss.ctirss.delta.gestion.solicitud.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.model.OpcionMenuGraficas;
import mx.gob.imss.ctirss.delta.gestion.solicitud.util.GraficasConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		
		List<OpcionMenuGraficas> opcionesMenu = new LinkedList<OpcionMenuGraficas>();
		
		String[] mainOptions = GraficasConfig.getProperty("menu.opciones").split("\\|");
				
		OpcionMenuGraficas opcion = null;
		OpcionMenuGraficas opcionDependiente = null;
		
		for(String mainOption : mainOptions) {
			opcion = new OpcionMenuGraficas();
			opcion.setId(mainOption);
			opcion.setTitulo(GraficasConfig.getProperty(mainOption + ".titulo"));
			opcion.setCollapsible(Boolean.parseBoolean(GraficasConfig.getProperty(mainOption + ".collapsible")));
			
			
			if(opcion.isCollapsible()) {
				String[] secOptions = GraficasConfig.getProperty(mainOption + ".opciones").split("\\|");
				
				for(String secOption : secOptions) {
					opcionDependiente = new OpcionMenuGraficas();
					opcionDependiente.setId(secOption);
					opcionDependiente.setTitulo(GraficasConfig.getProperty(secOption + ".titulo"));
					opcionDependiente.setUrl(generarUrl(secOption));
					
					opcion.getDependientes().add(opcionDependiente);
				}
			} else {
				opcion.setUrl(generarUrl(mainOption));
			}
			
			opcionesMenu.add(opcion);
		}
		
		request.setAttribute("opcionesMenu", opcionesMenu);
		
		return "home";
	}
	
	private String generarUrl(String id) {
		String url = GraficasConfig.getProperty(id + ".url");
		
		if(StringUtils.isBlank(url)) {
			url = "/gestionSolicitud-visor-graficas-web/grafica/" + id
					+ "/init";
		}
		
		return url;
	}

}
