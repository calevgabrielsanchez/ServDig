package mx.gob.imss.cit.cda.web.app.reportes.controller;

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
 */
@Controller
@Scope("request")
public class ReportesController extends AbstractController {

	@RequestMapping(value = RequestMappingConstants.VIEW_REPORTES)
	public String inicio(HttpSession session) {

		return "vistaReportes";
	}

}
