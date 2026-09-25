package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AnalistaDictamenDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioAnalistaDictamenServiceRemote;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioServiceRemote;

@WebService
@Stateless(name = "WSAnalistaDictamen", mappedName = "WSAnalistaDictamen")
public class WSAnalistaDictamen implements UsuarioAnalistaDictamenServiceRemote {
	
	@EJB
	private UsuarioServiceRemote usuarioServiceRemote;

	@Override
	@WebMethod
	public AnalistaDictamenDTO obtenerAnalistaNivelCentralPorCurp(@WebParam(name="curp")String curp) throws AdmonUsuariosException {
		
		AnalistaDictamenDTO analistaDictamenDTO = null;
		
		if (curp != null && !curp.isEmpty()) {
			analistaDictamenDTO = usuarioServiceRemote.obtenerAnalistaNivelCentralPorCurp(curp);
		} else {
			throw new AdmonUsuariosException("La curp no puede ser nula o vacía.");
		}
		
		return analistaDictamenDTO;
	}

	@Override
	@WebMethod
	public List<AnalistaDictamenDTO> obtenerAnalistasNivelCentral() throws AdmonUsuariosException {
		
		List<AnalistaDictamenDTO> listAnalistasDictamenDTOs = null;
		
		listAnalistasDictamenDTOs = usuarioServiceRemote.obtenerAnalistasNivelCentral();
		
		return listAnalistasDictamenDTOs;
	}

}
