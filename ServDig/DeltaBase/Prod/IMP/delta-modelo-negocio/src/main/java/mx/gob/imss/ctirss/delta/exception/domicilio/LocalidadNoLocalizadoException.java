package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author vanderluk
 *
 */
public class LocalidadNoLocalizadoException extends AbstractException {
	
	
	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("La localidad no ha sido localizada");
	
	
	public LocalidadNoLocalizadoException(){
		super(situacion , codigo);
	}

}
