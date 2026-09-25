package mx.imss.ctirss.login.service.ejb.dao;

import java.util.List;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public interface PerfilDAO<T extends AbstractModel> {
	
	List<T> recuperarPerfilesDisponibles(T model);
	

}
