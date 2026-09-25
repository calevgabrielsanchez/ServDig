package mx.gob.imss.ctirss.correccion.monitor.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao.DeteccionDAOBean;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtErrorCargaCed;
import mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao.ProrrogaDAOBean;

@Stateless
public class MonitorDAOBean  <T extends AbstractModel> extends AbstractRespository implements MonitorDAOLocal<T> {

	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(MonitorDAOBean.class);

	public T modificar(T model) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<T> consultar(T filtro) {
		/*
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = MonitorDAOBean.createExampleOf(filtro);
		criteria.add(e).addOrder(Order.asc("id"));
		List<T> resultados = criteria.list();
		
		return resultados;
		
		*/
		 List<T> resultados = null;
		
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
				// .add(Restrictions.eq("id.cveEjercicio", ((CrtControlFlujoCedula)filtro).getId().getCveEjercicio()))
				 .add(Restrictions.eq("id.cveSolicitudcorr", ((CrtControlFlujoCedula)filtro).getId().getCveSolicitudcorr())).addOrder(Order.asc("id.cveEjercicio")).addOrder(Order.asc("id.cveCedula"));
		
		if( !criteria.list().isEmpty()){
			logger.debug("************ ControlesCedulasEncontrados");
			resultados = criteria.list();
		}
			
		return resultados;
	}

	public T consultaPorClave(T model) {
		// TODO Auto-generated method stub
		return null;
	}



	public T agregar(T model) {
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			System.out.println(".-.ERROR"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	@Override
	public void eliminar(T model) {
		// TODO Auto-generated method stub
		
	}

	public List<T> consultarErrores(CrtErrorCargaCed cargaCed) {
		// TODO Auto-generated method stub
		logger.debug("************ QueryconsultarErrores");
		List<T> resultado = null;

		Criteria criteria = this.getSession().createCriteria(cargaCed.getClass())
				 .add(Restrictions.eq("crtControlFlujoCedula", cargaCed.getCrtControlFlujoCedula()));
		
		if( !criteria.list().isEmpty()){
			logger.debug("************ ErroresEncontrados");
			resultado = criteria.list();
		}
			
	
		return resultado;
	}


	
}
