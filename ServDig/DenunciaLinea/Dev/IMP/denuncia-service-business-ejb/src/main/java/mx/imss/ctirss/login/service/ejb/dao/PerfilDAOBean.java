package mx.imss.ctirss.login.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import mx.imss.ctirss.catalogos.model.DlcPerfilUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.login.model.SegPerfilUsuario;

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
				"dlcUsuario.cveIdUsuario", ((DlcPerfilUsuario) filtro)
						.getDlcUsuario().getCveIdUsuario()));
		logger.debug("criteria :: " + criteria);
		final List<T> resp = criteria.list();
		logger.debug("resp :: " + resp.size());
		return resp;
	}

}
