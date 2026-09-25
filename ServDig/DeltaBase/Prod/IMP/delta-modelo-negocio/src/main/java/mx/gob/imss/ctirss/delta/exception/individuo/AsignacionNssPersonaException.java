/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author jon
 *
 */
public class AsignacionNssPersonaException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se realiz\u00f3 la asignaci\u00f3 de Nss de la persona...");
	
	
	/**
	 * Constructor por omision
	 */
	public AsignacionNssPersonaException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public AsignacionNssPersonaException(String situacion){
		super(situacion, codigo);
	}

}
