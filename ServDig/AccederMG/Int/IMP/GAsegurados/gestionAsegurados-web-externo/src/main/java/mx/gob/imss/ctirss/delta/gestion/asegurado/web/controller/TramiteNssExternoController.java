package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.RegistroAseguradoDatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.web.validator.TramiteAseguradoDatosComplementariosValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/tramite")
public class TramiteNssExternoController extends AbstractController {

	private static final String KEY_SOLICITUD = "solicitudNssExterno";
	private static final String KEY_FISICA = "fisicaEncontrada";
	private static final String IS_DOMICILIO_SESSION_KEY = "IS_DOMICILIO";
	private static final String NSS_CORREO = "NSS_CORREO";
	private static final String EDO_SOLICITUD = "EDO_SOLICITUD";
	private static final String MOTIVO_CANCELACION_SOLICITUD = "MOTIVO_CANCELACION_SOLICITUD";
	private static final String HASH_CODE_DATOS_BASICOS = "hashDatosPersona";
	private static final String SOL_CHECK_STATUS = "SOL_AUX";

	@Autowired
	ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	PersonaBusinessRemote personaBusinessRemote;
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	ServiceBusinessRemote serviceBusiness;
	@Autowired
	ComponentesExternosBusinessRemote componentesExternosBusiness;
	@Autowired
	SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	@Autowired
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@Autowired
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@Autowired
	private  AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	
	@Autowired
	private SerieServiceBusinessRemote serieServiceBusiness;
	
	/* Paso 1 */
	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public Object iniciar(Model model, HttpSession session) {
		limpiarDoctosSession(session);
		/*model.addAttribute("fisica", new Fisica());
		
		this.setFechaSistema(session);
				
		return "consultapor.datosbasicos";*/
		
		return new RedirectView("/asignacionNSS", true);
	}

	/* Del paso 1 hacia el paso 2 */
	@RequestMapping(value = "/consultaDatosBasicos", method = RequestMethod.POST)
	public String consultaDatosBasicos(@ModelAttribute Fisica fisica,
			BindingResult result, Model model,
			@RequestParam("captcha") String captcha,
			HttpSession session, HttpServletRequest request) {
		
		int codigoRespuesta = 0;
		String vista = null;
		AsignacionNSS nss = new AsignacionNSS();
		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();

		// Se pasa a minúsculas el correo capturado y el de confirmación 
		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);
		
		this.log.debug("CURP capturado -> " + fisica.getCurp());
		this.log.debug("Correo electrónico capturado -> " + correoCapturado);
		this.log.debug("Correo electrónico confirmacion -> " + correoConfirmacion);
		
		this.log.debug("Captcha capturado -> " + captcha);
		this.log.debug("Captcha generado -> " + session.getAttribute("captcha"));

		new RegistroAseguradoDatosBasicosValidator().validate(fisica, result);
		
		if (StringUtils.isBlank(captcha)) {
			FieldError fieldError = new FieldError(
		            "fisica",
		            "errorFormGeneral",
		            "Campo requerido.");
	        result.addError(fieldError);
		}
		
		if (result.hasErrors()) {
			this.log.warn("Errores de captura");
			return "consultapor.datosbasicos";
		}
		
		this.log.debug("Se va a validar el captcha");
				
		// Se valida el captcha
		if (!captcha.equals(session.getAttribute("captcha"))) {
			this.log.error("Captcha no válido!!!");
			
			FieldError fieldError = new FieldError(
		            "fisica",
		            "errorFormGeneral",
		            "El captcha no fue válido, favor de intentar nuevamente");
	        result.addError(fieldError);
	        
	        return "consultapor.datosbasicos";
        }
		
		this.log.debug("Captcha válido!!!");
		
		/*
		 * Si las validaciones de datos requeridos y del captcha fueron
		 * exitosas, se elimina el correoFiscal, que en este caso representa la
		 * confirmación del correo electrónico. Se quita para que no se guarde
		 * en la solicitud y se tenga duplicado el correo.
		 */
		fisica.setCorreoElectronicoFiscal(null);
		
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue() );
		
		Fisica fisicaEncontrada = null;
		
		try {
			
			// Se valida la consulta/registro de NSS
			codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);
			
			try {
				fisicaEncontrada = this.serviceBusiness.validacionesNSSIncluyeCL3(fisica, true);
			} catch (DomicilioNoLocalizadoException e) {
					this.log.info(e);
					fisicaEncontrada = e.getFisica();
					session.setAttribute(IS_DOMICILIO_SESSION_KEY + fisicaEncontrada.hashCodeDatosBasicos(), false);
			}
			
			this.log.debug("Persona encontrada -> [id:"
					+ fisicaEncontrada.getIdPersona() + ", curp:"
					+ fisicaEncontrada.getCurp() + "]");
			
			
			if (fisicaEncontrada.getIdPersona() != null) {
				
				/*
				 * Una vez que se encuentra a la persona candidata para asignarle NSS,
				 * se valida que no tenga solicitudes pendientes siempre y cuando
				 * ya existan en la base de datos, es decir, que tenga idPersona
				 */
				
				boolean existeSolRegistrada = false;
				boolean existeSolProceso = false;
				Solicitud solicitudActiva = null;
				
				try {
					solicitudActiva = this.serviceBusiness.obtenerSolicitudRegistrada(fisicaEncontrada
									.getIdPersona());
				
					if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
						existeSolRegistrada = true;
					} else {
						solicitudActiva = this.serviceBusiness.obtenerSolicitudEnProceso(fisicaEncontrada
								.getIdPersona());
						
						if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
							existeSolProceso = true;
						} else {
							solicitudActiva = new Solicitud();
						}
					}
					
					if (existeSolRegistrada) {
						this.log.info("La persona [idPersona:"
								+ fisicaEncontrada.getIdPersona()
								+ "] ya cuenta con solicitud REGISTRADA para NSS [folio:"
								+ solicitudActiva.getNoFolioSolicitud() + "]");
	
						tramiteAsegurado
								.setErrorFormGeneral("Usted ya cuenta con una solicitud para Generación de Número de Seguridad Social registrada, cuyo folio es: "
										+ solicitudActiva.getNoFolioSolicitud());
	
						// Subimos al request el objeto de Modelo
						model.addAttribute("tramiteAsegurado", tramiteAsegurado);
						
						return "captura.libre";
					} else if (existeSolProceso) {
						this.log.info("La persona [idPersona:"
								+ fisicaEncontrada.getIdPersona()
								+ "] ya cuenta con solicitud EN_PROCESO para NSS [folio:"
								+ solicitudActiva.getNoFolioSolicitud() + "]");
	
						tramiteAsegurado
								.setErrorFormGeneral("Usted ya cuenta con una solicitud para Generación de Número de Seguridad Social en proceso, cuyo folio es: "
										+ solicitudActiva.getNoFolioSolicitud());
						
						// Subimos al request el objeto de Modelo
						model.addAttribute("tramiteAsegurado", tramiteAsegurado);
						
						return "captura.libre";
					}
				} catch (SolicitudException e) {
					this.log.error(e);
				}
				
				/*
				 * Se obtienen los documentos probatorios de la persona localizada,
				 * sólo si fue localizada en el IMSS
				 */
				fisicaEncontrada
						.setDocumentosProbatorios(this.componentesExternosBusiness
								.getDocumentosProbatoriosPersona(fisicaEncontrada));
		
				pasarDocumentosProbatorios(fisicaEncontrada);
			}

			/*
			 * Para evitar que los medios de contacto (si es que existen), se
			 * tomen en cuenta en el proceso, se limpia y después se settea el
			 * correo electrónico capturado
			 */
			fisicaEncontrada.getMediosContacto().clear();
			fisicaEncontrada.setCorreoElectronico(fisica.getCorreoElectronico());
			
			try {
				ConvertUtils.register(new DateConverter(null), Date.class);
				BeanUtils.copyProperties(nss, fisicaEncontrada);

				List<PersonaCalificacion> calificaciones = fisicaEncontrada
						.getPersonaCalificaciones();
				this.log.debug("Calificaciones :" + calificaciones);

				nss.setPersonaCalificaciones(calificaciones);

			} catch (IllegalAccessException e) {
				this.log.error(e);
			} catch (InvocationTargetException e) {
				this.log.error(e);
			}

			tramiteAsegurado.setFisica(nss);

			model.addAttribute(HASH_CODE_DATOS_BASICOS, fisicaEncontrada.hashCodeDatosBasicos());
			
			session.setAttribute(KEY_FISICA + fisicaEncontrada.hashCodeDatosBasicos(), fisicaEncontrada);
			session.setAttribute(NSS_CORREO + fisicaEncontrada.hashCodeDatosBasicos(), nssCorreo);
			
			vista = "captura.libre";
		} catch (GenerarNSSException e) {
			/* 
			 * Alguna de las validaciones no se cumplió, por lo tanto,
			 * se manda a ventanilla
			 */
			this.log.info(e);
			tramiteAsegurado.setErrorFormGeneral(e.getMessage());
			request.setAttribute("mostrarInstruccionesVentanilla", true);
			vista = "captura.libre";
		} catch (PersonaConNSSException e) {
			/*
			 * La persona ya cuenta con NSS se debe realizar lo siguiente:
			 * mandar por correo, registrar operación y crear solicitud
			 * de recuperación de NSS
			 */
			this.log.info(e);
			request.setAttribute("NSS_RECUPERADO", true);
			e.getFisica().setCorreoElectronico(fisica.getCorreoElectronico());
						
			try {
				ConvertUtils.register(new DateConverter(null), Date.class);
				BeanUtils.copyProperties(nss, e.getFisica());
			} catch (IllegalAccessException e1) {
				this.log.error(e1);
			} catch (InvocationTargetException e1) {
				this.log.error(e1);
			}
			tramiteAsegurado.setFisica(nss);
			
			this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
			
			//se setea el usuario con los datos de la curp localizada
			this.log.debug("el curp es" + e.getFisica().getCurp());
			Usuario usuario = new Usuario();
			usuario.setUsuario(e.getFisica().getCurp());
			
			try {
				Solicitud solicitud = null;
				
				Map<String, Object> resultado = this.serviceBusiness.crearSolicitudRecuperacionNSS(
						e.getFisica(), OrigenSolicitudEnum.INTERNET, usuario);
				
				solicitud = (Solicitud) resultado.get("solicitud");
				byte[] comprobante = (byte[]) resultado.get("comprobante");
				byte[] comprobanteQR = (byte[]) resultado.get("comprobanteQR");
				
				Map<String, byte[]> doctos = new HashMap<String, byte[]>();
				doctos.put("comprobanteLocalizacion"+nss.getNss()+".pdf",comprobante);
				doctos.put("tarjetaNSS"+nss.getNss()+".pdf",comprobanteQR);
				this.serviceBusiness.enviarCorreoNSS(e.getFisica(),
						solicitud.getNoFolioSolicitud(), false, solicitud.getSolicitudId(), doctos);
				
				request.setAttribute("id_solicitud", solicitud.getSolicitudIdHashed());
				session.setAttribute("idSolicitudReporte", solicitud.getSolicitudId());
				session.setAttribute("comprobanteNSS", comprobante);
				session.setAttribute("tarjetaNSS", comprobanteQR);
			} catch (SolicitudNoValidaException e1) {
				this.log.error("No se creo la solicitud de recuperacion de NSS y no se envia correo",
						e1);
			} catch (SolicitudException e1) {
				this.log.error("No se creo la solicitud de recuperacion de NSS y no se envia correo",
						e1);
			}			
			
			vista = "captura.libre";
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.info(e);
			tramiteAsegurado.setErrorFormGeneral("Mensaje: No se localizó información en RENAPO con la CURP capturada.");
			vista = "captura.libre";
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.info(e);
			if (e.getCodigo().equals(10005)) {
				tramiteAsegurado
						.setErrorFormGeneral("Mensaje: No se localizó información en RENAPO con la CURP <strong>(000000000000000000)</strong> que se localizó en el Insituto. Para poder realizar el trámite deberá presentarse en una subdelegación del Instituto.");
				request.setAttribute("mostrarInstruccionesVentanilla", true);
			} else {
				tramiteAsegurado.setErrorFormGeneral("Mensaje: El servicio de RENAPO no se encuentra disponible. Intente más tarde");
			}
			vista = "captura.libre";
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (UmfNoLocalizadaException e) {
			this.log.error(e);
			tramiteAsegurado.setErrorFormGeneral("No se encontraron UMF asociadas a su domicilio particular, para continuar con el trámite es necesario que acuda a una Subdelegación");			
			vista = "captura.libre";
		} catch (SolicitudNssCorreoException e) {
			this.log.info(e);
			tramiteAsegurado.setErrorFormGeneral(e.getMessage());
			vista = "captura.libre";
		} 

		// Subimos al request el objeto de Modelo
		model.addAttribute("tramiteAsegurado", tramiteAsegurado);
		
		return vista;
	}

	/* Del paso 2 hacia el paso 3 */
	@RequestMapping(value = "/crear", method = RequestMethod.POST)
	public String crearSolicitud(@RequestParam("hDP") String hashDatosPersona,
			@ModelAttribute TramiteAsegurado tramiteAsegurado,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
				
		this.log.debug("Iniciando la creacion de la solicitud de NSS externo");
		
		String solSessionKey = KEY_SOLICITUD + hashDatosPersona;
		String fisicaSessionKey = KEY_FISICA + hashDatosPersona;
		String nssCorreoSessionKey = NSS_CORREO + hashDatosPersona;
		
		Solicitud solicitud = (Solicitud) session.getAttribute(solSessionKey);
		String view = "";
		
		/*
		 * Se valida si la solicitud esta en sesión, de ser así se significa
		 * que ya existe una solicitud, por lo tanto se valida su estado
		 * y no se crea una nueva.
		 */
		if (solicitud != null && solicitud.getSolicitudId() != null) {
		
			Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
			String edoSolicitud = (String) res.get(EDO_SOLICITUD);
			request.setAttribute(EDO_SOLICITUD, edoSolicitud);
			
			if (edoSolicitud.equals("CANCELADA")) {
				String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
				if (StringUtils.isNotBlank(motivoCancelacion)) {
					request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
				}
			}
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudIdHashed());
			
			view = "concluir.solicitud";
						
		} else {
			Fisica fisica = (Fisica) session.getAttribute(fisicaSessionKey);
			this.log.debug("Persona fisica para crear solicitud asignacion -> "
					+ fisica);
			
			try {
				
				SolicitudNssCorreo nssCorreo = (SolicitudNssCorreo) session.getAttribute(nssCorreoSessionKey);
				
				// Creamos la solicitud.
				solicitud = this.serviceBusiness
						.crearSolicitudAsignacionNSS(fisica, TipoSerieEnum.ORDINARIA,
								OrigenSolicitudEnum.INTERNET, fisica.getCurp(), nssCorreo, null, ModuloOrigenAsignacionEnum.INTERNET);
				
				// Subimos a la sesion la solicitud
				this.log.debug("Solicitud recien creada que se sube a sesion: " + solicitud);
				session.setAttribute(solSessionKey, solicitud);
				
				model.addAttribute("folio", solicitud.getNoFolioSolicitud());
				model.addAttribute("tramiteAsegurado", solicitud.getTramites().get(0));
				
				view = "captura.complementos";
			} catch (SolicitudNoValidaException e) {
				this.log.error(e);
				tramiteAsegurado.setErrorFormGeneral(e.getMessage());
				view = "captura.libre";
				
			} catch (SolicitudException e) {
				this.log.error(e);
				tramiteAsegurado.setErrorFormGeneral(e.getMessage());
				view = "captura.libre";
				
			}	
		}

		model.addAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return view;

	}

	/* Del paso 3 hacia el paso 4 */
	@RequestMapping(value = "/concluir", method = RequestMethod.POST)
	public String concluirSolicitud(@RequestParam("hDP") String hashDatosPersona,
			@ModelAttribute TramiteAsegurado tramiteAsegurado,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		String vista = null;
		String mensaje = null;
		String curp = tramiteAsegurado.getFisica().getCurp();
		
		String solSessionKey = KEY_SOLICITUD + hashDatosPersona;
		
		Solicitud solicitud = (Solicitud) session.getAttribute(solSessionKey);
		
		this.log.debug("Solicitud por INTERNET de asignacion de NSS de la persona " + curp
				+ " a concluir en sesion -> " + solicitud);
		
		/*
		 * Se valida si la solicitud esta en sesión, de ser así se procede al
		 * procesamiento, en caso contrario, se manda un mensaje de error
		 */
		if (solicitud != null) {
						
			/*
			 * Si el usuario refresca la pantalla, se checa que la solicitud
			 * tenga id, en caso de tenerlo significa que ya fue creada y se
			 * busca el estado en el que esta y dependiendo del estado de la
			 * solicitud se ponen mensajes en la pantalla
			 */
			if (solicitud.getSolicitudId() != null) {
				Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
				String edoSolicitud = (String) res.get(EDO_SOLICITUD);
				request.setAttribute(EDO_SOLICITUD, edoSolicitud);
				
				if (edoSolicitud.equals("CANCELADA")) {
					String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
					if (StringUtils.isNotBlank(motivoCancelacion)) {
						request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
					}
				}
				
				request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
				request.setAttribute("idSolicitud", solicitud.getSolicitudIdHashed());
				
				model.addAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				
				return "concluir.solicitud";
			}
			
			// Se validan los datos requeridos
			new TramiteAseguradoDatosComplementariosValidator().validate(tramiteAsegurado, result);
					
			if (result.hasErrors()) {
				
				model.addAttribute("tramiteAsegurado", tramiteAsegurado);
				
				vista ="captura.complementos";
			} else {				
				try {
					UnidadMedicaFamiliar umf = tramiteAsegurado.getFisica().getUmf();
					
					this.log.debug("UMF seleccionada por persona " + curp + " -> " + umf);
					
					//se setea la persona que esta realizando el tramite con su CURP
					Usuario usuario = new Usuario();
					usuario.setUsuario(curp);
					solicitud.setSolicitante(usuario);
					
					solicitud.setFirmadaDigitalmente(false);
					
					Domicilio domicilio = tramiteAsegurado.getFisica().getDomicilios().get(0);
					// Se agrega el tipo de domicilio
					TipoDomicilio tipoDomicilio = new TipoDomicilio();
					tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
					domicilio.setDicTipoDomicilio(tipoDomicilio);
					
					this.log.debug("Domicilio seleccionado por persona " + curp + " -> " + domicilio);
					TramiteFisica  tramiteFisica = null;
					
					// Se pasa el objeto domicilio a los tramites de la solicitud en sesion
					List<Tramite> tramites = solicitud.getTramites();
					for (Tramite tramite : tramites) {
						if (tramite instanceof TramiteFisica) {
							this.log.debug("Se agrega domicilio a tramite de Alta de persona desde Asignación externo");
							 tramiteFisica = (TramiteFisica) tramite;

							 tramiteFisica.getFisica().getDomicilios().clear();
							 tramiteFisica.getFisica().getDomicilios()
									.add(domicilio);
						} else if (tramite instanceof TramiteAsegurado) {
							this.log.debug("Se agrega domicilio a tramite de asignacion NSS desde Asignación externo");
							TramiteAsegurado tramiteAseguradoSession = (TramiteAsegurado) tramite;

							tramiteAseguradoSession.getFisica().getDomicilios()
									.clear();
							tramiteAseguradoSession.getFisica().getDomicilios()
									.add(domicilio);
							
							tramiteAsegurado.getFisica().getDomicilios()
							.clear();
							tramiteAsegurado.getFisica().getDomicilios()
							.add(domicilio);
							
							this.log.debug("la serie es ************************ " + tramiteAseguradoSession.getAsignacionSerieNss().getSerie());
							tramiteAsegurado.setAsignacionSerieNss(tramiteAseguradoSession.getAsignacionSerieNss());
							tramiteAsegurado.setFisica(tramiteAseguradoSession.getFisica());
							tramiteAsegurado.getFisica().setUmf(umf);
							tramiteAsegurado.setNssCorreo(tramiteAseguradoSession.getNssCorreo());
							this.log.debug("la FISICA ES ************************ " +tramiteAsegurado.getFisica());
							this.log.debug("la UMF ES ************************ " +tramiteAsegurado.getFisica().getUmf());
							this.log.debug("los datos de nssCorreo son en session " + tramiteAseguradoSession.getNssCorreo().getCurp());
						//	this.log.debug("los datos de nssCorreo del tramite " + tramiteAsegurado.getNssCorreo().getCurp());
						}
					}
					// Se obtiene el origen de la solicitud
					OrigenSolicitud origenSolicitud = new OrigenSolicitud();
					
					origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
					solicitud.setOrigenSolicitud(origenSolicitud);
					
					
					// Se persiste la solicitud en la base de datos
					solicitud = this.serviceBusiness.guardarSolicitudAsignacionNSS(solicitud, umf, null);
					
					this.log.debug("Solicitud de asignación SIN FIEL a encolar -> "
							+ solicitud.getSolicitudId());
					
					// Se concluye la solicitud de asignación
//					this.serviceBusiness.encolarSolicitudAsignacionNSS(solicitud);
					
					//this.serviceBusiness.procesarSolicitudAsignacionNSS(solicitud.getSolicitudId());
					//se cambia en proceso para hacerlo ligero partiendo la consulta de la transaccion
					
					AsignacionSerieNSS asignacionSerie = null;
					AsignacionNSS fisica = null;
					
					String msgErrorValidacion = "La solicitud  "
								+ solicitud.getNoFolioSolicitud() + " no será procesada debido a que la persona a dar de alta ya cuenta con NSS";

					
					
					
					SolicitudNssCorreo nssCorreo = null;
					
					this.log.debug("La solicitud de asignacion con folio "
							+ solicitud.getNoFolioSolicitud()
							+ " esta por ser atendida" + origenSolicitud.getIdTipoSolicitud());
						
					/*
					 * Antes de comenzar a procesar la solicitud, es necesario
					 * checar los trámties dentro de la solicitud, si se encuentra
					 * uno de REGISTRO DE PERSONA, se debe checar si la persona ya existe
					 * con un NSS, en caso de cumplirse NO se debe procesar la solicitud
					 * recibida.
					 */
					if (tramiteFisica != null && tramiteFisica.getFisica() != null
							&& tramiteFisica.getTipoTramite() != null
							&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
						
						this.log.info("Se va a realizar la validación para confirmar el procesamiento de la solicitud de NSS "
								+ solicitud.getNoFolioSolicitud() + " ya que se encontró un trámite de REGISTRO DE PERSONA");
						
						boolean isCalificacionIMSS = false;
						Fisica fisicaNuevaValidar = tramiteFisica.getFisica();
						
						this.log.debug("Persona nueva para realizar validaciones -> " + fisicaNuevaValidar);
						
						List<PersonaCalificacion> calificaciones = fisicaNuevaValidar.getPersonaCalificaciones();
						
						if (calificaciones != null) {
							for (PersonaCalificacion calificacion : calificaciones) {
								if (calificacion.getCalificacion().getIdCalificacion()
										.intValue() == CalificacionPersona.VALIDADO_IMSS.intValue()) {
									isCalificacionIMSS = true;
								}
							}
						}
						
						if (fisicaNuevaValidar.getIdPersona() == null && isCalificacionIMSS) {
							/*
							 * Si la persona NUEVA cuenta con calificación IMSS significa que
							 * se esta dando de alta manualmente, por lo tanto, se debe procesar
							 * la solicitud de manera normal
							 */
							this.log.info("La persona encontrada tiene calificación IMSS, la solicitud "
								+ solicitud.getNoFolioSolicitud() + " va a ser procesada de manera normal");
						} else {
							/*
							 * Ya que se sabe que la persona no fue capturada manualmente, se garantiza que 
							 * la persona a dar de alta trae CURP, por lo tanto, la búsqueda de las
							 * personas con NSS se puede realizar sólo por CURP.
							 */
							List<Fisica> personasEncontradas = null;
							String curpNuevo = fisicaNuevaValidar.getCurp();

							this.log.info("La búsqueda para la validación se realiza a través de la CURP " + curpNuevo);
							
							personasEncontradas = this.personaBusinessRemote
									.buscarPersonaFisicaPorCurpEnImss(curpNuevo);
																
							if (personasEncontradas != null) {
								for (Fisica fisicaAux : personasEncontradas) {
									boolean tieneCalificacionRENAPO = calificacionesPersonaBusinessService
											.tieneCalificacionEspecifica(fisicaAux,
													CalificacionEnum.VALIDADO_RENAPO);
									
									if (StringUtils.isNotBlank(fisicaAux.getNss()) && tieneCalificacionRENAPO) {
										this.log.error(msgErrorValidacion + " -> " + fisicaAux);
										throw new SolicitudNoValidaException(msgErrorValidacion);
									} 
								}
							}
							
						}
					}
					if (tramiteAsegurado != null) {
						
						// Tramite para la generación y asignacion de NSS
						String curpRenapo = tramiteAsegurado.getCurpRENAPO();
						
						asignacionSerie = tramiteAsegurado.getAsignacionSerieNss();
						
						if (tramiteAsegurado.getNssCorreo() != null) {
							nssCorreo = tramiteAsegurado.getNssCorreo();
						}
						
						if (tramiteAsegurado.getFisica() != null) {
							fisica = tramiteAsegurado.getFisica();
							umf = fisica.getUmf();
							this.log.debug("****************** inicia modificacion de personas *****************  " + new Date() + " curpRenapo "
									+ curpRenapo +" y curp fisica " +  curp);
								// Se obtiene la persona esta registrada se actualiza la informacion
								if (StringUtils.isNotBlank(curp) && fisica != null && fisica.getIdPersona() != null) {
									
									Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness.
											localizarPersonaFisicaEnRENAPOxCURP(curp);
									fisicaRENAPO.setIdPersona(fisica.getIdPersona());
									fisicaRENAPO.setCveFisica(fisica.getCveFisica());

									AfectarDatosPersonaWrapper personaWrapper = new AfectarDatosPersonaWrapper();
									personaWrapper.setFisica(fisicaRENAPO);
									personaWrapper.setModificarCURP(true);
									personaWrapper.setModificarFechaNacimiento(true);
									//se actualizara el anio y mes de nacimiento, el segundo parametro indica que los datos se obtendran a partir de la fecha de nacimiento
									//y no del mes y anio que traigan la persona que en esta caso seria null porque la persona que se manda es la de renapo
									personaWrapper.actualizarAnioMesNacimiento(true, true);

									calificacionesPersonaBusinessService.calificarRENAPO(fisicaRENAPO);
									log.debug("Se actualizara a la persona con la curp: " + fisicaRENAPO.getCurp() + " y id: " + fisicaRENAPO.getIdPersona());
									personaBusinessRemote.afectarDatosPersona(personaWrapper);

									fisica.setCurp(fisicaRENAPO.getCurp());
									fisica.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
								}
							//}
							this.log.debug("****************** finaliza modificacion de personas *****************  " + new Date());
						}
						
						if (tramiteAsegurado.getIcaDatosRespuesta() != null) {
							this.log.debug("****************** inicia ICA de personas *****************  " + new Date());
							ICADatosRespuesta datosRespuesta = (ICADatosRespuesta) tramiteAsegurado
									.getIcaDatosRespuesta();
							TramiteCambioInformacionPersona infoPersona = new TramiteCambioInformacionPersona();
							infoPersona.setDatosICA(datosRespuesta);
							infoPersona.setTramiteId(tramiteAsegurado.getTramiteId());

							Modulo mod = new Modulo();
							mod.setIdModulo(ModuloEnum.ASIGNACION_NSS.getCodigo()
									.longValue());
							afectarDatosPersonaBusiness.afectarDatos(infoPersona, mod);
							this.log.debug("****************** finaliza ICA de personas *****************  " + new Date());
						}
						
					}
					this.log.debug("****************** inicia Calculo de NSS *****************  " + new Date());
					fisica.setNss(serieServiceBusiness.calculaNSS(tramiteAsegurado.getAsignacionSerieNss(), fisica));
					this.log.debug("****************** finaliza Calculo de NSS *****************  " + new Date());
					tramiteAsegurado.setFisica(fisica);
					
					this.log.debug("****************** inicia IMPACTO y registro de NSS y concluir Solicitud *****************  " + new Date());
					solicitud = serviceBusiness.procesarSolicitudAsignacionNSSLigero(solicitud, tramiteFisica, tramiteAsegurado);
					
					Map<String, byte[]> doctos = new HashMap<String, byte[]>();
					try {
						byte[] comprobanteQR = solicitudBusinessRemote.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue());
						byte[] comprobante = solicitudBusinessRemote.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue());
						
						if(comprobante == null) {
							doctos.put("comprobante", comprobante);
						}
						
						if(comprobanteQR == null ) {
							doctos.put("comprobanteQR", comprobanteQR);
						}
						
						session.setAttribute("idSolicitudReporte", solicitud.getSolicitudId());
						session.setAttribute("comprobanteNSS", comprobante);
						session.setAttribute("tarjetaNSS", comprobanteQR);
					} catch(Exception e) {
						log.error("Ocurrio un error al generar los documentos de asignacion ", e);
						e.printStackTrace();
					}
					
					this.log.debug("****************** finaliza IMPACTO y registro de NSS y concluir Solicitud *****************  " + new Date());
					
					try{
						/*
						 * Se ejecuta la operación del NSS-Correo y se envía el correo con el
						 * NSS asignado sólo cuando el origen es INTERNET
						 */
						
							serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);	
					
						
						/*
						 * Se envia el correo con el NSS asignado siempre y cuando se tenga
						 * correo capturado, para el caso de asignacion externa siempre se debe
						 * de cumplir la condicion ya que el correo es requerido, para
						 * ventanilla puede no venir
						 */
						if (fisica.getCorreoElectronico() != null
								&& StringUtils.isNotBlank(fisica.getCorreoElectronico().getCorreo())) {
								serviceBusiness.enviarCorreoNSS(fisica, solicitud.getNoFolioSolicitud(), true, solicitud.getSolicitudId(), doctos);
						}
						}catch(Exception e){
						log.error("ocurrio un error al generar la bitacora o el envio de correo ", e);
					}

						
/**************************  FIN nueva seccion *********************************/
					
					
					
				} catch (SolicitudNoValidaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (SolicitudNoEncontradaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (TramiteNoEncontradoException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
			/*	} catch (SolicitudException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud); 	*/
				} catch (AfectacionDatosPersonaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (PersonaNoEncontradaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
					
				} /*catch (RegistroPersonaFisicaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
					
				}*/catch (NivelDeAsignacionSerieIndefinidoException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (SeriesNoLocalizadasException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ErrorAlActivarSerieException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (DomicilioNoValidoException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (CURPNoLocalizadoEnEntidadExternaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ClienteWebserviceRenapoCurpException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (NSSYaExistenteException e) {
					this.log.error(e);
					mensaje = e.getMessage(); 
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (SerieNssAgotadaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				}
				
				model.addAttribute("mensaje", mensaje);
				session.setAttribute(solSessionKey, solicitud);
				
				request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
				request.setAttribute("idSolicitud", solicitud.getSolicitudIdHashed());
				
				vista = "concluir.solicitud";
			}
		} else {
			mensaje = "Ocurrió un error inesperado, favor de reintentar.";
			this.log.error(mensaje);
			model.addAttribute("HUBO_ERROR", true);
			vista = "concluir.solicitud";
			request.setAttribute("mensaje", mensaje);
		}
		
		model.addAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return vista;

	}
	
	/**
	 * URL auxiliar para checar el estado de la solicitud cuando el COMET tarde
	 * más de la cuenta.
	 * 
	 */
	@RequestMapping(value = "/concluir", method = RequestMethod.GET)
	public String checarEstadoSolicitud(Model model, HttpSession session,
			HttpServletRequest request, @RequestParam("iS") String idSolicitud,
			@RequestParam("hDP") String hashDatosPersona) {
				
		String vista = null;
		String mensaje = null;

		Long solicitudId = null;
		try {
			solicitudId = Long.valueOf(Base64Cipher.descrifrar(idSolicitud));
		} catch (NumberFormatException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		} catch (IOException e) {
			this.log.error(e);
		}
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(solicitudId);
		
		this.log.debug("Se va a checar el estado de la solicitud de asignacion en sesion -> "
				+ solicitud);
		
		/*
		 * Se valida si la solicitud esta en sesión y que tenga id, de ser así
		 * se procede a checar el estado, en caso contrario, se manda un mensaje
		 * de error ya que significa que el usuario ingreso la URL directo
		 */
		if (solicitud != null && solicitud.getSolicitudId() != null) {
			Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
			String edoSolicitud = (String) res.get(EDO_SOLICITUD);
			solicitud = (Solicitud) res.get(SOL_CHECK_STATUS);
			
			this.log.debug("Estado de la solicitud -> " + edoSolicitud);
			request.setAttribute(EDO_SOLICITUD, edoSolicitud);
			
			if (edoSolicitud.equals("CANCELADA")) {
				String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
				if (StringUtils.isNotBlank(motivoCancelacion)) {
					request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
				}
			}
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", idSolicitud);
						
			vista = "concluir.solicitud";
		} else {
			this.log.warn("No se encontro solicitud en sesion para checar su estatus, se procede a poner mensaje de error.");
			mensaje = "Ocurrió un error inesperado, favor de reintentar.";
			model.addAttribute("HUBO_ERROR", true);
			vista = "concluir.solicitud";
			request.setAttribute("mensaje", mensaje);
		}
		
		model.addAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return vista;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody String limpiarDatosSession(HttpSession session,
			@RequestParam("hDP") String hashDatosPersona) {
		
		session.removeAttribute(KEY_SOLICITUD + hashDatosPersona);
		session.removeAttribute(KEY_FISICA + hashDatosPersona);
		session.removeAttribute(NSS_CORREO + hashDatosPersona);
		session.removeAttribute(IS_DOMICILIO_SESSION_KEY + hashDatosPersona);
		session.removeAttribute("captcha");
	
		return "";
	}
	
	/**
	 * Se pasan los documentos probatorios a los atributos
	 * específicos para poder mostrarlos en la vista
	 */
	private void pasarDocumentosProbatorios(Fisica fisica) {
		
		if (fisica.getDocumentosProbatorios() != null && !fisica.getDocumentosProbatorios().isEmpty()) {
			for (DocumentoProbatorio doc : fisica.getDocumentosProbatorios()) {
				Long idTipoDocumento = doc.getDocumentoPorTipo().getIdDocumentoPorTipo();
				
				this.log.debug("Tipo documento probatorio -> " + idTipoDocumento);
				
				if (idTipoDocumento.equals(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId())) {
					fisica.setActaNacimientoAux((Nacimiento) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId())) {
					fisica.setCartaNaturalizacionAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId())) {
					fisica.setDocumentoMigratorioAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId())) {
					fisica.setNumeroUnicoExtranjeroAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId())) {
					fisica.setCertificadoNacionalidadMexicanaAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId())) {
					fisica.setOficioSolicitanteRefugiadoAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId())) {
					fisica.setFormaMigratoriaTuristaAux((CURP) doc);
				}
			}
		}
	}
	
	private Map<String, Object> validarSolicitudEnSesion(Solicitud solicitud) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solAux = this.solicitudBusinessRemote.obtenerEstados(solicitud);
		int cveEdoSolicitud = solAux.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
		String edoSolicitud = null;
						
		if (cveEdoSolicitud == EstadoSolicitudEnum.ATENDIDA
				.getCodigo().intValue()) {
			
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " ya fue atendida");

			edoSolicitud = "ATENDIDA";
						
		} else if (cveEdoSolicitud == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().intValue()) {
				
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " esta en proceso");

			edoSolicitud = "EN_PROCESO";

		} else if (cveEdoSolicitud == EstadoSolicitudEnum.CANCELADA
				.getCodigo().intValue()) {
	
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " fue cancelada");
			
			edoSolicitud = "CANCELADA";
		}
		
		result.put(SOL_CHECK_STATUS, solAux);
		result.put(EDO_SOLICITUD, edoSolicitud);
		
		return result;
	}
	
	private String obtenerMotivoCancelacion(Solicitud solicitud) {
		
		this.log.debug("Se busca el motivo de cancelación de la solicitud "
				+ solicitud.getNoFolioSolicitud());
		
		String motivoCancelacion = null;
		
//		try {
//			Solicitud solAux = this.solicitudBusinessRemote.consultarFolioSinDatosTramite(solicitud);
//			if (StringUtils.isNotBlank(solAux.getObservacion())) {
//				motivoCancelacion = solAux.getObservacion();
//			}
//		} catch (SolicitudNoEncontradaException e) {
//			this.log.error(e);
//		}
		
		return motivoCancelacion;
	}
	
	private void limpiarDoctosSession(HttpSession session) {
		session.removeAttribute("idSolicitudReporte");
		session.removeAttribute("comprobanteNSS");
		session.removeAttribute("tarjetaNSS");
	}
}
