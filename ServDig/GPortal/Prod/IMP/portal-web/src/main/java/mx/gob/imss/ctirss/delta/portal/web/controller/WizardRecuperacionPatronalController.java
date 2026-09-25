package mx.gob.imss.ctirss.delta.portal.web.controller;

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

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RelacionConRegistroPatronalExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
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
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.web.validator.SujetoObligadoValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wizard/tramite/recuperacion/patron")
public class WizardRecuperacionPatronalController extends AbstractController {
		
	private static final String KEY_PATRONES_RECUPERAR = "patronesARecuperar";
	private static final String KEY_PERSONA_RELACIONAR = "personaARelacionar";
	private static final String KEY_SOLICITUD = "solicitudRecuperacionPatron";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";	
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String ATRIBUTE_SOLICITUD = "solicitud";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_ERROR = "error";
	private static final String KEY_MENSAJE = "mensaje";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_RETOMAR = "isRetomar";	
	private static final String KEY_SUJETOS = "sujetos";
	private static final String KEY_SUJETO_BUSQUEDA = "sujetoObligadoBusqueda";
	private static final String KEY_ENCONTRADO = "encontrado";
	private static final String KEY_DUPLICADO = "duplicado";
	private static final String KEY_RECUPERADO = "recuperado";
	private static final String KEY_SUJETO = "sujeto";
	private static final String KEY_EXISTE_RELACION = "existeRelacion";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	//Vistas
	private static final String VIEW_INICIAL = "wizardRecuperacionPatronInit";
	private static final String VIEW_CONTENIDO = "wizardRecuperacionPatronContent";
	private static final String VIEW_NOMBRE_COMERCIAL = "formularioNombreComercial";
		
	private static final String VAR_TAMANIO_LISTA = "numeroPatrones";
	private static final String VAR_PATRONES_ASOCIADOS = "sujetos";
	//Mensajes
	private static final String DESC_TIPO_SOLICITUD = "Recuperación del Registro Patronal";
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurrió un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurrió un error al localizar información de la persona.";
	private static final String MSG_ERROR_GUARDAR_SOLICITUD = "Ocurri&oacute; un error al intentar guardar la solicitud.";
	private static final String MSG_ERROR_RETOMAR_SOLICITUD = "Error al retomar la solicitud.";	
	private static final String MSG_ERROR_ACTUALIZAR_SOLICITUD = "Ocurri&oacute; un error al intentar guardar los cambios";
	private static final String MSG_ERROR_CANCELAR_SOLICITUD = "Hubo un error al cancelar la solicitud: ";	
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	
	@Autowired SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	@Autowired ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	@RequestMapping("/{idPersona}/{idTipoPersona}/{rfc}")
	public String initRecuperacionPatron(HttpServletRequest request, HttpSession session, 
			Model model, @PathVariable Long idPersona, @PathVariable Long idTipoPersona, 
			@PathVariable String rfc) {
		Long idOrigen =  getOrigenInternetContext(request);
		Persona persona = new Persona();		
		persona.setIdPersona(idPersona);
		persona.setRfc(rfc.toUpperCase());
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		session.setAttribute(KEY_PERSONA_RELACIONAR, persona);		
		Solicitud solicitudActiva = new Solicitud();
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		//Verificamos que tipo de persona es para buscar en tramite persona moral o en tramite persona fisica
		TipoPersonaEnum tipoP = idTipoPersona.equals(TipoPersonaEnum.FISICA.getId()) 
			? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL ;
		
		try {
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			PortalController portal = new PortalController();
			portal.getUsuarioSesion(sso);			
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, tipoP.getId(), 
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL);				
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = validarSolicitudMismoOrigen(solicitudActiva, idOrigen);				
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona, tipoP.getId(), 
						TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;					
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
		listTipoTramite.add(TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL.getCodigo());
		request.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		
		return VIEW_INICIAL;
	}
	
	@RequestMapping("/iniciarTramite")
	public String iniciarTramite(HttpServletRequest request, Model model,HttpSession session) {
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		Persona persona = (Persona) session.getAttribute(KEY_PERSONA_RELACIONAR);
		Solicitud solicitudCreada = null;		
		try {
			Persona personaRecuperada;
			if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
				personaRecuperada = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(persona.getIdPersona());
			} else {
				personaRecuperada = personaMoralBusiness.getPersonaMoral(persona.getIdPersona());
			}			
			solicitudCreada = this.crearSolicitudRecuperacion(persona, request, session);
			session.setAttribute(KEY_SOLICITUD, solicitudCreada);
			request.setAttribute(ATRIBUTE_SOLICITUD, solicitudCreada);
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			// Datos del acuse
			Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
			if(esSolicitudInternet(solicitudCreada.getOrigenSolicitud().getIdTipoSolicitud())){
				obtenerDatosAcuse(solicitudCreada, personaSesion, session);			
				generarCadenaOriginal(solicitudCreada, personaRecuperada, session);
			}			
		} catch (AbstractException e) {
			e.printStackTrace();
			request.setAttribute(KEY_ERROR, MSG_ERROR_GUARDAR_SOLICITUD);
		}		
		session.setAttribute(KEY_PATRONES_RECUPERAR, sujetos);
		model.addAttribute(KEY_RETOMAR, false);		
		request.setAttribute(KEY_SUJETOS, sujetos);
		request.setAttribute(VAR_TAMANIO_LISTA, sujetos.size());
		model.addAttribute(KEY_SUJETO_BUSQUEDA, new SujetoObligado());
		
		return VIEW_CONTENIDO;
	}
	
	@RequestMapping("/solicitud/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_SOLICITUD_FORM) Solicitud solicitud, 
			HttpSession session, HttpServletRequest request) {		
		Persona persona = (Persona) session.getAttribute(KEY_PERSONA_RELACIONAR);
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();		
		model.addAttribute(KEY_RETOMAR, true);
		model.addAttribute(KEY_SUJETO_BUSQUEDA, new SujetoObligado());
		try {
			Persona personaRecuperada;
			if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
				personaRecuperada = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(persona.getIdPersona());
			} else {
				personaRecuperada = personaMoralBusiness.getPersonaMoral(persona.getIdPersona());
			}
			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
			if(esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				obtenerDatosAcuse(solicitud, personaSesion, session);			
				generarCadenaOriginal(solicitud, personaRecuperada, session);				
			}			
			TramitePersonaAutorizada tpA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
			sujetos = tpA.getSujetosObligados();				
			if(sujetos != null) {
				request.setAttribute(VAR_TAMANIO_LISTA, sujetos.size());
				request.setAttribute(VAR_PATRONES_ASOCIADOS, sujetos);
				session.setAttribute(KEY_PATRONES_RECUPERAR, sujetos);
			} else {
				request.setAttribute(VAR_TAMANIO_LISTA, 0);
				request.setAttribute(VAR_PATRONES_ASOCIADOS, sujetos);
				session.setAttribute(KEY_PATRONES_RECUPERAR, new ArrayList<SujetoObligado>());
			}				
			session.setAttribute(KEY_SOLICITUD, solicitud);
		} catch(Exception e) {
			solicitud = new Solicitud();
			request.setAttribute(KEY_ERROR, MSG_ERROR_RETOMAR_SOLICITUD);
		}		
		model.addAttribute(ATRIBUTE_SOLICITUD, solicitud);
		return VIEW_CONTENIDO;
	}
	
	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/solicitud/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudRecuperacionTramite(
			HttpServletRequest request, HttpSession session) {		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		TramitePersonaAutorizada tramite = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute(KEY_PATRONES_RECUPERAR);		
		tramite.setSujetosObligados(sujetos);
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);		
		try {
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put(KEY_MENSAJE, MSG_ACTUALIZADA_SOLICITUD);
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_ACTUALIZAR_SOLICITUD);
		} catch (Exception e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_ACTUALIZAR_SOLICITUD);
		}
		
		return result;
	}
	
	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudRegistroRepresentado(
			HttpServletRequest request, HttpSession session) {		
		Map<String, Object> result = new HashMap<String, Object>();
		FirmaElectronica firmaElectronica = null;
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		TramitePersonaAutorizada tramite = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute(KEY_PATRONES_RECUPERAR);		
		tramite.setSujetosObligados(sujetos);
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);
		solicitud.setSolicitante(getUsuarioSession(session));
		try {
			if(esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud()))
				firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			
			sujetoObligadoServiceBusinessRemote.finalizarSolicitudRecuperacionRP(solicitud, firmaElectronica);
			result.put(KEY_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch(AbstractException e) {
			e.printStackTrace();
			result.put(KEY_MENSAJE, e.getMessage());
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			result.put(KEY_MENSAJE, "Error al finalizar la solicitud.");
			return result;
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
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudActualizacionDatos(
			@RequestBody Solicitud solicitud, HttpServletResponse response, HttpServletRequest request, 
			HttpSession session) {	
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitante(getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(ATRIBUTE_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_CANCELAR_SOLICITUD + e.getMessage());
		}	
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/agregarPatron", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> guardarPatron(@RequestBody SujetoObligado sujeto, 
			HttpSession session) {		
		Persona persona = (Persona) session.getAttribute(KEY_PERSONA_RELACIONAR);
		Map<String, Object> result= new HashMap<String, Object>();
		SujetoObligado sujetoSec = null;
		List<SujetoObligado> sujetosRecuperar = (List<SujetoObligado>)session
			.getAttribute(KEY_PATRONES_RECUPERAR);
		sujetosRecuperar = sujetosRecuperar == null 
			? new ArrayList<SujetoObligado>() : sujetosRecuperar;
		Boolean existeRelacion = false;				
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(persona.getIdPersona());
			fisica.setRfc(persona.getRfc());			
			sujeto.setFisica(fisica);
		} else {
			Moral moral = new Moral();
			moral.setIdPersona(persona.getIdPersona());
			moral.setRfc(persona.getRfc());			
			sujeto.setMoral(moral);
		}		
		try {
			sujetoSec  = sujetoObligadoServiceBusinessRemote.getSujetoObligadoPorMunImmsDeleSubdeRFC(sujeto);
		} catch (GestionPatronalBusinessException e) {			
			e.printStackTrace();
		}catch (RelacionConRegistroPatronalExisteException re) {
			re.printStackTrace();
			existeRelacion = true;
		}		
		if(sujetoSec == null) {
			if(existeRelacion) {
				result.put(KEY_ENCONTRADO, true);
			} else {
				result.put(KEY_ENCONTRADO, false);
			}			
			result.put(KEY_EXISTE_RELACION, existeRelacion);
		} else {
			boolean recuperado = false;
			result.put(KEY_ENCONTRADO, true);
			if(sujetoSec.getIndPatronConfirmado() != null && sujetoSec.getIndPatronConfirmado().equals(1)) {
				recuperado = true;
			}
			else {
				if(!sujetosRecuperar.isEmpty()) {
					boolean duplicado = false;
					for(SujetoObligado sujetoE : sujetosRecuperar) {
						if(sujetoE.getCveIdSujetoObligado().equals(sujetoSec.getCveIdSujetoObligado())) {
							duplicado = true;
							break;
						}
					}					
					result.put(KEY_DUPLICADO, duplicado);					
					if(!duplicado){
						sujetosRecuperar.add(sujetoSec);
						result.put(KEY_SUJETO, sujetoSec);
					}
				} else {
					sujetosRecuperar.add(sujetoSec);
					result.put(KEY_DUPLICADO, false);
					result.put(KEY_SUJETO, sujetoSec);
				}
			}			
			result.put(KEY_RECUPERADO, recuperado);
		}		
		session.setAttribute(KEY_PATRONES_RECUPERAR, sujetosRecuperar);
		
		return result;
	}
	
	@RequestMapping(value = "/getRpsRecuperar")
	public @ResponseBody Map<String,Object> getPatronesARecuperar(HttpServletRequest request, 
			HttpSession session) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("patrones", session.getAttribute(KEY_PATRONES_RECUPERAR));
		
		return result;
	}
	
	@RequestMapping(value = "/formNombreComercial/{cvePatron}")
	public String mostrarFormularioNombreC(@PathVariable Long cvePatron, Model model, 
			HttpServletRequest request, HttpSession session) {
		model.addAttribute("cvePatron", cvePatron);
		
		return VIEW_NOMBRE_COMERCIAL;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setNombreComercial", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> guardarNombreComercial(@RequestBody SujetoObligado sujeto, 
			HttpSession session) {
		Map<String, Object> result= new HashMap<String, Object>();
		List<SujetoObligado> sujetosRecuperar = (List<SujetoObligado>)session
			.getAttribute(KEY_PATRONES_RECUPERAR);
		List<SujetoObligado> aux = new ArrayList<SujetoObligado>();
		Boolean guardado = false;		
		for(SujetoObligado sujetoR: sujetosRecuperar) {
			if(sujetoR.getCveIdSujetoObligado().equals(sujeto.getCveIdSujetoObligado())) {
				sujetoR.setNombreComercial(sujeto.getNombreComercial());
				guardado = true;
			}			
			aux.add(sujetoR);
		}		
		sujetosRecuperar = aux;
		session.setAttribute(KEY_PATRONES_RECUPERAR, sujetosRecuperar);
		result.put("nombreComercial", sujeto.getNombreComercial());
		result.put("guardado", guardado);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarPatron", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> eliminarPatron(@RequestBody SujetoObligado sujeto, 
			HttpSession session) {		
		Map<String, Object> result= new HashMap<String, Object>();
		List<SujetoObligado> sujetosRecuperar = (List<SujetoObligado>)session
			.getAttribute(KEY_PATRONES_RECUPERAR);
		List<SujetoObligado> sujetosAux = new ArrayList<SujetoObligado>();		
		for(SujetoObligado sujetoObligado: sujetosRecuperar) {
			if(!sujetoObligado.getCveIdSujetoObligado().equals(sujeto.getCveIdSujetoObligado())) {
				sujetosAux.add(sujetoObligado);
			}
		}
		session.setAttribute(KEY_PATRONES_RECUPERAR, sujetosAux);
		result.put(VAR_PATRONES_ASOCIADOS,sujetosAux);	
		
		return result;
	}
	
	/**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody SujetoObligado oForm, 
    		final HttpServletResponse response) {
        log.trace("entramos a WizardRecuperacionPAtronalController para validar el objeto de formulario --> " 
        	+ ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));        
        final Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        new SujetoObligadoValidator().validate(oForm, errors);        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
    
    @RequestMapping(value = "/nombreComercial/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormularioNoombreComercial(final 
    		@RequestBody SujetoObligado oForm, final HttpServletResponse response) {
        log.trace("entramos a WizardRecuperacionPAtronalController para validar el objeto de formulario --> " 
        	+ ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));        
        final Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        if(oForm.getNombreComercial() == null || oForm.getNombreComercial().trim().length() == 0) {
        	errors.rejectValue("nombreComercial", "field.required");
        }        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);
        
        return result;
    }
    
	@RequestMapping("/limpiar-sesion")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {		
		session.removeAttribute(KEY_PATRONES_RECUPERAR);
		session.removeAttribute(KEY_PERSONA_RELACIONAR);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_TIPO_TRAMITE);		
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);		
		session.removeAttribute(KEY_USUARIO_SSO);
		
		return null;
	}

	private Solicitud crearSolicitudRecuperacion(Persona persona, HttpServletRequest request, 
			HttpSession session) throws SolicitudNoValidaException {
		Date fechaActual = new Date();
		Long idOrigen =  getOrigenInternetContext(request);
		Usuario usuario = getUsuarioSession(session);
		
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
		TramitePersonaAutorizada tramitePA =new TramitePersonaAutorizada();
		tramitePA.setSujetosObligados(new ArrayList<SujetoObligado>());		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(persona.getIdPersona());
			fisica.setRfc(persona.getRfc());			
			tramitePA.setPersonaFisica(fisica);
		} else {
			Moral moral = new Moral();
			moral.setIdPersona(persona.getIdPersona());
			moral.setRfc(persona.getRfc());			
			tramitePA.setPersonaMoral(moral);
		}
		tramitePA.setEstadoTramite(new EstadoTramite());
		tramitePA.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramitePA.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramitePA.setTipoTramite(new TipoTramite());
		tramitePA.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL.getCodigo());
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
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
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
		contenidoAFirmar.append("Registro Patronal:||");
		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:|");
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());
		// RFC
		datosAcuse.setRfc(persona.getRfc());
		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		datosAcuse.setNombreCompleto(sbnombre.toString());
		// CURP
		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica)persona).getCurp());
		}
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}

	private boolean validarSolicitudMismoOrigen(Solicitud solicitud, Long origenSolicitud){
		boolean solicitudMismoOrigen = false;
		if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(origenSolicitud)){
			solicitudMismoOrigen = true;
		}
		return solicitudMismoOrigen;		
	}
	
	private boolean esSolicitudInternet(Long origenSolicitud){
		boolean esInternet=false;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
		if(origen.equals(OrigenSolicitudEnum.INTERNET)){
			esInternet = true;
		}
		return esInternet;		
	}

	private Usuario getUsuarioSession(HttpSession session){
		Fisica personaSesion = (Fisica)session.getAttribute(DATOS_PERSONA_SESION_KEY);
		UsuarioSSO sso = (UsuarioSSO)session.getAttribute(KEY_USUARIO_SSO);
		if(personaSesion!=null){
			Usuario usuario = new Usuario();
			usuario.setUsuario(personaSesion.getCurp());
			usuario.setFisica(personaSesion);
			if(sso!=null && sso.getSubdelegacion()!=null){
				usuario.setUsuarioFuncionario(new UsuarioFuncionario());
				usuario.getUsuarioFuncionario().setSubdelegacion(new Subdelegacion());				
				usuario.getUsuarioFuncionario()
					.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
			return usuario;
		}
		return null;
	}

	private Long getOrigenInternetContext(HttpServletRequest request){
		return OrigenSolicitudEnum.INTERNET.getId();
	}
	
}
