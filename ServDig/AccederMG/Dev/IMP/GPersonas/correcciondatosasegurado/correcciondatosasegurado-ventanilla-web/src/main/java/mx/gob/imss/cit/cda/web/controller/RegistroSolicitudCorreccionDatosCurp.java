package mx.gob.imss.cit.cda.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.RegistroSolicitudCorreoUtils;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.cda.web.validator.DatosAdicionalesHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DatosHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DocumentosProbatoriosValidator;
import mx.gob.imss.cit.cda.web.validator.DomicilioValidator;
import mx.gob.imss.cit.cda.web.validator.HistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.validator.SolicitudResponsableValidator;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.cit.cda.web.vo.DatosFolioTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
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
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
@RequestMapping(value = RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE)
@SessionAttributes({"sol","personaCorreccion"})
public class RegistroSolicitudCorreccionDatosCurp extends SolicitudBaseController {
	private  final Logger log = LoggerFactory.getLogger(RegistroSolicitudCorreccionDatosCurp.class);
	private final String VIEW_INICIAR_REGISTRO = "inicioRegistroCDACURP";
	private final String VIEW_INFORMACION_RENAPO = "obtenerInformacionRenapo";
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
	private final String VIEW_GENERAR_ACUSE ="generarAcuse";
	private final static String DOMICILIO_ACLARACION_KEY = "domicilioAclaracion";
	private static final String MOTIVOS_ACLARACION_IMSS = "motivosAclaracionIMSS";
	private static final String MOTIVOS_ACLARACION_INFONAVIT = "motivosAclaracionInfonavit";
	private static final String MOTIVOS_ACLARACION_AFORE ="motivosAclaracionAfore";
	private static final String STATUS_ERROR_UPLOAD="error";
	private static final String STATUS_SUCCESS_UPLOAD="success";
	private static final String TIPO_ID_USR ="IDPERSONA";
	private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";
	private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
	private static final String TRAMITE_REASIGNADO="REASIGNADA";
	private static final String ORIGEN_SOLICITUD_VENTANILLA = "VENTANILLA";
	private static final String ID_DOCUMENTO_BOVEDA="idDocBoveda";
	
	private static final String NO_EXISTE_SOLICITUD = "noExisteSolicitud";
        
    private static final String TIPOSOLICITANTE_BENEFICIARIO = "Beneficiario";
    private static final String TIPOSOLICITANTE_REPRESENTANTE = "Representante";
    private static final String TIPOSOLICITANTE_ASEGURADO = "Asegurado";
        
    private static final String ASEGURADO_PENSIONADO="ASEGURADO_PENSIONADO";
    private static final String REPRESENTANTE_LEGAL="REPRESENTANTE_LEGAL";
    private static final String BENEFICIARIO="1";
    private static final String DATOSHISTORIALABORALBEBEFICIARIO="datosHistoriaLaboralBeneficiario";
    private static final String PERSONA_BENEFICIARIO = "personaBeneficiario";
	
	@Value("${url.envio.correo.seguimiento.ciudadano}")
	private String URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
	
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
	@Autowired
	private SolicitudResponsableValidator solicitudResponsableValidator;

	@Autowired
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusinessRemote;
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionRemote;
	@Autowired
	@Qualifier("responsableTareaBusiness")
	private ResponsableTareaRemote responsableTareaRemote;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

	@Autowired
	private DocumentosProbatoriosValidator documentosProbatoriosValidator;
	
	@Autowired
	@Qualifier("documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	
	// EJB de solicitudes
	@Autowired
	@Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

	@Autowired
	@Qualifier("manejadorReportesBusiness")
	private ManejadorReportesRemote manejadorReportesBusiness;
	
	@Autowired
	@Qualifier("serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;
	
	@Autowired
	private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;
	
	@Autowired
	@Qualifier("solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	
	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("cuentaIndividualBusiness")
	private CuentaIndividualRemote cuentaIndividualBusiness;
	
	@Autowired
	@Qualifier("bovedaBusiness")
	private BovedaRemote bovedaBusiness;
	
	@Autowired
	private RegistroSolicitudCorreoUtils registroSolicitudCorreoUtils;
	
	@Autowired
	private DocumentoProbatorioUtil documentoProbatorioUtil;
	
	@ModelAttribute("sol")
	public Solicitud  getSolicitud() {
		return new Solicitud();
	}
	@ModelAttribute("personaCorreccion")
	public Fisica  getFisica() {
		return new Fisica();
	}

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
		session.removeAttribute("sol");
		session.removeAttribute("personaCorreccion");
		session.removeAttribute(CONTINUACION_TRAMITE_KEY);
                session.removeAttribute(CONTINUACION_TRAMITE_KEY);
                session.removeAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
                session.removeAttribute(PERSONA_BENEFICIARIO);
		Fisica fisica = new Fisica();
		model.addAttribute("fisica", fisica);		
		return VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
	}
	

	@RequestMapping(value = "/obtenerInformacionRenapo", method = RequestMethod.GET)
	public String informacionRenapo(Model model, @RequestParam(value="r", required=false) String fromReturn, 
			HttpSession session) {
		Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		model.addAttribute("fisica", persona);
		
		if (fromReturn != null && StringUtils.equals("true", fromReturn)) {
			session.setAttribute(CONTINUACION_TRAMITE_KEY, true);
		}
		
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
			try {
				solicitud = guardarSolicitudInicial(solicitante, OrigenSolicitudEnum.VENTANILLA, usuario);				
				session.setAttribute(SOLICITUD, solicitud);
			} catch(TramiteNoEncontradoException ex) {
				log.error(ex.getMessage(), ex);
			} catch (SolicitudNoEncontradaException e) {
				log.error(e.getMessage(), e);
			}
		}
		return capturaDomicilioAclaracion(model, session);
	}
	
	@RequestMapping(value = "/validar/noExisteSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> validaNoExisteSolicitud (@RequestParam String curp, @RequestParam Integer isFromReturn, HttpSession session) {		
		List<String> curps = new ArrayList<String>();
		curps.add(curp);

		Solicitud solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness.obtenerUltimaSolicitudSeguimientoCDA(curps, ORIGEN_SOLICITUD_VENTANILLA);
		
		Boolean continuacionTramite = (Boolean) session.getAttribute(CONTINUACION_TRAMITE_KEY);
		
		Map<String, Boolean> resultado = new HashMap<String, Boolean> ();
		
		if (continuacionTramite != null && continuacionTramite) {
			resultado.put(NO_EXISTE_SOLICITUD, true);
		} else {
			resultado.put(NO_EXISTE_SOLICITUD, solicitudActiva != null ? !solicitudActiva.getEstadoSolicitud()
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
			BindingResult result, Model model) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		
		log.debug("---CDA--- ***********Subdelegacion******** {} ", domicilioCorto.getSubdelegacion());
		UserProfile userProfile = getUsuarioEnSesion(session);
		Fisica solicitante = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		List<Domicilio> domiciliosList = new ArrayList<Domicilio>();
		Domicilio domicilio = new Domicilio();
		BeanUtils.copyProperties(domicilioCorto, domicilio);
		
		domiciliosList.add(domicilio);
		solicitante.setDomicilios(domiciliosList);
		
		mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
		usuario.setCveIdUsuario(userProfile.getUsuario());
		usuario.setUsuario(userProfile.getUsuario());
		usuario.setCveIdSubdelegacion(userProfile.getIdSubdelegacion());
		
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
		session.setAttribute(SOLICITUD, registroCorreccionCurpUtil.actualizarXmlMotivos(solicitud,domicilioCorto.getMotivoAclaracionVO()));
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
                        listHist.add(histLaboral);
                    }
                    informacionHistoriaLaboral.setHistoriaLaboralGrid(listHist);
                }
		model.addAttribute("informacionHistoriaLaboral",informacionHistoriaLaboral);
                
		return consultainformacionHistoriaLaboralRedirect(model, session, request, response);

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
	
	private RedirectView consultainformacionHistoriaLaboralRedirect(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		DatosHistoriaLaboralVO informacionHistoriaLaboral;
		if (session.getAttribute("informacionHistoriaLaboral") != null) {
			informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
		} else {
			informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
			session.setAttribute("informacionHistoriaLaboral", informacionHistoriaLaboral);
		}
		informacionHistoriaLaboral.getHistoriaLaboralForm();
		model.addAttribute("informacionHistoriaLaboral",informacionHistoriaLaboral);
		return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_INFORMACION_HISTORIA_LABORAL,true);
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
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		
//		UserProfile userProfile = (UserProfile)session.getAttribute(SessionConstants.USER_PROFILE);
		UserProfile userProfile = getUsuarioEnSesion(session);
		this.log.debug("---CDA--- codigo postal :{}" + codigo);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = new Solicitud();
		result.put("solicitud",solicitud);
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
		
		Set<Subdelegacion> subdelegaciones= new LinkedHashSet<Subdelegacion>();
		if (!errors.hasErrors()) {
			try {
				List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote
						.getAsentamientoPorCodigoPosta(codigoPostal);
				this.log.debug("---CDA--- Asentamientos[ " + asentamientos + "]");
				result.put("asentamientos", asentamientos);		
				
				try {					
					subdelegaciones=					
							new LinkedHashSet<Subdelegacion>(
							registroCorreccionCurpUtil.generarListaSubdelegacion(
									codigoPostal.getCodigoPostal(),
									asentamientos.get(0).getMunicipio()));					
				} catch (MunicipioImssNoLocalizadoException ex) {
					subdelegaciones = new LinkedHashSet<Subdelegacion>();
					this.log.error("---CDA--- no se encontr\u00F3 ningun Municipio para el asentamiento");
				} catch (Exception e) {
					subdelegaciones = new LinkedHashSet<Subdelegacion>();
					this.log.error("---CDA--- error no especificado ", e);
				}
			} catch (DomicilioNoLocalizadoException e) {
				this.log.error("---CDA--- Domicilio no identificado ", e);
			}
		} 
		result.put("codigoPostalMismaSubdelegacion",false);
		if (userProfile.getIdSubdelegacion() != null){
			for(Subdelegacion subdelegacion:subdelegaciones){
				if(subdelegacion.getId().equals(userProfile.getIdSubdelegacion())){
					result.put("codigoPostalMismaSubdelegacion",true);
					break;
				}
			}
			
			subdelegaciones.add(domicilioServiceBusinessRemote.obtenerSubdelegacionPorId(userProfile.getIdSubdelegacion()));
			result.put(
					"subdelegaciones",subdelegaciones); 
		}
		return result;
	}
	
	public Solicitud guardarSolicitudInicial(Fisica solicitante,  OrigenSolicitudEnum origenSolicitud, mx.gob.imss.ctirss.delta.model.Usuario usuario) throws SolicitudNoEncontradaException, TramiteNoEncontradoException{
		Solicitud solicitud = null;
        try {
			solicitud =  this.registroSolicitudCorreccionDatosAseguradoBusiness.crearTramiteCorreccionCurp(solicitante, origenSolicitud, usuario);
		} catch (SolicitudNoValidaException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		}catch (SolicitudNoEncontradaException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		} catch (TramiteNoEncontradoException e) {
			log.debug("Ocurrio un error al guardar la solicitud {}",e);
		}
		return solicitud;
	}
	
	public Solicitud actualizarSolicitud(Solicitud solicitud, DatosHistoriaLaboralVO documentosNss, 
			DatosAdicionalesHistoriaLaboral contacto, DomicilioCorto motivosAclaracion){
		TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
		if(solicitud.getTramites() != null){
			log.debug("---CDA--- el tramite no es null {}", solicitud.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		}
		
		if(documentosNss != null)
			log.debug("---CDA--- los documentos del NSS no son nulos {}", documentosNss.getNSSList().get(0));
		if(contacto != null)
			log.debug("---CDA--- los datos de contacto no son nulos {}", contacto);
		
//		
		tramite.setDatosLaborales(registroCorreccionCurpUtil.cargarDatosLaborales(documentosNss.getHistoriaLaboralGrid()));
		log.debug("---CDA--- HistoriaLaboralGrid: {}", documentosNss.getHistoriaLaboralGrid());	
		log.debug("---CDA--- Motivos de aclaracion {}", motivosAclaracion.getMotivoAclaracionVO());
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
		
		UserProfile userProfile = getUsuarioEnSesion(session);
		DatosFolioTramiteVO folio = new DatosFolioTramiteVO();
		folio.setNumFolioTramite(sol.getNoFolioSolicitud());	
		log.debug("---CDA--- SOLICITUD: {}", sol.getTramites().get(0));
			try {
                            List<InicioTramite> listaInicio =  iniciarWorkFlow(sol, userProfile);
                                
				this.registroSolicitudCorreccionDatosAseguradoBusiness.
                                        finalizaRegistroCorreccionDatosAsegurados(sol,listaInicio,userProfile.getUsuario(), OrigenCapturaCDAEnum.RESPONSABLE.getClave());
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
	    return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_GENERAR_ACUSE,true);
	}
	
	 @RequestMapping(value = VIEW_GENERAR_ACUSE, method = RequestMethod.GET)
	    public RedirectView generarAcuse(Model model, HttpSession session,HttpServletRequest request){
		 
		   Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
		   UserProfile userProfile = getUsuarioEnSesion(session);
	        Fisica personaCorreccion =(Fisica)session.getAttribute(PERSONA_SESSION_KEY);
	        DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO)session.getAttribute("informacionHistoriaLaboral");
	        
	        DomicilioCorto motivosAclaracion = (DomicilioCorto)session.getAttribute("domicilioAclaracion");
	        
	        List<InicioTramite> inicioTramite =  iniciarWorkFlow(sol,userProfile);
		 
		 try {
				FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness.selloDigital(personaCorreccion,sol);
				sol.setFirmaElectronica(firma);
				session.setAttribute(SOLICITUD, sol);
			} catch (CorreccionDatosAseguradoException e) {
				log.error("---CDA--- Error al generar la firma  {}",e);
			}
			byte[] adjuntos = (byte[]) obtenerComprobanteSolicitud(sol,documentoProb,motivosAclaracion);
			String idDocumento = null;
			try{
				idDocumento = bovedaBusiness.subirDocumento(adjuntos, sol,TipoDocumentoCDAEnum.ACUSE);
			}catch (BovedaCDAException bce){
				log.error(bce.getSituacion());
			}
			
			try {
				if(idDocumento != null){
					agregarIdDocumento(sol, idDocumento,TipoDocumentoCDAEnum.ACUSE);
				}else{
					log.warn("---CDA--- Ocurrio un error al subir el documento no regreso idDocumento");
				}
			} catch (SolicitudNoEncontradaException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			} catch (TramiteNoEncontradoException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			} catch (IllegalArgumentException e) {
				log.error("---CDA--- Error al actualizar XML  {}",e);
			}
			
			log.debug("Documentos adjuntos" + adjuntos);
			String url = request.getScheme()+"://"+ URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
			enviarCorreoCorreccionDatosPorCurp(sol, adjuntos, inicioTramite.get(0).getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), url);
			
			model.addAttribute("sol", sol);
			model.addAttribute("personaCorreccion", personaCorreccion);
			
			return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+VIEW_FOLIO_TRAMITE,true);
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
	
	
	private List<InicioTramite> iniciarWorkFlow(Solicitud solicitud, UserProfile userProfile){
            
            //modificando...
             List<InicioTramite> listaInicioTramite = new ArrayList<InicioTramite>();
            for(mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud.getTramites()){
                
                InicioTramite inicioTramite = null;
                TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) tramite;   
                TareaBandeja tareaBandeja = flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCDA.getTramiteId());
                log.info("Tarea bandeja  {}", tareaBandeja);
                if(tareaBandeja != null && tareaBandeja.getInicioTramite() != null){
			inicioTramite = tareaBandeja.getInicioTramite();
		} 
                    inicioTramite = (inicioTramite != null ? inicioTramite : inicioTramiteWorkFlow(solicitud, userProfile, tramiteCDA));
                  
                listaInicioTramite.add(inicioTramite);                
            }
            
		return listaInicioTramite;
	}
        
        private InicioTramite inicioTramiteWorkFlow(Solicitud solicitud, UserProfile userProfile, TramiteCorreccionCurp tramiteCDA){
            
            List<Fisica> autorizador = new ArrayList<Fisica>();
			List<Fisica> listResponsables = new ArrayList<Fisica>();
			Map<String, List<Fisica>> personas = new LinkedHashMap<String, List<Fisica>>();
			
			log.debug("---CDA--- se asigna la solicitud {} para la subdelegacion {} y la delegacion {}",
					new Object[]{solicitud.getSolicitudId(),solicitud.getSubdelegacion().getId(),solicitud.getSubdelegacion().getDelegacion().getId()});
			Fisica fisicaAutorizador = new Fisica();
			fisicaAutorizador.setCurp(FlujoTrabajoConstants.BPM_ADMIN+solicitud.getSubdelegacion().getId());
			String ultimoResponsable=flujoTrabajoBusiness.getCurpResponsable(solicitud.getSubdelegacion().getId().toString());
			
			log.debug("---CDA---EL CURP OBTENIDO DEL RESP ES {}",ultimoResponsable);
			autorizador.add(fisicaAutorizador);
			personas.put("Autorizador", autorizador);
			try{
				listResponsables = responsablesDelegacionRemote.
						consultarResponsablesDelegacion(solicitud.getSubdelegacion().getDelegacion().getId().intValue(),
								solicitud.getSubdelegacion().getId().intValue());
				log.debug("---CDA--- balanceador {}",listResponsables.size());
				personas.put("Responsable", listResponsables);
				
			} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				log.error("---CDA--- Error al consultar webservice {} ",e);
				Fisica fisicaResponsable = new Fisica();
				fisicaResponsable.setCurp(SUBDELEGACION_SIN_RESPONSABLE);
				listResponsables.add(fisicaResponsable);
				personas.put("Responsable", listResponsables);
				
			}

			return WorkFlowDataUtil.iniciarTramite(solicitud,ultimoResponsable,personas,userProfile.getUsuario(), tramiteCDA);
            
        }
	
	@RequestMapping(value = "/folioTramite", method = RequestMethod.GET)
	public String obtenerFolioTramite(Model model, HttpSession session) {
		DatosFolioTramiteVO folio = new DatosFolioTramiteVO();		
		model.addAttribute("folio", folio);
		return VIEW_FOLIO_TRAMITE;
	}

	@RequestMapping(value = "/seguimientoTramite")
	public String seguimientoTramite(Model model, HttpServletRequest request, HttpSession session) {
		
		UserProfile userProfile = getUsuarioEnSesion(session);
		Solicitud sol = (Solicitud)session.getAttribute(SOLICITUD);
		Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp)sol.getTramites().get(0);
		

		DomicilioCorto domicilioSolicitud = procesaDomicilioCorto(sol, tramiteCDA);
		domicilioSolicitud.setSubdelegacion(sol.getSubdelegacion());
		
		sol.setPersonaInteresada(tramiteCDA.getPersonaRENAPO());
		log.debug("FECHA DE FLUJO TRABAJO 2: " + sol.getFechaSolicitudParse());
	
		ConsultaSolicitudTramiteVO informacionConsulta = procesarInformacionConsulta(sol, persona, tramiteCDA);
		
		model.addAttribute("informacionConsulta", informacionConsulta);		
		session.setAttribute("domicilioAclaracion", domicilioSolicitud);
		session.setAttribute(CONTINUACION_TRAMITE_KEY, false);
		this.log.debug("---CDA--- Estado Solicitud {} Estado Tramite {} ",sol.getEstadoSolicitud().getIdEstadoSolicitud(),sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona());
		if(sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())){
			informacionConsulta.setEstatusMot(true);
			informacionConsulta.setMotivoCancelacion(registroCorreccionCurpUtil.obtenerUltimoMotivo(tramiteCDA.getObservacionesSubdelegacion()));
		}
		if (sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo())){
			informacionConsulta.setEstatusMot(true);
			//CAMBIO 30 DIC
			informacionConsulta.setMotivoCancelacion(registroCorreccionCurpUtil.obtenerUltimoMotivo(tramiteCDA.getObservacionesSubdelegacion()));
			this.log.debug("---CDA---************ EN ESPERA DEL DERECHOHABIENTE DETALLE VENTANILLA{} ", 
					tramiteCDA.getObservacionesSubdelegacion().get(0).getDetalle());
			informacionConsulta.setStatus(EstadoNegocioEnum.obtenerDescripcionNegocio(
					EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo()));
		}
		
		if(validarSeguimientoTramite(sol)){
			model.addAttribute("banderaContinuarTramite","true");
			session.setAttribute(CONTINUACION_TRAMITE_KEY, true);

		}
		
		model.addAttribute("banderaContinuarTramiteAtendido","false");
		//VALIDA SI ESTA ATENDIDA Y ES DE INTERNET
		if(validarSeguimientoTramiteAtendido(sol)){
			model.addAttribute("banderaContinuarTramiteAtendido","true");
		}
		
		//FIXME seguir reglas de las bandejas para buscar las tarea, buscar tarea relacionada al tramite
		if (CollectionUtils.isNotEmpty(sol.getTramites()) && userProfile != null) {
			TareaBandeja tarea = flujoTrabajoBusiness.getTareaPorIdTramite(sol.getTramites().get(0).getTramiteId(), userProfile.getUsuario());
			informacionConsulta.setPropietarioTarea(false);
			informacionConsulta.setIdTarea(0L);
			informacionConsulta.setMismaSubdelegacion(false);
			
			if(tarea != null){
				informacionConsulta.setPropietarioTarea(true);
				informacionConsulta.setIdTarea(tarea.getIdTareaUsuario());
			}
		
		}
		
		if (sol.getSubdelegacion() != null) {
			log.debug("---CDA--- subdelegacion funcionario {}, subdelegacion solicitud {}", 
					userProfile.getIdSubdelegacion(), sol.getSubdelegacion().getId());
			log.debug("---CDA--- equals subdel {}", userProfile.getIdSubdelegacion().equals(sol.getSubdelegacion().getId()));
			
			if(userProfile.getIdSubdelegacion().equals(sol.getSubdelegacion().getId())) {
				informacionConsulta.setMismaSubdelegacion(true);
			} else if(tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.INICIADO.getCodigo())) {
					session.removeAttribute("domicilioAclaracion");			
			}
		}
		
		return VIEW_SEGUIMIENTO_TRAMITE;
	}
	
	private ConsultaSolicitudTramiteVO procesarInformacionConsulta(Solicitud sol, Fisica persona, TramiteCorreccionCurp tramiteCDA) {
		ConsultaSolicitudTramiteVO informacionConsulta = new ConsultaSolicitudTramiteVO();
		
		informacionConsulta.setCurp(persona.getCurp());
		informacionConsulta.setFechaSolicitud(flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCDA.getTramiteId()) != null ? flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCDA.getTramiteId()).getInicioTramite().getFechaSolicitud() : sol.getFechaSolicitudParse());
		informacionConsulta.setFolio(sol.getNoFolioSolicitud());
		informacionConsulta.setNombre(persona.getNombreCompleto());
		informacionConsulta.setNss(tramiteCDA.getListaNSS()!=null?tramiteCDA.getListaNSS().get(0):"");
		informacionConsulta.setStatus(tramiteCDA.getEstadoTramite().getDescripcion().toUpperCase().equals(TRAMITE_REASIGNADO)?TRAMITE_REASIGNADO:EstadoNegocioEnum.obtenerDescripcionNegocio(tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona()));
		informacionConsulta.setSubdelegacion(sol.getSubdelegacion()!= null? sol.getSubdelegacion().getClave() + "-" + sol.getSubdelegacion().getDescripcion():"");
		informacionConsulta.setEstatusDescarga(sol.getEstadoSolicitud()!= null ?sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()):false);
		informacionConsulta.setIdTramite(tramiteCDA.getTramiteId().toString());
		
		return informacionConsulta;
	}
	
	private DomicilioCorto procesaDomicilioCorto(Solicitud sol, TramiteCorreccionCurp tramiteCDA) {
		DomicilioCorto domicilioSolicitud = new DomicilioCorto();
		if (CollectionUtils.isNotEmpty(tramiteCDA.getPersonaRENAPO().getDomicilios())) {
			BeanUtils.copyProperties(tramiteCDA.getPersonaRENAPO().getDomicilios().get(0), domicilioSolicitud);
		}
		//LLAMAR METODO
		if (tramiteCDA.getMotivosAclaracion()!=null){
			log.debug("---CDA--- **********Si tiene motivos de aclaracion******** {} ");
			domicilioSolicitud.setMotivoAclaracionVO(registroCorreccionCurpUtil.obtenerMotivoAclaracion(tramiteCDA.getMotivosAclaracion()));
		} else {
			log.debug("---CDA--- **********No tiene motivos de aclaracion******** {} ");
		}
		
		return domicilioSolicitud;
	}
	
	private boolean validarSeguimientoTramite(Solicitud sol){
		boolean solicitudEstatusEnRegistro = sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		boolean tramiteEstatusIniciado = sol.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.INICIADO.getCodigo()); 
		return solicitudEstatusEnRegistro && tramiteEstatusIniciado;
	}
	
	private boolean validarSeguimientoTramiteAtendido(Solicitud sol){
		boolean solicitudEstatusEnRegistro = sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		log.debug("---CDA--- ORIGEN {}   DESCRIPCION {}", sol.getOrigenSolicitud().getIdOrigenSolicitud(), sol.getOrigenSolicitud().getDescripcion());
		if(sol.getOrigenSolicitud().getIdOrigenSolicitud().equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()) && solicitudEstatusEnRegistro){
			return solicitudEstatusEnRegistro;
		} 
		else
		return false;
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
			BindingResult result, Model model, HttpSession session) {

		session.setAttribute("datosAdicionalesHistoriaLaboral", datosAdicionalesHistoriaLaboral);

		log.debug("---CDA--- datosAdicionalesHistoriaLaboral {}", datosAdicionalesHistoriaLaboral);
		datosAdicionalesHistoriaLaboralValidator.validate(datosAdicionalesHistoriaLaboral, result);
		if (result.hasErrors()) {
			this.log.warn("---CDA--- Errores de captura");
			// model.addAttribute("datosAdicionalesHistoriaLaboral",datosAdicionalesHistoriaLaboral);
			return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
		}
		
		Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);
		Fisica personaSesion = (Fisica)session.getAttribute(PERSONA_SESSION_KEY);
		
		//TODO agregar medios de contacto a la persona de RENAPO utilizada en la revision vs NSS
		DatosAdicionalesHistoriaLaboral contacto = datosAdicionalesHistoriaLaboral;
		TelefonoFijo fijo = new TelefonoFijo();
		TelefonoMovil movil = new TelefonoMovil();
		CorreoElectronico correo = new CorreoElectronico();
                String observaciones="";
		if(contacto!=null){
    		fijo.setNumero(contacto.getDatosContacto().getTelefonoFijo());
    		personaSesion.setTelefonoFijo(fijo);
    		movil.setNumero(contacto.getDatosContacto().getTelefonoCelular());
    		personaSesion.setTelefonoMovil(movil);
    		correo.setCorreo(contacto.getDatosContacto().getCorreoElectronico());
    		personaSesion.setCorreoElectronico(correo);
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
		
		solicitud = this.actualizarSolicitud(solicitud, datosHistoriaLaboral, datosAdicionalesHistoriaLaboral, motivosAclaracion);
                ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).setObservacion(observaciones);
		log.debug("---CDA--- los datos de la solicitud son {}", solicitud);
		log.debug("---CDA--- los datos de tramite son {}", solicitud.getTramites().get(0)); 
		
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
                List<Fisica> personaBeneficiario=((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonas();
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
                if(personaBeneficiario!=null && !personaBeneficiario.isEmpty()){
                    Fisica beneficiario=personaBeneficiario.get(0);
                    if(beneficiario.getTipoPersona().getDescripcion().equals(TIPOSOLICITANTE_ASEGURADO)){
                        datosHistoriaLaboral.setTipoSolicitante(ASEGURADO_PENSIONADO);
                    }else if(beneficiario.getTipoPersona().getDescripcion().equals(TIPOSOLICITANTE_REPRESENTANTE)){
                        datosHistoriaLaboral.setTipoSolicitante(REPRESENTANTE_LEGAL);
                    }else if(beneficiario.getTipoPersona()!=null){
                        datosHistoriaLaboral.setTipoSolicitante(BENEFICIARIO);
                        if(beneficiario.getChecked()!=null){
                            datosHistoriaLaboral.setDefuncion(beneficiario.getChecked());
                        }
                    }
                }
                log.debug("--CDA-- DOCUMENTOPROBATORIOS Aseg: {}", datosHistoriaLaboral.getDocumentoProbatorioList());
                model.addAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
                

		Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session.getAttribute(PERSONA_SESSION_KEY) : null;
		
		this.log.error("---CDA--- idPersona fisica en sesion: {}", fisica!= null ? fisica.getIdPersona(): "sin persona en sesion");
		
		cargarPantallaDatosHistoriaLaboral(model, session);
		return VIEW_DATOS_HISTORIA_LABORAL;
	}

	private void cargarPantallaDatosHistoriaLaboral(Model model,HttpSession session) {
		model.addAttribute("datosHistoriaLaboral", (DatosHistoriaLaboralVO) 
				session.getAttribute("datosHistoriaLaboral"));
		log.debug("datoshitorialaboral CARGAR PANTALLA {}" , (DatosHistoriaLaboralVO) 
				session.getAttribute("datosHistoriaLaboral") );
		// Valor
                model.addAttribute("documentosProbatoriosVo", documentoProbatorioUtil.getDocumentProbAsegurado());
////		model.addAttribute("documentoNSSClave", TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
//		model.addAttribute("documentosActa", TipoDocumentoProbatorioEnum.ACTAS.getId());
//		model.addAttribute("documentosIdentificacion", TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
	}

	@RequestMapping(value = "/validar/datosHistoriaLaboral", method = RequestMethod.POST)
	public String consultaDatosHistoriaLaboral(
			@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboral,BindingResult result, Model model, HttpSession session) throws DocumentoException {		
		log.debug("---CDA--- DatosHistoriaLaboral {}", datosHistoriaLaboral);
		Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session.getAttribute(PERSONA_SESSION_KEY) : null;
		List<String> curpsHistoricas = new ArrayList<String>();
		if(fisica.getCurpsHistoricas() != null){
			curpsHistoricas.addAll(fisica.getCurpsHistoricas());
		}		
		curpsHistoricas.add(fisica.getCurp());
		datosHistoriaLaboralValidator.validate(datosHistoriaLaboral, result, curpsHistoricas);
		
		log.debug("---CDA---**************DOCUMENTOS PROBATORIOS A VALIDAR****************{}", datosHistoriaLaboral.getDocumentoProbatorioList());
		if(datosHistoriaLaboral.getDocumentoProbatorioList() != null &&
				!datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()){
			
			//caso de defuncion
			if (datosHistoriaLaboral.getTipoSolicitante().equals("1") && datosHistoriaLaboral.getTipoBeneficiario() != null){
				Long beneficiario = TipoPersonaInteresadaSolEnum.valueOf(datosHistoriaLaboral.getTipoBeneficiario()).getId();
				documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), result, beneficiario.intValue());
			}
			//caso representante legal o asegurado
			else if (!datosHistoriaLaboral.getTipoSolicitante().equals("1")) {
				Long solicitante = TipoPersonaInteresadaSolEnum.valueOf(datosHistoriaLaboral.getTipoSolicitante()).getId();
				documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), result, solicitante.intValue());
			} 
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
		
		if (datosHistoriaLaboral.getTipoBeneficiario()!=null || !datosHistoriaLaboral.getTipoSolicitante().equals(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.toString())){
			Fisica interesado = agregarBeneficiario(datosHistoriaLaboral.getCurp());
			if (interesado != null){
				Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
				Fisica beneficiario = interesado;
				beneficiario.setTipoPersona(new TipoPersona());
				if (datosHistoriaLaboral.getTipoBeneficiario()!=null){
					beneficiario.getTipoPersona().setIdTipoPersona(TipoPersonaInteresadaSolEnum.valueOf(datosHistoriaLaboral.getTipoBeneficiario()).getId());
					beneficiario.getTipoPersona().setDescripcion(datosHistoriaLaboral.getTipoBeneficiario());
				} else{
					beneficiario.getTipoPersona().setIdTipoPersona(TipoPersonaInteresadaSolEnum.valueOf(datosHistoriaLaboral.getTipoSolicitante()).getId());
					beneficiario.getTipoPersona().setDescripcion(datosHistoriaLaboral.getTipoSolicitante().replace("_", " "));
				}
				
				List<Fisica> personas = new ArrayList<Fisica>();
				personas.add(beneficiario);
				List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> tramites = sol.getTramites();
				for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tram : tramites){
					if (tram.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo())){
						tram.setPersonas(personas);
					}
				}
				this.log.debug("---CDA--- LISTA DE PERSONAS EN TRAMITE {}" + sol.getTramites().get(0).getPersonas());
				session.setAttribute(SOLICITUD, sol);
			} else {
				result.reject("label.solicitud.mensaje.error.curp");
				model.addAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
				return VIEW_DATOS_HISTORIA_LABORAL; 
			}
		}
		model.addAttribute("datosAdicionalesHistoriaLaboral",datosAdicionalesHistoriaLaboral);
		return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
	}
	
	@RequestMapping(value = "/informacionHistoriaLaboral")
	public String consultainformacionHistoriaLaboral(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		DatosHistoriaLaboralVO informacionHistoriaLaboral;
		if (session.getAttribute("informacionHistoriaLaboral") != null) {
			informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute("informacionHistoriaLaboral");
		} else {
			informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
			session.setAttribute("informacionHistoriaLaboral", informacionHistoriaLaboral);
		}
		informacionHistoriaLaboral.getHistoriaLaboralForm();
		//informacionHistoriaLaboral.setHistoriaLaboralForm(null);
		model.addAttribute("informacionHistoriaLaboral",informacionHistoriaLaboral);
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
			@RequestParam("fileData") MultipartFile file,@RequestParam String tipoPer, 
			@RequestParam("idDocPorTipo") String idDocporTipo, HttpSession ses,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
		if( file.getSize() <= TAMANIO_MAXIMO_ARCHIVO){		
		Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
		String nombreCompleto="";
		String ben="CDA_PI_" + idDocporTipo + "_" ;
		log.debug("---CDA--- #####Subiendo Archivo######");
		String nombre= idDocporTipo + "_" + file.getOriginalFilename();
		if(tipoPer.equals("Beneficiario")){
			nombreCompleto=ben.concat(nombre);
			log.debug("---CDA--- El nombre del documento queda:{}",nombreCompleto);
		}
		else{
			nombreCompleto=nombre;
		}
		
		String[] n = file.getOriginalFilename().split("\\.");
		Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses.getAttribute(PERSONA_SESSION_KEY) : null;
		log.debug("---CDA--- Persona Fisica upload docto null?: {}", fisica != null);
		if (fisica != null) {
			log.debug("---CDA--- Persona Fisica para iniciar upload de docto: {} idsolicitud: {}", fisica.getIdPersona(), solicitud.getSolicitudId());

			String idDocBoveda = null;
			try {
				idDocBoveda = bovedaBusiness.subirDocumento(file.getBytes(), solicitud, nombreCompleto, n[n.length - 1] , file.getContentType());
			} catch (BovedaCDAException bce) {
				log.error(bce.getSituacion());
				result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
				result.put("__situacion_", bce.getSituacion());
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.TEXT_HTML);
				return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);			
			} catch(Exception e) {				
				log.error("---CDA--- Error al subir el documento {}",e);
				result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.TEXT_HTML);
				return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
			}

			if (idDocBoveda != null) {
				log.debug("---CDA--- documento insertado en boveda {}",idDocBoveda );
				result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
				result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
				
			} else {
				log.debug("---CDA--- Error en createDocument ::: el metodo regreso un error ");
				result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
			}
		} else {
			log.warn("---CDA--- El usuario no esta logeado idPersona null");
			result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
		} 
		}else{
			result.put(STATUS_ERROR_UPLOAD, "El archivo no cumple con el tama&ntilde;o m&aacute;ximo permitido [4MB]. No es posible adjuntar el archivo.");
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
		
	}

	@RequestMapping(value = "/validar/NSS/{nssLength}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validateNSS(@RequestBody NSSVO nssvo,
			HttpServletResponse response, @PathVariable Integer nssLength,Model model) {
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
					"---CDA--- Error al generar el acuse -> ",
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
	public Object cancelarSolicitud(Model model, HttpSession session, HttpServletRequest request,
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
		
		return new RedirectView("/" + request.getSession().getServletContext().getInitParameter("webAppRootKey")
				+ "/atencionResponsable");
	}
	
	@RequestMapping(value = "/concluirSolicitud", method = RequestMethod.GET)
	public Object concluirSolicitud(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws SolicitudNoValidaException {
		log.debug("---CDA--- ******Entra al metodo de concluirSolicitud ************************");
		return new RedirectView("/" + request.getSession().getServletContext().getInitParameter("webAppRootKey")
				+ "/atencionResponsable");
		
	}
	
	private void enviarCorreoCorreccionDatosPorCurp(Solicitud solicitud, byte[] adjuntos,String curpResponsable, String url) {
		CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
		log.debug("---CDA---url: {} ", url);
		if(((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico() != null && 
				((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo() != null){
			log.debug("---CDA--- Correo Derechohabiente: " + ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo());
			correoElectronicoDTO.setCorreoPara(new String[1]);
			correoElectronicoDTO.getCorreoPara()[0] = ((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getPersonaRENAPO().getCorreoElectronico().getCorreo();
			correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
			correoElectronicoDTO.setCuerpoCorreo(registroSolicitudCorreoUtils.contenidoCorreoCDA(solicitud, url));
			log.debug("---CDA--- Contenido Correo Derechohabiente: " + (correoElectronicoDTO.getCuerpoCorreo()));
			if(adjuntos != null){
				Map<String,byte[]> adjuntosMap = new HashMap<String, byte[]>();
				//TODO Cambiar nombre en la llave del mapa con el nombre del documento que sera adjuntado.
				adjuntosMap.put("Documento.pdf", adjuntos);
				correoElectronicoDTO.setAdjuntos(adjuntosMap);
			}
			
			try {
				envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
			} catch (Exception e) {
				log.debug("---CDA--- Error al enviar correo " , e);
			}
		}
		
		if(StringUtils.isBlank(curpResponsable)){
			
			try{
				List<Fisica> autorizadores = responsablesDelegacionRemote.consultarAutorizadoresDelegacion(solicitud.getSubdelegacion().getDelegacion().getId().intValue(), solicitud.getSubdelegacion().getId().intValue());
				for(Fisica autorizador:autorizadores){
					correoElectronicoDTO = new CorreoElectronicoDTO();
					
					if (autorizador.getCorreoElectronico() != null && autorizador.getCorreoElectronico().getCorreo() != null){
						correoElectronicoDTO.setCorreoPara(new String[1]);
						correoElectronicoDTO.getCorreoPara()[0] = autorizador.getCorreoElectronico().getCorreo();
						log.debug("---CDA--- correoAutorizador: " + autorizador.getCorreoElectronico().getCorreo());
						correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
						correoElectronicoDTO.setCuerpoCorreo(registroSolicitudCorreoUtils.contenidoCorreoAutorizador(solicitud,autorizador));
						log.debug("---CDA--- contenidoCorreoAutorizador: " + correoElectronicoDTO.getCuerpoCorreo());
						try {
							envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
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
					correoElectronicoDTO.setCuerpoCorreo(registroSolicitudCorreoUtils.contenidoCorreoResponsable(solicitud, responsable));
					log.debug("---CDA--- contenidoCorreoResponsable: " + correoElectronicoDTO.getCuerpoCorreo());
					try {
						envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
					} catch (Exception e) {
						log.debug("---CDA--- Error al enviar correo Responsable" , e);
					}
				}
			}catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				log.error("---CDA--- ErrorClienteWebserviceResponsablesSubdelegacionException {} ",e);
			}
		}		
	}
	
	@RequestMapping(value = "/registroSolicitudCDAResponsable")
	public Object registroSolicitudCDAResponsable(Model model, HttpSession session) {
		Fisica fisica= new Fisica();
		model.addAttribute("fisica", fisica);
		return VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
	}
	
	@RequestMapping(value = "/registroVentanilla/buscarPersonaRenapo", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> buscarPersonaRenapo(Model model,
			@RequestBody Fisica persona, HttpSession ses, HttpServletRequest request, HttpServletResponse response) { 
		
		inicioCorreccionCurp(model, ses);
		Map<String, Object> result = new HashMap<String, Object>();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_HTML);
		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
		
		final Errors errors = new BindException(persona, "model");
		log.debug("---CDA--- ###Validar formulario de registro###");
		log.debug("---CDA--- datos de persona recibidos : {} ", persona);
		solicitudResponsableValidator.validate(persona, errors);
		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
			log.debug("---CDA--- mapa resultado validar persona {}" , result);
			respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.PRECONDITION_FAILED);
		} else {
			Fisica personaRenapo = persona;		
			boolean excepcion = false;
			if (persona.getCurp()!= null && !persona.getCurp().equals("")){
				try {
					personaRenapo = this.personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(persona.getCurp());
				} catch (ClienteWebserviceRenapoCurpException e) {
					log.error("---CDA--- Error en la busqueda de persona por CURP {} en RENAPO", persona.getCurp(), e);
					excepcion = true;
					result.put("exception", "El servicio web de RENAPO no se encuentra disponible en este momento, por favor int\u00e9ntelo m\u00e1s tarde.");
					respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				if (personaRenapo == null && !excepcion){
					result.put("exception" , "La CURP proporcionada no fue localizada en RENAPO, por favor verifique la informaci\u00f3n capturada.");
					respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				try {
					personaRenapo = this.personaBusiness.buscarPersonaFisicaPorDatosBasicosEnRenapo(persona.getNombre(), persona.getPrimerApellido(),
							persona.getSegundoApellido(), persona.getSexo().getIdSexo(), persona.getFechaNacimiento(), Integer.parseInt(persona.getLugarNacimiento().getClave()));
					} catch (Exception e) {
						log.error("---CDA--- Error en la busqueda de persona datos basicos en RENAPO", e);
						excepcion = true ;
						result.put("exception", "El servicio web de RENAPO no se encuentra disponible en este momento, por favor int\u00e9ntelo m\u00e1s tarde.");
						respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				if (personaRenapo == null && !excepcion){
					log.debug("---CDA--- Solicitud sin CURP");
					result.put("noResult", "No se ha localizado una CURP en RENAPO asociada a los datos b\u00e1sicos ingresados, por favor verifique que la informaci\u00f3n capturada sea correcta.<br>Si desea registrar una solicitud sin CURP y los datos del interesado fueron verificados con el acta de nacimiento, seleccione la opci\u00f3n Aceptar.");
					respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.NOT_FOUND);
				}
			}
			
			//validar que la persona en caso de ser encontrada por CURP 
			//en BDTU no sea patron ni representante legal
			Map<String, Object> roles = registroSolicitudCorreccionDatosAseguradoBusiness.personaAutorizadaRegistroCDA(persona.getCurp(),persona.getCurpsHistoricas());
			if (roles != null && !roles.isEmpty()){
				log.error("---CDA--- La persona no puede registrar una solicitud ya que no es Asegurado");
				excepcion = true ;
				result.put("exception","El asegurado que va a realizar su registro de solicitud de regularizaci\u00f3n y/o correcci\u00f3n de datos personales, tiene el o los siguientes roles, por favor verifique la informaci\u00f3n en SINDO y BDTU correspondiente a cada rol, con el objeto de que no se generen inconsistencias derivado de la Correcci\u00f3n de Datos:");
				result.putAll(roles);
				respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				if (personaRenapo != null){
					persona = personaRenapo;
				}
			}
			
			if (personaRenapo != null  && !excepcion){
				persona = personaRenapo;
				result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
				respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.OK);
			}			
		}
		Solicitud solicitudActiva = null;
		//TODO validar la solicitud activa mediante la CURP
		List<String> curps = new ArrayList<String>();
		curps.add(persona.getCurp());
		if(persona.getCurpsHistoricas() != null){
			curps.addAll(persona.getCurpsHistoricas());
		}
		solicitudActiva =  registroSolicitudCorreccionDatosAseguradoBusiness.obtenerUltimaSolicitudSeguimientoCDA(curps,ORIGEN_SOLICITUD_VENTANILLA);
		this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva);
                
		if(solicitudActiva != null && solicitudActiva.getSolicitudId() !=null){
			this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva.getNoFolioSolicitud());
                        solicitudActiva = registroCorreccionCurpUtil.orderByTramite(solicitudActiva);
			ses.setAttribute(SOLICITUD, solicitudActiva);
			ses.setAttribute(PERSONA_SESSION_KEY, ((TramiteCorreccionCurp)solicitudActiva.getTramites().get(0)).getPersonaRENAPO());
			result.put("vista", "/wizard/correccionDatosAsegurado/seguimientoTramite");
		}else{
			if(solicitudActiva != null){
				log.error("---CDA--- Se encontro mas de un resultado");
				result.clear();
				result.put("exception","Se encontr\u00f3 m\u00e1s de un registro asociado a su CURP");
				respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}		
		ses.setAttribute(PERSONA_SESSION_KEY, persona);	
		return respuesta;
	}
	
	@RequestMapping (value = "/datosHistoriaLaboral/obtenerDocsProbatorios", method= RequestMethod.POST)
	public @ResponseBody Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> obtenerDocumentos 
	(@RequestBody  DatosHistoriaLaboralVO datosHistoriaLaboralVO, HttpServletResponse response){
		
		log.debug("************es defuncion o no       " + datosHistoriaLaboralVO.isDefuncion() );
		log.debug("  * _ * _ * tipoBeneficiario {}" + datosHistoriaLaboralVO.getTipoBeneficiario());
		Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> doctos;
		if (datosHistoriaLaboralVO.getTipoBeneficiario().equals("")){
			doctos = elegirDocsProbatoriosPorPersona(null);
		} else {
			Long beneficiario = TipoPersonaInteresadaSolEnum.valueOf(datosHistoriaLaboralVO.getTipoBeneficiario()).getId();
			log.debug("enumeracion de tipo de beneficiario {}" + beneficiario.intValue() );
			doctos = elegirDocsProbatoriosPorPersona(beneficiario.intValue());
		} 
		return doctos;
		
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
				return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,errores);
			}
		} catch (BovedaCDAException bce){
			Map<String, Object> errores = new HashMap<String, Object>();
	  	  	errores.put("errorBoveda", bce.getSituacion());
	  	  	errores.put("errorNegocio", bce.getMensajeError());
	    	return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,errores);
			
		}catch (Exception ioe) {
			Map<String, Object> errores = new HashMap<String, Object>();
	  	  	errores.put("errorBoveda", ioe.getMessage());
	  	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
	    	return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,errores);
		}
	}
	
	private Fisica agregarBeneficiario (String curp){
		Fisica beneficiarioRenapo = null;
		try {
			beneficiarioRenapo = this.personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.debug("---CDA--- CURP beneficiario inexistente en RENAPO {}", e);
		}
		if (beneficiarioRenapo != null){
			Fisica beneficiarioIMSS = null;
			beneficiarioIMSS = registroCorreccionCurpUtil.buscarExistenciaIMSS(beneficiarioRenapo);
			beneficiarioRenapo.setIdPersona(beneficiarioIMSS.getIdPersona());
			this.log.debug("---CDA--- beneficiario con idPersona {}", beneficiarioRenapo.getIdPersona());
		}
		return beneficiarioRenapo;
	}
	
	private Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> elegirDocsProbatoriosPorPersona (Integer tipoSolicitante){
		List<DoctoReqTramite> documentos = documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramite(
				TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo().longValue());
		
		//agrupacion por tipo
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new LinkedHashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB; 
		for (DoctoReqTramite doctoReqTramite : documentos) {
			Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
			TipoDocumentoProbatorio currMapKey = null;
			//TipoDocumentoProbatorio currMapKeyb = null;
			if(doctos.keySet() != null && !doctos.keySet().isEmpty()){
				currMapKey = getCurrentMapKey(doctos, tipoDocto);
			}
			if(currMapKey == null){
				currMapKey = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio();
				doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
				log.debug("--CDA-- creando llave para tipo: {}",currMapKey.getIdTipoDocumentoProbatorio());
			}
			DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
			docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
			docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
			docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
			docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId() 
					|| (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
							&& docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP.getId() )
					|| docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()
					|| docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.FORMATOS.getId()){
				log.debug("--CDA-- se agrega documento: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctos.get(currMapKey).add(docProbatorioVo);
			}
			if(tipoSolicitante != null){
				addDocumentoPorTipoSolicitante(tipoSolicitante, doctos, docProbatorioVo, currMapKey);
			}			
		}
			
		//documentos beneficiario
		doctosB = getDocumentosBeneficiario(documentos, tipoSolicitante);
		doctos = filtrarDocumentosRequeridos(doctos);
		doctosB = filtrarDocumentosRequeridos(doctosB);
		
		Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> doctosCompletos= new LinkedHashMap<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>>();
		doctosCompletos.put("Asegurado",doctos);
		doctosCompletos.put("Beneficiario",doctosB);
		return doctosCompletos;
	}
	
	private void addDocumentoPorTipoSolicitante(Integer tipoSolicitante, 
			Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos, 
			DocumentoProbatorio docProbatorioVo, TipoDocumentoProbatorio currMapKey){
		
		if(tipoSolicitante != 6 && tipoSolicitante != 1){
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()){
				log.debug("--CDA-- se agrega documento de beneficiarios: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctos.get(currMapKey).add(docProbatorioVo);		
			}
		}
	}
	
	private TipoDocumentoProbatorio getCurrentMapKey(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos, Integer tipoDocto){
		TipoDocumentoProbatorio currMapKey = null;
		for (TipoDocumentoProbatorio key : doctos.keySet()) {
			if(tipoDocto.equals(key.getIdTipoDocumentoProbatorio())){
				currMapKey = key;	
				log.debug("--CDA-- se encontro la llave: {}",key.getIdTipoDocumentoProbatorio());
			}
		}
		return currMapKey;
	}
	
	private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> filtrarDocumentosRequeridos(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos){
		
		List<TipoDocumentoProbatorio> keys = new ArrayList<TipoDocumentoProbatorio>();
		
		for (TipoDocumentoProbatorio key : doctos.keySet()) {
			if(doctos.get(key).isEmpty()){
				keys.add(key);
			}
		}
		for (TipoDocumentoProbatorio tipoDocumentoProbatorio : keys) {
			doctos.remove(tipoDocumentoProbatorio);
		}
		
		return doctos;
	}
	
	private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentosBeneficiario(List<DoctoReqTramite> documentos,
			Integer tipoSolicitante){
		// CICLO PARA LLENAR DOCTOS B
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB = new LinkedHashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
		for (DoctoReqTramite doctoReqTramite : documentos) {
			Integer tipoDoctoB = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
			TipoDocumentoProbatorio currMapKeyb = null;
			if (doctosB.keySet() != null && !doctosB.keySet().isEmpty()) {
				currMapKeyb = getCurrentMapKey(doctosB, tipoDoctoB);
			}
			if (currMapKeyb == null) {
				currMapKeyb = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio();
				doctosB.put(currMapKeyb, new ArrayList<DocumentoProbatorio>());
				log.debug("--CDA-- creando llave para tipo bene: {}", currMapKeyb.getIdTipoDocumentoProbatorio());
			}
			DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
			docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
			docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
			docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
			docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
			if (tipoSolicitante != null) {
				addDocumentoPorTipoBeneficiario(tipoSolicitante, doctosB, docProbatorioVo, currMapKeyb);
			}

		}
		return doctosB;
	}
	
	private void addDocumentoPorTipoBeneficiario(Integer tipoSolicitante, 
			Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB, 
			DocumentoProbatorio docProbatorioVo, TipoDocumentoProbatorio currMapKeyb){
		
		if (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
				&& docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP.getId()){
			log.debug("--CDA-- se agrega documento identificacion bene: {}, {}",
					docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
			doctosB.get(currMapKeyb).add(docProbatorioVo);
		}
		switch(tipoSolicitante){
		//Conyuge
		case 2:
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_MATRIMONIO.getId()){
				log.debug("--CDA-- se agrega ACTA DE MATRIMONIO EN BENE: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctosB.get(currMapKeyb).add(docProbatorioVo);
			}
			break;
		//Descendiente
		case 3:
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId()){
				log.debug("--CDA-- se agrega documento hijo: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctosB.get(currMapKeyb).add(docProbatorioVo);
			}
			break;
		//Concubino
		case 5:
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()){
				log.debug("--CDA-- se agrega documento concubino: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctosB.get(currMapKeyb).add(docProbatorioVo);
			}
			break;
		//Representante
		case 6:
			if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.PODER_NOTARIAL.getId()) {
				log.debug("--CDA-- se agrega documento representante: {}, {}",
						docProbatorioVo.getCveIdDocumento(),docProbatorioVo.getDesDocumento());
				doctosB.get(currMapKeyb).add(docProbatorioVo);
			}
			break;
		default:
			log.debug("---CDA--- No agrega nada");
			
		}
	}
	
	private byte[] obtenerCertificacionSolicitud(ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO)throws BovedaCDAException,Exception{
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
            Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas = new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();
			
			parametrosCuentas.add(cuentaIndividualBusiness.consultarMovimientosCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
			
			parametrosCuentas.add(cuentaIndividualBusiness.consultarUltimoMovimientoCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
			
			FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness.selloDigitalCertificacion(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO(),sol, parametrosCuentas);
			sol.setFirmaElectronica(firma);
			
			documentoCertificado= manejadorReportesBusiness.ejecutaReporteCertificacion(registroCorreccionCurpUtil.generarParametrosReporte(sol,flujoTrabajoBusiness.obtenerParticipantesUltimaTareaByTramite(Long.parseLong(consultaSolicitudTramiteVO.getIdTramite())), parametrosCuentas));			
					
			try {
				String idDocumento = bovedaBusiness.subirDocumento(documentoCertificado, sol,TipoDocumentoCDAEnum.CERTIFICADO);
				agregarIdDocumento(sol, idDocumento, TipoDocumentoCDAEnum.CERTIFICADO);
			}catch(BovedaCDAException bce){
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
		
	private String recuperarIdDocumento(Solicitud sol, TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws SolicitudNoEncontradaException{
		TramiteCorreccionCurp tramite = null;
		if(sol.getTramites() != null){
			log.debug("---CDA--- el tramite no es null {}", sol.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
			Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(tramite.getTramiteId());
			
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		}	
		return tramite != null ?(tipoDocumentoCDAEnum.equals(TipoDocumentoCDAEnum.ACUSE)?tramite.getIdDocumentoAcuse():tramite.getIdDocumentoCertificacion()):null;
	}
	
	@RequestMapping(value = "/eliminarDocumentoBoveda", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoBoveda (@RequestBody Map<String, String> params, HttpSession session){
		        
        log.debug("---CDA---Llega a RSCDC con el parametro: " + params.get("idDocBoveda"));
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
	
	@RequestMapping(value = "/eliminarListaDocsBoveda", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoBoveda (HttpSession session, 
    		@RequestBody Map<String, List<String>> ListaIdsBovedaDocs) {
       
        List<String> idsBovedaDocs = ListaIdsBovedaDocs.get("idsBovedaDocs");
        String confirmacion = "";
        Map<String, String> respuesta = new HashMap<String, String>();
        for(String idDocBoveda : idsBovedaDocs){
            try{
                while(!confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo()) && !confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5004.getCodigo())){
                    confirmacion = bovedaBusiness.eliminarDocumento(idDocBoveda);
                }
            }catch (BovedaCDAException bce){
                log.error(bce.getSituacion());
                confirmacion = bce.getSituacion();
            }
            respuesta.put(idDocBoveda, confirmacion);
        }
        
        DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session.getAttribute("datosHistoriaLaboral");
        
        if (datos.getDocumentoProbatorioList() != null && !datos.getDocumentoProbatorioList().isEmpty() 
        		&& datos.getNSSList() != null && !datos.getNSSList().isEmpty()){
        	datos.getNSSList().clear();
            datos.getDocumentoProbatorioList().clear();
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
