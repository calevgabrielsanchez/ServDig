package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Guevara
 *
 */
@ApplicationException(rollback=true)
public class PermisosSolicitudException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2067102890823706579L;

	public PermisosSolicitudException(String situacion, Integer codigo) {
		super(situacion, codigo);
	}

}
