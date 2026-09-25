package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechosArcoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.Cifrar;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.EnvioCorreoUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.ConsultaCartillaValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cartilla")
public class ConsultaCartillaController extends AbstractController {

    // KEYs en session error
    private static final String KEY_FISICA_ERRORES_NEG = "fisicaErroresNegocioSession";
    private final String KEY_VALIDACIONES = "keyValidacionesNSSCorrectas";
    // key en session del documento de cartilla
    private final String KEY_DOCTO_CARTILLA = "comprobanteCartillaSession";
    // vista que ocntiene la pantalla inicial de la consulta de cartilla
    private final String VIEW_LOGIN_GOBMX = "loginCartilla";
    private final String VIEW_ERROR = "error";
    // vista de consulta finalizada
    private final String VIEW_CONSULTA_FINALIZADA = "cartillaFinaliza";
    private final String VIEW_CONSULTA_FINALIZADA_NSS = "cartillaFinalizaNSS";
    // key del objeto tramite
    private final String KEY_TRAMITE_ASEGURADO = "tramiteAsegurado";
    // key del id del tramite
    private final String KEY_ID_TRAMITE = "tramiteId";
    // key del id de la solicitud
    private final String KEY_ID_SOLICITUD = "idSolicitud";
    // key del folio de la solicitud
    private final String KEY_FOLIO_SOL = "folio";
    // key de los reenvios del correo
    private final String KEY_NUMERO_REENVIOS = "numeroEnviosSession";
    // mensaje de error al comparar los datos
    private final String MSG_ERROR_COMPARACION = "El Instituto (IMSS) ha localizado inconsistencias en tus datos,"
            + " por lo que es necesario te presentes en tu subdelegaci&oacute;n m&aacute;s cercana " + "para regularizar tu informaci&oacute;n.";

    private final Integer IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE = TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor();
    private final Integer CARTILLA_NACIONAL_DE_SALUD = TipoTramiteEnum.CARTILLA_NACIONAL_DE_SALUD.getCodigo();

    private final String MSG_ERROR_CURP = "No se localiz&oacute; informaci&oacute;n en RENAPO con la CURP capturada, por favor "
            + "<a href=\"https://consultas.curp.gob.mx/CurpSP/gobmx/inicio.jsp\" target=\"_blank\">cons&uacute;ltala aqu&iacute;</a>.";

    private final String MSG_ERROR_INTENTOS = "Has agotado el n&uacute;mero de consultas permitidas por d&iacute;a.<br>"
            + "Podr&aacute;s realizar nuevas consultas ma&ntilde;ana o si lo prefieres puedes acudir a la Unidad Medica Familiar (UMF) "
            + "que te corresponde para solicitar tu comprobante de vigencia de derechos.";

    private final String MSG_ERROR_BAJA_LOGICA = "Estimado asegurado(a) o pensionado(a), el N&uacute;mero de seguridad social asociado a la CURP que ingres&oacute;, requiere realizar su aclaraci&oacute;n, por lo que le agradeceremos acudir a la Subdelegaci&oacute;n m&aacute;s cercana.";

    @Autowired
    private ServiceBusinessRemote serviceBusiness;
    @Autowired
    private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
    @Autowired
    private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
    @Autowired
    private TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
    @Autowired
    private DocumentosServiceRemote documentosServiceRemote;
    @Autowired
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
    @Autowired
    private EnvioCorreoUtils correoUtils;
    @Autowired
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
    @Autowired
    private DerechosArcoServiceRemote derechosArcoServiceRemote;

    /**
     * Metodo para iniciar el tramite con gobmx
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "")
    public String homeGobMX(Model model, HttpSession session) {

        //Fisica fisica
        Fisica fisica = (Fisica) session.getAttribute(KEY_FISICA_ERRORES_NEG);
        //limpiamos la session
        limpiarSession(session);
        //agregamos el objeto al modelo para el formulario
        model.addAttribute("fisica", fisica != null ? fisica : new Fisica());

        return VIEW_LOGIN_GOBMX;
    }

    @RequestMapping(value = "/consultar", method = RequestMethod.POST)
    public Object consultaDatosBasicosGobMX(@ModelAttribute Fisica fisica, BindingResult result, @RequestParam("captcha") String captcha, Model model,
            HttpSession session, HttpServletRequest request) {

        limpiarSession(session);
        return this.validacionesCommon(fisica, model, result, captcha, session, request);
    }

    @RequestMapping(value = "/reporte", method = RequestMethod.GET)
    public Object consultaReporte(@ModelAttribute Fisica fisica, BindingResult result, Model model, HttpSession session, HttpServletRequest request) {

        String nssCifrado = request.getParameter("nss");
        String nss="";
        try{
            nss = Base64Cipher.descrifrar(nssCifrado);
            log.debug("nssCifrado: " + nssCifrado);
            log.debug("nss: " + nss);
        } catch (Exception e) {
			this.log.error(e);
		}
        limpiarSession(session);
        return this.validacionesCommonNSS(fisica, model, result, nss, session, request);
    }

    @RequestMapping(value = "/confirmacionUrl", method = RequestMethod.GET)
    public Object confirmacionToken(@ModelAttribute Fisica fisica, Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        limpiarSession(session);
        return this.validacionesCommonToken(model, session, request, response);
    }

    
    @RequestMapping("/finalizado")
    public String solicitudCartillaFinalizada(HttpSession session, HttpServletRequest request) {

        return VIEW_CONSULTA_FINALIZADA;
    }

    @RequestMapping("/finalizadoNSS")
    public String solicitudCartillaFinalizadaNSS(HttpSession session, HttpServletRequest request) {

        return VIEW_CONSULTA_FINALIZADA_NSS;
    }


    /**
     * cuando demos click en el boton salir, pasa por aqui limpia la session y
     * regresa a la pantalla de inicio
     *
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/salir")
    public Object salirTramite(HttpSession session, HttpServletRequest request) {
        // limpiamos los elementos de session
        limpiarSession(session);
        // una vez que limpiamos la session redireccionamos a la pantalla de
        // inicio
        return new RedirectView("/cartilla", true);
    }

    @RequestMapping(value = "/viewReport", method = RequestMethod.POST)
    public void mostrarPDF(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String folio = request.getParameter("Folio");
        descargarVerReporte(model, session, request, response, folio, false);
    }

    @RequestMapping(value = "/downloadReport", method = RequestMethod.POST)
    public void downloadPDF(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String folio = request.getParameter("Folio");
        descargarVerReporte(model, session, request, response, folio, true);
    }

    private void descargarVerReporte(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, String folio, boolean descargar) {

        byte[] documento = (byte[]) session.getAttribute(KEY_DOCTO_CARTILLA);

        try {
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Cache-Control", "public");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Pragma", "public");
            response.addHeader("expires", "0");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", (descargar ? "attachment" : "inline") + ";filename = Cartillas.pdf");
            response.getOutputStream().write(documento);
            response.getOutputStream().flush();
            response.getOutputStream().close();
    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/reenvioMail", method = RequestMethod.POST) 

    public @ResponseBody
    Map<String, Object> reenviarMail(HttpSession session, HttpServletRequest request) {
        String folio = request.getParameter("Folio");
        String solicitudId = request.getParameter("solicitudId");

        Map<String, Object> result = new HashMap<String, Object>();
        boolean reenviar = false;
        Integer numeroIntentos = (Integer) session.getAttribute(KEY_NUMERO_REENVIOS);

        if (numeroIntentos == null || numeroIntentos <= 2) {
            // s epone el contador en 0 en caso de no existir el valor, ya que
            // mas adelante se aumenta el valor
            numeroIntentos = numeroIntentos == null ? 0 : numeroIntentos;
            reenviar = true;
        }
        TramiteAsegurado tramite = (TramiteAsegurado) session.getAttribute(KEY_TRAMITE_ASEGURADO);
        if (tramite.getFisica().getCorreoElectronico().getCorreo().length() > 0){
            if (reenviar) {
                byte[] documento = (byte[]) session.getAttribute(KEY_DOCTO_CARTILLA);
                // Env�o de correo
                envioCorreoCartilla(folio, solicitudId, tramite.getFisica(), documento);
                session.setAttribute(KEY_NUMERO_REENVIOS, numeroIntentos + 1);
            }
            result.put("mensaje", "La cartilla ha sido enviada al correo " + tramite.getFisica().getCorreoElectronico().getCorreo());
        } else {
            result.put("mensaje", "No se puede enviar la cartilla porque no cuenta con un correo registrado.");
        }
        return result;
    }

    /**
     * Metodo que limpiar los datos de la sesion
     *
     * @param session
     */
    private void limpiarSession(HttpSession session) {

        session.removeAttribute(KEY_DOCTO_CARTILLA);
        session.removeAttribute(KEY_ID_TRAMITE);
        session.removeAttribute(KEY_TRAMITE_ASEGURADO);
        session.removeAttribute(KEY_FOLIO_SOL);
        session.removeAttribute(KEY_ID_SOLICITUD);
        session.removeAttribute(KEY_NUMERO_REENVIOS);
        session.removeAttribute(KEY_VALIDACIONES);
        session.removeAttribute("solicitudes");
    }

    private Object validacionesCommon(Fisica fisica, Model model, BindingResult result, String captcha, HttpSession session,
            HttpServletRequest request) {

        session.removeAttribute(KEY_DOCTO_CARTILLA);
        session.removeAttribute("solicitudes");
        SolicitudNssCorreo nssCorreo;
        Fisica fisicaEncontrada;
        String mensajeErrror = "";
        // Se pasa a min�sculas el correo capturado y el de confirmaci�n
        String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
        fisica.getCorreoElectronico().setCorreo(correoCapturado);
        String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
        fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

        // Validaciones de obligatoriedad, longitud y formato
        new ConsultaCartillaValidator().validate(fisica, result);

        if (StringUtils.isBlank(captcha)) {
            FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "Este campo es obligatorio");
            result.addError(fieldError);
        }

        if (result.hasErrors()) {
            this.log.warn("Errores de captura");
            return VIEW_LOGIN_GOBMX;
        }

        // Se valida el captcha
        if (!captcha.equals(session.getAttribute("captcha"))) {
            this.log.error("Captcha no valido!!!");
            FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "El captcha no fue v\u00E1lido, favor de intentar nuevamente");
            result.addError(fieldError);

            return VIEW_LOGIN_GOBMX;
        }

        //Se valida bloqueo ARCO
        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoServiceRemote.consultaBloqueo(fisica.getNss(),  new Long(CARTILLA_NACIONAL_DE_SALUD));

        if(bloqueoDerechosArco != null && bloqueoDerechosArco.isIndBloqueo()){
        	 this.log.error("El nss cuenta con bloqueo DERECHO ARCO!!!");

        	 fisica.setErrorFormGeneral("La Cartilla Nacional de Salud no se puede generar por Internet. En caso de que se requiera generar su Constancia, favor de acudir a la Subdelegaci&oacute;n");
             // Subimos al request el objeto de Modelo
             session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);

             return VIEW_LOGIN_GOBMX;
        }

        try {

            // Se valida que el correo no esta asociado a otra curp y que este
            // dentro del numero de intentos por dia
            nssCorreo = new SolicitudNssCorreo();
            nssCorreo.setCorreo(fisica.getCorreoElectronico());
            nssCorreo.setCurp(fisica.getCurp());
            nssCorreo.setCveIdTipoSolicitud(null);

            this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);

        } catch (SolicitudNssCorreoException e) {
            log.warn("Error solicitud nss correo no valida", e);
            fisica.setErrorFormGeneral(e.getMessage());
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;

        }

	    //Se obtiene asegurado por nss y curp para validar que el nss este asignado a la curp
        try {
            fisicaEncontrada = this.serviceBusiness.validacionesNSSConsultaVigencia(fisica);
        } catch (AsignacionNSSNoLocalizadoException e) {
            if (e.getCodigo() == 1) { // Si el codigo de excepcion es igual a 1 el NSS se encuentra en baja
                log.error("El NSS: " + fisica.getNss() + " presenta fecha de baja");
                mensajeErrror = MSG_ERROR_BAJA_LOGICA;
            } else {
                log.error("Ocurrio un error al consultar el asegurado por NSS: " + fisica.getNss() + " { Mensaje: " + e.getMessage() + " Situacion: " + e.getSituacion() + " }");
                mensajeErrror = e.getMessage();
            }
            fisica.setErrorFormGeneral(mensajeErrror);
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        } catch (CURPNoLocalizadoEnEntidadExternaException e) {
            this.log.error(e);
            fisica.setErrorFormGeneral(MSG_ERROR_CURP);
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        } catch (ClienteWebserviceRenapoCurpException e) {
            this.log.error(e);
            fisica.setErrorFormGeneral(MSG_ERROR_CURP);
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        } catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
            this.log.error(e);
            mensajeErrror = e.getMessage();
            fisica.setErrorFormGeneral(mensajeErrror);
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        } catch (ErrorComparacionDatosRENAPOException e) {
            this.log.error(e);
            fisica.setErrorFormGeneral(MSG_ERROR_COMPARACION);
            mensajeErrror = e.getMessage();
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }

        // Se agrega confirmacion v�a correo electronico
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        correoElectronicoDTO.setCorreoPara(new String[1]);
        correoElectronicoDTO.getCorreoPara()[0] = fisica.getCorreoElectronico().getCorreo();
        correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoUtils.ASUNTO_CONFIRMACION_CORREO_CARTILLA));
        String tokenUrl = Cifrar.generarPassword();
        int port = request.getServerPort();
        String URL = request.getScheme() + "://" + request.getServerName() + ((port == 80) ? "" : ":" + port) + request.getContextPath()
                + EnvioCorreoUtils.URL_CONFIRMACION_CORREO_CARTILLA + "?token=" + tokenUrl + "&curp=" + fisica.getCurp() + "&correo=" + fisica
                .getCorreoElectronico().getCorreo() + "&nss=" + fisica.getNss();
        log.debug("---URL CONFIRMACION CORREO---: " + URL);
        correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoConfirmacionCartilla(URL));
        log.debug("---CDA--- Contenido Correo Derechohabiente: " + (correoElectronicoDTO.getCuerpoCorreo()));

        try {
            envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoUtils.MAIL_PROPERTIES_ADRESS);

        } catch (Exception x) {
            log.debug("---CDA--- Error al enviar correo ", x);
        }

        try {
            SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
                    .recuperarConfirmacion(fisica.getCurp(), TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor().longValue());
            String decifrar = Cifrar.descifrar(tokenUrl);
            System.out.print(decifrar);
            if (modelo != null) {

                this.solicitudNssCorreoServiceBusiness.actualizaConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
                        TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor().longValue());
            } else {

                this.solicitudNssCorreoServiceBusiness.guardarConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
                        TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor().longValue());
            }
        } catch (TransformacionException e) {
            log.warn("Servicio no disponible", e);
            fisica.setErrorFormGeneral(e.getMessage());
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        } catch (SolicitudNssCorreoException e) {
            log.warn("Servicio no disponible", e);
            fisica.setErrorFormGeneral(e.getMessage());
            // Subimos al request el objeto de Modelo
            session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }

        request.setAttribute("exito",
                "Para continuar con su tr\u00e1mite le hemos enviado una liga de confirmaci\u00f3n a su correo electr\u00f3nico");

        return VIEW_LOGIN_GOBMX;

    }

    private Object validacionesCommonNSS(Fisica fisica, Model model, BindingResult result, String sNSS, HttpSession session,
            HttpServletRequest request) {

        session.removeAttribute(KEY_DOCTO_CARTILLA);
        session.removeAttribute("solicitudes");

        //Se valida bloqueo ARCO
        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoServiceRemote.consultaBloqueo(sNSS, new Long(CARTILLA_NACIONAL_DE_SALUD));

        if(bloqueoDerechosArco != null && bloqueoDerechosArco.isIndBloqueo()){
        	 this.log.error("El nss cuenta con bloqueo DERECHO ARCO!!!");

        	 fisica.setErrorFormGeneral("La Cartilla Nacional de Salud no se puede generar por Internet. En caso de que se requiera generar su Constancia, favor de acudir a la Subdelegaci&oacute;n");
             // Subimos al request el objeto de Modelo
             session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);

             return VIEW_ERROR;
        }

        fisica.setNss(sNSS);
        fisica.setCurp(sNSS);
        return this.validacionesCommonSinTokenNSS(fisica, model, session, request);
    }

    private Object validacionesCommonToken(Model model, HttpSession session,HttpServletRequest request, HttpServletResponse response) {

        session.setAttribute(KEY_VALIDACIONES, false);
        SolicitudNssCorreo nssCorreo;
        Fisica fisicaEncontrada;
        String correoCapturado = null;
        String token = null;

        int codigoRespuesta;
        String mensajeErrror = "";
        // el error lo dejamos en true para que solo en caso de que todo este ok
        // se ponga en false
        boolean error = true;

        TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();

        /*GrupoFamiliar asegurado = null;*/

        // Se solicita al usuario la confirmacion de su correo electronico para
        // poder continuar con el tramite

        // Se pasan los paramentros del request al obtener la respuesta desde el
        // correo
        Fisica fisica = new Fisica();
        if (request.getParameter("correo") != null) {
            correoCapturado = request.getParameter("correo");
            correoCapturado = correoCapturado.toLowerCase();
            fisica.setCorreoElectronico(new CorreoElectronico());
            fisica.getCorreoElectronico().setCorreo(correoCapturado);
            fisica.setCorreoElectronicoFiscal(new CorreoElectronico());
            fisica.getCorreoElectronicoFiscal().setCorreo(correoCapturado);

        }

        if (request.getParameter("curp") != null) {
            fisica.setCurp(request.getParameter("curp"));
        }

        if (request.getParameter("nss") != null) {
            fisica.setNss(request.getParameter("nss"));
        }

        if (request.getParameter("token") != null) {
            token = request.getParameter("token");
            token = token.replace(" ", "+");
            log.debug("token cifrado recibido: " + token);
        }

        this.log.debug("CURP capturado desde correo -> " + fisica.getCurp() + "\nCorreo electr�nico capturado -> " + correoCapturado);

        // validar token de la url
        String decifrar = Cifrar.descifrar(token);

        /*
         * Si las validaciones de datos requeridos y del captcha fueron
         * exitosas, se elimina el correoFiscal, que en este caso representa la
         * confirmaci�n del correo electr�nico. Se quita para que no se guarde
         * en la solicitud y se tenga duplicado el correo.
         */
        fisica.setCorreoElectronicoFiscal(null);

        nssCorreo = new SolicitudNssCorreo();
        nssCorreo.setCorreo(fisica.getCorreoElectronico());
        nssCorreo.setCurp(fisica.getCurp());
        nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor().longValue());

        List<GrupoFamiliar> integrantes;

        try {

            SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
                    .recuperarConfirmacion(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(),
                            TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor().longValue());

            if (modelo == null) {

                return procesarErrorEnPantallaInicio("Confirmaci\u00F3n inv\u00E1lida", session);
            }
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (!calendar.getTime().equals(modelo.getFechaTokenActualizacion())) {

                return procesarErrorEnPantallaInicio("Token expirado", session);

            }

            if (modelo.getVigente() != 0) {

                return procesarErrorEnPantallaInicio("Este token ya fue utilizado", session);
            }

            if (!modelo.getToken().equals(decifrar)) {

                return procesarErrorEnPantallaInicio("Token invalido", session);
            }
            // Se valida la consulta/registro de NSS
            codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
            nssCorreo.setOperacionEjecutar(codigoRespuesta);
            this.solicitudNssCorreoServiceBusiness.actualizaConfirmacionVigencia(modelo);

            // Se busca el curp capturado en RENAPO
            fisicaEncontrada = this.serviceBusiness.validacionesNSSConsultaVigencia(fisica);

            fisicaEncontrada.setCorreoElectronico(fisica.getCorreoElectronico());

            AsignacionNSS nss;

            try {
                /*asegurado = grupoFamiliarServiceRemote.getGrupoFamiliar(fisica.getNss(), true);*/

                nss = grupoFamiliarServiceRemote.getAsignacionNssSinPersona(fisica.getNss(), false);

//                integrantes = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNss(fisica.getNss());
                integrantes = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNumNss(fisica.getNss(),true, true, false);
                nss.setCorreoElectronico(fisica.getCorreoElectronico());
                tramiteAsegurado.setFisica(nss);
                // Identificadores para el tipo de tramite, solicitud
                Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
                identificadoresMap.put("tramite", CARTILLA_NACIONAL_DE_SALUD);
                identificadoresMap.put("solicitud", IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE);

                List<Object> solicitudes = new ArrayList<Object>();
                if(integrantes != null && !integrantes.isEmpty()) {
                    Map<String, Object> resultado = this.generaCartillas(integrantes, nss, null, identificadoresMap, true, false);
                    byte[] res = (byte[]) resultado.get("documento");
                    session.setAttribute(KEY_DOCTO_CARTILLA, res);
                    long tramiteId = (Long) resultado.get(KEY_ID_TRAMITE);
                    session.setAttribute(KEY_ID_TRAMITE, tramiteId);
                                // seteamos el folio en session
                    String folio = (String) resultado.get("folio");
                    session.setAttribute(KEY_FOLIO_SOL, folio);
                    String idSolicitud = (String) resultado.get("idSolicitud");
                    session.setAttribute(KEY_ID_SOLICITUD, idSolicitud);
                    session.setAttribute("homoclaveTramite", resultado.get("homoclaveTramite"));
                    //session.setAttribute("solicitudCartilla", resultado.get("solicitud"));
                    solicitudes.add(resultado.get("solicitud"));
                    session.setAttribute("solicitudes", solicitudes);

                    // Env�o de correo
                    envioCorreoCartilla(folio, idSolicitud, tramiteAsegurado.getFisica(), res);

                    response.setHeader("Pragma", "No-cache");
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setDateHeader("Expires", 0);
                    request.setAttribute("NSS_RECUPERADO", true);
                   
                }

                log.debug("se genero la cartilla sin ningun error");
                // Se actualiza el numero de intentos de consulta
                this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
                error = false;

            } catch (DerechohabientesBusinessException e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurrió un error al generar la cartilla nacional de salud.");
            } catch (DocumentoException ex) {
                this.log.error(ex);
                mensajeErrror = ex.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurrió un error al generar la cartilla nacional de salud.");
            } catch (Exception e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurrió un error al generar la cartilla nacional de salud.");
            }
        } catch (CURPNoLocalizadoEnEntidadExternaException e) {
            this.log.error(e);
            tramiteAsegurado.setErrorFormGeneral(MSG_ERROR_CURP);

            mensajeErrror = tramiteAsegurado.getErrorFormGeneral();
        } catch (ClienteWebserviceRenapoCurpException e) {
            this.log.error(e);
            tramiteAsegurado.setErrorFormGeneral(MSG_ERROR_CURP);
            mensajeErrror = tramiteAsegurado.getErrorFormGeneral();
        } catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
            this.log.error(e);
            mensajeErrror = e.getMessage();
            tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
        } catch (ErrorComparacionDatosRENAPOException e) {
            this.log.error(e);
            tramiteAsegurado.setErrorFormGeneral(MSG_ERROR_COMPARACION);
            mensajeErrror = e.getMessage();
        } catch (SolicitudNssCorreoException e) {
            // Esta exception debe mandar al home (a traves de la pantalla de
            // salida)
            this.log.error(e);
            // verificamos si el error es por el numero de operacioens
            if (e.getMessage().contains("operaciones por periodo")) {
                log.debug("El error es debido a los intentos");
                // de ser asi se tustituye el mensaje, no se hace desde el
                // servicio debido a que tambien lo usa la asignacion de NSS
                mensajeErrror = MSG_ERROR_INTENTOS;
            } else {
                log.debug("E error es debido a otra cosa");
                mensajeErrror = e.getMessage();
            }

            tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
        } catch (AsignacionNSSNoLocalizadoException e) {
            if (e.getCodigo() == 1) { // Si el codigo de excepcion es igual a 1 el NSS se encuentra en baja
                log.error("El NSS: " + fisica.getNss() + " presenta fecha de baja");
                mensajeErrror = MSG_ERROR_BAJA_LOGICA;
            } else {
                log.error("Ocurrio un error al consultar el asegurado por NSS: " + fisica.getNss() + " { Mensaje: " + e.getMessage() + " Situacion: " + e.getSituacion() + " }");
                mensajeErrror = e.getMessage();
            }
            tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
        } catch (TransformacionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mensajeErrror = e.getMessage();
            tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
        }

        if (error) {
            tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), mensajeErrror);
            log.error("Ocurrio un error al generar la cartilla nacional de salud para el curp " + fisica.getCurp());
        }

        if (error) {
            fisica = new Fisica();
            fisica.setErrorFormGeneral(tramiteAsegurado.getErrorFormGeneral());
            model.addAttribute("fisica", fisica);
            return VIEW_LOGIN_GOBMX;
        }
        // Subimos al request el objeto de Modelo
        session.setAttribute(KEY_TRAMITE_ASEGURADO, tramiteAsegurado);

        return new RedirectView("/cartilla/finalizado", true);

    }

    private Object validacionesCommonSinTokenNSS(Fisica fisica, Model model, HttpSession session,HttpServletRequest request) {

        session.setAttribute(KEY_VALIDACIONES, false);
        String mensajeErrror = "";
        String correo = "";
        // el error lo dejamos en true para que solo en caso de que todo este ok
        // se ponga en false
        boolean error = true;
        TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();
        //Model model = null;
        AsignacionNSS nss;
        List<GrupoFamiliar> integrantes;

        try {
                /*asegurado = grupoFamiliarServiceRemote.getGrupoFamiliar(fisica.getNss(), true);*/

                nss = grupoFamiliarServiceRemote.getAsignacionNssSinPersona(fisica.getNss(), false);

//                integrantes = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNss(fisica.getNss());
                integrantes = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNumNss(fisica.getNss(),true, true, false);

                correo = this.solicitudNssCorreoServiceBusiness.obtieneMedioContactoCorreoPersona(nss.getIdPersona().toString());

                CorreoElectronico correoelectronico = new CorreoElectronico();
                correoelectronico.setCorreo(correo);

                nss.setCorreoElectronico(correoelectronico);
                
                tramiteAsegurado.setFisica(nss);
                // Identificadores para el tipo de tramite, solicitud
                Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
                identificadoresMap.put("tramite", CARTILLA_NACIONAL_DE_SALUD);
                identificadoresMap.put("solicitud", IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE);

                List<Object> solicitudes = new ArrayList<Object>();

                if(integrantes != null && !integrantes.isEmpty()) {
                    Map<String, Object> resultado = this.generaCartillas(integrantes, nss, null, identificadoresMap, true, false);
                    byte[] res = (byte[]) resultado.get("documento");
                    session.setAttribute(KEY_DOCTO_CARTILLA, res);
                    long tramiteId = (Long) resultado.get(KEY_ID_TRAMITE);
                    session.setAttribute(KEY_ID_TRAMITE, tramiteId);
                                // seteamos el folio en session
                    String folio = (String) resultado.get("folio");
                    session.setAttribute(KEY_FOLIO_SOL, folio);
                    String idSolicitud = (String) resultado.get("idSolicitud");
                    session.setAttribute(KEY_ID_SOLICITUD, idSolicitud);
                    session.setAttribute("homoclaveTramite", resultado.get("homoclaveTramite"));
                    //session.setAttribute("solicitudCartilla", resultado.get("solicitud"));
                    solicitudes.add(resultado.get("solicitud"));
                    session.setAttribute("solicitudes", solicitudes);
                }
                session.setAttribute("solicitudes", solicitudes);
                log.debug("se genero la cartilla sin ningun error");
                // Se actualiza el numero de intentos de consulta
                //this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
                error = false;

        } catch (DerechohabientesBusinessException e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                model.addAttribute("exception", e2);
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
        } catch (DocumentoException ex) {
                this.log.error(ex);
                ex.printStackTrace();
                mensajeErrror = ex.getMessage();
                model.addAttribute("exception", ex);
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
        } catch (Exception e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                model.addAttribute("exception", e2);
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
        }

        if (error) {
            tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), mensajeErrror);
            log.error("Ocurrio un error al generar la cartilla nacional de salud para el NSS " + fisica.getCurp());
        }

        if (error) {
            fisica = new Fisica();
            fisica.setErrorFormGeneral(tramiteAsegurado.getErrorFormGeneral());
            model.addAttribute("fisica", fisica);
            return VIEW_ERROR;
        }
        // Subimos al request el objeto de Modelo
        session.setAttribute(KEY_TRAMITE_ASEGURADO, tramiteAsegurado);

        return new RedirectView("/cartilla/finalizadoNSS", true);

    }

    private Map<String, Object> generaCartillas(List<GrupoFamiliar> integrantes, AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap,
            boolean enviaMail, boolean infoWS) throws DocumentoException {

        byte[] documentByteArray = null;
        Map<String, Object> resultado = new HashMap<String, Object>();
        String nss = asignacionNSS.getNssStr();
        asignacionNSS.setNss(nss);
        Map<String,byte[]> cartillas = new HashMap<String,byte[]>();

        try {

            // 1. Se debe crear un tramite y solicitud.
            Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
            if (origenSolicitud == null) {
                origenSolicitud = OrigenSolicitudEnum.INTERNET.getId().intValue();
            }

            log.debug("voy a crear la solicitud");
            // asignacionNSS es de tipo AsignacionNSS que extiende de Fisica por
            // lo que puede pasarse como parametro para crear la solicitud
            Solicitud solicitud = tramiteDocumentosServiceRemote
                    .crearTramiteSolicitud(asignacionNSS, origenSolicitud.longValue(), usuario, identificadoresMap);

            log.debug("Termino de generar la solicitud");
            // 2. Obtener sello digital
            // Se obtiene el sello digital
            // Se genera cadena original
            asignacionNSS.setNss(nss);
            log.debug("voy a guardar en notaria");
            FirmaElectronica firmaElectronica = tramiteDocumentosServiceRemote
                    .generaFirmaElectronica(asignacionNSS, solicitud, "Cartilla nacional de salud");
            log.debug("Voy a generar el pdf");
//                            if (grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.BAJA.getId()  &&
//                            grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.FALLECIDO.getId()){
            documentByteArray = (byte[]) documentosServiceRemote.getCartillasNacionalSalud(integrantes, asignacionNSS, firmaElectronica);
//                            }
            // 4. Se guarda el reporte
            firmaDigitalBusinessRemote
                    .guardarArchivoFirmado(firmaElectronica.getReciboNotarial(), "cartilla" + nss + ".pdf", documentByteArray);
            log.debug("Termino de guardar en notaria en portal y con WS " + infoWS);

            Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
            String homoclave = solicitud.getTramites().get(0).getTipoTramite().getHomoclave();
            resultado.put("documento", documentByteArray);
            resultado.put("homoclaveTramite", homoclave);
            resultado.put("tramiteId", tramiteId);
            resultado.put("folio", solicitud.getNoFolioSolicitud());
            resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
            resultado.put("secuenciaNotarial", firmaElectronica.getSecuenciaNotaria());
            resultado.put("solicitud", solicitud);
            resultado.put("cartillas", cartillas);
        } catch (SolicitudNoValidaException e) {
            throw new DocumentoException(e.getMessage());
        } catch (SolicitudException e) {
            throw new DocumentoException(e.getMessage());
        } catch (DerechohabientesBusinessException e) {
            throw new DocumentoException(e.getMessage());
        } catch (Exception e) {
            throw new DocumentoException(e.getMessage());
        }
        return resultado;
    }


    private void envioCorreo(String folio, String idSolicitud, AsignacionNSS nss, byte[] docto) {
        // Env�o de correo
        try {
            // tramiteDocumentosService.enviaCorreo(nss, res, null);
            // String subject = "Reporte de vigencia de derechos";
            Map<String, String> paramAdicionales = new HashMap<String, String>();
            String cuerpoCorreo = correoUtils.contenidoCorreoCartilla(nss.getNombreCompleto(), nss.getNss());
            paramAdicionales.put("folio", folio);
            paramAdicionales.put("idSolicitud", idSolicitud);
            paramAdicionales.put("folioCifrado", Base64Cipher.cifrar(folio));

            Map<String, byte[]> doctos = new HashMap<String, byte[]>();
            doctos.put("reimpresionCartillaNacionalDeSalud.pdf", docto);

            // tramiteDocumentosServiceRemote.enviaCorreo(nss, docto, null);

            serviceBusiness.enviarCorreoPersonaFisicaContent(nss, TipoTramiteEnum.CARTILLA_NACIONAL_DE_SALUD, paramAdicionales,
                    "IMSS DIGITAL REIMPRESION DE CARTILLA NACIONAL DE SALUD", doctos, cuerpoCorreo);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void envioCorreoCartilla(String folio, String idSolicitud, AsignacionNSS nss, byte[] docto) {

        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        correoElectronicoDTO.setCorreoPara(new String[1]);
        correoElectronicoDTO.getCorreoPara()[0] = nss.getCorreoElectronico().getCorreo();
        correoElectronicoDTO.setAsunto("IMSS DIGITAL REIMPRESION DE CARTILLA NACIONAL DE SALUD");
        correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoCartilla(nss.getNombreCompleto(), nss.getNss()));
        Map<String, byte[]> doctos = new HashMap<String, byte[]>();
        doctos.put("reimpresionCartillaNacionalDeSalud.pdf", docto);
        correoElectronicoDTO.setAdjuntos(doctos);
        try {
            envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoUtils.MAIL_PROPERTIES_ADRESS);

        } catch (Exception x) {
            log.debug("---CDA--- Error al enviar correo ", x);
        }
    }



    private RedirectView procesarErrorEnPantallaInicio(String mensajeError, HttpSession session) {

        Fisica fisica = new Fisica();
        fisica.setErrorFormGeneral(mensajeError);
        // Subimos al request el objeto de Modelo
        session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
        // retornamos el redirec view
        return new RedirectView("/cartilla/", true);
    }
}
