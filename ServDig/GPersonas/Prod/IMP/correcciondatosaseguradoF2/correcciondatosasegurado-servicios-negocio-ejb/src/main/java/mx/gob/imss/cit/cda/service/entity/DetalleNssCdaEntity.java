package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

@Stateless
public class DetalleNssCdaEntity extends AbstractServiceEntity implements DetalleNssCdaLocal {
    
    private static Logger logger = LoggerFactory.getLogger(DetalleNssCdaEntity.class);
    
    /**
     * Metodo para obtener los nss relacionados a la solicitud, diferentes a no existe en CANASE
     *  select dd.CVE_DETALLE_NSS from  dit_solicitud ds ,dit_tramite dt, dit_correccion_datos_Aseg da, dit_detalle_nss_cda dd
        where ds.cve_id_solicitud=dt.cve_id_solicitud
        and dt.cve_id_tramite=da.cve_id_tramite
        and da.CVE_ID_CORRECCION_DATOS_ASEG=dd.CVE_ID_CORRECCION_DATOS_ASEG
        and dd.CVE_ID_TIPO_NSS!= 4
        and ds.ref_folio = '153314087033274811256'
     * @param folio
     * @return 
     */
    @Override
    public List<DitDetalleNss> getListNssByFolio(String folio) {
        StringBuilder sql = new StringBuilder();
        sql.append("Select d from DitSolicitud a ,DitTramite b ,DitCorreccionDatosAsegurado c , DitDetalleNss d ");
        sql.append(" where a.cveIdSolicitud= b.ditSolicitud.cveIdSolicitud and b.cveIdTramite=c.tramite.cveIdTramite ");
        sql.append(" and c.cveIdCorreccionDatosAsegurado= d.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado ");
        sql.append(" and d.dicTipoNss.cveTipoNss != :cveTipoNss and a.refFolio = :folio ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("folio", folio);
        query.setParameter("cveTipoNss",TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE.getId());
        
        return (List<DitDetalleNss>) query.getResultList();
    }

    @Override
    public int getCountDetalleNssByIdTramite(Long idTramite, String nss) {

        StringBuilder sql = new StringBuilder();
        sql.append("Select d from DitDetalleNss d ");
        sql.append(" where d.correccionDatosAsegurado.tramite.cveIdTramite = :idTramite ");
        sql.append(" and d.nss = :nss ");

        Query query = em.createQuery(sql.toString());
        query.setParameter("idTramite", idTramite);
        query.setParameter("nss", nss);

        return query.getResultList().size();
    }

    /**
     * Metodo para obtener un registro Detalle a partir de un folio y un NSS
     * @param folio
     * @param nss
     * @return 
     */
    @Override
    public DitDetalleNss getDetalleNssByFolioNss(String folio, String nss) {
        StringBuilder sql = new StringBuilder();        
        sql.append("Select det from DitSolicitud sol, DitTramite tram, DitCorreccionDatosAsegurado cor, DitDetalleNss det ");
        sql.append(" where sol.cveIdSolicitud = tram.ditSolicitud.cveIdSolicitud and tram.cveIdTramite = cor.tramite.cveIdTramite ");
        sql.append(" and cor.cveIdCorreccionDatosAsegurado = det.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado ");
        sql.append(" and det.dicTipoNss.cveTipoNss != :cveTipoNss and sol.refFolio = :folio and det.nss =:nss");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("folio", folio);
        query.setParameter("cveTipoNss",TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE.getId());
        query.setParameter("nss", nss);
        
        List<DitDetalleNss> result = query.getResultList();
        
        // CONVERTIR A MODELO
        
        DitDetalleNss resultFinal = result.size() >= 1 ? result.get(0): null;
        return resultFinal;
    }
    
     @Override
    public DitDetalleNss buscarNssEnTramiteCorreccion(Long cveIdCorreccionDatosAsegurado, String nss) {
        
        StringBuilder sql = new StringBuilder();        
        sql.append("Select det from DitDetalleNss det ");
        sql.append(" where det.nss = :nss ");
        sql.append(" and det.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = :cveIdCorreccionDatosAsegurado ");
        sql.append(" and det.fecRegistroBaja is null");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("nss", nss);
        query.setParameter("cveIdCorreccionDatosAsegurado",cveIdCorreccionDatosAsegurado);
        
        List<DitDetalleNss> result = query.getResultList();
        
        // CONVERTIR A MODELO
        
        DitDetalleNss resultFinal = result.size() >= 1 ? result.get(0): null;
        return resultFinal;
    }
    
}
