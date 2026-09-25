/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Division;

/**
 * @author lucio
 *
 */
public interface DivisionService {

	
	
	public List<Division> cargarDivisionesActivas();
	
	
	public List<Division> cargarDivisionesInactivas();
	
}
