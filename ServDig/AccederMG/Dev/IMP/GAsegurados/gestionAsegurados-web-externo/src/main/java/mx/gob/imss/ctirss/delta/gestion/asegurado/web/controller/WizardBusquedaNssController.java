package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.DatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wizard/busqueda/nss/")
public class WizardBusquedaNssController extends AbstractController {

	@Autowired
	private ServiceBusinessRemote serviceBusinessRemote;
	
	@RequestMapping(value = "/init")
	public String initBusquedaPersona(HttpSession session, Model model) {
		
		Fisica asignacion = new Fisica();
		model.addAttribute("asignacion", asignacion);
	
		
		return "initConsultaNss";
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> buscarNsss(@RequestBody Fisica asignacion, Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<AsignacionNSS> nsss = null;
		try {
			
			if(!StringUtils.isBlank(asignacion.getCurp())) {
				asignacion.setCurp(asignacion.getCurp().toUpperCase());
			}
			
			nsss = serviceBusinessRemote.buscarAsignacionNssPorCurpODatosBasicos(asignacion); 
			
			if(nsss == null || nsss.isEmpty()) {
				result.put("error", "No se encontraron NSS asociados con los datos proporcionados");
			} else {
				result.put("lista", nsss);
			}
		} catch (AsignacionNSSNoLocalizadoException e) {
			if(StringUtils.isBlank(asignacion.getCurp())) {
				result.put("error", "No se localiz&oacute; ningun N&uacute;mero de Seguridad Social asociado a los datos personales proporcionados.");
			} else {
				result.put("error", "No se localiz&oacute; ningun N&uacute;mero de Seguridad Social asociado a la CURP proporcionada.");
			}
		} catch(DatosInsuficientesParaConsultaException e) {
			result.put("error", e.getSituacion());
		}
		
		return result;
	}
	
	/**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Fisica oForm, final HttpServletResponse response) {
        log.trace("entramos a busqueda de persona para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new PersonaFisicaCURPValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
    
    /**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/validacionesBasicos", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormularioDatosBasicos(final @RequestBody Fisica oForm, final HttpServletResponse response) {
        log.trace("entramos a busqueda de persona para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new DatosBasicosValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
}
