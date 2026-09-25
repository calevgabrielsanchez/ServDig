package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

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

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator.TramiteBajaValidator;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.utils.OpcionesProperties;

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
@RequestMapping( value = "/wizard/baja/")
public class WizardBajaDerechohabienteController extends AbstractController {

	@Autowired
	private BajaDerechohabienteServiceRemote bajaDerechohabienteServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired
	private OpcionesProperties opcionesProperties;
	
	//Variables de la session
	private final String KEY_INTEGRANTE_BAJA = "integranteBajaSession";
	private final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	private final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_SOLICITUD = "solicitudBajaDerechohabiente";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_ESTADO_REQUISITOS = "correcto";
	private static final String KEY_MENSAJE_REQUISITOS = "mensaje";
	
	
	//vistas
	private static final String VIEW_INICIAL = "wizardBajaDerechohabienteInit";
	private static final String VIEW_INICIAL_CIUDADANO = "wizardBajaDerechohabienteCiudadanoInit";
	private static final String VIEW_CONTENIDO = "wizardBajaDerechohabienteContent";
	
	
	@RequestMapping( value = "/tramite/{idAsignacionNss}/{nss}/{tipoBaja}/{idIntegrante}")
	public String initWizardTipoBajaDerechohabiente(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss, @PathVariable String nss, @PathVariable Long tipoBaja, @PathVariable Long idIntegrante) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudActiva = new Solicitud();
		Boolean mismoOrigen = true;
		Boolean solicitudCreada = false;
		Boolean otroTipoTramite = false;
		
		
		GrupoFamiliar integranteBaja = null;
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		
		/*
		 * Verificamos si el origen es portal ciudadano para que la vista cambia
		 * a la pantalla donde se pide la curp y el tipo de baja que se va a hacer
		 */
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) {
			TramiteBajaDerechohabiente baja = new TramiteBajaDerechohabiente();
			baja.setIdAsignacionNSS(idAsignacionNss);
			model.addAttribute("baja", baja);
			model.addAttribute("opciones", opcionesProperties.getOpciones());
			return VIEW_INICIAL_CIUDADANO;
		}
		
		try {
			integranteBaja = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(idAsignacionNss, idIntegrante);
			if(integranteBaja != null) {
				session.setAttribute(KEY_ASIGNACION_NSS, integranteBaja.getAsignacionNSS());
			}
		}catch(DerechohabientesBusinessException e) {
			log.error("Error al obtener al derechohabiemnte", e);
			model.addAttribute("error", "No fue posible obtener los datos del integrante del grupo familiar");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.requiereDocumentos(session, tipoBaja);
		
		result = this.validacionesSolicitudAbierta(nss, idIntegrante, tipoBaja,idOrigenSolicitud);
		
		if(result != null) {
			solicitudActiva = (Solicitud) result.get("solicitud");
			solicitudCreada = (Boolean) result.get("solicitudCreada");
			mismoOrigen = (Boolean) result.get("mismoOrigen");
			otroTipoTramite = (Boolean) result.get("otroTipoTramite");
			
			session.setAttribute(KEY_SOLICITUD, solicitudActiva);
		}
		
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, this.getTipoTramite(tipoBaja));
		
		this.setTipoTramiteSession(session, tipoBaja);
		session.setAttribute(KEY_INTEGRANTE_BAJA, integranteBaja);
		
		model.addAttribute("solicitudCreada", solicitudCreada);
		model.addAttribute("otroTipoTramite", otroTipoTramite);
		model.addAttribute("mismoOrigen", mismoOrigen);
		model.addAttribute("solicitudForm", solicitudActiva != null ? solicitudActiva : new Solicitud());
		
		if(otroTipoTramite || solicitudCreada) {
			model.addAttribute("tipoTramiteCreado", solicitudActiva.getTramites().get(0).getTipoTramite());
		}
		
		
		
		
		return VIEW_INICIAL;
	}
	
	private Map<String, Object> validacionesSolicitudAbierta(String nss, Long idPersona, Long idTipoTramiteARealizar, Long idOrigenSoliciutd) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "Todos los requisitos son correctos";
		
		Boolean solicitudCreada = false;
		Boolean mismoOrigen = true;
		Boolean otroTipoTramite = false;
		
		List<Solicitud> solicitudes = null;
		Solicitud solicitudActiva = null;
		
		try {
			solicitudes = solicitudBusinessRemote.getSolicitudeActivasGrupoFamiliarEIntegrante(nss,idPersona,1L, null);
			
			if(solicitudes != null && !solicitudes.isEmpty()) {
				solicitudActiva = solicitudes.get(0);
				TipoTramite tipoCreado = solicitudActiva.getTramites().get(0).getTipoTramite();
				result.put("solicitud", solicitudActiva);
				
				log.debug("El tipo de tramite a realizar es : " + idTipoTramiteARealizar + " y el que ya existe es : " + tipoCreado.getIdTipoTramite());
				
				if(tipoCreado.getIdTipoTramite().equals(idTipoTramiteARealizar.intValue())) {
					solicitudCreada = true;
					mismoOrigen = solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(idOrigenSoliciutd);
				} else {
					otroTipoTramite = true;
					result.put("error", "No es posible realizar el tr&aacute;mite debido a que la persona cuenta con "
							+ "otro de tipo " + solicitudActiva.getTramites().get(0).getTipoTramite().getDescripcion());
				}
				
			} else {
				correcto = true;
			}
		}catch(Exception e) {
			log.error("Ocurrio un error al consultar las solicitudes", e);
			mensaje = "No fue posible consultar si el integrante del grupo familiar cuenta con solicitudes registradas";
		}
		
		result.put("correcto", correcto);
		result.put("mensaje", mensaje);
		result.put("solicitudCreada",solicitudCreada);
		result.put("mismoOrigen",mismoOrigen);
		result.put("otroTipoTramite",otroTipoTramite);
		
		
		return result;
	}
	
	@RequestMapping( value = "/iniciarTramiteCiudadano")
	public String crearTramiteCiudadano(@ModelAttribute TramiteBajaDerechohabiente baja, Model model,HttpSession session, HttpServletRequest request) {
		
		//Obtenemos el origen en el que estamos
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		Long idTipoBaja = baja.getTipoTramite().getIdTipoTramite().longValue();
		if(baja != null && baja.getPersona() != null && baja.getPersona().getCurp() != null){
			baja.getPersona().setCurp(baja.getPersona().getCurp().toUpperCase());
		}
		Map<String, Object> result =  this.validacionesInicioCiudadano(baja);
		GrupoFamiliar integrante = null;
		
		if(this.getEstadoValidaciones(result)) {
			this.setTipoTramiteSession(session, idTipoBaja);
			this.requiereDocumentos(session, idTipoBaja);
		}
		
		//checamos si el objeto de validaciones no viene nulo
		if(result != null) {
			//Obtenemos el indicador de las validaciones
			Boolean correcto = this.getEstadoValidaciones(result);
			//Si todo es correcto
			if(correcto) {
				//agregamos al integrante a session
				integrante = (GrupoFamiliar) result.get("integrante");
				
				//Checamos si existen solicitudes abiertas
				result = this.validacionesSolicitudAbierta(integrante.getAsignacionNSS().getNss(), integrante.getDerechohabiente().getIdPersona(), idTipoBaja, idOrigenSolicitud);
				//checamos los requisitos
				correcto = this.getEstadoValidaciones(result);
				if(!result.containsKey("error")) {
					
					if(correcto) {
						session.setAttribute(KEY_INTEGRANTE_BAJA, integrante);
					} else {
						model.addAttribute("validaciones", result);
					}
				} else {
					//mandamos el mensaje de error que venga en las validaciones
					model.addAttribute("error", result.get("error"));
					model.addAttribute("baja", new TramiteBajaDerechohabiente());
				}
			} else {// de lo contrario
				//mandamos el mensaje de error que venga en las validaciones
				model.addAttribute("error", result.get("mensaje"));
				model.addAttribute("baja", new TramiteBajaDerechohabiente());
			}
			
			if(!correcto) {
				return VIEW_INICIAL_CIUDADANO;
			}

		}
		
		return this.guardarTramiteBajaInterno(integrante, model, session, request, idOrigenSolicitud);
		
	}
	
	@RequestMapping( value = "/iniciarTramite")
	public String crearTramiteBaja(Model model, HttpSession session, HttpServletRequest request){
		
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_BAJA);
		//Obtenemos el origen en el que estamos
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		
		return this.guardarTramiteBajaInterno(integrante, model, session, request, idOrigenSolicitud);
		
	}
	
	private void setTipoTramiteSession(HttpSession session, Long idTipoBaja) {
		List<Long> idtiposTramite = new ArrayList<Long>();
		idtiposTramite.add(idTipoBaja);
		session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
		session.setAttribute(KEY_TIPO_SOLICITUD, idTipoBaja);
		
		return ;
	}
	
	private Boolean getEstadoValidaciones(Map<String, Object> result) {
		return (Boolean) result.get(KEY_ESTADO_REQUISITOS);
	}
	
	/**
	 * Metodo para guardar la solicitud de baja de derechohabientes para el portal ciudadano e imss digital
	 * @param integrante
	 * @param model
	 * @param session
	 * @param request
	 * @param result
	 * @return
	 */
	private String guardarTramiteBajaInterno(GrupoFamiliar integrante, Model model, HttpSession session, HttpServletRequest request, Long idOrigenSolicitud) {

		//obtenemos el tipo de baja a realizar
		Long idTipoTramite = (Long) session.getAttribute(KEY_TIPO_SOLICITUD);
		//Obtenemos al usuario que esta realizando la solicitud
		Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
		//la solicitud a crear
		Solicitud solicitud = null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			//si el integrante no es nulo
			if(integrante != null) {
				//seteamos e tipo de baja de derechohabiente a partir del tipo de tramite
				TipoBajaDerechohabienteEnum tipoBaja = this.getTipoBajaFromTipoTramite(idTipoTramite);
				//obtenemos el origen de la solicitud a partir de su id
				OrigenSolicitudEnum origenSolicitud = this.getOrigenSolicitudFromIdOrigen(idOrigenSolicitud);
				//Creamos la solicitud
				result = bajaDerechohabienteServiceRemote.saveSolicitudBajaDerechohabiente(integrante.getDerechohabiente().getIdPersona()
						, tipoBaja,integrante.getAsignacionNSS(), origenSolicitud, usuario);			
				//obtenemos la solicitud que se haya creado
				solicitud = (Solicitud) result.get("solicitud");
				//si se creo exitosamente la solicitud
				if(solicitud != null) {
					//Seteamos en sesion la solicitud
					session.setAttribute(KEY_SOLICITUD, solicitud);
					//y el tramite
					model.addAttribute("tramite", solicitud.getTramites().get(0));
					//solo si el origen es internet creamos la cadena original y los datos para el acuse
					if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
						// Datos del acuse
						obtenerDatosAcuse(solicitud, integrante.getAsignacionNSS(), session);
						// Datos para la firma digital
						generarCadenaOriginal(solicitud, integrante.getAsignacionNSS(), session, integrante.getAsignacionNSS().getNssStr());
					}
				}
			}
			
			
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar al derehcohabiente", e);
		}
		
		//mandamos a pantalla el origen de la solicitud
		model.addAttribute("idOrigenSolicitud", idOrigenSolicitud);
		//mandamos al integrante al que le estamos haciendo la solicitud
		model.addAttribute("integrante", integrante);
		//enviamos la solicitud que acabamos de crear
		model.addAttribute("solicitud", solicitud);
		//indicamos que la siolicitud no se esta retomando
		model.addAttribute("isRetomar", false);
	
		
		return VIEW_CONTENIDO;
	}
	
	/**
	 * Obtenemos el enum de origen solicitud a partir de su id
	 * @param idOrigenSolicitud
	 * @return
	 */
	private OrigenSolicitudEnum getOrigenSolicitudFromIdOrigen(Long idOrigenSolicitud) {
		OrigenSolicitudEnum origenSolicitud = null;
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) {
			origenSolicitud = OrigenSolicitudEnum.PORTAL_CIUDADANO;
		} else if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
			origenSolicitud = OrigenSolicitudEnum.INTERNET;
		} else if(idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
			origenSolicitud = OrigenSolicitudEnum.VENTANILLA;
		}
		
		return origenSolicitud;
	}
	
	/**
	 * Obtenemos el enum de tipo de baja a partir del id del tipo de tramite
	 * @param idTipoTramite
	 * @return
	 */
	private TipoBajaDerechohabienteEnum getTipoBajaFromTipoTramite(Long idTipoTramite) {
		TipoBajaDerechohabienteEnum tipoBaja = null;
		if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue())) {
			tipoBaja = TipoBajaDerechohabienteEnum.DEFUNCION;
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue())) {
			tipoBaja = TipoBajaDerechohabienteEnum.DIVORCIO;
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue())) {
			tipoBaja = TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO;
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue())) {
			tipoBaja = TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA;
		}else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL.getCodigo().longValue())) {
				tipoBaja = TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL;
		}
		
		return tipoBaja;
	}
	
	@RequestMapping(value="/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value="solicitudForm") Solicitud solicitud, HttpSession session, HttpServletRequest request) {

		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_BAJA);
		
		model.addAttribute("solicitudCreada", true);
		model.addAttribute("isRetomar", true);
		model.addAttribute("integrante", integrante);
		try {

			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			
			// Datos del acuse
			obtenerDatosAcuse(solicitud, integrante.getAsignacionNSS(), session);

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, integrante.getAsignacionNSS(), session, integrante.getAsignacionNSS().getNssStr());
			
			//Se verifica que no sea nula 
			if(solicitud != null) {
				
				session.setAttribute(KEY_SOLICITUD, solicitud);
				model.addAttribute("solicitud", solicitud);
				model.addAttribute("tramite", solicitud.getTramites().get(0));
				
			} else {
				request.setAttribute("error", "No fue posible recuperar la solicitud");
			}
		} catch(Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error", "No fue posible recuperar la solicitud");
		}
		
		
		model.addAttribute("solicitud", solicitud);
		return VIEW_CONTENIDO;
	}
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudBaja(@RequestBody TramiteBajaDerechohabiente tramite, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_BAJA);
		
		TramiteBajaDerechohabiente baja = (TramiteBajaDerechohabiente)solicitud.getTramites().get(0);
		baja.setPersona(integrante.getDerechohabiente());
		baja.setFechaDefuncion(tramite.getFechaDefuncion());
		baja.setObservaciones(tramite.getObservaciones());
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(baja);
		
		try {
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put("mensaje", "Se han guardado correctamente los cambios");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudBaja(@RequestBody TramiteBajaDerechohabiente tramite,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		//Obtenemos el origen en el que estamos
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		Boolean isInternet = idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId());
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_BAJA);
		
		TramiteBajaDerechohabiente baja = (TramiteBajaDerechohabiente)solicitud.getTramites().get(0);
		baja.setPersona(integrante.getDerechohabiente());
		baja.setFechaDefuncion(tramite.getFechaDefuncion());
		baja.setObservaciones(tramite.getObservaciones());
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(baja);
		
		try {
			//Actualizamos el tramite de baja
			solicitudBusinessRemote.actualizarXmlTramite(baja);
			//solo si es internet habra elementos de firma en la sesion
			if(isInternet) {
				log.debug("Firma Electronica" + firma);
				log.debug("secuencia: " + firma.getSecuenciaNotaria());
				solicitud.setFirmaElectronica(firma);
			}
			//se finaliza la solicitud
			solicitud = bajaDerechohabienteServiceRemote.finalizarSolicitudBaja(solicitud);
			//si es internet se generan los documentos resultantes en la misma transaccion
			if(isInternet) {
				solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			}
			result.put("mensaje", "Tu solicitud ha finalizado correctamente");
			
			session.removeAttribute(KEY_SOLICITUD);
		} catch(ImpactaAlmacenesWSException e) {
			this.log.error("error ws",e);
			result.put("mensaje", "Ocurri&oacute; un error al calcular la vigencia, favor de intentarlo nuevamente mas tarde.");
		}catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/validaInicioCiudadano", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarInicioCiudadano(final @RequestBody TramiteBajaDerechohabiente oForm, final HttpServletResponse response) {
        log.trace("entramos a WizardBajaDerechohabienteController para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new TramiteBajaValidator().validateCiudadado(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
	
	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteBajaDerechohabiente oForm, final HttpServletResponse response) {
        log.trace("entramos a WizardBajaDerechohabienteController para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new TramiteBajaValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) {
			this.cancelarsolicitud((Solicitud) session.getAttribute(KEY_SOLICITUD),"solicitud cancelada debido al cierra de ventana desde portal ciudadano");
		}
		this.limpiarElementosSession(session);
		
		return null;
	}
	
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudBaja(@RequestBody Solicitud solicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);

		if(solicitud.getSolicitudId().equals(solicitudA.getSolicitudId())) {

			result = this.cancelarsolicitud(solicitudA, "Solicitud cancelada a peticion del usuario");
			session.removeAttribute(KEY_SOLICITUD);
		} else {
			result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
		}



		return result;
	}
	
	private Map<String, Object> cancelarsolicitud(Solicitud solicitud, String observaciones) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitud != null) {
			log.debug("Existe una solicitud que se cancelara");
			try {
				
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(),5L,1L,null, observaciones); 
				
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} catch (SolicitudException e) {
				this.log.error(e);
				result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
			} 
			
			solicitud.setSolicitante(null);
			result.put("solicitud", solicitud);
		} else {
			log.debug("No existia la solicitud");
		}
		
		return result;
	}
	
	private void limpiarElementosSession(HttpSession session) {
		session.removeAttribute(KEY_INTEGRANTE_BAJA);
		session.removeAttribute(KEY_ASIGNACION_NSS);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_REQUIERE_DOCS);
		
		return;
	}
	private String getTipoTramite(Long tipoBaja) {
		String descTipoTramite = "";
		if(tipoBaja.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue())) {
			descTipoTramite= "BAJA DE DERECHOHABIENTE POR DEFUNCI&Oacute;N";
		} else if(tipoBaja.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue())) {
			descTipoTramite= "BAJA DE DERECHOHABIENTE POR DIVORCIO";
		} else if(tipoBaja.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue())) {
			descTipoTramite= "BAJA DE DERECHOHABIENTE POR T\u00C9RMINO DE CONCUBINATO";
		} else if(tipoBaja.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue())) {
			descTipoTramite= "BAJA DE DERECHOHABIENTE POR T\u00C9RMINO DE CONVIVENCIA";
		} else if(tipoBaja.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL.getCodigo().longValue())) {
			descTipoTramite= "BAJA DE DERECHOHABIENTE POR T\u00C9RMINO DE UNI\\u00D3N CIVIL";
		}
		
		return descTipoTramite;
	}
	
	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.
		
		if(usuariosso.getDelegacion() != null  && usuariosso.getSubdelegacion() != null){
			
			// LUDS Se agrego esta validacion para que si es en caso de un usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}
		
		
		usuario.setCveIdUsuario(usuariosso.getCurp());
		
		return usuario;
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
	
	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session, String nss) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		String tipoSolicitud = (String) session.getAttribute(KEY_DESC_TIPO_SOLICITUD);
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(tipoSolicitud).append("|");

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

		
		if (persona instanceof Fisica) {
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("|");
		}

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:"+nss+"||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}
	
	private void requiereDocumentos(HttpSession session, Long tipoBaja) {
		Boolean requiereDocumentos = false;
		try {
			requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tipoBaja);
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}
		
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		
	}
	
	/**
	 * Metodo para validaciones de inicio de tramite desde el portal ciudadano
	 * @param baja
	 * @return
	 */
	private Map<String, Object> validacionesInicioCiudadano(TramiteBajaDerechohabiente baja) {
		//Verificamos si existe la persona con curp
		Map<String, Object> result =  requisitosMinimosServiceRemote.validacionExistenciaPersonaTramiteBajaCiudadano(baja.getIdAsignacionNSS(), baja.getPersona().getCurp());
		//si las validaciones son correctas
		if(this.getEstadoValidaciones(result)) {
			GrupoFamiliar integrante = (GrupoFamiliar) result.get("integrante");
			result = this.validarConsistenciaBajaParentesco(integrante, baja.getTipoTramite().getIdTipoTramite().longValue(), OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
			result.put("integrante", integrante);
		}
		
		return result;
	}
	
	private Map<String, Object> validarConsistenciaBajaParentesco(GrupoFamiliar grupo, Long idTipoTramite, Long idOrigen) {
		Long idParentesco = grupo.getParentesco().getIdParentesco();
		
		if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue())) {
			if((idOrigen.equals(OrigenSolicitudEnum.INTERNET.getId()) || idOrigen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) 
				&&	(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId()))) {
				return this.getMapResquisitos(false, "No es posible realizar el tr&aacute;mite de baja por defunci&oacute; para el asegurado "
						+ "o pensionado mediante este medio.");
			}
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue())) {
			if(!idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
				return this.getMapResquisitos(false, "La baja por divorcio solo aplica para beneficiarios con parentesco conyuge.");
			}
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue())) {
			if(!idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
				return this.getMapResquisitos(false, "La baja por t&eacute;rmino de concubinaro solo aplica para beneficiarios con parentesco concubina(rio).");
			}
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue())) {
			if(!idParentesco.equals(ParentescoEnum.PADRES.getId())) {
				return this.getMapResquisitos(false, "La baja por t&eacute;rmino de convivencia solo aplica para beneficiarios con parentesco madre o padre.");
			}
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL.getCodigo().longValue())) {
			if(!idParentesco.equals(ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId())) {
				return this.getMapResquisitos(false, "La baja por t&eacute;rmino de persona en uni&oacute;n civil solo aplica para beneficiarios con parentesco persona en union civil.");
			}
		} else {
			return this.getMapResquisitos(false, "El tipo de tr&aacute;mite no corresponde con ninguna baja (" + idTipoTramite + ")");
		}
		
		return this.getMapResquisitos(true, "Las validaciones son correctas");
	}
	
	private Map<String, Object> getMapResquisitos(Boolean correcto, String mensaje) {
		Map<String,Object> resultado = new HashMap<String, Object>();
		
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
}
