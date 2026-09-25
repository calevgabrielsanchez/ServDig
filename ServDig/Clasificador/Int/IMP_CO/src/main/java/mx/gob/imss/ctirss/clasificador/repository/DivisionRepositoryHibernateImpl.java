package mx.gob.imss.ctirss.clasificador.repository;

import java.util.Iterator;
import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Division;
import mx.gob.imss.ctirss.clasificador.repository.DivisionRepository;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class DivisionRepositoryHibernateImpl extends SupportDAOHibernate implements DivisionRepository {
  public List<Division> cargarDivisionesActivas() {
    List<Division> result = getSession().createQuery("select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo order by cveDivision,nomDivision asc ").setParameter("indActivo", Boolean.valueOf(true)).list();
    if (result != null) {
      Iterator<Division> it = result.iterator();
      while (it.hasNext()) {
        Division division = it.next();
        System.out.println(division.getNomDivision());
      } 
    } 
    return result;
  }
  
  public List<Division> cargarDivisionesInactivas() {
    List<Division> result = getSession().createQuery("select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo ").setParameter("indActivo", Boolean.valueOf(false)).list();
    if (result != null) {
      Iterator<Division> it = result.iterator();
      while (it.hasNext()) {
        Division division = it.next();
        System.out.println(division.getNomDivision());
      } 
    } 
    return result;
  }
  
  public List<Division> cargarDivisionesVigentes() {
    List<Division> result = getSession().createQuery("select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo order by cveDivision,nomDivision asc ").setParameter("indActivo", Boolean.valueOf(false)).list();
    if (result != null) {
      Iterator<Division> it = result.iterator();
      while (it.hasNext()) {
        Division division = it.next();
        System.out.println(division.getNomDivision());
      } 
    } 
    return result;
  }
}

