package mx.gob.imss.ctirss.correccion.login.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.login.model.SegRol;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;

public interface LoginDAO<T extends AbstractModel> {
	
	public SegUsuario validarCredenciales(T model);
	
	public SegRol consultaRolUsuario(Long idUsuario);
	
	public SegUsuario validarVigencia(SegUsuario segUsuario);

}
