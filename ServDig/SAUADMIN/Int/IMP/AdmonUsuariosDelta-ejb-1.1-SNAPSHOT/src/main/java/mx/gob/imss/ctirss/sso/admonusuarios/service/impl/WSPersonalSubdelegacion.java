package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsableDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsablesDelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioIdentidadDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioInfoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.services.ResponsablesDelegacionWebServiceRemote;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioServiceRemote;

/**
 * Session Bean implementation class UsuarioServiceImpl
 */
@WebService
@Stateless(name = "WSPersonalSubdelegacion", mappedName = "WSPersonalSubdelegacion")
public class WSPersonalSubdelegacion extends GenericService implements ResponsablesDelegacionWebServiceRemote {

	@EJB
	private UsuarioServiceRemote usuarioServiceRemote;

	@Override
	@WebMethod
	public ResponsablesDelegacionDTO recuperaResponsablesDelegacion(@WebParam(name = "cveDelegacion") int cveDelegacion,
			@WebParam(name = "cveSubdelegacion") int cveSubdelegacion, @WebParam(name = "roles") String roles,
			@WebParam(name = "modulo") int modulo) {
		// TODO Auto-generated method stub

		ResponsablesDelegacionDTO respuesta = usuarioServiceRemote.recuperaResponsables(cveDelegacion, cveSubdelegacion,
				roles, modulo);

		return respuesta;
	}
	
	@Override
	public UsuarioInfoDTO consultaDatosUsuario(@WebParam(name = "curp") String curp) {
		UsuarioInfoDTO identidad = null;
		identidad = usuarioServiceRemote.consultaInfoUsuarioPorCURP(curp);
		return identidad;
	}

}
