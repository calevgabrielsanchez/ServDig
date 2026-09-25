/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Guevara
 *
 */
@ApplicationException(rollback=true)
public class ExportarReporteException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6618280522328282936L;

	public ExportarReporteException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
