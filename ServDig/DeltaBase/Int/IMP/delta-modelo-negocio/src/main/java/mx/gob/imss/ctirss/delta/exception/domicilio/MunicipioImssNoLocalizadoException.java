package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class MunicipioImssNoLocalizadoException extends AbstractException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3729226650951987883L;

	/**
	 * 
	 */


	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("El municipio IMSS no ha sido localizado");
	
	
	public MunicipioImssNoLocalizadoException(){
		super(situacion , codigo);
	}

}