package mx.imss.ctirss.login.service.interfaces;

import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;




public interface LoginService<T extends AbstractModel> {
	
	public DlcUsuario validarCredenciales(T model);
	
	public DlcUsuario validarCredencialesFuncionario(T model);
	
	public DlcRol consultaRolUsuario(Long idUsuario);
	
	
}
