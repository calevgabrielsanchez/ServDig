package mx.imss.estrados.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.repository.AbstractRespository;
import mx.imss.estrados.service.ejb.dao.ConsultaExternaDAO;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class ConsultaExternaDAOImpl extends AbstractRespository implements ConsultaExternaDAO {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ConsultaExternaDAOImpl.class);
	
	/**
	 * Metodo para obtener el numero de registros de las notificaciones existentes en la base de datos
	 * @return Integer
	 */
	@Override
	public Integer contarTotalRegistros() {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", new Integer(2)));
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistros = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistros;
	}
	
	/**
	 * Metodo para obtener el numero de registros filtrados de las notificaciones existentes en la base de datos
	 * @return Integer
	 */
	@Override
	public Integer contarRegistrosFiltrados(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		if (!paginadoRequest.getSearch().isEmpty()) {
			criteria.add(Restrictions.like("razonSocial", "%"+paginadoRequest.getSearch().toUpperCase()+"%"));
		}
		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", new Integer(2)));
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistrosMostrar = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistrosMostrar;
	}
	
	/**
	 * Metodo para obtener un listado filtrado de las notificaciones existentes en la base de datos
	 * @param PaginadoRequest
	 * @return List<NeeNotificaciones>
	 */
	@Override
	public List<NeeNotificaciones> filtrar(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		criteria.setFirstResult(paginadoRequest.getDisplayStart());
		criteria.setMaxResults(paginadoRequest.getDisplayLength());
		if (!paginadoRequest.getSearch().isEmpty()) {
			criteria.add(Restrictions.like("razonSocial", "%"+paginadoRequest.getSearch().toUpperCase()+"%"));
		}
		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", new Integer(2)));
		String sortColum = null;
		switch (paginadoRequest.getFiltroColumna().getSortCol()) {
	        case 0:
	        	sortColum = "razonSocial";
	            break;
	        case 1:
	        	sortColum = "neeCatAreaRespNotif";
	            break;
	        case 2:
	        	sortColum = "neeCatTipodocumento";
	            break;
	        case 3:
	        	sortColum = "fecPublicacion";
	            break;
	    }
		if (paginadoRequest.getFiltroColumna().getSortDir().equals("asc")) {
			criteria.addOrder( Order.asc(sortColum) );
			criteria.addOrder( Order.asc("cveNotificaciones") );
		} else {
			criteria.addOrder( Order.desc(sortColum) );
			criteria.addOrder( Order.desc("cveNotificaciones") );
		}
		List<NeeNotificaciones> listNeeNotificaciones = (List<NeeNotificaciones>) criteria.list();
		return listNeeNotificaciones;
	}

	@Override
	public SsoVwUsuario obtenerSsoVwUsuario(String cveUsuario) {
		SsoVwUsuario ssoVwUsuario;
		System.out.println("Entra 2 CURP");
		Criteria criteria = getSession().createCriteria(SsoVwUsuario.class);
		criteria.add(Restrictions.eq("desUsrCURP", cveUsuario));
		ssoVwUsuario = (SsoVwUsuario) criteria.list().get(0);
		return ssoVwUsuario;
	}
	
}
