package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.internet.wizard.WizardClasificacionMovPatInternetUtil;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.DELTA_SESSION_VARIABLES;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

@Controller
@RequestMapping(value="/movPat/ventanilla/tramite")
public class MovPatVentanillaController extends AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(MovPatVentanillaController.class);
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@Autowired
	private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestosRemote; 
	
	private String DESC_TIPO_SOLICITUD = "MODIFICACION AL SRT";
	
	@RequestMapping(value = "/registar", method = {RequestMethod.GET, RequestMethod.POST})
	public String registrarTramite(Model model, HttpSession session, HttpServletRequest request) {
		log.debug("entre al metodo registrarTramite");
		String forward ="pantallaTramites";
		return forward;
		
	}

	@RequestMapping(value="/tramiteVentanilla", method=RequestMethod.GET)
    public String consultaRegistroPatronal(Model model) {
		String registroPatronal = ""; 
        model.addAttribute("registroPatronal", registroPatronal);
        return "registrar.tramite.ventanilla";
    }
	
	@RequestMapping(value = "/validaRegistroPatronalExistente", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validaRegistroPatronalExistente(
			@RequestBody SujetoObligado inputObject,
			Model model, 
			HttpSession session, Locale locale) {
		
//		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		System.err.println("Evaluando seleccion de solicitud......... ");
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		inputObject = sujetoObligadoService.consultarPorNumeroRegistroPatronal(inputObject.getNumeroRegistroPatronal());
		
		if(inputObject == null){
			Object[] args = null;
			String mensaje = messageSource.getMessage("error.registro.patronal.inexistente.movPat", args, locale);
			result.put("mensajeError", mensaje);
			return result;
		}
		Usuario usaurio = (Usuario)session.getAttribute(DELTA_SESSION_VARIABLES.KEY_USUARIO_SESION);
		log.debug("el id subdelegacion usuario " + usaurio.getCveIdSubdelegacion().intValue() + " y la subdelegacion "
				+ "del patron " + inputObject.getSubdelegacion().getId().intValue() );
		if(inputObject.getSubdelegacion().getId().intValue() != usaurio.getCveIdSubdelegacion().intValue()){
			Object[] args = new Object[]{inputObject.getSubdelegacion().getDescripcion()};
			String mensaje = messageSource.getMessage("error.registro.patronal.externo", args, locale);
			result.put("mensajeError", mensaje);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/folio", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> getFolio(Model model, HttpSession session,
											HttpServletRequest request,
											Locale locale,
											@RequestBody String folio) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("getFolio :: {}", folio);
		
		String registroPatronalCompleto = "";
		String mensaje = "";
		
		try {
			
			Usuario usuarioSession = (Usuario)session.getAttribute(KEY_USUARIO);
			CitaSolicitud citaSolicitud = getCitaSolicitud(folio);
								
			if (citaSolicitud == null) {
				result.put("mensajeError", messageSource.getMessage("movPat.ventanilla.folio.error.inexistente", null, locale));
				return result;
			}
			
			if (usuarioSession != null && usuarioSession.getCveIdSubdelegacion() != null) {
				if (usuarioSession.getCveIdSubdelegacion().intValue() != citaSolicitud.getSubdelegacion().getId()) {
					result.put("mensajeError", messageSource.getMessage("movPat.ventanilla.folio.error.subdelegacion", null, locale));
					return result;					
				}
			}
			
															
			Solicitud solicitud = obtieneSolicitud(citaSolicitud.getRefFolioCita());
			
			log.debug(":: idSolicitud :: {}", solicitud.getSolicitudId());
			log.debug(":: TipoSolicitud :: {}", solicitud.getTipoSolicitud().getDescripcion());
			log.debug(":: Registro Patrona:: {}", solicitud.getSujetoObligado().getNumeroRegistroPatronal());				
			log.debug(":: getNumModalidad:: {}", solicitud.getSujetoObligado().getModalidad().getNumModalidad());
			log.debug(":: getDesCorta:: {}", solicitud.getSujetoObligado().getModalidad().getDesCorta());
			log.debug(":: getDescripcion:: {}", solicitud.getSujetoObligado().getModalidad().getDescripcion());				
			log.debug(":: Modalidad:: {}", solicitud.getSujetoObligado().getModalidad().getNumModalidad());
			log.debug(":: idEstadoSolicitud :: {}", solicitud.getEstadoSolicitud().getIdEstadoSolicitud());
			
			if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud().intValue() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {	
				
				
				String fechaCita = WizardClasificacionMovPatInternetUtil.formatDateddMMMMyyyy(citaSolicitud.getFechaHora());
				Calendar ahora = Calendar.getInstance();
				ahora.set(Calendar.HOUR_OF_DAY, 0);
				ahora.set(Calendar.MINUTE, 0);
				ahora.set(Calendar.SECOND, 0);
				ahora.set(Calendar.MILLISECOND, 0);
												
				if (citaSolicitud.getFechaHora().before(ahora.getTime())) {
					result.put("mensajeError", messageSource.getMessage("movPat.ventanilla.folio.error.fechaFolio", new Object[]{fechaCita}, locale));	
					return result;		
				}
				
				Modalidad modalidad = sujetoObligadoService.getModalidadPatron(solicitud.getSujetoObligado().getCveIdSujetoObligado());
				
				if (modalidad != null && modalidad.getNumModalidad() != null) {
					solicitud.getSujetoObligado().setModalidad(modalidad);
					
					registroPatronalCompleto = solicitud.getSujetoObligado().getNumeroRegistroPatronal() +
							solicitud.getSujetoObligado().getModalidad().getNumModalidad();	
				} else {
					registroPatronalCompleto = solicitud.getSujetoObligado().getNumeroRegistroPatronal() + "10";
				}
										
				result.put("registroPatronal", registroPatronalCompleto);
				result.put("idSolicitud", solicitud.getSolicitudId());
				result.put("idTramite", solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
				
				initDatos(model, session, request, registroPatronalCompleto, TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor());
				
			} else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().intValue() == EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue()) {
				mensaje = messageSource.getMessage("movPat.ventanilla.folio.error.atendido", null, locale);
				result.put("mensajeError", mensaje);
			} else {			
				mensaje = messageSource.getMessage("movPat.ventanilla.folio.error.estado", null, locale);
				result.put("mensajeError", mensaje);					
			}
				
			
		} catch (Exception e) {
			mensaje = messageSource.getMessage("movPat.ventanilla.folio.error.busqueda.exception", null, locale);;
			log.error(mensaje + " {}", e.getMessage());
			result.put("mensajeError", mensaje);
		}

		return result;
	}
	
	
	private Solicitud obtieneSolicitud(String folio) {
		Solicitud solicitud = new Solicitud();

		try {
			solicitud = solicitudServiceBusiness.consultarSolicitudPorFolio(folio);				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			solicitud = null;
		}
		return solicitud ;
	}
	
	private CitaSolicitud getCitaSolicitud(String folioCita) {
		CitaSolicitud citaSolicitud = new CitaSolicitud();
		try {
			citaSolicitud.setRefFolioCita(folioCita);
			citaSolicitud = solicitudServiciosExpuestosRemote.consultaCItaSOlicitud(citaSolicitud); 			
		} catch (Exception e) {
			log.error("Error al consultar la cita para el folio {} {} ", folioCita, e.getMessage());
			return null;
		}
		return citaSolicitud; 
	}
	
	private void initDatos(Model model, HttpSession session, HttpServletRequest request, String numeroRegistroPatronal, Integer tipoTramite) {
			log.debug("::: En initModificarDatosPersona, numeroRegistroPatronal: " + numeroRegistroPatronal);
			String view;
			limpiarSesion(session);
			// Se busca si existen solicitudes en proceso pendientes
			boolean existenPendientes = false;
			boolean existenEnProceso = false;
			boolean modalidadValida = false;
			boolean existeMsjBuzon = false;
			SujetoObligado sujetoTramite = new SujetoObligado();
			BuzonClasificacion buzon = null;
			String msjBuzon = "";

			sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
	        log.debug("numeroRegistroPatronal: {}", numeroRegistroPatronal);
			sujetoTramite = sujetoObligadoService.obtenerSujetoObligadoActividadEconomica(sujetoTramite);
	        log.debug("sujeto tramite obtenido");
			
	        Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
	        DESC_TIPO_SOLICITUD = this.getDescripcionTipoTramite(tipoTramite);
			TipoSolicitudEnum tipoSolicitud;
			TipoTramiteEnum tipoTramiteEnum=null;
	
			tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;

			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD, tipoSolicitud.getValor());
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);

			Solicitud solicitudEnCaptura = solicitudServiceBusiness
					.obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario, tipoTramiteEnum);
			
			if (solicitudEnCaptura == null) {
				solicitudEnCaptura = solicitudServiceBusiness
						.obtenerSolicitudActiva(sujetoTramite, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, usuario, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
			}
			
			log.debug("solicitudEnCaptura :" + solicitudEnCaptura);
			if (solicitudEnCaptura != null) {
				Tramite tramiteVigente = solicitudEnCaptura.getTramites().get(0);
				model.addAttribute("idSolicitud", solicitudEnCaptura.getSolicitudId());
				model.addAttribute("folioSolicitud", solicitudEnCaptura.getNoFolioSolicitud());
				session.setAttribute("folioSolicitud", solicitudEnCaptura.getNoFolioSolicitud());
				model.addAttribute("idTipoTramite", tramiteVigente.getTipoTramite().getIdTipoTramite());
				model.addAttribute("sujetoTramite", sujetoTramite);
				model.addAttribute("desTipoTramiteVigente", tramiteVigente.getTipoTramite().getDescripcion());
				existenPendientes = true;
				session.setAttribute("keyPatronesSustitucionFusion", ((TramiteSujetoObligado) tramiteVigente).getSujetoObligado().getSujetosObligados());
			} else {
				Solicitud solicitudProceso = solicitudServiceBusiness
						.obtenerSolicitudEnProceso(sujetoTramite, tipoSolicitud, tipoTramiteEnum);
				
				if (solicitudProceso == null) {
					solicitudProceso = solicitudServiceBusiness
							.obtenerSolicitudEnProceso(sujetoTramite, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
				}

				if (solicitudProceso != null) {
					Tramite tramiteVigente = solicitudProceso.getTramites().get(0);

					model.addAttribute("idSolicitud", solicitudProceso.getSolicitudId());
					model.addAttribute("folioSolicitud", solicitudProceso.getNoFolioSolicitud());
					session.setAttribute("folioSolicitud", solicitudProceso.getNoFolioSolicitud());
					model.addAttribute("idTipoTramite", tramiteVigente.getTipoTramite().getIdTipoTramite());
					model.addAttribute("sujetoTramite", sujetoTramite);
					model.addAttribute("desTipoTramiteVigente", tramiteVigente.getTipoTramite().getDescripcion());
					existenEnProceso = true;
					session.setAttribute("keyPatronesSustitucionFusion", ((TramiteSujetoObligado) tramiteVigente).getSujetoObligado().getSujetosObligados());
				}

			}
			
			

			model.addAttribute("sujetoTramite", sujetoTramite);
			
			request.setAttribute("existenPendientes", existenPendientes);
			request.setAttribute("existenEnProceso", existenEnProceso);
			request.setAttribute("modalidadValida", modalidadValida);
			request.setAttribute("existeMsjBuzon", existeMsjBuzon);
			request.setAttribute("msjBuzon", msjBuzon);		
	}
	
	private void limpiarSesion(final HttpSession session) {
		session.removeAttribute("keyPatronesSustitucionFusion");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD);
		session.removeAttribute("folioSolicitud");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO);
		session.removeAttribute("showFinalizarFD");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE);
		session.removeAttribute("mensajeError");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_CADENA_ORIGINAL);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_FIRMA_ELECTRONICA);
	}
	
	private String getDescripcionTipoTramite(Integer idTramite) {
		TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(idTramite);
		String descripcionTipoTramite = "MODIFICACION AL SRT";
		switch (tipoTramite) {
			case ACTIVIDAD_ECONOMICA: 
				descripcionTipoTramite = "Cambio de actividad";
				break;
			case DISPOSICION_DE_LEY: 
				descripcionTipoTramite = "Cambio por disposicion de Ley, o del RACERF";
				break;
			case INCORPORACION_DE_ACTIVIDADES: 
				descripcionTipoTramite = "Incorporacion de actividades";
				break;
			case COMPRA_DE_ACTIVOS: 
				descripcionTipoTramite = "Compra de activos";
				break;
			case COMODATO: 
				descripcionTipoTramite = "Comodato";
				break;
			case ENAJENACION: 
				descripcionTipoTramite = "Enajenacion";
				break;
			case ARRENDAMIENTO: 
				descripcionTipoTramite = "Arrendamiento";
				break;
			case FIDEICOMISO_TRASLATIVO: 
				descripcionTipoTramite = "Fideicomiso traslativo";
				break;
			case ACTUALIZACION_CENTRO_TRABAJO:
				descripcionTipoTramite = "Cambio de domicilio";
				break;
			case FUSION: 
				descripcionTipoTramite = "Fusi&oacute;n";
				break;
			case ESCISION: 
				descripcionTipoTramite = "Escisi&oacute;n";
				break;
			case REANUDACION_DE_ACTIVIDADES: 
				descripcionTipoTramite = "Reanudaci&oacute;n de actividades";
				break;
			case SUSTITUCION_PATRONAL: 
				descripcionTipoTramite = "Sustituci&oacute;n patronal";
				break;
			case SUSTITUCION_PATRONAL_SUBCONTRATACION: 
				descripcionTipoTramite = "Sustituci&oacute;n patronal por subcontrataci&oacute;n";
				break;
			case CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO: 
				descripcionTipoTramite = "Modificaci&oacute;n en SRT por cambio de domicilio diferentes municipios";
				break;
				
			default:
				break;
		}
		
		log.debug("MSRT - El tipo de tramite es "+idTramite+"\nLa descripcion del tramite es " + descripcionTipoTramite);
	
		
		return descripcionTipoTramite;
	}

}

