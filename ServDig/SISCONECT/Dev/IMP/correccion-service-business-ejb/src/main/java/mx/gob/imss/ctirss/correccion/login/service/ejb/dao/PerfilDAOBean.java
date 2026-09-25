package mx.gob.imss.ctirss.correccion.login.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.login.model.SegPerfilUsuario;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;

@Stateless
public class PerfilDAOBean<T extends AbstractModel> extends AbstractRespository
		implements PerfilDAOLocal<T> {

	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(PerfilDAOBean.class);

	@Override
	public List<T> recuperarPerfilesDisponibles(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		criteria.add(org.hibernate.criterion.Restrictions.eq(
				"segUsuario.cveIdUsuario", ((SegPerfilUsuario) filtro)
						.getSegUsuario().getCveIdUsuario()));
		logger.debug("criteria :: " + criteria);
		final List<T> resp = criteria.list();
		logger.debug("resp :: " + resp.size());
		return resp;
	}

}
