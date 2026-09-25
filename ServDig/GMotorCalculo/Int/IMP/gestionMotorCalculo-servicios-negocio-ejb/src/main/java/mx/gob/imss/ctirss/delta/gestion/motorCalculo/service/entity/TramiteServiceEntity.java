/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: delta
 * @Archivo: TramiteServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.entity
 * @Fecha: 17:57:55
 */
@Stateless
public class TramiteServiceEntity extends AbstractServiceEntity implements
        TramiteServiceEntityLocal {

    @Override
    public List<DitTramite> getTramitesPorPersona(Fisica persona) {
        // Creamos SQL de la consulta
        StringBuffer sql = new StringBuffer("from DitTramite  t where t.cveIdPersona = :idpersona ");
        // Ejecutamos query
        List<DitTramite> tramites = this.em.createQuery(sql.toString())
                .setParameter(0, persona.getIdPersona()).getResultList();
        return tramites;
    }

}
