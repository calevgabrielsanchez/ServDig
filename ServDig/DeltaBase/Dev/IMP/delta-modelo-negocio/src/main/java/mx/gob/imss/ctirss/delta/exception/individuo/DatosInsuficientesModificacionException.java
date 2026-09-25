/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Marco Sanchez
 * 
 */
public class DatosInsuficientesModificacionException extends AbstractException {

	private static final long serialVersionUID = 7648019989452315242L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se cuenta con datos necesarios para realizar la modificaci\u00f3n.");

	/**
	 * Constructor por omision
	 */
	public DatosInsuficientesModificacionException() {
		super(situacion, codigo);
	}

	/**
	 * Constructor que recibe un mensaje de error
	 * 
	 * @param situacion
	 */
	public DatosInsuficientesModificacionException(String situacion) {
		super(situacion, codigo);
	}

}
