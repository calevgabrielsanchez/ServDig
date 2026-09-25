package mx.gob.imss.ctirss.delta.exception.firma;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RegistroPatronalInvalidoEnCertificadoException extends
		AbstractException {

	private static final long serialVersionUID = 1L;

	private static final String situacion = "El registro patronal del certificado no corresponde con el de la solicitud.";

	private static final Integer codigo = new Integer(100);

	/**
	 * Lanza excepcion la cual indicaque el registro patronal del certificado no es el mismo.
	 * 
	 */
	public RegistroPatronalInvalidoEnCertificadoException() {
		super(situacion, codigo);
	}
}
