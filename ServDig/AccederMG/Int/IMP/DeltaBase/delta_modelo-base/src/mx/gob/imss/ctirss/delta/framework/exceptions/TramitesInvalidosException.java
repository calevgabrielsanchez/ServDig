/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author SRG
 *
 */
public class TramitesInvalidosException extends AbstractException {
	
	
    private static final long serialVersionUID = 1L;

    private static final String mensaje ="No sera registrada la solicitud en BDU al no contener tramites validos";
	
	private static final Integer codigo = new Integer(101);
	
	
	public TramitesInvalidosException(){
		super(mensaje, codigo);
	}
	
}
