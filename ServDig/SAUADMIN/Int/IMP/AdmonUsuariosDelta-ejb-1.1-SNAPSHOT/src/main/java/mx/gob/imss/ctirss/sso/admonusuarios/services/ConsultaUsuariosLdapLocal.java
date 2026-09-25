package mx.gob.imss.ctirss.sso.admonusuarios.services;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@Local
public interface ConsultaUsuariosLdapLocal {
	
	UsuarioDTO consultaUsuariosLdap(String uid) throws AdmonUsuariosException;

}
