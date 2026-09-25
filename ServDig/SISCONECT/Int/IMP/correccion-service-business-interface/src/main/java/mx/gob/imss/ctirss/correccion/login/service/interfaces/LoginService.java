package mx.gob.imss.ctirss.correccion.login.service.interfaces;


import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.login.model.SegRol;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;

public interface LoginService<T extends AbstractModel> {
	
	public SegUsuario validarCredenciales(T model);
	
	public SegRol consultaRolUsuario(Long idUsuario);
	
	public SegUsuario consultaVigenciaUsuario(SegUsuario usrFirmado);
}
