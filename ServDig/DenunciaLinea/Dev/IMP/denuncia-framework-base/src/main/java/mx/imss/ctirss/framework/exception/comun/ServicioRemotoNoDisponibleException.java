/**
 * 
 */
package mx.imss.ctirss.framework.exception.comun;

import mx.imss.ctirss.framework.base.exception.AbstractException;

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
