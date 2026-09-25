package mx.gob.imss.cit.cda.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.MotivoAclaracionVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Institucion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.InstitucionEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RegistroCorreccionCurpUtil {

    /**
     * logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RegistroCorreccionCurpUtil.class);

    private static final String TITULO_FORMULARIO_SOLICITUD = "Solicitud de Regularizaci\u00F3n y/o Correcci\u00F3n de Datos Personales del Asegurado";
    private static final String HOMOCLAVE_TRAMITE = "IMSS-02-012";
    private static final String TITULO_TRAMITE = "Comprobante de solicitud de regularizaci\u00F3n y/o correcci\u00F3n de datos personales del asegurado";

    @Autowired
    @Qualifier("flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;

    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

    @Autowired
    private PersonaBusinessRemote personaBusiness;

    public Map<String, Object> obtenerParametros(Solicitud solicitud) {
        LOGGER.debug("---CDA--- Genero reporte de Solicitud con ID: {}",
                solicitud.getSolicitudId());

        TramiteCorreccionCurp tramiteCorreccionCurp = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);

        Map<String, Object> parametros1 = new HashMap<String, Object>();
        DeltaUtils deltaUtils = new DeltaUtils();

        parametros1.put("titulo", TITULO_TRAMITE);
        parametros1.put("folioSolicitud", solicitud.getNoFolioSolicitud());

        try {
            parametros1.put("fechaSolicitud", deltaUtils.cambiarFormatoFecha(
                    "dd/MM/yyyy",
                    flujoTrabajoBusiness
                            .getTareaActivaPorIdTramite(
                                    tramiteCorreccionCurp.getTramiteId())
                            .getInicioTramite().getFechaSolicitud(),
                    "dd / MM / yyyy"));
            parametros1.put("fechaHoraRecepcion", deltaUtils
                    .cambiarFormatoFecha(
                            "dd/MM/yyyy",
                            flujoTrabajoBusiness
                                    .getTareaActivaPorIdTramite(
                                            tramiteCorreccionCurp
                                                    .getTramiteId())
                                    .getInicioTramite().getFechaSolicitud(),
                            "dd / MM / yyyy"));
        } catch (ParseException e1) {
            LOGGER.error("---CDA--- Error Fecha Solicitud. ", e1);
        }

        parametros1.put("cadenaOriginal", solicitud.getFirmaElectronica()
                .getCadenaOriginal());
        parametros1.put("secuenciaNotarial", solicitud.getFirmaElectronica()
                .getSecuenciaNotaria());
        parametros1.put("selloDigital", solicitud.getFirmaElectronica()
                .getRecibo());
        parametros1.put("numeroSerie", solicitud.getFirmaElectronica()
                .getSerialCertificado());

        return parametros1;
    }
    
    private void obtenerParametrosComplementoStep01(
      Map<String, Object> parametros2, Fisica personaRegistro,
      TramiteCorreccionCurp tramiteCDA ){
      parametros2.put("CURP",
              personaRegistro.getCurp() != null ?
              personaRegistro.getCurp() :
              " ");
      parametros2.put(
              "NOMBRE",
              personaRegistro.getNombre() != null ?
              personaRegistro
                      .getNombre() :
              "");
      parametros2.put(
              "PRIMER_APELLIDO",
              personaRegistro.getPrimerApellido() != null ?
              personaRegistro
                      .getPrimerApellido() :
              "");
      parametros2.put(
              "SEGUNDO_APELLIDO",
              personaRegistro.getSegundoApellido() != null ?
              personaRegistro
                      .getSegundoApellido() :
              " ");

      
      parametros2.put(
              "OBSERVACIONES",
              tramiteCDA.getObservacion() != null ?
              tramiteCDA
                      .getObservacion() :
              "");

      parametros2
              .put("DIA_NACIMIENTO",
                      personaRegistro.getFechaNacimientoFormateada() != null ?
                      personaRegistro
                              .getFechaNacimientoFormateada() :
                      "");
    
    }
    
    private void obtenerParametrosComplementoStep02(Map<String, Object> parametros2,
      Fisica personaRegistro){
      
      parametros2.put("NACIMIENTO_LOCALIDAD", personaRegistro
              .getLugarNacimiento() != null ?
                      personaRegistro
                              .getLugarNacimiento().getNombre() :
                      " ");
      parametros2.put("NACIMIENTO_PAIS",
              personaRegistro.getPais() != null ?
              personaRegistro.getPais()
                      .getNacionalidad() :
              " ");

      parametros2.put("SEXO",
              personaRegistro.getSexo() != null ?
              personaRegistro.getSexo()
                      .getDescripcion() :
              "");

      parametros2.put("RFC",
              personaRegistro.getRfc() != null ?
              personaRegistro.getRfc() :
              "");
    }

    public Map<String, Object> obtenerParametrosComplemento(
            Solicitud solicitud, DatosHistoriaLaboralVO histLaboral,
            DomicilioCorto motivosAclaracion) {
        Map<String, Object> parametros2 = new HashMap<String, Object>();

        TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0));

        LOGGER.debug("El contenido del tramiteCDA es  {}", tramiteCDA);
        Fisica personaRegistro = tramiteCDA.getPersonaRENAPO();
        DeltaUtils deltaUtils = new DeltaUtils();
        List<String> listaNss = new ArrayList<String>();
        if(tramiteCDA.getListaNssCorreccion()!=null && !tramiteCDA.getListaNssCorreccion().isEmpty()){
            for (Iterator<CorreccionNSS> iterator = tramiteCDA.getListaNssCorreccion().iterator(); iterator.hasNext();) {
                CorreccionNSS correccionNSS = (CorreccionNSS)iterator.next();
                listaNss.add(correccionNSS.getNss());
            }
        }

        parametros2.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
        parametros2.put("titulo", TITULO_FORMULARIO_SOLICITUD);
        parametros2.put("homoclaveTramite", HOMOCLAVE_TRAMITE);
        parametros2.put("folio", solicitud.getNoFolioSolicitud());

        try {
            parametros2.put("fechaSolicitudTramite", deltaUtils
                    .cambiarFormatoFecha(
                            "dd/MM/yyyy",
                            flujoTrabajoBusiness
                                    .getTareaActivaPorIdTramite(
                                            tramiteCDA.getTramiteId())
                                    .getInicioTramite().getFechaSolicitud(),
                            "dd / MM / yyyy"));
        } catch (ParseException e1) {

            LOGGER.error("---CDA--- Error Fecha Solicitud. ", e1);
        }

        obtenerParametrosComplementoStep01(parametros2, personaRegistro,
                tramiteCDA);
        obtenerParametrosComplementoStep02(parametros2, personaRegistro);
        parametros2.put("NSS", listaNss);
        parametros2.put("title", TITULO_FORMULARIO_SOLICITUD);

        

        parametros2.putAll(obtenerParametrosActaNacimiento(personaRegistro));

        parametros2.put("CADENA_ORIGINAL", solicitud.getFirmaElectronica()
                .getCadenaOriginal());
        parametros2.put("SECUENCIA_NOTARIAL", solicitud.getFirmaElectronica()
                .getSecuenciaNotaria());
        parametros2.put("SELLO_DIGITAL", solicitud.getFirmaElectronica()
                .getRecibo());
        parametros2.put("NUM_SERIE", solicitud.getFirmaElectronica()
                .getSerialCertificado());

        

        parametros2.putAll(obtenerParametrosDatosContacto(personaRegistro));

        parametros2.putAll(obtenerParametrosDomicilio(personaRegistro
                .getDomicilios()));
//        LOGGER.debug("--CDA-- DATOS LABORALES: {}",tramiteCDA.get);
        parametros2.putAll(obtenerParametrosCDAPersonas(tramiteCDA));

        parametros2.put("LISTA_HISTORIA_LAB", tramiteCDA.getDatosLaborales());
        LOGGER.debug("--CDA-- DATOS LABORALES: {}",
                tramiteCDA.getDatosLaborales());
        parametros2
                .put("DOC_PROB_LIST",
                        obtenerParametrosDocumentosProbatorios(obtenerDocumentacionProbatoriaGeneral(solicitud)));
        parametros2.putAll(obtenerParametrosMotivoAclaracion(motivosAclaracion
                .getMotivoAclaracionVO()));
        LOGGER.debug("----CDA------ MOTIVOS{}",
                obtenerParametrosMotivoAclaracion(motivosAclaracion
                        .getMotivoAclaracionVO()));
        LOGGER.debug("----CDA------ ORIGEN {}", solicitud.getOrigenSolicitud()
                .getIdTipoSolicitud());
        return parametros2;
    }

    private Map<String, Object> obtenerParametrosDatosContacto(
            Fisica personaRegistro) {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros
                .put("ASG_MAIL",
                        personaRegistro.getCorreoElectronico() != null ? personaRegistro
                                .getCorreoElectronico().getCorreo() : "");

        if (personaRegistro.getTelefonoFijo() != null) {
            parametros.put("ASG_TELEFONO_FIJO", personaRegistro
                    .getTelefonoFijo().getNumero());
        } else {
            parametros.put("ASG_TELEFONO_FIJO", " ");
        }

        if (personaRegistro.getTelefonoMovil() != null) {
            parametros.put("ASG_TELEFONO_MOVIL", personaRegistro
                    .getTelefonoMovil().getNumero());
        } else {
            parametros.put("ASG_TELEFONO_MOVIL", " ");
        }
        return parametros;
    }

    private Map<String, Object> obtenerParametrosDomicilio(
            List<Domicilio> domicilios) {
        Map<String, Object> parametros = new HashMap<String, Object>();

        if (domicilios.get(0).getNumInteriorAlf() != null) {
            parametros
                    .put("ASG_NUM_INT", domicilios.get(0).getNumInteriorAlf());
        } else {
            parametros.put("ASG_NUM_INT", " ");
        }

        if (domicilios.get(0).getAsentamiento() != null) {
            parametros.put("ASG_LOCALIDAD", domicilios.get(0).getAsentamiento()
                    .getLocalidad().getMunicipio().getNombre());
        } else {
            parametros.put("ASG_LOCALIDAD", " ");
        }

        parametros.put("ASG_COLONIA", domicilios.get(0).getAsentamiento()
                .getNombre());
        parametros.put("ASG_CODIGO_POSTAL", domicilios.get(0).getCodigoPostal()
                .getCodigoPostal());
        parametros.put("ASG_CALLE", domicilios.get(0).getCalle());
        parametros.put("ASG_NUM_EXT", domicilios.get(0).getNumExteriorAlf());

        if (domicilios.get(0).getAsentamiento() != null) {
            parametros.put("ASG_MUNICIPIO", domicilios.get(0).getAsentamiento()
                    .getLocalidad().getMunicipio().getNombre());
            parametros.put("DOM_ASG_ESTADO", domicilios.get(0)
                    .getAsentamiento().getLocalidad().getMunicipio()
                    .getEntidadFederativa().getNombre());
        } else {
            parametros.put("ASG_MUNICIPIO", " ");
            parametros.put("DOM_ASG_ESTADO", " ");
        }

        return parametros;
    }

    private Map<String, Object> obtenerParametrosMotivoAclaracion(
            MotivoAclaracionVO motivoAclaracionVO) {
        Map<String, Object> parametros = new HashMap<String, Object>();

        if (motivoAclaracionVO != null) {
            parametros.put("MOTIVOS_ACLARACION_IMSS",
                    obtenerParametrosMotivos(motivoAclaracionVO
                            .getMotivosAclaracionIMSS()));
            parametros.put("MOTIVOS_ACLARACION_INFONAVIT",
                    obtenerParametrosMotivos(motivoAclaracionVO
                            .getMotivosAclaracionInfonavit()));
            parametros.put("MOTIVOS_ACLARACION_AFORE",
                    obtenerParametrosMotivos(motivoAclaracionVO
                            .getMotivosAclaracionAfore()));
            parametros.put("CREDITO_DESCONTADO", motivoAclaracionVO
                    .getCreditoDescontado() != null ? motivoAclaracionVO
                    .getCreditoDescontado().toUpperCase() : "");
            parametros
                    .put("OTRO",
                            motivoAclaracionVO.getEspecificacion() != null ? motivoAclaracionVO
                                    .getEspecificacion().toUpperCase() : "");
        } else {
            parametros.put("MOTIVOS_ACLARACION_IMSS", new ArrayList<String>());
            parametros.put("MOTIVOS_ACLARACION_INFONAVIT",
                    new ArrayList<String>());
            parametros.put("MOTIVOS_ACLARACION_AFORE", new ArrayList<String>());
        }

        return parametros;
    }

    private Map<String, Object> obtenerParametrosActaNacimiento(
            Fisica personaRegistro) {
        Map<String, Object> parametros = new HashMap<String, Object>();

        if (personaRegistro.getActaNacimiento() != null) {
            parametros.put("MUNICIPIO", personaRegistro.getActaNacimiento()
                    .getMunicipio().getNombre());
            parametros.put("ENTIDAD_FEDERATIVA", personaRegistro
                    .getActaNacimiento().getMunicipio().getEntidadFederativa()
                    .getNombre());
            parametros.put("ANIO_REGISTRO", personaRegistro.getActaNacimiento()
                    .getAnio().toString());
            parametros.put("NUM_LIBRO", personaRegistro.getActaNacimiento()
                    .getNoLibro());
            parametros.put("NUM_ACTA", personaRegistro.getActaNacimiento()
                    .getNoActa());
            parametros.put("NUM_FOJA", personaRegistro.getActaNacimiento()
                    .getNoFoja());
            parametros.put("NUM_TOMO", personaRegistro.getActaNacimiento()
                    .getTomo());
            parametros.put("NUM_CRIP", personaRegistro.getActaNacimiento()
                    .getCrip());
        } else {
            parametros.put("MUNICIPIO", " ");
            parametros.put("ENTIDAD_FEDERATIVA", "");
            parametros.put("ANIO_REGISTRO", " ");
            parametros.put("NUM_LIBRO", " ");
            parametros.put("NUM_ACTA", " ");
            parametros.put("NUM_FOJA", " ");
            parametros.put("NUM_TOMO", " ");
            parametros.put("NUM_CRIP", " ");
        }

        return parametros;
    }

    private Map<String, Object> obtenerParametrosCDAPersonas(
            TramiteCorreccionCurp tramiteCDA) {
        Map<String, Object> parametros2 = new HashMap<String, Object>();
        LOGGER.debug("Obtiene los parametros de las persinas(beneficiario/representante legal)");
        if (tramiteCDA.getRepresentante() == null
                && tramiteCDA.getBeneficiario()== null) {
            parametros2.put("BNF_CURP", " ");
            parametros2.put("BNF_RFC", " ");
            parametros2.put("BNF_NOMBRES", " ");
            parametros2.put("BNF_PRIMER_APELLIDO", " ");
            parametros2.put("BNF_SEGUNDO_APELLIDO", " ");
            parametros2.put("BNF_TELEFONO_FIJO", " ");
            parametros2.put("BNF_TELEFONO_MOVIL", " ");
            parametros2.put("BNF_CORREO_ELECTRONICO", " ");
            parametros2.put("BNF_CODIGO_POSTAL", " ");
            parametros2.put("BNF_CALLE", " ");
            parametros2.put("BNF_NUM_EXTERIOR", " ");
            parametros2.put("BNF_NUM_INTERIOR", " ");
            parametros2.put("BNF_COLONIA", " ");
            parametros2.put("BNF_LOCALIDAD", " ");
            parametros2.put("BNF_MUNICIPIO", " ");
            parametros2.put("BNF_ESTADO", " ");
            parametros2.put("BNF_SEXO", " ");
            parametros2.put("BNF_SOLICITANTE", " ");
            parametros2.put("ASG_ESTADO", " ");
            parametros2.put("ASG_ESTADO", " ");
        }else if (tramiteCDA.getRepresentante() != null){
            parametros2=obtenerParametrosCDARepresentante(tramiteCDA);
        }else  if(tramiteCDA.getBeneficiario() != null){
            parametros2=obtenerParametrosCDABeneficiario(tramiteCDA);
        }

        return parametros2;
    }
    
    private void obtenerParametrosCDABeneficiarioStep01(Map<String, Object> parametros2,
      TramiteCorreccionCurp tramiteCDA      ){
      parametros2.put("BNF_CURP", tramiteCDA.getBeneficiario()
              .getCurp() != null ?
                      tramiteCDA.getBeneficiario()
                              .getCurp() :
                      "");
      parametros2
              .put("BNF_RFC",
                      tramiteCDA.getBeneficiario().getRfc() != null ?
                      tramiteCDA.getBeneficiario().getRfc() :
                      "");
      parametros2.put("BNF_NOMBRES", tramiteCDA.getBeneficiario()
              .getNombre() != null ?
                      tramiteCDA.getBeneficiario()
                              .getNombre() :
                      "");
      parametros2.put("BNF_PRIMER_APELLIDO", tramiteCDA.getBeneficiario().
              getPrimerApellido() != null ?
                      tramiteCDA.getBeneficiario().getPrimerApellido() :
                      "");
      parametros2.put("BNF_SEGUNDO_APELLIDO", tramiteCDA.getBeneficiario().
              getSegundoApellido() != null ?
                      tramiteCDA.getBeneficiario().getSegundoApellido() :
                      "");
      parametros2.put("BNF_TELEFONO_FIJO", tramiteCDA.getBeneficiario().
              getTelefonoFijo() != null ?
                      tramiteCDA.getBeneficiario().getTelefonoFijo().getNumero() :
                      "");

    }
    
    private Map<String, Object> obtenerParametrosCDABeneficiario(
            TramiteCorreccionCurp tramiteCDA) {
        Map<String, Object> parametros2 = new HashMap<String, Object>();
                LOGGER.debug("El contenido del BENEFICIARIO es  {}", tramiteCDA.getBeneficiario());
                obtenerParametrosCDABeneficiarioStep01(parametros2, tramiteCDA);
                parametros2
                        .put("BNF_TELEFONO_MOVIL", tramiteCDA.getBeneficiario()
                                .getTelefonoMovil() != null ? tramiteCDA.getBeneficiario().getTelefonoMovil()
                                .getNumero() : "");
                parametros2.put("BNF_CORREO_ELECTRONICO", tramiteCDA.getBeneficiario().getCorreoElectronico() != null ? tramiteCDA.getBeneficiario().getCorreoElectronico().getCorreo()
                        : "");
                parametros2.put("BNF_CODIGO_POSTAL", "");
                parametros2.put("BNF_CALLE", "");
                parametros2.put("BNF_NUM_EXTERIOR", "");
                parametros2.put("BNF_NUM_INTERIOR", "");
                parametros2.put("BNF_COLONIA", "");
                parametros2.put("BNF_LOCALIDAD", "");
                parametros2.put("BNF_MUNICIPIO", "");
                parametros2.put("BNF_ESTADO", "");
                if(tramiteCDA.getBeneficiario()
                        .getSexo() != null){
                    parametros2.put("BNF_SEXO",tramiteCDA.getBeneficiario().getSexo().getDescripcion());
                }else{
                    parametros2.put("BNF_SEXO","");
                }
                
                if(tramiteCDA.getBeneficiario()
                .getTipoBeneficiario() != null){
                    TipoSolicitanteEnum enumeracion=TipoSolicitanteEnum.getTipoSolicitudEnumById(tramiteCDA.getBeneficiario().getTipoBeneficiario());
//                    String solicitante = Integer.toString(enumeracion.getId());
                    parametros2.put("BNF_SOLICITANTE",tramiteCDA.getBeneficiario().getTipoBeneficiario().toString());
                    LOGGER.info("-----CDA-----Beneficiario--PDF" + enumeracion.getId());
                    LOGGER.info("-----CDA-----Beneficiario--PDF" + parametros2.toString() );
                }else{
                    parametros2.put("BNF_SOLICITANTE", "");
                }
        return parametros2;
    }
    
    private void obtenerParametrosCDARepresentanteStep01(
      Map<String, Object> parametros2, TramiteCorreccionCurp tramiteCDA ){
      
      parametros2.put("BNF_CURP", tramiteCDA.getRepresentante()
              .getCurp() != null ?
                      tramiteCDA.getRepresentante()
                              .getCurp() :
                      "");
      parametros2
              .put("BNF_RFC",
                      tramiteCDA.getRepresentante().getRfc() != null ?
                      tramiteCDA.getRepresentante().getRfc() :
                      "");
      parametros2.put("BNF_NOMBRES", tramiteCDA.getRepresentante()
              .getNombre() != null ?
                      tramiteCDA.getRepresentante()
                              .getNombre() :
                      "");
      parametros2.put("BNF_PRIMER_APELLIDO", tramiteCDA.getRepresentante().
              getPrimerApellido() != null ?
                      tramiteCDA.getRepresentante().getPrimerApellido() :
                      "");
      parametros2.put("BNF_SEGUNDO_APELLIDO", tramiteCDA.getRepresentante().
              getSegundoApellido() != null ?
                      tramiteCDA.getRepresentante().getSegundoApellido() :
                      "");
      parametros2.put("BNF_TELEFONO_FIJO", tramiteCDA.getRepresentante().
              getTelefonoFijo() != null ?
                      tramiteCDA.getRepresentante().getTelefonoFijo().
                              getNumero() :
                      "");
    }
    
    private Map<String, Object> obtenerParametrosCDARepresentante(
            TramiteCorreccionCurp tramiteCDA) {
        Map<String, Object> parametros2 = new HashMap<String, Object>();
            LOGGER.debug("El contenido del getRepresentante es  {}", tramiteCDA.getRepresentante());
            obtenerParametrosCDARepresentanteStep01(parametros2, tramiteCDA);
            parametros2
                    .put("BNF_TELEFONO_MOVIL", tramiteCDA.getRepresentante()
                            .getTelefonoMovil() != null ? tramiteCDA.getRepresentante().getTelefonoMovil()
                            .getNumero() : "");
            parametros2.put("BNF_CORREO_ELECTRONICO", tramiteCDA.getRepresentante().getCorreoElectronico() != null ? tramiteCDA.getRepresentante().getCorreoElectronico().getCorreo()
                    : "");
            parametros2.put("BNF_CODIGO_POSTAL", "");
            parametros2.put("BNF_CALLE", "");
            parametros2.put("BNF_NUM_EXTERIOR", "");
            parametros2.put("BNF_NUM_INTERIOR", "");
            parametros2.put("BNF_COLONIA", "");
            parametros2.put("BNF_LOCALIDAD", "");
            parametros2.put("BNF_MUNICIPIO", "");
            parametros2.put("BNF_ESTADO", "");
            parametros2.put("BNF_SEXO", tramiteCDA.getRepresentante()
                        .getSexo() != null ? tramiteCDA.getRepresentante().getSexo().getDescripcion() : "");
            parametros2.put("BNF_SOLICITANTE", String.valueOf(TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getId()));
            
        return parametros2;
    }
    
   

    private List<String> obtenerParametrosMotivos(List<String> motivosAclaracion) {
        List<String> motivos = new ArrayList<String>();

        if (motivosAclaracion != null && !motivosAclaracion.isEmpty()) {
            for (String motivoAclaracion : motivosAclaracion) {
                motivos.add(motivoAclaracion);
            }
        }
        return motivos;
    }

    
    private void obtenerDocumentacionProbatoriaGeneralStep01(
      TramiteCorreccionCurp tramitesCDA, List<DocumentoProbatorio> listaDocumentos ){
      if (tramitesCDA.getListaNssCorreccion() != null
                && !tramitesCDA.getListaNssCorreccion().isEmpty()) {
            for (CorreccionNSS correccionNSS : tramitesCDA
                    .getListaNssCorreccion()) {
                if (correccionNSS.getDocumentosProbatorios() != null
                        && !correccionNSS.getDocumentosProbatorios().isEmpty()) {
                    for (DocumentoProbatorio doc : correccionNSS.getDocumentosProbatorios()) {
                        listaDocumentos.add(doc);
                    }
                }
            }

        }
    }
    /**
     * Se obtienen los documentos de probatorios del asegurado, nss y del beneficiario/representante
     * @param solicitud
     * @return
     */
    private List<DocumentoProbatorio> obtenerDocumentacionProbatoriaGeneral(
            Solicitud solicitud) {
        List<DocumentoProbatorio> listaDocumentos = new ArrayList<DocumentoProbatorio>();

        TramiteCorreccionCurp tramitesCDA = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);
        LOGGER.debug("Se obtienen los documentos del beneficiario/representante");
        if (tramitesCDA.getBeneficiario() != null || tramitesCDA.getRepresentante() != null) {
            listaDocumentos = obtenerDocumentacionPersonas(tramitesCDA,
                    listaDocumentos);
        }
        LOGGER.debug("Se obtienen los documentos del asegurado");
        if (tramitesCDA.getAsegurado() != null
                && tramitesCDA.getAsegurado().getDocumentosProbatorios() != null
                && !tramitesCDA.getAsegurado().getDocumentosProbatorios()
                        .isEmpty()) {
            for (DocumentoProbatorio doc : tramitesCDA.getAsegurado().getDocumentosProbatorios()) {
                listaDocumentos.add(doc);
            }
        }
        LOGGER.debug("Se obtienen los documentos de los nss");
        obtenerDocumentacionProbatoriaGeneralStep01(tramitesCDA, listaDocumentos);
        return listaDocumentos;
    }

    /**
     * Obtiene los documentos del beneficiario/representante
     * @param tramitesCDA
     * @param listaDocumentos
     * @return
     */
    private List<DocumentoProbatorio> obtenerDocumentacionPersonas(
            TramiteCorreccionCurp tramitesCDA,
            List<DocumentoProbatorio> listaDocumentos) {
        if (tramitesCDA.getBeneficiario() != null
                && tramitesCDA.getBeneficiario().getDocumentosProbatorios() != null
                && !tramitesCDA.getBeneficiario().getDocumentosProbatorios().isEmpty()) {
                    for (DocumentoProbatorio doc : tramitesCDA.getBeneficiario().getDocumentosProbatorios()) {
                        listaDocumentos.add(doc);
                    }
        }else if (tramitesCDA.getRepresentante() != null
                && tramitesCDA.getRepresentante().getDocumentosProbatorios() != null
                && !tramitesCDA.getRepresentante().getDocumentosProbatorios().isEmpty()) {
            for (DocumentoProbatorio doc : tramitesCDA.getRepresentante().getDocumentosProbatorios()) {
                 listaDocumentos.add(doc);
            }
        }
        return listaDocumentos;
    }

    private List<String> obtenerParametrosDocumentosProbatorios(
            List<DocumentoProbatorio> documentosProbatorios) {
        List<String> listaDocumentosProbatorios = new ArrayList<String>();
        if (documentosProbatorios != null && !documentosProbatorios.isEmpty()) {
            for (DocumentoProbatorio doctoProbatorio : documentosProbatorios) {
                LOGGER.debug("---CDA--- clave documento reporte {}",
                        doctoProbatorio.getDocumentoPorTipo().getDocumento()
                                .getCveIdDocumento());
                listaDocumentosProbatorios.add(doctoProbatorio
                        .getDocumentoPorTipo().getDocumento()
                        .getCveIdDocumento().toString());
            }
        }
        return listaDocumentosProbatorios;
    }

    public List<DatosLaborales> cargarDatosLaborales(
            List<HistoriaLaboralVO> historiaLaboralGrid) {
        List<DatosLaborales> listDatosLaborales = new ArrayList<DatosLaborales>();
        if (historiaLaboralGrid != null && !historiaLaboralGrid.isEmpty()) {
            for (HistoriaLaboralVO h : historiaLaboralGrid) {
                listDatosLaborales.add(TransformerRegistroUtils.voToModelHistorialLaboral(h));
            }
        }
        return listDatosLaborales;
    }

    public List<MotivoAclaracion> cargarMotivosAclaracion(
            MotivoAclaracionVO motivos) {

        List<MotivoAclaracion> listMotivos = new ArrayList<MotivoAclaracion>();

        listMotivos.addAll(cargarMotivosAclaracion(
                motivos.getMotivosAclaracionIMSS(),
                TiposAclaracionEnum.Dependencia.IMSS, InstitucionEnum.IMSS));
        listMotivos.addAll(cargarMotivosAclaracion(
                motivos.getMotivosAclaracionInfonavit(),
                TiposAclaracionEnum.Dependencia.INFONAVIT,
                InstitucionEnum.INFONAVIT, motivos.getCreditoDescontado()));
        listMotivos.addAll(cargarMotivosAclaracion(
                motivos.getMotivosAclaracionAfore(),
                TiposAclaracionEnum.Dependencia.AFORE, InstitucionEnum.AFORE));
        listMotivos.addAll(cargarMotivosAclaracionOtro(motivos));

        return listMotivos;
    }

    private List<MotivoAclaracion> cargarMotivosAclaracion(
            List<String> motivos, Dependencia dependencia,
            InstitucionEnum institucionEnum) {
        return cargarMotivosAclaracion(motivos, dependencia, institucionEnum,
                null);
    }

    private List<MotivoAclaracion> cargarMotivosAclaracion(
            List<String> motivos, Dependencia dependencia,
            InstitucionEnum institucionEnum, String creditoDescontado) {
        List<MotivoAclaracion> listMotivos = new ArrayList<MotivoAclaracion>();
        if (motivos != null) {
            List<TiposAclaracionEnum> motivosIMSS = TiposAclaracionEnum
                    .obtenerAclaracionesPorClaves(dependencia, motivos);
            Institucion institucion = new Institucion();
            institucion.setIdInstitucion(Long.valueOf(institucionEnum.getId()).intValue());
            institucion.setNombreInstitucion(institucionEnum.toString());
            for (TiposAclaracionEnum aclaracion : motivosIMSS) {
                MotivoAclaracion motivo = new MotivoAclaracion();
                motivo.setIdMotivoAclaracion(Long.valueOf(aclaracion.getMotivoAclaracion().getId()).intValue());
                motivo.setInstitucion(institucion);
                motivo.setDescripcionMotivo(aclaracion.getMensaje());
                if (aclaracion.getMotivoAclaracion().getId() == MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO
                        .getId()) {
                    motivo.setDetalleAclaracion(creditoDescontado);
                }
                listMotivos.add(motivo);
            }
        }
        return listMotivos;
    }

    private List<MotivoAclaracion> cargarMotivosAclaracionOtro(
            MotivoAclaracionVO motivos) {
        List<MotivoAclaracion> listMotivos = new ArrayList<MotivoAclaracion>();
        if (motivos.getOtro() != null && motivos.getOtro() != "") {
            MotivoAclaracion motivo = new MotivoAclaracion();
            Institucion institucion = new Institucion();
            institucion.setIdInstitucion(Long.valueOf(InstitucionEnum.OTRO.getId()).intValue());
            institucion.setNombreInstitucion(InstitucionEnum.OTRO.toString());
            motivo.setIdMotivoAclaracion(Long.valueOf(MotivoAclaracionEnum.OTRO.getId()).intValue());
            motivo.setInstitucion(institucion);
            motivo.setDescripcionMotivo(InstitucionEnum.OTRO.toString());
            motivo.setDetalleAclaracion(motivos.getEspecificacion());
            listMotivos.add(motivo);
        }
        return listMotivos;
    }

    private void generarParametrosReporteStep01(TramiteCorreccionCurp tramite,
      Map<String, Object> parametros){
      CorreoElectronico correo = null;
        TelefonoFijo telefono = null;
      if (tramite.getPersona().getMediosContacto() != null
                && !tramite.getPersona().getMediosContacto().isEmpty()) {
            for (MedioContacto med : tramite.getPersona().getMediosContacto()) {
                if (med instanceof CorreoElectronico) {
                    correo = (CorreoElectronico) med;
                }

                if (med instanceof TelefonoFijo) {
                    telefono = (TelefonoFijo) med;
                }
            }
        }

        parametros.put(
                "TELEFONO_FIJO",
                telefono != null && telefono.getNumero() != null ? telefono
                        .getNumero() : "");
        parametros.put("MAIL", correo != null ? correo.getCorreo() : "");
    
    }
    
    public Map<String, Object> generarParametrosReporte(Solicitud solicitud,
            Map<String, String> participantes,
            Set<List<PeriodoMovimientoAfiliatorio>> cuentas) {

        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);

        Locale locMEX = new Locale("es", "MX");
        SimpleDateFormat sdf = new SimpleDateFormat("dd ' / ' MM ' / 'yyyy",
                locMEX);

        Map<String, Object> parametros = new HashMap<String, Object>();

        tramite.setPersona(tramite.getPersonaRENAPO());

        parametros.put("CADENA_ORIGINAL", solicitud.getFirmaElectronica()
                .getCadenaOriginal());
        parametros.put("SELLO_DIGITAL", solicitud.getFirmaElectronica()
                .getRecibo());
        parametros.put("SECUENCIA_NOTARIAL", solicitud.getFirmaElectronica()
                .getSecuenciaNotaria());
        parametros.put("NUMERO_SERIE", solicitud.getFirmaElectronica()
                .getSerialCertificado());
        parametros.put("FECHA_FORMATO_DOF", "31 / 07 /2015");
        parametros.put("FOLIO", solicitud.getNoFolioSolicitud());
        parametros.put("FECHA_EXPEDICION",
                sdf.format(solicitud.getFechaConclusion()));
        parametros.put("NSS", tramite.getPersona().getNss());
        parametros.put("CURP", tramite.getPersona().getCurp());
        parametros.put("NOMBRE", tramite.getPersona().getNombre());
        parametros.put("PRIMER_APELLIDO", tramite.getPersona()
                .getPrimerApellido());
        parametros.put("SEGUNDO_APELLIDO", tramite.getPersona()
                .getSegundoApellido());
        parametros.put("FECHA_NACIMIENTO", tramite.getPersona()
                .getFechaNacimientoFormateada());
        parametros.put("LUGAR_NACIMIENTO", tramite.getPersona()
                .getLugarNacimiento().getNombre());

        // medio de contacto
        

        generarParametrosReporteStep01(tramite, parametros);

        List<TipoRegularizacionNSS> listTipoRegulacion = obtenerClaveNSSReporte(
                tramite, tramite.getPersona().getNss());

        parametros.put("LISTA_TIPO_NSS", listTipoRegulacion);
        parametros.put("SEXO", tramite.getPersona().getSexo().getDescripcion()/*.getIdSexo()
                .toString()*/);
        parametros.put("RESPONSABLE", participantes
                .get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
        parametros.put("AUTORIZADOR", participantes
                .get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));

        Iterator<List<PeriodoMovimientoAfiliatorio>> perIterator = cuentas
                .iterator();
        if (perIterator.hasNext()) {
            parametros.put("LISTA_DATOS_LABORALES",
                    crearMovimientosAfiliatorios(perIterator.next()));
            LOGGER.debug("-------CDA------ cuenta{}",
                    parametros.get("LISTA_DATOS_LABORALES"));
        }

        if (perIterator.hasNext()) {
            parametros.put("LISTA_MOVIMIENTO_FINAL", perIterator.next());
            LOGGER.debug("-------CDA------ cuenta{}",
                    parametros.get("LISTA_MOVIMIENTO_FINAL"));
        }

        List<String> listMotivosAclaracion = new ArrayList<String>();
        for (MotivoAclaracion motivoAclaracion : tramite.getMotivosAclaracion()) {
            if (motivoAclaracion.toString().toUpperCase()
                    .contains("DESCUENTO INDEBIDO")
                    || motivoAclaracion.toString().toUpperCase()
                            .contains("OTRO")) {
                listMotivosAclaracion.add(motivoAclaracion
                        .getDescripcionMotivo()
                        + ": "
                        + motivoAclaracion.getDetalleAclaracion());
            }
        }
        parametros.put("MOTIVO_ACLARACION", listMotivosAclaracion.toString()
                .toUpperCase());

        parametros.put("ORIGEN", solicitud.getOrigenSolicitud()
                .getIdTipoSolicitud().toString());
        LOGGER.debug("VALOR DE ORIGEN: ", solicitud.getOrigenSolicitud()
                .getIdTipoSolicitud());

        return parametros;
    }

    private List<String> obtenerMotivosAclaracionReporte(
            List<MotivoAclaracion> motivosAclaracion) {
        List<String> listMotivosAclaracion = new ArrayList<String>();
        for (MotivoAclaracion motivoAclaracion : motivosAclaracion) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(motivoAclaracion.getDescripcionMotivo());
            if (motivoAclaracion.toString().toUpperCase()
                    .contains("DESCUENTO INDEBIDO")
                    || motivoAclaracion.toString().toUpperCase()
                            .contains("OTRO")) {
                stringBuffer.append(": ");
                stringBuffer.append(motivoAclaracion.getDetalleAclaracion());
            }
            listMotivosAclaracion.add(stringBuffer.toString());
        }

        return listMotivosAclaracion;
    }

    private List<TipoRegularizacionNSS> obtenerClaveNSSReporte(
            TramiteCorreccionCurp tramiteCorreccionCurp, String nss) {
        List<TipoRegularizacionNSS> claveNSS = new ArrayList<TipoRegularizacionNSS>();

        LOGGER.debug("valor de topo NSS: {}", tramiteCorreccionCurp
                .getCertificacionNSS().getTipoRegularizacionNSS());
        for (TipoRegularizacionNSS idNSS : tramiteCorreccionCurp
                .getCertificacionNSS().getTipoRegularizacionNSS()) {
            TipoRegularizacionNSS tipoRegNss = new TipoRegularizacionNSS();
            if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getId())) {
                tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getClave());
            } else {
                if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId())) {
                    tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getClave());
                }
            }
            tipoRegNss.setDescripcionRegularizacionNSS(nss);
            claveNSS.add(tipoRegNss);

        }
        LOGGER.debug("LA CLAVE ES: {}", claveNSS);
        return claveNSS;
    }

    private List<PeriodoMovimientoAfiliatorio> crearMovimientosAfiliatorios(
            List<PeriodoMovimientoAfiliatorio> periodos) {
        List<PeriodoMovimientoAfiliatorio> periodoMovimientos = new ArrayList<PeriodoMovimientoAfiliatorio>();
        if (periodos != null && !periodos.isEmpty()) {
            periodoMovimientos.addAll(periodos);
        } else {
            PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
            periodoMovimientoAfiliatorio.setFechaFinalMovimiento(null);
            periodoMovimientoAfiliatorio.setFechaInicioMovimiento(null);
            periodoMovimientoAfiliatorio.setNrp(null);
            periodoMovimientos.add(periodoMovimientoAfiliatorio);
        }

        return periodoMovimientos;
    }

    
    private void  generarListaSubdelegacionStep01(
      List<UnidadMedicaFamiliar> unidades, Set<String> clavesSubdelegacion,
      List<Subdelegacion> subdelegaciones ){
      if (unidades != null && !unidades.isEmpty()) {
        for (UnidadMedicaFamiliar unidad : unidades) {
          if (clavesSubdelegacion.add(unidad.getSubdelegacion()
                  .getClave())) {
            subdelegaciones.add(unidad.getSubdelegacion());
          }
        }
      }
    }
    public List<Subdelegacion> generarListaSubdelegacion(String codigoPostal,
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
                subdelegaciones);
                
        if (unidadesEstado != null && !unidadesEstado.isEmpty()) {
            for (MunicipioIMSS unidad : unidadesEstado) {
                if (clavesSubdelegacion.add(unidad.getSubdelegacion()
                        .getClave())) {
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

        return subdelegaciones;
    }

    public Solicitud actualizarXmlMotivos(Solicitud solicitud,
            MotivoAclaracionVO motivoAclaracionVO) {
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if (solicitud.getTramites() != null) {
            LOGGER.debug("---CDA--- Tramite {}", solicitud.getTramites().get(0)
                    .getTramiteId());
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        tramite.setMotivosAclaracion(cargarMotivosAclaracion(motivoAclaracionVO));
        try {
            solicitudBusiness.actualizarXmlTramite(tramite);
        } catch (TramiteNoEncontradoException e) {
            LOGGER.error("---CDA--- No se encontro el Tramite {}", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(
                    "---CDA--- Ocurrio un error al actualizar el tramite {}", e);
        }
        return solicitud;
    }

    public String obtenerUltimoMotivo(List<ObservacionesSubdelegacion> motivos) {
        String motivo = "";
        if (motivos != null && !motivos.isEmpty()) {
            motivo = motivos.get(motivos.size() - 1).getDetalle() != null ? motivos
                    .get(motivos.size() - 1).getDetalle() : " ";
        }
        return motivo.toUpperCase();
    }

    public Fisica buscarExistenciaIMSS(Fisica persona) {
        List<Fisica> personasIMSS = new ArrayList<Fisica>(); 
        if (persona.getCurp() != null && !persona.getCurp().equals("")) {
            LOGGER.debug("---CDA--- Info por curp {}:", persona.getCurp());
            personasIMSS.addAll( personaBusiness
                    .buscarPersonaFisicaPorCurpEnImss(persona.getCurp()) );
        } else {
            LOGGER.debug("---CDA--- Informacion Datos BASICOS");
            personasIMSS.addAll( personaBusiness
                    .buscarPersonaFisicaPorDatosBasicosEnImss(persona) );
        }
        if ( personasIMSS.isEmpty() ) {
            try {
                personasIMSS.add(personaBusiness.altaPersonaFisica(persona));
            } catch (DomicilioNoValidoException e) {
                LOGGER.error("---CDA--- No se puede crear persona ", e);
                personasIMSS.get(0).setErrorFormGeneral(
                        "No se puede crear la persona");
            }
        } else {
            this.LOGGER.debug(
                    "---CDA--- PERSONA ENCONTRADA EN IMSS CON IDPERSONA {}",
                    personasIMSS.get(0).getIdPersona());
        }
        return personasIMSS.get(0);
    }

    public MotivoAclaracionVO obtenerMotivoAclaracion(
            List<MotivoAclaracion> motivos) {
        MotivoAclaracionVO motivoAclaracionVO = new MotivoAclaracionVO();
        motivoAclaracionVO.setMotivosAclaracionIMSS(new ArrayList<String>());
        motivoAclaracionVO.setMotivosAclaracionAfore(new ArrayList<String>());
        motivoAclaracionVO
                .setMotivosAclaracionInfonavit(new ArrayList<String>());

        for (MotivoAclaracion motivoAclaracion : motivos) {

            if (motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.IMSS
                    .getId()) {
                motivoAclaracionVO
                        .getMotivosAclaracionIMSS()
                        .add(TiposAclaracionEnum
                                .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                        motivoAclaracion
                                                .getIdMotivoAclaracion())
                                .getClave());
            }

            if (motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.AFORE
                    .getId()) {
                motivoAclaracionVO
                        .getMotivosAclaracionAfore()
                        .add(TiposAclaracionEnum
                                .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                        motivoAclaracion
                                                .getIdMotivoAclaracion())
                                .getClave());
            }
            if (motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.INFONAVIT
                    .getId()) {
                motivoAclaracionVO
                        .getMotivosAclaracionInfonavit()
                        .add(TiposAclaracionEnum
                                .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                        motivoAclaracion
                                                .getIdMotivoAclaracion())
                                .getClave());
                if (motivoAclaracion.getIdMotivoAclaracion() == MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO
                        .getId()) {
                    motivoAclaracionVO.setCreditoDescontado(motivoAclaracion
                            .getDetalleAclaracion());
                }
            }

            if (motivoAclaracion.getInstitucion().getIdInstitucion() == InstitucionEnum.OTRO
                    .getId()
                    && motivoAclaracion.getIdMotivoAclaracion() == MotivoAclaracionEnum.OTRO
                            .getId()) {
                motivoAclaracionVO.setOtro(TiposAclaracionEnum
                        .obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(
                                motivoAclaracion.getIdMotivoAclaracion())
                        .getDependencia().toLowerCase());
                motivoAclaracionVO.setEspecificacion(motivoAclaracion
                        .getDetalleAclaracion());
            }
        }
        return motivoAclaracionVO;
    }

    public List<DocumentoProbatorio> cargarDatosTramiteDocumentAsegurado(
            TramiteCorreccionCurp tramite,
            DatosHistoriaLaboralVO documenHistoriaLaboralVO) {
        List<DocumentoProbatorio> listDocumentos = new ArrayList<DocumentoProbatorio>();
        Long idDocumento = TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
                .getId();
        if (documenHistoriaLaboralVO != null) {
            listDocumentos = ensamblaDocumentos(documenHistoriaLaboralVO
                    .getDocumentoProbatorioList());
        }
        List<DocumentoProbatorio> listPrelimiar = tramite
                .getDocumentosProbatorios();
        if (listPrelimiar != null && !listPrelimiar.isEmpty()) {
            for (DocumentoProbatorio doc : listPrelimiar) {
                if (doc.getDocumentoPorTipo().getTipoDocumentoProbatorio()
                        .getIdTipoDocumentoProbatorio() == idDocumento
                        .intValue()) {
                    listDocumentos.add(doc);
                }
            }
            return listDocumentos;
        } else {
            return listDocumentos;
        }
    }

    public List<DocumentoProbatorio> ensamblaDocumentos(
            List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> documentos) {
        List<DocumentoProbatorio> listDocumentos = new ArrayList<DocumentoProbatorio>();
        for (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio d : documentos) {
            listDocumentos.add(TransformerRegistroUtils.voToModelDocumentoProbatorio(d));
        }
        return listDocumentos;
    }

    public TramiteCorreccionCurp cargarDatosTramiteNuevo(
            TramiteCorreccionCurp tramite, NSSVO documentosNss) {
        CorreccionNSS correccionNSS = new CorreccionNSS();
        correccionNSS.setNss(documentosNss.getNSS());
        List<DocumentoProbatorio> listDocumentos = ensamblaDocumentos(documentosNss
                .getDocumentoProbatorioList());
        correccionNSS.setDocumentosProbatorios(listDocumentos);
        tramite.getListaNssCorreccion().add(correccionNSS);
        if(documentosNss.getObservaciones()!=null){
            tramite.setObservacion(documentosNss.getObservaciones()); 
        }
        return tramite;
    }

    public List<DocumentoProbatorio> cargarDatosTramiteDocumentBeneficiario(
            DatosHistoriaLaboralVO documenHistoriaLaboralVO) {
        List<DocumentoProbatorio> listDocumentos = null;
        if (documenHistoriaLaboralVO != null) {
            listDocumentos = ensamblaDocumentos(documenHistoriaLaboralVO
                    .getDocumentoProbatorioList());
        }
        return listDocumentos;
    }

    public Solicitud orderByTramite(Solicitud solicitudActiva) {
        TramiteCorreccionCurp firstTramite = new TramiteCorreccionCurp();
        Iterator<Tramite> iterador = solicitudActiva.getTramites().iterator();

        while (iterador.hasNext()) {
            TramiteCorreccionCurp tramiteCurp = ((TramiteCorreccionCurp) iterador
                    .next());
            LOGGER.debug("--CDA-- tramites documentos {}",
                    tramiteCurp.getDocumentosProbatorios());
            if (EstadoTramiteEnum.CANCELADO.getCodigo().intValue() == tramiteCurp
                    .getEstadoTramite().getIdEstadoTramitePersona().intValue()) {
                iterador.remove();
            } else {
                if (tramiteCurp.getPersonaRENAPO() != null) {
                    firstTramite = tramiteCurp;
                    iterador.remove();
                }
            }
        }
        solicitudActiva.getTramites().add(0, firstTramite);
        return solicitudActiva;
    }
    
    private void procesarInformacionConsultaStep01( ConsultaSolicitudTramiteVO informacionConsulta,
      TramiteCorreccionCurp tramiteCDA ,Solicitud sol     ){
      informacionConsulta
              .setNss(tramiteCDA.getListaNssCorreccion() != null ?
                      tramiteCDA
                              .getListaNssCorreccion().get(0).getNss() :
                      "");
      informacionConsulta.setStatus(sol.getEstadoSolicitud() != null ?
              sol
                      .getEstadoSolicitud().getDescripcion().trim() :
              "");
      informacionConsulta
              .setSubdelegacion(sol.getSubdelegacion() != null ?
                      sol
                              .getSubdelegacion().getClave()
                      + "-"
                      + sol.getSubdelegacion().getDescripcion() :
                      "");
      informacionConsulta
              .setEstatusDescarga(sol.getEstadoSolicitud() != null ?
                      sol
                              .getEstadoSolicitud().getIdEstadoSolicitud()
                              .equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()) :
                      false);
    }

    public ConsultaSolicitudTramiteVO procesarInformacionConsulta(
            Fisica persona, Solicitud sol, TramiteCorreccionCurp tramiteCDA) {
        ConsultaSolicitudTramiteVO informacionConsulta = new ConsultaSolicitudTramiteVO();

        informacionConsulta.setCurp(persona.getCurp());
        TareaBandeja tareaBandeja = flujoTrabajoBusiness
                .getTareaActivaPorIdTramite(sol.getTramites().get(0)
                        .getTramiteId());
        if (tareaBandeja != null && tareaBandeja.getInicioTramite() != null) {
            informacionConsulta.setFechaSolicitud(tareaBandeja
                    .getInicioTramite().getFechaSolicitud());
        }
        informacionConsulta.setFolio(sol.getNoFolioSolicitud());
        informacionConsulta.setNombre(persona.getNombreCompleto());
        procesarInformacionConsultaStep01(informacionConsulta, tramiteCDA, sol);
        informacionConsulta.setIdTramite(tramiteCDA.getTramiteId().toString());

        if (sol.getEstadoSolicitud().getIdEstadoSolicitud()
                .equals(EstadoSolicitudEnum.CANCELADA.getCodigo())) {
            informacionConsulta.setEstatusMot(true);
            informacionConsulta
                    .setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA
                            .getObservacionesSubdelegacion()));
            informacionConsulta
                    .setIdEstadoTramite(EstadoSolicitudEnum.CANCELADA
                            .getCodigo());
        }

        if (sol.getTramites()
                .get(0)
                .getEstadoTramite()
                .getIdEstadoTramitePersona()
                .equals(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo())
                && (tramiteCDA.getObservacionesSubdelegacion() != null
                        && !tramiteCDA.getObservacionesSubdelegacion()
                                .isEmpty() && tramiteCDA
                        .getObservacionesSubdelegacion().get(0) != null)) {
            informacionConsulta.setEstatusMot(true);
            informacionConsulta
                    .setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA
                            .getObservacionesSubdelegacion()));
            informacionConsulta
                    .setStatus(EstadoNegocioEnum
                            .obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE
                                    .getCodigo()));
            
            informacionConsulta.setInfAdicional(obtenerUltimoMotivo(tramiteCDA.getObservacionesSubdelegacion()));
            informacionConsulta
                    .setIdEstadoTramite(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE
                            .getCodigo());
        }

        return informacionConsulta;
    }
    
    public List<DocumentoProbatorio> cargarDatosTramiteDocumentAsegurado(
            TramiteCorreccionCurp tramite,
            List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> listDocumentoProbatorios) {
        List<DocumentoProbatorio> listDocumentos = new ArrayList<DocumentoProbatorio>();
        listDocumentos = ensamblaDocumentos(listDocumentoProbatorios);
//        List<DocumentoProbatorio> listPrelimiar = new ArrayList<DocumentoProbatorio>();
//        if(tramite.getAsegurado()!=null){
//            listPrelimiar = tramite.getAsegurado().getDocumentosProbatorios();
//        }
//        if (listPrelimiar != null && !listPrelimiar.isEmpty()) {
//            for (DocumentoProbatorio doc : listPrelimiar) {
//                    listDocumentos.add(doc);
//            }
//            return listDocumentos;
//        } else {
            return listDocumentos;
//        }
    }

}
