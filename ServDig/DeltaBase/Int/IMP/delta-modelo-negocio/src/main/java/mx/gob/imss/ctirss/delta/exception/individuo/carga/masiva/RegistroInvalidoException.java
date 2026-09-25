package mx.gob.imss.ctirss.delta.exception.individuo.carga.masiva;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RegistroInvalidoException extends AbstractException{

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Registro con errores en layout");

	/**
	 * Constructor por omision
	 */
	public RegistroInvalidoException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public RegistroInvalidoException(String situacion){
		super(situacion , codigo);
	}

}
