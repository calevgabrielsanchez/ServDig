package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

/**
 * Interfaz local del servicio de administración de perfiles. Aquí se exponen servicios reutilizables por EJBs.
 * @author Alan Rene Garcia Rico
 * @version 1.0
 */
@Local
public interface AdmonPerfilesSessionLocal {

	/**
	 * Asigna un nuevo rol al perfil
	 * @param perfil al que se asignara el nuevo rol
	 * @param rol que será agregado
	 * @return si el rol fue asignado correctamente
	 * @throws AdmonUsuariosException en caso de errores al asignar el rol
	 */
	boolean asignarRolAPerfil(String perfil, String rol) throws AdmonUsuariosException;

	/**
	 * Elimina un rol del perfil proporcionado
	 * @param perfil al que se eliminara el rol
	 * @param rol que será eliminado del perfil
	 * @return si el rol fue eliminado correctamente
	 * @throws AdmonUsuariosException
	 */
	boolean eliminarRolDePerfil(String perfil, String rol) throws AdmonUsuariosException;

	/**
	 * Verifica la existencia de un perfil
	 * @param nombre del perfil a verificar
	 * @return si existe o no el perfil
	 * @throws AdmonUsuariosException en caso de errores al verificar la existencia del perfil
	 */
	boolean existePerfil(String nombre) throws AdmonUsuariosException;

	/**
	 * Cambia el perfil a un usuario
	 * @param uid con el identificador del usuario
	 * @param perfil nombre del perfil a asignar
	 * @return si la asignación se realizo
	 * @throws AdmonUsuariosException en caso de errores al realizar la asignación
	 */
	boolean cambiaPerfilAUsuario(String uid,   String perfil) throws AdmonUsuariosException;

	/**
	 * Revoca el perfil al usuario
	 * @param uid identificador del usuario al que se revocara el perfil
	 * @return si la revocación fue exitosa
	 * @throws AdmonUsuariosException en caso de errores al revocar el perfil
	 */
	boolean revocaPerfilAUsuario(String uid) throws AdmonUsuariosException;

	/**
	 * Asigna un perfil a un usuario
	 * @param uid con el identificador del usuario
	 * @param perfil nombre del perfil a asignar
	 * @return si la asignación se realizo
	 * @throws AdmonUsuariosException en caso de errores al realizar la asignación
	 */
	boolean asignaPerfilAUsuario(String uid, String perfil) throws AdmonUsuariosException;
	
	public boolean asignaPerfilAUsuarioExterno (String uid, String perfil) throws AdmonUsuariosException;
	
	boolean asignaAreayGrupoAUsuario(final String uid, final List<String> areaygrupo) throws AdmonUsuariosException;
}
