package mx.gob.imss.distss.gestion.cuestionario.excepcion;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class CuestionarioNoExisteException extends AbstractException {

	private static final long serialVersionUID = -3131058129873915479L;

	public CuestionarioNoExisteException(String message) {
		super(message);
	}

	

}
