package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

/**
 * Interfaz local para el servicio de Administración de Usuarios. Esta interfaz expondrá servicios que puedan ser
 * reutilizados entre EJBs.
 * @author Horacio Oswaldo Ferro Díaz
 * @version 1.0
 */
@Local
public interface AdmonUsuariosSessionLocal {

	/**
	 * Método que realiza una búsqueda en el directorio de usuarios para validar si el identificador del usuario
	 * se encuentra en uso
	 * @param uid String con el identificador del usuario
	 * @return si el usuario existe
	 * @throws AdmonUsuariosException en caso de errores al realizar la consulta
	 */
	boolean existeUsuario(  String uid) throws AdmonUsuariosException;



	
	/**
	 * Método para agregar a un usuario externo al LDAP
	 * @param usuario de tipo UsuarioDTO 
	 * @return la respuesta de si fue agregado o no por medio de la curp
	 * @throws AdmonUsuariosException en caso de errores al agregar el usuario
	 */ 
	public String agregarUsuarioExterno(UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * Método para validar que un usuario exista en LDAP
	 * @param uid
	 * @param password
	 * @param curp 
	 * @return true si existe el usuario, false en caso contrario
	 * @throws AdmonUsuariosException en caso de algun error al buscar al usuario
	 */
	public boolean validaUsuario(String uid, String password, String curp) throws AdmonUsuariosException;
	
	/**
	 * Método para validar que un usuario exista en LDAP y valida que el usuario esté activo
	 * @param uid
	 * @param password
	 * @param curp 
	 * @return true si existe el usuario, false en caso contrario
	 * @throws AdmonUsuariosException en caso de algun error al buscar al usuario
	 */
	public boolean validaUsuarioEstatus(String uid, String password, String curp) throws AdmonUsuariosException;
	
	public boolean eliminarUsuario(String uid) throws AdmonUsuariosException;
	

	
	/**
	 * Método remoto para crear usuarios en el registro centralizado
	 * @param usuario con la información para ser agregada al repositorio
	 * @return el identificador del nuevo usuario
	 * @throws AdmonUsuariosException en caso de errores
	 */
	String agregarUsuario(UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * Método remoto para crear usuarios en el registro centralizado
	 * @param usuario con la información para ser agregada al repositorio
	 * @return el identificador del nuevo usuario
	 * @throws AdmonUsuariosException en caso de errores
	 */
	String agregarUsuarioConPerfiles(UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * Método remoto para modificar usuarios en el registro centralizado
	 * @param usuario con la información actualizada del usuario a modificar
	 * @return si el usuario fue modificado o no hubo modificaciones por aplicar
	 * @throws AdmonUsuariosException en caso de errores
	 */
	boolean modificarMatriculaUsuario(String curp, String cveMatricula) throws AdmonUsuariosException;

	/**
	 * Método remoto para modificar usuarios en el registro centralizado
	 * @param usuario con la información actualizada del usuario a modificar
	 * @return si el usuario fue modificado o no hubo modificaciones por aplicar
	 * @throws AdmonUsuariosException en caso de errores
	 */
	boolean modificarUsuario(UsuarioDTO usuario) throws AdmonUsuariosException;

	/**
	 * Método remoto para desactivar a un usuario y deshabilitar su acceso a todas las aplicaciones Delta
	 * @param uid identificador del usuario a desactivar
	 * @return si el usuario se encuentra desactivado
	 * @throws AdmonUsuariosException en caso de errores
	 */
	boolean desactivarUsuario(String uid) throws AdmonUsuariosException;

	/**
	 * Método para reactivar usuarios
	 * @param uid identificador del usuario a desactivar
	 * @return si el usuario pudo ser reactivado
	 * @throws AdmonUsuariosException en caso de errores
	 */
	boolean activarUsuario(String uid) throws AdmonUsuariosException;

	/**
	 * Muesta los usuarios disponibles en LDAP
	 * @return List
	 * @throws AdmonUsuariosException
	 */
	public List<UsuarioDTO> listaUsuarios(int delegacion, int subdelegacion, int umf) throws AdmonUsuariosException;
	
	/**
	 * Método para buscar un usuario con todas las propiedades 
	 * @param cn propiedad nombre del usuario
	 * @param sn propiedad apellido del usuario
	 * @param employeeNumber propiedad curp del usuario 
	 * @return lista de usuarios con el perfil asignado
	 * @throws AdmonUsuariosException en caso de errores al obtener al usuarios
	 */
	 UsuarioDTO buscaUsuario(String cn, String sn, 
			                            String employeeNumber, String mail, 
			                            String employeeType) throws AdmonUsuariosException;
	 

	/**
	 * Método para obtener el objeto de transferencia de un usuario
	 * @param uid identificador del usuario a consultar
	 * @return objeto con la información del usuario
	 * @throws AdmonUsuariosException en caso de errores al consultar el usuario
	*/
	UsuarioDTO obtenUsuario(String uid) throws AdmonUsuariosException;
	
	/**
	 * Método para eliminar los registros actuales en LDAP
	 * @param curp identificador del usuario a consultar
	 * @return null
	 * @throws AdmonUsuariosException en caso de errores al consultar el usuario
	*/
	public String eliminaUsuario(String uid) throws AdmonUsuariosException;
	
	public boolean actualizaPerfilUsuario(final String uid, final String nombre) throws AdmonUsuariosException;
	
	public List<UsuarioDTO> obtenUsuariosPorPerfil(PerfilDTO perfil) throws AdmonUsuariosException;
	
	public List<UsuarioDTO> listaUsuario( ) throws AdmonUsuariosException;
	
	public boolean agregarModuloAprobador(final String uid,String mod) throws AdmonUsuariosException;
	
	public boolean borrarModuloAprobador(final String uid,String mod) throws AdmonUsuariosException;
	
	public boolean modificaModuloAprobador(final String uid,String mod) throws AdmonUsuariosException;
	
	public boolean modificaPerfilAprobador(final String uid,String per) throws AdmonUsuariosException;

	public boolean agregaPerfilAprobador(String uid, String mod)throws AdmonUsuariosException;

	public boolean borrarPerfilAprobador(String uid, String per)throws AdmonUsuariosException;

	public UsuarioDTO obtenContrasena(String uid, String correoEletronico)throws AdmonUsuariosException;
	
	public 	boolean regeneraPassword(String uid,String newPass) throws AdmonUsuariosException;

	
}
