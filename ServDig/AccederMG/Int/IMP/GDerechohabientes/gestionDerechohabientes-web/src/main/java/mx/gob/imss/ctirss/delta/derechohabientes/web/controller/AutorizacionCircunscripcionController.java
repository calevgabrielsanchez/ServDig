package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AutorizacionCircunscripcionServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/derechohabiente/correccion/circunscripcion/")
public class AutorizacionCircunscripcionController extends AbstractController {
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private FinalizaSolicitudServiceRemote finalizaSolicitudServiceRemote;
	@Autowired
	private AutorizacionCircunscripcionServiceRemote autorizacionCircunscripcionServiceRemote;
	@Autowired
	private CambioClinicaServiceRemote cambioClinicaServiceRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	@Autowired
	private FileUploadController fileUploadController; 
	
	private final String KEY_DATOS_AFECTADO = "afectadoAutorizacionCircunscripcion";
	private final String KEY_DATOS_SOLICITUD = "keySolicitudCircunscripcionSesion";
	
	@RequestMapping( value = "/autorizacion")
	public String correccionCircunscripcionHome(Model model,HttpServletRequest request, HttpSession session) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		List<GrupoFamiliar> candidatos;
		
		try {
			candidatos = autorizacionCircunscripcionServiceRemote.findGrupoFamiliarCircunscripcion(asignacionNSS, cabezaGrupo, usuario);

			model.addAttribute("candidatos", candidatos);
			model.addAttribute("descripcionTipoTramite","AUTORIZACIÓN PARA RECIBIR SERVICIOS EN CIRCUNSCRIPCIÓN FORÁNEA");
			model.addAttribute("idTipoTramite",4);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","AUTORIZACIÓN PARA RECIBIR SERVICIOS EN CIRCUNSCRIPCIÓN FORÁNEA");
			request.setAttribute("errores", e.getMessage());
		}

		return Constants.LISTA_CORRECCION_FORWARD;
	}

	@RequestMapping( value = "/autorizacion/datos/{idDerechohabiente}")
	public String cambioCircunscripcion(@PathVariable( value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {

		GrupoFamiliar integranteAfectado = null;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);

		try {
			this.setearDelegacionYUmfOrigen(session, model);
			//se obtiene al integrante del grupo familiar
			integranteAfectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);

			TramiteCorreccionDerechohabiente correccion = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			correccion.setDomicilioAnterior(correccion.getDomicilio());
			
			session.setAttribute(KEY_DATOS_AFECTADO, integranteAfectado);
			
			model.addAttribute("idUmfOrigen",integranteAfectado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			model.addAttribute("idUmfUs",usuario.getIdUmf());
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("hijo",integranteAfectado);
			model.addAttribute("validacion",0);
			model.addAttribute("tipoTramite", TipoTramiteEnum.AUTORIZACION_CIRCUNSCRIPCION.getCodigo());
			
			

			Boolean requiereDocumentos = false;
			requiereDocumentos =fileUploadController.requiereDocumentosTramite(model, datosActuales.getTipoTramite().getIdTipoTramite());
			if(requiereDocumentos) {
				model.addAttribute("tipoDocsNoMostrar", TramiteUtil.quitarTipoDocumentosMenoresEdadad(integranteAfectado.getDerechohabiente().getFechaNacimiento()));
				
			}
			

		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}

		return Constants.AUTORIZACION_CIRCUNSCRIPCION;
	}

	@RequestMapping( value = "/autorizacion/validacion/{idSolicitud}")
	public String validarAutorizacionCir(@PathVariable("idSolicitud") Long idSolicitud,
			HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCircunscripcionForanea circunscripcion = null;
		GrupoFamiliar integrante = null;
		
		try {

			Solicitud solicitudCircunscripcion = new Solicitud(idSolicitud);
			
			solicitudCircunscripcion = solicitudBusinessRemote.consultar(solicitudCircunscripcion);
			
			this.setearDelegacionYUmfOrigen(session, model);
			
			TramiteCorreccionDerechohabiente derechohabiente = new TramiteCorreccionDerechohabiente();

			circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudCircunscripcion);
			
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), circunscripcion.getPersona().getIdPersona());
			
			derechohabiente.setDomicilio(circunscripcion.getDomicilioDestino());
			
			derechohabiente.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoDestino());
			derechohabiente.setTramiteId(circunscripcion.getTramiteId());
			derechohabiente.setObservacion(circunscripcion.getObservacion());
			model.addAttribute("validacion", 1);
			TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(integrante);
			derechohabiente.setDomicilioAnterior(datosActuales.getDomicilio());
			derechohabiente.setParentesco(integrante.getParentesco());
			session.setAttribute(KEY_DATOS_AFECTADO, integrante);
			
			session.setAttribute(KEY_DATOS_SOLICITUD, solicitudCircunscripcion);
			
			model.addAttribute("solicitud", solicitudCircunscripcion);
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("resultado",circunscripcion);
			model.addAttribute("derechohabiente",derechohabiente);
			model.addAttribute("hijo",integrante);
			model.addAttribute("idPersona", circunscripcion.getPersona().getIdPersona());
			
			

		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.AUTORIZACION_CIRCUNSCRIPCION;
	}

	@RequestMapping( value = "/autorizacion/guardar", method = RequestMethod.POST)
	public String guardarAutorizacionCircunscripcion(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente derechohabiente,
			BindingResult result,SessionStatus status,
			HttpSession session, HttpServletRequest request, Model model){


		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar afectado = (GrupoFamiliar) session.getAttribute(KEY_DATOS_AFECTADO);
		String vista="";
		Solicitud solicitudCircunscripcion = null;

		TipoTramite tipoTram = new TipoTramite();
		tipoTram.setIdTipoTramite(TipoTramiteEnum.AUTORIZACION_CIRCUNSCRIPCION.getCodigo());
		tipoTram.setDescripcion("Autorización para recibir servicios en circunscripción foránea".toLowerCase());

		try {

			solicitudCircunscripcion = autorizacionCircunscripcionServiceRemote.saveCircunscripcionAutorizacionDerechohabiente(derechohabiente.getIdPersona(), afectado, usuario, asignacionNSS, derechohabiente, OrigenSolicitudEnum.VENTANILLA);

			TramiteCircunscripcionForanea circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudCircunscripcion);
			try {
				this.salvaDocumentos(session, circunscripcion.getTramiteId(),derechohabiente.getIdPersona());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(usuario.getIdUmf().equals(derechohabiente.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF())) {
				try {
					
					return this.finalizarSolicitud(afectado, solicitudCircunscripcion, asignacionNSS, model, session, usuario);
					
				} catch(DerechohabientesBusinessException e) {
					e.printStackTrace();
					request.setAttribute("exception", e.getMessage());
					request.setAttribute("error", e.getSituacion());
					return "internalError";
				}
			}

			derechohabiente.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoDestino());
			derechohabiente.setTramiteId(circunscripcion.getTramiteId());

			ImpresionReporteDto reporte = new ImpresionReporteDto();
			//Objeto para generar el reporte
			reporte.setIdPersona(circunscripcion.getPersona().getIdPersona());
			reporte.setIdTramite(circunscripcion.getTramiteId());
			reporte.setRechazado(false);
			reporte.setIdUmf(circunscripcion.getMedicoEnTurnoDestino().getUnidadMedicaFamiliar().getIdUMF());
			reporte.setIdUmfUsuario(usuario.getIdUmf());
			reporte.setMensaje("Para finalizar el tramite acudir a la umf destino");
			reporte.setTipoTramite(tipoTram);
			model.addAttribute("reporte", reporte);



			try{	
				// ------------------------------------------------------------
				// Necesitamos el folio de la solicitud en la vista
				// ------------------------------------------------------------
				solicitudCircunscripcion = solicitudBusinessRemote.consultar(solicitudCircunscripcion);
			}catch(SolicitudNoEncontradaException e){
			}


			model.addAttribute("solicitud",solicitudCircunscripcion);

			return "finalizacionTramite";

		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		vista=Constants.FINALIZACION_TRAMITE_CORRECCION;
		return vista;
	}
	
	/**
	 * Marca la solicitud como atendida y genera los documentos resultantes
	 * 
	 * @param solicitudId
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/autorizar/validar", method = RequestMethod.POST)
	public String guardarValidacionAutorizacion(
			@RequestParam(value = "solicitudId") Long solicitudId,HttpSession session, Model model, HttpServletRequest request
			){
			
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_DATOS_SOLICITUD);
		GrupoFamiliar afectado = (GrupoFamiliar) session.getAttribute(KEY_DATOS_AFECTADO);
			
		try {
			
			if(solicitud == null || !solicitud.getSolicitudId().equals(solicitudId)) {
				solicitud = solicitudBusinessRemote.consultar(new Solicitud(solicitudId));
			}
			
			return this.finalizarSolicitud(afectado, solicitud, asignacionNSS, model, session, usuario);
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		
		} catch (SolicitudNoEncontradaException e) {
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			request.setAttribute("exception", e.getMessage());
			return "internalError";
		} 
		
	}
	
	private String finalizarSolicitud(GrupoFamiliar afectado, Solicitud solicitudCircunscripcion, AsignacionNSS asignacionNSS,
			Model model, HttpSession session, Usuario usuario) throws DerechohabientesBusinessException, Exception {
		TramiteCircunscripcionForanea circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudCircunscripcion);
		log.debug("Se finalizara la circunscripcion, la solicitud tiene el id: " + solicitudCircunscripcion.getSolicitudId());
		
		if(afectado == null) {
			//se busca al integrante
			afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), circunscripcion.getPersona().getIdPersona());
		}
		
		List<Long> idsPersonasExcluirDeConsulta = new ArrayList<Long>();
		idsPersonasExcluirDeConsulta.add(afectado.getDerechohabiente().getIdPersona());
		
		Map<String, Object> validacionesCambioMedico = cambioClinicaServiceRemote.getFechaCambioYDatosCambioMedico(asignacionNSS, 
				circunscripcion.getMedicoEnTurnoDestino(), 
				idsPersonasExcluirDeConsulta, false, null, TramiteUtil.permiteCambioMedico(afectado));
		
		solicitudCircunscripcion = autorizacionCircunscripcionServiceRemote.finalizaSolicitudCircunscripcion(solicitudCircunscripcion, asignacionNSS, afectado, validacionesCambioMedico);
		circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudCircunscripcion);
		ImpresionReporteDto reporte = new ImpresionReporteDto();

		//Objeto para generar el reporte
		reporte.setIdPersona(circunscripcion.getPersona().getIdPersona());
		reporte.setIdTramite(circunscripcion.getTramiteId());
		reporte.setRechazado(false);
		reporte.setTipoTramite(circunscripcion.getTipoTramite());
		model.addAttribute("reporte", reporte);

		try{
			TramiteCorreccionDerechohabiente derechohabiente = new TramiteCorreccionDerechohabiente();
			derechohabiente.setIdAsignacionNss(asignacionNSS.getIdAsignacionNSS());
			derechohabiente.setIdPersona(circunscripcion.getPersona().getIdPersona());
			finalizaSolicitudServiceRemote.modificarCambioDerechohabiente(usuario.getCveIdUsuario(), derechohabiente, asignacionNSS);
		}catch(Exception e){
			e.printStackTrace();
		}

		model.addAttribute("solicitud", solicitudCircunscripcion);

		session.removeAttribute(KEY_DATOS_AFECTADO);
		session.removeAttribute(KEY_DATOS_SOLICITUD);

		return "finalizacionTramite";
	}
	
	private Long setearDelegacionYUmfOrigen(HttpSession session, Model model) {
		Long idUmf = null;
		GrupoFamiliar asegurado  = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		AsignacionNSS asignacion = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		MedicoEnTurno adscripcionAsegurado = null;

		if(asegurado == null){
			try {
				asegurado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacion.getIdAsignacionNSS(), asignacion.getIdPersona());
			} catch (DerechohabientesBusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(asegurado != null) {
			session.setAttribute("miGrupoFamiliar", asegurado);
			adscripcionAsegurado = asegurado.getMedicoEnTurno();
			
			if(adscripcionAsegurado != null && adscripcionAsegurado.getUnidadMedicaFamiliar() != null) {
				log.debug("los datos de adscripcion del asegurado no son nulos");
				idUmf = adscripcionAsegurado.getUnidadMedicaFamiliar().getIdUMF(); 
				model.addAttribute("idUmfOrigen",idUmf);
				model.addAttribute("idDelegacionOrigen", adscripcionAsegurado.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId());
			}
		}
		
		return idUmf;
	}
	
	@SuppressWarnings("null")
	private void salvaDocumentos(HttpSession ses,Long idTramite, Long idPersona) throws Exception{
		guardaDocumentosAsincrono.salvaDocumentosProbatoriosSincrono(ses, idTramite, idPersona);
	}
}
