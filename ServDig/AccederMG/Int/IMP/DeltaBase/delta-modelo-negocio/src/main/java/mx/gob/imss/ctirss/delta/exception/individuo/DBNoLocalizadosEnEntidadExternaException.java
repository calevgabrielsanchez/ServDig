/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author 191807 071212
 *
 */
public class DBNoLocalizadosEnEntidadExternaException extends AbstractException {

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los Datos Basicos proporcionados no fueron localizados en la entidad externa RENAPO");
	
	/**
	 * Constructor por omision
	 */
	public DBNoLocalizadosEnEntidadExternaException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public DBNoLocalizadosEnEntidadExternaException(String situacion){
		super(situacion , codigo);
	}
	
}
