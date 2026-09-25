package mx.gob.imss.ctirss.login.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.login.service.ejb.PerfilServiceRemote;
import mx.imss.ctirss.login.service.ejb.dao.PerfilDAOLocal;

@Stateless(name="perfilServiceDL", mappedName = "perfilServiceDL")
public class PerfilServiceBean<T extends AbstractModel> extends AbstractService implements PerfilServiceRemote<T>{
	
	@EJB PerfilDAOLocal<T> dao;

	@Override
	public List<T> recuperarPerfiles(T model) {
		 return dao.recuperarPerfilesDisponibles(model);
	}
	
	

	
}
