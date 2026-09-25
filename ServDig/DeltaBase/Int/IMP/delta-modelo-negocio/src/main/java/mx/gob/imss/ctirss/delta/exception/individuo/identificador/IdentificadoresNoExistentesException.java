package mx.gob.imss.ctirss.delta.exception.individuo.identificador;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class IdentificadoresNoExistentesException extends AbstractException{

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Los identificadores no existen. Debe existir al menos 1");

	/**
	 * Constructor por omision
	 */
	public IdentificadoresNoExistentesException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public IdentificadoresNoExistentesException(String situacion){
		super(situacion , codigo);
	}

}
