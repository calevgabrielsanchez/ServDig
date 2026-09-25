/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.utils.WizardActualizarDatosInput;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.DatosConstitucionValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
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

/**
 * @author Marco S�nchez
 * 
 */

@Controller
@RequestMapping(value = "/wizard/tramite/actualizar/datos")
public class WizardActualizarDatosController extends AbstractController {
	
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String KEY_RFC_PERSONA_SESION = "rfcPersonaSesion";
	private static final String KEY_PERSONA_INTERESADA_SESSION = "personaInteresadaSolSession";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String ICA_SESSION_KEY = "icaDatosAux";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String KEY_ATRIBUTE_ERROR = "error";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	private static final String DESC_TIPO_SOLICITUD = "ACTUALIZACION DE DATOS GENERALES";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionAct";
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";

	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	

	@RequestMapping(method = RequestMethod.POST)
	public String initActualizarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute WizardActualizarDatosInput wizardInput) {

		this.log.debug("Datos de entrada para wizard de actualizaci�n de datos -> " + wizardInput);
				
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		String view = null;
		
		Long idPersonaSesion = wizardInput.getIdPersonaSesion();
		Integer idTipoPersona = wizardInput.getIdTipoPersona();
		Long idPersona = wizardInput.getIdPersona();
		String curp = wizardInput.getCurp();
		String rfc = wizardInput.getRfc();
		Long idPersonaInteresadaSol = wizardInput.getIdPersonaInteresadaSol();
		Boolean consultaRenapo = wizardInput.getConsultaRenapo();
		Boolean consultaSat = wizardInput.getConsultaSat();
		
		boolean tramiteIniciadoPorOtraPersona = false;
		boolean sinCURP = false;
		boolean sinRFC = false;
		boolean pedirCURP = false;
		boolean pedirRFC = false;
				
		if (curp.equals("SIN_CURP")) {
			sinCURP = true;
			if (consultaRenapo) {
				pedirCURP = true;
			}
		}
		
		if (rfc.equals("SIN_RFC")) {
			sinRFC = true;
			if (consultaSat) {
				pedirRFC = true;
			}
		}		
		
		// Objeto para la forma auxiliar para invocar al servicio del ICA
		ICADatosConsulta datosEntrada = new ICADatosConsulta();
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);			
			if (!sinCURP) {
				fisica.setCurp(curp);
			}			
			if (!sinRFC) {
				fisica.setRfc(rfc);
			}			
			datosEntrada.setPersonaFisica(fisica);
			
			if (consultaRenapo) {
				datosEntrada.setIndicadorConsultaRENAPO(Boolean.TRUE);
			} else {
				datosEntrada.setIndicadorConsultaRENAPO(Boolean.FALSE);
			}

			if (consultaSat) {
				datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
			} else {
				datosEntrada.setIndicadorConsultaSAT(Boolean.FALSE);
			}
			
			datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);
			
			view = "wizardActualizarDatosFisicaInit";			
		} else {
			Moral moral = new Moral();
			moral.setCveMoral(idPersona);			
			
			if (!sinRFC) {
				moral.setRfc(rfc);
			}			
			
			datosEntrada.setPersonaMoral(moral);
			/*
			 * Siempre se manda TRUE a la consulta del SAT sin importar el
			 * indicador recibido, ya que para persona moral es la �nica entidad
			 * externa que se consulta
			 */
			datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
			datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);			
			view = "wizardActualizarDatosMoralInit";
		}

		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		Solicitud solicitudActiva = null;
		
		try {
			Fisica personaSesion = null;
			if (idPersonaSesion != null && idPersonaSesion > 0) {
				personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersonaSesion);
				session.setAttribute(KEY_RFC_PERSONA_SESION, personaSesion.getRfc());
			}
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(
					idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
					TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES);
		
			if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(
						idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
						TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES);
				
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {
			this.log.error(e);
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
		}
        
        /*
		 * Validaciones para saber si el tramite de actualizacion fue iniciado
		 * por la misma persona
		 */
        if(existeSolRegistrada || existeSolProceso) {
        	log.debug("Existe una solicitud en proceso o registradas");
        	PersonaInteresadaSolicitud perInt = solicitudActiva.getPersonaInteresadaSolicitud();
        	if(perInt != null) {
        		log.debug("la persona interesada no es nula");
				Long idPersonaInteresada = perInt.getPersona().getIdPersona();
				log.debug("el id de la persona interesada pasada como parametro es: "
						+ idPersonaInteresadaSol
						+ "el id de la persona interesada sol es: "
						+ idPersonaInteresadaSol);
				if(!idPersonaInteresada.equals(idPersonaInteresadaSol)) {
					tramiteIniciadoPorOtraPersona = true;
				}
			} else {
				log.debug("la solicitud no cuenta con persona interesada");
				if(!idPersona.equals(idPersonaInteresadaSol)){
					tramiteIniciadoPorOtraPersona = true;
				}
			}
        }

		//SETEO DE PROPIEDADES DEL USUARIO QUE REALIZA EL TRAMITE
		WizardMediosParticularesController mediosController = new WizardMediosParticularesController();
		mediosController.putUsuarioSesionbyUsuarioSSOonRequest(request);		
		model.addAttribute("solicitudForm", solicitudActiva);
		request.setAttribute("existeSolRegistrada", existeSolRegistrada);
		request.setAttribute("existeSolProceso", existeSolProceso);
		request.setAttribute("tramiteIniciadoPorOtraPersona", tramiteIniciadoPorOtraPersona);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		model.addAttribute("icaDatosEntrada", datosEntrada);
		request.setAttribute("PEDIR_CURP", pedirCURP);
		request.setAttribute("PEDIR_RFC", pedirRFC);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		session.setAttribute(KEY_PERSONA_INTERESADA_SESSION, idPersonaInteresadaSol);
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		return view;
	}
	
	private void verificarRoles(Long idPersona) throws Exception{
		Map<String, Boolean> roles = null;
		roles = personaFisicaServiceBusiness.getRolesPorPersona(idPersona);
		Boolean isAsegurado = false;
		Boolean isPatron = false;
		Boolean isRl = false;
		String mensaje = null;
		
		if(roles != null) {
			isAsegurado = (Boolean) roles.get("isAsegurado");
			isPatron = (Boolean) roles.get("isPatron");
			isRl = (Boolean) roles.get("isRL");			
			if(!isAsegurado && !isPatron && !isRl) {
				return;
			}			
			mensaje = "No es posible realizar el tr&aacute;mite ya que la persona cuenta con el rol de <strong>";
			mensaje += isAsegurado ? "asegurado" : "";
			mensaje += isPatron ? ((isAsegurado ? ", ": "") + "patron") :  "";
			mensaje += isRl ? ((isAsegurado || isPatron ? ", ":"") + "representante legal") : "";
			mensaje += "</strong>.";
		} 
		if(mensaje !=  null) {
			throw new Exception(mensaje);
		}
	}
	
	/**
	 * Compara los datos IMSS con las entidades externas (a trav�s del ICA),
	 * genera la solicitud y tramite de actualizaci�n de datos s�lo 
	 * en caso de existir diferencias, no afecta en la base de datos.
	 * 
	 * @param icaDatosConsulta
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fisica/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudActualizacionDatos(@ModelAttribute ICADatosConsulta icaDatosConsulta,
			final HttpSession session, HttpServletRequest request) {
        
    	ICADatosRespuesta icaDatosRespuesta = null;
    	Boolean isDerechohabiente = false;
    	Solicitud solicitud = null;    	
    	Long idPersonaInteresadaSolicitud = (Long) session.getAttribute(KEY_PERSONA_INTERESADA_SESSION);
    	
    	try {    		
    		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(new CommonValidator().getOrigenContext(request));
    		
    		//TODO
    		//Se inhibe regla a solicitud de requerimiento por el Lic. Arturo Ramos por tema de OUTSOURCING
//    		if(!origen.equals(OrigenSolicitudEnum.VENTANILLA))
//    			this.verificarRoles(icaDatosConsulta.getPersonaFisica().getIdPersona());
    		
    		//isDerechohabiente = personaFisicaServiceBusiness.personaRegistradaComoDerechohabiente(icaDatosConsulta.getPersonaFisica().getIdPersona(),null);
			icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
			this.log.debug("los datos del usuario son entre al metodo");			
			
			Usuario usuario = getUsuarioSession(session);
			solicitud = this.solicitudPersonaBusiness.crearTramiteActualizacionDatosPersona(icaDatosRespuesta, usuario, 
				idPersonaInteresadaSolicitud, origen);
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", false);			
			
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);				
				generarCadenaOriginal(solicitud, icaDatosRespuesta.getPersonaFisicaIMSS(), session);
				request.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			}			
			
			request.setAttribute("isDerechohabiente", isDerechohabiente);
			request.setAttribute("tramiteId", solicitud.getTramites().get(0).getTramiteId());
			log.debug("la persona es derechohabiente? :" + isDerechohabiente);
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_COMPARACION_DATOS_RENAPO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ComparacionSinDiferenciasException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch(DatosInsuficientesICAException e){
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DIFERENCIAS_RENAPO_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		}catch(ClienteWebserviceRenapoCurpException e){
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (AfectacionDatosPersonaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (RegistroPersonaFisicaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (Exception e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		}
    	
    	session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
    	
    	return "wizardActualizarDatosFisicaContenido";
    }
	
	/**
	 * Compara los datos IMSS con las entidades externas (a trav�s del ICA),
	 * genera la solicitud y tramite de actualizaci�n de datos s�lo 
	 * en caso de existir diferencias, no afecta en la base de datos.
	 * 
	 * @param icaDatosConsulta
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/moral/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudActualizacionDatosMoral(@ModelAttribute ICADatosConsulta icaDatosConsulta,
			final HttpSession session, HttpServletRequest request) {
        
		this.log.debug("::: En WizardActualizarDatosController.crearSolicitudActualizacionDatosMoral ");
    	ICADatosRespuesta icaDatosRespuesta = null;
    	Solicitud solicitud = null;
    	Long idOrigen =  new CommonValidator().getOrigenContext(request);
    	try {    		
			/*
			 * Se pasa el atributo cveMoral al idPersona, ya que el servicio
			 * para identificar cambios espera el idPersona y no cveMoral
			 */
    		icaDatosConsulta.getPersonaMoral().setIdPersona(icaDatosConsulta.getPersonaMoral().getCveMoral());    		
			
    		//Cambio para obtener los datos de la PM - INC110489
    		//icaDatosRespuesta = personaMoralBusiness.identificarSoloCambios(icaDatosConsulta);	
			icaDatosRespuesta = personaMoralBusiness.identificarSoloCambios_AP(icaDatosConsulta);	
			
			this.log.debug("Para ver que en el flujo va los cambios" +  icaDatosRespuesta.getCambios().get("tipoSociedad") +  icaDatosRespuesta.getCambios().get("nombreRazonSocial"));	

			boolean existenDiferencias = existenDiferencias(icaDatosRespuesta.getCambios());			
			
			if(icaDatosRespuesta.getPersonaMoralEE() != null && StringUtils.isNotBlank(icaDatosRespuesta.getPersonaMoralEE().getRazonSocial())) {
				//se escapan las comillas dobles
				String nombreConComillasEsc = icaDatosRespuesta.getPersonaMoralEE().getRazonSocial().replace("\"", "\\\"");
				icaDatosRespuesta.getPersonaMoralEE().setRazonSocial(nombreConComillasEsc);
			} 
			
			if(!existenDiferencias){
				icaDatosRespuesta.setErrorFormGeneral("No existen diferencias entre la informaci�n registrada en IMSS, SAT y RENAPO.");
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
						.getCodigo(), "No existen diferencias entre la informaci�n registrada en IMSS, SAT y RENAPO.");
				icaDatosRespuesta.setTraza(mensajes);
				
				Moral persona=icaDatosRespuesta.getPersonaMoralIMSS();
				
				
				/*TODO se comenta la validacion de acreditado ya que se pidio que no se valide por JMLL 02/01/2018
				if (persona.getIdPersona() != null) {
					Integer acreditado = personaMoralBusiness.validaAcreditadoPersonaMoral(persona.getIdPersona());
					icaDatosRespuesta.getPersonaMoralIMSS().setIndAcreditado(BigDecimal.valueOf(acreditado.longValue()));
					
					if(acreditado==1){
						icaDatosRespuesta.setErrorFormGeneral("La informaci�n ha sido registrada previamente y no es necesario actualizarla.");
						session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
						return "wizardActualizarDatosMoralContenido";
					}
				}*/
				
				agregarCambioDeDatosComplementarios(icaDatosRespuesta);
			}
			/* se cambia la validacon para que se aplique el ICA solo si los datos del nombre y razon social son correctos **/
			else{
				if(icaDatosRespuesta.getTraza().get("MSG_DIF_RFC") != null){
					icaDatosRespuesta.setErrorFormGeneral("La informaci�n de la Persona Moral presenta diferencias entre el IMSS y la entidades externa SAT en el RFC, acuda a la subdelegaci�n correspondiente para regularizar esta informaci�n");
					session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
					return "wizardActualizarDatosMoralContenido";
				}
			}
			
			this.log.debug("Para saber que datos trae cambios" +  icaDatosRespuesta.getCambios());	

			
			Usuario usuario = getUsuarioSession(session);			
			solicitud = this.solicitudPersonaBusiness.crearTramiteActualizacionDatosPersona(icaDatosRespuesta, usuario, 
				null, OrigenSolicitudEnum.getById(new CommonValidator().getOrigenContext(request)));
			
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", false);			
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);
				generarCadenaOriginal(solicitud, icaDatosRespuesta.getPersonaMoralIMSS(), session);
				request.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			}						
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ComparacionSinDiferenciasException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch(DatosInsuficientesICAException e){
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (AfectacionDatosPersonaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (RegistroPersonaFisicaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		}
    	
    	session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
    	
    	return "wizardActualizarDatosMoralContenido";
    }
	
	@RequestMapping(value = "/fisica/retomar/solicitud", method = RequestMethod.POST)
	public String retomarSolicitudActualizacionDatos(@ModelAttribute Solicitud solicitud,
			HttpSession session, HttpServletRequest request) { 
	
		ICADatosRespuesta icaDatosRespuesta = null;
		
		try {
			Map<String, Object> resultado = this.solicitudPersonaBusiness
					.retomarSolicitudActualizacionDatos(solicitud.getSolicitudId());			
			solicitud = (Solicitud) resultado.get("solicitud");			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", true);
			icaDatosRespuesta = (ICADatosRespuesta) resultado.get("datosICA");
			Boolean isDerechohabiente = false;//personaFisicaServiceBusiness.personaRegistradaComoDerechohabiente(icaDatosRespuesta.getPersonaFisicaIMSS().getIdPersona(), null);
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);
				generarCadenaOriginal(solicitud, icaDatosRespuesta.getPersonaFisicaIMSS(), session);
				request.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			}			
			request.setAttribute("isDerechohabiente", isDerechohabiente);
			request.setAttribute("tramiteId", solicitud.getTramites().get(0).getTramiteId());
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		}		
		session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
    	
    	return "wizardActualizarDatosFisicaContenido";
	} 
	
	@RequestMapping(value = "/moral/retomar/solicitud", method = RequestMethod.POST)
	public String retomarSolicitudActualizacionDatosMoral(@ModelAttribute Solicitud solicitud,
			HttpSession session, HttpServletRequest request) { 
	
		ICADatosRespuesta icaDatosRespuesta = null;
		
		try {
			Map<String, Object> resultado = this.solicitudPersonaBusiness
					.retomarSolicitudActualizacionDatos(solicitud.getSolicitudId());			
			solicitud = (Solicitud) resultado.get("solicitud");			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", true);
			icaDatosRespuesta = (ICADatosRespuesta) resultado.get("datosICA");		
			if(icaDatosRespuesta.getPersonaMoralEE() != null && StringUtils.isNotBlank(icaDatosRespuesta.getPersonaMoralEE().getRazonSocial())) {
				//se escapan las comillas dobles
				String nombreConComillasEsc = icaDatosRespuesta.getPersonaMoralEE().getRazonSocial().replace("\"", "\\\"");
				icaDatosRespuesta.getPersonaMoralEE().setRazonSocial(nombreConComillasEsc);
			}
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);			
				generarCadenaOriginal(solicitud, icaDatosRespuesta.getPersonaMoralIMSS(), session);
				request.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());
			}		
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
		}		
		session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
    	
    	return "wizardActualizarDatosMoralContenido";
	}
	
	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}/{idPersonaFirmada}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> finalizarSolicitudActualizacionDatos(
			@PathVariable Long idSolicitud, @PathVariable Long idPersonaFirmada,
			@RequestBody Moral persona, HttpSession session, HttpServletRequest request) {		
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitudReq = new Solicitud();
			solicitudReq.setSolicitudId(idSolicitud);
			if(persona!=null &&
				(persona.getEscrituraConstitutiva()!=null || persona.getRegistroSindicato()!=null)){
					guardarDatosSolicitudActualizacion(idSolicitud, persona, session);
			}
			
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);			
			FirmaElectronica firmaElectronica = null;
			firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			solicitudBusinessRemote.enviarSolicitudAProceso(solicitud, firmaElectronica);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		} catch (Exception e ){
			e.printStackTrace();
			this.log.error(e);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		}		
		return result;
	}
	
	@RequestMapping(value = "/guardar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> guardarSolicitudActualizacionDatos(@PathVariable Long idSolicitud,
			@RequestBody Moral persona, HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		Map<String, Object> result = guardarDatosSolicitudActualizacion(idSolicitud, persona, session);	
		return result;
	}
	
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudActualizacionDatos(@PathVariable Long idSolicitud,
			HttpServletResponse response, HttpServletRequest request) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Solicitud solicitud = this.solicitudPersonaBusiness.cancelarSolicitud(idSolicitud);
			
			result.put("mensaje", "La solicitud fue cancelada correctamente");
			result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.GET)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {
	
		session.removeAttribute(ICA_SESSION_KEY);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(KEY_PERSONA_INTERESADA_SESSION);
		session.removeAttribute(KEY_RFC_PERSONA_SESION);
		
		return null;
	}
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
		Date fechaSistema = Calendar.getInstance().getTime();
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
		String strFechaElectronica = dateFormat.format(fechaSistema);
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fechaSistema);

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
		Date fechaSistema = Calendar.getInstance().getTime();
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fechaSistema);
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(fechaSistema);

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
	
	
	@RequestMapping(value = "/validar/datos/constitucion", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> validaDatosConstitucion(@RequestBody Moral persona,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) { 
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(persona, "model");
		System.out.println("Se esta validando los datos de constitucion");
		result=new DatosConstitucionValidator().validate(persona, errors,response,this.messageSource);
			
		return result;
	}
	
	private Map<String, Object> guardarDatosSolicitudActualizacion(Long idSolicitud, Moral persona, HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>();
		ICADatosRespuesta datosRespuesta = (ICADatosRespuesta) session.getAttribute(ICA_SESSION_KEY);
		
		if(persona!=null){
			this.log.error("datos persona"+persona);
			datosRespuesta.getCambios().put("datosComplementarios", CambioComparacionEnum.CAMBIO);
			if(persona.getEscrituraConstitutiva()!=null){
				datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
				datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
				datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(null);
				datosRespuesta.getPersonaMoralEE().setRegistroSindicato(null);
				datosRespuesta.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
				datosRespuesta.getCambios().remove("registroSindicato");
			}else if(persona.getRegistroSindicato()!=null){
				datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(persona.getRegistroSindicato());
				datosRespuesta.getPersonaMoralEE().setRegistroSindicato(persona.getRegistroSindicato());
				datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(null);
				datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(null);
				datosRespuesta.getCambios().put("registroSindicato", CambioComparacionEnum.CAMBIO);
				datosRespuesta.getCambios().remove("actaConstitutiva");
			}
		}
		
		try {
			this.solicitudPersonaBusiness.guardarSolicitudActualizacionDatos(idSolicitud, datosRespuesta);
			result.put("mensaje", "La solicitud se ha guardado exitosamente.");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		
		return result;
	}
	
	
	public boolean existenDiferencias(
			Map<String, CambioComparacionEnum> diferencias) {
		
		boolean existenDiferencias = false;
		
		if(diferencias != null && !diferencias.isEmpty()){
			for (Entry<String, CambioComparacionEnum> entry : diferencias.entrySet()) {
			    if(fueCambio(entry.getValue())) {
			    	existenDiferencias = true;
			    	break;
			    }
			}
		}
		
		return existenDiferencias;
		
	}

	private boolean fueCambio(CambioComparacionEnum cambio) {

		boolean fueCambio = false;

		if (cambio.getId().longValue() == CambioComparacionEnum.CAMBIO.getId()
				.longValue()
				|| cambio.getId().longValue() == CambioComparacionEnum.NUEVO
						.getId().longValue()) {
			fueCambio = true;
		}

		return fueCambio;
	}

	public void agregarCambioDeDatosComplementarios(ICADatosRespuesta datosRespuesta){
		datosRespuesta.getCambios().put("datosComplementarios", CambioComparacionEnum.CAMBIO);
		Moral persona=datosRespuesta.getPersonaMoralIMSS();
		
		if(datosRespuesta.getCambios()==null)
			datosRespuesta.setCambios(new HashMap<String, CambioComparacionEnum>());
						
		if(persona.getEscrituraConstitutiva()!=null){
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(null);
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(null);
			datosRespuesta.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("registroSindicato");
			
		}else if(persona.getRegistroSindicato()!=null){
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(persona.getRegistroSindicato());
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(persona.getRegistroSindicato());
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(null);
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(null);
			datosRespuesta.getCambios().put("registroSindicato", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("actaConstitutiva");
			
		}else if(persona.getRegistroSindicato()==null && persona.getEscrituraConstitutiva()==null){
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(new EscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(new EscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(null);
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(null);
			datosRespuesta.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("registroSindicato");
			
		}
	}
	
	private Usuario getUsuarioSession(HttpSession session){
		Fisica personaSesion = (Fisica)session.getAttribute(DATOS_PERSONA_SESION_KEY);
		//UsuarioSSO sso = (UsuarioSSO)session.getAttribute(KEY_USUARIO_SSO);
		if(personaSesion!=null){
			Usuario usuario = new Usuario();
			usuario.setUsuario(personaSesion.getCurp());
			usuario.setFisica(personaSesion);
			/*if(sso!=null && sso.getSubdelegacion()!=null){
				usuario.setUsuarioFuncionario(new UsuarioFuncionario());
				usuario.getUsuarioFuncionario().setSubdelegacion(new Subdelegacion());				
				usuario.getUsuarioFuncionario()
					.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}*/
			return usuario;
		}
		return null;
	}
	
	
}
