package mx.gob.imss.ctirss.delta.derechohabientes.util;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DaoUtils {
	/**  * Counts the number of results of a search.  *   
	 * 		* @param criteria The criteria for the query.  
	 * * @return The number of results of the query.  
	 * */ 
	public static <T> Long findCountByCriteria(CriteriaQuery<?> criteria,EntityManager em) {    
		CriteriaBuilder builder = em.getCriteriaBuilder();      
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);    
		Root<?> entityRoot = countCriteria.from(criteria.getResultType());     
		countCriteria.select(builder.count(entityRoot));     
		countCriteria.where(criteria.getRestriction());      
		return em.createQuery(countCriteria).getSingleResult(); 
	} 
	
	public static <T> Long numeroEncontradosPorParametro(CriteriaQuery<?> criteria,EntityManager em,String parametro) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<?> entityRoot = countCriteria.from(criteria.getResultType());
		countCriteria.select(builder.count(entityRoot.get(parametro)));
		
		if(criteria.getRestriction() != null)
			countCriteria.where(criteria.getRestriction());
		
		return em.createQuery(countCriteria).getSingleResult();
	}
}
