/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class VialidadesNoLocalizadasException extends AbstractException {
	
	
	
	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("No se encontraron vialidades en la localidad.");
	
	
	
	
	
	public VialidadesNoLocalizadasException(){
		super(situacion , codigo);
	}
	
	
	/**
	 * 
	 * @param situacion
	 */
	public VialidadesNoLocalizadasException(String situacion){
		super(situacion , codigo);
	}

}
