/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.dto.DomicilioServiceDtoRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CompraFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvroMigrado;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramiteSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.domicilio.*;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "consultaSeguroIvroEntity", mappedName = "consultaSeguroIvroEntity")
public class ConsultaSeguroIvroEntity implements ConsultaSeguroIvroLocal {

    /**
     * Loggerde la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaSeguroIvroEntity.class);

    /**
     * Dia del mes por defecto en el que se debe situar la fecha limite de pago
     * para la modalidad 40 CVRO
     */
    private static final int DIA_PAGO_MOD_40 = 17;

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Servicio para consultar las compras
     */
    @EJB(mappedName = "compraServiceBusiness")
    private CompraServiceRemote compraServiceRemote;

    @EJB
    private DomicilioServiceDtoRemote domicilioServiceDtoRemote;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal
     * #getUltimoSeguro(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public SeguroIvro getUltimoSeguro(Persona persona) {
        List<SeguroIvro> seguros = buscaUltimosSegurosIndividual(persona);
        SeguroIvro seguro = null;
        if (seguros != null && !seguros.isEmpty()) {
            seguro = seguros.get(0);
        }
        return seguro;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal#buscaSeguroPorId(long)
     */
    @Override
    public SeguroIvro buscaSeguroPorId(long id) {
        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(
                "Select seguro FROM DitSeguroIvro seguro "
                + " WHERE seguro.cveIdSeguroIvro = :cveIdSeguro ", DitSeguroIvro.class);
        q.setParameter("cveIdSeguro", Long.valueOf(id));
        List<DitSeguroIvro> seguros = q.getResultList();
        if (seguros != null && !seguros.isEmpty()) {
            SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguros.get(0));

            if (seguroIvro.getTramite() == null) {
                //Es un seguro migrado
                try {
                    DitSeguroIvroMigrado sim = entityManager.find(DitSeguroIvroMigrado.class, seguroIvro.getCveIdSeguroIvro());

                    if (sim == null) {
            			throw new NoResultException("No se encontró seguro migrado");
                    }

                    Query qry = entityManager.createQuery("SELECT pg.digVer FROM DitPatronSujetoObligado registroPatronal"
                            + " JOIN registroPatronal.ditPatronGenerals pg  JOIN registroPatronal.dicModalidad modalidad "
                            + " WHERE pg.regPatron = :nrpBase AND modalidad.numModalidad = :nModalidad ");

                    qry.setParameter("nrpBase", sim.getNrp().substring(0, 8));
                    qry.setParameter("nModalidad", sim.getCveIdModalidad());
                    @SuppressWarnings("unchecked")
                    List<String> digsVer = qry.getResultList();

                    if (digsVer != null && !digsVer.isEmpty()) {
                        TramiteSeguroIvro tsi = new TramiteSeguroIvro();
                        tsi.setRegistroPatronal(new RegistroPatronal());
                        StringBuffer nrpbuff = new StringBuffer();
                        nrpbuff.append(sim.getNrp()).append(sim.getCveIdModalidad()).append(digsVer.get(0));
                        tsi.getRegistroPatronal().setNumeroRegistroPatronal(nrpbuff.toString());
                        seguroIvro.setTramite(tsi);
                    } else {
						LOGGER.error("No se encontró seguro migrado");
                    }
            	}catch(NoResultException nre){
            		LOGGER.error("No se encontró seguro migrado");
                }
            }
            if (seguroIvro.getCompra() != null) {
                try {
                    seguroIvro.setCompra(compraServiceRemote.findCompraById(seguroIvro.getCompra()
                            .getIdCompra()));
                } catch (SUAException e) {
                    LOGGER.warn("No hay compra asociada al seguro", e);
                }
            }

//            seguroIvro= actualizaDomicilioActualCVRO(seguroIvro);
            return seguroIvro;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal#buscaSeguroPorId(long)
     */
    @Override
    public SeguroIvro buscaSeguroPorIdValidaPago(long id) {
        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(
                "Select seguro FROM DitSeguroIvro seguro "
                        + " WHERE seguro.cveIdSeguroIvro = :cveIdSeguro ", DitSeguroIvro.class);
        q.setParameter("cveIdSeguro", Long.valueOf(id));
        List<DitSeguroIvro> seguros = q.getResultList();

        try {

            if (seguros != null && !seguros.isEmpty()) {

                SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguros.get(0));

                if (seguroIvro.getModalidad().getIdModalidad() == ModalidadEnum.CUARENTA.getId()){
                    // Se valida que no tenga una LC activa en el mes
                    StringBuffer sql = new StringBuffer();
                    sql.append("SELECT MGPBDTU9X.FN_YATIENELCMOD40( :cveIdSeguroIvro )RESULTADO FROM DUAL");
                    Query query = entityManager.createNativeQuery(sql.toString());
                    query.setParameter("cveIdSeguroIvro", seguroIvro.getCveIdSeguroIvro());
                    BigDecimal resultadoB = (BigDecimal)query.getSingleResult();
                    LOGGER.info("FN_YATIENELCMOD40 Resultado de la funcion para validar si ya tiene LC del mes: "+resultadoB);

                    if(resultadoB!=null) {
                        Long resultado = resultadoB.longValue();
                        if (resultado.equals(1L)) {
                            LOGGER.error("FN_YATIENELCMOD40 El seguro ya cuenta con LC para el mes corriente, sale del proceso.");
                            //Si ya tiene una LC para el mes corriente, se sale del proceso
                            return null;
                        }
                    }
                }

                if (seguroIvro.getTramite() == null) {
                    //Es un seguro migrado
                    try {
                        DitSeguroIvroMigrado sim = entityManager.find(DitSeguroIvroMigrado.class, seguroIvro.getCveIdSeguroIvro());

                        if (sim == null) {
                            throw new NoResultException("No se encontró seguro migrado");
                        }

                        Query qry = entityManager.createQuery("SELECT pg.digVer FROM DitPatronSujetoObligado registroPatronal"
                                + " JOIN registroPatronal.ditPatronGenerals pg  JOIN registroPatronal.dicModalidad modalidad "
                                + " WHERE pg.regPatron = :nrpBase AND modalidad.numModalidad = :nModalidad ");

                        qry.setParameter("nrpBase", sim.getNrp().substring(0, 8));
                        qry.setParameter("nModalidad", sim.getCveIdModalidad());
                        @SuppressWarnings("unchecked")
                        List<String> digsVer = qry.getResultList();

                        if (digsVer != null && !digsVer.isEmpty()) {
                            TramiteSeguroIvro tsi = new TramiteSeguroIvro();
                            tsi.setRegistroPatronal(new RegistroPatronal());
                            StringBuffer nrpbuff = new StringBuffer();
                            nrpbuff.append(sim.getNrp()).append(sim.getCveIdModalidad()).append(digsVer.get(0));
                            tsi.getRegistroPatronal().setNumeroRegistroPatronal(nrpbuff.toString());
                            seguroIvro.setTramite(tsi);
                        } else {
                            LOGGER.error("No se encontró seguro migrado");
                        }
                    } catch (NoResultException nre) {
                        LOGGER.error("No se encontró seguro migrado");
                    }
                }
                if (seguroIvro.getCompra() != null) {
                    try {
                        seguroIvro.setCompra(compraServiceRemote.findCompraById(seguroIvro.getCompra()
                                .getIdCompra()));
                    } catch (SUAException e) {
                        LOGGER.warn("No hay compra asociada al seguro", e);
                    }
                }

                seguroIvro = actualizaDomicilioActualCVRO(seguroIvro);
                return seguroIvro;
            }
        }catch(Exception e){
            LOGGER.error("No se pudo ejecutar la función para validar si el seguro ya tiene una LC del mes:",e);
        }
        return null;
    }

    @Override
    public List<SeguroIvro> buscaSegurosPersona(Persona persona, List<DicModalidad> modalidades,
                                                List<DicEstadoSeguro> estados) {
        return buscaSegurosPersona(persona, modalidades, estados, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal
     * #buscaSegurosPersona(mx.gob.imss.digital.modelo.persona.Persona,
     * java.lang.Long, java.lang.Boolean)
     */
    private List<SeguroIvro> buscaSegurosPersona(Persona persona, List<DicModalidad> modalidades,
            List<DicEstadoSeguro> estados, boolean ultimo) {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ").append(
                " seguro.ditPersona = :persona ");
        if (modalidades != null && !modalidades.isEmpty()) {
            query.append(" AND seguro.dicModalidad in (:modalidades) ");
        }
        if (estados != null && !estados.isEmpty()) {
            query.append(" AND seguro.dicEstadoSeguro in (:estados) ");
        }

        query.append(" ORDER BY seguro.fecInicio ");

        if(ultimo){
            query.append("desc");
        }

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);
        DitPersona ditPersona = new DitPersona();
        ditPersona.setCveIdPersona(persona.getIdPersona());
        q.setParameter("persona", ditPersona);
        if (modalidades != null && !modalidades.isEmpty()) {
            q.setParameter("modalidades", modalidades);
        }
        if (estados != null && !estados.isEmpty()) {
            q.setParameter("estados", estados);
        }
        
        List<DitSeguroIvro> seguros = q.getResultList();
        
        
        // Se valida que el tramite en ditDetalleTramite sea correcto para cada idSeguro
        for (DitSeguroIvro ditSeguro : seguros) {
        	
        	if( ditSeguro.getDicModalidad().getCveIdModalidad()== ModalidadEnum.CUARENTA.getId()){
        		LOGGER.info("Se va a validar el seguro: "+ditSeguro.getCveIdSeguroIvro());
        		this.corrigeDetalleTramitePorSeguroIndividual(ditSeguro.getCveIdSeguroIvro());
        	}
        }

        
        return IvroFactory.generaSeguros(seguros);
    }
    
    /**
     * Basado en buscaSegurosPersona, es usado para la busqueda de seguros domesticos y se puedan obtener los datos de la compra
     * @param persona
     * @param modalidades
     * @param estados
     * @return
     */
    private List<SeguroIvro> buscaSegurosPersonaPatron(Persona persona, List<DicModalidad> modalidades,
            List<DicEstadoSeguro> estados) {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites  ")
        //.append(" LEFT OUTER JOIN seguro.ditCompra ")
        .append(" WHERE ")
        .append(" seguro.ditPersona = :persona ");
        if (modalidades != null && !modalidades.isEmpty()) {
            query.append(" AND seguro.dicModalidad in (:modalidades) ");
        }
        if (estados != null && !estados.isEmpty()) {
            query.append(" AND seguro.dicEstadoSeguro in (:estados) ");
        }

        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);
        DitPersona ditPersona = new DitPersona();
        ditPersona.setCveIdPersona(persona.getIdPersona());
        q.setParameter("persona", ditPersona);
        if (modalidades != null && !modalidades.isEmpty()) {
            q.setParameter("modalidades", modalidades);
        }
        if (estados != null && !estados.isEmpty()) {
            q.setParameter("estados", estados);
        }
        return IvroFactory.generaSegurosConCompra(q.getResultList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal#buscaSeguroCompra(java.lang.Long)
     */
    @Override
    public DitSeguroIvro buscaSeguroCompra(Long idCompra) throws IvroException {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ")
                .append(" join seguro.ditPersona join seguro.ditTramites join seguro.ditCompra WHERE ")
                .append(" seguro.ditCompra = :compra ");
        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);
        DitCompra compra = new DitCompra();
        compra.setCveIdCompra(idCompra);
        q.setParameter("compra", compra);
        List<DitSeguroIvro> seguros = q.getResultList();
        if (seguros.isEmpty()) {
            throw new IvroException(IvroConstants.COD_NO_SEGURO_COMPRA,
                    IvroConstants.MSG_NO_SEGURO_COMPRA);
        }
        return seguros.get(0);

    }

    /**
     * Busca el ultimo seguro individual asociado a una persona o el que este
     * activo en periodo de renovacion y el nuevo por pagar
     *
     * @param persona la persona a buscar sus seguros individuales
     * @return La lista de seguros encontrados
     */
    public List<SeguroIvro> buscaUltimosSegurosIndividual(Persona persona) {
        List<SeguroIvro> seguros = buscaSegurosPersona(persona,
                IvroFactory.generaModalidades(IvroFactory.MOD_INDIVIDUAL),
                IvroFactory.estadosSeguro(true));
        // Si no exiten seguros activos buscamos los inactivos, y solo agregamos
        // el ultimo
        if (seguros.isEmpty()) {
            List<SeguroIvro> segurosInactivos = buscaSegurosPersona(persona,
                    IvroFactory.generaModalidades(IvroFactory.MOD_INDIVIDUAL),
                    IvroFactory.estadosSeguro(false));
            if (!segurosInactivos.isEmpty()) {

                if (segurosInactivos.size() > 1) {
                    Collections.sort(segurosInactivos, new Comparator<SeguroIvro>() {
                        @Override
                        public int compare(SeguroIvro o1, SeguroIvro o2) {
                            return o2.getFechaInicio().compareTo(o1.getFechaInicio());
                        }
                    });
                }

                seguros.add(segurosInactivos.get(0));
            }
        } else if (seguros.size() > 1) {
            Collections.sort(seguros, new Comparator<SeguroIvro>() {
                @Override
                public int compare(SeguroIvro o1, SeguroIvro o2) {
                    return o2.getFechaInicio().compareTo(o1.getFechaInicio());
                }
            });

            SeguroIvro ultimoSeguro = seguros.get(0);
            seguros = new ArrayList<SeguroIvro>();
            seguros.add(ultimoSeguro);
        }
        return seguros;
    }

    /**
     * Busca todos los seguros activos asociados a un patron, o los que estene
     * el mes de renovacion extemporania y que no esten renovados aún
     * 
     * @param persona
     *            Los datos del patron asociado a los segurps
     * @return los seguros domesticos encontrados
     */
    public List<SeguroIvro> buscaSegurosDomesticoPatron(Persona persona) {
        // BUscamos todos los seguros domesticos activos de un patron
        List<SeguroIvro> seguros = buscaSegurosPersonaPatron(persona,
                IvroFactory.generaModalidades(IvroFactory.MOD_DOMESTICO),
                IvroFactory.estadosSeguro(true));
        List<Long> segurosRenovados = new ArrayList<Long>();
        // Guardamos lo id de seguros a los cuales se les pidio su renovacion
        for (SeguroIvro seguro : seguros) {
            if (seguro.getTramite() != null && seguro.getTramite().getIdSeguroAnterior() != null) {
                segurosRenovados.add(seguro.getTramite().getIdSeguroAnterior());
            }
        }

        // Agregamos los seguros que aun no esten renovados por otro seguro
        List<SeguroIvro> segurosValidos = new ArrayList<SeguroIvro>();
        for (SeguroIvro seguro : seguros) {
            if (!seguroRenovado(seguro, segurosRenovados)) {
                segurosValidos.add(seguro);
            }
        }

        // Agregamos los seguros concluidos aú no renovados
        segurosValidos.addAll(getSegurosDomesticosConcluidosValidos(persona, segurosRenovados));
        return segurosValidos;
    }
    
    /**
     * Busca todos los seguros familiares asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    @Override
    public List<SeguroIvro> buscaSegurosFamiliares(Persona persona) {

        // Buscamos todos los seguros domesticos activos de un patron
        List<SeguroIvro> seguros = buscaSegurosPersona(persona,
                IvroFactory.generaModalidades(IvroFactory.MOD_FAMILIAR),
                IvroFactory.estadosSeguro(true));
        if (seguros.isEmpty()) {
            List<SeguroIvro> segurosInactivos = buscaSegurosPersona(persona,
                    IvroFactory.generaModalidades(IvroFactory.MOD_FAMILIAR),
                    IvroFactory.estadosSeguro(false));
            if (!segurosInactivos.isEmpty()) {

                for (SeguroIvro listSeguros : segurosInactivos) {
                    if (listSeguros.getFechaInicio().
                            equals(segurosInactivos.get(0).getFechaInicio())) {
                        seguros.add(listSeguros);
                    }
                }
            }
        }
        return seguros;
    }

    /**
     * Busca todos los seguros de Continuación Voluntaria asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    @Override
    public List<SeguroIvro> buscaSegurosCVRO(Persona persona) {
        List<SeguroIvro> seguros = buscaSegurosPersona(persona,
                IvroFactory.generaModalidades(IvroFactory.MOD_CVRO),
                IvroFactory.estadosSeguro(true), true);

        // Si no exiten seguros activos buscamos los inactivos, y solo agregamos
        // el ultimo
        if (seguros.isEmpty()) {
            List<SeguroIvro> segurosInactivos = buscaSegurosPersona(persona,
                    IvroFactory.generaModalidades(IvroFactory.MOD_CVRO),
                    IvroFactory.estadosSeguro(false), true);
            if (!segurosInactivos.isEmpty()) {
                seguros.add(segurosInactivos.get(0));
            }
        }
        return seguros;
    }

    /**
     * Busca todos los seguros activos que ya paso su fecha de terminacion
     *
     * @return la lista de seguros encontrados
     */
    public List<DitSeguroIvro> buscaSegurosAConcluir() {
        StringBuilder query = new StringBuilder("Select distinct seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona inner join seguro.ditCompra LEFT OUTER JOIN seguro.ditTramites WHERE ");
        query.append(" seguro.dicEstadoSeguro = :estado ");
        query.append(" AND seguro.fecFin <= :fecha ");
        query.append(" AND seguro.dicModalidad != :modalidad ");
        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);

        Calendar hoy = Calendar.getInstance();
        q.setParameter("estado", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        q.setParameter("fecha", hoy, TemporalType.DATE);
        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));

        return q.getResultList();
    }

    /**
     * BUsca los seguros domesticos concluiods que esten en periodo de
     * renovacion y aun no tengan un nuevo seguro de renovacion
     *
     * @param persona la persona asociada al seguro domestico
     * @param segurosRenovados lal ista de seguros que ya renovo esa persona
     * @return la lista de seguros aun validos a mostrar para un patron
     */
    private List<SeguroIvro> getSegurosDomesticosConcluidosValidos(Persona persona,
            List<Long> segurosRenovados) {
        // BUscamos todo los seguros concluidos que esten en periodo de
        // renovacion
        List<SeguroIvro> segurosConcluidos = buscaSegurosDomesticosConcluidosYEnRenovacion(persona);
        List<SeguroIvro> segurosValidos = new ArrayList<SeguroIvro>();
        for (SeguroIvro seguro : segurosConcluidos) {
            if (!seguroRenovado(seguro, segurosRenovados)) {
                seguro.setEnRenovacion(true);
                segurosValidos.add(seguro);
            }
        }
        return segurosValidos;
    }

    /**
     * Verifica si un seguro se encuentra la lista de los seguros que ya fueron
     * renovados
     *
     * @param seguro el seguro a valida
     * @param renovados la lista de seguros ya renovados
     * @return true si ya fue renovado, false si aun no es renovado
     */
    private boolean seguroRenovado(SeguroIvro seguro, List<Long> renovados) {
        for (Long renovado : renovados) {
            if (renovado.longValue() == seguro.getCveIdSeguroIvro().longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la lista de seguros domesticos que esten como concliods pero en
     * el mes de renovacion extemporanea
     *
     * @param persona El patron del seguro domestic
     * @return la listad e seguroa asociados
     */
    private List<SeguroIvro> buscaSegurosDomesticosConcluidosYEnRenovacion(Persona persona) {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ")
                .append(" seguro.ditPersona = :persona ")
                .append(" AND seguro.dicModalidad = :modalidad ")
                .append(" AND seguro.dicEstadoSeguro = :estado ")
                .append(" AND seguro.fecFin = :fecha ").append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);
        DitPersona ditPersona = new DitPersona();
        ditPersona.setCveIdPersona(persona.getIdPersona());
        q.setParameter("persona", ditPersona);
        DicModalidad modalidad = new DicModalidad();
        modalidad.setCveIdModalidad(ModalidadEnum.TREINTAYCUATRO.getId());
        q.setParameter("modalidad", modalidad);
        DicEstadoSeguro estado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.CONCLUIDO);
        q.setParameter("estado", estado);
        Calendar fecha = Calendar.getInstance();
        fecha.set(Calendar.DAY_OF_MONTH, 1);
        fecha.add(Calendar.DATE, -1);
        q.setParameter("fecha", fecha, TemporalType.DATE);

        return IvroFactory.generaSeguros(q.getResultList());
    }

    /**
     * Busca todos los seguros en periodo de renovacio
     *
     * @return los seguros en periodo de renovacion
     */
    public List<SeguroIvro> buscaSegurosPorRenovar() {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ");
        query.append(" seguro.dicEstadoSeguro = :estado ");
        query.append(" AND seguro.fecFin <= :inicio ");
        query.append(" AND seguro.fecFin > :fin ");
        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);

        Calendar fin = Calendar.getInstance();
        fin.set(Calendar.DATE, 1);
        fin.add(Calendar.MONTH, 1);
        Calendar ini = Calendar.getInstance();
        ini.set(Calendar.DATE, 1);

        q.setParameter("estado", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        q.setParameter("inicio", ini, TemporalType.DATE);
        q.setParameter("fin", fin, TemporalType.DATE);

        return IvroFactory.generaSeguros(q.getResultList());
    }

    /**
     * Busca todos los seguros que esten por vencer su fecha de pago
     *
     * @return la lista de seguros por vencer
     */
    public List<SeguroIvro> buscaSegurosPorVencer() {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ");
        query.append(" seguro.dicEstadoSeguro = :estado ");
        query.append(" AND seguro.fecInicio > :inicio ");
        query.append(" AND seguro.fecInicio < :fin ");
        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);

        Calendar fin = Calendar.getInstance();
        fin.add(Calendar.MONTH, 1);
        Calendar ini = Calendar.getInstance();

        q.setParameter("estado", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        q.setParameter("inicio", ini, TemporalType.DATE);
        q.setParameter("fin", fin, TemporalType.DATE);

        return IvroFactory.generaSeguros(q.getResultList());
    }

    /**
	 * Busca todos los seguros CVRO candidatos a generarles su línea de captura
	 * automática
	 * 
     * @return la lista de seguros por vencer
     */
    @Override
    public List<SeguroIvro> buscaSegurosCvroLineaCapturaAutomatica() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT seguro FROM DitSeguroIvro seguro ");
        query.append("JOIN seguro.ditPersona ");
        query.append("WHERE seguro.dicModalidad = :modalidad ");
        query.append("AND (seguro.dicEstadoSeguro = :estado1 ");
        query.append("OR seguro.dicEstadoSeguro = :estado2) ");
        query.append("ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));
        q.setParameter("estado1", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        q.setParameter("estado2", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.NUEVO));

        return IvroFactory.generaSeguros(q.getResultList());
    }

    @Override
    public List<DitSeguroIvro> buscaSegurosCvroUltimoPagoPagado() {

        StringBuilder query = new StringBuilder();
        query.append("SELECT seguro FROM DitSeguroIvro seguro ");
        query.append("JOIN seguro.ditPersona ");
        query.append("JOIN seguro.ditTramites ");
        query.append("WHERE seguro.dicModalidad = :modalidad ");
        query.append("AND seguro.dicEstadoSeguro = :estado1 ");
        query.append("ORDER BY seguro.fecInicio");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(
                query.toString(), DitSeguroIvro.class);

        q.setParameter("modalidad",
                IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));
        q.setParameter("estado1",
                IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));

        List<DitSeguroIvro> entities = q.getResultList();

        LOGGER.debug("Se obtuvieron " + entities.size()
                + " seguros CVRO para checar el estado de su ultimo pago");

        query.delete(0, query.length());
        query.append("SELECT pago FROM DitPago pago ");
        query.append("WHERE pago.ditCompra.cveIdCompra = :idCompra ");
        query.append("AND pago.fecLimitePago < :fechaReferencia ");
        query.append("ORDER BY pago.fecLimitePago DESC, pago.cveIdPago DESC ");

        TypedQuery<DitPago> queryPagos = this.entityManager.createQuery(
                query.toString(), DitPago.class);
        queryPagos.setFirstResult(0);
        queryPagos.setMaxResults(1);

        List<DitSeguroIvro> seguros = new ArrayList<DitSeguroIvro>();

        for (DitSeguroIvro entity : entities) {
            // Se toma como referencia el 1 dia del mes actual, para no considerar las lineas de captura recien creadas
            Calendar fechaReferencia = Calendar.getInstance();
            fechaReferencia.set(Calendar.DATE, 1);

            queryPagos.setParameter("idCompra", entity.getDitCompra().getCveIdCompra());
            queryPagos.setParameter("fechaReferencia", fechaReferencia.getTime());

            try {
                DitPago pago = queryPagos.getSingleResult();

                if (pago.getDicEstadoPago().getCveIdEstadoPago().longValue() == EstadoPagoEnum.PAGADO.getId()) {
                    seguros.add(entity);
                }
            } catch (NoResultException e) {
                LOGGER.debug("El seguro no cuenta con ninguna linea de captura a evaluar");
            }
        }

        LOGGER.debug("Se obtuvieron " + seguros.size()
                + " seguros CVRO con su ultimo pago PAGADO");

        return seguros;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaSeguroIvroLocal#buscaSegurosActivosMod40()
     */
    public List<SeguroIvro> buscaSegurosActivosMod40() {
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ");
        query.append(" seguro.dicModalidad = :modalidad ");
        query.append(" AND seguro.dicEstadoSeguro = :estado1 ");
        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));
        q.setParameter("estado1", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));

        return IvroFactory.generaSeguros(q.getResultList());
    }

    @Override
    public Pago buscaUltimoPagoSeguro(long idSeguro) throws SUAException {

        StringBuilder query = new StringBuilder();

        query.append("SELECT pagos FROM DitSeguroIvro seguro ");
        query.append("JOIN seguro.ditCompra.ditPagos pagos ");
        query.append("WHERE seguro.cveIdSeguroIvro = :idSeguro ");
        query.append("ORDER BY pagos.fecRegistroAlta DESC, pagos.cveIdPago DESC ");

        TypedQuery<DitPago> queryPagos = this.entityManager.createQuery(
                query.toString(), DitPago.class);
        queryPagos.setFirstResult(0);
        queryPagos.setMaxResults(1);
        queryPagos.setParameter("idSeguro", idSeguro);

        DitPago entity = queryPagos.getSingleResult();

        return CompraFactoryUtil.generaPago(entity, true, false, false);
    }

    @Override
    public TramiteSeguroIvro buscaTramiteSeguroIndividual(long idSeguro) {
        StringBuilder query = new StringBuilder();
        long idTramite = obtenerTramitePorSeguro(idSeguro).getId().getCveIdTramite();
        query.append("SELECT detalle FROM DitDetalleTramite detalle ");
        query.append("where detalle.cveIdTramite = :idTramite ");
        TypedQuery<DitDetalleTramite> detalle = entityManager.createQuery(query.toString(),
                DitDetalleTramite.class);
        detalle.setParameter("idTramite", Long.valueOf(idTramite));
        return IvroFactory.crearTramiteSeguroIvro(detalle.getSingleResult());
    }

    /**
     * Obtiene los idSeguros´s que se les generará las nuevas LC´s
     * automáticas del mes
     *
     * @return idSeguros´s que se les generará las nuevas LC´s automáticas
     * del mes
     */
    @Override
    public List<Long> buscaSegurosCvroLCAutomatica() {

        LOGGER.info("Se inicia con la busqueda de los seguros a generar y notificar el nuevo pago CVRO");

        List<Long> list;

        String query = "SELECT s.cveIdSeguroIvro FROM DitSeguroIvro s "
                + "WHERE s.dicModalidad = :modalidad "
                + "AND (s.dicEstadoSeguro = :estado1 OR s.dicEstadoSeguro = :estado2) "
                + "AND s.fecInicio < :fechaInicio "
                + "AND NOT EXISTS (SELECT p.cveIdPago FROM DitPago p WHERE p.ditCompra = s.ditCompra AND p.fecIniPeriodo = :fecIniPeriodo ) "
                + "ORDER BY s.cveIdSeguroIvro asc ";

        Query q = entityManager.createQuery(query);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));
        q.setParameter("estado1", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        q.setParameter("estado2", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.NUEVO));

        Calendar fechaInicio = Calendar.getInstance();

        fechaInicio.clear(Calendar.HOUR);
        fechaInicio.clear(Calendar.MINUTE);
        fechaInicio.clear(Calendar.SECOND);
        fechaInicio.clear(Calendar.MILLISECOND);

        fechaInicio.set(Calendar.DATE, 1);

        q.setParameter("fechaInicio", fechaInicio, TemporalType.DATE);

        Calendar fecIniPeriodo = Calendar.getInstance();

        fecIniPeriodo.clear(Calendar.HOUR);
        fecIniPeriodo.clear(Calendar.MINUTE);
        fecIniPeriodo.clear(Calendar.SECOND);
        fecIniPeriodo.clear(Calendar.MILLISECOND);

        fecIniPeriodo.set(Calendar.DATE, 1);

        q.setParameter("fecIniPeriodo", fecIniPeriodo, TemporalType.DATE);

        list = q.getResultList();

        LOGGER.info("Numero de seguros encontrado para generar y notificar el nuevo pago CVRO:" + list.size());

        return list;
    }

    private DitTramiteSeguroIvro obtenerTramitePorSeguro(long idSeguro) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT tramite FROM DitTramiteSeguroIvro tramite ");
        query.append("join tramite.ditSeguroIvro ");
        query.append("where tramite.ditSeguroIvro.cveIdSeguroIvro = :idSeguro ");
        TypedQuery<DitTramiteSeguroIvro> tramite = entityManager.createQuery(query.toString(),
                DitTramiteSeguroIvro.class);
        tramite.setParameter("idSeguro", Long.valueOf(idSeguro));
        return tramite.getResultList().get(0);
    }

    /**
     * Obtiene los idSeguros que se les dara de baja mensual CVRO
     *
     * @return idSeguros que se les dara de baja mensual CVRO
     */
    @Override
    public List<Long> buscaSegurosBajaMensualCvro() {
        LOGGER.info("Se inicia con la busqueda de los seguros de la baja mensual Cvro");

        List<Long> list;

        String query = "SELECT s.cveIdSeguroIvro FROM DitSeguroIvro s "
                + "WHERE s.dicModalidad = :modalidad "
                + "AND  s.dicEstadoSeguro = :estado "
                + "AND EXISTS (SELECT pag FROM DitPago pag "
                + "WHERE pag.ditCompra.cveIdCompra = s.ditCompra.cveIdCompra "
                + "AND pag.dicEstadoPago = :estadoPago "
                + //estadoPago : Pagado
                "AND pag.fecLimitePago > :fechaInicio "
                + //fechaInicio : dia 16 del mes anterior (23hrs:59min:59seg)
                "AND pag.fecLimitePago < :fechaFin "
                + //fechaFin : dia primero del mes actual (0hrs:0min:0seg)
                "AND pag.fecPago < :fechaPagoFin "
                + //fechaPagoFin : dia primero del mes actual (0hrs:0min:0seg)
                ") "
                + "ORDER BY s.fecInicio ";

        Query q = entityManager.createQuery(query);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));
        q.setParameter("estado", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));

        DicEstadoPago estadoPagado = new DicEstadoPago();
        estadoPagado.setCveIdEstadoPago(EstadoPagoEnum.PAGADO.getId());

        q.setParameter("estadoPago", estadoPagado);

        Calendar fechaInicio = Calendar.getInstance();

        fechaInicio.set(Calendar.HOUR, fechaInicio.getActualMaximum(Calendar.HOUR));
        fechaInicio.set(Calendar.MINUTE, fechaInicio.getActualMaximum(Calendar.MINUTE));
        fechaInicio.set(Calendar.SECOND, fechaInicio.getActualMaximum(Calendar.SECOND));
        fechaInicio.set(Calendar.MILLISECOND, fechaInicio.getActualMaximum(Calendar.MILLISECOND));

        // Lo atrasamos un mes
        fechaInicio.add(Calendar.MONTH, -1);

        fechaInicio.set(Calendar.DATE, DIA_PAGO_MOD_40 - 1);

        q.setParameter("fechaInicio", fechaInicio);

        Calendar fechaFin = Calendar.getInstance();

        fechaFin.set(Calendar.HOUR, fechaFin.getActualMinimum(Calendar.HOUR));
        fechaFin.set(Calendar.MINUTE, fechaFin.getActualMinimum(Calendar.MINUTE));
        fechaFin.set(Calendar.SECOND, fechaFin.getActualMinimum(Calendar.SECOND));
        fechaFin.set(Calendar.MILLISECOND, fechaFin.getActualMinimum(Calendar.MILLISECOND));

        //Lo posicionamos en el primer dia del mes actual
        fechaFin.set(Calendar.DATE, fechaFin.getActualMinimum(Calendar.DAY_OF_MONTH));

        q.setParameter("fechaFin", fechaFin);

        Calendar fechaPagoFin = Calendar.getInstance();

        fechaPagoFin.set(Calendar.HOUR, fechaPagoFin.getActualMinimum(Calendar.HOUR));
        fechaPagoFin.set(Calendar.MINUTE, fechaPagoFin.getActualMinimum(Calendar.MINUTE));
        fechaPagoFin.set(Calendar.SECOND, fechaPagoFin.getActualMinimum(Calendar.SECOND));
        fechaPagoFin.set(Calendar.MILLISECOND, fechaPagoFin.getActualMinimum(Calendar.MILLISECOND));

        //Lo posicionamos en el primer dia del mes actual
        fechaPagoFin.set(Calendar.DATE, fechaPagoFin.getActualMinimum(Calendar.DAY_OF_MONTH));

        q.setParameter("fechaPagoFin", fechaPagoFin);

        list = q.getResultList();

        LOGGER.info("Numero de seguros baja mesual CVRO encontrados:" + list.size());

        return list;
    }

    /**
     * Obtiene los idSeguro que se les dara de baja por mora
     *
     * @return idSeguro que se les dara de baja por mora
     */
    @Override
    public List<Long> buscaSegurosBajaPorMora() {
        LOGGER.info("Se inicia con la busqueda de los seguros de la baja por mora");

        List<Long> list;

        String query = "SELECT seg.cveIdSeguroIvro FROM DitSeguroIvro seg "
                + "WHERE seg.dicModalidad = :modalidad "
                + //modalidad = mod 40
                "AND  (seg.dicEstadoSeguro = :estado1 OR seg.dicEstadoSeguro = :estado2) "
                + //estado1 : Pendiente Pago, estado2 : Activo
                "AND EXISTS (SELECT pag.ditCompra FROM DitPago pag "
                + "where pag.ditCompra.cveIdCompra = seg.ditCompra.cveIdCompra "
                + "AND pag.dicEstadoPago = :estadoPago "
                + //estadoPago : Por pagar
                "AND pag.fecLimitePago > :fechaInicio "
                + //fechaInicio : dia 16 del mes anterior (Un dia antes del de dia de pago por defecto)
                "AND pag.fecLimitePago < :fechaFin "
                + //fechaFin : Fecha Actual
                "group by pag.ditCompra "
                + "having count(pag.ditCompra) > 1 ) ";

        Query q = entityManager.createQuery(query);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));

        q.setParameter("estado1", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.NUEVO));
        q.setParameter("estado2", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));

        DicEstadoPago estadoPorPagar = new DicEstadoPago();
        estadoPorPagar.setCveIdEstadoPago(EstadoPagoEnum.POR_PAGAR.getId());

        q.setParameter("estadoPago", estadoPorPagar);

        Calendar fechaInicio = Calendar.getInstance();

        fechaInicio.set(Calendar.HOUR, fechaInicio.getActualMaximum(Calendar.HOUR));
        fechaInicio.set(Calendar.MINUTE, fechaInicio.getActualMaximum(Calendar.MINUTE));
        fechaInicio.set(Calendar.SECOND, fechaInicio.getActualMaximum(Calendar.SECOND));
        fechaInicio.set(Calendar.MILLISECOND, fechaInicio.getActualMaximum(Calendar.MILLISECOND));

        fechaInicio.add(Calendar.MONTH, -1);

        fechaInicio.set(Calendar.DATE, DIA_PAGO_MOD_40 - 1);

        q.setParameter("fechaInicio", fechaInicio);

        Calendar fechaFin = Calendar.getInstance();

        q.setParameter("fechaFin", fechaFin);

        list = q.getResultList();

        LOGGER.info("Numero de seguros baja por mora CVRO encontrados:" + list.size());

        return list;
    }

    /**
     * Busca todos los seguros activos que ya paso su fecha de terminacion
     *
     * @return idSeguro_s que se encontraron
     */
    @Override
    public List<Long> buscaSegurosPorConcluir() {

        LOGGER.info("Se inicia con la busqueda de los seguros por concluir");

        List<Long> list;

        String query = "SELECT seg.cveIdSeguroIvro FROM DitSeguroIvro seg "
                + "join seg.ditPersona "
                + "inner join seg.ditCompra "
                + "LEFT OUTER JOIN seg.ditTramites "
                + "WHERE seg.dicEstadoSeguro = :estado "
                + // Activo
                "AND seg.fecFin <= :fecha "
                + "AND seg.dicModalidad != :modalidad "
                + "ORDER BY seg.fecInicio";

        Query q = entityManager.createQuery(query.toString());

        q.setParameter("estado", IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));

        Calendar fecha = Calendar.getInstance();

        fecha.set(Calendar.HOUR, fecha.getActualMaximum(Calendar.HOUR));
        fecha.set(Calendar.MINUTE, fecha.getActualMaximum(Calendar.MINUTE));
        fecha.set(Calendar.SECOND, fecha.getActualMaximum(Calendar.SECOND));
        fecha.set(Calendar.MILLISECOND, fecha.getActualMaximum(Calendar.MILLISECOND));

        // Lo adelantamos un día, al primer día del mes siguiente
        fecha.add(Calendar.DATE, 1);

        q.setParameter("fecha", fecha, TemporalType.DATE);

        q.setParameter("modalidad", IvroFactory.modalidadSeguro(ModalidadEnum.CUARENTA));

        list = q.getResultList();

        LOGGER.info("Numero de seguros por concluir encontrados:" + list.size());

        return list;
    }

    @Override
    public List<SeguroIvro> buscaNuevosSegurosFamiliares(Fisica titular) {
        List<DicModalidad> modalidades = IvroFactory.generaModalidades(IvroFactory.MOD_FAMILIAR);
        List<DicEstadoSeguro> estados = new ArrayList<DicEstadoSeguro>();
        estados.add(IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.NUEVO));
        StringBuilder query = new StringBuilder("Select seguro FROM DitSeguroIvro seguro  ");
        query.append(" join seguro.ditPersona LEFT OUTER JOIN seguro.ditTramites WHERE ").append(
                " seguro.ditPersona = :persona ");
        query.append(" AND seguro.dicModalidad in (:modalidades) ");
        query.append(" AND seguro.dicEstadoSeguro in (:estados) ");
        query.append(" ORDER BY seguro.fecInicio ");

        TypedQuery<DitSeguroIvro> q = entityManager.createQuery(query.toString(),
                DitSeguroIvro.class);
        DitPersona ditPersona = new DitPersona();
        ditPersona.setCveIdPersona(titular.getIdPersona());
        q.setParameter("persona", ditPersona);
        q.setParameter("modalidades", modalidades);
        q.setParameter("estados", estados);
        return IvroFactory.generaSeguros(q.getResultList());
    }

    @Override
    public Boolean guardaHistSeguroCompra(Long cveIdCompraAnt, Long cveIdCompraNva, Long cveIdSeguroIvro) {

        StringBuffer q = new StringBuffer();
        q.append("INSERT INTO DIT_HIST_SEGURO_COMPRA ")
                .append("(CVE_ID_COMPRA_ANT,CVE_ID_COMPRA_NVA,CVE_ID_SEGURO_IVRO) ")
                .append("VALUES ")
                .append("(:cveIdCompraAnt,:cveIdCompraNva,:cveIdSeguroIvro) ");

        try {
            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("cveIdCompraAnt", cveIdCompraAnt);
            query.setParameter("cveIdCompraNva", cveIdCompraNva);
            query.setParameter("cveIdSeguroIvro", cveIdSeguroIvro);

            int resultado = query.executeUpdate();

            if (resultado  == 1) {
                LOGGER.info("Insert ejecutado correctamente ");
                return true;
            }else{
                LOGGER.error("No se inserto el historico");
            }
        } catch (Exception e) {
            LOGGER.error("No se inserto el historico: ", e);
        }
        return false;
    }

    private SeguroIvro actualizaDomicilioActualCVRO(SeguroIvro seguroIvro){
        String cveEnt;
        String cveMun;
        LOGGER.info("Se empieza a actualizar el domicilio");
        if(seguroIvro != null && seguroIvro.getModalidad()!= null &&
                seguroIvro.getModalidad().getIdModalidad()== ModalidadEnum.CUARENTA.getId()){

                String dupla = domicilioServiceDtoRemote.getZonaSalarialByIdSeguro(seguroIvro.getCveIdSeguroIvro());
                LOGGER.info("Se consulta la tabla nueva: "+dupla);
                if(dupla!=null && dupla.contains(":")){
                    String claves[] = dupla.split(":");
                    if(claves[0]!=null&&claves[0]!="" &&claves[1]!=null&&claves[1]!="") {
                        cveEnt = claves[0];
                        cveMun = claves[1];

                        if (seguroIvro.getTramite() != null && seguroIvro.getTramite().getPersona() != null) {
                            LOGGER.info("Se obtuvo el tramite o la persona: null");

                            if (seguroIvro.getTramite().getPersona().getDomicilioParticular() != null) {
                                Domicilio domicilioParticular = seguroIvro.getTramite().getPersona().getDomicilioParticular();
                                LOGGER.info("Se obtuvo el domicilio Particular: {}", domicilioParticular);


                                if(domicilioParticular.getAsentamiento()!=null) {

                                    LOGGER.info("SE obtuvo el Asentamiento");

                                    if (domicilioParticular.getAsentamiento().getLocalidad() != null) {

                                        if (domicilioParticular.getAsentamiento().getLocalidad().getMunicipio() != null) {

                                            LOGGER.info("SE obtuvo el Municipio y se setea : "+cveMun);
//a
                                            domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setClave(cveMun);
                                            if (domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null) {
                                                LOGGER.info("SE obtuvo la entidad federativa y se setea : "+cveEnt);
                                                domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave(cveEnt);
                                                seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                                            } else {
                                                LOGGER.info("EntidadFed nula, se crea una nueva ");
                                                domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
                                                domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave(cveEnt);
                                                seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                                            }
                                        } else {
                                            LOGGER.info("Municipio null, se crea uno");
                                            domicilioParticular.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
                                            domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setClave(cveMun);
                                            domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
                                            domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave(cveEnt);
                                            seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                                        }

                                    } else {
                                        LOGGER.info("Localidad null, se crea una");
                                        domicilioParticular.getAsentamiento().setLocalidad(new Localidad());
                                        domicilioParticular.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
                                        domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setClave(cveMun);
                                        domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
                                        domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave(cveEnt);
                                        seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                                    }

                                }else{
                                    LOGGER.info("Asentamiento null, se crea una");
                                    domicilioParticular.setAsentamiento(new Asentamiento());
                                    domicilioParticular.getAsentamiento().setLocalidad(new Localidad());
                                    domicilioParticular.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
                                    domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setClave(cveMun);
                                    domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
                                    domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave(cveEnt);
                                    seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                                }

                            } else {
                                LOGGER.info("No se encuentro domicilio particular, se genera la estructura");
                                Domicilio domicilioParticular = new Domicilio();
                                domicilioParticular.setAsentamiento(new Asentamiento());
                                domicilioParticular.getAsentamiento().setLocalidad(new Localidad());
                                Municipio municipio = new Municipio();
                                municipio.setClave(cveMun);
                                domicilioParticular.getAsentamiento().getLocalidad().setMunicipio(municipio);
                                EntidadFederativa entidadFederativa = new EntidadFederativa();
                                entidadFederativa.setClave(cveEnt);
                                domicilioParticular.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(entidadFederativa);
                                seguroIvro.getTramite().getPersona().setDomicilioParticular(domicilioParticular);
                            }
                        }
                    }
                }
        }
        return seguroIvro;
    }

    public SeguroIvro buscaUltimoSeguroIVRO(Persona persona){

        List<DicModalidad> modalidades = IvroFactory.generaModalidades(IvroFactory.MOD_INDIVIDUAL);

        List<DicEstadoSeguro> estados = new ArrayList<DicEstadoSeguro>();
        estados.add(IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
        estados.add(IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.CONCLUIDO));

        List<SeguroIvro> seguros = buscaSegurosPersona(persona,modalidades,estados,true);

        if(seguros!=null && seguros.size()>0){
            return seguros.get(0);
        }else{
            return null;
        }
    }
    
    
    /**Valida si el XML del Tramite es correcto, en caso contrario, lo actualiza
     * @param idSeguro
     * @return
     */
    @Override
    public void corrigeDetalleTramitePorSeguroIndividual(long idSeguro) {
    	
    	LOGGER.info("Entrando a corrigeDetalleTramitePorSeguroIndividual");
    	
        StringBuilder query = new StringBuilder();
        long idTramite = obtenerTramitePorSeguro(idSeguro).getId().getCveIdTramite();
        query.append("SELECT detalle FROM DitDetalleTramite detalle ");
        query.append("where detalle.cveIdTramite = :idTramite ");
        TypedQuery<DitDetalleTramite> detalle = entityManager.createQuery(query.toString(),
                DitDetalleTramite.class);
        detalle.setParameter("idTramite", Long.valueOf(idTramite));
        
        TramiteSeguroIvro resultado = IvroFactory.validaTramiteSeguroIvro(detalle.getSingleResult());
        
        LOGGER.info("Resultado: "+resultado);
        
        if(resultado!=null && !resultado.getTramiteId().equals(-1L)) {
        	LOGGER.info("Se obtiene el detalle del tramite exitosamente");
        }else {
        	
        	LOGGER.info("Se va a corregir el detalle del tramite");
        	TramiteSeguroIvro corregido = new TramiteSeguroIvro();
        	
       	
        	StringBuffer q = new StringBuffer();
        	 q.append("SELECT dp.CVE_ID_PERSONA as cveIdPersona, dp.NOM_NOMBRE, dp.NOM_PRIMER_APELLIDO, dp.NOM_SEGUNDO_APELLIDO, dan.NUM_NSS, "); 
        	 q.append("	   dc.CVE_ID_COMPRA , dc.FEC_REGISTRO_ALTA, dc.FEC_LIMITE_PAGO, dc2.NUM_TOTAL, dc2.CVE_ID_COTIZACION, dc.CVE_ID_FORMA_PAGO, "); 
        	 q.append("	   dpd.DOMICILIO_ID as cveIdPersonaF "); 
        	 q.append("FROM DIT_SEGURO_IVRO dsi  "); 
        	 q.append("INNER JOIN DIT_PERSONA dp "); 
        	 q.append("ON (dsi.CVE_ID_PERSONA = dp.CVE_ID_PERSONA) "); 
        	 q.append("INNER JOIN DIT_ASIGNACION_NSS dan  "); 
        	 q.append("ON (dan.CVE_ID_PERSONA = dp.CVE_ID_PERSONA) "); 
        	 q.append("INNER JOIN DIT_COMPRA dc  "); 
        	 q.append("ON (dsi.CVE_ID_COMPRA = dc.CVE_ID_COMPRA) "); 
        	 q.append("INNER JOIN DIT_COTIZACION dc2  "); 
        	 q.append("ON (dc.CVE_ID_COTIZACION = dc2.CVE_ID_COTIZACION) "); 
        	 q.append("LEFT JOIN DIT_PERSONAF_DOM dpd "); 
        	 q.append("ON (dp.CVE_ID_PERSONA = dpd.CVE_ID_PERSONA) "); 
        	 q.append("WHERE dsi.CVE_ID_SEGURO_IVRO =  :idSeguro");
        	
            try {
            	LOGGER.info("Ejecutando el query: " + q.toString());

                Query queryPrincipal = entityManager.createNativeQuery(q.toString());
                queryPrincipal.setParameter("idSeguro", idSeguro);
                
                List<Object[]> resultadoList = (List<Object[]>) queryPrincipal.getResultList();
                
                
                //0 idTramite
                corregido.setTramiteId(idTramite);
                
                for(Object[] obj:resultadoList) {
                	
                	//1 Persona
                	BigDecimal cveIdPersona 	= (validaBigDecimal(obj[0]));
                	String nombre 	= (validaString(obj[1]));
                	String apellidoPat 	= (validaString(obj[2]));
                	String apellidoMat 	= (validaString(obj[3]));
                	String numNSS = (validaString(obj[4]));
                	
                	//2 Compra
                	BigDecimal cveIdCompra = (validaBigDecimal(obj[5]));
                	Date fecCompra = (validaDate(obj[6]));
                	Date fecLimite = (validaDate(obj[7]));
                	BigDecimal monto = (validaBigDecimal(obj[8]));
                	BigDecimal idCotizacion = (validaBigDecimal(obj[9]));
                	BigDecimal formaPago = (validaBigDecimal(obj[10]));
                	
                	//3 id para la busqueda de domicilio
                	BigDecimal cveIdPersonaFDom 	= (validaBigDecimal(obj[11]));
                	
                	
                	//1 Se inserta Persona
                	Fisica persona = new Fisica();
                	persona.setIdPersona(cveIdPersona.longValue());
                	TipoPersona tipoPersona = new TipoPersona();
                	tipoPersona.setIdTipoPersona(1L);
                	persona.setTipoPersona(tipoPersona);
                	String nombreCompleto = nombre+" "+apellidoPat+" "+apellidoMat;
                	persona.setNombre(nombreCompleto);
                	persona.setLugarNacimiento(null);
                	persona.setSexo(null);
                	persona.setNss(numNSS);
                	                	
                	//2 Se inserta Compra
                	Compra compra = new Compra();
                	compra.setIdCompra(cveIdCompra.longValue());
                	compra.setFechaCompra(fecCompra);
                	compra.setFechaLimite(fecLimite);
                	compra.setMonto(monto);
                	compra.setIdCotizacion(idCotizacion.longValue());
                	compra.setFormaPago(formaPago.longValue());
                	corregido.setCompra(compra);
                	
                	//3 Se inserta idDomicilio
                	Domicilio domicilioParticular = new Domicilio();
                	domicilioParticular.setIdDomicilio(cveIdPersonaFDom.longValue());
                	persona.setDomicilioParticular(domicilioParticular);
                	corregido.setPersona(persona);
                	
                	//4 Se inserta Cotizacion
                	Cotizacion cotizacion = new Cotizacion();
                	cotizacion.setIdCotizacion(idCotizacion.longValue());
                	cotizacion.setFecha(null);
                	cotizacion.setFechaVigencia(null);
                	cotizacion.setDetalle(null);
                	corregido.setCotizacion(cotizacion);
                	
                    //5 beneficiario = persona
                	Fisica beneficiario = new Fisica();
                	beneficiario.setIdPersona(cveIdPersona.longValue());
                	beneficiario.setTipoPersona(tipoPersona);
                	beneficiario.setNombre(nombreCompleto);
                	beneficiario.setLugarNacimiento(null);
                	beneficiario.setSexo(null);
                	beneficiario.setNss(numNSS);
                	Fisica[] beneficiarios = new Fisica[1];
                	beneficiarios[0] = beneficiario;
                    corregido.setBeneficiarios(beneficiarios);
                	
                	
                }
                
                // 6 se llega el domicilio
                if(corregido.getPersona().getDomicilioParticular().getIdDomicilio()!=null) {
                	
                	DgDomicilioGeografico domicilioGeografico = this.entityManager.find(DgDomicilioGeografico.class, corregido.getPersona().getDomicilioParticular().getIdDomicilio());
                	Domicilio domicilio = corregido.getPersona().getDomicilioParticular();
                	fillDomicilio(domicilioGeografico, domicilio);
                	corregido.getPersona().setDomicilioParticular(domicilio);
                	
                }
                
                DitTramite tramite = this.entityManager.find(DitTramite.class, idTramite);
                DitDetalleTramite ditDetalleTramite = this.entityManager.find(DitDetalleTramite.class, idTramite);
                
                if(tramite!=null&&tramite.getDicTipoTramite()!=null) {
                	Long tipoTramite = tramite.getDicTipoTramite().getCveIdTipoTramite();
                	if(tipoTramite.equals(145L)) {
                		corregido.setRenovacion(true);
                	}else {
                		corregido.setRenovacion(false);
                	}
                }
                
                corregido.setDesdeExtranjero(false);
                corregido.setSoloSolicitante(false);
                
                ditDetalleTramite.setFecRegistroActualizado(new Date());
                ditDetalleTramite.setRefDatosTramiteXml(JaxbUtil.marshaller(corregido));
                LOGGER.debug("Detalle tramite {}", ditDetalleTramite.getRefDatosTramiteXml());
                this.entityManager.merge(ditDetalleTramite);
                
                LOGGER.info("El resultado del update fue exitoso.");
                
                
            }catch(Exception e) {
            	LOGGER.error("Ocurrio un error al corregir el tramite: "+e.getMessage());
            	e.printStackTrace();
            }
        	
        }
        
    }

    private BigDecimal validaBigDecimal(Object num) {
		return num != null ? (BigDecimal) num : new BigDecimal(0);
	}
    
    private String validaString(Object cad) {
		return cad != null ? (String) cad : "";
	}
    
    private Date validaDate(Object fecha) {
		return fecha != null ? (Date) fecha : null;
	}
 
    private void fillDomicilio(DgDomicilioGeografico entity, Domicilio domicilio) {
    	
        domicilio.setCalle(entity.getNomvial());
        
        DgAsentamiento dgAsentamiento = entity.getDgAsentamiento();
        Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave(dgAsentamiento.getId().getCveAsen());
        asentamiento.setNombre(dgAsentamiento.getNomAsen());
        domicilio.setAsentamiento(asentamiento);
        domicilio.setColonia(dgAsentamiento.getNomAsen());
        
        DgCatLocalidad dgLocalidad = entity.getDgCatLocalidad();
        Localidad localidad = new Localidad();
        localidad.setClave(dgLocalidad.getId().getCveLoc());
        localidad.setNombre(dgLocalidad.getNomLoc());
        asentamiento.setLocalidad(localidad);
        
        DgCatMunicipio dgMunicipio = dgLocalidad.getDgCatMunicipio();
        Municipio municipio = new Municipio();
        municipio.setClave(dgMunicipio.getId().getCveMun());
        municipio.setNombre(dgMunicipio.getNomMun());
        localidad.setMunicipio(municipio);
        DgCatEstado dgEstado = dgMunicipio.getDgCatEstado();

        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave(dgEstado.getCveEnt());
        entidadFederativa.setNombre(dgEstado.getNomEnt());
        municipio.setEntidadFederativa(entidadFederativa);
        DgCodigosPostale dgCodigo = entity.getDgCodigosPostale();
        domicilio.setCodigoPostal(dgCodigo.getId().getCodigo());
        asentamiento.setCodigoPostal(dgCodigo.getId().getCodigo());

        Vialidad vialidadPrimaria = new Vialidad();
        vialidadPrimaria.setNombre(entity.getNomvial());
        domicilio.setVialidadPrimaria(vialidadPrimaria);

        if(entity.getNumextalf() != null) {
            domicilio.setNumExteriorAlf(entity.getNumextalf().toString());
        }
        domicilio.setDescripcion(entity.getDescripc());
    }
}
