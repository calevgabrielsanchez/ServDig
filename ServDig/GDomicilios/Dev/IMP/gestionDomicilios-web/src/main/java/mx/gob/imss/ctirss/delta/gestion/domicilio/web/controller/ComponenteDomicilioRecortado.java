package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller.validator.DomicilioRecortadoValidator;
import mx.gob.imss.ctirss.delta.gestion.domicilio.web.dto.InitDomicilioRecortadoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/domicilio/recortado")
public class ComponenteDomicilioRecortado extends AbstractController {

	private static final String VIEW_RECORTADA = "componenteDomicilioRecortado";
	private static final String VIEW_PRUEBA = "pruebaDomicilioRecortado";
	
	@RequestMapping(value = "/pruebaPlugin",method = {RequestMethod.POST, RequestMethod.GET})
	public String pruebaPlugin(Model model, HttpSession session) {
		
		return VIEW_PRUEBA;
	}
	
	@RequestMapping(value = "/pruebaBack",method = {RequestMethod.POST, RequestMethod.GET})
	public String pruebaBack(Model model, HttpSession session) {
		
		return VIEW_PRUEBA;
	}
	
	@RequestMapping(value = "/prueba",method = {RequestMethod.POST, RequestMethod.GET})
	public String prueba(Model model, HttpSession session) {
		
		return VIEW_PRUEBA;
	}
	
	@RequestMapping(value = "/init",method = {RequestMethod.POST, RequestMethod.GET})
	public String home(@RequestBody InitDomicilioRecortadoDTO initDomicilio, HttpServletRequest request, HttpSession session) {
		
		request.setAttribute("inicializadores", initDomicilio);
		
		return VIEW_RECORTADA;
	}
	
	@RequestMapping(value = "/validacionesCP", method = RequestMethod.POST) 
	public @ResponseBody Map<String, ? extends Object> validarCodigoPostal(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, final HttpSession session) {
	
		Map<String, Object> result = new HashMap<String, Object>();
		final Errors errors = new BindException(oForm, "model");
		new DomicilioRecortadoValidator().validateCodigoPostal(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
		return result;
	}
	
	@RequestMapping(value = "/validarDomicilio", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, final HttpSession session) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        final Errors errors = new BindException(oForm, "model");
        //Validamos el formulario
        new DomicilioRecortadoValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } 
       
        return result;
    }
}