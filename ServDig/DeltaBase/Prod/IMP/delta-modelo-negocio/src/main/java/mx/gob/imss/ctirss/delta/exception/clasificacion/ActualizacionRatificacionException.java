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
public class ActualizacionRatificacionException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2244841301565695268L;

	public ActualizacionRatificacionException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
