package mx.gob.imss.cit.dacvass.servicios.externos.service.activeDirectory;

import javax.ejb.Local;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.ConsultaUsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;

@Local
public interface IActiveDirectoryAuthenticationServiceLocal {
	
	/**
	 * Servicio que consulta un usuario en el directorio activo basado a su dominio
	 * @param consulta
	 * @return
	 * @throws AuthenticationException
	 * @throws NamingException
	 * @throws Exception
	 */
	UsuarioDirectorioActivo geDatostUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) throws ServiciosRestException;
	
	/**
	 * Servicio que autentica al usuario basado en su dominio
	 * @param consulta
	 * @return
	 * @throws AuthenticationException
	 * @throws NamingException
	 * @throws Exception
	 */
	boolean autenticaUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) throws ServiciosRestException;

}
