package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.integracion.vo.ConceptosOmitidosVO;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoOrigen;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoConceptoOmitido;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoPago;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoRP;
import mx.gob.imss.ctirss.correccion.model.CgtCorreccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.Division;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
	
	
	/* Metodo que agrega un MODEL en BD
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
		model = (this.consultaPorClave(model));
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
				e.printStackTrace();
			}catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
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
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaDiv(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(
				Division.class);

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
		criteria.add(Restrictions.like("desDivision", sSearch,  MatchMode.ANYWHERE));

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
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoPago.class);

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
		criteria.add(Restrictions.eq("folio", sSearch));
				
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

	public DatosSalidaPaginador<T> paginaAnexoPagosA(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoPago.class);

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
		criteria.add(Restrictions.eq("folio", sSearch));
		criteria.add(Restrictions.eq("idProceso", 1));
				
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
	
	
	public DatosSalidaPaginador<T> paginaAnexoPagosR(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoPago.class);

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
		criteria.add(Restrictions.eq("folio", sSearch));
		criteria.add(Restrictions.eq("idProceso", 2));
				
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
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaPromociones(DatosEntradaPaginador<T> params, Long subdel) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtPromocion.class).
				add(Restrictions.eq("sacSubdelegacion.cvePk", subdel)).
				add(Restrictions.isNull("foliocorreccion")).
				addOrder(Order.asc("cgcCatTipo.idTipo")).
				addOrder(Order.desc("folio")).
				setFetchMode("cgcCatOrigen", FetchMode.LAZY).
				setFetchMode("cgcCatTipo", FetchMode.LAZY).
				setFetchMode("cgtCatCriterioSeleccion", FetchMode.LAZY).
				setFetchMode("sacSubdelegacion", FetchMode.LAZY);
				logger.debug(sSearch);
				 
		 if(sSearch!=null && !sSearch.equals("")){
			String[] parametros = sSearch.split("\\|");
			logger.debug(parametros.toString());
			for(int i=0;i<parametros.length; i++){
				logger.debug(parametros.toString());
				
				String[] map = parametros[i].toString().split(":");
				logger.debug(map.toString());
				logger.debug(map);
				String llave = map[0];
				
				if(!llave.equals("-1") && map.length>=2){
					
					String value = map[1];	
					logger.debug(llave);
					logger.debug(value);
					if(llave.equalsIgnoreCase("findClasificacion")){
						criteria.add(Restrictions.eq("cgcCatTipo.idTipo", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFuente")){
						criteria.add(Restrictions.eq("cgcCatOrigen.idOrigen", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFolio")){
						criteria.add(Restrictions.eq("folio", value));
					}
					if(llave.equalsIgnoreCase("findRP")){
						criteria.add(Restrictions.eq("cvePatron", value));
					}
					if(llave.equalsIgnoreCase("findNombre")){
						criteria.add(Restrictions.like("nombre", "%"+value+"%"));
					}
					if(llave.equalsIgnoreCase("findAfil")){
						criteria.add(Restrictions.eq("afil15",value));
					}
					if(llave.equalsIgnoreCase("idTipo")){
						ArrayList lista = new ArrayList();
						long val1 = 0;
						if(value.equals("3"))
						{
							val1 = 5;
							criteria.add(Restrictions.isNotNull("sp"));
						}else if(value.equals("4")){
							val1 = 5;
							criteria.add(Restrictions.isNotNull("oi"));
						}else if(value.equals("1")){
							val1 = 6;
						}
						lista.add(val1);
						criteria.add(Restrictions.in("cgcCatTipo.idTipo", lista));
				
				}
						}
				
			}
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
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaCorrrecciones(DatosEntradaPaginador<T> params, Long subdel) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		boolean bSearch = false;
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtCorreccion.class).
				add(Restrictions.eq("sacSubdelegacion.cvePk", subdel)).
				add(Restrictions.isNotNull("cgcCatStatus")).
				add(Restrictions.isNotNull("cgcCatOrigen")).
				add(Restrictions.isNotNull("cgtCatCriterioSeleccion")).
				add(Restrictions.isNotNull("periododel")).
				add(Restrictions.isNotNull("periodoal")).
				setFetchMode("cgcCatOrigen", FetchMode.LAZY).
				setFetchMode("cgcCatTipo", FetchMode.LAZY).
				setFetchMode("cgcCatStatus", FetchMode.LAZY).
				setFetchMode("cgtCatCriterioSeleccion", FetchMode.LAZY).
				setFetchMode("sacSubdelegacion", FetchMode.LAZY);
		
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		 logger.debug(sSearch);
		 if(sSearch!=null && !sSearch.equals("")){
			String[] parametros = sSearch.split("\\|");
			logger.debug(parametros.toString());
			for(int i=0;i<parametros.length; i++){
				logger.debug(parametros.toString());
				
				String[] map = parametros[i].toString().split(":");
				logger.debug(map.toString());
				logger.debug(map);
				String llave = map[0];
				if(!llave.equals("-1") && map.length>=2 ){
					String value = map[1];	
					logger.debug(llave);
					logger.debug(value);
					if(llave.equalsIgnoreCase("findClasificacion")){
						criteria.add(Restrictions.eq("cgcCatTipo.idTipo", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFuente")){
						criteria.add(Restrictions.eq("cgcCatOrigen.idOrigen", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFolio")){
						criteria.add(Restrictions.eq("folio", value));
					}
					if(llave.equalsIgnoreCase("findRP")){
						criteria.add(Restrictions.eq("cvePatron", value));
					}
					if(llave.equalsIgnoreCase("findNombre")){
						criteria.add(Restrictions.like("nombre", "%"+value+"%"));
					}
					if(llave.equalsIgnoreCase("findAfil")){
						criteria.add(Restrictions.eq("afil15",value));
					}
					if(llave.equalsIgnoreCase("idTipo")){
						ArrayList lista = new ArrayList();
						long val1 = 0;
						if(llave.equals("3") || llave.equals("4"))
						{
							val1 = 5;
						}else if(llave.equals("1")){
							val1 = 6;
						}
						lista.add(val1);
						criteria.add(Restrictions.in("cgcCatTipo.idTipo", lista));
				
				}
						
				}
				
			}
		 }
		
		 
		
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

		 this.paginaCorrreccionesAddOrder(params, criteria);
		 
		// criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
	     logger.debug(result);
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	/**
	 * 
	 * @param params
	 * @param criteria
	 */
	private void paginaCorrreccionesAddOrder(DatosEntradaPaginador<T> params,
			Criteria criteria) {
		// TODO VAP incluir todas las columnas
		String colOrder = params.getiSortCol_0();
		String colDir = params.getsSortDir_0();
		if (colOrder.equals("0")) {
			criteria.addOrder(Order.desc("fecFechareg"));
		} else if (colOrder.equals("1")) {
			addOrder(criteria, colDir, "cgcCatTipo");
		} else if (colOrder.equals("2")) {
			addOrder(criteria, colDir, "cgcCatOrigen");
		} else if (colOrder.equals("3")) {
			addOrder(criteria, colDir, "folio");
		} else if (colOrder.equals("4")) {
			addOrder(criteria, colDir, "cvePatron");
		} else if (colOrder.equals("5")) {
			addOrder(criteria, colDir, "cvePatron");
		} else if (colOrder.equals("6")) {
			addOrder(criteria, colDir, "periododel");
		} else if (colOrder.equals("7")) {
			addOrder(criteria, colDir, "periodoal");
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAllPromociones(DatosEntradaPaginador<T> params, String subdel, String del) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		boolean bSearch = false;
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtPromocion.class).
				add(Restrictions.eq("sacSubdelegacion.cvePk", Long.parseLong(subdel))).
				add(Restrictions.isNull("foliocorreccion")).
				setFetchMode("cgcCatOrigen", FetchMode.LAZY).
				setFetchMode("cgcCatTipo", FetchMode.LAZY).
				setFetchMode("cgtCatCriterioSeleccion", FetchMode.LAZY).
				setFetchMode("sacSubdelegacion", FetchMode.LAZY);
		
		 
		 logger.debug(sSearch);
		 if(sSearch!=null && !sSearch.equals("")){
			String[] parametros = sSearch.split("\\|");
			logger.debug(parametros.toString());
			for(int i=0;i<parametros.length; i++){
				logger.debug(parametros.toString());
				
				String[] map = parametros[i].toString().split(":");
				logger.debug(map.toString());
				logger.debug(map);
				String llave = "";
				String value = "";
				if(map.length>1){
					llave = map[0];
					value = map[1];
					logger.debug(llave);
					logger.debug(value);
					if(llave.equalsIgnoreCase("findClasificacion")){
						criteria.add(Restrictions.eq("cgcCatTipo.idTipo", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFuente")){
						criteria.add(Restrictions.eq("cgcCatOrigen.idOrigen", Long.parseLong(value)));
					}
					if(llave.equalsIgnoreCase("findFolio")){
						criteria.add(Restrictions.eq("folio", value));
					}
					if(llave.equalsIgnoreCase("findRP")){
						criteria.add(Restrictions.eq("cvePatron", value));
					}
					if(llave.equalsIgnoreCase("findNombre")){
						criteria.add(Restrictions.like("nombre", "%"+value+"%"));
					}
					if(llave.equalsIgnoreCase("findAfil")){
						criteria.add(Restrictions.eq("afil15",value));
					}
					
				}
				
			}
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

		logger.debug(sSearch);
		
		
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
		
		this.paginaAllPromocionesAddOrder(params, criteria);
		
		result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
	     logger.debug(result);
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}

	/**
	 * @param params
	 * @param criteria
	 */
	private void paginaAllPromocionesAddOrder(DatosEntradaPaginador<T> params,
			Criteria criteria) {
		String colOrder = params.getiSortCol_0();
		String colDir = params.getsSortDir_0();
		if (colOrder.equals("0")) {
			criteria.addOrder(Order.desc("fecFechareg"));
		} else if (colOrder.equals("1")) {
			addOrder(criteria, colDir, "cgcCatTipo");
		} else if (colOrder.equals("2")) {
			addOrder(criteria, colDir, "cgcCatOrigen");
		} else if (colOrder.equals("3")) {
			addOrder(criteria, colDir, "folio");
		} else if (colOrder.equals("4") || colOrder.equals("5")) {
			addOrder(criteria, colDir, "cvePatron");
		} else if (colOrder.equals("6")) {
			addOrder(criteria, colDir, "afil15");
		} else if (colOrder.equals("7")) {
			addOrder(criteria, colDir, "sp");
		} else if (colOrder.equals("8")) {
			addOrder(criteria, colDir, "oi");
		} else if (colOrder.equals("9")) {
			addOrder(criteria, colDir, "pr");
		}
	}

	
	
	
	/**
	 * 
	 * @param criteria
	 * @param dir
	 * @param field
	 */
	private void addOrder(Criteria criteria, String dir, String field) {
		if (dir.equals("asc")) {
			criteria.addOrder(Order.asc(field));
		} else {
			criteria.addOrder(Order.desc(field));
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoPatrones(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoRP.class);

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
		criteria.add(Restrictions.eq("id.folio", sSearch));
				
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> consultaSQL(String query) {
		
		try{
			return this.getSession().createSQLQuery(query).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
				
	}
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoConceptos(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoConceptoOmitido.class);

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
		criteria.add(Restrictions.eq("id.folio", sSearch));
		criteria.add(Restrictions.eq("id.idProceso", 1L));// TODO consultar por proceso
		criteria.add(Restrictions.eq("id.cgcCatSituacionCO.idSituacionco", 1L));// TODO consultar por proceso
		
		criteria.addOrder(Order.asc("id.cgcCatConceptoOmitido.idConceptoomitido"));
				
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
	     
		result = criteria.setFirstResult(params.getiDisplayStart()).list();
	     
	     logger.debug(result);
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoConceptosC(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";
		
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoConceptoOmitido.class);

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
		criteria.setProjection(null);

		/*Iniciamos los filtros por CATALOGO.*/
		criteria.add(Restrictions.eq("id.folio", sSearch));
		criteria.add(Restrictions.eq("id.idProceso", 2L)); 
		//TODO consultar por proceso
		
		criteria.addOrder(Order.asc("id.cgcCatConceptoOmitido.idConceptoomitido"));
				
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
	     
		result = criteria.setFirstResult(params.getiDisplayStart()).list();
	    List listaNueva = new ArrayList();
		for(int i=0; i<result.size(); i ++){
				int j = i + 1;
				CgtAnexoConceptoOmitido co1 = (CgtAnexoConceptoOmitido)result.get(i);
				CgtAnexoConceptoOmitido co2 = null;
				if(j >= result.size()){
					co2 = null;
				}else{
					co2 = (CgtAnexoConceptoOmitido)result.get(j);
				}
				
								
				
				if(co2!= null && co1.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() == co2.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() ){
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					temp.setFolio(co1.getId().getFolio());
					temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
					
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
					}
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 2){
						temp.setAclarado(co1.getId().getCgcCatSituacionCO());
					}
					
					if(co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co2.getId().getCgcCatSituacionCO());
					}
					if(co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 2 ){
						temp.setAclarado(co2.getId().getCgcCatSituacionCO());
					}
					
					
					
					temp.setIdProceso(co1.getId().getIdProceso());
					listaNueva.add(temp);
					i++;
					continue;
				}else{
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco()!=3){
						temp.setFolio(co1.getId().getFolio());
						temp.setIdProceso(co1.getId().getIdProceso());
						temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
							temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
						}
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 2){
							temp.setAclarado(co1.getId().getCgcCatSituacionCO());
						}
						
						listaNueva.add(temp);
					}
				}
		}
		
		iTotalDisplayRecords = listaNueva.size();
	     logger.debug(listaNueva);
		response.setAaData(listaNueva);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoConceptosPAI(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";

		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoConceptoOmitido.class);

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
		criteria.add(Restrictions.eq("id.folio", sSearch));
		criteria.add(Restrictions.eq("id.idProceso", 2L)); 
		//TODO consultar por proceso
		
		criteria.addOrder(Order.asc("id.cgcCatConceptoOmitido.idConceptoomitido"));
				
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		// criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	     
		result = criteria.setFirstResult(params.getiDisplayStart()).list();
	    List listaNueva = new ArrayList();
		for(int i=0; i<result.size(); i ++){
				int j = i + 1;
				CgtAnexoConceptoOmitido co1 = (CgtAnexoConceptoOmitido)result.get(i);
				CgtAnexoConceptoOmitido co2 = null;
				if(j >= result.size()){
					co2 = null;
				}else{
					co2 = (CgtAnexoConceptoOmitido)result.get(j);
				}
				
								
				
				if(co2!= null && co1.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() == co2.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() ){
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					temp.setFolio(co1.getId().getFolio());
					temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
					
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
					}
					if( co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
						temp.setNoAclarado(co1.getId().getCgcCatSituacionCO());
					}
					if(co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co2.getId().getCgcCatSituacionCO());
					}
					if( co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
						temp.setNoAclarado(co2.getId().getCgcCatSituacionCO());
					}
					
					temp.setIdProceso(co1.getId().getIdProceso());
					listaNueva.add(temp);
					i++;
					continue;
				}else{
					
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco()!=2){
						temp.setFolio(co1.getId().getFolio());
						temp.setIdProceso(co1.getId().getIdProceso());
						temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
					
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
					
							temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
						}
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
					
							temp.setNoAclarado(co1.getId().getCgcCatSituacionCO());
						}
						listaNueva.add(temp);
					}
				}
		}
		
		iTotalDisplayRecords = listaNueva.size();
	     logger.debug(listaNueva);
		response.setAaData(listaNueva);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaAnexoConceptosAPAI(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		String sSearch= "";

		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			sSearch = params.getsSearch().toUpperCase();
		}
		
		List<T> result = null;
		
		/*Nuestro universo de datos son todas los elementos del catalgo*/
		
		Criteria criteria = this.getSession().createCriteria(CgtAnexoConceptoOmitido.class);

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
		criteria.add(Restrictions.eq("id.folio", sSearch));
		criteria.add(Restrictions.eq("id.idProceso", 1L)); 
		//TODO consultar por proceso
		
		criteria.addOrder(Order.asc("id.cgcCatConceptoOmitido.idConceptoomitido"));
				
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
	     
		result = criteria.setFirstResult(params.getiDisplayStart()).list();
	    List listaNueva = new ArrayList();
		for(int i=0; i<result.size(); i ++){
				int j = i + 1;
				CgtAnexoConceptoOmitido co1 = (CgtAnexoConceptoOmitido)result.get(i);
				CgtAnexoConceptoOmitido co2 = null;
				if(j >= result.size()){
					co2 = null;
				}else{
					co2 = (CgtAnexoConceptoOmitido)result.get(j);
				}
				
								
				
				if(co2!= null && co1.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() == co2.getId().getCgcCatConceptoOmitido().getIdConceptoomitido() ){
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					temp.setFolio(co1.getId().getFolio());
					temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
				
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
					}
					if( co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
						temp.setNoAclarado(co1.getId().getCgcCatSituacionCO());
					}
					if(co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
						temp.setPagoRecibido(co2.getId().getCgcCatSituacionCO());
					}
					if( co2.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
						temp.setNoAclarado(co2.getId().getCgcCatSituacionCO());
					}
					
					temp.setIdProceso(co1.getId().getIdProceso());
					listaNueva.add(temp);
					i++;
					continue;
				}else{
					
					ConceptosOmitidosVO temp = new ConceptosOmitidosVO();
					if(co1.getId().getCgcCatSituacionCO().getIdSituacionco()!=2){
						temp.setFolio(co1.getId().getFolio());
						temp.setIdProceso(co1.getId().getIdProceso());
						temp.setCgcCatConceptoOmitido(co1.getId().getCgcCatConceptoOmitido());
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 1){
							temp.setPagoRecibido(co1.getId().getCgcCatSituacionCO());
						}
						if(co1.getId().getCgcCatSituacionCO().getIdSituacionco() == 3){
							temp.setNoAclarado(co1.getId().getCgcCatSituacionCO());
						}
						listaNueva.add(temp);
					}
				}
		}
		
		iTotalDisplayRecords = listaNueva.size();
	     logger.debug(listaNueva);
		response.setAaData(listaNueva);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}

	
	
	@Override
	public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		CgcCatTipo tipo = (CgcCatTipo) params.getModelo();

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());	
		criteria.createCriteria("cgcCatflujo").add(Restrictions.eq("idFlujo", tipo.getCgcCatflujo().idFlujo));
		if(tipo.getCboSeccionDesc() != null){
			criteria.add(Restrictions.like("descripcion", tipo.getCboSeccionDesc(), MatchMode.ANYWHERE));
		}
		

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
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

	@Override
	public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		CgcCatOrigen tipo = (CgcCatOrigen) params.getModelo();

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());	
		if(tipo.getDescOrigen() != null){
			criteria.add(Restrictions.like("descOrigen", tipo.getDescOrigen(), MatchMode.ANYWHERE));
		}
		

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
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

	@Override
	public DatosSalidaPaginador<T> paginaTipoOrigen(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		CgcCatTipoOrigen tipo = (CgcCatTipoOrigen) params.getModelo();

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());
		if(tipo.getCgcCatTipo() != null && tipo.getCgcCatTipo().getIdTipo() != -1){
			criteria.createCriteria("cgcCatTipo").add(Restrictions.eq("idTipo", tipo.getCgcCatTipo().getIdTipo()));
		}
		if(tipo.getCgcCatOrigen() != null && tipo.getCgcCatOrigen().getIdOrigen() != -1){
			criteria.createCriteria("cgcCatOrigen").add(Restrictions.eq("idOrigen", tipo.getCgcCatOrigen().getIdOrigen()));
		}
		if(tipo.getDescripcion() != null){
			criteria.add(Restrictions.like("descripcion", tipo.getDescripcion(), MatchMode.ANYWHERE));
		}
		

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
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

	@Override
	public DatosSalidaPaginador<T> paginaCriterioSeleccion(	DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		CgcCatcriterioseleccion tipo = (CgcCatcriterioseleccion) params.getModelo();

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());
		if(tipo.getIdTipo() > 0L){
			criteria.add(Restrictions.eq("idTipo", tipo.getIdTipo()));
		}
		if(tipo.getIdOrigen() > 0L){
			criteria.add(Restrictions.eq("idOrigen", tipo.getIdOrigen()));
		}
				

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
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

	@Override
	public List<T> consultaLibrePorClavePagina(Long claveConsultar, int limiteInferior, int tamPage, String query) {
		
		Query consulta=this.getSession().createQuery(query);
		consulta.setFirstResult(limiteInferior);
		consulta.setMaxResults(limiteInferior+tamPage);
		List<T> resultados = consulta.list();		
		return resultados;
		
	}
	
	
}
