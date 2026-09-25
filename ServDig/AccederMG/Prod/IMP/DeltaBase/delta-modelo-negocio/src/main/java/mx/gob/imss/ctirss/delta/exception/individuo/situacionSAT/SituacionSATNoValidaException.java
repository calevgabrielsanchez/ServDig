package mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class SituacionSATNoValidaException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Situaci\u00f3n SAT invalido");

	public SituacionSATNoValidaException() {
		super(situacion, codigo);
	}

	public SituacionSATNoValidaException(String situacion) {
		super(situacion, codigo);
	}
}
