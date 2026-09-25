/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:Articulo155ServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clem
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clem;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem.Articulo155ServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.Articulo155ServiceBusinessRemote;

@Stateless(name="articulo155ServiceBusiness", mappedName="articulo155ServiceBusiness")
public class Articulo155ServiceBusiness extends AbstractServiceBusiness implements Articulo155ServiceBusinessRemote{
	
	@EJB
	Articulo155ServiceEntityLocal articulo155ServiceEntity;
	
	@Override
	public List<Articulo155> getArticulo155(Long cveIdSubdelegacion){
		return articulo155ServiceEntity.getArticulo155(cveIdSubdelegacion);
	}
}