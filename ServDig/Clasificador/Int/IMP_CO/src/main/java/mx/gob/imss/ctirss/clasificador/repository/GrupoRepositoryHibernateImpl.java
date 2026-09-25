package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
import mx.gob.imss.ctirss.clasificador.repository.GrupoRepository;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class GrupoRepositoryHibernateImpl extends SupportDAOHibernate implements GrupoRepository {
  public List<Grupo> cargarGruposPorDivision(int cveDivision) {
    List<Grupo> result = getSession().createQuery("select new Grupo( id.cveGrupo,id.cveDivision  , nomGrupo) from Grupo where id.cveDivision = :cveDivision order by id.cveGrupo,nomGrupo asc").setParameter("cveDivision", Integer.valueOf(cveDivision)).list();
    return result;
  }
}

