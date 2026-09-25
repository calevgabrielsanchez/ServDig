package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;



import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.DetalleNivelEducativoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNivelEducativo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "detalleNivelEducativoDao", mappedName = "detalleNivelEducativoDao")
public class DetalleNivelEducativoDao extends AbstractServiceEntity implements DetalleNivelEdicativoDaoLocal {
	private static final Logger logger = Logger.getLogger(DetalleNivelEducativoDao.class);

	@Override
	public DetalleNivelEducativo find(Long idTipoNivel,Long idNivel) throws DerechohabientesBusinessException,Exception {
		DitDetalleNivelEducativo detalleNivelEducativo=null;
		try {
			
			Criteria queryNivel = this.getSession().createCriteria(DitDetalleNivelEducativo.class);
			queryNivel.createAlias("dicNivelEducativo","nivel");
			queryNivel.add(Restrictions.eq("nivel.cveIdNivelEducativo", idNivel));
			queryNivel.createAlias("dicTipoNivelEducativo", "tipo");
			queryNivel.add(Restrictions.eq("tipo.cveIdTipoNivelEducativo", idTipoNivel));
			queryNivel.add(Restrictions.isNull("fecRegistroBaja"));
			
			detalleNivelEducativo = (DitDetalleNivelEducativo) queryNivel.uniqueResult();
			
			
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitDetalleNivelEducativo> cQuery = cb.createQuery(DitDetalleNivelEducativo.class);
			
			Root<DitDetalleNivelEducativo> root = cQuery.from(DitDetalleNivelEducativo.class);
			cQuery.select(root);
			
			Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
			conjunction.getExpressions().add(cb.equal(root.get("dicNivelEducativo").get("cveIdNivelEducativo").as(Integer.class), idNivel));
			conjunction.getExpressions().add(cb.equal(root.get("dicTipoNivelEducativo").get("cveIdTipoNivelEducativo").as(Integer.class), idTipoNivel));
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conjunction);
			detalleNivelEducativo=em.createQuery(cQuery).getSingleResult();*/
		} catch (NoResultException e) {
			detalleNivelEducativo=null;
		}catch (Exception e) {
			logger.error("find", e);
			throw e;
		}
		
		
		return DetalleNivelEducativoParser.persisToModel(detalleNivelEducativo);
	}
}
