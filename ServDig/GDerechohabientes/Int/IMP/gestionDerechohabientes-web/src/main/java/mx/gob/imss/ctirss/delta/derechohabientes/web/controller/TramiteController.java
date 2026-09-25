package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.RechazoTramiteDto;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.ProrrogasDto;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 05/05/2012
 */
@Controller
@RequestMapping(value = "/tramite/*")
public class TramiteController extends AbstractController {

	@Autowired private SolicitudServiceRemote solicitudServiceRemote;
	@Autowired private TramiteServiceRemote tramiteServiceRemote;
	@Autowired private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	private static final String KEY_ESTADO_VALIDACION = "correcto";
	private static final String KEY_MENSAJE_REQUISITOS = "mensaje";
	
	@RequestMapping( value = "/comprobanteInternet", method = RequestMethod.GET)
	public String imprimirComprobanteSolicitud(@RequestParam(value = "idSolicitud") Long idSolicitud, @RequestParam(value = "titulo") String titulo,
			Model model, HttpServletRequest request, HttpSession session) {
		
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		reporte.setIdSolicitud(idSolicitud);
		reporte.setMensaje("Debe acudir a la umf destino para la validación del trámite");
		TipoTramite tipoTram = new TipoTramite();
		tipoTram.setIdTipoTramite(1001);
		tipoTram.setDescripcion(titulo);
		reporte.setTipoTramite(tipoTram);
		reporte.setRechazado(false);
		
		model.addAttribute("reporte", reporte);
		return "finalizacionTramite";
	}
	
	@RequestMapping( value = "/rechazarProrrogas", method = RequestMethod.POST)
	public String rechazarProrrogas(@ModelAttribute("datos") ProrrogasDto prorroga,Model model, HttpServletRequest request, HttpSession session) {
		Usuario miUsuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//Rechazamos la solicitud y obtenemos el tramite relacionado con ella
		try {
			solicitudServiceRemote.rechazarSolicitud(prorroga.getRechazo().getIdSolicitud(), prorroga.getRechazo().getIdPersona(), 
					prorroga.getRechazo().getIdTramite(), prorroga.getRechazo().getIdRazonRechazo(), prorroga.getRechazo().getObservaciones(),miUsuario.getFisica());			
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			//Objeto para generar el reporte
			reporte.setIdPersona(prorroga.getRechazo().getIdPersona());
			reporte.setIdTramite(prorroga.getRechazo().getIdTramite());
			reporte.setIdSolicitud(prorroga.getRechazo().getIdSolicitud());
			reporte.setRechazado(true);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(Integer.valueOf(""+prorroga.getRechazo().getIdTipoTramite()));
			tipoTram.setDescripcion("");
			reporte.setTipoTramite(tipoTram);
			model.addAttribute("reporte", reporte);
			return "finalizacionTramite";
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			model.addAttribute("exception", e.getMessage());
			model.addAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			log.error("", e);
			model.addAttribute("exception", "error.actualizar.tramite");
			model.addAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		
	}
	
	@RequestMapping( value = "/rechazar", method = RequestMethod.POST)
	public String rechazarTramitPosible(@ModelAttribute RechazoTramiteDto rechazoTramite,Model model, HttpServletRequest request, HttpSession session) {
		Usuario miUsuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//Rechazamos la solicitud y obtenemos el tramite relacionado con ella
		try {
			Solicitud solicitudCircunscripcion = new Solicitud(rechazoTramite.getIdSolicitud());
			
			solicitudServiceRemote.rechazarSolicitud(rechazoTramite.getIdSolicitud(), rechazoTramite.getIdPersona(), 
					rechazoTramite.getIdTramite(), rechazoTramite.getIdRazonRechazo(), rechazoTramite.getObservaciones(),miUsuario.getFisica());
			
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			//Objeto para generar el reporte
			reporte.setIdPersona(rechazoTramite.getIdPersona());
			reporte.setIdTramite(rechazoTramite.getIdTramite());
			reporte.setIdSolicitud(rechazoTramite.getIdSolicitud());
			reporte.setRechazado(true);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(rechazoTramite.getIdTipoTramite().intValue());
			tipoTram.setDescripcion("");
			reporte.setTipoTramite(tipoTram);
			reporte.setMuestraBotonImpresion(false);
			
			model.addAttribute("reporte", reporte);
			model.addAttribute("solicitud", solicitudCircunscripcion);
			
			return "finalizacionTramite";
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			model.addAttribute("exception", e.getMessage());
			model.addAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			log.error("", e);
			model.addAttribute("exception", "error.actualizar.tramite");
			model.addAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		
	}
	
	@RequestMapping( value = "/tramitePosible", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Integer> tramitePosible(@RequestBody Tramite tramite,Model model, HttpServletRequest request, HttpSession session) {
		RespuestaJSON<Integer> respuesta = new RespuestaJSON<Integer>();
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		Integer res= solicitudServiceRemote.tramitePosible(tramite.getPersona().getIdPersona(),tramite.getTipoTramite().getIdTipoTramite().longValue(),asignacionNSS) ? 1 : 0;
		respuesta.setModelo(res);
		return respuesta;
	}
	
	@RequestMapping( value = "/tramiteAbierto", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Tramite> tramitePosible(@RequestBody Fisica persona ,Model model, HttpServletRequest request, HttpSession session) {
		RespuestaJSON<Tramite> respuesta = new RespuestaJSON<Tramite>();
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		try {
			Tramite res= solicitudServiceRemote.tramiteAbierto(persona.getIdPersona(), asignacionNSS);
			respuesta.setEstado(true);
			respuesta.setModelo(res);
		} catch (Exception e) {
			respuesta.setEstado(false);
			respuesta.setMensaje("Ocurrio un error al consultar las solicitudes de la persona.");
		}
		return respuesta;
	}
	
	@RequestMapping( value = "/tramiteModalidad", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Boolean> tramitePosibleModalidad(
			@RequestParam("tipoTramite") Long idTipoTramite,
			@RequestParam("parentesco") Long idParentesco,
			Model model, HttpServletRequest request, HttpSession session
	) {
		CabezaGrupoFamiliar cabezaGrupoFamiliar = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		
		RespuestaJSON<Boolean> respuesta = new RespuestaJSON<Boolean>();
		
		Boolean posible = tramiteServiceRemote.tramitePosiblePorModalidadParenteso(
				cabezaGrupoFamiliar.getIdModalidad(), idParentesco, idTipoTramite);
		
		respuesta.setEstado(posible);
		
		return respuesta;
	}
	
	/**
	 * Metodo para obtener el detalle de in tramite de tipo registro de
	 * derechohabiente
	 * 
	 * @param idSolicitud
	 * @param idPersona
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/registro")
	public String detalleTramiteRegistro(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {
		CabezaGrupoFamiliar cabezaGrupoFamiliar = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		TramiteRegistroDerechohabiente registroDerechohabiente = null;
		//modalidades
		List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
		//ids modalidades
		List<Long> idsModalidades = this.getModalidadesActivas(modalidades);
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		try {
			
			//consultar solicitud sacar tramite registro y mandar al nuevo requisitos EFM
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			for(Tramite tramite : solicitud.getTramites()){
				if(tramite instanceof TramiteRegistroDerechohabiente){
					registroDerechohabiente = (TramiteRegistroDerechohabiente) tramite;
					break;
				}
			}
			Map<String, Object> requisitosMinimos = requisitosMinimosServiceRemote.requisitosMinimosRegistro(registroDerechohabiente, 
					cabezaGrupoFamiliar, OrigenSolicitudEnum.VENTANILLA.getId(), usuario.getIdUmf(), false, idsModalidades);
			
			model.addAttribute("registro", registroDerechohabiente);
			model.addAttribute("requisitos",  (Boolean) requisitosMinimos.get(KEY_ESTADO_VALIDACION));
			model.addAttribute("razones", requisitosMinimos.get(KEY_MENSAJE_REQUISITOS));
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}

		return Constants.DETALLE_REGISTRO;
	}

	/**
	 * Metodo para obtener el detalle de un tramite de tipo correccion de datos
	 * de derechohabiente
	 * 
	 * @param idSolicitud
	 * @param idPersona
	 * @param model
	 * @param reques
	 * @return
	 */
	@RequestMapping(value = "/detalle/correccion")
	public String detalleTramiteCorreccion(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCorreccionDerechohabiente corregido = null;
		TramiteCorreccionDerechohabiente correccion = null;
		GrupoFamiliar integrante = null;
		try {
			Solicitud sol = this.obtenerSolicitud(idTramite);
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			corregido = TramiteUtil.getTramiteCorreccionPorTipo(sol, null);
			
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), corregido.getIdPersona());
			correccion = this.convetirGrupoCorreccion(integrante);
			model.addAttribute("corregido", corregido);
			model.addAttribute("correccion", correccion);
			model.addAttribute("integrante", integrante);
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		return Constants.DETALLE_CORRECCION_DATOS;
	}

	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/circunscripcion/autorizacion")
	public String detalleTramiteCircunscripcion(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar integrante = null;
		TramiteCircunscripcionForanea circunscripcion = null;
		Solicitud solicitudCircunscripcion = null;
		try {
			solicitudCircunscripcion = this.obtenerSolicitud(idTramite);
			
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(solicitudCircunscripcion.getSolicitudId());
			}
			circunscripcion = (TramiteCircunscripcionForanea)solicitudCircunscripcion.getTramites().get(0);
			
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),circunscripcion.getPersona().getIdPersona());
			model.addAttribute("integrante", integrante);
			model.addAttribute("umfAnterior",circunscripcion.getMedicoEnTurnoOrigen());
			model.addAttribute("umfActual",circunscripcion.getMedicoEnTurnoDestino());
			model.addAttribute("domicilioAnterior",circunscripcion.getDomicilioOrigen());
			model.addAttribute("domicilioActual",circunscripcion.getDomicilioDestino());
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		return Constants.DETALLE_CIRCUNSCRIPCION;
	}
	
	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/circunscripcion/suspension")
	public String detalleTramiteSuspensionCircunscripcion(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar integrante = null;
		TramiteCircunscripcionForanea circunscripcion = null;
		GrupoFamiliar cabeza = null;
		try {
			if(asignacionNSS == null){
				Solicitud sol = this.obtenerSolicitud(idTramite);
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			circunscripcion = solicitudServiceRemote.detalleSuspencionCircunscripcion(idTramite);
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),circunscripcion.getPersona().getIdPersona());
			cabeza = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), asignacionNSS.getIdPersona());
			model.addAttribute("integrante", integrante);
			model.addAttribute("umfAnterior",circunscripcion.getMedicoEnTurnoDestino());
			model.addAttribute("umfActual",cabeza.getMedicoEnTurno());
			model.addAttribute("domicilioAnterior",circunscripcion.getDomicilioDestino());
			model.addAttribute("domicilioActual",cabeza.getDomicilio());
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		return Constants.DETALLE_CIRCUNSCRIPCION;
	}

	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/cambioTurno")
	public String detalleCambioMedico(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCorreccionDerechohabiente correccion = null;
		GrupoFamiliar integrante = null;
		try {
			Solicitud sol = this.obtenerSolicitud(idTramite);
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			correccion = TramiteUtil.getTramiteCorreccionPorTipo(sol, null);
			
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),correccion.getIdPersona());
			model.addAttribute("integrante", integrante);
			if (correccion.getEstadoTramite().getIdEstadoTramitePersona().longValue() == EstadoTramiteEnum.CERRADO.getId() && correccion.getRazonResultado().getIdRazonResultado().longValue() == RazonResultadoEnum.NORMAL.getId()) {
				model.addAttribute("umfAnterior", integrante.getMedicoEnTurno());
				model.addAttribute("umfActual", correccion.getMedicoEnTurno());
			} else {
				model.addAttribute("umfActual", correccion.getMedicoEnTurno());
				model.addAttribute("umfAnterior", integrante.getMedicoEnTurno());
			}
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		return Constants.DETALLE_CAMBIO_MEDICO;
	}

	@RequestMapping( value = "/detalle/bajas")
	public String detalleBajas(@RequestParam(value = "idPersona") Long idPersona,  
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {
		AsignacionNSS asignacionNSS = (AsignacionNSS) session
		.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		try {
			if(asignacionNSS == null){
				Solicitud sol = this.obtenerSolicitud(idTramite);
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			GrupoFamiliar integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),idPersona);
			model.addAttribute("integrante", integrante);
		} catch (DerechohabientesBusinessException e) {			
			request.setAttribute("exception", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.DETALLE_TRAMITE_DERECHOHABIENTE;
	}
	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/cambioUmf")
	public String detalleCambioUmf(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session
				.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCorreccionDerechohabiente correccion = null;
		GrupoFamiliar integrante = null;
		List<GrupoFamiliar> integrantes = null;
		try {
			Solicitud sol = this.obtenerSolicitud(idTramite);
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			correccion = TramiteUtil.getTramiteCorreccionPorTipo(sol, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
			if(correccion.getPersonas() == null) {
				integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),correccion.getIdPersona());
			} else {
				integrantes = new ArrayList<GrupoFamiliar>();
				for(Fisica persona: correccion.getPersonas())
				{
					integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), persona.getIdPersona());
					integrantes.add(integrante);
				}
				integrante = this.getMayorCalidad(integrantes);
			}
			
			if(integrantes == null)
				model.addAttribute("integrante", integrante);
			else
				model.addAttribute("integrantes",integrantes);
			if (correccion.getEstadoTramite().getIdEstadoTramitePersona().longValue() == EstadoTramiteEnum.CERRADO.getId() && correccion.getRazonResultado().getIdRazonResultado().longValue() == RazonResultadoEnum.NORMAL.getId()) {
				model.addAttribute("umfAnterior", integrante.getMedicoEnTurno());
				model.addAttribute("umfActual", correccion.getMedicoEnTurno());
				model.addAttribute("domicilioAnterior",integrante.getDomicilio());
				model.addAttribute("domicilioActual", correccion.getDomicilio());
				model.addAttribute("idTramite", correccion.getTramiteId());
			} else {
				model.addAttribute("idTramite", correccion.getTramiteId());
				model.addAttribute("umfActual", correccion.getMedicoEnTurno());
				model.addAttribute("umfAnterior", integrante.getMedicoEnTurno());
				model.addAttribute("domicilioAnterior",integrante.getDomicilio());
				model.addAttribute("domicilioActual", correccion.getDomicilio());
			}
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		return Constants.DETALLE_CAMBIO_UMF;
	}

	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/asignacionMedico")
	public String detalleAsignacionMedico(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar integrante = null;
		TramiteCorreccionDerechohabiente correccion = null;
		try {
			Solicitud sol = this.obtenerSolicitud(idTramite);
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			correccion = TramiteUtil.getTramiteCorreccionPorTipo(sol, null);
			
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),correccion.getIdPersona());
			model.addAttribute("medico", correccion.getMedicoEnTurno());
			model.addAttribute("integrante", integrante);
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("exception", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);				
		}
		return Constants.DETALLE_ASIGNACION_MEDICO;
	}
	
	/**
	 * 
	 * @param idTramite
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle/prorroga")
	public String detalleProrroga(
			@RequestParam(value = "idTramite") Long idTramite, Model model,
			HttpServletRequest request, HttpSession session) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar integrante = null;
		TramiteProrroga prorroga = null;
		try {
			if(asignacionNSS == null){
				Solicitud sol = this.obtenerSolicitud(idTramite);
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			
			prorroga = solicitudServiceRemote.recuperaProrrogaXML(idTramite);
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(),prorroga.getPersona().getIdPersona());
			model.addAttribute("prorroga",prorroga);
			model.addAttribute("integrante", integrante);
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("exception", e.getMessage());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
		
		return Constants.DETALLE_PRORROGAS;
	}
	
	/**
	 * Metodo para obtener el detalle de un tramite
	 * 
	 * @param isTramite
	 * @param model
	 * @param reques
	 * @return
	 */
	@RequestMapping(value = "/detalleTramite")
	public String detalleTramite(Long idTramite, Model model,
			HttpServletRequest request) {

		System.out
				.println("************************   detalle del tramite *********************");

		return "mostrarDetalleTramite";

	}
	
	/**
	 * Metodo para pasar los datos de un objeto de tipo GrupoFamiliar aun CorreccionDatoDerechohabiente
	 * @param integrante
	 * @return
	 */
	private TramiteCorreccionDerechohabiente convetirGrupoCorreccion(GrupoFamiliar integrante) {
		TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
		
		correccion.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		correccion.setNombre(integrante.getDerechohabiente().getNombre());
		correccion.setPrimerApellido(integrante.getDerechohabiente().getPrimerApellido());
		correccion.setSegundoApellido(integrante.getDerechohabiente().getSegundoApellido());
		correccion.setCurpCap(integrante.getDerechohabiente().getCurp());
		correccion.setSexo(integrante.getDerechohabiente().getSexo());
		correccion.setFechaNacimiento(integrante.getDerechohabiente().getFechaNacimiento());
		correccion.setLugarNacimiento(integrante.getDerechohabiente().getLugarNacimiento());
		correccion.setParentesco(integrante.getParentesco());
		correccion.setCalidad(integrante.getCalidad().toString());
		correccion.setNss(integrante.getAsignacionNSS().getNssStr());
		correccion.setDomicilio(integrante.getDomicilio());
		correccion.setMedicoEnTurno(integrante.getMedicoEnTurno());
		correccion.setEstadoCivil(integrante.getDerechohabiente().getEstadoCivil());
		
		return correccion;
	}

	/**
	 * MEtodo para obtener al integrante de un grupo familiar con la mayor calidad
	 * donde la mayor es la calidad 1
	 * @param integrantes
	 * @return GrupoFamiliar - Integrante con la mayor calidad
	 */
	private GrupoFamiliar getMayorCalidad(List<GrupoFamiliar> integrantes) {
		GrupoFamiliar mayor = null;
		if(!integrantes.isEmpty()) {
			mayor = integrantes.get(0);
			for(GrupoFamiliar integrante: integrantes) {
				if(integrante.getCalidad().intValue() < mayor.getCalidad().intValue()) {
					mayor = integrante;
				}
			}
		}
		return mayor;
	}
	
	private Solicitud obtenerSolicitud(Long idTramite) {
		
		Solicitud encontrada = null;
		try {
			encontrada = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
		}catch(Exception e) {
			log.error("No se pudo consutar la solicitud", e);
		}
		return encontrada;
	}
	
	@SuppressWarnings("unchecked")
	private List<Modalidad> obtenerModalidadesPatrones(HttpSession session) {
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute("patrones");
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			if(cabeza.getPatronSujetoObligado() != null) {
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
			}
		}
		
		return modalidades;
	}
	
	private List<Long> getModalidadesActivas(List<Modalidad> modalidades) {
		List<Long> idsModalidades = new ArrayList<Long>();
		
		if(modalidades == null || !modalidades.isEmpty()) {
		for(Modalidad mod: modalidades) {
			idsModalidades.add(mod.getIdModalidad());
		}
		}
		
		return idsModalidades;
	}
}