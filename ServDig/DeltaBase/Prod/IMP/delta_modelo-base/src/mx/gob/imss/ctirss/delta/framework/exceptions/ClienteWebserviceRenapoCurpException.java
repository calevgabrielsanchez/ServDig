
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Ponte Diaz
 * @since 13/07/2012
 * @version 1.0
 * 
 */
public class ClienteWebserviceRenapoCurpException  extends AbstractException implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error de conexi\u00f3n con el WS de RENAPO: ";
	private static final Integer codigo = new Integer (10001);

    public ClienteWebserviceRenapoCurpException() {
    	super(situacion, codigo);
    }

    public ClienteWebserviceRenapoCurpException(String causa) {
    	super(situacion + causa, codigo);
    }
}
