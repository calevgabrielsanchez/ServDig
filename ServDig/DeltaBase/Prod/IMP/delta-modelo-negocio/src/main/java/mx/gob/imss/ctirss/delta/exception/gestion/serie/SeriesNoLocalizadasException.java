/**
 * Excepcion del flujo de negocio de la asignacion del NSS, esta excepcion ocurre
 * cuando no existen Series disponibles para la asignacion.
 */
package mx.gob.imss.ctirss.delta.exception.gestion.serie;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import javax.ejb.ApplicationException;

/**
 * @author Lucio Duran Silva
 * @date 16/Octubre/2012
 *
 */
@ApplicationException(rollback=false)
public class SeriesNoLocalizadasException extends AbstractException {
	
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "No existen Series disponibles para la Asignaci\u00F3n.";
	private static final Integer codigo = new Integer (110);
	
	public SeriesNoLocalizadasException(){
		super(situacion , codigo);
	}
	
	public SeriesNoLocalizadasException(String message){
		super(situacion , codigo);
	}

}
