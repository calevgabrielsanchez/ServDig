package mx.gob.imss.ctirss.correccion.login.service.interfaces;


import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface PerfilService<T extends AbstractModel> {
	
	public List<T> recuperarPerfiles(T model);
	
	
}
