/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.HomeValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.cdsss.delta.portal.utils.PortalCiudadanoEnum;
import mx.gob.imss.cdsss.delta.portal.utils.PortalCiudadanoUtils;
import mx.gob.imss.cdsss.delta.portal.utils.PropertiesOpciones;
import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechosArcoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.ValidarAsignacionLocalizacionNssWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Ciudadano;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {

	private final String HOME = "home";
	private final String PRINCIPAL = "principal";
	private static final String KEY_CIUDADANO_SESSION = "ciudadano";
	private static final String ERROR_MENSAJE ="Usted cuenta con m\u00E1s de un NSS o su informaci\u00F3n esta desactualizada, por favor acuda a su Subdelegaci\u00F3n m\u00E1s cercana para aclarar su situaci\u00F3n.";
	private static final String KEY_PORTAL_CIUDADANO_SESSION = "PortalCiudadanoSession";
	private static final String KEY_ID_PORTAL_CIUDADANO = "idPortalCiudadano";
	private static final String KEY_URL_LOGIN_CIUDADANO = "urlLoginCiudadano";
	
	@Autowired
	private PropertiesOpciones propertiesOpciones;
	
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	@Autowired
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	
	@Autowired
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoService;
	
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;

	@Autowired
	private DerechosArcoServiceRemote derechosArcoServiceRemote;

	@RequestMapping("/testTramites")
	public String pruebaTest() {
		return "portalPrueba";
	}
	
	@RequestMapping("/testCont")
	public String pruebaCont() {
		return "portalPruebaCont";
	}
	
	/**
	 * Metodo para cargar pagina principal
	 * @param model 
	 * @param session 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session, HttpServletRequest request){
		return loginCommon(model, session, request, PortalCiudadanoEnum.PORTAL_CIUDADANO_GENERAL);
	}
	
	@RequestMapping(value = PortalCiudadanoUtils.URL_PORTAL_CIUDADANO_ALTA_PATRONAL, method = RequestMethod.GET)
	public String loginAltaPatronal(Model model, HttpSession session, HttpServletRequest request){
		return loginCommon(model, session, request, PortalCiudadanoEnum.PORTAL_CIUDADANO_ALTA_PATRONAL);
	}
	
	@RequestMapping(value = PortalCiudadanoUtils.URL_PORTAL_CIUDADANO_SEGURO_INDIVIDUAL, method = RequestMethod.GET)
	public String loginSeguroPersonal(Model model, HttpSession session, HttpServletRequest request){
		return loginCommon(model, session, request, PortalCiudadanoEnum.PORTAL_CIUDADANO_IVRO_INDIVIDUAL);
	}
	
	private String loginCommon(Model model, HttpSession session, HttpServletRequest request, 
			PortalCiudadanoEnum portalCiudadanoEnum){		
		session.setAttribute(KEY_PORTAL_CIUDADANO_SESSION, portalCiudadanoEnum);
		request.getSession().setAttribute(KEY_URL_LOGIN_CIUDADANO, portalCiudadanoEnum.getUrlLogin());
		
		session.removeAttribute("solicitudRegistro");
		setFechaSistema(session);
		model.addAttribute("fisica", new Fisica());
		return HOME;
	}

	/**
	 * Metodo que valida si ya se acepto previamente la carta de terminos y condiciones
	 * @param curp
	 * @param correo
	 * @return true si ya se acepto, false en caso contrario.
	 */
	@RequestMapping(value = "/terminos", method = RequestMethod.POST)
	@ResponseBody
	public String validarTerminos(@RequestParam String curp,
			@RequestParam String correo,
			@RequestParam String correoConfirmacion,
			@RequestParam("captcha") String captcha) {
		HomeValidator validador = new HomeValidator();

		// Se realiza validacion de campos
		if (StringUtils.isBlank(curp) || !validador.validarCurp(curp)) {
			return Boolean.FALSE.toString();
		}

		if (StringUtils.isBlank(correo) || !validador.validarCorreo(correo)) {
			return Boolean.FALSE.toString();
		}

		if (StringUtils.isBlank(correoConfirmacion)
				|| !validador.validarCorreo(correoConfirmacion)) {
			return Boolean.FALSE.toString();
		}

		if (StringUtils.isBlank(captcha)) {
			return Boolean.FALSE.toString();
		}

		// Se realiza validacion de NSS
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		CorreoElectronico correoElectronico = new CorreoElectronico();
		correoElectronico.setCorreo(correo);
		fisica.setCorreoElectronico(correoElectronico);

		try {
			Fisica fisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);

			try {
				ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
				validacionWrapper.setFisica(fisica);
				validacionWrapper.setValidarDifSoloFecNac(true);

				Fisica fisicaIMSS = serviceBusiness.validacionesNSS(fisica, false);

				if (!fisicaRenapo.getCurp().equals(fisicaIMSS.getCurp())
						|| !fisicaRenapo.getFechaNacimiento().equals(fisicaIMSS.getFechaNacimiento())) {
					// TODO Modificar a TRUE cuando se habilite carta de terminos
					return Boolean.FALSE.toString();
				} else {
					return validarCiudadano(curp, correo);
				}
			} catch (PersonaConNSSException e) {
				Fisica fisicaIMSS = e.getFisica();

				if (!fisicaRenapo.getCurp().equals(fisicaIMSS.getCurp())
						|| !fisicaRenapo.getFechaNacimiento().equals(fisicaIMSS.getFechaNacimiento())) {
					// TODO Modificar a TRUE cuando se habilite carta de terminos
					return Boolean.FALSE.toString();
				} else {
					return validarCiudadano(curp, correo);
				}
			} catch (Exception e) {
				return Boolean.FALSE.toString();
			}
		} catch (ClienteWebserviceRenapoCurpException e1) {
			return Boolean.FALSE.toString();
		}
	}

	private String validarCiudadano(String curp, String correo) {
		CiudadanoCurpCorreo ciudadanoCurp = null;

		try {
			ciudadanoCurp = portalCiudadanoService.consultarCurpCorreo(curp, correo);
		} catch (PortalCiudadanoException e) {
			e.printStackTrace();
			return Boolean.FALSE.toString();
		}

		if (ciudadanoCurp != null && !ciudadanoCurp.getIndAceptoTerminosCondicione()) {
			return Boolean.TRUE.toString();
		} else {
			return Boolean.FALSE.toString();
		}
	}

	/**
	 * Metodo que valida requisitos para iniciar sesion
	 * @param fisica objeto con los datos capturados
	 * @param result
	 * @param model
	 * @param captcha capturado por el usuario
	 * @param terminos true si se aceptan, false en caso contrario
	 * @param session
	 * @param request
	 * @param response
	 * @return pagina principal o pagina de inicio
	 */
	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public String consultaDatosBasicos(@ModelAttribute Fisica fisica, BindingResult result, Model model,
			@RequestParam("captcha") String captcha, boolean terminos, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		boolean requiereActualizacionFechaNac = false;
		boolean requiereActualizacionCurp = false;
		// String sesionCaptcha = (String) session.getAttribute(Constants.KEY_CAPTCHA_SESSION);
		// session.removeAttribute(Constants.KEY_CAPTCHA_SESSION);
		session.removeAttribute("solicitudRegistro");
		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

		new HomeValidator().validate(fisica, result);
		
		if (result.hasErrors()) {
			this.log.warn("Errores de captura");
			return HOME;
		}
	
		//NUeva implementación del captcha
		CaptchaSD captchaSd = new CaptchaSD(request, response);
		captchaSd.setCaptchaValue(captcha);
		try {
			CaptchaUtilSD.validaCaptachaSesion(captchaSd);
		}catch (CaptchaExceptionSD e) {
			this.log.error("ocurrio un error al  validar el captcha", e);
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", e.getMessage());
			result.addError(fieldError);
			return HOME;
		}

		
		Ciudadano ciudadano = new Ciudadano();
		Fisica fisicaIMSS = null;

		try {
			Fisica fisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp());

			try {
				ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
				validacionWrapper.setFisica(fisica);
				validacionWrapper.setValidarDifSoloFecNac(true);

				fisicaIMSS = serviceBusiness.validacionesNSS(fisica, false);

				if (!fisicaRenapo.getCurp().equals(fisicaIMSS.getCurp())) {
					requiereActualizacionCurp = true;
					fisicaIMSS.setCurp(fisicaRenapo.getCurp());
				}
				if (!fisicaRenapo.getFechaNacimiento().equals(fisicaIMSS.getFechaNacimiento())) {
					requiereActualizacionFechaNac = true;
					fisicaIMSS.setFechaNacimiento(fisicaRenapo.getFechaNacimiento());
				}
			} catch (PersonaConNSSException e) {
				fisicaIMSS = e.getFisica();

				if (!fisicaRenapo.getCurp().equals(fisicaIMSS.getCurp())) {
					requiereActualizacionCurp = true;
					fisicaIMSS.setCurp(fisicaRenapo.getCurp());
				}
				if (!fisicaRenapo.getFechaNacimiento().equals(fisicaIMSS.getFechaNacimiento())) {
					requiereActualizacionFechaNac = true;
					fisicaIMSS.setFechaNacimiento(fisicaRenapo.getFechaNacimiento());
				}
			}

			log.info("-----------------------------------> requiereActualizacionCurp: " + requiereActualizacionCurp);
			log.info("-----------------------------------> requiereActualizacionFechaNac: " + requiereActualizacionFechaNac);
			log.info("-----------------------------------> IdPersona a tratar: " + fisicaIMSS.getIdPersona());
			log.info("-----------------------------------> Usuario acepta terminos: " + terminos);
			boolean actualizarTerminos = false;
			CiudadanoCurpCorreo ciudadanoCurp = portalCiudadanoService
					.consultarCurpCorreo(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo());

			// Se indica que se acepta terminos y condiciones si previamente
			// se habia rechazado la carta de Terminos o se requiere cambiar CURP y
			// Fecha de Nacimiento y el usuario lo acepta desde el mensaje desplegado
			if ((ciudadanoCurp != null && !ciudadanoCurp.getIndAceptoTerminosCondicione() && terminos)
					|| ((requiereActualizacionCurp || requiereActualizacionFechaNac) && terminos)) {
				actualizarTerminos = true;
			}

			ciudadanoCurp = portalCiudadanoService.validarInicioCurpCorreo(fisica.getCurp(),
					fisica.getCorreoElectronico().getCorreo(), terminos);
			if (actualizarTerminos) {
				ciudadanoCurp = portalCiudadanoService.actualizarTerminos(
						ciudadanoCurp.getCveIdCiudadanoCurpCorreo(), terminos);
			}
			
			// Asociar correo-persona a Medios de Contacto si no
			// existe la relacion.
			if (fisicaIMSS != null && fisicaIMSS.getIdPersona() != null) {
				mediosContactoServiceBusiness.asociarCorreoMedioContactoPersona(
					fisicaIMSS.getIdPersona(), correoCapturado);
			}
			

			if (ciudadanoCurp.getIndAceptoTerminosCondicione() && terminos) {
				if (fisicaIMSS != null && fisicaIMSS.getIdPersona() != null) {

					ciudadano.setCurp(fisicaIMSS.getCurp());
					ciudadano.setCveIdFisica(fisicaIMSS.getCveFisica());
					ciudadano.setCveIdPersona(fisicaIMSS.getIdPersona());
					ciudadano.setRfc(fisicaIMSS.getRfc());
					ciudadano.setNombreCompleto(fisicaIMSS.getNombreCompleto());

					try {
						List<AsignacionNSS> listaNSS = grupoFamiliarService.getAsignacionNss(fisicaIMSS.getIdPersona());

						if (listaNSS != null && listaNSS.size() == 1) {
							ciudadano.setCveIdAsingacionNSS(listaNSS.get(0).getIdAsignacionNSS());
							ciudadano.setStrNss(listaNSS.get(0).getNss());

							// TODO Eliminar banderas para habilitar actualizacion de CURP y/o Fecha de Nacimiento
							requiereActualizacionCurp = false;
							requiereActualizacionFechaNac = false;
							//Solo se actualizan los campos que se pueden editar en caso de requerirse
							if (requiereActualizacionCurp || requiereActualizacionFechaNac) {
								portalCiudadanoService.actualizarDatosCiudadano(fisicaIMSS,
										requiereActualizacionCurp, requiereActualizacionFechaNac);
							}
							

							// Asociar correo-persona a Medios de Contacto si no
							// existe la relacion.
							mediosContactoServiceBusiness.asociarCorreoMedioContactoPersona(
											ciudadano.getCveIdPersona(), correoCapturado);
						} else if (listaNSS != null && listaNSS.size() > 1) {
							FieldError fieldError = new FieldError("fisica", "errorFormGeneral",
									"Usted cuenta con m\u00E1s de un NSS por favor acuda a su Subdelegaci\u00F3n m\u00E1s cercana para aclarar su situaci\u00F3n.");
							result.addError(fieldError);
							return HOME;
						} else {
							// TODO Eliminar banderas para habilitar actualizacion de CURP y/o Fecha de Nacimiento
							requiereActualizacionCurp = false;
							requiereActualizacionFechaNac = false;
							//Solo se actualizan los campos que se pueden editar en caso de requerirse
							if (requiereActualizacionCurp || requiereActualizacionFechaNac) {
								portalCiudadanoService.actualizarDatosCiudadano(fisicaIMSS,
										requiereActualizacionCurp, requiereActualizacionFechaNac);
							}
						}
					} catch (DerechohabientesBusinessException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// Se registra la persona a crear
					Fisica fisicaNueva = portalCiudadanoService.crearCiudadanoNuevo(fisicaIMSS);

					ciudadano.setCurp(fisicaNueva.getCurp());
					ciudadano.setCveIdFisica(fisicaNueva.getCveFisica());
					ciudadano.setCveIdPersona(fisicaNueva.getIdPersona());
					ciudadano.setRfc(fisicaNueva.getRfc());
					ciudadano.setNombreCompleto(fisicaNueva.getNombreCompleto());

					// Asociar correo-persona a Medios de Contacto
					mediosContactoServiceBusiness.asociarCorreoMedioContactoPersona(
									ciudadano.getCveIdPersona(), correoCapturado);
				}

				request.setAttribute("opciones", propertiesOpciones.getOpciones());
				request.setAttribute("correo", ciudadanoCurp.getRefCorreoElectronico());

				ObjectMapper json = new ObjectMapper();
				try {
					String data = json.writeValueAsString(ciudadano).toString();
					request.setAttribute("jsonCiudadano", data);
				} catch (Exception e3) {
					this.log.info(" -- No se puede parsear a JSON");
				}

				request.getSession().setAttribute(KEY_CIUDADANO_SESSION, ciudadano);
		
				//Se recupera indicador para determinar si es Portal General Ciudadano
				PortalCiudadanoEnum portalCiudadanoEnum = (PortalCiudadanoEnum) session.getAttribute(KEY_PORTAL_CIUDADANO_SESSION);
				log.info(" ID PORTAL CIUDADANO: " + portalCiudadanoEnum.getId());
				request.setAttribute(KEY_ID_PORTAL_CIUDADANO, portalCiudadanoEnum.getId());
				
				return PRINCIPAL;
			} else {
				FieldError fieldError = new FieldError("fisica", "errorFormGeneral",
						"Para poder continuar es requerido que acepte la carta t\u00E9rminos y condiciones.");
				result.addError(fieldError);
				return HOME;
			}
		} catch (PortalCiudadanoException e) {
			this.log.error("PortalCiudadanoException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", e.getMessage());
			result.addError(fieldError);
			return HOME;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error("CURPNoLocalizadoEnEntidadExternaException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", e.getMessage());
			result.addError(fieldError);
			return HOME;
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error("ClienteWebserviceRenapoCurpException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", e.getMessage());
			result.addError(fieldError);
			return HOME;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error("ErrorValidacionDatosConsultaEnEntidaExternaException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", e.getMessage());
			result.addError(fieldError);
			return HOME;
		} catch (GenerarNSSException e) {
			this.log.error("GenerarNSSException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_MENSAJE);
			result.addError(fieldError);
			return HOME;
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error("ErrorComparacionDatosRENAPOException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_MENSAJE);
			result.addError(fieldError);
			return HOME;
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error("DomicilioNoLocalizadoException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_MENSAJE);
			result.addError(fieldError);
			return HOME;
		} catch (UmfNoLocalizadaException e) {
			this.log.error("UmfNoLocalizadaException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_MENSAJE);
			result.addError(fieldError);
			return HOME;
		} catch (DomicilioNoValidoException e) {
			this.log.error("DomicilioNoValidoException" + e.getMessage());
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_MENSAJE);
			result.addError(fieldError); 
			return HOME;
		}
	}

	@ResponseBody
	@RequestMapping(value = "validaDerechosArco/{nss}", method = RequestMethod.POST)
	public Object validarNSS(@PathVariable("nss") String nss) {

		log.info("Se valida el NSS: " + nss);
		BloqueoDerechosArco bloqueoDerechosArco = derechosArcoServiceRemote
				.consultaBloqueo(nss, TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());
		if (bloqueoDerechosArco != null) {
			return bloqueoDerechosArco.isIndBloqueo();
		} else {
			log.info("Sin registros localizados");
			return null;
		}
	}

	/**
	 * Metodo que limpia los objetos en sesion
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(Model model, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "La sesion se ha limpiado");
		session.removeAttribute(KEY_CIUDADANO_SESSION);
		session.removeAttribute(KEY_PORTAL_CIUDADANO_SESSION);
		session.removeAttribute(Constants.KEY_CAPTCHA_SESSION);
		session.invalidate();
		
		return result;
	}
}
