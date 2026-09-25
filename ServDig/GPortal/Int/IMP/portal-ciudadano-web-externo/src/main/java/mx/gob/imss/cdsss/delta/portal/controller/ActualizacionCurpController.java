package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.HomeValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Ciudadano;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/asegurados/tramite/actualizacion")
public class ActualizacionCurpController extends AbstractController {
	
	
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	
	@Autowired
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoService;
	
	
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	
	private static final String VIEW_LOGIN = "inicioActualizacionCURP";
	private static final String VIEW_CONFIRMACION = "confirmacionActualizacionCURP";
	private static final String VIEW_SOLICITUD = "solicitudActualizacionCURP";
	private static final String REDIRECT_CONFIRMACION = "redirect:/asegurados/tramite/actualizacion/confirmacion";
	private static final String REDIRECT_FINALIZA ="redirect:/asegurados/tramite/actualizacion/solicitud/finalizada";
	
	private static final String ERROR_CAPTCHA = "La informaci\u00F3n del captcha no coincide, favor de intentar nuevamente.";
	private static final String ERROR_REQUERIDO = "Campo requerido";
	private static final String ERROR_TERMINOS = "Es necesario aceptar los terminos y condiciones";
	
	private static final String MENSAJE_ACTUALIZACION_GENERICO = "Los datos registrados en el IMSS asociados a la CURP, presentan alguna inconsistencia, por favor acude a tu Subdelegación para realizar la solicitud de regularización.";
	
	@RequestMapping("/{conNSS}")
	public String login(Model model, HttpSession session, @PathVariable String conNSS){
		this.limpiaSession(session);
		Boolean formConNSS = conNSS.equals("nss") ? true : false;
		
		session.setAttribute(Constants.KEY_FORM_CON_NSS,formConNSS);
		
		//se setea la marca de terminos y condiciones
		
		session.setAttribute(Constants.KEY_VIEW_TERMINOS_ACTUALIZA_CURP, "true");
	    Fisica fisica = new Fisica();
        
		model.addAttribute("fisica", fisica);
		return VIEW_LOGIN;
	}
	
	@RequestMapping("/validar")
	public String validar(@ModelAttribute Fisica fisica, BindingResult result, Model model,
			   @RequestParam("captcha") String captcha,   @RequestParam("hiddenTerminos") String terminos , HttpSession session,
			   HttpServletRequest request, HttpServletResponse response) {
		
		
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue() );
		String correo = fisica.getCorreoElectronico().getCorreo();
		TramiteActualizacionAsegurado tramite = null;
		Boolean validarNSS = (Boolean) session.getAttribute(Constants.KEY_FORM_CON_NSS);
		
		Ciudadano ciudadano = new Ciudadano();
		int codigoRespuesta = 0;
		Fisica fisicaEncontrada = null;
		Long cveIdAsignacon = null;
		String nssCapturado = fisica.getNss();
		
		
		
		new HomeValidator().validateConNSS(fisica, validarNSS, result);
		
		if (StringUtils.isBlank(captcha)) {
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_REQUERIDO);
			result.addError(fieldError);
		}
		
		log.debug("esto trae el checkbox [" +terminos+ "]" );
		if (StringUtils.isBlank(terminos)) {
			FieldError fieldError = new FieldError("fisica", "nombre", ERROR_TERMINOS);
			result.addError(fieldError);
			
		}
		

		
		
		
		if (result.hasErrors()) {
		
			return VIEW_LOGIN;
		}
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
			
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral",ERROR_CAPTCHA);
			result.addError(fieldError);
			return VIEW_LOGIN;
		}
		
		try{
			//se valida si tiene antecedentes en la relacion de asignacion de NSS por internet
			codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			if (codigoRespuesta < 1){
				nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue() );
				codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			}
			
			CiudadanoCurpCorreo ciudadanoCurp = null;
			log.debug("voy a hacer la llamada a la validacion");
				ciudadanoCurp = portalCiudadanoService.validaRegistroCurpCorreoCiudadano(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo());
			log.debug("sali la llamada a la validacion");
			if(fisica.getNss() != null &&  StringUtils.isNotBlank(fisica.getNss())){
				tramite = serviceBusiness.validacionesNSSActualizaCURPporNSS(fisica);
			}else{
				tramite = serviceBusiness.validacionesNSSActualizaCURP(fisica);
			}
			//paso la vadliacion primaria	
			
			fisicaEncontrada = tramite.getFisicaAnterior();
			
			cveIdAsignacon = serviceBusiness.obtenerCveAsignacionNss(fisicaEncontrada.getNss());
			
			tramite.setIdAsignacionNSS(cveIdAsignacon);
			tramite.setIdPersona(fisicaEncontrada.getIdPersona());
			
			//seccion para validar los roles con los que cuenta la persona
			try{
				CabezaGrupoFamiliar cabeza = grupoFamiliarService.getCabezaWS(cveIdAsignacon);
				List <Long> parentesco = new ArrayList();
				parentesco.add(ParentescoEnum.PADRES.getId());
				parentesco.add(ParentescoEnum.HIJOS.getId());
				parentesco.add(ParentescoEnum.CONYUGE.getId());
				parentesco.add(ParentescoEnum.CONCUBINARIO.getId());
				boolean isBeneficiario = grupoFamiliarService.validaPersonaExisteEnGruposFamiliaresPorParentesco(fisicaEncontrada.getIdPersona(), parentesco);
					if (grupoFamiliarService.esPatron(fisicaEncontrada.getIdPersona())||
							grupoFamiliarService.esRepresentanteLegal(fisicaEncontrada.getIdPersona()) ||
							cabeza.getCalidadParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId() ||
							personaFisicaServiceBusiness.isSocio(fisicaEncontrada.getIdPersona()) ||
							personaFisicaServiceBusiness.isPersonaAutorizada(fisicaEncontrada.getIdPersona())|| isBeneficiario
							){
						log.debug("----------------------------cuenta con algun rol por lo que no hace tramite");
						fisica.setErrorFormGeneral("Sus datos no pueden ser actualizados por este medio, por favor acude a tu subdelegación para realizar la solicitud de actualización.");
						model.addAttribute("fisica", fisica);
						return VIEW_LOGIN;
					}
					//socio dit_socio
					//autorizada dit_persona_autorizada
				 
			}catch(Exception e){
				log.error("----------ocurrio un error la consultar los roles", e);
				fisica.setErrorFormGeneral(e.getMessage());
				model.addAttribute("fisica", fisica);
				return VIEW_LOGIN;
			}
			
			
			ciudadano.setNombreCompleto(fisicaEncontrada.getNombreCompleto());
			ciudadano.setCurp(fisicaEncontrada.getCurp());
			ciudadano.setStrNss(fisicaEncontrada.getNss());
			
			tramite.getFisicaNueva().setNss(nssCapturado);
			
			
			model.addAttribute("fisica", fisica);
			//se coloca en session las variables para saber si se afectan los regisros encontrados
			if(codigoRespuesta < 1)
				session.setAttribute(Constants.KEY_ACTUALIZA_ASIGNACION_CORREO, true);
			else
				session.setAttribute(Constants.KEY_ACTUALIZA_ASIGNACION_CORREO, false);
			if(ciudadanoCurp != null)
				session.setAttribute(Constants.KEY_ACTUALIZA_PORTAL_CIUDADANO_CORREO, true);
			else
				session.setAttribute(Constants.KEY_ACTUALIZA_PORTAL_CIUDADANO_CORREO, false);
			

			session.setAttribute(Constants.KEY_TRAMITE_CAMBIO_CURP, tramite);
			session.setAttribute(Constants.KEY_CIUDADANO_SESSION, ciudadano);
			session.setAttribute(Constants.KEY_CORREO_CIUDADANO, correo);
			
			
			//se hace un redirect para que en caso de que se presione F5 no se vuelvan a hacer todas las validaciones
			//realizadas hasta el momento, y solo se recargaria el formulario
			return REDIRECT_CONFIRMACION;
		
		}catch (SolicitudNssCorreoException e) {
			//Esta exception debe mandar al home (a traves de la pantalla de salida)
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		}  catch (PortalCiudadanoException e) {
			this.log.error("error al consultar correo ciudadano ", e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral("No se localizó información en RENAPO con la CURP capturada");
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(MENSAJE_ACTUALIZACION_GENERICO);
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch ( GenerarNSSException e){
			this.log.error(e);
			fisica.setErrorFormGeneral(MENSAJE_ACTUALIZACION_GENERICO);
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		}catch (AsignacionNssPersonaException e){
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		}catch (AsignacionNSSNoLocalizadoException e){
			this.log.error(e);
			fisica.setErrorFormGeneral("No se localizó ningún registro con el NSS capturado");
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		}
		
		
	}
	
	@RequestMapping("/confirmacion")
	public String confirmacion(HttpSession session,
			   HttpServletRequest request, HttpServletResponse response) {
		
		//obtenemos el error de la seseion
		String error = (String)session.getAttribute("error");
		log.debug("existe error: " + error);
		//en caso de que si haya mensaje de error lo seteamos en el request
		if(StringUtils.isNotBlank(error)) {
			log.debug("Se pone el error en el request");
			request.setAttribute("error", error);
		}
		//quitamos el error de la sesion
		session.removeAttribute("error");
		log.debug("el error en request es : " + request.getAttribute("error"));
		
		return VIEW_CONFIRMACION;
	}
	
	@RequestMapping("/finalizar")
	public String finalizaSolicitud(HttpSession session, HttpServletRequest request) {
		
		log.debug("Entro a finalizar el tramite de actualizacion de datos");
		Boolean actualizaCurpCiudadano = (Boolean) session.getAttribute(Constants.KEY_ACTUALIZA_PORTAL_CIUDADANO_CORREO);
		Boolean actualizaCurpSolicitud = (Boolean) session.getAttribute(Constants.KEY_ACTUALIZA_ASIGNACION_CORREO);
		TramiteActualizacionAsegurado tramite = (TramiteActualizacionAsegurado) session.getAttribute(Constants.KEY_TRAMITE_CAMBIO_CURP);
		String correo = (String) session.getAttribute(Constants.KEY_CORREO_CIUDADANO);
		
		String curpNueva = tramite.getFisicaNueva().getCurp();
		Solicitud solicitud = null;
		String error = null;
		try {
			solicitud =serviceBusiness.crearFinalizarTramiteActualizacionDatos(tramite);
		} catch (ImpactaAlmacenesWSException e) {
			e.printStackTrace();
			error = "<strong>Ocurrio un error al finalizar la solicitud</strong>. "+ e.getMessage();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			error = "<strong>Ocurrio un error al finalizar la solicitud</strong>. "+ e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			error = "<strong>Ocurrio un error al finalizar la solicitud</strong>. "+e.getMessage();
		}
		
		if(StringUtils.isNotBlank(error)) {
			session.setAttribute("error", error);
			return REDIRECT_CONFIRMACION;
		}
		
		try {
			tramite.setTipoTramite(solicitud.getTramites().get(0).getTipoTramite());
			byte[] docto = (byte[])serviceBusiness.getAcuseActualizacionDatos(solicitud.getNoFolioSolicitud(), tramite.getTipoTramite().getDescripcion(), solicitud.getFirmaElectronica(), tramite);
			session.setAttribute(Constants.KEY_ACUSE_TRAMITE, docto);
		} catch (Exception e1) {
			log.error("Ocurrio un error al generar el acuse de actualizacion de datos");
			e1.printStackTrace();
		}
		
		
		
		if(actualizaCurpCiudadano) {
			try {
				portalCiudadanoService.actualizarCurpACorreo(correo,curpNueva);
			} catch (PortalCiudadanoException e) {
				e.printStackTrace();
			}
		} else {
			try {
				portalCiudadanoService.validarInicioCurpCorreo(curpNueva,
						correo, true);
			} catch (PortalCiudadanoException e) {
				e.printStackTrace();
			}
		}
		
		if(actualizaCurpSolicitud) {
			try {
				solicitudNssCorreoServiceBusiness.actualizarCurpACorreo(correo, curpNueva);
			} catch (SolicitudNssCorreoException e) {
				e.printStackTrace();
			}
		}
		
		
		session.setAttribute(Constants.KEY_SOLICITUD, solicitud);
		session.setAttribute(Constants.KEY_TRAMITE_CAMBIO_CURP, tramite);
		Ciudadano ciudadano =  (Ciudadano)session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		ciudadano.setCurp(curpNueva);
		session.setAttribute(Constants.KEY_CIUDADANO_SESSION, ciudadano);
		
		
		//Se hace un redirect para que no se vuelvan a hacer las peticiones de finalizado
		//en dado caso de que se presione F5 solo se recargaria el formulario
		return REDIRECT_FINALIZA;
	}
	
	@RequestMapping("/solicitud/finalizada")
	public String muestraDetalleSolicitud(HttpSession session, HttpServletRequest request) {
		
		return VIEW_SOLICITUD;
	}
	
	private void limpiaSession(HttpSession session) {
		session.removeAttribute(Constants.KEY_FORM_CON_NSS);
		session.removeAttribute(Constants.KEY_SOLICITUD);
		session.removeAttribute(Constants.KEY_TRAMITE_CAMBIO_CURP);
		session.removeAttribute(Constants.KEY_CORREO_CIUDADANO);
		session.removeAttribute(Constants.KEY_ACTUALIZA_PORTAL_CIUDADANO_CORREO);
		session.removeAttribute(Constants.KEY_CIUDADANO_SESSION);
		session.removeAttribute(Constants.KEY_ACTUALIZA_ASIGNACION_CORREO);
		session.removeAttribute(Constants.KEY_ACUSE_TRAMITE);
		session.removeAttribute(Constants.KEY_VIEW_TERMINOS_ACTUALIZA_CURP);

		session.removeAttribute("error");
	}
}
