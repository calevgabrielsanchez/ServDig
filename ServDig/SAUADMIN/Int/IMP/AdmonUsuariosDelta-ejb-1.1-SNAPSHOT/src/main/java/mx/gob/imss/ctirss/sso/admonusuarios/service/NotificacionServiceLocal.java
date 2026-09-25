package mx.gob.imss.ctirss.sso.admonusuarios.service;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;


@Local
public interface NotificacionServiceLocal{

	public List<ActivaCuentaDTO> searchNotificaciones(SolicitudDTO sol)throws AdmonUsuariosException;

	public boolean actualizaNotificaciones(ActivaCuentaDTO sol)throws AdmonUsuariosException;
	


}
