package mx.gob.imss.ctirss.delta.exception.individuo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException extends AbstractException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error de validaci\u00f3n se localizaron mas de una persona " +
			"con calificaci\u00f3n con la misma CURP"
			+ "con los mismos datos estadísticos"; 
	private static final String error = "Error de validaci\u00f3n: ";
	private static final Integer codigo = new Integer(10008000);

    public ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException() {
    	super(situacion, codigo);
    }

    public ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException (String causa) {
    	super(error + causa, codigo);
    }
    
}
