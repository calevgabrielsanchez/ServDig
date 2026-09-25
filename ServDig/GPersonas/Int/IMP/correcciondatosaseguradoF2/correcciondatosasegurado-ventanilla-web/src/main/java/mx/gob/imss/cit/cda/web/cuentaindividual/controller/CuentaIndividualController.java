/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cuentaindividual.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

@Controller
public class CuentaIndividualController extends AbstractController {

	@RequestMapping(value = RequestMappingConstants.READ_CUENTA_INDIVIDUAL)
	public String inicioSeguimiento(@RequestParam("folioTramite") String folioTramite, Model model,
			HttpSession session) {
		model.addAttribute("folioTramite", folioTramite);
		return "cuentaIndividual";
	}

}
