/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: FraccionEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

@Local
public interface FraccionEntityLocal{
	Fraccion consultaPorId(Long cveIdFraccion) throws PersistenceException;
	
	Fraccion consultarFraccionEquivalente(Fraccion fraccion) throws PersistenceException, Exception;
	
	Fraccion consultarFracEqPorNumero(Fraccion fraccion) throws PersistenceException, Exception;
	

	/**
	 * Obtiene la foto que se persistió de la clasificación actual del patrón
	 * 
	 * @param cveIdAnalisis
	 * @return fraccion
	 */
	Fraccion obtenerFotoClasificacionActual(final long cveIdAnalisis);
	
	/**
	 * Obtiene la clasificación actual del sujeto obligado
	 * 
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	Clasificacion obtenerClasificacionActual(
			final long cveIdPatronSujetoObligado);
	
	/**
	 * servicio encargado de recuperar el catalogo de clases de clasiificación
	 * @return
	 */
	List <Clase> consultaCatalogoClase() throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de clases de clasiificación
	 * @return
	 */
	Clase consultaCatalogoClaseById(Long idClase) throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de fracciones de clasiificación por clase
	 * @return
	 */
	List <Fraccion> consultaCatalogoFraccionByIdClase(Long idClase) throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Division> consultaCatalogoDivision() throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Grupo> consultaCatalogoGrupoByIdDivision(Long idDivision) throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Fraccion> consultaCatalogoFraccionByIdGrupo(Long idGrupo) throws PersistenceException;
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	List <Fraccion> consultaCatalogoFraccionConClaseActivasByIdGrupo(Long idGrupo) throws PersistenceException;
	

}
 
