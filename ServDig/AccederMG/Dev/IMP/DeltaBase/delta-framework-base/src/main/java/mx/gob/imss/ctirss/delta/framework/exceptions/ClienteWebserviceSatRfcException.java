
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Ponte Diaz
 * @since 13/07/2012
 * @version 1.0
 * 
 */
public class ClienteWebserviceSatRfcException  extends AbstractException implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String situacion ="No se pudieron recuperar los datos correspondientes al RFC del webservice del SAT";
	
	private static final Integer codigo = new Integer (10000);

    public ClienteWebserviceSatRfcException() {
    	super(situacion, codigo);
       
    }

}
