package mx.gob.imss.ctirss.delta.exception.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RelacionConRegistroPatronalExisteException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8905422962507594689L;
	private static final String situacion = "La relacion con el registro patronal ya existe";
	private static final Integer codigo = new Integer (310);
	
	public RelacionConRegistroPatronalExisteException() {
		super(situacion,codigo);
	}
	public RelacionConRegistroPatronalExisteException(String message) {
		super(message,codigo);
	}
}
