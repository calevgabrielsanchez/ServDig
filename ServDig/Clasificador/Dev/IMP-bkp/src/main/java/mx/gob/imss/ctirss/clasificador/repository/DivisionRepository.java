/**
 * Respository de la Entidad de Division.
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Division;

/**
 * 
 * @author NOVUTEK - Lucio Duran Silva
 * 2011
 * Clasificador.
 *
 */
public interface DivisionRepository {
	
	
	/**
	 * Obtiene una lista con las descripciones de las divisiones que se encuentran activas.
	 * Este metodo es utilizado para la carga de catalogos.
	 * @return
	 */
	public List<Division> cargarDivisionesActivas();
	
	
	/**
	 * Obtiene una lista con las descripciones de las divisiones que se encuentran inactivas.
	 * Este metodo es utilizado para la carga de catalogos.
	 * @return
	 */
	public List<Division> cargarDivisionesInactivas();
	

}
