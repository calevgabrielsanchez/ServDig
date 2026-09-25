package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.DatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/busqueda/persona/")
public class WizardBusquedaPersonaController extends AbstractController {
	
	@Autowired
	private ServiceBusinessRemote serviceBusinessRemote;

	@RequestMapping(value = "/init/{origenPeticion}")
	public String initBusquedaPersona(HttpSession session, Model model, @PathVariable Integer origenPeticion) {
		
		Fisica fisica = new Fisica();
		model.addAttribute("fisica", fisica);
		model.addAttribute("origenPeticion", origenPeticion);
		
		return "initConsultaPersona";
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> buscarPersonas(@RequestBody Fisica fisica, Model model) {
		
		String curp = fisica.getCurp();
		fisica = new Fisica();
		fisica.setSexo(null);
		fisica.setLugarNacimiento(null);
		fisica.setCurp(curp);
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Fisica> lista = null;
		
		try {
			lista = serviceBusinessRemote.localizarPersonaReglasDerechohabiente(fisica);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error("el curp no fue localizado", e);
			result.put("curpNoLocalizada", true);
			result.put("error", e.getSituacion());
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error("el cliente de renapo no se encuentra disponible", e);
			result.put("servicioNoDisponible", true);
			result.put("error", e.getSituacion());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error("Error en la validacion de datos", e);
			result.put("errorValidadacion", true);
			result.put("error", e.getSituacion());
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error("error al comparar renapo y datos enviados", e);
			result.put("errorComparacionDatos", true);
			result.put("error", e.getSituacion());
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("datos insuficientes", e);
			result.put("datosInsuficientes", true);
			result.put("error", e.getSituacion());
		}
		
		if(result.get("error") != null) {
			return result;
		} else {
			result.put("curpNoLocalizada", false);
			result.put("servicioNoDisponible", false);
			result.put("errorValidadacion", false);
			result.put("errorComparacionDatos", false);
			result.put("datosInsuficientes", false);
		}
		
		result.put("lista", lista);
		
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
