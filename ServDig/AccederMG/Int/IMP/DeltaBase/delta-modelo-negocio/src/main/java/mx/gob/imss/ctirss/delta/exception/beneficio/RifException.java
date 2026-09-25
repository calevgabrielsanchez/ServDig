package mx.gob.imss.ctirss.delta.exception.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RifException extends AbstractException {

	private static final long serialVersionUID = -1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No es RIF.");

	private int code;

	public RifException() {
		super(situacion, codigo);
	}

	public RifException(String message) {
		super(message);
	}

	public RifException(String message, int code) {
		super(message, code);
	}

	public Integer getCode() {
		return this.code;
	}

	
}
