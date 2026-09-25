/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:Articulo155ServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;

@Remote
public interface Articulo155ServiceBusinessRemote{
	/**
	 * Obtiene Artículo(s) a partir de cveIdSubdelegacion
	 * @param cveIdSubdelegacion
	 * @return 
	 */
	List<Articulo155> getArticulo155(Long cveIdSubdelegacion);
}
