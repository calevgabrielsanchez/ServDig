package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.PromocionDAOBean;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.impl.CatalogoDAOBean;

@Stateless
public class RegularizacionDAOBean<T extends AbstractModel> extends AbstractRespository implements RegularizacionDAOLocal<T> {

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
	
	public void elimina(T model) {
		model = (this.consultaPorClavePago(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = RegularizacionDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		
		return resultados;
	}
	
	
	public List<T> consultaPagosDetPorCvePago(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											.add(Restrictions.eq("crtRegulapagos.cveRegulapagos", ((CrtRegulapagosdet)filtro).getCrtRegulapagos().getCveRegulapagos()));
		Example e = RegularizacionDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		
		return resultados;
	}
	
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cveRegulapagos", ((CrtRegulapagos)filtro).getCveRegulapagos()));
		filtro = (T) criteria.list().get(0);
		return filtro;
	}
	
	public T consultaPorClavePago(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cveRegulapagosdet", ((CrtRegulapagosdet)filtro).getCveRegulapagosdet()));
		filtro = (T) criteria.list().get(0);
		return filtro;
	}
	
	public T consultaPorClavePromocion(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cvePromocion", ((CrtRegulapagos)filtro).getCvePromocion()));
		if(criteria.list().size()>0){
			filtro = (T) criteria.list().get(0);
		}
		
		return filtro;
	}
	
	public T modifica(T model) {
		this.getSession().update(model);
		this.getSession().flush();
		return model;
	}
	
	public DatosSalidaPaginador<T> paginaPagos(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass())
											 .add(Restrictions.eq("crtRegulapagos.cveRegulapagos", ((CrtRegulapagosdet)params.getModelo()).getCrtRegulapagos().getCveRegulapagos()));	
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
