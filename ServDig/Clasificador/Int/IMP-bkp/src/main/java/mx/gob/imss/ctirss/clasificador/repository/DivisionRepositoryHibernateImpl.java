/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Division;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;

import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;

/**
 * @author lucio
 * 
 */
@Repository
public class DivisionRepositoryHibernateImpl extends SupportDAOHibernate
		implements DivisionRepository {

	@SuppressWarnings("unchecked")
	public List<Division> cargarDivisionesActivas() {

		
		
		List<Division> result = (List<Division>) this
				.getSession()
				.createQuery(
						"select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo order by nomDivision asc ")
				.setParameter("indActivo", true).list();
		
		if(result != null){
			
			Iterator<Division> it = result.iterator();
			while(it.hasNext()){
				Division division = it.next();
				System.out.println(division.getNomDivision());
			}
		}
		
		 //this.getHibernateTemplate().loadAll(Division.class);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Division> cargarDivisionesInactivas() {
		List<Division> result = (List<Division>) this
				.getSession()
				.createQuery(
						"select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo ")
				.setParameter("indActivo", false).list();
		
		if(result != null){
			
			Iterator<Division> it = result.iterator();
			while(it.hasNext()){
				Division division = it.next();
				System.out.println(division.getNomDivision());
			}
		}
		
		
		 //this.getHibernateTemplate().loadAll(Division.class);
		return result;
	}

}
