/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoServiceBusinessRemote.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha: 09/01/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface GCESujetoObligadoServiceBusinessRemote {

	/**
	 * Valida si el registro patronal asociado a la solicitud está dado de baja;
	 * En caso de que el registro patronal haya sido dado de baja, se cancelan
	 * los análisis asociados a éste.
	 * 
	 * @param solicitud
	 * @throws GCESujetoObligadoException
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 */
	void validarEstadoRegistroPatronal(final Solicitud solicitud)
			throws GCESujetoObligadoException, PersistenceException, ClasificacionException;

}
