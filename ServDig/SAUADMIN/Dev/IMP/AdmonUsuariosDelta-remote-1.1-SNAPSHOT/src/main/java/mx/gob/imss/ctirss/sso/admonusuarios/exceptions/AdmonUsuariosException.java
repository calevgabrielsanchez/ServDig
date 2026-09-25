package mx.gob.imss.ctirss.sso.admonusuarios.exceptions;

/**
 * Excepción para manejo de errores del modulo de administración de usuarios
 * @author Horacio Oswaldo Ferro Díaz
 * @version 1.0
 */
public class AdmonUsuariosException extends Exception {
	/** UID Serial de versión */
	private static   long serialVersionUID = -1676350350594927258L;

	/**
	 * Constructor
	 */
	public AdmonUsuariosException() {
	}

	/**
	 * Constructor
	 * @param msg mensaje del error
	 */
	public AdmonUsuariosException(  String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param cause causa del error
	 */
	public AdmonUsuariosException(  Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param msg mensaje del error
	 * @param cause cuasa del error
	 */
	public AdmonUsuariosException(  String msg,   Throwable cause) {
		super(msg, cause);
	}

}
