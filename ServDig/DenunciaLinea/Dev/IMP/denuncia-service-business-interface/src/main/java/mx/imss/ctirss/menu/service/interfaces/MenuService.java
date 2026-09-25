package mx.imss.ctirss.menu.service.interfaces;

import java.util.List;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public interface MenuService<T extends AbstractModel> {
	
	public List<T> consultar(T model);

	public List<T> consultarDictamen(T model);
	
	
	
	
}
