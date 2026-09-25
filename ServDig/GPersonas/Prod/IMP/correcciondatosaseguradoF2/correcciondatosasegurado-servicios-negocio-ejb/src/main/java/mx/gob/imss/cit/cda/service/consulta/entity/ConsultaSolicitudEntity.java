package mx.gob.imss.cit.cda.service.consulta.entity;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ConsultaSolicitudEntity extends AbstractServiceEntity implements
        ConsultaSolicitudLocal {

    private static Logger logger = LoggerFactory
            .getLogger(ConsultaSolicitudEntity.class);

    public DitDetalleNss consultarOrigenNssPorTramite(String cveIdTramite) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT detalle FROM DitDetalleNss detalle ");
        sql.append("WHERE detalle.correccionDatosAsegurado.tramite.cveIdTramite = :cveIdTramite ");
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveIdTramite", cveIdTramite);

        DitDetalleNss detalle = null;
        List lista;
        try {
            lista =  query.getResultList();
            if(lista !=null && !lista.isEmpty()){
                detalle = (DitDetalleNss)lista.get(0);
            }
        } catch (NoResultException e) {
            logger.error(
                    "---CDA--- Sin resultados el Tramite {} no tiene origen el nss",
                    cveIdTramite);
        } catch (NonUniqueResultException e) {
            logger.error(
                    "---CDA--- Sin resultados el Tramite {} no tiene origen el nss",
                    cveIdTramite);
        }

        return detalle;
    }

}
