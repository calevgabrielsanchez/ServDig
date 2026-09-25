/**
 * Servicio encargado de la integracion del proceso de convivencia
 * con SINDO.
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface MovimientoPatronalBusinessLocal {
	
	/**
	 * Proceso para el envio del movimiento de la modificacion patronal.
	 * @param movimiento
	 */
	void enviarModificacionPatronal(MovimientoPatronalType movimiento);

}
