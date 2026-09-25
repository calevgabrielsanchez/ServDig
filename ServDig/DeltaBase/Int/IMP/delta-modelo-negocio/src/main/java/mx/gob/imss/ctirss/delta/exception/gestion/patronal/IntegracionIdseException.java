package mx.gob.imss.ctirss.delta.exception.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class IntegracionIdseException extends AbstractException {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error en integración con IDSE";
	private static final Integer codigo = new Integer(120);

	public IntegracionIdseException() {
		super(situacion, codigo);
	}

	public IntegracionIdseException(final String error) {
		super(error, codigo);
	}
}
