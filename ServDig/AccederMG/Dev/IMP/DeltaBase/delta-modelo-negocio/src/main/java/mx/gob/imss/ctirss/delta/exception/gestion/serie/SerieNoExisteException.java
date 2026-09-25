/**
 *  Excepcion que ocurre cuando la Serie no existe.
 */
package mx.gob.imss.ctirss.delta.exception.gestion.serie;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;


/**
 * @author Lucio Duran Silva
 * @date 16/Octubre/2012
 *
 */
@ApplicationException(rollback=true)
public class SerieNoExisteException extends AbstractException {

	
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "La Serie no existe.";
	private static final Integer codigo = new Integer (110);
	
	public SerieNoExisteException(){
		super(situacion , codigo);
	}
	
	public SerieNoExisteException(String message){
		super(situacion , codigo);
	}
	
}
