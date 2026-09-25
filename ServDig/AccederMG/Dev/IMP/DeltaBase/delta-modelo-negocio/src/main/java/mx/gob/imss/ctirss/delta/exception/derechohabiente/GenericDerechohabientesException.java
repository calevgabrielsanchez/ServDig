package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class GenericDerechohabientesException extends AbstractException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8115222241949516416L;
	private static Integer code = 1;
    private static String msg = "Excepcion no categorizada en el sistema :" ;

	public GenericDerechohabientesException(Exception e){
		super(msg + " ["+ e.getMessage()+"]", code);
		
	}
}
