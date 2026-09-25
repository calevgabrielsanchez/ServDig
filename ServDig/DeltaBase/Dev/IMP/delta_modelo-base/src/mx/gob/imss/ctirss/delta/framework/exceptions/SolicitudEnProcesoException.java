
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author 191807
 * @since 02/07/2012
 * @version 1.0
 * 
 */
public class SolicitudEnProcesoException  extends AbstractException implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String situacion ="La solicitud se encuentra en proceso.";
	
	private static final Integer codigo = new Integer (9999);

    public SolicitudEnProcesoException() {
    	super(situacion, codigo);
       
    }

}
