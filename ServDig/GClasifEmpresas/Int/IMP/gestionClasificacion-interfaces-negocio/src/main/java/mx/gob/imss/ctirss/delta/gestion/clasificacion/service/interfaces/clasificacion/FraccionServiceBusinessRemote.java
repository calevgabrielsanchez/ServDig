/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:FraccionServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion
 *  @Fecha:20/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion;

import java.util.List;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

@Remote
public interface FraccionServiceBusinessRemote{
	/**
	 * Consulta Fraccion completa para ser asignada a una clasificacion
	 * @param cveIdFraccion
	 * @return
	 * @throws Exception
	 */
	Fraccion consultaPorId(Long cveIdFraccion) throws Exception;
	
	/**
	 * Obtiene la foto que se persistió de la clasificación actual del patrón
	 * 
	 * @param cveIdAnalisis
	 * @return fraccion
	 */
	Fraccion obtenerFotoClasificacionActual(final long cveIdAnalisis);
	
	List<Clase> consultaCatalogoClase() throws Exception;
	List <Fraccion> consultaCatalogoFraccionByIdClase(Long idClase) throws Exception;
	Clase consultaCatalogoClaseById(Long idClase) throws Exception;
	
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Division> consultaCatalogoDivision() throws Exception;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Grupo> consultaCatalogoGrupoByIdDivision(Long idDivision) throws Exception;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Fraccion> consultaCatalogoFraccionByIdGrupo(Long idGrupo) throws Exception;
	
	
	/**
	 * servicio encargado de recuperar el catalogo de grupo
	 * @return
	 */
	List<Fraccion> consultaCatalogoFraccionConClaseActivasByIdGrupo(Long idGrupo) throws Exception;

}
 
