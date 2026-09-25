package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/error/*")
public class errorControler extends AbstractController {
	@RequestMapping(value="/error", method = RequestMethod.POST)
	public String goToErrorPage(){
	
		return "error";
	}

}
