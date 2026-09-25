package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CorreccionDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@RequestMapping( value = "/derechohabiente/correccion/*")
public class CorreccionDerechohabienteController extends AbstractController{
	
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private SolicitudServiceRemote solicitudServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private FinalizaSolicitudServiceRemote finalizaSolicitudService;
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosService;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	@Autowired
	private FileUploadController fileUploadController; 
	
	
	
	//Variables alojadas en sesion
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	
	@RequestMapping( value = "/getMedicoEnTurnoActivo", method = RequestMethod.POST)
	public @ResponseBody MedicoEnTurno buscarMedicoEnTurnoActivo(@RequestBody GrupoFamiliar integrante, Model model,
			HttpServletRequest request, HttpSession session) {
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		MedicoEnTurno medicoEnTurnoActivo = null;
		
		try {
			List<GrupoFamiliar> integrantesEnUmf = grupoFamiliarService.findGrupoFamiliarActivoPorUmf(asignacionNSS.getIdAsignacionNSS(), integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			if(integrantesEnUmf != null) {
				Long parentesco = integrante.getParentesco().getIdParentesco();
				
				if(!integrantesEnUmf.isEmpty()) {
					if(parentesco.equals(ParentescoEnum.ASEGURADO.getId())
							|| parentesco.equals(ParentescoEnum.PENSIONADO.getId()) || parentesco.equals(ParentescoEnum.CONYUGE.getId())) {
						Date fechaUltimoCambio = this.buscarFechaCambioMedico(integrantesEnUmf);
						Long dias = this.diasEntreFechayHoy(fechaUltimoCambio);
						
						if(dias != null) {
							if(dias > 365) {
								return null;
							} else {
								medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
							}
						} else {
							medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
						}
					} else {
						medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
					}
				} else {
					return null;
				}
			}
		} catch (DerechohabientesBusinessException e) {
			medicoEnTurnoActivo = null;
		}
		
		return medicoEnTurnoActivo;
	}
	
	@RequestMapping( value = "/verificarTramites")
	public @ResponseBody List<Tramite> verificarTramite(@RequestBody CorreccionDTO correccion ,Model model,
			HttpServletRequest request, HttpSession session) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		List<Tramite> tramites = null;
		
		try {
			tramites = correccionDerechohabienteServiceRemote.findTramitesAbiertos(asignacionNSS, correccion.getCandidatos());
		} catch (DerechohabientesBusinessException e1) {
			return null;
		}
		
		return tramites;
	}
	
	//cambio umf
	@RequestMapping( value = "/asignacionMedico")
	public String asignarMedicoHome(Model model,HttpServletRequest request, HttpSession session) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		List<GrupoFamiliar> candidatos;
		try {
			candidatos = correccionDerechohabienteServiceRemote.findGrupoFamiliarAsignacionMedico(asignacionNSS, usuario);
			model.addAttribute("candidatos", candidatos);
			model.addAttribute("descripcionTipoTramite","ASIGNACIÓN DE CONSULTORIO, TURNO Y MÉDICO");
			model.addAttribute("idTipoTramite",6);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","ASIGNACIÓN DE CONSULTORIO, TURNO Y MÉDICO");
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Constants.LISTA_CORRECCION_FORWARD;
	}
	
	
	@RequestMapping( value = "/circunscripcion/suspencion")
	public String correccionSuspensionCircunscripcionHome(Model model,HttpServletRequest request, HttpSession session) {
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		List<GrupoFamiliar> candidatos;
		try {
			candidatos = correccionDerechohabienteServiceRemote.findGrupoFamiliarSuspencionCircunscripcion(asignacionNSS, true, usuario);

			model.addAttribute("candidatos", candidatos);
			model.addAttribute("descripcionTipoTramite","SUSPENSIÓN DE CIRCUNSCRIPCIÓN FORANEA");
			model.addAttribute("idTipoTramite",5);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","SUSPENSIÓN DE CIRCUNSCRIPCIÓN FORANEA");
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.LISTA_CORRECCION_FORWARD;
	}	

	@RequestMapping( value = "/asignarMedico/datos/{idDerechohabiente}")
	public String asignarMedicoDerechohabiente(@PathVariable(value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {
		GrupoFamiliar derechohabiente;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		try {
			
			derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
			TramiteCorreccionDerechohabiente correccion = TramiteUtil.convetirGrupoCorreccion(derechohabiente);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("hijo", derechohabiente);
			model.addAttribute("validacion", 0);
			model.addAttribute("umf", usuario.getIdUmf());
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.ASIGNACION_MEDICO_FORWARD;
	}
	
	@RequestMapping( value = "/asignarMedico/validar/{idSolicitud}")
	public String validarAsignarMedicoTurno(
			@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session){
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		Solicitud solicitud = null;
		TramiteCorreccionDerechohabiente tramite;
		GrupoFamiliar derechohabiente;
		
		try {
			
			solicitud = correccionDerechohabienteServiceRemote.inicioValidacionCorreccion(idSolicitud, asignacionNSS.getIdAsignacionNSS());
			
			tramite = TramiteUtil.obtenerCorreccion(solicitud);
			
			session.setAttribute(SOLICITUD_KEY, solicitud);
			
			derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), tramite.getIdPersona());
			model.addAttribute("derechohabiente",tramite);
			model.addAttribute("hijo", derechohabiente);
			model.addAttribute("validacion", 1);
			model.addAttribute("umf", usuario.getIdUmf());
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		
		return Constants.ASIGNACION_MEDICO_FORWARD;
	}
	
	
	
	
	
	@RequestMapping( value = "/asignarMedico/validacion/guardar", method = RequestMethod.POST)
	public String guardarValidacionAsignarMedico(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		try {
			Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(correccion);
			
			solicitud = correccionDerechohabienteServiceRemote.saveValidacionAsignacionMedico(solicitud, asignacionNSS,usuario.getFisica());
			
			TramiteCorreccionDerechohabiente resultado = TramiteUtil.obtenerCorreccion(solicitud);
			
			model.addAttribute("resultado", resultado);
			
			model.addAttribute("titulo", "Asignar consultorio,médico o turno".toUpperCase());
			model.addAttribute("idTipoTramite",6);
			model.addAttribute("idTramite", resultado.getTramiteId());
			model.addAttribute("idPersona", resultado.getIdPersona());
			model.addAttribute("umf", "1");
			model.addAttribute("umfUsuario", "1");
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.FINALIZACION_TRAMITE_CORRECCION;
	}
	
	@RequestMapping( value = "/tramite/autorizacion/{idSolicitud}")
	public String getTramiteEsperaAutorizacion(@PathVariable( value = "idSolicitud") Long idSolicitud,
			Model model, HttpSession session, HttpServletRequest request) {
		
		GrupoFamiliar derechohabiente;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		try {
			
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(idSolicitud);
			}
			
			Solicitud correccionSol = correccionDerechohabienteServiceRemote.inicioValidacionCorreccion(idSolicitud, asignacionNSS.getIdAsignacionNSS());
			TramiteCorreccionDerechohabiente correccion = (TramiteCorreccionDerechohabiente) correccionSol.getTramites().get(0);
			
			if(correccion.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())
					&& correccion.getSexo().getIdSexo().equals(SexoEnum.MUJER.getId())){
				correccion.getParentesco().setIdParentesco(ParentescoEnum.MADRE.getId());
			}
			derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), correccion.getIdPersona());
			TramiteCorreccionDerechohabiente datosActuales= TramiteUtil.convetirGrupoCorreccion(derechohabiente);
			model.addAttribute("derechohabiente", correccion) ;
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("miGrupoFamiliar",derechohabiente);
			model.addAttribute("hijo",derechohabiente);
			model.addAttribute("solicitud", correccionSol);
			session.setAttribute(SOLICITUD_KEY, correccionSol);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.AUTORIZACION_CORRECCION;
	}
	
	@RequestMapping( value = "/tramite/autorizar/guardar")
	public String getGuardarTramiteEsperaAutorizacion(@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente tramite,
			Model model, HttpSession session, HttpServletRequest request) {
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			Solicitud solicitudCorreccion = null;
			TramiteCorreccionDerechohabiente resultado = null;
			AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
			Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
			
			try {
				
				solicitudCorreccion = (Solicitud) session.getAttribute(SOLICITUD_KEY);
				
				if(asignacionNSS == null){
					asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(solicitudCorreccion.getSolicitudId());
				}
				
				solicitudCorreccion=correccionDerechohabienteServiceRemote.saveSolicitudAceptada(solicitudCorreccion.getSolicitudId(),asignacionNSS,usuario.getFisica());
				resultado = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
				//Objeto para generar el reporte
				reporte.setIdPersona(resultado.getIdPersona());
				reporte.setIdTramite(resultado.getTramiteId());
				reporte.setRechazado(false);
				TipoTramite tipoTram = new TipoTramite();
				tipoTram.setIdTipoTramite(TipoTramiteEnum.CORRECCION_DATOS.getCodigo());
				tipoTram.setDescripcion("Corrección de Datos".toLowerCase());
				reporte.setTipoTramite(tipoTram);
				model.addAttribute("reporte", reporte);
				return "finalizacionTramite";
			} catch (DerechohabientesBusinessException e) {
				e.printStackTrace();
				request.setAttribute("exception", e.getMessage());
				request.setAttribute("error", e.getSituacion());
				return "internalError";
			} catch (Exception e) {
				e.printStackTrace();
				return "internalError";
			}
			
	}
	
	
	
	
	@RequestMapping( value = "/circunscripcion/suspencion/datos/{idDerechohabiente}")
	public String suspencionCircunscripcion(@PathVariable( value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCircunscripcionForanea circunscripcion = null;
		GrupoFamiliar integrante = null;
		GrupoFamiliar cabeza = null;
		try {
			
			circunscripcion = correccionDerechohabienteServiceRemote.getCircunscripcionForanea(idDerechohabiente, asignacionNSS, true);
			integrante = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
			cabeza = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), asignacionNSS.getIdPersona());
			model.addAttribute("integrante", integrante);
			
			
			circunscripcion.setDomicilioOrigen(circunscripcion.getDomicilioDestino());
			circunscripcion.setDomicilioDestino(cabeza.getDomicilio());
			model.addAttribute("derechohabiente", circunscripcion);
			model.addAttribute("umfAnterior", circunscripcion.getMedicoEnTurnoDestino());
			model.addAttribute("umfActual", cabeza.getMedicoEnTurno());
			model.addAttribute("domicilioAnterior", circunscripcion.getDomicilioDestino());
			model.addAttribute("domicilioActual", cabeza.getDomicilio());
			model.addAttribute("hijo", integrante);
			model.addAttribute("validacion", 0);
			model.addAttribute("tipoTramite", TipoTramiteEnum.SUSPENSION_CIRCUNSCRIPCION.getCodigo());
			
			
			Boolean requiereDocumentos = false;
			requiereDocumentos =fileUploadController.requiereDocumentosTramite(model, TipoTramiteEnum.SUSPENSION_CIRCUNSCRIPCION.getCodigo());
			if(requiereDocumentos) {	
				model.addAttribute("tipoDocsNoMostrar", TramiteUtil.quitarTipoDocumentosMenoresEdadad(integrante.getDerechohabiente().getFechaNacimiento()));
			}
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.SUSPENSION_CIRCUNSCRIPCION;
	}
	
	@RequestMapping( value = "/asignarMedico/guardar", method = RequestMethod.POST)
	public String guardarAsignarMedico(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente datos,
			BindingResult result,SessionStatus status,
			HttpSession session, HttpServletRequest request, Model model
			) {
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		Long perfil = 0L;
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
				String vista="";
				
				try {

					GrupoFamiliar derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), datos.getIdPersona());
					TramiteCorreccionDerechohabiente correccion = null;
					Solicitud sol = correccionDerechohabienteServiceRemote.saveAsignacionMedico(datos.getIdPersona(), usuario, asignacionNSS, datos,OrigenSolicitudEnum.VENTANILLA);
					correccion = TramiteUtil.obtenerCorreccion(sol);
					
					if(perfil.longValue() != PerfilesEnum.TRAMITADOR.getId().longValue()) {

						request.setAttribute("solicitud", sol);
						request.setAttribute("tituloComprobante","ASIGNACIÓN DE UNIDAD MEDICA FAMILIAR");
						vista = Constants.CITA_SOLICITUD_GENERICA;
					} else {
						
						session.setAttribute(SOLICITUD_KEY, sol);
						
						vista = Constants.ASIGNACION_MEDICO_FORWARD;
						model.addAttribute("derechohabiente", correccion);
						model.addAttribute("umf", usuario.getIdUmf());
						model.addAttribute("hijo", derechohabiente);
						model.addAttribute("validacion", 1);
					}

				} catch (DerechohabientesBusinessException e) {
					e.printStackTrace();
					request.setAttribute("errores", e.getMessage());
				}
		
		return vista;
	}
	
	@RequestMapping( value = "/circunscripcion/suspension/validar", method = RequestMethod.POST)
	public String guardarValidacionSuspension(
			@ModelAttribute TramiteCircunscripcionForanea suspension,HttpSession session, Model model
			){
			
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar cabeza = null;
			
		try {
			TramiteCircunscripcionForanea circunscripcion=correccionDerechohabienteServiceRemote.saveValidacionSuspensionDerechohabiente(suspension, usuario, asignacionNSS); 
			cabeza = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(),asignacionNSS.getIdPersona());
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			reporte.setIdUmf(cabeza.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			reporte.setIdUmfUsuario(usuario.getIdUmf());
			if(reporte.getIdUmf().longValue() != reporte.getIdUmfUsuario().longValue())
				reporte.setMensaje("Debe acudir a la umf destino para finalizar el trámite");
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(TipoTramiteEnum.SUSPENSION_CIRCUNSCRIPCION.getCodigo());
			tipoTram.setDescripcion("Suspención de servicios en circunscripción foránea".toLowerCase());
			reporte.setTipoTramite(tipoTram);
			reporte.setIdPersona(circunscripcion.getPersona().getIdPersona());
			reporte.setIdTramite(circunscripcion.getTramiteSuspension().getTramiteId());
			reporte.setRechazado(false);
			
			try{	
				
				Solicitud solicitudCircunscripcion = solicitudBusinessRemote.consultarPorIdTramite(circunscripcion.getTramiteSuspension().getTramiteId());
				 
				// -----------------------------------------------------
				// Asigna al tramite los documentos que se generaran
				// -----------------------------------------------------
				log.debug("=========== CONSULTAR SOLICITUD CIRCUNSCRIPCION =================================");
				solicitudCircunscripcion = solicitudBusinessRemote.consultar(solicitudCircunscripcion);
				
				FirmaElectronica firma = new FirmaElectronica();
				firma.setSecuenciaNotaria(solicitudCircunscripcion.getSecuenciaDeNotaria());
				firma.setCadenaOriginal(solicitudCircunscripcion.getCadenaOriginal());
				firma.setSerialCertificado(solicitudCircunscripcion.getNumeroSerieCertificado());
				firma.setRecibo(solicitudCircunscripcion.getSelloDigital());
				solicitudCircunscripcion.setFirmaElectronica(firma);
				
				if( solicitudCircunscripcion.getSolicitudId() != null) {
				
					Derechohabiente der = new Derechohabiente();
					der.setAsignacionNSS(asignacionNSS);
					
					if(solicitudCircunscripcion.getTramites() != null && !solicitudCircunscripcion.getTramites().isEmpty()){
						for(Tramite tramite: solicitudCircunscripcion.getTramites()){
							der.setIdPersona(tramite.getPersona().getIdPersona());
							tramite.setPersona(der);
						}
					}
					
					
					OrigenSolicitud origen = new OrigenSolicitud();
					origen.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
					solicitudCircunscripcion.setOrigenSolicitud(origen);
					
					// ------------------------------------------------------
					// Genera los documentos resultantes
					// ------------------------------------------------------
					log.debug("=========== GUARDAR DOCUMENTOS RESULTANTES CIRCUNSCRIPCION =========================");
					solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitudCircunscripcion);
					
					
					try{
						finalizaSolicitudService.modificarSuspensionCircunscripcion(solicitudCircunscripcion, usuario.getCveIdUsuario(), asignacionNSS);
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}else{
					
					log.debug(" ============== RESULTANTES CIRCUNSCRIPCION ================================= ");
					log.debug(solicitudCircunscripcion);
					
				}
			
				
				model.addAttribute("solicitud", solicitudCircunscripcion);
				
			// -----------------------------------------------------------------
			// La solicitud ya fue finalizada en la base y en el WS,
			// se informa que el trámite fue exitoso aunque no se generen
			// los documentos resultantes	
			// -----------------------------------------------------------------
			}catch(SolicitudNoEncontradaException e){
			}catch(SolicitudNoValidaException e){
			}
			
			
			
			
			model.addAttribute("reporte", reporte);
			return "finalizacionTramite";
			
		} catch (DerechohabientesBusinessException e) {
			model.addAttribute("exception", e.getMessage());
			model.addAttribute("error", e.getSituacion());
			return "internalError";
		}
	}
	
	
/*	@RequestMapping( value = "/circunscripcion/suspension/validar", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<CircunscripcionForanea> guardarValidacionSuspension(
			@RequestBody CircunscripcionForanea suspension,HttpSession session
			){
			
			RespuestaJSON<CircunscripcionForanea> respuesta = new RespuestaJSON<CircunscripcionForanea>();
			this.llenarObjetosSesion(session);
			
		try {
			CircunscripcionForanea circunscripcion=correccionDerechohabienteServiceRemote.saveValidacionSuspensionDerechohabiente(suspension, usuario, asignacionNSS); 
			
			respuesta.setMensaje("Se ha suspendido correctamente la circunscripcion");
			respuesta.setModelo(circunscripcion);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			respuesta.setModelo(null);
			respuesta.setMensaje("No fue posible guardar la solicitud: " + e.getSituacion());
			List<String> errores = new ArrayList<String>();
			errores.add(e.getMessage());
			respuesta.setErrores(errores);
		}
		
		return respuesta;
	}*/
	
	@RequestMapping( value = "/circunscripcion/suspension/validacion/{idSolicitud}", method = RequestMethod.GET)
	public String getValidacionSuspension(@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request,HttpSession session){
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Solicitud solicitudSuspencion = null;
		TramiteCircunscripcionForanea circunscripcion = null;
		GrupoFamiliar integrante = null;
		GrupoFamiliar cabeza = null;
			
		try {
			solicitudSuspencion = correccionDerechohabienteServiceRemote.getCircunscripcionTramite(idSolicitud); 
			cabeza = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), asignacionNSS.getIdPersona());
			
			circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudSuspencion);
			integrante = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), circunscripcion.getPersona().getIdPersona());
			
			
			circunscripcion.setDomicilioOrigen(circunscripcion.getDomicilioDestino());
			circunscripcion.setDomicilioDestino(cabeza.getDomicilio());
		
			model.addAttribute("integrante", integrante);
			model.addAttribute("derechohabiente", circunscripcion);
			model.addAttribute("umfAnterior", circunscripcion.getMedicoEnTurnoDestino());
			model.addAttribute("umfActual", cabeza.getMedicoEnTurno());
			model.addAttribute("domicilioAnterior", circunscripcion.getDomicilioDestino());
			model.addAttribute("domicilioActual", cabeza.getDomicilio());
			model.addAttribute("hijo", integrante);
			model.addAttribute("validacion", 1);
			model.addAttribute("observaciones", circunscripcion.getTramiteSuspension().getObservacion());
			model.addAttribute("tipoTramite", TipoTramiteEnum.SUSPENSION_CIRCUNSCRIPCION.getCodigo());
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.SUSPENSION_CIRCUNSCRIPCION;
	}
	
	@RequestMapping( value = "/circunscripcion/suspencion/guardar", method = RequestMethod.POST)
	public String guardarSuspencionCircunscripcion(@ModelAttribute Tramite tramite, Model model, HttpSession session) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar cabeza = null;
		Solicitud solicitudCircunscripcion = null;
		TramiteCircunscripcionForanea circunscripcion = null;
		try {
			solicitudCircunscripcion = correccionDerechohabienteServiceRemote.saveCircunscripcionSuspensionDerechohabiente(tramite.getTramiteId(),tramite.getObservacion(), usuario, asignacionNSS);
			cabeza = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(),asignacionNSS.getIdPersona());
			
			circunscripcion = TramiteUtil.obtenerCircunscripcion(solicitudCircunscripcion);
			
			try {
				this.salvaDocumentos(session, circunscripcion.getTramiteSuspension().getTramiteId(),circunscripcion.getPersona().getIdPersona());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			reporte.setIdUmf(cabeza.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			reporte.setIdUmfUsuario(usuario.getIdUmf());
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(TipoTramiteEnum.SUSPENSION_CIRCUNSCRIPCION.getCodigo());
			tipoTram.setDescripcion("Suspención de servicios en circunscripción foránea".toLowerCase());
			reporte.setTipoTramite(tipoTram);
			reporte.setIdPersona(circunscripcion.getPersona().getIdPersona());
			reporte.setIdTramite(circunscripcion.getTramiteSuspension().getTramiteId());
			reporte.setRechazado(false);
			
			
			if(cabeza.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().equals(usuario.getIdUmf())) {
				
				correccionDerechohabienteServiceRemote.saveValidacionSuspensionDerechohabiente(circunscripcion, usuario, asignacionNSS);
				
				try{	
					
					// -----------------------------------------------------
					// Asigna al tramite los documentos que se generaran
					// -----------------------------------------------------
					log.debug("=========== CONSULTAR SOLICITUD CIRCUNSCRIPCION =================================");
					solicitudCircunscripcion = solicitudBusinessRemote.consultar(solicitudCircunscripcion);
					
					FirmaElectronica firma = new FirmaElectronica();
					firma.setSecuenciaNotaria(solicitudCircunscripcion.getSecuenciaDeNotaria());
					firma.setCadenaOriginal(solicitudCircunscripcion.getCadenaOriginal());
					firma.setSerialCertificado(solicitudCircunscripcion.getNumeroSerieCertificado());
					firma.setRecibo(solicitudCircunscripcion.getSelloDigital());
					solicitudCircunscripcion.setFirmaElectronica(firma);
					
					if( solicitudCircunscripcion.getSolicitudId() != null) {
					
						
						Derechohabiente der = new Derechohabiente();
						der.setAsignacionNSS(asignacionNSS);
						
						if(solicitudCircunscripcion.getTramites() != null && !solicitudCircunscripcion.getTramites().isEmpty()){
							for(Tramite _tramite: solicitudCircunscripcion.getTramites()){
								der.setIdPersona(_tramite.getPersona().getIdPersona());
								_tramite.setPersona(der);
							}
						}
						
						OrigenSolicitud origen = new OrigenSolicitud();
						origen.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
						solicitudCircunscripcion.setOrigenSolicitud(origen);
						
						// ------------------------------------------------------
						// Genera los documentos resultantes
						// ------------------------------------------------------
						log.debug("=========== GUARDAR DOCUMENTOS RESULTANTES CIRCUNSCRIPCION =========================");
						solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitudCircunscripcion);
					
						
						try{
							finalizaSolicitudService.modificarSuspensionCircunscripcion(solicitudCircunscripcion, usuario.getCveIdUsuario(), asignacionNSS);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
					}else{
						
						log.debug(" ============== RESULTANTES CIRCUNSCRIPCION ================================= ");
						log.debug(solicitudCircunscripcion);
						
					}
				
					
					
				// -----------------------------------------------------------------
				// La solicitud ya fue finalizada en la base y en el WS,
				// se informa que el trámite fue exitoso aunque no se generen
				// los documentos resultantes	
				// -----------------------------------------------------------------
				}catch(SolicitudNoEncontradaException e){
				}catch(SolicitudNoValidaException e){
				}
				
				
				
			} else {
				
				if(reporte.getIdUmf().longValue() != reporte.getIdUmfUsuario().longValue())
					reporte.setMensaje("Debe acudir a la umf destino para finalizar el trámite");
				
				
				
				try{	
					// ------------------------------------------------------------
					// Necesitamos el folio de la solicitud en la vista
					// ------------------------------------------------------------
					solicitudCircunscripcion = solicitudBusinessRemote.consultar(solicitudCircunscripcion);
				}catch(SolicitudNoEncontradaException e){
				}
				
			}
						
			
			model.addAttribute("reporte", reporte);
			model.addAttribute("solicitud", solicitudCircunscripcion);
			return "finalizacionTramite";
			
		} catch (DerechohabientesBusinessException e) {
			model.addAttribute("exception", e.getMessage());
			model.addAttribute("error", e.getSituacion());
			return "internalError";
		} catch (IllegalArgumentException e) {
			model.addAttribute("exception", e.getMessage());
			return "internalError";
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
			return "internalError";
		}
		

	}
	
	
	
	/**
	 * Metodo que valida los campos de la correccion de datos del derechohabiente 
	 * @param tramite
	 * @param session
	 * @return
	 */
	
	@RequestMapping( value = "/datos/correccion/validar", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<TramiteCircunscripcionForanea> validarCorreccionDatos(@RequestBody TramiteCorreccionDerechohabiente datos, 
			Model model, HttpServletRequest request, HttpSession session) {
		RespuestaJSON<TramiteCircunscripcionForanea> respuesta = new RespuestaJSON<TramiteCircunscripcionForanea>();
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		try {
			correccionDerechohabienteServiceRemote.validarDatosDerechohabiente(datos, asignacionNSS);
			respuesta.setMensaje("Se han realizado correctamente los cambios");
			respuesta.setEstado(true);
						
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			respuesta.setModelo(null);
			List<String> errores = new ArrayList<String>();
			errores.add(e.getMessage());
			respuesta.setErrores(errores);
			respuesta.setEstado(false);
		}
		return respuesta;
	}
	
	@SuppressWarnings("unused")
	private boolean cambioDomicilio(Domicilio nuevo, Domicilio anterior) {

		boolean resultado = false;

		if (!nuevo.getCodigoPostal().getCodigoPostal().equals(anterior.getCodigoPostal().getCodigoPostal())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo.getAsentamiento().getClave().equals(anterior.getAsentamiento().getClave())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo.getAsentamiento().getLocalidad().getClave().equals(anterior.getAsentamiento().getLocalidad().getClave())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo
				.getAsentamiento()
				.getLocalidad()
				.getMunicipio()
				.getClave()
				.equals(anterior.getAsentamiento().getLocalidad()
						.getMunicipio().getClave())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo
				.getAsentamiento()
				.getLocalidad()
				.getMunicipio()
				.getEntidadFederativa()
				.getClave()
				.equals(anterior.getAsentamiento().getLocalidad()
						.getMunicipio().getEntidadFederativa().getClave())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo.getVialidadPrimaria().getClave()
				.equals(anterior.getVialidadPrimaria().getClave())) {
			resultado = true;
			return resultado;
		}
		if ((nuevo.getNumExterior1()!=null || anterior.getNumExterior1()!=null) && 
				!nuevo.getNumExterior1().equals(anterior.getNumExterior1())) {
			resultado = true;
			return resultado;
		}
		if ((nuevo.getNumExteriorAlf()!=null || anterior.getNumExteriorAlf() != null)&& !nuevo.getNumExteriorAlf().equals(anterior.getNumExteriorAlf())) {
			resultado = true;
			return resultado;
		}
		if ((nuevo.getNumInterior()!=null || anterior.getNumInterior()!=null)&&
				!nuevo.getNumInterior().equals(anterior.getNumInterior())) {
			resultado = true;
			return resultado;
		}
		if (nuevo.getNumInteriorAlf()!=null && !nuevo.getNumInteriorAlf().equals(anterior.getNumInteriorAlf())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo.getVialidadReferenciaPrimaria().getClave().equals(anterior.getVialidadReferenciaPrimaria().getClave())) {
			resultado = true;
			return resultado;
		}
		if (!nuevo.getVialidadReferenciaSecundaria().getClave()
				.equals(anterior.getVialidadReferenciaSecundaria().getClave())) {
			resultado = true;
			return resultado;
		}
		if(nuevo.getVialidadReferenciaPosterior() != null){
			if(anterior.getVialidadReferenciaPosterior() !=null) {
				if(nuevo.getVialidadReferenciaPosterior().getClave() != null) {
					if (!nuevo.getVialidadReferenciaPosterior().getClave()
							.equals(anterior.getVialidadReferenciaPosterior().getClave())) {
						resultado = true;
						return resultado;
					}
				}
			} else {
				return true;
			}
		}
		return resultado;
	}
	
	private Long diasEntreFechayHoy(Date fecha) {
		
		if(fecha== null)
			return null;
		
		long tiempo1 = new Date().getTime();
		
		long tiempo2 = fecha.getTime();
		Long diasTranscrurridos = (tiempo1 - tiempo2) /(24 * 60 * 60 * 1000);
		return diasTranscrurridos;
	}
	
	private Date buscarFechaCambioMedico(List<GrupoFamiliar> entrada) {
		
		for(GrupoFamiliar integrante: entrada) {
			if(integrante.getFechaCambioTurnoMedico() != null)
				return integrante.getFechaCambioTurnoMedico();
		}
		return null;
	}
	
	
	/**
	 * Valida el domicilio que se asignara en base al nuevo parentesco seleccionado
	 * 
	 * @param integrante
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/validarDomicilioParentesco")
	public @ResponseBody RespuestaJSON<Object> validarDomicilioParentesco(@RequestBody GrupoFamiliar integrante,Model model,
			HttpServletRequest request, HttpSession session) {
	
		GrupoFamiliar miGrupoFamiliar = (GrupoFamiliar)session.getAttribute("miGrupoFamiliar");
		RespuestaJSON<Object> respuesta = new RespuestaJSON<Object>();
		Boolean isPatronIMSS = (Boolean) session.getAttribute("patronIMSS");

		try {

			Map<String,Object> result = requisitosMinimosServiceRemote.validarEleccionDeDomicilioYUmfPorPersonaParentesco(
					integrante.getDerechohabiente().getPersona(),integrante.getParentesco().getIdParentesco(), miGrupoFamiliar.getDomicilio(),isPatronIMSS);
			
			result.put("domicilioAsegurado",  miGrupoFamiliar.getDomicilio());
			
			respuesta.setModelo(result);
			
		} catch (IllegalArgumentException e) {
		
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("error", true);
			result.put("mensajeError", e.getMessage());
			respuesta.setModelo(result);
		
		} catch (Exception e) {
			
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("error", true);
			result.put("mensajeError", "Ocurrio un error en la aplicación al validar el domicilio para el nuevo parentesco");
			respuesta.setModelo(result);
		
		}
	
		return respuesta;
	}
	
	private void salvaDocumentos(HttpSession ses,Long idTramite, Long idPersona) throws Exception{		
		guardaDocumentosAsincrono.salvaDocumentosProbatoriosSincrono(ses, idTramite, idPersona);
	}
	
}