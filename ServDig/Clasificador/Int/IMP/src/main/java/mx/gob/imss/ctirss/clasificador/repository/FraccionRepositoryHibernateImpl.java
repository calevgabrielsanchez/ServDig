/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import mx.gob.imss.ctirss.clasificador.model.business.Equivalencia;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.FraccionDataTableReply;
import mx.gob.imss.ctirss.support.dao.hibernate.SupportDAOHibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author lucio
 *
 */
@Repository
public class FraccionRepositoryHibernateImpl extends SupportDAOHibernate implements FraccionRepository {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesNuevasPorGrupo(int)
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorGrupo(
			int cveGrupo, int cveDivision , String sSearch, int iDisplayLength, int iDisplayStart) {

		AbstractDataTableReply oReply = new FraccionDataTableReply();
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			bSearch = true;
		}
		
		Fraccion f = new Fraccion();
		List<Fraccion> result = null;
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.TRUE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		if(bSearch){
			
			/*Desechamos las palabras reservadas para solo dejar aquellas palabras que cuentan y agregarlas para la consulta del criteria*/
			List<String> palabras = this.getPalabrasConcretas(sSearch);
			
			/*Agregamos al resultado las palabras concretas utilizadas para la consulta*/
			oReply.setPalabrasConcretas(palabras);
			
			/*Agregamos al criteria las palabras encontradas*/
			this.setRestrictionPalabrasConcretas(criteria, palabras);
			
//			criteria.add(Restrictions.or(Restrictions.ilike("nomActividad",
//					sSearch, MatchMode.ANYWHERE), Restrictions.ilike(
//					"desActividad", sSearch, MatchMode.ANYWHERE)));
		}else{
			criteria.add(Restrictions.eq("id.cveGrupo", cveGrupo));
			criteria.add(Restrictions.eq("id.cveDivision", cveDivision));
		}
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 
		 criteria.addOrder(Order.asc("desFraccion"));
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesActivasPorPalabraClave(java.lang.String)
	 * 
	 * Obtiene las fracciones activas (nuevas) cuya fraccion anterior (equivalencia) su nombre coincida con la palabra clave.
	 * 
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorPalabraClaveAnterior(
			 int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
		
		AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		System.out.println("Parametros...." + sSearch);
		
		
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			sSearch = sSearch.toUpperCase();
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones activas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.TRUE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		
		/*Iniciamos los filtros por fraccion anterior.*/
		
		
		/*Fltramos las fracciones Anteriores que en su nombre o descripcion contengan la palabraClave*/
		Criteria subCriteria = criteria.createCriteria("fraccionesNuevas", "fnva")
				.createCriteria("fnva.fraccionAnterior");
//				.add(Restrictions.or(Restrictions.like("nomActividad",
//						sSearch, MatchMode.ANYWHERE), Restrictions.like(
//						"desActividad", sSearch, MatchMode.ANYWHERE)));
//		
		
		/*Desechamos las palabras reservadas para solo dejar aquellas palabras que cuentan y agregarlas para la consulta del criteria*/
		List<String> palabras = this.getPalabrasConcretas(sSearch);
		/*Agregamos al criteria las palabras encontradas*/
		this.setRestrictionPalabrasConcretas(subCriteria, palabras);
		
		/*Agregamos al resultado las palabras concretas utilizadas para la consulta*/
		oReply.setPalabrasConcretas(palabras);
	
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)subCriteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		subCriteria.setProjection(null);
		subCriteria.addOrder(Order.asc("desFraccion"));
		subCriteria.addOrder(Order.asc("nomActividad"));
		 
		 
		subCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = subCriteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesInactivasPorPalabraClave(java.lang.String)
	 */
	public AbstractDataTableReply obtenerFraccionesInactivasPorPalabraClave(
			String palabraClave,int iDisplayLength, int iDisplayStart) {
		
		AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		System.out.println("Parametros...." + palabraClave);
		
		
		boolean bSearch = false;
		if(palabraClave != null && !palabraClave.equals("") ){
			palabraClave =palabraClave.toUpperCase();
			
			
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones activas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", false));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		
		/*Iniciamos los filtros por fraccion anterior.*/
		
		
		/*Fltramos las fracciones Anteriores que en su nombre o descripcion contengan la palabraClave*/
		
		
		/*Desechamos las palabras reservadas para solo dejar aquellas palabras que cuentan y agregarlas para la consulta del criteria*/
		List<String> palabras = this.getPalabrasConcretas(palabraClave);
		/*Agregamos al criteria las palabras encontradas*/
		this.setRestrictionPalabrasConcretas(criteria, palabras);
		
//		criteria.add(Restrictions.or(Restrictions.like("nomActividad",
//						palabraClave, MatchMode.ANYWHERE), Restrictions.like(
//						"desActividad", palabraClave, MatchMode.ANYWHERE)));
		
		
		/*Agregamos al resultado las palabras concretas utilizadas para la consulta*/
		oReply.setPalabrasConcretas(palabras);
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 criteria.addOrder(Order.asc("desFraccion"));
		 criteria.addOrder(Order.asc("nomActividad"));
		 
		 
		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionPorClave(int)
	 */
	public Fraccion obtenerFraccionPorClave(String desFraccion) {
		StringBuffer bfr = new StringBuffer();
		bfr.append("from Fraccion where desFraccion = :desFraccion");
		Query query = this.getSession().createQuery(bfr.toString());
		query.setParameter("desFraccion", desFraccion);
		Fraccion fraccion = (Fraccion)query.uniqueResult();
		return fraccion;
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesActivasPorNumeroAnterior(int, java.lang.String, int, int)
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorNumeroAnterior(
			int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
			AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		System.out.println("Parametros...." + sSearch);
		
		
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			
			
			sSearch = sSearch.toUpperCase();
			
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones activas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.TRUE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		
		/*Iniciamos los filtros por fraccion anterior.*/
		
		
		/*Fltramos las fracciones Anteriores que en su nombre o descripcion contengan la palabraClave*/
		criteria.createCriteria("fraccionesNuevas", "fnva")
				.createCriteria("fnva.fraccionAnterior")
				.add(Restrictions.like("desFraccion",
						sSearch, MatchMode.EXACT));
		
		
	
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 criteria.addOrder(Order.asc("desFraccion"));
		 criteria.addOrder(Order.asc("nomActividad"));
		 
		 
		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesActivasPorNumero(int, java.lang.String, int, int)
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorNumero(
			int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
		
		AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			sSearch = sSearch.toUpperCase();
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones activas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.TRUE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		
		/*Iniciamos los filtros por desfraccion.*/
		
		criteria.add(Restrictions.like("desFraccion",
				sSearch, MatchMode.EXACT));
		
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 criteria.addOrder(Order.asc("desFraccion"));
		 criteria.addOrder(Order.asc("nomActividad"));
		 
		 
		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.repository.FraccionRepository#obtenerFraccionesActivasPorPalabra(int, java.lang.String, int, int)
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorPalabra(
			int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
		
		AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			sSearch = sSearch.toUpperCase();
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones activas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.TRUE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		/*Desechamos las palabras reservadas para solo dejar aquellas palabras que cuentan y agregarlas para la consulta del criteria*/
		List<String> palabras = this.getPalabrasConcretas(sSearch);
		
		/*Agregamos al resultado las palabras concretas utilizadas para la consulta*/
		oReply.setPalabrasConcretas(palabras);
		
		/*Agregamos al criteria las palabras encontradas*/
		this.setRestrictionPalabrasConcretas(criteria, palabras);
		
		/*Filtro por nombre o por descripcion de actividad.*/
//		criteria.add(Restrictions.or(Restrictions.like("nomActividad",
//				sSearch, MatchMode.ANYWHERE), Restrictions.like(
//				"desActividad", sSearch, MatchMode.ANYWHERE)));
	
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 criteria.addOrder(Order.asc("desFraccion"));
		 criteria.addOrder(Order.asc("nomActividad"));
		 
		 
		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}

	public AbstractDataTableReply obtenerFraccionesInactivasPorNumeroAnterior(
			String sSearch, int iDisplayLength, int iDisplayStart) {
		
		AbstractDataTableReply oReply = new FraccionDataTableReply();
		
		System.out.println("Parametros...." + sSearch);
		
		boolean bSearch = false;
		if(sSearch != null && !sSearch.equals("") ){
			sSearch = sSearch.toUpperCase();
		}
		
		List<Fraccion> result = null;
		
		/*Nuestro universo de datos son todas las fracciones inactivas-.*/
		
		Criteria criteria = this.getSession().createCriteria(Fraccion.class);
		criteria.add(Restrictions.eq("indActivo", Boolean.FALSE));
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		
		
		/*Iniciamos los filtros por fraccion anterior.*/
		
		
		/*Fltramos las fracciones Anteriores que en su nombre o descripcion contengan la palabraClave*/
		criteria.add(Restrictions.like("desFraccion",
						sSearch, MatchMode.EXACT));
		
		
	
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);
		 criteria.addOrder(Order.asc("desFraccion"));
		 criteria.addOrder(Order.asc("nomActividad"));
		 
		 
		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		 result = criteria.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength).list();
	     
		System.out.println(" Resultados .:::" + result);
	     
	     oReply.setAaData(result);
	     oReply.setiTotalDisplayRecords(iTotalDisplayRecords);
	     oReply.setiTotalRecords(iTotalRecords);
	     
		return oReply;
	}
	
	
	
	private static final String[] palabrasReservadas = new String[]{"A","E", "O", "U", "Y",  "AL" ,"ASI", "COMO", "COMPRENDE" , "CON", "CONSIDERAN", "CUAL", "DE" , "DEDICADAS", "DEDICAN", "DEL", "EL", "ELLAS", "EMPRESAS", "EN", "ESTA", "ESTE", "FRACCION", "INCLUYE", "LA" , "LAS", "LOS", "NI", "NO", "PARA", "POR", "QUE", "SE", "SIN", "SUS", "TAMBIEN", "Y/O", "YA"};
	
	/**
	 * Genera una lista con las palabras que no son reservadas para su busqueda.
	 * @param sSearch
	 * @return
	 */
	public List<String> getPalabrasConcretas(String sSearch){
		System.out.println("getPalabrasConcretas de [" + sSearch +"]");
		
		
		Arrays.sort(palabrasReservadas);
		
		List<String> palabras = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(sSearch);
		while(tokens.hasMoreTokens()){
			String s = tokens.nextToken().trim();
			System.out.println("Comparando la palabra["+s+"]");
			int r = Arrays.binarySearch(palabrasReservadas, s);
			System.out.println("Resultado de la comparacion:"+r);
			if( r < 0){
				
				palabras.add(s.toUpperCase());
			}
			
		}
		System.out.println("Palabras encontradas [ " + palabras + "]");
		return palabras;
		
	}
	
	
	
	/**
	 * Genera el criteria con todas las palabras no reservadas a buscar.
	 * @param criteria
	 * @param palabras
	 */
	public void setRestrictionPalabrasConcretas(Criteria criteria, List<String> palabras){
		System.out.println("Agregando al criteria las palabras ");
		Iterator<String> it = palabras.iterator();
		while( it.hasNext()){
			String sSearch = it.next();
			System.out.println("Agregando [" + sSearch +"]");
			criteria.add(Restrictions.or(Restrictions.like("nomActividad",
					sSearch, MatchMode.ANYWHERE), Restrictions.like(
					"desActividad", sSearch, MatchMode.ANYWHERE)));
		}
		
	}
	
	
	public static void main (String[] args){
		
		
		FraccionRepositoryHibernateImpl f = new FraccionRepositoryHibernateImpl();
		List<String> ps =f.getPalabrasConcretas("Materiales");
		if(ps != null && !ps.isEmpty()){
			Iterator it = ps.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
		}
	}
	
	
}
