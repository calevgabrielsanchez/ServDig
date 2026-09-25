/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.autorizador.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

/**
 *
 * @author antonio
 */
@Controller
@Scope("request")
public class AutorizadorController extends AbstractController {

	@RequestMapping(value = RequestMappingConstants.ATENCION_AUTORIZADOR)
	public String inicio(HttpSession session) {

		return "atencionAutorizador";
	}

}
