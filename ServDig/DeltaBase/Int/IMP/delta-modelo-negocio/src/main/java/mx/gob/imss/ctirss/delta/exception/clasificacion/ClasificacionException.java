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
public class ClasificacionException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1141298213258357783L;

	public ClasificacionException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
