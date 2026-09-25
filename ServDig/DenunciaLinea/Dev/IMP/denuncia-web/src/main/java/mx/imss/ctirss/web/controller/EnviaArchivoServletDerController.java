package mx.imss.ctirss.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/reporte/EnviaServletDer")
public class EnviaArchivoServletDerController {

	@RequestMapping(value = "/enviaServletDer")
    public String envia() {
        
        return "reportes/index2";
    }

	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
        return "reportes/index2";
	}

	
	
}
