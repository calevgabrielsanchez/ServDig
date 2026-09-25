package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class CURPNoActualizadaRenapoException extends AbstractException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3038256917671070988L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Existe una CURP actualizada en RENAPO");
	
	/**
	 * Constructor por omision
	 */
	public CURPNoActualizadaRenapoException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public CURPNoActualizadaRenapoException(String situacion){
		super(situacion , codigo);
	}

}
