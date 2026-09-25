package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RegistroPersonaException extends AbstractException {

	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"No se realiz\u00f3 el registro de la persona");

	public RegistroPersonaException() {
		super(situacion, codigo);
	}

	public RegistroPersonaException(String situacion) {
		super(situacion, codigo);
	}
}