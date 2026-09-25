package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.InstanceofPredicate;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
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
@RequestMapping(value = "/wizard/tramite/bajaSocios/")
public class WizardBajaSociosController extends AbstractController {
	
	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired 
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	private SolicitudServiceBusinessRemote gpSolicitudService;
	@Autowired
	private SocioServiceBusinessRemote socioServiceBusinessRemote;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	//Pantallas
	private static final String INICIO_WIZARD_SOCIOS = "wizardInicioBajaSocio";
	private static final String CONTENIDO_WIZARD_SOCIOS = "wizardContenidoBajaSocio";
	
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_ATRIBUTE_SOLICITUD = "solicitud";
	private static final String KEY_ATRIBUTE_SOCIO = "socio";
	private static final String KEY_ATRIBUTE_LISTA_SOCIOS = "listaSocios";
	private static final String SOCIOS_TO_SESSION = "_sociosToSession";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesion";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String KEY_ATRIBUTE_ERROR = "error";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_RL_SELECCIONADOS = "rLSeleccionados";
	private static final String KEY_ID_SOLICITUD = "idSolicitud";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	//Mensajes
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente.";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente.";
	private static final String MSG_GUARDAR_SOLICITUD = "La solicitud fue actualizada correctamente.";
	private static final String MSG_CANCELAR_SOLICITUD_ERROR = "Error al cancelar la solicitud: ";
	private static final String MSG_GUARDAR_SOLICITUD_ERROR = "Error al guardar la solicitud: ";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	private static final String DESC_TIPO_TRAMITE_SOLICITUD = "BAJA DE SOCIOS";
	
	@RequestMapping(value="iniciarBaja/{rfc}/{idPersona}")
	public String initWizardAltaSocio(Model model, HttpServletRequest request, HttpSession session,
			@PathVariable String rfc, @PathVariable Long idPersona){
		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		Socio socio = new Socio();
		socio.setIdPersonaMoralPatron(idPersona);
		socio.setRfcPersonaMoralPatron(rfc);
		model.addAttribute(KEY_ATRIBUTE_SOCIO,socio);		
		Solicitud solicitudActiva = new Solicitud();
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		
		try {			
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Fisica personaSesion = serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);	
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, TipoPersonaEnum.MORAL.getId(), 
					TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, TipoTramiteEnum.BAJA_SOCIO);				
			if(solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona, TipoPersonaEnum.MORAL.getId(), 
					TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, TipoTramiteEnum.BAJA_SOCIO);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {			
			model.addAttribute(KEY_ATRIBUTE_ERROR, "Ocurrió un error al consultar las solicitudes pendientes.");
			log.error(e);
		} catch (PersonaFisicaNoEncontradaException ee) {
			model.addAttribute(KEY_ATRIBUTE_ERROR, "Ocurrió un error al localizar información de la persona.");
			log.error(ee);
		}		
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		request.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());		
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
				
		return INICIO_WIZARD_SOCIOS;
	}
	
	@RequestMapping(value = "generarBajaSocio", method = RequestMethod.POST)
	public String generarBajaSocio(@ModelAttribute Socio socio,
			final HttpSession session, HttpServletRequest request,
			final Model model) {

		String error = null;
		List<Socio> listaSocios = getSociosChecked(socio, null, session);		
		if(CollectionUtils.isEmpty(listaSocios)){			
			request.setAttribute(KEY_ATRIBUTE_ERROR, "No hay socios candidatos a ser dados de baja.");
		}else{
			
			Solicitud solicitud;
			try {
				//Registrar solicitud sin socios a dar de baja.
				Usuario usuario = new CommonValidator().getUsuarioSession(session);
				solicitud = socioServiceBusinessRemote.generarSolicitudBajaSocio(socio, null, 
					OrigenSolicitudEnum.getById(new CommonValidator().getOrigenContext(request)), usuario);
				session.setAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
					generarCadenaOriginalyFirma(solicitud, personaSesion, session);
				}				
				datosFijosSession(session);				
				model.addAttribute(KEY_ATRIBUTE_LISTA_SOCIOS, listaSocios);
				model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
				model.addAttribute(KEY_RETOMAR, false);
				model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			} catch (GestionPatronalBusinessException e) {
				log.error(e);
				error = e.getSituacion();
				request.setAttribute(KEY_ATRIBUTE_ERROR, error);				
			}
		}		
		return CONTENIDO_WIZARD_SOCIOS;
	}
	
	@RequestMapping("solicitud/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_SOLICITUD_FORM) Solicitud solicitud,
			HttpServletRequest request, HttpSession session) {
				
		solicitud = gpSolicitudService.consultarSolicitudPorId(solicitud.getSolicitudId());
		model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
		session.setAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
		InstanceofPredicate tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
		if (tramiteInicial != null) {
			//Recuperar datos XML detalle tramite
			TramiteSocios tramiteSocio = (TramiteSocios) tramiteInicial;
			Socio socio = new Socio();
			socio.setIdPersonaMoralPatron(tramiteSocio.getPatron().getIdPersona());
			//Recuperar posibles socios seleccionados
			List<Socio> listaSocios = tramiteSocio.getListaSocios();
			listaSocios = getSociosChecked(socio, listaSocios, session);
			model.addAttribute(KEY_ATRIBUTE_LISTA_SOCIOS, listaSocios);
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				generarCadenaOriginalyFirma(solicitud, personaSesion, session);	
			}			
			datosFijosSession(session);
			model.addAttribute(KEY_ATRIBUTE_SOCIO,socio);
		}
		request.setAttribute(KEY_RETOMAR, true);
		return CONTENIDO_WIZARD_SOCIOS;
	}
	
	@RequestMapping(value = "solicitud/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> guardarSolicitud(@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//recuperamos la solicitud de sesion
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_ATRIBUTE_SOLICITUD);
		List<Socio> seleccionados = persona.getSocios();			
		try {			
			solicitud = gpSolicitudService.consultarSolicitudPorId(solicitud.getSolicitudId());
			//Actualizar tramite socios
			InstanceofPredicate tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
			Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);	
			if (tramiteInicial != null) {				
				TramiteSocios tramiteSocio = (TramiteSocios) tramiteInicial;
				tramiteSocio.setListaSocios(seleccionados);
			}
			//Actualizamos la solicitud con los nuevos socios seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_GUARDAR_SOLICITUD);
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_GUARDAR_SOLICITUD_ERROR + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud,
			@RequestBody Persona persona, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();		
		FirmaElectronica firmaElectronica = null;
		List<Socio> seleccionados = persona.getSocios();
		Solicitud solicitud = new Solicitud(idSolicitud);
		try {
			solicitud = gpSolicitudService.consultarSolicitudPorId(solicitud.getSolicitudId());
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			//Actualizar tramite socios
			InstanceofPredicate tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
			Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
			if (tramiteInicial != null) {				
				TramiteSocios tramiteSocio = (TramiteSocios) tramiteInicial;
				tramiteSocio.setListaSocios(seleccionados);
			}			
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				firmaElectronica = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
				solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, firmaElectronica);
			}else{
				//No se finalizan solicitudes de Ventanilla ya que falta indicar el RL, 
				//posterior a ello se manda a encolar la consilicitud para concluir.
				solicitudBusinessRemote.actualizarTramites(solicitud);
				session.setAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
				session.setAttribute(KEY_ID_SOLICITUD, solicitud.getSolicitudId());
			}
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_FINALIZAR_SOLICITUD);			
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitud = new Solicitud();
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_ATRIBUTE_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD_ERROR + e.getMessage());
		}	
		return result;
	}
	
	@RequestMapping(value = "procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}
	
	
	@RequestMapping(value = "get/seleccionados", method = RequestMethod.POST)
	public @ResponseBody Map<String ,? extends Object> getDatosSocioSeleccionados(@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Socio> seleccionados = persona.getSocios();
		List<Socio> sociosSeleccionados = setSociosSeleccionados(seleccionados, session);
		result.put(KEY_RL_SELECCIONADOS, sociosSeleccionados);		
		return result;
	}
	
	@RequestMapping(value = "limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	Solicitud limpiarSesion(final HttpSession session) {
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);		
		session.removeAttribute(KEY_TIPO_TRAMITE);		
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(SOCIOS_TO_SESSION);
		session.removeAttribute(KEY_ATRIBUTE_SOLICITUD);
		session.removeAttribute(KEY_USUARIO_SSO);
		
		return null;
	}
	
	@RequestMapping(value = "/validarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarSolicitud(@RequestBody Persona persona, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_ATRIBUTE_SOLICITUD);
		if(solicitud!=null && CollectionUtils.isNotEmpty(solicitud.getTramites())){
			//Recuperar socio de la solicitud guardada
			InstanceofPredicate tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
			Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);	
			TramiteSocios tramiteSocio = (TramiteSocios) tramiteInicial;
			Socio socio = new Socio();	
			socio.setIdPersonaMoralPatron(tramiteSocio.getPatron().getIdPersona());
			//Validar que quede almenos 1 Socio Activo
			List<Socio> seleccionados = persona.getSocios();
			List<Socio> totalSocios = getSociosChecked(socio, null, session);
			if(!CollectionUtils.isEmpty(seleccionados) && !CollectionUtils.isEmpty(totalSocios)){
				int restantesSocios = totalSocios.size()-seleccionados.size();
				if(restantesSocios <= 0){
					result.put(KEY_ATRIBUTE_ERROR, "Debe quedar activo un Socio.");
					return result;					
				}					
			}
		}				
		result.put(KEY_ATRIBUTE_MENSAJE, "Exito");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<Socio> setSociosSeleccionados(List<Socio> checados, HttpSession session) {
		List<Socio> seleccionados = null;
		List<Socio> socios = (List<Socio>) session.getAttribute(SOCIOS_TO_SESSION);		
		if(!CollectionUtils.isEmpty(socios)){
			if(checados != null && !checados.isEmpty()) {
				seleccionados = new ArrayList<Socio>();			
				socioss: for(Socio socio: socios) {
					for(Socio checado: checados) {
						if(checado.getIdSocio().equals(socio.getIdSocio())) {
							seleccionados.add(socio);
							continue socioss;
						}
					}
				}
			}
		}
		return seleccionados;
	}
	
	private List<Socio> getSociosChecked(Socio socio, List<Socio> checados, HttpSession session) {		
		List<Socio> socios = socioServiceBusinessRemote.sociosPorIdPersonaMoralPatron(socio);
		List<Socio> finales = null;
		session.setAttribute(SOCIOS_TO_SESSION, socios);		
		if(!CollectionUtils.isEmpty(socios)){
			if(checados != null) {				
				finales = new ArrayList<Socio>();
				for(Socio s: socios) {
					for(Socio checado: checados)  {
						if(s.getIdSocio().equals(checado.getIdSocio())) {
							s.setChecked(true);
							break;
						}						
						s.setChecked(false);
					}
					finales.add(s);					
				}
			} else {
				finales = new ArrayList<Socio>();
				for(Socio s: socios) {
					s.setChecked(false);
					finales.add(s);
				}
			}
		}		
		return finales;
	}
			
	private void generarCadenaOriginalyFirma(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TIPO_TRAMITE_SOLICITUD).append("|");
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
		// Nombre O razon social
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
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
	private void datosFijosSession(final HttpSession session){
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.BAJA_SOCIO.getCodigo());
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_TRAMITE_SOLICITUD);
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
	}	
		
}
