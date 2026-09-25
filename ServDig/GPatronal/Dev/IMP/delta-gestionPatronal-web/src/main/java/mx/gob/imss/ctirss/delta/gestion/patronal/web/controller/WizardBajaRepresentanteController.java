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
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
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
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
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

@Controller
@RequestMapping("/wizard/tramite/representante/baja")
public class WizardBajaRepresentanteController extends AbstractController {

	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired 
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@Autowired 
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired 
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired 
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	//Variables de sesion
	private static final String REPRESENTADOS_KEY = "datosSujetosObligados";
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	private static final String DATOS_EMPRESA_KEY = "datosEmpresa";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_ID_PERSONA = "idPersonaDelTramite";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String KEY_RFC_PERSONA_SESION = "rfcPersonaSesion";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String DESC_TIPO_SOLICITUD = "BAJA DE REPRESENTANTE LEGAL";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_ERROR = "error";
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_REPRESENTANTES_LEGALES = "representantesLegales";
	private static final String KEY_MENSAJE = "mensaje";
	private static final String KEY_RLS_SELECCIONADOS = "rLSeleccionados";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	//Pantallas
	private static final String VIEW_BAJA_REPRESENTANTE_INIT = "wizardBajaRepresentanteLegalInit";
	private static final String VIEW_BAJA_REPRESENTANTE_CONTENT = "wizardBajaRepresentanteLegalContenido";
	//Mensajes
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurrió un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurrió un error al localizar información de la persona.";
	private static final String MSG_ERROR_NO_RLS = "No hay representantes legales candidatos a ser dados de baja.";
	private static final String MSG_ERROR_NO_TRAMITE_RL = "La solicitud no cuenta con un tramite de baja de representante legal.";
	private static final String MSG_ERROR_RETOMAR_SOLICITUD = "Error al retomar la solicitud.";	
	private static final String MSG_ERROR_ACTUALIZAR_SOLICITUD = "Ocurri&oacute; un error al intentar guardar los cambios";
	private static final String MSG_ERROR_CANCELAR_SOLICITUD = "Hubo un error al cancelar la solicitud: ";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	
	@RequestMapping(value="/{idPersonaFM}/{idTipoPersona}/{rfcPersona}/{idPersona}")
	public String initWizardRegistroPersonaAutorizada(Model model, HttpServletRequest request, HttpSession session,
			@PathVariable Long idPersonaFM, @PathVariable Long idTipoPersona, @PathVariable String rfcPersona, 
			@PathVariable Long idPersona){
		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		//Establecemos los datos de la empresa
		Persona persona = new Persona();
		persona.setIdPersona(idPersonaFM);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		persona.setRfc(rfcPersona);		
		Solicitud solicitudActiva = new Solicitud();
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		
		//Establecemos en sesion los dados de la empresa
		session.setAttribute(DATOS_EMPRESA_KEY, persona);
		session.setAttribute(KEY_ID_PERSONA, idPersona);		
		//Verificamos que tipo de persona es para buscar en tramite persona moral o en tramite persona fisica
		TipoPersonaEnum tipoPersona = idTipoPersona.equals(TipoPersonaEnum.FISICA.getId()) ? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL ;
		//Se busca la solicitud de tipo actualizacion de datos patronales
		try {
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(KEY_RFC_PERSONA_SESION, personaSesion.getRfc());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_USUARIO_SSO, sso);

			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, 
					tipoPersona.getId(), TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, 
					TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL);
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona,
					tipoPersona.getId(), TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, 
					TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL);
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
		listTipoTramite.add(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo());
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		request.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);		
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
				
		return VIEW_BAJA_REPRESENTANTE_INIT;
	}
	
	@RequestMapping(value = "/iniciarTramite")
	public String iniciarTramite(Model model, HttpServletRequest request, HttpSession session) {
		Persona persona = (Persona) session.getAttribute(DATOS_EMPRESA_KEY);
		Long idPersona = (Long) session.getAttribute(KEY_ID_PERSONA);
		String error = null;
		Solicitud solicitud = null;
		Long idRepresentante = 0L;		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		
		if(new CommonValidator().esSolicitudInternet(idOrigen)){
			UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
			idRepresentante = usuarioSSO.getIdPersona().longValue();
		}else{
			idRepresentante = idPersona; 
		}		
		//Buscamos a los representados	
		List<RepresentanteLegal> representados = this.getRepresentantesChecked(
			persona, null, idRepresentante, session);				
		if(representados == null || representados.isEmpty()) {
			error = MSG_ERROR_NO_RLS;
		}		
		if(error != null) {
			model.addAttribute(KEY_ERROR, error);			
		} else {
			try {
				Long idPersonaFisicaMoral = persona.getIdPersona();
				//establecemos el id de la persona
				persona.setIdPersona(idPersona);
				solicitud = this.crearSolicitudBajaRepresentanteLegal(persona, null, session, request);
				session.setAttribute(SOLICITUD_KEY, solicitud);
				//volvemos a poner el id de le persona fisica
				persona.setIdPersona(idPersonaFisicaMoral);				
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					Fisica personaRecuperadaSesion =(Fisica) session
						.getAttribute(DATOS_PERSONA_SESION_KEY);
					// Datos del acuse					
					obtenerDatosAcuse(solicitud, personaRecuperadaSesion, session);
					// Datos para la firma digital
					if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
						Fisica personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
						generarCadenaOriginal(solicitud, personaRecuperada, session);
					} else {
						Moral personaRecuperada = personaMoralBusiness.getPersonaMoral(idPersona);
						generarCadenaOriginal(solicitud, personaRecuperada, session);
					}
					session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperadaSesion.getRfc());
				}				
			} catch (AbstractException e) {
				e.printStackTrace();
				model.addAttribute(KEY_ERROR,e.getSituacion());
				return VIEW_BAJA_REPRESENTANTE_CONTENT;
			}						
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_REPRESENTANTES_LEGALES,representados);
			model.addAttribute(KEY_RETOMAR, false);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());			
		}		
		return VIEW_BAJA_REPRESENTANTE_CONTENT;
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
		
		model.addAttribute(KEY_RETOMAR, true);		
		//Recuperamos a la empresa o persona a asociar
		Persona empresa = (Persona) session.getAttribute(DATOS_EMPRESA_KEY);
		Long idPersona = (Long) session.getAttribute(KEY_ID_PERSONA);
		List<RepresentanteLegal> representantes = null;
		List<RepresentanteLegal> checados = null;
		String error = null;		
		try {
			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				// Datos para el acuse
				Fisica personaRecuperadaSesion =(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperadaSesion, session);
				session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperadaSesion.getRfc());
				// Datos para la firma digital
				if (empresa.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
					Fisica personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
					generarCadenaOriginal(solicitud, personaRecuperada, session);
				} else {
					Moral personaRecuperada = personaMoralBusiness.getPersonaMoral(idPersona);
					generarCadenaOriginal(solicitud, personaRecuperada, session);
				}
			}
			//Se verifica que la solicitud contenga algun tramite
			if(solicitud.getTramites().isEmpty()) {
				error = MSG_ERROR_NO_TRAMITE_RL;
			} else {
				session.setAttribute(SOLICITUD_KEY, solicitud);
				Tramite tramite = solicitud.getTramites().get(0);
				if(tramite instanceof TramiteFisica) {
					checados = ((TramiteFisica) tramite).getFisica().getRepresentantesLegales();
				} else {
					checados = ((TramiteMoral) tramite).getMoral().getRepresentantesLegales();
				}
			}
		} catch(Exception e) {
			error = MSG_ERROR_RETOMAR_SOLICITUD;
		}		
		try {
			Long idRepresentante = 0L;
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
				idRepresentante = usuarioSSO.getIdPersona().longValue();
			}else{
				idRepresentante = idPersona;
			}
			representantes = this.getRepresentantesChecked(empresa,checados,idRepresentante, session);			
		} catch(Exception e) {
			e.printStackTrace();
			error = "Error en los datos";
		}		
		//Si no hubo error
		if(error == null) {
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			request.setAttribute(KEY_REPRESENTANTES_LEGALES, representantes);
		} else{
			model.addAttribute(KEY_ERROR, error);
		}		
		return VIEW_BAJA_REPRESENTANTE_CONTENT;
	}
	
	/**
	 * 
	 * @param sujetosO
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/solicitud/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> guardarSolicitud(@RequestBody Persona persona, 
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//recuperamos la solicitud de sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);		
		List<RepresentanteLegal> seleccionados = persona.getRepresentantesLegales();
		Tramite tramite = solicitud.getTramites().get(0);		
		if(tramite instanceof TramiteFisica) {
			TramiteFisica tramiteF = (TramiteFisica) tramite;
			tramiteF.getFisica().setRepresentantesLegales(
				this.setRepresentantesSeleccionados(seleccionados, session));			
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteF);
		} else {
			TramiteMoral tramiteM = (TramiteMoral) tramite;
			tramiteM.getMoral().setRepresentantesLegales(
				this.setRepresentantesSeleccionados(seleccionados, session));			
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteM);
		}		
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
	
	@RequestMapping(value = "/validarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarSolicitud(@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		if(!new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
			Persona empresa = (Persona) session.getAttribute(DATOS_EMPRESA_KEY);
			if(empresa != null && empresa.getTipoPersona() !=null && empresa.getTipoPersona().getIdTipoPersona()!= null 
					&& empresa.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.MORAL.getId())){
				//Verifica que quede activo 1 RL en Ventanilla para Personas Morales.
				List<RepresentanteLegal> seleccionados = persona.getRepresentantesLegales();				
				Long idPersona = (Long) session.getAttribute(KEY_ID_PERSONA);
				List<RepresentanteLegal> totalRLs = getRepresentantesChecked(empresa,null,idPersona, session);
				if(!CollectionUtils.isEmpty(seleccionados) && !CollectionUtils.isEmpty(totalRLs)){
					int restantesRL = totalRLs.size()-seleccionados.size();
					if(restantesRL<=0){
						result.put(KEY_ERROR, "Debe quedar activo un Representante Legal.");
						return result;
					}
				}
			}
		}
		result.put(KEY_MENSAJE, "Exito");
		return result;
	}
	/**
	 * 
	 * @param sujetosO
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/solicitud/finalizar/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> finalizarSolicitud(@PathVariable Long idSolicitud,
			@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//Obtenemos la solicitud de la sesion
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		List<RepresentanteLegal> seleccionados = persona.getRepresentantesLegales();
		try {
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			
			Tramite tramite = solicitud.getTramites().get(0);		
			if(tramite instanceof TramiteFisica) {
				TramiteFisica tramiteF = (TramiteFisica) tramite;
				tramiteF.getFisica().setRepresentantesLegales(
					this.setRepresentantesSeleccionados(seleccionados, session));			
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(tramiteF);
			} else {
				TramiteMoral tramiteM = (TramiteMoral) tramite;
				tramiteM.getMoral().setRepresentantesLegales(
					this.setRepresentantesSeleccionados(seleccionados, session));			
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(tramiteM);
			}		
			
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			FirmaElectronica firmaElectronica = null;
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
				solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, firmaElectronica);
				result.put(KEY_MENSAJE, MSG_FINALIZAR_SOLICITUD);
			}else{
				//No se finalizan solicitudes de Ventanilla ya que falta indicar el RL, 
				//posterior a ello se manda a encolar la consilicitud para concluir.
				solicitudBusinessRemote.actualizarTramites(solicitud);
				result.put(KEY_MENSAJE, MSG_ACTUALIZADA_SOLICITUD);
			}			
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
	@RequestMapping(value = "/solicitud/cancelar/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitud(@PathVariable Long idSolicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) {	
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			//Establecemos los nuevos estados de la solicitud
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			log.error(e);
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
		session.removeAttribute(DATOS_EMPRESA_KEY);
		session.removeAttribute(REPRESENTADOS_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_ID_PERSONA);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
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
	@RequestMapping(value = "/get/seleccionados", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> getDatosRepresentadosSeleccionados(
			@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RepresentanteLegal> seleccionados = persona.getRepresentantesLegales();		
		List<RepresentanteLegal> datosComplementariosSeleccionados = this.setRepresentantesSeleccionados(seleccionados, session);
		result.put(KEY_RLS_SELECCIONADOS, datosComplementariosSeleccionados);		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<RepresentanteLegal> setRepresentantesSeleccionados(List<RepresentanteLegal> checados, HttpSession session) {
		List<RepresentanteLegal> seleccionados = null;
		List<RepresentanteLegal> representantes = (List<RepresentanteLegal>) session.getAttribute(REPRESENTADOS_KEY);		
		if(checados != null && !checados.isEmpty()) {
			seleccionados = new ArrayList<RepresentanteLegal>();
			
			representantes: for(RepresentanteLegal representante: representantes) {
				for(RepresentanteLegal checado: checados) {
					if(checado.getCveIdRepresentanteLegal().equals(representante.getCveIdRepresentanteLegal())) {
						if(representante.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
							Fisica personaRecuperada = personaFisicaServiceBusiness
								.getPersonaFisica(representante.getCveIdPersona()); //Es el idPersonaFisica pero se pasa en la propiedad 
							representante.setPersonaFisicaRepresentada(personaRecuperada);							
						}else if(representante.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
							Moral personaRecuperada = personaMoralBusiness.getPersonaMoral(representante.getCveIdPersona());
							representante.setPersonaMoralRepresentada(personaRecuperada);
						}
						seleccionados.add(representante);
						continue representantes;
					}
				}
			}
		}		
		return seleccionados;
	}
	
	private List<RepresentanteLegal> getRepresentantesChecked(Persona persona, List<RepresentanteLegal> checados, 
			Long idRepresentante, HttpSession session) {
		List<RepresentanteLegal> representados = null;
		List<RepresentanteLegal> finales = null;		
		representados = this.getRepresentantes(persona);
		session.setAttribute(REPRESENTADOS_KEY, representados);		
		if(representados != null && !representados.isEmpty()) {
			if(checados != null) {
				
				finales = new ArrayList<RepresentanteLegal>();
				for(RepresentanteLegal rpl: representados) {
					for(RepresentanteLegal checado: checados)  {
						if(rpl.getCveIdRepresentanteLegal().equals(checado.getCveIdRepresentanteLegal())) {
							rpl.setChecked(true);
							break;
						}						
						rpl.setChecked(false);
					}					
					if(!rpl.getPersonaFisica().getIdPersona().equals(idRepresentante)) {
						finales.add(rpl);
					}
				}
			} else {
				finales = new ArrayList<RepresentanteLegal>();
				for(RepresentanteLegal rpl: representados) {
					rpl.setChecked(false);
					if(!rpl.getPersonaFisica().getIdPersona().equals(idRepresentante)) {
						finales.add(rpl);
					}
				}
			}
		}		
		return finales;
	}
	
	private List<RepresentanteLegal> getRepresentantes(Persona persona) {
		List<RepresentanteLegal> representantes = null;
		TipoPersonaEnum tipoPersona = persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId()) 
			? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL;
		representantes = representanteLegalServiceBusinessRemote
			.obtenerRepresentantesLegalesPorPersona(persona.getIdPersona(), tipoPersona);
		
		return representantes;
	}
	
	private Solicitud crearSolicitudBajaRepresentanteLegal(Persona persona,List<RepresentanteLegal> representantes, 
			HttpSession session, HttpServletRequest request) throws SolicitudNoValidaException {
		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum.getById(idOrigen);
		Date fechaActual = new Date();
		Usuario usuario = new CommonValidator().getUsuarioSession(session);		
		Solicitud solicitud = new Solicitud();
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(origenSolicitud.getId());
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			TramiteFisica tramitePA = new TramiteFisica();			
			Fisica personaFisica = new Fisica();
			personaFisica.setIdPersona(persona.getIdPersona());
			personaFisica.setRfc(persona.getRfc());
			personaFisica.setRepresentantesLegales(representantes);				
			tramitePA.setFisica(personaFisica);		
			tramitePA.setEstadoTramite(new EstadoTramite());
			tramitePA.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			tramitePA.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
			tramitePA.setTipoTramite(new TipoTramite());
			tramitePA.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo());
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
		} else {
			TramiteMoral tramitePA = new TramiteMoral();			
			Moral moral = new Moral();
			moral.setIdPersona(persona.getIdPersona());
			moral.setCveMoral(persona.getIdPersona());
			moral.setRfc(persona.getRfc());
			moral.setRepresentantesLegales(representantes);			
			tramitePA.setMoral(moral);		
			tramitePA.setEstadoTramite(new EstadoTramite());
			tramitePA.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			tramitePA.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
			tramitePA.setTipoTramite(new TipoTramite());
			tramitePA.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo());
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
		}
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
		contenidoAFirmar.append("Registro Patronal:|");
		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:||");
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
		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica)persona).getCurp());
		}
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}
	
}
