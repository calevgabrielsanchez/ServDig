package mx.gob.imss.ctirss.correccion.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/reporte/EnviaServlet")
public class EnviaArchivoServletController {

	@RequestMapping(value = "/enviaServlet")
    public String envia() {
        
        return "reportes/index";
    }

	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
        return "reportes/index";
	}

	
	
}
