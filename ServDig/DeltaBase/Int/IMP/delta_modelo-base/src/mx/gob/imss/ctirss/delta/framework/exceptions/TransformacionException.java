/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class TransformacionException extends AbstractException {
	
	
	private static Integer codigo = new Integer(1);
	
	private static String mensaje = new String("Error en la transformaci-n de clases");
	
	
	
	
	public TransformacionException(){
		super(mensaje, codigo);
	}
	
	
	

}
