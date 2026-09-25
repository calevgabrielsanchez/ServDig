/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: JSM
 *  @Proyecto: delta
 *  @Archivo: ClasifPropuestaDictamenEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Local
public interface ClasifPropDictServiceEntityLocal{
	/**
	 * Metodo para agregar un elemento.
	 * @param model
	 * @return
	 */
	Boolean agrega(Long cveIdSujOb, Clasificacion model) throws PersistenceException;
	
	/**
	 * Metodo para eliminar un elemento
	 * @param model
	 */
	void elimina(long cveId) throws PersistenceException;
	
	/**
	 * Metodo para actualizar un elemento de DIT_CLASIFICACION
	 * @param model
	 */
	void actualizarClasificacion(List<DictamenClasificacion> clasDtm);

	/**
	 * Metodo para buscar la clasificacion
	 * @param cveIdSO
	 * @param model
	 */
	List<DictamenClasificacion> consultaClasifOriginal(Long cveIdSO)
			throws PersistenceException;

}
 
