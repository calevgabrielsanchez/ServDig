package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Eduardo Gonzalez
 *
 */
@ApplicationException(rollback=true)
public class EstatusMovimientoException extends AbstractException {
	
	private static final long serialVersionUID = 1L;
	
	private static String situacion = "field.error.estatus.movimiento";
	private static Integer codigo = 999;

	public EstatusMovimientoException() {
		super(situacion , codigo);
	}
	
	public EstatusMovimientoException(String message) {
		super(situacion , codigo);
	}
	
}
