package mx.gob.imss.ctirss.correccion.login.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface PerfilDAO<T extends AbstractModel> {
	
	List<T> recuperarPerfilesDisponibles(T model);
	

}
