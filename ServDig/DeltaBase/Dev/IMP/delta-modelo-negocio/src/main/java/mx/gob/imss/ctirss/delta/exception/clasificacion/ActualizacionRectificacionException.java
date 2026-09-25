package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Guevara
 *
 */
@ApplicationException(rollback=true)
public class ActualizacionRectificacionException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2067102890823706579L;

	public ActualizacionRectificacionException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
