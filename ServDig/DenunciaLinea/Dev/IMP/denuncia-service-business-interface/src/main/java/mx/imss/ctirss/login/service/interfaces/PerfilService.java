package mx.imss.ctirss.login.service.interfaces;


import java.util.List;

import mx.imss.ctirss.framework.base.model.AbstractModel;



public interface PerfilService<T extends AbstractModel> {
	
	public List<T> recuperarPerfiles(T model);
	
	
}
