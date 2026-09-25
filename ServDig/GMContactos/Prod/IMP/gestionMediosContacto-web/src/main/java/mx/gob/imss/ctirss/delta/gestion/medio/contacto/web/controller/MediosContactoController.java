/**
 * gestionMediosContacto-web03/05/2012
 * mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.controller03/05/2012
 * MediosContactoController.java
 * 03/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoFormWrapper;
import mx.gob.imss.ctirss.delta.model.enums.TipoValidacionMediosContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.web.validator.MedioContactoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value = "/medios/contacto")
public class MediosContactoController extends AbstractController {

	private static final String PANTALLA_MEDIOS_CONTACTO = "seccionMediosContactoCommon";
	
	//Variables de sesion para MEDIOS CONTACTO
	private static final String MEDIOS_CONTACTO_FISCAL_TO_SESSION = "_mediosContactoFiscalesToSession";
	private static final String MEDIOS_CONTACTO_PARTICULARES_TO_SESSION = "_mediosContactoParticularesToSession";
	
	@Autowired
	MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;

		
	/**
	 * Metodo para obtener la vista de la captura del tipo
	 * de medio de telefono fijo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/telefono/fijo", method=RequestMethod.GET )
	public String getTelefonoFijo(Model model){
		model.addAttribute("telefonoFijo", new TelefonoFijo());
		return "telefono.fijo";
	}
	
	
	
	/**
	 * Metodo para obtener la vista de la captura del tipo
	 * de medio de telefono movil
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/telefono/movil", method=RequestMethod.GET )
	public String getTelefonoMovil(Model model){
		model.addAttribute("telefonoMovil", new TelefonoMovil());
		return "telefono.movil";
	}
	

	/**
	 * Metodo para obtener la vista de la captura del tipo
	 * de medio de correo electronico
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/correo/electronico", method=RequestMethod.GET )
	public String getCorreo(Model model){
		model.addAttribute("correoElectronico", new CorreoElectronico());
		return "correo";
	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String inicio(Model model) {

		// model.addAttribute("telefonoFijo", new TelefonoFijo());
		// model.addAttribute("correoElectronico", new CorreoElectronico());
		// model.addAttribute("telefonoMovil", new TelefonoMovil());

		model.addAttribute("medioContactoFormWrapper", new MedioContactoFormWrapper());

		return "mediosContacto.inicio";

	}

	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public String guardarMedioContacto(
			@ModelAttribute MedioContactoFormWrapper medioContactoWrapper,
			BindingResult result, Model model) {

		this.log.debug(" datos complementarios a guardar [" + medioContactoWrapper + "]");
		
		//Se validan los campos capturados del medio de contacto
		/*
		 * SE COMENTO LA VALIDACION PARA EFECTOS DE QUE LOS MEDIOS DE CONTACTO
		 * NO SEAN REQUERIDOS.
		 */
		//new MedioContactoValidator().validate(medioContactoWrapper, result);

		model.addAttribute("medioContactoFormWrapper", medioContactoWrapper);
		
		if (result.hasErrors()) {
			return "mediosContacto.inicio";
		}

		return "mediosContacto.finalizado";
	}

	@RequestMapping(value = "/telefonoFijo/guardar", method = RequestMethod.POST)
	public @ResponseBody
	TelefonoFijo guardarTelefonoFijo(@RequestBody TelefonoFijo telefonoFijo,
			HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/telefonoMovil/guardar", method = RequestMethod.POST)
	public @ResponseBody
	TelefonoMovil guardarTelefonoMovil(
			@RequestBody TelefonoMovil telefonoMovil,
			HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/correoElectronico/guardar", method = RequestMethod.POST)
	public @ResponseBody
	CorreoElectronico guardarCorreoElectronico(
			@RequestBody CorreoElectronico correoElectronico,
			HttpServletResponse response) {
		return null;
	}
	
	
	


/**
	 * 
	 * @param oForm
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/guardarMedios" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  agregar(@RequestBody MedioContactoFormWrapper oForm ,  HttpServletResponse response ){
    	
		
		Map result = new HashMap < String , Object>();
    	Errors errors = new BindException(oForm, "model");
    	
    	
    	new MedioContactoValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		TelefonoFijo tf =  oForm.getTelefonoFijo();
		TelefonoMovil tm = oForm.getTelefonoMovil();
		CorreoElectronico c = oForm.getCorreoElectronico();
		Facebook f = oForm.getFacebook();
		Twitter tw = oForm.getTwitter();
		
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		medios.add(tf);
		medios.add(tm);
		medios.add(c);
		medios.add(f);
		medios.add(tw);
		
		
		try {
		
			this.mediosContactoServiceBusinessRemote.registrarMedioDeContacto(medios);
		
		} catch (RegistrarMedioContactoException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		}
    	
		result.put("mediosContacto", oForm);
    	return result;
	}
	
	
	//headers="Accept=application/json"
	
	@RequestMapping(value="/get/{idPersona}", method=RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody MedioContactoFormWrapper getJson(@PathVariable Integer idPersona,  Model model, HttpServletResponse response){
		
		
		
		MedioContactoFormWrapper hojaContacto = new MedioContactoFormWrapper();
		Persona persona = new Persona();
		persona.setIdPersona(new Long( idPersona));
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		
		
		try {
			List<MedioContacto> medios =  this.mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(persona);
			if(medios != null){
				 Iterator<MedioContacto> it =  medios.iterator();
				 while(it.hasNext()){
					 
					 MedioContacto m = it.next();
					 
					 if(m instanceof TelefonoFijo){
						 TelefonoFijo telefonoFijo = (TelefonoFijo)m;
						 hojaContacto.setTelefonoFijo(telefonoFijo);
					 }else if ( m instanceof TelefonoMovil){
						 
						 TelefonoMovil telefonoMovil = (TelefonoMovil)m;
						 hojaContacto.setTelefonoMovil(telefonoMovil);
						 
					 }else if (m instanceof CorreoElectronico){
						 CorreoElectronico correoElectronico = (CorreoElectronico)m;
						 hojaContacto.setCorreoElectronico(correoElectronico);
					 }else if (m instanceof Facebook){
						 Facebook facebook = (Facebook)m;
						 hojaContacto.setFacebook(facebook);
						 
					 }else if ( m instanceof Twitter){
						 Twitter twitter = (Twitter)m;
						 hojaContacto.setTwitter(twitter);
					 }
					 
				 }
			}
			
		} catch (PersonaSinMedioDeContactoException e) {
			this.log.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		
		
		return hojaContacto;
	}

	
	/**
	 * Metodo para obtener la vista de la captura del tipo
	 * de medio de telefono fijo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/captura", method=RequestMethod.GET )
	public String captura(Model model){
		model.addAttribute("medioContactoFormWrapper", new MedioContactoFormWrapper());
		return "mediosContacto.captura";
	}

	@RequestMapping(value = "/validarMediosContactoExistente/{idTipoValidacion}/{idPersona}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> validarMediosContactoPersonaFisica(
			@PathVariable Long idPersona,
			@PathVariable Integer idTipoValidacion,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session, Locale locale) {
		Map<String, String> result = new HashMap<String, String>();

		try {
			limpiarMediosContactoDeSesion(session);	//TODO Inicializar medios
			boolean existeMedConFiscal = false;
			boolean existeMedConParticular = false;

			Persona persona = new Fisica();
			persona.setIdPersona(idPersona);
			try {
				List<MedioContacto> mediosFiscales = mediosContactoServiceBusinessRemote
						.consultarMediosFiscalesPersona(persona);
				session.setAttribute(MEDIOS_CONTACTO_FISCAL_TO_SESSION, mediosFiscales);
				if (mediosFiscales != null && !mediosFiscales.isEmpty()) {
					existeMedConFiscal = true;
				}
			} catch (PersonaSinMedioDeContactoException e) {
				log.warn(e);
			}			
			try{
				List<MedioContacto> mediosContacto = mediosContactoServiceBusinessRemote
						.consultarMedioDeContactoPersona(persona);
				session.setAttribute(MEDIOS_CONTACTO_PARTICULARES_TO_SESSION, mediosContacto);
				if (mediosContacto != null && !mediosContacto.isEmpty()) {
					existeMedConParticular = true;
				}
			} catch (PersonaSinMedioDeContactoException e) {
				this.log.warn(e);
			}

			if (idTipoValidacion.equals(TipoValidacionMediosContactoEnum.PARTICULAR_FISCAL.getCodigo())) {
				if (existeMedConParticular && existeMedConFiscal) {
					result.put("mensajeExito", "La validacion de los medios de contacto fiscales y particulares se ha realizado exitosamente");
					result.put("procesaMediosContacto", "procesaMediosContacto");
				} else if (!existeMedConParticular) {
					result.put("MediosContactoParticular", "Usted no cuenta con medios de contacto particulares para realizar el tramite");
				} else if (!existeMedConFiscal) {
					result.put("MediosContactoFiscal", "Usted no cuenta con medios de contacto fiscales para realizar el tramite");
				}
			} else if(idTipoValidacion.equals(TipoValidacionMediosContactoEnum.SOLO_PARTICULAR.getCodigo())) {
				if (!existeMedConParticular) {
					result.put("MediosContactoParticular", "Usted no cuenta con medios de contacto particulares para realizar el tramite");
				} else {
					result.put("mensajeExito", "La validacion de los medios de contacto particulares se ha realizado exitosamente");
					result.put("procesaMediosContacto", "procesaMediosContacto");
				}
			} else if(idTipoValidacion.equals(TipoValidacionMediosContactoEnum.SOLO_FISCAL.getCodigo())) {
				if (!existeMedConFiscal) {
					result.put("MediosContactoFiscal", "Usted no cuenta con medios de contacto fiscales para realizar el tramite");
				} else {
					result.put("mensajeExito", "La validacion de los medios de contacto fiscales se ha realizado exitosamente");
					result.put("procesaMediosContacto", "procesaMediosContacto");
				}
			}
		} catch (Exception e) {
			result.put("mensajeError", e.getMessage());
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarMediosContactoExistentes/{idTipoValidacion}/{idPersona}", method = { RequestMethod.GET, RequestMethod.POST })
	public String mostrarMediosContactoExistentes(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idPersona, @PathVariable Integer idTipoValidacion){
		System.err.println("MostrarMediosContactoExistentes");
		System.err.println("TipoValidacion " + idTipoValidacion);
		System.err.println("Persona " + idPersona);
		List<MedioContacto> listaMediosContactoFiscales = (List<MedioContacto>) session.getAttribute(MEDIOS_CONTACTO_FISCAL_TO_SESSION);
		List<MedioContacto> listaMediosContactoParticulares = (List<MedioContacto>) session.getAttribute(MEDIOS_CONTACTO_PARTICULARES_TO_SESSION);
		
		//Models de medios contacto
		model.addAttribute("listaMediosContactoFiscales", listaMediosContactoFiscales);
		model.addAttribute("listaMediosContactoParticulares", listaMediosContactoParticulares);
		
		limpiarMediosContactoDeSesion(session);
		return PANTALLA_MEDIOS_CONTACTO;
	}

	private void limpiarMediosContactoDeSesion(final HttpSession session) {
		session.removeAttribute(MEDIOS_CONTACTO_FISCAL_TO_SESSION);
		session.removeAttribute(MEDIOS_CONTACTO_PARTICULARES_TO_SESSION);		
	}
	
}
