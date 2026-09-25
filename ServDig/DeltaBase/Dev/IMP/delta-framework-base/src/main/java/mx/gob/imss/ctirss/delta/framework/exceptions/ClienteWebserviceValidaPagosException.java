package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import java.io.Serializable;

public class ClienteWebserviceValidaPagosException extends AbstractException
		implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "Ha ocurrido un error inesperado al consultar el &uacute;ltimo periodo realizado en ventanilla";
	private static final Integer codigo = new Integer(10001);

	public ClienteWebserviceValidaPagosException() {
		super(situacion, codigo);
	}

	public ClienteWebserviceValidaPagosException(String causa) {
		super(situacion + " : " + causa, codigo);
	}

	/**
	 * Se creo nuevo constructor para poder tipificar y especificar los codigos
	 * de errores, 10001 error default 10002 error de timeout
	 *
	 * @param causa
	 * @param codigo
	 */
	public ClienteWebserviceValidaPagosException(String causa, Integer cod) {
		super(causa, cod);
	}

	/**
	 * Se creo nuevo constructor para poder tipificar y especificar los codigos
	 * de errores, 10001 error default 10002 error de timeout 10003 error en
	 * servicio en RENAPO 10006 persona no localizada
	 *
	 * @param codigo
	 */
	public ClienteWebserviceValidaPagosException(Integer cod) {
		super(situacion + " : ", cod);
	}

}
