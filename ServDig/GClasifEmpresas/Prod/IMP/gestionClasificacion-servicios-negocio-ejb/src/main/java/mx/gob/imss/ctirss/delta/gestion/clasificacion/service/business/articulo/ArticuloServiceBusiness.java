/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ArticuloServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.articulo
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.articulo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ArticuloNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo.ArticuloServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.articulo.ArticuloServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;

@Stateless(name = "articuloServiceBusiness", mappedName = "articuloServiceBusiness")
public class ArticuloServiceBusiness extends AbstractServiceBusiness implements
		ArticuloServiceBusinessRemote {

	@EJB
	ArticuloServiceEntityLocal articuloEntity; 
	
	@Override
	public ArticuloModel buscarPorDelegacionSubdelegacion(ArticuloModel articulo) 
			throws ArticuloNoEncontradoException{
		try{
			articulo = articuloEntity.buscarPorDelegacionSubdelegacion(articulo);
		}catch(Exception exc){
			log.error("Error al buscar el articulo155: "+exc.getMessage());
			throw new ArticuloNoEncontradoException();
		}
		return articulo;
	}

	@Override
	public List<ArticuloModel> buscarPorIdClem(Long idClem)
			throws ArticuloNoEncontradoException {
		return articuloEntity.buscarPorIdClem(idClem);
	}
}
