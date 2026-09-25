/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author jon
 *
 */
public class AltaNssCanaseException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se realiz\u00f3 el alta en CANASE del Nss...");
	
	
	/**
	 * Constructor por omision
	 */
	public AltaNssCanaseException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public AltaNssCanaseException(String situacion){
		super(situacion, codigo);
	}

}
