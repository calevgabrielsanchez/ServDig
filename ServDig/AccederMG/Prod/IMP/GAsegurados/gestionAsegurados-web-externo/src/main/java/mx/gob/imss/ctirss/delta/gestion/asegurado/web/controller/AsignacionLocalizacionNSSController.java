package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.Cifrar;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.EnvioCorreoUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.RegistroAseguradoDatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/asignacionNSS")
public class AsignacionLocalizacionNSSController extends AbstractController {

	//Vistas utilizadas
	private final String VIEW_INICIAL_ASIGNACION = "loginAsignacionNSS";
	private final String VIEW_DOMICILIO_ASIGNACION = "capturaDomicilioNSS";
	private final String VIEW_FINALIZA_ASIGNACION = "asignacionFinalizada";
	//redirects utilizados
	private final String REDIRECT_INICIO = "/asignacionNSS/";
	private final String REDIRECT_DOMICILIO = "/asignacionNSS/capturaDomicilio";
	private final String REDIRECT_FINALIZADA = "/asignacionNSS/solicitudFinalizada";
	
	
	//EJBS usados por la aplicacion
	@Autowired
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	@Autowired
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	@Autowired
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@Autowired
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	@Autowired
	private SerieServiceBusinessRemote serieServiceBusiness;
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	@Autowired
    private EnvioCorreoUtils correoUtils;

	//KEYs en session
	private static final String KEY_FISICA_ERRORES_NEG = "fisicaErroresNegocioSession";
	private static final String IS_DOMICILIO_SESSION_KEY = "IS_DOMICILIO";
	private static final String KEY_SOLICITUD = "solicitudNssExterno";
	private static final String KEY_TRAMITE_ASEGURADO = "tramiteAseguradoSession";
	private static final String KEY_IS_RECUPERACION = "NSS_RECUPERADO";
	private static final String KEY_COMPROBANTE= "comprobanteNSS";
	private static final String KEY_TARJETA = "tarjetaNSS";
	private static final String KEY_NUMERO_REENVIOS="numeroEnviosSession";
	
	private static final String EDO_SOLICITUD = "EDO_SOLICITUD";
	private static final String MOTIVO_CANCELACION_SOLICITUD = "MOTIVO_CANCELACION_SOLICITUD";
	private static final String HASH_CODE_DATOS_BASICOS = "hashDatosPersona";
	private static final String SOL_CHECK_STATUS = "SOL_AUX";
	
	private final String KEY_VALIDACIONES = "keyValidacionesNSSCorrectas";
	private final String KEY_UMF_ASIGNACDA ="keyUmfAsignadaAsignacionNSS";
	
	//mensajes del sistema
	private final String MENSAJE_SUBDELEGACION = "Tu solicitud requiere que acudas a la subdelegaci&oacute;n.<br><a href=\"http://www.imss.gob.mx/tramites/imss02008\" target=\"_blank\" id=\"requisitos\">Consulta los requisitos</a>";
	private final String MENSAJE_FALLA_TECNICA = "Debido a una falla t&eacute;cnica, no se pudo verificar tu CURP ante el Registro Nacional de Poblaci&oacute;n. En este momento "
			+ "no podemos continuar con tu solicitud, por lo que te pedimos intentar m&aacute;s tarde.";
	@RequestMapping(value="")
	public String inicioAsignacionLocalizacionNSS(Model model, HttpSession session) {
		
		//Fisica fisica
		Fisica fisica = (Fisica) session.getAttribute(KEY_FISICA_ERRORES_NEG);
		//limpiamos la session
		limpiarSession(session);
		this.setFechaSistema(session);
		//agregamos el objeto al modelo para el formulario
		model.addAttribute("fisica", fisica != null ? fisica : new Fisica());
		session.setAttribute(KEY_VALIDACIONES, false);
		//retornamos la vista inicial
		return VIEW_INICIAL_ASIGNACION;
	}
	
	@RequestMapping(value = "/capturaDomicilio")
	public Object capturaDomicilioAsignacion(Model model) {
//		
//		TramiteAsegurado tramite = (TramiteAsegurado) session.getAttribute(KEY_TRAMITE_ASEGURADO);
//		//si no existe el tramite de asegurao probablemente se capturo la url manualmente y no viene del formulario
//		//por lo que se redirige a la pantalla inicial del tramite
//		if(tramite == null) {
//			return new RedirectView(REDIRECT_INICIO,true);
//		}
//		
		//mandamos un domicilio nuevo
		model.addAttribute("domicilio", new Domicilio());
		//retornamos la vista de domicilio
		return VIEW_DOMICILIO_ASIGNACION;
	}
	
	@RequestMapping("/solicitudFinalizada")
	public Object solicitudAsignacionFinalizada(HttpSession session) {
		
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//si no existe la solicitud en session se redirecciona a la primera pantalla, debido
		//a que tal vez la url se esta metiendo directamente sin pasar por el formulario
		if(solicitud == null) {
			return new RedirectView(REDIRECT_INICIO,true);
		}
		
		return VIEW_FINALIZA_ASIGNACION;
	}
	
	@RequestMapping(value = "/getComprobante", method = RequestMethod.POST)
	public void getComprobanteAsignacion(HttpSession session, HttpServletResponse response) {
		//obtenemos el comprobante de vigencia
		prepararReporte(false, session, response,false);
	}
	

	@RequestMapping(value = "/getTarjetaNSS", method = RequestMethod.POST)
	public void getTarjetaAsignacion(HttpSession session, HttpServletResponse response) {
		//obtenemos la tarjeta del NSS
		prepararReporte(true, session, response,false);
	}
	
	@RequestMapping(value = "/downloadComprobante", method = RequestMethod.POST)
	public void downloadComprobanteAsignacion(HttpSession session, HttpServletResponse response) {
		//obtenemos el comprobante de vigencia
		prepararReporte(false, session, response,true);
	}
	

	@RequestMapping(value = "/downloadTarjetaNSS", method = RequestMethod.POST)
	public void downloadTarjetaAsignacion(HttpSession session, HttpServletResponse response) {
		//obtenemos la tarjeta del NSS
		prepararReporte(true, session, response,true);
	}
	
	@RequestMapping("/salir")
	public Object salirTramite(HttpSession session, HttpServletRequest request) {
		//limpiamos la session
		limpiarSession(session);
		//nos redirigimos a la pantalla inicial del tramite
		return new RedirectView(REDIRECT_INICIO, true);
	}
	
	@RequestMapping(value="/reenvioMail", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> reenviarMail(HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, byte[]> doctos = new HashMap<String, byte[]>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) session.getAttribute(KEY_TRAMITE_ASEGURADO);
		boolean reenviar = false;
		Integer numeroIntentos = (Integer) session.getAttribute(KEY_NUMERO_REENVIOS);
		
		if(numeroIntentos == null || numeroIntentos <= 2) {
			//s epone el contador en 0 en caso de no existir el valor, ya que mas adelante se aumenta el valor
			numeroIntentos = numeroIntentos == null ? 0 : numeroIntentos;
			reenviar = true;
		}
		
		if(reenviar) {
			doctos.put("comprobante.pdf", (byte[]) session.getAttribute(KEY_COMPROBANTE));
			doctos.put("tarjetaNSS.pdf", (byte[]) session.getAttribute(KEY_TARJETA));
			//Envío de correo
			enviarMail(solicitud, tramiteAsegurado.getFisica(), doctos);
			session.setAttribute(KEY_NUMERO_REENVIOS, numeroIntentos + 1);
		}
		
		result.put("mensaje", "El comprobante ha sido reenviado");
		
		return result;
	}


	@RequestMapping(value = "/valida", method = RequestMethod.POST)
	public Object validacionesPersona(@ModelAttribute Fisica fisica,
			BindingResult result, Model model,
			@RequestParam("captcha") String captcha,
			HttpSession session, HttpServletRequest request) {


		SolicitudNssCorreo nssCorreo;
		String correoCapturado;
		String correoConfirmacion;


				// Se pasa a minúsculas el correo capturado y el de confirmación
				correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
				fisica.getCorreoElectronico().setCorreo(correoCapturado);
				correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
				fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

				this.log.debug("CURP capturado -> " + fisica.getCurp() + "\nCorreo electrónico capturado -> " + correoCapturado
						+"\nCorreo electrónico confirmacion -> " + correoConfirmacion + "\nCaptcha capturado -> " + captcha +
						 " id -> " + session.getId());
				//se valida la captua de datos
				new RegistroAseguradoDatosBasicosValidator().validate(fisica, result);

				//validamos que el captch se haya capturado
				if (StringUtils.isBlank(captcha)) {
					FieldError fieldError = new FieldError("fisica","errorFormGeneral","Este campo es obligatorio");
			        result.addError(fieldError);
			    //si se capturo el captcha validamos que sea el mismo que el de la sesion
				} else if (!captcha.equals(session.getAttribute("captcha"))) {
					FieldError fieldError = new FieldError("fisica","errorFormGeneral","El captcha no fue v\u00E1lido, favor de intentar nuevamente");
			        result.addError(fieldError);
		        }
				//si el formulario tiene errores se mandan a pantalla
				if (result.hasErrors()) {
					this.log.warn("Existen errores de captura");
					return VIEW_INICIAL_ASIGNACION;
				}


				try {

					//Se valida que el correo no esta asociado a otra curp y que este dentro del numero de intentos por dia
					nssCorreo = new SolicitudNssCorreo();
					nssCorreo.setCorreo(fisica.getCorreoElectronico());
					nssCorreo.setCurp(fisica.getCurp());
					nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue() );
					 this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);

				} catch (SolicitudNssCorreoException e) {
					log.warn("Error solicitud nss correo no valida", e);
					fisica.setErrorFormGeneral(e.getMessage());
					// Subimos al request el objeto de Modelo
					session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			        return VIEW_INICIAL_ASIGNACION;

				}

		//Se agrega confirmacion vía correo electronico
		CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
		correoElectronicoDTO.setCorreoPara(new String[1]);
		correoElectronicoDTO.getCorreoPara()[0] = fisica.getCorreoElectronico().getCorreo();
		correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoUtils.ASUNTO_CONFIRMACION_CORREO_ASIGNACION));
		String tokenUrl = Cifrar.generarPassword();
		int port = request.getServerPort();
		String URL = request.getScheme() + "://" + request.getServerName() + ((port == 80) ? "" : ":" + port) + request.getContextPath()
				+ EnvioCorreoUtils.URL_CONFIRMACION_CORREO_ASIGNACION + "?token=" + tokenUrl + "&curp=" + fisica.getCurp() + "&correo=" + fisica
				.getCorreoElectronico().getCorreo();
		log.debug("---URL CONFIRMACION CORREO---: " + URL);
		correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoConfirmacionAsignacion(URL));
		log.debug("---CDA--- Contenido Correo Derechohabiente: " + (correoElectronicoDTO.getCuerpoCorreo()));

        try {
		            envioCorreoElectronicoBusinessRemote.enviarCorreo(
		                    correoElectronicoDTO,
		                    EnvioCorreoUtils.MAIL_PROPERTIES_ADRESS);

		          } catch (Exception x) {
		            log.debug("---CDA--- Error al enviar correo ", x);
		          }

		        try {
					SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
							.recuperarConfirmacion(fisica.getCurp(), TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());
					String decifrar = Cifrar.descifrar(tokenUrl);
					System.out.print(decifrar);
					if(modelo != null){

						this.solicitudNssCorreoServiceBusiness
								.actualizaConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
										TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());
					}
					else{

						this.solicitudNssCorreoServiceBusiness
								.guardarConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
										TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());
					}
				} catch (TransformacionException e) {
					log.warn("Servicio no disponible", e);
					fisica.setErrorFormGeneral(e.getMessage());
					// Subimos al request el objeto de Modelo
					session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			        return VIEW_INICIAL_ASIGNACION;
				} catch (SolicitudNssCorreoException e) {
					log.warn("Servicio no disponible", e);
					fisica.setErrorFormGeneral(e.getMessage());
					// Subimos al request el objeto de Modelo
					session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			        return VIEW_INICIAL_ASIGNACION;
				}

		        request.setAttribute("exito", "Para continuar con su tr\u00e1mite le hemos enviado una liga de confirmaci\u00f3n a su correo electr\u00f3nico");

		        return VIEW_INICIAL_ASIGNACION;


		}

	@RequestMapping(value="/confirmacionUrl")
	public Object inicioAsignacionLocalizacionNSS(Model model, HttpSession session, HttpServletRequest request) {

		limpiarSession(session);
		this.setFechaSistema(session);

		session.setAttribute(KEY_VALIDACIONES, false);

		RedirectView redirectError;
		Integer codigoRespuesta;
		SolicitudNssCorreo nssCorreo;
		Fisica fisicaEncontrada;
		String correoCapturado = null;
		String token = null;
		//Se solicita al usuario la confirmacion de su correo electronico para poder continuar con el tramite

		// Se pasan los paramentros del request al obtener la respuesta desde el correo
		Fisica	fisica = new Fisica();
		if(request.getParameter("correo") != null){
			correoCapturado = request.getParameter("correo");
			correoCapturado = correoCapturado.toLowerCase();
			fisica.setCorreoElectronico(new CorreoElectronico());
			fisica.getCorreoElectronico().setCorreo(correoCapturado);
			fisica.setCorreoElectronicoFiscal(new CorreoElectronico());
			fisica.getCorreoElectronicoFiscal().setCorreo(correoCapturado);

		}

		if(request.getParameter("curp") != null){
			fisica.setCurp (request.getParameter("curp"));
		}

		if(request.getParameter("token") != null){
			token = request.getParameter("token");
			token = token.replace(" ", "+");
			log.debug("token cifrado recibido: " + token);
		}

		this.log.debug("CURP capturado desde correo -> " + fisica.getCurp() + "\nCorreo electrónico capturado -> " + correoCapturado
				+ " id -> " + session.getId());
		//se valida la captua de datos
		//new RegistroAseguradoDatosBasicosValidator().validate(fisica, result);

		//validar token de la url
		String decifrar = Cifrar.descifrar(token);

		/*
		 * Si las validaciones de datos requeridos y del captcha fueron
		 * exitosas, se elimina el correoFiscal, que en este caso representa la
		 * confirmación del correo electrónico. Se quita para que no se guarde
		 * en la solicitud y se tenga duplicado el correo.
		 */
		fisica.setCorreoElectronicoFiscal(null);

		nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue() );

		try {
			SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
					.recuperarConfirmacion(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(), TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());

			if(modelo == null){

				return procesarErrorEnPantallaInicio("Confirmaci\u00F3n inv\u00E1lida", session);
			}
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			if(!calendar.getTime().equals(modelo.getFechaTokenActualizacion())) {

				return procesarErrorEnPantallaInicio("Token expirado", session);

			}

			if(modelo.getVigente() != 0){

				return procesarErrorEnPantallaInicio("Este token ya fue utilizado", session);
			}

			if(!modelo.getToken().equals(decifrar)){

				return procesarErrorEnPantallaInicio("Token invalido", session);
			}

			// Se valida la consulta/registro de NSS
			codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);
			this.solicitudNssCorreoServiceBusiness.actualizaConfirmacionVigencia(modelo);
			try {
				fisicaEncontrada = this.serviceBusiness.validacionesNSSIncluyeCL3(fisica, true);
			} catch (DomicilioNoLocalizadoException e) {
				this.log.info(e);
				fisicaEncontrada = e.getFisica();
				session.setAttribute(IS_DOMICILIO_SESSION_KEY + fisicaEncontrada.hashCodeDatosBasicos(), false);
			}

			this.log.debug("Persona encontrada -> [id:"+ fisicaEncontrada.getIdPersona() + ", curp:"+ fisicaEncontrada.getCurp() + "]");

			//validamos si se tiene una solicitud registrada o en proceso de asignacion de NSS
			redirectError = validarSolicitudExistente(fisicaEncontrada, fisica, session);
			//En caso de que el objeto venga diferente de nulo quiere decir que nos tenemos que redirigir a otro lado
			if(redirectError != null) {
				return redirectError;
			}

			/*
			 * Para evitar que los medios de contacto (si es que existen), se
			 * tomen en cuenta en el proceso, se limpia y después se settea el
			 * correo electrónico capturado
			 */
			fisicaEncontrada.getMediosContacto().clear();
			fisicaEncontrada.setCorreoElectronico(fisica.getCorreoElectronico());

			model.addAttribute(HASH_CODE_DATOS_BASICOS, fisicaEncontrada.hashCodeDatosBasicos());

			//Creamos el objeto de solicitud de asignacion de NSS sin persistirla en BD
			redirectError = crearSolicitud(session, request, fisicaEncontrada, nssCorreo);
			//si hubo algun error el redirect es diferente de null, en ese cao nos vamos a la pantalla que indique
			if(redirectError != null) {
				return redirectError;
			}
		}  catch (PersonaConNSSException e) {
			return procesarPersonaConNSS(session, request, fisica, e, nssCorreo);
		} catch (Exception e){
			return procesarExcepcionesBusquedaPersona(e, fisica, session);
		}

		//Una vez que realizamos las validaciones redireccionamos a la pantalla de captura de domicilio para que
		//si da F5 no se vuelvan a hacer las peticiones
		return new RedirectView(REDIRECT_DOMICILIO, true);
	}

	@RequestMapping("/validaDomicilio")
	public @ResponseBody Map<String, Object> obtenerUMF(@RequestBody Domicilio domicilio, HttpSession session) {
		Map<String, Object> resultado = new HashMap<String, Object>();
		//Validacion de corresponcencia
		Boolean tieneUMF= false;
		//Mensaje
		String mensaje = "OK";
		//se buscara la umf que corresponda con el codigo postal
		UnidadMedicaFamiliar umfAsignada = null;
		
		String codigoPostal = domicilio.getCodigoPostal().getCodigoPostal();
		//Se obtiene la umf asignada
		umfAsignada = this.getUMFAsignacion(codigoPostal);
		
		if(umfAsignada != null && umfAsignada.getIdUMF() != null && !umfAsignada.getIdUMF().equals(0L)) {
			tieneUMF = true;
			session.setAttribute(KEY_UMF_ASIGNACDA, umfAsignada);
		} else {
			mensaje = "El C&oacute;digo Postal " + codigoPostal + " no se encuentra relacionado a ninguna Unidad M&eacute;dica Familiar (UMF)";
		}
		
		session.setAttribute(KEY_VALIDACIONES, tieneUMF);
		resultado.put("resultado", tieneUMF);
		resultado.put("mensaje", mensaje);
		resultado.put("umf", umfAsignada);
		
		return resultado;
	}
	
	@RequestMapping(value="/finalizar", method = RequestMethod.POST)
	public Object finalizarSolicitudAsignacion(@ModelAttribute Domicilio domicilio,
			HttpSession session, HttpServletRequest request) {
		
		//Recuperamos la solicitud de session
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//se buscara la umf que corresponda con el codigo postal
		UnidadMedicaFamiliar umfAsignada = (UnidadMedicaFamiliar) session.getAttribute(KEY_UMF_ASIGNACDA);
		//Perosna que contendra todos los datos
		AsignacionNSS fisica= null;
		//el tramite de registro de personas
		TramiteFisica tramiteFisica = null;
		//el tramite de asignacion de NSS
		TramiteAsegurado tramiteAseguradoSession = null;
		SolicitudNssCorreo nssCorreo = null;
		//mapa que contendra los documentos de la asignacion de NSS
		Map<String, byte[]> doctos = null;
		
		try {
			// Se pasa el objeto domicilio a los tramites de la solicitud en sesion
			List<Tramite> tramites = solicitud.getTramites();
			List<Tramite> tramitesAcutualizados = new ArrayList<Tramite>();
			List<Domicilio> domicilios = new ArrayList<Domicilio>();
			domicilios.add(domicilio);
			for (Tramite tramite : tramites) {
				if (tramite instanceof TramiteFisica) {
					 this.log.debug("Se agrega domicilio a tramite de Alta de persona desde Asignación externo");
					 tramiteFisica = (TramiteFisica) tramite;
					 tramiteFisica.getFisica().setDomicilios(domicilios);
				} else if (tramite instanceof TramiteAsegurado) {
					this.log.debug("Se agrega domicilio a tramite de asignacion NSS desde Asignación externo");
					tramiteAseguradoSession = (TramiteAsegurado) tramite;
					tramiteAseguradoSession.getFisica().setDomicilios(domicilios);
				}
			}
			
			//Si por alguna razon la umf Asignada 
			if(umfAsignada == null || umfAsignada.getIdUMF() == null) {
				String codigoPostal = domicilio.getCodigoPostal().getCodigoPostal();
				umfAsignada = this.getUMFAsignacion(domicilio.getCodigoPostal().getCodigoPostal());
				if(umfAsignada == null || umfAsignada.getIdUMF() == null) {
					throw new SolicitudNoValidaException("El C&oacute;digo Postal " + codigoPostal +
							" no se encuentra relacionado a ninguna Unidad M&eacute;dica Familiar (UMF)");
				}
			}
			//SE agrega la umf ubica por el codigo postal
			tramiteAseguradoSession.getFisica().setUmf(umfAsignada);
			
			//Agregamos los nuevos detalles a la solicitud
			tramitesAcutualizados.add(tramiteAseguradoSession);
			if(tramiteFisica != null) {
				tramitesAcutualizados.add(tramiteFisica);
			}
			solicitud.setTramites(tramitesAcutualizados);
			
			// Se obtiene el origen de la solicitud
			OrigenSolicitud origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
			solicitud.setOrigenSolicitud(origenSolicitud);
			
			// Se persiste la solicitud en la base de datos
			solicitud = this.serviceBusiness.guardarSolicitudAsignacionNSS(solicitud, umfAsignada, null);
			
			validarPersonaConNSS(tramiteFisica, solicitud.getNoFolioSolicitud());
			
			fisica = validaActualizacionDatos(tramiteAseguradoSession);
			
			if (tramiteAseguradoSession.getNssCorreo() != null) {
				nssCorreo = tramiteAseguradoSession.getNssCorreo();
			}
			
			this.log.debug("****************** inicia Calculo de NSS *****************  " + new Date());
			fisica.setNss(serieServiceBusiness.calculaNSS(tramiteAseguradoSession.getAsignacionSerieNss(), fisica));
			this.log.debug("****************** finaliza Calculo de NSS *****************  " + new Date());
			tramiteAseguradoSession.setFisica(fisica);
			
			this.log.debug("****************** inicia IMPACTO y registro de NSS y concluir Solicitud *****************  " + new Date());
			solicitud = serviceBusiness.procesarSolicitudAsignacionNSSLigero(solicitud, tramiteFisica, tramiteAseguradoSession);
			//Se pone la fecha de conclusion
			solicitud.setFechaConclusion(new Date());
			//Se setea el ultimo tramite de asignacion para que tenga todos los elementos, ya con el nss
			session.setAttribute(KEY_TRAMITE_ASEGURADO, tramiteAseguradoSession);
			//se setea la solicitud en session, ya questa es la que ya contiene los numeros de folio y toda la informacion actualizada
			session.setAttribute(KEY_SOLICITUD, solicitud);
			//generamos los documentos de la asignacion de NSS
			doctos = generarDocumentosAsignacion(solicitud,session);
			
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
				log.debug("se enviara correo de asignacion para el NSS: " + tramiteAseguradoSession.getFisica().getNss());
				enviarMail(solicitud, fisica, doctos);
			}catch(Exception e){
				log.error("ocurrio un error al generar la bitacora o el envio de correo ", e);
			}
		} catch (Exception e) {
			return procesarExcepcionesFinalizadoAsignacion(e, solicitud, session);
		}
		
		session.setAttribute(KEY_IS_RECUPERACION, false);
		return new RedirectView(REDIRECT_FINALIZADA, true);
	}
	
	/**
	 * Metodo que envia mail con los documentos de l tramite
	 * @param solicitud
	 * @param fisica
	 * @param doctos
	 */
	private void enviarMail(Solicitud solicitud, AsignacionNSS fisica, Map<String, byte[]> doctos) {
		log.debug("Envio mail");
		if (fisica.getCorreoElectronico() != null
				&& StringUtils.isNotBlank(fisica.getCorreoElectronico().getCorreo())) {
				log.debug("envio mail a la direccion " + fisica.getCorreoElectronico().getCorreo());
			try {
				serviceBusiness.enviarCorreoNSS(fisica, solicitud.getNoFolioSolicitud(), true, solicitud.getSolicitudId(), doctos);
			} catch(Exception e) {
				log.error("No pudo mandarse el correo para el nss " + fisica.getNss(), e);
			}
		}
	}
	
	/**
	 * Metodo para crear la solicitud de asignacion de nss, en caso de crear el tramite correctamente retornara null
	 * y en caso de haber algun problema se retorna un redirect view, que se va a la pantalla inicial
	 * para mostrar el error correspondiente
	 * @param session
	 * @param request
	 * @param fisica
	 * @param nssCorreo
	 * @return
	 */
	private RedirectView crearSolicitud(HttpSession session, HttpServletRequest request, Fisica fisica, SolicitudNssCorreo nssCorreo) {
				
		this.log.debug("Iniciando la creacion de la solicitud de NSS externo");
		String mensajeError = null;
		Solicitud solicitud = null;

		try {
			// Creamos la solicitud.
			solicitud = this.serviceBusiness.crearSolicitudAsignacionNSS(fisica, TipoSerieEnum.ORDINARIA,
							OrigenSolicitudEnum.INTERNET, fisica.getCurp(), nssCorreo, null, ModuloOrigenAsignacionEnum.INTERNET);

			// Subimos a la sesion la solicitud
			this.log.debug("Solicitud recien creada que se sube a sesion: " + solicitud);
			session.setAttribute(KEY_SOLICITUD, solicitud);
			session.setAttribute(KEY_TRAMITE_ASEGURADO, solicitud.getTramites().get(0));
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			mensajeError = e.getMessage();
		} catch (SolicitudException e) {
			this.log.error(e);
			mensajeError = e.getMessage();

		}	
		
		if(mensajeError != null) {
			return this.procesarErrorEnPantallaInicio(mensajeError, session);
		}
		
		return null;

	}
	
	/**
	 * Metodo para validar si existen solicitudes abiertas o en proceso de asigancion de NSS
	 * @param fisicaEncontrada
	 * @return RedirectView
	 */
	private RedirectView validarSolicitudExistente(Fisica fisicaEncontrada, Fisica fisicaFormulario, HttpSession session) {
		String mensajeError = null;
		//validamos que la persona cuente con una solicitud siempre y cuando cuente con un id de persona
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
				solicitudActiva = this.serviceBusiness.obtenerSolicitudRegistrada(fisicaEncontrada.getIdPersona());
			
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolRegistrada = true;
				} else {
					solicitudActiva = this.serviceBusiness.obtenerSolicitudEnProceso(fisicaEncontrada.getIdPersona());
					
					if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
						existeSolProceso = true;
					} else {
						solicitudActiva = new Solicitud();
					}
				}
				
				if (existeSolRegistrada) {
					this.log.info("La persona [idPersona:"+ fisicaEncontrada.getIdPersona()+ "] ya cuenta con solicitud REGISTRADA para NSS [folio:"+ solicitudActiva.getNoFolioSolicitud() + "]");
					//mensaje de error cuando la solicitud solo esta registrada
					mensajeError = "Usted ya cuenta con una solicitud para Generación de Número de Seguridad Social registrada, cuyo folio es: "+ solicitudActiva.getNoFolioSolicitud();
				} else if (existeSolProceso) {
					this.log.info("La persona [idPersona:" + fisicaEncontrada.getIdPersona() + "] ya cuenta con solicitud EN_PROCESO para NSS [folio:"+ solicitudActiva.getNoFolioSolicitud() + "]");
					//mensaje de error cuando la solicitud ya esta en proceso
					mensajeError = "Usted ya cuenta con una solicitud para Generación de Número de Seguridad Social en proceso, cuyo folio es: " + solicitudActiva.getNoFolioSolicitud();
				}
				
			} catch (SolicitudException e) {
				this.log.error(e);
			}
			
		}
		//si el mensaje de error no esta vacio redireccionaremos a la pantalla de inicio
		if(mensajeError != null) {
			//retornamos el redirec view
			return procesarErrorEnPantallaInicio(mensajeError, session);
		}
		
		return null;
	}
	
	/**
	 * procesamos las posibles excepciones de la busqueda de personas y nos redirigimos a la 
	 * pantalla inicial de la asignacion o localizacion de NSS
	 * @param e
	 * @param fisicaFormulario
	 * @param session
	 * @return
	 */
	private RedirectView procesarExcepcionesBusquedaPersona(Exception e, Fisica fisicaFormulario,HttpSession session) {
		String mensajeError = null;
		//pintamos el trace
		e.printStackTrace();
		//Verificamoa sde que tipo es la excepcion para mostrar el mensaje adecuado
		if (e instanceof GenerarNSSException) {
			mensajeError = MENSAJE_SUBDELEGACION;
		} else if (e instanceof CURPNoLocalizadoEnEntidadExternaException) {
			mensajeError = "Mensaje: No se localizó información en RENAPO con la CURP capturada.";
		} else if (e instanceof ClienteWebserviceRenapoCurpException) {
			Integer codigo = ((ClienteWebserviceRenapoCurpException) e) .getCodigo();
			if (codigo.equals(10005)) {
				mensajeError = MENSAJE_SUBDELEGACION;
			} else {
				mensajeError = MENSAJE_FALLA_TECNICA;
			}
		} else if (e instanceof ErrorValidacionDatosConsultaEnEntidaExternaException) {
			mensajeError = ((ErrorValidacionDatosConsultaEnEntidaExternaException) e) .getMessage();
		} else if (e instanceof ErrorComparacionDatosRENAPOException) {
			mensajeError = ((ErrorComparacionDatosRENAPOException) e) .getMessage();
		} else if (e instanceof UmfNoLocalizadaException) {
			mensajeError = "No se encontraron UMF asociadas a su domicilio particular, para continuar con el trámite es necesario que acuda a una Subdelegación";			
		} else if (e instanceof SolicitudNssCorreoException) {
			mensajeError = ((SolicitudNssCorreoException) e) .getMessage();
		} 

		//mandamos el error a pantall
		return procesarErrorEnPantallaInicio(mensajeError, session);
	}
	
	/**
	 * Metodo para procesar las posibles excepciones que pueden ser lanzadas al momento 
	 * de finalizar la asignacion de NSS
	 * @param e
	 * @param solicitud
	 * @param session
	 * @return
	 */
	private RedirectView procesarExcepcionesFinalizadoAsignacion(Exception e, Solicitud solicitud, HttpSession session) {
		
		String mensajeError = null;
		//pintamos el trace
		e.printStackTrace();
		if (e instanceof SolicitudNoValidaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if (e instanceof SolicitudNoEncontradaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof TramiteNoEncontradoException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof AfectacionDatosPersonaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof PersonaNoEncontradaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof NivelDeAsignacionSerieIndefinidoException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof SeriesNoLocalizadasException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof ErrorAlActivarSerieException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof DomicilioNoValidoException) {
			mensajeError = e.getMessage();
		} else if  (e instanceof CURPNoLocalizadoEnEntidadExternaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof ClienteWebserviceRenapoCurpException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof ErrorValidacionDatosConsultaEnEntidaExternaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof PersonaSinCalificacionesException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else if  (e instanceof NSSYaExistenteException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage(); 
		} else if  (e instanceof SerieNssAgotadaException) {
			mensajeError = ((SolicitudNoValidaException)e).getMessage();
		} else {
			mensajeError = "Ocurri&oacute; un error al finalizar la solicitud.";
		}
		
		this.serviceBusiness.cancelar(solicitud);
		//mandamos el error a pantall
		return procesarErrorEnPantallaInicio(mensajeError, session);
	}
	
	private RedirectView procesarErrorEnPantallaInicio(String mensajeError, HttpSession session) {
		Fisica fisica = new Fisica();
		fisica.setErrorFormGeneral(mensajeError);
		// Subimos al request el objeto de Modelo
		session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
		//retornamos el redirec view
		return new RedirectView(REDIRECT_INICIO, true);
	}
	
	/**
	 * Metodo que procela la excepcion de PersonaConNSSException para que en base a ella se cree el tramite de localizacion de NSS
	 * y se generen los reportes correspondientes, y una vez terminada la solicitud, se redirige a la vista de solicitud finalizada
	 * para que en caso de dar F5 no se vuelvan a hacer las peticiones
	 * 
	 * @param session
	 * @param request
	 * @param fisica
	 * @param nss
	 * @param e
	 * @param nssCorreo
	 * @return
	 */
	private RedirectView procesarPersonaConNSS(HttpSession session, HttpServletRequest request, Fisica fisica, PersonaConNSSException e, SolicitudNssCorreo nssCorreo) {
		/*
		 * La persona ya cuenta con NSS se debe realizar lo siguiente:
		 * mandar por correo, registrar operación y crear solicitud
		 * de recuperación de NSS
		 */
		this.log.info(e);
		request.setAttribute("NSS_RECUPERADO", true);
		e.getFisica().setCorreoElectronico(fisica.getCorreoElectronico());
		AsignacionNSS nss = new AsignacionNSS();			
		try {
			ConvertUtils.register(new DateConverter(null), Date.class);
			BeanUtils.copyProperties(nss, e.getFisica());
		} catch (IllegalAccessException e1) {
			this.log.error(e1);
		} catch (InvocationTargetException e1) {
			this.log.error(e1);
		}
		
		this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
		
		//se setea el usuario con los datos de la curp localizada
		this.log.debug("el curp es" + e.getFisica().getCurp());
		Usuario usuario = new Usuario();
		usuario.setUsuario(e.getFisica().getCurp());
		
		try {
			Solicitud solicitud = null;
			
			Map<String, Object> resultado = this.serviceBusiness.crearSolicitudRecuperacionNSS(
					e.getFisica(), OrigenSolicitudEnum.INTERNET, usuario);
			
			//obtenemos la solicitud y los comprobantes que vienen en un map
			solicitud = (Solicitud) resultado.get("solicitud");
			byte[] comprobante = (byte[]) resultado.get("comprobante");
			byte[] comprobanteQR = (byte[]) resultado.get("comprobanteQR");
			
			Map<String, byte[]> doctos = new HashMap<String, byte[]>();
			doctos.put("comprobanteLocalizacion"+nss.getNss()+".pdf",comprobante);
			doctos.put("tarjetaNSS"+nss.getNss()+".pdf",comprobanteQR);
			log.debug("se enviara correo de localizacion para el NSS: " + nss.getNss());
			enviarMail(solicitud, nss, doctos);
			
			//this.serviceBusiness.enviarCorreoNSS(e.getFisica(),solicitud.getNoFolioSolicitud(), false, solicitud.getSolicitudId(), doctos);
			//Se setea los datos en session y se pone una bandera para indicar que es recuperacion
			session.setAttribute(KEY_SOLICITUD, solicitud);
			session.setAttribute(KEY_TRAMITE_ASEGURADO, solicitud.getTramites().get(0));
			session.setAttribute(KEY_IS_RECUPERACION,true);
			session.setAttribute(KEY_COMPROBANTE, comprobante);
			session.setAttribute(KEY_TARJETA, comprobanteQR);
		} catch (SolicitudNoValidaException e1) {
			this.log.error("No se creo la solicitud de recuperacion de NSS y no se envia correo",e1);
		} catch (SolicitudException e1) {
			this.log.error("No se creo la solicitud de recuperacion de NSS y no se envia correo",e1);
		}			
		
		return new RedirectView(REDIRECT_FINALIZADA,true);
	}
	
	/**
	 * Antes de comenzar a procesar la solicitud, es necesario
	 * checar los trámties dentro de la solicitud, si se encuentra
	 * uno de REGISTRO DE PERSONA, se debe checar si la persona ya existe
	 * con un NSS, en caso de cumplirse NO se debe procesar la solicitud
	 * recibida.	 
	 * @param Fisica
	 * @return
	 * @throws SolicitudNoValidaException 
	 */
	private void validarPersonaConNSS(TramiteFisica tramiteFisica, String folioSolicitud) throws SolicitudNoValidaException {
		 String msgErrorValidacion = "La solicitud  " + folioSolicitud + " no será procesada debido a que la persona a dar de alta ya cuenta con NSS";
		if (tramiteFisica != null && tramiteFisica.getFisica() != null
				&& tramiteFisica.getTipoTramite() != null
				&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
			
			this.log.info("Se va a realizar la validación para confirmar el procesamiento de la solicitud de NSS "
					+ folioSolicitud + " ya que se encontró un trámite de REGISTRO DE PERSONA");
			
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
				this.log.info("La persona encontrada tiene calificación IMSS, la solicitud " + folioSolicitud + " va a ser procesada de manera normal");
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
	}
	
	/**
	 * MEtodo que valida si a partir del tramite asegurado se requiere actualizar datos de la persona(curp fecha de nacimeinto)
	 * @param tramiteAsegurado
	 * @return
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws PersonaSinCalificacionesException
	 */
	private AsignacionNSS validaActualizacionDatos(TramiteAsegurado tramiteAsegurado) throws AfectacionDatosPersonaException, PersonaNoEncontradaException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException, PersonaSinCalificacionesException {
		
		AsignacionNSS fisica = null;
		if (tramiteAsegurado != null) {
			String curp = tramiteAsegurado.getCurpRENAPO();
			
			if (tramiteAsegurado.getFisica() != null) {
				fisica = tramiteAsegurado.getFisica();
					// Se obtiene la persona esta registrada se actualiza la informacion
					if (StringUtils.isNotBlank(curp) && fisica != null && fisica.getIdPersona() != null) {
						
						Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(curp);
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
		
		return fisica;
	}
	
	/**
	 * Metodo para obtener la umf que se mandara a sindo, en caso de haber mas de una asociada al mismo codigo postal
	 * se man
	 * @param codigoPostal
	 * @return
	 */
	private UnidadMedicaFamiliar getUMFAsignacion(String codigoPostal) {
		log.debug("VOy a buscar las umfs con el codigo postal " + codigoPostal);
		UnidadMedicaFamiliar umf = null;
		List<UnidadMedicaFamiliar> listaUMFS = null;
		String msgError = null;
		
		try {
			listaUMFS = domicilioServiceBusiness.getUmfByCodigoPostal(codigoPostal);
		} catch (UmfNoLocalizadaException ex) {
			this.log.error("no se encontr\u00F3 ninguna UMF para el asentamiento");
			msgError = "No se encontr\u00F3 ninguna UMF para el c\u00F3digo postal";
		} catch (Exception e) {
			this.log.error("Error no especificado ", e);
			msgError = "Ocurri\u00F3 un error inesperado al consultar las UMF ";
		}
		
		if(listaUMFS != null) {
			if(listaUMFS.size()==1) {
				log.debug("Encontre solo una UMF asociada al codigo postal " + codigoPostal);
				umf = listaUMFS.get(0);
			} else {
				
				//si vienen mas de una UMF se selecciona de manera aleatoria la umf
				Random generator = new Random();
				int index = generator.nextInt(listaUMFS.size());
				umf = listaUMFS.get(index);
				log.debug("Encontre " + listaUMFS.size() + " UMF asociada al codigo postal " + codigoPostal + " por lo que se selecciona por default la " + umf.getNombreCorto());
			}
		}
		
		log.error(msgError);
		return umf;
	}
	
	private Map<String, byte[]> generarDocumentosAsignacion(Solicitud solicitud, HttpSession session) {
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
			session.setAttribute(KEY_COMPROBANTE, comprobante);
			session.setAttribute(KEY_TARJETA, comprobanteQR);
		} catch(Exception e) {
			log.error("Ocurrio un error al generar los documentos de asignacion ", e);
			e.printStackTrace();
		}
		
		return doctos;
	}
	
	/**
	 * Metodo que obtiene de la session el documento solicitado para mostrarlo en pantalla
	 * @param tarjeta si es tru se obtendra la tarjeta del nss, de lo contrario el comprobante
	 * @param session es nesecario para obtener los documentos
	 * @param response
	 */
	private void prepararReporte(boolean tarjeta,HttpSession session,HttpServletResponse response, boolean download) {
		//ontenemos de la session el documento que necesitamos
		byte[] documento = tarjeta ?  (byte[])session.getAttribute(KEY_TARJETA) : (byte[])session.getAttribute(KEY_COMPROBANTE);
		//dependiendo de la manera ponemos el nombre del reporte
		String name = tarjeta ? "tarjetaNSS.pdf" : "comprobanteNSS.pdf";
		//preparamos los encabezados
		try {
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.addHeader("expires","0");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", (download ? "attachment":"inline") +";filename = "+name);
			response.getOutputStream().write(documento);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que limpia los datos de la session que han sido utilizados para generar el traite
	 * @param session
	 */
	private void limpiarSession(HttpSession session) {
		//limpiamos todos los elememntos de la session
		session.removeAttribute(KEY_VALIDACIONES);
		session.removeAttribute(IS_DOMICILIO_SESSION_KEY);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_TRAMITE_ASEGURADO);
		session.removeAttribute(KEY_IS_RECUPERACION);
		session.removeAttribute(KEY_COMPROBANTE);
		session.removeAttribute(KEY_TARJETA);
		session.removeAttribute(EDO_SOLICITUD);
		session.removeAttribute(MOTIVO_CANCELACION_SOLICITUD);
		session.removeAttribute(HASH_CODE_DATOS_BASICOS);
		session.removeAttribute(SOL_CHECK_STATUS);
		session.removeAttribute(KEY_FISICA_ERRORES_NEG);
		session.removeAttribute(KEY_NUMERO_REENVIOS);
	}
}
