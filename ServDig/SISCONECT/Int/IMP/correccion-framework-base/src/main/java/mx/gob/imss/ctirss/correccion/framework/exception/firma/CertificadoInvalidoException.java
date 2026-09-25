/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.exception.firma;

import mx.gob.imss.ctirss.correccion.framework.base.exception.AbstractException;

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
