/**
 * 
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author vanderluk
 *
 */
@Controller
@RequestMapping(value="/portlet")
public class PortletController extends AbstractController {


	@Autowired
	ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	
	
	@RequestMapping( value="/consulta/persona/fisica/{idPersona}" ,  method=RequestMethod.GET)
    public String initPersonaFisicaPortlet(Model model , HttpSession session,HttpServletRequest request , @PathVariable Long idPersona ) {
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);
		model.addAttribute("fisica", fisica);
        return "portletConsultaPersonaFisicaInit";
    }
	
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping( value="consulta/persona/fisica/detalle/{idPersona}" ,  method=RequestMethod.GET)
	public String detallePersonaFisicaWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
        model.addAttribute("fisica", new Fisica());
        
        try {
			Fisica fisica = this.serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
			model.addAttribute("fisica", fisica);
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
			model.addAttribute("error", e.getMessage());
		}
        
        return "portletConsultaPersonaFisicaContent";
    }
	
	
	
	
}
