package mx.gob.imss.ctirss.domiciliosInegi.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IDomicilioInegiController {
	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody Object almacenaSessionDomicilioInegi(Object obj, HttpServletRequest request);
	
	public void eliminaSessionDomicilioInegi(HttpServletRequest request);
	public Object obtieneSessionDomicilioInegi(HttpServletRequest request);

}
