package mx.gob.imss.ctirss.delta.cobranza.exception;

public class EstadoAdeudoException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3412489353699451687L;
	
	private String situacion;
	
	public EstadoAdeudoException() {
		super();
	}

	public EstadoAdeudoException(String situacion) {
		super(situacion);
		this.situacion = situacion;
	}
	
	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
}
