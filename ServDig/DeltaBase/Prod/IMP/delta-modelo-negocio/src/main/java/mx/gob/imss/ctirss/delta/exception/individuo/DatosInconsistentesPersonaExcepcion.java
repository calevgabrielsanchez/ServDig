package mx.gob.imss.ctirss.delta.exception.individuo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DatosInconsistentesPersonaExcepcion extends AbstractException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error de validaci\u00f3n no coinciden los datos estadisticos de la persona " +
			"con el curp y/o rfc";
	private static final String error = "Error de validaci\u00f3n: ";
	private static final Integer codigo = new Integer(10008000);

    public DatosInconsistentesPersonaExcepcion() {
    	super(situacion, codigo);
    }

    public DatosInconsistentesPersonaExcepcion (String causa) {
    	super(error + causa, codigo);
    }
    
}
