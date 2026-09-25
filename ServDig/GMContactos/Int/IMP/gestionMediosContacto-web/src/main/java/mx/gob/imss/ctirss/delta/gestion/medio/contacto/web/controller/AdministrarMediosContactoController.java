package mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoFormWrapper;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.web.validator.AdministracionMediosContactoValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/medios/particulares/administrar")
public class AdministrarMediosContactoController extends AbstractController {

	private static final String MEDIOS_SESSION_KEY = "mediosContacto_";
	private static final String ID_PERSONA_SESSION_KEY = "idPersona";
	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;

	
	@RequestMapping(value = "/{idPersona}", method = RequestMethod.GET)
	public String initAdmonMediosContactoParticulares(final HttpServletRequest request,
			@PathVariable Long idPersona) {

		request.setAttribute("idPersona", idPersona);
		
		return "initAdmonMediosContacto";
	}
	
	@RequestMapping(value = "/init-test", method = RequestMethod.GET)
	public String initAdmonMediosContactoParticularesTest(final HttpSession session) {

		return "admonMediosContactoTest";
	}
	
	@RequestMapping(value = "/init/{idPersona}", method = RequestMethod.POST)
	public String obtenerAdmonMediosContactoWrapper(final HttpServletRequest request,
			@PathVariable Long idPersona) {
		
		request.setAttribute("idPersona", idPersona);
		
		return "admonMediosContactoWrapper";
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String admonMediosContactoWrapper(final HttpServletRequest request) {
		
		return "admonMediosContactoWrapper";
	}
	
	@RequestMapping(value = "/init/retomar/{idSolicitud}/{idPersona}", method = RequestMethod.POST)
	public String retomarAdmonMediosContactoWrapper(final HttpServletRequest request,
			@PathVariable Long idSolicitud, @PathVariable Long idPersona) {
		
		request.setAttribute("idSolicitud", idSolicitud);
		request.setAttribute("idPersona", idPersona);
		
		request.setAttribute("isRetomar", true);
		
		return "admonMediosContactoWrapper";
	}
	
	@RequestMapping(value = "/fisica/{idPersona}", method = RequestMethod.GET)
	public String obtenerMediosContactoParticularesPersonaFisica(final HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		session.removeAttribute(mediosSessionKey);

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		persona.setTipoPersona(tipoPersona);
		
		try {
			List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness
					.consultarMedioDeContactoPersona(persona);
			
			/* Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y así poder mostrarlo en la vista
			 */
			for(MedioContacto medio : mediosContacto){
				if(medio instanceof CorreoElectronico){
					medio.setDesFormaContacto(((CorreoElectronico)medio).getCorreo());
				}else if(medio instanceof TelefonoMovil){
					medio.setDesFormaContacto(((TelefonoMovil)medio).getNumero());
				}else if(medio instanceof TelefonoFijo){
					
					TelefonoFijo telFijo = (TelefonoFijo)medio;
										
					StringBuffer numFijo = new StringBuffer();
					if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
						numFijo.append(telFijo.getClaveLada());
						numFijo.append("-");
					}
					numFijo.append(telFijo.getNumero());
					if (StringUtils.isNotBlank(telFijo.getExtension())) {
						numFijo.append("-");
						numFijo.append(telFijo.getExtension());
					}
					telFijo.setDesFormaContacto(numFijo.toString());
					
					medio.setDesFormaContacto(numFijo.toString());
					
				}else if(medio instanceof Facebook){
					medio.setDesFormaContacto(((Facebook)medio).getCuenta());
				}else if(medio instanceof Twitter){
					medio.setDesFormaContacto(((Twitter)medio).getCuenta());
				}
			}
						
			session.setAttribute(mediosSessionKey, mediosContacto);
		} catch (PersonaSinMedioDeContactoException e) {
			session.setAttribute(mediosSessionKey, new ArrayList<MedioContacto>());
			log.info(e);
		}

		request.setAttribute(ID_PERSONA_SESSION_KEY, idPersona);
		
		return "initAdmonMediosContacto";
	}
	
	@RequestMapping(value = "/fisica", method = RequestMethod.GET)
	public String admonMediosSinIDPersona(final HttpSession session) {

		session.removeAttribute(MEDIOS_SESSION_KEY);
		session.setAttribute(MEDIOS_SESSION_KEY, new ArrayList<MedioContacto>());

		return "initAdmonMediosContacto";
	}
	
	@RequestMapping(value = "/retomar/fisica/{idSolicitud}/{idPersona}", method = RequestMethod.GET)
	public String retomarMediosContactoParticularesPersonaFisica(
			final HttpSession session, final HttpServletRequest request,
			@PathVariable Long idSolicitud, @PathVariable Long idPersona) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		session.removeAttribute(mediosSessionKey);
		
		try {
			List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness.retomarTramiteAdmonMedios(idSolicitud, false);
			List<MedioContacto> mediosContactoAux = null;
			
			
			/* Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y así poder mostrarlo en la vista
			 */
			if(!mediosContacto.isEmpty()){
				mediosContactoAux = new ArrayList<MedioContacto>();
				
				for(MedioContacto medio : mediosContacto){
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
						CorreoElectronico correo = new CorreoElectronico();
						correo.setClave(medio.getClave());
						correo.setDesFormaContacto(medio.getDesFormaContacto());
						correo.setCorreo(medio.getDesFormaContacto());
						correo.setTipoMedioContacto(medio.getTipoMedioContacto());
						correo.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(correo);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
						TelefonoMovil telMovil = new TelefonoMovil();
						telMovil.setClave(medio.getClave());
						telMovil.setDesFormaContacto(medio.getDesFormaContacto());
						telMovil.setNumero(medio.getDesFormaContacto());
						telMovil.setTipoMedioContacto(medio.getTipoMedioContacto());
						telMovil.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(telMovil);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
						TelefonoFijo telFijo = new TelefonoFijo();
						telFijo.setClave(medio.getClave());
												
						String[] telAux = medio.getDesFormaContacto().split("\\|");
						telFijo.setClaveLada(telAux[0]);
						telFijo.setNumero(telAux[1]);
						telFijo.setExtension(telAux[2]);
						
						StringBuffer numFijo = new StringBuffer();
						if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
							numFijo.append(telFijo.getClaveLada());
							numFijo.append("-");
						}
						numFijo.append(telFijo.getNumero());
						if (StringUtils.isNotBlank(telFijo.getExtension())) {
							numFijo.append("-");
							numFijo.append(telFijo.getExtension());
						}
						telFijo.setDesFormaContacto(numFijo.toString());
						
						telFijo.setTipoMedioContacto(medio.getTipoMedioContacto());
						telFijo.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(telFijo);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_FACEBOOK)){
						Facebook facebook = new Facebook();
						facebook.setClave(medio.getClave());
						facebook.setDesFormaContacto(medio.getDesFormaContacto());
						facebook.setCuenta(medio.getDesFormaContacto());
						facebook.setTipoMedioContacto(medio.getTipoMedioContacto());
						facebook.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(facebook);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TWITTER)){
						Twitter twitter = new Twitter();
						twitter.setClave(medio.getClave());
						twitter.setDesFormaContacto(medio.getDesFormaContacto());
						twitter.setCuenta(medio.getDesFormaContacto());
						twitter.setTipoMedioContacto(medio.getTipoMedioContacto());
						twitter.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(twitter);
					}
				}
			} else {
				/* Se buscan directamente a la base de datos los medios de la persona,
				 * ya que cuando ocurra este escenario es porque se creo una solicitud
				 * pero no se guardaron los cambios y la solicitud que se recupera
				 * está vacía
				 */
				
				Persona persona = new Persona();
				persona.setIdPersona(idPersona);
				
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
				persona.setTipoPersona(tipoPersona);
				
				try {
					mediosContactoAux = this.mediosContactoServiceBusiness
							.consultarMedioDeContactoPersona(persona);
					
					/* Se recorre la lista de medios de contacto, para settear el campo
					 * desFormaContacto y así poder mostrarlo en la vista
					 */
					for(MedioContacto medio : mediosContactoAux){
						if(medio instanceof CorreoElectronico){
							medio.setDesFormaContacto(((CorreoElectronico)medio).getCorreo());
						}else if(medio instanceof TelefonoMovil){
							medio.setDesFormaContacto(((TelefonoMovil)medio).getNumero());
						}else if(medio instanceof TelefonoFijo){
							
							TelefonoFijo telFijo = (TelefonoFijo)medio;
							
							StringBuffer numFijo = new StringBuffer();
							if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
								numFijo.append(telFijo.getClaveLada());
								numFijo.append("-");
							}
							numFijo.append(telFijo.getNumero());
							if (StringUtils.isNotBlank(telFijo.getExtension())) {
								numFijo.append("-");
								numFijo.append(telFijo.getExtension());
							}
							telFijo.setDesFormaContacto(numFijo.toString());
							
							medio.setDesFormaContacto(numFijo.toString());
						}else if(medio instanceof Facebook){
							medio.setDesFormaContacto(((Facebook)medio).getCuenta());
						}else if(medio instanceof Twitter){
							medio.setDesFormaContacto(((Twitter)medio).getCuenta());
						}
					}
					
				} catch (PersonaSinMedioDeContactoException e) {
					mediosContactoAux = new ArrayList<MedioContacto>();
					log.error(e);
				}
			}
			
			session.setAttribute(mediosSessionKey, mediosContactoAux);
		} catch (SolicitudNoEncontradaException e) {
			session.setAttribute(mediosSessionKey, new ArrayList<MedioContacto>());
			log.error(e);
		} catch (Exception e) {
			this.log.error(e);
		}

		request.setAttribute("idPersona", idPersona);
		
		return "initAdmonMediosContacto";
	}
	
	@RequestMapping(value = "/moral/{idPersona}", method = RequestMethod.GET)
	public String obtenerMediosContactoParticularesPersonaMoral(final HttpSession session,
			@PathVariable Long idPersona) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		session.removeAttribute(mediosSessionKey);

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		persona.setTipoPersona(tipoPersona);

		try {
			List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness
					.consultarMedioDeContactoPersona(persona);
						
			session.setAttribute(mediosSessionKey, mediosContacto);
		} catch (PersonaSinMedioDeContactoException e) {
			session.setAttribute(mediosSessionKey, new ArrayList<MedioContacto>());
			log.error(e);
		}

		return "initAdmonMediosContacto";
	}

	
	@RequestMapping(value = "/init-agregar/{idPersona}", method = RequestMethod.GET)
	public String initAgregarMedioContacto(Model model, @PathVariable Long idPersona) {

		MedioContactoFormWrapper medioContactoFormWrapper = new MedioContactoFormWrapper();
		medioContactoFormWrapper.setIdPersona(idPersona);
		
		model.addAttribute("medioContactoFormWrapper", medioContactoFormWrapper);
		
		return "admon.mediosContacto.captura";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> agregarMedioContacto(Model model,
			final HttpSession session, MedioContactoFormWrapper form, 
			HttpServletResponse response) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + form.getIdPersona();
		
		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<MedioContacto> mediosContacto = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);
		
		MedioContacto medioContacto = null;
		
		//Se valida el medio de contacto capturado		
		new AdministracionMediosContactoValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
			form.getCorreoElectronico().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getCorreoElectronico();
			medioContacto.setDesFormaContacto(form.getCorreoElectronico().getCorreo());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
			form.getTelefonoFijo().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoFijo();
			
			StringBuffer numFijo = new StringBuffer();
			if (StringUtils.isNotBlank(form.getTelefonoFijo().getClaveLada())) {
				numFijo.append(form.getTelefonoFijo().getClaveLada());
				numFijo.append("-");
			}
			numFijo.append(form.getTelefonoFijo().getNumero());
			if (StringUtils.isNotBlank(form.getTelefonoFijo().getExtension())) {
				numFijo.append("-");
				numFijo.append(form.getTelefonoFijo().getExtension());
			}
			
			medioContacto.setDesFormaContacto(numFijo.toString());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
			form.getTelefonoMovil().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoMovil();
			medioContacto.setDesFormaContacto(form.getTelefonoMovil().getNumero());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_FACEBOOK)){
			form.getFacebook().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getFacebook();
			medioContacto.setDesFormaContacto(form.getFacebook().getCuenta());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TWITTER)){
			form.getTwitter().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTwitter();
			medioContacto.setDesFormaContacto(form.getTwitter().getCuenta());
		}
				
		medioContacto.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
		mediosContacto.add(medioContacto);
		
		result.put("medioContactoFormWrapper", form);
    	return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modificar/{indexMedioContacto}/{idPersona}", method = RequestMethod.GET)
	public String modificiarMedioContacto(final HttpSession session,
			HttpServletRequest request,
			@PathVariable Integer indexMedioContacto,
			@PathVariable Long idPersona, Model model) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		List<MedioContacto> mediosContacto = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);
		
		MedioContacto medioContacto = mediosContacto.get(indexMedioContacto);

		MedioContactoFormWrapper formWrapper = new MedioContactoFormWrapper();
		
		if(medioContacto instanceof CorreoElectronico){
			formWrapper.setCorreoElectronico((CorreoElectronico) medioContacto);
		}else if(medioContacto instanceof TelefonoFijo){
			formWrapper.setTelefonoFijo((TelefonoFijo) medioContacto);
		}else if(medioContacto instanceof TelefonoMovil){
			formWrapper.setTelefonoMovil((TelefonoMovil) medioContacto);
		}else if(medioContacto instanceof Facebook){
			formWrapper.setFacebook((Facebook) medioContacto);
		}else if(medioContacto instanceof Twitter){
			formWrapper.setTwitter((Twitter) medioContacto);
		}
		
		formWrapper.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
		formWrapper.setIdPersona(idPersona);
		
		request.setAttribute("indexMedioContacto", indexMedioContacto);
		model.addAttribute("medioContactoFormWrapper", formWrapper);

		return "admon.mediosContacto.captura";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmar-modificacion/{indexMedioContacto}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> confirmarModificacion(final HttpSession session,
			MedioContactoFormWrapper form, HttpServletResponse response,
			@PathVariable Integer indexMedioContacto) {
		
		String mediosSessionKey = MEDIOS_SESSION_KEY + form.getIdPersona();

		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<MedioContacto> mediosContacto = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);
		
		MedioContacto medioContacto = null;
		
		//Se valida el medio de contacto capturado		
		new AdministracionMediosContactoValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
			form.getCorreoElectronico().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getCorreoElectronico();
			medioContacto.setDesFormaContacto(form.getCorreoElectronico().getCorreo());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
			form.getTelefonoFijo().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoFijo();
			
			StringBuffer numFijo = new StringBuffer();
			if (StringUtils.isNotBlank(form.getTelefonoFijo().getClaveLada())) {
				numFijo.append(form.getTelefonoFijo().getClaveLada());
				numFijo.append("-");
			}
			numFijo.append(form.getTelefonoFijo().getNumero());
			if (StringUtils.isNotBlank(form.getTelefonoFijo().getExtension())) {
				numFijo.append("-");
				numFijo.append(form.getTelefonoFijo().getExtension());
			}
			
			medioContacto.setDesFormaContacto(numFijo.toString());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
			form.getTelefonoMovil().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoMovil();
			medioContacto.setDesFormaContacto(form.getTelefonoMovil().getNumero());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_FACEBOOK)){
			form.getFacebook().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getFacebook();
			medioContacto.setDesFormaContacto(form.getFacebook().getCuenta());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TWITTER)){
			form.getTwitter().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTwitter();
			medioContacto.setDesFormaContacto(form.getTwitter().getCuenta());
		}

		medioContacto.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);

		mediosContacto.set(indexMedioContacto.intValue(), medioContacto);
		
		result.put("medioContactoFormWrapper", form);

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminar/{indexMedioContacto}/{idPersona}", method = RequestMethod.GET)
	public String eliminarMedioContacto(final HttpSession session,
			@PathVariable Integer indexMedioContacto, 
			@PathVariable Long idPersona,
			Model model) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		List<MedioContacto> mediosContacto = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);
		
		MedioContacto medio = mediosContacto.get(indexMedioContacto.intValue());

		/*
		 * Se settea el estado actual, para en caso de que el usuario solicite
		 * la acción de deshacer se tenga el estado que tenía
		 */
		medio.setEstadoAdministracionAnteriorMedioContacto(medio.getEstadoAdministracionMedioContacto());
		medio.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);

		return "initAdmonMediosContacto";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtener-medios/{idPersona}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody List<MedioContacto> obtenerListaMediosContactoAdministrados(
			final HttpSession session, @PathVariable Long idPersona) {

		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		List<MedioContacto> mediosContactoAux = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);
		List<MedioContacto> mediosContacto = null;
		
		if(mediosContactoAux != null){
			mediosContacto = new ArrayList<MedioContacto>();
			
			// Se transforman las especificaciones de medio de contacto a la generalidad de medio de contacto
			MedioContacto medio = null;
			for(MedioContacto medioContacto : mediosContactoAux){
				medio = new MedioContacto();
				medio.setClave(medioContacto.getClave());
				medio.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
				medio.setEstadoAdministracionMedioContacto(medioContacto.getEstadoAdministracionMedioContacto());
				
				if(medioContacto instanceof CorreoElectronico){
					medio.setDesFormaContacto(((CorreoElectronico) medioContacto).getCorreo());
				}else if(medioContacto instanceof TelefonoFijo){
					StringBuffer numeroFijo = new StringBuffer();
					TelefonoFijo telFijo = (TelefonoFijo) medioContacto;
					String lada = StringUtils.isNotBlank(telFijo.getClaveLada()) ? telFijo.getClaveLada() : " ";
					String extension = StringUtils.isNotBlank(telFijo.getExtension()) ? telFijo.getExtension() : " ";
					numeroFijo.append(lada).append("|");
					numeroFijo.append(telFijo.getNumero()).append("|");
					numeroFijo.append(extension);
					medio.setDesFormaContacto(numeroFijo.toString());
				}else if(medioContacto instanceof TelefonoMovil){
					medio.setDesFormaContacto(((TelefonoMovil) medioContacto).getNumero());
				}else if(medioContacto instanceof Facebook){
					medio.setDesFormaContacto(((Facebook) medioContacto).getCuenta());
				}else if(medioContacto instanceof Twitter){
					medio.setDesFormaContacto(((Twitter) medioContacto).getCuenta());
				}
				
				mediosContacto.add(medio);
			}
		}

		return mediosContacto;
	}
	
	@RequestMapping(value = "/limpiar-medios/{idPersona}", method = RequestMethod.POST)
	public @ResponseBody MedioContacto limpiarListaMediosAdministrados(
			final HttpSession session, @PathVariable Long idPersona) {
	
		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		session.removeAttribute(mediosSessionKey);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deshacer-eliminar/{indexMedioContacto}/{idPersona}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> deshacerEliminarMedioContacto(final HttpSession session,
			@PathVariable Integer indexMedioContacto,
			@PathVariable Long idPersona) {
		
		String mediosSessionKey = MEDIOS_SESSION_KEY + idPersona;
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<MedioContacto> mediosContacto = (List<MedioContacto>) session
				.getAttribute(mediosSessionKey);

		MedioContacto medio = mediosContacto.get(indexMedioContacto.intValue()); 
		
		medio.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionAnteriorMedioContacto());

		return result;
	}
}