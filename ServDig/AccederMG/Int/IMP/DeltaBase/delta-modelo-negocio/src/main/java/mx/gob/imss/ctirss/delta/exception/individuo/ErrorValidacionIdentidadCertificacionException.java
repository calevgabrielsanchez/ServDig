/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class ErrorValidacionIdentidadCertificacionException extends
		AbstractException {

	
	private static final long serialVersionUID = 1L;

	public ErrorValidacionIdentidadCertificacionException(String causa, Integer codigo) {
		super(causa, codigo);
	}

	
}
