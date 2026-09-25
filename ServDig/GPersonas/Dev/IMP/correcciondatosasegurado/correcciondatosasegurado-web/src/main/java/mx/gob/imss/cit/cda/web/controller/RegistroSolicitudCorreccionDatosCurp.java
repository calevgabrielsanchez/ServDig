package mx.gob.imss.cit.cda.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.utils.EnvioCorreoUtils;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.cda.web.validator.CorreccionValidator;
import mx.gob.imss.cit.cda.web.validator.DatosAdicionalesHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DatosHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DocumentosProbatoriosValidator;
import mx.gob.imss.cit.cda.web.validator.DomicilioValidator;
import mx.gob.imss.cit.cda.web.validator.HistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.validator.PersonaRENAPOValidator;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.cit.cda.web.vo.DatosAseguradoVO;
import mx.gob.imss.cit.cda.web.vo.DatosFolioTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.MotivoAclaracionVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.Tramite;
import mx.gob.imss.cit.clienteServiciosComunes.model.Usuario;
import mx.gob.imss.cit.clienteServiciosComunes.services.MediosContactoService;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.InstitucionEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/wizard/correccionDatosAsegurado")
@SessionAttributes(value = {"idPersona","sol","personaCorreccion"})
public class RegistroSolicitudCorreccionDatosCurp extends AbstractController {
	private  final Logger log = LoggerFactory.getLogger(RegistroSolicitudCorreccionDatosCurp.class);
	
	private static final int DIAS_MAXIMOS_ESPERA = 40;
	
	private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";
	private final String VIEW_INICIAR_REGISTRO = "inicioRegistroCDACURP";
	private final String VIEW_INFORMACION_RENAPO = "obtenerInformacionRenapo";
	private final String VIEW_LOGIN = "loginRegistro";
	private final String VIEW_CAPTURAR_DOMICILIO = "capturarDomicilio";
	private final String VIEW_SEGUIMIENTO_TRAMITE = "seguimientoTramite";
	private final String VIEW_FOLIO_TRAMITE = "folioTramite";
	private final String VIEW_CAPTURAR_SOLICITUD_RESPONSABLE = "registroSolicitudCDAResponsable";
	private static final String PERSONA_SESSION_KEY = "persona_correccion";
	private static final String CONTINUACION_TRAMITE_KEY = "esContinuacionTramite";
	private static final String SOLICITUD = "solicitud";
	private final String VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL = "datosAdicionalesHistoriaLaboral";
	private final String VIEW_DATOS_HISTORIA_LABORAL = "datosHistoriaLaboral";
	private final String VIEW_INFORMACION_HISTORIA_LABORAL = "informacionHistoriaLaboral";
	private final String VIEW_CONFIRMAR_SOLICITUD = "confirmarDatosSolicitud";
	private final String VIEW_RECURSO_NO_DISPONIBLE = "recursoNoDisponible";
	private final static String VIEW_GENERAR_ACUSE = "/generarAcuse";
	private final static String DOMICILIO_ACLARACION_KEY = "domicilioAclaracion";
	private static final String MOTIVOS_ACLARACION_IMSS = "motivosAclaracionIMSS";
	private static final String MOTIVOS_ACLARACION_INFONAVIT = "motivosAclaracionInfonavit";
	private static final String MOTIVOS_ACLARACION_AFORE ="motivosAclaracionAfore";
	private static final String STATUS_ERROR_UPLOAD="error";
	private static final String STATUS_SUCCESS_UPLOAD="success";
	private static final String TIPO_ID_USR ="IDPERSONA";
	private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";
	private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
	private static final String USUARIO_INTERNET_BITACORA ="ASEGURADO";
	private static final String ID_DOCUMENTO_BOVEDA="idDocBoveda";
        
	
	private static final String EXTENCIONES_VALIDAS[]={"gif","tif","jpg","png","pdf"};
	
	private static final String NULL= "null";

	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

	@Autowired
	private PersonaBusinessRemote personaBusiness;

	@Autowired
	private NSSValidator nSSValidator;

	@Autowired
	private HistoriaLaboralValidator historiaLaboralValidator;
	@Autowired
	private DatosHistoriaLaboralValidator datosHistoriaLaboralValidator;
	@Autowired
	private DatosAdicionalesHistoriaLaboralValidator datosAdicionalesHistoriaLaboralValidator;
	@Autowired
	private DomicilioValidator domicilioValidator;
//	@Autowired
//	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusinessRemote;
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionRemote;
	
//        @Autowired
//	@Qualifier("responsableTareaBusiness")
//	private ResponsableTareaRemote responsableTareaRemote;
	
	@Autowired
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

	@Autowired
	private DocumentosProbatoriosValidator documentosProbatoriosValidator;
	
	@Autowired
	@Qualifier("documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	
	@Autowired
	@Qualifier("solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	
	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Autowired
	@Qualifier("cuentaIndividualBusiness")
	private CuentaIndividualRemote cuentaIndividualBusiness;
	
//	@Autowired
//	private DeltaUtils deltaUtils;
	
	@ModelAttribute("idPersona")
    public Long getIdPersona() {
        return 0L;
    }
	
	@ModelAttribute("sol")
	public Solicitud  getSolicitud() {
		return new Solicitud();
	}
	
	@ModelAttribute("personaCorreccion")
	public Fisica  getFisica() {
		return new Fisica();
	}

	// EJB de solicitudes
	@Autowired
	@Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

	@Autowired
	@Qualifier("manejadorReportesBusiness")
	private ManejadorReportesRemote manejadorReportesBusiness;
	
	@Autowired
	private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;	
	
	@Autowired
	@Qualifier("bovedaBusiness")
	private BovedaRemote bovedaBusiness;
	
	@Autowired
	private EnvioCorreoUtils correoUtils;

	private ConsultaSolicitudTramiteVO solicitante;
	
	@RequestMapping(value = "")
	public String inicioCorreccionCurp(Model model, HttpSession session) {
		
		model.addAttribute("tramiteSeguro", "");
		
		session.removeAttribute("datosAdicionalesHistoriaLaboral");
		session.removeAttribute("datosHistoriaLaboral");
		session.removeAttribute("tramiteSeguro");
		session.removeAttribute(SOLICITUD);
		session.removeAttribute(PERSONA_SESSION_KEY);
		session.removeAttribute(DOMICILIO_ACLARACION_KEY);
		session.removeAttribute("informacionHistoriaLaboral");
		session.removeAttribute("domicilioAclaracion");
		
		session.removeAttribute(CONTINUACION_TRAMITE_KEY);
		
		Fisica fisica = new Fisica();
		model.addAttribute("fisica", fisica);
		return VIEW_LOGIN;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public Object consultaDatosBasicosGobMX(@ModelAttribute Fisica fisica, BindingResult result, Model model,
			@RequestParam("captcha") String captcha, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("---CDA--- Error de comunicacion con RENAPO");
		//Validar LogIn{}
		
		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);
				
		CorreccionValidator validator = new CorreccionValidator();
		validator.validate(fisica, result);
		
		// Validar captcha
		validarCaptcha(captcha, session, result);
		
		if (CollectionUtils.isNotEmpty(result.getAllErrors())) {
			// Si el captcha es invalido entonces regresamos al Login con el mensaje de error correspondiente
			return VIEW_LOGIN;
		}
		
		boolean curpCorreoValido = portalCiudadanoServiceBusinessRemote.validaRegistroCurpCorreoCiudadano (
				fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(), DateUtils.addDays(new Date(), -DIAS_MAXIMOS_ESPERA));
		
		if (curpCorreoValido == false) {
			fisica.setErrorFormGeneral("Es probable que el correo ya este asignado a otra curp");
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		} 
		
		Solicitud solicitudActiva = null;
		
		try {
			
			Fisica personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp());
			
			if (personaRenapo != null) {
				
				new PersonaRENAPOValidator().validate(personaRenapo, result);
				personaRenapo.setCorreoElectronico(fisica.getCorreoElectronico());
				fisica = personaRenapo;
				
				List<String> curps = new ArrayList<String>();
				curps.add(fisica.getCurp());
				
				if (CollectionUtils.isNotEmpty(fisica.getCurpsHistoricas())) {
					curps.addAll(fisica.getCurpsHistoricas());
					validarCorreoCurpsHistoricas(fisica, curps);
				}
				
				solicitudActiva =  registroSolicitudCorreccionDatosAseguradoBusiness.obtenerUltimaSolicitudSeguimientoCDA(curps, ORIGEN_SOLICITUD_INTERNET);
				
				//validar que la persona en caso de ser encontrada por CURP 
				//en BDTU no sea patron ni representante legal
				Map<String, Object> roles = registroSolicitudCorreccionDatosAseguradoBusiness.personaAutorizadaRegistroCDA(fisica.getCurp(),fisica.getCurpsHistoricas());			
				if (roles != null && !roles.isEmpty() && !validarSolicitudAtendida(solicitudActiva)){
					log.error("---CDA--- La persona no puede registrar una solicitud ya que no es Asegurado");
					result.reject("label.solicitud.mensaje.error.rol");
				}
				
			} else {
				result.reject("label.solicitud.mensaje.error.curp");
				return VIEW_LOGIN;
			}
			
		} catch (ClienteWebserviceRenapoCurpException ewsr) {
			log.error("---CDA--- Error de comunicacion con RENAPO", ewsr);
			result.reject("label.solicitud.mensaje.error.renapo");
		} catch (PortalCiudadanoException pex) {
			log.error("---CDA--- ----------ocurrio un error al realizar validacion CURP-Correo ", pex);
			fisica.setErrorFormGeneral(pex.getMessage());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN;
		}
		
		if (result.hasErrors()) {
			this.log.warn("---CDA--- Errores de captura");
			return VIEW_LOGIN;
		}
		
		if(solicitudActiva != null && solicitudActiva.getSolicitudId() != null){
			this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva.getNoFolioSolicitud());
                        solicitudActiva = registroCorreccionCurpUtil.orderByTramite(solicitudActiva);
                        this.log.debug("---CDA--- objeto Renapo: {}",((TramiteCorreccionCurp)solicitudActiva.getTramites().get(0)));
			((TramiteCorreccionCurp)solicitudActiva.getTramites().get(0)).getPersonaRENAPO().setCorreoElectronico(fisica.getCorreoElectronico());
			session.setAttribute(SOLICITUD, solicitudActiva);
			session.setAttribute(PERSONA_SESSION_KEY, ((TramiteCorreccionCurp)solicitudActiva.getTramites().get(0)).getPersonaRENAPO());
			return new RedirectView("/wizard/correccionDatosAsegurado/seguimientoTramite", true);
		} else {
			if(solicitudActiva != null) {
				log.error("---CDA--- Se encontro mas de un resultado");
				result.reject("label.registros.encontrados.asociados.curp");
				return VIEW_LOGIN;
			}
		}	
		
		this.log.error("---CDA--- CURP fisica en sesion: {}", fisica.getCurp());
		session.setAttribute(PERSONA_SESSION_KEY, fisica);
		return new RedirectView("/wizard/correccionDatosAsegurado/obtenerInformacionRenapo", true);
	}
	
	@RequestMapping(value = "/seguimientoSolicitudCURP")
	public Object seguimientoSolicitudCURP(@RequestParam("CURP") String curp,HttpSession session,HttpServletRequest request) {
		final Errors result = new BindException(curp, "model");
		Fisica personaRenapo = null;
		Solicitud solicitudActiva= null;
		try {
			personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
			if(personaRenapo==null){
				result.reject("label.solicitud.mensaje.error.curp");
			}
			new PersonaRENAPOValidator().validate(personaRenapo, result);			
		} catch (ClienteWebserviceRenapoCurpException e1) {
			log.error("---CDA--- Error de comunicacion con RENAPO", e1);
			result.reject("label.solicitud.mensaje.error.renapo");
		}
		
		if (result.hasErrors()) {
			this.log.warn("---CDA--- Errores de captura");
			return VIEW_LOGIN;
		}
		
		//cambiar por tabla de CDA
		
		List<String> curps = new ArrayList<String>();
		curps.add(personaRenapo.getCurp());
		if(personaRenapo.getCurpsHistoricas()!= null){
			curps.addAll(personaRenapo.getCurpsHistoricas());
		}
		
		solicitudActiva =  registroSolicitudCorreccionDatosAseguradoBusiness.obtenerUltimaSolicitudSeguimientoCDA(curps,ORIGEN_SOLICITUD_INTERNET);
		this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva);
		if(solicitudActiva != null && solicitudActiva.getSolicitudId() !=null){
			this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva.getNoFolioSolicitud());
			session.setAttribute(SOLICITUD, solicitudActiva);
			session.setAttribute(PERSONA_SESSION_KEY, ((TramiteCorreccionCurp)solicitudActiva.getTramites().get(0)).getPersonaRENAPO());
			return new RedirectView("/wizard/correccionDatosAsegurado/seguimientoTramite", true);
		}else{
			if(solicitudActiva != null){
				log.error("---CDA--- Se encontro mas de un resultado");
				result.reject("label.registros.encontrados.asociados.curp");
			}
		}
		
		return VIEW_LOGIN;
		
	}
	
	
	/**
	 * Valida que el captcha capturado no sea una cadena vacia y que sea identico al guardado en la sesion
	 * @param captcha
	 * @param session
	 * @param result
	 */
	private void validarCaptcha(String captcha, HttpSession session, BindingResult result) {
		if (StringUtils.isBlank(captcha)) {
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "Campo requerido");
			result.addError(fieldError);
		} else if (!captcha.equals(session.getAttribute("captcha"))) {
			this.log.error("---CDA--- Captcha no valido!!!");
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral",
					"El captcha no fue v\u00e1lido, favor de intentar nuevamente");
			result.addError(fieldError);
		}
	}
	
	private boolean validarSolicitudAtendida(Solicitud sol){		
		boolean solicitudAtendida = sol != null;
		if(solicitudAtendida){
			log.debug("---CDA---Solicitud {}",sol.getEstadoSolicitud().getIdEstadoSolicitud());
		}
		return solicitudAtendida && sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		
	}
	
	private void validarCorreoCurpsHistoricas(Fisica fisica,List<String>curpsHistoricas) throws PortalCiudadanoException{
		for(String curp: curpsHistoricas){
			portalCiudadanoServiceBusinessRemote.validaRegistroCurpCorreoCiudadano(curp, fisica.getCorreoElectronico().getCorreo());
		}
	}

	@RequestMapping(value = "/obtenerInformacionRenapo", method = RequestMethod.GET)
	public String informacionRenapo(Model model, HttpSession session) {
		Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		model.addAttribute("fisica", persona);
		return VIEW_INFORMACION_RENAPO;
	}

	@RequestMapping(value = "/obtenerInformacionRenapo", method = RequestMethod.POST)
	public String consultaInformacionRenapo(Model model, HttpSession session) {
		Fisica solicitante = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		
		mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
		usuario.setCveIdUsuario(solicitante.getCurp());
		usuario.setUsuario(solicitante.getCurp());
		
		Solicitud solicitud;
		
		if(session.getAttribute(SOLICITUD) == null) {
			solicitud = guardarSolicitudInicial(solicitante, OrigenSolicitudEnum.PORTAL_CIUDADANO, usuario);				
			session.setAttribute(SOLICITUD, solicitud);
		}
		
		return capturaDomicilioAclaracion(model, session);
	}
	
	@RequestMapping(value = "/validar/noExisteSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> validaNoExisteSolicitud (@RequestParam String curp, HttpSession session) {		
		List<String> curps = new ArrayList<String>();
		curps.add(curp);

		Solicitud solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness.obtenerUltimaSolicitudSeguimientoCDA(curps, ORIGEN_SOLICITUD_INTERNET);
		
		Boolean continuacionTramite = (Boolean) session.getAttribute(CONTINUACION_TRAMITE_KEY);
		
		Map<String, Boolean> resultado = new HashMap<String, Boolean> ();
		
		if (continuacionTramite != null && continuacionTramite) {
			resultado.put("noExisteSolicitud", true);
		} else {
			resultado.put("noExisteSolicitud", solicitudActiva != null ? !solicitudActiva.getEstadoSolicitud()
					.getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo()) : true);
		}
		
		return resultado;
	}
	
	@RequestMapping(value = "/capturarDomicilio", method = RequestMethod.GET)
	public String capturaDomicilioAclaracion(Model model, HttpSession session) {
		DomicilioCorto domicilioAclaracion;
		if (session.getAttribute("domicilioAclaracion") != null) {
			domicilioAclaracion = (DomicilioCorto) session.getAttribute("domicilioAclaracion");
		} else {
			domicilioAclaracion = new DomicilioCorto();
			
		}
		model.addAttribute(DOMICILIO_ACLARACION_KEY, domicilioAclaracion);
		model.addAttribute(MOTIVOS_ACLARACION_IMSS, TiposAclaracionEnum.obtenerMotivosPorDependencia(Dependencia.IMSS));
		model.addAttribute(MOTIVOS_ACLARACION_INFONAVIT, TiposAclaracionEnum.obtenerMotivosPorDependencia(Dependencia.INFONAVIT));
		model.addAttribute(MOTIVOS_ACLARACION_AFORE, TiposAclaracionEnum.obtenerMotivosPorDependencia(Dependencia.AFORE));
		return VIEW_CAPTURAR_DOMICILIO;
	}

	@RequestMapping(value = "/capturarDomicilio", method = RequestMethod.POST)
	public RedirectView capturaDomicilioAclaracion(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(DOMICILIO_ACLARACION_KEY) DomicilioCorto domicilioCorto,
			BindingResult result, Model model) {
		
		log.debug("---CDA--- ***********Subdelegacion******** {} ", domicilioCorto.getSubdelegacion());
		
		Fisica solicitante = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);

		List<Domicilio> domiciliosList = new ArrayList<Domicilio>();
		Domicilio domicilio = new Domicilio();
		BeanUtils.copyProperties(domicilioCorto, domicilio);
		
		domiciliosList.add(domicilio);
		solicitante.setDomicilios(domiciliosList);
		
		mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
		usuario.setCveIdUsuario(solicitante.getCurp());
		usuario.setUsuario(solicitante.getCurp());
		usuario.setCveIdSubdelegacion(domicilioCorto.getSubdelegacion().getId());
		
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
		((TramiteCorreccionCurp)solicitud.getTramites().get(0)).setPersonaRENAPO(solicitante);
		
		if (solicitante != null) {
			solicitud.setPersonaInteresada(solicitante);
		}
		
		if(usuario != null){
			solicitud.setSolicitante(usuario);
		}
                
		solicitud.setSubdelegacion(domicilioServiceBusinessRemote.obtenerSubdelegacionPorId(domicilioCorto.getSubdelegacion().getId()));
		log.debug("---CDA--- **********Subdelegacion********** {} ", solicitud.getSubdelegacion());
		registroSolicitudCorreccionDatosAseguradoBusiness.actualizarSubdelegacionSolicitud(solicitud);
		session.setAttribute(SOLICITUD, actualizarXmlMotivos(solicitud,domicilioCorto.getMotivoAclaracionVO()));
		session.setAttribute(SOLICITUD, actualizarTramites(solicitud));
		session.setAttribute("domicilioAclaracion", domicilioCorto);
		
		if(((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getDomicilios() == null || ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getDomicilios().isEmpty()){
			return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CAPTURAR_DOMICILIO,true);
		}
		
		DatosHistoriaLaboralVO informacionHistoriaLaboral;
		if (session.getAttribute("informacionHistoriaLaboral") != null) {
			informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
		} else {
			informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
			session.setAttribute("informacionHistoriaLaboral", informacionHistoriaLaboral);
		}
                
                List<DatosLaborales> listDatosLaborales=((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getDatosLaborales();
                if(listDatosLaborales!=null && !listDatosLaborales.isEmpty()){
                    List<HistoriaLaboralVO> listHist = new ArrayList<HistoriaLaboralVO>();
                    for(DatosLaborales datoLaboral:listDatosLaborales){
                        HistoriaLaboralVO histLaboral= new HistoriaLaboralVO();
                        histLaboral.setDomicilioEmpresa(datoLaboral.getDomicilio());
                        histLaboral.setActividadEmpresa(datoLaboral.getActividad());
                        histLaboral.setEntidadFederativa(datoLaboral.getEntidadFederativaNombre());
                        histLaboral.setFechaBaja(datoLaboral.getFechaBaja());
                        histLaboral.setFechaInscripcion(datoLaboral.getFechaInscripcion());
                        histLaboral.setNombrePatron(datoLaboral.getNombrePatron());
                        histLaboral.setNumeroRegistroPatronal(datoLaboral.getNrp());
                        histLaboral.setClaveEntidad(datoLaboral.getEntidadFederativa().getClave());
                        listHist.add(histLaboral);
                    }
                    informacionHistoriaLaboral.setHistoriaLaboralGrid(listHist);
                }
		model.addAttribute("informacionHistoriaLaboral",informacionHistoriaLaboral);
		return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE + "/" + VIEW_INFORMACION_HISTORIA_LABORAL,true);


	}
	
        @RequestMapping(value = "/actualizarLista", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> actualizarListaHistorial(
			@RequestBody HistoriaLaboralVO historiaLaboralVO, HttpServletResponse response,HttpSession session, Model model) {

		Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
                DatosHistoriaLaboralVO documentoProb=(DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
                if(documentoProb.getHistoriaLaboralGrid()!=null && !documentoProb.getHistoriaLaboralGrid().isEmpty()){
                    Iterator<HistoriaLaboralVO> itera=documentoProb.getHistoriaLaboralGrid().iterator();
                    while(itera.hasNext()) {
                        HistoriaLaboralVO datosLab= itera.next();
                        if (historiaLaboralVO.getDomicilioEmpresa().equals(datosLab.getDomicilioEmpresa()) &&
                             historiaLaboralVO.getActividadEmpresa().equals(datosLab.getActividadEmpresa()) && 
                              historiaLaboralVO.getEntidadFederativa().equals(datosLab.getEntidadFederativa()) 
                                && historiaLaboralVO.getFechaBaja().equals(datosLab.getFechaBaja()) &&
                            historiaLaboralVO.getFechaInscripcion().equals(datosLab.getFechaInscripcion()) &&
                            historiaLaboralVO.getNombrePatron().equals(datosLab.getNombrePatron()) &&
                            historiaLaboralVO.getNumeroRegistroPatronal().equals(datosLab.getNumeroRegistroPatronal())) {
                            log.debug("---CDA--- Se remueve de session historiaLaboralVO {} ",datosLab);
                            itera.remove();
                        }
                    }
                }
                TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
                Map<String, Object> result = new HashMap<String, Object>();
		tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
                if(tramite.getDatosLaborales()!=null && !tramite.getDatosLaborales().isEmpty()){
                    Iterator<DatosLaborales> itera=tramite.getDatosLaborales().iterator();
                    while(itera.hasNext()) {
                        DatosLaborales datosLab= itera.next();
                        if (historiaLaboralVO.getDomicilioEmpresa().equals(datosLab.getDomicilio()) &&
                             historiaLaboralVO.getActividadEmpresa().equals(datosLab.getActividad()) && 
                              historiaLaboralVO.getEntidadFederativa().equals(datosLab.getEntidadFederativaNombre()) 
                                && historiaLaboralVO.getFechaBaja().equals(datosLab.getFechaBaja()) &&
                            historiaLaboralVO.getFechaInscripcion().equals(datosLab.getFechaInscripcion()) &&
                            historiaLaboralVO.getNombrePatron().equals(datosLab.getNombrePatron()) &&
                            historiaLaboralVO.getNumeroRegistroPatronal().equals(datosLab.getNrp())) {
                            log.debug("---CDA--- historiaLaboralVO {}",historiaLaboralVO.getNumeroRegistroPatronal());
                            itera.remove();
                        }
                    }
                    try {
                        log.debug("---CDA--- tamanio de datoslaborales {}",tramite.getDatosLaborales().size());
                        solicitudBusiness.actualizarXmlTramite(tramite);
                        sol.getTramites().set(0, tramite);
                        session.setAttribute(SOLICITUD, sol);
                    } catch (TramiteNoEncontradoException ex) {
                        log.debug("---CDA--- TramiteNoEncontradoException {}",ex);
                    } catch (IllegalArgumentException ex) {
                        log.debug("---CDA--- IllegalArgumentException {}",ex);
                    }
                }
                result.put("success","");
		log.debug("---CDA--- actualiza la tabla {}",result.toString());
		return result;
	}
	
	
	private Solicitud actualizarXmlMotivos(Solicitud solicitud,MotivoAclaracionVO motivoAclaracionVO){
		TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
		if(solicitud.getTramites() != null){
			log.debug("---CDA--- Tramite {}", solicitud.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		}		
		tramite.setMotivosAclaracion(registroCorreccionCurpUtil.cargarMotivosAclaracion(motivoAclaracionVO));
		try {
			solicitudBusiness.actualizarXmlTramite(tramite);
		} catch (TramiteNoEncontradoException e) {
			log.error("---CDA--- No se encontro el Tramite {}",e);
		} catch (IllegalArgumentException e) {
			log.error("---CDA--- Ocurrio un error al actualizar el tramite {}",e);
		}
		
		return solicitud;		
	}
	
	private Solicitud actualizarTramites(Solicitud solicitud){
		Solicitud solicitudReturn= null;
		try {
			solicitudReturn = solicitudBusiness.actualizarTramites(solicitud);
		} catch (TramiteNoEncontradoException e) {
			log.error("---CDA--- No se encontro el Tramite {}",e);
		} catch (SolicitudNoEncontradaException e){
			log.error("---CDA--- Solicitud no encontrada {}",e);
		}
		
		return solicitudReturn;		
	}
	
	@RequestMapping(value = "/validar/domicilioCorto", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validateDomicilioCorto(
			@RequestBody DomicilioCorto domicilioCorto, HttpServletResponse response) {
		final Errors errors = new BindException(domicilioCorto, "model");

		log.debug("---CDA--- ############Validar Domicilio#####################");
		log.debug("---CDA--- Domicilio corto recibido : {} ", domicilioCorto);
		
		domicilioValidator.validate(domicilioCorto, errors);
		
		log.debug("---CDS--- {} {}",errors.getAllErrors(),errors.hasErrors());

		Map<String, Object> result = new HashMap<String, Object>();

		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
		}

		log.debug("---CDA--- validar domicilioCorto {}",result.toString());
		return result;
	}

	@RequestMapping(value = "/asentamiento/get/codigoPostal", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigo(@RequestParam String codigo,
			HttpServletResponse response) {
		this.log.debug("---CDA--- codigo postal :{}" + codigo);
		Map<String, Object> result = new HashMap<String, Object>();
		CodigoPostal codigoPostal = new CodigoPostal();
		codigoPostal.setCodigoPostal(codigo);

		Errors errors = new BindException(codigoPostal, "model");
		if (codigo == null || codigo.isEmpty()) {
			this.log.error("---CDA--- codigo postal nulo regresando el error");
			errors.rejectValue("codigoPostal", "field.required");
		} else if (codigo.length() != 5) {
			this.log.error("---CDA--- codigo postal incorrecto regresando el error");
			errors.rejectValue("codigoPostal", "field.cp.incorrecto");
		}

		if (!errors.hasErrors()) {
			try {
				List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote
						.getAsentamientoPorCodigoPosta(codigoPostal);
				this.log.debug("---CDA--- Asentamientos[ " + asentamientos + "]");
				result.put("asentamientos", asentamientos);				
				try {					
					result.put(
							"subdelegaciones",
							generarListaSubdelegacion(
									codigoPostal.getCodigoPostal(),
									asentamientos.get(0).getMunicipio()));					
				} catch (UmfNoLocalizadaException ex) {
					this.log.error("---CDA--- no se encontr\u00F3 ningun municipio para el asentamiento");
				} catch (Exception e) {
					this.log.error("---CDA--- error no especificado ", e);
				}
			} catch (DomicilioNoLocalizadoException e) {
				this.log.error("---CDA--- Domicilio no identificado ", e);
			}
		} 
		return result;
	}
	
	private List <Subdelegacion> generarListaSubdelegacion(String codigoPostal,Municipio municipio) throws UmfNoLocalizadaException, MunicipioImssNoLocalizadoException {
		
		List<UnidadMedicaFamiliar> unidades = domicilioServiceBusinessRemote.getUmfByCodigoPostal(codigoPostal);
		
		List <MunicipioIMSS> unidadesEstado = null;
				
		if(unidades == null || unidades.isEmpty()){
			Municipio municipioClon = new Municipio();
			BeanUtils.copyProperties(municipio, municipioClon);
			municipioClon.setClave("");
			unidadesEstado = this.domicilioServiceBusinessRemote.getMunicipioIMSSbyEstadoMunCP(municipioClon, codigoPostal);					
		}
		
		List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
		Set<String> clavesSubdelegacion = new HashSet<String>();
		if(unidades != null && !unidades.isEmpty()){
			for(UnidadMedicaFamiliar unidad: unidades){
				if(clavesSubdelegacion.add(unidad.getSubdelegacion().getClave())){
					subdelegaciones.add(unidad.getSubdelegacion());
				}			
			}
		}
		if(unidadesEstado != null && !unidadesEstado.isEmpty()){
			for(MunicipioIMSS unidad: unidadesEstado){
				if(clavesSubdelegacion.add(unidad.getSubdelegacion().getClave())){
					subdelegaciones.add(unidad.getSubdelegacion());
				}			
			}
		}
		
		
		Collections.sort(subdelegaciones, new Comparator<Subdelegacion>() {

			@Override
			public int compare(Subdelegacion o1, Subdelegacion o2) {
				return o1.getClave().compareToIgnoreCase(o2.getClave());
			}
		});
		
		return 	subdelegaciones;
	}

	public Solicitud guardarSolicitudInicial(Fisica solicitante,  OrigenSolicitudEnum origenSolicitud, mx.gob.imss.ctirss.delta.model.Usuario usuario) {
		Solicitud solicitud = null;
        try {
			solicitud =  this.registroSolicitudCorreccionDatosAseguradoBusiness.crearTramiteCorreccionCurp(solicitante, origenSolicitud, usuario);
		} catch (SolicitudNoValidaException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		} catch (SolicitudNoEncontradaException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		} catch (TramiteNoEncontradoException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		}
        log.debug("SE CREA EL GUARDADO INICIAL DE LA SOLICITUD {}", solicitud);
        
        try {
			portalCiudadanoServiceBusinessRemote.validarInicioCurpCorreo(solicitante.getCurp(), solicitante.getCorreoElectronico().getCorreo(),true);
		} catch (Exception e) {
			log.error("---CDA---  Error actualizacion correo",e);
		}
        
        
		return solicitud;
	}
	
	public Solicitud actualizarSolicitud(Solicitud solicitud, DatosHistoriaLaboralVO documentosNss, 
			DatosAdicionalesHistoriaLaboral contacto, DomicilioCorto motivosAclaracion){
		TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
		if(solicitud.getTramites() != null){
			log.debug("---CDA--- el tramite no es null {}", solicitud.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
                        log.debug("---CDA--- el tramite no es null {}", solicitud.getTramites().get(0).getDocumentosProbatorios());
		}
		
		if(documentosNss != null)
			log.debug("---CDA--- los documentos del NSS no son nulos {}", documentosNss.getNSSList().get(0));
		if(contacto != null)
			log.debug("---CDA--- los datos de contacto no son nulos {}", contacto);
		
//		List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> doctosPersistencia = registroCorreccionCurpUtil.cargarDatosTramite(tramite, documentosNss, contacto);
//		log.debug("---CDA--- Numero de documentos persistidos: {}", doctosPersistencia!= null ? doctosPersistencia.size() : "0");
		tramite.setDatosLaborales(registroCorreccionCurpUtil.cargarDatosLaborales(documentosNss.getHistoriaLaboralGrid()));
		log.debug("---CDA--- HistoriaLaboralGrid: {}", documentosNss.getHistoriaLaboralGrid());		
//		tramite.setDocumentosProbatorios(doctosPersistencia);
		tramite.setMotivosAclaracion(registroCorreccionCurpUtil.cargarMotivosAclaracion(motivosAclaracion.getMotivoAclaracionVO()));
		log.debug("---CDA--- Numero de motivos de aclaracion: {}", tramite.getMotivosAclaracion() != null ? tramite.getMotivosAclaracion().size() : "0");
		return solicitud;
	}

	@RequestMapping(value = "/confirmarDatosSolicitud", method = RequestMethod.GET)
	public String confirmarDatosSolicitud(Model model, HttpSession session) {
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
		model.addAttribute("solicitud", solicitud);		
		log.debug("---CDA--- los datos de tramite son {}", solicitud.getTramites().get(0));
		return VIEW_CONFIRMAR_SOLICITUD;
	}

		@RequestMapping(value = "/confirmarSolicitud", method = RequestMethod.POST)
	public RedirectView confirmarDatosSolicitud(Model model, HttpSession session,
			@ModelAttribute("datosSolicitud") Solicitud datosSolicitud,HttpServletRequest request) {
		Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
		log.info("---CDA--- Tramites en solicitud {}" , sol.getTramites().size());
		//Invocar bpmService.iniciarWorkFlow
				 
			try {				
				List<InicioTramite> listaInicioTramite = iniciarWorkFlow(sol);                               
				this.registroSolicitudCorreccionDatosAseguradoBusiness.finalizaRegistroCorreccionDatosAsegurados(sol,listaInicioTramite,null, OrigenCapturaCDAEnum.SOLICITUD.getClave());
			} catch (CorreccionDatosAseguradoException e1) {
				log.error("---CDA--- Ocurrio un error {}",e1);
				return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CONFIRMAR_SOLICITUD,true);
			} catch (SolicitudNoEncontradaException e1) {
				log.error("---CDA--- Ocurrio un error {}",e1);
				return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CONFIRMAR_SOLICITUD,true);
			} catch (TramiteNoEncontradoException e1) {
				log.error("---CDA--- Ocurrio un error {}",e1);
				return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CONFIRMAR_SOLICITUD,true);
			} catch (RegistrarDocumentoProbatorioException e) {
				log.error("---CDA--- Error en el registro de los documentos ", e);
				return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CONFIRMAR_SOLICITUD,true);
			} catch (Exception e) {
				log.error("---CDA--- Error ", e);
				return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_CONFIRMAR_SOLICITUD,true);
			}
			
			return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+VIEW_GENERAR_ACUSE,true);
		
	}
	
	@RequestMapping(value = VIEW_GENERAR_ACUSE, method = RequestMethod.GET)
	public RedirectView generarAcuse(Model model, HttpSession session,HttpServletRequest request){
		Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
		
		Fisica personaCorreccion =(Fisica)session.getAttribute(PERSONA_SESSION_KEY);
		DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO)session.getAttribute("informacionHistoriaLaboral");
		
		DomicilioCorto motivosAclaracion = (DomicilioCorto)session.getAttribute("domicilioAclaracion");
		
		List<InicioTramite> listaInicioTramite= null;
		listaInicioTramite = iniciarWorkFlow(sol);
		
		try {
			FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness.selloDigital(personaCorreccion,sol);
			sol.setFirmaElectronica(firma);
			session.setAttribute(SOLICITUD, sol);			
		} catch (CorreccionDatosAseguradoException e) {
			log.warn("---CDA---  Error al generar la firma  {}",e);
		}
		byte[] adjuntos = (byte[]) obtenerComprobanteSolicitud(sol,documentoProb,motivosAclaracion);
				
		try {
			
			String idDocumento = bovedaBusiness.subirDocumento(adjuntos, sol,TipoDocumentoCDAEnum.ACUSE);
			agregarIdDocumento(sol, idDocumento,TipoDocumentoCDAEnum.ACUSE);
		}catch (BovedaCDAException bce){
			log.error(bce.getSituacion(), bce);			
		} catch (SolicitudNoEncontradaException e) {
			log.error("---CDA--- Error al actualizar XML  {}",e);
		} catch (TramiteNoEncontradoException e) {
			log.error("---CDA--- Error al actualizar XML  {}",e);
		} catch (IllegalArgumentException e) {
			log.error("---CDA--- Error al actualizar XML  {}",e);
		}
		log.debug("Documentos adjuntos" + adjuntos);
		
		String url = request.getScheme()+"://"+request.getServerName()+request.getContextPath();
		enviarCorreoCorreccionDatosPorCurp(sol, adjuntos, listaInicioTramite.get(0).getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), url);
		
		model.addAttribute("sol", sol);
		model.addAttribute("personaCorreccion", personaCorreccion);
		return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_FOLIO_TRAMITE,true);
	}
	
	
	private List<InicioTramite> iniciarWorkFlow(Solicitud solicitud){
		
            List<InicioTramite> listaInicioTramite = new ArrayList<InicioTramite>();
            for(mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud.getTramites()){
                
                InicioTramite inicioTramite = null;
                TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) tramite;   
                TareaBandeja tareaBandeja = flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCDA.getTramiteId());
                log.info("Tarea bandeja  {}", tareaBandeja);
                if(tareaBandeja != null && tareaBandeja.getInicioTramite() != null){
			inicioTramite = tareaBandeja.getInicioTramite();
		} 
                    inicioTramite = (inicioTramite != null ? inicioTramite : inicioTramiteWorkFlow(solicitud, tramiteCDA));
                  
                listaInicioTramite.add(inicioTramite);                
            }
		
		
		return listaInicioTramite;
	}
        
        private InicioTramite inicioTramiteWorkFlow(Solicitud solicitud, TramiteCorreccionCurp tramiteCDA){
            
		List<Fisica> autorizador = new ArrayList<Fisica>();
		List<Fisica> listResponsable = new ArrayList<Fisica>();
				log.debug("---CDA--- se asigna la solicitud {} para la subdelegacion y la delegacion {}",new Object[]{solicitud.getSolicitudId(),solicitud.getSubdelegacion().getId(),solicitud.getSubdelegacion().getDelegacion().getId()});
				Fisica fisicaAutorizador = new Fisica();
				fisicaAutorizador.setCurp(FlujoTrabajoConstants.BPM_ADMIN+solicitud.getSubdelegacion().getId());
				autorizador.add(fisicaAutorizador);
				String ultimoResponsable=flujoTrabajoBusiness.getCurpResponsable(solicitud.getSubdelegacion().getId().toString());
				
				log.debug("---CDA---EL CURP OBTENIDO DEL RESP ES {}",ultimoResponsable);
                                log.debug("---CDA---EL CURP OBTENIDO DEL autorizador es ES {}",autorizador.get(0).getCurp());

				try{
					listResponsable = responsablesDelegacionRemote.
							consultarResponsablesDelegacion(solicitud.getSubdelegacion().getDelegacion().getId().intValue(),
									solicitud.getSubdelegacion().getId().intValue());
					log.debug("---CDA--- balanceador {}",listResponsable.size());
                                        
                                        for(Fisica curp : listResponsable){
                                        log.debug("---CDA--- curps de los responsables {}",curp.getCurp());                                            
                                        }
					
				} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
					log.error("---CDA--- Error al consultar webservice {} ",e);
					Fisica fisicaResponsable = new Fisica();
					fisicaResponsable.setCurp(SUBDELEGACION_SIN_RESPONSABLE);
					listResponsable.add(fisicaResponsable);					
				}
		
            return WorkFlowDataUtil.iniciarTramite(solicitud,autorizador, tramiteCDA, listResponsable,ultimoResponsable);
        }
	
	@RequestMapping(value = "/folioTramite", method = RequestMethod.GET)
	public String obtenerFolioTramite(Model model, HttpSession session) {
		DatosFolioTramiteVO folio = new DatosFolioTramiteVO();

		model.addAttribute("folio", folio);
		return VIEW_FOLIO_TRAMITE;
	}

	@RequestMapping(value = "/seguimientoTramite")
	public String seguimientoTramite(Model model, HttpSession session) {

		Solicitud sol = (Solicitud)session.getAttribute(SOLICITUD);
		Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) sol.getTramites().get(0);
		DomicilioCorto domicilioSolicitud = new DomicilioCorto();
		
		if (CollectionUtils.isNotEmpty(tramiteCDA.getPersonaRENAPO().getDomicilios())) {
			BeanUtils.copyProperties(tramiteCDA.getPersonaRENAPO().getDomicilios().get(0), domicilioSolicitud);	
			domicilioSolicitud.setSubdelegacion(sol.getSubdelegacion());
		} 
		
		if (tramiteCDA.getMotivosAclaracion() != null){
			log.debug("---CDA--- **********Si tiene motivos de aclaracion******** {} ");
			
			MotivoAclaracionVO motivoAclaracionVO = new MotivoAclaracionVO();
			motivoAclaracionVO.setMotivosAclaracionIMSS(new ArrayList<String>());
			motivoAclaracionVO.setMotivosAclaracionAfore(new ArrayList<String>());
			motivoAclaracionVO.setMotivosAclaracionInfonavit(new ArrayList<String>());		
			
			List<MotivoAclaracion> motivos = tramiteCDA.getMotivosAclaracion();
			
			for(MotivoAclaracion motivoAclaracion:motivos){
				
				if(motivoAclaracion.getInstitucion().getIdInstitucion() ==InstitucionEnum.IMSS.getId()){
					motivoAclaracionVO.getMotivosAclaracionIMSS().add(TiposAclaracionEnum.obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(motivoAclaracion.getIdMotivoAclaracion()).getClave());
				}
				
				if(motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.AFORE.getId()){
					motivoAclaracionVO.getMotivosAclaracionAfore().add(TiposAclaracionEnum.obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(motivoAclaracion.getIdMotivoAclaracion()).getClave());
				}
				if(motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.INFONAVIT.getId()){				
					motivoAclaracionVO.getMotivosAclaracionInfonavit().add(TiposAclaracionEnum.obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(motivoAclaracion.getIdMotivoAclaracion()).getClave());
					if (motivoAclaracion.getIdMotivoAclaracion() == MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO.getId()){
						motivoAclaracionVO.setCreditoDescontado(motivoAclaracion.getDetalleAclaracion());
					}
				}
				
				if(motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.OTRO.getId() && 
						motivoAclaracion.getIdMotivoAclaracion() == MotivoAclaracionEnum.OTRO.getId()){				
						motivoAclaracionVO.setOtro(TiposAclaracionEnum.obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(motivoAclaracion.getIdMotivoAclaracion()).getDependencia().toLowerCase());
						motivoAclaracionVO.setEspecificacion(motivoAclaracion.getDetalleAclaracion());
				}
				
				
			}		
			
			domicilioSolicitud.setMotivoAclaracionVO(motivoAclaracionVO);
			
		} else {
			log.debug("---CDA--- **********No tiene motivos de aclaracion******** {} ");
		}
		
		ConsultaSolicitudTramiteVO informacionConsulta = procesarInformacionConsulta(persona, sol, tramiteCDA);
		
		sol.setPersonaInteresada(tramiteCDA.getPersonaRENAPO());
		
		session.setAttribute("domicilioAclaracion", domicilioSolicitud);
		this.log.debug("---CDA--- Estado Solicitud {} Estado Tramite {} ",sol.getEstadoSolicitud().getIdEstadoSolicitud(),sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona());
			
		model.addAttribute("informacionConsulta", informacionConsulta);
		model.addAttribute("banderaContinuarTramite","false");
		session.setAttribute(CONTINUACION_TRAMITE_KEY, false);
		
		if(sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())
				&& sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.INICIADO.getCodigo())){
			model.addAttribute("banderaContinuarTramite","true");
			session.setAttribute(CONTINUACION_TRAMITE_KEY, true);
		}
		
		return VIEW_SEGUIMIENTO_TRAMITE;
	}
	
	
	private ConsultaSolicitudTramiteVO procesarInformacionConsulta(Fisica persona, Solicitud sol, TramiteCorreccionCurp tramiteCDA) {
		ConsultaSolicitudTramiteVO informacionConsulta = new ConsultaSolicitudTramiteVO();
		
		informacionConsulta.setCurp(persona.getCurp());
                TareaBandeja tareaBandeja = flujoTrabajoBusiness.getTareaActivaPorIdTramite(sol.getTramites().get(0).getTramiteId());
                if(tareaBandeja!=null && tareaBandeja.getInicioTramite()!=null){
                    informacionConsulta.setFechaSolicitud(tareaBandeja.getInicioTramite().getFechaSolicitud());
                }
		informacionConsulta.setFolio(sol.getNoFolioSolicitud());
		informacionConsulta.setNombre(persona.getNombreCompleto());
		informacionConsulta.setNss(tramiteCDA.getListaNSS() != null?tramiteCDA.getListaNSS().get(0) : "");
		informacionConsulta.setStatus(sol.getEstadoSolicitud() != null ? sol.getEstadoSolicitud().getDescripcion().trim() : "");
		informacionConsulta.setSubdelegacion(sol.getSubdelegacion() != null? sol.getSubdelegacion().getClave() + "-" + sol.getSubdelegacion().getDescripcion() : "");
		informacionConsulta.setEstatusDescarga(sol.getEstadoSolicitud() != null ?sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()) : false);
		informacionConsulta.setIdTramite(tramiteCDA.getTramiteId().toString());
		
		if(sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.CANCELADA.getCodigo())){
			informacionConsulta.setEstatusMot(true);
			informacionConsulta.setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA.getObservacionesSubdelegacion()));
		}
		
		if(sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo())
				&& (tramiteCDA.getObservacionesSubdelegacion() != null && !tramiteCDA.getObservacionesSubdelegacion().isEmpty() 
					&&  tramiteCDA.getObservacionesSubdelegacion().get(0) != null)) {
			informacionConsulta.setEstatusMot(true);
			//CAMBIO 30 DIC
			informacionConsulta.setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA.getObservacionesSubdelegacion()));
			informacionConsulta.setStatus(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo()));
		}
		
		return informacionConsulta;
	}
	
	private String obtenerUltimoMotivo(List<ObservacionesSubdelegacion> motivos){
		String motivo = "";
		if(motivos != null && !motivos.isEmpty()){
			motivo = motivos.get(motivos.size()-1).getDetalle()!= null?motivos.get(motivos.size()-1).getDetalle():" ";
		}
		return motivo.toUpperCase();
	}

	@RequestMapping(value = "/registroSolicitudCDAResponsable")
	public Object registroSolicitudCDAResponsable(Model model, HttpSession session) {

		DatosAseguradoVO datosAsegurado = new DatosAseguradoVO();

		model.addAttribute("datosAsegurado", datosAsegurado);
		return VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
	}

	@RequestMapping(value = "/init/{curp}", method = RequestMethod.GET)
	public String altaInit(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String curp) {

		this.log.info("---CDA--- Datos limpiados de la sesion: OK, CURP: " + curp);

		try {

			List<Solicitud> solicitudes = registroSolicitudCorreccionDatosAseguradoBusiness
					.obtenerSolicitudesPorCurp(curp);
			this.log.info("---CDA--- IdSolicitud: " + solicitudes.get(0).getSolicitudId());
		} catch (CorreccionDatosAseguradoException ex) {
			this.log.error("---CDA--- Error al consultar la solicitud " + ex.getMessage());
		}

		return VIEW_INICIAR_REGISTRO;
	}

	@RequestMapping(value = "/validarAccesoTramite/{curp}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> validarAccesotramite(Model model, HttpSession session,
			@PathVariable String curp) {
		this.comunLimpiarDatos(model, session);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("curp", curp);
		result.put("error", false);

		return result;
	}

	@RequestMapping(value = "/comunes/limpiarDatos")
	public @ResponseBody Map<String, ? extends Object> comunLimpiarDatos(Model model, HttpSession session) {

		model.addAttribute("tramiteSeguro", "");

		session.removeAttribute("tramiteSeguro");
		session.removeAttribute(SOLICITUD);
		session.removeAttribute(PERSONA_SESSION_KEY);
		session.removeAttribute(DOMICILIO_ACLARACION_KEY);
		session.removeAttribute(CONTINUACION_TRAMITE_KEY);

		this.log.info("---CDA--- Datos limpiados de la sesion: OK");
		return null;
	}

	@RequestMapping(value = "/datosAdicionalesHistoriaLaboral")
	public String consultaDatosAdicionalesHistoriaLaboral(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
		if (session.getAttribute("datosAdicionalesHistoriaLaboral") != null) {
			datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
					.getAttribute("datosAdicionalesHistoriaLaboral");
		} else {
			datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
		}
		model.addAttribute("datosAdicionalesHistoriaLaboral", datosAdicionalesHistoriaLaboral);
		return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
	}

	@RequestMapping(value = "/validar/datosAdicionalesHistoriaLaboral", method = RequestMethod.POST)
	public String consultaDatosAdicionalesHistoriaLaboral(
			@ModelAttribute("datosAdicionalesHistoriaLaboral") DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral,
			BindingResult result, Model model, HttpSession session,
			@ModelAttribute("idPersona") Long idPersona) {

		session.setAttribute("datosAdicionalesHistoriaLaboral", datosAdicionalesHistoriaLaboral);

		log.debug("---CDA---  datosAdicionalesHistoriaLaboral {}", datosAdicionalesHistoriaLaboral);
		datosAdicionalesHistoriaLaboralValidator.validate(datosAdicionalesHistoriaLaboral, result);
		if (result.hasErrors()) {
			this.log.warn("---CDA--- Errores de captura");
			// model.addAttribute("datosAdicionalesHistoriaLaboral",datosAdicionalesHistoriaLaboral);
			return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
		}
		
		Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);
		Fisica personaSesion = (Fisica)session.getAttribute(PERSONA_SESSION_KEY);
		
		DatosAdicionalesHistoriaLaboral contacto = datosAdicionalesHistoriaLaboral;
		TelefonoFijo fijo = new TelefonoFijo();
		TelefonoMovil movil = new TelefonoMovil();
		String observaciones="";
		if(contacto!=null) {
    		fijo.setNumero(contacto.getDatosContacto().getTelefonoFijo());
    		List<MedioContacto> list = eliminaDatosContacto(personaSesion.getMediosContacto());
    		personaSesion.setMediosContacto(list);
    		personaSesion.getMediosContacto().add(fijo);
    		movil.setNumero(contacto.getDatosContacto().getTelefonoCelular());
    		personaSesion.getMediosContacto().add(movil);
                observaciones=contacto.getObservaciones();
    	}
		
		((TramiteCorreccionCurp)solicitud.getTramites().get(0)).setPersonaRENAPO(personaSesion);
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("datosHistoriaLaboral");
		DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
		datosHistoriaLaboral.setHistoriaLaboralGrid(informacionHistoriaLaboral.getHistoriaLaboralGrid());
		DomicilioCorto motivosAclaracion = (DomicilioCorto) session.getAttribute("domicilioAclaracion");
		if(motivosAclaracion.getMotivoAclaracionVO() != null){
			if(motivosAclaracion.getMotivoAclaracionVO().getCreditoDescontado() != null){
				String cd = motivosAclaracion.getMotivoAclaracionVO().getCreditoDescontado();
				motivosAclaracion.getMotivoAclaracionVO().setCreditoDescontado(cd.toUpperCase());
			}
			if( motivosAclaracion.getMotivoAclaracionVO().getEspecificacion() != null){
				String otro = motivosAclaracion.getMotivoAclaracionVO().getEspecificacion();
				motivosAclaracion.getMotivoAclaracionVO().setEspecificacion(otro.toUpperCase());
			}
		}
		session.setAttribute("domicilioAclaracion", motivosAclaracion);
		
		solicitud.setPersonaInteresadaSolicitud(new PersonaInteresadaSolicitud());
		solicitud.getPersonaInteresadaSolicitud().setPersona(new Persona());
		solicitud.getPersonaInteresadaSolicitud().setTipoPersonaInteresadaSol(new TipoPerInteresadaSol());
		solicitud.getPersonaInteresadaSolicitud().getTipoPersonaInteresadaSol()
			.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		
		solicitud = this.actualizarSolicitud(solicitud, datosHistoriaLaboral, datosAdicionalesHistoriaLaboral, motivosAclaracion);
                ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).setObservacion(observaciones);
                log.debug("---CDA--- La solicitud tiene tramite(s): ", solicitud.getTramites());
		//TODO unicamente para facilitar el manejo en pantalla 
		personaSesion.setTelefonoFijo(fijo);
		personaSesion.setTelefonoMovil(movil);
		
		session.setAttribute(SOLICITUD, solicitud);
		session.setAttribute(PERSONA_SESSION_KEY, personaSesion);
		if(((DomicilioCorto) session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionIMSS() != null){
			
			model.addAttribute("motivosIMSS",TiposAclaracionEnum.obtenerMensajesPorClaves(TiposAclaracionEnum.Dependencia.IMSS, ((DomicilioCorto)session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionIMSS()));
		} 
		if(((DomicilioCorto) session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionInfonavit() != null){
			model.addAttribute("motivosINFONAVIT",TiposAclaracionEnum.obtenerMensajesPorClaves(TiposAclaracionEnum.Dependencia.INFONAVIT, ((DomicilioCorto)session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionInfonavit()));
		} 
		if(((DomicilioCorto) session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionAfore() != null){
			model.addAttribute("motivosAFORE",TiposAclaracionEnum.obtenerMensajesPorClaves(TiposAclaracionEnum.Dependencia.AFORE, ((DomicilioCorto)session.getAttribute("domicilioAclaracion")).getMotivoAclaracionVO().getMotivosAclaracionAfore()));
		}
		return VIEW_CONFIRMAR_SOLICITUD;
	}


	
	public List<MedioContacto> eliminaDatosContacto(List<MedioContacto> mediosContacto) {		
		CollectionUtils.filter(mediosContacto, new Predicate() {
			@Override
			public boolean evaluate(Object c) {
				return (!(c instanceof TelefonoFijo || c instanceof TelefonoMovil));
			}
		});
				
		return mediosContacto;
	}
	
	@RequestMapping(value = "/datosHistoriaLaboral")
	public String consultaDatosHistoriaLaboral(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		DatosHistoriaLaboralVO datosHistoriaLaboral;
                
		if (session.getAttribute("datosHistoriaLaboral") == null) {
			session.setAttribute("datosHistoriaLaboral", (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral"));
		}
                Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
                datosHistoriaLaboral = (DatosHistoriaLaboralVO)session.getAttribute("datosHistoriaLaboral");
                List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>list =((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getDocumentosProbatorios();
                if(list!=null && !list.isEmpty()){
//                    log.debug("--CDA-- DOCUMENTOPROBATORIOS Aseg: {}", list);
                    List<DocumentoProbatorio> documentoProbatorioList =new ArrayList<DocumentoProbatorio>();
                    for(mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio:list){
                        log.debug("--CDA--tipo DOCUMENTOPROBATORIOS Aseg: {}", documentosprobatorio.getDocumentoPorTipo());
                        if(documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()==TipoDocumentoProbatorioEnum.ACTAS.getId()
                                || documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()==TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()){
                            DocumentoProbatorio documento = new DocumentoProbatorio();
                            documento.setIdDocBoveda(documentosprobatorio.getBovedaDocId());
                            documento.setNombre(documentosprobatorio.getNomNombreDocumento());
                            documento.setCveIdDocumento(documentosprobatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
                            documento.setDesDocumento(documentosprobatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
                            documento.setTipoDocumento(documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
                            documento.setIdDocumentoPorTipo(documentosprobatorio.getDocumentoPorTipo().getIdDocumentoPorTipo());
                            documentoProbatorioList.add(documento);
                        }
                    }
                    datosHistoriaLaboral.setDocumentoProbatorioList(documentoProbatorioList);
                }
                log.debug("--CDA-- DOCUMENTOPROBATORIOS Aseg: {}", datosHistoriaLaboral.getDocumentoProbatorioList());
                model.addAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
                
		Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session.getAttribute(PERSONA_SESSION_KEY) : null;
		
		this.log.error("---CDA--- idPersona fisica en sesion: {}", fisica!= null ? fisica.getIdPersona(): "sin persona en sesion");
		
		cargarPantallaDatosHistoriaLaboral(model, session);
		return VIEW_DATOS_HISTORIA_LABORAL;
	}

	private void cargarPantallaDatosHistoriaLaboral(Model model,
			HttpSession session){
		model.addAttribute("datosHistoriaLaboral",(DatosHistoriaLaboralVO) session
						.getAttribute("datosHistoriaLaboral"));

		List<DoctoReqTramite> documentos = documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramite(
				TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo().longValue());
		
		//agrupacion por tipo
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
		for (DoctoReqTramite doctoReqTramite : documentos) {
			Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
			TipoDocumentoProbatorio currMapKey = null;
			if(doctos.keySet() != null && !doctos.keySet().isEmpty()){
				for (TipoDocumentoProbatorio key : doctos.keySet()) {
					if(tipoDocto.equals(key.getIdTipoDocumentoProbatorio())){
						currMapKey = key;
						log.debug("--CDA-- se encontro la llave: {}",key.getIdTipoDocumentoProbatorio());
					}
				}
			}
				
			if(currMapKey == null){
				currMapKey = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio();
				doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
				log.debug("--CDA-- creando llave para tipo: {}",currMapKey.getIdTipoDocumentoProbatorio());
			}
			DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
			docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
			log.debug("--CDA-- TIPO DE DOCUMENTO: {}", docProbatorioVo.getTipoDocumento().toString());
			docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
			docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
			docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
			log.debug("--CDA-- ID DE DOCUMENTO PROBATORIO POR TIPO: {}", docProbatorioVo.getIdDocumentoPorTipo());
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId() 
					|| (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
							&& docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP.getId())
					|| docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()){
				log.debug("--CDA-- se agrega documento: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctos.get(currMapKey).add(docProbatorioVo);
			}
		}
		
		List<TipoDocumentoProbatorio> keys = new ArrayList<TipoDocumentoProbatorio>();
		for (TipoDocumentoProbatorio key : doctos.keySet()) {
			if(doctos.get(key).isEmpty() || key.getIdTipoDocumentoProbatorio()==11){
				keys.add(key);
			}
		}
		for (TipoDocumentoProbatorio tipoDocumentoProbatorio : keys) {
			doctos.remove(tipoDocumentoProbatorio);
		}
		
		model.addAttribute("documentosProbatoriosVo",doctos);
		// Valor
		model.addAttribute("documentoNSSClave", TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
	}
	

	@RequestMapping(value = "/validar/datosHistoriaLaboral", method = RequestMethod.POST)
	public String consultaDatosHistoriaLaboral(
			@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboral,BindingResult result, Model model, HttpSession session) throws DocumentoException {		
		log.debug("---CDA--- DatosHistoriaLaboral {}", datosHistoriaLaboral);
                log.debug("---CDA--- DatosHistoriaLaboral.entidad {}", datosHistoriaLaboral.getHistoriaLaboralForm().getClaveEntidad());
		datosHistoriaLaboralValidator.validate(datosHistoriaLaboral, result);
		if(datosHistoriaLaboral.getDocumentoProbatorioList() != null &&
				!datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()){
			documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), result);
		}else{
			result.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
		}
		
		DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
		if (session.getAttribute("datosAdicionalesHistoriaLaboral") != null) {
			datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
					.getAttribute("datosAdicionalesHistoriaLaboral");
		} else {
			datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
		}
		session.setAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
		if (result.hasErrors() || result.hasFieldErrors("documentoProbatorioList")) {
			this.log.warn("---CDA--- Errores de captura");
			cargarPantallaDatosHistoriaLaboral(model, session);
			model.addAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
			return VIEW_DATOS_HISTORIA_LABORAL;
		}
		log.debug("******************CDA************************* SE AGREGAN AL MODELO DatosHistoriaLaboral {}", datosAdicionalesHistoriaLaboral.getObservaciones());
		model.addAttribute("datosAdicionalesHistoriaLaboral",datosAdicionalesHistoriaLaboral);
		return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
	}
	
	@RequestMapping(value = "/informacionHistoriaLaboral")
	public String consultainformacionHistoriaLaboral(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		DatosHistoriaLaboralVO informacionHistoriaLaboral;
		if (session.getAttribute("informacionHistoriaLaboral") == null) {
			informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
			session.setAttribute("informacionHistoriaLaboral", informacionHistoriaLaboral);
		}
		model.addAttribute("informacionHistoriaLaboral",(DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral"));
		return VIEW_INFORMACION_HISTORIA_LABORAL;
	}
	
	@RequestMapping(value = "/validar/informacionHistoriaLaboral", method = RequestMethod.POST)
	public String validateInformacionHistoriaLaboral(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("informacionHistoriaLaboral") DatosHistoriaLaboralVO informacionHistoriaLaboral,BindingResult result, Model model) {
		
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");

                model.addAttribute("informacionHistoriaLaboral",datosHistoriaLaboral);
                Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
                TramiteCorreccionCurp tramite=(TramiteCorreccionCurp)sol.getTramites().get(0);
                tramite.setDatosLaborales(registroCorreccionCurpUtil.cargarDatosLaborales(datosHistoriaLaboral.getHistoriaLaboralGrid()));
                try {
			solicitudBusiness.actualizarXmlTramite(tramite);
		} catch (TramiteNoEncontradoException e) {
			log.error("---CDA--- No se encontro el Tramite {}",e);
		} catch (IllegalArgumentException e) {
			log.error("---CDA--- Ocurrio un error al actualizar el tramite {}",e);
		}
		return consultaDatosHistoriaLaboral(model, session, request, response);
	}
	
	
	@RequestMapping(value = "/validar/infHistoriaLaboral", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validateGridInformacion(
			@RequestBody DatosHistoriaLaboralVO datosHistoriaLaboralVO, HttpServletResponse response) {
		final Errors errors = new BindException(datosHistoriaLaboralVO, "model");
		
		if(!(datosHistoriaLaboralVO.getHistoriaLaboralGrid()!=null && !datosHistoriaLaboralVO.getHistoriaLaboralGrid().isEmpty())){
			errors.rejectValue("historiaLaboralForm","field.historiaLaboral.listaVacia");
		} 
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
		}
		return result;
	}
	
	@RequestMapping( value = "/uploadify")
	public ResponseEntity<Map<String, Object>> uploadBytes(
			@RequestParam("fileData") MultipartFile file, 
			@RequestParam("idDocPorTipo") String idDocporTipo, 
			HttpSession ses,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
		if( file.getSize() <= TAMANIO_MAXIMO_ARCHIVO){		
			Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
			log.debug("---CDA--- #####Subiendo Archivo######");
			String[] n = file.getOriginalFilename().split("\\.");
			Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
					.getAttribute(PERSONA_SESSION_KEY) : null;
			if (fisica != null) {
				String idDocBoveda = null;
				try{
					String nombreCompleto = idDocporTipo + "_" + file.getOriginalFilename();
					idDocBoveda = bovedaBusiness.subirDocumento(file.getBytes(), solicitud, nombreCompleto, n[n.length - 1] , file.getContentType());
				}catch (BovedaCDAException bce){
					log.error(bce.getSituacion());					
					result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
					result.put("__situacion_", bce.getSituacion());
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.TEXT_HTML);
					return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);			
				}catch(Exception e){
					log.error("---CDA--- Error al subir el documento {}",e);
					result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.TEXT_HTML);
					return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
				}
	
				if (idDocBoveda != null) {
					log.debug("---CDA--- documento insertado en boveda {}",idDocBoveda);
					result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
					result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
				} else { 
					log.debug("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
					result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
				}
			} else {
				log.warn("---CDA--- El usuario no esta logeado idPersona null");
				result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
			}
		}else{
			result.put(STATUS_ERROR_UPLOAD, "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
		
	}	

	@RequestMapping(value = "/validar/NSS/{nssLength}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validateNSS(@RequestBody NSSVO nssvo,
			HttpServletResponse response, @PathVariable Integer nssLength,Model model, @ModelAttribute("idPersona") Long idPersona) {
		final Errors errors = new BindException(nssvo, "model");

		log.debug("---CDA--- ############Validar NSS#####################");
		log.debug("---CDA--- NSS recibido : {} ", nssvo);
		log.debug("---CDA--- NSS list length : {} ", nssLength);
		
		Map<String, Object> result = new HashMap<String, Object>();

		nSSValidator.validateLengthListaNSS(errors, nssLength);
		
		if(!errors.hasErrors()){
			nSSValidator.validate(nssvo, errors);
		}
		
		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
		}
		
		log.debug("---CDA--- mapa resultado agregar NSS {}", result.toString());
		return result;
	}

	@RequestMapping(value = "/validar/historiaLaboral", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validateHistoriaLaboral(
			@RequestBody HistoriaLaboralVO historiaLaboralVO, HttpServletResponse response,HttpSession session) {
		final Errors errors = new BindException(historiaLaboralVO, "model");

		log.debug("---CDA--- ############Validar Historia Laboral#####################");
		log.debug("---CDA--- Historia Laboral recibido : {} ", historiaLaboralVO);

		historiaLaboralValidator.validate(historiaLaboralVO, errors);

		Map<String, Object> result = new HashMap<String, Object>();

		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
		}else{
                    DatosHistoriaLaboralVO datosHistorialLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
                    if(datosHistorialLaboral.getHistoriaLaboralGrid()==null){
                       datosHistorialLaboral.setHistoriaLaboralGrid(new ArrayList<HistoriaLaboralVO>());
                    }
                    datosHistorialLaboral.getHistoriaLaboralGrid().add(historiaLaboralVO);
                }
                
		log.debug("---CDA--- validar historiaLaboral {}",result.toString());
		return result;
	}

	@RequestMapping(value = "/descargarComprobante", method = RequestMethod.GET)
	public void getComprobanteSolicitud(Model model,
			final HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		Solicitud sol = (Solicitud)session.getAttribute(SOLICITUD);
		DomicilioCorto motivoAclaracion = (DomicilioCorto)session.getAttribute("domicilioAclaracion");
		DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO)session.getAttribute("informacionHistoriaLaboral");
		try {
			byte[] docto = null;
			try{
			docto = bovedaBusiness.recuperarDocumento(sol,TipoDocumentoCDAEnum.ACUSE,recuperarIdDocumento(sol,TipoDocumentoCDAEnum.ACUSE));
			}catch (BovedaCDAException bce){
				log.error(bce.getSituacion());			
			}
			if (docto == null) {
				log.info("---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID",sol.getSolicitudId());
				docto = (byte[]) obtenerComprobanteSolicitud(sol,informacionHistoriaLaboral,motivoAclaracion);		
				try {
					String idDocumento = bovedaBusiness.subirDocumento(docto, sol,TipoDocumentoCDAEnum.ACUSE);
					agregarIdDocumento(sol, idDocumento,TipoDocumentoCDAEnum.ACUSE);
				}catch (BovedaCDAException bce){
					log.error(bce.getSituacion(), bce);				
				} catch (SolicitudNoEncontradaException e) {
					log.error("---CDA--- Error al actualizar XML  {}",e);
				} catch (TramiteNoEncontradoException e) {
					log.error("---CDA--- Error al actualizar XML  {}",e);
				} catch (IllegalArgumentException e) {
					log.error("---CDA--- Error al actualizar XML  {}",e);
				}
			}

			model.addAttribute("solicitud", sol);
			if (docto != null) {
				log.debug("---CDA--- El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition",
						"attachment;filename=\"" + sol.getNoFolioSolicitud() + ".pdf\"");
				response.setContentLength(docto.length);
				response.getOutputStream().write(docto);
				response.flushBuffer();

			} else {
				this.log.debug("---CDA--- No se pudo obtener el acuse de la solicitud con folio {}", sol.getNoFolioSolicitud());
			}
		} catch (IOException e) {
			this.log.error(
					"---CDA--- Error al generar el comprobante de asignacion de NSS -> ",
					e);
		} catch (SolicitudNoEncontradaException e1) {
			this.log.error(
					"---CDA--- Error al generar el comprobante -> ",
					e1);
		}
		this.log.debug("---CDA--- Termina generacion de documentos de solicitud");
	}

	private Object obtenerComprobanteSolicitud(Solicitud solicitud, DatosHistoriaLaboralVO informacionHistoriaLaboral, DomicilioCorto motivoAclaracion){
		Map<String, Object> parametros1 = new HashMap<String, Object>();
		parametros1 = registroCorreccionCurpUtil.obtenerParametros(solicitud);
		
		Map<String, Object> parametros2 = new HashMap<String, Object>();
		parametros2 = registroCorreccionCurpUtil.obtenerParametrosComplemento(solicitud, informacionHistoriaLaboral, motivoAclaracion);
		log.debug("---CDA--- --------Se genera reporte de idSolicitud: --------------- "+solicitud.getSolicitudId());
		byte[] repo = manejadorReportesBusiness.ejecutaReporte(parametros1,parametros2);
		log.debug("---CDA--- -----FIRMA ELECTRONICA----: " + solicitud.getFirmaElectronica());
		return repo;
	}

	@RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
	public String cancelarSolicitud(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws SolicitudNoValidaException {

		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
		log.error("---CDA--- ---------Datos de la solicitud---------------------"+solicitud);
		if(solicitud != null){
			 try {
				 log.debug("---CDA--- ---------------Entra metodo cancelar Solicitud---------------------"+solicitud);
				 registroSolicitudCorreccionDatosAseguradoBusiness.cancelarSolicitud(solicitud);
				 log.debug("---CDA--- ---------------Cancelacion Exitosa---------------------" + solicitud);
			 }catch(SolicitudNoEncontradaException e) {
			   this.log.error("---CDA--- -----------------SolicitudNoEncontradaException-------------------------", e);
			}catch(TramiteNoEncontradoException ex){
				this.log.error("---CDA--- -----------------TramiteNoEncontradoException-------------------------", ex);
			}
		}
		
		return inicioCorreccionCurp(model, session);
	}
	
	@RequestMapping(value = "/concluirSolicitud", method = RequestMethod.GET)
	public String concluirSolicitud(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws SolicitudNoValidaException {
		log.debug("---CDA--- ******Entra al metodo de concluirSolicitud ************************");
		return inicioCorreccionCurp(model, session);		
	}
	
	
	@RequestMapping("/obtenerDocumento/{idPersona}/{folio}/{nombreArchivo}/{idDocBoveda}")		
	public Object load(@PathVariable String idPersona,
          @PathVariable String folio,
          @PathVariable String nombreArchivo,
          @PathVariable String idDocBoveda, 
          HttpServletResponse response) {
		mx.gob.imss.cit.cda.web.vo.Documento input = new mx.gob.imss.cit.cda.web.vo.Documento();
		input.setNombreArchivo(nombreArchivo);
		input.setFolio(folio);
		input.setIdPersona(idPersona);
		input.setIdDocBoveda(idDocBoveda);
    try{
    	byte documento[]= obtenerDocumentoBoveda(input);
    	
      if( documento!= null ){
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(input.getNombreArchivo(), "UTF-8"));
        response.setContentLength((int) documento.length );
        response.getOutputStream().write( documento );      

        return null;
      }else{
    	  Map<String, Object> errores = new HashMap<String, Object>();
    	  	errores.put("errorBoveda", "Documento Nulo");
    	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
    	  return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);
      }
    }catch(BovedaCDAException bce){
    	Map<String, Object> errores = new HashMap<String, Object>();
  	  	errores.put("errorBoveda", bce.getSituacion());
  	  	errores.put("errorNegocio", bce.getMensajeError());
  	  	log.error("---CDA--- errores {}",errores);
    	return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);    	
    }catch( Exception ioe){
    	Map<String, Object> errores = new HashMap<String, Object>();
  	  	errores.put("errorBoveda", ioe.getMessage());
  	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
  	  log.error("---CDA--- errores {}",errores);
    	return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);
    }
	}	
	
	@RequestMapping("/generarCertificacion.do")
	public Object load(@ModelAttribute ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO, HttpServletResponse response) {

		log.debug("---CDA--- Generando Certificacion Id Tramite  {}", consultaSolicitudTramiteVO.getIdTramite());

		

		try {
			
			byte[] documento = obtenerCertificacionSolicitud(consultaSolicitudTramiteVO);

			if (documento != null) {
				response.addHeader("Content-Disposition", "attachment; filename=" + consultaSolicitudTramiteVO.getFolio() + ".pdf");
				response.setContentLength(documento.length);
				response.setContentType("application/pdf");
				response.getOutputStream().write(documento);

				return null;
			} else {
				Map<String, Object> errores = new HashMap<String, Object>();
	    	  	errores.put("errorBoveda", "Documento Nulo");
	    	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
				return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);
			}
		} catch (BovedaCDAException bce){
			Map<String, Object> errores = new HashMap<String, Object>();
	  	  	errores.put("errorBoveda", bce.getSituacion());
	  	  	errores.put("errorNegocio", bce.getMensajeError());
	    	return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);
			
		}catch (Exception ioe) {
			Map<String, Object> errores = new HashMap<String, Object>();
	  	  	errores.put("errorBoveda", ioe.getMessage());
	  	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
	    	return new ModelAndView(VIEW_RECURSO_NO_DISPONIBLE,errores);
		}
	}
	
	private void enviarCorreoCorreccionDatosPorCurp(Solicitud solicitud, byte[] adjuntos,String curpResponsable, String url) {
		CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
		
		if(((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico() != null && 
				((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo() != null){
			log.debug("---CDA--- Correo Derechohabiente: " + ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo());
			correoElectronicoDTO.setCorreoPara(new String[1]);
			correoElectronicoDTO.getCorreoPara()[0] = ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo();
			correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
			correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoCDA(solicitud, url));
			log.debug("---CDA--- Contenido Correo Derechohabiente: " + (correoElectronicoDTO.getCuerpoCorreo()));
			if(adjuntos != null){
				Map<String,byte[]> adjuntosMap = new HashMap<String, byte[]>();
				//TODO Cambiar nombre en la llave del mapa con el nombre del documento que sera adjuntado.
				adjuntosMap.put("Documento.pdf", adjuntos);
				correoElectronicoDTO.setAdjuntos(adjuntosMap);
			}
			
			try {
				envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO,EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
			} catch (Exception e) {
				log.debug("---CDA--- Error al enviar correo " , e);
			}
		}
		
		if(StringUtils.isBlank(curpResponsable) ){
			
			try{
				List<Fisica> autorizadores = responsablesDelegacionRemote.consultarAutorizadoresDelegacion(solicitud.getSubdelegacion().getDelegacion().getId().intValue(), solicitud.getSubdelegacion().getId().intValue());
				for(Fisica autorizador:autorizadores){
					correoElectronicoDTO = new CorreoElectronicoDTO();
					
					if (autorizador.getCorreoElectronico() != null && autorizador.getCorreoElectronico().getCorreo() != null){
						correoElectronicoDTO.setCorreoPara(new String[1]);
						correoElectronicoDTO.getCorreoPara()[0] = autorizador.getCorreoElectronico().getCorreo();
						log.debug("---CDA--- correoAutorizador: " + autorizador.getCorreoElectronico().getCorreo());
						correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
						correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoAutorizador(solicitud,autorizador));
						log.debug("---CDA--- contenidoCorreoAutorizador: " + correoElectronicoDTO.getCuerpoCorreo());
						try {
							envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO,EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
						} catch (Exception e) {
							log.debug("---CDA--- Error al enviar correo Autorizador" , e);
						}
					}
				}
			}catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				log.debug("---CDA--- Error al obtener autorizadores" , e);				
			}
			
		}else{
			correoElectronicoDTO = new CorreoElectronicoDTO();
			
			try{
				mx.gob.imss.ctirss.delta.model.Usuario responsable = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curpResponsable);
				
				if (responsable.getFisica().getCorreoElectronico() != null && responsable.getFisica().getCorreoElectronico().getCorreo() != null){
					correoElectronicoDTO.setCorreoPara(new String[1]);
					correoElectronicoDTO.getCorreoPara()[0] = responsable.getFisica().getCorreoElectronico().getCorreo();
					log.debug("---CDA--- correoResponsable: " + responsable.getFisica().getCorreoElectronico().getCorreo());
					correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
					correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoResponsable(solicitud, responsable));
					log.debug("---CDA--- contenidoCorreoResponsable: " + correoElectronicoDTO.getCuerpoCorreo());
					try {
						envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO,EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
					} catch (Exception e) {
						log.debug("---CDA--- Error al enviar correo Responsable" , e);
					}
				}
			} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				// TODO Auto-generated catch block
				log.debug("---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",e);
			}
		}		
	}
	
	private byte[] obtenerDocumentoBoveda(mx.gob.imss.cit.cda.web.vo.Documento documentoEnviado) throws BovedaCDAException,Exception{
		log.debug("init obtener documento");
		byte[] documento = null;
		try {
			mx.gob.imss.cit.clienteServiciosComunes.model.Documento doc = new mx.gob.imss.cit.clienteServiciosComunes.model.Documento();
			doc.setNombreArchivo(documentoEnviado.getNombreArchivo());
			if(StringUtils.isBlank(documentoEnviado.getExtension()) || !Arrays.asList(EXTENCIONES_VALIDAS).contains(documentoEnviado.getExtension().toLowerCase())){
				String[] n = doc.getNombreArchivo().split("\\.");
				documentoEnviado.setExtension(n[n.length - 1]);
			}
			doc.setExtencion(documentoEnviado.getExtension());
			log.debug("Nombre {}",doc.getNombreArchivo());
			log.debug("Extencion {}",doc.getExtencion());
			DocumentReq documentReq = new DocumentReq();
			Tramite tramite = new Tramite();
			tramite.setFolioTramite(documentoEnviado.getFolio());
			log.debug("Folio Tramite {}",tramite.getFolioTramite());
			Usuario usuario = new Usuario();
			usuario.setIdUsr(documentoEnviado.getIdPersona());
			log.debug("Usuario {}",usuario.getIdUsr());
			usuario.setOwner(false);
			usuario.setTipoIdUsr(TIPO_ID_USR);
			documentReq.setDocumento(doc);
			documentReq.setTramite(tramite);
			documentReq.setUsuario(usuario);
			
			Solicitud sol = new Solicitud();
			sol.setNoFolioSolicitud(documentoEnviado.getFolio());
			sol.setSolicitudId(Long.parseLong(documentoEnviado.getIdPersona()));			
			String idDocumento =documentoEnviado.getIdDocBoveda().equalsIgnoreCase(NULL)?null:documentoEnviado.getIdDocBoveda();
			try{
			documento = bovedaBusiness.recuperarDocumento(sol, idDocumento, documentoEnviado.getNombreArchivo());
			//documento = bovedaBusiness.recuperarDocumentoDropbox(sol.getNoFolioSolicitud(), idDocumento, documentoEnviado.getNombreArchivo());
			}catch (BovedaCDAException bce){
				log.error("---CDA--- ocurrio un error {}",bce.getSituacion(), bce);
				throw bce;
			}
			
			return documento;
		} catch (Exception e) {
			log.error("---CDA--- Error: {}", e);			
			throw e;
		}
	}
	
	private byte[] obtenerCertificacionSolicitud(ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO) throws BovedaCDAException,Exception{
		log.debug("---CDA--- Generando Solicitud Tramite {}",consultaSolicitudTramiteVO.getIdTramite());
		byte[] documentoCertificado = null;
		try {
			Solicitud sol = solicitudBusiness.consultarPorIdTramite(Long.parseLong(consultaSolicitudTramiteVO.getIdTramite()));
			log.debug("---CDA--- Estado Solicitud {} - {}",sol.getEstadoSolicitud().getDescripcion(),sol.getEstadoSolicitud().getDescripcion());						
            try{
            	documentoCertificado = bovedaBusiness.recuperarDocumento(sol,TipoDocumentoCDAEnum.CERTIFICADO,recuperarIdDocumento(sol,TipoDocumentoCDAEnum.CERTIFICADO));
			}catch (BovedaCDAException bce){
				log.error(bce.getSituacion(), bce);			
			}
			if(documentoCertificado == null){				
				log.info("---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID",sol.getSolicitudId());
			Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas = new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();
			
			parametrosCuentas.add(cuentaIndividualBusiness.consultarMovimientosCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
			
			parametrosCuentas.add(cuentaIndividualBusiness.consultarUltimoMovimientoCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
			
			FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness.selloDigitalCertificacion(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO(),sol, parametrosCuentas);
			sol.setFirmaElectronica(firma);
			
			documentoCertificado= manejadorReportesBusiness.ejecutaReporteCertificacion(registroCorreccionCurpUtil.generarParametrosReporte(sol,flujoTrabajoBusiness.obtenerParticipantesUltimaTareaByTramite(Long.parseLong(consultaSolicitudTramiteVO.getIdTramite())), parametrosCuentas));
			try {
				String idDocumento = bovedaBusiness.subirDocumento(documentoCertificado, sol,TipoDocumentoCDAEnum.CERTIFICADO);
				agregarIdDocumento(sol, idDocumento, TipoDocumentoCDAEnum.CERTIFICADO);
			}catch (BovedaCDAException bce){
				log.error(bce.getSituacion(), bce);	
				throw bce;
			} catch (SolicitudNoEncontradaException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			} catch (TramiteNoEncontradoException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			} catch (IllegalArgumentException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			}
			}
			return documentoCertificado;
		} catch (Exception e) {
			log.error("Error: {}", e);			
			throw e;
		}
	}
	
private void  agregarIdDocumento(Solicitud sol ,String idDocumento,TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, IllegalArgumentException{
		
		TramiteCorreccionCurp tramite = null;
		if(sol.getTramites() != null && idDocumento != null){
			log.debug("---CDA--- el tramite no es null {}", sol.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
			Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(tramite.getTramiteId());
			
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
			switch (tipoDocumentoCDAEnum) {
			case ACUSE:
				tramite.setIdDocumentoAcuse(idDocumento);
				break;
			case CERTIFICADO:
				tramite.setIdDocumentoCertificacion(idDocumento);
				break;
			}	        
			solicitudBusiness.actualizarXmlTramite(tramite);
		}else{
			log.warn("---CDA--- El idDocumento es nulo {}",idDocumento==null);
			log.warn("---CDA--- la solicitud no tiene tramites {}",sol.getTramites()==null);
		}
	}

private String recuperarIdDocumento(Solicitud sol, TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws SolicitudNoEncontradaException{
	TramiteCorreccionCurp tramite = null;
	if(sol.getTramites() != null){
		log.debug("---CDA--- el tramite no es null {}", sol.getTramites().get(0).getTramiteId() );
		tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
		Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(tramite.getTramiteId());
		
		tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
	}	
	
	log.debug("---CDA--- ID DOCUMENTO {}", tramite != null ?(tipoDocumentoCDAEnum.equals(TipoDocumentoCDAEnum.ACUSE)?tramite.getIdDocumentoAcuse():tramite.getIdDocumentoCertificacion()):null);
	
	return tramite != null ?(tipoDocumentoCDAEnum.equals(TipoDocumentoCDAEnum.ACUSE)?tramite.getIdDocumentoAcuse():tramite.getIdDocumentoCertificacion()):null;
}

@RequestMapping(value = "/eliminarDocumentoBoveda", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoBoveda (@RequestBody Map<String, String> params, HttpSession session){
	    
	    log.debug("---CDA--- Llega a RSCDC con el parametro: " + params.get("idDocBoveda"));
	    String confirmacion = null;
	    Map<String, String> respuesta = new HashMap<String, String>();
	    try{
	        confirmacion = bovedaBusiness.eliminarDocumento(params.get("idDocBoveda"));
	        if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo())) {
	            respuesta.put("success", "El documento fue eliminado con \u00e9xito <br>"+confirmacion);
	            
	            DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session.getAttribute(params.get("view")); 
	    		int index = Integer.parseInt(params.get("idRow"));
	    		if (datos != null) {
	    			if(params.get("type").equals("listadoDocumentosGrid")){
	    				if(datos.getDocumentoProbatorioList() != null && !datos.getDocumentoProbatorioList().isEmpty()){
	    					datos.getDocumentoProbatorioList().remove(index);			
	    					session.setAttribute(params.get("view"), datos);
	    				}
	    			}		
	    		}
	        } else {
	            respuesta.put("error", confirmacion);      
	        }
        }catch (BovedaCDAException bce){
            log.error("---CDA-- Ocurrio un error {}",bce.getSituacion());
            respuesta.put("error", bce.getMensajeError()); 
        }	  
	   		
	    return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.OK);
	}

	@RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> actualizarLista( 
		HttpSession session, @RequestBody Map<String, String> params) { 
	
		DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session.getAttribute(params.get("view")); 
		int index = Integer.parseInt(params.get("idRow"));
		
		if (datos != null) {
			if(params.get("type").equals("datosHistoriaLaboralGrid")){
				if (datos.getHistoriaLaboralGrid() != null && !datos.getHistoriaLaboralGrid().isEmpty()) {
					datos.getHistoriaLaboralGrid().remove(index);			
					session.setAttribute(params.get("view"), datos);
				}
			} else if(params.get("type").equals("listadoNSSInvolucradosGrid")){
				if(datos.getNSSList() != null && !datos.getNSSList().isEmpty()){
					datos.getNSSList().remove(index);			
					session.setAttribute(params.get("view"), datos);
				}
			} 
		}
	
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}	
        
        
        
}
