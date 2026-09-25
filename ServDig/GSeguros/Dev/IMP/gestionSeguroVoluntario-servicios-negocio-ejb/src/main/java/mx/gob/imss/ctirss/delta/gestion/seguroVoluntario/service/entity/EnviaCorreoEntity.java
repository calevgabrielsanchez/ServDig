/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.EnviaCorreoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.TramiteIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DecimalFormat;
import java.util.*;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "enviaCorreoEntity", mappedName = "enviaCorreoEntity")
public class EnviaCorreoEntity implements EnviaCorreoLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnviaCorreoEntity.class);
    /**
     * Formato para las fechas
     */
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    /**
     * Formato para las fechas
     */
    private static final long MODALIDAD_34_ID = 16;
    /**
     * Subject de los correos
     */
    private static final String SUBJECT = "Aviso Seguro Voluntario IVRO";

    private static final String SUBJECT_CVRO = "Aviso Continuaci\u00f3n Voluntaria";

    private static final String SUBJECT_CVRO_RENOVACION = "Aviso Continuaci\u00f3n Voluntaria";

    private static final String SUBJECT_ISSF = "Aviso Seguro de Salud para la Familia";

    private static final String SUBJECT_ISSF_RENOVACION = "Aviso Seguro de Salud para la Familia";

    private static final String SUBJECT_IVRO = "Aviso Incorporaci�n Voluntaria al R�gimen Obligatorio ";

    private static final String SUBJECT_IVRO_RENOVACION = "Aviso Incorporaci�n Voluntaria al R�gimen Obligatorio ";

    /**
     * tipo de correo
     */
    private static final String CONTENT_TYPE = "text/html";

    private static final String MIME_PDF = "application/pdf";
    /**
     * Consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvroLocal;
    /**
     * Servicio para el envio de correos generico
     */
    @EJB(name = "EMailQProducer", mappedName = "EMailQProducer")
    private EMailProducer eMailProducer;
    @EJB(name = "mediosContactoServiceBusiness", mappedName = "mediosContactoServiceBusiness")
    private MediosContactoServiceBusinessRemote contactoServiceBusinessRemote;
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;
    @EJB
    private TramiteIvroServiceLocal tramiteIvroService;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    /**
     * Servicio para consultar cotizaciones
     */
    @EJB(mappedName = "cotizacionServiceBusiness")
    private CotizacionServiceRemote cotizacionServiceRemote;
    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    /**
     * Servicios de domicilios
     */
    @EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;
    @EJB(name = "parametrosServiceBusiness", mappedName = "parametrosServiceBusiness")
    private ParametrosServiceBusinessRemote parametrosService;
    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * EnviaCorreoLocal
     * #enviaCorreo(mx.gob.imss.digital.modelo.seguros.SeguroIvro, int, String)
     */
    @Override
    public void enviaCorreo(SeguroIvro seguro, int tipo) {
        seguro = consultaSeguroIvroLocal.buscaSeguroPorId(seguro.getCveIdSeguroIvro());
        Fisica titular = seguro.getTitular();
        String correo = obtenCorreo(titular);
        boolean esCompra = true;
        // Sin correo no hacemos nada
        if (StringUtils.trimToNull(correo) != null) {
            try {
                EmailPayloadType emailRequest = new EmailPayloadType();
                emailRequest.setContentType(CONTENT_TYPE);
                emailRequest.setTo(correo);
                String correosSegsMonitorBcc =parametrosService.obtenerParametroDeConfiguracion(ParametroSistemaEnum.CORREOS_SEGUROS_MONITOR_BCC.getCodigo());
                if(correosSegsMonitorBcc != null && !correosSegsMonitorBcc.isEmpty()){
                    emailRequest.setBcc(correosSegsMonitorBcc);
                }
                if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
                    if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getRenovacion()) {
                        emailRequest.setSubject(SUBJECT_CVRO_RENOVACION);
                        esCompra = false;
                    } else {
                        emailRequest.setSubject(SUBJECT_CVRO);
                        esCompra = true;
                    }
                } else if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                    if (seguro.getEnRenovacion()) {
                        emailRequest.setSubject(SUBJECT_ISSF_RENOVACION);
                        esCompra = false;
                    } else {
                        emailRequest.setSubject(SUBJECT_ISSF);
                        esCompra = true;
                    }
                } else if ((seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId())||
                        (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId())||
                        (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId())){

                    if (seguro.getTramite() != null && seguro.getTramite().getRenovacion()) {
                        emailRequest.setSubject(SUBJECT_IVRO_RENOVACION);
                        esCompra = false;
                    } else {
                        emailRequest.setSubject(SUBJECT_IVRO);
                        esCompra = true;
                      }

                }  else {
                }
                emailRequest.setContent("");
                emailRequest.setParameters(obtenValores(seguro, tipo,esCompra));

                eMailProducer.agendarCorreoElectronico(emailRequest);
            } catch (Exception e) {
                LOGGER.error("Error generado al enviar el correo a una persona", e);
            }

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * EnviaCorreoLocal#enviaCorreos(java.util.List, int)
     */
    @Override
    public void enviaCorreos(List<SeguroIvro> seguros, int tipo) {
        for (SeguroIvro seguro : seguros) {
            enviaCorreo(seguro, tipo);
        }
    }

    /**
     * Obtiene el correo asociado a una persona
     *
     * @param titular el titular del seguro
     * @return el correo asociado a la persona
     */
    @Override
    public String obtenCorreo(Fisica titular) {
        Persona personaCorreo = new Persona();
        personaCorreo.setIdPersona(titular.getIdPersona());
        personaCorreo.setTipoPersona(new TipoPersona());
        personaCorreo.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
        String correo = null;
        try {
            List<MedioContacto> medios = contactoServiceBusinessRemote
                    .consultarMedioDeContactoPersona(personaCorreo);
            for (MedioContacto medio : medios) {
                if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
                        .equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
                    if (StringUtils.isNotBlank(correo)) {
                        correo += ", " + medio.getDesFormaContacto();
                    } else {
                        correo = medio.getDesFormaContacto();
                    }
                }
            }
        } catch (PersonaSinMedioDeContactoException e) {
            LOGGER.debug("Persona sin medios de contacto");
        }
        return correo;
    }

    private String obtenCorreo(List<String> correos) {
        String cadenaCorreos = "";
        for (String correo : correos) {
            if (StringUtils.isNotBlank(cadenaCorreos)) {
                cadenaCorreos += ", " + correo;
            } else {
                cadenaCorreos = correo;
            }
        }
        return cadenaCorreos;
    }

    /**
     * Obtiene el mapa de valores a escribir en el cuerpo del correo
     *
     * @param seguro el seguroa enviar correo
     * @param tipo el tipo de correo
     * @return los valores a agregar en el correo
     */
    private Map<String, String> obtenValores(SeguroIvro seguro, int tipo, boolean esCompra) {
        Map<String, String> parametros = new HashMap<String, String>();
        Fisica beneficiario;
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica trabajador = null;
        BigDecimal montoPagar = new BigDecimal(BigInteger.ZERO);
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        try {
            beneficiario = seguro.getTramite().getBeneficiarios()[0];
            trabajador = personaFisicaServiceBusiness
                    .localizarPersonaFisicaPorNss(beneficiario.getNss());
            parametros.put("curp", trabajador.getCurp());
            parametros.put("nombreCompleto", trabajador.getNombreCompleto().replace("#", "\u00D1"));
            parametros.put("nss", trabajador.getNss());
            if(esCompra){
                parametros.put("idTipoTramite", TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo()
                        .toString());
            }else{
                parametros.put("idTipoTramite", TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo()
                        .toString());
            }

        } catch (Exception e) {
            LOGGER.error("No se encontraron los datos del beneficiario");
        }
        Compra compra = seguro.getCompra();
        boolean tieneBeneficio = false;
        int umf = 0;
        try {
            Cotizacion cotizacion = cotizacionServiceRemote
                    .findCotizacion(compra.getIdCotizacion());
            parametros.put("cantidadPagada", formatter.format(cotizacion.getCuotaTotal()));
            tieneBeneficio = cotizacion.getDetalle().getConBeneficio();

            if (seguro.getModalidad().getIdModalidad() == MODALIDAD_34_ID) {
                umf = obtenerUMF(cotizacion.getDetalle().getNumeroRegistroPatronal(), trabajador.getIdPersona());
            } else if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
                parametros.put("idTipoTramite", obtenerTipoTramiteMod40(seguro));
                parametros.put("salarioBaseCotiz", obtenSalarioBase(cotizacion));
            } else if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                parametros.put("idTipoTramite", obtenerTipoTramiteMod33(seguro));
                parametros.put("nombreCompleto", seguro.getTitular().getNombreCompleto().replace("#", "\u00D1"));
                if (tipo == TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo()) {
                    List<SeguroIvro> buscaSegurosFamiliares = consultaSeguroIvroLocal.buscaNuevosSegurosFamiliares(seguro.getTitular());
                    for (SeguroIvro seguroFamiliar : buscaSegurosFamiliares) {
                        Cotizacion cotizacionFamiliar = cotizacionServiceRemote.findCotizacion(seguroFamiliar.getCompra().getIdCotizacion());
                        montoPagar = montoPagar.add(cotizacionFamiliar.getCuotaTotal(), MathContext.DECIMAL64);
                    }
                    parametros.put("cantidadPagada", formatter.format(montoPagar));
                }
            }

            if(seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
                parametros.put("fechaInicioVigencia",
                        DateUtils.dateToStringConFormato(seguro.getFechaInicio(), FORMATO_FECHA));
                parametros.put("fechaFinVigencia",
                        DateUtils.dateToStringConFormato(seguro.getFechaFin(), FORMATO_FECHA));
            } else if(tieneBeneficio
                    || compra.getFormaPago().longValue() == FormaPagoEnum.BIMESTRAL.getId()
            		|| (compra.getFormaPago().longValue() == FormaPagoEnum.MENSUAL.getId())){
                Pago pagoEfectuado;
            	if ( compra.getPagos()!=null && compra.getPagos().length > 0 )	{

            	    pagoEfectuado = compra.getPagos()[compra.getPagos().length - 1];

                    parametros.put("fechaInicioVigencia",
                            DateUtils.dateToStringConFormato(pagoEfectuado.getFechaInicioPeriodo(), FORMATO_FECHA));
                    parametros.put("fechaFinVigencia",
                            DateUtils.dateToStringConFormato(pagoEfectuado.getFechaFinPeriodo(), FORMATO_FECHA));
                    parametros.put("cantidadPagada", formatter.format(pagoEfectuado.getMonto()));
            	}

            }

        } catch (SUAException e) {
            LOGGER.error("No se encontro la cotizacion asociada");
        }
        parametros.put("modalidadAseguramiento",
                ModalidadEnum.fromId(seguro.getModalidad().getIdModalidad()).getNumModalidad()
                + " - " + seguro.getModalidad().getDescripcion());


        
        if(!tieneBeneficio && !(compra.getFormaPago().longValue() == FormaPagoEnum.BIMESTRAL.getId())
        		&& !(compra.getFormaPago().longValue() == FormaPagoEnum.MENSUAL.getId())){
            parametros.put("fechaInicioVigencia",
                    DateUtils.dateToStringConFormato(seguro.getFechaInicio(), FORMATO_FECHA));
            parametros.put("fechaFinVigencia",
                    DateUtils.dateToStringConFormato(seguro.getFechaFin(), FORMATO_FECHA));
        }

        parametros.put("tipoPago", FormaPagoEnum.fromId(compra.getFormaPago()).getDescripcion());
        parametros.put("tipoOperacion", "" + tipo);
        parametros.put("fechaOperacion",
                DateUtils.dateToStringConFormato(Calendar.getInstance().getTime(), FORMATO_FECHA));
        parametros.put("umf", String.valueOf(umf));

        return parametros;
    }

    /**
     * Para ordenar los pagos con respecto a la fecha limite de pago de forma
     * ascendente
     *
     * @param pagosSeguroIvro El array de pagos a ordenar
     */
    private void ordenarListaPagos(Pago[] pagosSeguroIvro) {
        Arrays.sort(pagosSeguroIvro, new Comparator<Pago>() {
            @Override
            public int compare(Pago pago1, Pago pago2) {
                return pago1.getFechaLimitePago().compareTo(pago2.getFechaLimitePago());
            }
        }
        );
    }
    
    /**
     * Ordena el arreglo de pagos por fecha de fin de periodo 
     * para poder mostrar de forma correcta el campo periodo de aseguramiento del correo
     */
    private void ordenarPagosPorFinPeriodo(Pago[] pagosSeguroIvro) {
        Arrays.sort(pagosSeguroIvro, new Comparator<Pago>() {
            @Override
            public int compare(Pago pago1, Pago pago2) {
                return pago1.getFechaFinPeriodo().compareTo(pago2.getFechaFinPeriodo());
            }
        }
        );
    }

    @Override
    public UnidadMedicaFamiliar getUmfByIdPersona(Long idPersona) {
        UnidadMedicaFamiliar unidadMedicaFamiliar = null;

        try {
            String nss = personaBusinessRemote.obtenerNssPersona(idPersona);
            unidadMedicaFamiliar = tramiteIvroService.getUnidadMedicoFamiliarByAsignacion(nss, idPersona);
        } catch (PersonaConVariosNSSException e) {
            LOGGER.error("La persona cuenta con mas de un nss, no se devuelve la umf");
        } catch (PersonaSinNSSException e) {
            LOGGER.error("La persona cuenta con mas de un nss, no se devuelve la umf");
        }

        return unidadMedicaFamiliar;
    }

    private int obtenerUMF(String nrp, Long idPersona) {
        int umf = 0;

        // Se valida si el trabajador tiene una UMF asociada. Si no tiene UMF
        // asociada se coloca la UMF del centro de trabajo
        LOGGER.debug("========================>> Comienza Validacion de UMF");
        UnidadMedicaFamiliar umfTrabajador = getUmfByIdPersona(idPersona);
        try {
            SujetoObligado patron = getSujetoObligado(nrp);

            LOGGER.debug("==>> UMF Trabajador" + umfTrabajador);
            if (umfTrabajador != null) {
                Integer cizTrabajador = umfTrabajador.getSubdelegacion()
                        .getDelegacion().getCiz();
                Integer cizCentroTrabajo = patron.getSubdelegacion().getDelegacion().getCiz();

                // Se valida la umf del trabajador. Si el ciz de la UMF localizada
                // coinicide con el ciz del Centro de Trabajo, se asocia la UMF del
                // trabajador; en caso contrario se asocia la UMF del Centro de
                // Trabajo
                if (cizTrabajador != null && cizTrabajador != 0
                        && cizCentroTrabajo != 0
                        && cizTrabajador.equals(cizCentroTrabajo)) {
                    umf = umfTrabajador.getNoEconomico().intValue();
                    LOGGER.debug("El trabajador tiene asociada la UMF " + umf
                            + " y coincide con el CIZ del patron: ");
                } else {

                    UnidadMedicaFamiliar umfCentroTrabajo = getUmfByCentroTrabajo(patron);
                    if (umfCentroTrabajo != null) {
                        umf = umfCentroTrabajo.getNoEconomico().intValue();
                        LOGGER.debug("El trabajador tiene asociada la UMF pero NO coincide con el CIZ del patron, se utilizara la UMF: "
                                + umf);
                    }
                }
            } else {
                UnidadMedicaFamiliar umfCentroTrabajo = getUmfByCentroTrabajo(patron);
                if (umfCentroTrabajo != null) {
                    umf = umfCentroTrabajo.getNoEconomico().intValue();
                    LOGGER.debug("El trabajador tiene asociada la UMF del patron, se utilizara la UMF: "
                            + umf);
                }
            }
        } catch (IvroException e) {
            LOGGER.error(e.getMessage());
        }
        return umf;
    }

    /**
     * Obtiene el Sujeto obligado a partir del registro patronal
     *
     * @param rp el numero de registro patronal
     * @return el patron encontrado
     * @throws IvroException error al no eonctrar el patron
     */
    private SujetoObligado getSujetoObligado(String rp) throws IvroException {
        // BUscamos el sujeto obligado por su registro patronal
        LOGGER.debug("BUscado al patron {}", new Date());

        SujetoObligado patron = sujetoObligadoServiceBusiness
                .consultarPorNumeroRegistroPatronal(rp);
        LOGGER.debug("Patron encontrado {}", patron);
        if (patron == null) {
            throw new IvroException(IvroConstants.COD_NO_PATRON, IvroConstants.MSG_NO_PATRON);
        }
        LOGGER.debug("Se enontro y se busca su detalle al patron {}", new Date());
        // Agregamos los datos complementarios del sujeto obligado
        patron = sujetoObligadoServiceBusiness.obtenerDetalleRP(patron);
        LOGGER.debug("patron  completo{}", new Date());
        return patron;
    }

    private UnidadMedicaFamiliar getUmfByCentroTrabajo(SujetoObligado patron) {
        UnidadMedicaFamiliar unidadMedicaFamiliar = null;

        /*
		 * Se busca UMF a partir del domicilio del patron, para este caso el
		 * patron siempre es persona fisica
         */
        if (patron.getCntroTrabajo() != null) {
            LOGGER.debug("Centro de trabajo domestico: " + patron.getCntroTrabajo());
            Domicilio domPatron = patron.getCntroTrabajo();
            String cp = null;

            if (domPatron.getCodigoPostal() != null
                    && StringUtils.isNotEmpty(domPatron.getCodigoPostal().getCodigoPostal())) {
                cp = domPatron.getCodigoPostal().getCodigoPostal();
                LOGGER.debug("Codigo postal 1: " + cp);
            } else if (domPatron.getAsentamiento() != null
                    && domPatron.getAsentamiento().getCodigoPostal() != null
                    && StringUtils.isNotEmpty(domPatron.getAsentamiento()
                            .getCodigoPostal().getCodigoPostal())) {
                cp = domPatron.getAsentamiento().getCodigoPostal().getCodigoPostal();
                LOGGER.debug("Codigo postal 2: " + cp);
            } else {
                LOGGER.debug("Codigo postal 3");
            }

            if (StringUtils.isNotEmpty(cp)) {
                LOGGER.debug("Se va a buscar UMF con el codigo postal " + cp
                        + " del domicilio del patron");
                try {
                    List<UnidadMedicaFamiliar> umfs = this.domicilioServiceBusiness
                            .getUmfByCodigoPostal(cp);
                    unidadMedicaFamiliar = umfs.get(0);

                    LOGGER.debug("Se localiza la UMF para el codigo postal " + cp);
                } catch (UmfNoLocalizadaException e) {
                    LOGGER.warn(e.getMessage());
                }
            } else {
                LOGGER.debug("El patron no cuenta con codigo postal en su domicilio para poder obtener la UMF, se asigna la UMF por default");
            }
        } else {
            LOGGER.debug("El patron no cuenta con domicilio para obtener la UMF, se asigna la UMF por default");
        }

        return unidadMedicaFamiliar;
    }

    @Override
    public void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]> adjuntos) {
        seguro = consultaSeguroIvroLocal.buscaSeguroPorId(seguro.getCveIdSeguroIvro());
        String correo = obtenCorreo(correos);
        boolean esCompra = true;
        // Sin correo no hacemos nada
        if (StringUtils.trimToNull(correo) != null) {
            try {
                EmailPayloadType emailRequest = new EmailPayloadType();
                emailRequest.setContentType(CONTENT_TYPE);
                String correosSegsMonitorBcc =parametrosService.obtenerParametroDeConfiguracion(ParametroSistemaEnum.CORREOS_SEGUROS_MONITOR_BCC.getCodigo());
                if(correosSegsMonitorBcc != null && !correosSegsMonitorBcc.isEmpty()){
                    emailRequest.setBcc(correosSegsMonitorBcc);
                }
                emailRequest.setTo(correo);
                if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
                    if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getRenovacion()) {
                        emailRequest.setSubject(SUBJECT_CVRO_RENOVACION);
                        esCompra = false;
                    } else {
                        emailRequest.setSubject(SUBJECT_CVRO);
                        esCompra = true;
                    }
                } else if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                    if (seguro.getTramiteSeguroFamiliar() != null && seguro.getTramiteSeguroFamiliar().getRenovacion()) {
                        emailRequest.setSubject(SUBJECT_ISSF_RENOVACION);
                        esCompra = false;
                    } else {
                        emailRequest.setSubject(SUBJECT_ISSF);
                        esCompra = true;
                    }

                } else if ((seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId())||
                            (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId())||
                            (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId())){

                        if (seguro.getTramite() != null && seguro.getTramite().getRenovacion()) {
                            emailRequest.setSubject(SUBJECT_IVRO_RENOVACION);
                            esCompra = false;
                        } else {
                            emailRequest.setSubject(SUBJECT_IVRO);
                            esCompra = true;
                        }

                } else {
                }
                emailRequest.setContent("");
                emailRequest.setParameters(obtenValores(seguro, tipo, esCompra));
                if (adjuntos != null && !adjuntos.isEmpty()) {
                    AttachmentContent[] contents = new AttachmentContent[adjuntos.size()];
                    int i = 0;
                    for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
                        String key = entry.getKey();
                        byte[] value = entry.getValue();
                        AttachmentContent atach = new AttachmentContent();
                        atach.setContentType(MIME_PDF);
                        atach.setContentDisposition(key.replace('/', '-'));
                        atach.setTextBody(Base64Cipher.simpleEncode(value));
                        contents[i] = atach;
                        i++;
                    }
                    emailRequest.setAttachments(contents);
                }
                eMailProducer.agendarCorreoElectronico(emailRequest);
            } catch (Exception e) {
                LOGGER.error("Error generado al enviar el correo a una persona", e);
            }

        }

    }

    private String obtenerTipoTramiteMod40(SeguroIvro seguro) {
        if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getTipoTramite() != null
                && seguro.getTramiteContVoluntaria().getTipoTramite().getIdTipoTramite() != null) {
            return seguro.getTramiteContVoluntaria().getTipoTramite().getIdTipoTramite().toString();
        } else if (seguro.getTramite() != null && seguro.getTramite().getTipoTramite() != null
                && seguro.getTramite().getTipoTramite().getIdTipoTramite() != null) {
            return seguro.getTramite().getTipoTramite().getIdTipoTramite().toString();
        } else if (seguro.getTramite().getRenovacion()) {
            return TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA.getCodigo().toString();
        } else {
            return TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo().toString();
        }
    }

    private String obtenerTipoTramiteMod33(SeguroIvro seguro) {
        if (seguro.getTramiteSeguroFamiliar() != null && seguro.getTramiteSeguroFamiliar().getTipoTramite() != null
                && seguro.getTramiteSeguroFamiliar().getTipoTramite().getIdTipoTramite() != null) {
            return seguro.getTramiteSeguroFamiliar().getTipoTramite().getIdTipoTramite().toString();
        } else if (seguro.getTramite() != null && seguro.getTramite().getTipoTramite() != null
                && seguro.getTramite().getTipoTramite().getIdTipoTramite() != null) {
            return seguro.getTramite().getTipoTramite().getIdTipoTramite().toString();
        } else {
            return TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo().toString();
        }
    }

    private String obtenSalarioBase(Cotizacion cotizacion) {
        if (cotizacion != null && cotizacion.getDetalle() != null
                && cotizacion.getDetalle().getEmpleados() != null) {
            for (EmpleadoCuota empleado : cotizacion.getDetalle().getEmpleados()) {
                DecimalFormat formatter = new DecimalFormat("###,###,###.00");
                return formatter.format(empleado.getSalario());
            }
        }
        return "";
    }

	@Override
	public void enviaNotificacion(SeguroIvro seguro, int tipoOperacion) throws Exception {
		 seguro = consultaSeguroIvroLocal.buscaSeguroPorId(seguro.getCveIdSeguroIvro());
	        Fisica titular = seguro.getTitular();
	        String correo = obtenCorreo(titular);
	        boolean esCompra=true;
	        // Sin correo no hacemos nada
	        if (StringUtils.trimToNull(correo) != null) {
	            try {
	                EmailPayloadType emailRequest = new EmailPayloadType();
	                emailRequest.setContentType(CONTENT_TYPE);
	                emailRequest.setTo(correo);
	                String correosSegsMonitorBcc =parametrosService.obtenerParametroDeConfiguracion(ParametroSistemaEnum.CORREOS_SEGUROS_MONITOR_BCC.getCodigo());
	                if(correosSegsMonitorBcc != null && !correosSegsMonitorBcc.isEmpty()){
	                    emailRequest.setBcc(correosSegsMonitorBcc);
	                }
	                int codOperNotif = TipoOperacionNotificacionIVROEnum.RECEPCION_DE_PAGO.getCodigo();
	                if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()) {
	                	codOperNotif = TipoOperacionNotificacionIVROEnum.RECEPCION_DE_PAGO_MOD40.getCodigo();
	                    if (seguro.getTramiteContVoluntaria() != null && seguro.getTramiteContVoluntaria().getRenovacion()) {
	                        emailRequest.setSubject(SUBJECT_CVRO_RENOVACION);
                            esCompra = false;
	                    } else {
	                        emailRequest.setSubject(SUBJECT_CVRO);
                            esCompra = true;
	                    }
	                } else if (seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
	                    if (seguro.getEnRenovacion()) {
	                        emailRequest.setSubject(SUBJECT_ISSF_RENOVACION);
                            esCompra = true;
	                    } else {
	                        emailRequest.setSubject(SUBJECT_ISSF);
                            esCompra = false;
	                    }

	                }  else if ((seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId())||
                            (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId())||
                            (seguro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId())){

                        if (seguro.getTramite() != null && seguro.getTramite().getRenovacion()) {
                            emailRequest.setSubject(SUBJECT_IVRO_RENOVACION);
                            esCompra = false;
                        } else {
                            emailRequest.setSubject(SUBJECT_IVRO);
                            esCompra = true;
                        }

                    } else {
                    }
	                emailRequest.setContent("");

	                if(tipoOperacion==-1){
	                emailRequest.setParameters(obtenValores(seguro, codOperNotif,esCompra));
	                }else{
	                	emailRequest.setParameters(obtenValores(seguro, tipoOperacion,esCompra));
	                }
	                eMailProducer.agendarCorreoElectronico(emailRequest);

	            } catch (Exception e) {
	                LOGGER.error("Error generado al enviar el correo a una persona", e);
	            }

	        }else{
	        	LOGGER.error("La persona asociada al seguro: "+seguro.getCveIdSeguroIvro()+ " no cuenta con medios de contacto");
	        	throw new Exception("La persona asociada al seguro: "+seguro.getCveIdSeguroIvro()+ " no cuenta con medios de contacto");
	        }

	}
	
	/**
     * Envía correo para plantillas simplificadas (61, 71, 85, 86).
     * Usa la misma lógica que enviaCorreo(SeguroIvro, int).
     */
    @Override
    public void enviaCorreo(Long cveIdSeguroIvro,
                            Date fechaEfectivaBaja,
                            String urlConfirmacion,
                            int tipoPlantilla) throws Exception {
 
        // 1. Buscar seguro (igual que código 70)
        SeguroIvro seguro = consultaSeguroIvroLocal.buscaSeguroPorId(cveIdSeguroIvro);
 
        if (seguro == null) {
            LOGGER.error("No se encontró seguro con ID: " + cveIdSeguroIvro);
            if (tipoPlantilla == 85) {
                throw new Exception("No se encontró seguro con ID: " + cveIdSeguroIvro);
            }
            return;
        }
 
        // 2. Obtener correo (igual que código 70)
        Fisica titular = seguro.getTitular();
        String correo = obtenCorreo(titular);
 
        // 3. Validar correo (crítico solo para código 85)
        if (StringUtils.trimToNull(correo) == null) {
            if (tipoPlantilla == 85) {
                throw new Exception("No se puede enviar solicitud de baja expresa: email vacío");
            } else {
                LOGGER.warn("No se pudo enviar correo plantilla " + tipoPlantilla +
                           " para seguro " + cveIdSeguroIvro + ": email vacío");
                return;
            }
        }
 
        // 4. Validar URL (solo para código 85)
        if (tipoPlantilla == 85 && StringUtils.trimToNull(urlConfirmacion) == null) {
            throw new Exception("No se puede enviar solicitud de baja expresa: URL vacía");
        }
 
        // 5. Formatear fecha y construir Map
        String fechaFormateada = formatearFechaLarga(fechaEfectivaBaja);
 
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombreCompleto", titular.getNombreCompleto().replace("#", "\u00D1"));
        parametros.put("fechaOperacion", fechaFormateada);
        parametros.put("idTipoTramite", TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo().toString());
        parametros.put("tipoOperacion", String.valueOf(tipoPlantilla));
 
        if (tipoPlantilla == 85 && urlConfirmacion != null) {
            parametros.put("folio", urlConfirmacion);
        }
 
        // 6. Crear EmailPayloadType (igual que código 70)
        try {
            EmailPayloadType emailRequest = new EmailPayloadType();
            emailRequest.setContentType(CONTENT_TYPE);
            emailRequest.setTo(correo);
 
            // BCC opcional (igual que código 70)
            String correosSegsMonitorBcc = parametrosService.obtenerParametroDeConfiguracion(
                ParametroSistemaEnum.CORREOS_SEGUROS_MONITOR_BCC.getCodigo());
            if (correosSegsMonitorBcc != null && !correosSegsMonitorBcc.isEmpty()) {
                emailRequest.setBcc(correosSegsMonitorBcc);
            }
 
            emailRequest.setSubject(SUBJECT_CVRO);
            emailRequest.setContent("");
            emailRequest.setParameters(parametros);
 
            // 7. Enviar (igual que código 70)
            eMailProducer.agendarCorreoElectronico(emailRequest);
 
            LOGGER.info("Correo plantilla " + tipoPlantilla +
                       " enviado a " + correo +
                       " para seguro " + cveIdSeguroIvro);
 
        } catch (Exception e) {
            LOGGER.error("Error enviando correo plantilla " + tipoPlantilla +
                        " para seguro " + cveIdSeguroIvro + ": " + e.getMessage(), e);
 
            // Si es código 85 (crítico), relanzar
            if (tipoPlantilla == 85) {
                throw e;
            }
        }
    }

    /**
     * Formatea fecha a formato largo español: "15 de enero de 2025"
     */
    private String formatearFechaLarga(Date fecha) {
        if (fecha == null) {
            return "fecha no disponible";
        }
 
        try {
            String[] meses = {
                "enero", "febrero", "marzo", "abril", "mayo", "junio",
                "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
            };
 
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
 
            return cal.get(Calendar.DAY_OF_MONTH) + " de " +
                   meses[cal.get(Calendar.MONTH)] + " de " +
                   cal.get(Calendar.YEAR);
 
        } catch (Exception e) {
            LOGGER.error("Error formateando fecha: " + e.getMessage(), e);
            return "fecha no disponible";
        }
    }

}
