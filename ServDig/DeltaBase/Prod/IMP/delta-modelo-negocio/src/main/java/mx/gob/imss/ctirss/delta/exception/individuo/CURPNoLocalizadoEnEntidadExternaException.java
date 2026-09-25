/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class CURPNoLocalizadoEnEntidadExternaException extends AbstractException {

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("El CURP proporcionado no fue localizado en la entidad externa RENAPO");
	
	/**
	 * Constructor por omision
	 */
	public CURPNoLocalizadoEnEntidadExternaException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public CURPNoLocalizadoEnEntidadExternaException(String situacion){
		super(situacion , codigo);
	}
	
}
