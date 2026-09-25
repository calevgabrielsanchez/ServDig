/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author vanderluk
 *
 */
@Controller
@RequestMapping(value="/domicilio/nacional/detalle/{idDomicilio}")
public class DetalleDomicilioController extends AbstractController {
	
	
	
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	
	/**
	 * Metodo para consultar el detalle de un domicilio y responder 
	 * con HTML.
	 * 
	 * @param idDomicilio
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping( method=RequestMethod.GET)
	public String get(@PathVariable Integer idDomicilio,  Model model, HttpServletResponse response)throws DomicilioNoLocalizadoException{
		
		Domicilio domicilio = new Domicilio();
		domicilio.setClave(idDomicilio);
		
		try {
			
			domicilio = this.domicilioServiceBusinessRemote.consultarDomicilio(domicilio);
			model.addAttribute("domicilio", domicilio);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			throw e;
		}
		return "nacional.detalle";
	}
	
	
	
	/**
	 * Metodo para consultar del detalle de un domicilio
	 * y regresar el objeto JSON del mismo.
	 * @param idDomicilio
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping( method=RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody Domicilio getJson(@PathVariable Integer idDomicilio,  Model model, HttpServletResponse response){
		
		Domicilio domicilio = new Domicilio();
		domicilio.setClave(idDomicilio);
		
		try {
			
			domicilio = this.domicilioServiceBusinessRemote.consultarDomicilio(domicilio);
			model.addAttribute("domicilio", domicilio);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return domicilio;
	}
	
	
	
	
	
	

}
