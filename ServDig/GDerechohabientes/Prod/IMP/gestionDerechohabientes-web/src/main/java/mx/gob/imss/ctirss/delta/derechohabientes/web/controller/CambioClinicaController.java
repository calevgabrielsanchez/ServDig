package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping( value = "/derechohabiente/correccion/cambioUmf")
public class CambioClinicaController extends AbstractController {

	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private CambioClinicaServiceRemote cambioClinicaServiceRemote;
	@Autowired
	private GestionDocumentalServiceRemote gestionDocumentalServiceRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	private Boolean requiereDocumentos = false;
	

	//Variables alojadas en sesion
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	private static final String REDIRECT_CORRECTO = "/solicitud/finalizada";
	private static final String REDIRECT_ERROR = "/solicitud/errorFinalizado";
	
	@RequestMapping( value = "/origen")
	public String correccionUmfHomeOrigen(Model model,HttpServletRequest request, HttpSession session) {
		//invocamos al metodo general para pasamos el ultimo parametro en true ya que estamos en la umf origen
		return this.inicioTramiteCambioClinica(model, request, session, true);
	}
	
	@RequestMapping( value = "/destino")
	public String correccionUmfHomeDestino(Model model,HttpServletRequest request, HttpSession session) {
		//invocamos al metoso general pero pasamos false en el ultimo parametro para que sepamos que no estamos en la umf origen
		return this.inicioTramiteCambioClinica(model, request, session, false);
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @param origen
	 * @return
	 */
	private String inicioTramiteCambioClinica(Model model,HttpServletRequest request, HttpSession session, Boolean clinicaOrigen) {
		//Obtenemos los datos de session
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		session.setAttribute("requiereDocumentacion", requiereDocumentos);
		
		List<GrupoFamiliar> candidatos;
		
		try {
			//Verificamos si el patron de el asegurado es el IMSS
			Boolean patronImss = cabeza.getPatronImss().equals(1);
			//Verificamos a los candidatos el ultimo paramtro debe ser tru si estas en la umf origen
			candidatos = cambioClinicaServiceRemote.findGrupoFamiliarCambioUmf(asignacionNSS, usuario,patronImss,clinicaOrigen);
			//Se pasa a la vista a los candidatos
			model.addAttribute("candidatos", candidatos);
			model.addAttribute("descripcionTipoTramite","CAMBIO DE UNIDAD MEDICA FAMILIAR");
			model.addAttribute("idTipoTramite",2);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","CAMBIO DE UNIDAD MEDICA FAMILIAR");
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Constants.CAMBIO_CLINICA_HOME;
	}

	@RequestMapping( value = "/datos/", method = RequestMethod.POST)
	public String cambioUmf(@RequestParam(value = "candidato") List<String> candidato,
			Model model, HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
	
		TramiteCorreccionDerechohabiente correccion = null;
		TramiteCorreccionDerechohabiente datosActuales = null;
		String tipoDocsNoMostrar = "";
		int tieneAcuerdo = 0;

		try {
			this.setearDelegacionYUmfOrigen(session, model);
			
			List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
			GrupoFamiliar mayorCalidad = null;
			
			//Obtenemos el detalle de cada uno de los integrantes que seran cambiados
			for(String integrante: candidato) {
				GrupoFamiliar derechohabiente = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), new Long(integrante));
				integrantes.add(derechohabiente);
				//Se obtiene el parentesco
				Long idParentesco = derechohabiente.getParentesco().getIdParentesco().longValue();
				if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())){
					model.addAttribute("esAsegurado",true);
				}
				
				tieneAcuerdo += derechohabiente.getIndAcuerdo();
			}
			
			model.addAttribute("tieneAcuerdo", tieneAcuerdo);
			
			//Verificamos que la lista de integrantes no sea vacia de ser asi obtenemos 
			//al de mayor calidad y esos son los datos que se mostraran en pantalla ;)
			if(!integrantes.isEmpty()) {
				mayorCalidad = TramiteUtil.getMayorCalidad(integrantes);
				correccion = TramiteUtil.convetirGrupoCorreccion(mayorCalidad);
				correccion.setDomicilioAnterior(mayorCalidad.getDomicilio());
				
				datosActuales = TramiteUtil.convetirGrupoCorreccion(mayorCalidad);
			}

			//Verificamos donde esta haciendo el tramite
			if(usuario.getIdUmf().equals(mayorCalidad.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF())) {
				correccion.setEnUmfDestino(0);
			} else {
				correccion.setEnUmfDestino(1);
			}
			
			correccion.setTipoTramite(new TipoTramite());
			correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
			
			
			// -----------------------------------------------------------------------
			// Si el cambio es para mas de un integrante, se pide identificación
			// Si es para uno, se valida la edad
			// -----------------------------------------------------------------------
			if( integrantes.size() == 1 )
				tipoDocsNoMostrar = TramiteUtil.quitarTipoDocumentosMenoresEdadad(correccion.getFechaNacimiento());
			
			
			model.addAttribute("documentacion",0);
			model.addAttribute("datosActuales",datosActuales);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("validacion",0);
			model.addAttribute("tipoDocsNoMostrar", tipoDocsNoMostrar);
			
			request.setAttribute("integrantes", integrantes);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}

		return Constants.CAMBIO_UMF_FORWARD;

	}
	
	/**
	 * MEtodo para setear los datos para la impresion de los documentos resultantes
	 * @param solicitud
	 * @param request
	 * @return
	 */
	private ImpresionReporteDto obtenerDatosImpresion(Solicitud solicitud, HttpServletRequest request) {
		TramiteCorreccionDerechohabiente correccion = TramiteUtil.getTramiteCorreccionPorTipo(solicitud, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		reporte.setIdUmf(correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
		reporte.setIdUmfUsuario(solicitud.getSolicitante().getIdUmf());
		Long idPersona = null;
		if(correccion.getIdPersona() != null) {
			idPersona = correccion.getIdPersona();
		} else {
			idPersona = correccion.getCandidatosCambioClinica().get(0);
		}
		reporte.setIdPersona(idPersona);
		reporte.setIdTramite(correccion.getTramiteId());
		reporte.setRechazado(false);

		if(reporte.getIdUmf().longValue() != reporte.getIdUmfUsuario().longValue()) {
			reporte.setMensaje("Debe acudir a la umf destino para finalizar el trámite");
			reporte.setMuestraBotonImpresion(false);
			request.setAttribute("impresionSav005", true);
		} else {
			reporte.setMuestraBotonImpresion(true);
			request.setAttribute("impresionSav005", false);
		}

		TipoTramite tipoTram = new TipoTramite();
		tipoTram.setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
		tipoTram.setDescripcion("Cambio de clínica".toLowerCase());
		reporte.setTipoTramite(tipoTram);
		
		return reporte;

	}

	@RequestMapping( value = "/guardar", method = RequestMethod.POST)
	public Object guardarCambioUmf(@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente derechohabiente,
			BindingResult result, SessionStatus status, HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		Solicitud solicitud = null;
		String exception = null;
		String error = null;
		log.debug("guardar de cambio de clinica");
		
		session.removeAttribute("requiereDocumentacion");
		try {
			log.debug("Se comienza con el guardado de cambio de clinica");
			//seteamos los datos del nss en el objeto de cambio de clinica
			derechohabiente.setIdAsignacionNss(asignacionNSS.getIdAsignacionNSS());
			derechohabiente.setNss(asignacionNSS.getNss());
			//Se buscan a los paddres y concubinas del asegurado
			List<GrupoFamiliar> padresConcubinasUnionCivil = cambioClinicaServiceRemote.getPadresConcubinasParaCambio(asignacionNSS, cabeza.getPatronImss());
			//se crea la solicitud de cambio de clinica con los datos proporcionados
			solicitud = cambioClinicaServiceRemote.crearSolicitudCambioClinica(derechohabiente, asegurado, asignacionNSS, cabeza, 
					false, padresConcubinasUnionCivil, usuario, OrigenSolicitudEnum.VENTANILLA);

			//throw new ImpactaAlmacenesWSException();
			
			//Se finaliza la solicitud de cambio de clinica
			solicitud = this.finalizaSolicitudCambioClinica(solicitud, asignacionNSS, cabeza, session, derechohabiente.getIdPersona());
			//se setean los datos necesario para la impresion de reporte
			ImpresionReporteDto reporte = this.obtenerDatosImpresion(solicitud, request);
			/*
			 * Redireccionadamos para evistar que en caso de que se presione f5 o se refresque la pantalla
			 * no se vuelvan a hacer todas las peticiones
			 */
			session.setAttribute("reporte", reporte);
			session.setAttribute("solicitud", solicitud);

			return new RedirectView(REDIRECT_CORRECTO, true);
			
		} catch(ImpactaAlmacenesWSException e){
			exception = "Ocurri&oacute; un error al calcular la vigencia. " + e.getMessage();
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
			log.error("error inesperado",e);
			exception = ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD;
			error = e.getCause().getMessage();
		}
		
		session.setAttribute("mostrarBoton", true);
		session.setAttribute("exception", exception);
		session.setAttribute("error", error);
		
		return new RedirectView(REDIRECT_ERROR, true);
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
	public String validarCambioUmf(
			@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session){
		session.setAttribute("requiereDocumentacion", requiereDocumentos);
		return this.iniciarValidacionCambioClinica(idSolicitud, model, request, session);
	}
	
	
	/**
	 * Metodo para guardar la validacion de la correccion de datos de derechohabiente
	 * @param correccion
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/validacion/guardar", method = RequestMethod.POST)
	public Object guardarValidacionCambioUmf(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		String exception = null;
		String error = null;
		
		session.removeAttribute("requiereDocumentacion");
		try {
			//obtenemos la solicitud de la session
			Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
			//Seteamos al usuario
			solicitud.setSolicitante(usuario);
			//finalizamos la solicitud
			solicitud = this.finalizaSolicitudCambioClinica(solicitud, asignacionNSS, cabeza, session, correccion.getIdPersona());
			//seteamos los datos necesarios para la pantalla de impresion dereporte
			ImpresionReporteDto reporte = this.obtenerDatosImpresion(solicitud, request);
			/*
			 * Redireccionadamos para evistar que en caso de que se presione f5 o se refresque la pantalla
			 * no se vuelvan a hacer todas las peticiones
			 */
			session.setAttribute("reporte", reporte);
			session.setAttribute("solicitud", solicitud);

			return new RedirectView(REDIRECT_CORRECTO, true);
		} catch(ImpactaAlmacenesWSException e){
			exception = "Ocurri&oacute; un error al calcular la vigencia. " + e.getMessage();
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
			log.error("error inesperado",e);
			exception = ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD;
			error = e.getCause().getMessage();
		}
		
		session.setAttribute("mostrarBoton", true);
		session.setAttribute("exception", exception);
		session.setAttribute("error", error);
		
		return new RedirectView(REDIRECT_ERROR, true);
	}
	
	private Solicitud finalizaSolicitudCambioClinica(Solicitud solicitud, AsignacionNSS asignacionNSS, CabezaGrupoFamiliar cabeza, HttpSession session, Long idPersonaDocs) throws DerechohabientesBusinessException, ImpactaAlmacenesWSException {
		Usuario usuario = solicitud.getSolicitante();
		TramiteCorreccionDerechohabiente correccion = TramiteUtil.getTramiteCorreccionPorTipo(solicitud, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		//verificamos si es en la umf destino donde se esta realizando el tramite
		if( usuario.getIdUmf().equals(correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF())) {
			//verificaremos si alguno de los integrantes es el asegurado
			Boolean isAsegurado = false;
			//seteamos la bandera en true
			for(Long idPersona: correccion.getCandidatosCambioClinica()) {
				if(asignacionNSS.getIdPersona().equals(idPersona)) {
					isAsegurado = true;
				}
			}
			//verificaremos si es necesarios realizar el cambio de medico en la clinica destino
			Map<String, Object> validacionesCambio = cambioClinicaServiceRemote.getFechaCambioYDatosCambioMedico(
					asignacionNSS, correccion.getMedicoEnTurno(), correccion.getCandidatosCambioClinica()
					, false, null, isAsegurado);
			//finalizamos la solicitud de cambio de clinica
			log.debug("Cambio de clinica finalización");
			solicitud = cambioClinicaServiceRemote.finalizaSolicitudCambioClinica(solicitud, asignacionNSS, cabeza, validacionesCambio);
			
			//Se guardan los documentos probatorios
			log.debug("Cambio de clinica se guardan documentos");
			guardaDocumentosAsincrono.salvaDocumentosProbatorios(session, correccion.getTramiteId(), idPersonaDocs);
			
			//seteamos la correccion original, ya que para este paso la clinica que trae la correccion es la anterior y no la nueva
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(correccion);
			//actualizamos el usuario de la solicitud, para que se quede con el que finalizo la solicitud
			solicitud.setSolicitante(usuario);
			try {
				log.debug("se comienza con la actualización del asugurado");
				solicitudBusinessRemote.actualizarUsuarioSolicitud(solicitud);
			} catch(Exception e) {
				log.error("No fue posible actualizar al usuario de la solicitud", e);
				e.printStackTrace();
			}
		}
		
		return solicitud;
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
	
	/**
	 * Metodo para iniciar la validacion del cambio de clinica
	 * @param idSolicitud
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	private String iniciarValidacionCambioClinica(Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session) {
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		TramiteCorreccionDerechohabiente tramiteCorreccion;
		Solicitud solicitudCorreccion;
		int validacion = 0;
		int documentacion = 0;
		List<GrupoFamiliar> integrantes = null;
		String vista = "";
		try {
			GrupoFamiliar integrante=null;
			TramiteCorreccionDerechohabiente datosActuales=null;
			
			solicitudCorreccion = solicitudBusinessRemote.consultar(new Solicitud(idSolicitud));
			tramiteCorreccion = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
			
			
			long idUmfUsuario = usuario.getIdUmf();
			long idUmfDestino = tramiteCorreccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
			long idUmfOrigen = this.setearDelegacionYUmfOrigen(session, model);
			
			if(idUmfUsuario == idUmfDestino) {
				validacion = 2;
			} else if(idUmfUsuario == idUmfOrigen) {
				validacion = 1;
			}
			
			
			List<DocumentoProbatorio> dPList=gestionDocumentalServiceRemote.listaDocumentosProbatoriosTramite(new Long(tramiteCorreccion.getTramiteId()));
			if(dPList != null && !dPList.isEmpty()){
					documentacion = 1;
			}
			
			integrantes = new ArrayList<GrupoFamiliar>();

			for(Long idIntegrante: tramiteCorreccion.getCandidatosCambioClinica()) {
				GrupoFamiliar derechohabiente = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), idIntegrante);
				//se agrega comparacion ya que puede venir un null
				if(derechohabiente != null) {
					integrantes.add(derechohabiente);
				}
			}

			integrante = TramiteUtil.getMayorCalidad(integrantes);
			model.addAttribute("integrantes", integrantes);
			vista = Constants.CAMBIO_UMF_FORWARD;
			
			session.setAttribute(SOLICITUD_KEY, solicitudCorreccion);
			datosActuales = TramiteUtil.convetirGrupoCorreccion(integrante);
			model.addAttribute("resultado", tramiteCorreccion);
			model.addAttribute("derechohabiente",tramiteCorreccion);
			model.addAttribute("validacion", validacion);
			model.addAttribute("datosActuales",datosActuales);
			model.addAttribute("miGrupoFamiliar",integrante);
			model.addAttribute("documentacion", documentacion);
			model.addAttribute("idSolicitud", solicitudCorreccion.getSolicitudId());
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vista;
	}	
	
	
}