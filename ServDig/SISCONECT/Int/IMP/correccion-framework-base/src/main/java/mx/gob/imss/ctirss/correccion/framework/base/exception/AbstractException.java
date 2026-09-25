/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.base.exception;

/**
 * @author vaguirre
 * 
 */
public abstract class AbstractException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4572304480504012184L;

	/**
	 * 
	 */
	public AbstractException() {
		super();
	}

	/**
	 * 
	 * @param mensaje
	 * @param causa
	 */
	public AbstractException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

	/**
	 * 
	 * @param mensaje
	 */
	public AbstractException(String mensaje) {
		super(mensaje);
	}

}
