package mx.gob.imss.cit.cda.web.registro.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
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
import mx.gob.imss.cit.cda.web.vo.DatosContacto;
import mx.gob.imss.cit.cda.web.vo.DatosFolioTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.MotivoAclaracionVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
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
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
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
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
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
@SessionAttributes(value = { SessionConstants.ATTR_ID_PERSONA, "sol", "personaCorreccion" })
public class RegistroSolicitudCorreccionDatosCurp extends AbstractController {
    
    private final Logger log = LoggerFactory.getLogger(RegistroSolicitudCorreccionDatosCurp.class);

    private static final int DIAS_MAXIMOS_ESPERA = 40;

    private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";
    
    private static final String PERSONA_SESSION_KEY = "persona_correccion";
    private static final String CONTINUACION_TRAMITE_KEY = "esContinuacionTramite";
    
    private static final String MOTIVOS_ACLARACION_IMSS = "motivosAclaracionIMSS";
    private static final String MOTIVOS_ACLARACION_INFONAVIT = "motivosAclaracionInfonavit";
    private static final String MOTIVOS_ACLARACION_AFORE = "motivosAclaracionAfore";
    private static final String STATUS_ERROR_UPLOAD = "error";
    private static final String STATUS_SUCCESS_UPLOAD = "success";
    private static final String TIPO_ID_USR = "IDPERSONA";
    private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";
    private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
    private static final String ID_DOCUMENTO_BOVEDA = "idDocBoveda";
    private static final String ERROR_BOVEDA = "errorBoveda";
    private static final String TRAMINTE_NO_NULO = "---CDA--- el tramite no es null {}";
    private static final String MODEL = "model";    
    private static final String ERRORES_DE_CAPTURA = "---CDA--- Errores de captura";
    private static final String ERROR_ACTUALIZAR_XML = "---CDA--- Error al actualizar XML  {}";
    private static final String ERROR_NEGOCIO = "errorNegocio";
    private static final String VIEW = "view";    

    private static final String EXTENCIONES_VALIDAS[] = { "gif", "tif", "jpg",
            "png", "pdf" };

    private static final String NULL = "null";

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
    private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusinessRemote;
    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionRemote;


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

    @ModelAttribute(SessionConstants.ATTR_ID_PERSONA)
    public Long getIdPersona() {
        return 0L;
    }

    @ModelAttribute("sol")
    public Solicitud getSolicitud() {
        return new Solicitud();
    }

    @ModelAttribute("personaCorreccion")
    public Fisica getFisica() {
        return new Fisica();
    }

    @Autowired
    @Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

    @Autowired
    @Qualifier("manejadorReportesBusiness")
    private ManejadorReportesRemote manejadorReportesBusiness;

    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    @Autowired
    private EnvioCorreoUtils correoUtils;


    @RequestMapping(value = "")
    public String inicioCorreccionCurp(Model model, HttpSession session) {

        model.addAttribute(SessionConstants.ATTR_TRAMITE_SEGURO, "");

        session.removeAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_TRAMITE_SEGURO);
        session.removeAttribute(SessionConstants.ATTR_SOLICITUD);
        session.removeAttribute(PERSONA_SESSION_KEY);
        session.removeAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);

        session.removeAttribute(CONTINUACION_TRAMITE_KEY);

        Fisica fisica = new Fisica();
        model.addAttribute(SessionConstants.ATTR_FISICA, fisica);
        return RequestMappingConstants.VIEW_LOGIN;
    }
    
    private Solicitud consultaDatosBasicosGobMXStep01( Fisica fisica, 
      List<String> curps, BindingResult result ) throws PortalCiudadanoException{
      Solicitud solicitudActiva = null;
      if (CollectionUtils.isNotEmpty(fisica.getCurpsHistoricas())) {
        curps.addAll(fisica.getCurpsHistoricas());
        validarCorreoCurpsHistoricas(fisica, curps);
      }

      solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness
              .obtenerUltimaSolicitudSeguimientoCDA(curps,
                      ORIGEN_SOLICITUD_INTERNET);

//                log.debug("-CDA-  SOLICITUD CREADA CON FOLIO: {}.......",solicitudActiva.getNoFolioSolicitud());
      Map<String, Object> roles =
              registroSolicitudCorreccionDatosAseguradoBusiness
                      .personaAutorizadaRegistroCDA(fisica.getCurp(),
                              fisica.getCurpsHistoricas());
      if (roles != null && !roles.isEmpty()
              && !validarSolicitudAtendida(solicitudActiva)) {
        log.error(
                "---CDA--- La persona no puede registrar una solicitud ya que no es Asegurado");
        result.reject("label.solicitud.mensaje.error.rol");
      }
      return solicitudActiva;
    }
    
    public Object consultaDatosBasicosGobMXStep02(BindingResult result,
      Solicitud solicitudActiva,Fisica fisica,HttpSession session){
      Object response = null;
      if (result.hasErrors()) {
        this.log.warn(ERRORES_DE_CAPTURA);
        return RequestMappingConstants.VIEW_LOGIN;
      }

      if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
        this.log.debug("---CDA--- solicitud activa: {}",
                solicitudActiva.getNoFolioSolicitud());
        solicitudActiva = registroCorreccionCurpUtil
                .orderByTramite(solicitudActiva);
        this.log.debug(
                "---CDA--- objeto Renapo: {}",
                ((TramiteCorreccionCurp) solicitudActiva.getTramites().get(
                        0)));
        ((TramiteCorreccionCurp) solicitudActiva.getTramites().get(0))
                .getPersonaRENAPO().setCorreoElectronico(
                        fisica.getCorreoElectronico());
        session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitudActiva);
        session.setAttribute(
                PERSONA_SESSION_KEY,
                ((TramiteCorreccionCurp) solicitudActiva.getTramites().get(
                        0)).getPersonaRENAPO());
        return new RedirectView(
                "/wizard/correccionDatosAsegurado/seguimientoTramite", true);
      } else {
        if (solicitudActiva != null) {
          log.error("---CDA--- Se encontro mas de un resultado");
          result.reject("label.registros.encontrados.asociados.curp");
          return RequestMappingConstants.VIEW_LOGIN;
        }
      }
      return response;
    }

    @RequestMapping(value = "/consultar", method = RequestMethod.POST)
    public Object consultaDatosBasicosGobMX(@ModelAttribute Fisica fisica,
            BindingResult result, Model model,
            @RequestParam("captcha") String captcha, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        log.debug("---CDA--- Error de comunicacion con RENAPO");

        String correoCapturado = fisica.getCorreoElectronico().getCorreo()
                .toLowerCase();
        fisica.getCorreoElectronico().setCorreo(correoCapturado);

        String correoConfirmacion = fisica.getCorreoElectronicoFiscal()
                .getCorreo().toLowerCase();
        fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

        CorreccionValidator validator = new CorreccionValidator();
        validator.validate(fisica, result);

        validarCaptcha(captcha, request, response, result);

        if (CollectionUtils.isNotEmpty(result.getAllErrors())) {
            return RequestMappingConstants.VIEW_LOGIN;
        }

        boolean curpCorreoValido = portalCiudadanoServiceBusinessRemote
                .validaRegistroCurpCorreoCiudadano(fisica.getCurp(), fisica
                        .getCorreoElectronico().getCorreo(), DateUtils.addDays(
                        new Date(), -DIAS_MAXIMOS_ESPERA));

        if (!curpCorreoValido) {
            fisica.setErrorFormGeneral("Es probable que el correo ya este asignado a otra curp");
            model.addAttribute(SessionConstants.ATTR_FISICA, fisica);
            return RequestMappingConstants.VIEW_LOGIN;
        }

        Solicitud solicitudActiva = null;

        try {

            Fisica personaRenapo = personaBusiness
                    .buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp());

            if (personaRenapo != null) {

                new PersonaRENAPOValidator().validate(personaRenapo, result);
                personaRenapo.setCorreoElectronico(fisica
                        .getCorreoElectronico());
                fisica = personaRenapo;

                List<String> curps = new ArrayList<String>();
                curps.add(fisica.getCurp());

                solicitudActiva = consultaDatosBasicosGobMXStep01(fisica, curps,
                        result);

            } else {
                result.reject("label.solicitud.mensaje.error.curp");
                return RequestMappingConstants.VIEW_LOGIN;
            }

        } catch (ClienteWebserviceRenapoCurpException ewsr) {
            log.error("---CDA--- Error de comunicacion con RENAPO", ewsr);
            result.reject("label.solicitud.mensaje.error.renapo");
        } catch (PortalCiudadanoException pex) {
            log.error(
                    "---CDA--- ----------ocurrio un error al realizar validacion CURP-Correo ",
                    pex);
            fisica.setErrorFormGeneral(pex.getMessage());
            model.addAttribute(SessionConstants.ATTR_FISICA, fisica);
            return RequestMappingConstants.VIEW_LOGIN;
        }

        Object respuesta = consultaDatosBasicosGobMXStep02(result,
                solicitudActiva, fisica, session);
        
        if( respuesta != null ){
          return respuesta;
        }

        this.log.error("---CDA--- CURP fisica en sesion: {}", fisica.getCurp());
        session.setAttribute(PERSONA_SESSION_KEY, fisica);
        return new RedirectView(
                "/wizard/correccionDatosAsegurado/obtenerInformacionRenapo",
                true);
    }

    @RequestMapping(value = "/seguimientoSolicitudCURP")
    public Object seguimientoSolicitudCURP(@RequestParam("CURP") String curp,
            HttpSession session, HttpServletRequest request) {
        final Errors result = new BindException(curp, MODEL);
        Fisica personaRenapo = null;
        Solicitud solicitudActiva = null;
        try {
            personaRenapo = personaBusiness
                    .buscarPersonaFisicaPorCurpEnRenapo(curp);
            if (personaRenapo == null) {
                result.reject("label.solicitud.mensaje.error.curp");
            }
            new PersonaRENAPOValidator().validate(personaRenapo, result);
        } catch (ClienteWebserviceRenapoCurpException e1) {
            log.error("---CDA--- Error de comunicacion con RENAPO", e1);
            result.reject("label.solicitud.mensaje.error.renapo");
        }

        if (result.hasErrors()) {
            this.log.warn(ERRORES_DE_CAPTURA);
            return RequestMappingConstants.VIEW_LOGIN;
        }

        List<String> curps = new ArrayList<String>();
        curps.add(personaRenapo.getCurp());
        if (personaRenapo.getCurpsHistoricas() != null) {
            curps.addAll(personaRenapo.getCurpsHistoricas());
        }

        solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness
                .obtenerUltimaSolicitudSeguimientoCDA(curps,
                        ORIGEN_SOLICITUD_INTERNET);
        this.log.debug("---CDA--- solicitud activa: {}", solicitudActiva);
        if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
            this.log.debug("---CDA--- solicitud activa: {}",
                    solicitudActiva.getNoFolioSolicitud());
            session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitudActiva);
            session.setAttribute(
                    PERSONA_SESSION_KEY,
                    ((TramiteCorreccionCurp) solicitudActiva.getTramites().get(
                            0)).getPersonaRENAPO());
            return new RedirectView(
                    "/wizard/correccionDatosAsegurado/seguimientoTramite", true);
        } else {
            if (solicitudActiva != null) {
                log.error("---CDA--- Se encontro mas de un resultado");
                result.reject("label.registros.encontrados.asociados.curp");
            }
        }

        return RequestMappingConstants.VIEW_LOGIN;

    }

    /**
     * Valida que el captcha capturado no sea una cadena vacia y que sea identico al guardado en la sesion
     */
    private void validarCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response, BindingResult result) {

        if (StringUtils.isBlank(captcha)) {
            FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "Campo requerido");
            result.addError(fieldError);
        } else {
            try {
                CaptchaSD captchaSD = new CaptchaSD(request, response);
                captchaSD.setCaptchaValue(captcha);
                CaptchaUtilSD.validaCaptachaSesion(captchaSD);
            } catch (CaptchaExceptionSD e) {
                this.log.error("---CDA--- Captcha no valido!!!" + e.getMessage());
                FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "El captcha no fue v\u00e1lido, favor de intentar nuevamente");
                result.addError(fieldError);
            }
        }
    }

    private boolean validarSolicitudAtendida(Solicitud sol) {
        boolean solicitudAtendida = sol != null;
        if (solicitudAtendida) {
            log.debug("---CDA---Solicitud {}", sol.getEstadoSolicitud()
                    .getIdEstadoSolicitud());
        }
        return solicitudAtendida
                && sol.getEstadoSolicitud().getIdEstadoSolicitud()
                        .equals(EstadoSolicitudEnum.ATENDIDA.getCodigo());

    }

    private void validarCorreoCurpsHistoricas(Fisica fisica,
            List<String> curpsHistoricas) throws PortalCiudadanoException {
        for (String curp : curpsHistoricas) {
            portalCiudadanoServiceBusinessRemote
                    .validaRegistroCurpCorreoCiudadano(curp, fisica
                            .getCorreoElectronico().getCorreo());
        }
    }

    @RequestMapping(value = "/obtenerInformacionRenapo", method = RequestMethod.GET)
    public String informacionRenapo(Model model, HttpSession session) {
        Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
        model.addAttribute(SessionConstants.ATTR_FISICA, persona);
        return RequestMappingConstants.VIEW_INFORMACION_RENAPO;
    }

    @RequestMapping(value = "/obtenerInformacionRenapo", method = RequestMethod.POST)
    public String consultaInformacionRenapo(Model model, HttpSession session) {
        Fisica solicitante = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);

        mx.gob.imss.ctirss.delta.model.Usuario usuario = new mx.gob.imss.ctirss.delta.model.Usuario();
        usuario.setCveIdUsuario(solicitante.getCurp());
        usuario.setUsuario(solicitante.getCurp());

        Solicitud solicitud;

        if (session.getAttribute(SessionConstants.ATTR_SOLICITUD) == null) {
            solicitud = guardarSolicitudInicial(solicitante,
                    OrigenSolicitudEnum.PORTAL_CIUDADANO, usuario);
            session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);
        }

        return capturaDomicilioAclaracion(model, session);
    }

    @RequestMapping(value = "/validar/noExisteSolicitud", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> validaNoExisteSolicitud(
            @RequestParam String curp, HttpSession session) {
        List<String> curps = new ArrayList<String>();
        curps.add(curp);

        Solicitud solicitudActiva = registroSolicitudCorreccionDatosAseguradoBusiness
                .obtenerUltimaSolicitudSeguimientoCDA(curps,
                        ORIGEN_SOLICITUD_INTERNET);

        Boolean continuacionTramite = (Boolean) session
                .getAttribute(CONTINUACION_TRAMITE_KEY);

        Map<String, Boolean> resultado = new HashMap<String, Boolean>();

        if (continuacionTramite != null && continuacionTramite) {
            resultado.put("noExisteSolicitud", true);
        } else {
            resultado.put("noExisteSolicitud",
                    solicitudActiva != null ? !solicitudActiva
                            .getEstadoSolicitud().getIdEstadoSolicitud()
                            .equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())
                            : true);
        }

        return resultado;
    }

    @RequestMapping(value = "/capturarDomicilio", method = RequestMethod.GET)
    public String capturaDomicilioAclaracion(Model model, HttpSession session) {
        DomicilioCorto domicilioAclaracion;
        if (session.getAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION) != null) {
            domicilioAclaracion = (DomicilioCorto) session.getAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        } else {
            domicilioAclaracion = new DomicilioCorto();

        }
        model.addAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION, domicilioAclaracion);
        model.addAttribute(MOTIVOS_ACLARACION_IMSS, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.IMSS));
        model.addAttribute(MOTIVOS_ACLARACION_INFONAVIT, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.INFONAVIT));
        model.addAttribute(MOTIVOS_ACLARACION_AFORE, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.AFORE));
        return RequestMappingConstants.VIEW_CAPTURAR_DOMICILIO;
    }

    private void capturaDomicilioAclaracionStep01(Fisica solicitante,
      mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite,
      HttpSession session ){
      TramiteCorreccionCurp tcda = (TramiteCorreccionCurp) tramite;
      if (tcda.getPersonaRENAPO() != null
              && tcda.getPersonaRENAPO().getMediosContacto() != null
              && !tcda.getPersonaRENAPO().getMediosContacto().isEmpty()
              && tcda.getPersonaRENAPO().getMediosContacto().size() > 1) {
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
        session.setAttribute(
                SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL,
                datosAdicionalesHistoriaLaboral);
      }
    }
    
    private void capturaDomicilioAclaracionStep02(HttpSession session,
      Solicitud solicitud, Model model){
      DatosHistoriaLaboralVO informacionHistoriaLaboral;
      if (session.getAttribute(
              SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL) != null) {
        informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.
                getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
      } else {
        informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
        session.
                setAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,
                        informacionHistoriaLaboral);
      }

      List<DatosLaborales> listDatosLaborales =
              ((TramiteCorreccionCurp) solicitud
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
          histLaboral.setClaveEntidad(datoLaboral.getEntidadFederativa()
                  .getClave());
          listHist.add(histLaboral);
        }
        informacionHistoriaLaboral.setHistoriaLaboralGrid(listHist);
      }
      model.addAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,
              informacionHistoriaLaboral);
    }
    
    @RequestMapping(value = "/capturarDomicilio", method = RequestMethod.POST)
    public RedirectView capturaDomicilioAclaracion(
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION) DomicilioCorto domicilioCorto,
            BindingResult result, Model model) {

        log.debug("---CDA--- ***********Subdelegacion******** {} ",
                domicilioCorto.getSubdelegacion());

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

        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
                .getTramites()) {
          capturaDomicilioAclaracionStep01(solicitante, tramite, session);
        }
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
        session.setAttribute(
        		SessionConstants.ATTR_SOLICITUD,
                actualizarXmlMotivos(solicitud,
                        domicilioCorto.getMotivoAclaracionVO()));
        session.setAttribute(SessionConstants.ATTR_SOLICITUD, actualizarTramites(solicitud));
        session.setAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION, domicilioCorto);

        if (((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .getPersonaRENAPO().getDomicilios() == null
                || ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                        .getPersonaRENAPO().getDomicilios().isEmpty()) {
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CAPTURAR_DOMICILIO, true);
        }

        capturaDomicilioAclaracionStep02(session, solicitud, model);
        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE + "/"
                        + RequestMappingConstants.VIEW_INFORMACION_HISTORIA_LABORAL, true);

    }
    
    private void actualizarListaHistorialStep01(Iterator<HistoriaLaboralVO> itera,
      HistoriaLaboralVO historiaLaboralVO ){
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
                    log.debug("---CDA--- historiaLaboralVO de vista {} ",
                            historiaLaboralVO);
                    log.debug(
                            "---CDA--- Se remueve de session historiaLaboralVO {} ",
                            datosLab);
                    itera.remove();
                }
            }
    
    }

    @RequestMapping(value = "/actualizarLista", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> actualizarListaHistorial(
            @RequestBody HistoriaLaboralVO historiaLaboralVO,
            HttpServletResponse response, HttpSession session, Model model) {

        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO) session
                .getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        if (documentoProb.getHistoriaLaboralGrid() != null
                && !documentoProb.getHistoriaLaboralGrid().isEmpty()) {
            Iterator<HistoriaLaboralVO> itera = documentoProb
                    .getHistoriaLaboralGrid().iterator();
            actualizarListaHistorialStep01(itera, historiaLaboralVO);
        }
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
                    log.debug("---CDA--- DatosLaborales {}", datosLab);
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

    private Solicitud actualizarXmlMotivos(Solicitud solicitud,
            MotivoAclaracionVO motivoAclaracionVO) {
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if (solicitud.getTramites() != null) {
            log.debug("---CDA--- Tramite {}", solicitud.getTramites().get(0)
                    .getTramiteId());
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        tramite.setMotivosAclaracion(registroCorreccionCurpUtil
                .cargarMotivosAclaracion(motivoAclaracionVO));
        try {
            solicitudBusiness.actualizarXmlTramite(tramite);
        } catch (TramiteNoEncontradoException e) {
            log.error("---CDA--- No se encontro el Tramite {}", e);
        } catch (IllegalArgumentException e) {
            log.error("---CDA--- Ocurrio un error al actualizar el tramite {}",
                    e);
        }

        return solicitud;
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

    @RequestMapping(value = "/asentamiento/get/codigoPostal", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> getAsentamientosPorCodigo(
            @RequestParam String codigo, HttpServletResponse response) {
        this.log.debug("---CDA--- codigo postal :{}" + codigo);
        Map<String, Object> result = new HashMap<String, Object>();
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal(codigo);

        Errors errors = new BindException(codigoPostal, MODEL);
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
                this.log.debug("---CDA--- Asentamientos[ " + asentamientos
                        + "]");
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

    private void generarListaSubdelegacionStep01(
      List<UnidadMedicaFamiliar> unidades, Set<String> clavesSubdelegacion,
      List<MunicipioIMSS> unidadesEstado, List<Subdelegacion> subdelegaciones ){
      if (unidades != null && !unidades.isEmpty()) {
        for (UnidadMedicaFamiliar unidad : unidades) {
          if (clavesSubdelegacion.add(unidad.getSubdelegacion()
                  .getClave())) {
            subdelegaciones.add(unidad.getSubdelegacion());
          }
        }
      }
      if (unidadesEstado != null && !unidadesEstado.isEmpty()) {
        for (MunicipioIMSS unidad : unidadesEstado) {
          if (clavesSubdelegacion.add(unidad.getSubdelegacion()
                  .getClave())) {
            subdelegaciones.add(unidad.getSubdelegacion());
          }
        }
      }
    }
    
    private List<Subdelegacion> generarListaSubdelegacion(String codigoPostal,
            Municipio municipio) throws UmfNoLocalizadaException,
            MunicipioImssNoLocalizadoException {

        List<UnidadMedicaFamiliar> unidades = domicilioServiceBusinessRemote
                .getUmfByCodigoPostal(codigoPostal);

        List<MunicipioIMSS> unidadesEstado = null;

        if (unidades == null || unidades.isEmpty()) {
            Municipio municipioClon = new Municipio();
            BeanUtils.copyProperties(municipio, municipioClon);
            municipioClon.setClave("");
            unidadesEstado = this.domicilioServiceBusinessRemote
                    .getMunicipioIMSSbyEstadoMunCP(municipioClon, codigoPostal);
        }

        List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
        Set<String> clavesSubdelegacion = new HashSet<String>();
        generarListaSubdelegacionStep01(unidades, clavesSubdelegacion,
              unidadesEstado, subdelegaciones);

        Collections.sort(subdelegaciones, new Comparator<Subdelegacion>() {

            @Override
            public int compare(Subdelegacion o1, Subdelegacion o2) {
                return o1.getClave().compareToIgnoreCase(o2.getClave());
            }
        });

        return subdelegaciones;
    }

    public Solicitud guardarSolicitudInicial(Fisica solicitante,
            OrigenSolicitudEnum origenSolicitud,
            mx.gob.imss.ctirss.delta.model.Usuario usuario) {
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
        log.debug("SE CREA EL GUARDADO INICIAL DE LA SOLICITUD {}", solicitud);

        try {
            portalCiudadanoServiceBusinessRemote.validarInicioCurpCorreo(
                    solicitante.getCurp(), solicitante.getCorreoElectronico()
                            .getCorreo(), true);
        } catch (Exception e) {
            log.error("---CDA---  Error actualizacion correo", e);
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
            log.debug(TRAMINTE_NO_NULO, solicitud
                    .getTramites().get(0).getDocumentosProbatorios());
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
        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
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

        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);

        try {
        	List<InicioTramite> listaInicioTramite = iniciarWorkFlow(sol);
            this.registroSolicitudCorreccionDatosAseguradoBusiness
                    .finalizaRegistroCorreccionDatosAsegurados(sol,
                            listaInicioTramite, null,
                            OrigenCapturaCDAEnum.SOLICITUD.getClave());
        } catch (CorreccionDatosAseguradoException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (SolicitudNoEncontradaException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (TramiteNoEncontradoException e1) {
            log.error("---CDA--- Ocurrio un error {}", e1);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (RegistrarDocumentoProbatorioException e) {
            log.error("---CDA--- Error en el registro de los documentos ", e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (Exception e) {
            log.error("---CDA--- Error ", e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        }

        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                        + RequestMappingConstants.VIEW_GENERAR_ACUSE, true);

    }

    @RequestMapping(value = RequestMappingConstants.VIEW_GENERAR_ACUSE, method = RequestMethod.GET)
    public RedirectView generarAcuse(Model model, HttpSession session,
            HttpServletRequest request) {

        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica personaCorreccion = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
        DatosHistoriaLaboralVO documentoProb = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        DomicilioCorto motivosAclaracion = (DomicilioCorto) session.getAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);

        List<InicioTramite> listaInicioTramite = null;

        byte[] adjuntos = null;

        log.debug("Se llevo a cabo iniciarWorkFlow se procede a generar comprobante");

        //se genera comprobante en boveda antes guardar en BD, en caso de error el tramite no continua
        try {
        	listaInicioTramite = iniciarWorkFlow(sol);
            FirmaElectronica firma = registroSolicitudCorreccionDatosAseguradoBusiness.selloDigital(personaCorreccion, sol);
            sol.setFirmaElectronica(firma);
            session.setAttribute(SessionConstants.ATTR_SOLICITUD, sol);
            adjuntos = (byte[]) obtenerComprobanteSolicitud(sol, documentoProb, motivosAclaracion);
            String idDocumento = bovedaBusiness.subirDocumento(adjuntos, sol, TipoDocumentoCDAEnum.ACUSE);
            agregarIdDocumento(sol, idDocumento, TipoDocumentoCDAEnum.ACUSE);
        } catch (BovedaCDAException bce) {
            log.error(bce.getSituacion(), bce);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (SolicitudNoEncontradaException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (TramiteNoEncontradoException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (IllegalArgumentException e) {
            log.error(ERROR_ACTUALIZAR_XML, e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (CorreccionDatosAseguradoException e) {
            log.warn("---CDA---  Error al generar la firma  {}", e);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        } catch (Exception e) {
        	 log.warn("---CDA---  Error al generar la firma  {}", e);
        	 return new RedirectView(
                     RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                             + "/" + RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD, true);
        }

        log.debug("Enviando correo, documentos adjuntos" + Arrays.toString(adjuntos));
        // envia de correo electronico
        String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
        enviarCorreoCorreccionDatosPorCurp(sol, adjuntos, listaInicioTramite.get(0).getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), url);

        //antes de regresar respuesta se revisa que la solicitud y su tramite tenga los estados correctos
        log.debug("**** Buscando la solicitud en base de datos folio: " + sol.getNoFolioSolicitud());
        try{
        	sol = solicitudBusiness.consultarPorFolioSolicitud(sol.getNoFolioSolicitud());

        	TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) sol.getTramites().get(0);
            log.debug("---CDA--- Tramites en solicitud {}", tramiteCDA.getMotivosAclaracion().size());

            // si la solicitud tiene estado correcto pero el tramite esta en iniciado
    		if (sol.getEstadoSolicitud().getIdEstadoSolicitud()
    				.equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())
    				&& tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona()
    						.equals(EstadoTramiteEnum.INICIADO.getCodigo())) {
    			log.debug("::: Error en el estado del tramite, getIdEstadoSolicitud: "
    					+ sol.getEstadoSolicitud().getIdEstadoSolicitud()
    					+ " getIdEstadoTramitePersona(): "
    					+ tramiteCDA.getEstadoTramite().getIdEstadoTramitePersona());
    			//se cancela la solicitud y se reeenvia a pantalla de error
    	        return new RedirectView(
    	                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
    	                        + "/cancelaSolicitudPorErrorEstatus", true);
    		}
        }catch(SolicitudNoEncontradaException e){
        	e.printStackTrace();
        	return new RedirectView(
	                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
	                        + "/confirmarDatosSolicitud", true);
		}

        model.addAttribute("sol", sol);
        model.addAttribute("personaCorreccion", personaCorreccion);
        return new RedirectView(
                RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE + "/"
                        + RequestMappingConstants.VIEW_FOLIO_TRAMITE, true);
    }


    @RequestMapping(value = "/cancelaSolicitudPorErrorEstatus", method = RequestMethod.GET)
    public String cancelaSolicitudPorErrorEstatus(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {

        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        log.error("---CDA--- ---------Cancelando la solicitud por error en el estatus del tramite---------------------"
                + solicitud.getSolicitudId());
        if (solicitud != null) {
            try {
                registroSolicitudCorreccionDatosAseguradoBusiness
                        .cancelarSolicitud(solicitud);
                log.debug("---CDA--- ---------------Cancelacion Exitosa---------------------"
                        + solicitud.getSolicitudId());
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

        model.addAttribute(SessionConstants.ATTR_TRAMITE_SEGURO, "");
        session.removeAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_TRAMITE_SEGURO);
        session.removeAttribute(SessionConstants.ATTR_SOLICITUD);
        session.removeAttribute(PERSONA_SESSION_KEY);
        session.removeAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        session.removeAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);

        session.removeAttribute(CONTINUACION_TRAMITE_KEY);

        Fisica fisica = new Fisica();
        fisica.setErrorFormGeneral("Ocurri\u00F3 un error al procesar su solicitud, por favor intente m\u00E1s tarde");
        model.addAttribute(SessionConstants.ATTR_FISICA, fisica);
        return RequestMappingConstants.VIEW_LOGIN;

    }



    private List<InicioTramite> iniciarWorkFlow(Solicitud solicitud) {

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
                    : inicioTramiteWorkFlow(solicitud, tramiteCDA));

            listaInicioTramite.add(inicioTramite);
        }

        return listaInicioTramite;
    }

    private InicioTramite inicioTramiteWorkFlow(Solicitud solicitud,
            TramiteCorreccionCurp tramiteCDA) {

        List<Fisica> autorizador = new ArrayList<Fisica>();
        List<Fisica> listResponsable = new ArrayList<Fisica>();
        log.debug(
                "---CDA--- se asigna la solicitud {} para la subdelegacion y la delegacion {}",
                new Object[] { solicitud.getSolicitudId(),
                        solicitud.getSubdelegacion().getId(),
                        solicitud.getSubdelegacion().getDelegacion().getId() });
        Fisica fisicaAutorizador = new Fisica();
        fisicaAutorizador.setCurp(FlujoTrabajoConstants.BPM_ADMIN
                + solicitud.getSubdelegacion().getId());
        autorizador.add(fisicaAutorizador);
        String ultimoResponsable = flujoTrabajoBusiness
                .getCurpResponsable(solicitud.getSubdelegacion().getId()
                        .toString());

        log.debug("---CDA---EL CURP OBTENIDO DEL RESP ES {}", ultimoResponsable);
        log.debug("---CDA---EL CURP OBTENIDO DEL autorizador es ES {}",
                autorizador.get(0).getCurp());

        try {
            listResponsable = responsablesDelegacionRemote
                    .consultarResponsablesDelegacion(solicitud
                            .getSubdelegacion().getDelegacion().getId()
                            .intValue(), solicitud.getSubdelegacion().getId()
                            .intValue());
            log.debug("---CDA--- balanceador {}", listResponsable.size());

            for (Fisica curp : listResponsable) {
                log.debug("---CDA--- curps de los responsables {}",
                        curp.getCurp());
            }

        } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
            log.error("---CDA--- Error al consultar webservice {} ", e);
            Fisica fisicaResponsable = new Fisica();
            fisicaResponsable.setCurp(SUBDELEGACION_SIN_RESPONSABLE);
            listResponsable.add(fisicaResponsable);
        }

        return WorkFlowDataUtil.iniciarTramite(solicitud, autorizador,
                tramiteCDA, listResponsable, ultimoResponsable);
    }

    @RequestMapping(value = "/folioTramite", method = RequestMethod.GET)
    public String obtenerFolioTramite(Model model, HttpSession session) {
        DatosFolioTramiteVO folio = new DatosFolioTramiteVO();

        model.addAttribute("folio", folio);
        return RequestMappingConstants.VIEW_FOLIO_TRAMITE;
    }

    private void seguimientoTramiteStep01(MotivoAclaracion motivoAclaracion,
      MotivoAclaracionVO motivoAclaracionVO){
      if (motivoAclaracion.getInstitucion().getIdInstitucion()
              == InstitucionEnum.IMSS
                      .getId()) {
        motivoAclaracionVO
                .getMotivosAclaracionIMSS()
                .add(TiposAclaracionEnum
                        .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                motivoAclaracion
                                        .getIdMotivoAclaracion())
                        .getClave());
      }

      if (motivoAclaracion.getInstitucion().getIdInstitucion()
              == InstitucionEnum.AFORE
                      .getId()) {
        motivoAclaracionVO
                .getMotivosAclaracionAfore()
                .add(TiposAclaracionEnum
                        .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                motivoAclaracion
                                        .getIdMotivoAclaracion())
                        .getClave());
      }
      if (motivoAclaracion.getInstitucion().getIdInstitucion()
              == InstitucionEnum.INFONAVIT
                      .getId()) {
        motivoAclaracionVO
                .getMotivosAclaracionInfonavit()
                .add(TiposAclaracionEnum
                        .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                motivoAclaracion
                                        .getIdMotivoAclaracion())
                        .getClave());
        if (motivoAclaracion.getIdMotivoAclaracion()
                == MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO
                        .getId()) {
          motivoAclaracionVO
                  .setCreditoDescontado(motivoAclaracion
                          .getDetalleAclaracion());
        }
      }

      if (motivoAclaracion.getInstitucion().getIdInstitucion()
              == InstitucionEnum.OTRO
                      .getId()
              && motivoAclaracion.getIdMotivoAclaracion()
              == MotivoAclaracionEnum.OTRO
                      .getId()) {
        motivoAclaracionVO.setOtro(TiposAclaracionEnum
                .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                        motivoAclaracion.getIdMotivoAclaracion())
                .getDependencia().toLowerCase());
        motivoAclaracionVO.setEspecificacion(motivoAclaracion
                .getDetalleAclaracion());
      }
    }
    
    @RequestMapping(value = "/seguimientoTramite")
    public String seguimientoTramite(Model model, HttpSession session) {

        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica persona = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
        TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        DomicilioCorto domicilioSolicitud = new DomicilioCorto();

        if (CollectionUtils.isNotEmpty(tramiteCDA.getPersonaRENAPO()
                .getDomicilios())) {
            BeanUtils.copyProperties(tramiteCDA.getPersonaRENAPO()
                    .getDomicilios().get(0), domicilioSolicitud);
            domicilioSolicitud.setSubdelegacion(sol.getSubdelegacion());
        }

        if (tramiteCDA.getMotivosAclaracion() != null) {
            log.debug("---CDA--- **********Si tiene motivos de aclaracion******** {} ");

            MotivoAclaracionVO motivoAclaracionVO = new MotivoAclaracionVO();
            motivoAclaracionVO
                    .setMotivosAclaracionIMSS(new ArrayList<String>());
            motivoAclaracionVO
                    .setMotivosAclaracionAfore(new ArrayList<String>());
            motivoAclaracionVO
                    .setMotivosAclaracionInfonavit(new ArrayList<String>());

            List<MotivoAclaracion> motivos = tramiteCDA.getMotivosAclaracion();

            for (MotivoAclaracion motivoAclaracion : motivos) {

              seguimientoTramiteStep01(motivoAclaracion, motivoAclaracionVO);

            }

            domicilioSolicitud.setMotivoAclaracionVO(motivoAclaracionVO);

        } else {
            log.debug("---CDA--- **********No tiene motivos de aclaracion******** {} ");
        }

        ConsultaSolicitudTramiteVO informacionConsulta = registroCorreccionCurpUtil.procesarInformacionConsulta(
                persona, sol, tramiteCDA);

        sol.setPersonaInteresada(tramiteCDA.getPersonaRENAPO());

        session.setAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION, domicilioSolicitud);
        this.log.debug("---CDA--- Estado Solicitud {} Estado Tramite {} ", sol
                .getEstadoSolicitud().getIdEstadoSolicitud(), sol.getTramites()
                .get(0).getEstadoTramite().getIdEstadoTramitePersona());

        model.addAttribute("informacionConsulta", informacionConsulta);
        model.addAttribute("banderaContinuarTramite", "false");
        session.setAttribute(CONTINUACION_TRAMITE_KEY, false);

        if (sol.getEstadoSolicitud().getIdEstadoSolicitud()
                .equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())
                && sol.getTramites().get(0).getEstadoTramite()
                        .getIdEstadoTramitePersona()
                        .equals(EstadoTramiteEnum.INICIADO.getCodigo())) {
            model.addAttribute("banderaContinuarTramite", "true");
            session.setAttribute(CONTINUACION_TRAMITE_KEY, true);
        }

        return RequestMappingConstants.VIEW_SEGUIMIENTO_TRAMITE;
    }


    @RequestMapping(value = "/registroSolicitudCDAResponsable")
    public Object registroSolicitudCDAResponsable(Model model,
            HttpSession session) {

        DatosAseguradoVO datosAsegurado = new DatosAseguradoVO();

        model.addAttribute("datosAsegurado", datosAsegurado);
        return RequestMappingConstants.VIEW_CAPTURAR_SOLICITUD_RESPONSABLE;
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

    @RequestMapping(value = "/comunes/limpiarDatos")
    @ResponseBody 
    public Map<String, ? extends Object> comunLimpiarDatos(Model model, HttpSession session) {

        model.addAttribute(SessionConstants.ATTR_TRAMITE_SEGURO, "");

        session.removeAttribute(SessionConstants.ATTR_TRAMITE_SEGURO);
        session.removeAttribute(SessionConstants.ATTR_SOLICITUD);
        session.removeAttribute(PERSONA_SESSION_KEY);
        session.removeAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        session.removeAttribute(CONTINUACION_TRAMITE_KEY);

        this.log.info("---CDA--- Datos limpiados de la sesion: OK");
        return null;
    }

    @RequestMapping(value = "/datosAdicionalesHistoriaLaboral")
    public String consultaDatosAdicionalesHistoriaLaboral(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
        if (session.getAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL) != null) {
            datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session.getAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL);
        } else {
            datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
        }
        model.addAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL,datosAdicionalesHistoriaLaboral);
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
      Model model, HttpSession session){
      if (((DomicilioCorto) session.getAttribute(
              SessionConstants.ATTR_DOMICILIO_ACLARACION)).
              getMotivoAclaracionVO().getMotivosAclaracionIMSS() != null) {

        model.addAttribute("motivosIMSS", TiposAclaracionEnum
                .obtenerMensajesPorClaves(
                        TiposAclaracionEnum.Dependencia.IMSS,
                        ((DomicilioCorto) session
                                .getAttribute(
                                        SessionConstants.ATTR_DOMICILIO_ACLARACION)).
                                getMotivoAclaracionVO()
                                .getMotivosAclaracionIMSS()));
      }
      if (((DomicilioCorto) session.getAttribute(
              SessionConstants.ATTR_DOMICILIO_ACLARACION)).
              getMotivoAclaracionVO().getMotivosAclaracionInfonavit() != null) {
        model.addAttribute("motivosINFONAVIT", TiposAclaracionEnum
                .obtenerMensajesPorClaves(
                        TiposAclaracionEnum.Dependencia.INFONAVIT,
                        ((DomicilioCorto) session
                                .getAttribute(
                                        SessionConstants.ATTR_DOMICILIO_ACLARACION)).
                                getMotivoAclaracionVO()
                                .getMotivosAclaracionInfonavit()));
      }
      if (((DomicilioCorto) session.getAttribute(
              SessionConstants.ATTR_DOMICILIO_ACLARACION)).
              getMotivoAclaracionVO().getMotivosAclaracionAfore() != null) {
        model.addAttribute("motivosAFORE", TiposAclaracionEnum
                .obtenerMensajesPorClaves(
                        TiposAclaracionEnum.Dependencia.AFORE,
                        ((DomicilioCorto) session.getAttribute(
                                SessionConstants.ATTR_DOMICILIO_ACLARACION)).
                                getMotivoAclaracionVO().
                                getMotivosAclaracionAfore()));
      }
    }

    @RequestMapping(value = "/validar/datosAdicionalesHistoriaLaboral", method = RequestMethod.POST)
    public String consultaDatosAdicionalesHistoriaLaboral(
            @ModelAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL) DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral,
            BindingResult result, Model model, HttpSession session,
            @ModelAttribute(SessionConstants.ATTR_ID_PERSONA) Long idPersona)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        session.setAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL,datosAdicionalesHistoriaLaboral);

        
       
        log.debug("---CDA---  datosAdicionalesHistoriaLaboral {}",
                datosAdicionalesHistoriaLaboral);
        datosAdicionalesHistoriaLaboralValidator.validate(
                datosAdicionalesHistoriaLaboral, result);
        if (result.hasErrors()) {
            this.log.warn(ERRORES_DE_CAPTURA);
            return RequestMappingConstants.VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
        }

        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica personaSesion = (Fisica) session
                .getAttribute(PERSONA_SESSION_KEY);
        
        
        log.debug("---CDA---  CONFIRMAR DATOS SOLICITUD****************  {}",
                solicitud.getNoFolioSolicitud());
        

        DatosAdicionalesHistoriaLaboral contacto = datosAdicionalesHistoriaLaboral;
        TelefonoFijo fijo = new TelefonoFijo();
        TelefonoMovil movil = new TelefonoMovil();
        String observaciones = "";
        if (contacto != null) {
            fijo.setNumero(contacto.getDatosContacto().getTelefonoFijo());
            personaSesion.setTelefonoFijo(fijo);
            movil.setNumero(contacto.getDatosContacto().getTelefonoCelular());
            personaSesion.setTelefonoMovil(movil);
            observaciones = contacto.getObservaciones().toUpperCase();
            ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                    .setObservacion(observaciones);
            solicitudBusiness.actualizaTramite(solicitud, solicitud
                    .getTramites().get(0));
        }

        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setPersonaRENAPO(personaSesion);
        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
        DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        datosHistoriaLaboral.setHistoriaLaboralGrid(informacionHistoriaLaboral
                .getHistoriaLaboralGrid());
        DomicilioCorto motivosAclaracion = (DomicilioCorto) session.getAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        consultaDatosAdicionalesHistoriaLaboralStep01(motivosAclaracion);
        session.setAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION, motivosAclaracion);

        solicitud
                .setPersonaInteresadaSolicitud(new PersonaInteresadaSolicitud());
        solicitud.getPersonaInteresadaSolicitud().setPersona(new Persona());
        solicitud.getPersonaInteresadaSolicitud().setTipoPersonaInteresadaSol(
                new TipoPerInteresadaSol());
        solicitud
                .getPersonaInteresadaSolicitud()
                .getTipoPersonaInteresadaSol()
                .setCveTipoInteresadaSol(
                        TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO
                                .getId());

        solicitud = this.actualizarSolicitud(solicitud, datosHistoriaLaboral,
                datosAdicionalesHistoriaLaboral, motivosAclaracion);
        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                .setObservacion(observaciones);
        log.debug("---CDA--- La solicitud tiene tramite(s): ",
                solicitud.getTramites());
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
        session.setAttribute(PERSONA_SESSION_KEY, personaSesion);
        consultaDatosAdicionalesHistoriaLaboralStep02(model, session);
        return RequestMappingConstants.VIEW_CONFIRMAR_SOLICITUD;
    }

    public List<MedioContacto> eliminaDatosContacto(
            List<MedioContacto> mediosContacto) {
        CollectionUtils.filter(mediosContacto, new Predicate() {
            @Override
            public boolean evaluate(Object c) {
                return (!(c instanceof TelefonoFijo || c instanceof TelefonoMovil));
            }
        });

        return mediosContacto;
    }

    private void consultaDatosHistoriaLaboralStep01( DatosHistoriaLaboralVO datosHistoriaLaboral,
      List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> list){
      if (list != null && !list.isEmpty()) {
        log.debug("DOCUMENTOS APROBATORISO EXISTENTES EN LA SESSION {}", list.
                size());
        List<DocumentoProbatorio> documentoProbatorioList =
                new ArrayList<DocumentoProbatorio>();
        for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio
                : list) {
          log.debug("--CDA--tipo DOCUMENTOPROBATORIOS Aseg: {}",
                  documentosprobatorio.getDocumentoPorTipo());
          if (documentosprobatorio.getDocumentoPorTipo()
                  .getTipoDocumentoProbatorio()
                  .getIdTipoDocumentoProbatorio()
                  == TipoDocumentoProbatorioEnum.ACTAS
                          .getId()
                  || documentosprobatorio.getDocumentoPorTipo()
                          .getTipoDocumentoProbatorio()
                          .getIdTipoDocumentoProbatorio()
                  == TipoDocumentoProbatorioEnum.IDENTIFICACION
                          .getId()) {
            DocumentoProbatorio documento = new DocumentoProbatorio();
            documento.setIdDocBoveda(documentosprobatorio
                    .getBovedaDocId());
            documento.setNombre(documentosprobatorio
                    .getNomNombreDocumento());
            documento.setCveIdDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getDocumento()
                    .getCveIdDocumento());
            documento.setDesDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getDocumento()
                    .getDesDocumento());
            documento.setTipoDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio());
            documento.setIdDocumentoPorTipo(documentosprobatorio
                    .getDocumentoPorTipo().getIdDocumentoPorTipo());
            documentoProbatorioList.add(documento);
          }
        }
        datosHistoriaLaboral
                .setDocumentoProbatorioList(documentoProbatorioList);
      }
    }
    
    @RequestMapping(value = "/datosHistoriaLaboral")
    public String consultaDatosHistoriaLaboral(Model model,HttpSession session, HttpServletRequest request,HttpServletResponse response) {
        DatosHistoriaLaboralVO datosHistoriaLaboral;

        if (session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL) == null) {
            session.setAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL,(DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL));
        }
        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        datosHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
        List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> list = new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>();
        if(((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getAsegurado()!=null){
            list = ((TramiteCorreccionCurp) solicitud
                    .getTramites().get(0)).getAsegurado().getDocumentosProbatorios();
        }
        
        
        consultaDatosHistoriaLaboralStep01(datosHistoriaLaboral, list);
        
        log.debug("--CDA-- DOCUMENTOPROBATORIOS Aseg: {}",
                datosHistoriaLaboral.getDocumentoProbatorioList());
        model.addAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL, datosHistoriaLaboral);

        Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session
                .getAttribute(PERSONA_SESSION_KEY) : null;

        this.log.error("---CDA--- idPersona fisica en sesion: {}",
                fisica != null ? fisica.getIdPersona()
                        : "sin persona en sesion");

        cargarPantallaDatosHistoriaLaboral(model, session);
        return RequestMappingConstants.VIEW_DATOS_HISTORIA_LABORAL;
    }

    
    private void cargarPantallaDatosHistoriaLaboralStep01( 
      DoctoReqTramite doctoReqTramite,
      Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos){
      Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo()
              .getTipoDocumentoProbatorio()
              .getIdTipoDocumentoProbatorio();
      TipoDocumentoProbatorio currMapKey = null;
      if (doctos.keySet() != null && !doctos.keySet().isEmpty()) {
        for (TipoDocumentoProbatorio key : doctos.keySet()) {
          if (tipoDocto.equals(key.getIdTipoDocumentoProbatorio())) {
            currMapKey = key;
            log.debug("--CDA-- se encontro la llave: {}",
                    key.getIdTipoDocumentoProbatorio());
          }
        }
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
      log.debug("--CDA-- TIPO DE DOCUMENTO: {}", docProbatorioVo
              .getTipoDocumento().toString());
      docProbatorioVo.setCveIdDocumento(doctoReqTramite
              .getDocumentoPorTipo().getDocumento().getCveIdDocumento());
      docProbatorioVo.setDesDocumento(doctoReqTramite
              .getDocumentoPorTipo().getDocumento().getDesDocumento());
      docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite
              .getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
      log.debug("--CDA-- ID DE DOCUMENTO PROBATORIO POR TIPO: {}",
              docProbatorioVo.getIdDocumentoPorTipo());
      if (docProbatorioVo.getCveIdDocumento().intValue()
              == DocumentoEnum.ACTA_NACIMIENTO
                      .getId()
              || (docProbatorioVo.getTipoDocumento().longValue()
              == TipoDocumentoProbatorioEnum.IDENTIFICACION
                      .getId() && docProbatorioVo.getCveIdDocumento()
                      .intValue() != DocumentoEnum.CURP.getId())
              || docProbatorioVo.getTipoDocumento().longValue()
              == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
                      .getId()) {
        log.debug("--CDA-- se agrega documento: {}, {}",
                docProbatorioVo.getCveIdDocumento(),
                docProbatorioVo.getDesDocumento());
        doctos.get(currMapKey).add(docProbatorioVo);
      }
    }
    private void cargarPantallaDatosHistoriaLaboral(Model model,HttpSession session) {
        
        model.addAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL,(DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL));

        List<DoctoReqTramite> documentos = documentoProbatorioServiceBusiness
                .getDocumentosRequeridosPorTipoTramite(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO
                        .getCodigo().longValue());

        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        for (DoctoReqTramite doctoReqTramite : documentos) {
            cargarPantallaDatosHistoriaLaboralStep01(doctoReqTramite, doctos);
        }

        List<TipoDocumentoProbatorio> keys = new ArrayList<TipoDocumentoProbatorio>();
        for (TipoDocumentoProbatorio key : doctos.keySet()) {
            if (doctos.get(key).isEmpty()
                    || key.getIdTipoDocumentoProbatorio() == 11) {
                keys.add(key);
            }
        }
        for (TipoDocumentoProbatorio tipoDocumentoProbatorio : keys) {
            doctos.remove(tipoDocumentoProbatorio);
        }

        model.addAttribute("documentosProbatoriosVo", doctos);
        model.addAttribute("documentoNSSClave",
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
    }

    @RequestMapping(value = "/validar/datosHistoriaLaboral", method = RequestMethod.POST)
    public String consultaDatosHistoriaLaboral(
            @ModelAttribute() DatosHistoriaLaboralVO datosHistoriaLaboral,
            BindingResult result, Model model, HttpSession session)
            throws DocumentoException {
        log.debug("---CDA--- DatosHistoriaLaboral {}", datosHistoriaLaboral);
        log.debug("---CDA--- DatosHistoriaLaboral.entidad {}",
                datosHistoriaLaboral.getHistoriaLaboralForm().getClaveEntidad());
        datosHistoriaLaboralValidator.validate(datosHistoriaLaboral, result);
//        if (datosHistoriaLaboral.getDocumentoProbatorioList() != null
//                && !datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()) {
//            documentosProbatoriosValidator.validate(
//                    datosHistoriaLaboral.getDocumentoProbatorioList(), result);
//        } else {
//            result.rejectValue("documentoProbatorioList",
//                    "field.documentoProbatorio.listaVacia");
//        }

        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
        if (session.getAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL) != null) {
            datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session.getAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL);
        } else {
            datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
        }
        session.setAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL, datosHistoriaLaboral);
        if (result.hasErrors()
                || result.hasFieldErrors("documentoProbatorioList")) {
            this.log.warn(ERRORES_DE_CAPTURA);
            cargarPantallaDatosHistoriaLaboral(model, session);
            model.addAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL, datosHistoriaLaboral);
            return RequestMappingConstants.VIEW_DATOS_HISTORIA_LABORAL;
        }
        log.debug(
                "******************CDA************************* SE AGREGAN AL MODELO DatosHistoriaLaboral {}",
                datosAdicionalesHistoriaLaboral.getObservaciones());
        model.addAttribute(SessionConstants.ATTR_DATOS_ADICIONALES_HISTORIA_LABORAL,datosAdicionalesHistoriaLaboral);
        return RequestMappingConstants.VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
    }

    @RequestMapping(value = "/informacionHistoriaLaboral")
    public String consultainformacionHistoriaLaboral(Model model,HttpSession session, HttpServletRequest request,HttpServletResponse response) {
        DatosHistoriaLaboralVO informacionHistoriaLaboral;
        if (session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL) == null) {
            informacionHistoriaLaboral = new DatosHistoriaLaboralVO();
            session.setAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,informacionHistoriaLaboral);
        }
        model.addAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,
                (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL));
        return RequestMappingConstants.VIEW_INFORMACION_HISTORIA_LABORAL;
    }

    
    
    /**
     * Valida la informacion del historial laboral 
     * si todo es OK, redircciona a la pantalla
     * de Documentos Probatorios del Asegurado
     * 
     * 
     * @param session
     * @param request
     * @param response
     * @param informacionHistoriaLaboral
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/validar/informacionHistoriaLaboral", method = RequestMethod.POST)
    public String validateInformacionHistoriaLaboral(HttpSession session,HttpServletRequest request,HttpServletResponse response,
            @ModelAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL) DatosHistoriaLaboralVO informacionHistoriaLaboral,
            BindingResult result, Model model) {

        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL, datosHistoriaLaboral);
       
        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        tramite.setDatosLaborales(registroCorreccionCurpUtil
                .cargarDatosLaborales(datosHistoriaLaboral
                        .getHistoriaLaboralGrid()));
        try {
        	log.debug("- CDA - ACTUALIZANDO XML DE LA SOLICITUD CON FOLIO: {}", sol.getNoFolioSolicitud());
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

    @RequestMapping(value = "/uploadify")
    public ResponseEntity<Map<String, Object>> uploadBytes(
            @RequestParam("fileData") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo, HttpSession ses,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
        if (file.getSize() <= TAMANIO_MAXIMO_ARCHIVO) {
            Solicitud solicitud = (Solicitud) ses.getAttribute(SessionConstants.ATTR_SOLICITUD);
            log.debug("---CDA--- #####Subiendo Archivo######");
            String[] n = file.getOriginalFilename().split("\\.");
            Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
                    .getAttribute(PERSONA_SESSION_KEY) : null;
            if (fisica != null) {
                String idDocBoveda = null;
                try {
                    String nombreCompleto = idDocporTipo + "_"
                            + file.getOriginalFilename();
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
                    log.debug("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
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
                    "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
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
            @PathVariable Integer nssLength, Model model,
            @ModelAttribute(SessionConstants.ATTR_ID_PERSONA) Long idPersona) {
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
            DatosHistoriaLaboralVO datosHistorialLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
            if (datosHistorialLaboral.getHistoriaLaboralGrid() == null) {
                datosHistorialLaboral.setHistoriaLaboralGrid(new ArrayList<HistoriaLaboralVO>());
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

    @RequestMapping(value = "/descargarComprobante", method = RequestMethod.GET)
    public void getComprobanteSolicitud(Model model,
            final HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        Solicitud sol = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
        DomicilioCorto motivoAclaracion = (DomicilioCorto) session.getAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        DatosHistoriaLaboralVO informacionHistoriaLaboral = (DatosHistoriaLaboralVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
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
        } catch (IOException e) {
            this.log.error(
                    "---CDA--- Error al generar el comprobante de asignacion de NSS -> ",
                    e);
        } catch (SolicitudNoEncontradaException e1) {
            this.log.error("---CDA--- Error al generar el comprobante -> ", e1);
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
    public String cancelarSolicitud(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {

        Solicitud solicitud = (Solicitud) session.getAttribute(SessionConstants.ATTR_SOLICITUD);
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

        return inicioCorreccionCurp(model, session);
    }

    @RequestMapping(value = "/concluirSolicitud", method = RequestMethod.GET)
    public String concluirSolicitud(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws SolicitudNoValidaException {
        log.debug("---CDA--- ******Entra al metodo de concluirSolicitud ************************");
        return inicioCorreccionCurp(model, session);
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
                + "/atencionResponsable");

    }

    @RequestMapping("/obtenerDocumento/{idPersona}/{folio}/{nombreArchivo}/{idDocBoveda}")
    public Object load(@PathVariable String idPersona,
            @PathVariable String folio, @PathVariable String nombreArchivo,
            @PathVariable String idDocBoveda, HttpServletResponse response) {
        mx.gob.imss.cit.cda.web.vo.Documento input = new mx.gob.imss.cit.cda.web.vo.Documento();
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
                return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
            }
        } catch (BovedaCDAException bce) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, bce.getSituacion());
            errores.put(ERROR_NEGOCIO, bce.getMensajeError());
            log.error("---CDA--- errores {}", errores);
            return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        } catch (Exception ioe) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, ioe.getMessage());
            errores.put(
                    ERROR_NEGOCIO,
                    MensajesBovedaCDAEnum
                            .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                    .getCodigo()));
            log.error("---CDA--- errores {}", errores);
            return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        }
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
                return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
            }
        } catch (BovedaCDAException bce) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, bce.getSituacion());
            errores.put(ERROR_NEGOCIO, bce.getMensajeError());
            return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);

        } catch (Exception ioe) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put(ERROR_BOVEDA, ioe.getMessage());
            errores.put(
                    ERROR_NEGOCIO,
                    MensajesBovedaCDAEnum
                            .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                    .getCodigo()));
            return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        }
    }
    
    private void enviarCorreoCorreccionDatosPorCurpStep01(
      Solicitud solicitud, CorreoElectronicoDTO correoElectronicoDTO,
      byte[] adjuntos, String url ){
      if (((TramiteCorreccionCurp) solicitud.getTramites().get(0))
              .getPersonaRENAPO().getCorreoElectronico() != null
              && ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                      .getPersonaRENAPO().getCorreoElectronico().getCorreo()
              != null) {
        log.debug("---CDA--- Correo Derechohabiente: "
                + ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                        .getPersonaRENAPO().getCorreoElectronico()
                        .getCorreo());
        correoElectronicoDTO.setCorreoPara(new String[1]);
        correoElectronicoDTO.getCorreoPara()[0] =
                ((TramiteCorreccionCurp) solicitud
                        .getTramites().get(0)).getPersonaRENAPO()
                        .getCorreoElectronico().getCorreo();
        correoElectronicoDTO
                .setAsunto(StringEscapeUtils
                        .unescapeHtml(
                                EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
        correoElectronicoDTO.setCuerpoCorreo(correoUtils
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
    
    private void enviarCorreoCorreccionDatosPorCurpStep02(
      Solicitud solicitud, CorreoElectronicoDTO correoElectronicoDTO ){
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
                            .unescapeHtml(
                                    EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
            correoElectronicoDTO.setCuerpoCorreo(correoUtils
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

        enviarCorreoCorreccionDatosPorCurpStep01(solicitud, correoElectronicoDTO,
                adjuntos, url);

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
                            .setCuerpoCorreo(correoUtils
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
                log.debug(
                        "---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",
                        e);
            }
        }
    }

    private byte[] obtenerDocumentoBoveda(
            mx.gob.imss.cit.cda.web.vo.Documento documentoEnviado)
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
    
    private byte[] obtenerCertificacionSolicitudStep01( Solicitud sol,
      byte[] documentoCertificado, ConsultaSolicitudTramiteVO consultaSolicitudTramiteVO ) throws CorreccionDatosAseguradoException, NoExisteTareaUsuarioException, BovedaCDAException, CuentaIndividualNoDisponibleException {
    
      if (documentoCertificado == null) {
        log.info(
                "---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID",
                sol.getSolicitudId());
        Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas =
                new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();

        parametrosCuentas
                .add(cuentaIndividualBusiness
                        .consultarMovimientosCuentaIndividual(
                                ((TramiteCorreccionCurp) sol
                                        .getTramites().get(0))
                                        .getPersonaRENAPO().getNss()));

        parametrosCuentas
                .add(cuentaIndividualBusiness
                        .consultarUltimoMovimientoCuentaIndividual(
                                ((TramiteCorreccionCurp) sol
                                        .getTramites().get(0))
                                        .getPersonaRENAPO().getNss()));

        FirmaElectronica firma =
                registroSolicitudCorreccionDatosAseguradoBusiness
                        .selloDigitalCertificacion(((TramiteCorreccionCurp) sol
                                .getTramites().get(0)).getPersonaRENAPO(), sol,
                                parametrosCuentas);
        sol.setFirmaElectronica(firma);

        documentoCertificado = manejadorReportesBusiness
                .ejecutaReporteCertificacion(registroCorreccionCurpUtil
                        .generarParametrosReporte(
                                sol,
                                flujoTrabajoBusiness
                                        .obtenerParticipantesUltimaTareaByTramite(
                                                Long
                                                        .parseLong(
                                                                consultaSolicitudTramiteVO.
                                                                        getIdTramite())),
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
            documentoCertificado = obtenerCertificacionSolicitudStep01(sol,
                    documentoCertificado, consultaSolicitudTramiteVO);
            return documentoCertificado;
        } catch (Exception e) {
            log.error("Error: {}", e);
            throw e;
        }
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

        log.debug(
                "---CDA--- ID DOCUMENTO {}",
                tramite != null ? (tipoDocumentoCDAEnum
                        .equals(TipoDocumentoCDAEnum.ACUSE) ? tramite
                        .getIdDocumentoAcuse() : tramite
                        .getIdDocumentoCertificacion()) : null);

        return tramite != null ? (tipoDocumentoCDAEnum
                .equals(TipoDocumentoCDAEnum.ACUSE) ? tramite
                .getIdDocumentoAcuse() : tramite.getIdDocumentoCertificacion())
                : null;
    }

    @RequestMapping(value = "/eliminarDocumentoBoveda", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, String>> eliminarDocumentoBoveda(
            @RequestBody Map<String, String> params, HttpSession session) {

        log.debug("---CDA--- Llega a RSCDC con el parametro: "
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

    
    private void actualizarListaStep01(HttpSession session, 
      Map<String, String> params, DatosHistoriaLaboralVO datos, int index ){
      if ( params.get("type").equals("listadoNSSInvolucradosGrid")
          && datos.getNSSList() != null && !datos.getNSSList().isEmpty()) {
          datos.getNSSList().remove(index);
          session.setAttribute(params.get(VIEW), datos);        
      }
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
            } else {
              actualizarListaStep01(session, params, datos, index);
            }
        }

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
