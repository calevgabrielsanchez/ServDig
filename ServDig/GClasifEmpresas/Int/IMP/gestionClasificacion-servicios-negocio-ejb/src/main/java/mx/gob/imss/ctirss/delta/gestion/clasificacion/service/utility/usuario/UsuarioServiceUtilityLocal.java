/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: UsuarioServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.usuario
 *  @Fecha: 17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.usuario;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

@Local
public interface UsuarioServiceUtilityLocal {
	/**
	 * 
	 * @param model
	 * @return
	 */
	Usuario convertirEntityToModel(DitUsuario model) throws Exception;
	
}
