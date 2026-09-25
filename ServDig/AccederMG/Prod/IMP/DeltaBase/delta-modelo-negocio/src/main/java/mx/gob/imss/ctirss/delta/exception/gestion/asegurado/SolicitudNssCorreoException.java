package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class SolicitudNssCorreoException extends AbstractException {

	private static final long serialVersionUID = 6835283852664902041L;
	private static final Integer codigo = new Integer(0);

	public SolicitudNssCorreoException(String situacion) {
		super(situacion, codigo);
	}
}
