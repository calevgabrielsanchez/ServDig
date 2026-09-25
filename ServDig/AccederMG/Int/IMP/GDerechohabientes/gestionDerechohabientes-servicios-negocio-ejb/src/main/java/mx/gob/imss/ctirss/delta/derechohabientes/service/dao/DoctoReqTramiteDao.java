package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "doctoReqTramiteDao", mappedName = "doctoReqTramiteDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DoctoReqTramiteDao extends AbstractServiceEntity implements DoctoReqTramiteDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DoctoReqTramiteDao.class);
	@EJB TipoTramiteDaoLocal tipoTramiteDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<DitDoctoReqTramite> getListaTramiteDocumentacion(Long cveIdTipoTramite) throws Exception {
		
		List<DitDoctoReqTramite> res;
		//DicTipoTramite tipoTramite=tipoTramiteDao.findTipoTramite(cveIdTramite);
		try {
			
			Criteria queryDocto = this.getSession().createCriteria(DitDoctoReqTramite.class);
			queryDocto.createAlias("dicTipoTramite", "tipo");
			queryDocto.add(Restrictions.eq("tipo.cveIdTipoTramite", cveIdTipoTramite));
			queryDocto.add(Restrictions.isNull("fecRegistrosBaja"));
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
			CriteriaQuery<DitDoctoReqTramite> cqry= cb.createQuery(DitDoctoReqTramite.class);  //Step 1
			Root<DitDoctoReqTramite> root = cqry.from(DitDoctoReqTramite.class); //Step 2 //se crea la raiz
			cqry.select(root);
			
			Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
			conjunction.getExpressions().add(cb.equal(root.get("dicTipoTramite").get("cveIdTipoTramite").as(Integer.class),cveIdTipoTramite));
			//fecha de baja nula
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistrosBaja")));

			cqry.where(conjunction); 
			res= em.createQuery(cqry).getResultList();*/
			
			res = queryDocto.list();
		} catch (Exception e) {
			logger.error("Error - getListaTramiteDocumentacion", e);
			throw e;
		}
		
		return res;
	}
}
