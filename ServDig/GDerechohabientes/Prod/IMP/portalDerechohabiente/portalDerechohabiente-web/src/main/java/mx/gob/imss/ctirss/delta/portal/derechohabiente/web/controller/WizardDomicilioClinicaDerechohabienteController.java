/*
 * Esta clase es el controller encargada de administrar las peticiones de los tramites de prorrogas
 * La funcionalidad que cubre este Controller es:
 * 1.Obtener informacion del derechohabiente
 * 2.Iniciar tramite
 * 3.Retomar
 * 4.Guardar
 * 5. Finalizar
 * 6. Cancelar
 */
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

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

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
@RequestMapping(value = "/wizard/domicilio/")
public class WizardDomicilioClinicaDerechohabienteController extends AbstractController {
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteService;
	@Autowired
	private ProrrogaServiceRemote prorrogaService;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	// Variables de la session
	private final String KEY_CABEZA_GRUPO_FAMILIAR = "KEY_CABEZA_GRUPO_FAMILIAR";
	private final String KEY_INTEGRANTE_PRORROGA = "integranteProrrogaSession";
	private final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	private final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_DOMICILIO_EXISTENTE = "KEY_DOMICILIO_EXISTENTE";

	// vistas
	private static final String VIEW_INICIAL = "wizardDomicilioClinicaDerechohabienteInit";
	private static final String VIEW_CONTENIDO = "wizardDomicilioClinicaDerechohabienteContent";

	private static final Locale LOCALE_MX = new Locale("es", "mx");;

	/**
	 * Este metodo sirve para obtener la informacion del derechohabiente
	 * @param model
	 * @param session
	 * @param request
	 * @param idAsignacionNss
	 * @param nss
	 * @param idTipoTramite
	 * @param idIntegrante
	 * @return
	 */
	@RequestMapping(value = "/tramite/{idAsignacionNss}/{nss}/{idTipoTramite}/{idIntegrante}")
	public String initWizardDomicilioClinicaDerechohabiente(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idAsignacionNss, @PathVariable String nss,
			@PathVariable Long idTipoTramite, @PathVariable Long idIntegrante) {

		Solicitud solicitudActiva = new Solicitud();
		Boolean mismoOrigen = true;
		Boolean solicitudCreada = false;
		Boolean otroTipoTramite = false;
		Boolean requiereDocs = false;

		GrupoFamiliar integrante = null;
		
		//Se hace validacion para saber si el tramite requiero o no documentos
		try {
			requiereDocs = documentoProbatorioServiceBusinessRemote.requiereDocumentos(idTipoTramite);
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}

		try {
			integrante = derechohabienteServiceRemote
					.detalleDerechohabienteGrupoFamiliar(idAsignacionNss,
							idIntegrante);
			if (integrante != null) {
				session.setAttribute(KEY_ASIGNACION_NSS,
						integrante.getAsignacionNSS());
			}
		} catch (DerechohabientesBusinessException e) {
			log.error("Error al obtener al derechohabiemnte", e);
			model.addAttribute("error",
					"No fue posible obtener los datos del integrante del grupo familiar");
		}

		List<Solicitud> solicitudes = null;
		try {
			// Estas solicitudes tienen que ser de tipo prorroga
			// Comparar contra ProrrogaController
			solicitudes = solicitudBusinessRemote
					.getSolicitudeActivasGrupoFamiliarEIntegrante(nss,
							idIntegrante, 1L, null);

			if (solicitudes != null && !solicitudes.isEmpty()) {

				solicitudActiva = solicitudes.get(0);

				if (solicitudActiva.getTramites().get(0).getTipoTramite()
						.getIdTipoTramite().equals(idTipoTramite.intValue())) {
					solicitudCreada = true;
					
					mismoOrigen = solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId());
				} else {
					otroTipoTramite = true;
				}
			}
			
			//Si el tipo de tramite es: ASIGNACION_DE_DOMICILIO_PARTICULAR_DH, ACTUALIZACION_DOMICILIO_PARTICULAR
			//Se debe verificar si ya cuenta con un domicilio en DIT_PERSONAF_DOM, de ser así
			//se debe indicar si se desa asignar ese mismo domicilio en su grupo familiar
			if( idTipoTramite.equals(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
					.getCodigo().longValue()) || idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR
							.getCodigo().longValue()) ){
				Persona persona = new Persona();
				persona.setIdPersona( integrante.getDerechohabiente().getIdPersona() );
				List<Domicilio>domicilios = domicilioServiceBusiness.consultarDomiciliosPersonaFisica( persona );
				Domicilio domicilio = domicilios.get(0);
				session.setAttribute(KEY_DOMICILIO_EXISTENTE, domicilio);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar las solicitudes", e);
			model.addAttribute(
					"error",
					"No fue posible consultar si el integrante del grupo familiar cuenta con solicitudes registradas");
		}
		
		session.setAttribute(KEY_TIPO_SOLICITUD, idTipoTramite);
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD,
				this.getTipoTramite(idTipoTramite));
		List<Long> idtiposTramite = new ArrayList<Long>();
		idtiposTramite.add(idTipoTramite);
		session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
		session.setAttribute(KEY_INTEGRANTE_PRORROGA, integrante);
		requiereDocs = false;
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocs);
		
		model.addAttribute("solicitudCreada", solicitudCreada);
		model.addAttribute("otroTipoTramite", otroTipoTramite);
		model.addAttribute("mismoOrigen", mismoOrigen);

		if (otroTipoTramite || solicitudCreada) {
			model.addAttribute("tipoTramiteCreado", solicitudActiva
					.getTramites().get(0).getTipoTramite());
		}

		model.addAttribute("solicitudForm", solicitudActiva);

		return VIEW_INICIAL;
	}

	/**
	 * Este metodo se encarga de iniciar el tramite de prorroga de acuerdo al tipo seleccionado en la pantalla
	 * Crea una solicitud y tramite con los campos mínimos requeridos
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/iniciarTramite")
	public String crearTramiteProrroga(Model model, HttpSession session,
			HttpServletRequest request) {

		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);
		Long idTipoTramite = (Long) session.getAttribute(KEY_TIPO_SOLICITUD);
		Usuario usuario = this.getUsuarioSesion(this
				.procesarUsuarioSSO(request));
		Solicitud solicitud = null;
		TramiteCorreccionDerechohabiente tramite = null;

		try {

			if (integrante != null) {
				tramite = new TramiteCorreccionDerechohabiente();
				
				//Asignamos el domicilio actual del derechohabiente
				tramite.setDomicilio( integrante.getDomicilio() );
				tramite.setIdAsignacionNss( integrante.getAsignacionNSS().getIdAsignacionNSS() );
				tramite.setIdPersona( integrante.getDerechohabiente().getIdPersona() );
				
				if (idTipoTramite.equals(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
						.getCodigo().longValue())) {
					solicitud = correccionDerechohabienteService.guardarSolicitudCorreccionDerechohabiente(integrante, 
							usuario, 
							integrante.getAsignacionNSS(), 
							tramite, 
							TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH, 
							OrigenSolicitudEnum.INTERNET);
				} else if (idTipoTramite
						.equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo()
								.longValue())) {
					solicitud = correccionDerechohabienteService.guardarSolicitudCorreccionDerechohabiente(integrante, 
							usuario, 
							integrante.getAsignacionNSS(), 
							tramite, 
							TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR, 
							OrigenSolicitudEnum.INTERNET);
				} else if (idTipoTramite
						.equals(TipoTramiteEnum.CAMBIO_CLINICA
								.getCodigo().longValue())) {
					solicitud = correccionDerechohabienteService.guardarSolicitudCorreccionDerechohabiente(integrante, 
							usuario, 
							integrante.getAsignacionNSS(), 
							tramite, 
							TipoTramiteEnum.CAMBIO_CLINICA, 
							OrigenSolicitudEnum.INTERNET);
				}

				if (solicitud != null) {
					this.generarCadenaOriginal(solicitud,
							integrante.getDerechohabiente(), session);
					this.obtenerDatosAcuse(solicitud,
							integrante.getDerechohabiente(), session);
					session.setAttribute(KEY_SOLICITUD, solicitud);
					model.addAttribute("tramite", solicitud.getTramites()
							.get(0));
				}
			}

		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar al derehcohabiente", e);
		} 
		
		model.addAttribute("integrante", integrante);
		model.addAttribute("isRetomar", false);

		return VIEW_CONTENIDO;
	}

	/**
	 * Este metodo se encarga de consultar la soilicitud no concluida de prorroga 
	 * para mostrarla en pantalla
	 * @param model
	 * @param solicitud
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/retomar")
	public String retomarSolicitud(Model model,
			@ModelAttribute(value = "solicitudForm") Solicitud solicitud,
			HttpSession session, HttpServletRequest request) {

		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);
		
		model.addAttribute("solicitudCreada", true);
		model.addAttribute("isRetomar", true);
		model.addAttribute("integrante", integrante);
		try {

			// Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);

			// Datos del acuse
			obtenerDatosAcuse(solicitud, integrante.getDerechohabiente(),
					session);

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, integrante.getDerechohabiente(),
					session);

			// Se verifica que no sea nula
			if (solicitud != null) {

				session.setAttribute(KEY_SOLICITUD, solicitud);
				model.addAttribute("solicitud", solicitud);
				TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente) solicitud.getTramites().get(0);
				
				//Si el domicilio es nulo indica que el tramite no se guardo previamente y se debe 
				//mostrar el domicilio actual
				if( tramite.getDomicilio() == null ){
					tramite.setDomicilio( integrante.getDomicilio() );
				}
				
				model.addAttribute("tramite", tramite);

			} else {
				request.setAttribute("error",
						"No fue posible recuperar la solicitud");
			}
		} catch (Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error",
					"No fue posible recuperar la solicitud");
		}

		model.addAttribute("solicitud", solicitud);
		return VIEW_CONTENIDO;
	}

	/**
	 * Este metodo se encarga de guardar los la solicitud y tramite con la información capturada
	 * en pantalla
	 * @param tramite, contiene la informacion capturada en pantalla
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarSolicitud(
			@RequestBody TramiteCorreccionDerechohabiente tramite, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);

		// Se asigna el estado del tramite creado en /iniciarTramite
		tramite.setEstadoTramite(solicitud.getTramites().get(0)
				.getEstadoTramite());
		tramite.setPersona(integrante.getDerechohabiente());

		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);

		try {
			// Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put("mensaje", "Se han guardado correctamente los cambios");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		}

		return result;
	}

	/**
	 * Este método se encarga de finalizar la solicitud y tramite de prorroga.
	 * Dependiendo del tipo de prorroga se haran ciertas validaciones, ademas de esto
	 * se guardan los documentos capturados y se generan los documentos resultantes.
	 * @param tramite
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarSolicitud(
			@RequestBody TramiteCorreccionDerechohabiente tramite, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);
		tramite.setPersona(integrante.getDerechohabiente());

		// Se asigna el estado del tramite creado en /iniciarTramite
		tramite.setEstadoTramite(solicitud.getTramites().get(0)
				.getEstadoTramite());

		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);

		try {
			// Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitud = solicitudBusinessRemote.actualizarTramites(solicitud);
			solicitud.setFirmaElectronica(firma);
			/*
			 * Seccion que finaliza el tramite de acuerdo al tipo de prorroga seleccionada
			 */
			//Inicia prorroga por estudios
			
//			solicitud = prorrogaService.finalizarSolicitudProrroga(solicitud, integrante);
			solicitud.setFirmaElectronica(firma);
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		}

		return result;
	}
	
	/**
	 * Controller para el tipo de tramite ACTUALIZACION_DOMICILIO_PARTICULAR(6)
	 * Este metodo es el encargado de actualizar el domicilio del derechohabiente de la siguiente forma:
	 * En DIT_GRUPO_FAMILIAR se conserva el mismo ID de CVE_ID_PERSONA_FDOM
	 * En DIT_PERSONAF_DOM se actualiza el campo FEC_REGISTRO_ACTUALIZADO, se conserva el mismo ID de DOMICILIO_ID
	 * En DG_DOMICILIO_GEOGRAFICO Se actualiza la infomacion capturada en pantalla
	 * Tambien se generan los documentos probatorios correspondientes a Corrección de datos
	 * @param idSolicitud
	 * @param mdmDatosEntrada
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
//	public @ResponseBody
//	Map<String, ? extends Object> finalizarSolicitud(
//			@RequestBody TramiteCorreccionDerechohabiente tramite, HttpServletResponse response,
//			HttpServletRequest request, HttpSession session)
	
	@RequestMapping(value = "/actualizar/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarDomicilio(
			@PathVariable Long idSolicitud,
			@RequestBody TramiteCorreccionDerechohabiente tramite, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			FirmaElectronica firmaElectronica = (FirmaElectronica) session
					.getAttribute(KEY_FIRMA_ELECTRONICA);

			Solicitud solicitud = (Solicitud)session.getAttribute(KEY_SOLICITUD);
			// Consultar informacion de la solcitud para
			// posteriormente guardarla y aplicar los cambios correspondientes
			// a la asignacion de domicilio
			
			solicitud.setFirmaElectronica(firmaElectronica);
			
			//Se setea el id del domicilio a actualizar
			Domicilio dg = (Domicilio)session.getAttribute(KEY_DOMICILIO_EXISTENTE);
			tramite.getDomicilio().setClave( dg.getClave() );
//			domicilio.setClave( dg.getClave() );

			// Se settea el tipo de domicilio geografico
			TipoDomicilio tipoDomicilio = new TipoDomicilio();
			// Tipo DOMICILIO URBANO
			tipoDomicilio.setClave(1);
//			domicilio.setTipoDomicilio(tipoDomicilio);
			tramite.getDomicilio().setTipoDomicilio( tipoDomicilio );

			// Se settea el tipo de domicilio
			tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo()
					.intValue());
//			domicilio.setDicTipoDomicilio(tipoDomicilio);
			tramite.getDomicilio().setDicTipoDomicilio( tipoDomicilio );
			
			solicitud = solicitudBusinessRemote.actualizarTramites(solicitud);
			
			solicitud.getTramites().set(0, tramite);
			
			correccionDerechohabienteService.finalizarSolicitudActualizacionDomicilio(solicitud);
//			solicitud = solicitudBusinessRemote.finalizarSolicitudActualizacionDomicilio(solicitud, null);
			
			//Generacion de documentos resultantes
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitud.setFirmaElectronica(firmaElectronica);
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			
			result.put("mensaje",
					"Su solicitud esta siendo procesada. Por favor espere");
			
			//ejemplo
//			solicitud = solicitudBusinessRemote.actualizarTramites(solicitud);
//			solicitud.setFirmaElectronica(firma);
//						
//			solicitud = prorrogaService.finalizarSolicitudProrroga(solicitud, integrante);
//			solicitud.setFirmaElectronica(firma);
//			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
						
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Este metodo se encarga de cancelar la solicitud de prorroga que no ha sido finalizada
	 * @param solicitud
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudProrroga(
			@RequestBody Solicitud solicitud, HttpServletResponse response,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
					EstadoSolicitudEnum.CANCELADA.getCodigo());

			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put("mensaje", "La solicitud fue cancelada correctamente");
			result.put("solicitud", solicitud);

		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: "
					+ e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: "
					+ e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(
			@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	@RequestMapping(value = "/limpiar-session")
	public @ResponseBody
	Map<String, Object> limpiarSession(HttpSession session) {
		session.removeAttribute(KEY_CABEZA_GRUPO_FAMILIAR);
		session.removeAttribute(KEY_INTEGRANTE_PRORROGA);
		session.removeAttribute(KEY_ASIGNACION_NSS);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_REQUIERE_DOCS);
		session.removeAttribute(KEY_DOMICILIO_EXISTENTE);

		return null;
	}

	private String getTipoTramite(Long idTipoTramite) {
		String descTipoTramite = "";
		if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR VIGENCIA PERMANENTE";
		} else if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR ACUERDO";
		} else if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR INCAPACIDAD OBSTETRICA";
		} else if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR INCAPACIDAD FISICA O PSIQUICA";
		} else if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR ESTUDIOS";
		} else if (idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_LAUDO
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR LAUDO";
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo()
						.longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR VIGENCIA TEMPORAL";
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

		if (usuariosso.getDelegacion() != null
				&& usuariosso.getSubdelegacion() != null) {

			// LUDS Se agrego esta validacion para que si es en caso de un
			// usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(
					usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}

		usuario.setCveIdUsuario(usuariosso.getCurp());

		return usuario;
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona,
			HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance()
				.getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());

		// RFC
		datosAcuse.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el
		// de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica) persona).getNombre().trim()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getPrimerApellido())) {
				sbnombre.append(((Fisica) persona).getPrimerApellido()).append(
						" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral) persona).getRazonSocial());
		}
		datosAcuse.setNombreCompleto(sbnombre.toString());

		// CURP
		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica) persona).getCurp());
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona,
			HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		String tipoSolicitud = (String) session
				.getAttribute(KEY_DESC_TIPO_SOLICITUD);
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(tipoSolicitud).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance()
				.getTime());
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

		// Nombre, denominacion o razon social del interesado (y en su caso el
		// de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica) persona).getNombre().trim()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getPrimerApellido())) {
				sbnombre.append(((Fisica) persona).getPrimerApellido()).append(
						" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral) persona).getRazonSocial());
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica) persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica) persona).getCurp());
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
}
