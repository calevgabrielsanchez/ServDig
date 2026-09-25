/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class SolicitudProcesadaConErrorException extends AbstractException {
	
	private static final long serialVersionUID = 1L;

	private static final String situacion ="Ocurrio un error al procesar la solicitud, favor de verificar los datos.";
	
	private static final Integer codigo = new Integer (10000);

    public SolicitudProcesadaConErrorException() {
    	super(situacion, codigo);
       
    }
}
