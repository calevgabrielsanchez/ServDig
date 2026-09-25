package mx.gob.imss.ctirss.sso.admonusuarios.services;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsablesDelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioInfoDTO;

public interface ResponsablesDelegacionWebServiceRemote {

	ResponsablesDelegacionDTO recuperaResponsablesDelegacion(int cveDelegacion, int cveSubdelegacion,String roles, int modulo);
	UsuarioInfoDTO consultaDatosUsuario(String curp);
	
}
