/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ClasificacionPropuestaServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion
 *  @Fecha:11/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClaseNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

@Remote
public interface ClasificacionPropuestaServiceBusinessRemote {
	
	/**
	 * Obtiene la Clase correspondiente al id del analisis.
	 * @param cveIdAnalisis
	 * @return String la clase correspondiente a la clasificaci&oacute;n propuesta
	 * @throws Exception
	 */
	AnalisisClasificacionEmpresas obtenerClaseDeClasificacionPropuesta(String cveIdAnalisis) throws ClaseNoEncontradaException;

	/**
	 * Borra la clasificacion propuesta de solicitudes que deben ser ratificadas.
	 * @param clas
	 * @return void
	 * @throws Exception
	 */
	void borrarClasificacionPropuesta(long cveIdAnalisis) throws ClasificacionException;
	
	AnalisisClasificacionEmpresas consultaPorIdAnalisis(Long cveIdAnalisis) throws ClaseNoEncontradaException;
	
}