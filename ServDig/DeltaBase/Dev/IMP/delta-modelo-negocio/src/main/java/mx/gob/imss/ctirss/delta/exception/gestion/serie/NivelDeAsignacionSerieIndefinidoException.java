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
public class NivelDeAsignacionSerieIndefinidoException extends
		AbstractException {
	private static final long serialVersionUID = 1L;
	private static final String situacion = "No se pudo determinar el Nivel de la Asignaci\u00F3n de la Serie.";
	
	public NivelDeAsignacionSerieIndefinidoException(){
		super(situacion );
	}
	
	public NivelDeAsignacionSerieIndefinidoException(String message){
		super(message);
	}
}
