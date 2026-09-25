package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.service.ejb.dao.impl.CatalogoDAOBean;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad;

@Stateless
public class DomiciliosInegiDAOBean<T extends AbstractModel> extends AbstractRespository implements DomiciliosInegiDAOLocal<T> {
	
	public T agrega(T model) throws PersistenceException{
		try{
			
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveLoc(((DgDomicilioGeografico)model).getDgCatLocalidad().getId().getCveLoc());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCvePeriodo(1);
			
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCvePeriodo(1);
			
			((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveAsen(((DgDomicilioGeografico)model).getDgAsentamiento().getId().getCveAsen());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveLoc(((DgDomicilioGeografico)model).getDgCatLocalidad().getId().getCveLoc());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCvePeriodo(1);
			((DgDomicilioGeografico)model).getDgCodigosPostales().setDgAsentamiento(((DgDomicilioGeografico)model).getDgAsentamiento());

			DgVialidad dgVialiad3 = ((DgDomicilioGeografico)model).getDgVialidadByCveViaRef3();
			if(dgVialiad3==null || 
					(dgVialiad3!=null && dgVialiad3.getCveVia()==null) || 
					(dgVialiad3!=null && dgVialiad3.getCveVia()!=null && dgVialiad3.getCveVia().intValue()<=0)){
				
				((DgDomicilioGeografico)model).setDgVialidadByCveViaRef3(null);
				
			}
				
			
			
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
		model = (this.consultaPorClave(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = DomiciliosInegiDAOBean.createExampleOf(filtro);
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
											 .add(Restrictions.eq("domicilioId", ((DgDomicilioGeografico)filtro).getDomicilioId()));
		try{
			
			filtro = (T) criteria.list().get(0);
			
		}catch(Exception e){ filtro = null;}
		
		return filtro;
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
