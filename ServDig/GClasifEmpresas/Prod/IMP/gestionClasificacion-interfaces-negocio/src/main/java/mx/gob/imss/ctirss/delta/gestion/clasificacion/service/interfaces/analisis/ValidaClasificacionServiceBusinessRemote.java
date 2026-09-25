/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jonathan Sanchez Montiel
 *  @Proyecto: delta
 *  @Archivo: ValidaClasificacionServiceBusinessRemote.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha: 02/10/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Remote
public interface ValidaClasificacionServiceBusinessRemote {

	/**
	 * Utiliza los servicios de gestion patronal para la 
	 * validacion de la clasificacion
	 * 
	 * @param solicitud
	 * @throws ClasificacionException
	 */
	Clasificacion validaClasificacionGP(final Clasificacion clasificacion, String regPat, Long idSolicitud)
			throws GestionPatronalBusinessException;

}
