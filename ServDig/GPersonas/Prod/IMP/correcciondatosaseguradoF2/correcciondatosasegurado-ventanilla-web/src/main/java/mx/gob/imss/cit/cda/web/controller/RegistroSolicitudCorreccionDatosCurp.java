package mx.gob.imss.cit.cda.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.RegistroSolicitudCorreoUtils;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.cda.web.validator.DatosAdicionalesHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DomicilioValidator;
import mx.gob.imss.cit.cda.web.validator.HistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.validator.SolicitudResponsableValidator;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.cit.cda.web.vo.DatosContacto;
import mx.gob.imss.cit.cda.web.vo.DatosFolioTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
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
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
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
@SessionAttributes({ "sol", "personaCorreccion" })
public class RegistroSolicitudCorreccionDatosCurp extends
        SolicitudBaseController {
    private final Logger log = LoggerFactory
            .getLogger(RegistroSolicitudCorreccionDatosCurp.class);

    private static final String STATUS_ERROR_UPLOAD = "error";
    private static final String STATUS_SUCCESS_UPLOAD = "success";
    private static final String TIPO_ID_USR = "IDPERSONA";
    private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";
    private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
    private static final String TRAMITE_REASIGNADO = "REASIGNADA";
    private static final String ORIGEN_SOLICITUD_VENTANILLA = "VENTANILLA";
    private static final String ID_DOCUMENTO_BOVEDA = "idDocBoveda";
    private static final String ERROR_BOVEDA = "errorBoveda";
    private static final String TRAMINTE_NO_NULO = "---CDA--- el tramite no es null {}";
    private static final String MODEL = "model";    
    private static final String ERRORES_DE_CAPTURA = "---CDA--- Errores de captura";
    private static final String ERROR_ACTUALIZAR_XML = "---CDA--- Error al actualizar XML  {}";
    private static final String ERROR_NEGOCIO = "errorNegocio";
    private static final String VIEW = "view";
    private static final String NSS = "NSS:";
    private static final String DOMICILIO_ACLARACION = "domicilioAclaracion";
    private static final String DATOS_ADICIONALES_HIST_LABORAL = "datosAdicionalesHistoriaLaboral";
    private static final String INFORMACION_HIST_LABORAL = "informacionHistoriaLaboral";
    private static final String EXCEPTION_CONST = "exception";
    private static final String DATOS_HIST_LABORAL = "datosHistoriaLaboral";

    private static final String EXTENSIONES_VALIDAS[] = { "gif", "tif", "jpg",
            "png", "pdf" };
    private static final String NO_EXISTE_SOLICITUD = "noExisteSolicitud";

    private static final String DATOSHISTORIALABORALBEBEFICIARIO = "datosHistoriaLaboralBeneficiario";
    private static final String PERSONA_BENEFICIARIO = "personaBeneficiario";
    private static final String NULL = "null";

    @Value("${url.envio.correo.seguimiento.ciudadano}")
    private String URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;

    @Autowired
    private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

    
    @Autowired
    @Qualifier("personaBusiness")
    private PersonaBusinessRemote personaBusiness;

    @Autowired
    private NSSValidator nSSValidator;

    @Autowired
    private HistoriaLaboralValidator historiaLaboralValidator;
    @Autowired
    private DatosAdicionalesHistoriaLaboralValidator datosAdicionalesHistoriaLaboralValidator;
    @Autowired
    private DomicilioValidator domicilioValidator;
    @Autowired
    private SolicitudResponsableValidator solicitudResponsableValidator;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionRemote;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;

    @Autowired
    @Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

 
    @Autowired
    @Qualifier("manejadorReportesBusiness")
    private ManejadorReportesRemote manejadorReportesBusiness;

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
    @Qualifier("cuentaIndividualBusiness")
    private CuentaIndividualRemote cuentaIndividualBusiness;

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    @Autowired
    private RegistroSolicitudCorreoUtils registroSolicitudCorreoUtils;

    @Autowired
    private DocumentoProbatorioUtil documentoProbatorioUtil;
    
    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;

    @RequestMapping(value = "")
    public String inicioCorreccionCurp(Model model, HttpSession session) {
        model.addAttribute(SessionConstants.ATTR_TRAMITE_SEGURO, "");
        session.removeAttribute(DATOS_ADICIONALES_HIST_LABORAL);
        session.removeAttribute(DATOS_HIST_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_TRAMITE_SEGURO);
        session.removeAttribute(SessionConstants.ATTR_SOLICITUD);
        session.removeAttribute(SessionConstants.ATTR_PERSONA_KEY);
        session.removeAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        session.removeAttribute(INFORMACION_HIST_LABORAL);
        session.removeAttribute(DOMICILIO_ACLARACION);
        session.removeAttribute("sol");
        session.removeAttribute("personaCorreccion");
        session.removeAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE);
        session.removeAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE);
        session.removeAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
        session.removeAttribute(PERSONA_BENEFICIARIO);
        Fisica fisica = new Fisica();
        model.addAttribute("fisica", fisica);
        return RequestMappingConstants.VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
    }

    @RequestMapping(value = RequestMappingConstants.REQUEST_INFO_RENAPO, method = RequestMethod.GET)
    public String informacionRenapo(Model model,
            @RequestParam(value = "r", required = false) String fromReturn,
            @RequestParam(value = "solicitudNueva", required = false) String newSolicitud,
            HttpSession session) {
        Fisica persona = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        model.addAttribute("fisica", persona);
        
		Solicitud soicitudActiva = (Solicitud) session
				.getAttribute(SessionConstants.ATTR_SOLICITUD);

        if (fromReturn != null && StringUtils.equals("true", fromReturn)) {
            session.setAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE,
                    true);
        }
        
        if (newSolicitud != null && StringUtils.equals("true", newSolicitud)) {
            try {
                session.setAttribute(SessionConstants.ATTR_SOLICITUD, null);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } 
        }
		if (soicitudActiva != null
				&& soicitudActiva.getEstadoSolicitud().getIdEstadoSolicitud()
						.equals(EstadoSolicitudEnum.CANCELADA.getCodigo())) {
			session.setAttribute(SessionConstants.ATTR_SOLICITUD, null);
		}
        
        return RequestMappingConstants.VIEW_INFORMACION_RENAPO;
    }

    @RequestMapping(value = RequestMappingConstants.REQUEST_INFO_RENAPO, method = RequestMethod.POST)
    public String consultaInformacionRenapo(Model model,             
            HttpSession session) {
        
        Fisica solicitante = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
        usuario.setCveIdUsuario(solicitante.getCurp());
        usuario.setUsuario(solicitante.getCurp());

        Solicitud solicitud;
        
        if (session.getAttribute(SessionConstants.ATTR_SOLICITUD) == null) {
            try {
                solicitud = guardarSolicitudInicial(solicitante,
                        OrigenSolicitudEnum.VENTANILLA, usuario);
                session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);
                return removerDomicilioAclaracion(model, session);
            } catch (TramiteNoEncontradoException ex) {
                log.error(ex.getMessage(), ex);
            } catch (SolicitudNoEncontradaException e) {
                log.error(e.getMessage(), e);
            }
        }
        return capturaDomicilioAclaracion(model, session);
    }

    @RequestMapping(value = "/validar/noExisteSolicitud", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, Boolean> validaNoExisteSolicitud(
            @RequestParam String curp, @RequestParam Integer isFromReturn,
            HttpSession session) {
        List<String> curps = new ArrayList<String>();
        curps.add(curp);

        Solicitud solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness
                .obtenerUltimaSolicitudSeguimientoCDA(curps,
                        ORIGEN_SOLICITUD_VENTANILLA);

        Boolean continuacionTramite = (Boolean) session
                .getAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE);

        Map<String, Boolean> resultado = new HashMap<String, Boolean>();

        if (continuacionTramite != null && continuacionTramite) {
            resultado.put(NO_EXISTE_SOLICITUD, true);
        } else {
            resultado.put(NO_EXISTE_SOLICITUD,
                    solicitudActiva != null ? !solicitudActiva
                            .getEstadoSolicitud().getIdEstadoSolicitud()
                            .equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())
                            : true);
        }
        return resultado;
    }

    
    private void sesionDomicilioCortoStep01(Solicitud solicitud,
      Fisica solicitante, HttpSession session ){
      for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tcda = (TramiteCorreccionCurp) tramite;
            if (tcda.getPersonaRENAPO() != null
                    && tcda.getPersonaRENAPO().getMediosContacto() != null
                    && !tcda.getPersonaRENAPO().getMediosContacto().isEmpty()) {
                solicitante.setTelefonoFijo(tcda.getPersonaRENAPO()
                        .getTelefonoFijo());
                solicitante.setTelefonoMovil(tcda.getPersonaRENAPO()
                        .getTelefonoMovil());
                solicitante.setCorreoElectronico(tcda.getPersonaRENAPO()
                        .getCorreoElectronico());
                DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
                DatosContacto datos = new DatosContacto();
                datos.setCorreoElectronico(solicitante.getCorreoElectronico() != null ? solicitante
                        .getCorreoElectronico().getCorreo() : null);
                datos.setTelefonoFijo(solicitante.getTelefonoFijo() != null ? solicitante
                        .getTelefonoFijo().getNumero() : null);
                datos.setTelefonoCelular(solicitante.getTelefonoMovil() != null ? solicitante
                        .getTelefonoMovil().getNumero() : null);
                datosAdicionalesHistoriaLaboral.setDatosContacto(datos);
                datosAdicionalesHistoriaLaboral.setObservaciones(tcda
                        .getObservacion() != null ? tcda.getObservacion()
                        : null);
                session.setAttribute(DATOS_ADICIONALES_HIST_LABORAL,
                        datosAdicionalesHistoriaLaboral);
            }
        }
    }
    
    @RequestMapping(value = "/sesionDomicilioCorto", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> sesionDomicilioCorto(
            @RequestBody DomicilioCorto domicilioCorto,
            HttpServletResponse response, HttpSession session) {
        session.setAttribute(DOMICILIO_ACLARACION, domicilioCorto);

        log.debug("---CDA--- ***********Subdelegacion******** {} ",
                domicilioCorto.getSubdelegacion());
        UserProfile userProfile = getUsuarioEnSesion(session);
        Fisica solicitante = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        List<Domicilio> domiciliosList = new ArrayList<Domicilio>();
        Domicilio domicilio = new Domicilio();
        BeanUtils.copyProperties(domicilioCorto, domicilio);

        domiciliosList.add(domicilio);
        solicitante.setDomicilios(domiciliosList);

        mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
        usuario.setCveIdUsuario(userProfile.getUsuario());
        usuario.setUsuario(userProfile.getUsuario());
        usuario.setCveIdSubdelegacion(userProfile.getIdSubdelegacion());

        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        
        sesionDomicilioCortoStep01(solicitud, solicitante, session);

        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setPersonaRENAPO(solicitante);

        if (solicitante != null) {
            solicitud.setPersonaInteresada(solicitante);
        }

        if (usuario != null) {
            solicitud.setSolicitante(usuario);
        }
        solicitud.setSubdelegacion(domicilioServiceBusinessRemote
                .obtenerSubdelegacionPorId(domicilioCorto.getSubdelegacion()
                        .getId()));
        log.debug("---CDA--- **********Subdelegacion********** {} ",
                solicitud.getSubdelegacion());

        registroSolicitudCorreccionDatosAseguradoBusiness
                .actualizarSubdelegacionSolicitud(solicitud);
        session.setAttribute(SessionConstants.ATTR_SOLICITUD,
                registroCorreccionCurpUtil.actualizarXmlMotivos(solicitud,
                        domicilioCorto.getMotivoAclaracionVO()));
        session.setAttribute(SessionConstants.ATTR_SOLICITUD,
                actualizarTramites(solicitud));
        session.setAttribute(DOMICILIO_ACLARACION, domicilioCorto);

        DatosHistoriaLaboralVO informacionHistoriaLaboral;
        if (session.getAttribute(INFORMACION_HIST_LABORAL) != null) {
            informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(INFORMACION_HIST_LABORAL);
        } else {
            informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
            session.setAttribute(INFORMACION_HIST_LABORAL,
                    informacionHistoriaLaboral);
        }

        List<DatosLaborales> listDatosLaborales = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0)).getDatosLaborales();
        if (listDatosLaborales != null && !listDatosLaborales.isEmpty()) {
            List<HistoriaLaboralVO> listHist = new ArrayList<HistoriaLaboralVO>();
            for (DatosLaborales datoLaboral : listDatosLaborales) {
                HistoriaLaboralVO histLaboral = new HistoriaLaboralVO();
                histLaboral.setDomicilioEmpresa(datoLaboral.getDomicilio());
                histLaboral.setActividadEmpresa(datoLaboral.getActividad());
                histLaboral.setEntidadFederativa(datoLaboral
                        .getEntidadFederativaNombre());
                histLaboral.setFechaBaja(datoLaboral.getFechaBaja());
                histLaboral.setFechaInscripcion(datoLaboral
                        .getFechaInscripcion());
                histLaboral.setNombrePatron(datoLaboral.getNombrePatron());
                histLaboral.setNumeroRegistroPatronal(datoLaboral.getNrp());
                listHist.add(histLaboral);
            }
            informacionHistoriaLaboral.setHistoriaLaboralGrid(listHist);
        }
        Map<String, Object> result = new HashMap<String, Object>();

        return result;
    }
    
    private void capturaDomicilioAclaracionStep01(TramiteCorreccionCurp tcda,
      Fisica solicitante, HttpSession session ){
      if (tcda.getPersonaRENAPO() != null
              && tcda.getPersonaRENAPO().getMediosContacto() != null
              && !tcda.getPersonaRENAPO().getMediosContacto().isEmpty()) {
        solicitante.setTelefonoFijo(tcda.getPersonaRENAPO()
                .getTelefonoFijo());
        solicitante.setTelefonoMovil(tcda.getPersonaRENAPO()
                .getTelefonoMovil());
        solicitante.setCorreoElectronico(tcda.getPersonaRENAPO()
                .getCorreoElectronico());
        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral =
                new DatosAdicionalesHistoriaLaboral();
        DatosContacto datos = new DatosContacto();
        datos.setCorreoElectronico(solicitante.getCorreoElectronico() != null ?
                solicitante
                        .getCorreoElectronico().getCorreo() :
                null);
        datos.setTelefonoFijo(solicitante.getTelefonoFijo() != null ?
                solicitante
                        .getTelefonoFijo().getNumero() :
                null);
        datos.setTelefonoCelular(solicitante.getTelefonoMovil() != null ?
                solicitante
                        .getTelefonoMovil().getNumero() :
                null);
        datosAdicionalesHistoriaLaboral.setDatosContacto(datos);
        datosAdicionalesHistoriaLaboral.setObservaciones(tcda
                .getObservacion() != null ?
                        tcda.getObservacion() :
                        null);
        session.setAttribute(DATOS_ADICIONALES_HIST_LABORAL,
                datosAdicionalesHistoriaLaboral);
      }
    }
    
    private void capturaDomicilioAclaracionStep02(Fisica solicitante,
            Solicitud solicitud,
            mx.gob.imss.ctirss.delta.model.Usuario usuario ){
      if (solicitante != null) {
            solicitud.setPersonaInteresada(solicitante);
        }

        if (usuario != null) {
            solicitud.setSolicitante(usuario);
        }
    }

    @RequestMapping(value = "/capturarDomicilio", method = RequestMethod.POST)
    public RedirectView capturaDomicilioAclaracion(
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION) DomicilioCorto domicilioCorto,
            BindingResult result, Model model)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        log.debug("---CDA--- ***********Subdelegacion******** {} ",
                domicilioCorto.getSubdelegacion());
        UserProfile userProfile = getUsuarioEnSesion(session);
        Fisica solicitante = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        List<Domicilio> domiciliosList = new ArrayList<Domicilio>();
        Domicilio domicilio = new Domicilio();
        BeanUtils.copyProperties(domicilioCorto, domicilio);

        domiciliosList.add(domicilio);
        solicitante.setDomicilios(domiciliosList);

        mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
        usuario.setCveIdUsuario(userProfile.getUsuario());
        usuario.setUsuario(userProfile.getUsuario());
        usuario.setCveIdSubdelegacion(userProfile.getIdSubdelegacion());

        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tcda = (TramiteCorreccionCurp) tramite;
            capturaDomicilioAclaracionStep01(tcda, solicitante, session);
        }

        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setPersonaRENAPO(solicitante);

        capturaDomicilioAclaracionStep02(solicitante, solicitud, usuario);
                
        solicitud.setSubdelegacion(domicilioServiceBusinessRemote
                .obtenerSubdelegacionPorId(domicilioCorto.getSubdelegacion()
                        .getId()));
        log.debug("---CDA--- **********Subdelegacion********** {} ",
                solicitud.getSubdelegacion());

        registroSolicitudCorreccionDatosAseguradoBusiness
                .actualizarSubdelegacionSolicitud(solicitud);
        session.setAttribute(SessionConstants.ATTR_SOLICITUD,
                registroCorreccionCurpUtil.actualizarXmlMotivos(solicitud,
                        domicilioCorto.getMotivoAclaracionVO()));
        session.setAttribute(SessionConstants.ATTR_SOLICITUD,
                actualizarTramites(solicitud));
        session.setAttribute(DOMICILIO_ACLARACION, domicilioCorto);

        if (((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .getPersonaRENAPO().getDomicilios() == null
                || ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                        .getPersonaRENAPO().getDomicilios().isEmpty()) {
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CAPTURAR_DOMICILIO,
                    true);
        }

        DatosHistoriaLaboralVO informacionHistoriaLaboral;
        if (session.getAttribute(INFORMACION_HIST_LABORAL) != null) {
            informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(INFORMACION_HIST_LABORAL);
        } else {
            informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
            session.setAttribute(INFORMACION_HIST_LABORAL,
                    informacionHistoriaLaboral);
        }

        List<DatosLaborales> listDatosLaborales = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0)).getDatosLaborales();
        if (listDatosLaborales != null && !listDatosLaborales.isEmpty()) {
            List<HistoriaLaboralVO> listHist = new ArrayList<HistoriaLaboralVO>();
            for (DatosLaborales datoLaboral : listDatosLaborales) {
                HistoriaLaboralVO histLaboral = new HistoriaLaboralVO();
                histLaboral.setDomicilioEmpresa(datoLaboral.getDomicilio());
                histLaboral.setActividadEmpresa(datoLaboral.getActividad());
                histLaboral.setEntidadFederativa(datoLaboral
                        .getEntidadFederativaNombre());
                histLaboral.setFechaBaja(datoLaboral.getFechaBaja());
                histLaboral.setFechaInscripcion(datoLaboral
                        .getFechaInscripcion());
                histLaboral.setNombrePatron(datoLaboral.getNombrePatron());
                histLaboral.setNumeroRegistroPatronal(datoLaboral.getNrp());
                listHist.add(histLaboral);
            }
            informacionHistoriaLaboral.setHistoriaLaboralGrid(listHist);
        }
        model.addAttribute(INFORMACION_HIST_LABORAL,
                informacionHistoriaLaboral);

        return consultainformacionHistoriaLaboralRedirect(model, session,
                request, response);

    }
    
    private void actualizarListaHistorialStep01(HistoriaLaboralVO historiaLaboralVO,
      DatosHistoriaLaboralVO documentoProb      ){
      if (documentoProb.getHistoriaLaboralGrid() != null
                && !documentoProb.getHistoriaLaboralGrid().isEmpty()) {
            Iterator<HistoriaLaboralVO> itera = documentoProb
                    .getHistoriaLaboralGrid().iterator();
            while (itera.hasNext()) {
                HistoriaLaboralVO datosLab = itera.next();
                if (historiaLaboralVO.getDomicilioEmpresa().equals(
                        datosLab.getDomicilioEmpresa())
                        && historiaLaboralVO.getActividadEmpresa().equals(
                                datosLab.getActividadEmpresa())
                        && historiaLaboralVO.getEntidadFederativa().equals(
                                datosLab.getEntidadFederativa())
                        && historiaLaboralVO.getFechaBaja().equals(
                                datosLab.getFechaBaja())
                        && historiaLaboralVO.getFechaInscripcion().equals(
                                datosLab.getFechaInscripcion())
                        && historiaLaboralVO.getNombrePatron().equals(
                                datosLab.getNombrePatron())
                        && historiaLaboralVO.getNumeroRegistroPatronal()
                                .equals(datosLab.getNumeroRegistroPatronal())) {
                    log.debug(
                            "---CDA--- Se remueve de session historiaLaboralVO {} ",
                            datosLab);
                    itera.remove();
                }
            }
        }
    }

    @RequestMapping(value = "/actualizarLista", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> actualizarListaHistorial(
            @RequestBody HistoriaLaboralVO historiaLaboralVO,
            HttpServletResponse response, HttpSession session, Model model) {

        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO) session
                .getAttribute(INFORMACION_HIST_LABORAL);
        actualizarListaHistorialStep01(historiaLaboralVO, documentoProb);
                
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        Map<String, Object> result = new HashMap<String, Object>();
        tramite = (TramiteCorreccionCurp) sol.getTramites().get(0);
        if (tramite.getDatosLaborales() != null
                && !tramite.getDatosLaborales().isEmpty()) {
            Iterator<DatosLaborales> itera = tramite.getDatosLaborales()
                    .iterator();
            while (itera.hasNext()) {
                DatosLaborales datosLab = itera.next();
                if (historiaLaboralVO.getDomicilioEmpresa().equals(
                        datosLab.getDomicilio())
                        && historiaLaboralVO.getActividadEmpresa().equals(
                                datosLab.getActividad())
                        && historiaLaboralVO.getEntidadFederativa().equals(
                                datosLab.getEntidadFederativaNombre())
                        && historiaLaboralVO.getFechaBaja().equals(
                                datosLab.getFechaBaja())
                        && historiaLaboralVO.getFechaInscripcion().equals(
                                datosLab.getFechaInscripcion())
                        && historiaLaboralVO.getNombrePatron().equals(
                                datosLab.getNombrePatron())
                        && historiaLaboralVO.getNumeroRegistroPatronal()
                                .equals(datosLab.getNrp())) {
                    log.debug("---CDA--- historiaLaboralVO {}",
                            historiaLaboralVO.getNumeroRegistroPatronal());
                    itera.remove();
                }
            }
            try {
                log.debug("---CDA--- tamanio de datoslaborales {}", tramite
                        .getDatosLaborales().size());
                solicitudBusiness.actualizarXmlTramite(tramite);
                sol.getTramites().set(0, tramite);
                session.setAttribute(SessionConstants.ATTR_SOLICITUD, sol);
            } catch (TramiteNoEncontradoException ex) {
                log.debug("---CDA--- TramiteNoEncontradoException {}", ex);
            } catch (IllegalArgumentException ex) {
                log.debug("---CDA--- IllegalArgumentException {}", ex);
            }
        }
        result.put("success", "");
        log.debug("---CDA--- actualiza la tabla {}", result.toString());
        return result;
    }

    private RedirectView consultainformacionHistoriaLaboralRedirect(
            Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        
        DatosHistoriaLaboralVO informacionHistoriaLaboral;
        if (session.getAttribute(INFORMACION_HIST_LABORAL) != null) {
            informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(INFORMACION_HIST_LABORAL);
        } else {
            informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
            session.setAttribute(INFORMACION_HIST_LABORAL,
                    informacionHistoriaLaboral);
        }
        informacionHistoriaLaboral.getHistoriaLaboralForm();
        model.addAttribute(INFORMACION_HIST_LABORAL,
                informacionHistoriaLaboral);
        
        if(request!=null&&response!=null)
        {
            log.debug("request y response no nulos");
        }
        
        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                        + "/"
                        + RequestMappingConstants.VIEW_INFORMACION_HISTORIA_LABORAL,
                true);
    }

    private Solicitud actualizarTramites(Solicitud solicitud) {
        Solicitud solicitudReturn = null;
        try {
            solicitudReturn = solicitudBusiness.actualizarTramites(solicitud);
        } catch (TramiteNoEncontradoException e) {
            log.error("---CDA--- No se encontro el Tramite {}", e);
        } catch (SolicitudNoEncontradaException e) {
            log.error("---CDA--- Solicitud no encontrada {}", e);
        }

        return solicitudReturn;
    }

    @RequestMapping(value = "/validar/domicilioCorto", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> validateDomicilioCorto(
            @RequestBody DomicilioCorto domicilioCorto,
            HttpServletResponse response) {
        final Errors errors = new BindException(domicilioCorto, MODEL);

        log.debug("---CDA--- ############Validar Domicilio#####################");
        log.debug("---CDA--- Domicilio corto recibido : {} ", domicilioCorto);

        domicilioValidator.validate(domicilioCorto, errors);

        log.debug("---CDS--- {} {}", errors.getAllErrors(), errors.hasErrors());

        Map<String, Object> result = new HashMap<String, Object>();

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
        }

        log.debug("---CDA--- validar domicilioCorto {}", result.toString());
        return result;
    }

    
    private void getAsentamientosPorCodigoStep01(String codigo, Errors errors ){
      if (codigo == null || codigo.isEmpty()) {
            this.log.error("---CDA--- codigo postal nulo regresando el error");
            errors.rejectValue("codigoPostal", "field.required");
        } else if (codigo.length() != 5) {
            this.log.error("---CDA--- codigo postal incorrecto regresando el error");
            errors.rejectValue("codigoPostal", "field.cp.incorrecto");
        }
    }
    
    private void getAsentamientosPorCodigoStep02( UserProfile userProfile,
      Set<Subdelegacion> subdelegaciones, Map<String, Object> result      ){
      if (userProfile.getIdSubdelegacion() != null) {
            for (Subdelegacion subdelegacion : subdelegaciones) {
                if (subdelegacion.getId().equals(
                        userProfile.getIdSubdelegacion())) {
                    result.put("codigoPostalMismaSubdelegacion", true);
                    break;
                }
            }

            subdelegaciones
                    .add(domicilioServiceBusinessRemote
                            .obtenerSubdelegacionPorId(userProfile
                                    .getIdSubdelegacion()));
            result.put("subdelegaciones", subdelegaciones);
        }
    }
    
    @RequestMapping(value = "/asentamiento/get/codigoPostal", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> getAsentamientosPorCodigo(
            @RequestParam String codigo, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {

        UserProfile userProfile = getUsuarioEnSesion(session);
        this.log.debug("---CDA--- codigo postal :{}" + codigo);
        Map<String, Object> result = new HashMap<String, Object>();
        Solicitud solicitud = new Solicitud();
        result.put("solicitud", solicitud);
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal(codigo);

        Errors errors = new BindException(codigoPostal, MODEL);
        getAsentamientosPorCodigoStep01(codigo, errors);

        Set<Subdelegacion> subdelegaciones = new LinkedHashSet<Subdelegacion>();
        if (!errors.hasErrors()) {
            try {
                List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote
                        .getAsentamientoPorCodigoPosta(codigoPostal);
                this.log.debug("---CDA--- Asentamientos[ " + asentamientos
                        + "]");
                result.put("asentamientos", asentamientos);

                try {
                    subdelegaciones = new LinkedHashSet<Subdelegacion>(
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
        result.put("codigoPostalMismaSubdelegacion", false);
        getAsentamientosPorCodigoStep02(userProfile, subdelegaciones, result);
        return result;
    }

    public Solicitud guardarSolicitudInicial(Fisica solicitante,
            OrigenSolicitudEnum origenSolicitud,
            mx.gob.imss.ctirss.delta.model.Usuario usuario)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        Solicitud solicitud = null;
        try {
            solicitud = this.registroSolicitudCorreccionDatosAseguradoBusiness
                    .crearTramiteCorreccionCurp(solicitante, origenSolicitud,
                            usuario);
        } catch (SolicitudNoValidaException e) {
            log.debug("Ocurrio un error al guardar la solicitud {}", e);
        } catch (SolicitudNoEncontradaException e) {
            log.debug("Ocurrio un error al guardar la solicitud {}", e);
        } catch (TramiteNoEncontradoException e) {
            log.debug("Ocurrio un error al guardar la solicitud {}", e);
        }
        return solicitud;
    }

    public Solicitud actualizarSolicitud(Solicitud solicitud,
            DatosHistoriaLaboralVO documentosNss,
            DatosAdicionalesHistoriaLaboral contacto,
            DomicilioCorto motivosAclaracion) {
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if (solicitud.getTramites() != null) {
            log.debug(TRAMINTE_NO_NULO, solicitud
                    .getTramites().get(0).getTramiteId());
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }

        if (documentosNss != null)
            log.debug("---CDA--- los documentos del NSS no son nulos {}",
                    documentosNss.getNSSList().get(0));
        if (contacto != null)
            log.debug("---CDA--- los datos de contacto no son nulos {}",
                    contacto);

        tramite.setDatosLaborales(registroCorreccionCurpUtil
                .cargarDatosLaborales(documentosNss.getHistoriaLaboralGrid()));
        log.debug("---CDA--- HistoriaLaboralGrid: {}",
                documentosNss.getHistoriaLaboralGrid());
        log.debug("---CDA--- Motivos de aclaracion {}",
                motivosAclaracion.getMotivoAclaracionVO());
        tramite.setMotivosAclaracion(registroCorreccionCurpUtil
                .cargarMotivosAclaracion(motivosAclaracion
                        .getMotivoAclaracionVO()));
        log.debug("---CDA--- Numero de motivos de aclaracion: {}", tramite
                .getMotivosAclaracion() != null ? tramite
                .getMotivosAclaracion().size() : "0");
        return solicitud;
    }

    @RequestMapping(value = "/confirmarDatosSolicitud", method = RequestMethod.GET)
    public String confirmarDatosSolicitud(Model model, HttpSession session) {
        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        model.addAttribute("solicitud", solicitud);
        log.debug("---CDA--- los datos de tramite son {}", solicitud
                .getTramites().get(0));
        return RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD;
    }

    @RequestMapping(value = "/confirmarSolicitud", method = RequestMethod.POST)
    public RedirectView confirmarDatosSolicitud(Model model,
            HttpSession session,
            @ModelAttribute("datosSolicitud") Solicitud datosSolicitud,
            HttpServletRequest request) {
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);

        UserProfile userProfile = getUsuarioEnSesion(session);
        DatosFolioTramiteVO folio = new DatosFolioTramiteVO();
        folio.setNumFolioTramite(sol.getNoFolioSolicitud());
        log.debug("---CDA--- SOLICITUD pre registro: {}", sol.getTramites().get(0));
        try {
            List<InicioTramite> listaInicio = iniciarWorkFlow(sol, userProfile);

            this.registroSolicitudCorreccionDatosAseguradoBusiness
                    .finalizaRegistroCorreccionDatosAsegurados(sol,
                            listaInicio, userProfile.getUsuario(),
                            OrigenCapturaCDAEnum.SOLICITUD.getClave());
        } catch (CorreccionDatosAseguradoException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD,
                    true);
        } catch (SolicitudNoEncontradaException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD,
                    true);
        } catch (TramiteNoEncontradoException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD,
                    true);
        } catch (RegistrarDocumentoProbatorioException e) {
            log.error("---CDA--- Error en el registro de los documentos ", e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD,
                    true);
        } catch (Exception e) {
            log.error("---CDA--- Error ", e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/"
                            + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD,
                    true);
        }
        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                        + RequestMappingConstants.VIEW_GENERAR_ACUSE, true);
    }

    @RequestMapping(value = RequestMappingConstants.VIEW_GENERAR_ACUSE, method = RequestMethod.GET)
    public RedirectView generarAcuse(Model model, HttpSession session,
            HttpServletRequest request) {

        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        UserProfile userProfile = getUsuarioEnSesion(session);
        Fisica personaCorreccion = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO) session
                .getAttribute(INFORMACION_HIST_LABORAL);

        DomicilioCorto motivosAclaracion = (DomicilioCorto) session
                .getAttribute(DOMICILIO_ACLARACION);

        List<InicioTramite> inicioTramite = iniciarWorkFlow(sol, userProfile);

        try {
            FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness
                    .selloDigital(personaCorreccion, sol);
            sol.setFirmaElectronica(firma);
            session.setAttribute(SessionConstants.ATTR_SOLICITUD, sol);
        } catch (CorreccionDatosAseguradoException e) {
            log.error("---CDA--- Error al generar la firma  {}", e);
        }
        byte[] adjuntos = (byte[]) obtenerComprobanteSolicitud(sol,
                documentoProb, motivosAclaracion);
        String idDocumento = null;
        try {
            idDocumento = bovedaBusiness.subirDocumento(adjuntos, sol,
                    TipoDocumentoCDAEnum.ACUSE);
        } catch (BovedaCDAException bce) {
            log.error(bce.getSituacion());
        }

        try {
            if (idDocumento != null) {
                agregarIdDocumento(sol, idDocumento, TipoDocumentoCDAEnum.ACUSE);
            } else {
                log.warn("---CDA--- Ocurrio un error al subir el documento no regreso idDocumento");
            }
        } catch (SolicitudNoEncontradaException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
        } catch (TramiteNoEncontradoException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
        } catch (IllegalArgumentException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
        }

        log.debug("Documentos adjuntos" +Arrays.toString(adjuntos));
        String url = request.getScheme() + "://"
                + URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
        enviarCorreoCorreccionDatosPorCurp(
                sol,
                adjuntos,
                inicioTramite.get(0).getParticipantes()
                        .get(ParticipantesEnum.RESPONSABLE.getDescripcion()),
                url);

        model.addAttribute("sol", sol);
        model.addAttribute("personaCorreccion", personaCorreccion);
        log.debug("Se va a la pantalla de folio ");
        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE + "/"
                        + RequestMappingConstants.VIEW_FOLIO_TRAMITE, true);
    }

    private void agregarIdDocumento(Solicitud sol, String idDocumento,
            TipoDocumentoCDAEnum tipoDocumentoCDAEnum)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, IllegalArgumentException {

        TramiteCorreccionCurp tramite = null;
        if (sol.getTramites() != null && idDocumento != null) {
            log.debug(TRAMINTE_NO_NULO, sol.getTramites()
                    .get(0).getTramiteId());
            tramite = (TramiteCorreccionCurp) sol.getTramites().get(0);
            Solicitud solicitud = solicitudBusiness
                    .consultarPorIdTramite(tramite.getTramiteId());

            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
            switch (tipoDocumentoCDAEnum) {
            case ACUSE:
                tramite.setIdDocumentoAcuse(idDocumento);
                break;
            case CERTIFICADO:
                tramite.setIdDocumentoCertificacion(idDocumento);
                break;
            }
            solicitudBusiness.actualizarXmlTramite(tramite);
        } else {
            log.warn("---CDA--- El idDocumento es nulo {}", idDocumento == null);
            log.warn("---CDA--- la solicitud no tiene tramites {}",
                    sol.getTramites() == null);
        }

    }

    private List<InicioTramite> iniciarWorkFlow(Solicitud solicitud,
            UserProfile userProfile) {

        List<InicioTramite> listaInicioTramite = new ArrayList<InicioTramite>();
        for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
                .getTramites()) {

            InicioTramite inicioTramite = null;
            TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) tramite;
            TareaBandeja tareaBandeja = flujoTrabajoBusiness
                    .getTareaActivaPorIdTramite(tramiteCDA.getTramiteId());
            log.info("Tarea bandeja  {}", tareaBandeja);
            if (tareaBandeja != null && tareaBandeja.getInicioTramite() != null) {
                inicioTramite = tareaBandeja.getInicioTramite();
            }
            inicioTramite = (inicioTramite != null ? inicioTramite
                    : inicioTramiteWorkFlow(solicitud, userProfile, tramiteCDA));

            listaInicioTramite.add(inicioTramite);
        }

        return listaInicioTramite;
    }

    private InicioTramite inicioTramiteWorkFlow(Solicitud solicitud,
            UserProfile userProfile, TramiteCorreccionCurp tramiteCDA) {

        List<Fisica> autorizador = new ArrayList<Fisica>();
        List<Fisica> listResponsables = new ArrayList<Fisica>();
        Map<String, List<Fisica>> personas = new LinkedHashMap<String, List<Fisica>>();

        log.debug(
                "---CDA--- se asigna la solicitud {} para la subdelegacion {} y la delegacion {}",
                new Object[] { solicitud.getSolicitudId(),
                        solicitud.getSubdelegacion().getId(),
                        solicitud.getSubdelegacion().getDelegacion().getId() });
        Fisica fisicaAutorizador = new Fisica();
        fisicaAutorizador.setCurp(FlujoTrabajoConstants.BPM_ADMIN
                + solicitud.getSubdelegacion().getId());
        String ultimoResponsable = flujoTrabajoBusiness
                .getCurpResponsable(solicitud.getSubdelegacion().getId()
                        .toString());

        log.debug("---CDA---EL CURP OBTENIDO DEL RESP ES {}", ultimoResponsable);
        autorizador.add(fisicaAutorizador);
        personas.put("Autorizador", autorizador);
        try {
            listResponsables = responsablesDelegacionRemote
                    .consultarResponsablesDelegacion(solicitud
                            .getSubdelegacion().getDelegacion().getId()
                            .intValue(), solicitud.getSubdelegacion().getId()
                            .intValue());
            log.debug("---CDA--- balanceador {}", listResponsables.size());
            personas.put("Responsable", listResponsables);

        } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
            log.error("---CDA--- Error al consultar webservice {} ", e);
            Fisica fisicaResponsable = new Fisica();
            fisicaResponsable.setCurp(SUBDELEGACION_SIN_RESPONSABLE);
            listResponsables.add(fisicaResponsable);
            personas.put("Responsable", listResponsables);

        }

        return WorkFlowDataUtil.iniciarTramite(solicitud, ultimoResponsable,
                personas, userProfile.getUsuario(), tramiteCDA);

    }

  
    private void seguimientoTramiteStep01( Solicitud sol,
      ConsultaSolicitudTramiteVO informacionConsulta,
      TramiteCorreccionCurp tramiteCDA,
      Model model, HttpSession session){
      if (sol.getTramites().get(0).getEstadoTramite()
                .getIdEstadoTramitePersona()
                .equals(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())) {
            informacionConsulta.setEstatusMot(true);
            informacionConsulta.setMotivoCancelacion(registroCorreccionCurpUtil
                    .obtenerUltimoMotivo(tramiteCDA
                            .getObservacionesSubdelegacion()));
        }
        if (sol.getTramites()
                .get(0)
                .getEstadoTramite()
                .getIdEstadoTramitePersona()
                .equals(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo())) {
            informacionConsulta.setEstatusMot(true);
            informacionConsulta.setMotivoCancelacion(registroCorreccionCurpUtil
                    .obtenerUltimoMotivo(tramiteCDA
                            .getObservacionesSubdelegacion()));
            this.log.debug(
                    "---CDA---************ EN ESPERA DEL DERECHOHABIENTE DETALLE VENTANILLA{} ",
                    tramiteCDA.getObservacionesSubdelegacion().get(0)
                            .getDetalle());
            informacionConsulta
                    .setStatus(EstadoNegocioEnum
                            .obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE
                                    .getCodigo()));
            informacionConsulta.setIdEstadoTramite(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE
                    .getCodigo());
        }

        if (validarSeguimientoTramite(sol)) {
            model.addAttribute("banderaContinuarTramite", "true");
            session.setAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE,
                    true);

        }
    }

    @RequestMapping(value = "/seguimientoTramite")
    public String seguimientoTramite(Model model, HttpServletRequest request,
            HttpSession session) {

        UserProfile userProfile = getUsuarioEnSesion(session);
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica persona = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) sol
                .getTramites().get(0);

        DomicilioCorto domicilioSolicitud = procesaDomicilioCorto(sol,
                tramiteCDA);
        domicilioSolicitud.setSubdelegacion(sol.getSubdelegacion());
        
        sol.setPersonaInteresada(tramiteCDA.getPersonaRENAPO());
        log.debug("FECHA DE FLUJO TRABAJO 2: " + sol.getFechaSolicitudParse());

        ConsultaSolicitudTramiteVO informacionConsulta = procesarInformacionConsulta(
                sol, persona, tramiteCDA);

        model.addAttribute("informacionConsulta", informacionConsulta);
        session.setAttribute(DOMICILIO_ACLARACION, domicilioSolicitud);
        session.setAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE, false);
        this.log.debug("---CDA--- Estado Solicitud {} Estado Tramite {} ", sol
                .getEstadoSolicitud().getIdEstadoSolicitud(), sol.getTramites()
                .get(0).getEstadoTramite().getIdEstadoTramitePersona());
         this.log.debug("---CDA--->> Descripcion Estado Solicitud {} ", sol
                .getEstadoSolicitud().getDescripcion() );
         this.log.debug("---CDA--->> Descripcion Estado Tramite {} ", sol.getTramites()
                .get(0).getEstadoTramite().getDescripcion() );
         this.log.debug("---CDA--->> Descripcion informacionConsulta {} ", informacionConsulta.getStatus() );
        seguimientoTramiteStep01(sol, informacionConsulta, tramiteCDA, model,
                session);
//Validar si funciona para algo este codigo
        model.addAttribute("banderaContinuarTramiteAtendido", "false");
        if (validarSeguimientoTramiteAtendido(sol)) {        	
            model.addAttribute("banderaContinuarTramiteAtendido", "true");
        }
        //													Valida si la solicitud esta cancelada.
                
		if (validarSeguimientoTramiteCancelado(sol)) {
			log.info("----CDA-----Permite continuar con la solicitud");
			session.setAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE, false);
			model.addAttribute("estatusSolicitudCancelada", "true");
		} else {
			log.info("----CDA-----No permite continuar con la solicitud");
			model.addAttribute("estatusSolicitudCancelada", "false");
		}
        
        if (CollectionUtils.isNotEmpty(sol.getTramites())
                && userProfile != null) {
            TareaBandeja tarea = flujoTrabajoBusiness.getTareaPorIdTramite(sol
                    .getTramites().get(0).getTramiteId(),
                    userProfile.getUsuario());
            informacionConsulta.setPropietarioTarea(false);
            informacionConsulta.setIdTarea(0L);
            informacionConsulta.setMismaSubdelegacion(false);

            if (tarea != null) {
                informacionConsulta.setPropietarioTarea(true);
                informacionConsulta.setIdTarea(tarea.getIdTareaUsuario());
            }

        }

        if (sol.getSubdelegacion() != null) {
            log.debug(
                    "---CDA--- subdelegacion funcionario {}, subdelegacion solicitud {}",
                    userProfile.getIdSubdelegacion(), sol.getSubdelegacion()
                            .getId());
            log.debug("---CDA--- equals subdel {}", userProfile
                    .getIdSubdelegacion()
                    .equals(sol.getSubdelegacion().getId()));

            if (userProfile.getIdSubdelegacion().equals(
                    sol.getSubdelegacion().getId())) {
                informacionConsulta.setMismaSubdelegacion(true);
            } else if (tramiteCDA.getEstadoTramite()
                    .getIdEstadoTramitePersona()
                    .equals(EstadoTramiteEnum.INICIADO.getCodigo())) {
                session.removeAttribute(DOMICILIO_ACLARACION);
            }
        }

        return RequestMappingConstants.VIEW_SEGUIMIENTO_TRAMITE;
    }
    
    private String validarSolicitudCancelada(Solicitud sol)
    {
    	if(sol.getEstadoSolicitud().getDescripcion().equals("CANCELADA")|| sol.getEstadoSolicitud().getIdEstadoSolicitud()== 3 )
    	{
    		return "true";
    	}
    	return "false";
    }

    
    private void procesarInformacionConsultaStep01(Solicitud sol,
      StringBuffer listaNss      ){
      for(Tramite tramite:sol.getTramites()){
        	TramiteCorreccionCurp trCDA=(TramiteCorreccionCurp)tramite;
        	if(trCDA.getListaNssCorreccion() != null )
        	{
        		for(CorreccionNSS nss:trCDA.getListaNssCorreccion()){
		        	if(!listaNss.toString().equals("")){
		        		listaNss.append("<br>");
		        	}
		        	listaNss.append(nss.getNss());
		        }
        	}
        }
    }
    
    private ConsultaSolicitudTramiteVO procesarInformacionConsulta(
            Solicitud sol, Fisica persona, TramiteCorreccionCurp tramiteCDA) {
        ConsultaSolicitudTramiteVO informacionConsulta = new ConsultaSolicitudTramiteVO();

        informacionConsulta.setCurp(persona.getCurp());
        informacionConsulta
                .setFechaSolicitud(flujoTrabajoBusiness
                        .getTareaActivaPorIdTramite(tramiteCDA.getTramiteId()) != null ? flujoTrabajoBusiness
                        .getTareaActivaPorIdTramite(tramiteCDA.getTramiteId())
                        .getInicioTramite().getFechaSolicitud()
                        : sol.getFechaSolicitudParse());
        informacionConsulta.setFolio(sol.getNoFolioSolicitud());
        informacionConsulta.setNombre(persona.getNombreCompleto());
    
        
        StringBuffer listaNss = new StringBuffer("");
        procesarInformacionConsultaStep01(sol, listaNss);
        
        informacionConsulta.setNss(listaNss.toString());
        informacionConsulta
                .setStatus(tramiteCDA.getEstadoTramite().getDescripcion()
                        .toUpperCase().equals(TRAMITE_REASIGNADO) ? TRAMITE_REASIGNADO
                        : EstadoNegocioEnum
                                .obtenerDescripcionNegocio(tramiteCDA
                                        .getEstadoTramite()
                                        .getIdEstadoTramitePersona()));
        this.log.debug("---CDA--->> obtenerDescripcionNegocio {} ", 
                EstadoNegocioEnum.obtenerDescripcionNegocio(tramiteCDA
                                        .getEstadoTramite().getIdEstadoTramitePersona()));
        this.log.debug("---CDA--->> informacionConsulta.getStatus() {} ", 
                informacionConsulta.getStatus());
        this.log.debug("---CDA--->> tramiteCDA.getEstadoTramite().getDescripcion() {} ", 
                tramiteCDA.getEstadoTramite().getDescripcion());
        this.log.debug("---CDA--->> tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona() {} ", 
                tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona());
        informacionConsulta.setIdEstadoTramite(tramiteCDA.getEstadoTramite()
                .getIdEstadoTramitePersona());
        informacionConsulta
                .setSubdelegacion(sol.getSubdelegacion() != null ? sol
                        .getSubdelegacion().getClave()
                        + "-"
                        + sol.getSubdelegacion().getDescripcion() : "");
        informacionConsulta
                .setEstatusDescarga(sol.getEstadoSolicitud() != null ? sol
                        .getEstadoSolicitud().getIdEstadoSolicitud()
                        .equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())
                        : false);
        informacionConsulta.setIdTramite(tramiteCDA.getTramiteId().toString());

        return informacionConsulta;
    }

    private DomicilioCorto procesaDomicilioCorto(Solicitud sol,
            TramiteCorreccionCurp tramiteCDA) {
        DomicilioCorto domicilioSolicitud = new DomicilioCorto();
        if (CollectionUtils.isNotEmpty(tramiteCDA.getPersonaRENAPO()
                .getDomicilios())) {
            BeanUtils.copyProperties(tramiteCDA.getPersonaRENAPO()
                    .getDomicilios().get(0), domicilioSolicitud);
        }
        if (tramiteCDA.getMotivosAclaracion() != null) {
            log.debug("---CDA--- **********Si tiene motivos de aclaracion******** {} ");
            domicilioSolicitud
                    .setMotivoAclaracionVO(registroCorreccionCurpUtil
                            .obtenerMotivoAclaracion(tramiteCDA
                                    .getMotivosAclaracion()));
        } else {
            log.debug("---CDA--- **********No tiene motivos de aclaracion******** {} ");
        }

        return domicilioSolicitud;
    }

  

    @RequestMapping(value = "/init/{curp}", method = RequestMethod.GET)
    public String altaInit(Model model, SessionStatus sessionStatus,
            HttpSession session, @PathVariable String curp) {

        this.log.info("---CDA--- Datos limpiados de la sesion: OK, CURP: "
                + curp);

        try {

            List<Solicitud> solicitudes = registroSolicitudCorreccionDatosAseguradoBusiness
                    .obtenerSolicitudesPorCurp(curp);
            this.log.info("---CDA--- IdSolicitud: "
                    + solicitudes.get(0).getSolicitudId());
        } catch (CorreccionDatosAseguradoException ex) {
            this.log.error("---CDA--- Error al consultar la solicitud "
                    + ex.getMessage());
        }

        return RequestMappingConstants.VIEW_INICIAR_REGISTRO;
    }

    @RequestMapping(value = "/validarAccesoTramite/{curp}", method = RequestMethod.GET)
    @ResponseBody 
    public Map<String, ? extends Object> validarAccesotramite(
            Model model, HttpSession session, @PathVariable String curp) {
        this.comunLimpiarDatos(model, session);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("curp", curp);
        result.put(STATUS_ERROR_UPLOAD, false);

        return result;
    }

    @RequestMapping(value = "/datosAdicionalesHistoriaLaboral")
    public String consultaDatosAdicionalesHistoriaLaboral(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
        if (session.getAttribute(DATOS_ADICIONALES_HIST_LABORAL) != null) {
            datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
                    .getAttribute(DATOS_ADICIONALES_HIST_LABORAL);
        } else {
            datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
        }
        model.addAttribute(DATOS_ADICIONALES_HIST_LABORAL,
                datosAdicionalesHistoriaLaboral);
        return RequestMappingConstants.VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
    }

    private void consultaDatosAdicionalesHistoriaLaboralStep01(
      DomicilioCorto motivosAclaracion ){
      if (motivosAclaracion.getMotivoAclaracionVO() != null) {
        if (motivosAclaracion.getMotivoAclaracionVO()
                .getCreditoDescontado() != null) {
          String cd = motivosAclaracion.getMotivoAclaracionVO()
                  .getCreditoDescontado();
          motivosAclaracion.getMotivoAclaracionVO().setCreditoDescontado(
                  cd.toUpperCase());
        }
        if (motivosAclaracion.getMotivoAclaracionVO().getEspecificacion()
                != null) {
          String otro = motivosAclaracion.getMotivoAclaracionVO()
                  .getEspecificacion();
          motivosAclaracion.getMotivoAclaracionVO().setEspecificacion(
                  otro.toUpperCase());
        }
      }
    }
    
    private void consultaDatosAdicionalesHistoriaLaboralStep02(
      HttpSession session, Model model ){
      if (((DomicilioCorto) session.getAttribute(DOMICILIO_ACLARACION))
                .getMotivoAclaracionVO().getMotivosAclaracionIMSS() != null) {
            model.addAttribute("motivosIMSS", TiposAclaracionEnum
                    .obtenerMensajesPorClaves(
                            TiposAclaracionEnum.Dependencia.IMSS,
                            ((DomicilioCorto) session
                                    .getAttribute(DOMICILIO_ACLARACION))
                                    .getMotivoAclaracionVO()
                                    .getMotivosAclaracionIMSS()));
        }
        if (((DomicilioCorto) session.getAttribute(DOMICILIO_ACLARACION))
                .getMotivoAclaracionVO().getMotivosAclaracionInfonavit() != null) {
            model.addAttribute("motivosINFONAVIT", TiposAclaracionEnum
                    .obtenerMensajesPorClaves(
                            TiposAclaracionEnum.Dependencia.INFONAVIT,
                            ((DomicilioCorto) session
                                    .getAttribute(DOMICILIO_ACLARACION))
                                    .getMotivoAclaracionVO()
                                    .getMotivosAclaracionInfonavit()));
        }
        if (((DomicilioCorto) session.getAttribute(DOMICILIO_ACLARACION))
                .getMotivoAclaracionVO().getMotivosAclaracionAfore() != null) {
            model.addAttribute("motivosAFORE", TiposAclaracionEnum
                    .obtenerMensajesPorClaves(
                            TiposAclaracionEnum.Dependencia.AFORE,
                            ((DomicilioCorto) session
                                    .getAttribute(DOMICILIO_ACLARACION))
                                    .getMotivoAclaracionVO()
                                    .getMotivosAclaracionAfore()));
        }
    }
    
    @RequestMapping(value = "/validar/datosAdicionalesHistoriaLaboral", method = RequestMethod.POST)
    public String consultaDatosAdicionalesHistoriaLaboral(
            @ModelAttribute(DATOS_ADICIONALES_HIST_LABORAL) DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral,
            BindingResult result, Model model, HttpSession session)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        session.setAttribute(DATOS_ADICIONALES_HIST_LABORAL,
                datosAdicionalesHistoriaLaboral);

        log.debug("---CDA--- datosAdicionalesHistoriaLaboral {}",
                datosAdicionalesHistoriaLaboral);
        datosAdicionalesHistoriaLaboralValidator.validate(
                datosAdicionalesHistoriaLaboral, result);
        if (result.hasErrors()) {
            this.log.warn(ERRORES_DE_CAPTURA);
            return RequestMappingConstants.VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
        }

        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica personaSesion = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);

        DatosAdicionalesHistoriaLaboral contacto = datosAdicionalesHistoriaLaboral;
        TelefonoFijo fijo = new TelefonoFijo();
        TelefonoMovil movil = new TelefonoMovil();
        CorreoElectronico correo = new CorreoElectronico();
        String observaciones = "";
        if (contacto != null) {
            fijo.setNumero(contacto.getDatosContacto().getTelefonoFijo());
            personaSesion.setTelefonoFijo(fijo);
            movil.setNumero(contacto.getDatosContacto().getTelefonoCelular());
            personaSesion.setTelefonoMovil(movil);
            correo.setCorreo(contacto.getDatosContacto().getCorreoElectronico());
            personaSesion.setCorreoElectronico(correo);
            observaciones = contacto.getObservaciones().toUpperCase();
            ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                    .setObservacion(observaciones);
            solicitudBusiness.actualizaTramite(solicitud, solicitud
                    .getTramites().get(0));
        }

        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setPersonaRENAPO(personaSesion);
        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOS_HIST_LABORAL);
        DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(INFORMACION_HIST_LABORAL);
        datosHistoriaLaboral.setHistoriaLaboralGrid(informacionHistoriaLaboral
                .getHistoriaLaboralGrid());
        DomicilioCorto motivosAclaracion = (DomicilioCorto) session
                .getAttribute(DOMICILIO_ACLARACION);

        consultaDatosAdicionalesHistoriaLaboralStep01(motivosAclaracion);
        
        session.setAttribute(DOMICILIO_ACLARACION, motivosAclaracion);

        solicitud = this.actualizarSolicitud(solicitud, datosHistoriaLaboral,
                datosAdicionalesHistoriaLaboral, motivosAclaracion);
        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setObservacion(observaciones);
        log.debug("---CDA--- los datos de la solicitud son {}", solicitud);
        log.debug("---CDA--- los datos de tramite son {}", solicitud
                .getTramites().get(0));

        personaSesion.setTelefonoFijo(fijo);
        personaSesion.setTelefonoMovil(movil);
        try {
            solicitudBusiness.actualizarXmlTramite(solicitud.getTramites().get(
                    0));
            solicitud.getTramites().set(0, solicitud.getTramites().get(0));
            session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);
        } catch (TramiteNoEncontradoException ex) {
            log.debug("---CDA--- TramiteNoEncontradoException {}", ex);
        } catch (IllegalArgumentException ex) {
            log.debug("---CDA--- IllegalArgumentException {}", ex);
        }
        session.setAttribute(SessionConstants.ATTR_PERSONA_KEY, personaSesion);
        consultaDatosAdicionalesHistoriaLaboralStep02(session, model);
        return RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD;
    }

    @RequestMapping(value = "/datosHistoriaLaboral")
    public String consultaDatosHistoriaLaboral(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        DatosHistoriaLaboralVO datosHistoriaLaboral;

        if (session.getAttribute(DATOS_HIST_LABORAL) == null) {
            session.setAttribute(DATOS_HIST_LABORAL,
                    (DatosHistoriaLaboralVO) session
                            .getAttribute(INFORMACION_HIST_LABORAL));
        }
        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOS_HIST_LABORAL);
        datosHistoriaLaboral=(tramiteCorreccionUtil.getPrecargaAsegurado(datosHistoriaLaboral, solicitud));
        model.addAttribute(DATOS_HIST_LABORAL, datosHistoriaLaboral);

        Fisica fisica = session.getAttribute(SessionConstants.ATTR_PERSONA_KEY) != null ? (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY) : null;

        this.log.error("---CDA--- idPersona fisica en sesion: {}",
                fisica != null ? fisica.getIdPersona()
                        : "sin persona en sesion");

        cargarPantallaDatosHistoriaLaboral(model, session);
        return RequestMappingConstants.VIEW_DATOS_HISTORIA_LABORAL;
    }

    private void cargarPantallaDatosHistoriaLaboral(Model model,
            HttpSession session) {
        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOS_HIST_LABORAL);
        model.addAttribute(DATOS_HIST_LABORAL, datosHistoriaLaboralVO);
        model.addAttribute("documentosProbatoriosVo", documentoProbatorioUtil
                .getDocumentProbAsegurado(datosHistoriaLaboralVO.isDefuncion()));
    }

    @RequestMapping(value = "/informacionHistoriaLaboral")
    public String consultainformacionHistoriaLaboral(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        DatosHistoriaLaboralVO informacionHistoriaLaboral;
        if (session.getAttribute(INFORMACION_HIST_LABORAL) != null) {
            informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(INFORMACION_HIST_LABORAL);
        } else {
            informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
            session.setAttribute(INFORMACION_HIST_LABORAL,
                    informacionHistoriaLaboral);
        }
        informacionHistoriaLaboral.getHistoriaLaboralForm();
        model.addAttribute(INFORMACION_HIST_LABORAL,
                informacionHistoriaLaboral);
        return RequestMappingConstants.VIEW_INFORMACION_HISTORIA_LABORAL;
    }

    @RequestMapping(value = "/validar/informacionHistoriaLaboral", method = RequestMethod.POST)
    public String validateInformacionHistoriaLaboral(
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(INFORMACION_HIST_LABORAL) DatosHistoriaLaboralVO informacionHistoriaLaboral,
            BindingResult result, Model model) {
        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(INFORMACION_HIST_LABORAL);

        model.addAttribute(INFORMACION_HIST_LABORAL, datosHistoriaLaboral);
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        tramite.setDatosLaborales(registroCorreccionCurpUtil
                .cargarDatosLaborales(datosHistoriaLaboral
                        .getHistoriaLaboralGrid()));
        try {
            solicitudBusiness.actualizarXmlTramite(tramite);
        } catch (TramiteNoEncontradoException e) {
            log.error("---CDA--- No se encontro el Tramite {}", e);
        } catch (IllegalArgumentException e) {
            log.error("---CDA--- Ocurrio un error al actualizar el tramite {}",
                    e);
        }
        return consultaDatosHistoriaLaboral(model, session, request, response);
    }

    @RequestMapping(value = "/validar/infHistoriaLaboral", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> validateGridInformacion(
            @RequestBody DatosHistoriaLaboralVO datosHistoriaLaboralVO,
            HttpServletResponse response) {
        final Errors errors = new BindException(datosHistoriaLaboralVO, MODEL);

        if (!(datosHistoriaLaboralVO.getHistoriaLaboralGrid() != null && !datosHistoriaLaboralVO
                .getHistoriaLaboralGrid().isEmpty())) {
            errors.rejectValue("historiaLaboralForm",
                    "field.historiaLaboral.listaVacia");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
        }
        return result;
    }
    
    private String uploadBytesStep01(String tipoPer,
      String nombreCompleto, String ben, String nombre){
      if (tipoPer.equals("Beneficiario")) {
          nombreCompleto = ben.concat(nombre);
          log.debug("---CDA--- El nombre del documento queda:{}",
                  nombreCompleto);
      } else {
          nombreCompleto = nombre;
      }
      return nombreCompleto;
    }

    @RequestMapping(value = "/uploadify")
    public ResponseEntity<Map<String, Object>> uploadBytes(
            @RequestParam("fileData") MultipartFile file,
            @RequestParam String tipoPer,
            @RequestParam("idDocPorTipo") String idDocporTipo, HttpSession ses,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
        if (file.getSize() <= TAMANIO_MAXIMO_ARCHIVO) {
            Solicitud solicitud = (Solicitud) ses
                    .getAttribute(SessionConstants.ATTR_SOLICITUD);
            String nombreCompleto = "";
            String ben = "CDA_PI_" + idDocporTipo + "_";
            log.debug("---CDA--- #####Subiendo Archivo######");
            String nombre = idDocporTipo + "_" + file.getOriginalFilename();
            
            nombreCompleto = uploadBytesStep01(tipoPer, nombreCompleto, ben,
                    nombre);

            String[] n = file.getOriginalFilename().split("\\.");
            Fisica fisica = ses.getAttribute(SessionConstants.ATTR_PERSONA_KEY) != null ? (Fisica) ses
                    .getAttribute(SessionConstants.ATTR_PERSONA_KEY) : null;
            log.debug("---CDA--- Persona Fisica upload docto null?: {}",
                    fisica != null);
            if (fisica != null) {
                log.debug(
                        "---CDA--- Persona Fisica para iniciar upload de docto: {} idsolicitud: {}",
                        fisica.getIdPersona(), solicitud.getSolicitudId());

                String idDocBoveda = null;
                try {
                    idDocBoveda = bovedaBusiness.subirDocumento(
                            file.getBytes(), solicitud, nombreCompleto,
                            n[n.length - 1], file.getContentType());
                } catch (BovedaCDAException bce) {
                    log.error(bce.getSituacion());
                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                    result.put("__situacion_", bce.getSituacion());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("---CDA--- Error al subir el documento {}", e);
                    result.put(STATUS_ERROR_UPLOAD,
                            "Error al adjuntar Documento. Intente nuevamente.");
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.OK);
                }

                if (idDocBoveda != null) {
                    log.debug("---CDA--- documento insertado en boveda {}",
                            idDocBoveda);
                    result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                    result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);

                } else {
                    log.debug("---CDA--- Error en createDocument ::: el metodo regreso un error ");
                    result.put(STATUS_ERROR_UPLOAD,
                            "Error al adjuntar Documento. Intente nuevamente.");
                }
            } else {
                log.warn("---CDA--- El usuario no esta logeado idPersona null");
                result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
            }
        } else {
            result.put(
                    STATUS_ERROR_UPLOAD,
                    "El archivo no cumple con el tama&ntilde;o m&aacute;ximo permitido [4MB]. No es posible adjuntar el archivo.");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);

    }

    @RequestMapping(value = "/validar/NSS/{nssLength}", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> validateNSS(
            @RequestBody NSSVO nssvo, HttpServletResponse response,
            @PathVariable Integer nssLength, Model model) {
        final Errors errors = new BindException(nssvo, MODEL);

        log.debug("---CDA--- ############Validar NSS#####################");
        log.debug("---CDA--- NSS recibido : {} ", nssvo);
        log.debug("---CDA--- NSS list length : {} ", nssLength);

        Map<String, Object> result = new HashMap<String, Object>();

        nSSValidator.validateLengthListaNSS(errors, nssLength);

        if (!errors.hasErrors()) {
            nSSValidator.validate(nssvo, errors);
        }

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
        }

        log.debug("---CDA--- mapa resultado agregar NSS {}", result.toString());
        return result;
    }

    @RequestMapping(value = "/validar/historiaLaboral", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> validateHistoriaLaboral(
            @RequestBody HistoriaLaboralVO historiaLaboralVO,
            HttpServletResponse response, HttpSession session) {
        final Errors errors = new BindException(historiaLaboralVO, MODEL);

        log.debug("---CDA--- ############Validar Historia Laboral#####################");
        log.debug("---CDA--- Historia Laboral recibido : {} ",
                historiaLaboralVO);

        historiaLaboralValidator.validate(historiaLaboralVO, errors);

        Map<String, Object> result = new HashMap<String, Object>();

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
        } else {
            DatosHistoriaLaboralVO datosHistorialLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(INFORMACION_HIST_LABORAL);
            if (datosHistorialLaboral.getHistoriaLaboralGrid() == null) {
                datosHistorialLaboral
                        .setHistoriaLaboralGrid(new ArrayList<HistoriaLaboralVO>());
            }
            datosHistorialLaboral.getHistoriaLaboralGrid().add(
                    historiaLaboralVO);
        }

        log.debug("---CDA--- validar historiaLaboral {}", result.toString());
        return result;
    }
    
    private byte[] getComprobanteSolicitudStep01(byte[] docto,
      Solicitud sol, DatosHistoriaLaboralVO informacionHistoriaLaboral,
      DomicilioCorto motivoAclaracion ){
      if (docto == null) {
          log.info(
                  "---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID",
                  sol.getSolicitudId());
          docto = (byte[]) obtenerComprobanteSolicitud(sol,
                  informacionHistoriaLaboral, motivoAclaracion);

          try {
              String idDocumento = bovedaBusiness.subirDocumento(docto,
                      sol, TipoDocumentoCDAEnum.ACUSE);
              agregarIdDocumento(sol, idDocumento,
                      TipoDocumentoCDAEnum.ACUSE);
          } catch (BovedaCDAException bce) {
              log.error(bce.getSituacion(), bce);
          } catch (SolicitudNoEncontradaException e) {
              log.error(ERROR_ACTUALIZAR_XML, e);
          } catch (TramiteNoEncontradoException e) {
              log.error(ERROR_ACTUALIZAR_XML, e);
          } catch (IllegalArgumentException e) {
              log.error(ERROR_ACTUALIZAR_XML, e);
          }
      }
      return docto;
    }
    
    private void getComprobanteSolicitudStep02(byte[] docto,
            HttpServletResponse response,Solicitud sol ) throws IOException{
      if (docto != null) {

          log.debug("---CDA--- El documento no es nulo");

          response.addHeader("Accept-Ranges", "bytes");
          response.addHeader("Cache-Control", "public");
          response.addHeader("Cache-Control", "must-revalidate");
          response.addHeader("Pragma", "public");
          response.setContentType("application/pdf");
          response.addHeader("expires", "0");
          response.addHeader("Content-disposition",
                  "attachment;filename=\"" + sol.getNoFolioSolicitud()
                          + ".pdf\"");
          response.setContentLength(docto.length);
          response.getOutputStream().write(docto);
          response.flushBuffer();

      } else {

          this.log.debug(
                  "---CDA--- No se pudo obtener el acuse de la solicitud con folio {}",
                  sol.getNoFolioSolicitud());
      }
    }

    @RequestMapping(value = "/descargarComprobante", method = RequestMethod.GET)
    public void getComprobanteSolicitud(Model model,
            final HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        DomicilioCorto motivoAclaracion = (DomicilioCorto) session
                .getAttribute(DOMICILIO_ACLARACION);
        DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(INFORMACION_HIST_LABORAL);
        try {

            byte[] docto = null;
            try {
                docto = bovedaBusiness.recuperarDocumento(sol,
                        TipoDocumentoCDAEnum.ACUSE,
                        recuperarIdDocumento(sol, TipoDocumentoCDAEnum.ACUSE));
            } catch (BovedaCDAException bce) {
                log.error(bce.getSituacion());
            }

            docto = getComprobanteSolicitudStep01(docto, sol,
                    informacionHistoriaLaboral, motivoAclaracion);

            model.addAttribute(SessionConstants.ATTR_SOLICITUD, sol);
            getComprobanteSolicitudStep02(docto, response, sol);
            
        } catch (IOException e) {
            this.log.error(
                    "---CDA--- Error al generar el comprobante de asignacion de NSS -> ",
                    e);
        } catch (SolicitudNoEncontradaException e1) {
            this.log.error("---CDA--- Error al generar el acuse -> ", e1);
        }
        this.log.debug("---CDA--- Termina generacion de documentos de solicitud");
    }
    
    private Object obtenerComprobanteSolicitud(Solicitud solicitud,DatosHistoriaLaboralVO informacionHistoriaLaboral,DomicilioCorto motivoAclaracion) {
        log.debug("---CDA--- --------Se genera reporte de idSolicitud: --------------- "+ solicitud.getSolicitudId());
        byte[] repo = manejadorReportesBusiness.ejecutaReporte(registroCorreccionCurpUtil.obtenerParametros(solicitud),
                                                               registroCorreccionCurpUtil.obtenerParametrosComplemento(solicitud, 
                                                                                                                       informacionHistoriaLaboral, 
                                                                                                                       motivoAclaracion));
        log.debug("---CDA--- -----FIRMA ELECTRONICA----: "+ solicitud.getFirmaElectronica());
        return repo;
    }

    @RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
    public Object cancelarSolicitud(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {

        Solicitud solicitud = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        log.error("---CDA--- ---------Datos de la solicitud---------------------"
                + solicitud);
        if (solicitud != null) {
            try {
                log.debug("---CDA--- ---------------Entra metodo cancelar Solicitud---------------------"
                        + solicitud);
                registroSolicitudCorreccionDatosAseguradoBusiness
                        .cancelarSolicitud(solicitud);
                log.debug("---CDA--- ---------------Cancelacion Exitosa---------------------"
                        + solicitud);
            } catch (SolicitudNoEncontradaException e) {
                this.log.error(
                        "---CDA--- -----------------SolicitudNoEncontradaException-------------------------",
                        e);
            } catch (TramiteNoEncontradoException ex) {
                this.log.error(
                        "---CDA--- -----------------TramiteNoEncontradoException-------------------------",
                        ex);
            }
        }

        return new RedirectView("/"
                + request.getSession().getServletContext()
                        .getInitParameter("webAppRootKey")
                + "/atencionResponsable");
    }

    @RequestMapping(value = "/concluirSolicitud", method = RequestMethod.GET)
    public Object concluirSolicitud(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {
        log.debug("---CDA--- ******Entra al metodo de concluirSolicitud ************************");
        return new RedirectView("/"
                + request.getSession().getServletContext()
                        .getInitParameter("webAppRootKey")
                + "/atencionResponsable");

    }
    
    @RequestMapping(value = "/consultarSolicitud" + "/{folio}", method = RequestMethod.GET)
    public Object consultarSolicitud(Model model, HttpSession session, @PathVariable String folio,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {
        log.debug("---CDA--- ******Entra al metodo de concluirSolicitud ************************");
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        List<String> datos=Arrays.asList("1", sol.getNoFolioSolicitud());
        session.setAttribute(SessionConstants.CONSULTASOLICITUDUI_RESPONSABLE, datos);
        return new RedirectView("/"
                + request.getSession().getServletContext()
                        .getInitParameter("webAppRootKey")
                + "/atencionResponsable?folio="+folio);

    }
    
    

    private byte[] obtenerDocumentoBoveda(
            mx.gob.imss.cit.cda.web.app.responsable.model.Documento documentoEnviado)
            throws BovedaCDAException, Exception {
        log.debug("init obtener documento");
        byte[] documento = null;
        try {

            Solicitud sol = new Solicitud();
            sol.setNoFolioSolicitud(documentoEnviado.getFolio());
            sol.setSolicitudId(Long.parseLong(documentoEnviado.getIdPersona()));
            String idDocumento = documentoEnviado.getIdDocBoveda()
                    .equalsIgnoreCase(NULL) ? null : documentoEnviado
                    .getIdDocBoveda();
            try {
                documento = bovedaBusiness.recuperarDocumento(sol, idDocumento,
                        documentoEnviado.getNombreArchivo());
            } catch (BovedaCDAException bce) {
                log.error("---CDA--- ocurrio un error {}", bce.getSituacion(),
                        bce);
                throw bce;
            }

            return documento;
        } catch (Exception e) {
            log.error("---CDA--- Error: {}", e);
            throw e;
        }
    }

    @RequestMapping("/obtenerDocumento/{idPersona}/{folio}/{nombreArchivo}/{idDocBoveda}")
    public Object load(@PathVariable String idPersona,
            @PathVariable String folio, @PathVariable String nombreArchivo,
            @PathVariable String idDocBoveda, HttpServletResponse response) {
        mx.gob.imss.cit.cda.web.app.responsable.model.Documento input = new mx.gob.imss.cit.cda.web.app.responsable.model.Documento();
        input.setNombreArchivo(nombreArchivo);
        input.setFolio(folio);
        input.setIdPersona(idPersona);
        input.setIdDocBoveda(idDocBoveda);
        try {
            byte documento[] = obtenerDocumentoBoveda(input);

            if (documento != null) {
                response.addHeader(
                        "Content-Disposition",
                        "attachment; filename="
                                + URLEncoder.encode(input.getNombreArchivo(),
                                        "UTF-8"));
                response.setContentLength((int) documento.length);
                response.getOutputStream().write(documento);

                return null;
            } else {
                Map<String, Object> errores = new HashMap<String, Object>();
                errores.put(ERROR_BOVEDA, "Documento Nulo");
                errores.put(
                        ERROR_NEGOCIO,
                        MensajesBovedaCDAEnum
                                .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                        .getCodigo()));
                return new ModelAndView(
                        RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,
                        errores);
            }
        } catch (BovedaCDAException bce) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, bce.getSituacion());
            errores.put(ERROR_NEGOCIO, bce.getMensajeError());
            log.error("---CDA--- errores {}", errores);
            return new ModelAndView(
                    RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        } catch (Exception ioe) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, ioe.getMessage());
            errores.put(
                    ERROR_NEGOCIO,
                    MensajesBovedaCDAEnum
                            .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                    .getCodigo()));
            log.error("---CDA--- errores {}", errores);
            return new ModelAndView(
                    RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        }
    }

    
    private void enviarCorreoCorreccionDatosPorCurpStep01(Solicitud solicitud,
            byte[] adjuntos, String url, CorreoElectronicoDTO correoElectronicoDTO){
      if (((TramiteCorreccionCurp) solicitud.getTramites().get(0))
            .getPersonaRENAPO().getCorreoElectronico() != null
            && ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                    .getPersonaRENAPO().getCorreoElectronico().getCorreo() != null) {
        log.debug("---CDA--- Correo Derechohabiente: "
                + ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                        .getPersonaRENAPO().getCorreoElectronico()
                        .getCorreo());
        correoElectronicoDTO.setCorreoPara(new String[1]);
        correoElectronicoDTO.getCorreoPara()[0] = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0)).getPersonaRENAPO()
                .getCorreoElectronico().getCorreo();
        correoElectronicoDTO
                .setAsunto(StringEscapeUtils
                        .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
        correoElectronicoDTO.setCuerpoCorreo(registroSolicitudCorreoUtils
                .contenidoCorreoCDA(solicitud, url));
        log.debug("---CDA--- Contenido Correo Derechohabiente: "
                + (correoElectronicoDTO.getCuerpoCorreo()));
        if (adjuntos != null) {
            Map<String, byte[]> adjuntosMap = new HashMap<String, byte[]>();
            adjuntosMap.put("Documento.pdf", adjuntos);
            correoElectronicoDTO.setAdjuntos(adjuntosMap);
        }

        try {
            envioCorreoElectronicoBusinessRemote.enviarCorreo(
                    correoElectronicoDTO,
                    EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
        } catch (Exception e) {
            log.debug("---CDA--- Error al enviar correo ", e);
        }
      }
    
    }
    
    private void enviarCorreoCorreccionDatosPorCurpStep02(Solicitud solicitud,
      CorreoElectronicoDTO correoElectronicoDTO      ){
      try {
          List<Fisica> autorizadores = responsablesDelegacionRemote
                  .consultarAutorizadoresDelegacion(solicitud
                          .getSubdelegacion().getDelegacion().getId()
                          .intValue(), solicitud.getSubdelegacion()
                          .getId().intValue());
          for (Fisica autorizador : autorizadores) {
              correoElectronicoDTO = new CorreoElectronicoDTO();

              if (autorizador.getCorreoElectronico() != null
                      && autorizador.getCorreoElectronico().getCorreo() != null) {
                  correoElectronicoDTO.setCorreoPara(new String[1]);
                  correoElectronicoDTO.getCorreoPara()[0] = autorizador
                          .getCorreoElectronico().getCorreo();
                  log.debug("---CDA--- correoAutorizador: "
                          + autorizador.getCorreoElectronico()
                                  .getCorreo());
                  correoElectronicoDTO
                          .setAsunto(StringEscapeUtils
                                  .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
                  correoElectronicoDTO
                          .setCuerpoCorreo(registroSolicitudCorreoUtils
                                  .contenidoCorreoAutorizador(solicitud,
                                          autorizador));
                  log.debug("---CDA--- contenidoCorreoAutorizador: "
                          + correoElectronicoDTO.getCuerpoCorreo());
                  try {
                      envioCorreoElectronicoBusinessRemote
                              .enviarCorreo(
                                      correoElectronicoDTO,
                                      EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
                  } catch (Exception e) {
                      log.debug(
                              "---CDA--- Error al enviar correo Autorizador",
                              e);
                  }
              }
          }
      } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
          log.debug("---CDA--- Error al obtener autorizadores", e);
      }
    }
    
    private void enviarCorreoCorreccionDatosPorCurp(Solicitud solicitud,
            byte[] adjuntos, String curpResponsable, String url) {
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        log.debug("---CDA---url: {} ", url);
        enviarCorreoCorreccionDatosPorCurpStep01(solicitud, adjuntos, url,
                correoElectronicoDTO);

        if (StringUtils.isBlank(curpResponsable)) {

          enviarCorreoCorreccionDatosPorCurpStep02(solicitud,
                  correoElectronicoDTO);

        } else {
            correoElectronicoDTO = new CorreoElectronicoDTO();

            try {
                mx.gob.imss.ctirss.delta.model.Usuario responsable = responsablesDelegacionBusiness
                        .recuperaUsuarioEsquemaSeguridadByCURP(curpResponsable);

                if (responsable.getFisica().getCorreoElectronico() != null
                        && responsable.getFisica().getCorreoElectronico()
                                .getCorreo() != null) {
                    correoElectronicoDTO.setCorreoPara(new String[1]);
                    correoElectronicoDTO.getCorreoPara()[0] = responsable
                            .getFisica().getCorreoElectronico().getCorreo();
                    log.debug("---CDA--- correoResponsable: "
                            + responsable.getFisica().getCorreoElectronico()
                                    .getCorreo());
                    correoElectronicoDTO
                            .setAsunto(StringEscapeUtils
                                    .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
                    correoElectronicoDTO
                            .setCuerpoCorreo(registroSolicitudCorreoUtils
                                    .contenidoCorreoResponsable(solicitud,
                                            responsable));
                    log.debug("---CDA--- contenidoCorreoResponsable: "
                            + correoElectronicoDTO.getCuerpoCorreo());
                    try {
                        envioCorreoElectronicoBusinessRemote.enviarCorreo(
                                correoElectronicoDTO,
                                EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
                    } catch (Exception e) {
                        log.debug(
                                "---CDA--- Error al enviar correo Responsable",
                                e);
                    }
                }
            } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
                log.error(
                        "---CDA--- ErrorClienteWebserviceResponsablesSubdelegacionException {} ",
                        e);
            }
        }
    }

    @RequestMapping(value = "/registroSolicitudCDAResponsable")
    public Object registroSolicitudCDAResponsable(Model model,
            HttpSession session) {
        Fisica fisica = new Fisica();
        model.addAttribute("fisica", fisica);
        return RequestMappingConstants.VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
    }

    
    private ResponseEntity<Map<String, Object>> buscarPersonaRenapoStep01(
      Fisica persona, HttpSession ses, 
      ResponseEntity<Map<String, Object>> respuesta,
      Map<String, Object> result,
      HttpHeaders httpHeaders ){
      
      Solicitud solicitudActiva = null;
        List<String> curps = new ArrayList<String>();
        curps.add(persona.getCurp());
        if (persona.getCurpsHistoricas() != null) {
            curps.addAll(persona.getCurpsHistoricas());
        }
        solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness
                .obtenerUltimaSolicitudSeguimientoCDA(curps,
                        ORIGEN_SOLICITUD_VENTANILLA);
        this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva);

        if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
            this.log.debug("---CDA--- solicitud activa: {}",
                    solicitudActiva.getNoFolioSolicitud());
            solicitudActiva = registroCorreccionCurpUtil
                    .orderByTramite(solicitudActiva);
            ses.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitudActiva);
            ses.setAttribute(
                    SessionConstants.ATTR_PERSONA_KEY,
                    ((TramiteCorreccionCurp) solicitudActiva.getTramites().get(
                            0)).getPersonaRENAPO());
            result.put("vista",
                    "/wizard/correccionDatosAsegurado/seguimientoTramite");
        } else {
            if (solicitudActiva != null) {
                log.error("---CDA--- Se encontro mas de un resultado");
                result.clear();
                result.put(EXCEPTION_CONST,
                        "Se encontr\u00f3 m\u00e1s de un registro asociado a su CURP");
                respuesta = new ResponseEntity<Map<String, Object>>(result,
                        httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        ses.setAttribute(SessionConstants.ATTR_PERSONA_KEY, persona);
        return respuesta;
    }
    
    private ResponseEntity<Map<String, Object>> buscarPersonaRenapoStep02(
      ResponseEntity<Map<String, Object>> respuesta,
      boolean excepcion, Fisica personaRenapo,
      Map<String, Object> result,HttpHeaders httpHeaders ){
      
      if (personaRenapo == null && !excepcion) {
        result.put(
          EXCEPTION_CONST,
          "La CURP proporcionada no fue localizada en RENAPO. Por favor verifique la informaci\u00f3n capturada.");
        respuesta = new ResponseEntity<Map<String, Object>>(result,
                httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return respuesta;
      
    }
    
    private void buscarPersonaRenapoStep03(Map<String, Object> roles,
      Map<String, Object> mapNSS, Fisica persona,
      List<AsignacionNSS> valNSS ){

        mapNSS.putAll(roles);
        if (!roles.containsKey("NSS")) {
            if(valNSS != null){
                if (persona.getNss() == null || persona.getNss().equals("")) {
                    mapNSS.put(NSS, "NSS: " + valNSS.get(0).getNssStr());
                }
            }
        }
    }
    
    /**
     * 
     * @param persona
     * @param personaRenapo
     * @param result
     * @param respuesta
     * @param httpHeaders
     * @param excepcion
     * @return 
     * [0] Boolean, indicando si se debe aplicar un return al metodo o continuar
     * [1] Objeto respuesta
     * [2] Objeto personaRenapo
     */
    private Object[] buscarPersonaRenapoStep04(Fisica persona,
        Fisica personaRenapo, Map<String, Object> result,
        ResponseEntity<Map<String, Object>> respuesta,
        HttpHeaders httpHeaders,
        boolean excepcion){
      
      Object[] returnData = new Object[4];
      
      if (persona.getCurp() != null && !persona.getCurp().equals("")) {
                try {
                    personaRenapo = this.personaBusiness
                            .buscarPersonaFisicaPorCurpEnRenapo(persona
                                    .getCurp());
                } catch (ClienteWebserviceRenapoCurpException e) {
                    log.error(
                            "---CDA--- Error en la busqueda de persona por CURP {} en RENAPO",
                            persona.getCurp(), e);

                    result.put(
                            EXCEPTION_CONST,
                            "El servicio web de RENAPO no se encuentra disponible en este momento, por favor int\u00e9ntelo m\u00e1s tarde.");
                    respuesta = new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                    
                    returnData[0] = Boolean.TRUE;
                    returnData[1] = respuesta;
                    returnData[2] = personaRenapo;
                    return returnData;
                }                
                respuesta = buscarPersonaRenapoStep02(respuesta, excepcion,
                        personaRenapo, result, httpHeaders);                
            } else {
                try {
                    personaRenapo = this.personaBusiness
                            .buscarPersonaFisicaPorDatosBasicosEnRenapo(persona
                                    .getNombre(), persona.getPrimerApellido(),
                                    persona.getSegundoApellido(), persona
                                            .getSexo().getIdSexo(), persona
                                            .getFechaNacimiento(), Integer
                                            .parseInt(persona
                                                    .getLugarNacimiento()
                                                    .getClave()));
                } catch (Exception e) {
                    log.error(
                            "---CDA--- Error en la busqueda de persona datos basicos en RENAPO",
                            e);                    
                    result.put(
                            EXCEPTION_CONST,
                            "El servicio web de RENAPO no se encuentra disponible en este momento, por favor int\u00e9ntelo m\u00e1s tarde.");
                    respuesta = new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                    returnData[0] = Boolean.TRUE;
                    returnData[1] = respuesta;
                    returnData[2] = personaRenapo;
                    return returnData;
                }
                if (personaRenapo == null && !excepcion) {
                    log.debug("---CDA--- Solicitud sin CURP");
                    result.put(
                            "noResult",
                            "No se ha localizado una CURP en RENAPO asociada a los datos b\u00e1sicos ingresados, por favor verifique que la informaci\u00f3n capturada sea correcta.<br>Si desea registrar una solicitud sin CURP y los datos del interesado fueron verificados con el acta de nacimiento, seleccione la opci\u00f3n Aceptar.");
                    respuesta = new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.NOT_FOUND);
                    returnData[0] = Boolean.TRUE;
                    returnData[1] = respuesta;
                    returnData[2] = persona;
                    return returnData;
                }
            }
      
      
      returnData[0] = Boolean.FALSE;
      returnData[1] = respuesta;
      returnData[2] = personaRenapo;
      
      return returnData;
    
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registroVentanilla/buscarPersonaRenapo", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> buscarPersonaRenapo(Model model,
            @RequestBody Fisica persona, HttpSession ses,
            HttpServletRequest request, HttpServletResponse response) {

        inicioCorreccionCurp(model, ses);
        Map<String, Object> result = new HashMap<String, Object>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
                HttpStatus.OK);

        final Errors errors = new BindException(persona, MODEL);
        log.debug("---CDA--- ###Validar formulario de registro###");
        log.debug("---CDA--- datos de persona recibidos : {} ", persona);
        solicitudResponsableValidator.validate(persona, errors);
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            log.debug("---CDA--- mapa resultado validar persona {}", result);
            respuesta = new ResponseEntity<Map<String, Object>>(result,
                    httpHeaders, HttpStatus.PRECONDITION_FAILED);
        } else {
            Fisica personaRenapo = persona;
            boolean excepcion = false;
                    
                    
          Object[] buscarPersonaRenapoStep04 =
                  buscarPersonaRenapoStep04(persona, personaRenapo, result,
                          respuesta, httpHeaders, excepcion);
          if( (Boolean) buscarPersonaRenapoStep04[0] ){
              ses.setAttribute(SessionConstants.ATTR_PERSONA_KEY, buscarPersonaRenapoStep04[2]);
            return (ResponseEntity<Map<String, Object>> ) buscarPersonaRenapoStep04[1];
          }
          respuesta = (ResponseEntity<Map<String, Object>> ) buscarPersonaRenapoStep04[1];
          personaRenapo = (Fisica) buscarPersonaRenapoStep04[2];
            

            Map<String, Object> roles = registroSolicitudCorreccionDatosAseguradoBusiness
                    .personaAutorizadaRegistroCDA(persona.getCurp(),
                            persona.getCurpsHistoricas());
            if (roles != null && !roles.isEmpty()) {
                log.error("---CDA--- La persona no puede registrar una solicitud ya que no es Asegurado");
                excepcion = true;
                
                
                result.put(
                        EXCEPTION_CONST,
                 "El asegurado que va a realizar su registro de solicitud de regularizaci\u00f3n y/o correcci\u00f3n de datos personales, tiene el o los siguientes roles, por favor verifique la informaci\u00f3n en SINDO y BDTU correspondiente a cada rol, con el objeto de que no se generen inconsistencias derivado de la Correcci\u00f3n de Datos: ");
                  Map<String, Object> mapNSS = new HashMap<String, Object>();
                  List<AsignacionNSS> valNSS = personaBusiness.obtenerNsssByCurp(persona.getCurp());
                 // log.debug("---CDA--- valNSS {}", valNSS.toString());
                
                 buscarPersonaRenapoStep03(roles, mapNSS, persona, valNSS);
                /*    
                 log.debug("---CDA--- NSS: Prueba->",persona.getNss());*/
                 result.putAll(mapNSS);
                 log.debug("---CDA--- mapa resultado validar NSS {}", result);
                 log.debug("---CDA--- mapa resultado validar roles {}", roles);
                respuesta = new ResponseEntity<Map<String, Object>>(result,
                        httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                if (personaRenapo != null) {
                    persona = personaRenapo;
                }
            }

            if (personaRenapo != null && !excepcion) {
                persona = personaRenapo;
                result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                respuesta = new ResponseEntity<Map<String, Object>>(result,
                        httpHeaders, HttpStatus.OK);
            }
        }
        
        respuesta = buscarPersonaRenapoStep01(persona, ses, respuesta, result,
                httpHeaders);        
        return respuesta;
    }

    @RequestMapping(value = "/datosHistoriaLaboral/obtenerDocsProbatorios", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> obtenerDocumentos(
            @RequestBody DatosHistoriaLaboralVO datosHistoriaLaboralVO,
            HttpServletResponse response) {

        log.debug("************es defuncion o no       "
                + datosHistoriaLaboralVO.isDefuncion());
        log.debug("  * _ * _ * tipoBeneficiario {}"
                + datosHistoriaLaboralVO.getTipoBeneficiario());
        Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> doctos;
        if (datosHistoriaLaboralVO.getTipoBeneficiario().equals("")) {
            doctos = elegirDocsProbatoriosPorPersona(null);
        } else {
            Long beneficiario = TipoPersonaInteresadaSolEnum.valueOf(
                    datosHistoriaLaboralVO.getTipoBeneficiario()).getId();
            log.debug("enumeracion de tipo de beneficiario {}"
                    + beneficiario.intValue());
            doctos = elegirDocsProbatoriosPorPersona(beneficiario.intValue());
        }
        return doctos;

    }

    @RequestMapping("/generarCertificacion.do")
    public Object load(
            @ModelAttribute ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO,
            HttpServletResponse response) {

        log.debug("---CDA--- Generando Certificacion Id Tramite  {}",
                consultaSolicitudTramiteVO.getIdTramite());

        try {

            byte[] documento = obtenerCertificacionSolicitud(consultaSolicitudTramiteVO);

            if (documento != null) {
                response.addHeader(
                        "Content-Disposition",
                        "attachment; filename="
                                + consultaSolicitudTramiteVO.getFolio()
                                + ".pdf");
                response.setContentLength(documento.length);
                response.setContentType("application/pdf");
                response.getOutputStream().write(documento);

                return null;
            } else {
                Map<String, Object> errores = new HashMap<String, Object>();
                errores.put(ERROR_BOVEDA, "Documento Nulo");
                errores.put(
                        ERROR_NEGOCIO,
                        MensajesBovedaCDAEnum
                                .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                        .getCodigo()));
                return new ModelAndView(
                        RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,
                        errores);
            }
        } catch (BovedaCDAException bce) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, bce.getSituacion());
            errores.put(ERROR_NEGOCIO, bce.getMensajeError());
            return new ModelAndView(
                    RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);

        } catch (Exception ioe) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, ioe.getMessage());
            errores.put(
                    ERROR_NEGOCIO,
                    MensajesBovedaCDAEnum
                            .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                    .getCodigo()));
            return new ModelAndView(
                    RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        }
    }

    private Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> elegirDocsProbatoriosPorPersona(
            Integer tipoSolicitante) {
        List<DoctoReqTramite> documentos = documentoProbatorioServiceBusiness
                .getDocumentosRequeridosPorTipoTramite(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO
                        .getCodigo().longValue());

        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new LinkedHashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB;
        for (DoctoReqTramite doctoReqTramite : documentos) {
            Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo()
                    .getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio();
            TipoDocumentoProbatorio currMapKey = null;
            if (doctos.keySet() != null && !doctos.keySet().isEmpty()) {
                currMapKey = getCurrentMapKey(doctos, tipoDocto);
            }
            if (currMapKey == null) {
                currMapKey = doctoReqTramite.getDocumentoPorTipo()
                        .getTipoDocumentoProbatorio();
                doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
                log.debug("--CDA-- creando llave para tipo: {}",
                        currMapKey.getIdTipoDocumentoProbatorio());
            }
            DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
            docProbatorioVo.setTipoDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio());
            docProbatorioVo.setCveIdDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getDocumento().getCveIdDocumento());
            docProbatorioVo.setDesDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getDocumento().getDesDocumento());
            docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite
                    .getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
            if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO
                    .getId()
                    || (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION
                            .getId() && docProbatorioVo.getCveIdDocumento()
                            .intValue() != DocumentoEnum.CURP.getId())
                    || docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
                            .getId()
                    || docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.FORMATOS
                            .getId()) {
                log.debug("--CDA-- se agrega documento: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctos.get(currMapKey).add(docProbatorioVo);
            }
            if (tipoSolicitante != null) {
                addDocumentoPorTipoSolicitante(tipoSolicitante, doctos,
                        docProbatorioVo, currMapKey);
            }
        }

        doctosB = getDocumentosBeneficiario(documentos, tipoSolicitante);
        doctos = filtrarDocumentosRequeridos(doctos);
        doctosB = filtrarDocumentosRequeridos(doctosB);

        Map<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> doctosCompletos = new LinkedHashMap<String, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>>();
        doctosCompletos.put("Asegurado", doctos);
        doctosCompletos.put("Beneficiario", doctosB);
        return doctosCompletos;
    }

    private void addDocumentoPorTipoSolicitante(Integer tipoSolicitante,
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKey) {

        if (tipoSolicitante != 6 && tipoSolicitante != 1) {
            if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION
                    .getId()) {
                log.debug(
                        "--CDA-- se agrega documento de beneficiarios: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctos.get(currMapKey).add(docProbatorioVo);
            }
        }
    }

    private TipoDocumentoProbatorio getCurrentMapKey(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos,
            Integer tipoDocto) {
        TipoDocumentoProbatorio currMapKey = null;
        for (TipoDocumentoProbatorio key : doctos.keySet()) {
            if (tipoDocto.equals(key.getIdTipoDocumentoProbatorio())) {
                currMapKey = key;
                log.debug("--CDA-- se encontro la llave: {}",
                        key.getIdTipoDocumentoProbatorio());
            }
        }
        return currMapKey;
    }

    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> filtrarDocumentosRequeridos(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos) {

        List<TipoDocumentoProbatorio> keys = new ArrayList<TipoDocumentoProbatorio>();

        for (TipoDocumentoProbatorio key : doctos.keySet()) {
            if (doctos.get(key).isEmpty()) {
                keys.add(key);
            }
        }
        for (TipoDocumentoProbatorio tipoDocumentoProbatorio : keys) {
            doctos.remove(tipoDocumentoProbatorio);
        }

        return doctos;
    }

    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentosBeneficiario(
            List<DoctoReqTramite> documentos, Integer tipoSolicitante) {
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB = new LinkedHashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        for (DoctoReqTramite doctoReqTramite : documentos) {
            Integer tipoDoctoB = doctoReqTramite.getDocumentoPorTipo()
                    .getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio();
            TipoDocumentoProbatorio currMapKeyb = null;
            if (doctosB.keySet() != null && !doctosB.keySet().isEmpty()) {
                currMapKeyb = getCurrentMapKey(doctosB, tipoDoctoB);
            }
            if (currMapKeyb == null) {
                currMapKeyb = doctoReqTramite.getDocumentoPorTipo()
                        .getTipoDocumentoProbatorio();
                doctosB.put(currMapKeyb, new ArrayList<DocumentoProbatorio>());
                log.debug("--CDA-- creando llave para tipo bene: {}",
                        currMapKeyb.getIdTipoDocumentoProbatorio());
            }
            DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
            docProbatorioVo.setTipoDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio());
            docProbatorioVo.setCveIdDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getDocumento().getCveIdDocumento());
            docProbatorioVo.setDesDocumento(doctoReqTramite
                    .getDocumentoPorTipo().getDocumento().getDesDocumento());
            docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite
                    .getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
            if (tipoSolicitante != null) {
                addDocumentoPorTipoBeneficiario(tipoSolicitante, doctosB,
                        docProbatorioVo, currMapKeyb);
            }

        }
        return doctosB;
    }
    
    
    private void addDocumentoPorTipoBeneficiarioStep01(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb){
      if (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION
                .getId()
                && docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP
                        .getId()) {
            log.debug(
                    "--CDA-- se agrega documento identificacion bene: {}, {}",
                    docProbatorioVo.getCveIdDocumento(),
                    docProbatorioVo.getDesDocumento());
            doctosB.get(currMapKeyb).add(docProbatorioVo);
        }
    }
    
    private void addDocumentoPorTipoBeneficiarioStep02(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb){
      if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_MATRIMONIO
                    .getId()) {
                log.debug(
                        "--CDA-- se agrega ACTA DE MATRIMONIO EN BENE: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctosB.get(currMapKeyb).add(docProbatorioVo);
            }
    }
    
    private void addDocumentoPorTipoBeneficiarioStep03(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb){
      if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO
                    .getId()) {
                log.debug("--CDA-- se agrega documento hijo: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctosB.get(currMapKeyb).add(docProbatorioVo);
            }
    }
    
    private void addDocumentoPorTipoBeneficiarioStep04(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb){
      if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.CONSTANCIA_CONCUBINATO
                    .getId()) {
                log.debug("--CDA-- se agrega documento concubino: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctosB.get(currMapKeyb).add(docProbatorioVo);
            }
    }
    
    private void addDocumentoPorTipoBeneficiarioStep05(
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb){
      if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.PODER_NOTARIAL
                    .getId()) {
                log.debug("--CDA-- se agrega documento representante: {}, {}",
                        docProbatorioVo.getCveIdDocumento(),
                        docProbatorioVo.getDesDocumento());
                doctosB.get(currMapKeyb).add(docProbatorioVo);
            }
    }

    private void addDocumentoPorTipoBeneficiario(Integer tipoSolicitante,
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctosB,
            DocumentoProbatorio docProbatorioVo,
            TipoDocumentoProbatorio currMapKeyb) {

        addDocumentoPorTipoBeneficiarioStep01(doctosB, docProbatorioVo,
                currMapKeyb);
        switch (tipoSolicitante) {
        case 2:
            addDocumentoPorTipoBeneficiarioStep02(doctosB, docProbatorioVo,
                  currMapKeyb);
            break;
        case 3:
            addDocumentoPorTipoBeneficiarioStep03(doctosB, docProbatorioVo,
                  currMapKeyb);
            break;
        case 5:
            addDocumentoPorTipoBeneficiarioStep04(doctosB, docProbatorioVo,
                  currMapKeyb);
            break;
        case 6:
            addDocumentoPorTipoBeneficiarioStep05(doctosB, docProbatorioVo,
                  currMapKeyb);
            break;
        default:
            log.debug("---CDA--- No agrega nada");

        }
    }
    
    private byte[] obtenerCertificacionSolicitudStep01(
      byte[] documentoCertificado, Solicitud sol,
      ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO ) throws CorreccionDatosAseguradoException, NoExisteTareaUsuarioException, BovedaCDAException , CuentaIndividualNoDisponibleException{
    
      if (documentoCertificado == null) {
                Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas = new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();

                parametrosCuentas
                        .add(cuentaIndividualBusiness
                                .consultarMovimientosCuentaIndividual(((TramiteCorreccionCurp) sol
                                        .getTramites().get(0))
                                        .getPersonaRENAPO().getNss()));

                parametrosCuentas
                        .add(cuentaIndividualBusiness
                                .consultarUltimoMovimientoCuentaIndividual(((TramiteCorreccionCurp) sol
                                        .getTramites().get(0))
                                        .getPersonaRENAPO().getNss()));
                
               if(parametrosCuentas != null || !parametrosCuentas.isEmpty() )
               {
            	   FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness
                           .selloDigitalCertificacion(((TramiteCorreccionCurp) sol
                                   .getTramites().get(0)).getPersonaRENAPO(), sol,
                                   parametrosCuentas);
                   sol.setFirmaElectronica(firma);

                   documentoCertificado = manejadorReportesBusiness
                           .ejecutaReporteCertificacion(registroCorreccionCurpUtil
                                   .generarParametrosReporte(
                                           sol,
                                           flujoTrabajoBusiness
                                                   .obtenerParticipantesUltimaTareaByTramite(Long
                                                           .parseLong(consultaSolicitudTramiteVO
                                                                   .getIdTramite())),
                                           parametrosCuentas));

                   try {
                       String idDocumento = bovedaBusiness.subirDocumento(
                               documentoCertificado, sol,
                               TipoDocumentoCDAEnum.CERTIFICADO);
                       agregarIdDocumento(sol, idDocumento,
                               TipoDocumentoCDAEnum.CERTIFICADO);
                   } catch (BovedaCDAException bce) {
                       log.error(bce.getSituacion(), bce);
                       throw bce;
                   } catch (SolicitudNoEncontradaException e) {
                       log.error(ERROR_ACTUALIZAR_XML, e);
                   } catch (TramiteNoEncontradoException e) {
                       log.error(ERROR_ACTUALIZAR_XML, e);
                   } catch (IllegalArgumentException e) {
                       log.error(ERROR_ACTUALIZAR_XML, e);
                   }
               }                
            }
      return documentoCertificado;
    }

    private byte[] obtenerCertificacionSolicitud(
            ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO)
            throws BovedaCDAException, Exception {
        log.debug("---CDA--- Generando Solicitud Tramite {}",
                consultaSolicitudTramiteVO.getIdTramite());
        byte[] documentoCertificado = null;
        try {
            Solicitud sol = solicitudBusiness.consultarPorIdTramite(Long
                    .parseLong(consultaSolicitudTramiteVO.getIdTramite()));
            log.debug("---CDA--- Estado Solicitud {} - {}", sol
                    .getEstadoSolicitud().getDescripcion(), sol
                    .getEstadoSolicitud().getDescripcion());
            try {
                documentoCertificado = bovedaBusiness.recuperarDocumento(
                        sol,
                        TipoDocumentoCDAEnum.CERTIFICADO,
                        recuperarIdDocumento(sol,
                                TipoDocumentoCDAEnum.CERTIFICADO));
            } catch (BovedaCDAException bce) {
                log.error(bce.getSituacion(), bce);
            }
            documentoCertificado = obtenerCertificacionSolicitudStep01(
                    documentoCertificado, sol, consultaSolicitudTramiteVO);
            return documentoCertificado;
        } catch (Exception e) {
            log.error("Error: {}", e);
            throw e;
        }
    }

    private String recuperarIdDocumento(Solicitud sol,
            TipoDocumentoCDAEnum tipoDocumentoCDAEnum)
            throws SolicitudNoEncontradaException {
        TramiteCorreccionCurp tramite = null;
        if (sol.getTramites() != null) {
            log.debug(TRAMINTE_NO_NULO, sol.getTramites()
                    .get(0).getTramiteId());
            tramite = (TramiteCorreccionCurp) sol.getTramites().get(0);
            Solicitud solicitud = solicitudBusiness
                    .consultarPorIdTramite(tramite.getTramiteId());

            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        return tramite != null ? (tipoDocumentoCDAEnum
                .equals(TipoDocumentoCDAEnum.ACUSE) ? tramite
                .getIdDocumentoAcuse() : tramite.getIdDocumentoCertificacion())
                : null;
    }

    @RequestMapping(value = "/eliminarDocumentoBoveda", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, String>> eliminarDocumentoBoveda(
            @RequestBody Map<String, String> params, HttpSession session) {

        log.debug("---CDA---Llega a RSCDC con el parametro: "
                + params.get("idDocBoveda"));
        String confirmacion = null;
        Map<String, String> respuesta = new HashMap<String, String>();
        try {
            confirmacion = bovedaBusiness.eliminarDocumento(params
                    .get("idDocBoveda"));
            if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
                    .getCodigo())) {
                respuesta.put("success",
                        "El documento fue eliminado con \u00e9xito <br>"
                                + confirmacion);

                DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session
                        .getAttribute(params.get(VIEW));
                int index = Integer.parseInt(params.get("idRow"));
                if (datos != null) {
                    if (params.get("type").equals("listadoDocumentosGrid")) {
                        if (datos.getDocumentoProbatorioList() != null
                                && !datos.getDocumentoProbatorioList()
                                        .isEmpty()) {
                            datos.getDocumentoProbatorioList().remove(index);
                            session.setAttribute(params.get(VIEW), datos);
                        }
                    }
                }
            } else {
                respuesta.put(STATUS_ERROR_UPLOAD, confirmacion);
            }
        } catch (BovedaCDAException bce) {
            log.error("---CDA-- Ocurrio un error {}", bce.getSituacion());
            respuesta.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
        }

        return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminarListaDocsBoveda", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, String>> eliminarDocumentoBoveda(
            HttpSession session,
            @RequestBody Map<String, List<String>> listaIdsBovedaDocs) {

        List<String> idsBovedaDocs = listaIdsBovedaDocs.get("idsBovedaDocs");
        String confirmacion = "";
        Map<String, String> respuesta = new HashMap<String, String>();
        for (String idDocBoveda : idsBovedaDocs) {
            try {
                while (!confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
                        .getCodigo())
                        && !confirmacion
                                .contains(MensajesBovedaCDAEnum.MSJ_BP5004
                                        .getCodigo())) {
                    confirmacion = bovedaBusiness
                            .eliminarDocumento(idDocBoveda);
                }
            } catch (BovedaCDAException bce) {
                log.error(bce.getSituacion());
                confirmacion = bce.getSituacion();
            }
            respuesta.put(idDocBoveda, confirmacion);
        }

        DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOS_HIST_LABORAL);

        if (datos.getDocumentoProbatorioList() != null
                && !datos.getDocumentoProbatorioList().isEmpty()
                && datos.getNSSList() != null && !datos.getNSSList().isEmpty()) {
            datos.getNSSList().clear();
            datos.getDocumentoProbatorioList().clear();
        }
        return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<String> actualizarLista(
            HttpSession session, @RequestBody Map<String, String> params) {

        DatosHistoriaLaboralVO datos = (DatosHistoriaLaboralVO) session
                .getAttribute(params.get(VIEW));
        int index = Integer.parseInt(params.get("idRow"));

        if (datos != null) {
            if (params.get("type").equals("datosHistoriaLaboralGrid")) {
                if (datos.getHistoriaLaboralGrid() != null
                        && !datos.getHistoriaLaboralGrid().isEmpty()) {
                    datos.getHistoriaLaboralGrid().remove(index);
                    session.setAttribute(params.get(VIEW), datos);
                }
            } else if (params.get("type").equals("listadoNSSInvolucradosGrid")) {
                if (datos.getNSSList() != null && !datos.getNSSList().isEmpty()) {
                    datos.getNSSList().remove(index);
                    session.setAttribute(params.get(VIEW), datos);
                }
            }
        }

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/folioTramite", method = RequestMethod.GET)
    public String obtenerFolioTramite(Model model, HttpSession session) {
        log.debug("registro solicitud folioTramite");
        DatosFolioTramiteVO folio = new DatosFolioTramiteVO();
        model.addAttribute("folio", folio);
        return RequestMappingConstants.VIEW_FOLIO_TRAMITE;
    }
    
    
}
