/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class RFCNoLocalizadoEnEntidadExternaException extends AbstractException {

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("El RFC proporcionado no fue localizado en la entidad externa SAT");
	
	/**
	 * Constructor por omision
	 */
	public RFCNoLocalizadoEnEntidadExternaException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public RFCNoLocalizadoEnEntidadExternaException(String situacion){
		super(situacion , codigo);
	}
	
}
