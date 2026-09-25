package mx.gob.imss.ctirss.sso.admonusuarios.services;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;

@Remote
public interface AdminUsuarioWebServiceRemote {

	AdminUserResponseDTO consultaUsuario(String curp);
	
}
