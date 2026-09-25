/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity
 *  @Fecha: 09/01/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Local
public interface GCESujetoObligadoServiceEntityLocal {

	/**
	 * Obtiene el patrón sujeto obligado asociado al identificador enviado como
	 * parámetro enviado como parámetro
	 * 
	 * @param cveIdPatronSujetoObligado
	 * @return ditPatronSujetoObligado
	 */
	DitPatronSujetoObligado obtenerPatronSujetoObligado(
			final long cveIdPatronSujetoObligado);

}
