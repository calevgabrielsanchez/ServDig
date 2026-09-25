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
public class SerieInactivaException extends AbstractException {

	
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "La Serie no se encuentra Activa.";
	private static final Integer codigo = new Integer (110);
	
	public SerieInactivaException(){
		super(situacion , codigo);
	}
	
	public SerieInactivaException(String message){
		super(situacion , codigo);
	}
	
}
