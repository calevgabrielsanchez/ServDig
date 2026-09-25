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
public class ErrorGuardarSerieException extends AbstractException {

	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error al guardar la Serie.";
	private static final Integer codigo = new Integer (110);
	
	public ErrorGuardarSerieException(){
		super(situacion , codigo);
	}
	
	public ErrorGuardarSerieException(String message){
		super(message , codigo);
	}
	
	
}
