/**
 * 
 */
package mx.imss.ctirss.framework.exception.firma;

import mx.imss.ctirss.framework.base.exception.AbstractException;

/**
 * @author vaguirre
 * 
 */
public class CertificadoInvalidoException extends AbstractException {
	private final static String MENSAJE = "Certificado no valido.";

	/**
	 * 
	 */
	public CertificadoInvalidoException() {
		super(MENSAJE);
	}

	/**
	 * 
	 * @param mensaje
	 */
	public CertificadoInvalidoException(String mensaje) {
		super(mensaje);
	}

}
