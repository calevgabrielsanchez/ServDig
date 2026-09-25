/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaBeneficiarioMigradoLocal;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvroMigrado;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "consultaBeneficiarioMigradoEntity", mappedName = "consultaBeneficiarioMigradoEntity")
public class ConsultaBeneficiarioMigradoEntity implements ConsultaBeneficiarioMigradoLocal {

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConsultaBeneficiarioMigradoEntity.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaBeneficiarioMigradoLocal
     * #buscaBeneficiarioSeguro(mx.gob.imss.digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public Fisica buscaBeneficiarioSeguro(SeguroIvro seguro) {
        List<Fisica> personas = buscaBeneficiariosSeguro(seguro);
        if (!personas.isEmpty()) {
            return personas.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaBeneficiarioMigradoLocal
     * #buscaBeneficiariosSeguro(mx.gob.imss.digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public List<Fisica> buscaBeneficiariosSeguro(SeguroIvro seguro) {
        List<DitSeguroIvroMigrado> migrados = buscaSeguroMigrado(seguro.getCveIdSeguroIvro());
        List<Fisica> personas = new ArrayList<Fisica>();
        for (DitSeguroIvroMigrado migrado : migrados) {
            Fisica persona = new Fisica();
            persona.setNss(migrado.getNss());
            personas.add(persona);
        }
        return personas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ConsultaBeneficiarioMigradoLocal
     * #buscaBeneficiariosSalarioSeguro(mx.gob.imss
     * .digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public Map<Fisica, BigDecimal> buscaBeneficiariosSalarioSeguro(SeguroIvro seguro) {
        List<DitSeguroIvroMigrado> migrados = buscaSeguroMigrado(seguro.getCveIdSeguroIvro());
        Map<Fisica, BigDecimal> personas = new HashMap<Fisica, BigDecimal>();
        for (DitSeguroIvroMigrado migrado : migrados) {
            Fisica persona = new Fisica();
            persona.setNss(migrado.getNss());
            personas.put(persona, migrado.getSalario());
        }
        return personas;
    }

    /**
     * Busca la lista de seguros migrados asociados al id de un segur ivro
     * @param idSeguro el id del seguro ivro 
     * @return los registros migrados al seguro
     */
    private List<DitSeguroIvroMigrado> buscaSeguroMigrado(Long idSeguro) {
        StringBuilder query = new StringBuilder(" Select seguro FROM DitSeguroIvroMigrado seguro ")
                .append(" where seguro.cveIdSeguroIvro = :idSeguro");
        LOGGER.debug("Buscando seguro migrado {}", idSeguro);
        TypedQuery<DitSeguroIvroMigrado> q = entityManager.createQuery(query.toString(),
                DitSeguroIvroMigrado.class);
        q.setParameter("idSeguro", idSeguro);

        return q.getResultList();
    }

    /**
     * Obtiene el nrp registrado al seguro
     * 
     * @param seguro
     *            el seguro asociado
     * @return el NRP
     */
    public String obtenNrpPatron(SeguroIvro seguro) {
        String nrp = null;
        List<DitSeguroIvroMigrado> migrados = buscaSeguroMigrado(seguro.getCveIdSeguroIvro());
        LOGGER.debug("Se regresan los seguros migrados");
        if (!migrados.isEmpty()) {
            DitSeguroIvroMigrado migrado = migrados.get(0);
            nrp = migrado.getNrp() + migrado.getCveIdModalidad();
        }
        return nrp;
    }
}
