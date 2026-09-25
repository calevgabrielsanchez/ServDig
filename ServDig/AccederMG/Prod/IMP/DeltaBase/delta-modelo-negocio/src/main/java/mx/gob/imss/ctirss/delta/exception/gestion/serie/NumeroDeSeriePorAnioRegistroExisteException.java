/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.serie;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
@ApplicationException(rollback=true)
public class NumeroDeSeriePorAnioRegistroExisteException extends
		AbstractException {

	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "El N\u00FAmero de Serie para el A\u00F1o de Registro ya existe.";
	private static final Integer codigo = new Integer (110);
	
	public NumeroDeSeriePorAnioRegistroExisteException(){
		super(situacion , codigo);
	}
	
	public NumeroDeSeriePorAnioRegistroExisteException(String message){
		super(message , codigo);
	}
	
}
