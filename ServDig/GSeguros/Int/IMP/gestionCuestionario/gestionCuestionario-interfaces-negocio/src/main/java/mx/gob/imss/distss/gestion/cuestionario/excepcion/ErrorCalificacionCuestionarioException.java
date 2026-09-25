/**
 * 
 */
package mx.gob.imss.distss.gestion.cuestionario.excepcion;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class ErrorCalificacionCuestionarioException extends AbstractException {

	/**
	 * 
	 */
	public ErrorCalificacionCuestionarioException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ErrorCalificacionCuestionarioException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param e
	 */
	public ErrorCalificacionCuestionarioException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param situacion
	 * @param codigo
	 */
	public ErrorCalificacionCuestionarioException(String situacion,
			Integer codigo) {
		super(situacion, codigo);
		// TODO Auto-generated constructor stub
	}

}
