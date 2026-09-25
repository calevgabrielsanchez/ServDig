/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 * 
 */
public class DatosInsuficientesParaConsultaException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static Integer codigo = new Integer(1);
	private static String mensaje = new String(
			"Error en la transformaci-n de clases");

	public DatosInsuficientesParaConsultaException() {
		super(mensaje, codigo);
	}

	public DatosInsuficientesParaConsultaException(String mensaje) {
		super(mensaje, codigo);
	}

}
