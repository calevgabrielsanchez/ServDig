package mx.gob.imss.ctirss.correccion.login.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.login.service.ejb.PerfilServiceRemote;
import mx.gob.imss.ctirss.correccion.login.service.ejb.dao.PerfilDAOLocal;

@Stateless(name="perfilService", mappedName = "perfilService")
public class PerfilServiceBean<T extends AbstractModel> extends AbstractService implements PerfilServiceRemote<T>{
	
	@EJB PerfilDAOLocal<T> dao;

	@Override
	public List<T> recuperarPerfiles(T model) {
		 return dao.recuperarPerfilesDisponibles(model);
	}
	
	

	
}
