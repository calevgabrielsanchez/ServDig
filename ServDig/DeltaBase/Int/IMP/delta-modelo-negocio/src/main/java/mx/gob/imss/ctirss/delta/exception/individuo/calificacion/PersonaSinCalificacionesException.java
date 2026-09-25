package mx.gob.imss.ctirss.delta.exception.individuo.calificacion;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaSinCalificacionesException extends AbstractException {

	private static final long serialVersionUID = -6285562780730669323L;

	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"La persona no cuenta con calificaciones");

	/**
	 * Constructor por omision
	 */
	public PersonaSinCalificacionesException() {
		super(situacion, codigo);
	}

	/**
	 * Recibe la situacion que origino el problema
	 * 
	 * @param situacion
	 */
	public PersonaSinCalificacionesException(String situacion) {
		super(situacion, codigo);
	}

}
