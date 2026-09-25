package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception;

/**
 * La Class ExceptionGenerico.
 */
public class IVROExceptionGenerico extends Exception {

	/** La constante serialVersionUID. @author DjLeo Exception General */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia un nuevo exception generico.
	 */
	public IVROExceptionGenerico() {
	}

	/**
	 * Instancia un nuevo exception generico.
	 *
	 * @param message el valor de: message
	 */
	public IVROExceptionGenerico(String message) {
		super(message);
	}

	/**
	 * Instancia un nuevo exception generico.
	 *
	 * @param cause el valor de: cause
	 */
	public IVROExceptionGenerico(Throwable cause) {
		super(cause);
	}

	/**
	 * Instancia un nuevo exception generico.
	 *
	 * @param message el valor de: message
	 * @param cause el valor de: cause
	 */
	public IVROExceptionGenerico(String message, Throwable cause) {
		super(message, cause);
	}

}
