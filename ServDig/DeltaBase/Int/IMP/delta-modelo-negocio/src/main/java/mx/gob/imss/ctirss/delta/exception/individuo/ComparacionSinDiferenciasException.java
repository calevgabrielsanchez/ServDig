/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Marco Sanchez
 * 
 */
public class ComparacionSinDiferenciasException extends AbstractException {

	private static final long serialVersionUID = 7648019989452315242L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"No existen diferencias con la información registrada en IMSS.");

	/**
	 * Constructor por omision
	 */
	public ComparacionSinDiferenciasException() {
		super(situacion, codigo);
	}

	/**
	 * Constructor que recibe un mensaje de error
	 * 
	 * @param situacion
	 */
	public ComparacionSinDiferenciasException(String situacion) {
		super(situacion, codigo);
	}

}
