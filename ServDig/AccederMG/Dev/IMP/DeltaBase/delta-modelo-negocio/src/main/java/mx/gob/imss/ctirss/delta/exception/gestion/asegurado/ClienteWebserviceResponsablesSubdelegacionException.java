/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;


/**
 * @author STK
 *
 */
public class ClienteWebserviceResponsablesSubdelegacionException  extends AbstractException implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String situacion ="No se pudieron recuperar los datos correspondientes a los Responsables por Subdelegaci\u00f3n";
	
	private static final Integer codigo = new Integer (10000);
	
	public static final Integer CODIGO_ERROR_THREAD_INTERRUPT = new Integer (10002);
	public static final Integer CODIGO_ERROR_WS = new Integer (10003);

    public ClienteWebserviceResponsablesSubdelegacionException() {
    	super(situacion, codigo);
       
    }

    public ClienteWebserviceResponsablesSubdelegacionException(int codigo) {
    	super(situacion, codigo);       
    }

    public ClienteWebserviceResponsablesSubdelegacionException(String situacion) {
    	super(situacion, codigo);       
    }

    public ClienteWebserviceResponsablesSubdelegacionException(String situacion,int codigo) {
    	super(situacion, codigo);       
    }

}

