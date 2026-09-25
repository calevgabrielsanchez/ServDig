package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AnalistaDictamenDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@Remote
public interface UsuarioAnalistaDictamenServiceRemote {
	
	/**
	 * Metodo para obtener el perfil de un Analista de Dictamen por medio de un curp, sin importar su estado del mismo.
	 * 
	 * @param curp
	 * @return AnalistaDictamenDTO
	 * @throws AdmonUsuariosException
	 */
	AnalistaDictamenDTO obtenerAnalistaNivelCentralPorCurp(String curp) throws AdmonUsuariosException;
	
	/**
	 * Metodo para obtener la lista de todos los Analistas de Dictamen, filtrado por los que estan Autorizados.
	 * 
	 * @return List<AnalistaDictamenDTO>
	 * @throws AdmonUsuariosException
	 */
	List<AnalistaDictamenDTO> obtenerAnalistasNivelCentral() throws AdmonUsuariosException;
	
}