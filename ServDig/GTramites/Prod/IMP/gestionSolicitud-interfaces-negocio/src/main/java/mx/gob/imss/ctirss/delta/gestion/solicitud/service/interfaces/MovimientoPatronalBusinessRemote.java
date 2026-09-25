/**
 * Servicio encargado de la integracion del proceso de convivencia
 * con SINDO.
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

/**
 * @author vanderluk
 *
 */
@Remote
public interface MovimientoPatronalBusinessRemote {
	
	
	
	/**
	 * Proceso para el envio del movimiento de la modificacion patronal.
	 * @param movimiento
	 */
	void enviarModificacionPatronal(MovimientoPatronalType movimiento);

}
