package mx.gob.imss.ctirss.clasificador.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.FraccionDataTableReply;
import mx.gob.imss.ctirss.clasificador.repository.FraccionRepository;
import mx.gob.imss.ctirss.support.dao.hibernate.OrderToNumber;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class FraccionRepositoryHibernateImpl
  extends SupportDAOHibernate
  implements FraccionRepository
{
  public AbstractDataTableReply obtenerFraccionesActivasPorGrupo(int cveGrupo, int cveDivision, String sSearch, int iDisplayLength, int iDisplayStart, boolean vigente) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals("")) {
      bSearch = true;
    }
    
    Fraccion f = new Fraccion();
    List<Fraccion> result = null;
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.valueOf(vigente)));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);

    
    if (bSearch) {

      
      List<String> palabras = getPalabrasConcretas(sSearch);

      
      fraccionDataTableReply.setPalabrasConcretas(palabras);

      
      setRestrictionPalabrasConcretas(criteria, palabras);
    
    }
    else {

      
      criteria.add((Criterion)Restrictions.eq("id.cveGrupo", Integer.valueOf(cveGrupo)));
      criteria.add((Criterion)Restrictions.eq("id.cveDivision", Integer.valueOf(cveDivision)));
    } 




    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));

    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();


    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }








  
  public AbstractDataTableReply obtenerFraccionesActivasPorPalabraClaveAnterior(int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    System.out.println("Parametros...." + sSearch);

    
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals("")) {
      sSearch = sSearch.toUpperCase();
    }
    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.TRUE));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);






    
    Criteria subCriteria = criteria.createCriteria("fraccionesNuevas", "fnva").createCriteria("fnva.fraccionAnterior");






    
    List<String> palabras = getPalabrasConcretas(sSearch);
    
    setRestrictionPalabrasConcretas(subCriteria, palabras);

    
    fraccionDataTableReply.setPalabrasConcretas(palabras);






    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)subCriteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    subCriteria.setProjection(null);
    
    subCriteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    subCriteria.addOrder(Order.asc("nomActividad"));

    
    subCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = subCriteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }





  
  public AbstractDataTableReply obtenerFraccionesInactivasPorPalabraClave(String palabraClave, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    System.out.println("Parametros...." + palabraClave);

    
    boolean bSearch = false;
    if (palabraClave != null && !palabraClave.equals("")) {
      palabraClave = palabraClave.toUpperCase();
    }


    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.valueOf(false)));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);









    
    List<String> palabras = getPalabrasConcretas(palabraClave);
    
    setRestrictionPalabrasConcretas(criteria, palabras);






    
    fraccionDataTableReply.setPalabrasConcretas(palabras);





    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    criteria.addOrder(Order.asc("nomActividad"));

    
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }



  
  public Fraccion obtenerFraccionPorClave(String desFraccion) {
    StringBuffer bfr = new StringBuffer();
    bfr.append("from Fraccion where desFraccion = :desFraccion");
    Query query = getSession().createQuery(bfr.toString());
    query.setParameter("desFraccion", desFraccion);
    Fraccion fraccion = (Fraccion)query.uniqueResult();
    return fraccion;
  }





  
  public AbstractDataTableReply obtenerFraccionesActivasPorNumeroAnterior(int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    System.out.println("Parametros...." + sSearch);

    
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals(""))
    {
      
      sSearch = sSearch.toUpperCase();
    }

    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.TRUE));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);






    
    criteria.createCriteria("fraccionesNuevas", "fnva").createCriteria("fnva.fraccionAnterior").add((Criterion)Restrictions.like("desFraccion", sSearch, MatchMode.EXACT));











    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    criteria.addOrder(Order.asc("nomActividad"));

    
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }









  
  public AbstractDataTableReply obtenerFraccionesActivasPorNumero(int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals("")) {
      sSearch = sSearch.toUpperCase();
    }
    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.TRUE));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);




    
    criteria.add((Criterion)Restrictions.like("desFraccion", sSearch, MatchMode.EXACT));







    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    criteria.addOrder(Order.asc("nomActividad"));

    
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }








  
  public AbstractDataTableReply obtenerFraccionesActivasPorPalabra(int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals("")) {
      sSearch = sSearch.toUpperCase();
    }
    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.TRUE));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);

    
    List<String> palabras = getPalabrasConcretas(sSearch);

    
    fraccionDataTableReply.setPalabrasConcretas(palabras);

    
    setRestrictionPalabrasConcretas(criteria, palabras);











    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    criteria.addOrder(Order.asc("nomActividad"));

    
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }


  
  public AbstractDataTableReply obtenerFraccionesInactivasPorNumeroAnterior(String sSearch, int iDisplayLength, int iDisplayStart) {
    FraccionDataTableReply fraccionDataTableReply = new FraccionDataTableReply();
    
    System.out.println("Parametros...." + sSearch);
    
    boolean bSearch = false;
    if (sSearch != null && !sSearch.equals("")) {
      sSearch = sSearch.toUpperCase();
    }
    
    List<Fraccion> result = null;


    
    Criteria criteria = getSession().createCriteria(Fraccion.class);
    criteria.add((Criterion)Restrictions.eq("indActivo", Boolean.FALSE));




    
    int iTotalRecords = 0;
    
    iTotalRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();

    
    criteria.setProjection(null);






    
    criteria.add((Criterion)Restrictions.like("desFraccion", sSearch, MatchMode.EXACT));









    
    int iTotalDisplayRecords = 0;
    
    iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    criteria.setProjection(null);
    
    criteria.addOrder(OrderToNumber.toNumberAsc("desFraccion"));
    criteria.addOrder(Order.asc("nomActividad"));

    
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
    
    System.out.println(" Resultados .:::" + result);
    
    fraccionDataTableReply.setAaData(result);
    fraccionDataTableReply.setiTotalDisplayRecords(iTotalDisplayRecords);
    fraccionDataTableReply.setiTotalRecords(iTotalRecords);
    
    return (AbstractDataTableReply)fraccionDataTableReply;
  }


  
  private static final String[] palabrasReservadas = new String[] { "A", "E", "O", "U", "Y", "AL", "ASI", "COMO", "COMPRENDE", "CON", "CONSIDERAN", "CUAL", "DE", "DEDICADAS", "DEDICAN", "DEL", "EL", "ELLAS", "EMPRESAS", "EN", "ESTA", "ESTE", "FRACCION", "INCLUYE", "LA", "LAS", "LOS", "NI", "NO", "PARA", "POR", "QUE", "SE", "SIN", "SUS", "TAMBIEN", "Y/O", "YA" };





  
  public List<String> getPalabrasConcretas(String sSearch) {
    System.out.println("getPalabrasConcretas de [" + sSearch + "]");

    
    Arrays.sort((Object[])palabrasReservadas);
    
    List<String> palabras = new ArrayList<String>();
    StringTokenizer tokens = new StringTokenizer(sSearch);
    while (tokens.hasMoreTokens()) {
      String s = tokens.nextToken().trim();
      System.out.println("Comparando la palabra[" + s + "]");
      int r = Arrays.binarySearch((Object[])palabrasReservadas, s);
      System.out.println("Resultado de la comparacion:" + r);
      if (r < 0)
      {
        palabras.add(s.toUpperCase());
      }
    } 
    
    System.out.println("Palabras encontradas [ " + palabras + "]");
    return palabras;
  }








  
  public void setRestrictionPalabrasConcretas(Criteria criteria, List<String> palabras) {
    System.out.println("Agregando al criteria las palabras ");
    Iterator<String> it = palabras.iterator();
    while (it.hasNext()) {
      String sSearch = it.next();
      System.out.println("Agregando [" + sSearch + "]");
      criteria.add((Criterion)Restrictions.or((Criterion)Restrictions.like("nomActividad", sSearch, MatchMode.ANYWHERE), (Criterion)Restrictions.like("desActividad", sSearch, MatchMode.ANYWHERE)));
    } 
  }






  
  public static void main(String[] args) {
    mx.gob.imss.ctirss.clasificador.repository.FraccionRepositoryHibernateImpl f = new mx.gob.imss.ctirss.clasificador.repository.FraccionRepositoryHibernateImpl();
    List<String> ps = f.getPalabrasConcretas("Materiales");
    if (ps != null && !ps.isEmpty()) {
      Iterator<String> it = ps.iterator();
      while (it.hasNext())
        System.out.println(it.next()); 
    } 
  }
}
