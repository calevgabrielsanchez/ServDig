/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.riesgosTrabajo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author STK
 *
 */
public class RiesgosTrabajoException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se proces\u00f3 correctamente la solicitud...");
	
	
	/**
	 * Constructor por omision
	 */
	public RiesgosTrabajoException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public RiesgosTrabajoException(String situacion){
		super(situacion, codigo);
	}
	
	public RiesgosTrabajoException(Integer codigoError, String situacion) {
		super(situacion, codigoError);
	}

	public static void throwException(Integer codigoError, String situacion) throws RiesgosTrabajoException{
		//Por favor no ensierren entre try catch
		throw new RiesgosTrabajoException(codigoError, situacion);
	}
}
