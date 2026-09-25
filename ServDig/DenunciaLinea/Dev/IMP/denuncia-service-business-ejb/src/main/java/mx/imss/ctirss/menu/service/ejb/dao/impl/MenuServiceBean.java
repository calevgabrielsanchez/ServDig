package mx.imss.ctirss.menu.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.service.AbstractService;
import mx.imss.ctirss.menu.service.ejb.MenuServiceRemote;
import mx.imss.ctirss.menu.service.ejb.dao.MenuDAOLocal;

@Stateless(name="menuServiceDictamenT", mappedName = "menuServiceDictamenT")
public class MenuServiceBean<T extends AbstractModel> extends AbstractService implements MenuServiceRemote<T>{
	
	@EJB MenuDAOLocal<T> daoMenu;
	
	
	public List<T> consultar(T filtro) {
		return daoMenu.consulta(filtro);		
	}

	public List<T> consultarDictamen(T filtro) {
		return daoMenu.consultaDictamen(filtro);
	}

	
}
