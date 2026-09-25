package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.DicFactorModalidadRamaEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoAportacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para obtener los valores de cuotas aplicables a un trabajador
 * @author NOVUTECK1
 *
 */
@Stateless(name = "dicFactorModalidadRamaEntity", mappedName = "dicFactorModalidadRamaEntity")
public class DicFactorModalidadRamaEntity implements DicFactorModalidadRamaEntityLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DicFactorModalidadRamaEntity.class);

    /**
     * Tipo de aportacion
     */
    private static final String TIPO_PATRONAL = "PATRONAL";

    /**
     * Nombre de aportacion
     */
    private static final String SRT = "SRT";

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * BUsca todas las cuotas que aplican para el calculo de cobro, ya sean por modalidad y/o patron
     * @param modalidad id de la modalidad para buscar las cuotas
     * @param registroPatronal numero de identificacion del patron
     * @return la lista de cuotas a plicables a un trabajador
     * @throws SUAException Error al buscar ramas por registro patronal  que no exista
     */
    @Override
    public List<RamaCalculo> buscarRamasCalculo(long modalidad, String registroPatronal) throws SUAException {
        LOGGER.debug("buscando las ramas para la modalidad: {}", modalidad);
        List<RamaCalculo> ramaCalculoList = buscarRamasModalidad(modalidad);        
        if (ModalidadEnum.TREINTAYCINCO.equals(ModalidadEnum.fromId(modalidad))) {            
            LOGGER.debug("buscaRamaSRT registro patronal: {}", registroPatronal);
            ramaCalculoList = agregaRama(ramaCalculoList, buscarRamaSRT(registroPatronal));
        }
        return ramaCalculoList;
    }

	/**
     * BUsca la lista de cuotas a plicables a una modalidad
     * @param modalidad el id de la modalidad sobre la cual se buscan sus cuotas
     * @return lista de cuotas aplicables a la modalidad
     */
    @Override
    public List<RamaCalculo> buscarRamasModalidad(long modalidad) {

        StringBuilder q = new StringBuilder("select ")
                .append("new mx.gob.imss.digital.modelo.cobranza.RamaCalculo(\n")
                .append("rama.desRama,\n")
                .append("tipoAportacion.desTipoAportacion,\n")
                .append("factormodalidadramas.numFactor,\n")
                .append("rama.cveIdRama,\n")
                .append("tipoAportacion.cveIdTipoAportacion)\n")
                .append("From DicRama rama join rama.dicFactorModalidadRamas factormodalidadramas\n")
                .append("join factormodalidadramas.dicTipoAportacion tipoAportacion\n")
                .append("where factormodalidadramas.dicModalidad.cveIdModalidad = ?");
        
        TypedQuery<RamaCalculo> query = entityManager.createQuery(q.toString(), RamaCalculo.class)
                .setParameter(1, modalidad);
        List<RamaCalculo> ramaCalculoList = query.getResultList();
        LOGGER.debug("Ramas encontradas: {}", ramaCalculoList.size());
        return ramaCalculoList;
    }

    /**
     * BUsca la cuota de riesgo de trabajao asociada a un patron 
     * @param registroPatronal el numero de identificacion de un patron
     * @return Regresa la cuota para el riesgo de trabajao asociada al patron
     * @throws SUAException Error al buscar ramas por registro patronal  que no exista
     */
    @Override
    public RamaCalculo buscarRamaSRT(String registroPatronal)  throws SUAException {
        
        if (StringUtils.trimToNull(registroPatronal) == null) {
            throw new SUAException(SUAConstants.COD_NRP_NULL, SUAConstants.MSG_NRP_NULL);
        }
        registroPatronal = registroPatronal.replaceFirst("(.{8}).*", "$1");
        
        LOGGER.debug("buscarRamaSRT, registroPatronal: {}", registroPatronal);
        StringBuilder q = new StringBuilder("select \n").append("clasificacion.numPrimaPago\n")
                .append("FROM DitClasificacion clasificacion \n")
                .append("join clasificacion.ditPatronSujetoObligado sujetoObligado\n")
                .append("join sujetoObligado.ditPatronGenerals patronGeneral\n")
                .append("where sujetoObligado.fecRegistroBaja is null\n")
                .append("and patronGeneral.regPatron = ?");

        TypedQuery<BigDecimal> query = entityManager.createQuery(q.toString(), BigDecimal.class)
                .setParameter(1, registroPatronal);

        BigDecimal result = BigDecimal.ZERO;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn("No se encontro la prima de riesgo de trabajo para el patron {}",
                    registroPatronal);
        }
        RamaCalculo ramaCalculo = new RamaCalculo(SRT, TIPO_PATRONAL, result,
                Integer.valueOf(ClavesRama.RIESGOS_TRABAJO),
                Integer.valueOf(ClavesTipoAportacion.PATRONAL));
        LOGGER.debug("Returning {}", ramaCalculo);
        return ramaCalculo;
    }

    /**
     * Agrega una rama de calcula a una lista existente sobreescribiendo si ya
     * existe la rama
     * 
     * @param ramas la lista de ramas a las cuales se agregara la rama nueva
     * @param rama la rama a nueva a ser agregada a la lista 
     * @return la lista con las ramas que aplican para el cobro
     */
    private List<RamaCalculo> agregaRama(List<RamaCalculo> ramas, RamaCalculo rama) {
        List<RamaCalculo> nuevas = new ArrayList<RamaCalculo>();
        for (RamaCalculo r : ramas) {
            if (!(r.getIdRama().equals(rama.getIdRama()) && r.getIdTipoAportacion().equals(
                    rama.getIdTipoAportacion()))) {
                nuevas.add(r);
            }
        }
        nuevas.add(rama);
        return nuevas;
    }
}
