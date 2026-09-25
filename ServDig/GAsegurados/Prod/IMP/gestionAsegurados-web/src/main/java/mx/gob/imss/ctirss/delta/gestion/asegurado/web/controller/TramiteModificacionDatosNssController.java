package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConRPAsignado;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.ActualizacionDatosNssValidator;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
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

@Controller
@RequestMapping(value = "/tramite/actualiza/datos")
public class TramiteModificacionDatosNssController extends AbstractController {
	
	private static final String VISTA_INICIAR_TRAMITE = "consultapor.nssCurp";
	private static final String VISTA_MOSTRAR_DATOS = "mostrarResultadoCurpNss";
	private static final String VISTA_RESULTADO_TRAMITE = "mostrarResultadoTramite";
	private static final String KEY_PERSONA_ENCONTRADA = "keyPersonaSession";
	private static final String KEY_MDM = "keyMDMSession";
	private static final String KEY_ICA = "keyICASEssion";
	
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	@RequestMapping(value = "/iniciar")
	public String iniciarActualizacion(Model model) {
		
		model.addAttribute("fisica", new Fisica());
		
		return VISTA_INICIAR_TRAMITE;
	}
	
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public String buscarYMostrarDatosEncontrados(@ModelAttribute Fisica fisica,BindingResult result,Model model, HttpSession session) {
		
		String vista = VISTA_MOSTRAR_DATOS;
		Fisica encontrada = null;
		Boolean isICA = false;
		Boolean isMDM = false;
		fisica = this.nullearCampos(fisica);
		
		log.warn("Se recibio el siguiente nss: " + fisica.getNss());
		
		//Se valida la informacion del formulario
		new ActualizacionDatosNssValidator().validate(fisica, result);
		
		//Si existen errores en el fomulario se muestra la misma pantalla
		if(result.hasErrors()) {
			log.error("Se encontraron errores de captura");
			vista = VISTA_INICIAR_TRAMITE;
		} else {
			
			try {
				encontrada = serviceBusiness.validaExisteNSSActualizacion(fisica);
			} catch (ArgumentosInvalidosException e) {
				log.error(e);
			} catch (AsignacionNSSNoLocalizadoException e) {
				log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());
			} catch (GestionPatronalBusinessException e) {
				log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());
			} catch (AseguradoConRPAsignado e) {
				log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());
			} catch (ClienteWebserviceRenapoCurpException e) {
				log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());
			}
			
			if(result.hasErrors()) {
				return VISTA_INICIAR_TRAMITE;
			}
			
			if(!StringUtils.isEmpty(encontrada.getCurp())) {
				isICA = true;
			} else {
				isMDM = true;
			}
			
			session.setAttribute(KEY_PERSONA_ENCONTRADA, encontrada);
			model.addAttribute("fisica", encontrada);
			model.addAttribute("isICA", isICA);
			model.addAttribute("isMDM", isMDM);
		}
		
		return vista;
	}

	@RequestMapping(value = "/guardarICA", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> guardarICA(@RequestBody ICADatosRespuesta ica, HttpSession session) {
		
		Map<String, Object > respuesta = new HashMap<String, Object>();
		
		session.setAttribute(KEY_ICA, ica);
		session.removeAttribute(KEY_MDM);
		respuesta.put("message", "Se ha guardado correctamente el ica");
		
		return respuesta;
	}
	
	@RequestMapping(value = "/guardarMDM", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> guardarMDM(@RequestBody MDMDatosEntrada mdm, HttpSession session) {
		
		Map<String, Object > respuesta = new HashMap<String, Object>();
		
		session.setAttribute(KEY_MDM, mdm);
		session.removeAttribute(KEY_ICA);
		respuesta.put("message", "Se ha guardado correctamente el mdm");
		
		return respuesta;
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public String finalizarTramite(@ModelAttribute Fisica fisica, HttpSession session, Model model) {
		
		ICADatosRespuesta ica = (ICADatosRespuesta) session.getAttribute(KEY_ICA);
		MDMDatosEntrada mdm = (MDMDatosEntrada) session.getAttribute(KEY_MDM);
		Fisica encontrada = (Fisica) session.getAttribute(KEY_PERSONA_ENCONTRADA);
		String errorMessaje = null;
		Solicitud solicitud = null;
		TramiteCambioInformacionPersona tramite = new TramiteCambioInformacionPersona();
		
		if(mdm != null) {
			this.copiarAtributos(mdm.getPersonaFisica(),encontrada);
			
		} else {
			this.copiarAtributos(ica.getPersonaFisicaIMSS(), encontrada);
		}
		
		tramite.setFisica(encontrada);
		tramite.setDatosICA(ica);
		tramite.setDatosModifManual(mdm);
		Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
		
		try {
			solicitud = serviceBusiness.actualizaAsignacionNSS(tramite, usuario);
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		} catch (PersonaConNSSException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		} catch (SolicitudNoValidaException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		} catch (AfectacionDatosPersonaException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		} catch (PersonaNoEncontradaException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		} catch (SolicitudNoEncontradaException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
		} catch (TramiteNoEncontradoException e) {
			log.error("Ocurrio un error: " + e.getMessage(), e);
			errorMessaje = e.getSituacion();
		}
		
		session.removeAttribute(KEY_ICA);
		session.removeAttribute(KEY_MDM);
		
		if(solicitud != null) {
			model.addAttribute("solicitud", solicitud);
		}
		model.addAttribute("errorMessage", errorMessaje);
		
		return VISTA_RESULTADO_TRAMITE;
	}
	
	@RequestMapping(value = "/validar/curp", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarDatosConsulta( @RequestBody Fisica fisica,  Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		this.log.debug("entre a validar los datos" + ReflectionToStringBuilder.toString(fisica, ToStringStyle.MULTI_LINE_STYLE));
		
		Errors errors = new BindException(fisica, "model");
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			new PersonaFisicaCURPValidator().validate(fisica, errors);
			
			if( errors.hasErrors()){
				this.procesaErroresDeCaptura(errors, result, response);
				model.addAttribute("fisica", fisica);
				return result;
			}
			
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
			
		return result;
	}
	
	
	private void copiarAtributos(Fisica entrada,Fisica salida) {
		salida.setNombre(entrada.getNombre());
		salida.setPais(entrada.getPais());
		salida.setPrimerApellido(entrada.getPrimerApellido());
		salida.setSegundoApellido(entrada.getSegundoApellido());
		salida.setFechaNacimiento(entrada.getFechaNacimiento());
		salida.setFechaNacimientoFormateada(salida.getFechaNacimientoFormateada());
		salida.setSexo(entrada.getSexo());
		salida.setLugarNacimiento(entrada.getLugarNacimiento());
	}
	/**
	 * Con este metodo evitamos que se pasen al backend propiedades que vengan
	 * oomo cadenas vacias. Para un funcionamiento correcto de las consultas,
	 * las propiedades deben ser NULAS o NO NULAS (pero NUNCA CADENAS VACIAS)
	 * 
	 * @param oForm
	 * @return
	 */
	private Fisica nullearCampos(Fisica oForm) {
		Fisica personaFisica = new Fisica();
		
		if(StringUtils.isBlank(oForm.getNss())) {
			personaFisica.setNss(null);
		} else {
			personaFisica.setNss(oForm.getNss());
		}
		
		if (StringUtils.isBlank(oForm.getCurp())) {
			personaFisica.setCurp(null);
		} else {
			personaFisica.setCurp(oForm.getCurp().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getRfc())) {
			personaFisica.setRfc(null);
		} else {
			personaFisica.setRfc(oForm.getRfc().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getNombre())) {
			personaFisica.setNombre(null);
		} else {
			personaFisica.setNombre(oForm.getNombre().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getPrimerApellido())) {
			personaFisica.setPrimerApellido(null);
		} else {
			personaFisica.setPrimerApellido(oForm.getPrimerApellido().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getSegundoApellido())) {
			personaFisica.setSegundoApellido(null);
		} else {
			personaFisica.setSegundoApellido(oForm.getSegundoApellido().toUpperCase());
		}

		if (oForm.getFechaNacimiento() == null) {
			personaFisica.setFechaNacimiento(null);
		} else {
			personaFisica.setFechaNacimiento(oForm.getFechaNacimiento());
		}

		if (oForm.getLugarNacimiento() != null
				&& (oForm.getLugarNacimiento().getClave() == null
				|| oForm.getLugarNacimiento().getClave().equals("-1")
				|| oForm.getLugarNacimiento().getClave().equals(""))) {
			personaFisica.setLugarNacimiento(null);
		} else {
			personaFisica.setLugarNacimiento(oForm.getLugarNacimiento());
		}

		if (oForm.getSexo() != null && (oForm.getSexo().getIdSexo() == null
				|| oForm.getSexo().getIdSexo() == -1)) {
			personaFisica.setSexo(null);
		} else {
			personaFisica.setSexo(oForm.getSexo());
		}

		return personaFisica;
	}
}
