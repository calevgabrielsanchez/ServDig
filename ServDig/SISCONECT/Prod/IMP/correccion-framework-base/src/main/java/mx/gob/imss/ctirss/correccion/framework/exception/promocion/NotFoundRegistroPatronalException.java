/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.exception.promocion;

import mx.gob.imss.ctirss.correccion.framework.base.exception.AbstractException;

/**
 * @author Oscar Beltran Ortega
 * Excepcion para manejar cuando no se encuentra o no existe un 
 * registro patronal
 *
 */
public class NotFoundRegistroPatronalException extends AbstractException {
	
	private static final long serialVersionUID = 1L;

	public NotFoundRegistroPatronalException() {}

	public NotFoundRegistroPatronalException(String message) {
		super(message);
	}

	public NotFoundRegistroPatronalException(String message, Throwable cause) {
		super(message, cause);
	}



}
