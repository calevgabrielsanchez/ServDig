package mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class GenerarFolioCertificacionException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public GenerarFolioCertificacionException(String causa, Integer codigo) {
		super(causa, codigo);
	}

}
