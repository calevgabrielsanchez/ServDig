/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import java.io.Serializable;

/**
 * @author vanderluk
 *
 */
public class NumeroMaximoResultadosSuperadoException extends AbstractException implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private static final String mensaje ="La b\u00FAsqueda ha superado el m\u00E1ximo permitido. Especifique sus datos y vuelva a intentarlo.";
	
	private static final Integer codigo = new Integer(101);
	
	
	public NumeroMaximoResultadosSuperadoException(){
		super(mensaje, codigo);
	}

}
