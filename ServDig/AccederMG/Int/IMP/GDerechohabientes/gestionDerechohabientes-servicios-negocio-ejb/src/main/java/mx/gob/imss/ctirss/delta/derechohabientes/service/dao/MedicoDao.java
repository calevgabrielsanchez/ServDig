package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MedicoFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicMedico;

import org.apache.log4j.Logger;

@Stateless(name = "medicoDao", mappedName = "medicoDao")
public class MedicoDao implements medicoDaoLocal {
	private static final Logger logger = Logger.getLogger(MedicoDao.class);

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.medicoDaoLocal#findAllMedicos()
	 */
	@Override
	public List<MedicoFamiliar> findAllMedicos() throws DerechohabientesBusinessException, Exception{
		List <DicMedico> dicMedicos=null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicMedico> cQuery = cb.createQuery(DicMedico.class);
			Root<DicMedico> root = cQuery.from(DicMedico.class);
			cQuery.select(root);
			dicMedicos=em.createQuery(cQuery).getResultList();
		} catch (Exception e) {
			logger.error("findAllMedicos", e);
			throw e;
		}
		
		return MedicoFamiliarParser.persisToModelList(dicMedicos);
	}

}
