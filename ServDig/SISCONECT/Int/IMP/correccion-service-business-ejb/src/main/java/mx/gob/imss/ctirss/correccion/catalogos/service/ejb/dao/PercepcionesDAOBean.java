package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.impl.CatalogoDAOBean;

@Stateless
public class PercepcionesDAOBean<T extends AbstractModel> extends AbstractRespository implements PercepcionesDAOLocal<T> {
	
	public T agrega(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}

	}
	
	public T elimina(T model) {
		model = (this.consultaPorClave(model));
		
		try{
			this.getSession().delete(model);
			this.getSession().flush();
			model.setError(ConstantesBusiness.NO_ERRROR);
		}catch(HibernateException e){
			e.printStackTrace();
			
			if(e.toString().contains("ConstraintViolationException")){
				model.setError("No se pudo eliminar el registro\n "
						      +"porque tiene referencias dentro del sistema");
			}
			
			
		}
		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = PercepcionesDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		
		return resultados;
	}
	
	public T modifica(T model) {
		this.getSession().merge(model);
		this.getSession().flush();
		return model;
	}
	
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cvePercepcion", ((CrcPercepciones)filtro).getCvePercepcion()));
											 
		filtro = (T) criteria.list().get(0);
		return filtro;
	}
	
	@Override
	public List<T> consultaPorCveSolCorr(T filtro) {
		// TODO Auto-generated method stub
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
				 .add(Restrictions.eq("cveSolicitudCorr", ((CrcPercepciones)filtro).getCveSolicitudCorr()));				 
		return (List<T>) criteria.list();
	}
	
	
	
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


}
