/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class AsentamientoNoLocalizadoException extends AbstractException {
	
	
	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("El asentamiento no ha sido localizado");
	
	
	public AsentamientoNoLocalizadoException(){
		super(situacion , codigo);
	}

}
