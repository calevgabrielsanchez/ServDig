/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author antonio
 */
@Controller
@Scope("request")
public class ResponsableController extends AbstractController {


	@RequestMapping(value = RequestMappingConstants.ATENCION_RESPONSABLE)
	public String inicio(HttpSession session) {
		return "atencionResponsable";
	}

	@RequestMapping(value = RequestMappingConstants.RESPONSABLE_SEGUIMIENTO)
	public String inicioSeguimiento(@RequestParam("idTramite") String idTramite,
			@RequestParam("propietarioTarea") String propietarioTarea,@RequestParam("idTarea") String idTarea,
			Model model, HttpSession session) {
		
		log.debug("---CDA--- idtramite seguimiento " + idTramite);
		model.addAttribute("idTramite", idTramite);
		model.addAttribute("propietarioTarea", propietarioTarea);
		model.addAttribute("idTarea", idTarea);
		return "responsableSeguimiento";
	}

}
