package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

/**
 * Interfaz local del servicio de administración de roles. En esta interfaz se exponen servicios que serán consumidos por otros EJBs.
 * @author Horacio Oswaldo Ferro Díaz
 * @version 1.0
 */
@Local
public interface AdmonRolesSessionLocal {

	/**
	 * Obtiene el rol con el nombre proporcionado
	 * @param nombre del rol
	 * @return rol encontrado, nulo en caso contrario
	 * @throws AdmonUsuariosException en caso de errores al realizar la consulta
	 */
	PuestoDTO obtenRol(String nombre) throws AdmonUsuariosException;

	/**
	 * Asigna un nuevo rol al usuario proporcionado
	 * @param uid identificador del usuario
	 * @param rol nombre del rol
	 * @return si se asigno el rol al usuario
	 * @throws AdmonUsuariosException en caso de errores al asignar rol
	 */
	boolean asignaRolUsuario(String uid, String rol) throws AdmonUsuariosException;

	/**
	 * Revoca un rol al usuario proporcionado
	 * @param uid identificador del usuario
	 * @param rol nombre del rol
	 * @return si se revoco el rol al usuario
	 * @throws AdmonUsuariosException
	 */
	boolean revocaRolUsuario(  String uid,   String rol) throws AdmonUsuariosException;

	/**
	 * Método para obtener una lista de roles por nombre
	 * @param nombres arreglo de nombres de roles a obtener
	 * @return lista de roles
	 * @throws AdmonUsuariosException en caso de errores al obtener los roles
	 */
	List<PuestoDTO> obtenRoles(String[] nombres) throws AdmonUsuariosException;

	/**
	 * Obtiene todos los roles asignados al usuario
	 * @param uid identificador del usuario
	 * @return roles asignados al usuario
	 * @throws AdmonUsuariosException en caso de errores al obtener los roles
	 */
	List<PuestoDTO> obtenRolesUsuario(  String uid) throws AdmonUsuariosException;

	/**
	 * Asigna todos los roles proporcionados al usuario proporcionado
	 * @param uid identificador del usuario
	 * @param roles nombre de los roles
	 * @return si los roles fueron asignados
	 * @throws AdmonUsuariosException en caso de errores al asignar roles
	 */
	boolean[] asignaRolesUsuario(  String uid,   String[] roles) throws AdmonUsuariosException;

	/**
	 * Revoca todos los roles proporcionados al usuario proporcionado
	 * @param uid identificador del usuario
	 * @param roles nombre de los roles a revocar
	 * @return si los roles fueron revocados
	 * @throws AdmonUsuariosException en caso de errores al revocar roles
	 */
	boolean[] revocaRolesUsuario(  String uid,   String[] roles) throws AdmonUsuariosException;
	
	public boolean[] asignaAreayGrupoUsuario(  String uid,   List<String> areaGrupo) throws AdmonUsuariosException;

	List<PuestoDTO> obtenRoles() throws AdmonUsuariosException;

	public  boolean asignaAreayGrupoUsuario(  String uid,   String areaygrupo) throws AdmonUsuariosException;
}
