package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class NotificacionNoValidaException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Notificación no válida");

	public NotificacionNoValidaException() {
		super(situacion, codigo);
	}

	/**
	 * 
	 * @param situacion
	 */
	public NotificacionNoValidaException(String situacion) {
		super(situacion, codigo);
	}

}
