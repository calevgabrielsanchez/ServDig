package mx.gob.imss.ctirss.correccion.login.service.ejb.dao.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.login.model.SegRol;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.login.service.ejb.LoginServiceRemote;
import mx.gob.imss.ctirss.correccion.login.service.ejb.dao.LoginDAOLocal;

@Stateless(name="loginService", mappedName = "loginService")
public class LoginServiceBean<T extends AbstractModel> extends AbstractService implements LoginServiceRemote<T>{
	
	@EJB LoginDAOLocal<T> dao;
	
	
	
	@Override
	public SegUsuario validarCredenciales(T filtro) {
		return dao.validarCredenciales(filtro);
	}
	
	public SegRol consultaRolUsuario(Long idUsuario){
		return dao.consultaRolUsuario(idUsuario);
	}

	
	@Override
	public SegUsuario consultaVigenciaUsuario(SegUsuario usrFirmado) {
		// TODO Auto-generated method stub
		return dao.validarVigencia(usrFirmado);
	}
	
}
