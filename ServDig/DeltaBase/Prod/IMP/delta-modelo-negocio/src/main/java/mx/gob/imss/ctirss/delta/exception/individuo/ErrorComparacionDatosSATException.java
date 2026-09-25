/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class ErrorComparacionDatosSATException extends AbstractException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los datos de entrada no coinciden con los datos de la entidad externa SAT");
	
	/**
	 * Constructor por omision
	 */
	public ErrorComparacionDatosSATException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de erro
	 * @param situacion
	 */
	public ErrorComparacionDatosSATException(String situacion){
		super(situacion , codigo);
	}
	
	
}
