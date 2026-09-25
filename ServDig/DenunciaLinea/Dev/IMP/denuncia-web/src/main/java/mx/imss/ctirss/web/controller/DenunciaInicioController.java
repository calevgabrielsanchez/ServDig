package mx.imss.ctirss.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.imss.ctirss.framework.base.controller.AbstractController;

@Controller
@RequestMapping(value="/denunciaLinea/denunciaInicio")
public class DenunciaInicioController extends AbstractController{
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		return "denuncia/denunciaMain";
	}
}
