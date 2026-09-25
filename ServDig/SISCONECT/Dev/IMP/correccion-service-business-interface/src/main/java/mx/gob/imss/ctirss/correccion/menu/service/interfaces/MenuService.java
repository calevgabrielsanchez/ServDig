package mx.gob.imss.ctirss.correccion.menu.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface MenuService<T extends AbstractModel> {
	
	public List<T> consultar(T model);
	
	public List<T> obtenerMenuPatron();
	
	
}
