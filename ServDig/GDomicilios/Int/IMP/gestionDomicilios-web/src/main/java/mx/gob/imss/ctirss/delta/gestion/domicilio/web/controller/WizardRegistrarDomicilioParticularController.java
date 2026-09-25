package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.web.validator.AsentamientoValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioConcluirValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Marco Sanchez
 * 
 */

@Controller
@RequestMapping(value = "/wizard/tramite/registrar/domicilio/particular")
public class WizardRegistrarDomicilioParticularController extends AbstractController {
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String MDM_SESSION_KEY = "MDM_SESSION";
	private static final String SOLICITUD_SESSION_KEY = "SOLICITUD_SESSION";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String IS_RETOMAR = "isRetomar";
	private static final String DESC_TIPO_SOLICITUD = "ACTUALIZACION DE DATOS GENERALES";
	private static final String KEY_TIPO_NUEVA_VIALIDAD = "tipoNuevaVialidad";
	private static final String KEY_TIPO_BUSQUEDA = "tipoBusquedaDomicilio";
	
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idTipoTramite
	 * @param idTipoPersona
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/{idTipoPersona}/{idPersona}/{idTipoTramite}", method = RequestMethod.GET)
	public String initModificarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Integer idTipoPersona, @PathVariable Long idPersona, 
			@PathVariable String idTipoTramite) {
		session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		
		String view = null;

		// Objeto para la forma auxiliar para invocar al servicio de modificacion manual
		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);
			
			mdmDatosEntrada.setPersonaFisica(fisica);
			
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE);
			
			
			view = "wizardRegistrarDomParticularFisicaInit";
			
		} else {
			Moral moral = new Moral();
			moral.setIdPersona(idPersona);
			mdmDatosEntrada.setPersonaMoral(moral);
			
			view = "wizardRegistrarDomParticularMoralInit";
		}
		
		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		Solicitud solicitudActiva = null;
		
		try {
			TipoTramiteEnum tipoTramiteEnum = null;
			
			//Dependiendo del valor de idTipoTramite se hara la busqueda de la solicitud
			if(idTipoTramite.equals( TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().toString() )){
				tipoTramiteEnum = TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH;
				mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.TRUE );
				mdmDatosEntrada.setIndActualizacionDomicilioDerechohabiente( Boolean.FALSE );
			}
			else if( idTipoTramite.equals( TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo().toString() ) ){
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR;
				mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.FALSE );
				mdmDatosEntrada.setIndActualizacionDomicilioDerechohabiente( Boolean.TRUE );
			}
			else{
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES;
				mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.FALSE );
				mdmDatosEntrada.setIndActualizacionDomicilioDerechohabiente( Boolean.FALSE );
			}
			
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(
					idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
					tipoTramiteEnum);
		
			if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(
						idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
						tipoTramiteEnum);
				
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {
			this.log.error(e);
		}
		
		//seteo de propiedades del usuario en sesion para recuperarlo posteriormente
		putUsuarioSesionbyUsuarioSSOonRequest(request);		
		model.addAttribute("solicitudForm", solicitudActiva);
		request.setAttribute("existeSolRegistrada", existeSolRegistrada);
		request.setAttribute("existeSolProceso", existeSolProceso);
		
		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		
		
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		
		if(idTipoTramite.equals( TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().toString() )){
			listTipoTramite.add(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo());
		}
		else{
			listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo());
		}
		
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		
		return view;
	}
	
	/**
	 * Compara los datos IMSS con las entidades externas (a traves del ICA),
	 * genera la solicitud y tramite de actualizacion de datos solo 
	 * en caso de existir diferencias, no afecta en la base de datos.
	 * 
	 * @param icaDatosConsulta
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudModificacionDatos(@ModelAttribute MDMDatosEntrada mdmDatosEntrada,
			final HttpSession session, HttpServletRequest request, final Model model) {
        
    	Solicitud solicitud = null;
    	
    	log.debug("ID PERSONA -> " + mdmDatosEntrada.getPersonaFisica().getIdPersona());
    	
    	// Se agregan las banderas que requiere el servicio de la modificacion manual
		mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaNombre(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaCURP(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaSexo(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.FALSE);
		
		mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaRFC(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.FALSE);
		
		mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);

		//Si el tipo de tramite es ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
		//setIndCapturaDomicilioParticular debe ser FALSE 
		//-Cambio- Lo que nos servira de bandera es mdmDatosEntrada.setIndAsignacionDomicilio
		if( mdmDatosEntrada.getIndAsignacionDomicilio() != null && mdmDatosEntrada.getIndAsignacionDomicilio() ){
			mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.FALSE);
			mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.TRUE );
		}
		else{
			mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE); 
			mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.FALSE );
		}
		
		mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.FALSE);
		
		mdmDatosEntrada.setIndAutorizacion(Boolean.FALSE);

		try {
						
			if (mdmDatosEntrada.getPersonaFisica().getDomicilios() == null
					|| !mdmDatosEntrada.getPersonaFisica().getDomicilios()
							.isEmpty()) {
				mdmDatosEntrada.getPersonaFisica().setDomicilios(new ArrayList<Domicilio>());
			}
			
			UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
			Usuario usuario = new Usuario();
			usuario.setUsuario(usuarioSSO.getNombre());
			//Se tiene que pasar el tipo de tramite seteado en initModificarDatosPersona
			solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada, usuario);
			//solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada);
			
			//Si getIndAsignacionDomicilio  == true, indica que se debe verificar que
			//el asegurado tenga un domicilio en DIT_PERSONAF_DOM, si cuenta con domicilio
			//en el wizard se le debe indicar si desea asignar ese domicilio a su grupo familiar.
			//Si no cuenta con el domicilio en DIT_PERSONAF_DOM el flujo del wizard sigue normal
			
			if( mdmDatosEntrada.getIndAsignacionDomicilio() || mdmDatosEntrada.getIndActualizacionDomicilioDerechohabiente() ){
				List<Domicilio>domicilios = domicilioServiceBusiness.consultarDomiciliosPersonaFisica( mdmDatosEntrada.getPersonaFisica() );
				Domicilio domicilio = domicilios.get(0);
				
				request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
				request.setAttribute("idSolicitud", solicitud.getSolicitudId());
				session.setAttribute(IS_RETOMAR, true);
				model.addAttribute("domicilio", domicilio);
				model.addAttribute("asentamiento", new Asentamiento());
				model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
				model.addAttribute("solicitud", solicitud);
				session.setAttribute(MDM_SESSION_KEY, mdmDatosEntrada);
				session.setAttribute(SOLICITUD_SESSION_KEY, solicitud);
				Fisica objfisicaRecuperado = serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(mdmDatosEntrada.getPersonaFisica().getIdPersona());
				obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
				generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
				request.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
				return "wizardRegistrarDomParticularFisicaExistente";
			}
			
			mdmDatosEntrada.getIndAsignacionDomicilio();
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			session.setAttribute(IS_RETOMAR, false);
			
			session.setAttribute(SOLICITUD_SESSION_KEY, solicitud);
			
			GrupoFamiliar asegurado = domicilioServiceBusiness.getAsegurado( mdmDatosEntrada.getPersonaFisica().getIdPersona() );
			model.addAttribute("idUmf", asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		session.setAttribute(MDM_SESSION_KEY, mdmDatosEntrada);
		
		model.addAttribute("domicilio", new Domicilio());
		model.addAttribute("asentamiento", new Asentamiento());
    	
    	return "wizardRegistrarDomParticularFisicaInitUbicar";
    }
	
	@RequestMapping(value = "/regresar", method = RequestMethod.POST)
	public String regresar(@ModelAttribute Domicilio domicilio,
			final HttpSession session, HttpServletRequest request, final Model model) {
            	
    	MDMDatosEntrada mdmDatosEntrada = (MDMDatosEntrada) session.getAttribute(MDM_SESSION_KEY);
    	model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		
		if (domicilio.getCodigoPostal() != null
				&& StringUtils.isNotBlank(domicilio.getCodigoPostal()
						.getCodigoPostal())) {
			model.addAttribute("domicilio", domicilio);
			model.addAttribute("asentamiento", new Asentamiento());
		} else if (domicilio.getAsentamiento() != null
				&& domicilio.getAsentamiento().getLocalidad() != null) {
			model.addAttribute("domicilio", new Domicilio());
			model.addAttribute("asentamiento", domicilio.getAsentamiento());
		} else {
			model.addAttribute("domicilio", new Domicilio());
			model.addAttribute("asentamiento", new Asentamiento());
		}
    	
    	return "wizardRegistrarDomParticularFisicaInitUbicar";
    }
	
	@RequestMapping(value = "/porCodigoPostal", method = RequestMethod.POST)
	public String ubicarPorCodigoPostal(@ModelAttribute Domicilio domicilio,
			BindingResult result, Model model, final HttpSession session,
			HttpServletRequest request) {

		this.log.debug(" datos complementarios por codigo [" + domicilio + "]");
		
		MDMDatosEntrada mdmDatos = (MDMDatosEntrada) session.getAttribute(MDM_SESSION_KEY);
		model.addAttribute("mdmDatosEntrada", mdmDatos);
		
		new DomicilioValidator().validate(domicilio, result);
		if (result.hasErrors()) {
			model.addAttribute("asentamiento", new Asentamiento());
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		}

		model.addAttribute("domicilio", domicilio);

		Asentamiento asentamiento = domicilio.getAsentamiento();

		// Obtenemos el detalle del asentamiento.
		try {
			
			asentamiento.setCodigoPostal(domicilio.getCodigoPostal());
			asentamiento = this.domicilioServiceBusiness
					.getAsentamiento(asentamiento);

			this.log.debug("Asentamiento localizado" + asentamiento);

			model.addAttribute("asentamiento", asentamiento);

			// Seteamos el asentamiento del domicilio.
			domicilio.setAsentamiento(asentamiento);
			
			Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_SESSION_KEY);
			
			// Datos para la firma digital
			Fisica objfisicaRecuperado = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(mdmDatos.getPersonaFisica().getIdPersona());
			obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			request.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
		} catch (DomicilioNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e
					.getMessage()));
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		} catch (AsentamientoNoLocalizadoException e) {
			model.addAttribute("asentamiento", new Asentamiento());
			result.addError(new ObjectError("asentamiento.clave", e
					.getMessage()));
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";

		} catch (PersonaFisicaNoEncontradaException e) {
			model.addAttribute("asentamiento", new Asentamiento());
			result.addError(new ObjectError("asentamiento.clave", e
					.getMessage()));
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		}
		
		request.setAttribute("FROM_CODIGO_POSTAL", true);

		return "wizardRegistrarDomParticularFisicaContenido";
	}
	
	
	@RequestMapping(value = "/porMunicipio", method = RequestMethod.POST)
	public String ubicarPorMunicipio(@ModelAttribute Asentamiento asentamiento,
			BindingResult result, Model model, final HttpSession session,
			HttpServletRequest request) {

		this.log.debug(" datos complementarios por municipio [" + asentamiento
				+ "]");
		
		MDMDatosEntrada mdmDatos = (MDMDatosEntrada) session.getAttribute(MDM_SESSION_KEY);
		model.addAttribute("mdmDatosEntrada", mdmDatos);
		
		new AsentamientoValidator().validate(asentamiento, result);
		if (result.hasErrors()) {
			model.addAttribute("domicilio", new Domicilio());
			model.addAttribute("asentamiento", asentamiento);
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		}

		Domicilio domicilio = new Domicilio();

		// Obtenemos el detalle del asentamiento.
		try {

			asentamiento = this.domicilioServiceBusiness
					.getAsentamiento(asentamiento);

			this.log.debug("Asentamiento localizado" + asentamiento);

			/**
			 * Validacion del codigo postal del asentamiento
			 */

			CodigoPostal cp = asentamiento.getCodigoPostal();
			if (cp == null) {
				throw new DomicilioNoLocalizadoException();
			} else {
				if (cp.getCodigoPostal() == null) {
					throw new DomicilioNoLocalizadoException();
				}
			}

			model.addAttribute("asentamiento", asentamiento);
			domicilio.setAsentamiento(asentamiento);
			model.addAttribute("domicilio", domicilio);
			
			Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_SESSION_KEY);
			
			// Datos para la firma digital
			Fisica objfisicaRecuperado = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(mdmDatos.getPersonaFisica().getIdPersona());
			obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			request.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
		} catch (DomicilioNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave", "", e.getMessage());
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		} catch (AsentamientoNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave", "", e.getMessage());
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";

		} catch (PersonaFisicaNoEncontradaException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave", "", e.getMessage());
			this.log.error(e);
			return "wizardRegistrarDomParticularFisicaInitUbicar";
		}

		request.setAttribute("FROM_MUNICIPIO", true);
		
		return "wizardRegistrarDomParticularFisicaContenido";
	}
	
	@RequestMapping(value = "/retomar/solicitud", method = RequestMethod.POST)
	public String retomarSolicitudModificacionDatos(@ModelAttribute Solicitud solicitud,
			HttpSession session, HttpServletRequest request, final Model model) { 
	
		MDMDatosEntrada datosModif = null;
		String control = null;
		
		//Bandera de control solo para cuando el flujo sea 
		//por la validacion del domicilio del asegurado
		//Si la bandera es igual a 'retomar' el flujo es normal
		if( solicitud.getObservacion() != null && solicitud.getObservacion().equals( "retomar" ) ){
			control = solicitud.getObservacion();
		}
		
		try {
			Map<String, Object> resultado = this.solicitudPersonaBusiness
					.retomarSolicitudModificacionDatosPersona(solicitud.getSolicitudId());
			
			solicitud = (Solicitud) resultado.get("solicitud");
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			session.setAttribute(IS_RETOMAR, true);
			
			datosModif = (MDMDatosEntrada) resultado.get("datosModif");
			
			//Si getIndAsignacionDomicilio  == true, indica que se debe verificar que
			//el asegurado tenga un domicilio en DIT_PERSONAF_DOM, si cuenta con domicilio
			//en el wizard se le debe indicar si desea asignar ese domicilio a su grupo familiar.
			//Si no cuenta con el domicilio en DIT_PERSONAF_DOM el flujo del wizard sigue normal
			if( control == null ){
				List<Domicilio>domicilios = domicilioServiceBusiness.consultarDomiciliosPersonaFisica( datosModif.getPersonaFisica() );
				Domicilio domicilio = domicilios.get(0);

				model.addAttribute("domicilio", domicilio);
				model.addAttribute("asentamiento", new Asentamiento());
				model.addAttribute("mdmDatosEntrada", datosModif);
				session.setAttribute(MDM_SESSION_KEY, datosModif);
				session.setAttribute(SOLICITUD_SESSION_KEY, solicitud);
				Fisica objfisicaRecuperado = serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(datosModif.getPersonaFisica().getIdPersona());
				obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
				generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
				request.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
				return "wizardRegistrarDomParticularFisicaExistente";
			}
			
			//Valores para poder habilitar el wizard de documentos probatorios
			session.setAttribute("tipoTramite", solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
			session.setAttribute("idTramite", solicitud.getTramites().get(0).getTramiteId());
			session.setAttribute("indActualizacionDomicilioDerechohabiente", datosModif.getIndActualizacionDomicilioDerechohabiente());
			
			/*
			 * Se checa si la solicitud a retomar ya contiene un domcilio, si NO
			 * tiene domicilio se redirecciona a la pantalla donde elige su
			 * codigo postal o datos del asentamiento
			 */
			if(datosModif.getPersonaFisica().getDomicilios() == null || 
					datosModif.getPersonaFisica().getDomicilios().isEmpty()) {
				GrupoFamiliar asegurado = domicilioServiceBusiness.getAsegurado( datosModif.getPersonaFisica().getIdPersona() );
				model.addAttribute("idUmf", asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
				model.addAttribute("domicilio", new Domicilio());
				model.addAttribute("asentamiento", new Asentamiento());
				
				model.addAttribute("mdmDatosEntrada", datosModif);
				session.setAttribute(MDM_SESSION_KEY, datosModif);
				session.setAttribute(SOLICITUD_SESSION_KEY, solicitud);
				return "wizardRegistrarDomParticularFisicaInitUbicar";
			}
			
			// Datos para la firma digital
			Fisica objfisicaRecuperado = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(datosModif.getPersonaFisica().getIdPersona());
			obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			request.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
			
			model.addAttribute("mdmDatosEntrada", datosModif);
			model.addAttribute("domicilio", datosModif.getPersonaFisica().getDomicilios().get(0));
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} 
		catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "wizardRegistrarDomParticularFisicaContenido";
	} 
	
	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDatos(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
		
		if (mdmDatosEntrada.getPersonaFisica().getDomicilios() != null &&
				!mdmDatosEntrada.getPersonaFisica().getDomicilios().isEmpty()){
			/*
			 * Se settea el estado de administracion para que el servicio de
			 * afectar lo pueda procesar
			 */
			Domicilio domicilio = mdmDatosEntrada.getPersonaFisica().getDomicilios().get(0);
			domicilio.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.NUEVO);
			
			// Se settea el tipo de domicilio geografico
			TipoDomicilio tipoDomicilio = new TipoDomicilio();
			//Tipo DOMICILIO URBANO 
			tipoDomicilio.setClave(1);
			domicilio.setTipoDomicilio(tipoDomicilio);
			
			// Se settea el tipo de domicilio
			tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
			domicilio.setDicTipoDomicilio(tipoDomicilio);
		}
		
		try {
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);

			mdmDatosEntrada = this.comprobarCambiosDomicilio(mdmDatosEntrada);
			solicitudPersonaBusiness.finalizarSolicitudModificacionDatosPersona(solicitud, mdmDatosEntrada, firmaElectronica);
			result.put("mensaje", "Su solicitud esta siendo procesada. Por favor espere");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (SolicitudException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Este metodo es el encargado de procesar las peticiones provenientes de la pantalla de domicilioExistente.jsp,
	 * su funcionalidad principal es la de asignar el id de domicilio de DIT_PERSONAF_DOM a DIT_GRUPO_FAMILIAR.
	 * Este metodo debe cerrar la solicitud y el tramite correspondiente. 
	 * @param idSolicitud
	 * @param mdmDatosEntrada
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/asignar/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> asignarMismoDomicilio(@PathVariable Long idSolicitud,
			HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//Consultar informacion de la solcitud para
			//posteriormente guardarla y aplicar los cambios correspondientes
			//a la asignacion de domicilio
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			solicitud.setFirmaElectronica(firmaElectronica);
			
			//Actualizar la solicitud y tramites
			solicitud = solicitudPersonaBusiness.actualizarTramites(solicitud);
			
			//Este metodo sera usado por el OSB para finalizar la solicitud
			//En este momento se esta llamando de forma directa
			//Se envia la solicitud con almenos el su ID, de tal forma que ya dentro del metodo 
			//se consulte la informacion y se pueda determinar que accion se debe realizar
			//En este caso agregar un domicilio al grupo familiar
			solicitudPersonaBusiness.finalizarSolicitudAsignacionDomicilio(solicitud);
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		return result;
	}
	
	/**
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
	@RequestMapping(value = "/actualizar/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarDomicilio(
			@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
		try {

			if (mdmDatosEntrada.getPersonaFisica().getDomicilios() != null
					&& !mdmDatosEntrada.getPersonaFisica().getDomicilios()
							.isEmpty()) {
				/*
				 * Se settea el estado de administracion para que el servicio de
				 * afectar lo pueda procesar
				 */
				Domicilio domicilio = mdmDatosEntrada.getPersonaFisica()
						.getDomicilios().get(0);
				domicilio
						.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.NUEVO);
				
				List<Domicilio>domicilios = domicilioServiceBusiness.consultarDomiciliosPersonaFisica( mdmDatosEntrada.getPersonaFisica() );
				Domicilio dg = domicilios.get(0);
				domicilio.setClave( dg.getClave() );

				// Se settea el tipo de domicilio geografico
				TipoDomicilio tipoDomicilio = new TipoDomicilio();
				// Tipo DOMICILIO URBANO
				tipoDomicilio.setClave(1);
				domicilio.setTipoDomicilio(tipoDomicilio);

				// Se settea el tipo de domicilio
				tipoDomicilio = new TipoDomicilio();
				tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo()
						.intValue());
				domicilio.setDicTipoDomicilio(tipoDomicilio);
			}
			FirmaElectronica firmaElectronica = (FirmaElectronica) session
					.getAttribute(KEY_FIRMA_ELECTRONICA);
			

			mdmDatosEntrada = this.comprobarCambiosDomicilio(mdmDatosEntrada);
			
			// Consultar informacion de la solcitud para
			// posteriormente guardarla y aplicar los cambios correspondientes
			// a la asignacion de domicilio
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			
			solicitud.setFirmaElectronica(firmaElectronica);
			solicitud = solicitudPersonaBusiness.actualizarTramites(solicitud);
			solicitud = solicitudPersonaBusiness.finalizarSolicitudActualizacionDomicilio(solicitud, mdmDatosEntrada);
			
			//Generacion de documentos resultantes
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitud.setFirmaElectronica(firmaElectronica);
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			
			result.put("mensaje",
					"Su solicitud esta siendo procesada. Por favor espere");
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
	
	private MDMDatosEntrada comprobarCambiosDomicilio(MDMDatosEntrada datos) {
		
		if(datos.getPersonaFisica() != null) {
			Domicilio dom = datos.getPersonaFisica().getDomicilios().get(0);
			if (dom.getTipoBusquedaVialidad() != null) {
				if (dom.getTipoBusquedaVialidad().equals(
								TipoBusquedaVialidadEnum.CAMINO.getCodigo())) {
					dom.setDomicilioCarretera(null);
					dom.setVialidadPrimaria(null);
				} else if(dom.getTipoBusquedaVialidad().equals(TipoBusquedaVialidadEnum.CARRETERA.getCodigo())) {
					dom.setDomicilioCamino(null);
					dom.setVialidadPrimaria(null);
				} else {
					dom.setDomicilioCarretera(null);
					dom.setDomicilioCamino(null);
				}
			} else {
				dom.setDomicilioCarretera(null);
				dom.setDomicilioCamino(null);
			}
			
			datos.getPersonaFisica().setDomicilios(new ArrayList<Domicilio>());
			datos.getPersonaFisica().getDomicilios().add(dom);
		} else {
			Domicilio dom = datos.getPersonaMoral().getDomicilios().get(0);
			
			if (dom.getTipoBusquedaVialidad() != null) {
				if (dom.getTipoBusquedaVialidad() != null
						&& dom.getTipoBusquedaVialidad().equals(
								TipoBusquedaVialidadEnum.CAMINO.getCodigo())) {
					dom.setDomicilioCarretera(null);
					dom.setVialidadPrimaria(null);
				} else if(dom.getTipoBusquedaVialidad().equals(TipoBusquedaVialidadEnum.CARRETERA.getCodigo())) {
					dom.setDomicilioCamino(null);
					dom.setVialidadPrimaria(null);
				} else {
					dom.setDomicilioCarretera(null);
					dom.setDomicilioCamino(null);
				}
			} else {
				dom.setDomicilioCarretera(null);
				dom.setDomicilioCamino(null);
			}
			
			datos.getPersonaMoral().setDomicilios(new ArrayList<Domicilio>());
			datos.getPersonaMoral().getDomicilios().add(dom);
		}
			
		return datos;
	}

	@RequestMapping(value = "/guardar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> guardarSolicitudModificacionnDatos(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada datosModif, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) { 
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);
			this.solicitudPersonaBusiness.guardarSolicitudModificacionDatosPersona(solicitud, datosModif);
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
	
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudModificacionDatos(@PathVariable Long idSolicitud,
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
	
	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> validarDomicilio(
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Domicilio domicilio = mdmDatosEntrada.getPersonaFisica().getDomicilios().get(0);
		
		final Errors errors = new BindException(domicilio, "model");
		new DomicilioConcluirValidator().validate(domicilio,errors);
		
		if(errors.hasErrors()){
			procesaErroresDeCaptura(errors, result, response);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Domicilio limpiarSessionWizard(
			final HttpSession session) {
	
		session.removeAttribute(MDM_SESSION_KEY);
		session.removeAttribute(SOLICITUD_SESSION_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(IS_RETOMAR);
		session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		session.removeAttribute("tipoTramite");
		session.removeAttribute("idTramite");
		session.removeAttribute("indActualizacionDomicilioDerechohabiente");
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
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fechaSistema);
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fechaSistema);

		// RFC
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
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		// CURP
		if (persona instanceof Fisica) {
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
	
	public Usuario putUsuarioSesionbyUsuarioSSOonRequest(HttpServletRequest request) {
		
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = null;
		
		if (usuariosso.getNombre()!=null){
			usuario = new Usuario();
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
			if(usuariosso.getIdPersona()!= null)
				usuario.setCveIdUsuario(usuariosso.getIdPersona().toString());
			
			
			request.getSession().setAttribute(KEY_USUARIO, usuario);
		}
		return usuario;
	}
}