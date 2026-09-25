
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import weblogic.wsee.util.StringUtil;

/**
 * @author Joaquin Ponte Diaz
 * @since 13/07/2012
 * @version 1.0
 * 
 */
public class ClienteWebserviceRenapoCurpException  extends AbstractException implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "El servicio de RENAPO no esta disponible intente m\u00E1s tarde";
	private static final Integer codigo = new Integer (10001);

    public ClienteWebserviceRenapoCurpException() {
    	super(situacion, codigo);
    }

    public ClienteWebserviceRenapoCurpException(String causa) {
    	super(situacion + (!StringUtil.isEmpty(causa)? " : " + causa : ""), codigo);
    }
    
    /**
     * Se creo nuevo constructor para poder tipificar y especificar los codigos de errores,
     * 10001 error default
     * 10002 error de timeout
     * 10003 error en servicio en RENAPO
     * 10006 persona no localizada
     * 
     * @param causa
     * @param codigo
     */
    public ClienteWebserviceRenapoCurpException(String causa, Integer cod) {
    	super(situacion + " : " + causa, cod);
    }
    
    
    /**
     * Se creo nuevo constructor para poder tipificar y especificar los codigos de errores,
     * 10001 error default
     * 10002 error de timeout
     * 10003 error en servicio en RENAPO
     * 10006 persona no localizada
     * 
     * @param codigo
     */
    public ClienteWebserviceRenapoCurpException(Integer cod) {
    	super(situacion, cod);
    }

    
}
