package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class UmfNoLocalizadaException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078260617647831918L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"La UMF no ha sido localizada");

	private Fisica fisica;

	public UmfNoLocalizadaException() {
		super(situacion, codigo);
	}

	public UmfNoLocalizadaException(Fisica fisica) {
		super(situacion, codigo);
		this.fisica = fisica;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

}