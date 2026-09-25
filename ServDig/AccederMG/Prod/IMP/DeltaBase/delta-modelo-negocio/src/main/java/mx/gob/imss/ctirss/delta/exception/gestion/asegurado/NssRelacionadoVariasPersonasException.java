/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 * 
 */
public class NssRelacionadoVariasPersonasException extends AbstractException {

	private static final long serialVersionUID = 1L;
	
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"El NSS está relacionado a más de una persona");

	/**
	 * Constructor por omision
	 */
	public NssRelacionadoVariasPersonasException() {
		super(SITUACION_BASE, CODIGO);
	}

	public NssRelacionadoVariasPersonasException(String nss) {
		super(SITUACION_BASE + " [NSS : " + nss + "]", CODIGO);

	}

}
