/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:UsuarioServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.usuario
 *  @Fecha:28/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.usuario;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.usuario.UsuarioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.usuario.UsuarioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.usuario.UsuarioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

@Stateless(name="usuarioServiceBusiness" ,mappedName="usuarioServiceBusiness")
public class UsuarioServiceBusiness extends AbstractServiceBusiness implements
		UsuarioServiceBusinessRemote {

	@EJB
	private UsuarioServiceEntityLocal entity;
	
	@EJB
	UsuarioServiceUtilityLocal clasificacionUtility;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.usuario.UsuarioServiceBusinessRemote#consultaUsuarioPorId(mx.gob.imss.ctirss.delta.model.Usuario)
	 */
	@Override
	public Usuario consultaUsuarioPorId(Usuario pUsuario) throws UsuarioNoEncontradoException {
		Usuario usuario = new Usuario();
		
		DitUsuario ditUsuario = entity.consultaUsuarioPorId(pUsuario);

		try{
			usuario = clasificacionUtility.convertirEntityToModel(ditUsuario);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ERROR - [UsuarioServiceEntity-consultaUsuarioPorId]: " + e.getMessage());
		}
		
		return usuario;
	}

}
