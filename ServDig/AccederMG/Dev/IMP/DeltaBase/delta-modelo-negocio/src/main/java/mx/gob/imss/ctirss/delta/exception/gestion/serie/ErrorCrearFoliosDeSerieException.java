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
public class ErrorCrearFoliosDeSerieException extends AbstractException {


	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error al crear los Folios por A\u00F1os de Nacimiento para la Serie.";
	private static final Integer codigo = new Integer (110);
	
	public ErrorCrearFoliosDeSerieException(){
		super(situacion , codigo);
	}
	
	public ErrorCrearFoliosDeSerieException(String message){
		super(message , codigo);
	}
	
}
