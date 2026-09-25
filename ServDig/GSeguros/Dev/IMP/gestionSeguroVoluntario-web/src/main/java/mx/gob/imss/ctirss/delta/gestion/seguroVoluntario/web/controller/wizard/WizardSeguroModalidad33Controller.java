package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.CriptoUtilities;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroCvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.UtilConvert;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.WebServiceCallerController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.derechohabiente.UnidadMedicoFamiliar;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.medio.contacto.TipoMedioContacto;
import mx.gob.imss.digital.modelo.persona.Familiar;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Parentesco;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TramiteCuestionarioDummy;
import mx.gob.imss.ws.vo.Modalidad33VO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ws.client.core.WebServiceTemplate;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Compra;

@Controller
@SessionAttributes(value = {"parentescos", "aplicaCuestionario", "tramiteSeguro", "idPersonaSolicitante",
    "solicitante", "domicilioSeguro", "domicilioOtraUbicacion", "tramites", "datosCalculo",
    "solicitud", "umf", "enRenovacion", "segurosFamiliares", "extemporanea", "umfSeguroAsociado", "desdeExtranjero", "soloSolicitante", "desdeExtranjeroDom"})
@RequestMapping(value = "/wizard/seguroFamiliar")
public class WizardSeguroModalidad33Controller extends WebServiceCallerController {

    private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
    private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
    private static final String KEY_EN_RENOVACION_SSF = "enRenovacionSSF";
    private static final String DESC_ALTA_TRAMITE_SOLICITUD = "ALTA DE SEGURO IVRO";
    private static final String KEY_NSS_CIFRADO_FINAL = "_nssCifradoFinal";
    private static final String KEY_NSS_SOLIC_SEGURO_FAMILIAR = "nssSolic";
    private static final String KEY_NSS_CIFRADO_SOLIC_SEGURO_FAMILIAR = "nssCifradoSolic";
    private static final String KEY_CORREO_SSF = "correoseg33";
    private static final String MESSAGE_LOG_ERROR = "Error ----->";
    private static final String WSF_COMUN_RESUMEN = "wizardSeguroFamiliarComunResumen";
    private static final String WSF_ALTA_LISTA_INTEGRANTES = "wizardSeguroFamiliarAltaListaIntegrantes";
    private static final String WSF_ALTA_CUESTIONARIO = "wizardSeguroFamiliarAltaCuestionario";
    private static final String COMUN_ERROR = "error";
    private static final String CADENA_VACIA = "";
    private static final String ERROR_COTIZACION = "Ocurri\u00F3 un error al intentar realizar la cotizaci\u00F3n. Intenta nuevamente.";
    private static final String KEY_TRAMITES = "tramites";
    private static final String KEY_UMFSEGUROASOCIADO = "umfSeguroAsociado";
    private static final String KEY_APLICACUESTIONARIO = "aplicaCuestionario";
    private static final String KEY_DATOS_CALCULO = "datosCalculo";
    private static final String KEY_DEXTRANJERO = "desdeExtranjero";
    private static final String KEY_DOMICILIO_OUBICACION = "domicilioOtraUbicacion";
    private static final String KEY_EN_RENOVACION = "enRenovacion";
    private static final String KEY_EXTEMPORANEA = "extemporanea";
    private static final String KEY_ID_PERSOL = "idPersonaSolicitante";
    private static final String KEY_MSGERROR = "msgError";
    private static final String KEY_PARENTESCOS = "parentescos";
    private static final String KEY_SEG_FAM = "segurosFamiliares";
    private static final String KEY_SOLICITANTE = "solicitante";
    private static final String KEY_SOLICITUD = "solicitud";
    private static final String KEY_SSOLICITANTE = "soloSolicitante";
    private static final String KEY_TRAM_SEGURO = "tramiteSeguro";

    @Autowired
    @Qualifier("webServiceConsultaSeguroFamiliar")
    private WebServiceTemplate webServiceConsultaSeguroFamiliar;

    @Autowired
    @Qualifier("webServiceLineasCaptura")
    private WebServiceTemplate webServiceLineasCaptura;

    @Autowired
    @Qualifier("webServiceCotizaSeguroFamiliar")
    private WebServiceTemplate webServiceCotizaSeguroFamiliar;

    @Autowired
    @Qualifier("webServiceSolicitudSeguroIvro")
    private WebServiceTemplate webServiceSolicitudSeguroIvro;

    @Autowired
    @Qualifier("webServiceValidaPersonaSeguroFamiliar")
    private WebServiceTemplate webServiceValidaPersonaSeguroFamiliar;

    @Autowired
    @Qualifier("webServiceValidaPersonaSeguroFamiliarRenova")
    private WebServiceTemplate webServiceValidaPersonaSeguroFamiliarRenova;

    @Autowired
    @Qualifier("webServiceValidaCompraSeguroFamiliar")
    private WebServiceTemplate webServiceValidaCompraSeguroFamiliar;

    @Autowired
    private ServiceBusinessRemote serviceBusinessRemote;

	@Autowired
	@Qualifier("compraServiceBusiness")
	CompraServiceRemote compraServiceRemote;

    @Autowired
    private SeguroIndividualServices seguroIndividualServices;

    @Autowired
    @Qualifier("domicilioExternosServiceBusiness")
    private DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness;

    @Autowired
    @Qualifier("modalidad33VO")
    private Modalidad33VO modalidad33VO;

    @Autowired
    @Qualifier("solicitudServiciosExpuestos")
    private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestos;

    @Autowired
    @Qualifier("domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;

    @Autowired
    private SeguroCvroUtil seguroCvroUtil;

    @ModelAttribute("extemporanea")
    public boolean getExtemporanea() {
        return false;
    }

    @ModelAttribute("desdeExtranjero")
    public boolean getDesdeExtranjero() {
        return false;
    }

    @ModelAttribute("desdeExtranjeroDom")
    public boolean getDesdeExtranjeroDom() {
        return false;
    }

    @ModelAttribute("parentescos")
    public ParentescoIssfEnum[] getParentescos() {
        return ParentescoIssfEnum.values();
    }

    @ModelAttribute("aplicaCuestionario")
    public boolean getAplicaCuestionario() {
        return false;
    }

    @ModelAttribute("tramiteSeguro")
    public TramiteSeguroIvroMod33 getTramiteSeguro() {
        return new TramiteSeguroIvroMod33();
    }

    @ModelAttribute("idPersonaSolicitante")
    public Long getIdPersonaSolicitante() {
        return new Long(0);
    }

    @ModelAttribute("solicitante")
    public Fisica getSolicitante() {
        return new Fisica();
    }

    @ModelAttribute("domicilioSeguro")
    public Domicilio getDomicilioSeguro() {
        return new Domicilio();
    }

    @ModelAttribute("domicilioOtraUbicacion")
    public Domicilio getDomicilioOtraUbicacion() {
        return new Domicilio();
    }

    @ModelAttribute("tramites")
    public List<TramiteSeguroIvroMod33> getTramites() {
        return new ArrayList<TramiteSeguroIvroMod33>();
    }

    @ModelAttribute("datosCalculo")
    public DatosCalculoCuota getDatosCalculo() {
        return new DatosCalculoCuota();
    }

    @ModelAttribute("solicitud")
    public Solicitud getSolicitud() {
        return new Solicitud();
    }

    @ModelAttribute("umf")
    public UnidadMedicoFamiliar getUmf() {
        return new UnidadMedicoFamiliar();
    }

    @ModelAttribute("enRenovacion")
    public boolean getEnRenovacion() {
        return false;
    }

    @ModelAttribute("umfSeguroAsociado")
    public String getUmfSeguroAsociado() {
        return new String();
    }

    @ModelAttribute("soloSolicitante")
    public boolean getSoloSolicitante() {
        return false;
    }

    @ModelAttribute("segurosFamiliares")
    public SegurosIvro getSegurosFamiliares() {
        return new SegurosIvro();
    }

    @RequestMapping(value = "/validarAccesoTramite/{idPersona}/{correo}/", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, ? extends Object> validarAccesotramite(Model model,
            HttpServletRequest request, HttpSession session,
            @PathVariable Long idPersona, @PathVariable String correo) {
        this.log.info("Metodo validarAccesotramite idPersona:[" + idPersona + "] correo:[" + correo + "]");
        if (correo.contains("\u0040")) {
            session.setAttribute(KEY_CORREO_SSF, correo);
        }
        this.comunLimpiarDatos(model, session);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("idPersona", idPersona);
        result.put(COMUN_ERROR, false);
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        try {
            /*
             * Se consume primero el WS que consulta los seguros modalidad 33 de
             * la persona que solicita, para poder mostrar el detalle del seguro
             * o, en su defecto, continuar con las validaciones de acceso
             */
            SegurosIvro seguros = callWebService(webServiceConsultaSeguroFamiliar, persona, SegurosIvro.class);

            if(seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0){
                log.info("Existe un seguro");
                log.info("Estatus del ultimo seguro de la persona: [" + idPersona + "] ultimoSeguro:["
                        + ((seguros.getSeguroIvro()[(seguros.getSeguroIvro().length) - 1]).getEstadoSeguro()).getIdEstadoSeguro() + "]");
                Long seguroUltimoEstado = ((seguros.getSeguroIvro()[(seguros.getSeguroIvro().length) - 1]).getEstadoSeguro()).getIdEstadoSeguro();
                log.info(" La persona  cuenta con el status de Renovaci\u00F3n: [" + idPersona + "] statusRenovacion:[" + seguros.getSeguroIvro()[0].getEnRenovacion() + "]");
                log.info("seguroUltimoEstado: "+seguroUltimoEstado);

				//************* Validar LC antes de detalle *********

                boolean lineasGeneradas = true;
                log.info("############################Iniciando validacion lineasGeneradas");
                for(SeguroIvro seguro: seguros.getSeguroIvro()) {

                	Long idCompra = seguro.getCompra().getIdCompra();
                	log.info("############################Se obtiene seguro: " + idCompra);
                	try {
                    	log.info("############################ compraServiceRemote: " + compraServiceRemote);
                		Compra compra = compraServiceRemote.findCompraById(idCompra);
                		log.info("############################Se obtiene compra: " + compra);
                		Pago[] pagos = compra.getPagos();

                		for(Pago pago: pagos) {
                			log.info("############################Se obtiene pago: " + pago.getLineaCaptura());
                			if(pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
                				lineasGeneradas = false;
                				break;
                			}
                		}

                		if (!lineasGeneradas) {
                			log.info("############################Se encontraron LC en nulo");
                			result.put("lineasGeneradas", false);
                			result.put("msgError", "El detalle de sus seguros se est\u00e1 procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros");
                			return result;
                		}

                	} catch (SUAException e) {
                		log.error("############################Ocurrio un error al validar las LC: " + e);
                		e.printStackTrace();
                        result.put(COMUN_ERROR, true);
                        result.put(KEY_MSGERROR, e.getMessage());
                        return result;
                	}
                }

            }else{
                log.info("No encontro un seguro anterior");
            }

            if (seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0 && (!((seguros.getSeguroIvro()[(seguros.getSeguroIvro().length) - 1]).getEstadoSeguro()).getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.VENCIDO.getId()))) {
                log.debug("Estatus del ultimo seguro de la persona: [" + idPersona + "] ultimoSeguro:["
                        + ((seguros.getSeguroIvro()[(seguros.getSeguroIvro().length) - 1]).getEstadoSeguro()).getIdEstadoSeguro() + "]");
                Long seguroUltimoEstado = ((seguros.getSeguroIvro()[(seguros.getSeguroIvro().length) - 1]).getEstadoSeguro()).getIdEstadoSeguro();
                session.setAttribute(KEY_EN_RENOVACION_SSF, seguros.getSeguroIvro()[0].getEnRenovacion() && !seguros.getSeguroIvro()[0].getExtemporanea());

                log.debug(" La persona  cuenta con el status de Renovaci\u00F3n: [" + idPersona + "] statusRenovacion:[" + seguros.getSeguroIvro()[0].getEnRenovacion() + "]");
                if (seguros.getSeguroIvro()[0].getEnRenovacion()) {

                    model.addAttribute(KEY_APLICACUESTIONARIO, false);
                    log.debug("La persona cuenta con el status de Extemporanea:[" + idPersona + "] statusExtemporanea:[" + seguros.getSeguroIvro()[0].getExtemporanea() + "]");

                    log.debug("ultimoEstado del seguro de la persona:[" + idPersona + "] estado:[" + seguroUltimoEstado + "] ");

                    if (seguros.getSeguroIvro()[0].getExtemporanea()) {
                        model.addAttribute(KEY_EXTEMPORANEA, Boolean.TRUE);
                    } else {
                        model.addAttribute(KEY_EXTEMPORANEA, Boolean.FALSE);
                    }
                    if (seguroUltimoEstado == 1) {
                        model.addAttribute(KEY_EN_RENOVACION, Boolean.FALSE);
                        result.put("renovacion", Boolean.TRUE);
                    } else {
                        model.addAttribute(KEY_EN_RENOVACION, Boolean.TRUE);
                        result.put("renovacion", Boolean.TRUE);
                    }
                    /**
                     * Obtencion de la umf
                     */
                    Fisica solicitante = seguros.getSeguroIvro()[0].getTramite().getPersona();
                    if (solicitante != null && solicitante.getUmfAsociado() != null) {
                        model.addAttribute(KEY_UMFSEGUROASOCIADO, solicitante.getUmfAsociado().getIdUMF());
                        this.log.info("Unidad Medico Familiar asociada de la Persona: [" + idPersona + "] ID:[ " + solicitante.getUmfAsociado().getIdUMF() + "]");
                    }
                    /**
                     * Obtencion de la bandera DesdeExtranjero
                     */
                    model.addAttribute(KEY_DEXTRANJERO, seguros.getSeguroIvro()[0].getTramite().isDesdeExtranjero());
                    this.log.info("Persona que desea hacer su tr\u00E1mite desde el Extranjero : [" + idPersona + "] tramitExtranjero:[" + seguros.getSeguroIvro()[0].getTramite().isDesdeExtranjero() + "]");
                    /*
                     *Obtencion de la bandera de "solo solicitante"
                     */
                    if (seguros.getSeguroIvro()[0] != null
                            && seguros.getSeguroIvro()[0].getTramite() != null) {

                        this.log.debug("Unicamente persona que solicita tramite de seguro: [" + idPersona + "] Solicitante:[" + seguros.getSeguroIvro()[0].getTramite().isSoloSolicitante() + "]");
                        model.addAttribute(KEY_SSOLICITANTE, seguros.getSeguroIvro()[0].getTramite().isSoloSolicitante());
                    }

                    /*
                     * Se manda el origen de la solicitud, se utiliza el atributo
                     * errorFormGeneral ya que el proxy en el OSB ahi lo espera
                     */
                    persona.setErrorFormGeneral(SeguroIvroUtil.getAmbiente(request).toString());
                    DatosCalculoCuota dcc = callWebService(
                            webServiceValidaPersonaSeguroFamiliarRenova, persona,
                            DatosCalculoCuota.class);

                    this.log.debug("Resultado de validar a la persona para dar acceso al tramite  DatosCalculoCuota:[" + idPersona + "] [" + dcc + "]");
                    log.info("dcc.aplicaCuestionario: "+dcc.getAplicaCuestionario());
                    if (StringUtils.isNotBlank(dcc.getErrorFormGeneral())) {
                        throw new IVROServiceException(dcc.getErrorFormGeneral());
                    } else {
                        model.addAttribute(KEY_APLICACUESTIONARIO, dcc.getAplicaCuestionario());
                    }

                } else {
                    /*
                     * Se tiene(n) seguro(s), por lo tanto, se responde en el JSON
                     * la bandera que indica que se tiene seguro
                     */
                    this.log.debug("La persona [id=" + idPersona
                            + "] ya cuenta con seguro modalidad 33 asociado, se procede a mostrar el detalle");
                    result.put("tieneSeguro", Boolean.TRUE);
                }
            } else {
                /*
                 * No se tiene seguro se procede a realizar las validaciones
                 * propias del acceso al tramite
                 */
                seguroIndividualServices.getValidarCorreoPersona(persona);
                session.setAttribute(KEY_EN_RENOVACION_SSF, Boolean.FALSE);
                /*
                 * Se manda el origen de la solicitud, se utiliza el atributo
                 * errorFormGeneral ya que el proxy en el OSB ahi lo espera
                 */
                persona.setErrorFormGeneral(SeguroIvroUtil.getAmbiente(request).toString());
                DatosCalculoCuota dcc = callWebService(
                        webServiceValidaPersonaSeguroFamiliar, persona,
                        DatosCalculoCuota.class);

                this.log.debug("Resultado de validar a la persona para dar acceso al tramite  DatosCalculoCuota: [" + idPersona + "] tramite:[" + dcc + "]");
                log.info("dcc.aplicaCuestionario: "+dcc.getAplicaCuestionario());
                if (StringUtils.isNotBlank(dcc.getErrorFormGeneral())) {
                    throw new IVROServiceException(dcc.getErrorFormGeneral());
                } else {
                    model.addAttribute(KEY_APLICACUESTIONARIO, dcc.getAplicaCuestionario());
                }
            }
        } catch (IVROServiceException e) {
            result.put(COMUN_ERROR, true);
            result.put(KEY_MSGERROR, e.getMessage());
            return result;
        } catch (Exception e) {
            String msgError = "Error inesperado al validar el acceso al tr\u00e1mite";
            this.log.error(msgError, e);
            result.put(COMUN_ERROR, true);
            result.put(KEY_MSGERROR, msgError);

            return result;
        }

        return result;
    }

    @RequestMapping(value = "/alta/init/{idPersona}/{nssCifrado}",
            method = RequestMethod.GET)
    public String altaInit(Model model, SessionStatus sessionStatus,
            HttpSession session,
            @ModelAttribute(KEY_APLICACUESTIONARIO) boolean aplicaCuestionario,
            @PathVariable Long idPersona,
            @PathVariable String nssCifrado) {

        String nss = null;
        String uno = "-1";
        String numberSS = CADENA_VACIA;
        if (!nssCifrado.equals(uno)) {
            Map<String, Object> result = descifrarNss(session, null, nssCifrado);
            nss = (String) result.get(KEY_NSS_SOLIC_SEGURO_FAMILIAR);
            numberSS = (String) result.get(KEY_NSS_CIFRADO_SOLIC_SEGURO_FAMILIAR);
        } else {
            numberSS = null;
        }

        log.info("La persona que esta solicitando el IVRO mod 33 es "
                + idPersona + " con NSS " + nss);

        TipoPersona tipoPersona = new TipoPersona();
        tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);

        Familiar persona = new Familiar();
        persona.setIdPersona(idPersona);
        persona.setTipoPersona(tipoPersona);
        persona.setNssCifrado(numberSS);
        persona.setNss(nss);
        persona.setCurp(seguroIndividualServices
                .obtenerCurpPorIdPersona(idPersona));
        persona.setAplicaCuestionario(aplicaCuestionario);
        log.info("aplicaCuestionario 1 : "+aplicaCuestionario);

        TramiteSeguroIvroMod33 tramiteSeguro = new TramiteSeguroIvroMod33();
        tramiteSeguro.setSolicitante(persona);
        tramiteSeguro.setPersona(persona);
        tramiteSeguro.setBeneficiarios(new Fisica[]{persona});
        List<TramiteSeguroIvroMod33> tramites = new ArrayList<TramiteSeguroIvroMod33>();
        tramites.add(tramiteSeguro);
        List<Familiar> familiares = new ArrayList<Familiar>();

        model.addAttribute(KEY_TRAM_SEGURO, tramiteSeguro);
        model.addAttribute(KEY_TRAMITES, tramites);
        model.addAttribute(KEY_ID_PERSOL, idPersona);
        model.addAttribute(KEY_SOLICITANTE, persona);
        model.addAttribute(KEY_PARENTESCOS, ParentescoIssfEnum.values());

        return "wizardSeguroFamiliarAltaInit";
    }

    @RequestMapping(value = "/lista/{idPersona}",
            method = RequestMethod.GET)
    public String listarSeguros(Model model, SessionStatus sessionStatus,
            HttpSession session, @PathVariable Long idPersona) {
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);

        try {

            //Se realizan 3 intentos

            int contador = 0;
            boolean continua = false;
            SegurosIvro segurosFamiliares = null;
            do {

                segurosFamiliares = callWebService(
                    webServiceConsultaSeguroFamiliar, persona,
                    SegurosIvro.class);
                log.info("Seguros Familiares: "+segurosFamiliares + " contador: "+ contador);

                if(segurosFamiliares!=null){
                    log.info("seguros Familiares not null");
                    if(segurosFamiliares.getSeguroIvro()!=null){
                        log.info("segurosIvro not null");
                        if(segurosFamiliares.getSeguroIvro().length>=1){
                            log.info("Lista con al menos un elemento: "+segurosFamiliares.getSeguroIvro().length);
                            continua = true;
                        }else{
                            log.info("Lista vacia");
                        }
                    }else{
                        log.info("segurosIvro null");
                    }
                }else{
                    log.info("seguros Familiares null");
                }

//                try
//                {
//                    if(continua==false){
//                        Thread.sleep(   1000);
//                    }
//                }
//                catch(InterruptedException ex)
//                {
//                    Thread.currentThread().interrupt();
//                }
                contador++;
            }while ( contador<=3 && continua == false );
            
            //Se actualizan los seguros para mostrar el detalle, ahora con idSeguro cifrado
            for(SeguroIvro seguro:segurosFamiliares.getSeguroIvro()) {
            	//seguro.setCveIdSeguroIvroCifrado(CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())) );
            }
            log.debug("La persona [id="
                    + idPersona
                    + "] cuenta con "
                    + segurosFamiliares.getSeguroIvro().length
                    + " seguro(s) modalidad 33 asociado(s), se procede a mostrar la lista");

            model.addAttribute(KEY_SEG_FAM, segurosFamiliares);

        } catch (Exception e) {
            model.addAttribute(COMUN_ERROR, "Ocurri\u00F3 un error inesperado al obtener la lista de seguros.");
            log.error(
                    "Ocurrio un error al intentar obtener la lista de seguros del solicitante [idPersona="
                    + idPersona + "]", e);
        }

        return "wizardSeguroFamiliarListaSeguros";
    }

    @RequestMapping(value = "/comunes/solicitarDomicilio")
    public String comunSolicitarDomicilio(Model model, HttpSession session,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("idPersonaSolicitante") Long idPersona,
            @ModelAttribute("enRenovacion") boolean enRenovacion,
            @ModelAttribute("segurosFamiliares") SegurosIvro segurosFamiliares) {

        Persona persona = new Persona();
        persona.setIdPersona(idPersona);

        solicitante.setIdPersona(idPersona);

        try {
            Domicilio domicilio;
            domicilio = domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(idPersona);
            solicitante.setDomicilioParticular(domicilio);
        } catch (DomicilioNoLocalizadoException e) {
            model.addAttribute(COMUN_ERROR, "Ocurri\u00F3 un error al intentar obtener el domicilio del solicitante.");
            log.error("********** Ocurrio un error al intentar obtener el domicilio del solicitante..", e);
        } catch (MunicipioImssNoLocalizadoException e) {
            model.addAttribute(COMUN_ERROR, "Ocurri\u00F3 un error al intentar obtener el domicilio del solicitante.");
            log.error("********** Ocurrio un error al intentar obtener el domicilio del solicitante..", e);
        }

        model.addAttribute("umf", new UnidadMedicoFamiliar());

        return "wizardSeguroFamiliarComunSolicitarDomicilio";
    }

    @RequestMapping(value = "/comunes/otraUbicacion", method = RequestMethod.POST)
    public String comunOtraUbicacion(Model model, @RequestParam("desdeExtranjeroDom") boolean desdeExtranjeroDom,
            @ModelAttribute("idPersonaSolicitante") Long idPersona) {
        model.addAttribute("domicilioAlterno", new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio());
        log.info("desdeExtranjero agregarDom de la Persona:[" + idPersona + "] domicilio:[" + desdeExtranjeroDom + "]");
        model.addAttribute(KEY_DEXTRANJERO, desdeExtranjeroDom);
        return "wizardSeguroFamiliarComunAgregarDomicilio";
    }

    @RequestMapping(value = "/comunes/agregarDomicilio", method = RequestMethod.POST)
    public String comunAgregarDomicilio(Model model,
            @ModelAttribute("idPersonaSolicitante") Long idPersona,
            @ModelAttribute mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio) {

        domicilio.setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal());
        domicilio.setLocalidad(domicilio.getAsentamiento().getLocalidad());
        domicilio.getVialidadPrimaria().setNombre(domicilio.getCalle());

        Domicilio domicilioOtraUbicacion = SeguroIvroUtil.convertirDomicilioAImssDigital(domicilio);

        domicilioOtraUbicacion.getAsentamiento().setMunicipio(
                domicilioOtraUbicacion.getAsentamiento().getLocalidad()
                        .getMunicipio());
        domicilioOtraUbicacion.setColonia(domicilioOtraUbicacion.getAsentamiento()
                .getNombre());

        log.info("********* VALOR DEL DOMICILIO OTRA UBICACION DE LA PERSONA:[" + idPersona + "]otraUbicacion[" + domicilioOtraUbicacion.toString() + "]");

        model.addAttribute(KEY_DOMICILIO_OUBICACION, domicilioOtraUbicacion);
        model.addAttribute("umf", new UnidadMedicoFamiliar());
        return "wizardSeguroFamiliarComunSolicitarDomicilio";
    }

    @RequestMapping(value = "/comunes/seleccionarDomicilio")
    public String comunSeleccionarDomicilio(Model model, HttpServletRequest request,
            HttpSession session,
            @ModelAttribute("aplicaCuestionario") boolean aplicaCuestionario,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod33 tramiteSeguro,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites,
            @ModelAttribute("domicilioSeguro") Domicilio domicilioSeguro,
            @ModelAttribute("domicilioOtraUbicacion") Domicilio domicilioOtraUbicacion,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("enRenovacion") boolean enRenovacion,
            @ModelAttribute("extemporanea") boolean extemporanea,
            @ModelAttribute("segurosFamiliares") SegurosIvro segurosFamiliares) {

        String view;

        /*
         * Se checa si el domicilio elegido tiene id, si es asi siginifica que
         * es el domicilio particular del solicitante (viene de base de datos),
         * de lo contrario, es el que se capturo a traves del componente de
         * "domicilio recortado". Dependiendo del resultado es el
         * domicilioSeguro que se asigna
         */
        Domicilio domicilio;

        if (tramiteSeguro.getDomicilioSeguro().getIdDomicilio() != null) {
            domicilio = solicitante.getDomicilioParticular();
            log.info("**** Continua con domicilio existente ssf:" + tramiteSeguro.getDomicilioSeguro().getIdDomicilio());
            log.info("**** domicilio particular:" + (solicitante.getDomicilioParticular() != null ? solicitante.getDomicilioParticular().getIdDomicilio() : 0));
        } else {
            domicilio = domicilioOtraUbicacion;
            seguroCvroUtil.eliminarCaracteresNoPermitidosDomicilio(domicilio);
            log.info("**** Continua con domicilio nuevo ssf:" + domicilioSeguro.getCalle() + ":****");
        }

        tramiteSeguro.setDomicilioSeguro(domicilio);

        log.info("enRenovacion: "+enRenovacion);

        if(enRenovacion){
            view = WSF_ALTA_LISTA_INTEGRANTES;
        }else{
            log.info("aplicaCuestionario 2: "+aplicaCuestionario);
            if (aplicaCuestionario) {
                view = WSF_ALTA_CUESTIONARIO;
            } else {
                view = WSF_ALTA_LISTA_INTEGRANTES;
            }
        }

        try {

            /*
             * Se cotiza en este punto al solicitante, ya que ya se cuenta con el
             * domicilio
             */
            DatosCalculoCuota datosCalculo = obtenerDatosCalculoCuota(domicilio, solicitante, request);
            datosCalculo.setAplicaCuestionario(aplicaCuestionario);
            model.addAttribute(KEY_DATOS_CALCULO, datosCalculo);

            List<Fisica> integrantes = new ArrayList<Fisica>();
            integrantes.add(solicitante);

            Cotizacion cotizacionSolicitante = this.generarCotizacion(
                    solicitante, integrantes, datosCalculo, enRenovacion);

            solicitante.setNombre(cotizacionSolicitante.getDetalle()
                    .getEmpleados()[0].getNombreTrabajador());
            solicitante.setCurp(cotizacionSolicitante.getDetalle()
                    .getEmpleados()[0].getCurp());
            cotizacionSolicitante.getDetalle()
                    .getEmpleados()[0].setAplicaCuestionario(((Familiar)solicitante).getAplicaCuestionario());

            Persona personaMedios = new Persona();
            personaMedios.setIdPersona(solicitante.getIdPersona());
            personaMedios.setTipoPersona(solicitante.getTipoPersona());

            personaMedios = seguroIndividualServices.getMediosContactoPersona(personaMedios);

            modificaMediosContactoCorreo(personaMedios, (String) session.getAttribute(KEY_CORREO_SSF));

            solicitante.setMediosContacto(personaMedios.getMediosContacto());

            tramiteSeguro.setCotizacion(cotizacionSolicitante);
            tramiteSeguro.setBeneficiarios(new Fisica[]{solicitante});

            tramites.set(0, tramiteSeguro);
            StringBuilder lstCURPS = new StringBuilder();
            boolean erroBeneficiaro = false;
            if (enRenovacion) {
                for (int cont = 0; cont < segurosFamiliares.getSeguroIvro().length; cont++) {
                    TramiteSeguroIvro tramiteFamiliar = segurosFamiliares.getSeguroIvro()[cont].getTramite();
                    if (!tramiteFamiliar.getBeneficiarios()[0].getNss().equals(solicitante.getNss())) {
                        TramiteSeguroIvroMod33 tramite = this.obtenerCotizacionBeneficiarios(
                                tramiteFamiliar.getBeneficiarios()[0],
                                solicitante, datosCalculo, enRenovacion);
                        if (tramite != null) {
                            tramites.add(tramite);
                        } else {
                            erroBeneficiaro = true;
                            lstCURPS.append(tramiteFamiliar.getBeneficiarios()[0].getNss()).append(" ");
                            this.log.error("Error no se han podido cargar los beneficiarios");
                        }
                    }
                }
                if (erroBeneficiaro) {
                    model.addAttribute(COMUN_ERROR, "No es posible agregar al (los) integrante(s) con NSS " + lstCURPS.toString() + "para su incorporaci\u00F3n al Seguro de Salud para la Familia, debido a que se encuentra(n) vigente(s), pensionado(s), jubilado(s) y/o como trabajador(es) IMSS, acude a la Subdelegaci\u00F3n que te corresponde para mayor informaci\u00F3n.");
                }
            }

            model.addAttribute(KEY_TRAMITES, tramites);

            this.log.debug("Detalle del solicitante -> \n"
                    + ToStringBuilder.reflectionToString(tramites.get(0)
                            .getBeneficiarios()[0], ToStringStyle.MULTI_LINE_STYLE));

            this.log.debug("Detalle de la cotizacion en tramite asociado al solicitante [nss="
                    + solicitante.getNss()
                    + "] -> \n"
                    + ToStringBuilder.reflectionToString(tramites.get(0)
                            .getCotizacion(), ToStringStyle.MULTI_LINE_STYLE));

        } catch (IVROServiceException e) {
            log.info("cayo en error, aplica cuestionario");
            view = WSF_ALTA_CUESTIONARIO;
            model.addAttribute(COMUN_ERROR, e.getMessage());
        }

        return view;
    }

    private void modificaMediosContactoCorreo(Persona personaMedios, String correo) {
        this.log.debug("Buscando correo entre contactos: " + correo);
        MedioContacto ultimoContacto = null;
        //Lista con todos los medios de contacto menos los que tienen correos, excepto el del qie se loguea
        List<MedioContacto> contactosFiltrados = new ArrayList<MedioContacto>();
        boolean contactoEncontrado = false;

        if (personaMedios != null && personaMedios.getMediosContacto() != null) {

            for (MedioContacto contacto : personaMedios.getMediosContacto()) {
                if (contacto != null && contacto.getTipoMedioContacto() != null
                        && TipoMedioContacto.TIPO_CORREO_ELECTRONICO.equals(contacto.getTipoMedioContacto().getIdTipoMedioContacto())) {
                    ultimoContacto = contacto;
                    if (contacto.getDesFormaContacto() != null && contacto.getDesFormaContacto().equals(correo)) {
                        this.log.debug("Correo encontrado: " + contacto.getDesFormaContacto());
                        contactosFiltrados.add(contacto);
                        contactoEncontrado = true;
                    }
                } else {
                    contactosFiltrados.add(contacto);
                }
            }
        } else {
            this.log.debug("Sin medios de contacto");
        }
        //Si no se encuentra el contacto agrega el ultimo contacto de la lista
        if (!contactoEncontrado) {
            contactosFiltrados.add(ultimoContacto);
            this.log.debug("Agrega ultimo contacto debido a que no se encontro el correo");
        }

        MedioContacto[] arregloContactos = new MedioContacto[contactosFiltrados.size()];
        contactosFiltrados.toArray(arregloContactos);

        personaMedios.setMediosContacto(arregloContactos);
    }

    @RequestMapping(value = "/comunes/agregarUmf", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> comunAgregarUmf(Model model,
            HttpServletRequest request, HttpSession session,
            @RequestBody UnidadMedicoFamiliar umf,
            @ModelAttribute("solicitante") Fisica solicitante) {

        solicitante.setUmfAsociado(umf);

        model.addAttribute("umf", umf);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(COMUN_ERROR, false);
        result.put("msg", "UMF agregada correctamente");

        return result;
    }

    @RequestMapping(value = "/alta/validarCuestionario")
    public String validarCuestionario(Model model, HttpServletRequest request,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod33 tramite,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("respuestasCuestionario") TramiteCuestionarioDummy tramiteCuestionario,
            @ModelAttribute("enRenovacion") boolean enRenovacion,
            @ModelAttribute("extemporanea") boolean extemporanea,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites) {

        String view;

        if (tramiteCuestionario.getRespuestas().getErrorFormGeneral() != null
                && !tramiteCuestionario.getRespuestas().getErrorFormGeneral()
                        .trim().isEmpty()) {
            model.addAttribute(COMUN_ERROR, tramiteCuestionario.getRespuestas()
                    .getErrorFormGeneral());

            view = WSF_ALTA_CUESTIONARIO;
        } else if (tramiteCuestionario.getRespuestas().getSumatoriaRespuestas() == 0) {
            PersonaCuestionario personaCuestionario = UtilConvert.parseToPersonaCuestionario(tramiteCuestionario);
            int indexTmp = tramite.getBeneficiarios().length - 1;
            personaCuestionario.setNssPersona(tramite.getBeneficiarios()[indexTmp].getNss());
            personaCuestionario.setCuestionarioValido(true);
            personaCuestionario.setIdPersona(tramite.getBeneficiarios()[indexTmp].getIdPersona());

            tramite.setCuetionarios(new PersonaCuestionario[]{personaCuestionario});

            view = WSF_ALTA_LISTA_INTEGRANTES;
//    } else if(!enRenovacion || extemporanea){
        } else if (!enRenovacion) {
            /*
       * Dado que se contesta en el cuestionario que se tienen
       * enfermedades preexistentes, se genera solicitud de rechazo
       * para no permitir la ejecucion del tramite desde P. CIUDADANO
             */
            log.error("Se agregara solicitud rechazo para NSS " + solicitante.getNss());

            Long idParentesco = (Long) request.getSession().getAttribute("idParentesco");
            String curpBeneficiario = (String) request.getSession().getAttribute("curpFamiliar");
            String nssBeneficiario = (String) request.getSession().getAttribute("nssFamiliar");
            log.info("++++ Parentesco: "+idParentesco+ "");
            log.info("++++ curpBeneficiario: "+curpBeneficiario+ "");
            log.info("++++ nssBeneficiario: "+nssBeneficiario+ "");

            Long idAsignacion = null;
            try {
                idAsignacion = seguroIndividualServices.recuperaIdAsignacionPorNSS(nssBeneficiario);
            } catch (IVROServiceException e) {
                log.error("Error: ",e);
            }

            log.info("++++ idAsignacion: "+idAsignacion+ "");

            Beneficiario beneficiarioCancelar = new Beneficiario();
            beneficiarioCancelar.setCurp(curpBeneficiario);
            beneficiarioCancelar.setCveIdAsignacionNSS(idAsignacion);
            beneficiarioCancelar.setTipoBeneficiario(idParentesco.intValue());
            beneficiarioCancelar.setNss(nssBeneficiario);


            this.generaSolicitudRechazoCuestionario(request, solicitante, tramiteCuestionario,
                    tramite,beneficiarioCancelar);

            altaQuitarUltimoIntegrante(tramites);

            model.addAttribute(
                    COMUN_ERROR,
                    "La solicitud de incorporaci\u00F3n al Seguro de Salud para la Familia no es procedente cuando padeces alguna enfermedad preexistente. "
                    + "Debes acudir a tu Subdelegaci\u00F3n para realizar, en su caso, la incorporaci\u00F3n de los integrantes que no presenten enfermedades "
                    + "preexistentes.");

            view = WSF_ALTA_LISTA_INTEGRANTES;
        } else {
            altaQuitarUltimoIntegrante(tramites);
            model.addAttribute(
                    COMUN_ERROR,
                    "La solicitud de incorporaci\u00F3n al Seguro de Salud para la Familia no es procedente cuando padeces alguna enfermedad preexistente. "
                    + "Debes acudir a tu Subdelegaci\u00F3n para realizar, en su caso, la incorporaci\u00F3n de los integrantes que no presenten enfermedades "
                    + "preexistentes.");

            view = WSF_ALTA_LISTA_INTEGRANTES;
        }
        return view;
    }

    @RequestMapping(value = "/alta/agregarCotizarIntegrante",
            method = RequestMethod.POST)
    public String agregarCotizarIntegrante(Model model, HttpServletRequest request,
            @ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("enRenovacion") boolean enRenovacion,
            @ModelAttribute("extemporanea") boolean extemporanea) {

        // Una vez  que solicito agregar un beneficiario el check se debe
        // desmarcar
        model.addAttribute(KEY_SSOLICITANTE, false);
        String error = null;
        String view = WSF_ALTA_LISTA_INTEGRANTES;

        Familiar familiar = new Familiar();
        familiar.setNss(request.getParameter("nssFamiliar"));
        familiar.setCurp(request.getParameter("curpFamiliar"));

        log.info("---NSS: "+familiar.getNss());
        log.info("---CURP: "+familiar.getCurp());
        familiar.setParentesco(new Parentesco());
        Long idParentesco = Long.valueOf(request.getParameter("idParentescoFamiliar"));
        familiar.getParentesco().setIdParentesco(idParentesco);
        familiar.getParentesco().setDescripcion(getDescripcionParentesco(idParentesco.intValue()));
        familiar.setInscripcion(true);
        List<String> nssIntegrantes = new ArrayList<String>();
        Map<Long, Integer> parentescos = new HashMap<Long, Integer>();

        if (tramites.size() > 49) {
            error = "No puedes agregar m\u00E1s de 50 integrantes al Grupo Familiar (incluyendo el Titular)";
        }

        request.getSession().setAttribute("nssFamiliar",familiar.getNss());
        request.getSession().setAttribute("curpFamiliar",familiar.getCurp());
        request.getSession().setAttribute("idParentesco",idParentesco);

        Long idAsignacion = null;
        try {
            idAsignacion = seguroIndividualServices.recuperaIdAsignacionPorNSS(familiar.getNss());

            //se revisa si el beneficiario ya esta marcado
            boolean beneficiarioDescartado = seguroIndividualServices.verificaBeneficiarioConEnfermedad(idAsignacion);

            if(beneficiarioDescartado){
                error = "El NSS "+familiar.getNss()+" ya cuenta con una solicitud previa de incorporaci&oacute;n al Seguro de Salud para la Familia, rechazada por padecer alguna enfermedad preexistente, por lo que en caso de que desees realizar alguna aclaraci&oacute;n debes acudir a tu Subdelegaci&oacute;n.";
                model.addAttribute(COMUN_ERROR, error);
                return view;
            }

        } catch (IVROServiceException e) {
            log.error("Error: ",e);
        }


        int totalPapas = 0;

        for (TramiteSeguroIvroMod33 tramite : tramites) {
            /*
       * Siempre se toma la posicion 0, ya que para cada integrante se
       * genera un tramite y el arreglo de beneficiarios solo contiene a
       * dicho integrante
             */
            Fisica integranteSeguro = tramite.getBeneficiarios()[0];

            if (integranteSeguro.getNssCifrado() == null) {
                if (((Familiar) integranteSeguro).getParentesco().getIdParentesco() == 4 || ((Familiar) integranteSeguro).getParentesco().getIdParentesco() == 5) {
                    totalPapas = totalPapas + 1;
                }

                int count = 0;

                if (parentescos.containsKey(((Familiar) integranteSeguro).getParentesco().getIdParentesco())) {
                    count = parentescos.get(((Familiar) integranteSeguro).getParentesco().getIdParentesco());
                }

                parentescos.put(((Familiar) integranteSeguro).getParentesco().getIdParentesco(), count + 1);
            }

            nssIntegrantes.add(integranteSeguro.getNss());
        }

        if (nssIntegrantes.contains(familiar.getNss())) {
            error = "El integrante con el NSS <strong>" + familiar.getNss() + "</strong> ya se encuentra en tu N\u00FAcleo Familiar";
        }

        // Se valida el parentesco del integrante
        if (idParentesco.intValue() == 1 || idParentesco.intValue() == 2) {
            // Solo se puede tener un familiar de este tipo de parentesco
            if (parentescos.get(idParentesco) != null && parentescos.get(idParentesco).intValue() == 1) {
                error = "No puedes agregar m\u00E1s de un " + getDescripcionParentesco(idParentesco.intValue());
            }
        } else if (idParentesco.intValue() == 6) {
            if (parentescos.get(idParentesco) != null && parentescos.get(idParentesco).intValue() == 4) {
                error = "No puedes agregar m\u00E1s de cuatro " + getDescripcionParentesco(idParentesco.intValue());
            }
        } else if ((idParentesco.intValue() == 4 || idParentesco.intValue() == 5) && parentescos.containsKey(idParentesco)) {
            error = "No puedes agregar m\u00E1s de dos PADRES";
        }

        /*
     * Se valida los parentescos conyuge/concubino que son excluyentes
         */
        if (idParentesco.intValue() == 1
                && parentescos.get(2L) != null) {
            // Se quiere agregar conyuge, y se tiene concubino
            error = "No puedes agregar a un "
                    + getDescripcionParentesco(idParentesco.intValue())
                    + ", debido a que ya cuentas con un "
                    + getDescripcionParentesco(2);
        } else if (idParentesco.intValue() == 2
                && parentescos.get(1L) != null) {
            // Se quiere agregar concubino, y se tiene conyuge
            error = "No puedes agregar a un "
                    + getDescripcionParentesco(idParentesco.intValue())
                    + ", debido a que ya cuentas con un "
                    + getDescripcionParentesco(1);
        }

        if (StringUtils.isBlank(error)) {

            List<Fisica> integrantes = new ArrayList<Fisica>();
            integrantes.add(familiar);

            /*
       * Se manda a cotizar a integrante, este mismo servicio se encarga
       * de realizar las validaciones de la informacion de la persona, si
       * esta dada de alta en otras modalidades, etc. Se manda a validar
       * como compra con el ultimo parametro de generar la cotizacion en false
       * ya que los integrantes nuevos siempre son inscripcion sin embargo el
       * mensaje de excepcion que se envia se obtiene para renovaciones en el
       * catch
             */
            try {
                Cotizacion cotizacionIntegrante = this.generarCotizacion(
                        solicitante, integrantes, datosCalculo, false);

                log.info("Se realiza la cotizacion del integrante: "+cotizacionIntegrante);

                familiar.setNombre(cotizacionIntegrante.getDetalle()
                        .getEmpleados()[0].getNombreTrabajador());
                familiar.setCurp(cotizacionIntegrante.getDetalle()
                        .getEmpleados()[0].getCurp());
                familiar.setAplicaCuestionario(cotizacionIntegrante.getAplicaCuestionario());
                log.info("Se aplica cuestionario: "+cotizacionIntegrante.getAplicaCuestionario());
                /*
                * Siempre que se agregue un integrante en un tramite
                * sera considerado inscripcion inicial, por lo tanto se envian
                * parametros para generar los comprobantes.
                 */
                TramiteSeguroIvroMod33 tramite = new TramiteSeguroIvroMod33();
                tramite.setBeneficiarios(new Familiar[]{familiar});
                tramite.setCotizacion(cotizacionIntegrante);
                int idxTmp = tramites.size();
                tramites.add(tramite);
                model.addAttribute(KEY_TRAMITES, tramites);
                if (cotizacionIntegrante.getAplicaCuestionario()) {
                    this.log.debug("Enviando a la vista wizardSeguroFamiliarAltaCuestionario");
                    view = WSF_ALTA_CUESTIONARIO;
                }

                this.log.debug("Detalle del solicitante -> \n"
                        + ToStringBuilder.reflectionToString(tramites.get(idxTmp)
                                .getBeneficiarios()[0], ToStringStyle.MULTI_LINE_STYLE));

                this.log.debug("Detalle de la cotizacion en tramite asociado al familiar [nss="
                        + familiar.getNss()
                        + "] -> \n"
                        + ToStringBuilder.reflectionToString(tramites.get(idxTmp)
                                .getCotizacion(), ToStringStyle.MULTI_LINE_STYLE));
            } catch (IVROServiceException e) {
                error = e.getErrorMsg();
                /*
                * Debido a que la renovacion envia mensajes distintos a los que
                * se generan en la compra para las mismas validaciones y
                * que las modalidades compatibles para nuevos integrantes son
                * distintas se reenvia la peticion de la cotizacion para obtener
                * los mensajes adecuados. En caso de que la cotizacion no tenga
                * excepcion la cotizacion se calcula del mismo modo.
                 */
                if (enRenovacion) {
                    try {
                        this.generarCotizacion(solicitante, integrantes, datosCalculo, true);
                    } catch (IVROServiceException ex) {
                        error = ex.getErrorMsg();
                    }
                }
                this.log.error(error, e);
                model.addAttribute(COMUN_ERROR, error);
            }
        } else {
            model.addAttribute(COMUN_ERROR, error);
        }
        return view;
    }

    @RequestMapping(value = "/alta/quitarIntegrante")
    public String altaQuitarIntegrante(Model model, HttpServletRequest request,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites) {

        int index = Integer.parseInt(request.getParameter("inputIndex"));
        log.info("********** Integrante: " + tramites.get(index).getBeneficiarios()[0].getCurp());
        tramites.remove(index);
        model.addAttribute(KEY_TRAMITES, tramites);
        return WSF_ALTA_LISTA_INTEGRANTES;
    }

    @RequestMapping(value = "/alta/quitarUltimoIntegrante")
    public String altaQuitarUltimoIntegrante(Model model, HttpServletRequest request,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites) {
        altaQuitarUltimoIntegrante(tramites);
        return WSF_ALTA_LISTA_INTEGRANTES;
    }

    private void altaQuitarUltimoIntegrante(List<TramiteSeguroIvroMod33> tramites) {
        int index = tramites.size() - 1;
        if (index >= 0) {
            tramites.remove(index);
            log.info("********** Integrantes que permanecen: " + tramites.size());
        }
    }

    @RequestMapping(value = "/comunes/resumen")
    public String comunResumen(Model model, HttpSession session, HttpServletRequest request,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod33 tramiteSeguro,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites,
            @ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
            @ModelAttribute("enRenovacion") boolean enRenovacion,
            @ModelAttribute("extemporanea") boolean extemporanea) {

        String view;

        /*
         * Se valida que el solicitante haya agregado al menos un familiar o, en
         * su defecto, que haya solicitado la incorporacion individual
         */
        boolean soloSolicitante = Boolean.parseBoolean(request.getParameter(KEY_SSOLICITANTE));

        //cambio para la
        solicitante.setNombre(SeguroIvroUtil.corrigeCadena(solicitante.getNombre()));
        solicitante.setPrimerApellido(SeguroIvroUtil.corrigeCadena(solicitante.getPrimerApellido()));
        solicitante.setSegundoApellido(SeguroIvroUtil.corrigeCadena(solicitante.getSegundoApellido()));

        if (!soloSolicitante && tramites.size() < 2) {

            if(enRenovacion){
                model.addAttribute(COMUN_ERROR, "Debes agregar al menos a un familiar para poder realizar la "
                        + "renovaci\u00F3n al seguro de salud para la familia");
            }else{
                model.addAttribute(COMUN_ERROR, "Debes agregar al menos a un familiar para poder realizar la "
                        + "incorporaci\u00F3n al seguro de salud para la familia");
            }

            model.addAttribute(KEY_SSOLICITANTE, false);

            view = WSF_ALTA_LISTA_INTEGRANTES;
        } else {

            List<Fisica> integrantes = new ArrayList<Fisica>(tramites.size());

            for (TramiteSeguroIvroMod33 tramite : tramites) {
                integrantes.add((Fisica) tramite.getBeneficiarios()[0]);
                if (soloSolicitante) {
                    break;
                }
            }

            try {

                Cotizacion cotizacionGeneral = this.generarCotizacion(solicitante, integrantes, datosCalculo, enRenovacion && !extemporanea);

                /*
                 * Se barre la lista de personas que se cotizaron para settear
                 * la descripcion del parentesco
                 */
                for (EmpleadoCuota integrante : cotizacionGeneral.getDetalle().getEmpleados()) {
                    if (integrante.getParentesco() != null) {
                        integrante.getParentesco().setDescripcion(
                                getDescripcionParentesco(integrante
                                        .getParentesco().getIdParentesco()
                                        .intValue()));
                    } else {
                        this.log.warn("El integrante con NSS "
                                + integrante.getNumeroSeguridadSocial()
                                + " no cuenta con parentesco");
                    }
                    integrante.setNombreTrabajador(SeguroIvroUtil.corrigeCadena(integrante.getNombreTrabajador()));
                }
                /* en caso de renovacion oportuna y extemporanea se omite la
                 * regla de aplicacion de cuestionario
                 */
                if (enRenovacion) {
                    cotizacionGeneral.setAplicaCuestionario(false);
                }
                tramiteSeguro.setBeneficiarios(integrantes.toArray(new Fisica[0]));
                tramiteSeguro.setCotizacion(cotizacionGeneral);
                EstadoTramite estadoTramite = new EstadoTramite();
                estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getId());
                tramiteSeguro.setEstadoTramite(estadoTramite);
                Modalidad modalidad = new Modalidad();
                modalidad.setIdModalidad(datosCalculo.getModalidad());
                tramiteSeguro.setModalidad(modalidad);
                tramiteSeguro.setPersona(solicitante);
                log.info("ID PERSONA: " + solicitante.getIdPersona());
                TipoTramite tipoTramite = new TipoTramite();
                tipoTramite.setIdTipoTramite((enRenovacion && !extemporanea)
                        ? TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo()
                        : TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo());
                tramiteSeguro.setTipoTramite(tipoTramite);
                Solicitud solicitud = new Solicitud();
                solicitud.setTramite(new TramiteSeguroIvroMod33[]{tramiteSeguro});
                OrigenSolicitud origenSolicitud = new OrigenSolicitud();
                origenSolicitud.setIdOrigenSolicitud(SeguroIvroUtil.getAmbiente(request));
                solicitud.setOrigenSolicitud(origenSolicitud);
                TipoSolicitud tipoSolicitud = new TipoSolicitud();
                tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.INCORPORACION_SEGURO_FAMILIAR.getId());
                solicitud.setTipoSolicitud(tipoSolicitud);
                EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
                solicitud.setEstadoSolicitud(estadoSolicitud);
                solicitud.setFechaRegistro(new Date());
                log.info("********** ID PERSONA: " + ((TramiteSeguroIvroMod33) solicitud.getTramite()[0]).getPersona().getIdPersona());
                //Se agrega usuario
                String strUsuario;
                Long ambiente = SeguroIvroUtil.getAmbiente(request);
                if (!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
                    UsuarioSSO sso = this.procesarUsuarioSSO(request);
                    strUsuario = sso.getCurp();
                } else {
                    strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(solicitante.getIdPersona());
                }
                solicitud.setUsuario(strUsuario);
                Solicitud solicitudResultado = new Solicitud();
                try {
                    solicitudResultado = callWebService(
                            webServiceSolicitudSeguroIvro, solicitud,
                            Solicitud.class, new Class[]{Solicitud.class,
                                TramiteSeguroIvroMod33.class});
                } catch (Exception e) {
                    model.addAttribute(COMUN_ERROR, "Ocurri\u00F3 un error al intentar registrar la solicitud.");
                    log.error("********** Ocurrio un error al intentar registrar la solicitud.", e);

                    view = WSF_COMUN_RESUMEN;
                }
                if (solicitudResultado.getErrorFormGeneral() != null && !solicitudResultado.getErrorFormGeneral().trim().isEmpty()) {
                    model.addAttribute(COMUN_ERROR, solicitudResultado.getErrorFormGeneral());
                    view = WSF_COMUN_RESUMEN;
                }
                solicitud.setIdSolicitud(solicitudResultado.getIdSolicitud());
                solicitud.setNumSolicitud(solicitudResultado.getNumSolicitud());
                solicitud.getTramite()[0].setTramiteId(solicitudResultado.getTramite()[0].getTramiteId());
                log.info("********** ID SOLICITUD: " + solicitud.getIdSolicitud() + " **********");
                generarCadenaOriginalyFirma(solicitud, solicitante, session);
                model.addAttribute(KEY_SOLICITUD, solicitud);
                session.setAttribute(KEY_SOLICITUD, solicitud);
                view = WSF_COMUN_RESUMEN;
            } catch (IVROServiceException e) {
                model.addAttribute(COMUN_ERROR, e.getErrorMsg());
                log.error("********** Ocurrio un error al intentar generar la cotizacion.", e);

                view = WSF_COMUN_RESUMEN;
            }
        }
        return view;
    }

    @RequestMapping(value = "/comunes/cancelarSolicitud")
    public @ResponseBody
    Map<String, ? extends Object> comunCancelarSolicitud(Model model,
            @ModelAttribute("solicitud") Solicitud solicitud) {

        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getId());
        solicitud.getTramite()[0].setEstadoTramite(estadoTramite);

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);

        Solicitud solicitudResultado = new Solicitud();

        try {
            solicitudResultado = callWebService(webServiceSolicitudSeguroIvro, solicitud, Solicitud.class);
        } catch (Exception e) {
            solicitud.setErrorFormGeneral("Ocurri\u00F3 un error al intentar cancelar la solicitud.");
            log.error("********** Ocurrio un error al intentar cancelar la solicitud.", e);
        }

        log.info("********** SOLICITUD: " + solicitudResultado.getIdSolicitud() + " CANCELADA **********");
        model.addAttribute(KEY_SOLICITUD, solicitud);

        return null;
    }

    @RequestMapping(value = "/comunes/limpiarDatos")
    public @ResponseBody
    Map<String, ? extends Object> comunLimpiarDatos(Model model,
            HttpSession session) {

        model.addAttribute(KEY_PARENTESCOS, getParentescos());
        model.addAttribute(KEY_APLICACUESTIONARIO, getTramiteSeguro());
        model.addAttribute(KEY_TRAM_SEGURO, getTramiteSeguro());
        model.addAttribute(KEY_ID_PERSOL, getIdPersonaSolicitante());
        model.addAttribute(KEY_SOLICITANTE, getSolicitante());
        model.addAttribute("domicilioSeguro", getDomicilioSeguro());
        model.addAttribute(KEY_DOMICILIO_OUBICACION, getDomicilioSeguro());
        model.addAttribute(KEY_TRAMITES, getTramites());
        model.addAttribute(KEY_DATOS_CALCULO, getDatosCalculo());
        model.addAttribute(KEY_SOLICITUD, getSolicitud());
        model.addAttribute("umf", getUmf());
        model.addAttribute(KEY_DEXTRANJERO, getDesdeExtranjero());
        model.addAttribute("desdeExtranjeroDom", getDesdeExtranjeroDom());
        model.addAttribute(KEY_EN_RENOVACION, getEnRenovacion());
        model.addAttribute(KEY_SEG_FAM, getSegurosFamiliares());
        model.addAttribute(KEY_EXTEMPORANEA, getExtemporanea());
        model.addAttribute(KEY_UMFSEGUROASOCIADO, getUmfSeguroAsociado());
        model.addAttribute(KEY_SSOLICITANTE, getSoloSolicitante());

        session.removeAttribute("parentescos");
        session.removeAttribute("aplicaCuestionario");
        session.removeAttribute("tramiteSeguro");
        session.removeAttribute("idPersonaSolicitante");
        session.removeAttribute("solicitante");
        session.removeAttribute("domicilioSeguro");
        session.removeAttribute("domicilioOtraUbicacion");
        session.removeAttribute("tramites");
        session.removeAttribute("datosCalculo");
        session.removeAttribute("solicitud");
        session.removeAttribute("umf");
        session.removeAttribute("desdeExtranjero");
        session.removeAttribute("desdeExtranjeroDom");
        session.removeAttribute("enRenovacion");
        session.removeAttribute("segurosFamiliares");
        session.removeAttribute("extemporanea");
        session.removeAttribute("umfSeguroAsociado");
        session.removeAttribute("soloSolicitante");
        session.removeAttribute("soloSolicitante");

        session.removeAttribute(KEY_CADENA_ORIGINAL);
        session.removeAttribute(KEY_FIRMA_ELECTRONICA);
        session.removeAttribute(KEY_EN_RENOVACION_SSF);

        return null;
    }

    @RequestMapping(value = "/comunes/procesar-datos-firma", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> comunProcesarDatosFirma(Model model,
            @RequestBody FirmaElectronica firmaElectronica,
            HttpServletRequest request,
            HttpServletResponse response, HttpSession session,
            @ModelAttribute("solicitud") Solicitud solicitud,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("domicilioSeguro") Domicilio domicilioSeguro,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod33 tramite,
            @ModelAttribute("tramites") List<TramiteSeguroIvroMod33> tramites,
            @ModelAttribute("extemporanea") boolean extemporanea) throws IVROServiceException {

        Long idOrigen = SeguroIvroUtil.getAmbiente(request);

        log.info("********** EL ORIGEN DE LA SOLICITUD ES: " + idOrigen != null ? OrigenSolicitudEnum.getById(idOrigen).getDesc() : CADENA_VACIA);

        if (esSolicitudInternet(idOrigen)) {
            log.info("********** ES PORTAL, SE RECIBEN LOS DATOS DE LA FIRMA ELECTRONICA **********");
            session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
            solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
            solicitud.setFirmadaDigitalmente(true);
            solicitud.setFirmaElectronica(firmaElectronica);
        }

        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO
                .getId());
        estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.name());
        solicitud.getTramite()[0].setEstadoTramite(estadoTramite);

        TramiteSeguroIvroMod33 tsi = (TramiteSeguroIvroMod33) solicitud
                .getTramite()[0];

        if (StringUtils.isNotBlank(tsi.getDetalleTramiteXml())) {
            tsi.setDetalleTramiteXml(null);
        }

        boolean enRenovacion = session.getAttribute(KEY_EN_RENOVACION_SSF) != null ? (Boolean) session.getAttribute(KEY_EN_RENOVACION_SSF) : false;
        tsi.setRenovacion(enRenovacion);

        //Se coloca la bandera de si debe aplicarse el cuestionario, esta misma se usa para mostrar o no la seccion en el comprobante
        if (enRenovacion || extemporanea) {
            boolean muestraCuestionario;
            try {
                muestraCuestionario = getMuestraCuestionario(tramites);
                log.info("Se muestra el cuestionario 3: "+muestraCuestionario);

                tsi.getCotizacion().setAplicaCuestionario(muestraCuestionario);
                tsi.setAplicaCuestionario(muestraCuestionario);
            } catch (IVROExceptionGenerico ex) {
                log.error("Error al determinar si aplica cuestionario en  comprobante.", ex);
            }
            /**
             * Se agrega Variable a sesion para el envio de comprobante y linea
             * de captura en la renovacion SSF Esta variable se quitara de
             * sesion en el detalle de tramite para qeu solo se ejecute al
             * termino de una Renovacion
             */
            session.setAttribute("enviarCorreoRenovacionSSF", Boolean.TRUE);
        } else {
            session.setAttribute("enviarCorreoCompraSSF", Boolean.TRUE);
        }

        String detalleXml = SeguroIvroUtil.getDetalleTramiteString(tsi);

        this.log.debug("DETALLE XML -> " + detalleXml);

        solicitud.getTramite()[0].setDetalleTramiteXml(detalleXml);

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);

        //Se agrega usuario
        String strUsuario;

        if (!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(idOrigen)) {
            UsuarioSSO sso = this.procesarUsuarioSSO(request);
            strUsuario = sso.getCurp();
        } else {
            strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(solicitante.getIdPersona());
        }
        solicitud.setUsuario(strUsuario);
        solicitud.setUsuarioResponsable(strUsuario);

        Map<String, Object> result = new HashMap<String, Object>();
        log.info("********** ID PERSONA: " + tsi.getPersona().getIdPersona());

        session.setAttribute(KEY_NSS_CIFRADO_FINAL, tsi.getPersona()
                .getNssCifrado());
        log.info("**** GUARDANDO SOLICITUD...***");
        try {
            String solicitudXml = JaxbUtil.marshaller(solicitud);
            log.info("solicitudFinalMod33Xml:" + solicitudXml);
        } catch (JAXBException ex) {
            log.error("Error en la solicitud:" + ex);
        }
        Solicitud solicitudResult = seguroIndividualServices
                .guardaSolicitud(solicitud);

        if (hasError(solicitudResult)) {
            result.put(COMUN_ERROR, solicitudResult.getErrorFormGeneral());
        } else {
            result.put(KEY_SOLICITUD, solicitud);

            log.info("**** GUARDANDO DOMICILIO...***");
            if (tsi != null && tsi.getDomicilioSeguro() != null
                    && tsi.getDomicilioSeguro().getIdDomicilio() == null) {
                log.info("**** Se asociara en la base el nuevo domicilio: " + tsi.getDomicilioSeguro().getCalle() + " ***");
                guardarPersonaDomicilio(tsi.getDomicilioSeguro(), tsi.getPersona().getIdPersona());
                log.info("****DOMICILIO GUARDADO***");

            }
            mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion subdelegacion;
            try {

                subdelegacion = domicilioServiceBusiness.getSubDelegacionPorCodigoPostal(tsi.getDomicilioSeguro().getCodigoPostal());
                if (subdelegacion != null && subdelegacion.getId() != null) {
                    solicitudServiciosExpuestos.asociarSolicitudSubDelegacion(solicitud.getIdSolicitud(), subdelegacion.getId());
            }
            } catch (SubDelegacionNoLocalizadaException ex) {
                log.error("No se localizo Subdelegacion", ex);
            }
            catch (Exception e) {
                log.error("No se localizo Subdelegacion", e);
            }

        }
        session.removeAttribute("tramiteSeguro");
        return result;
    }

    @RequestMapping(value = "/comunes/generarComprobantes")
    @ResponseBody
    public void comunGenerarComprobantes(HttpServletResponse response,
            @ModelAttribute("solicitud") Solicitud solicitud,
            @ModelAttribute("seguros") SegurosIvro segurosIvro) {
    }

    @RequestMapping(value = "/comunes/generarCuestionario")
    @ResponseBody
    public void comunGenerarCuestionarios(HttpServletResponse response,
            @ModelAttribute("solicitud") Solicitud solicitud,
            @ModelAttribute("seguros") SegurosIvro segurosIvro) {
    }

    public boolean esSolicitudInternet(Long origenSolicitud) {
        boolean esInternet = false;
        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
        if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
            esInternet = true;
        }
        return esInternet;
    }

    private Cotizacion generarCotizacion(Fisica solicitante, List<Fisica> integrantes,
            DatosCalculoCuota datosCalculo, boolean renovacion) throws IVROServiceException {

        String sueldoS = "2200";
        String cadena = CADENA_VACIA;
        BigDecimal sueldoDiarioTrabajador = new BigDecimal(sueldoS.equals(cadena) || sueldoS.trim().isEmpty() ? "0" : sueldoS);
        sueldoDiarioTrabajador = sueldoDiarioTrabajador.divide(new BigDecimal(
                30), 2, RoundingMode.HALF_UP);
        boolean soloSolicitante = integrantes.size() == 1;
        DatosCalculoCuota dcc = new DatosCalculoCuota();
        dcc.setFechaInicioCalculo(datosCalculo.getFechaInicioCalculo());
        dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
        dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
        dcc.setModalidad(datosCalculo.getModalidad());
        dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
        dcc.setRenovacion(renovacion);
        dcc.setAplicaCuestionario(datosCalculo.getAplicaCuestionario());

        dcc.setSalarioMinimo(new BigDecimal(1000));

        dcc.setRecargos(false);
        dcc.setIdEmpleador(solicitante.getIdPersona());

        // Datos del integrante
        List<DatosEmpleado> empleados = new ArrayList<DatosEmpleado>();

        for (Fisica integrante : integrantes) {
            DatosEmpleado empleado = new DatosEmpleado();
            empleado.setNumeroSeguridadSocial(integrante.getNss());
            empleado.setSalario(sueldoDiarioTrabajador);
            empleado.setEdad(0);
            empleado.setCurp(integrante.getCurp());

            if (integrante.getNssCifrado() == null) {
                empleado.setParentesco(((Familiar) integrante).getParentesco().getIdParentesco());
                empleado.setAplicaCuestionario(((Familiar) integrante).getAplicaCuestionario());
                empleado.setInscripcion(((Familiar) integrante).getInscripcion() || !renovacion);

            } else {
                // Se trata del titular, se debe mandar 0 al servicio
                empleado.setParentesco(0L);
                empleado.setIndividual(soloSolicitante);
                if (renovacion) {
                    //Si es renovación del titular se especifíca que no se le aplicará cuestionario para este caso
                    empleado.setAplicaCuestionario(false);
                    dcc.setAplicaCuestionario(false);
                }else {
                    empleado.setAplicaCuestionario(((Familiar) integrante).getAplicaCuestionario());
                }
                empleado.setInscripcion(!renovacion);

            }

            empleados.add(empleado);
        }

        dcc.setEmpleados(empleados.toArray(new DatosEmpleado[0]));

        Cotizacion cotizacionIntegrante = new Cotizacion();
        try {
            log.info("dcc a cotizar familiar: "+ BeanUtils.describe(dcc));

            cotizacionIntegrante = callWebService(
                    webServiceCotizaSeguroFamiliar, dcc,
                    Cotizacion.class);
        } catch (Exception e) {
            String error = "Ocurri\u00F3 un error al intentar realizar la cotizaci\u00F3n del integrante con NSS. Intenta nuevamente.";

            log.error(error, e);
            throw new IVROServiceException(error);
        }

        if (cotizacionIntegrante.getErrorFormGeneral() != null
                && !cotizacionIntegrante.getErrorFormGeneral().trim().isEmpty()) {
            if ("No coinciden los datos estad\u00EDsticos entre RENAPO y los localizados en el instituto".equals(cotizacionIntegrante.getErrorFormGeneral())) {
                log.info("Modifica el mensaje del webservice por reglas de negocio");
                cotizacionIntegrante.setErrorFormGeneral("La CURP y el NSS capturado no coinciden con lo registrado en el Instituto, verifique los datos ingresados o acuda a la Subdelegaci\u00F3n en caso de requerir mayor aclaraci\u00F3n");
            }
            log.error(cotizacionIntegrante.getErrorFormGeneral());
            throw new IVROServiceException(cotizacionIntegrante.getErrorFormGeneral());
        }

        if (cotizacionIntegrante.getDetalle() != null
                && cotizacionIntegrante.getDetalle().getEmpleados().length > 0) {
            Arrays.sort(cotizacionIntegrante.getDetalle().getEmpleados()[0].getPeriodos(), new Comparator<PeriodoCuota>() {
                @Override
                public int compare(PeriodoCuota o1, PeriodoCuota o2) {
                    if (o1.getOrden() < o2.getOrden()) {
                        return -1;
                    } else if (o1.getOrden() > o2.getOrden()) {
                        return 1;
                    }
                    return 0;
                }
            });
        } else {
            String error = ERROR_COTIZACION;

            log.error(error);
            throw new IVROServiceException(error);
        }
        return cotizacionIntegrante;
    }

    /**
     * Genera una solicitud de rechazo asociada al solicitante para su posterior
     * validacion
     *
     * @param request
     * @param empleador
     * @param infoCuestionario
     * @param tramiteSeguro
     */
    private void generaSolicitudRechazo(HttpServletRequest request,
            Fisica empleador, TramiteCuestionarioDummy infoCuestionario,
            TramiteSeguroIvroMod33 tramiteSeguro) {

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        log.error("Se generara solicitud de rechazo para seguro familiar: "
                + empleador.getNss());

        PersonaCuestionario personaCuestionario = UtilConvert
                .parseToPersonaCuestionario(infoCuestionario);
        personaCuestionario.setNssPersona(empleador.getNss());
        personaCuestionario.setCuestionarioValido(true);
        personaCuestionario.setIdPersona(empleador.getIdPersona());

        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR
                .getCodigo());
        tramiteSeguro.setEstadoTramite(new EstadoTramite());
        tramiteSeguro.getEstadoTramite().setIdEstadoTramitePersona(
                EstadoTramiteEnum.RECHAZADO.getId());
        tramiteSeguro.setTipoTramite(tipoTramite);
        tramiteSeguro.setPersona(empleador);

        String strUsuario;
        if (!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
            UsuarioSSO sso = this.procesarUsuarioSSO(request);
            strUsuario = sso.getCurp();
        } else {
            strUsuario = seguroIndividualServices
                    .obtenerCurpPorIdPersona(empleador.getIdPersona());
        }

        Solicitud solicitud = new Solicitud();

        OrigenSolicitud origen = new OrigenSolicitud();
        origen.setIdOrigenSolicitud(ambiente);
        solicitud.setOrigenSolicitud(origen);

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA
                .getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setFechaRegistro(new Date());

        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.INCORPORACION_SEGURO_FAMILIAR
                .getId());
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setUsuario(strUsuario);
        solicitud.setUsuarioResponsable(strUsuario);
        solicitud.setTramite(new TramiteSeguroIvroMod33[]{tramiteSeguro});

        // Se inicializa como solicitud rechazada
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
                EstadoSolicitudEnum.RECHAZADA.getId().intValue());
        solicitud = seguroIndividualServices.guardaSolicitud(solicitud);



        log.debug("Se genero el folio de rechazo para seguro familiar: "
                + solicitud.getNumSolicitud() + " para el NSS "
                + empleador.getNss());

    }

    /**
     * Genera una solicitud de rechazo asociada al solicitante para su posterior
     * validacion
     *
     * @param request
     * @param empleador
     * @param infoCuestionario
     * @param tramiteSeguro
     */
    private void generaSolicitudRechazoCuestionario(HttpServletRequest request,
                                        Fisica empleador, TramiteCuestionarioDummy infoCuestionario,
                                        TramiteSeguroIvroMod33 tramiteSeguro,
                                        Beneficiario beneficiarioCancelar) {

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        log.error("Se generara solicitud de rechazo para seguro familiar: "
                + beneficiarioCancelar.getNss());

        PersonaCuestionario personaCuestionario = UtilConvert
                .parseToPersonaCuestionario(infoCuestionario);
        personaCuestionario.setNssPersona(empleador.getNss());
        personaCuestionario.setCuestionarioValido(true);
        personaCuestionario.setIdPersona(empleador.getIdPersona());

        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR
                .getCodigo());
        tramiteSeguro.setEstadoTramite(new EstadoTramite());
        tramiteSeguro.getEstadoTramite().setIdEstadoTramitePersona(
                EstadoTramiteEnum.RECHAZADO.getId());
        tramiteSeguro.setTipoTramite(tipoTramite);
        tramiteSeguro.setPersona(empleador);

        String strUsuario;
        if (!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
            UsuarioSSO sso = this.procesarUsuarioSSO(request);
            strUsuario = sso.getCurp();
        } else {
            strUsuario = seguroIndividualServices
                    .obtenerCurpPorIdPersona(empleador.getIdPersona());
        }

        Solicitud solicitud = new Solicitud();

        OrigenSolicitud origen = new OrigenSolicitud();
        origen.setIdOrigenSolicitud(ambiente);
        solicitud.setOrigenSolicitud(origen);

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA
                .getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setFechaRegistro(new Date());

        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.INCORPORACION_SEGURO_FAMILIAR
                .getId());
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setUsuario(strUsuario);
        solicitud.setUsuarioResponsable(strUsuario);
        solicitud.setTramite(new TramiteSeguroIvroMod33[]{tramiteSeguro});

        // Se inicializa como solicitud rechazada
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
                EstadoSolicitudEnum.RECHAZADA.getId().intValue());
        solicitud = seguroIndividualServices.guardaSolicitud(solicitud);

        log.debug("Se genero el folio de rechazo para seguro familiar: "
                + solicitud.getNumSolicitud() + " para el NSS "
                + empleador.getNss());

        log.info("++Se procede a marcar al beneficiario");

        log.info("El numero de solicitud al que se le cancela el beneficiario es: "+solicitud.getNumSolicitud());

        boolean inserta = false;
        try {
            inserta = seguroIndividualServices.insertaCancelacionCuestionario(beneficiarioCancelar, solicitud.getNumSolicitud());
        } catch (IVROServiceException e) {
            log.error("Error: "+e);
        }
        if(inserta){
            log.info("Se marco correctamente el beneficiario");
        }else{
            log.info("No se marco, correctamente el beneficiario");
        }

        log.info("++termina el marcado del beneficiario");

    }

    private void generarCadenaOriginalyFirma(Solicitud solicitud,
            Persona persona, HttpSession session) {

        Locale locMEX = new Locale("es", "MX");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        Date fecha = Calendar.getInstance().getTime();
        FirmaElectronica datosEntradaFirma = new FirmaElectronica();
        StringBuffer contenidoAFirmar = new StringBuffer();

        // Inicio
        contenidoAFirmar.append("||");
        contenidoAFirmar.append("Invocante:portalimssdigital|");

        // Denominacion del Tramite o servicio
        contenidoAFirmar.append("Tramite:");
        contenidoAFirmar.append(DESC_ALTA_TRAMITE_SOLICITUD).append("|");

        // Fecha Electronica
        String strFechaElectronica = dateFormat.format(fecha);
        contenidoAFirmar.append("Fecha:");
        contenidoAFirmar.append(strFechaElectronica).append("|");
        datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
        datosEntradaFirma.setFechaElectronica(fecha);

        // Folio
        contenidoAFirmar.append("Folio:");
        contenidoAFirmar.append(solicitud.getNumSolicitud()).append("|");

//		datosEntradaFirma.setRfc(persona.getRfc());
        session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
        session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
    }

    private Map<String, Object> descifrarNss(HttpSession session,
            String valorRetornoErrorNssCifrado, String nssCifrado) {
        Map<String, Object> result = new HashMap<String, Object>();
        String nss = null;
        try {
            nss = Base64Cipher.descrifrar(nssCifrado);
        } catch (InvalidKeyException e1) {
            nssCifrado = valorRetornoErrorNssCifrado;
            log.error(e1);
        } catch (IllegalBlockSizeException e1) {
            nssCifrado = (String) session.getAttribute(KEY_NSS_CIFRADO_FINAL);
            if (StringUtils.isNotEmpty(nssCifrado) && StringUtils.isNotBlank(nssCifrado)) {
                try {
                    nss = Base64Cipher.descrifrar(nssCifrado);
                } catch (IOException e) {
                    nssCifrado = valorRetornoErrorNssCifrado;
                    log.error(MESSAGE_LOG_ERROR, e);
                } catch (InvalidKeyException e) {
                    nssCifrado = valorRetornoErrorNssCifrado;
                    log.error(MESSAGE_LOG_ERROR, e);
                } catch (BadPaddingException e) {
                    nssCifrado = valorRetornoErrorNssCifrado;
                    log.error(MESSAGE_LOG_ERROR, e);
                } catch (IllegalBlockSizeException e) {
                    nssCifrado = valorRetornoErrorNssCifrado;
                    log.error(MESSAGE_LOG_ERROR, e);
                }
            } else {
                nssCifrado = valorRetornoErrorNssCifrado;
                log.error(e1);
            }
        } catch (BadPaddingException e1) {
            nssCifrado = valorRetornoErrorNssCifrado;
            log.error(MESSAGE_LOG_ERROR, e1);
        } catch (IOException e1) {
            nssCifrado = valorRetornoErrorNssCifrado;
            log.error(MESSAGE_LOG_ERROR, e1);
        }

        if (StringUtils.isNotEmpty(nssCifrado)
                && StringUtils.isNotBlank(nssCifrado) && !nssCifrado.equals("-1")) {
            session.setAttribute(KEY_NSS_CIFRADO_FINAL, nssCifrado);
        } else {
            session.removeAttribute(KEY_NSS_CIFRADO_FINAL);
        }

        result.put(KEY_NSS_SOLIC_SEGURO_FAMILIAR, nss);
        result.put(KEY_NSS_CIFRADO_SOLIC_SEGURO_FAMILIAR, nssCifrado);

        return result;
    }

    private DatosCalculoCuota obtenerDatosCalculoCuota(Domicilio domicilioSeguro,
            Fisica solicitante, HttpServletRequest request) throws IVROServiceException {

        DatosCalculoCuota dcc = new DatosCalculoCuota();

        /*
     * Se utiliza una nueva instancia para no afectar a la que esta en
     * sesion
         */
        Fisica solicitanteTmp = new Fisica();

        try {
            solicitanteTmp.setDomicilioParticular(domicilioSeguro);
            solicitanteTmp.setIdPersona(solicitante.getIdPersona());

            dcc = callWebService(webServiceValidaCompraSeguroFamiliar,
                    solicitanteTmp, DatosCalculoCuota.class);

            dcc.setErrorFormGeneral(getOrigenContext(request));

            if (dcc != null) {
                dcc.setNumeroRegistroPatronal(null);
            }

        } catch (Exception e) {
            String msgError = "Ocurri\u00F3 un error inesperado al obtener los datos para calcular las cuotas.";
            log.error(msgError, e);
            throw new IVROServiceException(msgError);
        }

        return dcc;
    }

    private String getDescripcionParentesco(int idParentesco) {
        return ParentescoIssfEnum.obternerEnumById(idParentesco).getDescripcion();
    }

    private TramiteSeguroIvroMod33 obtenerCotizacionBeneficiarios(
            Fisica beneficiario, Fisica solicitante,
            DatosCalculoCuota datosCalculo, boolean renovacion)
            throws IVROServiceException {
        TramiteSeguroIvroMod33 tramite = new TramiteSeguroIvroMod33();
        Familiar familiar = new Familiar();
        List<Fisica> integrantes = new ArrayList<Fisica>();
        try {
            if (beneficiario.getCurp() == null) {
                AsignacionNSS persona = serviceBusinessRemote.obtenerAsignacionNss(beneficiario.getNss());
                beneficiario.setCurp(persona.getCurp());
            }
            StringTokenizer token = new StringTokenizer(beneficiario
                    .getErrorFormGeneral().trim(), "|");
            Long idParentesco = Long.valueOf(token.nextToken());

            familiar.setNss(beneficiario.getNss());
            familiar.setCurp(beneficiario.getCurp());
            familiar.setParentesco(new Parentesco());
            familiar.getParentesco().setIdParentesco(idParentesco);
            familiar.getParentesco().setDescripcion(
                    getDescripcionParentesco(idParentesco.intValue()));

            integrantes.add(familiar);

            Cotizacion cotizacionIntegrante = this.generarCotizacion(
                    solicitante, integrantes, datosCalculo, renovacion);

            familiar.setNombre(cotizacionIntegrante.getDetalle().getEmpleados()[0]
                    .getNombreTrabajador());
            familiar.setCurp(cotizacionIntegrante.getDetalle().getEmpleados()[0]
                    .getCurp());

            tramite.setBeneficiarios(new Fisica[]{familiar});
            tramite.setCotizacion(cotizacionIntegrante);
        } catch (Exception e) {
            tramite = null;
            this.log.debug(e);
        }

        return tramite;
    }

    /**
     * MEtodo encargado de cancelar una solicitud
     *
     * @param domicilioInicial
     * @param idPersona
     */
    public void guardarPersonaDomicilio(Domicilio domicilioInicial, Long idPersona) {
        log.info("solicitudResult:" + domicilioInicial.toString());
        log.info("IdPersona:" + idPersona);
        try {
            seguroIndividualServices.guardarYAsociarDomiciliosPersona(domicilioInicial, idPersona);
        } catch (DomicilioNoValidoException ex) {
            log.error("Error al guardar el domicilio", ex);
        } catch (DomicilioNoLocalizadoException ex) {
            log.error("Error al guardar el domicilio", ex);
        }
    }

    private Boolean getMuestraCuestionario(List<TramiteSeguroIvroMod33> tramites) throws IVROExceptionGenerico {
        for (TramiteSeguroIvroMod33 tramite : tramites) {
            if (tramite.getCotizacion() != null
                    && tramite.getCotizacion().getAplicaCuestionario()) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/validaPersona/{idPersona}/", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, ? extends Object> validarAccesotramite(Model model,
            HttpServletRequest request, HttpSession session,
            @PathVariable Long idPersona) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("idPersona", idPersona);
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        DatosCalculoCuota dcc;
        try {
            dcc = callWebService(
                    webServiceValidaPersonaSeguroFamiliarRenova, persona,
                    DatosCalculoCuota.class);
            this.log.debug("Resultado de la validacion para dar acceso a la Renovacion del SSF" + dcc);
            if (StringUtils.isNotBlank(dcc.getErrorFormGeneral())) {
                throw new IVROServiceException(dcc.getErrorFormGeneral());
            } else {
                //ruesult sin error para continuar el flujo de renovacion
                result.put(COMUN_ERROR, false);
            }
        } catch (IVROServiceException e) {
            result.put(COMUN_ERROR, true);
            result.put(KEY_MSGERROR, e.getMessage());
        } catch (Exception e) {
            String msgError = "Error inesperado al validar el acceso al tr\u00E1mite";
            this.log.error(msgError, e);
            result.put(COMUN_ERROR, true);
            result.put(KEY_MSGERROR, msgError);
        }
        return result;
    }

	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/finalizarSeguroDetalle/obtenerLcSipareSsf/{cveIdPersona}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String obtenerLcSipareSsf(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long cveIdPersona) {

    	log.info("************************** Entrando al metodo obtenerLcSipareSsf, cveIdPersona: " + cveIdPersona);

        Persona persona = new Persona();
        persona.setIdPersona(cveIdPersona);

        try {
            /*
             * Se consume primero el WS que consulta los seguros modalidad 33 de
             * la persona que solicita, para poder mostrar el detalle del seguro
             * o, en su defecto, continuar con las validaciones de acceso
             */

        	log.info("************************** Entrando a callWebService");
        	SegurosIvro seguros = callWebService(webServiceConsultaSeguroFamiliar, persona, SegurosIvro.class);
        	log.info("************************** Saliendo a callWebService");

        	if(seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0){

        			log.info("************************** Tiene seguros");
        			for(SeguroIvro seguro: seguros.getSeguroIvro()) {
        				log.info("************************** Obteniendo Compras");
        				Long idCompra = seguro.getCompra().getIdCompra();
        				log.info("************************** Se obtiene Compra: " + idCompra);
        				try {
        					log.info("************************** Llamando a compraServiceRemote");
        					Compra compra = compraServiceRemote.findCompraById(idCompra);
        					Pago[] pagos = compra.getPagos();
        					for(Pago pago: pagos) {
        						log.info("************************** Recorriendo Pagos");
        						if (pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
        							log.info("************************** Obteniendo Lineas de Captura");
        		    				Pago pagoLc = getPagoLC(pago.getIdPago());
        		    				log.info("************************** LC guardada en BD: " + pagoLc.getLineaCaptura());
        		    			}
        					}
        				} catch (SUAException e) {
                    		log.error("************************** Ocurrio un error al validar las LC: " + e);
                    		e.printStackTrace();
                            return e.getMessage();
        				}
        			}

        		}

	        } catch (Exception e) {
	            return e.getMessage();
        }
        return null;
	}

	/**
     * Obtiene un pago con la generacion de su linea de captura
     *
     * @param idPago el identificador del pago
     * @return el pago generado
     */
    private Pago getPagoLC(Long idPago) {
        Pago pago = new Pago();
        pago.setIdPago(idPago);
        Pagos pagos = new Pagos();
        pagos.setPago(new Pago[]{pago});
        Pago pagoLC = new Pago();
        String messageErrorPay = "";
        String messageErrorServer = "";
        Pago pagoDB = null;

		  try {

		  //Se obtiene pago de BD
	      pagoDB = compraServiceRemote.findPagoById(idPago);
		  if (pagoDB != null && pagoDB.getPdf() != null) {
		  pago.setPdf(pagoDB.getPdf());
		  pago.setLineaCaptura(pagoDB.getLineaCaptura());
		  log.info("************************** PDF obtenido de la BD" + pago);
		  return pago;
		  }
		  log.info("Objeto Pago obtenido de BD: "+pagoDB.getLineaCaptura());
		  }
		  catch (SUAException e){

		  log.error("Error al obtener el pago", e);
		  }

        try {
            String pagosXml = JaxbUtil.marshaller(pagos);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            log.info("Parametros WS Lineas Captura: "+pagosXml);
            webServiceLineasCaptura.sendSourceAndReceiveToResult(new StreamSource(new StringReader(pagosXml)),
                    result);
            String seguroRespuestaXml = writer.toString();
            log.info("Respuesta ws " + seguroRespuestaXml);
            messageErrorServer = seguroRespuestaXml;
            Pagos pagosLC = JaxbUtil.unmarshaller(seguroRespuestaXml, Pagos.class);
            messageErrorPay = pagosLC.getErrorFormGeneral();
            pagoLC = pagosLC.getPago()[0];

            //Actualiza PDF en BD
            pagoDB.setPdf(pagoLC.getPdf());
            if(pagoDB.getLineaCaptura() == null) {
                pagoDB.setLineaCaptura(pagoLC.getLineaCaptura());
                log.info("************************** Se actualiza PDF en BD" + pagoDB);
            }
            compraServiceRemote.actualizaPagoLC(pagoDB);

            log.info("Parseo Objeto " + ReflectionToStringBuilder.toString(pagoLC));
        } catch (Exception e) {
            log.error("Error no controlado ", e);
            pagoLC.setPdf(null);
            if (messageErrorPay.isEmpty()) {
                messageErrorPay = messageErrorServer;
            }
            pagoLC.setLineaCaptura(messageErrorPay);
            return pagoLC;
        }
        return pagoLC;
    }

}
