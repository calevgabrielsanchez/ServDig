/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: Articulo155ServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo155;

@Stateless
public class Articulo155ServiceUtility extends AbstractServiceUtility implements Articulo155ServiceUtilityLocal{
	@Override
	public Articulo155 convertirEntityToModel(DicArticulo155 entity){
		Articulo155 articulo155=new Articulo155();
		articulo155.setCveIdArticulo155(entity.getCveIdArticulo155());
		articulo155.setDesFraccion(entity.getDesFraccion());
		articulo155.setDesInciso(entity.getDesInciso());
		articulo155.setCveIdSubdelegacion(entity.getDicSubdelegacion().getCveIdSubdelegacion());
		return articulo155;
	}
}
