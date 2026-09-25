package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;


public class InfoComplementariaTramiteException extends AbstractException {

	private static final long serialVersionUID = 3337915361364008873L;

	public InfoComplementariaTramiteException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
