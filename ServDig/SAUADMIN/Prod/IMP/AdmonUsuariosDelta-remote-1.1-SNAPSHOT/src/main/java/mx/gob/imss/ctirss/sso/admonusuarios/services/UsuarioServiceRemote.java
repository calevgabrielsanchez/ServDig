package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AnalistaDictamenDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsablesDelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioInfoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@Remote
public interface UsuarioServiceRemote {

	AdminUserResponseDTO consultaUsuarioPorCURP(String curp);
	UsuarioInfoDTO consultaInfoUsuarioPorCURP(String curp);
	
	ResponsablesDelegacionDTO recuperaResponsables(int claveDelegacion,int claveSubdelegacion, String roles, int claveModulo);
	
	/**
	 * 
	 * @param curp
	 * @return AnalistaDictamenDTO
	 * @throws AdmonUsuariosException
	 */
	AnalistaDictamenDTO obtenerAnalistaNivelCentralPorCurp(String curp) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @return List<AnalistaDictamenDTO>
	 * @throws AdmonUsuariosException
	 */
	List<AnalistaDictamenDTO> obtenerAnalistasNivelCentral() throws AdmonUsuariosException;
	
}
