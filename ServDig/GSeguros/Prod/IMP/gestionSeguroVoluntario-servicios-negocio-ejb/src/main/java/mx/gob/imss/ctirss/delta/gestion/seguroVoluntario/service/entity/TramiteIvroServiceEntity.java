/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.TramiteIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.utility.UnidadMedicaFamiliarServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoIssfEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DicConsultorioUmf;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDtsExtraPatron;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;
import mx.gob.imss.ctirss.delta.persistence.DitTramiteSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitTramiteSeguroIvroPk;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsultorioTurno;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IMplementacion de los servicio de tramite
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "tramiteIvroServiceEntity", mappedName = "tramiteIvroServiceEntity")
public class TramiteIvroServiceEntity extends AbstractServiceEntity implements
        TramiteIvroServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TramiteIvroServiceEntity.class);

    /**
     * Servicio para el manejo de las compras
     */
    @EJB(name = "compraServiceBusiness", mappedName = "compraServiceBusiness")
    private CompraServiceRemote compraServiceRemote;
    /**
     * Servicio de consulta de coizaciones
     */
    @EJB(name = "cotizacionServiceBusiness", mappedName = "cotizacionServiceBusiness")
    private CotizacionServiceRemote cotizacionServiceRemote;

    @EJB
    private ComprobanteSeguroLocal comprobanteSeguroLocal;

    @EJB
	private UnidadMedicaFamiliarServiceUtilityLocal unidadMedicaFamiliarServiceUtility;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * TramiteIvroServiceLocal
     * #generaSeguro(mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro)
     */
    @Override
    public SeguroIvro generaSeguro(TramiteSeguroIvro tramite) throws IvroException {
        LOGGER.debug("Tramite a generar compra {}", ReflectionToStringBuilder.toString(tramite));
        DitSeguroIvro seguro = new DitSeguroIvro();
        seguro.setDicEstadoSeguro(IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.NUEVO));
        LOGGER.debug("Id de la persona {}", tramite.getPersona().getIdPersona());
        if (tramite.getPersona() == null || tramite.getPersona().getIdPersona() == null
                || tramite.getPersona().getIdPersona().longValue() == 0l) {
            throw new IvroException(IvroConstants.COD_TRAM_SIN_PERSONA,
                    IvroConstants.MSG_TRAM_SIN_PERSONA);
        }
        LOGGER.debug("Id de la compra {}", tramite.getCompra().getIdCompra());
        if (tramite.getCompra() == null || tramite.getCompra().getIdCompra() == null
                || tramite.getCompra().getIdCompra().longValue() == 0l) {
            throw new IvroException(IvroConstants.COD_TRAM_SIN_COMPRA,
                    IvroConstants.MSG_TRAM_SIN_COMPRA);
        }
        DitPersona ditPersona = new DitPersona();
        ditPersona.setCveIdPersona(tramite.getPersona().getIdPersona());
        seguro.setDitPersona(ditPersona);

        DitTramite ditTramite = em.find(DitTramite.class, tramite.getTramiteId());
        try {
            tramite.setCotizacion(new Cotizacion());
            tramite.getCotizacion().setIdCotizacion(tramite.getCompra().getIdCotizacion());
            ditTramite.getDitDetalleTramite().setRefDatosTramiteXml(JaxbUtil.marshaller(tramite));
            LOGGER.debug("Detalle tramite {}", ditTramite.getDitDetalleTramite()
                    .getRefDatosTramiteXml());
            em.merge(ditTramite.getDitDetalleTramite());
        } catch (JAXBException e1) {
            throw new IvroException("T1", "Error al generar el tramite");
        }

        Cotizacion cotizacion = buscaCotizacionCompra(tramite.getCompra().getIdCompra());

        DitCompra ditCompra = new DitCompra();
        ditCompra.setCveIdCompra(tramite.getCompra().getIdCompra());
        seguro.setDitCompra(ditCompra);
        DicModalidad dicModalidad = new DicModalidad();
        dicModalidad.setCveIdModalidad(cotizacion.getDetalle().getModalidad());
        seguro.setDicModalidad(dicModalidad);
        seguro.setFecInicio(cotizacion.getDetalle().getFechaInicioCalculo().getTime());
        seguro.setFecFin(cotizacion.getDetalle().getFechaFinCalculo().getTime());
        seguro.setFecRegistroAlta(new Date());
        LOGGER.debug("Guradando seguro nuevo");
        em.persist(seguro);
        DitTramiteSeguroIvro ditTramiteSeguro = new DitTramiteSeguroIvro();
        DitTramiteSeguroIvroPk pk = new DitTramiteSeguroIvroPk();
        pk.setCveIdSeguroIvro(seguro.getCveIdSeguroIvro());
        pk.setCveIdTramite(ditTramite.getCveIdTramite());
        ditTramiteSeguro.setId(pk);
        LOGGER.debug("Guradando el tramite del seguro asociado");
        em.persist(ditTramiteSeguro);

        DitTramitePersonaFisica tf = new DitTramitePersonaFisica();
        DitTramitePersonaFisicaPK tfPk = new DitTramitePersonaFisicaPK();
        tfPk.setCveIdPersona(tramite.getPersona().getIdPersona());
        tfPk.setCveIdTramite(ditTramite.getCveIdTramite());
        tf.setId(tfPk);
        em.persist(tf);
        Set<DitTramite> ditTramites = new HashSet<DitTramite>();
        ditTramites.add(ditTramite);
        seguro = em.find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());

        SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
        tramite.setTramiteId(ditTramite.getCveIdTramite());
        seguroIvro.setTramite(tramite);
        LOGGER.debug("Regresando el seguro creado");
        SegurosIvro seguros = new SegurosIvro();
        seguros.setSeguroIvro(new SeguroIvro[] {seguroIvro});     
       
        comprobanteSeguroLocal.generaComprobantes(seguros);
        
        return seguroIvro;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * TramiteIvroServiceLocal#
     * generaSegurosDomesticos(mx.gob.imss.digital.modelo
     * .tramite.TramiteSeguroIvro, java.util.List)
     */
    @Override
    public List<SeguroIvro> generaSegurosDomesticos(TramiteSeguroIvro tramite, List<Compra> compras)
            throws IvroException {
        List<SeguroIvro> seguros = new ArrayList<SeguroIvro>();
        if (!compras.isEmpty()) {
            boolean copia = false;
            for (Compra compra : compras) {
                TramiteSeguroIvro tramiteCopia = copiaTramite(tramite);
                tramiteCopia.setCompra(compra);
                Cotizacion cotizacion = buscaCotizacionCompra(compra.getIdCompra());
                tramiteCopia.setCotizacion(cotizacion);
                Fisica beneficiario = new Fisica();
                String nss = cotizacion.getDetalle().getEmpleados()[0].getNumeroSeguridadSocial();
                beneficiario.setNss(nss);
                beneficiario.setNombre(cotizacion.getDetalle().getEmpleados()[0]
                        .getNombreTrabajador());
                Fisica[] beneficiarios = new Fisica[] {beneficiario};
                tramiteCopia.setBeneficiarios(beneficiarios);
                tramiteCopia.setAplicaCuestionario(aplicaCuestionario(nss,
                        tramiteCopia.getCuetionarios()));
                if (copia) {
                    Long idDitTramite = guardaNuevoTramite(tramite, copia);
                    LOGGER.info("ID del TRamite nuevo {}", idDitTramite);
                    tramiteCopia.setTramiteId(idDitTramite);
                } else {
                    tramiteCopia.setTramiteId(tramite.getTramiteId());
                }
                SeguroIvro seguro = generaSeguro(tramiteCopia);
                seguros.add(seguro);
                copia = true;
                em.flush();
            }
        }
        LOGGER.debug("Regresando los seguros creados");
        return seguros;
    }
    
       @Override
    public List<SeguroIvro> generaSegurosFamiliares(TramiteSeguroIvro tramite,
            List<Compra> compras) throws IvroException {

        List<SeguroIvro> seguros = new ArrayList<SeguroIvro>();

        /*
		 * Se genera esta variable, para que la bandera de aplicaCuestionario se
		 * baje desde el tr�mite original a todos los tr�mites que se generan
		 * por seguro
         */
        Collections.sort(compras, new Comparator<Compra>() {
            @Override
            public int compare(Compra compra1, Compra compra2) {
                return compra1.getIdCompra().compareTo(compra2.getIdCompra());
            }
        }
        );
        if (!compras.isEmpty()) {
            boolean copia = false;
            for (Compra compra : compras) {
                TramiteSeguroIvro tramiteCopia = copiaTramite(tramite);

                tramiteCopia.getPersona().setUmfAsociado(tramite.getPersona().getUmfAsociado());

                tramiteCopia.setSoloSolicitante(tramite.isSoloSolicitante());
                tramiteCopia.setDesdeExtranjero(tramite.isDesdeExtranjero());
                tramiteCopia.setCompra(compra);

                Cotizacion cotizacion = buscaCotizacionCompra(compra.getIdCompra());
                tramiteCopia.setCotizacion(cotizacion);

                Fisica beneficiario = new Fisica();
                String nss = cotizacion.getDetalle().getEmpleados()[0].getNumeroSeguridadSocial();
                beneficiario.setNss(nss);
                beneficiario.setNombre(cotizacion.getDetalle().getEmpleados()[0]
                        .getNombreTrabajador());
                beneficiario.setCurp(cotizacion.getDetalle().getEmpleados()[0].getCurp());
                int idParentesco = cotizacion.getDetalle().getEmpleados()[0].getParentesco().getIdParentesco().intValue();
                beneficiario.setErrorFormGeneral(idParentesco
                        + "|"
                        + ParentescoIssfEnum.obternerEnumById(idParentesco)
                                .getDescripcion());

                Fisica[] beneficiarios = new Fisica[]{beneficiario};
                tramiteCopia.setBeneficiarios(beneficiarios);
                tramiteCopia.setAplicaCuestionario(aplicaCuestionario(nss, cotizacion.getDetalle().getEmpleados()));
                tramiteCopia.setRenovacion(cotizacion.getRenovacion());
                tramiteCopia.getTipoTramite().setIdTipoTramite(cotizacion.getDetalle().getEmpleados()[0].getInscripcion()
                        ? TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo()
                        : TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo());

                if (copia) {
                    Long idDitTramite = guardaNuevoTramite(tramite, copia, cotizacion.getDetalle().getEmpleados()[0].getInscripcion()
                            ? TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo().longValue()
                            : TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo().longValue());
                    LOGGER.info("ID del TRamite nuevo {}", idDitTramite);
                    tramiteCopia.setTramiteId(idDitTramite);
                } else {
                    tramiteCopia.setTramiteId(tramite.getTramiteId());
                }

                SeguroIvro seguro = generaSeguro(tramiteCopia);
                seguros.add(seguro);
                copia = true;
                em.flush();
            }
        }
        LOGGER.debug("Regresando los seguros creados");
        return seguros;
    }

	/**
     * Copia los valores de un tramite a otro
     * 
     * @param tramite
     *            el tramite a copiar
     * @return el tramite copiada
     */
    private TramiteSeguroIvro copiaTramite(TramiteSeguroIvro tramite) {
        TramiteSeguroIvro copia = new TramiteSeguroIvro();
        copia.setAplicaCuestionario(tramite.getAplicaCuestionario());
        copia.setCuetionarios(tramite.getCuetionarios());
        copia.setDetalleTramiteXml(tramite.getDetalleTramiteXml());
        copia.setEstadoTramite(tramite.getEstadoTramite());
        copia.setFechaConclusion(tramite.getFechaConclusion());
        copia.setFechaConclusionParse(tramite.getFechaConclusionParse());
        copia.setFechaFin(tramite.getFechaFin());
        copia.setFechaInicio(tramite.getFechaInicio());
        copia.setFechaPresentacion(tramite.getFechaPresentacion());
        copia.setFechaPresentacionParse(tramite.getFechaPresentacionParse());
        copia.setFechaRegistroActualizacion(tramite.getFechaRegistroActualizacion());
        copia.setFechaTramite(tramite.getFechaTramite());
        copia.setIdSeguroAnterior(tramite.getIdSeguroAnterior());
        copia.setModalidad(tramite.getModalidad());
        copia.setPersona(tramite.getPersona());
        copia.setRazonResultado(tramite.getRazonResultado());
        copia.setRenovacion(tramite.getRenovacion());
        copia.setResultado(tramite.getResultado());
        copia.setTipoTramite(tramite.getTipoTramite());
        return copia;
    }

    /**
     * PErsiste un nuevo tramite con los datos del tramite enviado
     * 
     * @param tramiteSeguro
     *            el tramite del seguro
     * @param copia bandera que indica si el tramite es una copia para domesticos
     * @return el tramite nuevo persistido
     */
    private Long guardaNuevoTramite(TramiteSeguroIvro tramiteSeguro, boolean copia) {
        DitTramite ditTramite = em.find(DitTramite.class, tramiteSeguro.getTramiteId());
        DitTramite ditTramite1 = new DitTramite();
        if (copia) {
            ditTramite1.setDicEstadoTramite(new DicEstadoTramite());
            ditTramite1.getDicEstadoTramite().setCveIdEstadoTramite(
                    EstadoTramiteEnum.CERRADO.getId());
            ditTramite1.setFecTramite(new Date());
            ditTramite1.setDitSolicitud(new DitSolicitud());
            ditTramite1.getDitSolicitud().setCveIdSolicitud(
                    ditTramite.getDitSolicitud().getCveIdSolicitud());
            ditTramite1.setDicTipoTramite(new DicTipoTramite());
            ditTramite1.getDicTipoTramite().setCveIdTipoTramite(
                    ditTramite.getDicTipoTramite().getCveIdTipoTramite());
            ditTramite1.setFecRegistroAlta(new Date());
            ditTramite1.setFecConclusion(new Date());
            ditTramite1.setFecPresentacion(new Date());
            ditTramite1.setFecRegistroActualizado(new Date());
            em.persist(ditTramite1);

            DitDetalleTramite ditDetalle1 = new DitDetalleTramite();

            ditDetalle1.setCveIdTramite(ditTramite1.getCveIdTramite());
            ditDetalle1.setDitTramite(ditTramite1);
            ditDetalle1.setRefDatosTramiteXml("");
            ditDetalle1.setFecRegistroActualizado(new Date());
            ditDetalle1.setFecRegistroAlta(new Date());
            em.persist(ditDetalle1);
            LOGGER.debug("Persistiendo tramite {}", ditTramite1.getCveIdTramite());
            ditTramite1.setDitDetalleTramite(ditDetalle1);
            em.merge(ditTramite1);

        }

        return ditTramite1.getCveIdTramite();
    }
    
    /**
     * PErsiste un nuevo tramite con los datos del tramite enviado
     * 
     * @param tramiteSeguro
     *            el tramite del seguro
     * @param copia bandera que indica si el tramite es una copia para domesticos
     * @return el tramite nuevo persistido
     */
    private Long guardaNuevoTramite(TramiteSeguroIvro tramiteSeguro, boolean copia,Long tipoTramite) {
        DitTramite ditTramite = em.find(DitTramite.class, tramiteSeguro.getTramiteId());
        DitTramite ditTramite1 = new DitTramite();
        if (copia) {
            ditTramite1.setDicEstadoTramite(new DicEstadoTramite());
            ditTramite1.getDicEstadoTramite().setCveIdEstadoTramite(
                    EstadoTramiteEnum.CERRADO.getId());
            ditTramite1.setFecTramite(new Date());
            ditTramite1.setDitSolicitud(new DitSolicitud());
            ditTramite1.getDitSolicitud().setCveIdSolicitud(
                    ditTramite.getDitSolicitud().getCveIdSolicitud());
            ditTramite1.setDicTipoTramite(new DicTipoTramite());
            ditTramite1.getDicTipoTramite().setCveIdTipoTramite(tipoTramite);
            ditTramite1.setFecRegistroAlta(new Date());
            ditTramite1.setFecConclusion(new Date());
            ditTramite1.setFecPresentacion(new Date());
            ditTramite1.setFecRegistroActualizado(new Date());
            em.persist(ditTramite1);

            DitDetalleTramite ditDetalle1 = new DitDetalleTramite();

            ditDetalle1.setCveIdTramite(ditTramite1.getCveIdTramite());
            ditDetalle1.setDitTramite(ditTramite1);
            ditDetalle1.setRefDatosTramiteXml("");
            ditDetalle1.setFecRegistroActualizado(new Date());
            ditDetalle1.setFecRegistroAlta(new Date());
            em.persist(ditDetalle1);
            LOGGER.debug("Persistiendo tramite {}", ditTramite1.getCveIdTramite());
            ditTramite1.setDitDetalleTramite(ditDetalle1);
            em.merge(ditTramite1);

        }

        return ditTramite1.getCveIdTramite();
    }

    /**
     * BUsca la cotizacion asociada a una compra
     * 
     * @param idCompra el identificador de la compra
     * @return la cotizacion encontrada
     * @throws IvroException error al consultar la cotizacion
     */
    private Cotizacion buscaCotizacionCompra(Long idCompra) throws IvroException {
        Cotizacion cotizacion = null;
        try {
            Compra compra = compraServiceRemote.findCompraById(idCompra);
            cotizacion = cotizacionServiceRemote.findCotizacion(compra.getIdCotizacion());
        } catch (SUAException e) {
            LOGGER.error("Error al consultar la compra o cotizacion ", e);
            throw new IvroException(IvroConstants.COD_TRAM_SIN_COMPRA,
                    IvroConstants.MSG_TRAM_SIN_COMPRA);
        }
        return cotizacion;
    }

    /**
     * MEtodo que indica si un nss aplico cuestionario medico al conratar el seguro
     * @param nss el numero de seguriad del contratante
     * @param cuestionarios la lista de cuestionarios aplicados en la compra del seguro
     * @return true si aplico cuestionario
     */
    private boolean aplicaCuestionario(String nss, PersonaCuestionario[] cuestionarios) {
        boolean aplica = false;
        if (cuestionarios != null) {
            for (PersonaCuestionario cuestionario : cuestionarios) {
                if (StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(nss),
                        StringUtils.trimToEmpty(cuestionario.getNssPersona()))) {
                    aplica = true;
                }
            }
        }
        return aplica;
    }

    /**
     * MEtodo que indica si un nss aplico cuestionario medico al conratar el seguro
     * @param nss el numero de seguriad del contratante
     * @param empleadosCuota [] la lista de empleados del tramite de compra de seguro
     * @return true si aplico cuestionario
     */
    private boolean aplicaCuestionario(String nss, EmpleadoCuota[] empleadosCuota) {
        boolean aplica = false;
        if (empleadosCuota != null) {
            for (EmpleadoCuota empleadoCuota : empleadosCuota) {
                if (StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(nss),
                        StringUtils.trimToEmpty(empleadoCuota.getNumeroSeguridadSocial())) && empleadoCuota.getAplicaCuestionario()) {
                    aplica = true;
                }
            }
        }
        return aplica;
    }

    @Override
	public UnidadMedicaFamiliar getUnidadMedicoFamiliarByAsignacion(String numNss, Long cveIdPersona) {
    	UnidadMedicaFamiliar unidadMedicaFamiliar = null;

		try {
			Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliar.class);

			log.debug("Se buscara la informacion del derechogabiente con los siguientes datos: \n  - NSS: "
					+ numNss + "\n  - IdPersona: " + cveIdPersona);
			criteria.createAlias("ditAsignacionNss", "nss");
			criteria.add(Restrictions.eq("nss.numNss", numNss));
			criteria.createAlias("ditPersona", "der");
			criteria.add(Restrictions.eq("der.cveIdPersona", cveIdPersona));

			DitGrupoFamiliar ditGrupo = (DitGrupoFamiliar) criteria.uniqueResult();

			if (ditGrupo != null) {
				DitUmfConsTurnoMedico umfConsTurnMed = ditGrupo.getDitUmfConsTurnoMedico();
				DitUmfConsultorioTurno ditUmfCT = umfConsTurnMed.getDitUmfConsultorioTurno();
				DicConsultorioUmf dicConUmf = ditUmfCT.getDicConsultorioUmf();
				DicUmf dicUmf = dicConUmf.getDicUmf();

				if (dicUmf.getFecRegistroBaja() == null) {
					unidadMedicaFamiliar = unidadMedicaFamiliarServiceUtility
							.convertirEntityToModel(dicUmf);
				}
			}
		} catch (NoResultException e) {
			unidadMedicaFamiliar = null;
		} catch (Exception e) {
			unidadMedicaFamiliar = null;
		}

		return unidadMedicaFamiliar;
	}

    @Override
	public SujetoObligado consultarPorRegistroPatronalBasic(
			String registroPatronal) {
		SujetoObligado sujetoObligado;
		DitPatronSujetoObligado ditSujeto = consultarPorRegistroPatronalCommon(registroPatronal);

		if (ditSujeto == null) {
			sujetoObligado = null;
		} else {
			sujetoObligado = convertirDitPatronSujObToSujetoObligado(ditSujeto);
		}

		return sujetoObligado;
	}

	private DitPatronSujetoObligado consultarPorRegistroPatronalCommon(String registroPatronal) {
		String regPatronal = registroPatronal.substring(0, 8);

		StringBuffer query = new StringBuffer();
		query.append("select pso from DitPatronSujetoObligado pso join pso.ditPatronGenerals pg where ");
		query.append(" pso.fecRegistroBaja is null and ");
		query.append(" pg.regPatron = '" + regPatronal + "'");

		if (registroPatronal.length() > 8) {
			String modalidad = registroPatronal.substring(8, 10);
			query.append(" and pso.dicModalidad.numModalidad = '" + modalidad + "'");
		}
		if (registroPatronal.length() == 11) {
			query.append(" and pg.digVer = '" + registroPatronal.substring(registroPatronal.length() - 1) + "'");
		}

		Query queryRegistroPatronal = em.createQuery(query.toString());

		DitPatronSujetoObligado ditPatronSujetoObligado = null;
		try {
			ditPatronSujetoObligado = (DitPatronSujetoObligado) queryRegistroPatronal.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		return ditPatronSujetoObligado;
	}

	private SujetoObligado convertirDitPatronSujObToSujetoObligado(DitPatronSujetoObligado entity) {
		TipoPersonaFiscal tipoPersonaFiscal = selectTipoPersonaFiscal(entity, null);
		SujetoObligado model = initSujetoObligado(entity, tipoPersonaFiscal);
		initDatosPatronGeneral(entity, model);

		if (tipoPersonaFiscal == TipoPersonaFiscal.FISICA) {
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity.getDitPersonaFisica()));
		} else {
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		}

		return model;
	}

	private TipoPersonaFiscal selectTipoPersonaFiscal(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
		TipoPersonaFiscal tipoPersona = tipoPersonaFiscal;

		if (tipoPersonaFiscal == null) {
			if (entity.getDitPersonaFisica() != null) {
				tipoPersona = TipoPersonaFiscal.FISICA;
			} else if (entity.getDitPersonaMoral() != null) {
				tipoPersona = TipoPersonaFiscal.MORAL;
			}
		}

		return tipoPersona;
	}

	private SujetoObligado initSujetoObligado(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
		SujetoObligado model = new SujetoObligado();

		model.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());
		model.setIndPatronConfirmado(entity.getIndPatronConfirmado());
		model.setTipoPersonaFiscal(tipoPersonaFiscal);
		model.setDesUsosBienes(entity.getDesUsosBienes());
		model.setDesAfectacion(entity.getDesAfectacion());
		model.setNombreComercial(entity.getNombreComercial());
		model.setModalidad(convertirEntityToModelModalidad(
				entity.getDicModalidad()));

		return model;
	}

	private Modalidad convertirEntityToModelModalidad(DicModalidad entity) {
		Modalidad model;

		if (entity != null) {
			model = new Modalidad();
			model.setIdModalidad(entity.getCveIdModalidad());
			model.setNumModalidad(entity.getNumModalidad());
			model.setDescripcion(entity.getDesModalidad());
			model.setSiglaAgregadoMedico(entity.getSiglaAgregadoMedico());
			model.setDesCorta(entity.getDesNomModalidadCorto());
		} else {
			model = null;
		}

		return model;
	}

	private void initDatosPatronGeneral(DitPatronSujetoObligado entity,
			SujetoObligado model) {
		List<DitPatronGeneral> patronGrals = entity.getDitPatronGenerals();

		if (patronGrals == null) {
			return;
		}

		DitPatronGeneral patrongeneral = patronGrals.get(0);
		model.setNumeroRegistroPatronal(patrongeneral.getRegPatron());
		model.setDigVerificador(patrongeneral.getDigVer());

		model.setIdTipoRegPatron(patrongeneral.getDicTipoRegPatron() != null ? patrongeneral
				.getDicTipoRegPatron().getCveIdTipoRegPatron() : 1);

		DitDtsExtraPatron datosExtra = patrongeneral.getDitDtsExtraPatron();
		if (datosExtra != null) {
			SimpleDateFormat fechaBase = new SimpleDateFormat("yyyy/MM/dd");
			if (datosExtra.getCveTipoMovto().intValue() == CausaBajaPatronEnum.BAJA.getClave()) {
				model.setDescSituacionBaja(CausaBajaPatronEnum.BAJA.getDescripcion());
				super.log.debug("tiene baja");
			} else if (!fechaBase.format(datosExtra.getFecIniHuelga())
					.equalsIgnoreCase(CausaBajaPatronEnum.FECHA_DE_HUELGA.getDescripcion())) {
				model.setDescSituacionBaja(CausaBajaPatronEnum.HUELGA.getDescripcion());
			}
		}
	}


    private mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica convertirEntityToModelPersonaFisicaBasic(DitPersonaFisica personaFisica) {
		DitPersona persona = personaFisica.getDitPersona();
		mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica pFisica =
				new mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica();
		
		pFisica.setIdPersona(persona.getCveIdPersona());		
		pFisica.setCveFisica(personaFisica.getCveIdPersonaFisica());		
		String rfc = personaFisica.getRfc()!=null ? personaFisica.getRfc() : persona.getRfc();
		pFisica.setRfc(rfc);
		pFisica.setCurp(persona.getCurp());

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		pFisica.setTipoPersona(tipoPersona);

        return pFisica;
    }

    public Moral convertirEntityToModelPersonaMoral(
			DitPersonaMoral personaMoral) {
		Moral pMoral = new Moral();
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		
		pMoral.setCveMoral(personaMoral.getCveIdPersonaMoral());
		pMoral.setIdPersona(personaMoral.getCveIdPersonaMoral());
		pMoral.setRfc(personaMoral.getRfc());
		pMoral.setRazonSocial(personaMoral.getDenominacionRazonSocial());
		
		pMoral.setTipoPersona(tipoPersona);

		return pMoral;
	}
}
