/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio
 *  @Fecha:28/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.usuario;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

@Local
public interface UsuarioServiceEntityLocal {
	
	/**
	 * Consulta un Usuario por cveIdUsuario
	 * @param usuario
	 * @return
	 * @throws UsuarioNoEncontradoException
	 */
	DitUsuario consultaUsuarioPorId(Usuario usuario) throws UsuarioNoEncontradoException;
		
}
