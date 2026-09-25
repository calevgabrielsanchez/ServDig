/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: UsuarioServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.usuario
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.usuario;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

@Stateless
public class UsuarioServiceUtility extends AbstractServiceUtility implements UsuarioServiceUtilityLocal  {
	
	@Override
	public Usuario convertirEntityToModel(DitUsuario entity) throws Exception {
		Usuario usuario = null;
		
		if(entity != null){
			usuario = new Usuario();
			usuario.setCveIdUsuario(entity.getCveIdUsuario() + "");
			usuario.setUsuario(entity.getNomUsuarioSistema());
		}
		
		return usuario;
	}
					
}
