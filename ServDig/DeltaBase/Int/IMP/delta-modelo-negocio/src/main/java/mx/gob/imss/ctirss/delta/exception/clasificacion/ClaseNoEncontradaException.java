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
public class ClaseNoEncontradaException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static String situacion = "field.rectificacion.error.clase";
	private static Integer codigo = 999;

	public ClaseNoEncontradaException() {
		super(situacion , codigo);
	}
	
}
