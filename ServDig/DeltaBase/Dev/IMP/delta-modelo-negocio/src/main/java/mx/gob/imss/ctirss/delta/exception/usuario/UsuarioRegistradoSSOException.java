package mx.gob.imss.ctirss.delta.exception.usuario;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class UsuarioRegistradoSSOException extends AbstractException implements Serializable {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7975362318932315150L;
	private static final String situacion = "Error de validaci\u00f3n ya existe el usuario en SSO";
	private static final String error = "Error de validaci\u00f3n: ";
	private static final Integer codigo = new Integer(10008000);

    public UsuarioRegistradoSSOException() {
    	super(situacion, codigo);
    }

    public UsuarioRegistradoSSOException(String causa) {
    	super(error + causa, codigo);
    }
    
}
