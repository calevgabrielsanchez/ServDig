/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception;


/**
 * The Class IVROServiceException.
 *
 * @author Dj Leo  14/11/2014
 */
public class IVROServiceException extends Exception {
	
	/** The error msg. */
	private String errorMsg;
	

	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new IVRO service exception.
	 */
	public IVROServiceException() {
		
	}

	/**
	 * Instantiates a new IVRO service exception.
	 *
	 * @param message the message
	 */
	public IVROServiceException(String message) {
		super(message);
		this.setErrorMsg(message);
		
	}

	/**
	 * Instantiates a new IVRO service exception.
	 *
	 * @param cause the cause
	 */
	public IVROServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new IVRO service exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public IVROServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
