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

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
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
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
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
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaPersonaAutorizada;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador para eliminar la relacion de persona autorizada
 * @author Mario Teran Blanco
 * @version 1
 */
@Controller
@RequestMapping("/wizard/tramite/personaAutorizada/baja")
public class WizardBajaPersonaAutorizadaController extends
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
	
	//Variables de sesion
	private static final String PESONAS_ASOCIADAS_KEY = "datosSujetosObligados";
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	private static final String DATOS_PERSONA_KEY = "datosPersona";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_RFC_PERSONA_SESION = "rfcPersonaSesion";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
		private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_MENSAJE = "mensaje";
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_ERROR = "error";
	private static final String KEY_TRAMITE_PA = "tramitePersonaAutorizada";
	private static final String KEY_PERSONAS_AUTORIZADAS = "personasAutorizadas";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	//Vistas
	private static final String VIEW_BAJA_PERSONA_INIT = "wizardBajaPersonaAutorizadaInit";
	private static final String VIEW_BAJA_PERSONA_CONTENT = "wizardBajaPersonaAutorizadaContenido";
	//Mensajes
	private static final String DESC_TIPO_SOLICITUD = "BAJA DE PERSONA AUTORIZADA";
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurrió un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurrió un error al localizar información de la persona.";
	private static final String MSG_ERROR_RETOMAR_SOLICITUD = "Error al retomar la solicitud.";	
	private static final String MSG_ERROR_PA_REQUERIDAS = "No existen personas autorizadas";
	private static final String MSG_ERROR_TRAMITE_PA = "La solicitud no cuenta con un tramite de baja de persona autorizada";
	private static final String MSG_ERROR_ACTUALIZAR_SOLICITUD = "Ocurri&oacute; un error al intentar guardar los cambios";
	private static final String MSG_ERROR_CANCELAR_SOLICITUD = "Hubo un error al cancelar la solicitud: ";
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	
	@RequestMapping(value="/{idPersona}/{idTipoPersona}/{rfcPersona}")
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
		TipoPersonaEnum tipoP = idTipoPersona.equals(TipoPersonaEnum.FISICA.getId()) 
			? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL ;
		//Se busca la solicitud de tipo actualizacion de datos patronales
		try {
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(KEY_RFC_PERSONA_SESION, personaSesion.getRfc());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, tipoP.getId(), 
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA);				
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona, tipoP.getId(), 
						TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA);
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
		listTipoTramite.add(TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA.getCodigo());
		request.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);		
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);		
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		return VIEW_BAJA_PERSONA_INIT;
	}
	
	@RequestMapping(value = "/iniciarTramite")
	public String iniciarTramite(Model model, HttpServletRequest request, HttpSession session) {
		
		Persona persona = (Persona) session.getAttribute(DATOS_PERSONA_KEY);
		String error = null;
		Solicitud solicitud = null;
		//buscamos a las personas autorizadas
		List<PersonaAutorizada> personasAutorizadas = this.getPersonasAutorizadasChecked(
			persona, null, session);		
		if(personasAutorizadas == null || personasAutorizadas.isEmpty()) {
			error = MSG_ERROR_PA_REQUERIDAS;
		}		
		if(error != null) {
			model.addAttribute(KEY_ERROR, error);			
		} else {			
			try {
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				solicitud = this.crearSolicitudBajaPersonaAutorizada(persona, null, request, session);
				session.setAttribute(SOLICITUD_KEY, solicitud);
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud()))
					generarCadenaOriginal(solicitud, personaRecuperada, session);
				
				session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());				
			} catch (SolicitudNoValidaException e) {
				e.printStackTrace();
				model.addAttribute(KEY_ERROR, e.getSituacion());
				return VIEW_BAJA_PERSONA_CONTENT;
			}			
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_PERSONAS_AUTORIZADAS, personasAutorizadas);
			model.addAttribute(KEY_RETOMAR, false);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			model.addAttribute(KEY_TRAMITE_PA, solicitud.getTramites().get(0));
		}
		
		return VIEW_BAJA_PERSONA_CONTENT;
	}

	/**
	 * 
	 * @param model
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/solicitud/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_SOLICITUD_FORM) Solicitud solicitud,
			HttpServletRequest request, HttpSession session) {		
		//variables de control de l jsp
		model.addAttribute(KEY_RETOMAR, true);
		//recuperamos a la empresa o persona a asociar
		Persona empresa = (Persona) session.getAttribute(DATOS_PERSONA_KEY);
		TramiteBajaPersonaAutorizada tPA = null;
		List<PersonaAutorizada> listaPA = null;
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
				tPA = (TramiteBajaPersonaAutorizada) solicitud.getTramites().get(0);
				session.setAttribute(SOLICITUD_KEY, solicitud);
			}	
			listaPA = this.getPersonasAutorizadasChecked(empresa, tPA.getPersonasAutorizadas(), session);
		} catch(Exception e) {
			error = MSG_ERROR_RETOMAR_SOLICITUD;
		}		
		//Si no hubo error
		if(error == null) {
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			model.addAttribute(KEY_TRAMITE_PA, tPA);
			request.setAttribute(KEY_PERSONAS_AUTORIZADAS, listaPA);
		} else{
			model.addAttribute(KEY_ERROR, error);
		}		
		return VIEW_BAJA_PERSONA_CONTENT;
	}
	/**
	 * 
	 * @param sujetosO
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/solicitud/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> guardarSolicitud(
			@RequestBody TramiteBajaPersonaAutorizada tramite, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//recuperamos la solicitud de sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		//Obtenemos el tramite de baja de persona autorizada
		TramiteBajaPersonaAutorizada tramiteBPA = (TramiteBajaPersonaAutorizada) solicitud.getTramites().get(0);
		List<PersonaAutorizada> personasAutorizadas = tramite.getPersonasAutorizadas();
		//Le agregamos al tramite los sujetos obligados seleccionados por el usuario
		tramiteBPA.setPersonasAutorizadas(this.setPersonasAutorizadasSeleccionadas(personasAutorizadas, session));
		//Establecemos los datos del tramite
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteBPA);		
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
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> finalizarSolicitud(
			@RequestBody TramiteBajaPersonaAutorizada tramite, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		FirmaElectronica firmaElectronica = null;
		//Obtenemos la solicitud de la sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		List<PersonaAutorizada> personasAutorizadas = tramite.getPersonasAutorizadas();
		//Obtenemos el tramite de baja de persona autorizada
		TramiteBajaPersonaAutorizada tramiteBPA = (TramiteBajaPersonaAutorizada) solicitud.getTramites().get(0);
		tramiteBPA.setPersonasAutorizadas(this.setPersonasAutorizadasSeleccionadas(personasAutorizadas, session));		
		//Establecemos los nuevos datos del tramite
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteBPA);
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
	 * Metodo para cancelar la solicitud
	 * @param solicitud
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
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
	 * Metodo para limpiar la sesion
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {	
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(DATOS_PERSONA_KEY);
		session.removeAttribute(PESONAS_ASOCIADAS_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);
		session.removeAttribute(KEY_TRAMITE_PA);
		session.removeAttribute(KEY_PERSONAS_AUTORIZADAS);
		session.removeAttribute(KEY_RFC_PERSONA_SESION);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(KEY_USUARIO_SSO);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<PersonaAutorizada> setPersonasAutorizadasSeleccionadas(List<PersonaAutorizada> seleccionadas, 
			HttpSession session) {
		List<PersonaAutorizada> seleccion = null;
		List<PersonaAutorizada> relacionados = (List<PersonaAutorizada>) session.getAttribute(PESONAS_ASOCIADAS_KEY);
		if(seleccionadas != null && !seleccionadas.isEmpty()) {
			seleccion = new ArrayList<PersonaAutorizada>();			
			relacionados : for(PersonaAutorizada relacionado: relacionados) {
				for(PersonaAutorizada seleccionada: seleccionadas) {
					if(relacionado.getCvePersonaAutorizada().equals(seleccionada.getCvePersonaAutorizada())) {
						seleccion.add(relacionado);
						continue relacionados;
					}
				}
			}
		}		
		return seleccion;
	}
	
	private List<PersonaAutorizada> getPersonasAutorizadasChecked(Persona persona, 
			List<PersonaAutorizada> checadas, HttpSession session) {
		List<PersonaAutorizada> asociadas = null;
		List<PersonaAutorizada> nuevaLista = null;		
		asociadas = getPersonasAutorizadas(persona);
		session.setAttribute(PESONAS_ASOCIADAS_KEY, asociadas);		
		if(asociadas != null && !asociadas.isEmpty()) {			
			if(checadas != null) {
				nuevaLista = new ArrayList<PersonaAutorizada>();
				for(PersonaAutorizada asociada: asociadas) {
					for(PersonaAutorizada personaA :checadas) {
						if(personaA.getCvePersonaAutorizada()
								.equals(asociada.getCvePersonaAutorizada())) {
							asociada.setChecked(true);
							break;
						} else {
							asociada.setChecked(false);
						}
					}					
					nuevaLista.add(asociada);
				}
			} else {
				nuevaLista = new ArrayList<PersonaAutorizada>();
				for(PersonaAutorizada personaA : asociadas) {
					personaA.setChecked(false);
					nuevaLista.add(personaA);
				}
			}			
		}		
		return nuevaLista;
	}
	
	private List<PersonaAutorizada> getPersonasAutorizadas(Persona persona) {
		List<PersonaAutorizada> personas = null;
		Long idPersonaMoral = null;		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.MORAL.getId())) {
			idPersonaMoral = persona.getIdPersona();
			persona.setIdPersona(null);
		}		
		personas = personasAutorizadasServiceRemote.getPersonasAutorizadasByPersona(persona);		
		if(idPersonaMoral != null)
			persona.setIdPersona(idPersonaMoral);
		
		return personas;
	}
	/**
	 * 
	 * @param personaAutorizada
	 * @param empresa
	 * @param sujetos
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	private Solicitud crearSolicitudBajaPersonaAutorizada(Persona empresa, List<PersonaAutorizada> personasAutorizadas, 
			HttpServletRequest request, HttpSession session) throws SolicitudNoValidaException{
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
		TramiteBajaPersonaAutorizada tramitePA = new TramiteBajaPersonaAutorizada();
		if(empresa.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			Fisica personaFisica = new Fisica();
			personaFisica.setIdPersona(empresa.getIdPersona());
			personaFisica.setRfc(empresa.getRfc());			
			tramitePA.setFisica(personaFisica);
		} else {
			Moral personaMoral = new Moral();
			personaMoral.setIdPersona(empresa.getIdPersona());
			personaMoral.setRfc(empresa.getRfc());			
			tramitePA.setMoral(personaMoral);
		}		
		tramitePA.setPersonasAutorizadas(personasAutorizadas);
		tramitePA.setEstadoTramite(new EstadoTramite());
		tramitePA.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramitePA.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramitePA.setTipoTramite(new TipoTramite());
		tramitePA.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA.getCodigo());
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
			contenidoAFirmar.append("|");
		}
		// Registro Patronal(No aplica)
		contenidoAFirmar.append("Registro Patronal:|");
		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:||");
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
}
