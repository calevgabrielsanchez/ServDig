/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: Articulo155ServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo155;

@Local
public interface Articulo155ServiceUtilityLocal{
	Articulo155 convertirEntityToModel(DicArticulo155 entity);
}