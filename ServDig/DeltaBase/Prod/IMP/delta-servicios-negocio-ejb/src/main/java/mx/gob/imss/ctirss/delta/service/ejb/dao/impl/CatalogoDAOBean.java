package mx.gob.imss.ctirss.delta.service.ejb.dao.impl;
 
import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.delta.util.DeltaUtils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Stateless
public class CatalogoDAOBean <T extends AbstractModel> extends AbstractServiceEntity implements CatalogoDAOLocal<T>{
	
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
			throw new PersistenceException(re);
		}

	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#actualiza(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T actualiza(T model) throws PersistenceException{
		try{
			this.getSession().merge(model);
			this.getSession().flush();
			return model;			
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}

	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#elimina(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public void elimina(T model)  throws PersistenceException{
		try{
			model = (this.consultaPorClave(model));
			this.getSession().delete(model);
			this.getSession().flush();
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}
	}

	
	@SuppressWarnings("unchecked")
	public T consultaPorClave(T filtro)  throws PersistenceException{
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		/*Se obtienen las propiedades que forman la llave primaria*/
		List<String> lsLlavePrimaria = CatalogoDAOBean.getLlavePrimaria(filtro);
		for(String sLlavePrimaria:lsLlavePrimaria){

				
				Field f;
				try {
					f = filtro.getClass().getField(sLlavePrimaria);
					criteria.add(Restrictions.eq(sLlavePrimaria,f.get(filtro)));
				} catch (IllegalAccessException e) {
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
		
		//Valida si realizara busqueda por alguna llave foranea
		boolean bBuscaForanea = false;
		if(params.getsSearch() != null && !params.getsSearch().equals("") ){
			bBuscaForanea = true;
		}			
		
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
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		//Valida si se debe filtrar por alguna llave foranea
		if(bBuscaForanea){
			DeltaUtils dutils = new DeltaUtils();
			try{
				//Obtiene las llaves foraneas por las cuales debe realizar el filtro
				String[] asForaneas = params.getsSearch().split("\\|");
				for(String sForanea: asForaneas){
					criteria.add(Restrictions.eq(sForanea, dutils.getValorCampodeObjeto(params.getModelo(), sForanea)));	
				}
			}catch(Exception eFiltro){
				eFiltro.printStackTrace();
			}
		}

		
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		criteria.setProjection(null);

		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		 
		result = criteria.setFirstResult(params.getiDisplayStart()).setMaxResults(params.getiDisplayLength()).list();
	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#consulta(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro)  throws PersistenceException{
		try{
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
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}
	}
	

}