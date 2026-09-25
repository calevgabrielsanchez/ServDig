package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Remote
public interface ResponsablesDelegacionRemote {
	List<Fisica> consultarResponsablesDelegacion(int cveDelegacion,int cveSubdelegacion) throws ClienteWebserviceResponsablesSubdelegacionException;
	List<Fisica> consultarAutorizadoresDelegacion(int cveDelegacion,int cveSubdelegacion) throws ClienteWebserviceResponsablesSubdelegacionException;

	/**
	 * Servicio que se encarga de recuperar los datos basicos de un usuario de seguridad 
	 * @param curp del usuario a recuperar
	 * @return Usuario con los datos basicos seteados
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 * @throws ClienteWebserviceResponsablesSubdelegacionException 
	 */
	Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp)	throws ClienteWebserviceResponsablesSubdelegacionException;
}
