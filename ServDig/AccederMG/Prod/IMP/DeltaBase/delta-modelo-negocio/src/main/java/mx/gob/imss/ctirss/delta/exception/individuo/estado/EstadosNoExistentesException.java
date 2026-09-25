package mx.gob.imss.ctirss.delta.exception.individuo.estado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class EstadosNoExistentesException extends AbstractException{

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los estados de la persona fisica no existen. Debe existir al menos 1");

	/**
	 * Constructor por omision
	 */
	public EstadosNoExistentesException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public EstadosNoExistentesException(String situacion){
		super(situacion , codigo);
	}

}
