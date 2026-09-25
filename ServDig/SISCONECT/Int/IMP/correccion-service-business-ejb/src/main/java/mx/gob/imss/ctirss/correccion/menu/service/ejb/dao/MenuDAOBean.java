package mx.gob.imss.ctirss.correccion.menu.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.login.model.SegMenu;

import org.apache.log4j.Logger;

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
		SegMenu fil=(SegMenu) filtro;
		System.out.println("CveRol "+fil.getCveRol());
//		querySB.append("select m from ");
//		querySB.append(filtro.getClass().getSimpleName()).append(" as m ");
//
//		querySB.append(" left outer join m.segMenusistemas as ms");
//		querySB.append(" inner join ms.segRol as r");
//		querySB.append(" left outer join r.segPerfilUsuarios as p");
//		querySB.append(" inner join p.segUsuario as u");
//		querySB.append(" where u.cveIdUsuario = ");
//		querySB.append(((SegMenu) filtro).getIdUsuario());
//		querySB.append(" and p.cveIdPerfilUsuario = ");
//		querySB.append(((SegMenu) filtro).getIdPerfil());
//		querySB.append(" order by m.cveIdMenu asc, m.segMenu desc, m.numOrden asc");
		querySB.append("Select menu from SegMenu menu,SegMenusistema menSis where menu.cveIdMenu=menSis.segMenu.cveIdMenu and menSis.segRol.cveRol="+fil.getCveRol()+" order by menSis.segMenu.cveIdMenu");
		System.out.println("querySB :: " + querySB);
		List<T> resultados = getSession().createQuery(querySB.toString())
				.list();

		return resultados;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> obtenerMenuPatron() {
		final StringBuffer querySB = new StringBuffer();
		querySB.append("select m from SegMenu as m");
		querySB.append(" left outer join m.segMenusistemas as ms");
		querySB.append(" inner join ms.segRol as r");
		querySB.append(" where r = 5");
		querySB.append(" order by m.cveIdMenu asc, m.segMenu desc, m.numOrden asc");
		logger.debug("querySB :: " + querySB);
		List<T> resultados = getSession().createQuery(querySB.toString()).list();

		return resultados;
	}

}
