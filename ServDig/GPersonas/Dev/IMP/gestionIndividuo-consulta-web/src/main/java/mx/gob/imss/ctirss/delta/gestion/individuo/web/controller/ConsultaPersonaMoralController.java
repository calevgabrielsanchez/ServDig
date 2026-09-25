/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.ConsultaPersonaMoralValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/persona/moral/ubicar")
public class ConsultaPersonaMoralController extends AbstractController {

	private static final String KEY_DATOS_ENTRADA = "KEY_DATOS_ENTRADA_PERSONA_MORAL";
	
	@Autowired
	ConsultaPersonaMoralServiceBusinessRemote consultaPersonaMoralServiceBusiness;
	
	@Autowired
	ComplementarCalificacionPersonaMoralServiceBusinessRemote ComplementarCalificacionPersonaMoralServiceBusiness;
	
	@Autowired
	LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote localizarPersonaMoralEnEntidadesExternasServiceBusiness;
	
	@RequestMapping( method=RequestMethod.GET)
	public String iniciar(Model model){
		model.addAttribute("moral", new Moral());
		
		return "consulta.personamoral";
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public String consultar( @ModelAttribute Moral moral , BindingResult result , Model model, HttpSession session){
		
		this.log.debug(" datos a consultar de la persona moral [" + moral +"]");
		
		
		new ConsultaPersonaMoralValidator().validate(moral, result);
		if(result.hasErrors()){
			return "consulta.personamoral";
		}
		
		List<Candidato> candidatos = null;
		candidatos = this.consultaPersonaMoralServiceBusiness.consultarPersonaMoral(moral);

		model.addAttribute("candidatos" , candidatos);
		
		/*
		 * Subimos a la sesion el objeto de la persona fisica
		 * consultada.
		 *  
		 */
		session.setAttribute(KEY_DATOS_ENTRADA, moral);
		
		
		return "consulta.personamoralcandidatos";
	}
	
	@RequestMapping(value="/complementar" , method=RequestMethod.POST)
	public String  complementar( @ModelAttribute Moral moral  , BindingResult result , Model model, HttpSession session){
		//Recuperamos los datos de entrada que se encuentran en la sesion
		Moral entrada = (Moral)session.getAttribute(KEY_DATOS_ENTRADA);
		
		/*
		 * Se inicia el proceso de complementacion de califiaciones de lapersona candidato
		 */
		try {
			
			moral = this.ComplementarCalificacionPersonaMoralServiceBusiness.complementarCalificaciones(moral, entrada);
			this.log.debug(" DATOS A COMPLEMENTAR [" + moral +"]");
			model.addAttribute("moral", moral);
			
		} catch (ErrorComparacionDatosSATException e) {
			model.addAttribute("moral", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personamoral";
		} catch (ClienteWebserviceSatRfcException e) {
			this.log.error(e);
			model.addAttribute("moral", entrada);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personamoral";
		}
		
		
		return "consulta.personamoraldetalle";
	}
		
	@RequestMapping(value="/validar" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  agregar(@RequestBody Moral oForm ,  HttpServletResponse response ){
    	
    	Map<String, Object> result = new HashMap < String , Object>();
    	Errors errors = new BindException(oForm, "model");
    	
    	new PersonaMoralValidator().validate(oForm, errors);
		
    	if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
    	
    	result.put("oForm", oForm);

    	return result;
	}




}
