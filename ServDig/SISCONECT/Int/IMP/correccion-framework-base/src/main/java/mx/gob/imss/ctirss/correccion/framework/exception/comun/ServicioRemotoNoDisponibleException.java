/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.exception.comun;

import mx.gob.imss.ctirss.correccion.framework.base.exception.AbstractException;

/**
 * @author vaguirre
 * 
 */
public class ServicioRemotoNoDisponibleException extends AbstractException {

	private final static String MENSAJE = "Servicio no disponible";

	/**
	 * 
	 */
	public ServicioRemotoNoDisponibleException() {
		super(MENSAJE);
	}

}
