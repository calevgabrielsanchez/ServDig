package mx.imss.ctirss.menu.service.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.login.model.SegMenu;
import mx.imss.ctirss.menu.model.Menu;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless
public class MenuDAOBean<T extends AbstractModel> extends AbstractRespository
		implements MenuDAOLocal<T> {

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(MenuDAOBean.class);

	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		final StringBuffer querySB = new StringBuffer();
		querySB.append("select m from ");
		querySB.append(filtro.getClass().getSimpleName()).append(" as m ");
		querySB.append(" left outer join m.dlcMenusistemas as ms");
		querySB.append(" inner join ms.dlcRol as r");
		querySB.append(" left outer join r.dlcPerfilUsuarios as p");
		querySB.append(" inner join p.dlcUsuario as u");
		querySB.append(" where u.cveIdUsuario = ");
		querySB.append(((SegMenu) filtro).getIdUsuario());
		querySB.append(" and p.cveIdPerfilUsuario = ");
		querySB.append(((SegMenu) filtro).getIdPerfil());
		querySB.append(" order by m.cveIdMenu asc, m.dlcMenu desc, m.numOrden asc");
		logger.debug("querySB :: " + querySB);
		List<T> resultados = getSession().createQuery(querySB.toString())
				.list();

		return resultados;
	}

	@SuppressWarnings("unchecked")
	public List<T> consultaDictamen(T filtro) {
		Criteria criteria = this.getSession().createCriteria(Menu.class);
		List<T> listaMenu = criteria.list();
		return listaMenu;
	}

	
	

}
