package mx.gob.imss.ctirss.login.service.ejb.dao.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.login.service.ejb.LoginServiceRemote;
import mx.imss.ctirss.login.service.ejb.dao.LoginDAOLocal;

@Stateless(name="loginServiceDL", mappedName = "loginServiceDL")
public class LoginServiceBean<T extends AbstractModel> extends AbstractService implements LoginServiceRemote<T>{
	
	@EJB LoginDAOLocal<T> dao;
	
	
	@Override
	public DlcUsuario validarCredenciales(T filtro) {
		return dao.validarCredenciales(filtro);
	}
	
	@Override
	public DlcUsuario validarCredencialesFuncionario(T filtro) {
		return dao.validarCredencialesFuncionario(filtro);
	}


	@Override
	public DlcRol consultaRolUsuario(Long idUsuario) {
		return dao.consultaRolUsuario(idUsuario);
	}
	
}
