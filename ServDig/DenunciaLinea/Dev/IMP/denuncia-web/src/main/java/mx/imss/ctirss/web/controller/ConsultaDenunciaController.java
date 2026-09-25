package mx.imss.ctirss.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.imss.ctirss.framework.base.controller.AbstractController;

@Controller
@RequestMapping(value = "/consulta/denuncia")
public class ConsultaDenunciaController extends AbstractController {

	
	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
				
		//String idDenuncia = (String)request.getSession().getAttribute("idDenuncia");
		//request.getSession().setAttribute("folioDenuncia", idDenuncia);
		
		return "reportes/muestraPDF";
	}
	
	@RequestMapping(value = "/muestraDenuncia")
	public String muestraDenuncia(HttpServletResponse response, HttpServletRequest request, HttpSession ses) 
	{
		
		//String idDenuncia = (String)request.getSession().getAttribute("idDenuncia");
		String numFolioDenuncia = (String)request.getSession().getAttribute("numFolioDenuncia");
		
		//request.getSession().setAttribute("folioDenuncia", idDenuncia);
		request.getSession().setAttribute("numFolioDenuncia", numFolioDenuncia);
		
		return "muestraPDF";

	}
	

	
}
