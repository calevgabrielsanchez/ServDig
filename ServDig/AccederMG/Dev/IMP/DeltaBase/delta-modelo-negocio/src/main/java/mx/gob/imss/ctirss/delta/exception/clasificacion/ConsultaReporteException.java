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
public class ConsultaReporteException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5719427572248489446L;

	public ConsultaReporteException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
