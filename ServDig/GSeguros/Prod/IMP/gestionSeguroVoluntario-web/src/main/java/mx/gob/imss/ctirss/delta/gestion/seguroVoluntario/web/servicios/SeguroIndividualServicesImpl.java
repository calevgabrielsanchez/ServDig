/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceValidaPagosException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.NotificacionSegurosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroRenovacionUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.legado.asegurado.RespuestaPagosVentanilla;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.TipoMedioContacto;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.util.ObtenerCurpPersonaResp;
import mx.gob.imss.digital.modelo.util.UmaResponse;
import mx.gob.imss.digital.modelo.util.obtenerSalarioMinimoVigenteDFResp;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import mx.gob.imss.ws.pagos.ivro.implementacion.ClienteWebserviceValidaPagosVentanilla;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.persistence.*;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author NOVUTECK1
 */
@Component
public class SeguroIndividualServicesImpl implements SeguroIndividualServices {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SeguroIndividualServicesImpl.class);
    /**
     * Error genericode la aplicacion
     */
    private static final String ERROR_GENERAL = "Se present\u00F3 un problema al evaluar su solicitud, intente m\u00E1s tarde";
    private static final String MSG_EXCEPTION_NSS_NO_VIGENTE = "El n\u00FAmero de seguridad social (NSS) no está vigente o no se localiza, favor de acudir a la Subdelegaci\u00F3n";

    /**
     *
     */
    public static final String FORMAT_DATE_GUINMEDIO_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String FORMAT_DATE_SINSEPARA_YYYYMMDD ="yyyyMMdd";

    public static final String CODIGO_WS_EXITO = "01";
    public static final String CODIGO_WS_ERROR = "02";
    public static final String CODIGO_WS_NO_DISPONIBLE = "-1";

    private static final Integer ESTADO_SEGURO_VENCIDO = 3;
    private static final Integer ESTADO_SEGURO_PAGADO = 2;

    /**
     * The web service datos cotizacion individual.
     */
    @Autowired
    @Qualifier("webServiceDatosCotizacionIndividual")
    private WebServiceTemplate webServiceDatosCotizacionIndividual;

    /**
     * The web service datos cotizacion individual renovacion.
     */
    @Autowired
    @Qualifier("webServiceDatosCotizacionIndividualRenova")
    private WebServiceTemplate webServiceDatosCotizacionIndividualRenova;

    /**
     * The web service genera cotizacion.
     */
    @Autowired
    @Qualifier("webServiceCotizaSeguroServices")
    private WebServiceTemplate webServiceCotizaSeguroServices;

    /**
     * The web service genera solicitud ivro personal.
     */
    @Autowired
    @Qualifier("webServiceSolicitudSeguroIvro")
    private WebServiceTemplate webServiceSolicitudSeguroIvro;

    /**
     * Servicio para cansultar el detalle de un seguro
     */
    @Autowired
    @Qualifier("webServiceConsultaSeguroIvro")
    private WebServiceTemplate consultaSeguroService;


    @Autowired
    @Qualifier("consultaSeguroIvroServiceBusiness")
    private ConsultaSeguroIvroServiceRemote consultaSeguroIvroServiceRemote;

    /**
     * Consulta de domicilios
     */
    @Autowired
    @Qualifier("webServiceDomicilio")
    private WebServiceTemplate webServiceDomicilio;

    /**
     * Consulta de medios de contacto
     */
    @Autowired
    @Qualifier("webServiceMediosContacto")
    private WebServiceTemplate webServiceMediosContacto;

    @Autowired
    @Qualifier("webServiceValidaIncorporacionBeneficioRiss")
    private WebServiceTemplate webServiceValidaIncorporacionBeneficioRiss;

    @Autowired
    @Qualifier("webServiceObtenerCurpPersona")
    private WebServiceTemplate webServiceObtenerCurpPersona;

    @Autowired
    @Qualifier("webServiceObtenerZonaSalarial")
    private WebServiceTemplate webServiceObtenerZonaSalarial;

    @Autowired
    @Qualifier("webServiceObtenerUma")
    private WebServiceTemplate webServiceObtenerUma;

    /**
     * Consulta de medios de contacto
     */
    @Autowired
    @Qualifier("webServiceValidaCorreoPersona")
    private WebServiceTemplate webServiceValidaCorreoPersona;

    @Autowired
    private ConsultaSeguroIvroServiceRemote consultaSeguroIvroService;

    @Autowired
    private CancelarBeneficioServiceBusinessRemote cancelarBeneficioServiceBusiness;

    @Autowired
    private VigenciaIvroServiceRemote vigenciaIvroServiceRemote;

    @Autowired
    private SeguroIvroRenovacionUtil seguroIvroRenovacionUtil;

    @Autowired
    private GrupoFamiliarServiceRemote grupoFamiliarService;

    @Autowired
    private NotificacionSegurosRemote notificacionSegurosRemote;

    @Autowired
    private SeguroIvroServiceRemote seguroIvroServiceRemote;

    @Autowired
    private RegistroPatronalServiceBusinessRemote registroPatronalServiceBusinessRemote;

    @Autowired
    @Qualifier("personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Obtiene los datos de calculo de un a persona
     *
     * @param persona
     * @param origen
     * @param modalidad
     * @return
     */
    @Override
    public DatosCalculoCuota obtenDatosCotizacion(Persona persona,
                                                  OrigenSolicitudEnum origen, String modalidad) {
        DatosCalculoCuota datosCalculo;
        try {
            // Se agrega el origen para determinar si se realiza validacion de
            // solicitud de rechazo
            String errorFormGeneral = origen != null ? origen.getId()
                    .toString() : "";

            // Se agrega la modalidad para incluirla en la validacion
            errorFormGeneral += modalidad != null ? "|" + modalidad : "";

            persona.setErrorFormGeneral(errorFormGeneral);
            String personaXml = JaxbUtil.marshaller(persona);

            datosCalculo = realizaConsulta(personaXml, DatosCalculoCuota.class,
                    webServiceDatosCotizacionIndividual);

        } catch (Exception e) {
            datosCalculo = new DatosCalculoCuota();
            datosCalculo.setErrorFormGeneral(ERROR_GENERAL);
            LOGGER.error("Error al parsear los datos de la peticion", e);
        }
        return datosCalculo;
    }

    /**
     * Obtiene los datos de una cotizacion para un seguro individual
     *
     * @param persona
     * @param origen
     * @param modalidad
     * @return
     */
    @Override
    public DatosCalculoCuota obtenDatosCotizacionRenovacion(Persona persona,
                                                            OrigenSolicitudEnum origen, String modalidad) {
        DatosCalculoCuota datosCalculo;
        try {
            // Se agrega el origen para determinar si se realiza validacion de
            // solicitud de rechazo
            String errorFormGeneral = origen != null ? origen.getId()
                    .toString() : "";

            // Se agrega la modalidad para incluirla en la validacion
            errorFormGeneral += modalidad != null ? "|" + modalidad : "";

            persona.setErrorFormGeneral(errorFormGeneral);
            String personaXml = JaxbUtil.marshaller(persona);
            LOGGER.info("personaXml:" + personaXml);
            datosCalculo = realizaConsulta(personaXml, DatosCalculoCuota.class,
                    webServiceDatosCotizacionIndividualRenova);
            LOGGER.info("datosCalculo:" + datosCalculo);
        } catch (Exception e) {
            datosCalculo = new DatosCalculoCuota();
            datosCalculo.setErrorFormGeneral(ERROR_GENERAL);
            LOGGER.error("Error al parsear los datos de la peticion", e);
        }
        return datosCalculo;
    }

    /**
     * Genera la cotizacion de un seguro a partir de sus datos de calculo
     *
     * @param datosCalculo los datos para generar la cotizacion
     * @return la cotizacion generada
     */
    @Override
    public Cotizacion generaCotizacion(DatosCalculoCuota datosCalculo) {
        Cotizacion cotizacion;
        try {
            String datosCalculoXml = JaxbUtil.marshaller(datosCalculo);
            LOGGER.info("dcc para cotizar:\n" + datosCalculoXml);
            cotizacion = realizaConsulta(datosCalculoXml, Cotizacion.class,
                    webServiceCotizaSeguroServices);

        } catch (Exception e) {
            cotizacion = new Cotizacion();
            cotizacion.setErrorFormGeneral(ERROR_GENERAL);
            LOGGER.error("Error al parsear los datos de la peticion", e);
        }
        return cotizacion;
    }

    /**
     * Obtiene los medios de contacto de la persona
     *
     * @param persona
     * @return persona
     */
    @Override
    public Persona getMediosContactoPersona(Persona persona) {
        Persona personaReturn = new Persona();
        try {
            String personaXml = JaxbUtil.marshaller(persona);
            LOGGER.info("========== OBJETO DE ENTRADA: " + personaXml);
            personaReturn = realizaConsulta(personaXml, Persona.class,
                    webServiceMediosContacto);
            LOGGER.info("========== Parseo Objeto "
                    + ReflectionToStringBuilder.toString(personaReturn));
        } catch (Exception e) {
            LOGGER.error("========== Error no controlado ", e);
        }
        return personaReturn;
    }

    /**
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico
     */
    @Override
    public SegurosIvro obtenSeguroIndividual(Long idPersona)
            throws IVROExceptionGenerico {
        SegurosIvro seguros = new SegurosIvro();
        try {
            Persona persona = new Persona();
            persona.setIdPersona(idPersona);
            seguros = consultaSeguroIvroService.buscaUltimosSegurosIndividual(persona);
            LOGGER.info("Parseo Objeto "
                    + ReflectionToStringBuilder.toString(seguros));
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
            throw new IVROExceptionGenerico(
                    "Se present\u00F3 un inconveniente al evaluar tu solicitud por favor int\u00E9ntalo nuevamente");
        }
        return seguros;
    }

    /**
     * Obtiene el detalle del seguro asociado
     *
     * @param idSeguro el identificador del seguro a buscar
     * @return el seguro encontrado
     */
    @Override
    public SeguroIvro getDetalleSeguro(Long idSeguro) {
        SeguroIvro seguro = new SeguroIvro();
        seguro.setCveIdSeguroIvro(idSeguro);
        try {

            seguro = consultaSeguroIvroServiceRemote.buscaSeguroDetalle(seguro);

        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }
        return seguro;
    }

    /**
     * gurada una solicitud
     */
    @Override
    public Solicitud guardaSolicitud(Solicitud solicitud) {
        Solicitud solicitudRespuesta;
        try {

            String solicitudXml = JaxbUtil.marshaller(solicitud);
            LOGGER.info("Se envia a guardar la solicitud: "+solicitud.getIdSolicitud());
            solicitudRespuesta = realizaConsulta(solicitudXml, Solicitud.class,
                    webServiceSolicitudSeguroIvro);
            // TODO: Se elimina "ErrorFormGeneral" para evitar errores. Se manda
            // guardar para respuesta RISS
            solicitudRespuesta.setErrorFormGeneral(null);
            solicitud.setErrorFormGeneral(null);
            LOGGER.info("Parseo Objeto "
                    + ReflectionToStringBuilder.toString(solicitudRespuesta));
            LOGGER.info("Imprimiendo Detalle Solicitud:\n"
                    + solicitudXml);
        } catch (Exception e) {
            solicitudRespuesta = new Solicitud();
            solicitudRespuesta.setErrorFormGeneral(ERROR_GENERAL);
            LOGGER.error("Error no controlado ", e);
        }
        return solicitudRespuesta;
    }

    /**
     * MEtodo utilitario que realiza las consultas de webservices
     *
     * @param entrada  xml de entrada para la consulta del web service
     * @param salida   objeto resultante del webservice
     * @param template el template del webservice a utilizar
     * @return el objeto regresado por el webservice
     * @throws JAXBException error al parsear la respuesta
     */
    private <T> T realizaConsulta(String entrada, Class<T> salida,
                                  WebServiceTemplate template) throws JAXBException {
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        template.sendSourceAndReceiveToResult(new StreamSource(
                new StringReader(entrada)), result);
        String comprobanesXml = writer.toString();
        // LOGGER.info("Respuesta peticion  {}", comprobanesXml);
        return JaxbUtil.unmarshaller(comprobanesXml, salida);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.
     * SeguroIndividualServices
     * #validaIncorporacionBeneficioRiss(mx.gob.imss.digital
     * .modelo.satRiss.DatosRiss)
     */
    @Override
    public DatosRiss validaIncorporacionBeneficioRiss(DatosRiss riss) {
        try {
            String datosRissXml = JaxbUtil.marshaller(riss);
            riss = realizaConsulta(datosRissXml, DatosRiss.class,
                    webServiceValidaIncorporacionBeneficioRiss);
            LOGGER.info("Parseo Objeto "
                    + ReflectionToStringBuilder.toString(riss));
        } catch (Exception e) {
            LOGGER.error("========== Error no controlado ", e);
        }
        return riss;
    }

    @Override
    public String obtenerCurpPorIdPersona(Long idPersona) {
        String curp = "";
        try {
            String personaXml = "<mx:idPersona xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">"
                    + idPersona + "</mx:idPersona>";
            ObtenerCurpPersonaResp cadenaSimple = realizaConsulta(personaXml,
                    ObtenerCurpPersonaResp.class, webServiceObtenerCurpPersona);
            curp = cadenaSimple.getCurp();

            LOGGER.error("Parseo Objeto "
                    + ReflectionToStringBuilder.toString(idPersona));
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }

        return curp;
    }

    @Override
    public BigDecimal obtenerSalarioMinimoVigenteDF(String zonaSalarial) {
        BigDecimal smvgdf = BigDecimal.ZERO;
        try {
            String xml = "<mx:zonaSalarial xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">"
                    + zonaSalarial + "</mx:zonaSalarial>";
            LOGGER.info(" -- xml entrada: " + xml);
            obtenerSalarioMinimoVigenteDFResp response = realizaConsulta(xml,
                    obtenerSalarioMinimoVigenteDFResp.class,
                    webServiceObtenerZonaSalarial);
            LOGGER.info(" -- xml response: " + response.toString());
            smvgdf = response.getSalarioMinimo();
            LOGGER.info(" -- SALARIO MINIMO VIGENTE DEL DF: " + smvgdf);
            LOGGER.error("Parseo Objeto "
                    + ReflectionToStringBuilder.toString(zonaSalarial));
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }

        return smvgdf;
    }

    @Override
    public BigDecimal obtenerUmaPorFecha(Date fecha) {
        LOGGER.info(" ******* Fecha entrada: " + fecha);

        BigDecimal uma = BigDecimal.ZERO;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormat = simpleDateFormat.format(fecha);
        LOGGER.info(" ******* Fecha format: " + fechaFormat);
        try {
            String xml = "<java:getUma xmlns:java=\"java:mx.gob.imss.digital.modelo.seguros\">"
                    + "<java:fecha>" + fechaFormat + "</java:fecha>"
                    + "</java:getUma>";
            LOGGER.info(" -- xml entrada: " + xml);
            //  LOGGER.info(" -- Objeto webServiceObtenerUma: " + webServiceObtenerUma);
int struct= 1;
            UmaResponse umaResponse = realizaConsulta(xml, UmaResponse.class, webServiceObtenerUma);
            LOGGER.info(" -- umaResponse: " + umaResponse);

            LOGGER.info(" -- xml response: " + umaResponse.toString());
            uma = umaResponse.getUma();
            LOGGER.info(" -- SALARIO MINIMO VIGENTE DEL DF: " + uma);
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }
        return uma;
    }

    /**
     * Obtiene los medios de contacto de la persona
     *
     * @param persona
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     */
    @Override
    public void getValidarCorreoPersona(Persona persona)
            throws IVROServiceException {
        TipoMedioContacto tipoMedioContacto = null;
        try {
            String xmlEntrada = generarXMLValidacionCorreoElectronico(persona
                    .getIdPersona());
            LOGGER.info("========== OBJETO DE ENTRADA: " + xmlEntrada);
            tipoMedioContacto = realizaConsulta(xmlEntrada,
                    TipoMedioContacto.class, webServiceValidaCorreoPersona);
        } catch (Exception e) {
            LOGGER.error("+++ERROR: ",e);
            throw new IVROServiceException(
                    "Ocurri\u00F3 un error al intentar realizar la validaci\u00F3n del correo electronico.");
        }
        if (tipoMedioContacto != null) {
            LOGGER.info("Mensaje: " + tipoMedioContacto.getDescripcion());
            if (tipoMedioContacto.getIdTipoMedioContacto() == null
                    || tipoMedioContacto.getIdTipoMedioContacto() == 0) {
                validWSRespError(tipoMedioContacto.getDescripcion());
            }
        }
    }

    private String generarXMLValidacionCorreoElectronico(Long idPersona) {
        StringBuilder sbXmlValidacion = new StringBuilder();
        sbXmlValidacion
                .append("<mx:idPersona xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">")
                .append(idPersona).append("</mx:idPersona>");
        return sbXmlValidacion.toString();
    }

    /**
     * Valid ws resp error.
     *
     * @param error the error
     * @throws IVROServiceException the IVRO service exception
     */
    private void validWSRespError(String error) throws IVROServiceException {
        if (error != null) {
            if (error.length() > 0 && !error.equals("")) {
                throw new IVROServiceException(error);
            }
        }

    }

    /**
     * Cancela Beneficio Riss
     *
     * @param seguro
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     * @Return
     */
    @Override
    public void cancelaBeneficioRiss(SeguroIvro seguro)
            throws IVROServiceException {
        TramiteSeguroIvro tramite = consultaSeguroIvroService.buscaTramiteSeguroIndividual(seguro);
        SimpleDateFormat formato = new SimpleDateFormat(FORMAT_DATE_GUINMEDIO_DD_MM_YYYY);
        LOGGER.info("RFC: " + seguro.getTitular().getRfc());
        LOGGER.info("NRP: " + tramite.getNrpFisica());
        LOGGER.info("NSS: " + seguro.getTitular().getNss());
        LOGGER.info("Fecha:" + formato.format(new Date()));
        LOGGER.info("motivo:" + MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS.getClave());
        RespuestaCancelacionBeneficio response = cancelarBeneficioServiceBusiness
                .cancelarBeneficio(seguro.getTitular().getRfc(),
                        tramite.getNrpFisica(),
                        seguro.getTitular().getNss(),
                        formato.format(new Date()),
                        MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS.getClave());
        LOGGER.info("Respuesta cancelacion:   " + response.getDescripcion());
        LOGGER.info("Error:   " + response.getClaveError());
        LOGGER.info("Exito:   " + response.getExito());
    }

    /**
     * Cancela Beneficio Riss
     *
     * @param seguro
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     * @Return
     */
    @Override
    public void cancelaBeneficioRiss(SeguroIvro seguro, MotivoCancelacionBeneficioEnum motivoCancelacionBeneficioEnum)
            throws IVROServiceException {

        if (motivoCancelacionBeneficioEnum == null) {
            motivoCancelacionBeneficioEnum = MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS;
        }
        TramiteSeguroIvro tramite = consultaSeguroIvroService.buscaTramiteSeguroIndividual(seguro);
        SimpleDateFormat formato = new SimpleDateFormat(FORMAT_DATE_GUINMEDIO_DD_MM_YYYY);
        LOGGER.info("RFC: " + seguro.getTitular().getRfc());
        LOGGER.info("NRP: " + tramite.getNrpFisica());
        LOGGER.info("NSS: " + seguro.getTitular().getNss());
        LOGGER.info("Fecha:" + formato.format(new Date()));
        LOGGER.info("motivo:" + motivoCancelacionBeneficioEnum.getDesc());
        RespuestaCancelacionBeneficio response = cancelarBeneficioServiceBusiness
                .cancelarBeneficio(seguro.getTitular().getRfc(),
                        tramite.getNrpFisica(),
                        seguro.getTitular().getNss(),
                        formato.format(new Date()),
                        motivoCancelacionBeneficioEnum.getClave());
        LOGGER.info("Respuesta cancelacion:   " + response.getDescripcion());
        LOGGER.info("Error:   " + response.getClaveError());
        LOGGER.info("Exito:   " + response.getExito());
    }

    @Override
    public void cancelaBeneficioRiss(mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica persona, MotivoCancelacionBeneficioEnum motivoCancelacionBeneficioEnum)
            throws IVROServiceException {

        if (motivoCancelacionBeneficioEnum == null) {
            motivoCancelacionBeneficioEnum = MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS;
        }

        SimpleDateFormat formato = new SimpleDateFormat(FORMAT_DATE_GUINMEDIO_DD_MM_YYYY);
        LOGGER.info("RFC: " + persona.getRfc());
        LOGGER.info("NRP: " + null);
        LOGGER.info("NSS: " + persona.getNss());
        LOGGER.info("Fecha:" + formato.format(new Date()));
        LOGGER.info("motivo:" + motivoCancelacionBeneficioEnum.getDesc());
        RespuestaCancelacionBeneficio response = cancelarBeneficioServiceBusiness
                .cancelarBeneficio(persona.getRfc(),
                        null,
                        persona.getNss(),
                        formato.format(new Date()),
                        motivoCancelacionBeneficioEnum.getClave());
        LOGGER.info("Respuesta cancelacion:   " + response.getDescripcion());
        LOGGER.info("Error:   " + response.getClaveError());
        LOGGER.info("Exito:   " + response.getExito());
    }

    /**
     * @param idPersona
     * @return
     * @throws NoResultException
     * @throws Exception
     */
    @Override
    public AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException, Exception {
        AsignacionNssIvro ditAsignacionNss = vigenciaIvroServiceRemote.obtenerAsignacionNss(idPersona);
        return ditAsignacionNss;
    }

    /**
     * @param numNss
     * @return
     * @throws NoResultException
     * @throws Exception
     */
    @Override
    public AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException, Exception {
        AsignacionNssIvro ditAsignacionNss = vigenciaIvroServiceRemote.obtenerAsignacionNss(numNss);
        return ditAsignacionNss;
    }

    /**
     * @param domicilio
     * @param idPersona
     * @throws DomicilioNoValidoException
     * @throws mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException
     */
    @Override
    public void guardarYAsociarDomiciliosPersona(Domicilio domicilio, Long idPersona) throws DomicilioNoValidoException, DomicilioNoLocalizadoException {
        try {
            consultaSeguroIvroService.guardarYAsociarDomiciliosPersona(domicilio, idPersona);
        } catch (UndeclaredThrowableException e) {
            LOGGER.error("Incopatibilidad entre el Back y el Front: ", e);
        }
    }

    @Override
    public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
        return vigenciaIvroServiceRemote.validaVigenciaSeguroFamiliarRenovacion(vigenciaSeguroFamiliar);
    }

//    public RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(VigenciaTrabajdor vigenciaTrabajdor){
//    	return vigenciaIvroServiceRemote.validaVigenciaTrabajadorDomesticoRenovacion(vigenciaTrabajdor);
//    }

    public void validarBeneficiariosNss(Map<String, Object> result, Long idAmbiente, SeguroIvro seguroSeleccionado) {
        if (seguroSeleccionado != null && seguroSeleccionado.getTramite() != null && seguroSeleccionado.getTramite().getBeneficiarios() != null) {
            for (Fisica beneficiario : seguroSeleccionado.getTramite().getBeneficiarios()) {
                if (beneficiario != null && beneficiario.getNss() != null) {
                    GrupoFamiliar grupoFam = null;
                    try {
                        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(idAmbiente);
                        grupoFam = grupoFamiliarService.getGrupoFamiliar(beneficiario.getNss(), false);
                        Persona persona = obtenerPersonaDeGrupoFamiliar(grupoFam);
                        DatosCalculoCuota dcc = obtenDatosCotizacion(persona, origen, null);
                        validarBeneficiariosNss(result, idAmbiente, grupoFam, dcc);
                    } catch (DerechohabientesBusinessException e) {
                        LOGGER.error("Error derechhabiente al consultar nss.", e);
                    } catch (Exception ex) {
                        LOGGER.error("Exception general atrapada.", ex);
                    }
                }
            }
        }
    }

    private Persona obtenerPersonaDeGrupoFamiliar(GrupoFamiliar grupoFam) {
        Persona persona = new Persona();
        persona.setIdPersona(grupoFam.getAsignacionNSS().getIdPersona());
        persona.setRfc(grupoFam.getAsignacionNSS().getRfc());
        persona.setTipoPersona(new TipoPersona());
        persona.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
        return persona;
    }

    public void validarBeneficiariosNss(Map<String, Object> result, Long idAmbiente, GrupoFamiliar grupoFam, DatosCalculoCuota dcc) {
        if (grupoFam != null && grupoFam.getAsignacionNSS() != null) {

            if (!seguroIvroRenovacionUtil.validaVigenciaNss(dcc)) ;
            {

                result.put("error", Boolean.TRUE);
                result.put("msgError", MSG_EXCEPTION_NSS_NO_VIGENTE);
            }
        } else {
            result.put("error", Boolean.TRUE);
            result.put("msgError", MSG_EXCEPTION_NSS_NO_VIGENTE);
        }
    }

    public SeguroIvroRenovacionUtil getSeguroIvroRenovacionUtil() {
        return seguroIvroRenovacionUtil;
    }

    public void setSeguroIvroRenovacionUtil(
            SeguroIvroRenovacionUtil seguroIvroRenovacionUtil) {
        this.seguroIvroRenovacionUtil = seguroIvroRenovacionUtil;
    }

    /**
     * Obtiene el domicilio de la
     *
     * @param persona
     * @return
     */
    @Override
    public Domicilio getDomicilioPersona(Persona persona) {
        Domicilio domicilio = new Domicilio();
        try {
            String personaXml = JaxbUtil.marshaller(persona);
            domicilio = realizaConsulta(personaXml, Domicilio.class, webServiceDomicilio);
            LOGGER.info("Parseo Objeto " + ReflectionToStringBuilder.toString(domicilio));
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }
        return domicilio;
    }

    @Override
    public void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]> adjuntos) {
        notificacionSegurosRemote.enviaCorreo(seguro, correos, tipo, adjuntos);
    }

    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(
            VigenciaTrabajdor vigenciaTrabajdor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String obtenZonaSalarialOriginal(String cveEnt, String cveMun) {

        StringBuffer q = new StringBuffer();
        q.append("SELECT DISTINCT DECODE(AG.CVE_ID_AREA_GEOGRAFICA,1,'A',2,'B',3,'C',4,'D','I')AREA_GEOGRAFICA ")
                .append("FROM DIT_MUNICIPIO_IMSS_INEGI   MII ")
                .append("INNER JOIN DIC_MUNICIPIO_IMSS   MI ON MII.CVE_ID_MUNICIPIO_IMSS = MI.CVE_ID_MUNICIPIO_IMSS ")
                .append("INNER JOIN DIC_AREA_GEOGRAFICA  AG ON MI.CVE_ID_AREA_GEOGRAFICA = AG.CVE_ID_AREA_GEOGRAFICA ")
                .append("WHERE MII.CVE_ENT = :cveEnt ")
                .append("AND MII.CVE_MUN = :cveMun ");
        try {

            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("cveEnt", cveEnt);
            query.setParameter("cveMun", cveMun);
            List zonaSalList = query.getResultList();

            Object resultado = (Object) zonaSalList.get(0);
            if (resultado instanceof String) {
                LOGGER.info("El resultado es " + resultado);
                return (String) resultado;
            } else {
                LOGGER.info("El resultado no es String");
            }
        } catch (NoResultException e) {
            LOGGER.error("No se encontro Zona Salarial para el cveEnt " + cveEnt + " cveMun " + cveMun);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de una Zona Salarial para el cveEnt " + cveEnt + " cveMun " + cveMun);
        } catch (Exception e) {
            LOGGER.error("ERROR al ejecutar el QUERY: ", e);
        }

        return null;
    }

    private String obtenerFechaUltimoPeriodoPagado(SeguroIvro seguro){

        try {
            Compra compra = seguro.getCompra();
            Pago[] pagos = compra.getPagos();
            List<Pago> listPagos = Arrays.asList(pagos);

            LOGGER.info("La lista de pagos contiene: "+listPagos.size());
            listPagos = ordenarListaPagos(listPagos);

            SimpleDateFormat formato = new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD);

            String respuesta = null;
            Boolean hayPagosVencidos = Boolean.FALSE;

            for (int i = 0; i < listPagos.size(); i++) {
                Pago actual = listPagos.get(i);
                LOGGER.info("Actual Fechas del periodo | periodo num:" +i+ "\n inicio: "+actual.getFechaInicioPeriodo()
                        +" \n fin: "+actual.getFechaFinPeriodo()
                + "\n Estado del Pago: " + actual.getEstadoPago().getIdEstadoPago());

                if (actual.getEstadoPago().getIdEstadoPago() == ESTADO_SEGURO_VENCIDO ){
                    LOGGER.info("Entra a Periodo vencido:");
                    Date fechaInicioPeriodo = actual.getFechaInicioPeriodo();
                    hayPagosVencidos = Boolean.TRUE;
                    respuesta = formato.format(fechaInicioPeriodo);
                } else if (!hayPagosVencidos && i == listPagos.size()-1 && actual.getEstadoPago().getIdEstadoPago() == ESTADO_SEGURO_PAGADO){
                    LOGGER.info("Entra a Periodo pagado:");
                    Date fechaFinSeguro = seguro.getFechaFin();
                    respuesta = formato.format(this.sumarDiasAFecha(fechaFinSeguro,1));
                }
            }
            LOGGER.info("El último pago fue: "+respuesta);
            return respuesta;

        }catch (Exception e){
            LOGGER.error("Ocurrio un error al valorar los pagos: ",e);
            return null;
        }

    }

    private static Date sumarDiasAFecha(Date fecha, int dias){
        if (dias==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    private List<Pago> ordenarListaPagos(List<Pago> pagosSeguroIvro) {
        Collections.sort(pagosSeguroIvro, new Comparator<Pago>() {
            @Override
            public int compare(Pago pago2, Pago pago1) {
                return pago1.getFechaLimitePago().compareTo(
                        pago2.getFechaLimitePago());
            }
        });

        return pagosSeguroIvro;
    }

    /**
     * Se valida si el ultimo seguro anterior esta en estado concluido o vencido para quitar beneficio RISS
     *
     * @param idPersona
     * @return
     */
    public boolean validaSeguroAnteriorVencidoCancelado(Long idPersona) {
        return consultaSeguroIvroService.validaSeguroAnteriorVencidoCancelado(idPersona);
    }

    public boolean insertaCancelacionCuestionario(Beneficiario beneficiarioCancelar, String numSolicitud) throws IVROServiceException {
        try {
            return seguroIvroServiceRemote.insertaCancelacionCuestionario(beneficiarioCancelar, numSolicitud);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al insertar la cancelacion: ", e);
            throw new IVROServiceException(e.getMessage());
        }
    }

    public Long recuperaIdAsignacionPorNSS(String nss) throws IVROServiceException {
        try {
            return seguroIvroServiceRemote.recuperaIdAsignacionPorNSS(nss);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al recuperar el IdAsignacion: ", e);
            throw new IVROServiceException(e.getMessage(), e.getCause());
        }
    }

    public boolean verificaBeneficiarioConEnfermedad(Long idAsignacion) throws IVROServiceException {
        try {
            return seguroIvroServiceRemote.verificaBeneficiarioConEnfermedad(idAsignacion);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al recuperar el IdAsignacion: ", e);
            throw new IVROServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Se valida un rango de tiempo para indicar si tiene pagos al corriente
     * por Ventanilla desde el ultimo pago de un seguro a la fecha
     * @param idPersona
    //     * @param nss
     * @param seguro
     * @return
     */
    public RespuestaPagosVentanilla validaPagosPorFechas(Long idPersona, SeguroIvro seguro)throws IVROServiceException{

        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        String nss;
        RespuestaPagosVentanilla respuestaPagosVentanilla = null;

        try {
            nss = personaBusinessRemote.obtenerNssPersona(idPersona);
        }catch (PersonaSinNSSException psn) {
            LOGGER.error("Error al obtener el Id Persona: ", psn);
            nss = "0";
            respuestaPagosVentanilla.setCodigoError("02");
            respuestaPagosVentanilla.setMensajeError(psn.getMessage());
        } catch (PersonaConVariosNSSException psn) {
            LOGGER.error("Error al obtener el Id Persona: ", psn);
            nss = "0";
            respuestaPagosVentanilla.setCodigoError("02");
            respuestaPagosVentanilla.setMensajeError(psn.getMessage());
        }

        try {

            seguro = getDetalleSeguro(seguro.getCveIdSeguroIvro());
            String fechaUltimoPeriodo = obtenerFechaUltimoPeriodoPagado(seguro);
            LOGGER.info("Fecha ultimoPeriodo: "+fechaUltimoPeriodo);

            String fechaFinPeriodoActual = SeguroIvroUtil.obtenerFechaFinPeriodo(Calendar.getInstance());

            ClienteWebserviceValidaPagosVentanilla cliente = new ClienteWebserviceValidaPagosVentanilla();

            respuestaPagosVentanilla =
                    cliente.validaPagosPorFechas(nss,fechaUltimoPeriodo,fechaFinPeriodoActual);

            LOGGER.info("Código de error que regresa validaPagoPorFechasWS: "+respuestaPagosVentanilla.getCodigoError());

            if (respuestaPagosVentanilla.getCodigoError().equals(CODIGO_WS_EXITO)) {
                if (respuestaPagosVentanilla.getIndPagosCompletos()!= null && respuestaPagosVentanilla.getIndPagosCompletos().equals("0")) {

                    LOGGER.info("El WS registra falta de pago en ventanilla");
                    LOGGER.info("Llamada al WS de pagos correcta: Se tienen todos los pagos de las fechas solicitadas");
                } else {

                    fechaFinPeriodoActual = SeguroIvroUtil.obtenerFechaFinPeriodo(Calendar.getInstance());
                    Integer mesPeriodo = Integer.valueOf(fechaFinPeriodoActual.substring(4,6));

                    Calendar fechaNuevaInicio = Calendar.getInstance();
                    fechaNuevaInicio.set(Calendar.MONTH,mesPeriodo);
                    fechaNuevaInicio.set(Calendar.DAY_OF_MONTH,1);
                    SimpleDateFormat formato = new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD);
                    String fechaInicio = formato.format(fechaNuevaInicio.getTime());

                    Calendar fechaNuevaFin = Calendar.getInstance();
                    fechaNuevaFin.set(Calendar.MONTH,mesPeriodo);
                    fechaNuevaFin.set(Calendar.DAY_OF_MONTH,1);
                    fechaNuevaFin.add(Calendar.MONTH,1);
                    fechaNuevaFin.add(Calendar.DATE,-1);
                    formato = new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD);
                    String fechaFin = formato.format(fechaNuevaFin.getTime());

                    RespuestaPagosVentanilla pagoAnticipadoVentanilla = cliente.validaPagosPorFechas(nss,fechaInicio,fechaFin);
                    respuestaPagosVentanilla.setCodigoError(pagoAnticipadoVentanilla.getIndPagosCompletos().equals("1")?"4":"1");
                }

            } else if (respuestaPagosVentanilla.getCodigoError().equals(CODIGO_WS_ERROR)){

                throw new ClienteWebserviceValidaPagosException(respuestaPagosVentanilla.getMensajeError(),
                        Integer.parseInt(respuestaPagosVentanilla.getCodigoError()));

            } else {
                LOGGER.error(" un error ocurrio: " + respuestaPagosVentanilla.getMensajeError());
                respuestaPagosVentanilla.setCodigoError(CODIGO_WS_NO_DISPONIBLE);
                throw new ClienteWebserviceValidaPagosException(respuestaPagosVentanilla.getMensajeError(),
                        Integer.parseInt(respuestaPagosVentanilla.getCodigoError()));
            }
        } catch (ClienteWebserviceValidaPagosException cve){
            LOGGER.error("Ocurrio un error ejecutar el WS para consultar el ultimo periodo: ",cve);
            throw new IVROServiceException(cve.getMessage(),cve.getCause());
        } catch (Exception e){
            LOGGER.error("Ocurrio un error ejecutar el WS para consultar el ultimo periodo: ",e);
            throw new IVROServiceException(e.getMessage(),e.getCause());
        }

        return respuestaPagosVentanilla;

    }

    /**
     * Se valida un rango de tiempo para indicar si a un periodo en especifico se le realizo su pago por ventanilla
     * @param idPersona
     * @param nss
     * @param periodo
     * @return
     */
    public RespuestaPagosVentanilla validaPagoDePeriodo(Long idPersona, String nss, String periodo, String numModalidad)throws IVROServiceException{
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);

        RespuestaPagosVentanilla respuestaPagosVentanilla = null;

        try {

            /*String numeroRegistroPatronal = registroPatronalServiceBusinessRemote.
                    obtenerNrpConvencionalPorDomicilioParticularYModalidad(idPersona, numModalidad);*/

            ClienteWebserviceValidaPagosVentanilla cliente = new ClienteWebserviceValidaPagosVentanilla();

            respuestaPagosVentanilla =
                    cliente.validaPagosPorPeriodo(nss,periodo);

            if (respuestaPagosVentanilla.getCodigoError().equals(CODIGO_WS_EXITO)) {
                if (respuestaPagosVentanilla.getIndPagosCompletos().equals("1")) {
                    LOGGER.info("Llamada al WS de pagos correcta: Se tienen todos los pagos de las fechas solicitadas");

                } else {
                    String mensajeError = "Para verificar la continuidad en el beneficio al R&eacute;gimen de Incorporaci&oacute;n a la Seguridad Social (RISS) se agradecer&aacute; acuda a la Subdelegaci&oacute;n correspondiente con todos sus antecedentes de pago, seleccione la opci&oacute;n &lt;Cancelar&gt;. En caso de desear continuar la solicitud sin beneficio RISS seleccione la opci&oacute;n &lt;Siguiente&gt;.";
                    Integer codigoError = 05;
                    throw new ClienteWebserviceValidaPagosException(mensajeError,codigoError);
                }
            } else {

                LOGGER.error("El WS mando error, codigo: "+respuestaPagosVentanilla.getCodigoError()
                        +" mensaje: "+respuestaPagosVentanilla.getMensajeError());
                String mensajeError = "";
                Integer codigoError = Integer.parseInt(respuestaPagosVentanilla.getCodigoError());

                throw new ClienteWebserviceValidaPagosException(mensajeError,codigoError);
            }

        } catch (ClienteWebserviceValidaPagosException cve){
            LOGGER.error("Ocurrio un error al obtener el NRP: ",cve);
        }

        return respuestaPagosVentanilla;
    }

}
