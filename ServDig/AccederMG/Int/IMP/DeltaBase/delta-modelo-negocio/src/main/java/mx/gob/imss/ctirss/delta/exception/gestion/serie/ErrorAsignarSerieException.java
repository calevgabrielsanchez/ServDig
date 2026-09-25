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
public class ErrorAsignarSerieException extends AbstractException {
	private static final long serialVersionUID = 1L;
	private static final String situacion = "No se pudo asignar la Serie.";
	
	public ErrorAsignarSerieException(){
		super(situacion );
	}
	
	public ErrorAsignarSerieException(String message){
		super(message);
	}
}
