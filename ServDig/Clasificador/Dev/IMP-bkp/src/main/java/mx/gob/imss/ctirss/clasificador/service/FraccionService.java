/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;


/**
 * @author lucio
 *
 */
public interface FraccionService {
	
	/**
	 * 
	 * @param cveGrupo
	 * @return
	 */
	public List<Fraccion> obtenerFraccionesActivasPorGrupo(int cveGrupo , String sSearch  , int iDisplayLength , int iDisplayStart );
	
	
	
	/**
	 * Obtiene el detalle de una fraccion.
	 * @param cveFraccion
	 * @return
	 */
	public Fraccion obtenerFraccionPorClave(String desFraccion);

}
