package mx.gob.imss.ctirss.delta.exception.firma;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class FirmaDigitalException extends AbstractException {

	private static final long serialVersionUID = -398178170071904041L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Error en firma digital");

	/**
	 * Constructor por omision
	 */
	public FirmaDigitalException() {
		super(situacion, codigo);
	}

	/**
	 * Constructor que recibe un mensaje de error
	 * 
	 * @param situacion
	 */
	public FirmaDigitalException(String situacion) {
		super(situacion, codigo);
	}

}
