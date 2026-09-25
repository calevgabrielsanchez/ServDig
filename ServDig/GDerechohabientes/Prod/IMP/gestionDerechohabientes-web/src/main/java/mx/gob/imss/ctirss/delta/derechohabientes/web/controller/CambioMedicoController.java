package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioMedicoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping( value = "/derechohabiente/correccion/cambioMedico/")
public class CambioMedicoController extends AbstractController{

	private final String KEY_INTEGRANTE_ASEGURADO = "miGrupoFamiliar";
	private final String KEY_INTEGRANTE_CAMBIO_MEDICO = "integranteCambioMedicoSession";
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	
	@Autowired
	private CambioMedicoServiceRemote cambioMedicoServiceRemote;
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	@Autowired
	private FileUploadController fileUploadController; 
	
	@RequestMapping( value = "/")
	public String correccionMedicoHome(Model model,HttpServletRequest request, HttpSession session) {
		
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO);
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		List<GrupoFamiliar> candidatos = null;;
		try {
			candidatos = cambioMedicoServiceRemote.findGrupoFamiliarCambioMedico(asignacionNSS, usuario);
			model.addAttribute("candidatos", candidatos);
			model.addAttribute("descripcionTipoTramite","CAMBIO DE MÉDICO, TURNO, CONSULTORIO");
			model.addAttribute("idTipoTramite",3);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","CAMBIO DE MÉDICO, TURNO, CONSULTORIO");
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.LISTA_CORRECCION_FORWARD;
	}
	
	@RequestMapping( value = "/posible", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Integer> cambioMedicoPosible(@RequestBody Fisica fisica, Model model, HttpServletRequest request, HttpSession session) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		RespuestaJSON<Integer> respuesta = new RespuestaJSON<Integer>();
		Integer res= cambioMedicoServiceRemote.isCambioMedicoPosible(fisica.getIdPersona(),asignacionNSS) ? 1 : 0;
		respuesta.setModelo(res);
		return respuesta;
	}
	
	@RequestMapping( value = "/datos/{idDerechohabiente}")
	public String cambioMedico(@PathVariable(value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {

		//los objetos que mandaremos a la vista
		TramiteCorreccionDerechohabiente datosActuales=null;
		TramiteCorreccionDerechohabiente correccion = null;
		//Obtenemos las variables de session necesarias
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar aseguradoPensionado = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_ASEGURADO);
		
		GrupoFamiliar integranteAfectado = null;
		
		try {
			//verificamos si la persona a quien se le hara el cambio de medico es al asegurado
			if(idDerechohabiente.equals(asignacionNSS.getIdAsignacionNSS())) {
				//Si es asi, ya no se consulta y se toma de la session 
				integranteAfectado = aseguradoPensionado;
			} else {
				//si no es el asegurado se consulta al integrante del grupo familiar
				integranteAfectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
			}
			
			//se convirten los objetos necesarios para la vista
			datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			correccion = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			//Se setea en sesion al integrante afectado
			session.setAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO, integranteAfectado);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("datosActuales",datosActuales);
			model.addAttribute("hijo", integranteAfectado);
			model.addAttribute("validacion", 0);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		return Constants.CAMBIO_MEDICO_FORWARD;
	}
	
	@RequestMapping( value = "/guardar", method = RequestMethod.POST)
	public String guardarCambioMedico(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			BindingResult result,SessionStatus status,
			HttpSession session, HttpServletRequest request, Model model
			){
		
		String vista = Constants.CAMBIO_MEDICO_FORWARD;
		AsignacionNSS nss = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar integranteAfectado = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		//objetos que mostrara los datos actuales con los que cuenta el integranteafectado
		TramiteCorreccionDerechohabiente datosActuales =null;		
		//Objeto que contendra la solicitud guardada
		Solicitud solicitud = null;
		try {
			//Verificamos si no existia el integrante en la session
			if(integranteAfectado == null) {
				integranteAfectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(nss.getIdAsignacionNSS(), correccion.getIdPersona());
			}
			solicitud = cambioMedicoServiceRemote.crearSolicitudCambioMedico(integranteAfectado, usuario, nss, correccion, OrigenSolicitudEnum.VENTANILLA);
			//se obtiene el tramite de la solicitud guardada
			correccion = TramiteUtil.obtenerCorreccion(solicitud);
			//Se convierte en un objeto correccion los datos del integrante a afectar
			datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			
			//se setea en session la solicitud encontrada
			session.setAttribute(SOLICITUD_KEY, solicitud);
			session.setAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO, integranteAfectado);
			
			//se setean los datos en el modelo
			model.addAttribute("datosActuales",datosActuales);
			model.addAttribute("hijo", integranteAfectado);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("validacion", 1);
			model.addAttribute("solicitud", solicitud);
			
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
		
		return vista;
	}
	
	/**
	 * Metodo para iniciar la validacion del tramite de cambio de consultorio o turno
	 * @param idTramite
	 * @param model
	 * @param request
	 * @param session
	 * @return vista a la cual se redigira
	 */
	@RequestMapping( value = "/validar/{idSolicitud}")
	public String validarCambioMedico(
			@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session){
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		Solicitud solicitudCorreccion = null;
		TramiteCorreccionDerechohabiente tramiteCorreccion = null;
		TramiteCorreccionDerechohabiente datosActuales = null;
		GrupoFamiliar aseguradoPensionado = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_ASEGURADO);
		GrupoFamiliar integranteAfectado = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO);
		
		try {
			//Consultamos la solicitud
			solicitudCorreccion = correccionDerechohabienteServiceRemote.inicioValidacionCorreccion(idSolicitud,asignacionNSS.getIdAsignacionNSS());
			tramiteCorreccion = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
			
			boolean consultarIntegrante = false;
			//verificamos si existe en session el integrante afectado
			if(integranteAfectado == null) {
				//En caso de que no existe en session se verifica si la personas es el asegurado
				if(tramiteCorreccion.getIdPersona().equals(asignacionNSS.getIdPersona())) {
					//en caso de ser el asegurado, se setea en el objeto los datos de la session
					integranteAfectado = aseguradoPensionado;
				} else {
					//si no es el mismo, lo consultaremos de la BDTU
					consultarIntegrante = true;
				}
			} else { //Si encontramos al integrante afectado
				//Verificamos que sea el mismo que viene en el tramite se no serlo lo consultaremos
				if(!integranteAfectado.getDerechohabiente().getIdPersona().equals(tramiteCorreccion.getIdPersona())) {
					consultarIntegrante = true;
				}
			}
			
			if(consultarIntegrante) {//consultamos al integrante afectado en caso de ser necesario
				integranteAfectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), tramiteCorreccion.getIdPersona());
			}
			
			//convertirmos en un objeto correccion al integrante afectado
			datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			//Seteamos en session los objetos necesarios
			session.setAttribute(SOLICITUD_KEY, solicitudCorreccion);
			session.setAttribute(KEY_INTEGRANTE_CAMBIO_MEDICO, integranteAfectado);
			//seteamos en el model los objetos necesarios
			model.addAttribute("derechohabiente",tramiteCorreccion);
			model.addAttribute("miGrupoFamiliar",integranteAfectado);
			model.addAttribute("datosActuales",datosActuales);
			model.addAttribute("hijo",integranteAfectado);
			model.addAttribute("validacion", 1);
			//seteo de variable para saber si se soicitan documentos probatorios o no
			
			Boolean requiereDocumentos = false;
			requiereDocumentos =fileUploadController.requiereDocumentosTramite(model, tramiteCorreccion.getTipoTramite().getIdTipoTramite());
			if(requiereDocumentos) {
				model.addAttribute("tipoDocsNoMostrar", TramiteUtil.quitarTipoDocumentosMenoresEdadad(integranteAfectado.getDerechohabiente().getFechaNacimiento()));
			}
			
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}catch (Exception e) {
			request.setAttribute("exception", ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD);
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		
		return Constants.CAMBIO_MEDICO_FORWARD;
	}
	
	/**
	 * Metodo para guardar la validacion de la correccion de datos de derechohabiente
	 * @param correccion
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/validacion/guardar", method = RequestMethod.POST)
	public Object guardarValidacionCambioMedico(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		TramiteCorreccionDerechohabiente resultado = null;
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		String exception = null;
		String error = null;
		
		try {
			Solicitud solicitudCorreccion = (Solicitud) session.getAttribute(SOLICITUD_KEY);
			log.debug("Se encontró la solicitud en session con folio: " + solicitudCorreccion.getNoFolioSolicitud());
			resultado = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
			
			resultado.setObservacion(correccion.getObservacion());
			resultado.setObservaciones(correccion.getObservaciones());
			resultado.setMedicoEnTurno(correccion.getMedicoEnTurno());
			
			solicitudCorreccion.setTramites(new ArrayList<Tramite>());
			solicitudCorreccion.getTramites().add(correccion);
			
			// ------------------------------------------------------
			// Finalización local
			// ------------------------------------------------------
			solicitudCorreccion = cambioMedicoServiceRemote.finalizaSolicitudCambioMedico(solicitudCorreccion, asignacionNSS);
			resultado = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
			
			//Se guardan los documentos probatorios
			guardaDocumentosAsincrono.salvaDocumentosProbatorios(session, correccion.getTramiteId(), correccion.getIdPersona());
			
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			reporte.setIdUmf(correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			reporte.setIdUmfUsuario(usuario.getIdUmf());
			reporte.setRechazado(false);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo());
			tipoTram.setDescripcion("Cambio de médico, consultorio y turno".toLowerCase());
			reporte.setTipoTramite(tipoTram);
			reporte.setIdPersona(resultado.getIdPersona());
			reporte.setIdTramite(resultado.getTramiteId());
			
			session.removeAttribute(SOLICITUD_KEY);
			
			/*
			 * Redireccionadamos para evistar que en caso de que se presione f5 o se refresque la pantalla
			 * no se vuelvan a hacer todas las peticiones
			 */
			session.setAttribute("reporte", reporte);
			session.setAttribute("solicitud", solicitudCorreccion);

			return new RedirectView("/solicitud/finalizada", true);
		} catch(ImpactaAlmacenesWSException e){
	
			exception =  "Ocurri&oacute; un error al calcular la vigencia. " + e.getMessage();
			
			error = "Intentelo m&aacute;s tarde retomando la solicitud, para esto" +
					" es necesario que en la secci&oacute;n de <strong>'Solicitudes Registradas'</strong> ubique la solicitud " +
					"y de clic en el bot&oacute;n <strong>'Detalle'</strong>, una " +
					"vez que el detalle se despliegue deber&aacute; dar clic en el bot&oacute;n <strong>Validar tr&aacute;mite</strong>. ";
		}catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			exception = e.getMessage();
			error = e.getSituacion();
		}catch (Exception e) {
			e.printStackTrace();
			exception = ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD;
			error =  e.getCause().getMessage();
		}
		
		session.setAttribute("mostrarBoton", true);
		session.setAttribute("exception", exception);
		session.setAttribute("error", error);
		
		return new RedirectView("/solicitud/errorFinalizado", true);
	}
}
