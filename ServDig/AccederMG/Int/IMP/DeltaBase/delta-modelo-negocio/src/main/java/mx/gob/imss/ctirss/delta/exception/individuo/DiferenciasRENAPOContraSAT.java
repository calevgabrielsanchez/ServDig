/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Marco Sanchez
 * 
 */
public class DiferenciasRENAPOContraSAT extends AbstractException {

	private static final long serialVersionUID = 7648019989452315242L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Existen diferencias en el nombre de la persona en RENAPO y SAT.");

	/**
	 * Constructor por omision
	 */
	public DiferenciasRENAPOContraSAT() {
		super(situacion, codigo);
	}

	/**
	 * Constructor que recibe un mensaje de error
	 * 
	 * @param situacion
	 */
	public DiferenciasRENAPOContraSAT(String situacion) {
		super(situacion, codigo);
	}

}
