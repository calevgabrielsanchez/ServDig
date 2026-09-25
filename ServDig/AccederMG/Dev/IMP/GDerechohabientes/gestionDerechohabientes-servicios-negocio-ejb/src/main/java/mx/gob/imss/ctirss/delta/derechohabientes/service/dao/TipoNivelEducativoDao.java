package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.TipoNivelEducativoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNivelEducativo;

import org.apache.log4j.Logger;


@Stateless(name = "tipoNivelEducativoDao", mappedName = "tipoNivelEducativoDao")
public class TipoNivelEducativoDao implements TipoNivelEducativoDaoLocal  {
	
	private static final Logger logger = Logger.getLogger(TipoNivelEducativoDao.class);

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em; 
	


	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TipoNivelEducativoDaoLocal#findAll()
	 */
	@Override
	public List<TipoNivelEducativo> findAll() throws DerechohabientesBusinessException,Exception {
		List<DicTipoNivelEducativo> dicTipoNivelEducativos=null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicTipoNivelEducativo> cQuery = cb.createQuery(DicTipoNivelEducativo.class);
			Root<DicTipoNivelEducativo> root = cQuery.from(DicTipoNivelEducativo.class);
			cQuery.select(root);
			Predicate conjunction = cb.conjunction();
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conjunction);
			dicTipoNivelEducativos=em.createQuery(cQuery).getResultList();
		} catch (Exception e) {
			logger.error("Error - findAll", e);
			throw e;
		}
		
		return TipoNivelEducativoParser.persisToModelList(dicTipoNivelEducativos);
	}
}
