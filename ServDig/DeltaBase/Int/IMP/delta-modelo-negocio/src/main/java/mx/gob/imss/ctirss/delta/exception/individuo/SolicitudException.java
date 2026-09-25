/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author 191807 301112
 *
 */
public class SolicitudException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se proces\u00f3 correctamente la solicitud...");
	
	
	/**
	 * Constructor por omision
	 */
	public SolicitudException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public SolicitudException(String situacion){
		super(situacion, codigo);
	}

}
