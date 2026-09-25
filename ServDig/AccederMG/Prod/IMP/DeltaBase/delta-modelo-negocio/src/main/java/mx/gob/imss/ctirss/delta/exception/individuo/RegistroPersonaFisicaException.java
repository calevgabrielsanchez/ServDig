/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author 191807 301112
 *
 */
public class RegistroPersonaFisicaException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se realiz\u00f3 el registro de la persona f\u00edsica...");
	
	
	/**
	 * Constructor por omision
	 */
	public RegistroPersonaFisicaException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public RegistroPersonaFisicaException(String situacion){
		super(situacion, codigo);
	}

}
