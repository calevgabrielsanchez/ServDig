package mx.gob.imss.ctirss.delta.exception.individuo.identificador;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaSinIdentificadoresException extends AbstractException{

	private static final long serialVersionUID = 1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"La persona no cuenta con identificadores");

	/**
	 * Constructor por omision
	 */
	public PersonaSinIdentificadoresException() {
		super(situacion, codigo);
	}

	/**
	 * Recibe la la clave de la persona
	 * 
	 * @param situacion
	 */
	public PersonaSinIdentificadoresException(Long cvePersona) {
		super("La persona [cvePersona = " + cvePersona
				+ "] no cuenta con identificadores", codigo);
	}
}
