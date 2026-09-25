package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdminUsuarioWebServiceRemote;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioServiceRemote;

/**
 * Session Bean implementation class AdminUsuarioWebService
 */
@WebService
@Stateless(name = "webServiceAdminUsuario", mappedName = "webServiceAdminUsuario")
public class WebServiceAdminUsuario implements AdminUsuarioWebServiceRemote {

    /**
     * Default constructor. 
     */
	
	@EJB
	private UsuarioServiceRemote usuarioServiceRemote;
	
    public WebServiceAdminUsuario() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public AdminUserResponseDTO consultaUsuario(String curp){
    	try {
    		return usuarioServiceRemote.consultaUsuarioPorCURP(curp);
		} catch (Exception e) {
			AdminUserResponseDTO adminUserResponseDTO = new AdminUserResponseDTO();
			adminUserResponseDTO.setCodigo("0004");
			adminUserResponseDTO
					.setDescripcion("Ocurrio un error al realizar la petición");
			e.printStackTrace();
			return adminUserResponseDTO;
		}
	}

    
}
