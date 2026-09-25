package mx.gob.imss.ctirss.delta.exception.individuo.calificacion;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class CalificacionesNoExistentesException extends AbstractException{

	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Las calificaciones de la persona no existen. Debe existir al menos 1");

	/**
	 * Constructor por omision
	 */
	public CalificacionesNoExistentesException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public CalificacionesNoExistentesException(String situacion){
		super(situacion , codigo);
	}

}
