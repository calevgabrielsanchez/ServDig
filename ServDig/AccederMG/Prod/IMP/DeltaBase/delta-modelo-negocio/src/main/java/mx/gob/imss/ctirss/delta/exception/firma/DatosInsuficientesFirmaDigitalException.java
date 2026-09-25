package mx.gob.imss.ctirss.delta.exception.firma;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DatosInsuficientesFirmaDigitalException extends AbstractException {

	private static final long serialVersionUID = 2847002194272662519L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"No se cuenta con datos necesarios para realizar la firma digital");

	/**
	 * Constructor por omision
	 */
	public DatosInsuficientesFirmaDigitalException() {
		super(situacion, codigo);
	}

	/**
	 * Constructor que recibe un mensaje de error
	 * 
	 * @param situacion
	 */
	public DatosInsuficientesFirmaDigitalException(String situacion) {
		super(situacion, codigo);
	}
}
