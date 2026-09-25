/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.serie;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import javax.ejb.ApplicationException;

/**
 * @author vanderluk
 *
 */
@ApplicationException(rollback=true)
public class ErrorAlActivarSerieException extends AbstractException {
	
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Ocurrio un error al Activar la Serie.";
	private static final Integer codigo = new Integer (110);
	
	public ErrorAlActivarSerieException(){
		super(situacion , codigo);
	}
	
	public ErrorAlActivarSerieException(String message){
		super(situacion , codigo);
	}
	

}
