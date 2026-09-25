/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:Articulo155ServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;

@Local
public interface Articulo155ServiceEntityLocal{
	List<Articulo155> getArticulo155(Long cveIdSubdelegacion);
}
