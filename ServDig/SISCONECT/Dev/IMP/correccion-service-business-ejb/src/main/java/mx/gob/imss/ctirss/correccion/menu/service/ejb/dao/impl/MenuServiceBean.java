package mx.gob.imss.ctirss.correccion.menu.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.menu.service.ejb.MenuServiceRemote;
import mx.gob.imss.ctirss.correccion.menu.service.ejb.dao.MenuDAOLocal;

@Stateless(name="menuService", mappedName = "menuService")
public class MenuServiceBean<T extends AbstractModel> extends AbstractService implements MenuServiceRemote<T>{
	
	@EJB MenuDAOLocal<T> daoMenu;
	
	
	public List<T> consultar(T filtro) {
		return daoMenu.consulta(filtro);
	}


	@Override
	public List<T> obtenerMenuPatron() {
		return daoMenu.obtenerMenuPatron();
	}
	
}
