package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.enums.TipoEstadoMovimientoEnviadoSindoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;

@Stateless
public class MovimientoCuentaIndividualEntity extends AbstractServiceEntity implements MovimientoCuentaIndividualLocal {
    
    private static Logger logger = LoggerFactory.getLogger(MovimientoCuentaIndividualEntity.class);

    @Override
    public void guardarMovimiento(DitCorreccionCtaIndCda movimiento) {
    	logger.info("guardarMovimiento CI");
    	logger.info("---CDA  CI----orgen  {}",movimiento.getCveDetalleNssOperOrigen() );
    	logger.info("---CDA  CI----destino  {}",movimiento.getCveDetalleNssOperDestino() );
    	logger.info("---CDA  CveIdCorreccionCtaIndCda  {}",movimiento.getCveIdCorreccionCtaIndCda() );    	
        em.persist(movimiento);
    }
    
    @Override
    public void actualizarMovimiento(DitCorreccionCtaIndCda movimiento) {
    	logger.info("actualizarMovimiento CI");
    	logger.info("---CDA  CI----orgen  {}",movimiento.getCveDetalleNssOperOrigen() );
    	logger.info("---CDA  CI----destino  {}",movimiento.getCveDetalleNssOperDestino() );
    	logger.info("---CDA  CveIdCorreccionCtaIndCda  {}",movimiento.getCveIdCorreccionCtaIndCda() );
        em.merge(movimiento);
    }
    
    @Override
    public Long obtenerConsecutivoMovimientos(Long nss){
        
        StringBuilder sql = new StringBuilder();        
        sql.append(" SELECT COALESCE(max(e.indConsecutivoMovimiento)+1 , 101) from DitCorreccionCtaIndCda e ");
        sql.append(" where e.cveDetalleNssOperOrigen.nss= :nss and e.cveDetalleNssOperOrigen.fecRegistroBaja is not null and e.fecRegistroBaja is not null ");
         
        Query query = em.createQuery(sql.toString());
        query.setParameter("nss", nss);
        
        return (Long)query.getSingleResult();
    }
    
    @Override
    public List<DitCorreccionCtaIndCda> obtenerMovimientosByFolio(String folio){
       
        StringBuilder sql = new StringBuilder();        
        sql.append(" SELECT d FROM DitCorreccionCtaIndCda d ");
        sql.append(" WHERE d.cveDetalleNssOperOrigen.correccionDatosAsegurado.tramite.ditSolicitud.refFolio= :folio ");
        sql.append(" and d.cveDetalleNssOperOrigen.dicTipoNss.cveTipoNss != :cveTipoNss and d.fecRegistroBaja is null");
                 
        Query query = em.createQuery(sql.toString());
        query.setParameter("folio", folio);
        query.setParameter("cveTipoNss",TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE.getId());
        
        return (List<DitCorreccionCtaIndCda>)query.getResultList();
    }
    
    @Override
    public List<DitCorreccionCtaIndCda> obtenerMovimientosEliminar(Long cveIdNss) {
    	logger.debug("---CDA CI-----obtenerMovimientosEliminar");
        StringBuilder sql = new StringBuilder();        
        sql.append(" SELECT d FROM DitCorreccionCtaIndCda d ");
        sql.append(" WHERE d.cveDetalleNssOperOrigen.cveDetalleNss = :cveIdNss ");
//        sql.append(" and d.fecRegistroBaja is null AND d.fecMovEnvSindo IS NULL AND (d.cveIdEstadoMovSindo IS NULL OR d.cveIdEstadoMovSindo= :edoSindo) ");
        sql.append(" and d.fecRegistroBaja is null AND (d.cveIdEstadoMovSindo IS NULL OR d.cveIdEstadoMovSindo= :edoSindo) "); 
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveIdNss", cveIdNss);
        query.setParameter("edoSindo",TipoEstadoMovimientoEnviadoSindoEnum.ERROR_SINDO.getId());
        
        return (List<DitCorreccionCtaIndCda>)query.getResultList();
    }
    
}
