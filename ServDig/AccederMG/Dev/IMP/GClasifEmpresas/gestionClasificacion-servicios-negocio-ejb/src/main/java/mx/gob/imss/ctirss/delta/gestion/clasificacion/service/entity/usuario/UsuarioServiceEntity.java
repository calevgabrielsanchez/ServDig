/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:UsuarioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.usuario
 *  @Fecha:28/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.usuario;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

import org.hibernate.Query;

@Stateless
public class UsuarioServiceEntity extends AbstractServiceEntity implements
		UsuarioServiceEntityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.usuario.UsuarioServiceEntityLocal#consultaUsuarioPorId(mx.gob.imss.ctirss.delta.model.Usuario)
	 */
	@Override
	public DitUsuario consultaUsuarioPorId(Usuario usuario) throws UsuarioNoEncontradoException {
		this.log.debug("consultaUsuarioPorId [ "+ usuario.getCveIdUsuario() +"]");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" from DitUsuario as u ");
		sql.append(" where u.cveIdUsuario = :cveIdUsuario ");

		Query query = this.getSession().createQuery(sql.toString());
		
		query.setParameter("cveIdUsuario", usuario.getCveIdUsuario());
		
		DitUsuario ditUsuario = (DitUsuario)query.uniqueResult();
		
		if(ditUsuario == null){
			throw new UsuarioNoEncontradoException();
		}
		
		return ditUsuario;
	}

}


