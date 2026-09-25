package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Date;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechosArcoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
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
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.dto.FirmaElectronicaResponseDTO;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.Cifrar;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.EnvioCorreoUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.ResourceBundleConfiguration;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.ConsultaVigenciaValidator;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/vigencia")
public class ConsultaVigenciaController extends AbstractController {

    // KEYs en session error
    private static final String KEY_FISICA_ERRORES_NEG = "fisicaErroresNegocioSession";
    private final String KEY_VALIDACIONES = "keyValidacionesNSSCorrectas";
    // key en session del documento de vigencia
    private final String KEY_DOCTO_VIGENCIA = "comprobanteVigenciaSession";
    // key en session del documento de reporte actualizacion
    private final String KEY_DOCTO_ACTUALIZACION = "comprobanteActualizacionSession";
    // vista que ocntiene la pantalla inicial de la consulta de vigencia
    private final String VIEW_LOGIN_GOBMX = "loginVigencia";
    // vista de consulta finalizada
    private final String VIEW_CONSULTA_FINALIZADA = "vigenciaFinaliza";
    // key del objeto tramite
    private final String KEY_TRAMITE_ASEGURADO = "tramiteAsegurado";
    // key del id del tramite
    private final String KEY_ID_TRAMITE = "tramiteId";
    // key del id de la solicitud
    private final String KEY_ID_SOLICITUD = "idSolicitud";
    // key del folio de la solicitud
    private final String KEY_FOLIO_SOL = "folio";
    // key del NSS
    private final String KEY_NSS = "nss";
    // key de los reenvios del correo
    private final String KEY_NUMERO_REENVIOS = "numeroEnviosSession";
    // mensaje de error al comparar los datos
    private final String MSG_ERROR_COMPARACION = "El Instituto (IMSS) ha localizado inconsistencias en tus datos,"
            + " por lo que es necesario te presentes en tu subdelegaci&oacute;n m&aacute;s cercana " + "para regularizar tu informaci&oacute;n.";
    // mensaje de error
    private final String MSG_ERROR_NSS = "El Instituto (IMSS) no ha podido localizar tu N&uacute;mero de Seguridad Social (NSS), por favor<br>"
            + "<a href=\"/gestionAsegurados-web-externo/asignacionNSS\" target=\"_blank\">cons&uacute;ltalo aqu&iacute;</a>.";

    private final String MSG_ERROR_CURP = "No se localiz&oacute; informaci&oacute;n en RENAPO con la CURP capturada, por favor "
            + "<a href=\"https://consultas.curp.gob.mx/CurpSP/gobmx/inicio.jsp\" target=\"_blank\">cons&uacute;ltala aqu&iacute;</a>.";

    private final String MSG_ERROR_INTENTOS = "Has agotado el n&uacute;mero de consultas permitidas por d&iacute;a.<br>"
            + "Podr&aacute;s realizar nuevas consultas ma&ntilde;ana o si lo prefieres puedes acudir a la Unidad Medica Familiar (UMF) "
            + "que te corresponde para solicitar tu comprobante de vigencia de derechos.";

    private final String MSG_ERROR_BAJA_LOGICA = "Estimado asegurado(a) o pensionado(a), el N&uacute;mero de seguridad social asociado a la CURP que ingres&oacute;, requiere realizar su aclaraci&oacute;n, por lo que le agradeceremos acudir a la Subdelegaci&oacute;n m&aacute;s cercana.";

    private final String KEY_TOKEN = "4CTuA7C07r3oS1S3";
    private final Integer EFIRMA = 1;
    private final Integer SIN_EFIRMA = 2;
    private final Integer CURP_BENEFICIARIO = 1;
    private final Integer RP = 2;
    private final Integer UMF = 3;
    private final Integer ASIGNAR_VENTANILLA = 4;
    
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
    @Autowired
    private PersonaBusinessRemote personaBusinessRemote;
    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;
    @Autowired
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;
    
    

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
        session.invalidate();
        //agregamos el objeto al modelo para el formulario
        model.addAttribute("fisica", fisica != null ? fisica : new Fisica());

        return VIEW_LOGIN_GOBMX;
    }

    @RequestMapping(value = "/consultar", method = RequestMethod.POST)
    public Object consultaDatosBasicosGobMX(@ModelAttribute Fisica fisica, BindingResult result, @RequestParam("captcha") String captcha,
            HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        limpiarSession(session);
        return this.validacionesCommon(fisica, result, captcha, session, request, response);
    }

    @RequestMapping(value = "/confirmacionUrl", method = RequestMethod.GET)
    public Object confirmacionToken(@ModelAttribute Fisica fisica, Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        limpiarSession(session);
        return this.validacionesCommonToken(model, session, request, response);
    }

    @RequestMapping("/finalizado")
    public String solicitudVigenciaFinalizada(HttpSession session, HttpServletRequest request) {

        return VIEW_CONSULTA_FINALIZADA;
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
        return new RedirectView("/vigencia", true);
    }

    @RequestMapping(value = "/viewReport", method = RequestMethod.POST)
    public void mostrarPDF(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        descargarVerReporte(model, session, request, response, false);
    }

    @RequestMapping(value = "/downloadReport", method = RequestMethod.POST)
    public void downloadPDF(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        descargarVerReporte(model, session, request, response, true);
    }

    private void descargarVerReporte(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, boolean descargar) {

        byte[] documento = (byte[]) session.getAttribute(KEY_DOCTO_VIGENCIA);
        String nss = (String) session.getAttribute(KEY_NSS);
        try {
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Cache-Control", "public");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Pragma", "public");
            response.addHeader("expires", "0");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", (descargar ? "attachment" : "inline") + ";filename = comprobanteVigenciaDerechos" + nss + ".pdf");
            response.getOutputStream().write(documento);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/reenvioMail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> reenviarMail(HttpSession session) {

        Map<String, Object> result = new HashMap<String, Object>();
        boolean reenviar = false;
        Integer numeroIntentos = (Integer) session.getAttribute(KEY_NUMERO_REENVIOS);

        if (numeroIntentos == null || numeroIntentos <= 2) {
            // s epone el contador en 0 en caso de no existir el valor, ya que
            // mas adelante se aumenta el valor
            numeroIntentos = numeroIntentos == null ? 0 : numeroIntentos;
            reenviar = true;
        }

        if (reenviar) {
            String folio = (String) session.getAttribute(KEY_FOLIO_SOL);
            String idSolicitud = (String) session.getAttribute(KEY_ID_SOLICITUD);
            byte[] documento = (byte[]) session.getAttribute(KEY_DOCTO_VIGENCIA);
            TramiteAsegurado tramite = (TramiteAsegurado) session.getAttribute(KEY_TRAMITE_ASEGURADO);
            // Envio de correo
            envioCorreo(folio, idSolicitud, tramite.getFisica(), documento);
            session.setAttribute(KEY_NUMERO_REENVIOS, numeroIntentos + 1);
        }

        result.put("mensaje", "El comprobante ha sido reenviado");

        return result;
    }

    /**
     * Metodo que limpiar los datos de la sesion
     *
     * @param session
     */
    private void limpiarSession(HttpSession session) {

        session.removeAttribute(KEY_DOCTO_VIGENCIA);
        session.removeAttribute(KEY_ID_TRAMITE);
        session.removeAttribute(KEY_TRAMITE_ASEGURADO);
        session.removeAttribute(KEY_FOLIO_SOL);
        session.removeAttribute(KEY_NSS);
        session.removeAttribute(KEY_ID_SOLICITUD);
        session.removeAttribute(KEY_NUMERO_REENVIOS);
        session.removeAttribute(KEY_VALIDACIONES);
    }

    private Object validacionesCommon(Fisica fisica, BindingResult result, String captcha, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {

        session.removeAttribute(KEY_DOCTO_VIGENCIA);
        SolicitudNssCorreo nssCorreo;
        Fisica fisicaEncontrada;
        String mensajeErrror = "";
		String curp = fisica.getCurp();
        Boolean requiereActualizacion = false; 
        // Se pasa a minusculas el correo capturado y el de confirmacion
        String correoCapturado = "";
        String correoConfirmacion = "";

        if(esGmail(fisica.getCorreoElectronico().getCorreo().toLowerCase())){
        	correoCapturado = removerPuntos(fisica.getCorreoElectronico().getCorreo().toLowerCase());
        	correoConfirmacion = removerPuntos(fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase());
        }else{
        	correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
        	correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();	
        }

        fisica.getCorreoElectronico().setCorreo(correoCapturado);
        fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);	
        
        // Validaciones de obligatoriedad, longitud y formato
        new ConsultaVigenciaValidator().validate(fisica, result);

        if (StringUtils.isBlank(captcha)) {
            FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "Este campo es obligatorio");
            result.addError(fieldError);
        }

        if (result.hasErrors()) {
            this.log.warn("Errores de captura");
            return VIEW_LOGIN_GOBMX;
        }

        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
            FieldError fieldError = new FieldError("fisica", "errorFormGeneral", "El captcha no fue v\u00E1lido, favor de intentar nuevamente");
            result.addError(fieldError);
            return VIEW_LOGIN_GOBMX;
        }

        //Se valida bloqueo ARCO
        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoServiceRemote.consultaBloqueo(fisica.getNss(), mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());

        if(bloqueoDerechosArco != null && bloqueoDerechosArco.isIndBloqueo()){
        	 this.log.error("El nss cuenta con bloqueo DERECHO ARCO!!!");

        	 fisica.setErrorFormGeneral("La Constancia de Vigencia de Derechos no se puede generar por Internet. En caso de que se requiera generar su Constancia, favor de acudir a la Subdelegaci&oacute;n");
             // Subimos al request el objeto de Modelo
             session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);

             return VIEW_LOGIN_GOBMX;
        }
        
		boolean esDominioPermitido;
		
        try {

            esDominioPermitido = this.solicitudNssCorreoServiceBusiness.esDominioPermitido(correoCapturado);
			
		    if (!esDominioPermitido) {
		        throw new SolicitudNssCorreoException("El dominio del correo electr&oacute;nico es inv&aacute;lido. Intente con otro");
		    }else if(esGmail(correoCapturado)){
		    	
		    	log.info("El correo: "+correoCapturado+" es GMAIL");
			    if(esValidoElCorreoGmail(correoCapturado)){

		    		log.info("El correo: "+correoCapturado+" es VALIDO, VERIFICANDO DUPLICIDAD");
		    		if(existeDuplicidad(correoCapturado, curp)){
			    		throw new SolicitudNssCorreoException("El correo electr\u00f3nico que captur\u00f3; ya se encuentra registrado en el Instituto relacionado a una CURP distinta a la capturada.");
			    	}
		    	}else if(!esValidoElCorreoGmail(correoCapturado)){
		    		log.info("El correo: "+correoCapturado+" es INVALIDO");
		    		throw new SolicitudNssCorreoException("Los correos Gmail no deben contener '-' ni '+'.");  		
		    	}
		    }

		} catch (SolicitudNssCorreoException e) {
			log.warn("Error solicitud nss correo no valida", e);
			fisica.setErrorFormGeneral(e.getMessage());
			// Subimos al request el objeto de Modelo
			request.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			return VIEW_LOGIN_GOBMX;
		}
        
        //Se valida que el curp no este asociado a mas de un correo, si lo esta se requiere actualizarlo
        int codigoActualizacion = 0;
        
        try {
			nssCorreo = new SolicitudNssCorreo();
			nssCorreo.setCorreo(fisica.getCorreoElectronico());
			nssCorreo.setCurp(fisica.getCurp());

			codigoActualizacion = this.solicitudNssCorreoServiceBusiness.validaCorreosRegistrados(nssCorreo);
			
		    if (codigoActualizacion > 1) {
				request.setAttribute("requiereActualizacion", true);
				request.setAttribute("showModal", "show");
		        throw new SolicitudNssCorreoException("REQUIERE ACTUALIZACION");
		    }

		} catch (SolicitudNssCorreoException e) {
			log.warn("Error solicitud nss correo no valida", e);
			fisica.setErrorFormGeneral(e.getMessage());
			// Subimos al request el objeto de Modelo
			request.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			session.setAttribute("codigoActualizacion", codigoActualizacion);
			log.debug("El codigo de actualizacion en session es : " + session.getAttribute("codigoActualizacion"));
			return VIEW_LOGIN_GOBMX;
		};
		
        try {

            // Se valida que el correo no esta asociado a otra curp y que este
            // dentro del numero de intentos por dia
            nssCorreo = new SolicitudNssCorreo();
            nssCorreo.setCorreo(fisica.getCorreoElectronico());
            nssCorreo.setCurp(fisica.getCurp());
            nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());

            this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);

        } catch (SolicitudNssCorreoException e) {
            log.warn("Error solicitud nss correo no valida", e);
            fisica.setErrorFormGeneral(e.getMessage());
            // Subimos al request el objeto de Modelo
            request.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
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

        // Se agrega confirmacion via correo electronico
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        correoElectronicoDTO.setCorreoPara(new String[1]);
        correoElectronicoDTO.getCorreoPara()[0] = fisica.getCorreoElectronico().getCorreo();
        correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoUtils.ASUNTO_CONFIRMACION_CORREO_VIGENCIA));
        String tokenUrl = Cifrar.generarPassword();
        ResourceBundle properties =ResourceBundleConfiguration.getResourceProperties();
		String strHttpProtocolo = "https";
		if(new Boolean(properties.getString("calcula.protocolo.http.ulr")).booleanValue() == true)
			strHttpProtocolo = request.getScheme();
		
		String curpCifrada = Cifrar.cifrar(fisica.getCurp());
		String correoCifrado=Cifrar.cifrar(fisica.getCorreoElectronico().getCorreo());
		String nssCofrado=Cifrar.cifrar(fisica.getNss());
        
        int port = request.getServerPort();
        String URL = strHttpProtocolo + "://" + request.getServerName() + ((port == 80) ? "" : ":" + port) + request.getContextPath()
                + EnvioCorreoUtils.URL_CONFIRMACION_CORREO_VIGENCIA + "?token=" + tokenUrl + "&curp=" + curpCifrada + "&correo=" + 
                correoCifrado + "&nss=" + nssCofrado;
        log.debug("---URL CONFIRMACION CORREO---: " + URL);
        correoElectronicoDTO.setCuerpoCorreo(correoUtils.contenidoCorreoConfirmacionVigencia(URL));
        log.debug("---CDA--- Contenido Correo Derechohabiente: " + (correoElectronicoDTO.getCuerpoCorreo()));

        try {
            envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoUtils.MAIL_PROPERTIES_ADRESS);

        } catch (Exception x) {
            log.debug("---CDA--- Error al enviar correo ", x);
        }

        try {
            SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
                    .recuperarConfirmacion(fisica.getCurp(), TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
            String decifrar = Cifrar.descifrar(tokenUrl);
            System.out.print(decifrar);
            if (modelo != null) {

                this.solicitudNssCorreoServiceBusiness.actualizaConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
                        TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
            } else {

                this.solicitudNssCorreoServiceBusiness.guardarConfirmacion(fisica.getCorreoElectronico().getCorreo(), fisica.getCurp(), decifrar,
                        TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
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

    private Object validacionesCommonToken(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

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
            correoCapturado = Cifrar.descifrar(request.getParameter("correo").replace(" ", "+"));
            correoCapturado = correoCapturado.toLowerCase();
            fisica.setCorreoElectronico(new CorreoElectronico());
            fisica.getCorreoElectronico().setCorreo(correoCapturado);
            fisica.setCorreoElectronicoFiscal(new CorreoElectronico());
            fisica.getCorreoElectronicoFiscal().setCorreo(correoCapturado);

        }

        if (request.getParameter("curp") != null) {
            fisica.setCurp (Cifrar.descifrar(request.getParameter("curp").replace(" ", "+")));
        }

        if (request.getParameter("nss") != null) {
            fisica.setNss (Cifrar.descifrar(request.getParameter("nss").replace(" ", "+")));
        }

        if (request.getParameter("token") != null) {
            token = request.getParameter("token");
            token = token.replace(" ", "+");
            log.debug("token cifrado recibido: " + token);
        }

        this.log.debug("CURP capturado desde correo -> " + fisica.getCurp() + "\nCorreo electronico capturado -> " + correoCapturado);

        // validar token de la url
        String decifrar = Cifrar.descifrar(token);

        /*
         * Si las validaciones de datos requeridos y del captcha fueron
         * exitosas, se elimina el correoFiscal, que en este caso representa la
         * confirmacion del correo electronico. Se quita para que no se guarde
         * en la solicitud y se tenga duplicado el correo.
         */
        fisica.setCorreoElectronicoFiscal(null);

        nssCorreo = new SolicitudNssCorreo();
        nssCorreo.setCorreo(fisica.getCorreoElectronico());
        nssCorreo.setCurp(fisica.getCurp());
        nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());

        try {

            SolicitudNssConfirmacion modelo = this.solicitudNssCorreoServiceBusiness
                    .recuperarConfirmacion(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(),
                            TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());

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

                nss.setCorreoElectronico(fisica.getCorreoElectronico());
                tramiteAsegurado.setFisica(nss);
                // Identificadores para el tipo de tramite, solicitud
                Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
                identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
                identificadoresMap.put("solicitud", TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor());

                Map<String, Object> resultado = this.generaConstancia(nss, null, identificadoresMap, true, false);
                byte[] res = (byte[]) resultado.get("documento");
                session.setAttribute(KEY_DOCTO_VIGENCIA, res);
                long tramiteId = (Long) resultado.get(KEY_ID_TRAMITE);
                session.setAttribute(KEY_ID_TRAMITE, tramiteId);
                // seteamos el folio en session
                String folio = (String) resultado.get("folio");
                session.setAttribute(KEY_FOLIO_SOL, folio);
                String idSolicitud = (String) resultado.get("idSolicitud");
                session.setAttribute(KEY_ID_SOLICITUD, idSolicitud);
                session.setAttribute(KEY_NSS, fisica.getNss());
                session.setAttribute("homoclaveTramite", resultado.get("homoclaveTramite"));
                session.setAttribute("solicitudVigencia", resultado.get("solicitud"));

                // Envio de correo
                envioCorreo(folio, idSolicitud, nss, res);

                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setDateHeader("Expires", 0);
                request.setAttribute("NSS_RECUPERADO", true);

                log.debug("se genero la constancia sin ningun error");
                // Se actualiza el numero de intentos de consulta
                this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
                error = false;

            } catch (DerechohabientesBusinessException e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri\u00F3 un error al generar el reporte de vigencia.");
            } catch (DocumentoException ex) {
                this.log.error(ex);
                mensajeErrror = ex.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri\u00F3 un error al generar el reporte de vigencia.");
            } catch (Exception e2) {
                e2.printStackTrace();
                mensajeErrror = e2.getMessage();
                tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri\u00F3 un error al generar el reporte de vigencia.");
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
            log.error("Ocurrio un error al generar la constancia de vigencia para el curp " + fisica.getCurp());
        }

        if (error) {
            fisica = new Fisica();
            fisica.setErrorFormGeneral(tramiteAsegurado.getErrorFormGeneral());
            model.addAttribute("fisica", fisica);
            return VIEW_LOGIN_GOBMX;
        }
        // Subimos al request el objeto de Modelo
        session.setAttribute(KEY_TRAMITE_ASEGURADO, tramiteAsegurado);

        return new RedirectView("/vigencia/finalizado", true);

    }

    private Map<String, Object> generaConstancia(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap,
            boolean enviaMail, boolean infoWS) throws DocumentoException {

        byte[] documentByteArray = null;
        Map<String, Object> resultado = new HashMap<String, Object>();
        String nss = asignacionNSS.getNssStr();
        asignacionNSS.setNss(nss);

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
                    .generaFirmaElectronica(asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
            log.debug("Voy a generar el pdf");

            if (infoWS) {
                // 3. Generacion del reporte con informacion del sello digital
                documentByteArray = (byte[]) documentosServiceRemote.getConstanciaVigenciaWS(asignacionNSS, firmaElectronica, usuario);
            } else {
                // 3. Generacion del reporte con informacion del sello digital
                documentByteArray = (byte[]) documentosServiceRemote.getConstanciaVigenciaInternetRecortado(asignacionNSS, firmaElectronica, usuario);
            }

            // 4. Se guarda el reporte
            firmaDigitalBusinessRemote
                    .guardarArchivoFirmado(firmaElectronica.getReciboNotarial(), "reporteVigencia" + nss + ".pdf", documentByteArray);
            log.debug("Termino de guardar en notaria en portal y con WS " + infoWS);

            Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
            String homoclave = solicitud.getTramites().get(0).getTipoTramite().getHomoclave();

            resultado.put("homoclaveTramite", homoclave);
            resultado.put("documento", documentByteArray);
            resultado.put("tramiteId", tramiteId);
            resultado.put("folio", solicitud.getNoFolioSolicitud());
            resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
            resultado.put("secuenciaNotarial", firmaElectronica.getSecuenciaNotaria());
            resultado.put("solicitud", solicitud);
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
        // Envio de correo
        try {
            // tramiteDocumentosService.enviaCorreo(nss, res, null);
            // String subject = "Reporte de vigencia de derechos";
            Map<String, String> paramAdicionales = new HashMap<String, String>();

            paramAdicionales.put("folio", folio);
            paramAdicionales.put("idSolicitud", idSolicitud);
            paramAdicionales.put("folioCifrado", Base64Cipher.cifrar(folio));

            Map<String, byte[]> doctos = new HashMap<String, byte[]>();
            doctos.put("comprobanteVigenciaDerechos" + nss.getNss() +  ".pdf", docto);

            // tramiteDocumentosServiceRemote.enviaCorreo(nss, docto, null);

            serviceBusiness.enviarCorreoPersonaFisica(nss, TipoTramiteEnum.CONSULTA_DE_VIGENCIA_DE_DERECHOS, paramAdicionales,
                    "IMSS DIGITAL REPORTE DE VIGENCIA DE DERECHOS", doctos);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private RedirectView procesarErrorEnPantallaInicio(String mensajeError, HttpSession session) {

        Fisica fisica = new Fisica();
        fisica.setErrorFormGeneral(mensajeError);
        // Subimos al request el objeto de Modelo
        session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
        // retornamos el redirec view
        return new RedirectView("/vigencia/", true);
    }
    
	@RequestMapping(value="/actualizarCorreo/sisec", method=RequestMethod.POST	)
	public Object actualizarCorreoSISEC(@RequestParam String identificador , @RequestParam String correoElectronico,
			@RequestParam String nss, @RequestParam String curp, @RequestParam Integer codigoActualizacion, 
			Model model,HttpServletResponse response, HttpSession session) throws SolicitudNssCorreoException{
		
		log.debug ("los parametros encriptados son identificador: " + identificador );
		log.debug ("los parametros encriptados son correoElectronico: " + correoElectronico);
		log.debug ("los parametros encriptados son curp: " + curp);
		log.debug ("los parametros encriptados son nss: " + nss);
		log.debug ("los parametros encriptados son codigoActualizacion: " + codigoActualizacion);
		
		Fisica fisica = new Fisica();
		Fisica fisicaBusqueda = new Fisica ();
		String mensajeError;
		
		HashMap<String, Object> decode = new HashMap<String, Object>();
		
		decode = this.validaParametros(identificador,correoElectronico,nss,curp);

        List <Fisica> fisicas = personaBusinessRemote.obtenerPersonaNssByCurpNoIndActivo((String)decode.get("curpDeco"));
        fisicaBusqueda = fisicas.get(0);
        
        List<Solicitud> consultaSolicitudes = solicitudBusiness.obtenerSolicitudPorPersona(
        		fisicaBusqueda.getIdPersona(),TipoPersonaFiscal.FISICA,TipoSolicitudEnum.ACTUALIZACION_CORREO_ELECTRONICO,EstadoSolicitudEnum.PENDIENTE_AUTORIZACION,false);
        
        //log.debug ("las solciitudes de act del usuario son: " + consultaSolicitudes);
        log.debug ("el numero de solicitudes pendientes son: " + consultaSolicitudes.size());
        
        if(consultaSolicitudes.size()>0){
        	
        	Solicitud solicitudPendiente = consultaSolicitudes.get(0);
        	String descripcionSubdelegacion = solicitudNssCorreoServiceBusiness.consultarSubdelegacion(solicitudPendiente.getSubdelegacion().getId().intValue());
			String descripcionSubdelegacionLimpio = descripcionSubdelegacion.replaceAll("(?i)\\bsubdelegacion\\b", "").trim().replaceAll("\\s{2,}", " ");
        	
        	mensajeError = "USTED YA CUENTA CON UN TR&Aacute;MITE POR AUTORIZAR EN LA SUBDELEGACI&Oacute;N " +descripcionSubdelegacionLimpio+ 
        			". ESPERE LA RESPUESTA DE LA SUBDELEGACI&Oacute;N";
        	fisica.setErrorFormGeneral(mensajeError);
			model.addAttribute("fisica", fisica);
			session.setAttribute("origenExterno", true);
			session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			return "errorActualizacion";
        }
        
		if (Boolean.TRUE.equals(decode.get("validacionTimeStamp"))) {
			fisica.setCorreoElectronico(new CorreoElectronico());
			fisica.setCorreoElectronicoFiscal(new CorreoElectronico());
			fisica.getCorreoElectronico().setCorreo((String)decode.get("correoElectronicoDeco"));
			fisica.getCorreoElectronicoFiscal().setCorreo((String)decode.get("correoElectronicoDeco"));
			fisica.setCurp((String)decode.get("curpDeco"));
			fisica.setNss((String)decode.get("nssDeco"));
			
			String tokenUsuario = fisica.getCurp().substring(0, 10)+ fisica.getNss();
			log.debug("El token del usuario es "+ tokenUsuario);
			
			model.addAttribute("fisica", fisica);
			session.setAttribute("fisica", fisica);
			session.setAttribute("codigoActualizacion", codigoActualizacion);
			session.setAttribute("origenExterno", true);
			session.setAttribute("TOKEN_USUARIO", tokenUsuario);
			
			return "actCorreo";
			
		} else {
			mensajeError = "ERROR DE COMUNICACION CON EL SISTEMA DE SEMANAS COTIZADAS";
			fisica.setErrorFormGeneral(mensajeError);
			session.setAttribute("origenExterno", true);
			session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			return "errorActualizacion";
		}
		
	}
	
    private HashMap<String, Object> validaParametros (String identificador , String correoElectronico,String nss, 
    		String curp) throws SolicitudNssCorreoException{
    	
    	HashMap<String, Object> parametrosDecodificados = new HashMap<String, Object>();
    	
    	String timestampDeco = new String();
    	String correoElectronicoDeco = new String();
    	String nssDeco = new String();
    	String curpDeco = new String();
    	
    	String token = this.solicitudNssCorreoServiceBusiness.getToken();
    	
    	timestampDeco = this.decrypt(identificador, token);
    	
        if (validarTimestamp(timestampDeco)) {
        	
	    	correoElectronicoDeco = this.decrypt(correoElectronico, timestampDeco);
	    	nssDeco = this.decrypt(nss, timestampDeco);
	    	curpDeco = this.decrypt(curp, timestampDeco);
	    	
	    	log.debug ("el correo deco " + correoElectronicoDeco);
	    	log.debug ("el nss deco " + nssDeco);
	    	log.debug ("el curp deco " + curpDeco);
	    	
			// Guardar en HashMap
	    	parametrosDecodificados.put("validacionTimeStamp", true);
			parametrosDecodificados.put("correoElectronicoDeco", correoElectronicoDeco);
			parametrosDecodificados.put("nssDeco", nssDeco);
			parametrosDecodificados.put("curpDeco", curpDeco);
            System.out.println("Timestamp valido.");
            
        } else {
        	parametrosDecodificados.put("validacionTimeStamp", false);
            System.out.println("Timestamp fuera de rango.");
        }

		return parametrosDecodificados;

    }
    
    public static String decrypt(String encryptedText, String semilla) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(semilla.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.decodeBase64(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar", e);
        }
    }
    
    public static boolean validarTimestamp(String timestampDeco) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            sdf.setLenient(false);

            Date fechaDecodificada = sdf.parse(timestampDeco);

            String fechaActualStr = sdf.format(new Date());
            Date fechaActual = sdf.parse(fechaActualStr);

            // Calcular diferencia en milisegundos
            long diferenciaMillis = Math.abs(fechaActual.getTime() - fechaDecodificada.getTime());

            // Convertir a segundos
            long diferenciaSegundos = diferenciaMillis / 1000;

            // Holgura fija de 1 minuto (60 segundos)
            return diferenciaSegundos <= 60;

        } catch (Exception e) {
            throw new RuntimeException("Error al comparar fechas", e);
        }
    }
    
    @RequestMapping(value ="/actualizarCorreo", method = RequestMethod.POST)
    public Object actualizarCorreo (@ModelAttribute Fisica fisica, Model model, HttpSession session,
    		HttpServletRequest request) throws SolicitudNssCorreoException {
    	
    	Fisica fisicaBusqueda = new Fisica ();
    	String mensajeError;
    	
        List <Fisica> fisicas = personaBusinessRemote.obtenerPersonaNssByCurpNoIndActivo(fisica.getCurp());
        fisicaBusqueda = fisicas.get(0);
        
        List<Solicitud> consultaSolicitudes = solicitudBusiness.obtenerSolicitudPorPersona(
        		fisicaBusqueda.getIdPersona(),TipoPersonaFiscal.FISICA,TipoSolicitudEnum.ACTUALIZACION_CORREO_ELECTRONICO,EstadoSolicitudEnum.PENDIENTE_AUTORIZACION,false);
        
        //log.debug ("las solciitudes de act del usuario son: " + consultaSolicitudes);
        log.debug ("el numero de solicitudes pendientes son: " + consultaSolicitudes.size());
        
        if(consultaSolicitudes.size()>0){
        	
        	Solicitud solicitudPendiente = consultaSolicitudes.get(0);
        	String descripcionSubdelegacion = solicitudNssCorreoServiceBusiness.consultarSubdelegacion(solicitudPendiente.getSubdelegacion().getId().intValue());
			String descripcionSubdelegacionLimpio = descripcionSubdelegacion.replaceAll("(?i)\\bsubdelegacion\\b", "").trim().replaceAll("\\s{2,}", " ");
        	
        	mensajeError = "USTED YA CUENTA CON UN TR&Aacute;MITE POR AUTORIZAR EN LA SUBDELEGACI&Oacute;N " +descripcionSubdelegacionLimpio+ 
        			". ESPERE LA RESPUESTA DE LA SUBDELEGACI&Oacute;N";
			fisica.setErrorFormGeneral(mensajeError);
			session.setAttribute("origenExterno", false);
			session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			return "errorActualizacion";
        }

		log.debug("---------LOS DATOS DE LA SESION en fisica son: " + fisica);
		
		String tokenUsuario = fisica.getCurp().substring(0, 10)+ fisica.getNss();
		log.debug("El token del usuario es "+ tokenUsuario);
	
		session.setAttribute("origenExterno", false);
		session.setAttribute("TOKEN_USUARIO", tokenUsuario);

        return "actCorreo";
    }
    
	//Se remueve funcionalidad por incidente INC899500
	/*@RequestMapping(value = "/capturarDocumentacion", method = RequestMethod.POST)
	public Object capturarDocumentacion(@ModelAttribute Fisica fisica, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws SolicitudNssCorreoException {

		log.debug("LOS DATOS DE LA SESION SON CORREO: " + fisica.getCorreoElectronico());
		log.debug("LOS DATOS DE LA SESION SON CURP: " + fisica.getCurp());
		
    	log.debug("datos de captcha: " + fisica.getBusqAprox());
    	
    	String mensajeError = null;
    	String captcha = fisica.getBusqAprox();
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }

		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();

		List<Fisica> fisicas = personaBusinessRemote.obtenerPersonaNssByCurpNoIndActivo(fisica.getCurp());
		fisica = fisicas.get(0);
		fisica.setCorreoElectronico(new CorreoElectronico());
		fisica.setCorreoElectronicoFiscal(new CorreoElectronico());
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);
		session.setAttribute("fisica", fisica);

		List<GrupoFamiliar> grupoFam;

		try {
			grupoFam = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNss(fisica.getNss());
			ArrayList<Integer> listaUMF = new ArrayList<Integer>();

			for (GrupoFamiliar integrante : grupoFam) {
				BigDecimal valorParentesco = integrante.getParentesco() != null
						? BigDecimal.valueOf(integrante.getParentesco().getIdParentesco())
						: null;
				if (valorParentesco != null
						&& (valorParentesco.equals(new BigDecimal(5)) || valorParentesco.equals(new BigDecimal(6)))) {
					Long umfLong = integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
					if (umfLong != null) {
						int umf = umfLong.intValue(); 
						listaUMF.add(umf);
					}
				}
			}
			log.debug("---- LA LISTA UMF DE ASEGURADO ES: " + listaUMF);

			int umfAsegurado = -1;

			if (!listaUMF.isEmpty()) {
				umfAsegurado = listaUMF.get(0);
			}

			log.debug("---- LA UMF DE ASEGURADO ES: " + umfAsegurado);

			ArrayList<Integer> umfRandom = this.generaNumUmfAleatorios(umfAsegurado);
			
			List<String> resultadosUMF = this.solicitudNssCorreoServiceBusiness
					.consultarUMF(umfRandom);
			
			
			log.debug("las respuestas de las UMF's, son: " + resultadosUMF);

			String umfAseguradoEncontrada = umfRandom.get(9).toString();
			log.debug("umfAseguradoEncontrada -> " + umfAseguradoEncontrada);
			
			session.setAttribute("umfAseguradoEncontrada", umfAseguradoEncontrada);

			for (int i = 0; i < resultadosUMF.size(); i++) {
				String resultadoUFM = resultadosUMF.get(i);
				request.setAttribute("opcionUMF" + (i + 1), resultadoUFM);
			}

		} catch (Exception e) {
			log.error("Ocurrio un error al consultar la UMF del asegurado. " + e.getMessage());
			e.printStackTrace();
		}

		return "capturaDocs";
	}*/
	
	private ArrayList<Integer> generaNumUmfAleatorios(final int umfAsegurado) {

		Random random = new Random();
		int min = 1;
		int max = 1650;

		ArrayList<Integer> numerosGenerados = new ArrayList<Integer>();

		// Si el asegurado no cuenta con UMF, debera generar un numero adicional.
		
		if (umfAsegurado == -1) {


	        int[] numeros = {681, 745, 591, 300, 43, 440, 759, 796, 851, 121, 379, 650, 67, 255, 360, 492, 692, 1105, 143, 319, 666, 706, 1693, 1751, 1721};
	        int n = 10;
	        
	        // Verificar que 'n' no sea mayor que la longitud del array
	        n = Math.min(n, numeros.length);

	        // Generar 'n' indices aleatorios y agregar los numeros correspondientes al ArrayList
	        for (int i = 0; i < n; i++) {
	            int indiceAleatorio = random.nextInt(numeros.length);
	            numerosGenerados.add(numeros[indiceAleatorio]);
	        }
			
		} else {
			int numGenerados = 9;
			while (numerosGenerados.size() < numGenerados) {
				int numeroAzar = random.nextInt(max - min + 1) + min;
				if (numeroAzar != umfAsegurado && !numerosGenerados.contains(numeroAzar)) {
					numerosGenerados.add(numeroAzar);
				}
			}
		}

		if (umfAsegurado != -BigInteger.ONE.intValue()) {
			numerosGenerados.add(umfAsegurado);
		}

		log.debug("se generaron los numeros al azar, son: " + numerosGenerados);

		return numerosGenerados;
	}
	
/*    @RequestMapping(value = "/validarCURP", method = RequestMethod.POST)
    private Object validacionCURP(@RequestParam("curpBen") String curpBen, @RequestParam("uploadfile") MultipartFile file,Fisica fisica, Model model, HttpSession session,
    		@RequestParam("captcha") String captcha, HttpServletRequest request, HttpServletResponse response) throws DocumentoException{
    	
    	log.debug("Valor capturado en 'captcha': " + captcha);
    	String mensajeError = null;
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }
    	
		try {
			byte[] fileBytes = file.getBytes();
	    	session.setAttribute("documentoAdjuntoSession", fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	Fisica fisicaEnSesion = (Fisica) session.getAttribute("fisica");
    	log.debug("Valor capturado en 'curpBen': " + curpBen);
    	log.debug("Valor de fisica en session es de : " + fisicaEnSesion);

    	boolean curpEncontrada = false;
    	List <GrupoFamiliar> grupoFam;
    	Integer edoSolicitud = null;
    	
        try {
        	grupoFam = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNss(fisicaEnSesion.getNss());
        	List<String> listaCurp = new ArrayList<String>();

        	for (GrupoFamiliar integrante : grupoFam) {
        		BigDecimal valorParentesco = integrante.getParentesco() != null ? BigDecimal.valueOf(integrante.getParentesco().getIdParentesco()) : null;
        	    if (valorParentesco != null && (!valorParentesco.equals(new BigDecimal(5)) || !valorParentesco.equals(new BigDecimal(6)))) {
        	        String curp = integrante.getDerechohabiente().getCurp();
        	        if (curp != null && !curp.isEmpty()) {
        	            listaCurp.add(curp);
        	        }
        	    }
        	}
        	
        	for (String curp : listaCurp) {
        	    if (curp.equals(curpBen)) {
        	        curpEncontrada = true;
        	        break; 
        	    }
        	}
        	

        	
        } catch (Exception e) {
        	log.error("Exception ocurred " + e.getMessage());
            e.printStackTrace(); 
        }
        
    	if (curpEncontrada) {
    		edoSolicitud = EstadoSolicitudEnum.ATENDIDA.getCodigo();
    		return this.saveActualizacion(fisicaEnSesion,edoSolicitud, SIN_EFIRMA, CURP_BENEFICIARIO, curpBen,
    				null, session, request, response, model);
    	} else {

    		Boolean seleccionarSubdelegacion = true;
    		Boolean bloqueoFormulario = true;
    		session.setAttribute("seleccionarSubdelegacion", seleccionarSubdelegacion);
    		session.setAttribute("bloqueoFormulario", bloqueoFormulario);
    		return "capturaDocs";
    	}
 	

    }*/
    
    
/*    @RequestMapping(value = "/validarUMF", method = RequestMethod.POST)
    private Object validacionUMF(@RequestParam("umfAdsc") String umfAdsc, @RequestParam("uploadfile") MultipartFile file, 
    		@RequestParam("captcha") String captcha, Fisica fisica, Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) throws DocumentoException{
		
    	String mensajeError = null;
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }
    	
    	try {
			byte[] fileBytes = file.getBytes();
	    	session.setAttribute("documentoAdjuntoSession", fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		log.debug("Valor capturado en 'captcha': " + captcha);
    	log.debug("Valor capturado en 'umfAdsc': " + umfAdsc);
    	Fisica fisicaEnSesion = (Fisica) session.getAttribute("fisica");
    	log.debug("Valor de fisica en session es de : " + fisicaEnSesion);
    	String umfAseguradoEncontradaSession = (String) session.getAttribute("umfAseguradoEncontrada");
    	log.info("umfAseguradoEncontrada " + umfAseguradoEncontradaSession);

    	boolean umfEncontrada = false;
    	Integer edoSolicitud = null;
    	
        try {
        	
        	if (umfAseguradoEncontradaSession.equals(umfAdsc)){
        		umfEncontrada = true;
        		log.debug("las umf son iguales: " + umfEncontrada );
        	}

        	
        } catch (Exception e) {
        	log.error("Exception ocurred " + e.getMessage());
            e.printStackTrace(); 
        }
        
    	if (umfEncontrada) {
    		edoSolicitud = EstadoSolicitudEnum.ATENDIDA.getCodigo();
    		return this.saveActualizacion(fisicaEnSesion,edoSolicitud, SIN_EFIRMA, UMF, umfAdsc,
    				null, session, request, response, model);
    	} else {

    		Boolean seleccionarSubdelegacion = true;
    		Boolean bloqueoFormulario = true;
    		session.setAttribute("seleccionarSubdelegacion", seleccionarSubdelegacion);
    		session.setAttribute("bloqueoFormulario", bloqueoFormulario);
    		return "capturaDocs";
    	}

    }*/
    	
        
 /*   @RequestMapping(value = "/validarRFC", method = RequestMethod.POST)
    private Object validacionRFC(@RequestParam("rfcPat") String rfcPat, @RequestParam("uploadfile") MultipartFile file, Fisica fisica, Model model, HttpSession session,
    		@RequestParam("captcha") String captcha, HttpServletRequest request, HttpServletResponse response) throws DocumentoException{
    	
    	log.debug("Valor capturado en 'captcha': " + captcha);
    	
    	String mensajeError = null;
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }
    	
		try {
			byte[] fileBytes = file.getBytes();
	    	session.setAttribute("documentoAdjuntoSession", fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	log.debug("Valor capturado en 'rfcPat': " + rfcPat);
    	Fisica fisicaEnSesion = (Fisica) session.getAttribute("fisica");
    	log.debug("Valor de fisica en session es de : " + fisicaEnSesion);
    	AsignacionNSS nss = new AsignacionNSS();
    	nss.setIdAsignacionNSS(fisicaEnSesion.getCveIdAsignacionNSS());
    	boolean rfcEncontrado = false;
    	List<String> listaRfcPatronesFisica;
    	List<String> listaRfcPatronesMoral;
    	List<String> listaRegPatronal;
    	List<Long> ids;
    	Integer edoSolicitud = null;
    	
        try {
        	
        	ids = grupoFamiliarServiceRemote.getListaIdsPatrones(nss);
        	
			if (ids != null) {

				log.debug("Los id's de los patrones del nss " + nss + " son:  " + ids);

				listaRfcPatronesFisica = solicitudNssCorreoServiceBusiness.getListaRfcPatronesFisica(ids);
				listaRfcPatronesMoral = solicitudNssCorreoServiceBusiness.getListaRfcPatronesMoral(ids);
				listaRegPatronal = solicitudNssCorreoServiceBusiness.getListaRegistroPatronal(ids);

				// Iterar sobre listaRfcPatronesFisica
				for (String rfc : listaRfcPatronesFisica) {
					if (rfc.equals(rfcPat)) {
						// Coincidencia encontrada
						rfcEncontrado = true;
						System.out.println("Coincidencia encontrada en listaRfcPatronesFisica: " + rfc);
						break; 
					}
				}

				// Iterar sobre listaRfcPatronesMoral
				for (String rfc : listaRfcPatronesMoral) {
					if (rfc.equals(rfcPat)) {
						rfcEncontrado = true;
						System.out.println("Coincidencia encontrada en listaRfcPatronesMoral: " + rfc);
						break; 
					}
				}

				// Iterar sobre listaRegPatronal
				for (String regPatronal : listaRegPatronal) {
					// Obtener los primeros 8 caracteres de regPatronal
					String primeros8Caracteres = regPatronal.length() > 8 ? regPatronal.substring(0, 8) : regPatronal;

					if (primeros8Caracteres.equals(rfcPat.substring(0, Math.min(8, rfcPat.length())))) {
						rfcEncontrado = true;
						System.out.println("Coincidencia encontrada en listaRegPatronal: " + regPatronal);
						break; 
					}
				}
			}
        	
        } catch (Exception e) {
        	log.error("Exception ocurred " + e.getMessage());
            e.printStackTrace(); 
        }
        
    	if (rfcEncontrado) {
    		edoSolicitud = EstadoSolicitudEnum.ATENDIDA.getCodigo();
    		return this.saveActualizacion(fisicaEnSesion,edoSolicitud, SIN_EFIRMA, RP, rfcPat,
    				null, session, request, response, model);
    	} else {
    		Boolean seleccionarSubdelegacion = true;
    		Boolean bloqueoFormulario = true;
    		session.setAttribute("seleccionarSubdelegacion", seleccionarSubdelegacion);
    		session.setAttribute("bloqueoFormulario", bloqueoFormulario);
    		return "capturaDocs";
    	}
    }*/
    
    @RequestMapping(value = "/autorizacionPendiente", method = RequestMethod.POST)
    private Object autorizacionPendiente(@RequestParam("uploadfile") MultipartFile file, Fisica fisica, Model model, HttpSession session,
    		@RequestParam("captcha") String captcha, HttpServletRequest request, HttpServletResponse response) throws DocumentoException {
		
    	log.debug("Valor capturado en 'captcha': " + captcha);
    	
    	String mensajeError = null;
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }
        
    	try {
			byte[] fileBytes = file.getBytes();
	    	session.setAttribute("documentoAdjuntoSession", fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	log.debug("entro a generar la solicitud pendiente");
    	Fisica fisicaEnSesion = (Fisica) session.getAttribute("fisica");
    	log.debug("Valor de fisica en session es de : " + fisicaEnSesion);
    	
		Boolean seleccionarSubdelegacion = true;
		Boolean bloqueoFormulario = true;
		session.setAttribute("seleccionarSubdelegacion", seleccionarSubdelegacion);
		session.setAttribute("bloqueoFormulario", bloqueoFormulario);
		return "capturaDocs";
    }
    
    @RequestMapping("/obtenerDatosDesdeCodigoPostal")
    @ResponseBody
    public Map<String, Object> obtenerDatosDesdeCodigoPostal(@RequestParam("codigoPostal") 
    String codigoPostal,HttpSession session, HttpServletRequest request, Model model) {
    	Map<String, Object> response = new HashMap<String, Object>();

        try {
            // Obtener lista de UnidadMedicaFamiliar
            List<Subdelegacion> listaSubdelegacion = domicilioServiceBusiness.getSubDelegacionesPorCodigoPostal(codigoPostal);
            // Verificar si la lista no esta vacia
            if (!listaSubdelegacion.isEmpty()) {
                log.debug("las unidades medicas disponibles" + listaSubdelegacion);
            	response.put("subdelegacion", listaSubdelegacion);
            } else {
                // Manejar el caso en el que la lista este vacia
                response.put("error", "No se encontraron datos para el c\u00F3digo postal proporcionado.");
            }
        } catch (SubDelegacionNoLocalizadaException e) {
            // Manejar otras excepciones si es necesario
            response.put("error", "Error al obtener datos desde el c\u00F3digo postal.");
            e.printStackTrace();
        }
        
        return response;
    }
    
/*    @RequestMapping(value = "/finalizarTramite", method = RequestMethod.POST)
    public Object finalizarTramite(@RequestParam("subdelegacion") String subdelegacion, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DocumentoException {
    	
    	Fisica fisicaEnSesion = (Fisica) session.getAttribute("fisica");
    	log.debug("Valor de fisica en session es de : " + fisicaEnSesion);
    	log.debug("Valor de subdelegacion en session es de : " + subdelegacion);
    	session.setAttribute("subdelegacionSession", subdelegacion);
        
        return this.saveActualizacion(fisicaEnSesion, EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo(), SIN_EFIRMA, ASIGNAR_VENTANILLA, null,
        		null, session, request, response, null);
    }*/
	
    /*@RequestMapping(value = "/guardarActualizacion", method = RequestMethod.POST)
    public Object guardarDatos(@ModelAttribute Fisica fisica, BindingResult result,
            HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws DocumentoException {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String mensajeError = null;
    	
    	log.debug("datos salida back" + fisica.getNssCifrado());
    	log.debug("datos de captcha: " + fisica.getBusqAprox());
    	
    	String captcha = fisica.getBusqAprox();
    	
        // Se valida el captcha
        try {
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            captchaSD.setCaptchaValue(captcha);
            CaptchaUtilSD.validaCaptachaSesion(captchaSD);
        } catch (CaptchaExceptionSD e) {
    	    mensajeError = "CAPTCHA NO COINCIDE, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
            return VIEW_LOGIN_GOBMX;
        }
    	
    	try {
    		FirmaElectronicaResponseDTO dto = mapper.readValue(fisica.getNssCifrado(), FirmaElectronicaResponseDTO.class);

    	    log.debug("Token: " + dto.getToken());
    	    log.debug("RFC: " + dto.getRfc());
    	    log.debug("CURP: " + dto.getCurp());
    	    log.debug("getTexto: " + dto.getTexto());
    	    log.debug("getVigIni: " + dto.getVigIni());
    	    log.debug("getVigFin: " + dto.getVigFin());
    	    log.debug("getSerieCert: " + dto.getSerie_cert());
  	        	    
    	    
    	    if (dto.getToken() == null || dto.getToken().trim().isEmpty() ||
    	    	    dto.getTexto() == null || dto.getTexto().trim().isEmpty() ||
    	    	    dto.getVigIni() == null || dto.getVigIni().trim().isEmpty() ||
    	    	    dto.getVigFin() == null || dto.getVigFin().trim().isEmpty() ||
    	    	    dto.getRfc() == null || dto.getRfc().trim().isEmpty() ||
    	    	    dto.getSerie_cert() == null || dto.getSerie_cert().trim().isEmpty() ||
    	    	    dto.getCurp() == null || dto.getCurp().trim().isEmpty() ||
    	    	    !dto.getCurp().equals(fisica.getCurp()) ||
    	    	    !dto.getTexto().equals("EXITO") || dto.getResultado() != 0) {

    	    	    log.debug("Al menos uno de los campos obligatorios es inválido");
    	    	    mensajeError = "ERROR EN EL SISTEMA, INTENTE NUEVAMENTE";
    	    	    fisica.setErrorFormGeneral(mensajeError);
    	    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
    	    	    return "errorActualizacion";
    	    	} else {
    	    		return this.saveActualizacion(fisica,EstadoSolicitudEnum.ATENDIDA.getCodigo(), 
    	    				EFIRMA,null,null,result, session, request, response, model);
    	    	}


    	} catch (Exception e) {
    	    log.error("Error al parsear JSON", e);
    	}
    	
    	return null;
    	
    }*/
    
    private Object saveActualizacion(Fisica fisica, Integer edoSolicitud, Integer flujoActualizacion, Integer preguntaSeleccionada,
    		String respuestaSeleccionada, BindingResult result, HttpSession session,
            HttpServletRequest request,HttpServletResponse response, Model model) throws DocumentoException{

    	String mensajeError;
    	String validaSession = (String) session.getAttribute("TOKEN_USUARIO");
    	log.debug("El token del usuario en session es "+ validaSession);

        List <Fisica> fisicas = personaBusinessRemote.obtenerPersonaNssByCurpNoIndActivo(fisica.getCurp());
        Fisica fisicaBusqueda = fisicas.get(0);
        List<Solicitud> consultaSolicitudes = solicitudBusiness.obtenerSolicitudPorPersona(fisicaBusqueda.getIdPersona(),TipoPersonaFiscal.FISICA,
        		TipoSolicitudEnum.ACTUALIZACION_CORREO_ELECTRONICO,EstadoSolicitudEnum.PENDIENTE_AUTORIZACION,false);
        
    	String tokenUsuario = fisica.getCurp().substring(0, 10)+ fisicaBusqueda.getNss();
    	log.debug("El token del usuario es "+ tokenUsuario);
    	

    	if (validaSession == null || validaSession.isEmpty() || !validaSession.equals(tokenUsuario) || consultaSolicitudes.size()>1) {
    	    mensajeError = "ERROR EN EL SISTEMA, INTENTE NUEVAMENTE";
    	    fisica.setErrorFormGeneral(mensajeError);
    	    session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
    	    return "errorActualizacion";
    	}
    	
		boolean esDominioPermitido;
		String correo = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		String curp = fisica.getCurp();
		
        try {

			esDominioPermitido = this.solicitudNssCorreoServiceBusiness.esDominioPermitido(correo);

		    if (!esDominioPermitido) {
		        throw new SolicitudNssCorreoException("El dominio del correo electr&oacute;nico es inv&aacute;lido. Intente con otro");
		    }

		} catch (SolicitudNssCorreoException e) {
			log.warn("Error solicitud nss correo no valida", e);
			fisica.setErrorFormGeneral(e.getMessage());
			// Subimos al request el objeto de Modelo
			request.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			return VIEW_LOGIN_GOBMX;
		};
    	
    	byte[] documentoAdjunto = (byte[]) session.getAttribute("documentoAdjuntoSession");
    	String subdelegacionSeleccionadaStr = (String) session.getAttribute("subdelegacionSession");
    	Long subdelegacionSeleccionada = null;

    	if (subdelegacionSeleccionadaStr != null && !subdelegacionSeleccionadaStr.isEmpty()) {
    	    try {
    	        subdelegacionSeleccionada = Long.parseLong(subdelegacionSeleccionadaStr);
    	    } catch (NumberFormatException e) {
    	        e.printStackTrace(); 
    	    }
    	}
        session.removeAttribute(KEY_DOCTO_VIGENCIA);
        Integer codigoActualizacionInteger = (Integer) session.getAttribute("codigoActualizacion");
        int codigoActualizacion = codigoActualizacionInteger.intValue();
        log.debug("El codigo de actualizacion en session al momento de guardar es: " + codigoActualizacion);
        
        Boolean esExterno = (Boolean)session.getAttribute("origenExterno");
        Integer origenAplicacion = null;
        
        if (esExterno) {
        	origenAplicacion = 2;
        } else {
        	origenAplicacion = 1;
        }
        
        SolicitudNssCorreo nssCorreo;
        String mensajeErrror = "";
        Long tipoTramite = 67L;
        String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
        String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
        
        
        fisica = fisicas.get(0);
        fisica.setCorreoElectronico (new CorreoElectronico());
        fisica.setCorreoElectronicoFiscal (new CorreoElectronico());
		if(esGmail(correoCapturado)){
			fisica.getCorreoElectronico().setCorreo(removerPuntos(correoCapturado));
	        fisica.getCorreoElectronicoFiscal().setCorreo(removerPuntos(correoConfirmacion));
		}else{
			fisica.getCorreoElectronico().setCorreo(correoCapturado);
	        fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);
		}

        log.debug(fisica.getCorreoElectronico().getCorreo());
        
        log.debug("------LA PERSONA COMPUESTA FINAL ES; " + fisica);
        log.debug("el estado de la solicitud es: " + edoSolicitud);
        
		if (edoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			
			log.debug("se actualizara el correo en la tabla");
			try {
				nssCorreo = new SolicitudNssCorreo();
				nssCorreo.setCorreo(fisica.getCorreoElectronico());					
				nssCorreo.setCurp(fisica.getCurp());
				nssCorreo.setCveIdTipoSolicitud(tipoTramite);

				this.solicitudNssCorreoServiceBusiness.actualizarPorCodigo(nssCorreo, codigoActualizacion);

			} catch (SolicitudNssCorreoException e) {
				log.warn("Error solicitud nss correo no valida", e);
				fisica.setErrorFormGeneral(e.getMessage());
				// Subimos al request el objeto de Modelo
				session.setAttribute(KEY_FISICA_ERRORES_NEG, fisica);
			}
		}
        
        TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();
      
        try {
        	Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
        	identificadoresMap.put("tramite", 177);
        	identificadoresMap.put("solicitud",TipoSolicitudEnum.ACTUALIZACION_CORREO_ELECTRONICO.getValor());
        	
        	TramiteActualizacionCorreo xml = new TramiteActualizacionCorreo ();
        	xml.setOrigenAplicacion(origenAplicacion);
        	xml.setFlujoActualizacion(flujoActualizacion);
        	xml.setPreguntaSeleccionada(preguntaSeleccionada);
        	xml.setRespuestaSeleccionada(respuestaSeleccionada);
        	
        	
        	AsignacionNSS asignacionNSS = new AsignacionNSS ();
        	asignacionNSS.setNss(fisica.getNss());
        	asignacionNSS.setCorreoElectronico(fisica.getCorreoElectronico());
        	
        	//Generar la solicitud por tipo 
        	Solicitud solicitud = new Solicitud();
        	solicitud = this.creaSolicitudPorEstado (asignacionNSS, identificadoresMap, edoSolicitud, fisica, subdelegacionSeleccionada, xml);
        	log.debug("el resultado solicitud es -----" + solicitud);
        	log.debug("*****fin del log de prueba ******" );
        	
        	Tramite tramite = solicitud.getTramites().get(0);
        	this.solicitudNssCorreoServiceBusiness.saveDitActualizacionCorreo(tramite.getTramiteId(),xml);
        	
        	//Se genera el reporte		
            Map<String, Object> resultado = this.generaReporte(asignacionNSS, solicitud, fisica, documentoAdjunto);
            log.debug("el resultado de genera reporte es -----" + resultado);
            
            byte[] reporte = (byte[]) resultado.get("documento");
            long tramiteId = (Long) resultado.get(KEY_ID_TRAMITE);
            String folio = (String) resultado.get("folio");
            String idSolicitud = (String) resultado.get("idSolicitud");
            
            session.setAttribute(KEY_FOLIO_SOL, folio);
            session.setAttribute(KEY_ID_TRAMITE, tramiteId);
            session.setAttribute(KEY_DOCTO_VIGENCIA, reporte);
            session.setAttribute(KEY_ID_SOLICITUD, idSolicitud);
            session.setAttribute(KEY_NSS, fisica.getNss());
            session.setAttribute("homoclaveTramite", resultado.get("homoclaveTramite"));
            session.setAttribute("solicitudVigencia", resultado.get("solicitud"));
            	
			Map<String, String> paramAdicionales = new HashMap<String, String>();

			paramAdicionales.put("folio", folio);
			paramAdicionales.put("idSolicitud", idSolicitud);
			paramAdicionales.put("folioCifrado", Base64Cipher.cifrar(folio));

			Map<String, byte[]> doctos = new HashMap<String, byte[]>();
			doctos.put("reporteActualizacion" + fisica.getNss() + ".pdf", reporte);

			serviceBusiness.enviarCorreoPersonaFisica(fisica, TipoTramiteEnum.ACTUALIZACION_CORREO_ELECTRONICO,
					paramAdicionales, "IMSS DIGITAL REPORTE DE ACTUALIZACION DE CORREO", doctos);
			log.debug("-----SE ENVIO EL CORREO ELECTRONICO-----");


            log.debug("deberia salir del catch y mandar al la pagina principal ");

            
            
        } catch (DocumentoException ex) {
            this.log.error(ex);
            mensajeErrror = ex.getMessage();
            tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri\u00F3 un error al generar el reporte de actualizacion.");
        } catch (Exception e2) {
            e2.printStackTrace();
            mensajeErrror = e2.getMessage();
            tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri\u00F3 un error al generar el reporte de actualizacion.");
        }
        
        session.setAttribute(KEY_TRAMITE_ASEGURADO, tramiteAsegurado);
        
        log.debug("deberia salir del catch y mandar al la pagina de finalizacion ");

        return new RedirectView("/vigencia/finalizado", true);   
    }
    
    private String removerPuntos(String correo){
    	//SPLIT DEL ARROBA
    	String correoSinDominio = correo.split("@")[0];
    	return correoSinDominio.replace(".", "") + "@gmail.com";
    }
    
    private boolean esGmail(String correo){
    	return correo.contains("@gmail.com"); //GMAIL
    }
    
    private boolean existeDuplicidad(String correo, String curp){
    	return this.solicitudNssCorreoServiceBusiness.esIgualAlAnterior(correo, curp);
    }
    
    private boolean esValidoElCorreoGmail(String correo){
    	String parteLocal = correo.split("@")[0];
		if (parteLocal.contains("-") || parteLocal.contains("+")) {
		    log.info("El correo: " + correo + " contiene '-' o '+'");
		    return false;
		}
		log.info("El correo: " + correo + " no contiene '-' o '+'");
		return true;
    }
    
    //TODO SE REMUEVE POR SOLICITUD DE REMOVER VALIDACIÓN DE PUNTOS
    /*
    private boolean |String correo){
    	String parteLocal = correo.split("@")[0];
    	String[] parteLocalPuntos = parteLocal.split("\\.");
    	//Si el nombre del correo tiene mas de dos puntos retorna falso
    	if(parteLocalPuntos.length > 3){
		    log.info("El correo: " + correo + " contiene mas de dos puntos");
    		return false;
    	}
		log.info("El correo: contiene el numero de puntos correctos");
		return true;
    }*/
    
    private Map<String, Object> generaReporte(AsignacionNSS asignacionNSS, Solicitud solicitud, Fisica fisica, byte[] documentoAdjunto ) 
    		throws DocumentoException {
    	
        byte[] documentByteArray = null;
        Map<String, Object> resultado = new HashMap<String, Object>();
        log.debug("entro a generar el reporte-----");

        try {
            FirmaElectronica firmaElectronica = tramiteDocumentosServiceRemote.generaFirmaElectronica
            		(asignacionNSS, solicitud, "COMPROBANTE DE ACTUALIZACION DE CORREO");
            solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
            solicitud.setSelloDigital(firmaElectronica.getRecibo());
            solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
            solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
            
            log.debug("Voy a guardar el archivo Adjunto");
    		if(documentoAdjunto != null && solicitud.getSecuenciaDeNotaria() != null){
    			firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "credencialElectorAdjunta.pdf", documentoAdjunto);
    		}
            
            
            log.debug("Voy a generar el pdf");
            
            documentByteArray = (byte[]) documentosServiceRemote.generarAcuseReciboElectronico(solicitud, fisica);
            log.debug("SE GENERO EL REPORTE CORRECTAMENTE");
            
            Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
            String homoclave = "IMSS-02-020";
            resultado.put("homoclaveTramite", homoclave);
            resultado.put("documento", documentByteArray);
            resultado.put("tramiteId", tramiteId);
            resultado.put("folio", solicitud.getNoFolioSolicitud());
            resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
            resultado.put("secuenciaNotarial", firmaElectronica.getSecuenciaNotaria());
            resultado.put("solicitud", solicitud); 
            
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
    
    private Solicitud creaSolicitudPorEstado (AsignacionNSS asignacionNSS, Map<String, Integer> identificadoresMap, 
    		Integer edoSolicitud, Fisica fisica, Long subdelegacionSeleccionada, TramiteActualizacionCorreo xmlDTO) {

        TramiteActualizacionCorreo tramiteXml = new TramiteActualizacionCorreo();
        Solicitud solicitud =new Solicitud();
        List<Tramite> tramites = new ArrayList<Tramite>();
        
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(identificadoresMap.get("solicitud").longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(edoSolicitud);
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(new Date());
		solicitud.setFechaPresentacion(new Date());
		if (edoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			solicitud.setFechaConclusion(new Date());
		}
		
		// Se settea el origen de la solicitud
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		solicitud.setOrigenSolicitud(origenSolicitud);
		
		Usuario usuarioPF = new Usuario();
		usuarioPF.setUsuario(fisica.getCurp());
		log.debug("el valor de la subdelegacion es: " + subdelegacionSeleccionada);

		if (subdelegacionSeleccionada != null) {
			log.debug("el valor de la subdelegacion es: " + subdelegacionSeleccionada);
		    
		    Subdelegacion subdelegacion = new Subdelegacion();
		    subdelegacion.setId(subdelegacionSeleccionada);
		    
		    solicitud.setSubdelegacion(subdelegacion);
		    
		}

		solicitud.setSolicitante(usuarioPF);

		
		if (fisica.getIdPersona() != null) {
		    PersonaInteresadaSolicitud personInt = new PersonaInteresadaSolicitud();
		    Persona persona = new Persona ();
		    persona.setIdPersona(fisica.getIdPersona());	    
		    personInt.setPersona(persona);
		    
		    TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
		    tipoPersona.setCveTipoInteresadaSol(1L);	    
		    personInt.setTipoPersonaInteresadaSol(tipoPersona);
		    
		    solicitud.setPersonaInteresadaSolicitud(personInt);
		}
		
		tramiteXml.setOrigenAplicacion(xmlDTO.getOrigenAplicacion());
		tramiteXml.setFlujoActualizacion(xmlDTO.getFlujoActualizacion());
		tramiteXml.setPreguntaSeleccionada(xmlDTO.getPreguntaSeleccionada());
		tramiteXml.setRespuestaSeleccionada(xmlDTO.getRespuestaSeleccionada());
		tramiteXml.setUsuario(new Usuario());
		tramiteXml.setUsuario(usuarioPF);
		
		tramiteXml.setFechaTramite(new Date());
		tramiteXml.setFechaPresentacion(new Date());
		if (edoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			tramiteXml.setFechaConclusion(new Date());
		}
		
		List <SolicitudNssCorreo> nssCurpExistente = this.solicitudNssCorreoServiceBusiness.obtenerPorCurpCorreosActivos(fisica.getCurp());
		if (nssCurpExistente != null){
			tramiteXml.setCurpADesvincularCorreo(nssCurpExistente);
		}
		tramiteXml.setPersona(fisica);
		tramiteXml.setOrigen(OrigenSolicitudEnum.INTERNET.getId().intValue());
		tramiteXml.getTipoTramite().setIdTipoTramite(177);
		tramiteXml.getEstadoTramite().setIdEstadoTramitePersona(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		tramiteXml.setAuditoria(true);

		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(identificadoresMap.get("tramite"));
		tramiteXml.setTipoTramite(tipoTramite);

		EstadoTramite estadoTramite = new EstadoTramite();
		if (edoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
			tramiteXml.setEstadoTramite(estadoTramite);
		}
		
		if (edoSolicitud.equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())) {
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
			tramiteXml.setEstadoTramite(estadoTramite);
		}
		
		tramiteXml.setCorreoCapturado(fisica.getCorreoElectronico().getCorreo());
		
		tramites.add(tramiteXml);
		
		log.debug("la lista de tramites es: " + tramites);

		solicitud.setTramites(tramites);
		
        try {
			solicitud = this.solicitudBusiness.crear(solicitud);
        } catch (SolicitudNoValidaException e) {
        	 log.debug("Error en la solicitud: " + e.getMessage());
        } 
			
			
        return solicitud;
    }
    	
    
}
