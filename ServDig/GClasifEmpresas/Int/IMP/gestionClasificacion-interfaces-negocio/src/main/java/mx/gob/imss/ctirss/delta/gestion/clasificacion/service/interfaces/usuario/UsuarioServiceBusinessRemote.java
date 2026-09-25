/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:UsuarioServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.usuario
 *  @Fecha:28/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.usuario;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;

/**
 * @author Eduardo Gonzalez
 *
 */
@Remote
public interface UsuarioServiceBusinessRemote {
	
	/**
	 * Localiza el usuario a partir de un cveIdUsuario.
	 * @param Usuario
	 * @return
	 * @throws UsuarioNoEncontradoException: En caso de que no se pueda encontrar el usuario.
	 */
	Usuario consultaUsuarioPorId(Usuario usuario ) throws UsuarioNoEncontradoException;
	
	
}
