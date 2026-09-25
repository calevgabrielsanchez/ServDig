/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author STK
 *
 */
@ApplicationException(rollback=true)
public class CorreccionDatosAseguradoException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se proces\u00f3 correctamente la solicitud...");
	
	
	/**
	 * Constructor por omision
	 */
	public CorreccionDatosAseguradoException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public CorreccionDatosAseguradoException(String situacion){
		super(situacion, codigo);
	}

}
