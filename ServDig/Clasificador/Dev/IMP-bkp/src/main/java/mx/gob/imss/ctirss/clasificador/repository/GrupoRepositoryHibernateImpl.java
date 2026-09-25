/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;

import org.springframework.stereotype.Repository;

/**
 * @author lucio
 *
 */
@Repository
public class GrupoRepositoryHibernateImpl extends SupportDAOHibernate implements GrupoRepository {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.GrupoRepository#cargarGruposPorDivision(int)
	 */
	public List<Grupo> cargarGruposPorDivision(int cveDivision) {
		
		
		List<Grupo> result = this
				.getSession()
				.createQuery(
						"select new Grupo( id.cveGrupo,id.cveDivision  , nomGrupo) from Grupo where id.cveDivision = :cveDivision order by nomGrupo asc")
				.setParameter("cveDivision", cveDivision).list();
		
		
		
		return result;
	}

}
