package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.web.validator.PersonaRFCValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador para el registro de una persona autorizada
 * @author Mario Teran Blanco
 * @version 1
 */
@Controller
@RequestMapping("/wizard/tramite/personaAutorizada")
public class WizardRegistroPersonaAutorizadaController extends
		AbstractController {
	
	@Autowired PersonasAutorizadasServiceRemote personasAutorizadasServiceRemote;
	@Autowired ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	@Autowired SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	@Autowired SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@Autowired ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_RFC_PERSONA_SESION = "rfcPersonaSesion";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String DATOS_PERSONA_KEY = "datosPersona";
	private static final String DATOS_PERSONA_AUTORIZADA_KEY = "datosPersonaAutorizada";
	private static final String SUJETOS_OBLIGADOS_KEY = "datosSujetosObligados";
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String KEY_MENSAJE = "mensaje";
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_ERROR = "error";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_TRAMITE_PA = "tramitePersonaAutorizada";
	private static final String KEY_SUJETOS_LOCALIZADOS = "sujetosEncontrados";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_SOLICITUD_CREADA = "solicitudCreada";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	//Pantallas
	private static final String VIEW_REGISTRO_PERSONA_INIT = "wizardRegistroPersonaAutorizadaInit";
	private static final String VIEW_REGISTRO_PERSONA_CONTENT = "wizardRegistroPersonaAutorizadaContenido";
	//Mensajes
	private static final String DESC_TIPO_SOLICITUD = "REGISTRO DE PERSONA AUTORIZADA";
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurrió un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurrió un error al localizar información de la persona.";
	private static final String MSG_ERROR_RETOMAR_SOLICITUD = "Error al retomar la solicitud.";	
	private static final String MSG_ERROR_TRAMITE_PA = "La solicitud no cuenta con un tramite de registro de persona autorizada";
	private static final String MSG_ERROR_ACTUALIZAR_SOLICITUD = "Ocurri&oacute; un error al intentar guardar los cambios";
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_ERROR_CANCELAR_SOLICITUD = "Hubo un error al cancelar la solicitud: ";
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";
	
	/**
	 * Metodo para inicializar el wizard de registro de persona autorizada
	 * @param model
	 * @param request
	 * @param session
	 * @param idPersona  - el id de la persona que registrara a la persona autorizada
	 * @param idTipoPersona - id de la persona puede ser Fisica 1 o Moral 2
	 * @param rfcPersona - El rfc de la persona
	 * @return String vista
	 */
	@RequestMapping(value="/registro/{idPersona}/{idTipoPersona}/{rfcPersona}")
	public String initWizardRegistroPersonaAutorizada(Model model, HttpServletRequest request, 
			HttpSession session, @PathVariable Long idPersona, @PathVariable Long idTipoPersona, 
			@PathVariable String rfcPersona){
		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		//Establecemos los datos de la empresa
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		persona.setRfc(rfcPersona);		
		Solicitud solicitudActiva = new Solicitud();
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;		
		//Establecemos en sesion los dados de la empresa
		session.setAttribute(DATOS_PERSONA_KEY, persona);
		//Verificamos que tipo de persona es para buscar en tramite persona moral o en tramite persona fisica
		TipoPersonaEnum tipoP = idTipoPersona.equals(TipoPersonaEnum.FISICA.getId()) ? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL ;
		//Se busca la solicitud de tipo actualizacion de datos patronales
		try {
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_RFC_PERSONA_SESION, personaSesion.getRfc());
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, tipoP.getId(), 
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.PERSONAS_AUTORIZADAS);				
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona, tipoP.getId(), 
						TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.PERSONAS_AUTORIZADAS);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {			
			model.addAttribute(KEY_ERROR, MSG_ERROR_SOL_PENDIENTES);
			log.error(e);
		} catch (PersonaFisicaNoEncontradaException ee) {
			model.addAttribute(KEY_ERROR, MSG_ERROR_LOCALIZAR_PERSONA);
			log.error(ee);
		}	
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.PERSONAS_AUTORIZADAS.getCodigo());		
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		model.addAttribute(KEY_SOLICITUD_CREADA, false);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		request.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		return VIEW_REGISTRO_PERSONA_INIT;
	}
	
	@RequestMapping(value = "/registro/iniciarTramite")
	public String iniciarTramite(Model model, HttpServletRequest request) {
		//establecemos las variables de control del jsp
		model.addAttribute(KEY_SOLICITUD_CREADA, false);
		//Creamos una fisica para la busqueda
		Fisica persona = new Fisica();
		model.addAttribute("persona", persona);		
		return VIEW_REGISTRO_PERSONA_CONTENT;
	}
	
	/**
	 * 
	 * @param model
	 * @param persona
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/registro/solicitud/crear", method = RequestMethod.POST)
	public String crearSolicitud(Model model, @ModelAttribute(value = "persona") 
			Fisica persona, BindingResult result,HttpServletRequest request, HttpSession session) {
	
		new PersonaFisicaCURPValidator().validate(persona, result);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		new PersonaRFCValidator().validate(persona, result);
		model.addAttribute(KEY_SOLICITUD_CREADA, false);
		//Si hubo algun error en las validaciones del formulario regresamos a la pantalla de captura
		if(result.hasErrors()) {
			return VIEW_REGISTRO_PERSONA_CONTENT;
		}		
		Solicitud solicitud = new Solicitud();
		//REcuperamos de sesion los datos de la empresa
		Persona empresa = (Persona) session.getAttribute(DATOS_PERSONA_KEY);		
		//Variable de errores
		String error = null;	
		//Una vez que se pasaron las validaciones de campos verificamos que el rfc de la persona
		//que esta dando de alta no sea el miso de la persona que sera registrada como persona autorizada
		//en caso de ser asi mostramos mensaje de error
		if(empresa.getRfc().equals(persona.getRfc())) {
			request.setAttribute(KEY_ERROR, "Esta persona no puede ser dada de alta como persona autorizada");
			return VIEW_REGISTRO_PERSONA_CONTENT;
		}		
		//Buscamos a la persona con la curp proporcionada en caso de algun error mandamos un mensaje al jsp
		try {			
			persona = consultaPersonaFisicaServiceBusinessRemote.getPersonaByCurpImssEntidadesExternas(persona);						
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
			error = "No fue posible comunicar con la entidad RENAPO";
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			error = "No fue posible comunicar con la entidad SAT";
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
			error = e.getSituacion();
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
			error = e.getSituacion();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			error = e.getSituacion();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			error = e.getSituacion();
		} catch (DiferenciasRENAPOContraSAT e) {
			e.printStackTrace();
			error = e.getSituacion();
		} catch (PersonaSinCalificacionesException e) {
			e.printStackTrace();
			error = e.getSituacion();
		}catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			error = e.getSituacion();
		}
		//Si encontramos algun error lo seteamos en el request
		if(error != null) {
			request.setAttribute(KEY_ERROR, error);
		} else {			
			List<SujetoObligado> listaP = null;			
			//Buscamos a todos los patrones asociados con la empreza, el segundo parametro es nulo 
			//ya que hasta ahora no se ha seleccionado ningun patron
			try {
				listaP = this.getSujetosSOChecked(empresa, null,persona.getCveFisica(),session);
			} catch(AbstractException e) {
				e.printStackTrace();
				request.setAttribute(KEY_ERROR, e.getMessage());
				return VIEW_REGISTRO_PERSONA_CONTENT;
			}		
			//Si la lista viene nula mandamos un error
			if(listaP == null) {
				request.setAttribute(KEY_ERROR,"Error en los datos");
				return VIEW_REGISTRO_PERSONA_CONTENT;
			} else {
				if(persona.getIdPersona() != null) {
					//En caso de que la persona ya esta registrara verificaremos si
					//Ya esta registrada como representante legal en caso de ser asi mandamos mensaje de error
					try {
						checarExistenciaRepresentante(persona, listaP.get(0));
					} catch (RepresentanteLegalInvalidoException e) {
						request.setAttribute(KEY_ERROR, "Error al consultar representante legal");
						return VIEW_REGISTRO_PERSONA_CONTENT;
					} catch (RepresentanteLegalYaExisteException e) {
						request.setAttribute(KEY_ERROR, "Esta persona ya se encuentra registrada como representante legal" );
						return VIEW_REGISTRO_PERSONA_CONTENT;
					}
				}
			}			
			//Una vez que hemos localizado a los patrones creamos la solicitud, el tercer parametro es nulo
			//ya que en este momento aun no se ha seleccionado ningun patron
			try {
				solicitud = this.crearSolicitudPersonaAutorizada(persona,empresa,null, request, session);	
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
					generarCadenaOriginal(solicitud, personaRecuperada, session);
				}
				session.setAttribute(KEY_RFC_SOLICITANTE, persona.getRfc());
				session.setAttribute(SOLICITUD_KEY, solicitud);
			} catch (SolicitudNoValidaException e) {
				e.printStackTrace();
				request.setAttribute(KEY_ERROR, e.getSituacion());
				return VIEW_REGISTRO_PERSONA_CONTENT;
			}			
			//Agregamos la lista de los patrones
			request.setAttribute(KEY_SUJETOS_LOCALIZADOS, listaP);
			request.setAttribute(KEY_SOLICITUD, solicitud);
			request.setAttribute(KEY_RETOMAR, false);
			model.addAttribute(KEY_SOLICITUD_CREADA, true);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			model.addAttribute(KEY_TRAMITE_PA,solicitud.getTramites().get(0));
		}		
		return VIEW_REGISTRO_PERSONA_CONTENT;
	}
	
	/**
	 * Metodo para limpiar la sesion
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/registro/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {	
		session.removeAttribute(DATOS_PERSONA_KEY);
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(DATOS_PERSONA_AUTORIZADA_KEY);
		session.removeAttribute(SUJETOS_OBLIGADOS_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(KEY_RFC_PERSONA_SESION);
		session.removeAttribute(KEY_RFC_PERSONA_SESION);
		session.removeAttribute(KEY_USUARIO_SSO);
		
		return null;
	}
	
	/**
	 * 
	 * @param sujetosO
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/registro/solicitud/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> guardarSolicitud(
			@RequestBody SujetoObligado sujetosO, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//Obtenemos la solicitud de la sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		//Obtenemos el tramite de registro de persona autorizada
		TramitePersonaAutorizada tramitePA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
		//LE agregamos al tramite los sujetos obligados seleccionados por el usuario
		tramitePA.setSujetosObligados(this.setSujetosChecados(sujetosO.getSujetosObligados(), session));
		//Establecemos los datos del tramite
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramitePA);
		try {
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put(KEY_MENSAJE, MSG_ACTUALIZADA_SOLICITUD);
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_ACTUALIZAR_SOLICITUD);
		}		
		return result;
	}
	
	/**
	 * 
	 * @param sujetosO
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/registro/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> finalizarSolicitud(
			@RequestBody SujetoObligado sujetosO, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		FirmaElectronica firmaElectronica = null;
		//Obtenemos la solicitud de la sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		//Establecemos los patrones seleccionados
		TramitePersonaAutorizada tramitePA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
		tramitePA.setSujetosObligados(this.setSujetosChecados(sujetosO.getSujetosObligados(), session));
		//Establecemos los nuevos datos del tramite
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramitePA);
		solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));		
		//invocamos el metodo para finalizar la solicitud
		try {
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud()))
				firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			
			solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, firmaElectronica);
			result.put(KEY_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_MENSAJE, e.getSituacion());
		}		
		return result;
	}
	
	/**
	 * 
	 * @param model
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/registro/solicitud/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_SOLICITUD_FORM) Solicitud solicitud, 
			HttpServletRequest request, HttpSession session) {
		
		model.addAttribute(KEY_RETOMAR, true);
		model.addAttribute(KEY_SOLICITUD_CREADA, true);
		//recuperamos a la empresa o persona a asociar
		Persona empresa = (Persona) session.getAttribute(DATOS_PERSONA_KEY);
		TramitePersonaAutorizada tPA = null;
		List<SujetoObligado> listaP = null;
		String error = null;		
		try {
			Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud()))
				generarCadenaOriginal(solicitud, personaRecuperada, session);
			
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());			
			//Se verifica que la solicitud contenga algun tramite
			if(solicitud.getTramites().isEmpty()) {
				error = MSG_ERROR_TRAMITE_PA;
			} else {
				//Si la solicitud trae algun tramite, lo obtenemos y colocamos la solicitud en sesion
				tPA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
				session.setAttribute(SOLICITUD_KEY, solicitud);
			}
			listaP = this.getSujetosSOChecked(empresa, tPA.getSujetosObligados(), 
					tPA.getPersonaAutorizada().getCveFisica(),session);
		} catch(Exception e) {
			error = MSG_ERROR_RETOMAR_SOLICITUD;
		}		
		//Si no hubo error
		if(error == null) {
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			model.addAttribute(KEY_TRAMITE_PA, tPA);
			request.setAttribute(KEY_SUJETOS_LOCALIZADOS, listaP);
		} else{
			model.addAttribute(KEY_ERROR, error);
		}
		
		return VIEW_REGISTRO_PERSONA_CONTENT;
	}
	/**
	 * Metodo para cancelar la solicitud
	 * @param solicitud
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registro/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitud(@RequestBody Solicitud solicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) {	
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			//Establecemos los nuevos estados de la solicitud
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_SOLICITUD, solicitud);
			
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_CANCELAR_SOLICITUD + e.getMessage());
		}		
		return result;
		
	}
	/**
	 * 
	 * @param persona
	 * @param checados
	 * @param session
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	private List<SujetoObligado> getSujetosSOChecked(Persona persona, List<SujetoObligado> checados, 
			Long idPersonaFisica,HttpSession session) throws GestionPatronalBusinessException {			
		List<SujetoObligado> lista = new ArrayList<SujetoObligado>();
		//Buscamos a los sujetos obligados relacionados con la persona
		List<SujetoObligado> sujetosEncontrados = null;		
		try {
			sujetosEncontrados = this.getSujetosO(persona);
			if(CollectionUtils.isEmpty(sujetosEncontrados)){
				log.error("La persona NO tiene asociados RPs con IdPersona " + persona.getIdPersona());
				throw new GestionPatronalBusinessException("La persona con RFC "+
					persona.getRfc()+" no cuenta con Registros Patronales asociados");
			}else{
				//verificamos si el id de persona fisica no es nulo, en ese caso buscamos a los patrones
				//que no esten relacionados ya con la misma persona, en dado caso de que el id de persona fisica
				//venga nulo, quiere decir que la persona es nueva
				if(idPersonaFisica != null) {
					Fisica fisica = new Fisica();
					fisica.setCveFisica(idPersonaFisica);
					sujetosEncontrados = this.getNoRelacionados(persona, fisica, sujetosEncontrados);
				}				
				//verificamos que la lista no sea nula o se encuentre vacia, en caso de ser asi mandamos una excepcion
				if(sujetosEncontrados == null || sujetosEncontrados.isEmpty()) {
					throw new GestionPatronalBusinessException("Esta persona ya se encuentra relacionado " +
						"con todos los Registros Patronales disponibles");
				}
			}
		} catch(GestionPatronalBusinessException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}		
		//Los patrones encontrados los colocamos en sesion
		session.setAttribute(SUJETOS_OBLIGADOS_KEY, sujetosEncontrados);		
		//Empezamos a verificar que patrones estan checados
		for(SujetoObligado sujeto: sujetosEncontrados) {
			//Si la lista de checados no es nula o no esta vacia verificamos si es igual a uno que venga en la
			//lista de patrones y de ser asi lo marcamos
			if(checados != null && !checados.isEmpty()) {
				for(SujetoObligado sujetoCheck: checados) {
					//Si el id del patron coincide lo marcamos como checado
					if(sujetoCheck.getCveIdSujetoObligado().equals(sujeto.getCveIdSujetoObligado())) {
						sujeto.setChecked(true);
						break;
					} else {
						sujeto.setChecked(false);
					}
				}
			} else { //si la lista de checados es nula todos se ponen como no checado
				sujeto.setChecked(false);
			}			
			lista.add(sujeto);
		}		
		return lista;
	}
	
	/**
	 * Metodo para obtener a los patrones que no estan relacionados ya con la persona fisica
	 * @param empresa
	 * @param autorizado
	 * @param todos
	 * @return
	 */
	private List<SujetoObligado> getNoRelacionados(Persona empresa, Fisica autorizado, List<SujetoObligado> todos) {
		List<SujetoObligado> salida = null;
		//invocamos al metodo que nos regresara la lista de patrones con el rfc que se encuentren relacionados ya con la persona
		List<SujetoObligado> relacionados = this.personasAutorizadasServiceRemote
			.getSujetosObligadosYaRepresentadosPorPersona(empresa,autorizado);		
		//Si la lista no es nula y no esta vacia recorremos a los patrones 
		if(relacionados != null && !relacionados.isEmpty()) {
			salida = new ArrayList<SujetoObligado>();
			for(SujetoObligado sujeto: todos) {
				boolean existeRelacion = false;
				for(SujetoObligado relacion: relacionados) {
					if(sujeto.getCveIdSujetoObligado().equals(relacion.getCveIdSujetoObligado())) {
						existeRelacion = true;
					}
				}
				//si descpues de verificar a los patrones ya relacionados ningun coincide con el sujeto comparado actualmente
				//lo agregamos a la lista de no relacionados
				if(!existeRelacion) {
					salida.add(sujeto);
				}
			}
		} else {
			//si la lista de relacionados viene nula o vacia retornamos todos los sujetos obligados relacionados
			return todos;
		}		
		return salida;
	}
	/**
	 * 
	 * @param persona
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	private List<SujetoObligado> getSujetosO(Persona persona) throws GestionPatronalBusinessException {
		//Consultamos a todos los sujetos obligados relacionados con la persona (Por Id de Persona);
		List<SujetoObligado> sujetosEncontrados = sujetoObligadoServiceBusinessRemote
			.listarRegistrosPatronalesPorPersona(persona);
		return sujetosEncontrados;
	}
	
	/**
	 * 
	 * @param personaAutorizada
	 * @param empresa
	 * @param sujetos
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	private Solicitud crearSolicitudPersonaAutorizada(Fisica personaAutorizada, Persona empresa, 
			List<SujetoObligado> sujetos, HttpServletRequest request, HttpSession session) throws SolicitudNoValidaException{
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		Date fechaActual = new Date();
		Usuario usuario = new CommonValidator().getUsuarioSession(session);
		
		Solicitud solicitud = new Solicitud();
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(idOrigen);
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());		
		TramitePersonaAutorizada tramitePA = new TramitePersonaAutorizada();
		tramitePA.setPersonaAutorizada(personaAutorizada);
		if(empresa.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			Fisica personaFisica = new Fisica();
			personaFisica.setIdPersona(empresa.getIdPersona());
			personaFisica.setRfc(empresa.getRfc());			
			tramitePA.setPersonaFisica(personaFisica);
		} else {
			Moral personaMoral = new Moral();
			personaMoral.setIdPersona(empresa.getIdPersona());
			personaMoral.setRfc(empresa.getRfc());			
			tramitePA.setPersonaMoral(personaMoral);
		}		
		tramitePA.setSujetosObligados(sujetos);
		tramitePA.setEstadoTramite(new EstadoTramite());
		tramitePA.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramitePA.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramitePA.setTipoTramite(new TipoTramite());
		tramitePA.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.PERSONAS_AUTORIZADAS.getCodigo());
		tramitePA.setFechaTramite(fechaActual);
		tramitePA.setFechaPresentacion(fechaActual);
		tramitePA.setIndRatificado(false);
		tramitePA.setAcuseVentanilla(new AcuseVentanilla());
		if(usuario != null){
			tramitePA.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());
			if(usuario.getUsuarioFuncionario()!=null 
					&& usuario.getUsuarioFuncionario().getSubdelegacion()!=null){
				tramitePA.getAcuseVentanilla().setIdSubdelegacion(usuario
					.getUsuarioFuncionario().getSubdelegacion().getId());
			}			
		}		
		solicitud.getTramites().add(tramitePA);
		solicitud.setSolicitante(usuario);
		solicitud = solicitudBusinessRemote.crear(solicitud);
		
		return solicitud;
	}
	

	/**
	 * Establecemos los sujetos obligados seleccionados
	 * @param sujetos
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<SujetoObligado> setSujetosChecados(List<SujetoObligado> sujetos, HttpSession session) {		
		List<SujetoObligado> checados = new ArrayList<SujetoObligado>();
		//Obtenemos la lista de sujetos obligados de sesion
		List<SujetoObligado> asociados = (List<SujetoObligado>) session.getAttribute(SUJETOS_OBLIGADOS_KEY);
		//Recorremos la lista
		asociados: for(SujetoObligado sO : asociados) {
			for(SujetoObligado checado: sujetos){
				if(sO.getCveIdSujetoObligado().equals(checado.getCveIdSujetoObligado())) {
					checados.add(sO);
					continue asociados;
				}
			}
		}		
		return checados;
	}
	
	/**
	 * 
	 * @param persona
	 * @param sujetoObligado
	 * @throws RepresentanteLegalInvalidoException
	 * @throws RepresentanteLegalYaExisteException
	 */
	private void checarExistenciaRepresentante(Fisica persona, SujetoObligado sujetoObligado) 
			throws RepresentanteLegalInvalidoException, RepresentanteLegalYaExisteException {
		//SE crea un objeto representante legal para saber si ya existe la relacion
		RepresentanteLegal representante = new RepresentanteLegal();
		representante.setCveIdPersona(persona.getIdPersona());
		representante.setPersonaFisica(persona);
		representante.setCveIdPatronSujetoObligado( sujetoObligado.getCveIdSujetoObligado());
		representante.setTipoPersonaRepresentada(new TipoPersona());
		representante.getTipoPersonaRepresentada().setIdTipoPersona(
			sujetoObligado.getTipoPersonaFiscal().getCodigo().longValue());
		representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);		
		//Se verifica que no exista la relacion de represnetante legal, de ser asi, el metodo lanzara una excepcion
		representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);

	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TIPO_SOLICITUD).append("|");
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());
		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());
		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("||");
		}
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
}
