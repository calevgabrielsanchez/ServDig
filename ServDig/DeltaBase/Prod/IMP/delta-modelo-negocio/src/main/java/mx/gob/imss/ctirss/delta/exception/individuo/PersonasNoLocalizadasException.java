/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class PersonasNoLocalizadasException extends AbstractException {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se localizo a ningun registro de personas con los datos proporcionados.");
	
	/**
	 * Constructor por omision
	 */
	public PersonasNoLocalizadasException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de erro
	 * @param situacion
	 */
	public PersonasNoLocalizadasException(String situacion){
		super(situacion , codigo);
	}

}
