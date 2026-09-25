/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class ErrorComparacionDatosRENAPOException extends AbstractException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los datos de entrada no coinciden con los datos de la entidad externa RENAPO");
	
	/**
	 * Constructor por omision
	 */
	public ErrorComparacionDatosRENAPOException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de erro
	 * @param situacion
	 */
	public ErrorComparacionDatosRENAPOException(String situacion){
		super(situacion , codigo);
	}
	
	
}
