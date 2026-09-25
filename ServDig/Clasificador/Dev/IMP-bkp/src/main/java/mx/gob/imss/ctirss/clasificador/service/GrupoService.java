/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Grupo;

/**
 * @author lucio
 *
 */
public interface GrupoService {
	
	
	/**
	 * 
	 * @param cveDivision
	 * @return
	 */
	public List<Grupo> cargarGruposPorDivision(int cveDivision);

}
