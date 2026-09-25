package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * Copia de la clase que se encuentra en el framework-base, esta clase es
 * utilizada en el OSB y no debe ser utilizada en el backend, para eso está la
 * otra ubicada en el framework-base
 * 
 */
public class ClienteWebserviceImssRissException extends AbstractException
		implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "Ha ocurrido un error inesperado al realizar la validaciones del RISS";
	private static final Integer codigo = new Integer(10001);

	public ClienteWebserviceImssRissException() {
		super(situacion, codigo);
	}

	public ClienteWebserviceImssRissException(String causa) {
		super(situacion + " : " + causa, codigo);
	}

	/**
	 * Se creo nuevo constructor para poder tipificar y especificar los codigos
	 * de errores, 10001 error default 10002 error de timeout 10003 error en
	 * servicio en RENAPO 10006 persona no localizada
	 * 
	 * @param causa
	 * @param codigo
	 */
	public ClienteWebserviceImssRissException(String causa, Integer cod) {
		super(causa, cod);
	}

	/**
	 * Se creo nuevo constructor para poder tipificar y especificar los codigos
	 * de errores, 10001 error default 10002 error de timeout 10003 error en
	 * servicio en RENAPO 10006 persona no localizada
	 * 
	 * @param codigo
	 */
	public ClienteWebserviceImssRissException(Integer cod) {
		super(situacion + " : ", cod);
	}

}
