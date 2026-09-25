/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Grupo;

/**
 * 
 * @author NOVUTEK - Lucio Duran Silva
 * 2011
 * Clasificador.
 *
 */
public interface GrupoRepository {

	
	/**
	 * Obtiene los grupos asociados a una division
	 * @param cveDivision - Clave de la division
	 * @return Lista de grupos con la clave y su descripcion.
	 */
	public List<Grupo> cargarGruposPorDivision(int cveDivision);
	
	
	
	
}
