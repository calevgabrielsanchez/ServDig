package mx.gob.imss.ctirss.sso.admonusuarios.service;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@Local
public interface ActivaCuentaServiceLocal {
	
	public void registraActivaCuenta(SolicitudDTO sol)throws AdmonUsuariosException;

	public ActivaCuentaDTO obtenActivaCuentaBySolicitud(SolicitudDTO sol)throws AdmonUsuariosException;

	public ActivaCuentaDTO obtenActivaCuentaByClaveMD5(String md5)throws AdmonUsuariosException;
	
	public boolean actualizaEstatusActivaCuenta(ActivaCuentaDTO aC)throws AdmonUsuariosException;

}
