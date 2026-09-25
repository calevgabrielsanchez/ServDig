package mx.gob.imss.cit.cda.service.entity;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitBitFlujoArchSindoCDA;

@Stateless
public class DitBitFlujoArchSindoCDAEntity extends AbstractServiceEntity implements DitBitFlujoArchSindoCDALocal  {

	@Override
	public DitBitFlujoArchSindoCDA guardarBitacoraSINDOCDA(String folio) {
		
		DitBitFlujoArchSindoCDA ditBitFlujoArchSindoCDA = new DitBitFlujoArchSindoCDA();
		
		ditBitFlujoArchSindoCDA.setRefFolioSolicitud(folio);
		
		if(em.find(DitBitFlujoArchSindoCDA.class, ditBitFlujoArchSindoCDA.getRefFolioSolicitud()) !=null){
			ditBitFlujoArchSindoCDA = em.merge(ditBitFlujoArchSindoCDA);
		}else{
			em.persist(ditBitFlujoArchSindoCDA);
		}
		
		
		return ditBitFlujoArchSindoCDA;
		
	}
	@Override
	public DitBitFlujoArchSindoCDA buscarBitacoraSINDOCDA(String folio){
		StringBuilder sql = new StringBuilder();		
		sql.append("from DitBitFlujoArchSindoCDA folio where folio.refFolioSolicitud =:refFolioSolicitud");		
		Query query = em.createQuery(sql.toString());
		query.setParameter("refFolioSolicitud", folio);		
		DitBitFlujoArchSindoCDA ditBitFlujoArchSindoCDA =(DitBitFlujoArchSindoCDA)query.getSingleResult();		
		return ditBitFlujoArchSindoCDA;
	}
	
	
	
	
}
