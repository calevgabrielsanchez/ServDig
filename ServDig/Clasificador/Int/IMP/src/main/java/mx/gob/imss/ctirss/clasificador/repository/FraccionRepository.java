/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;

/**
 * 
 * @author NOVUTEK - Lucio Duran Silva
 * 2011
 * Clasificador.
 *
 */
public interface FraccionRepository {
	
	/**
	 * Obtiene la lista de las descripciones y claves de las fracciones asociadas a un grupo.
	 * @param cveGrupo - Clave del Grupo
	 * @param cveDivision - Clave de la Division del grupo.
	 * @return - Lista de las Fracciones
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorGrupo( int cveGrupo, int cveDivision,  String sSearch  , int iDisplayLength , int iDisplayStart);

	/**
	 * Consulta las fracciones nuevas donde su descripcion de la fraccion anterior (equivalencia) 
	 * coincida con la palabra clave.
	 * @param palabraClave - Palabra a buscar
	 * @return - Lista de las fracciones.
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorPalabraClaveAnterior(
		 int cveGrupo, String sSearch,
			int iDisplayLength, int iDisplayStart);
	
	
	
	
	/**
	 * Consulta las fracciones nuevas donde su numero de la fraccion anterior (equivalencia)
	 * coincida con el numero.
	 * @param cveGrupo
	 * @param sSearch
	 * @param iDisplayLength
	 * @param iDisplayStart
	 * @return
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorNumeroAnterior(
			 int cveGrupo, String sSearch,
				int iDisplayLength, int iDisplayStart);
	
	
	/**
	 * Obtiele la lista de las descripciones y claves de las fracciones inactivas que 
	 * su descripcion coincidan con una palabra clave dada.
	 * @param palabraClave - Palabra a buscar
	 * @return - Lista de las fracciones.
	 */
	public AbstractDataTableReply obtenerFraccionesInactivasPorPalabraClave(String palabraClave, int iDisplayLength, int iDisplayStart);
	
	/**
	 * Obtiene el detalle de una fraccion.
	 * @param cveFraccion
	 * @return
	 */
	public Fraccion obtenerFraccionPorClave(String desFraccion);
	

	/**
	 * 
	 * @param cveGrupo
	 * @param sSearch
	 * @param iDisplayLength
	 * @param iDisplayStart
	 * @return
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorPalabra(
			int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart); 
	
	/**
	 * 
	 * @param cveGrupo
	 * @param sSearch
	 * @param iDisplayLength
	 * @param iDisplayStart
	 * @return
	 */
	public AbstractDataTableReply obtenerFraccionesActivasPorNumero(
			int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart);
	
	
	
	/**
	 * Consulta las fracciones anteriores con el numero de la fraccion anterior
	 * @param sSearch
	 * @param iDisplayLength
	 * @param iDisplayStart
	 * @return
	 */
	public AbstractDataTableReply obtenerFraccionesInactivasPorNumeroAnterior(
			String sSearch,	int iDisplayLength, int iDisplayStart);
	
}



