package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DatosPersonaSATNoValidosException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los datos SAT de la persona son inv\u00e1lidos");

	public DatosPersonaSATNoValidosException() {
		super(situacion, codigo);
	}

	public DatosPersonaSATNoValidosException(String situacion) {
		super(situacion, codigo);
	}
}
