/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.DS_REPORTE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.MAIN_REPORT_CUEST;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.PATH_REPORTS;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.REPORTE_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORT2_CUEST;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORTE2_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORTE_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORT_CUEST;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business.CuestionarioSeguroBussines;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.CuestionarioVO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.PropertiesOpciones;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.FormaPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoIssfEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.cuestionario.Opcion;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.cuestionario.Respuesta;
import mx.gob.imss.digital.modelo.cuestionario.RespuestasCuestionario;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.Sexo;
import mx.gob.imss.digital.modelo.seguros.ComprobanteSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.ComprobantesSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.CuestionarioSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.CuestionariosSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "comprobanteSeguroEntity", mappedName = "comprobanteSeguroEntity")
public class ComprobanteSeguroEntity implements ComprobanteSeguroLocal {

    /**
     * LOGGER de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComprobanteSeguroEntity.class);

    /** Ubicacion del reporte. */
    private static final String REPORTE_URL = "comprobantes/comprobanteSeguro.jasper";

    /** Ubicacion del reporte para modalidad 33. */
    private static final String REPORTE_MOD33_URL = "comprobantes/comprobanteSeguroMod33.jasper";

    /** Ubicacion del sub reporte beneficiarios modalidad 33. */
    private static final String SUB_REPORTE_MOD33_URL = "comprobantes/subRepBeneficiariosMod33.jrxml";

    /** Ubicacion del reporte para modalidad 33. */
    private static final String REPORTE_MOD40_URL = "comprobantes/comprobanteSeguroMod40.jasper";

    /**
     * Cadena para indicar si se trata de una comra inicial
     */
    private static final String COMPRA = "Inscripci\u00f3n Inicial";
    /**
     * Cadena para indicar si se trata de una renovacion
     */
    private static final String RENOVACION = "Renovaci\u00f3n";
    /**
     * Cadena para indicar si se trata de una renovacion
     */
    private static final String RENOVACION_CVRO = "Reingreso";
    /**
     * Formato para las fechas
     */
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * Formato para las fechas minutos
     */
    private static final String FORMATO_FECHA_MIN = "dd 'de' MMMM yyyy',' HH:mm:ss";
    /**
     * MArca para las respuesta
     */
    private static final String MARCA_RESPUESTA = "X";
    /**
     * MArca no disponible
     */
    private static final String ND = "N/D";

    public final static Long[] MOD_43_44 = new Long[]{ModalidadEnum.CUARENTAYTRES.getId(), ModalidadEnum.CUARENTAYCUATRO.getId()};


    private static final String BENEFICIARIO = "BENEFICIARIO";
    /**
     * Consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvroLocal;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    /**
     * Servicio de personas
     */
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;
    /**
     * Servicio para consultar cotizaciones
     */
    @EJB(mappedName = "cotizacionServiceBusiness")
    private CotizacionServiceRemote cotizacionServiceRemote;
    /**
     * Servicio para la firma
     */
    @EJB(mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigital;
    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusinessRemote;
    /**
     * Servicio de domicilios
     */
    @EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServices;
   
    /**
     * Servicio de ultimo
     */
   @EJB(name = "domicilioExternosServiceBusiness", mappedName = "domicilioExternosServiceBusiness")
	private DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness;
   /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager em;
    /**
     * Servicio para la consulta de patrones
     */
    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	@EJB(name = "registroPatronalServiceBusiness", mappedName = "registroPatronalServiceBusiness")
    private RegistroPatronalServiceBusinessRemote registroPatronalServiceBusiness;

    /**
     * Verifica si una cadena es nula regresa vacio, si no es nula regresa el
     * valor recibido
     *
     * @param cadena
     *            valor a verificar que no sea nulo
     * @return vacio si la cadena es nula
     */
    public static final String getCadenaNoNula(String cadena) {
        return cadena == null ? ND : cadena;
    }
	
	public static final String getCadenaNoNulaVacia(String cadena) {
        return cadena == null ? "" : cadena;
    }

    public static final Date getFechaFinalSeguro(SeguroIvro seguro) {
        Date fechaFinal = seguro.getFechaFin();
        if (seguro.getCompra() != null && seguro.getCompra().getPagos()[0].getConBeneficio()) {
            Date fechaPago = null;
            for (Pago pago : seguro.getCompra().getPagos()) {
                if(pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()){
                    fechaPago = getFechaMayor(fechaPago, pago.getFechaFinPeriodo());
                }
            }
            fechaFinal = fechaPago;
        }
        return fechaFinal;
    }

    public static final Date getFechaMayor(Date fechaIni, Date fechaFin) {
        Date fechaMayor = fechaIni;
        if(fechaMayor == null) {
            fechaMayor = fechaFin;
        }
        return fechaMayor.after(fechaFin) ? fechaMayor : fechaFin;
    }

    /*
     * (non-Javadoc)
     *
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte.
     * GneradorComprobanteSeguro
     * #generaComprobantes(mx.gob.imss.digital.modelo.seguros.SegurosIvro)
     */
    @Override
    public DocumentoSeguro generaComprobantes(SegurosIvro seguros) {
        DocumentoSeguro documentoSeg = new DocumentoSeguro();
        documentoSeg.setNombreArchivo("comprobante-seguro.pdf");
        LOGGER.debug("Generando comprobantes seguros");
        if (seguros != null && seguros.getSeguroIvro() != null
                && seguros.getSeguroIvro().length == 1) {
            LOGGER.info("Generando comprobantes seguros unico");
            documentoSeg = guardaYGeneraDocumento(seguros.getSeguroIvro()[0]);
        } else if (seguros != null && seguros.getSeguroIvro() != null) {
            LOGGER.info("Generando comprobantes seguros multiples");
            documentoSeg = generaDocumentoSeguro(seguros);
        }

        return documentoSeg;
    }

    /**
     * Consulta si existe un documento generado para el seguro y lo regresa, en
     * caso de no existir lo genera y lo guarda
     * @param seguro el seguro a generar su comprobante
     * @return el comprobante generado para el seguro
     */
    private DocumentoSeguro guardaYGeneraDocumento(SeguroIvro seguro) {
        DocumentoSeguro documentoSeg = new DocumentoSeguro();
        documentoSeg.setNombreArchivo("comprobante-seguro.pdf");
        LOGGER.debug("Buscando tramite asociado");

        // Tramite IvRO
        TramiteSeguroIvro tramite;
        if (seguro.getTramite() != null && seguro.getTramite().getTramiteId() != null) {
            tramite = seguro.getTramite();
        } else {
            DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
            SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
            tramite = seg.getTramite();
        }

        Long tramiteId = 0L;

        if (tramite != null && tramite.getTramiteId() != null) {
            LOGGER.debug("Tramite encontrado para el seguro");
            tramiteId = tramite.getTramiteId();
        } else {
            LOGGER.debug("Obteniendo el tramite del seguro enviado");
            tramiteId = seguro.getTramite() != null ? seguro.getTramite().getTramiteId() : 0L;
        }

        // Modalidad 40
        if (tramiteId == 0L) {
            TramiteSeguroIvroMod40 tramiteContVoluntaria;
            if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getTramiteId() != null) {
                tramiteContVoluntaria = seguro.getTramiteContVoluntaria();
            } else {
                DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
                SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
                tramiteContVoluntaria = seg.getTramiteContVoluntaria();
            }

            tramiteId = 0L;
            if (tramite != null && tramiteContVoluntaria.getTramiteId() != null) {
                LOGGER.debug("Tramite encontrado para el seguro");
                tramiteId = tramiteContVoluntaria.getTramiteId();
            } else {
                LOGGER.debug("Obteniendo el tramite del seguro enviado");
                tramiteId = seguro.getTramiteContVoluntaria() != null ? seguro.getTramiteContVoluntaria().getTramiteId() : 0L;
            }
        }

        // Modalidad 33
        if (tramiteId == 0L) {
            TramiteSeguroIvroMod33 tramiteSegutoFamiliar;
            if (seguro.getTramiteSeguroFamiliar() != null && seguro.getTramiteSeguroFamiliar().getTramiteId() != null) {
                tramiteSegutoFamiliar = seguro.getTramiteSeguroFamiliar();
            } else {
                DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
                SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
                tramiteSegutoFamiliar = seg.getTramiteSeguroFamiliar();
            }

            tramiteId = 0L;
            if (tramite != null && tramiteSegutoFamiliar.getTramiteId() != null) {
                LOGGER.debug("Tramite encontrado para el seguro");
                tramiteId = tramiteSegutoFamiliar.getTramiteId();
            } else {
                LOGGER.debug("Obteniendo el tramite del seguro enviado");
                tramiteId = seguro.getTramiteSeguroFamiliar() != null ? seguro.getTramiteSeguroFamiliar().getTramiteId() : 0L;
            }
        }

        byte[] documento = null;
        LOGGER.debug("Buscando comprobante asociado");

        DocumentoPorTipoEnum documentoPorTipo = null;
        DitTramite diTramiteConsulta = em.find(DitTramite.class, tramiteId);
        Long idTipoTramite = diTramiteConsulta.getDicTipoTramite().getCveIdTipoTramite();

        if (idTipoTramite.intValue() == TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_SEGURO_FAMILIAR;
        } else if (idTipoTramite.intValue() == TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_SSF;
        } else if (idTipoTramite.intValue() == TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_CVRO;
        } else if (idTipoTramite.intValue() == TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_IVRO;
        } else if (idTipoTramite.intValue() == TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_CVRO;
        } else if (idTipoTramite.intValue() == TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo().intValue()) {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_IVRO;
        } else {
            documentoPorTipo = DocumentoPorTipoEnum.COMPROBANTE_IVRO;
        }

        documento = (byte[]) solicitudBusinessRemote.getDocumentoPorTipoIdTramite(tramiteId,
                documentoPorTipo.getId());

        if (documento == null) {
        LOGGER.debug("Seguro sin comprobante asociado");
        SegurosIvro segurosIvro = new SegurosIvro();
        segurosIvro.setSeguroIvro(new SeguroIvro[]{seguro});
        documentoSeg = generaDocumentoSeguro(segurosIvro);
        solicitudBusinessRemote.actualizarDocumentosTramite(tramiteId,
                documentoPorTipo.getId(), documentoSeg.getArchivo());
        } else {
            LOGGER.debug("Seguro con comprobante asociado");
            documentoSeg.setArchivo(documento);
        }
        return documentoSeg;
    }

    /**
     * Genera el comprobante de seguro
     * @param seguros los seguros a generar su comprobante
     * @return el documento generado con los comprobantes para todos los seguros
     */
    private DocumentoSeguro generaDocumentoSeguro(SegurosIvro seguros) {
        ComprobantesSeguroReporte comprobantes = generaDatosComprobante(seguros);
        Long origen = seguros.getOrigen();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        DocumentoSeguro documento = new DocumentoSeguro();
        String reporteUrl = REPORTE_URL;
        documento.setNombreArchivo("comprobante-seguro.pdf");
        try {
            List<JasperPrint> prints = new ArrayList<JasperPrint>();
            documento.setListComprobanteSeguro(comprobantes.getComprobante());
            for (ComprobanteSeguroReporte comprobante : comprobantes.getComprobante()) {
                comprobante.setOrigen(origen != null ? origen.intValue()
                        : OrigenSolicitudEnum.INTERNET.getId().intValue());

                Map<String, Object> parametros = convierteDatosReporte(comprobante);
                boolean aplicaCuestionarioModalidad = false;
                boolean isModalidad33 = false;
                boolean isModalidad40 = false;

				for (SeguroIvro seguro : seguros.getSeguroIvro()) {
					DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class,
							seguro.getCveIdSeguroIvro());
					SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);

					if(comprobante!=null && comprobante.getCuestionario()!=null){
					    LOGGER.info("comprobante 3: "+comprobante.getCuestionario());
                    }

					if (seg.getModalidad() != null
							&& seg.getModalidad().getIdModalidad() != ModalidadEnum.TREINTAYTRES.getId()) {
						aplicaCuestionarioModalidad = true;
                        LOGGER.info("entra como seguro 33 y aplicaCuestionarioMOdalidad: true");
					}

                    if ( seg.getModalidad() != null ) {
                        if(seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()){
                            isModalidad33 = true;
                        }else if(seg.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()){
                            isModalidad40 = true;
                        }
                    }
				}

                if (aplicaCuestionarioModalidad) {
                    LOGGER.info("Se agregan los datos de cuestionario");
                    agregarDatosCuestionario(parametros, seguros);
                }

				if (isModalidad33) {
                    agregarDatosTipoContratacion(parametros, seguros);

					List<mx.gob.imss.digital.modelo.persona.Fisica> beneficiarios = obtenerBeneficiariosMod33(seguros);
					
                    JasperReport beneficiariosSubReporte = JasperCompileManager.compileReport(new ClassPathResource(SUB_REPORTE_MOD33_URL).getInputStream());
                    parametros.put("beneficiariosSubReporte", beneficiariosSubReporte);
                	parametros.put("beneficiarios", beneficiarios);
                    if(beneficiarios.size() == 1){
                    	String curpUnicoBeneficiario = (beneficiarios.get(0).getCurp() != null)?beneficiarios.get(0).getCurp():"";
                    	String curpTitular = (parametros.get("curp").toString()!=null)?parametros.get("curp").toString():"";
                    	LOGGER.debug("curpUnicoBeneficiario: "+curpUnicoBeneficiario);
                    	LOGGER.debug("curpTitular: 			 "+curpTitular);
                    	if(!curpUnicoBeneficiario.trim().isEmpty() && curpUnicoBeneficiario.equals(curpTitular)){
                        	parametros.put("conBeneficiarios", Boolean.FALSE);
                    	}else{
                        	parametros.put("conBeneficiarios", Boolean.TRUE);
                    	}
                    }else{
                    	parametros.put("conBeneficiarios", Boolean.TRUE);
                    }
                    
					LOGGER.debug("La lista de beneficiarios para modalidad 33 cuenta con "
							+ beneficiarios.size() + " personas.");
                    
                    reporteUrl = REPORTE_MOD33_URL;
				}else if(isModalidad40){
                    reporteUrl = REPORTE_MOD40_URL;
                }

                JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource(
                        reporteUrl).getInputStream());
                JREmptyDataSource emptyDS = new JREmptyDataSource();
                JasperPrint print = JasperFillManager.fillReport(report, parametros, emptyDS);
                prints.add(print);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, prints);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
                    Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArraySalida);
            exporter.exportReport();
        } catch (Exception e) {
            LOGGER.error("Error al generar el reporte jasper", e);
        }
        documento.setArchivo(byteArraySalida.toByteArray());

        for(SeguroIvro seguro : seguros.getSeguroIvro()){
         // Tramite IvRO
            TramiteSeguroIvro tramite;
    		if (seguro.getTramite() != null && seguro.getTramite().getTramiteId() != null) {
    			tramite = seguro.getTramite();
    		} else {
    			DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
    			SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
    			tramite = seg.getTramite();
    		}

            Long tramiteId = 0L;
            if (tramite != null && tramite.getTramiteId() != null) {
                LOGGER.debug("Tramite encontrado para el seguro");
                tramiteId = tramite.getTramiteId();
            } else {
                LOGGER.debug("Obteniendo el tramite del seguro enviado");
                tramiteId = seguro.getTramite() != null ? seguro.getTramite().getTramiteId() : 0L;
            }

            // Modalidad 40
    		if (tramiteId == 0L) {
            	TramiteSeguroIvroMod40 tramiteContVoluntaria;
        		if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getTramiteId() != null) {
        			tramiteContVoluntaria = seguro.getTramiteContVoluntaria();
        		} else {
        			DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
        			SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
        			tramiteContVoluntaria = seg.getTramiteContVoluntaria();
        		}

                tramiteId = 0L;
                if (tramite != null && tramiteContVoluntaria.getTramiteId() != null) {
                    LOGGER.debug("Tramite encontrado para el seguro");
                    tramiteId = tramiteContVoluntaria.getTramiteId();
                } else {
                    LOGGER.debug("Obteniendo el tramite del seguro enviado");
                    tramiteId = seguro.getTramiteContVoluntaria() != null ? seguro.getTramiteContVoluntaria().getTramiteId() : 0L;
                }
            }

			// Se guardo archivo generado y firmado a notaria
			try {
				DitTramite ditTRamite = em.find(DitTramite.class, tramiteId);
				Long idSolicitud = Long.valueOf(ditTRamite.getDitSolicitud().getCveIdSolicitud());

				Solicitud solicitud = new Solicitud();
				solicitud.setSolicitudId(idSolicitud);

				Tramite tramiteIvro = new Tramite();
				tramiteIvro.setTramiteId(tramiteId);
				TipoTramiteEnum tipoTramiteInicial = TipoTramiteEnum
						.obternerEnumById(ditTRamite.getDicTipoTramite().getCveIdTipoTramite().intValue());
				
				Boolean tramiteRenovaciones = false;
				
				if(tipoTramiteInicial.equals(TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA) || tipoTramiteInicial.equals(TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA)||
						tipoTramiteInicial.equals(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR) || tipoTramiteInicial.equals(TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR)) {
					tramiteRenovaciones = true;
					LOGGER.info("####El tramite es CVRO o SSF");
				}else {
					LOGGER.info("####El tramite NO es CVRO o SSF");
				}
				
				solicitud = solicitudBusinessRemote.asociarTramiteSolicitudPorEnum(solicitud, tramiteIvro,
								tipoTramiteInicial, null);

				if (tramiteId != null && tramiteId != 0L) {
					for (ComprobanteSeguroReporte comprobante : documento.getListComprobanteSeguro()) {
						if (StringUtils.isNotBlank(comprobante.getSecuenciaNotarial())) {
							FirmaElectronica datosFirma = firmaDigital.getFirmaElectronica(solicitud);

							String secuenciaNotarial;
							if (datosFirma != null) {
								secuenciaNotarial = datosFirma.getSecuenciaNotaria();
							} else {
								FirmaElectronica firmaElectronica = new FirmaElectronica();
								firmaElectronica.setCadenaOriginal(comprobante.getCadenaOriginal());
								firmaElectronica.setReciboNotarial(comprobante.getSecuenciaNotarial());
								firmaElectronica.setSecuenciaNotaria(comprobante.getSecuenciaNotarial());
								firmaElectronica.setSerialCertificado(comprobante.getNumeroSerie());
								firmaElectronica.setRecibo(comprobante.getSelloDigital());
								firmaElectronica.setUrlAcuseFirma("");
								firmaElectronica.setIniciaVigenciaCertificado(new Date());
								firmaElectronica.setFinVigenciaCertificado(new Date());

								firmaDigital.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
								secuenciaNotarial = comprobante.getSecuenciaNotarial();
							}

							String nombreArchivoNotaria = "comprobante-seguro-" + tramiteId + ".pdf";
							LOGGER.debug("Enviando " + nombreArchivoNotaria + " a notaria con secuencia notarial "
									+ secuenciaNotarial);
							
							if(tramiteRenovaciones) {
								firmaDigital.guardarArchivoBoveda(secuenciaNotarial,
										nombreArchivoNotaria, documento.getArchivo());
							}else {
								firmaDigital.guardarArchivoFirmado(secuenciaNotarial,
									nombreArchivoNotaria, documento.getArchivo());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("No fue posible guardar el documento en notaria");
			}
		}

        return documento;
    }

    private Map<String, Object> agregarDatosTipoContratacion( Map<String, Object> parametros, SegurosIvro seguros){

        parametros.put("desdeExtranjero", aplicoDesdeExtranjero(seguros));
        parametros.put("soloSolicitante", aplicoSoloSolicitante(seguros));
        return parametros;
    }

    private boolean aplicoDesdeExtranjero(SegurosIvro seguros) {
        boolean aplica = false;

        for (SeguroIvro seguro : seguros.getSeguroIvro()) {
            if (seguro.getTramite() != null) {
                aplica = seguro.getTramite().isDesdeExtranjero();
            }
        }

        return aplica;
    }

    private boolean aplicoSoloSolicitante(SegurosIvro seguros) {
        boolean aplica = false;

        for (SeguroIvro seguro : seguros.getSeguroIvro()) {
            if (seguro.getTramite() != null) {
                aplica = seguro.getTramite().isSoloSolicitante();
            }
        }

        return aplica;
    }

    private List<mx.gob.imss.digital.modelo.persona.Fisica> obtenerBeneficiariosMod33(SegurosIvro seguros){
        List<mx.gob.imss.digital.modelo.persona.Fisica> beneficiarios = new ArrayList<mx.gob.imss.digital.modelo.persona.Fisica>();
        TramiteSeguroIvro tramiteSeg;
        DitSeguroIvro ditSeguroIvro;
        SeguroIvro seg;
        for (SeguroIvro seguro : seguros.getSeguroIvro()) {
            ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
            seg = IvroFactory.generaSeguro(ditSeguroIvro);
            tramiteSeg = seg.getTramiteSeguroFamiliar();
          
            if (tramiteSeg == null) {
            	tramiteSeg = seg.getTramite();
            	
            	if (tramiteSeg == null) {
                	tramiteSeg = seguro.getTramite();
                }
            } 
            
            if(tramiteSeg != null
                    && tramiteSeg.getBeneficiarios() != null
                    && tramiteSeg.getBeneficiarios().length > 0){

            	for(mx.gob.imss.digital.modelo.persona.Fisica beneficiario : tramiteSeg.getBeneficiarios()) {
            		String nss = beneficiario.getNss();
            		Fisica beneficiarioTmp = null;
            		try {
                        LOGGER.info("Buscando BENEFICIARIO con NSS {}", nss);
                        beneficiarioTmp = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
                        
                        beneficiario.setCurp(beneficiarioTmp.getCurp());
                        beneficiario.setFechaNacimiento(beneficiarioTmp.getFechaNacimiento());
                        beneficiario.setFechaNacimientoFormateada(beneficiarioTmp.getFechaNacimientoFormateada());
                        beneficiario.setNombre(IvroFactory.corrigeCadena(beneficiarioTmp.getNombreCompleto()));
                        beneficiario.setNss(nss);
                        beneficiario.setSexo(new Sexo());
                        beneficiario.getSexo().setDescripcion(beneficiarioTmp.getSexo() != null ? beneficiarioTmp.getSexo()
                                .getDescripcion() : ND);

                        // Se utiliza este campo para enviar la edad y no se esta calculando en el jasper]
                        beneficiario.setAnioRegistroNac(calculaEdad(beneficiarioTmp.getFechaNacimiento()));

                        Integer idParentesco = null;
                        String tipoParentesco;
                        try {

                            if(tramiteSeg.getCompra()!=null){
                                LOGGER.info("se busca la compra");
                                DitCompra ditCompra = em.find(DitCompra.class, seg.getCompra().getIdCompra());
                                LOGGER.info("ditCompra: "+ditCompra);
                                if(ditCompra!=null ){
                                    Compra compra = IvroFactory.generaCompra(ditCompra);
                                    Cotizacion cotizacion = cotizacionServiceRemote.findCotizacion(compra
                                            .getIdCotizacion());

                                    EmpleadoCuota[] empleados = cotizacion.getDetalle().getEmpleados();

                                    if(empleados!=null && empleados.length>0){
                                        for(EmpleadoCuota temp: empleados){
                                            if(beneficiario.getCurp().equals(temp.getCurp())){
                                                LOGGER.info("Es el mismo beneficiario: "+beneficiario.getCurp());
                                                idParentesco = temp.getParentesco().getIdParentesco().intValue();
                                            }
                                        }
                                    }


                                    LOGGER.info("idParentesco de la compra: "+idParentesco);
                                }else{
                                    LOGGER.info("No exista la compra");
                                }

                            }

                            LOGGER.info("idParentesco: " + idParentesco);
                            if (idParentesco==null){
                                LOGGER.info("no se encontro Parentesco");
                                tipoParentesco = null;
                            }else if (ParentescoIssfEnum.obternerEnumById(idParentesco).equals(ParentescoIssfEnum.TITULAR)) {
                                LOGGER.info("Es TITULAR");
                                tipoParentesco = ParentescoIssfEnum.TITULAR.getDescripcion();
                            } else {
                                LOGGER.info("es un beneficiario ");
                                tipoParentesco = BENEFICIARIO + " | "
                                        + ParentescoIssfEnum.obternerEnumById(idParentesco).getDescripcion();
                            }
                        }catch(Exception e){
                            LOGGER.error("Hubo un error al obtener el parentesco: ",e);
                            tipoParentesco = null;
                        }

                        beneficiario.setErrorFormGeneral(tipoParentesco);

                    } catch (Exception e) {
                        LOGGER.error("No se encontraron los datos del beneficiario", e);
                    }
            	}
            	
                beneficiarios.add(tramiteSeg.getBeneficiarios()[0]);
            }
        }

        LOGGER.debug("Se va a pintar detalle de beneficiarios modalidad 33");
        for(mx.gob.imss.digital.modelo.persona.Fisica beneficiario : beneficiarios) {
        	LOGGER.debug("BENEFICIARIO -> \n"
    				+ ToStringBuilder.reflectionToString(beneficiario,
    						ToStringStyle.MULTI_LINE_STYLE));
        }
        
        return beneficiarios;
    }

    @Override
    public ComprobantesSeguroReporte generaDatosComprobante(SegurosIvro seguros) {
        ComprobantesSeguroReporte comprobanteSeguro = new ComprobantesSeguroReporte();
        List<ComprobanteSeguroReporte> comprobantes = new ArrayList<ComprobanteSeguroReporte>();
        LOGGER.info("seguros: "+seguros.getSeguroIvro().length);
        for (SeguroIvro seguro : seguros.getSeguroIvro()) {
        	DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
			SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);
			ComprobanteSeguroReporte comprobante = new ComprobanteSeguroReporte();

			DitCompra ditCompra = em.find(DitCompra.class, seg.getCompra().getIdCompra());
			Compra compra = IvroFactory.generaCompra(ditCompra);

            comprobante.setCuota(compra.getMonto());
            DicModalidad dicModalidad=em.find(DicModalidad.class, seg.getModalidad().getIdModalidad());
            String modalidad = dicModalidad.getNumModalidad()+" "+dicModalidad.getDesModalidad();
            comprobante.setModalidad(modalidad);

            comprobante.setPeriodo(getEtiquetaPeriodo(seg.getFechaInicio(), seg.getFechaFin()));

            TramiteSeguroIvro tramite = seg.getTramite();
            TramiteSeguroIvroMod33 tramiteSegFamiliar = seg.getTramiteSeguroFamiliar();
            TramiteSeguroIvroMod40 tramiteContVoluntaria = seg.getTramiteContVoluntaria();

            if (tramite == null) {
                tramite = seguro.getTramite();
                LOGGER.info("tramite null pero se asigna: "+tramite);
            }

            if (tramiteSegFamiliar == null) {
            	tramiteSegFamiliar = seguro.getTramiteSeguroFamiliar();
                LOGGER.info("tramiteFamiliar null pero se asigna: "+tramiteSegFamiliar);
            }

            if (tramiteContVoluntaria == null) {
            	tramiteContVoluntaria = seguro.getTramiteContVoluntaria();
                LOGGER.info("tramite40 null pero se asigna: "+tramiteContVoluntaria);
            }

            String nss = "";
            DitTramite ditTRamite = new DitTramite();
			if (tramite != null) {
                LOGGER.info("Entra como tramite normal");
				comprobante.setCuestionario(tramite.getAplicaCuestionario() != null
						? tramite.getAplicaCuestionario() : false);
                LOGGER.info("idPersona: "+seg.getTitular().getIdPersona());
                Boolean esCambioModalidad = esCambioModalidadIVRO(seg.getTitular().getIdPersona(),dicModalidad);
                LOGGER.info("esCambioModalidad: "+esCambioModalidad);
				comprobante.setTipoTramite(tramite.getRenovacion() != null
						&& tramite.getRenovacion() && !esCambioModalidad ? RENOVACION : COMPRA);

				nss = tramite.getBeneficiarios()[0].getNss();

				if (StringUtils.isNotBlank(tramite.getNrpFisica())) {
	                comprobante.setNrp(tramite.getNrpFisica());
	            }

				ditTRamite = em.find(DitTramite.class, tramite.getTramiteId());
			} else if (tramiteSegFamiliar != null) {
                LOGGER.info("entra como tramite Familiar");
				comprobante.setCuestionario(tramiteSegFamiliar.getAplicaCuestionario() != null
						? tramiteSegFamiliar.getAplicaCuestionario() : false);
				LOGGER.info("comprobante Cuestionario 1: "+comprobante.getCuestionario());
				comprobante.setTipoTramite(tramiteSegFamiliar.getRenovacion() != null
						&& tramiteSegFamiliar.getRenovacion() ? RENOVACION : COMPRA);
				nss = tramiteSegFamiliar.getBeneficiarios()[0].getNss();

				if (StringUtils.isNotBlank(tramiteSegFamiliar.getNrpFisica())) {
	                comprobante.setNrp(tramiteSegFamiliar.getNrpFisica());
	            }

				ditTRamite = em.find(DitTramite.class, tramiteSegFamiliar.getTramiteId());
			} else if (tramiteContVoluntaria != null) {
				comprobante.setCuestionario(tramiteContVoluntaria.getAplicaCuestionario() != null
						? tramiteContVoluntaria.getAplicaCuestionario() : false);
				comprobante.setTipoTramite(tramiteContVoluntaria.getRenovacion() != null
						&& tramiteContVoluntaria.getRenovacion() ? RENOVACION_CVRO : COMPRA);
				nss = tramiteContVoluntaria.getBeneficiarios()[0].getNss();

				if (StringUtils.isNotBlank(tramiteContVoluntaria.getNrpFisica())) {
	                comprobante.setNrp(tramiteContVoluntaria.getNrpFisica());
	            }

				ditTRamite = em.find(DitTramite.class, tramiteContVoluntaria.getTramiteId());
			}

            String nrp = null;
            comprobante.setRecargo(false);
            comprobante.setBeneficio(false);
            try {
                Cotizacion cotizacion = cotizacionServiceRemote.findCotizacion(compra
                        .getIdCotizacion());
                CalculoCuota detalle = cotizacion.getDetalle();
                nrp = detalle.getNumeroRegistroPatronal();
                
                if (StringUtils.isBlank(comprobante.getNrp())) {
                	comprobante.setNrp(nrp);
                }
                
                if((detalle.getConBeneficio() != null && detalle.getConBeneficio().booleanValue())
                		|| (compra.getFormaPago()!=null && compra.getFormaPago().longValue() == FormaPagoEnum.BIMESTRAL.getId())
                		|| (compra.getFormaPago()!=null && compra.getFormaPago().longValue() == FormaPagoEnum.MENSUAL.getId())
                		) {
                    if (seg.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()
                    		&& cotizacion.getDetalle().getAplicaRecargoPorFechaBaja()) {
                    	/*
						 * Se tiene un CVRO retroactivo, por lo que se toma del
						 * detalle de la cotizacion el monto (se recorren los
						 * periodos) y el periodo
						 */
                    	BigDecimal total = BigDecimal.ZERO;
                    	
                    	for (PeriodoCuota periodo : detalle.getEmpleados()[0].getPeriodos()) {
                    		total = total.add(periodo.getTotal());
                    	}
                    	
                    	comprobante.setCuota(total);
						comprobante.setPeriodo(getEtiquetaPeriodo(detalle
								.getFechaInicioCalculo().getTime(), detalle
								.getFechaFinCalculo().getTime()));                    	
                    } else {
	                	comprobante.setCuota(cotizacion.getDetalle().getEmpleados()[0].getPeriodos()[0].getTotal());
	                    comprobante.setPeriodo(getEtiquetaPeriodo(seg.getFechaInicio(),
	                            cotizacion.getDetalle().getEmpleados()[0].getPeriodos()[0].getFinPeriodo().getTime()));
                    }
                } else {
                    comprobante.setCuota(cotizacion.getCuotaTotal());
                }

                comprobante.setBeneficio(detalle.getConBeneficio() != null ? detalle
                        .getConBeneficio() : false);
                comprobante.setRecargo(detalle.getConRecargos() != null ? detalle.getConRecargos()
                        : false);
                comprobante.setSalarioDiario(cotizacion.getDetalle().getEmpleados()[0].getSalario());
            } catch (SUAException e) {
                LOGGER.error("No se encontro la cotizacion asociada");
            }

            mx.gob.imss.digital.modelo.persona.Fisica titular = seg.getTitular();
            
            if (tramiteSegFamiliar != null || seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
				LOGGER.debug("Se tiene un seguro modalidad 33, por lo tanto, el NSS tomar debe ser el del titular para mostrar la info del solicitante");
				
				/*
				 * Se checa si el titular trae el NSS, si no
				 * se usa el de la persona que trae el tramite
				 * que es el titular
				 */
				String nssTmp = seg.getTitular().getNss();
				if (StringUtils.isBlank(nssTmp)){
					nssTmp = tramite.getPersona().getNss();
				}
				comprobante = agregaDatosBeneficiario(comprobante, nssTmp);
				comprobante = agregaDatosTitularMod33y40(comprobante, tramite.getPersona(), nrp);
            } else  if (tramiteContVoluntaria != null || seg.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
            	comprobante = agregaDatosBeneficiario(comprobante, nss);
            	comprobante = agregaDatosTitularMod33y40(comprobante, tramite.getPersona(), nrp);
            } else if (seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() || 
                    seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId()) {
                comprobante = agregaDatosBeneficiario(comprobante, nss);
                comprobante = agregaDatosTitular(comprobante, titular.getIdPersona(), nrp);
            } else {
                comprobante = agregaDatosBeneficiario(comprobante, nss);
                String ultimoNrp = "";
                try {
                    LOGGER.debug(" titular.getIdPersona(): " + titular.getIdPersona());
                    LOGGER.debug(" seg.getModalidad().getIdModalidad(): " + seg.getModalidad().getIdModalidad());
                    ultimoNrp = registroPatronalServiceBusiness.obtenerNrpConvencionalPorDomicilioParticularYModalidad(
                            titular.getIdPersona(), ModalidadEnum.fromId(seg.getModalidad().getIdModalidad()).getNumModalidad());
                    LOGGER.debug(" ultimoNrp: " + ultimoNrp);
                } catch (GestionPatronalBusinessException ex) {
                    LOGGER.error("Error al obtener el nrp", ex);
                }

                comprobante = agregaDatosTitular(comprobante, titular.getIdPersona(), ultimoNrp);
                comprobante.setNrp(ultimoNrp);

            }
            
            comprobante.setFormaPago(FormaPagoEnum.fromId(compra.getFormaPago()).getDescripcion());

            String rfc = null;
            if (seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId()) {
                comprobante.setAseguramiento("Dom\u00e9stico");
                boolean aplica = obtenRespuestasExistentes(nss, tramite.getCuetionarios()) != null
                		? true : false;
                comprobante.setCuestionario(aplica);
            } else if (seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                LOGGER.info("entra como seguro 33 y se coloca como individual: "+seg.getModalidad().getIdModalidad());
				boolean aplica = tramite.getAplicaCuestionario() != null
						? tramite.getAplicaCuestionario() : false;
				comprobante.setAseguramiento("Individual");
				comprobante.setCuestionario(aplica);
                LOGGER.info("comprobante Cuestionario 2: "+comprobante.getCuestionario());
            } else {
                LOGGER.info("NO entra como 33 pero se coloca como individual: "+seg.getModalidad().getIdModalidad());
                comprobante.setAseguramiento("Individual");
                rfc = comprobante.getRfcFirma();
            }
            String folio = "";

            String tipoTramite;
			if (ditTRamite.getDicTipoTramite() != null) {
				Long idTipoTramite = ditTRamite.getDicTipoTramite().getCveIdTipoTramite();
				DicTipoTramite dicTipoTramite = em.find(DicTipoTramite.class, idTipoTramite);
				tipoTramite = dicTipoTramite.getDesTipoTramite();
			} else {
				tipoTramite = "";
			}
            folio = ditTRamite.getDitSolicitud().getRefFolio();
            if (StringUtils.trimToNull(folio) == null) {
                DitSolicitud ditSolicitud = em
                        .createQuery("Select sol from DitSolicitud sol Where "
                                + " sol.cveIdSolicitud = :idSol ", DitSolicitud.class)
                        .setParameter("idSol", Long.valueOf(ditTRamite.getDitSolicitud().getCveIdSolicitud()))
                        .getSingleResult();
                folio = ditSolicitud.getRefFolio();
            }
            comprobante = agregaDatosFirma(comprobante, rfc, folio, tipoTramite,
                    ditTRamite.getFecConclusion());

            LOGGER.debug("DATOS PARA PINTAR EN COMPROBANTE SEGURO -> \n"
    				+ ToStringBuilder.reflectionToString(comprobante,
    						ToStringStyle.MULTI_LINE_STYLE));
            
            comprobantes.add(comprobante);
        }
        comprobanteSeguro.setComprobante(comprobantes
                .toArray(new ComprobanteSeguroReporte[comprobantes.size()]));
                
        return comprobanteSeguro;
    }

    /**
     * Funcion que evalua si el el seguro actual es un cambio de modalidad de 35 a 43,44 o viceversa
     * @return
     */
    private Boolean esCambioModalidadIVRO(Long idPersona,DicModalidad modalidadSeguroActual ){

        if(Arrays.asList(IvroFactory.MOD_INDIVIDUAL).contains(modalidadSeguroActual.getCveIdModalidad())) {

            Persona persona = new Persona();
            persona.setIdPersona(idPersona);

            SeguroIvro seguroAnterior = consultaSeguroIvroLocal.buscaUltimoSeguroIVRO(persona);
            if (seguroAnterior != null) {
                if (seguroAnterior.getModalidad() != null) {
                    Modalidad modalidadSeguroAnterior = seguroAnterior.getModalidad();

                    LOGGER.info("Ultimo seguro encontrado: " + seguroAnterior.getCveIdSeguroIvro());
                    LOGGER.info("Modalidad de ultimo seguro encontrado: " + modalidadSeguroAnterior.getIdModalidad());
                    LOGGER.info("Modalidad de seguro actual: " + modalidadSeguroActual.getCveIdModalidad());

                    if(Arrays.asList(this.MOD_43_44).contains(modalidadSeguroAnterior.getIdModalidad()) &&
                            (modalidadSeguroActual.getCveIdModalidad()==ModalidadEnum.TREINTAYCINCO.getId() )){
                        return true;
                    } else if(Arrays.asList(this.MOD_43_44).contains(modalidadSeguroActual.getCveIdModalidad()) &&
                            (modalidadSeguroAnterior.getIdModalidad()==ModalidadEnum.TREINTAYCINCO.getId())){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }else{
            LOGGER.info("No es modalidad IVRO");
        }

        return false;
    }

    /**
     * Genera los datos del comprobante de cuetionarios aplicados a un unos
     * seguros
     *
     * @param seguros
     *            los seguros a generar sus comprobantes
     * @return los comprobantes de los seguros
     */
    public CuestionariosSeguroReporte generaDatosCuetionario(SegurosIvro seguros) {
        CuestionariosSeguroReporte cuestionarios = new CuestionariosSeguroReporte();
        List<CuestionarioSeguroReporte> cuestionariosAplicados = new ArrayList<CuestionarioSeguroReporte>();
        for (SeguroIvro seguro : seguros.getSeguroIvro()) {
        	DitSeguroIvro ditSeguroIvro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
			SeguroIvro seg = IvroFactory.generaSeguro(ditSeguroIvro);

            TramiteSeguroIvro tramite = seg.getTramite() != null ? seg.getTramite() : seguro.getTramite();
            TramiteSeguroIvroMod33 tramiteSeguroFamiliar = seg.getTramiteSeguroFamiliar() != null
            		? seg.getTramiteSeguroFamiliar() : seguro.getTramiteSeguroFamiliar();
            TramiteSeguroIvroMod40 tramiteContVolunt = seg.getTramiteContVoluntaria() != null
              		? seg.getTramiteContVoluntaria() : seguro.getTramiteContVoluntaria();

            String nss = "";
            RespuestasCuestionario respuestas;
            if(tramite != null) {
            	LOGGER.debug("Tramite seguro {}", tramite);
                LOGGER.debug("Tramite seguro {}", ReflectionToStringBuilder.toString(tramite));

            	nss = tramite.getBeneficiarios()[0].getNss();
            	respuestas = obtenRespuestas(nss, tramite.getCuetionarios());
            } else  if(tramiteSeguroFamiliar != null){
            	LOGGER.debug("Tramite seguro {}", tramiteSeguroFamiliar);
                LOGGER.debug("Tramite seguro {}", ReflectionToStringBuilder.toString(tramiteSeguroFamiliar));

            	nss = tramiteSeguroFamiliar.getBeneficiarios()[0].getNss();
            	respuestas = obtenRespuestas(nss, tramiteSeguroFamiliar.getCuetionarios());
            } else  if(tramiteContVolunt != null){
            	LOGGER.debug("Tramite seguro {}", tramiteContVolunt);
                LOGGER.debug("Tramite seguro {}", ReflectionToStringBuilder.toString(tramiteContVolunt));

            	nss = tramiteContVolunt.getBeneficiarios()[0].getNss();
            	respuestas = obtenRespuestas(nss, tramiteContVolunt.getCuetionarios());
            } else {
            	respuestas = null;
            }

            if (respuestas != null) {
                CuestionarioSeguroReporte cuestionario = new CuestionarioSeguroReporte();
                cuestionario.setRespuestasCuestionario(respuestas);
                cuestionario = agregaDatosBeneficiario(nss, cuestionario);
                if (seg.getModalidad() != null
                        && seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO
                                .getId()) {
                    cuestionario.setMod34(MARCA_RESPUESTA);
                } else if (seg.getModalidad() != null
                        && seg.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO
                                .getId()) {
                    cuestionario.setMod35(MARCA_RESPUESTA);
                } else if (seg.getModalidad() != null
                        && seg.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYTRES
                                .getId()) {
                    cuestionario.setMod43(MARCA_RESPUESTA);
                } else if (seg.getModalidad() != null
                        && seg.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO
                                .getId()) {
                    cuestionario.setMod44(MARCA_RESPUESTA);
                }
                cuestionariosAplicados.add(cuestionario);
            }
        }
        cuestionarios.setCuestionarios(cuestionariosAplicados
                .toArray(new CuestionarioSeguroReporte[cuestionariosAplicados.size()]));
        return cuestionarios;
    }

    /**
     * Agrega los datos de un beneficiario para el reporte
     *
     * @param comprobante
     *            el comprobante a agregar los datos del beneficiario
     * @param nss
     *            el nss del beneficiario
     * @return el comprobante a imprimir su reporte
     */
    private ComprobanteSeguroReporte agregaDatosBeneficiario(ComprobanteSeguroReporte comprobante,
            String nss) {
        Fisica trabajador;
        try {
            LOGGER.info("Buscando trabajador con NSS {}", nss);
            trabajador = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
            comprobante.setCurp(trabajador.getCurp());
            comprobante.setEdad(calculaEdad(trabajador.getFechaNacimiento()));
            comprobante.setFecNacimiento(trabajador.getFechaNacimientoFormateada());
            comprobante.setNombre(IvroFactory.corrigeCadena(trabajador.getNombreCompleto()));
            comprobante.setNss(nss);
            comprobante.setSexo(trabajador.getSexo() != null ? trabajador.getSexo()
                    .getDescripcion() : ND);
        } catch (Exception e) {
            LOGGER.error("No se encontraron los datos del beneficiario", e);
        }
        return comprobante;
    }

    /**
     * Agrega los datos de un beneficiario para el reporte
     *
     * @param comprobante el comprobante a agregar los datos del beneficiario
     * @param idPersona id del titular
     * @param nrp Numero de registro patronal del titular del seguro
     * @return el comprobante a imprimir su reporte
     */
    private ComprobanteSeguroReporte agregaDatosTitular(ComprobanteSeguroReporte comprobante,
            Long idPersona, String nrp) {
        Fisica titular;
		LOGGER.debug("Id _Titular " + idPersona);

        try {
            titular = (Fisica) personaBusinessRemote
                    .getDatosComplementariosPersonaFisica(idPersona);
            comprobante.setCurpFirma(titular.getCurp());
            comprobante.setNombreFirma(titular.getNombreCompleto());
            comprobante.setRfcFirma(titular.getRfc());
            SujetoObligado patron = sujetoObligadoServiceBusiness
                    .consultarPorNumeroRegistroPatronal(nrp);
            LOGGER.error("Patron encontrado {}", patron);
            patron = sujetoObligadoServiceBusiness.obtenerDetalleRP(patron);
            CentroTrabajo centroTrabajoPatron = patron.getCntroTrabajo();
            String strNumModalidad = null;

            if(patron.getModalidad() != null){
            	strNumModalidad = patron.getModalidad().getNumModalidad();
            }
            LOGGER.debug("=======>Modalidad del comprobante " + strNumModalidad);

            if(StringUtils.isNotBlank(strNumModalidad) && strNumModalidad.trim().equals(ModalidadEnum.TREINTAYCUATRO.getNumModalidad())) {
            	 LOGGER.debug("Modalidad del CT 34");
            	 LOGGER.debug("El domcilio ", patron);
            	if (centroTrabajoPatron != null) {
                    comprobante.setDomicilio("Calle "
                            + getCadenaNoNula(centroTrabajoPatron.getCalle() != null ? centroTrabajoPatron.getCalle() : "" ) + " "
                            + getCadenaNoNula((centroTrabajoPatron.getNumExterior1() != null && !centroTrabajoPatron.getNumExterior1().equals(0)) ?
                            		"Num. " + centroTrabajoPatron.getNumExterior1().toString() : "") + " "
                            + getCadenaNoNula(centroTrabajoPatron.getNumExteriorAlf() != null ? centroTrabajoPatron.getNumExteriorAlf() : "") + " " + "Colonia "
                            + getCadenaNoNula((centroTrabajoPatron.getAsentamiento() != null
                            && centroTrabajoPatron.getAsentamiento().getNombre() != null)  ? centroTrabajoPatron.getAsentamiento().getNombre() : "")
                            + " CP " + getCadenaNoNula(centroTrabajoPatron.getCodigoPostal() != null ?
                            		centroTrabajoPatron.getCodigoPostal().getCodigoPostal() : ""));
                }
            } else {
            	 LOGGER.debug("Modalidad del Titular " + strNumModalidad);
				 LOGGER.debug("Id_Titular" + idPersona);
				 mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio=domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(idPersona);
            	if (domicilio!= null) {
				   comprobante.setDomicilio("Calle "
                            + getCadenaNoNulaVacia(domicilio.getCalle()) + " "
                            + "Num. " + getCadenaNoNulaVacia(domicilio.getNumExterior1() != null ?
                                    domicilio.getNumExterior1().toString() : "") + " "
                            + getCadenaNoNulaVacia(domicilio.getNumExteriorAlf()) + " " 
                            + "Num Int. " + getCadenaNoNulaVacia(domicilio.getNumInterior() != null ?
                                    domicilio.getNumInterior().toString() : "") + " "
                            + getCadenaNoNulaVacia(domicilio.getNumInteriorAlf()) + " " 
                            + "Colonia "+ getCadenaNoNulaVacia(domicilio.getColonia())
                            + " CP " + getCadenaNoNulaVacia(domicilio.getCodigoPostal() != null ?
                                    domicilio.getCodigoPostal() : ""));
                }
            }

            if (patron.getSubdelegacion() != null
                    && patron.getSubdelegacion().getDelegacion() != null) {
                comprobante.setSubdelegacion(patron.getSubdelegacion().getDescripcion());
                comprobante.setDelegacion(patron.getSubdelegacion().getDelegacion()
                        .getDescripcion());
            }
        } catch (Exception e) {
            LOGGER.error("No se encontraron los datos del titular", e);
        }
        return comprobante;
    }
    
	private ComprobanteSeguroReporte agregaDatosTitularMod33y40(
			ComprobanteSeguroReporte comprobante, mx.gob.imss.digital.modelo.persona.Fisica titular, String nrp) {

		Fisica titularTmp = null;

		try {
			titularTmp = (Fisica) personaBusinessRemote
					.getDatosComplementariosPersonaFisica(titular
							.getIdPersona());

			comprobante.setCurpFirma(titularTmp.getCurp());
			comprobante.setNombreFirma(titularTmp.getNombreCompleto());
			comprobante.setRfcFirma(titularTmp.getRfc());

			SujetoObligado patron = sujetoObligadoServiceBusiness
					.consultarPorNumeroRegistroPatronal(nrp);
			
			LOGGER.debug("Patron encontrado {}", patron);
			patron = sujetoObligadoServiceBusiness.obtenerDetalleRP(patron);

			String strNumModalidad = null;

			if (patron.getModalidad() != null) {
				strNumModalidad = patron.getModalidad().getNumModalidad();
			}
			LOGGER.debug("=======>Modalidad del comprobante " + strNumModalidad);

			LOGGER.debug("Modalidad del Titular " + strNumModalidad);

			mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio = titular.getDomicilioParticular();
			
			StringBuffer descDomicilio = new StringBuffer();
			
			descDomicilio.append("Calle ");
			descDomicilio.append(fixUnescapeXml(StringEscapeUtils.unescapeXml(getCadenaNoNula(domicilio.getCalle()))));
			LOGGER.debug("CALLE MODIFICADA:" + descDomicilio.toString());
			descDomicilio.append(" Num. Ext. ");
			if (domicilio.getNumExterior1() != null 
					&& domicilio.getNumExterior1().intValue() != 0) {
				descDomicilio.append(domicilio.getNumExterior1());
			}
			if(StringUtils.isNotBlank(domicilio.getNumExteriorAlf())) {
				descDomicilio.append(" ");
				descDomicilio.append(fixUnescapeXml(StringEscapeUtils.unescapeXml(domicilio.getNumExteriorAlf())));
			}
			if ((domicilio.getNumInterior() != null 
					&& domicilio.getNumInterior().intValue() != 0)
					|| (StringUtils.isNotBlank(domicilio.getNumInteriorAlf()))) {
				descDomicilio.append(" Num. Int. ");
				if (domicilio.getNumInterior() != null 
						&& domicilio.getNumInterior().intValue() != 0) {
					descDomicilio.append(domicilio.getNumInterior());
				}
				if(StringUtils.isNotBlank(domicilio.getNumInteriorAlf())) {
					descDomicilio.append(" ");
					descDomicilio.append(fixUnescapeXml(StringEscapeUtils.unescapeXml(domicilio.getNumInteriorAlf())));
				}
				
			}
			descDomicilio.append(" Colonia ");
			if(domicilio.getAsentamiento() != null && 
					StringUtils.isNotBlank(domicilio.getAsentamiento().getNombre())) {
				descDomicilio.append(domicilio.getAsentamiento().getNombre());	
			} else {
				descDomicilio.append(getCadenaNoNula(domicilio.getColonia()));
			}
			descDomicilio.append(" CP ");
			descDomicilio.append(getCadenaNoNula(domicilio.getCodigoPostal() != null ? domicilio
					.getCodigoPostal() : ""));
					
			comprobante.setDomicilio(descDomicilio.toString());

			if (patron.getSubdelegacion() != null
					&& patron.getSubdelegacion().getDelegacion() != null) {
				comprobante.setSubdelegacion(patron.getSubdelegacion()
						.getDescripcion());
				comprobante.setDelegacion(patron.getSubdelegacion()
						.getDelegacion().getDescripcion());
			}
		} catch (Exception e) {
			LOGGER.error("No se encontraron los datos del titular");
		}
		
		return comprobante;
	}

    /**
     * Calcula la edad de una persona a partir de su fecha de nacimiento
     *
     * @param fechaNac
     *            fecha de nacimiento
     * @return la edad de la persona
     */
	private Integer calculaEdad(Date fechaNac) {
		if (fechaNac != null) {

			Calendar calNacimiento = Calendar.getInstance();
			calNacimiento.setTime(fechaNac);

			Calendar calFechaActual = Calendar.getInstance();

			int diferencia = calFechaActual.get(Calendar.YEAR) - calNacimiento.get(Calendar.YEAR);
			
			if (calNacimiento.get(Calendar.MONTH) > calFechaActual.get(Calendar.MONTH)
					|| (calNacimiento.get(Calendar.MONTH) == calFechaActual.get(Calendar.MONTH) 
					&& calNacimiento.get(Calendar.DATE) > calFechaActual.get(Calendar.DATE))) {
				diferencia--;
			}

			return diferencia;

		}
		
		return null;
	}

    /**
     * Verifica que aparir de una lista de cuestionarios asociados cual le pertenece a al nss ingresado
     * @param nss numero de seguridad del beneficiario
     * @param cuestionarios lista de cuestionarios aplicados
     * @return la respuestas del cuestionario que aplicaron para el nss
     */
    private RespuestasCuestionario obtenRespuestas(String nss, PersonaCuestionario[] cuestionarios) {
        RespuestasCuestionario respuestas = obtenRespuestasExistentes(nss, cuestionarios);
        return respuestas;
    }

    /**
     * Verifica que aparir de una lista de cuestionarios asociados cual le pertenece a al nss ingresado
     * @param nss numero de seguridad del beneficiario
     * @param cuestionarios lista de cuestionarios aplicados
     * @return la respuestas del cuestionario que aplicaron para el nss
     */
    private RespuestasCuestionario obtenRespuestasExistentes(String nss,
            PersonaCuestionario[] cuestionarios) {
        RespuestasCuestionario respuestas = null;
        if (cuestionarios != null) {
            for (PersonaCuestionario cuestionario : cuestionarios) {
                String nssCuestionario = StringUtils.trimToEmpty(cuestionario.getNssPersona());
                if (StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(nss),
                        StringUtils.trimToEmpty(nssCuestionario))
                        || StringUtils.trimToNull(nssCuestionario) == null) {
                    respuestas = cuestionario.getRespuestasCuestionario();
                }
            }
        }
        return respuestas;
    }

    /**
     * Agrega los datos personales de un trabajador al cuestionario
     *
     * @param nss numero de seguridad del beneficiario
     * @param cuestionario cuestionario generado
     * @return el cuestionario con los datos del beneficiario
     */
    private CuestionarioSeguroReporte agregaDatosBeneficiario(String nss,
            CuestionarioSeguroReporte cuestionario) {
        try {
            LOGGER.info("Buscando trabajador con NSS {}", nss);
            Fisica trabajador = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
            cuestionario.setApMaterno(trabajador.getSegundoApellido());
            cuestionario.setApPaterno(trabajador.getPrimerApellido());
            cuestionario.setNombres(trabajador.getNombre());
            cuestionario.setCurp(trabajador.getCurp());
            Integer edad = calculaEdad(trabajador.getFechaNacimiento());
            if (edad != null) {
                cuestionario.setEdad(edad.toString());
            }
            cuestionario.setFechaNacimiento(trabajador.getFechaNacimientoFormateada());
            if (trabajador.getSexo() != null
                    && trabajador.getSexo().getIdSexo() == SexoEnum.MUJER.getId()) {
                cuestionario.setSexFem(MARCA_RESPUESTA);
            } else {
                cuestionario.setSexMas(MARCA_RESPUESTA);
            }
            cuestionario.setNss(nss);
            cuestionario.setTitular(MARCA_RESPUESTA);
            cuestionario.setEstCivil(trabajador.getEstadoCivil() != null ? trabajador
                    .getEstadoCivil().getDescripcion() : "");

            cuestionario.setLugNacimiento(trabajador.getLugarNacimiento() != null ? trabajador
                    .getLugarNacimiento().getNombre() : "");
            try {
                List<Long> tiposDomicilio = new ArrayList<Long>();
                tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
                trabajador.setTipoPersona(new TipoPersona());
                trabajador.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
                List<Domicilio> domicilios = domicilioServices.obtenerDomiciliosPersonaPorTipo(
                        trabajador, tiposDomicilio);
                if (domicilios != null && !domicilios.isEmpty()) {
                    cuestionario = agregaDatosDomicilio(cuestionario, domicilios.get(0));
                }
            } catch (Exception e) {
                LOGGER.debug("El trabaador no cuenta con domicilio", e);
            }
        } catch (Exception e) {
            LOGGER.error("No se encontraron los datos del beneficiario", e);
        }
        return cuestionario;
    }

    /**
     * Agrega los datos del domicilio de un trabajador
     *
     * @param cuestionario
     *            el cuestionario a donde se agregan los datos
     * @param domicilio
     *            el domicilio
     * @return el cuestionario con los datos deldomicilio
     */
    private CuestionarioSeguroReporte agregaDatosDomicilio(CuestionarioSeguroReporte cuestionario,
            Domicilio domicilio) {

        cuestionario.setCodPostal(domicilio.getCodigoPostal() != null ? domicilio.getCodigoPostal()
                .getCodigoPostal() : "");

        cuestionario.setCalleIOManzana(domicilio.getVialidadPrimaria() != null ? domicilio
                .getVialidadPrimaria().getNombre() : "");
        cuestionario.setColonia(domicilio.getAsentamiento() != null ? domicilio.getAsentamiento()
                .getNombre() : "");

        cuestionario.setNumero(getCadenaNoNula(domicilio.getNumExterior1() != null ? domicilio
                .getNumExterior1().intValue() + "" : "")
                + " "
                + (domicilio.getNumExteriorAlf() != null ? domicilio.getNumExteriorAlf() : ""));
        if (domicilio.getAsentamiento() != null
                && domicilio.getAsentamiento().getLocalidad().getMunicipio() != null) {
            Municipio municipio = domicilio.getAsentamiento().getLocalidad().getMunicipio();
            cuestionario.setEstado(municipio.getEntidadFederativa() != null ? municipio
                    .getEntidadFederativa().getNombre() : "");
            cuestionario.setPoblacion(municipio.getNombre());
            cuestionario.setCveDeleg(municipio.getClave());
        }
        return cuestionario;
    }

    /**
     * Agrega los datos de la firma electronica a un comprobante
     * @param comprobante el comprobante del seguro
     * @param rfc RFC del solicitante
     * @param folio numero de solicitud
     * @param tipoTramite tipo del tramite del seguro (Compra, renovacion)
     * @param fechaSeg fecha de generacion
     * @return el comprobante con los datos de la firma
     */
    private ComprobanteSeguroReporte agregaDatosFirma(ComprobanteSeguroReporte comprobante,
            String rfc, String folio, String tipoTramite, Date fechaSeg) {
        String fecha = DateUtils.dateToStringConFormato(fechaSeg, FORMATO_FECHA_MIN);
        String cadenaOriginal = generaCadena(comprobante, rfc, folio, tipoTramite, fecha);
        RespuestaFirmadoSimple respuesta = firmaDigital.getSelloDigital(cadenaOriginal, null, null);
        comprobante.setCadenaOriginal(cadenaOriginal);
        comprobante.setSelloDigital(respuesta.getSello());
        comprobante.setSecuenciaNotarial(respuesta.getTramite());
        comprobante.setNumeroSerie(respuesta.getNoSerie());
        comprobante.setNombreFirma(folio);
        comprobante.setRfcFirma(fecha);
        return comprobante;
    }

    /**
     * MEtodo que genera la cadena original a partir de los datos del comprobante
     * @param comprobante comprobante a generar la cadena original
     * @param rfc del solicitanta
     * @param folio numero de solicitud
     * @param tipoTramite el tipo de tramite del comprobante
     * @param fecha la fecha de generacion
     * @return la cadena original generada
     */
    private String generaCadena(ComprobanteSeguroReporte comprobante, String rfc, String folio,
            String tipoTramite, String fecha) {
        StringBuffer cadena = new StringBuffer("||Invocante:portalimssdigital|").append("Tr\u00E1mite:")
                .append(tipoTramite).append("|").append("Fecha:").append(fecha).append("|")
                .append("Folio:").append(folio).append("|").append("NRP:")
                .append(comprobante.getNrp()).append("|");
        if (StringUtils.trimToNull(rfc) != null) {
            cadena.append("RFC:").append(rfc).append("|");
        }
        cadena.append("Nombre o Raz\u00f3n  Social:").append(comprobante.getNombre()).append("|");
        if (StringUtils.trimToNull(comprobante.getCurp()) != null) {
            cadena.append("CURP:").append(comprobante.getCurp()).append("|");
        }
        cadena.append("NSS:").append(comprobante.getNss()).append("||");

        return cadena.toString();
    }
    
    /**
     * Obtiene la etiqueta pra el periodo
     *
     * @param fechaInicio
     *            la fecha de inicio del periodo
     * @param fechaFin
     *            la fecha fin del periodo
     * @return el periodo del seguro
     */
    private String getEtiquetaPeriodo(Date fechaInicio, Date fechaFin) {
        StringBuilder periodo = new StringBuilder("De ")
                .append(DateUtils.dateToStringConFormato(fechaInicio, FORMATO_FECHA)).append(" a ")
                .append(DateUtils.dateToStringConFormato(fechaFin, FORMATO_FECHA));
        return periodo.toString();
    }
    
    /**
     * Genera el mapa de parametros para el reporte a partir de la clase scon
     * sus valores.
     *
     * @param comprobante
     *            el comprobante a obtener sus valores
     * @return el mapa de parametro
     */
    private Map<String, Object> convierteDatosReporte(ComprobanteSeguroReporte comprobante) {
        Field[] fileds = comprobante.getClass().getDeclaredFields();
        Map<String, Object> parametros = new HashMap<String, Object>();
        for (Field field : fileds) {
            try {
                field.setAccessible(true);
                parametros.put(field.getName(), field.get(comprobante));
                LOGGER.info("Agregando parametro {}", field.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parametros;
    }
    
	private boolean aplicoCuestionario(SegurosIvro seguros) {
		boolean aplica = false;

		for (SeguroIvro seguro : seguros.getSeguroIvro()) {
			if (seguro.getTramite() != null
					&& seguro.getTramite().getAplicaCuestionario() != null) {
				aplica = seguro.getTramite().getAplicaCuestionario();
			} else if (seguro.getTramiteSeguroFamiliar() != null
					&& seguro.getTramiteSeguroFamiliar().getAplicaCuestionario() != null) {
				aplica = seguro.getTramiteSeguroFamiliar().getAplicaCuestionario();
			}
		}

		return aplica;
    }
    
    /**
     * Se obtienen datos de respuesta del cuestionario
     * @param seguros
     * @return
     */
    private CuestionarioVO obtenerDatosComplementariosCuestionario(SegurosIvro seguros){
    	CuestionariosSeguroReporte cuestsReport = serviceGeneraCuestionario(seguros);
        CuestionarioSeguroReporte[] cuestsRespuestas = cuestsReport.getCuestionarios();
        CuestionarioVO cuestFully = null;
        for (CuestionarioSeguroReporte cuestCurrent : cuestsRespuestas) {
            // Llenar el cuestionarioVO con los datos recibidos del servicio
            cuestFully = fillCuestionario(cuestCurrent);
         }
        return cuestFully;
    }
    
    /**
     * Genera cuestionario.
     * 
     * @param seguros
     *            the seguros
     * @return the cuestionarios seguro reporte
     */
    private CuestionariosSeguroReporte serviceGeneraCuestionario(SegurosIvro seguros) {
        CuestionariosSeguroReporte cuestionariosReport = new CuestionariosSeguroReporte();
        try {
            cuestionariosReport = generaDatosCuetionario(seguros);
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }
        return cuestionariosReport;
    }
    
    /**
     * Fill cuestionario.
     * 
     * @author Dj Leo 3/12/2014
     * @param cuestIVROReceived
     *            the cuest ivro received
     * @return the cuestionario vo
     */
    private CuestionarioVO fillCuestionario(CuestionarioSeguroReporte cuestIVROReceived) {
        LOGGER.info("cuestionario  filling.. :");
        // Obtenemos las respuestas principales del cuestionario datos del
        // titular
        RespuestasCuestionario respCuest = cuestIVROReceived.getRespuestasCuestionario();
        // Obtenemos las respuestas a el cuestionario seccion uno y seccion dos
        Respuesta[] respuestas = respCuest.getRespuestas();
        LOGGER.info("cuestionario  :" + respCuest.getSumatoriaRespuestas());
        for (Respuesta resp : respuestas) {
            LOGGER.info(" respuesta valores idRep: " + resp.getNumPregunta() + " , seccion:"
                    + resp.getNumSeccion() + ", respuesta:" + resp.getValores());
            for (Opcion opcionP : resp.getValores()) {
                LOGGER.info(" respuesta: " + opcionP.getDescripcion() + " , value:"
                        + opcionP.getValor() + ", clave:" + opcionP.getClave());
            }
        }
        CuestionarioVO ctVO = new CuestionarioVO();
        // Llenado el vo Cuestionarios para plantarle los valores al PDF
        ctVO = ctVO.fill(cuestIVROReceived);
        return ctVO;
    }
    
    private Map<String, Object> agregarDatosCuestionario( Map<String, Object> parametros, SegurosIvro seguros){
    	 boolean aplicoCuestionario = aplicoCuestionario(seguros);
         parametros.put("aplicaCuestionario", aplicoCuestionario);
         
         if(aplicoCuestionario){
            PropertiesOpciones properties = new PropertiesOpciones();
            Map<String, String> opciones = properties.getOpciones();
            String pathReports = (String) opciones.get(PATH_REPORTS);
            String reporteIvroCuestionarioV1 = (String) opciones.get(MAIN_REPORT_CUEST);
            String reporteIvroCuestionarioV2 = (String) opciones.get(SUBREPORT_CUEST);
            String reporteIvroCuestionarioV3 = (String) opciones.get(SUBREPORT2_CUEST);
            
         	parametros.put(DS_REPORTE, obtenerDatosComplementariosCuestionario(seguros));         	
         	parametros.put(REPORTE_FILE, CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
         			+ reporteIvroCuestionarioV1));
         	parametros.put(SUBREPORTE_FILE, CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
         			+ reporteIvroCuestionarioV2));
         	parametros.put(SUBREPORTE2_FILE,CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
         			+ reporteIvroCuestionarioV3));
         }
         return parametros;
    }
    
    private String fixUnescapeXml(String cadenaOriginal){
    	if(cadenaOriginal == null){
    		return null;
    	}
    	String cadena = cadenaOriginal.replaceAll("&#225;", "\u00E1");//á
    	cadena = cadena.replaceAll("&#193;", "\u00c1");//Á
    	cadena = cadena.replaceAll("&#233;", "\u00e9");//é
    	cadena = cadena.replaceAll("&#201;", "\u00c9");//É
    	cadena = cadena.replaceAll("&#237;", "\u00ed");//í
    	cadena = cadena.replaceAll("&#205;", "\u00cd");//Í
    	cadena = cadena.replaceAll("&#243;", "\u00f3");//ó
    	cadena = cadena.replaceAll("&#211;", "\u00d3");//Ó
    	cadena = cadena.replaceAll("&#250;", "\u00fa");//ú
    	cadena = cadena.replaceAll("&#218;", "\u00da");//Ú
    	cadena = cadena.replaceAll("&#252;", "\u00fc");//ü
    	cadena = cadena.replaceAll("&#220;", "\u00dc");//Ü
    	cadena = cadena.replaceAll("&#241;", "\u00f1");///ñ
    	cadena = cadena.replaceAll("&#209;", "\u00d1");//Ñ
    	
    	cadena = cadena.replaceAll("&lt;", "\u003c");//<
    	cadena = cadena.replaceAll("&gt;", "\u003e");//>
    	cadena = cadena.replaceAll("&apos;", "\u0027");//'
    	cadena = cadena.replaceAll("&quot;", "\"");//"
    	cadena = cadena.replaceAll("&#180;", "\u00b4");//´
    	
    	return cadena;
    }
}
