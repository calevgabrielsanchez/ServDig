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
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.InstanceofPredicate;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.SocioAltaValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping(value = "/wizard/tramite/socios/")
public class WizardAltaSociosController extends AbstractController {

	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired 
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	private SolicitudServiceBusinessRemote gpSolicitudService;
	@Autowired
	private SocioServiceBusinessRemote socioServiceBusinessRemote;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralServiceBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	
	
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_ATRIBUTE_SOLICITUD = "solicitud";
	private static final String KEY_ATRIBUTE_SOCIO = "socio";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesion";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String KEY_ATRIBUTE_ERROR = "error";
	private static final String KEY_ATRIBUTE_RESPUESTA = "respuesta";
	private static final String KEY_ATRIBUTE_MSGERROR = "msgError";
	private static final String KEY_ATRIBUTE_SOLICITUDFORM = "solicitudForm";
	private static final String KEY_ATRIBUTE_FOLIOSOLICITUD = "folioSolicitud";
	private static final String KEY_ID_SOLICITUD = "idSolicitud";
	private static final String KEY_ATRIBUTE_RETOMAR = "isRetomar";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final int iEXITO = 0;
	private static final int iERROR = 1;
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	
	//Pantallas
	private static final String INICIO_WIZARD_SOCIOS = "wizardInicioRegistroSocio";
	private static final String FILTROS_WIZARD_SOCIOS = "wizardFiltrosRegistroSocio";
	private static final String CONTENIDO_WIZARD_SOCIOS = "wizardContenidoRegistroSocio";
	//Mensajes
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurrió un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurrió un error al localizar información de la persona.";
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";	
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_CANCELAR_SOLICITUD_ERROR = "Error al cancelar la solicitud: ";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	private static final String DESC_TIPO_TRAMITE_SOLICITUD = "ALTA DE SOCIO";
	
	@RequestMapping(value="iniciarRegistro/{rfc}/{idPersona}")
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
			//Recuperar persona autenticada OpenAM.
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Fisica personaSesion = serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, TipoPersonaEnum.MORAL.getId(), 
				TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, TipoTramiteEnum.ACTUALIZACION_SOCIO);
			
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona, TipoPersonaEnum.MORAL.getId(), 
						TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, TipoTramiteEnum.ACTUALIZACION_SOCIO);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {			
			model.addAttribute(KEY_ATRIBUTE_ERROR, MSG_ERROR_SOL_PENDIENTES);
			log.error(e);
		} catch (PersonaFisicaNoEncontradaException ee) {
			model.addAttribute(KEY_ATRIBUTE_ERROR, MSG_ERROR_LOCALIZAR_PERSONA);
			log.error(ee);
		}
		
		model.addAttribute(KEY_ATRIBUTE_SOLICITUDFORM, solicitudActiva);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);		
		request.setAttribute(KEY_ATRIBUTE_FOLIOSOLICITUD, solicitudActiva.getNoFolioSolicitud());
		
		return INICIO_WIZARD_SOCIOS;
	}
	
	@RequestMapping(value = "filtrosRegistro", method = RequestMethod.POST)
	public String filtrosRegistro(@ModelAttribute Socio socio,
			final HttpSession session, HttpServletRequest request,
			final Model model) {
		socio.setRfc(null);
		socio.setCurp(null);
		socio.setTipoSocio(new TipoPersona());
		model.addAttribute(KEY_ATRIBUTE_SOCIO,socio);
		return FILTROS_WIZARD_SOCIOS;
	}

	@RequestMapping(value = "contenidoRegistro", method = RequestMethod.POST)
	public String detalleRegistro(@ModelAttribute Socio socio, final HttpSession session, 
			HttpServletRequest request, final Model model) {	
		String vista = CONTENIDO_WIZARD_SOCIOS;
		String error = null;
		
        if(new CommonValidator().valorNoVacio(socio.getRfc()))
        	socio.setRfc(socio.getRfc().trim().toUpperCase());
        if(new CommonValidator().valorNoVacio(socio.getCurp()))
        	socio.setCurp(socio.getCurp().trim().toUpperCase());
        
		log.debug("ID_PM PATRON: " + socio.getIdPersonaMoralPatron());
		log.debug("RFC_PM PATRON: " + socio.getRfcPersonaMoralPatron());
		log.debug("Tipo Socio: " + socio.getTipoSocio().getIdTipoPersona());
		log.debug("RFC filtro: " + socio.getRfc());
		log.debug("CURP filtro: " + socio.getCurp());
			
		try {			
			//Validar 1) La empresa no debe ser socia de si misma. 2) Socio ya registrado.
			socio = socioServiceBusinessRemote.localizarSocioAlta(socio);
			model.addAttribute(KEY_ATRIBUTE_SOCIO, socio);
			//Generar Solicitud Alta Socio
			Usuario usuario = new CommonValidator().getUsuarioSession(session);
			Solicitud solicitud = socioServiceBusinessRemote.generarSolicitudAltaSocio(socio, 
				OrigenSolicitudEnum.getById(new CommonValidator().getOrigenContext(request)), usuario);
			model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);			
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				generarCadenaOriginalyFirma(solicitud, personaSesion, session);
			}			
			datosFijosSession(session);
		} catch (GestionPatronalBusinessException be) {
			log.error(be);
			model.addAttribute(KEY_ATRIBUTE_SOCIO, socio);
			error = be.getMessage();
			request.setAttribute(KEY_ATRIBUTE_ERROR, error);
			vista = FILTROS_WIZARD_SOCIOS;
		}
		request.setAttribute(KEY_ATRIBUTE_RETOMAR, false);	
		return vista;
	}
	
	@RequestMapping(value = "getSocio", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSocio(@RequestBody Socio socio, final HttpSession session, 
			HttpServletRequest request, final Model model) {	
		Map<String, Object> result = new HashMap<String, Object>();
		
        if(new CommonValidator().valorNoVacio(socio.getRfc()))
        	socio.setRfc(socio.getRfc().trim().toUpperCase());
        if(new CommonValidator().valorNoVacio(socio.getCurp()))
        	socio.setCurp(socio.getCurp().trim().toUpperCase());
        
		log.debug("ID_PM PATRON: " + socio.getIdPersonaMoralPatron());
		log.debug("RFC_PM PATRON: " + socio.getRfcPersonaMoralPatron());
		log.debug("Tipo Socio: " + socio.getTipoSocio().getIdTipoPersona());
		log.debug("RFC filtro: " + socio.getRfc());
		log.debug("CURP filtro: " + socio.getCurp());
			
		try {			
			//Validar 1) La empresa no debe ser socia de si misma. 2) Socio ya registrado.
			socio = socioServiceBusinessRemote.localizarSocioAlta(socio);
			
			try {
				if(socio.getPersonaFisica() != null && (socio.getPersonaFisica().getIdPersona() != null || socio.getPersonaFisica().getCveFisica() != null)) {
					DomicilioFiscal domFiscal = sujetoObligadoServiceBusinessRemote.obtenerDomFiscal(socio.getPersonaFisica());
					List<MedioContacto> medios = socioServiceBusinessRemote.getMediosPersona(socio.getPersonaFisica());
					socio.getPersonaFisica().setDomicilioFiscal(domFiscal);
					socio.getPersonaFisica().setMediosContactoFiscales(medios);
				} else if(socio.getPersonaMoral() != null && (socio.getPersonaMoral().getIdPersona() != null || socio.getPersonaMoral().getCveMoral() != null)){
					DomicilioFiscal domFiscal = sujetoObligadoServiceBusinessRemote.obtenerDomFiscal(socio.getPersonaMoral());
					List<MedioContacto> medios = socioServiceBusinessRemote.getMediosPersona(socio.getPersonaMoral());
					socio.getPersonaMoral().setDomicilioFiscal(domFiscal);
					socio.getPersonaMoral().setMediosContactoFiscales(medios);
				}
			} catch(Exception e) {
				log.error("Ocurrio un error al consultar el domicilio fiscal y los medios de contacto fiscales para el socio con rfc: " + socio.getRfc(), e);
			}
			result.put("socio", socio);
		} catch (GestionPatronalBusinessException be) {
			log.error(be);
			result.put("error", be.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping("solicitud/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_ATRIBUTE_SOLICITUDFORM) Solicitud solicitud,
			HttpServletRequest request, HttpSession session) {
				
		solicitud = gpSolicitudService.consultarSolicitudPorId(solicitud.getSolicitudId());
		model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
		InstanceofPredicate tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
		if (tramiteInicial != null) {
			//Recuperar datos XML detalle tramite
			TramiteSocios tramiteSocio = (TramiteSocios) tramiteInicial;
			Socio socio = new Socio();
			socio.setIdPersonaMoralPatron(tramiteSocio.getPatron().getIdPersona());
			socio.setTipoSocio(new TipoPersona());
			if(tramiteSocio.getSociosFisico()!=null){
				socio.setPersonaFisica(tramiteSocio.getSociosFisico());
				socio.setPersonaMoral(null);
				socio.getTipoSocio().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			}else{
				socio.setPersonaMoral(tramiteSocio.getSocioMoral());
				socio.setPersonaFisica(null);
				socio.getTipoSocio().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}	
			model.addAttribute(KEY_ATRIBUTE_SOCIO,socio);
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				generarCadenaOriginalyFirma(solicitud, personaSesion, session);		
			}
			datosFijosSession(session);
		}
		request.setAttribute(KEY_ATRIBUTE_RETOMAR, true);
		return CONTENIDO_WIZARD_SOCIOS;
	}
	
	@RequestMapping(value = "finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, @RequestBody Socio socio, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();			
		try {
			Solicitud solicitud = gpSolicitudService.consultarSolicitudPorId(idSolicitud);
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				FirmaElectronica firmaElectronica = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
				solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, firmaElectronica);
				result.put(KEY_ATRIBUTE_MENSAJE, MSG_FINALIZAR_SOLICITUD);
			}else{
				//No se finalizan solicitudes de Ventanilla ya que falta indicar el RL, 
				//posterior a ello se manda a encolar la consilicitud para concluir.
				solicitudBusinessRemote.actualizarTramites(solicitud);
				session.setAttribute(KEY_ATRIBUTE_FOLIOSOLICITUD, solicitud.getNoFolioSolicitud());
				session.setAttribute(KEY_ID_SOLICITUD, solicitud.getSolicitudId());
				result.put(KEY_ATRIBUTE_MENSAJE, MSG_ACTUALIZADA_SOLICITUD);
			}
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
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
	
	@RequestMapping(value = "validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Socio socio, 
    		final HttpServletResponse response, final HttpSession session) {
		ReflectionToStringBuilder.toString(socio, ToStringStyle.MULTI_LINE_STYLE);
		final Map<String, Object> result = new HashMap<String, Object>();        
        final Errors errors = new BindException(socio, "model");
        SocioAltaValidator socioValidator = new SocioAltaValidator();        
        if(new CommonValidator().valorNoVacio(socio.getRfc()))
        	socio.setRfc(socio.getRfc().trim().toUpperCase());
        if(new CommonValidator().valorNoVacio(socio.getCurp()))
        	socio.setCurp(socio.getCurp().trim().toUpperCase());
                
        //Validar tipoSocio
        socioValidator.setTipoValidacion(0);
        socioValidator.validate(socio, errors);
        if (!errors.hasErrors()) {
        	//Validar RFC/CURP segun corresponda por el tipoSocio
        	socioValidator.setTipoValidacion(socio.getTipoSocio().getIdTipoPersona().intValue());
        	socioValidator.validate(socio, errors);
        	if (!errors.hasErrors()) {
        		//No existen errores de validación
                result.put(KEY_ATRIBUTE_SOCIO, socio);
                return result;
            }
        }
        //Procesar errores
        procesaErroresDeCaptura(errors, result, response);
        return result;
	}
	
	@RequestMapping(value = "/validaciones/SocioSindicato/{cveIdPersona}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validaPersonaParaAlta(HttpSession session,
			HttpServletRequest request, @PathVariable Long cveIdPersona) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer validaMoralSindicato;				
		try{
			boolean tieneDatosConstitucion=tieneDatosConstitutivos(cveIdPersona);
			if(!tieneDatosConstitucion){
				result.put(KEY_ATRIBUTE_RESPUESTA, iERROR);
				result.put(KEY_ATRIBUTE_MSGERROR, "No es posible realizar un alta de socio debido a que no ha registrado sus datos de constitución.");
				return result;
			}
			validaMoralSindicato=personaMoralServiceBusiness.consultaSindicatoPersonaMoral(cveIdPersona);
				if (validaMoralSindicato > 0) {
					//Regresa registro y se manda parametro 1 para mostrar mensaje de error
					result.put(KEY_ATRIBUTE_RESPUESTA, iERROR);
					result.put(KEY_ATRIBUTE_MSGERROR, "No es posible realizar un alta de socio debido a que está constituido como sindicato.");
					return result;					
				} else {
					//No regresa registro y se manda parametro 0 para seguir con Alta
					result.put(KEY_ATRIBUTE_RESPUESTA, iEXITO);
				}		
		}catch(Exception gpbe){
			result.put(KEY_ATRIBUTE_RESPUESTA, iERROR);
			result.put(KEY_ATRIBUTE_MSGERROR, gpbe.getMessage());
		}		
		return 	result;
	}
	
	@RequestMapping(value = "limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	Solicitud limpiarSesion(final HttpSession session) {
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);		
		session.removeAttribute(KEY_TIPO_TRAMITE);			
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(KEY_ATRIBUTE_FOLIOSOLICITUD);
		session.removeAttribute(KEY_ID_SOLICITUD);
		session.removeAttribute(KEY_USUARIO_SSO);
		
		return null;		
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
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo());
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_TRAMITE_SOLICITUD);
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
	}
	
	private boolean tieneDatosConstitutivos(Long cveIdPersonaMoral){
		boolean tieneDatosConstitucion=false;
		Integer validaMoralSindicato=personaMoralServiceBusiness.consultaSindicatoPersonaMoral(cveIdPersonaMoral);
		if (validaMoralSindicato > 0)
			tieneDatosConstitucion=true;
		Integer validaMoralEscritura=personaMoralServiceBusiness.consultaActaConstitutivaPersonaMoral(cveIdPersonaMoral);
		if (validaMoralEscritura > 0)
			tieneDatosConstitucion=true;
		return tieneDatosConstitucion;
	}
	
}
