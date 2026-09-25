package mx.gob.imss.cit.clienteswebservices.boveda.rest.bean;

public class BovedaResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8566430941102990130L;

	public BovedaResponseException() {
		super();
	}

	public BovedaResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BovedaResponseException(String message) {
		super(message);
	}

	public BovedaResponseException(Throwable cause) {
		super(cause);
	}
}
