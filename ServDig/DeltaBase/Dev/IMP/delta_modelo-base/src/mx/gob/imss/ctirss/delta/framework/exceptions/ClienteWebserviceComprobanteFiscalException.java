package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class ClienteWebserviceComprobanteFiscalException extends
		AbstractException implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Ha ocurrido un error inesperado al consultar los Comprobantes Fiscales";
	private static final Integer codigo = new Integer(10001);

	public ClienteWebserviceComprobanteFiscalException() {
		super(situacion, codigo);
	}

	public ClienteWebserviceComprobanteFiscalException(String causa) {
		super(situacion + " : " + causa, codigo);
	}

	public ClienteWebserviceComprobanteFiscalException(String causa, Integer cod) {
		super(causa, cod);
	}

	public ClienteWebserviceComprobanteFiscalException(Integer cod) {
		super(situacion + " : ", cod);
	}
}
