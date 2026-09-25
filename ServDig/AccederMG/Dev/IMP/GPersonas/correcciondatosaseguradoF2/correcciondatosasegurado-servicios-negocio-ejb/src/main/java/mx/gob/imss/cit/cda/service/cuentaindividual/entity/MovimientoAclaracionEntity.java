package mx.gob.imss.cit.cda.service.cuentaindividual.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.cit.cda.service.entity.CuentaIndividualEntity;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNssAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramCorreccionNss;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class MovimientoAclaracionEntity extends AbstractServiceEntity implements
		MovimientoAclaracionLocal {

	private static Logger logger = LoggerFactory
			.getLogger(CuentaIndividualEntity.class);

	@Override
	public DitMovAclaracionNssCda guardarMovimiento(
			DitMovAclaracionNssCda movimiento) {
		logger.debug("guardar Movimiento aclaracion "
				+ movimiento.getCveIdDetalleNssCda().getCveDetalleNss());
		em.persist(movimiento);
		em.flush();

		return movimiento;
	}

	@Override
	public DitMovAclaracionNssCda actualizarMovimiento(
			DitMovAclaracionNssCda movimiento) {
		logger.debug("---CDA MOV--- actualiza movimiento");
		em.merge(movimiento);
		return movimiento;
	}

	@Override
	public DitMovAclaracionNssCda elimianrMovimiento(
			DitMovAclaracionNssCda movimiento) {
		logger.debug("---CDA MOV--- elimina movimiento");
			
		DitMovAclaracionNssCda usuarioABorrar = em.find(DitMovAclaracionNssCda.class, movimiento.getCveIdMovAclaracionNss());
		if(usuarioABorrar != null)
		{
		em.remove(usuarioABorrar);
		em.flush();
		}


		return movimiento;
	}

	/*
	 * 
	 * Acutalizar tipo tramite
	 */
	public void actualizarTipoTramitedeNSS(Long cveId,
			DitMovAclaracionNssCda movimiento) {

		log.debug("cveIdTipoNss : " + cveId);
		DitMovAclaracionNssCda detalle = em.find(DitMovAclaracionNssCda.class,
				cveId);
		detalle.setCveIdTipoTramCorrecNss(movimiento
				.getCveIdTipoTramCorrecNss());
		detalle.setCveIdTipoTramCorrecNss(movimiento
				.getCveIdTipoTramCorrecNss());
		detalle.setCveIdTipoNssAclaracion(movimiento
				.getCveIdTipoNssAclaracion());
		detalle.setFecRegistroAlta(movimiento.getFecRegistroAlta());
		detalle.setFecRegistroActualizado(movimiento
				.getFecRegistroActualizado());
		em.merge(detalle);

	}

	private long obtenerIdMovAclaracion(Long nss) {
		StringBuilder sql = new StringBuilder();
		String convert = "";
		long convertlong = 0L;
		sql.append(" SELECT DMANC.CVE_ID_MOV_ACLARACION_NSS FROM  DIT_MOV_ACLARACION_NSS_CDA DMANC ");
		sql.append(" WHERE DMANC.FEC_REGISTRO_BAJA IS NULL ");
		sql.append(" AND DMANC.CVE_ID_DETALLE_NSS_CDA = :nss ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("nss", nss);

		List<Object> lista = query.getResultList();

		for (Object id : lista) {
			convert = String.valueOf(id);
			convertlong = Long.parseLong(convert);

		}

		return convertlong;
	}

	@Override
	public List<DitMovAclaracionNssCda> obtenerMovimientosAclaracion(
			String refFolio) {
		StringBuilder sql = new StringBuilder();
		logger.debug("---CDA CI--- obtenerListaDeLosEstadosTramite");
		sql.append(" SELECT motivo from DitMovAclaracionNssCda motivo ");
		sql.append(" WHERE motivo.cveIdDetalleNssCda.correccionDatosAsegurado.tramite.ditSolicitud.refFolio = :refFolio ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("refFolio", refFolio);

		return (List<DitMovAclaracionNssCda>) query.getResultList();
	}

	@Override
	public List<DitMovAclaracionNssCda> obtenerMovimientosAclaracionByFolioNss(
			String refFolio, String nss) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT motivo from DitMovAclaracionNssCda motivo ");
		sql.append(" WHERE motivo.cveIdDetalleNssCda.correccionDatosAsegurado.tramite.ditSolicitud.refFolio = :refFolio ");
		sql.append(" AND motivo.cveIdDetalleNssCda.nss = :nss ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("refFolio", refFolio);
		query.setParameter("nss", nss);

		return (List<DitMovAclaracionNssCda>) query.getResultList();
	}

	@Override
	 public List<Object> obtenerTipoNSSAclaracion (Long nssrefLOng) {
        logger.debug("---CDA CI--- obtenerTipoNSSAclaracion");
        logger.error("---CDA CI--- ID NSS : {}", nssrefLOng);
        String nssref = String.valueOf(nssrefLOng);
        logger.error("---CDA CI--- convercion : {}", nssref);
        StringBuffer sql = new StringBuffer();
   
        sql.append(" SELECT CVE_ID_MOV_ACLARACION_NSS FROM DIT_MOV_ACLARACION_NSS_CDA ");
        sql.append(" WHERE ");
        sql.append(" FEC_REGISTRO_BAJA IS NULL ");
        sql.append(" AND CVE_ID_TIPO_TRAM_CORREC_NSS IN( '7', '5', '4', '3', '6') ");
        sql.append(" AND rownum = 1 ");
        sql.append(" AND CVE_ID_DETALLE_NSS_CDA =:refnss");
        
        
        javax.persistence.Query query = em.createNativeQuery(sql.toString());
		query.setParameter("refnss", nssref);		
		
		List<Object> idTramite =  query.getResultList();

		logger.debug("---CDA CI--- fin obtenerTipoNSSAclaracion");
		return idTramite;
		

    }
    

	@Override
	public List<Object> eliminarTipoNSSAclaracion(
			DitMovAclaracionNssCda movimiento) {

		if (em.find(DitMovAclaracionNssCda.class,
				movimiento.getCveIdDetalleNssCda()) != null) {
			movimiento = em.merge(movimiento);
		} else {
			em.persist(movimiento);
		}

		return null;
	}

	@Override
	public List<DitMovAclaracionNssCda> buscarMovExistentes(Long folio) {
		logger.debug("---CDA MOV--- obtiene los movimientos en base" + folio);
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT motivo from DitMovAclaracionNssCda motivo ");
//		sql.append(" WHERE motivo.cveIdDetalleNssCda.correccionDatosAsegurado.tramite.ditSolicitud.refFolio = :refFolio ");
		sql.append(" WHERE motivo.cveIdDetalleNssCda = :idDetalleNss ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("idDetalleNss", folio);

		return (List<DitMovAclaracionNssCda>) query.getResultList();
	}
	
	@Override
	public long CveIdMovAclaracionNssGet(long cve){
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT motivo.cveIdDetalleNssCda from DitMovAclaracionNssCda motivo ");
		sql.append(" WHERE motivo.cveIdMovAclaracionNss = :cve ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("cve", cve);
		
		return query.getFirstResult();
	}
	
	@Override
	public DicTipoNssAclaracion CveIdTipoNssAclaracion(long cve){
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT motivo.cveIdTipoNssAclaracion from DitMovAclaracionNssCda motivo ");
		sql.append(" WHERE motivo.cveIdMovAclaracionNss = :cve ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("cve", cve);
		DicTipoNssAclaracion dtna = new DicTipoNssAclaracion();
		dtna.setCveTipoNssAclaracion((int)query.getFirstResult()); 
		return dtna;
	}
	
	@Override
	public DicTipoTramCorreccionNss CveIdTipoTramCorrecNss(long cve){
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT motivo.cveIdTipoTramCorrecNss from DitMovAclaracionNssCda motivo ");
		sql.append(" WHERE motivo.cveIdMovAclaracionNss = :cve ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("cve", cve);
		DicTipoTramCorreccionNss dttcn = new DicTipoTramCorreccionNss();
		dttcn.setCveIdTipoTramCorrecNss((long)query.getFirstResult());
		return dttcn;
	}
	


}
