/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionPropuestaServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

@Local
public interface ClasificacionPropuestaServiceEntityLocal{
	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	Boolean agrega(AnalisisClasificacionEmpresas model) throws PersistenceException;
	
	/** 
	 * Metodo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	Boolean actualiza(AnalisisClasificacionEmpresas model) throws PersistenceException;
	
	
	/**
	 * Metodo para eliminar un elemento del catalogo
	 * @param model
	 */
	void elimina(long cveIdAnalisis) throws PersistenceException;
	
	/**
	 * Metodo para consultar por IDAnálisis un elemento del catalogo
	 * @param model
	 */
	AnalisisClasificacionEmpresas consultaPorIdAnalisis(long cveIdAnalisis) throws PersistenceException;
	
}
 
