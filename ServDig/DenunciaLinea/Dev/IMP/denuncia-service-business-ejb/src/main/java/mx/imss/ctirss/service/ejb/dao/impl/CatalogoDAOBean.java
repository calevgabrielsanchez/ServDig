package mx.imss.ctirss.service.ejb.dao.impl;
 

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;


import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.service.ejb.dao.CatalogoDAOLocal;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Stateless
public class CatalogoDAOBean <T extends AbstractModel> extends AbstractRespository implements CatalogoDAOLocal<T>{
	
//	private static Logger logger = Logger.getLogger(CatalogoDAOBean.class);
	
//	@PersistenceContext EntityManager manager;
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#agrega(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T agrega(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException();
		}

	}

	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#actualiza(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T actualiza(T model) {
		this.getSession().merge(model);
		this.getSession().flush();
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#elimina(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public void elimina(T model) {
		//model = (this.consultaPorClave(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}

	
	@SuppressWarnings("unchecked")
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		/*Se obtienen las propiedades que forman la llave primaria*/
		List<String> lsLlavePrimaria = CatalogoDAOBean.getLlavePrimaria(filtro.getClass());
		for(String sLlavePrimaria:lsLlavePrimaria){
			try {
				
				Field f = filtro.getClass().getField(sLlavePrimaria);
				criteria.add(Restrictions.eq(sLlavePrimaria,f.get(filtro)));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		filtro = (T) criteria.list().get(0);
		return filtro;
	}		

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());	
		Example e = CatalogoDAOBean.createExampleOf(params.getModelo());
		criteria.add(e);
		
		/*Se valida si la busqueda debera restringir los resultados por baja logica*/
		List<String> lsFiltrosBajLogica = CatalogoDAOBean.getFiltrosBajaLogica(params.getModelo());
		for(String sFiltro:lsFiltrosBajLogica){
			criteria.add(Restrictions.isNull(sFiltro));
		}

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		 
		 result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#consulta(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = CatalogoDAOBean.createExampleOf(filtro);
		criteria.add(e);
		/*Se valida si la busqueda debera restringir los resultados por baja logica*/
		List<String> lsFiltrosBajLogica = CatalogoDAOBean.getFiltrosBajaLogica(filtro);
		for(String sFiltro:lsFiltrosBajLogica){
			criteria.add(Restrictions.isNull(sFiltro));
		}		
		List<T> resultados = criteria.list();
		
		return resultados;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#consultaLibrePorClave(Long, String)
	 */
	public List<T> consultaLibrePorClave(Long claveConsultar, String query) {
		
		List<T> resultados = this.getSession().createQuery(query).list();
		return resultados;
	}



	@Override
	public DatosSalidaPaginador<T> paginaDiv(DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoPagos(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoPagosA(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoPagosR(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoPatrones(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaPromociones(
			DatosEntradaPaginador<T> params, Long subdel) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaCorrrecciones(
			DatosEntradaPaginador<T> params, Long subdel) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoConceptos(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAnexoConceptosC(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaAllPromociones(
			DatosEntradaPaginador<T> params, String subdel, String del) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaTipoOrigen(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DatosSalidaPaginador<T> paginaCriterioSeleccion(
			DatosEntradaPaginador<T> params) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public byte[] consultaLibrePorClaveObjeto(Long claveConsultar, String query) {
		// TODO Auto-generated method stub
		return null;
	}



	

	public DatosSalidaPaginador<T> paginaPatronesDenunciados(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		boolean bSearch = false;
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(DltDenuncia.class);

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);

		/*Iniciamos los filtros por CATALOGO.*/
		criteria.add(Restrictions.eq("dltUsuarioden.cveUsuarioden", sSearch)).
		setFetchMode("dltDatospatrons", FetchMode.LAZY).
		setFetchMode("dltMotivodenuncias", FetchMode.LAZY);
				
				//like("cgtPromocion.folio", sSearch,  MatchMode.ANYWHERE));

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		// criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
	     logger.debug(result);
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#consultaLibrePorClave(Long, String)
	 */
	
	
	public DatosSalidaPaginador<T> paginaDenuncias(DatosEntradaPaginador<T> params, DltUsuarioden user) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		boolean bSearch = false;
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(DltDenuncia.class);

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);

		/*Iniciamos los filtros por CATALOGO.*/
		criteria.add(Restrictions.eq("dltUsuarioden.cveUsuarioden", user.getCveUsuarioden())).
		setFetchMode("dlcStatus", FetchMode.LAZY).
		setFetchMode("dlcTipodenunciante", FetchMode.LAZY).
		setFetchMode("dltUsuarioden", FetchMode.LAZY).
		setFetchMode("dlcSubdelegacion", FetchMode.LAZY).
		setFetchMode("dltPersonas", FetchMode.LAZY).
		setFetchMode("dlcUsuarioFuncionario", FetchMode.LAZY);
		
		
				
				//like("cgtPromocion.folio", sSearch,  MatchMode.ANYWHERE));

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		// criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
	     logger.debug(result);
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#consultaLibrePorClave(Long, String)
	 */



	@Override
	public List<T> consultaSQL(String query) {
		try{
			return this.getSession().createSQLQuery(query).list();
		}catch(Exception e){
			return null;
		}
	}
	


	
	

	
}
